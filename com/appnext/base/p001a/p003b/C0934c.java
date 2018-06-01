package com.appnext.base.p001a.p003b;

import android.text.TextUtils;
import org.json.JSONObject;

public class C0934c extends C0193d {
    private JSONObject f58Y;
    private String dU;
    private String dV;
    private String dW;
    private String dX;
    private String dY;
    private String dZ;
    private boolean ea;

    public C0934c(String str, String str2, String str3, String str4, String str5, boolean z, String str6, String str7) {
        this.dU = str;
        this.dV = str2;
        this.dW = str3;
        this.dX = str4;
        this.dY = str5;
        this.dZ = str6;
        this.ea = z;
        if (TextUtils.isEmpty(str7)) {
            this.f58Y = null;
            return;
        }
        try {
            this.f58Y = new JSONObject(str7);
        } catch (Throwable th) {
            this.f58Y = null;
        }
    }

    public String aR() {
        return this.dU;
    }

    public String aS() {
        return this.dV;
    }

    public String aT() {
        return this.dW;
    }

    public String aU() {
        return this.dX;
    }

    public String aV() {
        return this.dY;
    }

    public String getKey() {
        return this.dZ;
    }

    public boolean aW() {
        return this.ea;
    }

    public JSONObject bt() {
        return this.f58Y;
    }
}
