package com.appnext.ads.fullscreen;

import com.appnext.core.AppnextAd;

public class FullscreenAd extends AppnextAd {
    private static final long serialVersionUID = 3889030223267203195L;
    private String filePath = "";

    protected FullscreenAd(AppnextAd appnextAd) {
        super(appnextAd);
    }

    protected String getAppURL() {
        return super.getAppURL();
    }

    protected void setAppURL(String str) {
        super.setAppURL(str);
    }

    protected String m477Z() {
        return this.filePath;
    }

    protected void m476F(String str) {
        this.filePath = str;
    }

    protected String getZoneID() {
        return super.getZoneID();
    }

    protected String getCampaignGoal() {
        return super.getCampaignGoal();
    }

    protected String getButtonText() {
        return super.getButtonText();
    }
}
