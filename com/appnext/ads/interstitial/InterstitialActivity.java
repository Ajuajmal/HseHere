package com.appnext.ads.interstitial;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.appnext.ads.AdsError;
import com.appnext.ads.C0149a;
import com.appnext.ads.C0150b;
import com.appnext.ads.C0907c;
import com.appnext.core.AppnextActivity;
import com.appnext.core.AppnextAd;
import com.appnext.core.AppnextError;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0262d.C0258a;
import com.appnext.core.C0266f;
import com.appnext.core.C0280o;
import com.appnext.core.webview.AppnextWebView;
import com.appnext.core.webview.AppnextWebView.C0284c;
import com.appnext.core.webview.WebAppInterface;
import com.google.android.gms.appinvite.PreviewActivity;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import org.apache.http.HttpHost;
import org.json.JSONException;
import org.json.JSONObject;

public class InterstitialActivity extends AppnextActivity {
    private ArrayList<AppnextAd> f57D;
    private Boolean autoPlay;
    protected WebView cQ;
    private boolean cR = false;
    private Interstitial cS;
    private String cT = "";
    private String cU = "";
    private boolean cV = false;
    private int cW = 0;
    private Handler cX;
    private C0258a cY;
    private WebAppInterface cZ;
    private Boolean canClose;
    private AppnextAd cd;
    private AppnextAd ce;
    private C0907c cl;
    Runnable cn = new C01746(this);
    Runnable co = new C01757(this);
    private Runnable da = new C01735(this);
    private Boolean mute;

    class C01713 extends WebViewClient {
        final /* synthetic */ InterstitialActivity db;

        C01713(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str == null) {
                return false;
            }
            if (!str.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            webView.loadUrl(str);
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            this.db.cX.removeCallbacksAndMessages(null);
            this.db.am();
        }
    }

    class C01724 extends WebChromeClient {
        final /* synthetic */ InterstitialActivity db;

        C01724(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            C0266f.m194P("console " + consoleMessage.message());
            if (consoleMessage.message().contains("TypeError") && consoleMessage.message().contains("has no method 'setParams'")) {
                if (this.db.cW = this.db.cW + 1 < 5) {
                    this.db.cX.postDelayed(this.db.da, 500);
                } else {
                    this.db.onError(AppnextError.INTERNAL_ERROR);
                    this.db.finish();
                }
            } else if (consoleMessage.message().contains("TypeError") && (consoleMessage.message().contains("has no method") || consoleMessage.message().contains("is not a function"))) {
                this.db.onError(AppnextError.INTERNAL_ERROR);
                this.db.finish();
            }
            return true;
        }
    }

    class C01735 implements Runnable {
        final /* synthetic */ InterstitialActivity db;

        C01735(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public void run() {
            this.db.cX.removeCallbacks(this.db.da);
            this.db.am();
        }
    }

    class C01746 implements Runnable {
        final /* synthetic */ InterstitialActivity db;

        C01746(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public void run() {
            if (this.db.click != null) {
                this.db.click.m187e(this.db.cd);
            }
        }
    }

    class C01757 implements Runnable {
        final /* synthetic */ InterstitialActivity db;

        C01757(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public void run() {
            this.db.mo1191a(this.db.cd, null);
        }
    }

    class C09191 implements C0284c {
        final /* synthetic */ InterstitialActivity db;

        C09191(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public void mo1219J(String str) {
            this.db.al();
        }

        public void error(String str) {
            this.db.al();
        }
    }

    class C09212 implements C0258a {
        final /* synthetic */ InterstitialActivity db;

        class C09201 implements C0258a {
            final /* synthetic */ C09212 dc;

            C09201(C09212 c09212) {
                this.dc = c09212;
            }

            public void onMarket(String str) {
                this.dc.db.cY.onMarket(str);
            }

