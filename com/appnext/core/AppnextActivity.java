package com.appnext.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.appnext.core.C0262d.C0258a;
import java.io.IOException;
import java.util.HashMap;

public abstract class AppnextActivity extends Activity {
    protected C0262d click;
    protected String dr = "";
    protected String gQ = "";
    private RelativeLayout gR;
    protected RelativeLayout gS;
    protected String guid = "";
    protected Handler handler;
    protected String placementID;

    class C02321 implements Runnable {
        final /* synthetic */ AppnextActivity gT;

        class C02311 implements Runnable {
            final /* synthetic */ C02321 gU;

            C02311(C02321 c02321) {
                this.gU = c02321;
            }

            public void run() {
                this.gU.gT.onError(AppnextError.CONNECTION_ERROR);
            }
        }

        C02321(AppnextActivity appnextActivity) {
            this.gT = appnextActivity;
        }

        public void run() {
            if (!this.gT.ca()) {
                this.gT.finish();
                this.gT.runOnUiThread(new C02311(this));
            }
        }
    }

    class C02384 implements OnClickListener {
        final /* synthetic */ AppnextActivity gT;

        C02384(AppnextActivity appnextActivity) {
            this.gT = appnextActivity;
        }

        public void onClick(View view) {
        }
    }

    class C02395 implements Runnable {
        final /* synthetic */ AppnextActivity gT;

        C02395(AppnextActivity appnextActivity) {
            this.gT = appnextActivity;
        }

        public void run() {
            this.gT.bZ();
        }
    }

    @SuppressLint({"ParcelCreator"})
    protected class DownloadReceiver extends ResultReceiver {
        final /* synthetic */ AppnextActivity gT;

        public DownloadReceiver(AppnextActivity appnextActivity, Handler handler) {
            this.gT = appnextActivity;
            super(handler);
        }

        protected void onReceiveResult(int i, Bundle bundle) {
            super.onReceiveResult(i, bundle);
            if (i == AdsService.START_APP) {
                try {
                    this.gT.m134a(bundle.getString("guid"), bundle.getString("bannerid"), bundle.getString("placementid"), bundle.getString("vid"), bundle.getString("package"));
                } catch (Throwable th) {
                    C0266f.m205c(th);
                }
            }
        }
    }

    protected class C0240a extends AsyncTask<String, Void, Void> {
        final /* synthetic */ AppnextActivity gT;

        protected C0240a(AppnextActivity appnextActivity) {
            this.gT = appnextActivity;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m130c((String[]) objArr);
        }

        protected Void m130c(String... strArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("guid", strArr[0]);
            hashMap.put("zone", "");
            hashMap.put("adsID", C0266f.m207w(this.gT));
            hashMap.put("isApk", "0");
            hashMap.put("bannerid", strArr[1]);
            hashMap.put("placementid", strArr[2]);
            hashMap.put("vid", strArr[3]);
            Object installerPackageName = this.gT.getPackageManager().getInstallerPackageName(strArr[4]);
            String str = "installer";
            if (installerPackageName == null) {
                installerPackageName = "";
            }
            hashMap.put(str, installerPackageName);
            try {
                C0266f.m196a("https://admin.appnext.com/AdminService.asmx/SetOpenV1", hashMap);
            } catch (Throwable th) {
            }
            return null;
        }
    }

    protected abstract C0280o getConfig();

    protected abstract void onError(String str);

    protected void onCreate(Bundle bundle) {
        new Thread(new C02321(this)).start();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        super.onCreate(bundle);
        this.click = C0262d.m183t(this);
        this.handler = new Handler();
    }

