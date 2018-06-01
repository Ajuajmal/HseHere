package com.appnext.ads.interstitial;

import com.appnext.core.C0280o;
import com.appnext.core.Configuration;
import java.io.Serializable;

public class InterstitialConfig extends Configuration implements Serializable {
    private static final long serialVersionUID = 1;
    public Boolean autoPlay;
    public String creativeType = "managed";
    public String skipText = "";

    public void setCreativeType(String str) {
        if (str.equals("managed") || str.equals("static") || str.equals("video")) {
            this.creativeType = str;
            return;
        }
        throw new IllegalArgumentException("Wrong creative type");
    }

    public String getCreativeType() {
        return this.creativeType;
    }

    protected C0280o ab() {
        return C0925c.as();
    }

    public boolean isAutoPlay() {
        return this.autoPlay == null ? Boolean.parseBoolean(ab().get("auto_play")) : this.autoPlay.booleanValue();
    }

    public void setAutoPlay(boolean z) {
        this.autoPlay = Boolean.valueOf(z);
    }

    public void setSkipText(String str) {
        if (str == null) {
            str = "";
        }
        this.skipText = str;
    }

    public String getSkipText() {
        return this.skipText;
    }

    protected boolean ah() {
        return this.mute != null;
    }

    protected boolean ar() {
        return this.autoPlay != null;
    }

    protected boolean ai() {
        return this.backButtonCanClose != null;
    }
}
