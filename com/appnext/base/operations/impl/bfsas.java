package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0207f;

public abstract class bfsas extends C1206d {
    private String eE;

    public bfsas(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
        this.eE = c0934c.getKey();
    }

    protected String getData() {
        long ak = C0207f.ak(this.eE);
        if (ak == -1) {
            return null;
        }
        return String.valueOf(ak);
    }

    public Object mo1242Z(String str) {
        return Long.valueOf(str);
    }
}
