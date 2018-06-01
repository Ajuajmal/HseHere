package com.appnext.ads.interstitial;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import com.appnext.ads.AdsError;
import com.appnext.core.Ad;
import com.appnext.core.AppnextAd;
import com.appnext.core.AppnextError;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0266f;
import com.appnext.core.C0271j;
import com.appnext.core.C0280o;
import com.appnext.core.C0280o.C0278a;
import com.appnext.core.webview.AppnextWebView;
import com.appnext.core.webview.C0285a;
import com.google.android.gms.drive.DriveFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Interstitial extends Ad {
    private static final String JS_URL = "https://appnext.hs.llnwd.net/tools/sdk/interstitial/v63/script.min.js";
    protected static final String TID = "300";
    public static final String TYPE_MANAGED = "managed";
    public static final String TYPE_STATIC = "static";
    public static final String TYPE_VIDEO = "video";
    protected static final String VID = "2.0.3.459";
    protected static Interstitial currentAd;
    private boolean autoPlay = true;
    private boolean calledShow = false;
    private boolean configLoaded = false;
    private String creativeType = "managed";
    private boolean setAutoPlay = false;
    private boolean setCanClose = false;
    private String skipText = "";
    private String titleText = "";

    class C01701 implements Runnable {
        final /* synthetic */ Interstitial cN;

        class C01681 implements Runnable {
            final /* synthetic */ C01701 cO;

            C01681(C01701 c01701) {
                this.cO = c01701;
            }

            public void run() {
                if (this.cO.cN.getOnAdErrorCallback() != null) {
                    this.cO.cN.getOnAdErrorCallback().adError(AppnextError.CONNECTION_ERROR);
                }
            }
        }

        class C01692 implements Runnable {
            final /* synthetic */ C01701 cO;

            class C09171 implements C0248a {
                final /* synthetic */ C01692 cP;

                C09171(C01692 c01692) {
                    this.cP = c01692;
                }

                public <T> void mo1187a(T t) {
                    Iterator it = ((ArrayList) t).iterator();
                    while (it.hasNext()) {
                        AppnextAd appnextAd = (AppnextAd) it.next();
                        if (this.cP.cO.cN.getCreativeType().equals("static")) {
                            if (!appnextAd.getWideImageURL().equals("")) {
                                if (this.cP.cO.cN.getOnAdLoadedCallback() != null) {
                                    this.cP.cO.cN.getOnAdLoadedCallback().adLoaded();
                                    return;
                                }
                                return;
                            }
                        } else if (this.cP.cO.cN.getCreativeType().equals("video")) {
                            if (this.cP.cO.cN.hasVideo(appnextAd)) {
                                if (this.cP.cO.cN.getOnAdLoadedCallback() != null) {
                                    this.cP.cO.cN.getOnAdLoadedCallback().adLoaded();
                                    return;
                                }
                                return;
                            }
                        } else if (this.cP.cO.cN.getOnAdLoadedCallback() != null) {
                            this.cP.cO.cN.getOnAdLoadedCallback().adLoaded();
                            return;
                        } else {
                            return;
                        }
                    }
                    if (this.cP.cO.cN.getOnAdErrorCallback() != null) {
                        this.cP.cO.cN.getOnAdErrorCallback().adError(AppnextError.NO_ADS);
                    }
                }

                public void error(String str) {
                    if (this.cP.cO.cN.getOnAdErrorCallback() != null) {
                        this.cP.cO.cN.getOnAdErrorCallback().adError(str);
                    }
                }
            }

            C01692(C01701 c01701) {
                this.cO = c01701;
            }

            public void run() {
                C0923a.aq().m152a(this.cO.cN.context, this.cO.cN, this.cO.cN.getPlacementID(), new C09171(this));
            }
        }

        C01701(Interstitial interstitial) {
            this.cN = interstitial;
        }

        public void run() {
            try {
                if (this.cN.context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
                    C0266f.m196a("http://www.appnext.com/myid.html", null);
                } else {
                    NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.cN.context.getSystemService("connectivity")).getActiveNetworkInfo();
                    if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                        throw new IOException();
                    }
                }
                new Handler(Looper.getMainLooper()).post(new C01692(this));
            } catch (Throwable th) {
                new Handler(Looper.getMainLooper()).post(new C01681(this));
            }
        }
    }

    class C09182 implements C0278a {
        final /* synthetic */ Interstitial cN;

        C09182(Interstitial interstitial) {
            this.cN = interstitial;
        }

        public void mo1197b(HashMap<String, String> hashMap) {
            this.cN.configLoaded = true;
            C0271j.ck().m214d(Integer.parseInt(C0925c.as().get("banner_expiration_time")));
            if (this.cN.calledShow) {
                this.cN.showAd();
            }
        }

        public void error(String str) {
            this.cN.configLoaded = true;
            C0271j.ck().m214d(Integer.parseInt(C0925c.as().get("banner_expiration_time")));
            if (this.cN.calledShow) {
                this.cN.showAd();
            }
        }
    }

    public Interstitial(Context context, String str) {
        super(context, str);
        init();
    }

    public Interstitial(Context context, String str, InterstitialConfig interstitialConfig) {
        super(context, str);
        init();
        setPostback(interstitialConfig.getPostback());
        setCategories(interstitialConfig.getCategories());
        setButtonText(interstitialConfig.getButtonText());
        setButtonColor(interstitialConfig.getButtonColor());
        if (interstitialConfig.ai()) {
            setBackButtonCanClose(interstitialConfig.isBackButtonCanClose());
        }
        setSkipText(interstitialConfig.getSkipText());
        if (interstitialConfig.ar()) {
            setAutoPlay(interstitialConfig.isAutoPlay());
        }
        setCreativeType(interstitialConfig.getCreativeType());
        setOrientation(interstitialConfig.getOrientation());
        if (interstitialConfig.ah()) {
            setMute(interstitialConfig.getMute());
        }
    }

    private void init() {
        loadConfig();
        AppnextWebView.m241B(this.context).m251a(getPageUrl(), null);
    }

    public void loadAd() {
        if (getPlacementID().equals("")) {
            throw new IllegalArgumentException("Placement ID cannot be empty");
        }
        new Thread(new C01701(this)).start();
    }

    public void showAd() {
        if (getPlacementID().equals("")) {
            throw new IllegalArgumentException("Placement ID cannot be empty");
        }
        int az = C0266f.az(C0925c.as().get("min_internet_connection"));
        int az2 = C0266f.az(C0266f.m210z(this.context));
        if (az2 == 0) {
            if (getOnAdErrorCallback() != null) {
                getOnAdErrorCallback().adError(AppnextError.CONNECTION_ERROR);
            }
        } else if (az2 >= az) {
            currentAd = this;
            Intent activityIntent = getActivityIntent();
            if (activityIntent == null) {
                throw new IllegalArgumentException("null intent");
            }
            this.context.startActivity(activityIntent);
        } else if (getOnAdErrorCallback() != null) {
            getOnAdErrorCallback().adError(AdsError.SLOW_CONNECTION);
        }
    }

    protected Intent getActivityIntent() {
        Intent intent = new Intent(this.context, InterstitialActivity.class);
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        intent.addFlags(67108864);
        intent.putExtra("id", getPlacementID());
        if (this.setAutoPlay) {
            intent.putExtra("auto_play", this.autoPlay);
        }
        if (this.setCanClose) {
            intent.putExtra("can_close", isBackButtonCanClose());
        }
        if (this.setMeute) {
            intent.putExtra("mute", getMute());
        }
        intent.putExtra("cat", getCategories());
        intent.putExtra("pbk", getPostback());
        intent.putExtra("b_title", getButtonText());
        intent.putExtra("b_color", getButtonColor());
        intent.putExtra("skip_title", getSkipText());
        int az = C0266f.az(C0925c.as().get("min_internet_connection"));
        int az2 = C0266f.az(C0925c.as().get("min_internet_connection_video"));
        int az3 = C0266f.az(C0266f.m210z(this.context));
        if (az3 < az || az3 >= az2) {
            intent.putExtra("creative", getCreativeType());
        } else {
            intent.putExtra("creative", "static");
        }
        return intent;
    }

    public boolean isAdLoaded() {
        return !getPlacementID().equals("") && C0923a.aq().m416b(this);
    }

    protected C0280o getConfig() {
        return C0925c.as();
    }

    private void loadConfig() {
        getConfig().m234a(new C09182(this));
    }

    public void setCreativeType(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Wrong creative type");
        } else if (str.equals("managed") || str.equals("static") || str.equals("video")) {
            this.creativeType = str;
        } else {
            throw new IllegalArgumentException("Wrong creative type");
        }
    }

    public String getCreativeType() {
        return this.creativeType;
    }

    public void setBackButtonCanClose(boolean z) {
        this.setCanClose = true;
        super.setBackButtonCanClose(z);
    }

    public boolean isAutoPlay() {
        return this.autoPlay;
    }

    public void setAutoPlay(boolean z) {
        this.setAutoPlay = true;
        this.autoPlay = z;
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

    private boolean hasVideo(AppnextAd appnextAd) {
        return (appnextAd.getVideoUrl().equals("") && appnextAd.getVideoUrlHigh().equals("") && appnextAd.getVideoUrl30Sec().equals("") && appnextAd.getVideoUrlHigh30Sec().equals("")) ? false : true;
    }

    protected String getPageUrl() {
        return JS_URL;
    }

    protected C0285a getFallback() {
        return new C0924b();
    }

    public String getVID() {
        return "2.0.3.459";
    }

    public String getTID() {
        return TID;
    }

    public String getAUID() {
        return "600";
    }

    public void destroy() {
        super.destroy();
        currentAd = null;
    }
}