            public void error(String str) {
                Intent intent;
                if (Boolean.parseBoolean(this.dc.db.m362H("urlApp_protection"))) {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("market://details?id=" + this.dc.db.ce.getAdPackage()));
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    try {
                        this.dc.db.startActivity(intent);
                    } catch (Throwable th) {
                    }
                } else {
                    try {
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        this.dc.db.startActivity(intent);
                    } catch (Throwable th2) {
                    }
                }
                this.dc.db.bZ();
            }
        }

        C09212(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public void onMarket(String str) {
            C0266f.m194P("marketUrl " + str);
            if (this.db.handler != null) {
                this.db.handler.removeCallbacks(null);
            }
            Intent intent;
            if (!C0266f.m206c(this.db, this.db.ce.getAdPackage())) {
                try {
                    if (!(this.db.ce == null || str.contains("market://details?id=" + this.db.ce.getAdPackage()))) {
                        this.db.click.m186a(C0266f.getCookie("admin.appnext.com", "applink"), this.db.ce.getBannerID(), this.db.placementID, this.db.cS.getTID(), str, "SetROpenV1");
                    }
                } catch (Throwable th) {
                }
                if (this.db.ce != null) {
                    if (this.db.cl == null) {
                        this.db.cl = new C0907c();
                    }
                    this.db.cl.m262a(this.db.ce.getAdPackage(), str, C0266f.getCookie("admin.appnext.com", "applink"), this.db.ce.getBannerID(), this.db.placementID, this.db.cS.getTID(), this.db.cS.getVID(), this.db.cS.getAUID(), null);
                    this.db.cl.m224A(this.db);
                }
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                try {
                    this.db.startActivity(intent);
                } catch (Throwable th2) {
                    this.db.m403a(this.db.placementID, str, "cannot_open_market");
                }
            } else if (str.startsWith("market://")) {
                try {
                    intent = this.db.getPackageManager().getLaunchIntentForPackage(this.db.ce.getAdPackage());
                    intent.addFlags(DriveFile.MODE_READ_ONLY);
                    this.db.startActivity(intent);
                } catch (Throwable th3) {
                    this.db.m403a(this.db.placementID, str, "cannot_open_installed_app");
                }
            } else {
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                try {
                    this.db.startActivity(intent);
                } catch (Throwable th4) {
                    this.db.m403a(this.db.placementID, str, "cannot_open_deeplink");
                }
            }
            this.db.bZ();
        }

        public void error(String str) {
            C0266f.m194P("---------------------- error ----------------------");
            if (this.db.handler != null) {
                this.db.handler.removeCallbacks(null);
            }
            if (this.db.ce == null || this.db.cS == null) {
                this.db.bZ();
                return;
            }
            this.db.click.m186a(C0266f.getCookie("admin.appnext.com", "applink"), this.db.ce.getBannerID(), this.db.placementID, this.db.cS.getTID(), str, "SetDOpenV1");
            this.db.m403a(this.db.placementID, new InterstitialAd(this.db.ce).getAppURL() + " " + str, C0150b.bv);
            try {
                this.db.mo1192b(this.db.ce, new C09201(this));
            } catch (Throwable th) {
            }
        }
    }

    class C09229 implements C0248a {
        final /* synthetic */ InterstitialActivity db;

        C09229(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
        }

