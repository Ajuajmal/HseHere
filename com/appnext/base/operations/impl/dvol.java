package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;

public abstract class dvol extends C1206d {
    private String eK;

    public dvol(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
        this.eK = c0934c.getKey();
    }

    protected String getData() {
        return String.valueOf(C0207f.m63a(C0205d.getContext().getApplicationContext(), this.eK));
    }

    public Object mo1242Z(String str) {
        return Integer.valueOf(str);
    }
}
