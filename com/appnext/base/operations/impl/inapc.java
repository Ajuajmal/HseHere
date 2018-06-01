package com.appnext.base.operations.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
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
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

public class inapc extends C1206d {
    private Context mContext = C0205d.getContext();

    public inapc(C0934c c0934c, Bundle bundle) {
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
        List<ApplicationInfo> a = C0212j.m91a(this.mContext.getPackageManager(), 0);
        HashMap hashMap = new HashMap();
        for (ApplicationInfo applicationInfo : a) {
            try {
                List R = C0192a.aE().aG().m439R(applicationInfo.packageName);
                if (R.size() > 0) {
                    int intValue = ((C0932a) R.get(0)).aO().intValue();
                    if (hashMap.get(Integer.valueOf(intValue)) == null) {
                        hashMap.put(Integer.valueOf(intValue), Integer.valueOf(1));
                    } else {
                        hashMap.put(Integer.valueOf(intValue), Integer.valueOf(((Integer) hashMap.get(Integer.valueOf(intValue))).intValue() + 1));
                    }
                }
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
        if (hashMap == null || hashMap.isEmpty() || hashMap == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (Entry entry : hashMap.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("categoryId", ((Integer) entry.getKey()).intValue());
                jSONObject.put("value", ((Integer) entry.getValue()).intValue());
                jSONArray.put(jSONObject);
            } catch (Throwable th2) {
                return null;
            }
        }
        return jSONArray.toString();
    }
}
