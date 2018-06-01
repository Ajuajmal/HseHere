package com.appnext.appnextsdk.API;

import java.io.Serializable;

public class AppnextAd extends com.appnext.core.AppnextAd implements Serializable {
    protected AppnextAd(com.appnext.core.AppnextAd appnextAd) {
        super(appnextAd);
    }

    public String getImageURLWide() {
        return getWideImageURL();
    }

    protected void setImageURLWide(String str) {
        setWideImageURL(str);
    }

    protected String getBpub() {
        return super.getBpub();
    }

    protected String getEpub() {
        return super.getEpub();
    }

    protected String getZoneID() {
        return super.getZoneID();
    }

    protected String getAppURL() {
        return super.getAppURL();
    }

    protected String getImpressionURL() {
        return super.getImpressionURL();
    }

    protected String getPlacementID() {
        return super.getPlacementID();
    }

    protected void setPlacementID(String str) {
        super.setPlacementID(str);
    }

    protected String getCampaignGoal() {
        return super.getCampaignGoal();
    }

    public String getButtonText() {
        return super.getButtonText();
    }
}
