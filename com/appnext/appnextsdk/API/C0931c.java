package com.appnext.appnextsdk.API;

import com.appnext.core.C0280o;
import java.util.HashMap;

public class C0931c extends C0280o {
    private static C0931c dw;
    private HashMap<String, String> aG = null;
    private String aZ = "https://appnext.hs.llnwd.net/tools/sdk/config/native_ads_config.txt";

    public static C0931c ay() {
        if (dw == null) {
            dw = new C0931c();
        }
        return dw;
    }

    private C0931c() {
    }

    protected String getUrl() {
        return this.aZ;
    }

    public void setUrl(String str) {
        this.aZ = str;
    }

    public void m434a(HashMap<String, String> hashMap) {
        this.aG = hashMap;
    }

    protected HashMap<String, String> mo1214s() {
        return this.aG;
    }

    protected HashMap<String, String> mo1215t() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("postview_location", "play_video_pv");
        hashMap.put("capRange", "6");
        hashMap.put("resolve_timeout", "8");
        hashMap.put("urlApp_protection", "true");
        return hashMap;
    }
}
