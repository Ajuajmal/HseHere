package org.apache.http.impl.client.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolVersion;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

@Immutable
class ResponseProtocolCompliance {
    ResponseProtocolCompliance() {
    }

    public void ensureProtocolCompliance(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (backendResponseMustNotHaveBody(request, response)) {
            response.setEntity(null);
        }
        authenticationRequiredDidNotHaveAProxyAuthenticationHeader(request, response);
        notAllowedResponseDidNotHaveAnAllowHeader(request, response);
        unauthorizedResponseDidNotHaveAWWWAuthenticateHeader(request, response);
        requestDidNotExpect100ContinueButResponseIsOne(request, response);
        transferEncodingIsNotReturnedTo1_0Client(request, response);
        ensurePartialContentIsNotSentToAClientThatDidNotRequestIt(request, response);
        ensure200ForOPTIONSRequestWithNoBodyHasContentLengthZero(request, response);
        ensure206ContainsDateHeader(response);
        ensure304DoesNotContainExtraEntityHeaders(response);
        identityIsNotUsedInContentEncoding(response);
        warningsWithNonMatchingWarnDatesAreRemoved(response);
    }

    private void warningsWithNonMatchingWarnDatesAreRemoved(HttpResponse response) {
        Date responseDate = null;
        try {
            responseDate = DateUtils.parseDate(response.getFirstHeader("Date").getValue());
        } catch (DateParseException e) {
        }
        if (responseDate != null) {
            Header[] warningHeaders = response.getHeaders("Warning");
            if (warningHeaders != null && warningHeaders.length != 0) {
                List<Header> newWarningHeaders = new ArrayList();
                boolean modified = false;
                for (Header h : warningHeaders) {
                    for (WarningValue wv : WarningValue.getWarningValues(h)) {
                        Date warnDate = wv.getWarnDate();
                        if (warnDate == null || warnDate.equals(responseDate)) {
                            newWarningHeaders.add(new BasicHeader("Warning", wv.toString()));
                        } else {
                            modified = true;
                        }
                    }
                }
                if (modified) {
                    response.removeHeaders("Warning");
                    for (Header h2 : newWarningHeaders) {
                        response.addHeader(h2);
                    }
                }
            }
        }
    }

    private void identityIsNotUsedInContentEncoding(HttpResponse response) {
        Header[] hdrs = response.getHeaders("Content-Encoding");
        if (hdrs != null && hdrs.length != 0) {
            List<Header> newHeaders = new ArrayList();
            boolean modified = false;
            for (Header h : hdrs) {
                StringBuilder buf = new StringBuilder();
                boolean first = true;
                for (HeaderElement elt : h.getElements()) {
                    if (HTTP.IDENTITY_CODING.equalsIgnoreCase(elt.getName())) {
                        modified = true;
                    } else {
                        if (!first) {
                            buf.append(",");
                        }
                        buf.append(elt.toString());
                        first = false;
                    }
                }
                String newHeaderValue = buf.toString();
                if (!"".equals(newHeaderValue)) {
                    newHeaders.add(new BasicHeader("Content-Encoding", newHeaderValue));
                }
            }
            if (modified) {
                response.removeHeaders("Content-Encoding");
                for (Header h2 : newHeaders) {
                    response.addHeader(h2);
                }
            }
        }
    }

    private void authenticationRequiredDidNotHaveAProxyAuthenticationHeader(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED && response.getFirstHeader("Proxy-Authenticate") == null) {
            throw new ClientProtocolException("407 Response did not contain a Proxy-Authentication header");
        }
    }

    private void notAllowedResponseDidNotHaveAnAllowHeader(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (response.getStatusLine().getStatusCode() == 405 && response.getFirstHeader("Allow") == null) {
            throw new ClientProtocolException("405 Response did not contain an Allow header.");
        }
    }

    private void unauthorizedResponseDidNotHaveAWWWAuthenticateHeader(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED && response.getFirstHeader("WWW-Authenticate") == null) {
            throw new ClientProtocolException("401 Response did not contain required WWW-Authenticate challenge header");
        }
    }

    private void ensure206ContainsDateHeader(HttpResponse response) {
        if (response.getFirstHeader("Date") == null) {
            response.addHeader("Date", DateUtils.formatDate(new Date()));
        }
    }

    private void ensurePartialContentIsNotSentToAClientThatDidNotRequestIt(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (request.getFirstHeader("Range") == null && response.getFirstHeader("Content-Range") != null) {
            throw new ClientProtocolException("Content-Range was returned for a request that did not ask for a Content-Range.");
        }
    }

    private void ensure200ForOPTIONSRequestWithNoBodyHasContentLengthZero(HttpRequest request, HttpResponse response) {
        if (request.getRequestLine().getMethod().equalsIgnoreCase("OPTIONS") && response.getStatusLine().getStatusCode() == 200 && response.getFirstHeader("Content-Length") == null) {
            response.addHeader("Content-Length", "0");
        }
    }

    private void ensure304DoesNotContainExtraEntityHeaders(HttpResponse response) {
        String[] disallowedEntityHeaders = new String[]{"Allow", "Content-Encoding", HttpHeaders.CONTENT_LANGUAGE, "Content-Length", HttpHeaders.CONTENT_MD5, "Content-Range", "Content-Type", "Last-Modified"};
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_MODIFIED) {
            for (String hdr : disallowedEntityHeaders) {
                response.removeHeaders(hdr);
            }
        }
    }

    private boolean backendResponseMustNotHaveBody(HttpRequest request, HttpResponse backendResponse) {
        return "HEAD".equals(request.getRequestLine().getMethod()) || backendResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT || backendResponse.getStatusLine().getStatusCode() == HttpStatus.SC_RESET_CONTENT || backendResponse.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_MODIFIED;
    }

    private void requestDidNotExpect100ContinueButResponseIsOne(HttpRequest request, HttpResponse response) throws ClientProtocolException {
        if (response.getStatusLine().getStatusCode() == 100 && requestWasWrapped(request) && getOriginalRequestProtocol((RequestWrapper) request).compareToVersion(HttpVersion.HTTP_1_1) < 0 && originalRequestDidNotExpectContinue((RequestWrapper) request)) {
            throw new ClientProtocolException("The incoming request did not contain a 100-continue header, but the response was a Status 100, continue.");
        }
    }

    private void transferEncodingIsNotReturnedTo1_0Client(HttpRequest request, HttpResponse response) {
        if (requestWasWrapped(request) && getOriginalRequestProtocol((RequestWrapper) request).compareToVersion(HttpVersion.HTTP_1_1) < 0) {
            removeResponseTransferEncoding(response);
        }
    }

    private void removeResponseTransferEncoding(HttpResponse response) {
        response.removeHeaders(HttpHeaders.TE);
        response.removeHeaders("Transfer-Encoding");
    }

    private boolean originalRequestDidNotExpectContinue(RequestWrapper request) {
        try {
            if (((HttpEntityEnclosingRequest) request.getOriginal()).expectContinue()) {
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private ProtocolVersion getOriginalRequestProtocol(RequestWrapper request) {
        return request.getOriginal().getProtocolVersion();
    }

    private boolean requestWasWrapped(HttpRequest request) {
        return request instanceof RequestWrapper;
    }
}
