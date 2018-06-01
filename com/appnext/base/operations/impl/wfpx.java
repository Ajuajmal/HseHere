package com.appnext.base.operations.impl;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class wfpx extends C1206d {
    private Context mContext = C0205d.getContext();

    public wfpx(C0934c c0934c, Bundle bundle) {
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
        WifiManager wifiManager = (WifiManager) this.mContext.getApplicationContext().getSystemService("wifi");
        if (!C0207f.m65b(this.mContext.getApplicationContext(), "android.permission.ACCESS_WIFI_STATE") || ((!C0207f.m65b(this.mContext.getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION") && !C0207f.m65b(this.mContext.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION")) || !wifiManager.isWifiEnabled())) {
            return null;
        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
        if (scanResults == null || scanResults.isEmpty()) {
            return null;
        }
        Set hashSet = new HashSet();
        JSONArray jSONArray = new JSONArray();
        for (ScanResult scanResult : scanResults) {
            String str = scanResult.SSID;
            if (!hashSet.contains(str)) {
                hashSet.add(str);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("ssid", str);
                } catch (Throwable th) {
                    C0216b.m114a(th);
                }
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray.toString();
    }
}
