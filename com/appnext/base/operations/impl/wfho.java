package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;

public class wfho extends C1206d {
    public wfho(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        return C0207f.m70h(C0205d.getContext().getApplicationContext());
    }
}
