package com.appnext.ads.fullscreen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import com.appnext.ads.AdsError;
import com.appnext.ads.C0150b;
import com.appnext.base.p005b.C0204c;
import com.appnext.core.Ad;
import com.appnext.core.AppnextError;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0266f;
import com.appnext.core.C0271j;
import com.appnext.core.C0280o;
import com.appnext.core.C0280o.C0278a;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.drive.DriveFile;
import java.util.HashMap;

public abstract class Video extends Ad {
    protected static final int FULL_SCREEN_VIDEO = 1;
    public static final String PROGRESS_BAR = "bar";
    public static final String PROGRESS_CLOCK = "clock";
    public static final String PROGRESS_DEFAULT = "default";
    public static final String PROGRESS_NONE = "none";
    protected static final int REWARDED_VIDEO = 2;
    protected static final String THANK_YOU_JS = "https://appnext.hs.llnwd.net/tools/sdk/rewarded/v63/script.min.js";
    protected static final String TID = "300";
    protected static final String VID = "2.0.3.459";
    @Deprecated
    public static final String VIDEO_LENGTH_DEFAULT = "default";
    public static final String VIDEO_LENGTH_LONG = "30";
    public static final String VIDEO_LENGTH_MANAGED = "managed";
    public static final String VIDEO_LENGTH_SHORT = "15";
    protected static Video currentAd;
    private static boolean displaySaved = true;
    private long closeDelay = -1;
    private String progressColor = "";
    private String progressType = "default";
    private boolean setShowClose = false;
    private boolean showClose = false;
    private int type;
    private OnVideoEnded videoEnded;
    private String videoLength = "managed";

    class C09111 implements C0278a {
        final /* synthetic */ Video cL;

        C09111(Video video) {
            this.cL = video;
        }

        public void mo1197b(HashMap<String, String> hashMap) {
            C0271j.ck().m214d(Integer.parseInt(this.cL.getConfig().get("banner_expiration_time")));
        }

        public void error(String str) {
            C0271j.ck().m214d(Integer.parseInt(this.cL.getConfig().get("banner_expiration_time")));
        }
    }

    class C09122 implements C0248a {
        final /* synthetic */ Video cL;

        C09122(Video video) {
            this.cL = video;
        }

        public <T> void mo1187a(T t) {
            if (this.cL.getOnAdLoadedCallback() != null) {
                this.cL.getOnAdLoadedCallback().adLoaded();
            }
        }

        public void error(String str) {
            if (this.cL.getOnAdErrorCallback() != null) {
                this.cL.getOnAdErrorCallback().adError(str);
            }
        }
    }

    public Video(Context context, int i, String str) {
        super(context, str);
        this.type = i;
        downloadConfig();
    }

    public Video(Context context, int i, String str, VideoConfig videoConfig) {
        super(context, str);
        this.type = i;
        downloadConfig();
        setPostback(videoConfig.getPostback());
        setCategories(videoConfig.getCategories());
        setOrientation(videoConfig.getOrientation());
        if (videoConfig.aj()) {
            setProgressColor(videoConfig.getProgressColor());
        } else {
            setProgressColor("");
        }
        setProgressType(videoConfig.getProgressType());
        if (videoConfig.ag()) {
            setShowClose(videoConfig.isShowClose());
        }
        if (videoConfig.getCloseDelay() >= 0) {
            setCloseDelay(videoConfig.getCloseDelay());
        }
        setVideoLength(videoConfig.getVideoLength());
        setButtonColor(videoConfig.getButtonColor());
        setButtonText(videoConfig.getButtonText());
        if (videoConfig.ai()) {
            setBackButtonCanClose(videoConfig.isBackButtonCanClose());
        }
        if (videoConfig.ah()) {
            setMute(videoConfig.getMute());
        }
    }

    private void downloadConfig() {
        getConfig().m234a(new C09111(this));
    }

