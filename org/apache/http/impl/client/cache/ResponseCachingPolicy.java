package org.apache.http.impl.client.cache;

import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

@Immutable
class ResponseCachingPolicy {
    private final Log log = LogFactory.getLog(getClass());
    private final int maxObjectSizeBytes;
    private final boolean sharedCache;

    public ResponseCachingPolicy(int maxObjectSizeBytes, boolean sharedCache) {
        this.maxObjectSizeBytes = maxObjectSizeBytes;
        this.sharedCache = sharedCache;
    }

    public boolean isResponseCacheable(String httpMethod, HttpResponse response) {
        if ("GET".equals(httpMethod)) {
            switch (response.getStatusLine().getStatusCode()) {
                case 200:
                case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION /*203*/:
                case 300:
                case HttpStatus.SC_MOVED_PERMANENTLY /*301*/:
                case 410:
                    this.log.debug("Response was cacheable");
                    Header contentLength = response.getFirstHeader("Content-Length");
                    if (contentLength != null && Integer.parseInt(contentLength.getValue()) > this.maxObjectSizeBytes) {
                        return false;
                    }
                    if (response.getHeaders("Age").length > 1) {
                        return false;
                    }
                    if (response.getHeaders("Expires").length > 1) {
                        return false;
                    }
                    Header[] dateHeaders = response.getHeaders("Date");
                    if (dateHeaders.length != 1) {
                        return false;
                    }
                    try {
                        DateUtils.parseDate(dateHeaders[0].getValue());
                        for (Header varyHdr : response.getHeaders("Vary")) {
                            for (HeaderElement elem : varyHdr.getElements()) {
                                if ("*".equals(elem.getName())) {
                                    return false;
                                }
                            }
                        }
                        if (isExplicitlyNonCacheable(response)) {
                            return false;
                        }
                        return true || isExplicitlyCacheable(response);
                    } catch (DateParseException e) {
                        return false;
                    }
                case HttpStatus.SC_PARTIAL_CONTENT /*206*/:
                    this.log.debug("Response was not cacheable (Partial Content)");
                    return false;
                default:
                    this.log.debug("Response was not cacheable (Unknown Status code)");
                    return false;
            }
        }
        this.log.debug("Response was not cacheable.");
        return false;
    }

    protected boolean isExplicitlyNonCacheable(HttpResponse response) {
        for (Header header : response.getHeaders("Cache-Control")) {
            for (HeaderElement elem : header.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_NO_STORE.equals(elem.getName()) || HeaderConstants.CACHE_CONTROL_NO_CACHE.equals(elem.getName()) || (this.sharedCache && "private".equals(elem.getName()))) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean hasCacheControlParameterFrom(HttpMessage msg, String[] params) {
        for (Header header : msg.getHeaders("Cache-Control")) {
            for (HeaderElement elem : header.getElements()) {
                for (String param : params) {
                    if (param.equalsIgnoreCase(elem.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected boolean isExplicitlyCacheable(HttpResponse response) {
        if (response.getFirstHeader("Expires") != null) {
            return true;
        }
        return hasCacheControlParameterFrom(response, new String[]{"max-age", "s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, HeaderConstants.CACHE_CONTROL_PROXY_REVALIDATE, "public"});
    }

    public boolean isResponseCacheable(HttpRequest request, HttpResponse response) {
        if (requestProtocolGreaterThanAccepted(request)) {
            this.log.debug("Response was not cacheable.");
            return false;
        }
        if (hasCacheControlParameterFrom(request, new String[]{HeaderConstants.CACHE_CONTROL_NO_STORE})) {
            return false;
        }
        if (request.getRequestLine().getUri().contains("?") && (!isExplicitlyCacheable(response) || from1_0Origin(response))) {
            this.log.debug("Response was not cacheable.");
            return false;
        } else if (expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(response)) {
            return false;
        } else {
            if (this.sharedCache) {
                Header[] authNHeaders = request.getHeaders("Authorization");
                if (authNHeaders != null && authNHeaders.length > 0) {
                    return hasCacheControlParameterFrom(response, new String[]{"s-maxage", HeaderConstants.CACHE_CONTROL_MUST_REVALIDATE, "public"});
                }
            }
            return isResponseCacheable(request.getRequestLine().getMethod(), response);
        }
    }

    private boolean expiresHeaderLessOrEqualToDateHeaderAndNoCacheControl(HttpResponse response) {
        if (response.getFirstHeader("Cache-Control") != null) {
            return false;
        }
        Header expiresHdr = response.getFirstHeader("Expires");
        Header dateHdr = response.getFirstHeader("Date");
        if (expiresHdr == null || dateHdr == null) {
            return false;
        }
        try {
            Date expires = DateUtils.parseDate(expiresHdr.getValue());
            Date date = DateUtils.parseDate(dateHdr.getValue());
            if (expires.equals(date) || expires.before(date)) {
                return true;
            }
            return false;
        } catch (DateParseException e) {
            return false;
        }
    }

    private boolean from1_0Origin(HttpResponse response) {
        Header via = response.getFirstHeader(HttpHeaders.VIA);
        if (via != null) {
            HeaderElement[] arr$ = via.getElements();
            if (0 < arr$.length) {
                String proto = arr$[0].toString().split("\\s")[0];
                if (proto.contains("/")) {
                    return proto.equals("HTTP/1.0");
                }
                return proto.equals("1.0");
            }
        }
        return HttpVersion.HTTP_1_0.equals(response.getProtocolVersion());
    }

    private boolean requestProtocolGreaterThanAccepted(HttpRequest req) {
        return req.getProtocolVersion().compareToVersion(HttpVersion.HTTP_1_1) > 0;
    }
}
