package org.apache.http.impl.client.cache;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.RequestLine;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HeaderConstants;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.ResourceFactory;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.VersionInfo;

@ThreadSafe
public class CachingHttpClient implements HttpClient {
    public static final String CACHE_RESPONSE_STATUS = "http.cache.response.status";
    private static final boolean SUPPORTS_RANGE_AND_CONTENT_RANGE_HEADERS = false;
    private final AsynchronousValidator asynchRevalidator;
    private final HttpClient backend;
    private final AtomicLong cacheHits;
    private final AtomicLong cacheMisses;
    private final AtomicLong cacheUpdates;
    private final CacheableRequestPolicy cacheableRequestPolicy;
    private final ConditionalRequestBuilder conditionalRequestBuilder;
    private final Log log;
    private final int maxObjectSizeBytes;
    private final RequestProtocolCompliance requestCompliance;
    private final HttpCache responseCache;
    private final ResponseCachingPolicy responseCachingPolicy;
    private final ResponseProtocolCompliance responseCompliance;
    private final CachedHttpResponseGenerator responseGenerator;
    private final boolean sharedCache;
    private final CachedResponseSuitabilityChecker suitabilityChecker;
    private final CacheValidityPolicy validityPolicy;

    CachingHttpClient(HttpClient client, HttpCache cache, CacheConfig config) {
        this.cacheHits = new AtomicLong();
        this.cacheMisses = new AtomicLong();
        this.cacheUpdates = new AtomicLong();
        this.log = LogFactory.getLog(getClass());
        if (client == null) {
            throw new IllegalArgumentException("HttpClient may not be null");
        } else if (cache == null) {
            throw new IllegalArgumentException("HttpCache may not be null");
        } else if (config == null) {
            throw new IllegalArgumentException("CacheConfig may not be null");
        } else {
            this.maxObjectSizeBytes = config.getMaxObjectSizeBytes();
            this.sharedCache = config.isSharedCache();
            this.backend = client;
            this.responseCache = cache;
            this.validityPolicy = new CacheValidityPolicy();
            this.responseCachingPolicy = new ResponseCachingPolicy(this.maxObjectSizeBytes, this.sharedCache);
            this.responseGenerator = new CachedHttpResponseGenerator(this.validityPolicy);
            this.cacheableRequestPolicy = new CacheableRequestPolicy();
            this.suitabilityChecker = new CachedResponseSuitabilityChecker(this.validityPolicy, config);
            this.conditionalRequestBuilder = new ConditionalRequestBuilder();
            this.responseCompliance = new ResponseProtocolCompliance();
            this.requestCompliance = new RequestProtocolCompliance();
            this.asynchRevalidator = makeAsynchronousValidator(config);
        }
    }

    public CachingHttpClient() {
        this(new DefaultHttpClient(), new BasicHttpCache(), new CacheConfig());
    }

    public CachingHttpClient(CacheConfig config) {
        this(new DefaultHttpClient(), new BasicHttpCache(config), config);
    }

    public CachingHttpClient(HttpClient client) {
        this(client, new BasicHttpCache(), new CacheConfig());
    }

    public CachingHttpClient(HttpClient client, CacheConfig config) {
        this(client, new BasicHttpCache(config), config);
    }

    public CachingHttpClient(HttpClient client, ResourceFactory resourceFactory, HttpCacheStorage storage, CacheConfig config) {
        this(client, new BasicHttpCache(resourceFactory, storage, config), config);
    }

    public CachingHttpClient(HttpClient client, HttpCacheStorage storage, CacheConfig config) {
        this(client, new BasicHttpCache(new HeapResourceFactory(), storage, config), config);
    }

