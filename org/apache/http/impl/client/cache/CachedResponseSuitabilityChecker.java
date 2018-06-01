package org.apache.http.impl.client.cache;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

@Immutable
class CachedResponseSuitabilityChecker {
    private final float heuristicCoefficient;
    private final long heuristicDefaultLifetime;
    private final Log log;
    private final boolean sharedCache;
    private final boolean useHeuristicCaching;
    private final CacheValidityPolicy validityStrategy;

    CachedResponseSuitabilityChecker(CacheValidityPolicy validityStrategy, CacheConfig config) {
        this.log = LogFactory.getLog(getClass());
        this.validityStrategy = validityStrategy;
        this.sharedCache = config.isSharedCache();
        this.useHeuristicCaching = config.isHeuristicCachingEnabled();
        this.heuristicCoefficient = config.getHeuristicCoefficient();
        this.heuristicDefaultLifetime = config.getHeuristicDefaultLifetime();
    }

    CachedResponseSuitabilityChecker(CacheConfig config) {
        this(new CacheValidityPolicy(), config);
    }

    private boolean isFreshEnough(HttpCacheEntry entry, HttpRequest request, Date now) {
        if (this.validityStrategy.isResponseFresh(entry, now)) {
            return true;
        }
        if (this.useHeuristicCaching) {
            if (this.validityStrategy.isResponseHeuristicallyFresh(entry, now, this.heuristicCoefficient, this.heuristicDefaultLifetime)) {
                return true;
            }
        }
        if (originInsistsOnFreshness(entry)) {
            return false;
        }
        long maxstale = getMaxStale(request);
        if (maxstale == -1) {
            return false;
        }
        return maxstale > this.validityStrategy.getStalenessSecs(entry, now);
    }

    private boolean originInsistsOnFreshness(HttpCacheEntry entry) {
        if (this.validityStrategy.mustRevalidate(entry)) {
            return true;
        }
        if (!this.sharedCache) {
            return false;
        }
        if (this.validityStrategy.proxyRevalidate(entry) || this.validityStrategy.hasCacheControlDirective(entry, "s-maxage")) {
            return true;
        }
        return false;
    }