        public <T> void mo1187a(T t) {
            if (t == null) {
                try {
                    Log.d("appnext SDK", "IA GAL 1");
                    this.db.finish();
                    this.db.onError(AppnextError.NO_ADS);
                } catch (Throwable th) {
                    this.db.finish();
                    this.db.onError(AppnextError.INTERNAL_ERROR);
                    C0266f.m205c(th);
                    this.db.m403a(this.db.placementID, C0266f.m202b(th), "InterstitialActivity_error");
                }
            } else if (((ArrayList) t).size() == 0) {
                Log.d("appnext SDK", "IA GAL 2");
                this.db.finish();
                this.db.onError(AppnextError.NO_ADS);
            } else {
                ArrayList b = C0923a.aq().m415b(this.db, this.db.cS, this.db.cT);
                if (b == null) {
                    Log.d("appnext SDK", "IA GAL 3");
                    this.db.finish();
                    this.db.onError(AppnextError.NO_ADS);
                    return;
                }
                String c = C0923a.aq().mo1232c(b);
                if (c == null) {
                    Log.d("appnext SDK", "IA GAL 4");
                    this.db.finish();
                    this.db.onError(AppnextError.NO_ADS);
                    return;
                }
                Object buttonText;
                String replace = c.replace(" ", "\\u2028").replace(" ", "\\u2029");
                JSONObject jSONObject = new JSONObject();
                if (this.db.hasVideo((AppnextAd) b.get(0))) {
                    jSONObject.put("remote_auto_play", "" + this.db.autoPlay);
                } else {
                    jSONObject.put("remote_auto_play", "false");
                }
                if (this.db.cS.getButtonText().equals("")) {
                    InterstitialAd interstitialAd = new InterstitialAd((AppnextAd) b.get(0));
                    if (!interstitialAd.getButtonText().equals("")) {
                        buttonText = interstitialAd.getButtonText();
                    } else if (C0266f.m206c(this.db, interstitialAd.getAdPackage())) {
                        buttonText = this.db.m362H("existing_button_text");
                    } else {
                        buttonText = this.db.m362H("new_button_text");
                    }
                } else {
                    buttonText = this.db.cS.getButtonText();
                }
                jSONObject.put("b_title", buttonText);
                this.db.cd = (AppnextAd) b.get(0);
                this.db.m373a("Appnext.setParams(" + jSONObject.toString() + ");");
                this.db.m373a("Appnext.loadInterstitial(" + replace + ");");
                C0923a.aq().mo1210a(this.db.cd.getBannerID(), this.db.placementID);
                if (Interstitial.currentAd.getOnAdOpenedCallback() != null) {
                    Interstitial.currentAd.getOnAdOpenedCallback().adOpened();
                }
            }
        }

        public void error(final String str) {
            this.db.runOnUiThread(new Runnable(this) {
                final /* synthetic */ C09229 df;

                public void run() {
                    C0266f.m194P("ads error");
                    this.df.db.m403a(this.df.db.placementID, str, "ads error");
                    this.df.db.onError(str);
                    this.df.db.finish();
                }
            });
        }
    }

    protected class WebInterface extends WebAppInterface {
        final /* synthetic */ InterstitialActivity db;

        class C01781 implements Runnable {
            final /* synthetic */ WebInterface dg;

            C01781(WebInterface webInterface) {
                this.dg = webInterface;
            }

            public void run() {
                this.dg.db.onClose();
                this.dg.db.finish();
            }
        }

        class C01792 implements Runnable {
            final /* synthetic */ WebInterface dg;

            C01792(WebInterface webInterface) {
                this.dg = webInterface;
            }

            public void run() {
                this.dg.db.onError(AdsError.AD_NOT_READY);
                this.dg.db.finish();
            }
        }

        public WebInterface(InterstitialActivity interstitialActivity) {
            this.db = interstitialActivity;
            super(interstitialActivity);
        }

        @JavascriptInterface
        public void destroy(String str) {
            if (str.equals(PreviewActivity.ON_CLICK_LISTENER_CLOSE)) {
                this.db.runOnUiThread(new C01781(this));
            } else {
                this.db.runOnUiThread(new C01792(this));
            }
        }

        @JavascriptInterface
        public void notifyImpression(String str) {
            super.notifyImpression(str);
            this.db.handler.postDelayed(this.db.cn, Long.parseLong(this.db.getConfig().get("postpone_impression_sec")) * 1000);
        }

