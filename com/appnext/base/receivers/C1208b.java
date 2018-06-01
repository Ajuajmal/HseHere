package com.appnext.base.receivers;

import android.content.Context;
import android.content.Intent;
import com.appnext.base.C0216b;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class C1208b extends C0949a {
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    protected String m494a(Boolean bool, String str) {
        JSONArray jSONArray = new JSONArray();
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("connected", bool);
            jSONObject.put("name", str);
            jSONArray.put(jSONObject);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
        return jSONArray.toString();
    }
}