    private long getMaxStale(HttpRequest request) {
        long maxstale = -1;
        for (Header h : request.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(elt.getName())) {
                    if ((elt.getValue() == null || "".equals(elt.getValue().trim())) && maxstale == -1) {
                        maxstale = Long.MAX_VALUE;
                    } else {
                        try {
                            long val = Long.parseLong(elt.getValue());
                            if (val < 0) {
                                val = 0;
                            }
                            if (maxstale == -1 || val < maxstale) {
                                maxstale = val;
                            }
                        } catch (NumberFormatException e) {
                            maxstale = 0;
                        }
                    }
                }
            }
        }
        return maxstale;
    }

    public boolean canCachedResponseBeUsed(HttpHost host, HttpRequest request, HttpCacheEntry entry, Date now) {
        if (!isFreshEnough(entry, request, now)) {
            this.log.debug("Cache entry was not fresh enough");
            return false;
        } else if (!this.validityStrategy.contentLengthHeaderMatchesActualLength(entry)) {
            this.log.debug("Cache entry Content-Length and header information do not match");
            return false;
        } else if (hasUnsupportedConditionalHeaders(request)) {
            this.log.debug("Request contained conditional headers we don't handle");
            return false;
        } else if (isConditional(request) && !allConditionalsMatch(request, entry, now)) {
            return false;
        } else {
            for (Header ccHdr : request.getHeaders("Cache-Control")) {
                for (HeaderElement elt : ccHdr.getElements()) {
                    if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(elt.getName())) {
                        this.log.debug("Response contained NO CACHE directive, cache was not suitable");
                        return false;
                    }
                    if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(elt.getName())) {
                        this.log.debug("Response contained NO STORE directive, cache was not suitable");
                        return false;
                    }
                    if ("max-age".equals(elt.getName())) {
                        try {
                            if (this.validityStrategy.getCurrentAgeSecs(entry, now) > ((long) Integer.parseInt(elt.getValue()))) {
                                this.log.debug("Response from cache was NOT suitable due to max age");
                                return false;
                            }
                        } catch (NumberFormatException ex) {
                            this.log.debug("Response from cache was malformed: " + ex.getMessage());
                            return false;
                        }
                    }
                    if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(elt.getName())) {
                        try {
                            if (this.validityStrategy.getFreshnessLifetimeSecs(entry) > ((long) Integer.parseInt(elt.getValue()))) {
                                this.log.debug("Response from cache was not suitable due to Max stale freshness");
                                return false;
                            }
                        } catch (NumberFormatException ex2) {
                            this.log.debug("Response from cache was malformed: " + ex2.getMessage());
                            return false;
                        }
                    }
                    if (HeaderConstants.CACHE_CONTROL_MIN_FRESH.equals(elt.getName())) {
                        try {
                            long minfresh = Long.parseLong(elt.getValue());
                            if (minfresh < 0) {
                                return false;
                            }
                            if (this.validityStrategy.getFreshnessLifetimeSecs(entry) - this.validityStrategy.getCurrentAgeSecs(entry, now) < minfresh) {
                                this.log.debug("Response from cache was not suitable due to min fresh freshness requirement");
                                return false;
                            }
                        } catch (NumberFormatException ex22) {
                            this.log.debug("Response from cache was malformed: " + ex22.getMessage());
                            return false;
                        }
                    }
                }
            }
            this.log.debug("Response from cache was suitable");
            return true;
        }
    }

    public boolean isConditional(HttpRequest request) {
        return hasSupportedEtagVadlidator(request) || hasSupportedLastModifiedValidator(request);
    }

    public boolean allConditionalsMatch(HttpRequest request, HttpCacheEntry entry, Date now) {
        boolean hasEtagValidator = hasSupportedEtagVadlidator(request);
        boolean hasLastModifiedValidator = hasSupportedLastModifiedValidator(request);
        boolean etagValidatorMatches;
        if (hasEtagValidator) {
            etagValidatorMatches = etagValidtorMatches(request, entry);
        } else {
            etagValidatorMatches = false;
        }
        boolean lastModifiedValidatorMatches;
        if (hasLastModifiedValidator) {
            lastModifiedValidatorMatches = lastModifiedValidatorMatches(request, entry, now);
        } else {
            lastModifiedValidatorMatches = false;
        }
        if (hasEtagValidator && hasLastModifiedValidator && (!etagValidatorMatches || !lastModifiedValidatorMatches)) {
            return false;
        }
        if (hasEtagValidator && !etagValidatorMatches) {
            return false;
        }
        if (!hasLastModifiedValidator || lastModifiedValidatorMatches) {
            return true;
        }
        return false;
    }

    private boolean hasUnsupportedConditionalHeaders(HttpRequest request) {
        return (request.getFirstHeader("If-Range") == null && request.getFirstHeader("If-Match") == null && !hasValidDateField(request, "If-Unmodified-Since")) ? false : true;
    }

    private boolean hasSupportedEtagVadlidator(HttpRequest request) {
        return request.containsHeader("If-None-Match");
    }

    private boolean hasSupportedLastModifiedValidator(HttpRequest request) {
        return hasValidDateField(request, "If-Modified-Since");
    }

    private boolean etagValidtorMatches(HttpRequest request, HttpCacheEntry entry) {
        Header etagHeader = entry.getFirstHeader("ETag");
        String etag = etagHeader != null ? etagHeader.getValue() : null;
        Header[] ifNoneMatch = request.getHeaders("If-None-Match");
        if (ifNoneMatch != null) {
            for (Header h : ifNoneMatch) {
                for (HeaderElement elt : h.getElements()) {
                    String reqEtag = elt.toString();
                    if (("*".equals(reqEtag) && etag != null) || reqEtag.equals(etag)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean lastModifiedValidatorMatches(HttpRequest request, HttpCacheEntry entry, Date now) {
        Header lastModifiedHeader = entry.getFirstHeader("Last-Modified");
        Date lastModified = null;
        if (lastModifiedHeader != null) {
            try {
                lastModified = DateUtils.parseDate(lastModifiedHeader.getValue());
            } catch (DateParseException e) {
            }
        }
        if (lastModified == null) {
            return false;
        }
        Header[] arr$ = request.getHeaders("If-Modified-Since");
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            try {
                Date ifModifiedSince = DateUtils.parseDate(arr$[i$].getValue());
                if (ifModifiedSince.after(now) || lastModified.after(ifModifiedSince)) {
                    return false;
                }
                i$++;
            } catch (DateParseException e2) {
            }
        }
        return true;
    }

    private boolean hasValidDateField(HttpRequest request, String headerName) {
        Header[] arr$ = request.getHeaders(headerName);
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            try {
                DateUtils.parseDate(arr$[i$].getValue());
                return true;
            } catch (DateParseException e) {
                i$++;
            }
        }
        return false;
    }
}
