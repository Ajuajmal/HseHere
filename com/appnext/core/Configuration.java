package com.appnext.core;

import android.graphics.Color;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public abstract class Configuration implements Serializable {
    private static final long serialVersionUID = 1;
    public Boolean backButtonCanClose;
    public String buttonColor = "";
    public String buttonText = "";
    public String categories = "";
    protected boolean hP = false;
    public Boolean mute;
    public String orientation = Ad.ORIENTATION_DEFAULT;
    public String postback = "";

    protected abstract C0280o ab();

    public Configuration() {
        ab().m234a(null);
    }

    public void setButtonText(String str) {
        if (str == null) {
            str = "";
        }
        this.buttonText = str;
    }

    public void setButtonColor(String str) {
        if (str == null) {
            this.buttonColor = "";
            return;
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        try {
            Color.parseColor(str);
            this.buttonColor = str;
            this.hP = true;
        } catch (Throwable th) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown color");
        }
    }

    public String getButtonText() {
        return this.buttonText;
    }

    public String getButtonColor() {
        return this.buttonColor;
    }

    public boolean isBackButtonCanClose() {
        return this.backButtonCanClose == null ? Boolean.parseBoolean(ab().get("can_close")) : this.backButtonCanClose.booleanValue();
    }

    public void setBackButtonCanClose(boolean z) {
        this.backButtonCanClose = Boolean.valueOf(z);
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
        } catch (Throwable th) {
            str2 = "";
        }
        this.categories = str2;
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
        this.postback = str2;
    }

    public void setMute(boolean z) {
        this.mute = Boolean.valueOf(z);
    }

    public String getCategories() {
        return this.categories;
    }

    public String getPostback() {
        return this.postback;
    }

    public boolean getMute() {
        return this.mute == null ? Boolean.parseBoolean(ab().get("mute")) : this.mute.booleanValue();
    }

    public String getOrientation() {
        return this.orientation;
    }

    public void setOrientation(String str) {
        if (str == null) {
            throw new IllegalArgumentException("orientation type");
        } else if (str.equals(Ad.ORIENTATION_AUTO) || str.equals(Ad.ORIENTATION_DEFAULT) || str.equals(Ad.ORIENTATION_LANDSCAPE) || str.equals(Ad.ORIENTATION_PORTRAIT)) {
            this.orientation = str;
        } else {
            throw new IllegalArgumentException("Wrong orientation type");
        }
    }
}
