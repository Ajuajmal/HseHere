package com.appnext.base.operations.impl;

import android.os.Build;
import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;

public class dmod extends C1206d {
    public dmod(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        return Build.MODEL;
    }
}
