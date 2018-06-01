package com.appnext.base.operations.impl;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;

public class dname extends C1206d {
    public dname(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        String str = null;
        if (C0207f.m65b(C0205d.getContext().getApplicationContext(), "android.permission.BLUETOOTH")) {
            try {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter != null) {
                    str = defaultAdapter.getName();
                }
            } catch (Throwable th) {
            }
        }
        if (TextUtils.isEmpty(str)) {
            return Build.BRAND;
        }
        return str;
    }
}
