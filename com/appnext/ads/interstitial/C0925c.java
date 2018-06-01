package com.appnext.ads.interstitial;

import com.appnext.ads.fullscreen.Video;
import com.appnext.core.C0280o;
import java.util.HashMap;

public class C0925c extends C0280o {
    private static C0925c di;
    private HashMap<String, String> aG = null;
    private String aZ = "https://appnext.hs.llnwd.net/tools/sdk/config/interstitial_config.txt";

    public static C0925c as() {
        if (di == null) {
            di = new C0925c();
        }
        return di;
    }

    private C0925c() {
    }

    protected String getUrl() {
        return this.aZ;
    }

    protected HashMap<String, String> mo1214s() {
        return this.aG;
    }

    public void setUrl(String str) {
        this.aZ = str;
    }

    public void m419a(HashMap<String, String> hashMap) {
        this.aG = hashMap;
    }

    protected HashMap<String, String> mo1215t() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("creative", "managed");
        hashMap.put("auto_play", "true");
        hashMap.put("mute", "false");
        hashMap.put("pview", "true");
        hashMap.put("min_internet_connection", "2g");
        hashMap.put("min_internet_connection_video", "3g");
        hashMap.put("urlApp_protection", "false");
        hashMap.put("can_close", "false");
        hashMap.put("video_length", Video.VIDEO_LENGTH_SHORT);
        hashMap.put("button_text", "");
        hashMap.put("button_color", "");
        hashMap.put("skip_title", "");
        hashMap.put("remove_poster_on_auto_play", "true");
        hashMap.put("banner_expiration_time", "12");
        hashMap.put("show_rating", "true");
        hashMap.put("show_desc", "true");
        hashMap.put("new_button_text", "Install");
        hashMap.put("existing_button_text", "Open");
        hashMap.put("postpone_vta_sec", "0");
        hashMap.put("postpone_impression_sec", "0");
        hashMap.put("resolve_timeout", "8");
        return hashMap;
    }
}
