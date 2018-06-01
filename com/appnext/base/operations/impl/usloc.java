package com.appnext.base.operations.impl;

import android.location.Location;
import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p001a.p003b.C0935e;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0211i;
import com.appnext.base.p005b.C0212j;
import java.util.Date;
import java.util.List;

public class usloc extends C1206d {
    private static final int UNKNOWN = 0;
    private static final int eV = 1;
    private static final int eW = 2;
    private static final int eX = 3;
    private static final int eY = 4;
    private static final int eZ = 100;
    private long eU = 0;

    public usloc(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        int i = C0209h.bG().getInt(C0209h.gk, 0);
        Location bI = C0211i.bI();
        if (bI == null) {
            return null;
        }
        this.eU = bI.getTime();
        if (i == 2) {
            if (m508a(bI, bp())) {
                return null;
            }
            return m509e(1);
        } else if (i == 3) {
            if (m508a(bI, bq())) {
                return null;
            }
            return m509e(4);
        } else if (i != 1 && i != 4 && i != 0) {
            return null;
        } else {
            if (m508a(bI, bp())) {
                return m509e(2);
            }
            if (m508a(bI, bq())) {
                return m509e(3);
            }
            return null;
        }
    }

    private List<C0935e> bp() {
        return C0192a.aE().aI().bg();
    }

    private List<C0935e> bq() {
        return C0192a.aE().aI().bf();
    }

    private String m509e(int i) {
        C0209h.bG().putInt(C0209h.gk, i);
        return "" + i;
    }

    private boolean m508a(Location location, List<C0935e> list) {
        int i = 0;
        for (C0935e c0935e : list) {
            int i2;
            if (location.distanceTo(C0212j.m89a(c0935e.aX().doubleValue(), c0935e.aY().doubleValue(), 0.0f)) < 100.0f) {
                i2 = i + 1;
            } else {
                i2 = i;
            }
            i = i2;
        }
        if (i >= 2) {
            return true;
        }
        return false;
    }

    protected Date getDate() {
        return new Date(this.eU);
    }
}
