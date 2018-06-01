package com.appnext.core;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import com.appnext.base.Appnext;
import com.appnext.core.callbacks.OnAdClicked;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnAdOpened;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public abstract class Ad {
    public static final String ORIENTATION_AUTO = "automatic";
    public static final String ORIENTATION_DEFAULT = "not_set";
    public static final String ORIENTATION_LANDSCAPE = "landscape";
    public static final String ORIENTATION_PORTRAIT = "portrait";
    private OnAdOpened adOpenedCallback;
    private String buttonColor = "";
    private String buttonText = "";
    protected Boolean canClose;
    private String cat = "";
    private OnAdClosed closeCallback;
    private int cnt = 50;
    protected Context context;
    private boolean mute = false;
    private OnAdClicked onAdClicked;
    private OnAdError onAdError;
    private OnAdLoaded onAdLoaded;
    private String orientation = ORIENTATION_DEFAULT;
    private String pbk = "";
    private String placementID = "";
    protected boolean setMeute = false;

    public abstract String getAUID();

    public abstract String getTID();

    public abstract String getVID();

    public abstract boolean isAdLoaded();

    public abstract void loadAd();

    public abstract void showAd();

    public Ad(final Context context, String str) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        } else if (str == null) {
            throw new IllegalArgumentException("placementID cannot be null");
        } else {
            this.context = context;
            this.placementID = str;
            C0271j.ck().m215d(context, str);
            Appnext.init(context);
            if (C0266f.ce().equals("")) {
                new Handler(Looper.getMainLooper()).post(new Runnable(this) {
                    final /* synthetic */ Ad gC;

                    public void run() {
                        C0266f.m208x(context);
                    }
                });
            }
        }
    }

    protected Ad(Ad ad) {
        this.placementID = ad.placementID;
        this.context = ad.context;
        this.cat = ad.cat;
        this.pbk = ad.pbk;
        this.cnt = ad.cnt;
        this.onAdClicked = ad.onAdClicked;
        this.onAdError = ad.onAdError;
        this.onAdLoaded = ad.onAdLoaded;
        this.closeCallback = ad.closeCallback;
        this.adOpenedCallback = ad.adOpenedCallback;
    }

    public String getPlacementID() {
        return this.placementID;
    }

    protected void setPlacementID(String str) {
        this.placementID = str;
    }

    public OnAdClicked getOnAdClickedCallback() {
        return this.onAdClicked;
    }

    public OnAdError getOnAdErrorCallback() {
        return this.onAdError;
    }

    public OnAdLoaded getOnAdLoadedCallback() {
        return this.onAdLoaded;
    }

    public OnAdClosed getOnAdClosedCallback() {
        return this.closeCallback;
    }

    public void setOnAdClickedCallback(OnAdClicked onAdClicked) {
        this.onAdClicked = onAdClicked;
    }

    public void setOnAdErrorCallback(OnAdError onAdError) {
        this.onAdError = onAdError;
    }

    public void setOnAdLoadedCallback(OnAdLoaded onAdLoaded) {
        this.onAdLoaded = onAdLoaded;
    }

    public void setOnAdClosedCallback(OnAdClosed onAdClosed) {
        this.closeCallback = onAdClosed;
    }

    public OnAdOpened getOnAdOpenedCallback() {
        return this.adOpenedCallback;
    }

    public void setOnAdOpenedCallback(OnAdOpened onAdOpened) {
        this.adOpenedCallback = onAdOpened;
    }

    public void setCategories(String str) {
        String str2;
        if (str == null) {
            str2 = "";
        } else {
            str2 = str;
        }
        try {
            if (str2.equals(URLDecoder.decode(str2, "UTF-8"))) {
                str2 = URLEncoder.encode(str2, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            str2 = "";
        }
        this.cat = str2;
    }

    public void setPostback(String str) {
        String str2;
        if (str == null) {
            str2 = "";
        } else {
            str2 = str;
        }
        try {
            if (str2.equals(URLDecoder.decode(str2, "UTF-8"))) {
                str2 = URLEncoder.encode(str2, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            str2 = "";
        }
        this.pbk = str2;
    }

    public void setButtonText(String str) {
        if (str == null) {
            str = "";
        }
        this.buttonText = str;
    }

    public void setButtonColor(String str) {
        if (str == null || str.equals("")) {
            this.buttonColor = "";
            return;
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        try {
            Color.parseColor(str);
            this.buttonColor = str;
        } catch (Throwable th) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown color");
        }
    }

    public void setMute(boolean z) {
        this.setMeute = true;
        this.mute = z;
    }

    public String getCategories() {
        return this.cat;
    }

    public String getPostback() {
        return this.pbk;
    }

    public String getButtonText() {
        return this.buttonText;
    }

    public String getButtonColor() {
        return this.buttonColor;
    }

    public boolean getMute() {
        return this.mute;
    }

    public boolean isBackButtonCanClose() {
        return this.canClose == null ? false : this.canClose.booleanValue();
    }

    public void setBackButtonCanClose(boolean z) {
        this.canClose = Boolean.valueOf(z);
    }

    protected int getCount() {
        return this.cnt;
    }

    protected void setCount(int i) {
        this.cnt = i;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String str) {
        if (str == null) {
            throw new IllegalArgumentException("orientation type");
        } else if (str.equals(ORIENTATION_AUTO) || str.equals(ORIENTATION_DEFAULT) || str.equals(ORIENTATION_LANDSCAPE) || str.equals(ORIENTATION_PORTRAIT)) {
            this.orientation = str;
        } else {
            throw new IllegalArgumentException("Wrong orientation type");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!(obj instanceof Ad)) {
            return super.equals(obj);
        }
        boolean z;
        if (((Ad) obj).getPlacementID().equals(getPlacementID()) && ((Ad) obj).getCategories().equals(getCategories()) && ((Ad) obj).getPostback().equals(getPostback()) && ((Ad) obj).getCount() == getCount()) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((getPlacementID().hashCode() * 31) + getCategories().hashCode()) * 31) + getPostback().hashCode()) * 31) + getCount();
    }

    public void destroy() {
        this.context = null;
        this.onAdClicked = null;
        this.onAdError = null;
        this.onAdLoaded = null;
        this.closeCallback = null;
        this.adOpenedCallback = null;
    }
}
