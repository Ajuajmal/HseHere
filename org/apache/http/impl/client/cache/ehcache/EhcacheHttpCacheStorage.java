package org.apache.http.impl.client.cache.ehcache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheEntrySerializer;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.HttpCacheUpdateCallback;
import org.apache.http.client.cache.HttpCacheUpdateException;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.DefaultHttpCacheEntrySerializer;

public class EhcacheHttpCacheStorage implements HttpCacheStorage {
    private final Ehcache cache;
    private final int maxUpdateRetries;
    private final HttpCacheEntrySerializer serializer;

    public EhcacheHttpCacheStorage(Ehcache cache) {
        this(cache, new CacheConfig(), new DefaultHttpCacheEntrySerializer());
    }

    public EhcacheHttpCacheStorage(Ehcache cache, CacheConfig config) {
        this(cache, config, new DefaultHttpCacheEntrySerializer());
    }

    public EhcacheHttpCacheStorage(Ehcache cache, CacheConfig config, HttpCacheEntrySerializer serializer) {
        this.cache = cache;
        this.maxUpdateRetries = config.getMaxUpdateRetries();
        this.serializer = serializer;
    }

    public synchronized void putEntry(String key, HttpCacheEntry entry) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        this.serializer.writeTo(entry, bos);
        this.cache.put(new Element(key, bos.toByteArray()));
    }

    public synchronized HttpCacheEntry getEntry(String key) throws IOException {
        HttpCacheEntry httpCacheEntry;
        Element e = this.cache.get(key);
        if (e == null) {
            httpCacheEntry = null;
        } else {
            httpCacheEntry = this.serializer.readFrom(new ByteArrayInputStream((byte[]) e.getValue()));
        }
        return httpCacheEntry;
    }

    public synchronized void removeEntry(String key) {
        this.cache.remove(key);
    }

    public synchronized void updateEntry(String key, HttpCacheUpdateCallback callback) throws IOException, HttpCacheUpdateException {
        int numRetries = 0;
        do {
            Element oldElement = this.cache.get(key);
            HttpCacheEntry existingEntry = null;
            if (oldElement != null) {
                existingEntry = this.serializer.readFrom(new ByteArrayInputStream((byte[]) oldElement.getValue()));
            }
            HttpCacheEntry updatedEntry = callback.update(existingEntry);
            if (existingEntry == null) {
                putEntry(key, updatedEntry);
            } else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                this.serializer.writeTo(updatedEntry, bos);
                if (!this.cache.replace(oldElement, new Element(key, bos.toByteArray()))) {
                    numRetries++;
                }
            }
        } while (numRetries <= this.maxUpdateRetries);
        throw new HttpCacheUpdateException("Failed to update");
    }
}