    CachingHttpClient(HttpClient backend, CacheValidityPolicy validityPolicy, ResponseCachingPolicy responseCachingPolicy, HttpCache responseCache, CachedHttpResponseGenerator responseGenerator, CacheableRequestPolicy cacheableRequestPolicy, CachedResponseSuitabilityChecker suitabilityChecker, ConditionalRequestBuilder conditionalRequestBuilder, ResponseProtocolCompliance responseCompliance, RequestProtocolCompliance requestCompliance) {
        this.cacheHits = new AtomicLong();
        this.cacheMisses = new AtomicLong();
        this.cacheUpdates = new AtomicLong();
        this.log = LogFactory.getLog(getClass());
        CacheConfig config = new CacheConfig();
        this.maxObjectSizeBytes = config.getMaxObjectSizeBytes();
        this.sharedCache = config.isSharedCache();
        this.backend = backend;
        this.validityPolicy = validityPolicy;
        this.responseCachingPolicy = responseCachingPolicy;
        this.responseCache = responseCache;
        this.responseGenerator = responseGenerator;
        this.cacheableRequestPolicy = cacheableRequestPolicy;
        this.suitabilityChecker = suitabilityChecker;
        this.conditionalRequestBuilder = conditionalRequestBuilder;
        this.responseCompliance = responseCompliance;
        this.requestCompliance = requestCompliance;
        this.asynchRevalidator = makeAsynchronousValidator(config);
    }

    private AsynchronousValidator makeAsynchronousValidator(CacheConfig config) {
        if (config.getAsynchronousWorkersMax() > 0) {
            return new AsynchronousValidator(this, config);
        }
        return null;
    }

    public long getCacheHits() {
        return this.cacheHits.get();
    }

    public long getCacheMisses() {
        return this.cacheMisses.get();
    }

    public long getCacheUpdates() {
        return this.cacheUpdates.get();
    }

    public HttpResponse execute(HttpHost target, HttpRequest request) throws IOException {
        return execute(target, request, null);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return execute(target, request, responseHandler, null);
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
        return responseHandler.handleResponse(execute(target, request, context));
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return execute(request, null);
    }

