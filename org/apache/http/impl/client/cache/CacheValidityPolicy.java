package org.apache.http.impl.client.cache;

import java.util.Date;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

@Immutable
class CacheValidityPolicy {
    public static final long MAX_AGE = 2147483648L;

    CacheValidityPolicy() {
    }

    public long getCurrentAgeSecs(HttpCacheEntry entry, Date now) {
        return getCorrectedInitialAgeSecs(entry) + getResidentTimeSecs(entry, now);
    }

    public long getFreshnessLifetimeSecs(HttpCacheEntry entry) {
        long maxage = getMaxAge(entry);
        if (maxage > -1) {
            return maxage;
        }
        Date dateValue = getDateValue(entry);
        if (dateValue == null) {
            return 0;
        }
        Date expiry = getExpirationDate(entry);
        if (expiry == null) {
            return 0;
        }
        return (expiry.getTime() - dateValue.getTime()) / 1000;
    }

    public boolean isResponseFresh(HttpCacheEntry entry, Date now) {
        return getCurrentAgeSecs(entry, now) < getFreshnessLifetimeSecs(entry);
    }

    public boolean isResponseHeuristicallyFresh(HttpCacheEntry entry, Date now, float coefficient, long defaultLifetime) {
        return getCurrentAgeSecs(entry, now) < getHeuristicFreshnessLifetimeSecs(entry, coefficient, defaultLifetime);
    }

    public long getHeuristicFreshnessLifetimeSecs(HttpCacheEntry entry, float coefficient, long defaultLifetime) {
        Date dateValue = getDateValue(entry);
        Date lastModifiedValue = getLastModifiedValue(entry);
        if (dateValue == null || lastModifiedValue == null) {
            return defaultLifetime;
        }
        long diff = dateValue.getTime() - lastModifiedValue.getTime();
        if (diff < 0) {
            return 0;
        }
        return (long) (((float) (diff / 1000)) * coefficient);
    }

    public boolean isRevalidatable(HttpCacheEntry entry) {
        return (entry.getFirstHeader("ETag") == null && entry.getFirstHeader("Last-Modified") == null) ? false : true;
    }

    public boolean mustRevalidate(HttpCacheEntry entry) {
        return hasCacheControlDirective(entry, HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE);
    }

    public boolean proxyRevalidate(HttpCacheEntry entry) {
        return hasCacheControlDirective(entry, HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE);
    }

    public boolean mayReturnStaleWhileRevalidating(HttpCacheEntry entry, Date now) {
        for (Header h : entry.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if ("stale-while-revalidate".equalsIgnoreCase(elt.getName())) {
                    try {
                        if (getStalenessSecs(entry, now) <= ((long) Integer.parseInt(elt.getValue()))) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        return false;
    }

    public boolean mayReturnStaleIfError(HttpRequest request, HttpCacheEntry entry, Date now) {
        long stalenessSecs = getStalenessSecs(entry, now);
        return mayReturnStaleIfError(request.getHeaders("Cache-Control"), stalenessSecs) || mayReturnStaleIfError(entry.getHeaders("Cache-Control"), stalenessSecs);
    }

    private boolean mayReturnStaleIfError(Header[] headers, long stalenessSecs) {
        boolean result = false;
        for (Header h : headers) {
            for (HeaderElement elt : h.getElements()) {
                if ("stale-if-error".equals(elt.getName())) {
                    try {
                        if (stalenessSecs <= ((long) Integer.parseInt(elt.getValue()))) {
                            result = true;
                            break;
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        return result;
    }

    protected Date getDateValue(HttpCacheEntry entry) {
        Date date = null;
        Header dateHdr = entry.getFirstHeader("Date");
        if (dateHdr != null) {
            try {
                date = DateUtils.parseDate(dateHdr.getValue());
            } catch (DateParseException e) {
            }
        }
        return date;
    }

    protected Date getLastModifiedValue(HttpCacheEntry entry) {
        Date date = null;
        Header dateHdr = entry.getFirstHeader("Last-Modified");
        if (dateHdr != null) {
            try {
                date = DateUtils.parseDate(dateHdr.getValue());
            } catch (DateParseException e) {
            }
        }
        return date;
    }

    protected long getContentLengthValue(HttpCacheEntry entry) {
        long j = -1;
        Header cl = entry.getFirstHeader("Content-Length");
        if (cl != null) {
            try {
                j = Long.parseLong(cl.getValue());
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    protected boolean contentLengthHeaderMatchesActualLength(HttpCacheEntry entry) {
        return getContentLengthValue(entry) == entry.getResource().length();
    }

    protected long getApparentAgeSecs(HttpCacheEntry entry) {
        Date dateValue = getDateValue(entry);
        if (dateValue == null) {
            return MAX_AGE;
        }
        long diff = entry.getResponseDate().getTime() - dateValue.getTime();
        if (diff >= 0) {
            return diff / 1000;
        }
        return 0;
    }

    protected long getAgeValue(HttpCacheEntry entry) {
        long ageValue = 0;
        for (Header hdr : entry.getHeaders("Age")) {
            long hdrAge;
            try {
                hdrAge = Long.parseLong(hdr.getValue());
                if (hdrAge < 0) {
                    hdrAge = MAX_AGE;
                }
            } catch (NumberFormatException e) {
                hdrAge = MAX_AGE;
            }
            if (hdrAge > ageValue) {
                ageValue = hdrAge;
            }
        }
        return ageValue;
    }

    protected long getCorrectedReceivedAgeSecs(HttpCacheEntry entry) {
        long apparentAge = getApparentAgeSecs(entry);
        long ageValue = getAgeValue(entry);
        return apparentAge > ageValue ? apparentAge : ageValue;
    }

    protected long getResponseDelaySecs(HttpCacheEntry entry) {
        return (entry.getResponseDate().getTime() - entry.getRequestDate().getTime()) / 1000;
    }

    protected long getCorrectedInitialAgeSecs(HttpCacheEntry entry) {
        return getCorrectedReceivedAgeSecs(entry) + getResponseDelaySecs(entry);
    }

    protected Date getCurrentDate() {
        return new Date();
    }

    protected long getResidentTimeSecs(HttpCacheEntry entry, Date now) {
        return (now.getTime() - entry.getResponseDate().getTime()) / 1000;
    }

    protected long getMaxAge(HttpCacheEntry entry) {
        long maxage = -1;
        for (Header hdr : entry.getHeaders("Cache-Control")) {
            for (HeaderElement elt : hdr.getElements()) {
                if ("max-age".equals(elt.getName()) || "s-maxage".equals(elt.getName())) {
                    try {
                        long currMaxAge = Long.parseLong(elt.getValue());
                        if (maxage == -1 || currMaxAge < maxage) {
                            maxage = currMaxAge;
                        }
                    } catch (NumberFormatException e) {
                        maxage = 0;
                    }
                }
            }
        }
        return maxage;
    }

    protected Date getExpirationDate(HttpCacheEntry entry) {
        Date date = null;
        Header expiresHeader = entry.getFirstHeader("Expires");
        if (expiresHeader != null) {
            try {
                date = DateUtils.parseDate(expiresHeader.getValue());
            } catch (DateParseException e) {
            }
        }
        return date;
    }

    public boolean hasCacheControlDirective(HttpCacheEntry entry, String directive) {
        for (Header h : entry.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if (directive.equalsIgnoreCase(elt.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public long getStalenessSecs(HttpCacheEntry entry, Date now) {
        long age = getCurrentAgeSecs(entry, now);
        long freshness = getFreshnessLifetimeSecs(entry);
        if (age <= freshness) {
            return 0;
        }
        return age - freshness;
    }
}
