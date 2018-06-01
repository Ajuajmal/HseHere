package com.appnext.base.operations.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0212j;
import java.util.Map.Entry;
import java.util.SortedMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class reap extends C1206d {
    private Context mContext = C0205d.getContext();

    public reap(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    public Object mo1242Z(String str) {
        try {
            return new JSONArray(str);
        } catch (Throwable th) {
            return null;
        }
    }

    protected String getData() {
        SortedMap a;
        double k = C0212j.m100k(this.mContext.getApplicationContext());
        if (VERSION.SDK_INT < 21) {
            a = C0212j.m92a(this.mContext.getApplicationContext(), Double.valueOf(k));
        } else {
            a = C0212j.m99j(this.mContext.getApplicationContext());
        }
        if (a == null || a.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (Entry entry : a.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("package", entry.getValue());
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray.toString();
    }
}
