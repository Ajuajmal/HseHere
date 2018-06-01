package com.appnext.appnextsdk.API;

import android.content.Context;
import com.appnext.core.Ad;

public class Native extends Ad {
    private static final String TID = "300";
    protected static final String VID = "2.0.3.459";

    public Native(Context context, String str) {
        super(context, str);
        C0187a.at().init(context);
    }

    public void showAd() {
    }

    public void loadAd() {
    }

    public boolean isAdLoaded() {
        return false;
    }

    public String getVID() {
        return "2.0.3.459";
    }

    public String getTID() {
        return TID;
    }

    public String getAUID() {
        return "500";
    }

    protected int getCount() {
        return super.getCount();
    }

    protected void setCount(int i) {
        super.setCount(i);
    }
}