    public HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException {
        URI uri = request.getURI();
        return execute(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()), (HttpRequest) request, context);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException {
        return execute(request, (ResponseHandler) responseHandler, null);
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException {
        return responseHandler.handleResponse(execute(request, context));
    }

    public ClientConnectionManager getConnectionManager() {
        return this.backend.getConnectionManager();
    }

    public HttpParams getParams() {
        return this.backend.getParams();
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        setResponseStatus(context, CacheResponseStatus.CACHE_MISS);
        String via = generateViaHeader(request);
        if (clientRequestsOurOptions(request)) {
            setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
            return new OptionsHttp11Response();
        }
        HttpResponse fatalErrorResponse = getFatallyNoncompliantResponse(request, context);
        if (fatalErrorResponse != null) {
            return fatalErrorResponse;
        }
        request = this.requestCompliance.makeRequestCompliant(request);
        request.addHeader(HttpHeaders.VIA, via);
        flushEntriesInvalidatedByRequest(target, request);
        if (!this.cacheableRequestPolicy.isServableFromCache(request)) {
            return callBackend(target, request, context);
        }
        HttpCacheEntry entry = satisfyFromCache(target, request);
        if (entry == null) {
            return handleCacheMiss(target, request, context);
        }
        return handleCacheHit(target, request, context, entry);
    }

    private HttpResponse handleCacheHit(HttpHost target, HttpRequest request, HttpContext context, HttpCacheEntry entry) throws ClientProtocolException, IOException {
        recordCacheHit(target, request);
        Date now = getCurrentDate();
        if (this.suitabilityChecker.canCachedResponseBeUsed(target, request, entry, now)) {
            return generateCachedResponse(request, context, entry, now);
        }
        if (!mayCallBackend(request)) {
            return generateGatewayTimeout(context);
        }
        if (this.validityPolicy.isRevalidatable(entry)) {
            return revalidateCacheEntry(target, request, context, entry, now);
        }
        return callBackend(target, request, context);
    }

    private HttpResponse revalidateCacheEntry(HttpHost target, HttpRequest request, HttpContext context, HttpCacheEntry entry, Date now) throws ClientProtocolException {
        this.log.debug("Revalidating the cache entry");
        try {
            if (this.asynchRevalidator == null || staleResponseNotAllowed(request, entry, now) || !this.validityPolicy.mayReturnStaleWhileRevalidating(entry, now)) {
                return revalidateCacheEntry(target, request, context, entry);
            }
            HttpResponse resp = this.responseGenerator.generateResponse(entry);
            resp.addHeader("Warning", "110 localhost \"Response is stale\"");
            this.asynchRevalidator.revalidateCacheEntry(target, request, context, entry);
            return resp;
        } catch (IOException e) {
            return handleRevalidationFailure(request, context, entry, now);
        } catch (Throwable e2) {
            throw new ClientProtocolException(e2);
        }
    }

    private HttpResponse handleCacheMiss(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        recordCacheMiss(target, request);
        if (!mayCallBackend(request)) {
            return new BasicHttpResponse(HttpVersion.HTTP_1_1, (int) HttpStatus.SC_GATEWAY_TIMEOUT, "Gateway Timeout");
        }
        Map<String, Variant> variants = getExistingCacheVariants(target, request);
        if (variants == null || variants.size() <= 0) {
            return callBackend(target, request, context);
        }
        return negotiateResponseFromVariants(target, request, context, variants);
    }

    private HttpCacheEntry satisfyFromCache(HttpHost target, HttpRequest request) {
        HttpCacheEntry entry = null;
        try {
            entry = this.responseCache.getCacheEntry(target, request);
        } catch (IOException ioe) {
            this.log.warn("Unable to retrieve entries from cache", ioe);
        }
        return entry;
    }

    private HttpResponse getFatallyNoncompliantResponse(HttpRequest request, HttpContext context) {
        HttpResponse fatalErrorResponse = null;
        for (RequestProtocolError error : this.requestCompliance.requestIsFatallyNonCompliant(request)) {
            setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
            fatalErrorResponse = this.requestCompliance.getErrorForRequest(error);
        }
        return fatalErrorResponse;
    }

    private Map<String, Variant> getExistingCacheVariants(HttpHost target, HttpRequest request) {
        Map<String, Variant> variants = null;
        try {
            variants = this.responseCache.getVariantCacheEntriesWithEtags(target, request);
        } catch (IOException ioe) {
            this.log.warn("Unable to retrieve variant entries from cache", ioe);
        }
        return variants;
    }

    private void recordCacheMiss(HttpHost target, HttpRequest request) {
        this.cacheMisses.getAndIncrement();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Cache miss [host: " + target + "; uri: " + request.getRequestLine().getUri() + "]");
        }
    }

    private void recordCacheHit(HttpHost target, HttpRequest request) {
        this.cacheHits.getAndIncrement();
        if (this.log.isDebugEnabled()) {
            this.log.debug("Cache hit [host: " + target + "; uri: " + request.getRequestLine().getUri() + "]");
        }
    }

    private void recordCacheUpdate(HttpContext context) {
        this.cacheUpdates.getAndIncrement();
        setResponseStatus(context, CacheResponseStatus.VALIDATED);
    }

    private void flushEntriesInvalidatedByRequest(HttpHost target, HttpRequest request) {
        try {
            this.responseCache.flushInvalidatedCacheEntriesFor(target, request);
        } catch (IOException ioe) {
            this.log.warn("Unable to flush invalidated entries from cache", ioe);
        }
    }

    private HttpResponse generateCachedResponse(HttpRequest request, HttpContext context, HttpCacheEntry entry, Date now) {
        HttpResponse cachedResponse;
        if (request.containsHeader("If-None-Match") || request.containsHeader("If-Modified-Since")) {
            cachedResponse = this.responseGenerator.generateNotModifiedResponse(entry);
        } else {
            cachedResponse = this.responseGenerator.generateResponse(entry);
        }
        setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
        if (this.validityPolicy.getStalenessSecs(entry, now) > 0) {
            cachedResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
        }
        return cachedResponse;
    }

    private HttpResponse handleRevalidationFailure(HttpRequest request, HttpContext context, HttpCacheEntry entry, Date now) {
        if (staleResponseNotAllowed(request, entry, now)) {
            return generateGatewayTimeout(context);
        }
        return unvalidatedCacheHit(context, entry);
    }

