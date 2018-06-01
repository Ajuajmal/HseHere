package org.apache.http.impl.client.cache;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.cache.HttpCacheEntry;
import org.apache.http.client.cache.HttpCacheStorage;
import org.apache.http.client.cache.HttpCacheUpdateCallback;

@ThreadSafe
public class ManagedHttpCacheStorage implements HttpCacheStorage {
    private final CacheMap entries;
    private final ReferenceQueue<HttpCacheEntry> morque = new ReferenceQueue();
    private final Set<ResourceReference> resources = new HashSet();
    private volatile boolean shutdown;

    public ManagedHttpCacheStorage(CacheConfig config) {
        this.entries = new CacheMap(config.getMaxCacheEntries());
    }

    private void ensureValidState() throws IllegalStateException {
        if (this.shutdown) {
            throw new IllegalStateException("Cache has been shut down");
        }
    }

    private void keepResourceReference(HttpCacheEntry entry) {
        if (entry.getResource() != null) {
            this.resources.add(new ResourceReference(entry, this.morque));
        }
    }

    public void putEntry(String url, HttpCacheEntry entry) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL may not be null");
        } else if (entry == null) {
            throw new IllegalArgumentException("Cache entry may not be null");
        } else {
            ensureValidState();
            synchronized (this) {
                this.entries.put(url, entry);
                keepResourceReference(entry);
            }
        }
    }

    public HttpCacheEntry getEntry(String url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL may not be null");
        }
        HttpCacheEntry httpCacheEntry;
        ensureValidState();
        synchronized (this) {
            httpCacheEntry = (HttpCacheEntry) this.entries.get(url);
        }
        return httpCacheEntry;
    }

    public void removeEntry(String url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL may not be null");
        }
        ensureValidState();
        synchronized (this) {
            this.entries.remove(url);
        }
    }

    public void updateEntry(String url, HttpCacheUpdateCallback callback) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL may not be null");
        } else if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        } else {
            ensureValidState();
            synchronized (this) {
                HttpCacheEntry existing = (HttpCacheEntry) this.entries.get(url);
                HttpCacheEntry updated = callback.update(existing);
                this.entries.put(url, updated);
                if (existing != updated) {
                    keepResourceReference(updated);
                }
            }
        }
    }

    public void cleanResources() {
        if (!this.shutdown) {
            while (true) {
                ResourceReference ref = (ResourceReference) this.morque.poll();
                if (ref != null) {
                    synchronized (this) {
                        this.resources.remove(ref);
                    }
                    ref.getResource().dispose();
                } else {
                    return;
                }
            }
        }
    }

    public void shutdown() {
        if (!this.shutdown) {
            this.shutdown = true;
            synchronized (this) {
                this.entries.clear();
                for (ResourceReference ref : this.resources) {
                    ref.getResource().dispose();
                }
                this.resources.clear();
                do {
                } while (this.morque.poll() != null);
            }
        }
    }
}