    public void showAd() {
        if (getPlacementID().equals("")) {
            throw new IllegalArgumentException("Placement ID cannot be empty");
        }
        int az = C0266f.az(getConfig().get("min_internet_connection_video"));
        int az2 = C0266f.az(C0266f.m210z(this.context));
        if (az2 == 0) {
            if (getOnAdErrorCallback() != null) {
                getOnAdErrorCallback().adError(AppnextError.CONNECTION_ERROR);
            }
        } else if (az2 >= az) {
            C0266f.m201a(getTID(), getVID(), getAUID(), getPlacementID(), "", C0150b.br, this.type == 1 ? "fullscreen" : "rewarded", "", "");
            if (C0913c.aa().m343e(this)) {
                Intent intent = new Intent(this.context, FullscreenActivity.class);
                intent.setFlags(DriveFile.MODE_READ_ONLY);
                intent.putExtra("id", getPlacementID());
                intent.putExtra(C0204c.fU, this.type);
                if (this.setShowClose) {
                    intent.putExtra("show_close", this.showClose);
                }
                currentAd = this;
                this.context.startActivity(intent);
                return;
            }
            if (getOnAdErrorCallback() != null) {
                getOnAdErrorCallback().adError(AdsError.AD_NOT_READY);
            }
            C0913c.aa().m152a(this.context, (Ad) this, getPlacementID(), null);
            C0266f.m201a(getTID(), getVID(), getAUID(), getPlacementID(), "", C0150b.AD_NOT_READY, this.type == 1 ? "fullscreen" : "rewarded", "", "");
        } else if (getOnAdErrorCallback() != null) {
            getOnAdErrorCallback().adError(AdsError.SLOW_CONNECTION);
        }
    }

    public void loadAd() {
        if (getPlacementID().equals("")) {
            throw new IllegalArgumentException("Placement ID cannot be empty");
        }
        C0913c.aa().m152a(this.context, (Ad) this, getPlacementID(), new C09122(this));
    }

    protected C0280o getConfig() {
        if (this.type == 2) {
            return C0916f.af();
        }
        if (this.type == 1) {
            return C0914d.ac();
        }
        return null;
    }

    public boolean isAdLoaded() {
        if (getPlacementID().equals("")) {
            return false;
        }
        return C0913c.aa().m342b((Ad) this);
    }

    public OnVideoEnded getOnVideoEndedCallback() {
        return this.videoEnded;
    }

    public void setOnVideoEndedCallback(OnVideoEnded onVideoEnded) {
        this.videoEnded = onVideoEnded;
    }

    protected Boolean getCanClose() {
        return this.canClose;
    }

    public String getProgressType() {
        return this.progressType;
    }

    public void setProgressType(String str) {
        if (str.equals("default") || str.equals(PROGRESS_BAR) || str.equals(PROGRESS_CLOCK) || str.equals(PROGRESS_NONE)) {
            this.progressType = str;
            return;
        }
        throw new IllegalArgumentException("Wrong progress type");
    }

    public String getVideoLength() {
        return this.videoLength;
    }

    public void setVideoLength(String str) {
        if (str.equals(VIDEO_LENGTH_SHORT) || str.equals(VIDEO_LENGTH_LONG) || str.equals("default") || str.equals("managed")) {
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
        if (str == null || str.equals("")) {
            this.progressColor = "";
            return;
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        try {
            Color.parseColor(str);
            this.progressColor = str;
        } catch (Throwable th) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown color");
        }
    }

    public boolean isShowClose() {
        return this.showClose;
    }

    public void setShowClose(boolean z) {
        this.setShowClose = true;
        this.showClose = z;
    }

    public void setShowClose(boolean z, long j) {
        setShowClose(z);
        setCloseDelay(j);
    }

    public static void setDisplaySaved(boolean z) {
        displaySaved = z;
    }

    protected static boolean getDisplaySaved() {
        return displaySaved;
    }

    public String getVID() {
        return "2.0.3.459";
    }

    public String getTID() {
        return TID;
    }

    public void destroy() {
        super.destroy();
        this.videoEnded = null;
        currentAd = null;
    }
}
