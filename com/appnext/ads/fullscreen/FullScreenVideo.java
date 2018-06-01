package com.appnext.ads.fullscreen;

import android.content.Context;

public class FullScreenVideo extends Video {
    public FullScreenVideo(Context context, String str) {
        super(context, 1, str);
    }

    public FullScreenVideo(Context context, String str, FullscreenConfig fullscreenConfig) {
        super(context, 1, str, fullscreenConfig);
    }

    protected C0914d getConfig() {
        return C0914d.ac();
    }

    public String getAUID() {
        return "700";
    }
}
