package org.apache.http.impl.client.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.HttpVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.HeaderConstants;

@Immutable
class CacheableRequestPolicy {
    private final Log log = LogFactory.getLog(getClass());

    CacheableRequestPolicy() {
    }

    public boolean isServableFromCache(HttpRequest request) {
        String method = request.getRequestLine().getMethod();
        if (HttpVersion.HTTP_1_1.compareToVersion(request.getRequestLine().getProtocolVersion()) != 0) {
            this.log.debug("Request was not serveable from cache");
            return false;
        } else if (!method.equals("GET")) {
            this.log.debug("Request was not serveable from cache");
            return false;
        } else if (request.getHeaders("Pragma").length > 0) {
            this.log.debug("Request was not serveable from cache");
            return false;
        } else {
            for (Header cacheControl : request.getHeaders("Cache-Control")) {
                HeaderElement[] arr$ = cacheControl.getElements();
                int len$ = arr$.length;
                int i$ = 0;
                while (i$ < len$) {
                    HeaderElement cacheControlElement = arr$[i$];
                    if (HeaderConstants.CACHE_CONTROL_NO_STORE.equalsIgnoreCase(cacheControlElement.getName())) {
                        this.log.debug("Request was not serveable from Cache");
                        return false;
                    } else if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equalsIgnoreCase(cacheControlElement.getName())) {
                        this.log.debug("Request was not serveable from cache");
                        return false;
                    } else {
                        i$++;
                    }
                }
            }
            this.log.debug("Request was serveable from cache");
            return true;
        }
    }
}
