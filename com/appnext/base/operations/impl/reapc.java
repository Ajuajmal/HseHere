package com.appnext.base.operations.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0932a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0212j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class reapc extends C1206d {
    private Context mContext = C0205d.getContext();

    public reapc(C0934c c0934c, Bundle bundle) {
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
        if (a.isEmpty()) {
            return null;
        }
        Map hashMap = new HashMap();
        for (Entry value : a.entrySet()) {
            List R = C0192a.aE().aG().m439R((String) value.getValue());
            if (R.size() > 0) {
                Integer aO = ((C0932a) R.get(0)).aO();
                if (hashMap.containsKey(aO)) {
                    hashMap.put(aO, Integer.valueOf(((Integer) hashMap.get(aO)).intValue() + 1));
                } else {
                    hashMap.put(aO, Integer.valueOf(1));
                }
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (Entry value2 : hashMap.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("category", value2.getKey());
                jSONObject.put("appcount", value2.getValue());
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
            jSONArray.put(jSONObject);
        }
        return jSONArray.toString();
    }
}
