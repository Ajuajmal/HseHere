package com.appnext.appnextsdk.API;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import com.appnext.ads.C0150b;
import com.appnext.core.Ad;
import com.appnext.core.AdsService;
import com.appnext.core.AdsService.C0230a;
import com.appnext.core.AppnextAd;
import com.appnext.core.AppnextError;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0262d;
import com.appnext.core.C0262d.C0258a;
import com.appnext.core.C0266f;
import com.appnext.core.C0280o.C0278a;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.plus.PlusShare;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

@SuppressLint({"SetJavaScriptEnabled", "NewApi"})
public class AppnextAPI {
    public static final String TYPE_MANAGED = "managed";
    public static final String TYPE_STATIC = "static";
    public static final String TYPE_VIDEO = "video";
    private static HashMap<String, C0183a> pv = new HashMap();
    private Ad ad;
    private volatile ArrayList<AppnextAd> adList;
    private AppnextAdListener adListener;
    private OnAdOpened adOpenedListener;
    private C0262d click;
    private AppnextAd clickedAd = null;
    private Context context;
    private String creativeType = "managed";
    private AdsService mBoundService;
    private ServiceConnection mConnection = new C01811(this);
    private DownloadReceiver mDownloadReceiver;
    private int pvLoadingState = 0;
    private final ArrayList<AppnextAd> pviewList = new ArrayList();

    class C01811 implements ServiceConnection {
        final /* synthetic */ AppnextAPI dj;

        C01811(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.dj.mBoundService = ((C0230a) iBinder).bW();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.dj.mBoundService = null;
        }
    }

    public interface AppnextAdListener {
        void onAdsLoaded(ArrayList<AppnextAd> arrayList);

        void onError(String str);
    }

    @SuppressLint({"ParcelCreator"})
    private class DownloadReceiver extends ResultReceiver {
        final /* synthetic */ AppnextAPI dj;

        public DownloadReceiver(AppnextAPI appnextAPI, Handler handler) {
            this.dj = appnextAPI;
            super(handler);
        }

        protected void onReceiveResult(int i, Bundle bundle) {
            super.onReceiveResult(i, bundle);
            if (i == AdsService.START_APP) {
                try {
                    this.dj.serverNotify(bundle.getString("guid"), bundle.getString("bannerid"), bundle.getString("placementid"), this.dj.ad.getVID());
                } catch (Throwable th) {
                    C0266f.m205c(th);
                }
            }
        }
    }

    public interface OnAdOpened {
        void onError(String str);

        void storeOpened();
    }

    private class C0183a {
        final /* synthetic */ AppnextAPI dj;
        String dm;
        String guid;

        private C0183a(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
            this.dm = "";
            this.guid = "";
        }
    }

    protected class C0184b extends AsyncTask<String, Void, Void> {
        final /* synthetic */ AppnextAPI dj;

        protected C0184b(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m15c((String[]) objArr);
        }

        protected Void m15c(String... strArr) {
            HashMap hashMap = new HashMap();
            hashMap.put("guid", strArr[0]);
            hashMap.put("zone", "");
            hashMap.put("adsID", this.dj.getAdsID());
            hashMap.put("isApk", "0");
            hashMap.put("bannerid", strArr[1]);
            hashMap.put("placementid", strArr[2]);
            hashMap.put("vid", strArr[3]);
            try {
                C0266f.m194P("set open " + C0266f.m196a("https://admin.appnext.com/AdminService.asmx/SetOpenV1", hashMap));
            } catch (Throwable th) {
                C0266f.m205c(th);
            }
            return null;
        }
    }

    private class C0185c extends AsyncTask<String, Void, Void> {
        final /* synthetic */ AppnextAPI dj;

        private C0185c(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m16c((String[]) objArr);
        }

