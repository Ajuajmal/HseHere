package com.appnext.base.operations;

import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p003b.C0934c;
import java.util.ArrayList;
import java.util.List;

public class C0218c {
    private static final String ey = "com.appnext.base.operations.impl";
    private static volatile C0218c ez;
    private List<C0948a> eA = new ArrayList();

    public static C0218c bn() {
        if (ez == null) {
            synchronized (C0218c.class) {
                if (ez == null) {
                    ez = new C0218c();
                }
            }
        }
        return ez;
    }

    private C0218c() {
    }

    public void m117a(String str, C0934c c0934c, Bundle bundle) {
        if (c0934c != null) {
            try {
                Object newInstance = Class.forName(aa(str)).getConstructor(new Class[]{C0934c.class, Bundle.class}).newInstance(new Object[]{c0934c, bundle});
                if (newInstance instanceof C0948a) {
                    C0948a c0948a = (C0948a) newInstance;
                    synchronized (this) {
                        this.eA.add(c0948a);
                    }
                    c0948a.bl();
                }
            } catch (ClassNotFoundException e) {
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
    }

    public void m116a(C0948a c0948a) {
        if (c0948a != null) {
            c0948a.bm();
            synchronized (this) {
                this.eA.remove(c0948a);
            }
        }
    }

    public void bo() {
        synchronized (this) {
            for (C0948a bm : this.eA) {
                bm.bm();
            }
            this.eA.clear();
        }
    }

    private String aa(String str) {
        return "com.appnext.base.operations.impl." + str;
    }
}
