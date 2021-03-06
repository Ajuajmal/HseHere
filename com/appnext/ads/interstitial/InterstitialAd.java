package com.appnext.ads.interstitial;

import com.appnext.core.AppnextAd;

public class InterstitialAd extends AppnextAd {
    private static final long serialVersionUID = 3889030223267203195L;
    private String filePath = "";

    protected InterstitialAd(AppnextAd appnextAd) {
        super(appnextAd);
    }

    protected String getAppURL() {
        return super.getAppURL();
    }

    protected void setAppURL(String str) {
        super.setAppURL(str);
    }

    protected String m479Z() {
        return this.filePath;
    }

    protected void m478F(String str) {
        this.filePath = str;
    }

    protected String getCampaignGoal() {
        return super.getCampaignGoal();
    }

    protected String getButtonText() {
        return super.getButtonText();
    }
}