        @JavascriptInterface
        public void postView(String str) {
            if (Boolean.parseBoolean(this.db.f57D != null ? "false" : this.db.m362H("pview"))) {
                this.db.handler.postDelayed(this.db.co, Long.parseLong(this.db.getConfig().get("postpone_vta_sec")) * 1000);
            }
        }

        @JavascriptInterface
        public void openStore(final String str) {
            this.db.runOnUiThread(new Runnable(this) {
                final /* synthetic */ WebInterface dg;

                public void run() {
                    this.dg.db.m364I(str);
                }
            });
        }

        @JavascriptInterface
        public void play() {
            this.db.m403a(this.db.placementID, "", C0150b.bs);
            this.db.play();
        }

        @JavascriptInterface
        public void videoPlayed() {
        }

        @JavascriptInterface
        public String filterAds(String str) {
            return str;
        }

        @JavascriptInterface
        public String loadAds() {
            return "";
        }

        @JavascriptInterface
        public void openLink(String str) {
            this.db.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        }

        @JavascriptInterface
        public void gotoAppWall() {
        }

        @JavascriptInterface
        public void jsError(String str) {
            this.db.onError(AppnextError.INTERNAL_ERROR);
            this.db.finish();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onCreate(android.os.Bundle r8) {
        /*
        r7 = this;
        r6 = 7;
        r5 = 6;
        r3 = 2;
        r1 = -1;
        r2 = 1;
        r0 = r7.getIntent();
        r4 = "product";
        r0 = r0.getStringExtra(r4);
        r7.cU = r0;
        r0 = r7.cU;
        if (r0 != 0) goto L_0x0019;
    L_0x0015:
        r0 = "interstitial";
        r7.cU = r0;
    L_0x0019:
        r0 = com.appnext.ads.interstitial.Interstitial.currentAd;
        if (r0 == 0) goto L_0x003a;
    L_0x001d:
        r0 = r7.getIntent();
        r4 = "orientation";
        r0 = r0.getBooleanExtra(r4, r2);
        if (r0 == 0) goto L_0x0089;
    L_0x0029:
        r0 = com.appnext.ads.interstitial.Interstitial.currentAd;
        r0 = r0.getOrientation();
        r4 = r0.hashCode();
        switch(r4) {
            case 729267099: goto L_0x0063;
            case 1430647483: goto L_0x0059;
            case 1673671211: goto L_0x004f;
            case 2129065206: goto L_0x0045;
            default: goto L_0x0036;
        };
    L_0x0036:
        r0 = r1;
    L_0x0037:
        switch(r0) {
            case 0: goto L_0x006d;
            case 1: goto L_0x006d;
            case 2: goto L_0x0081;
            case 3: goto L_0x0085;
            default: goto L_0x003a;
        };
    L_0x003a:
        super.onCreate(r8);
        r0 = com.appnext.ads.interstitial.Interstitial.currentAd;
        if (r0 != 0) goto L_0x00fb;
    L_0x0041:
        r7.finish();
    L_0x0044:
        return;
    L_0x0045:
        r4 = "not_set";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x0036;
    L_0x004d:
        r0 = 0;
        goto L_0x0037;
    L_0x004f:
        r4 = "automatic";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x0036;
    L_0x0057:
        r0 = r2;
        goto L_0x0037;
    L_0x0059:
        r4 = "landscape";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x0036;
    L_0x0061:
        r0 = r3;
        goto L_0x0037;
    L_0x0063:
        r4 = "portrait";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x0036;
    L_0x006b:
        r0 = 3;
        goto L_0x0037;
    L_0x006d:
        r0 = r7.getResources();
        r0 = r0.getConfiguration();
        r0 = r0.orientation;
        if (r0 != r3) goto L_0x007d;
    L_0x0079:
        r7.setRequestedOrientation(r5);
        goto L_0x003a;
    L_0x007d:
        r7.setRequestedOrientation(r6);
        goto L_0x003a;
    L_0x0081:
        r7.setRequestedOrientation(r5);
        goto L_0x003a;
    L_0x0085:
        r7.setRequestedOrientation(r6);
        goto L_0x003a;
    L_0x0089:
        r0 = r7.cU;
        r4 = "fullscreen";
        r0 = r0.equals(r4);
        if (r0 != 0) goto L_0x009d;
    L_0x0093:
        r0 = r7.cU;
        r4 = "rewarded";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x003a;
    L_0x009d:
        r0 = r7.getIntent();
        r4 = "orientation_type";
        r0 = r0.getStringExtra(r4);
        r4 = r0.hashCode();
        switch(r4) {
            case 729267099: goto L_0x00e2;
            case 1430647483: goto L_0x00d8;
            case 1673671211: goto L_0x00ce;
            case 2129065206: goto L_0x00c4;
            default: goto L_0x00ae;
        };
    L_0x00ae:
        r0 = r1;
    L_0x00af:
        switch(r0) {
            case 0: goto L_0x00b3;
            case 1: goto L_0x00b3;
            case 2: goto L_0x00f1;
            case 3: goto L_0x00f6;
            default: goto L_0x00b2;
        };
    L_0x00b2:
        goto L_0x003a;
    L_0x00b3:
        r0 = r7.getResources();
        r0 = r0.getConfiguration();
        r0 = r0.orientation;
        if (r0 != r3) goto L_0x00ec;
    L_0x00bf:
        r7.setRequestedOrientation(r5);
        goto L_0x003a;
    L_0x00c4:
        r4 = "not_set";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x00ae;
    L_0x00cc:
        r0 = 0;
        goto L_0x00af;
    L_0x00ce:
        r4 = "automatic";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x00ae;
    L_0x00d6:
        r0 = r2;
        goto L_0x00af;
    L_0x00d8:
        r4 = "landscape";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x00ae;
    L_0x00e0:
        r0 = r3;
        goto L_0x00af;
    L_0x00e2:
        r4 = "portrait";
        r0 = r0.equals(r4);
        if (r0 == 0) goto L_0x00ae;
    L_0x00ea:
        r0 = 3;
        goto L_0x00af;
    L_0x00ec:
        r7.setRequestedOrientation(r6);
        goto L_0x003a;
    L_0x00f1:
        r7.setRequestedOrientation(r5);
        goto L_0x003a;
    L_0x00f6:
        r7.setRequestedOrientation(r6);
        goto L_0x003a;
    L_0x00fb:
        r0 = com.appnext.ads.interstitial.Interstitial.currentAd;
        r7.cS = r0;
        r0 = new android.widget.RelativeLayout;
        r0.<init>(r7);
        r7.gS = r0;
        r0 = r7.gS;
        r7.setContentView(r0);
        r0 = r7.gS;
        r0 = r0.getLayoutParams();
        r0.width = r1;
        r0 = r7.gS;
        r0 = r0.getLayoutParams();
        r0.height = r1;
        r0 = r7.gS;
        r1 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r0.setBackgroundColor(r1);
        r0 = r7.getIntent();
        r0 = r0.getExtras();
        r1 = "id";
        r0 = r0.getString(r1);
        r7.placementID = r0;
        r0 = r7.getIntent();
        r1 = "auto_play";
        r0 = r0.hasExtra(r1);
        if (r0 == 0) goto L_0x014e;
    L_0x013e:
        r0 = r7.getIntent();
        r1 = "auto_play";
        r0 = r0.getBooleanExtra(r1, r2);
        r0 = java.lang.Boolean.valueOf(r0);
        r7.autoPlay = r0;
    L_0x014e:
        r0 = r7.getIntent();
        r1 = "can_close";
        r0 = r0.hasExtra(r1);
        if (r0 == 0) goto L_0x016a;
    L_0x015a:
        r0 = r7.getIntent();
        r1 = "can_close";
        r0 = r0.getBooleanExtra(r1, r2);
        r0 = java.lang.Boolean.valueOf(r0);
        r7.canClose = r0;
    L_0x016a:
        r0 = r7.getIntent();
        r1 = "mute";
        r0 = r0.hasExtra(r1);
        if (r0 == 0) goto L_0x0186;
    L_0x0176:
        r0 = r7.getIntent();
        r1 = "mute";
        r0 = r0.getBooleanExtra(r1, r2);
        r0 = java.lang.Boolean.valueOf(r0);
        r7.mute = r0;
    L_0x0186:
        r0 = r7.getIntent();
        r1 = "pview";
        r0 = r0.hasExtra(r1);
        if (r0 == 0) goto L_0x01b6;
    L_0x0192:
        r0 = r7.getIntent();
        r1 = "pview";
        r0 = r0.getStringExtra(r1);
        r7.gQ = r0;
        r0 = r7.getIntent();
        r1 = "banner";
        r0 = r0.getStringExtra(r1);
        r7.dr = r0;
        r0 = r7.getIntent();
        r1 = "guid";
        r0 = r0.getStringExtra(r1);
        r7.guid = r0;
    L_0x01b6:
        r0 = r7.getIntent();
        r1 = "ads";
        r0 = r0.getSerializableExtra(r1);
        r0 = (java.util.ArrayList) r0;
        r7.f57D = r0;
        r0 = r7.cU;
        r1 = "interstitial";
        r0 = r0.equals(r1);
        if (r0 != 0) goto L_0x01d8;
    L_0x01ce:
        r0 = r7.cU;
        r1 = "widget";
        r0 = r0.equals(r1);
        if (r0 == 0) goto L_0x01e1;
    L_0x01d8:
        r0 = r7.placementID;
        r1 = "";
        r2 = "show_request";
        r7.m403a(r0, r1, r2);
    L_0x01e1:
        r0 = new android.os.Handler;
        r0.<init>();
        r7.cX = r0;
        r0 = com.appnext.core.webview.AppnextWebView.m241B(r7);
        r1 = r7.cS;
        r1 = r1.getPageUrl();
        r2 = new com.appnext.ads.interstitial.InterstitialActivity$1;
        r2.<init>(r7);
        r0.m251a(r1, r2);
        r0 = new com.appnext.ads.interstitial.InterstitialActivity$2;
        r0.<init>(r7);
        r7.cY = r0;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appnext.ads.interstitial.InterstitialActivity.onCreate(android.os.Bundle):void");
    }

    private void al() {
        try {
            this.cQ = AppnextWebView.m241B(this).m249a(this, this.cS.getPageUrl(), an(), this.cS.getFallback(), this.f57D != null ? "fullscreen" : "interstitial");
            this.cQ.setWebViewClient(new C01713(this));
            this.cX.postDelayed(this.da, 500);
            this.cQ.setWebChromeClient(new C01724(this));
        } catch (Throwable th) {
            C0266f.m205c(th);
            onError(AppnextError.INTERNAL_ERROR);
            finish();
        }
    }

    private void am() {
        this.cR = true;
        try {
            m373a("Appnext.setParams(" + ap().toString() + ");");
        } catch (Throwable th) {
            finish();
            onError(AppnextError.INTERNAL_ERROR);
            C0266f.m205c(th);
            m403a(this.placementID, C0266f.m202b(th), "InterstitialActivity_error");
        }
        ao();
        if (this.cQ == null) {
            onError(AppnextError.INTERNAL_ERROR);
            finish();
            return;
        }
        if (this.cQ.getParent() != null) {
            ((ViewGroup) this.cQ.getParent()).removeView(this.cQ);
        }
        this.gS.addView(this.cQ);
        this.cQ.getLayoutParams().width = -1;
        this.cQ.getLayoutParams().height = -1;
    }

    private String m362H(String str) {
        String str2 = getConfig().get(str);
        return str2 == null ? "" : str2;
    }

    protected C0280o getConfig() {
        return C0925c.as();
    }

    protected void onResume() {
        super.onResume();
        if (this.cR && this.autoPlay.booleanValue()) {
            play();
        }
    }

    protected void onPause() {
        super.onPause();
        if (!this.cV) {
            stop();
        }
        if (this.handler != null) {
            this.handler.removeCallbacks(this.cn);
            this.handler.removeCallbacks(this.co);
        }
    }

    public void onBackPressed() {
        if (this.canClose != null) {
            if (!this.canClose.booleanValue()) {
                return;
            }
        } else if (!Boolean.parseBoolean(m362H("can_close"))) {
            return;
        }
        m373a("Appnext.Layout.destroy('internal');");
        onClose();
        if (this.cU.equals("fullscreen") || this.cU.equals("rewarded")) {
            m403a(this.placementID, "", C0150b.bG);
        } else {
            m403a(this.placementID, "", C0150b.bE);
        }
        this.cV = true;
        super.onBackPressed();
    }

    protected void onError(String str) {
        if (this.cS != null && this.cS.getOnAdErrorCallback() != null) {
            this.cS.getOnAdErrorCallback().adError(str);
        }
    }

    private void m364I(String str) {
        AppnextAd appnextAd = (AppnextAd) C0923a.aq().m161c(str);
        if (appnextAd != null) {
            this.ce = appnextAd;
            if (this.cS.getOnAdClickedCallback() != null) {
                this.cS.getOnAdClickedCallback().adClicked();
            }
            mo1192b(appnextAd, this.cY);
            if (!appnextAd.getBannerID().equals(this.cd != null ? this.cd.getBannerID() : "")) {
                m403a(this.placementID, "", C0150b.bu);
            } else if (this.cU.equals("interstitial") || this.cU.equals("widget")) {
                m403a(this.placementID, "", C0150b.bt);
            } else {
                m403a(this.placementID, "", C0150b.bD);
            }
        }
    }

    protected void mo1192b(AppnextAd appnextAd, C0258a c0258a) {
        m132a(this.gS, Base64.decode(C0149a.m9C("loader"), 0));
        super.mo1192b(appnextAd, c0258a);
    }

    protected WebAppInterface an() {
        if (this.cZ == null) {
            this.cZ = new WebInterface(this);
        }
        return this.cZ;
    }

    private void play() {
        m373a("Appnext.Layout.Video.play();");
    }

    private void stop() {
        if (this.cQ != null) {
            C0266f.m194P("stop");
            this.cQ.loadUrl("javascript:(function() { Appnext.Layout.Video.pause();})()");
        }
    }

    private void onClose() {
        try {
            if (this.cS != null && this.cS.getOnAdClosedCallback() != null) {
                this.cS.getOnAdClosedCallback().onAdClosed();
            }
        } catch (Throwable th) {
        }
    }

    protected void m403a(String str, String str2, String str3) {
        if (this.cS != null) {
            C0266f.m201a(this.cS.getTID(), this.cS.getVID(), this.cS.getAUID(), str, str2, str3, this.cU, this.cd != null ? this.cd.getBannerID() : "", this.cd != null ? this.cd.getCampaignID() : "");
        }
    }

    private void m373a(final String str) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ InterstitialActivity db;

            public void run() {
                if (this.db.cQ != null) {
                    C0266f.m194P(str);
                    this.db.cQ.loadUrl("javascript:(function() { try { " + str + "} catch(err){ Appnext.jsError(err.message); }})()");
                }
            }
        });
    }

    protected synchronized void ao() {
        if (this.f57D != null) {
            String str = "Appnext.loadRewarded(" + C0923a.aq().mo1232c(this.f57D) + ");";
            C0266f.m194P(str);
            m373a(str);
            this.cd = (AppnextAd) this.f57D.get(0);
        } else {
            C0923a.aq().m152a((Context) this, this.cS, this.placementID, new C09229(this));
        }
    }

    protected JSONObject ap() throws JSONException {
        Object H;
        if (this.cS.getButtonColor().equals("")) {
            H = m362H("button_color");
        } else {
            H = this.cS.getButtonColor();
        }
        if (H.startsWith("#")) {
            H = H.substring(1);
        }
        if (this.autoPlay == null) {
            this.autoPlay = Boolean.valueOf(Boolean.parseBoolean(m362H("auto_play")));
        }
        if (this.mute == null) {
            this.mute = Boolean.valueOf(Boolean.parseBoolean(m362H("mute")));
        }
        this.cT = getIntent().getExtras().getString("creative");
        if (this.cT == null || this.cT.equals("managed")) {
            this.cT = m362H("creative");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.placementID);
        jSONObject.put("cat", this.cS.getCategories());
        jSONObject.put("pbk", this.cS.getPostback());
        jSONObject.put("b_title", this.cS.getButtonText());
        jSONObject.put("b_color", H);
        if (this.f57D == null) {
            jSONObject.put("skip_title", this.cS.getSkipText().equals("") ? m362H("skip_title") : this.cS.getSkipText());
            jSONObject.put("pview", this.f57D != null ? "false" : m362H("pview"));
            jSONObject.put("video_length", m362H("video_length"));
            jSONObject.put("min_internet_connection", m362H("min_internet_connection"));
            jSONObject.put("min_internet_connection_video", m362H("min_internet_connection_video"));
            jSONObject.put("mute", "" + this.mute);
            jSONObject.put("auto_play", "" + this.autoPlay);
            jSONObject.put("remove_poster_on_auto_play", m362H("remove_poster_on_auto_play"));
            jSONObject.put("show_rating", m362H("show_rating"));
            jSONObject.put("show_desc", m362H("show_desc"));
            jSONObject.put("creative", this.cT);
        }
        jSONObject.put("ext", "t");
        jSONObject.put("dct", C0266f.m210z(this));
        jSONObject.put("did", C0266f.m207w(this));
        jSONObject.put("devn", C0266f.cf());
        jSONObject.put("dosv", VERSION.SDK_INT);
        jSONObject.put("dds", "0");
        jSONObject.put("urlApp_protection", m362H("urlApp_protection"));
        jSONObject.put("vid", this.cS.getVID());
        jSONObject.put("tid", this.cS.getTID());
        jSONObject.put("auid", this.cS.getAUID());
        jSONObject.put("osid", "100");
        jSONObject.put("ads_type", this.cU);
        if (getIntent() != null && getIntent().hasExtra("show_desc")) {
            jSONObject.put("show_desc", getIntent().getStringExtra("show_desc"));
        }
        return jSONObject;
    }

    private boolean hasVideo(AppnextAd appnextAd) {
        return (appnextAd.getVideoUrl().equals("") && appnextAd.getVideoUrlHigh().equals("") && appnextAd.getVideoUrl30Sec().equals("") && appnextAd.getVideoUrlHigh30Sec().equals("")) ? false : true;
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            this.cS = null;
            if (this.cX != null) {
                this.cX.removeCallbacksAndMessages(null);
            }
            this.cX = null;
            this.da = null;
            this.cd = null;
            if (this.cQ != null) {
                if (this.cQ.getParent() != null) {
                    ((ViewGroup) this.cQ.getParent()).removeView(this.cQ);
                }
                this.cQ.setWebChromeClient(null);
                this.cQ.setWebViewClient(null);
                this.cQ = null;
            }
            AppnextWebView.m241B(this).m250a(an());
            this.cZ = null;
            this.cY = null;
            if (this.cl != null) {
                this.cl.mo1184b(this);
                this.cl = null;
            }
        } catch (Throwable th) {
        }
    }
}
