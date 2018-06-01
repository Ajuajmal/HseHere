package com.appnext.ads.fullscreen;

import android.graphics.Color;
import com.appnext.core.C0280o;
import com.appnext.core.Configuration;
import java.io.Serializable;

public class VideoConfig extends Configuration implements Serializable {
    private static final long serialVersionUID = 1;
    protected boolean cM = false;
    public long closeDelay = -1;
    public String progressColor = "";
    public String progressType = "default";
    public Boolean showClose;
    public String videoLength = "managed";

    protected Boolean getCanClose() {
        return this.backButtonCanClose;
    }

    public String getProgressType() {
        return this.progressType;
    }

    public void setProgressType(String str) {
        if (str.equals("default") || str.equals(Video.PROGRESS_BAR) || str.equals(Video.PROGRESS_CLOCK) || str.equals(Video.PROGRESS_NONE)) {
            this.progressType = str;
            return;
        }
        throw new IllegalArgumentException("Wrong progress type");
    }

    public String getVideoLength() {
        return this.videoLength;
    }

    public void setVideoLength(String str) {
        if (str.equals(Video.VIDEO_LENGTH_SHORT) || str.equals(Video.VIDEO_LENGTH_LONG) || str.equals("default") || str.equals("managed")) {
            this.videoLength = str;
            return;
        }
        throw new IllegalArgumentException("Wrong video length");
    }

    public long getCloseDelay() {
        return this.closeDelay;
    }

    protected void setCloseDelay(long j) {
        if (j >= 0) {
            this.closeDelay = j;
            return;
        }
        throw new IllegalArgumentException("Close delay must be >= 0");
    }

    public String getProgressColor() {
        return this.progressColor;
    }

    public void setProgressColor(String str) {
        if (str == null) {
            this.progressColor = "";
            return;
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        try {
            Color.parseColor(str);
            this.progressColor = str;
            this.cM = true;
        } catch (Throwable th) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown color");
        }
    }

    public boolean isShowClose() {
        return this.showClose == null ? Boolean.parseBoolean(ab().get("show_close")) : this.showClose.booleanValue();
    }

    public void setShowClose(boolean z) {
        this.showClose = Boolean.valueOf(z);
    }

    public void setShowClose(boolean z, long j) {
        setShowClose(z);
        setCloseDelay(j);
    }

    protected C0280o ab() {
        return C0914d.ac();
    }

    protected boolean ag() {
        return this.showClose != null;
    }

    protected boolean ah() {
        return this.mute != null;
    }

    protected boolean ai() {
        return this.backButtonCanClose != null;
    }

    protected boolean aj() {
        return !this.buttonColor.equals("");
    }

    protected boolean ak() {
        return this.cM;
    }
}
