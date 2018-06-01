package org.apache.http.impl.client.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.http.annotation.Immutable;
import org.apache.http.client.cache.Resource;

@Immutable
public class HeapResource implements Resource {
    private static final long serialVersionUID = -2078599905620463394L;
    private final byte[] f69b;

    public HeapResource(byte[] b) {
        this.f69b = b;
    }

    byte[] getByteArray() {
        return this.f69b;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.f69b);
    }

    public long length() {
        return (long) this.f69b.length;
    }

    public void dispose() {
    }
}
