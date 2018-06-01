package com.appnext.core;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class AdsService extends IntentService {
    public static final int ADD_PACK = 8348;
    public static final int START_APP = 8346;
    private static HashMap<String, C0274m> gM;
    private Runnable gN = new C02291(this);
    private final IBinder gO = new C0230a(this);
    private Handler mHandler;

    class C02291 implements Runnable {
        final /* synthetic */ AdsService gP;

        C02291(AdsService adsService) {
            this.gP = adsService;
        }

        public void run() {
            try {
                for (Entry entry : AdsService.gM.entrySet()) {
                    C0274m c0274m = (C0274m) entry.getValue();
                    if (this.gP.av(c0274m.az)) {
                        new Bundle().putAll(c0274m.hU);
                        this.gP.m129a(c0274m);
                        AdsService.gM.remove(entry.getKey());
                        this.gP.startActivity(this.gP.getPackageManager().getLaunchIntentForPackage(c0274m.az));
                    }
                }
                if (this.gP.mHandler == null) {
                    this.gP.stopSelf();
                } else if (AdsService.gM.entrySet().size() > 0) {
                    this.gP.mHandler.postDelayed(this.gP.gN, 10000);
                } else {
                    this.gP.mHandler.removeCallbacksAndMessages(null);
                    this.gP.mHandler = null;
                    this.gP.stopSelf();
                }
            } catch (Throwable th) {
                this.gP.stopSelf();
            }
        }
    }

    public class C0230a extends Binder {
        final /* synthetic */ AdsService gP;

        public C0230a(AdsService adsService) {
            this.gP = adsService;
        }

        public AdsService bW() {
            return this.gP;
        }
    }

    public AdsService() {
        super("AdsService");
    }

    public void onStart(Intent intent, int i) {
        super.onStart(intent, i);
        gM = new HashMap();
    }

    public void onDestroy() {
        super.onDestroy();
        gM.clear();
        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
        this.mHandler = null;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent.getIntExtra("added_info", 0) == ADD_PACK) {
            addPack(intent.getStringExtra("package"), intent.getExtras(), (ResultReceiver) intent.getParcelableExtra("receiver"));
            C0266f.m194P("Package added: " + intent.getStringExtra("package"));
        }
    }

    public void addPack(String str, Bundle bundle, ResultReceiver resultReceiver) {
        if (gM == null) {
            gM = new HashMap();
        }
        if (gM.containsKey(str)) {
            m126a(str, bundle, resultReceiver);
            return;
        }
        C0274m c0274m = new C0274m();
        c0274m.az = str;
        c0274m.hU = bundle;
        gM.put(str, c0274m);
        if (this.mHandler == null) {
            this.mHandler = new Handler();
            this.mHandler.postDelayed(this.gN, 10000);
        }
    }

    private void m126a(String str, Bundle bundle, ResultReceiver resultReceiver) {
        C0274m c0274m = (C0274m) gM.get(str);
        if (c0274m == null) {
            addPack(str, bundle, resultReceiver);
            return;
        }
        c0274m.hU = bundle;
        gM.put(str, c0274m);
    }

    private boolean av(String str) {
        try {
            getPackageManager().getPackageInfo(str, 128);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    protected synchronized void m129a(C0274m c0274m) {
        HashMap hashMap = new HashMap();
        hashMap.put("guid", c0274m.hU.getString("guid"));
        hashMap.put("zone", c0274m.hU.getString("zone") == null ? "" : c0274m.hU.getString("zone"));
        hashMap.put("adsID", C0266f.m207w(this));
        hashMap.put("isApk", "0");
        hashMap.put("bannerid", c0274m.hU.getString("bannerid"));
        hashMap.put("placementid", c0274m.hU.getString("placementid"));
        hashMap.put("vid", c0274m.hU.getString("vid"));
        hashMap.put("tid", c0274m.hU.getString("tid", ""));
        hashMap.put("osid", "100");
        hashMap.put("auid", c0274m.hU.getString("auid", ""));
        Object installerPackageName = getPackageManager().getInstallerPackageName(c0274m.az);
        String str = "installer";
        if (installerPackageName == null) {
            installerPackageName = "";
        }
        hashMap.put(str, installerPackageName);
        try {
            C0266f.m196a("https://admin.appnext.com/AdminService.asmx/SetOpenV1", hashMap);
        } catch (IOException e) {
        }
    }

    public IBinder onBind(Intent intent) {
        return this.gO;
    }
}
