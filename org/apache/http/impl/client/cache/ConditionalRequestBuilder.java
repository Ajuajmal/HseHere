package org.apache.http.impl.client.cache;

import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.ProtocolException;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.impl.client.RequestWrapper;

@Immutable
class ConditionalRequestBuilder {
    private static final Log log = LogFactory.getLog(ConditionalRequestBuilder.class);

    ConditionalRequestBuilder() {
    }

    public HttpRequest buildConditionalRequest(HttpRequest request, HttpCacheEntry cacheEntry) throws ProtocolException {
        RequestWrapper wrapperRequest = new RequestWrapper(request);
        wrapperRequest.resetHeaders();
        Header eTag = cacheEntry.getFirstHeader("ETag");
        if (eTag != null) {
            wrapperRequest.setHeader("If-None-Match", eTag.getValue());
        }
        Header lastModified = cacheEntry.getFirstHeader("Last-Modified");
        if (lastModified != null) {
            wrapperRequest.setHeader("If-Modified-Since", lastModified.getValue());
        }
        boolean mustRevalidate = false;
        for (Header h : cacheEntry.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE.equalsIgnoreCase(elt.getName()) || HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE.equalsIgnoreCase(elt.getName())) {
                    mustRevalidate = true;
                    break;
                }
            }
        }
        if (mustRevalidate) {
            wrapperRequest.addHeader("Cache-Control", "max-age=0");
        }
        return wrapperRequest;
    }

    public HttpRequest buildConditionalRequestFromVariants(HttpRequest request, Map<String, Variant> variants) {
        try {
            HttpRequest wrapperRequest = new RequestWrapper(request);
            wrapperRequest.resetHeaders();
            StringBuilder etags = new StringBuilder();
            boolean first = true;
            for (String etag : variants.keySet()) {
                if (!first) {
                    etags.append(",");
                }
                first = false;
                etags.append(etag);
            }
            wrapperRequest.setHeader("If-None-Match", etags.toString());
            return wrapperRequest;
        } catch (ProtocolException pe) {
            log.warn("unable to build conditional request", pe);
            return request;
        }
    }

    public HttpRequest buildUnconditionalRequest(HttpRequest request, HttpCacheEntry entry) {
        try {
            RequestWrapper wrapped = new RequestWrapper(request);
            wrapped.resetHeaders();
            wrapped.addHeader("Cache-Control", HeaderConstants.CACHE_CONTROL_NO_CACHE);
            wrapped.addHeader("Pragma", HeaderConstants.CACHE_CONTROL_NO_CACHE);
            wrapped.removeHeaders("If-Range");
            wrapped.removeHeaders("If-Match");
            wrapped.removeHeaders("If-None-Match");
            wrapped.removeHeaders("If-Unmodified-Since");
            wrapped.removeHeaders("If-Modified-Since");
            return wrapped;
        } catch (ProtocolException e) {
            log.warn("unable to build proper unconditional request", e);
            return request;
        }
    }
}
