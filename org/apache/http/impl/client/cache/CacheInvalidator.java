package org.apache.http.impl.client.cache;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

@ThreadSafe
class CacheInvalidator {
    private final CacheKeyGenerator cacheKeyGenerator;
    private final Log log = LogFactory.getLog(getClass());
    private final HttpCacheStorage storage;

    public CacheInvalidator(CacheKeyGenerator uriExtractor, HttpCacheStorage storage) {
        this.cacheKeyGenerator = uriExtractor;
        this.storage = storage;
    }

    public void flushInvalidatedCacheEntries(HttpHost host, HttpRequest req) {
        if (requestShouldNotBeCached(req)) {
            this.log.debug("Request should not be cached");
            String theUri = this.cacheKeyGenerator.getURI(host, req);
            HttpCacheEntry parent = getEntry(theUri);
            this.log.debug("parent entry: " + parent);
            if (parent != null) {
                for (String variantURI : parent.getVariantMap().values()) {
                    flushEntry(variantURI);
                }
                flushEntry(theUri);
            }
            URL reqURL = getAbsoluteURL(theUri);
            if (reqURL == null) {
                this.log.error("Couldn't transform request into valid URL");
                return;
            }
            Header clHdr = req.getFirstHeader(HttpHeaders.CONTENT_LOCATION);
            if (clHdr != null) {
                String contentLocation = clHdr.getValue();
                if (!flushAbsoluteUriFromSameHost(reqURL, contentLocation)) {
                    flushRelativeUriFromSameHost(reqURL, contentLocation);
                }
            }
            Header lHdr = req.getFirstHeader(HttpHeaders.LOCATION);
            if (lHdr != null) {
                flushAbsoluteUriFromSameHost(reqURL, lHdr.getValue());
            }
        }
    }

    private void flushEntry(String uri) {
        try {
            this.storage.removeEntry(uri);
        } catch (IOException ioe) {
            this.log.warn("unable to flush cache entry", ioe);
        }
    }

    private HttpCacheEntry getEntry(String theUri) {
        try {
            return this.storage.getEntry(theUri);
        } catch (IOException ioe) {
            this.log.warn("could not retrieve entry from storage", ioe);
            return null;
        }
    }

    protected void flushUriIfSameHost(URL requestURL, URL targetURL) {
        URL canonicalTarget = getAbsoluteURL(this.cacheKeyGenerator.canonicalizeUri(targetURL.toString()));
        if (canonicalTarget != null && canonicalTarget.getAuthority().equalsIgnoreCase(requestURL.getAuthority())) {
            flushEntry(canonicalTarget.toString());
        }
    }

    protected void flushRelativeUriFromSameHost(URL reqURL, String relUri) {
        URL relURL = getRelativeURL(reqURL, relUri);
        if (relURL != null) {
            flushUriIfSameHost(reqURL, relURL);
        }
    }

    protected boolean flushAbsoluteUriFromSameHost(URL reqURL, String uri) {
        URL absURL = getAbsoluteURL(uri);
        if (absURL == null) {
            return false;
        }
        flushUriIfSameHost(reqURL, absURL);
        return true;
    }

    private URL getAbsoluteURL(String uri) {
        try {
            return new URL(uri);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private URL getRelativeURL(URL reqURL, String relUri) {
        try {
            return new URL(reqURL, relUri);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    protected boolean requestShouldNotBeCached(HttpRequest req) {
        return notGetOrHeadRequest(req.getRequestLine().getMethod());
    }

    private boolean notGetOrHeadRequest(String method) {
        return ("GET".equals(method) || "HEAD".equals(method)) ? false : true;
    }

    public void flushInvalidatedCacheEntries(HttpHost host, HttpRequest request, HttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status <= 299) {
            URL reqURL = getAbsoluteURL(this.cacheKeyGenerator.getURI(host, request));
            if (reqURL != null) {
                URL canonURL = getContentLocationURL(reqURL, response);
                if (canonURL != null) {
                    HttpCacheEntry entry = getEntry(this.cacheKeyGenerator.canonicalizeUri(canonURL.toString()));
                    if (entry != null && responseDateNewerThanEntryDate(response, entry) && responseAndEntryEtagsDiffer(response, entry)) {
                        flushUriIfSameHost(reqURL, canonURL);
                    }
                }
            }
        }
    }

    private URL getContentLocationURL(URL reqURL, HttpResponse response) {
        Header clHeader = response.getFirstHeader(HttpHeaders.CONTENT_LOCATION);
        if (clHeader == null) {
            return null;
        }
        String contentLocation = clHeader.getValue();
        URL canonURL = getAbsoluteURL(contentLocation);
        return canonURL == null ? getRelativeURL(reqURL, contentLocation) : canonURL;
    }

    private boolean responseAndEntryEtagsDiffer(HttpResponse response, HttpCacheEntry entry) {
        Header entryEtag = entry.getFirstHeader("ETag");
        Header responseEtag = response.getFirstHeader("ETag");
        if (entryEtag == null || responseEtag == null || entryEtag.getValue().equals(responseEtag.getValue())) {
            return false;
        }
        return true;
    }

    private boolean responseDateNewerThanEntryDate(HttpResponse response, HttpCacheEntry entry) {
        boolean z = false;
        Header entryDateHeader = entry.getFirstHeader("Date");
        Header responseDateHeader = response.getFirstHeader("Date");
        if (!(entryDateHeader == null || responseDateHeader == null)) {
            try {
                z = DateUtils.parseDate(responseDateHeader.getValue()).after(DateUtils.parseDate(entryDateHeader.getValue()));
            } catch (DateParseException e) {
            }
        }
        return z;
    }
}
