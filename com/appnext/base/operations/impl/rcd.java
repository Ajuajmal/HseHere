package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0204c.C0203a;
import org.json.JSONArray;

public class rcd extends C1206d {
    private String eO;
    private C0203a eP;

    public rcd(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
        if (bundle != null) {
            this.eO = bundle.getString(C0204c.fS);
            this.eP = (C0203a) bundle.get(C0204c.fU);
        }
    }

    protected String getData() {
        return String.valueOf(this.eO);
    }

    public Object mo1242Z(String str) {
        try {
            switch (this.eP) {
                case Boolean:
                    return Boolean.valueOf(str);
                case Integer:
                    return Integer.valueOf(str);
                case Long:
                    return String.valueOf(str);
                case JSONArray:
                    return new JSONArray(str);
                default:
                    return str;
            }
        } catch (Throwable th) {
            return str;
        }
    }
}