        protected Void m16c(String... strArr) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("guid", strArr[0]);
                hashMap.put("bannerId", strArr[1]);
                hashMap.put("placementId", strArr[2]);
                hashMap.put("vid", this.dj.ad.getTID());
                hashMap.put(PlusShare.KEY_CALL_TO_ACTION_URL, strArr[3]);
                C0266f.m196a("https://admin.appnext.com/AdminService.asmx/" + strArr[4], hashMap);
            } catch (Throwable th) {
            }
            return null;
        }
    }

    class C09262 implements C0278a {
        final /* synthetic */ AppnextAPI dj;

        C09262(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
        }

        public void mo1197b(HashMap<String, String> hashMap) {
            C0187a.at().m22d(Integer.parseInt(C0931c.ay().get("capRange")));
        }

        public void error(String str) {
            C0187a.at().m22d(Integer.parseInt(C0931c.ay().get("capRange")));
        }
    }

    class C09296 implements C0258a {
        final /* synthetic */ AppnextAPI dj;

        C09296(AppnextAPI appnextAPI) {
            this.dj = appnextAPI;
        }

        public void onMarket(String str) {
            if (this.dj.pviewList.size() != 0) {
                C0266f.m194P("pview loaded " + ((AppnextAd) this.dj.pviewList.get(0)).getAdTitle());
                C0183a c0183a = new C0183a();
                c0183a.guid = C0266f.getCookie("admin.appnext.com", "applink");
                c0183a.dm = str;
                AppnextAPI.pv.put(((AppnextAd) this.dj.pviewList.get(0)).getAppURL(), c0183a);
                this.dj.pvLoadingState = 0;
                this.dj.pviewList.remove(0);
                this.dj.postview(null);
            }
        }

        public void error(String str) {
            if (this.dj.pviewList.size() != 0) {
                C0266f.m194P("pview loading error " + ((AppnextAd) this.dj.pviewList.get(0)).getAdTitle());
                this.dj.pvLoadingState = 0;
                this.dj.pviewList.remove(0);
                this.dj.postview(null);
            }
        }
    }

    static {
        CookieHandler.setDefault(new CookieManager());
    }

    public AppnextAPI(Context context, String str) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        } else if (str.contains("<") || str.contains(">")) {
            throw new IllegalArgumentException("Check your placementID.");
        } else {
            this.context = context.getApplicationContext();
            this.ad = createAd(this.context, str);
            this.click = C0262d.m183t(this.context);
            C0931c.ay().m234a(new C09262(this));
        }
    }

    protected void finalize() throws Throwable {
        try {
            finish();
        } catch (Throwable th) {
        }
        super.finalize();
    }

    protected Ad createAd(Context context, String str) {
        return new Native(context, str);
    }

    public void finish() {
        destroy();
    }

    public void destroy() {
        try {
            this.context.unbindService(this.mConnection);
            this.mBoundService.stopSelf();
            this.mConnection = null;
        } catch (Throwable th) {
        }
        try {
            this.context = null;
            this.pviewList.clear();
            this.click = null;
            this.mDownloadReceiver = null;
            this.clickedAd = null;
            this.adOpenedListener = null;
            this.adListener = null;
        } catch (Throwable th2) {
        }
    }

    @SuppressLint({"NewApi"})
    public void loadAds(final AppnextAdRequest appnextAdRequest) {
        if (this.adListener == null) {
            throw new IllegalArgumentException("You must set ad listener before loading an ad.");
        } else if (appnextAdRequest == null) {
            throw new IllegalArgumentException("AppnextAdRequest cannot be null.");
        } else {
            this.ad.setPostback(appnextAdRequest.getPostback());
            this.ad.setCategories(appnextAdRequest.getCategory());
            ((Native) this.ad).setCount(appnextAdRequest.getCount());
            C0930b.ax().m429a(this.context, this.ad, this.ad.getPlacementID(), new C0248a(this) {
                final /* synthetic */ AppnextAPI dj;

                public <T> void mo1187a(T t) {
                    ArrayList arrayList = (ArrayList) t;
                    this.dj.adList = new ArrayList();
                    if (arrayList != null) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            AppnextAd appnextAd = (AppnextAd) it.next();
                            AppnextAd appnextAd2 = new AppnextAd(appnextAd);
                            String creativeType = this.dj.getCreativeType();
                            Object obj = -1;
                            switch (creativeType.hashCode()) {
                                case -892481938:
                                    if (creativeType.equals("static")) {
                                        obj = null;
                                        break;
                                    }
                                    break;
                                case 112202875:
                                    if (creativeType.equals("video")) {
                                        obj = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (obj) {
                                case null:
                                    if (!appnextAd.getWideImageURL().equals("")) {
                                        this.dj.adList.add(appnextAd2);
                                        break;
                                    }
                                    break;
                                case 1:
                                    if (this.dj.hasVideo(appnextAd2)) {
                                        this.dj.adList.add(appnextAd2);
                                        break;
                                    }
                                    break;
                                default:
                                    this.dj.adList.add(appnextAd2);
                                    break;
                            }
                            if (this.dj.adList.size() == appnextAdRequest.getCount()) {
                                if (this.dj.adListener == null) {
                                }
                                if (this.dj.adList.size() <= 0) {
                                    this.dj.adListener.onAdsLoaded(this.dj.adList);
                                } else {
                                    this.dj.adListener.onError(AppnextError.NO_ADS);
                                }
                            }
                        }
                        if (this.dj.adListener == null) {
                            if (this.dj.adList.size() <= 0) {
                                this.dj.adListener.onError(AppnextError.NO_ADS);
                            } else {
                                this.dj.adListener.onAdsLoaded(this.dj.adList);
                            }
                        }
                    } else if (this.dj.adListener != null) {
                        this.dj.adListener.onError(AppnextError.NO_ADS);
                    }
                }

                public void error(String str) {
                    if (this.dj.adListener != null) {
                        this.dj.adListener.onError(str);
                    }
                }
            }, appnextAdRequest);
        }
    }

    public void setAdListener(AppnextAdListener appnextAdListener) {
        this.adListener = appnextAdListener;
    }

    public void setOnAdOpenedListener(OnAdOpened onAdOpened) {
        this.adOpenedListener = onAdOpened;
    }

    private void openMarket(String str) {
        openMarket(str, null);
    }

    private void openMarket(String str, String str2) {
        String str3;
        Intent intent;
        if (!C0266f.m206c(this.context, this.clickedAd.getAdPackage())) {
            try {
                if (!(this.clickedAd == null || str.contains(this.clickedAd.getAdPackage()))) {
                    new C0185c().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{"", this.clickedAd.getBannerID(), this.clickedAd.getPlacementID(), str, "SetROpenV1"});
                }
            } catch (Throwable th) {
            }
            if (this.context != null) {
                intent = new Intent(this.context, AdsService.class);
                intent.putExtra("package", this.clickedAd.getAdPackage());
                if (str2 == null) {
                    intent.putExtra("guid", C0266f.getCookie("admin.appnext.com", "applink"));
                } else {
                    intent.putExtra("guid", str2);
                }
                intent.putExtra("bannerid", this.clickedAd.getBannerID());
                intent.putExtra("placementid", this.clickedAd.getPlacementID());
                intent.putExtra("zone", this.clickedAd.getZoneID());
                intent.putExtra("added_info", AdsService.ADD_PACK);
                if (this.mDownloadReceiver == null) {
                    this.mDownloadReceiver = new DownloadReceiver(this, null);
                }
                intent.putExtra("receiver", this.mDownloadReceiver);
                intent.putExtra("vid", this.ad.getVID());
                intent.putExtra("tid", this.ad.getTID());
                intent.putExtra("auid", this.ad.getAUID());
                this.context.bindService(intent, this.mConnection, 1);
                this.context.startService(intent);
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                try {
                    this.context.startActivity(intent);
                    if (this.adOpenedListener != null) {
                        this.adOpenedListener.storeOpened();
                    }
                } catch (Throwable th2) {
                    if (this.adOpenedListener != null) {
                        this.adOpenedListener.onError(AppnextError.NO_MARKET);
                    }
                }
            } else if (this.adListener != null) {
                this.adListener.onError("context cannot be null");
            }
        } else if (str.startsWith("market://")) {
            try {
                intent = this.context.getPackageManager().getLaunchIntentForPackage(this.clickedAd.getAdPackage());
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                this.context.startActivity(intent);
            } catch (Throwable th3) {
                str3 = str;
                C0266f.m201a(this.ad.getTID(), this.ad.getVID(), this.ad.getAUID(), this.ad.getPlacementID(), str3, "cannot_open_installed_app", "native_ads", this.clickedAd.getBannerID(), this.clickedAd.getCampaignID());
            }
        } else {
            intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            try {
                this.context.startActivity(intent);
            } catch (Throwable th4) {
                str3 = str;
                C0266f.m201a(this.ad.getTID(), this.ad.getVID(), this.ad.getAUID(), this.ad.getPlacementID(), str3, "cannot_open_deeplink", "native_ads", this.clickedAd.getBannerID(), this.clickedAd.getCampaignID());
            }
        }
    }

    public void privacyClicked(AppnextAd appnextAd) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.appnext.com/privacy_policy/index.html?z=" + new Random().nextInt(10) + appnextAd.getZoneID() + new Random().nextInt(10)));
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.context.startActivity(intent);
        } catch (Throwable th) {
        }
    }

    public void adClicked(final AppnextAd appnextAd) {
        if (appnextAd != null && this.context != null) {
            try {
                this.clickedAd = appnextAd;
                if (pv.containsKey(appnextAd.getAppURL())) {
                    new Thread(new Runnable(this) {
                        final /* synthetic */ AppnextAPI dj;

                        public void run() {
                            try {
                                C0266f.m196a("https://admin.appnext.com/AdminService.asmx/SetRL?guid=" + ((C0183a) AppnextAPI.pv.get(appnextAd.getAppURL())).guid + "&bid=" + appnextAd.getBannerID() + "&zid=" + appnextAd.getZoneID() + "&pid=" + appnextAd.getPlacementID(), null);
                            } catch (Throwable th) {
                                C0266f.m205c(th);
                            }
                        }
                    }).start();
                    openMarket(((C0183a) pv.get(appnextAd.getAppURL())).dm, ((C0183a) pv.get(appnextAd.getAppURL())).guid);
                    pv.remove(appnextAd.getAppURL());
                    return;
                }
                this.click.m185a(appnextAd.getAppURL() + "&device=" + C0266f.cf(), appnextAd.getBannerID(), new C0258a(this) {
                    final /* synthetic */ AppnextAPI dj;

                    public void onMarket(String str) {
                        this.dj.openMarket(str);
                    }

                    public void error(String str) {
                        try {
                            new C0185c().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{C0266f.getCookie("admin.appnext.com", "applink"), this.dj.clickedAd.getBannerID(), appnextAd.getPlacementID(), str, "SetDOpenV1"});
                        } catch (Throwable th) {
                        }
                        Intent intent;
                        if (Boolean.parseBoolean(C0931c.ay().get("urlApp_protection"))) {
                            intent = new Intent("android.intent.action.VIEW");
                            intent.setData(Uri.parse("market://details?id=" + this.dj.clickedAd.getAdPackage()));
                            intent.addFlags(DriveFile.MODE_READ_ONLY);
                            try {
                                this.dj.context.startActivity(intent);
                                if (this.dj.adOpenedListener != null) {
                                    this.dj.adOpenedListener.storeOpened();
                                }
                            } catch (Throwable th2) {
                                if (this.dj.adOpenedListener != null) {
                                    this.dj.adOpenedListener.onError(AppnextError.NO_MARKET);
                                }
                            }
                        } else if (str != null) {
                            intent = new Intent("android.intent.action.VIEW");
                            intent.setData(Uri.parse(str));
                            intent.addFlags(DriveFile.MODE_READ_ONLY);
                            try {
                                this.dj.context.startActivity(intent);
                                if (this.dj.adOpenedListener != null) {
                                    this.dj.adOpenedListener.storeOpened();
                                }
                            } catch (Throwable th3) {
                                if (this.dj.adOpenedListener != null) {
                                    this.dj.adOpenedListener.onError(AppnextError.NO_MARKET);
                                }
                            }
                        } else if (this.dj.adOpenedListener != null) {
                            this.dj.adOpenedListener.onError(AppnextError.INTERNAL_ERROR);
                        }
                    }
                }, Long.parseLong(C0931c.ay().get("resolve_timeout")) * 1000);
            } catch (Throwable th) {
            }
        }
    }

    public void adImpression(AppnextAd appnextAd) {
        try {
            this.click.m187e((AppnextAd) appnextAd);
            C0930b.ax().m426O(appnextAd.getBannerID());
            if (C0931c.ay().get("postview_location").equals("impression_pv")) {
                postview(appnextAd);
            }
        } catch (Throwable th) {
        }
    }

    protected void serverNotify(String str, String str2, String str3, String str4) {
        new C0184b(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{str, str2, str3, str4});
    }

    protected synchronized String getAdsID() {
        return C0266f.m207w(this.context);
    }

    private void postview(AppnextAd appnextAd) {
        if (!(appnextAd == null || pv.containsKey(appnextAd.getAppURL()))) {
            this.pviewList.add(appnextAd);
        }
        if (this.pviewList.size() >= 1 && this.pvLoadingState == 0) {
            this.pvLoadingState = 1;
            C0266f.m194P("pview load " + ((AppnextAd) this.pviewList.get(0)).getAdTitle());
            this.click.m184a(((AppnextAd) this.pviewList.get(0)).getAppURL() + "&device=" + C0266f.cf() + "&ox=0", ((AppnextAd) this.pviewList.get(0)).getBannerID(), new C09296(this));
        }
    }

    @Deprecated
    public void videoWatched(AppnextAd appnextAd) {
        videoStarted(appnextAd);
    }

    public void videoStarted(AppnextAd appnextAd) {
        try {
            C0266f.m201a(this.ad.getTID(), this.ad.getVID(), this.ad.getAUID(), appnextAd.getPlacementID(), "", C0150b.bs, "native_ads", appnextAd.getBannerID(), appnextAd.getCampaignID());
            if (C0931c.ay().get("postview_location").equals("play_video_pv")) {
                postview(appnextAd);
            }
        } catch (Throwable th) {
        }
    }

    public void videoEnded(AppnextAd appnextAd) {
        try {
            C0266f.m201a(this.ad.getTID(), this.ad.getVID(), this.ad.getAUID(), appnextAd.getPlacementID(), "", C0150b.bB, "native_ads", appnextAd.getBannerID(), appnextAd.getCampaignID());
        } catch (Throwable th) {
        }
    }

    public String getTID() {
        return this.ad.getTID();
    }

    public String getVID() {
        return this.ad.getVID();
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

    private boolean hasVideo(AppnextAd appnextAd) {
        return (appnextAd.getVideoUrl().equals("") && appnextAd.getVideoUrlHigh().equals("") && appnextAd.getVideoUrl30Sec().equals("") && appnextAd.getVideoUrlHigh30Sec().equals("")) ? false : true;
    }
}