    private HttpResponse generateGatewayTimeout(HttpContext context) {
        setResponseStatus(context, CacheResponseStatus.CACHE_MODULE_RESPONSE);
        return new BasicHttpResponse(HttpVersion.HTTP_1_1, (int) HttpStatus.SC_GATEWAY_TIMEOUT, "Gateway Timeout");
    }

    private HttpResponse unvalidatedCacheHit(HttpContext context, HttpCacheEntry entry) {
        HttpResponse cachedResponse = this.responseGenerator.generateResponse(entry);
        setResponseStatus(context, CacheResponseStatus.CACHE_HIT);
        cachedResponse.addHeader("Warning", "111 localhost \"Revalidation failed\"");
        return cachedResponse;
    }

    private boolean staleResponseNotAllowed(HttpRequest request, HttpCacheEntry entry, Date now) {
        return this.validityPolicy.mustRevalidate(entry) || ((isSharedCache() && this.validityPolicy.proxyRevalidate(entry)) || explicitFreshnessRequest(request, entry, now));
    }

    private boolean mayCallBackend(HttpRequest request) {
        for (Header h : request.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if ("only-if-cached".equals(elt.getName())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean explicitFreshnessRequest(HttpRequest request, HttpCacheEntry entry, Date now) {
        for (Header h : request.getHeaders("Cache-Control")) {
            for (HeaderElement elt : h.getElements()) {
                if (HeaderConstants.CACHE_CONTROL_MAX_STALE.equals(elt.getName())) {
                    try {
                        if (this.validityPolicy.getCurrentAgeSecs(entry, now) - this.validityPolicy.getFreshnessLifetimeSecs(entry) > ((long) Integer.parseInt(elt.getValue()))) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        return true;
                    }
                } else if (HeaderConstants.CACHE_CONTROL_MIN_FRESH.equals(elt.getName()) || "max-age".equals(elt.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String generateViaHeader(HttpMessage msg) {
        VersionInfo vi = VersionInfo.loadVersionInfo("org.apache.http.client", getClass().getClassLoader());
        String release = vi != null ? vi.getRelease() : VersionInfo.UNAVAILABLE;
        if (HttpHost.DEFAULT_SCHEME_NAME.equalsIgnoreCase(msg.getProtocolVersion().getProtocol())) {
            return String.format("%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[]{Integer.valueOf(msg.getProtocolVersion().getMajor()), Integer.valueOf(msg.getProtocolVersion().getMinor()), release});
        }
        return String.format("%s/%d.%d localhost (Apache-HttpClient/%s (cache))", new Object[]{msg.getProtocolVersion().getProtocol(), Integer.valueOf(msg.getProtocolVersion().getMajor()), Integer.valueOf(msg.getProtocolVersion().getMinor()), release});
    }

    private void setResponseStatus(HttpContext context, CacheResponseStatus value) {
        if (context != null) {
            context.setAttribute(CACHE_RESPONSE_STATUS, value);
        }
    }

    public boolean supportsRangeAndContentRangeHeaders() {
        return false;
    }

    public boolean isSharedCache() {
        return this.sharedCache;
    }

    Date getCurrentDate() {
        return new Date();
    }

    boolean clientRequestsOurOptions(HttpRequest request) {
        RequestLine line = request.getRequestLine();
        if ("OPTIONS".equals(line.getMethod()) && "*".equals(line.getUri()) && "0".equals(request.getFirstHeader("Max-Forwards").getValue())) {
            return true;
        }
        return false;
    }

    HttpResponse callBackend(HttpHost target, HttpRequest request, HttpContext context) throws IOException {
        Date requestDate = getCurrentDate();
        this.log.debug("Calling the backend");
        HttpResponse backendResponse = this.backend.execute(target, request, context);
        backendResponse.addHeader(HttpHeaders.VIA, generateViaHeader(backendResponse));
        return handleBackendResponse(target, request, requestDate, getCurrentDate(), backendResponse);
    }

    private boolean revalidationResponseIsTooOld(HttpResponse backendResponse, HttpCacheEntry cacheEntry) {
        Header entryDateHeader = cacheEntry.getFirstHeader("Date");
        Header responseDateHeader = backendResponse.getFirstHeader("Date");
        if (!(entryDateHeader == null || responseDateHeader == null)) {
            try {
                if (DateUtils.parseDate(responseDateHeader.getValue()).before(DateUtils.parseDate(entryDateHeader.getValue()))) {
                    return true;
                }
            } catch (DateParseException e) {
            }
        }
        return false;
    }

    HttpResponse negotiateResponseFromVariants(HttpHost target, HttpRequest request, HttpContext context, Map<String, Variant> variants) throws IOException {
        HttpRequest conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequestFromVariants(request, variants);
        Date requestDate = getCurrentDate();
        HttpResponse backendResponse = this.backend.execute(target, conditionalRequest, context);
        Date responseDate = getCurrentDate();
        backendResponse.addHeader(HttpHeaders.VIA, generateViaHeader(backendResponse));
        if (backendResponse.getStatusLine().getStatusCode() != HttpStatus.SC_NOT_MODIFIED) {
            return handleBackendResponse(target, request, requestDate, responseDate, backendResponse);
        }
        Header resultEtagHeader = backendResponse.getFirstHeader("ETag");
        if (resultEtagHeader == null) {
            this.log.warn("304 response did not contain ETag");
            return callBackend(target, request, context);
        }
        Variant matchingVariant = (Variant) variants.get(resultEtagHeader.getValue());
        if (matchingVariant == null) {
            this.log.debug("304 response did not contain ETag matching one sent in If-None-Match");
            return callBackend(target, request, context);
        }
        HttpCacheEntry matchedEntry = matchingVariant.getEntry();
        if (revalidationResponseIsTooOld(backendResponse, matchedEntry)) {
            return retryRequestUnconditionally(target, request, context, matchedEntry);
        }
        recordCacheUpdate(context);
        HttpCacheEntry responseEntry = getUpdatedVariantEntry(target, conditionalRequest, requestDate, responseDate, backendResponse, matchingVariant, matchedEntry);
        HttpResponse resp = this.responseGenerator.generateResponse(responseEntry);
        tryToUpdateVariantMap(target, request, matchingVariant);
        if (shouldSendNotModifiedResponse(request, responseEntry)) {
            return this.responseGenerator.generateNotModifiedResponse(responseEntry);
        }
        return resp;
    }

    private HttpResponse retryRequestUnconditionally(HttpHost target, HttpRequest request, HttpContext context, HttpCacheEntry matchedEntry) throws IOException {
        return callBackend(target, this.conditionalRequestBuilder.buildUnconditionalRequest(request, matchedEntry), context);
    }

    private HttpCacheEntry getUpdatedVariantEntry(HttpHost target, HttpRequest conditionalRequest, Date requestDate, Date responseDate, HttpResponse backendResponse, Variant matchingVariant, HttpCacheEntry matchedEntry) {
        HttpCacheEntry responseEntry = matchedEntry;
        try {
            responseEntry = this.responseCache.updateVariantCacheEntry(target, conditionalRequest, matchedEntry, backendResponse, requestDate, responseDate, matchingVariant.getCacheKey());
        } catch (IOException ioe) {
            this.log.warn("Could not update cache entry", ioe);
        }
        return responseEntry;
    }

    private void tryToUpdateVariantMap(HttpHost target, HttpRequest request, Variant matchingVariant) {
        try {
            this.responseCache.reuseVariantEntryFor(target, request, matchingVariant);
        } catch (IOException ioe) {
            this.log.warn("Could not update cache entry to reuse variant", ioe);
        }
    }

    private boolean shouldSendNotModifiedResponse(HttpRequest request, HttpCacheEntry responseEntry) {
        return this.suitabilityChecker.isConditional(request) && this.suitabilityChecker.allConditionalsMatch(request, responseEntry, new Date());
    }

    HttpResponse revalidateCacheEntry(HttpHost target, HttpRequest request, HttpContext context, HttpCacheEntry cacheEntry) throws IOException, ProtocolException {
        HttpRequest conditionalRequest = this.conditionalRequestBuilder.buildConditionalRequest(request, cacheEntry);
        Date requestDate = getCurrentDate();
        HttpResponse backendResponse = this.backend.execute(target, conditionalRequest, context);
        Date responseDate = getCurrentDate();
        if (revalidationResponseIsTooOld(backendResponse, cacheEntry)) {
            HttpRequest unconditional = this.conditionalRequestBuilder.buildUnconditionalRequest(request, cacheEntry);
            requestDate = getCurrentDate();
            backendResponse = this.backend.execute(target, unconditional, context);
            responseDate = getCurrentDate();
        }
        backendResponse.addHeader(HttpHeaders.VIA, generateViaHeader(backendResponse));
        int statusCode = backendResponse.getStatusLine().getStatusCode();
        if (statusCode == 304 || statusCode == 200) {
            recordCacheUpdate(context);
        }
        if (statusCode == 304) {
            HttpCacheEntry updatedEntry = this.responseCache.updateCacheEntry(target, request, cacheEntry, backendResponse, requestDate, responseDate);
            if (this.suitabilityChecker.isConditional(request)) {
                if (this.suitabilityChecker.allConditionalsMatch(request, updatedEntry, new Date())) {
                    return this.responseGenerator.generateNotModifiedResponse(updatedEntry);
                }
            }
            return this.responseGenerator.generateResponse(updatedEntry);
        }
        if (staleIfErrorAppliesTo(statusCode)) {
            if (!staleResponseNotAllowed(request, cacheEntry, getCurrentDate()) && this.validityPolicy.mayReturnStaleIfError(request, cacheEntry, responseDate)) {
                HttpResponse cachedResponse = this.responseGenerator.generateResponse(cacheEntry);
                cachedResponse.addHeader("Warning", "110 localhost \"Response is stale\"");
                return cachedResponse;
            }
        }
        return handleBackendResponse(target, conditionalRequest, requestDate, responseDate, backendResponse);
    }

    private boolean staleIfErrorAppliesTo(int statusCode) {
        return statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR || statusCode == HttpStatus.SC_BAD_GATEWAY || statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE || statusCode == HttpStatus.SC_GATEWAY_TIMEOUT;
    }

    HttpResponse handleBackendResponse(HttpHost target, HttpRequest request, Date requestDate, Date responseDate, HttpResponse backendResponse) throws IOException {
        this.log.debug("Handling Backend response");
        this.responseCompliance.ensureProtocolCompliance(request, backendResponse);
        boolean cacheable = this.responseCachingPolicy.isResponseCacheable(request, backendResponse);
        this.responseCache.flushInvalidatedCacheEntriesFor(target, request, backendResponse);
        if (cacheable && !alreadyHaveNewerCacheEntry(target, request, backendResponse)) {
            try {
                backendResponse = this.responseCache.cacheAndReturnResponse(target, request, backendResponse, requestDate, responseDate);
            } catch (IOException ioe) {
                this.log.warn("Unable to store entries in cache", ioe);
            }
            return backendResponse;
        }
        if (!cacheable) {
            try {
                this.responseCache.flushCacheEntriesFor(target, request);
            } catch (IOException ioe2) {
                this.log.warn("Unable to flush invalid cache entries", ioe2);
            }
        }
        return backendResponse;
    }

    private boolean alreadyHaveNewerCacheEntry(HttpHost target, HttpRequest request, HttpResponse backendResponse) {
        boolean z = false;
        HttpCacheEntry existing = null;
        try {
            existing = this.responseCache.getCacheEntry(target, request);
        } catch (IOException e) {
        }
        if (existing != null) {
            Header entryDateHeader = existing.getFirstHeader("Date");
            if (entryDateHeader != null) {
                Header responseDateHeader = backendResponse.getFirstHeader("Date");
                if (responseDateHeader != null) {
                    try {
                        z = DateUtils.parseDate(responseDateHeader.getValue()).before(DateUtils.parseDate(entryDateHeader.getValue()));
                    } catch (DateParseException e2) {
                    }
                }
            }
        }
        return z;
    }
}
