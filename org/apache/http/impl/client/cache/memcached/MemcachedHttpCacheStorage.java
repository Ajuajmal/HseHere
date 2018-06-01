package org.apache.http.impl.client.cache.memcached;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedClientIF;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheEntrySerializer;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.HttpCacheUpdateCallback;
import org.apache.http.client.cache.HttpCacheUpdateException;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.DefaultHttpCacheEntrySerializer;

public class MemcachedHttpCacheStorage implements HttpCacheStorage {
    private final MemcachedClientIF client;
    private final int maxUpdateRetries;
    private final HttpCacheEntrySerializer serializer;

    public MemcachedHttpCacheStorage(InetSocketAddress address) throws IOException {
        this(new MemcachedClient(new InetSocketAddress[]{address}));
    }

    public MemcachedHttpCacheStorage(MemcachedClientIF cache) {
        this(cache, new CacheConfig(), new DefaultHttpCacheEntrySerializer());
    }

    public MemcachedHttpCacheStorage(MemcachedClientIF client, CacheConfig config, HttpCacheEntrySerializer serializer) {
        this.client = client;
        this.maxUpdateRetries = config.getMaxUpdateRetries();
        this.serializer = serializer;
    }

    public void putEntry(String url, HttpCacheEntry entry) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        this.serializer.writeTo(entry, bos);
        this.client.set(url, 0, bos.toByteArray());
    }

    public HttpCacheEntry getEntry(String url) throws IOException {
        byte[] data = (byte[]) this.client.get(url);
        if (data == null) {
            return null;
        }
        return this.serializer.readFrom(new ByteArrayInputStream(data));
    }

    public void removeEntry(String url) throws IOException {
        this.client.delete(url);
    }

    public void updateEntry(String url, HttpCacheUpdateCallback callback) throws HttpCacheUpdateException, IOException {
        int numRetries = 0;
        do {
            CASValue<Object> v = this.client.gets(url);
            byte[] oldBytes = v != null ? (byte[]) v.getValue() : null;
            HttpCacheEntry existingEntry = null;
            if (oldBytes != null) {
                existingEntry = this.serializer.readFrom(new ByteArrayInputStream(oldBytes));
            }
            HttpCacheEntry updatedEntry = callback.update(existingEntry);
            if (v == null) {
                putEntry(url, updatedEntry);
                return;
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            this.serializer.writeTo(updatedEntry, bos);
            if (this.client.cas(url, v.getCas(), bos.toByteArray()) != CASResponse.OK) {
                numRetries++;
            } else {
                return;
            }
        } while (numRetries <= this.maxUpdateRetries);
        throw new HttpCacheUpdateException("Failed to update");
    }
}
