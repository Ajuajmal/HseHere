package com.appnext.base.operations.impl;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import com.appnext.base.operations.C0218c;
import com.appnext.base.operations.C0948a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0211i;
import com.appnext.base.p005b.C0211i.C0210c;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class geoci extends C0948a implements C0210c {
    private C0211i eB;
    private String eL;

    public geoci(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    public void bl() {
        synchronized (this) {
            this.eL = null;
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
        return this.eL;
    }

    public void mo4204a(final Location location) {
        this.eB.m86a(null);
        new Thread(new Runnable(this) {
            final /* synthetic */ geoci eM;

            public void run() {
                try {
                    List fromLocation = new Geocoder(C0205d.getContext(), Locale.getDefault()).getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (fromLocation.size() > 0) {
                        this.eM.eL = ((Address) fromLocation.get(0)).getLocality();
                    }
                } catch (IOException e) {
                }
                this.eM.bh();
            }
        }).start();
    }

    public void onError(String str) {
        this.eB.m86a(null);
        C0218c.bn().m116a(this);
    }
}
