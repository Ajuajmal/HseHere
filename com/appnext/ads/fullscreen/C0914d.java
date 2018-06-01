package com.appnext.ads.fullscreen;

import com.appnext.core.C0280o;
import java.util.HashMap;

public class C0914d extends C0280o {
    private static C0914d cE;
    private HashMap<String, String> aG = null;
    private String aZ = "https://appnext.hs.llnwd.net/tools/sdk/config/fullscreen_config.txt";

    public static C0914d ac() {
        if (cE == null) {
            cE = new C0914d();
        }
        return cE;
    }

    private C0914d() {
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

    public void m344a(HashMap<String, String> hashMap) {
        this.aG = hashMap;
    }

    protected HashMap<String, String> mo1215t() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("progress_type", Video.PROGRESS_CLOCK);
        hashMap.put("progress_color", "ffffff");
        hashMap.put("progress_delay", "0");
        hashMap.put("button_color", "66a031");
        hashMap.put("button_text", "Install");
        hashMap.put("button_width", "-1");
        hashMap.put("button_height", "-1");
        hashMap.put("button_position", "bottomright");
        hashMap.put("can_close", "false");
        hashMap.put("close_delay", "0");
        hashMap.put("show_close", "false");
        hashMap.put("close_button_position", "topright");
        hashMap.put("video_length", Video.VIDEO_LENGTH_SHORT);
        hashMap.put("mute", "false");
        hashMap.put("urlApp_protection", "false");
        hashMap.put("pview", "true");
        hashMap.put("min_internet_connection_video", "3g");
        hashMap.put("show_install", "true");
        hashMap.put("banner_expiration_time", "12");
        hashMap.put("show_icon", "true");
        hashMap.put("show_nameApp", "true");
        hashMap.put("show_rating", "true");
        hashMap.put("show_desc", "true");
        hashMap.put("new_button_text", "Install");
        hashMap.put("existing_button_text", "Open");
        hashMap.put("postpone_vta_sec", "0");
        hashMap.put("postpone_impression_sec", "0");
        hashMap.put("resolve_timeout", "8");
        hashMap.put("num_saved_videos", "5");
        return hashMap;
    }
}
