package com.appnext.base.operations;

import android.location.Location;
import android.os.Bundle;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p001a.p003b.C0935e;
import com.appnext.base.p005b.C0211i;
import com.appnext.base.p005b.C0211i.C0210c;
import java.util.Date;

public abstract class C1207e extends C0948a implements C0210c {
    private C0211i eB = null;

    public abstract void mo5232d(C0935e c0935e);

    public C1207e(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    public void bl() {
        synchronized (this) {
            this.eB = new C0211i();
            this.eB.m86a((C0210c) this);
            this.eB.init();
        }
    }

    public void bm() {
        synchronized (this) {
            if (this.eB != null) {
                this.eB.m86a(null);
                this.eB.bH();
                this.eB = null;
            }
        }
    }

    protected String getData() {
        return null;
    }

    public void mo4204a(final Location location) {
        this.eB.m86a(null);
        new Thread(new Runnable(this) {
            final /* synthetic */ C1207e eD;

            public void run() {
                this.eD.mo5232d(new C0935e(Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), String.valueOf(location.getAccuracy()), new Date(location.getTime())));
                C0218c.bn().m116a(this.eD);
            }
        }).start();
    }

    public void onError(String str) {
        this.eB.m86a(null);
        C0218c.bn().m116a(this);
    }

    public Object mo1242Z(String str) {
        return null;
    }
}