    @SuppressLint({"NewApi"})
    protected void bX() {
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() ^ 2;
        if (VERSION.SDK_INT >= 16) {
            systemUiVisibility ^= 4;
        }
        if (VERSION.SDK_INT >= 18) {
            systemUiVisibility ^= 4096;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    @SuppressLint({"NewApi"})
    protected void bY() {
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() | 2;
        if (VERSION.SDK_INT >= 16) {
            systemUiVisibility |= 4;
        }
        if (VERSION.SDK_INT >= 18) {
            systemUiVisibility |= 4096;
        }
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    protected static int m131a(Context context, float f) {
        return C0266f.m195a(context, f);
    }

    protected int m136e(float f) {
        return C0266f.m195a((Context) this, f);
    }

    protected void m134a(String str, String str2, String str3, String str4, String str5) {
        new C0240a(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{str, str2, str3, str4, str5});
    }

    protected void mo1191a(final AppnextAd appnextAd, final C0258a c0258a) {
        if (this.click != null) {
            final String str = appnextAd.getAppURL() + "&device=" + C0266f.cf() + "&ox=0";
            runOnUiThread(new Runnable(this) {
                final /* synthetic */ AppnextActivity gT;

                class C09501 implements C0258a {
                    final /* synthetic */ C02332 gX;

                    C09501(C02332 c02332) {
                        this.gX = c02332;
                    }

                    public void onMarket(String str) {
                        this.gX.gT.gQ = str;
                        this.gX.gT.guid = C0266f.getCookie("admin.appnext.com", "applink");
                        this.gX.gT.dr = appnextAd.getBannerID();
                        if (c0258a != null) {
                            c0258a.onMarket(str);
                        }
                    }

                    public void error(String str) {
                        this.gX.gT.gQ = "";
                        this.gX.gT.guid = "";
                        this.gX.gT.dr = "";
                        if (c0258a != null) {
                            c0258a.error(str);
                        }
                    }
                }

                public void run() {
                    this.gT.click.m184a(str, appnextAd.getBannerID(), new C09501(this));
                }
            });
        }
    }

    protected void mo1192b(final AppnextAd appnextAd, final C0258a c0258a) {
        new Thread(new Runnable(this) {
            final /* synthetic */ AppnextActivity gT;

            class C02341 implements Runnable {
                final /* synthetic */ C02373 gZ;

                C02341(C02373 c02373) {
                    this.gZ = c02373;
                }

                public void run() {
                    this.gZ.gT.bZ();
                    if (c0258a != null) {
                        c0258a.error(appnextAd.getAppURL() + "&device=" + C0266f.cf());
                    }
                }
            }

            class C02362 implements Runnable {
                final /* synthetic */ C02373 gZ;

                class C02351 implements Runnable {
                    final /* synthetic */ C02362 ha;

                    C02351(C02362 c02362) {
                        this.ha = c02362;
                    }

                    public void run() {
                        try {
                            C0266f.m196a("https://admin.appnext.com/AdminService.asmx/SetRL?guid=" + this.ha.gZ.gT.guid + "&bid=" + this.ha.gZ.gT.dr + "&pid=" + this.ha.gZ.gT.placementID, null);
                        } catch (Throwable th) {
                            C0266f.m205c(th);
                        }
                    }
                }

                C02362(C02373 c02373) {
                    this.gZ = c02373;
                }

                public void run() {
                    if (this.gZ.gT.gQ.equals("") || !this.gZ.gT.gQ.contains(appnextAd.getAdPackage())) {
                        C0266f.m194P("click url " + appnextAd.getAppURL());
                        this.gZ.gT.click.m185a(appnextAd.getAppURL() + "&device=" + C0266f.cf(), appnextAd.getBannerID(), c0258a, Long.parseLong(this.gZ.gT.getConfig().get("resolve_timeout")) * 1000);
                        return;
                    }
                    new Thread(new C02351(this)).start();
                    if (c0258a != null) {
                        c0258a.onMarket(this.gZ.gT.gQ);
                    }
                }
            }

            public void run() {
                if (!this.gT.ca()) {
                    this.gT.runOnUiThread(new C02341(this));
                } else if (appnextAd != null) {
                    this.gT.runOnUiThread(new C02362(this));
                }
            }
        }).start();
    }

    protected void m132a(ViewGroup viewGroup, byte[] bArr) {
        if (this.gR != null) {
            bZ();
        }
        this.gR = new RelativeLayout(this);
        this.gR.setBackgroundColor(Color.parseColor("#77ffffff"));
        viewGroup.addView(this.gR);
        this.gR.getLayoutParams().height = -1;
        this.gR.getLayoutParams().width = -1;
        View progressBar = new ProgressBar(this, null, 16842871);
        progressBar.setIndeterminateDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bArr, 0, bArr.length)));
        progressBar.setIndeterminate(true);
        this.gR.addView(progressBar);
        Animation rotateAnimation = new RotateAnimation(360.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(-1);
        progressBar.setAnimation(rotateAnimation);
        ((LayoutParams) progressBar.getLayoutParams()).addRule(13, -1);
        this.gR.setOnClickListener(new C02384(this));
        this.handler.postDelayed(new C02395(this), 8000);
    }

    protected void bZ() {
        if (this.gR != null) {
            this.gR.removeAllViews();
            this.gR.removeAllViewsInLayout();
            if (this.gR.getParent() != null) {
                ((RelativeLayout) this.gR.getParent()).removeView(this.gR);
            }
        }
        if (this.handler != null) {
            this.handler.removeCallbacks(null);
        }
        this.gR = null;
    }

    protected boolean ca() {
        try {
            if (checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
                C0266f.m196a("http://www.appnext.com/myid.html", null);
            } else {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    throw new IOException();
                }
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            this.click.destroy();
            this.click = null;
            this.handler.removeCallbacks(null);
            this.handler = null;
        } catch (Throwable th) {
        }
    }
}
