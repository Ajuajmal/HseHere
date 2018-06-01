package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;

public class duse extends C1206d {
    public duse(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        Object valueOf = Boolean.valueOf(false);
        try {
            valueOf = Boolean.valueOf(C0207f.m67e(C0205d.getContext()));
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
        return String.valueOf(valueOf);
    }

    public Object mo1242Z(String str) {
        return Boolean.valueOf(str);
    }
}
