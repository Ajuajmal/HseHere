package com.appnext.base.p005b;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.util.Locale;

public class C0207f {
    public static final String TAG = C0207f.class.getSimpleName();
    static final /* synthetic */ boolean fX = (!C0207f.class.desiredAssertionStatus());

    public static String bD() {
        return VERSION.RELEASE;
    }

    public static String bE() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public static String m66d(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager.getSimState() == 5 && !telephonyManager.getSimOperatorName().isEmpty()) {
            return telephonyManager.getSimOperatorName();
        }
        if (telephonyManager.getNetworkOperatorName().isEmpty()) {
            return null;
        }
        return telephonyManager.getNetworkOperatorName();
    }

    public static long ak(String str) {
        if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE") || TextUtils.isEmpty(str)) {
            return -1;
        }
        if (VERSION.SDK_INT >= 18) {
            return C0207f.am(str);
        }
        return C0207f.al(str);
    }

    private static long al(String str) {
        long j = 0;
        if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            StatFs statFs;
            if ("isfs".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getPath());
                return (((long) statFs.getBlockSize()) * ((long) statFs.getFreeBlocks())) / C0204c.fB;
            } else if ("iss".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getPath());
                return (long) C0207f.m64b((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / C0204c.fB);
            } else if ("esfs".equalsIgnoreCase(str)) {
                if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    return 0;
                }
                statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                return (((long) statFs.getBlockSize()) * ((long) statFs.getFreeBlocks())) / C0204c.fB;
            } else if ("ess".equalsIgnoreCase(str)) {
                if (C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                    j = (((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / C0204c.fB;
                }
                return (long) C0207f.m64b(j);
            } else if ("isfp".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getPath());
                return Math.round((((double) ((((long) statFs.getFreeBlocks()) * ((long) statFs.getBlockSize())) / C0204c.fB)) / C0207f.m64b(((long) (statFs.getBlockCount() * statFs.getBlockSize())) / C0204c.fB)) * 100.0d);
            } else if (!"esfp".equalsIgnoreCase(str)) {
                return -1;
            } else {
                if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    return -1;
                }
                statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                return Math.round((((double) ((((long) statFs.getFreeBlocks()) * ((long) statFs.getBlockSize())) / C0204c.fB)) / C0207f.m64b((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / C0204c.fB)) * 100.0d);
            }
        } catch (Throwable th) {
            C0213k.m110j(TAG, th.toString());
            return -1;
        }
    }

    @RequiresApi(api = 18)
    private static long am(String str) {
        long j = 0;
        if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
            return -1;
        }
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        try {
            StatFs statFs;
            if ("isfs".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getPath());
                return (statFs.getBlockSizeLong() * statFs.getFreeBlocksLong()) / C0204c.fB;
            } else if ("iss".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
                return (long) C0207f.m64b((statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / C0204c.fB);
            } else if ("esfs".equalsIgnoreCase(str)) {
                if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    return 0;
                }
                statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                return (statFs.getBlockSizeLong() * statFs.getFreeBlocksLong()) / C0204c.fB;
            } else if ("ess".equalsIgnoreCase(str)) {
                if (C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                    j = (statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / C0204c.fB;
                }
                return (long) C0207f.m64b(j);
            } else if ("isfp".equalsIgnoreCase(str)) {
                statFs = new StatFs(Environment.getDataDirectory().getPath());
                r4 = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
                return Math.round((((double) r4) / C0207f.m64b(statFs.getBlockCountLong() * statFs.getBlockSizeLong())) * 100.0d);
            } else if (!"esfp".equalsIgnoreCase(str)) {
                return -1;
            } else {
                if (!C0207f.m65b(C0205d.getContext(), "android.permission.READ_EXTERNAL_STORAGE")) {
                    return -1;
                }
                statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                r4 = (statFs.getFreeBlocksLong() * statFs.getBlockSizeLong()) / C0204c.fB;
                return Math.round((((double) r4) / C0207f.m64b((statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / C0204c.fB)) * 100.0d);
            }
        } catch (Throwable th) {
            C0213k.m110j(TAG, th.toString());
            return -1;
        }
    }

    private static double m64b(long j) {
        int log = (int) (Math.log((double) j) / Math.log(2.0d));
        if (Math.pow(2.0d, (double) log) == ((double) j)) {
            return (double) j;
        }
        return Math.pow(2.0d, (double) (log + 1));
    }

    public static int m63a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        int streamVolume = "dvolr".equalsIgnoreCase(str) ? audioManager.getStreamVolume(2) : "dvolm".equalsIgnoreCase(str) ? audioManager.getStreamVolume(3) : "dvola".equalsIgnoreCase(str) ? audioManager.getStreamVolume(4) : "dvoln".equalsIgnoreCase(str) ? audioManager.getStreamVolume(5) : "dvolc".equalsIgnoreCase(str) ? audioManager.getStreamVolume(0) : 0;
        return streamVolume;
    }

    public static boolean m67e(Context context) throws SettingNotFoundException {
        return Secure.getInt(context.getContentResolver(), "install_non_market_apps") == 1;
    }

    public static NetworkInfo m68f(Context context) {
        return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
    }

    public static boolean m69g(Context context) {
        if (context == null) {
            return false;
        }
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (fX || registerReceiver != null) {
            int intExtra = registerReceiver.getIntExtra("plugged", -1);
            if (intExtra == 1 || intExtra == 2 || intExtra == 3) {
                return true;
            }
            return false;
        }
        throw new AssertionError();
    }

    public static boolean m65b(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str) || context.checkPermission(str, Process.myPid(), Process.myUid()) != 0) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m70h(android.content.Context r4) {
        /*
        r1 = "not connected";
        r0 = "android.permission.ACCESS_NETWORK_STATE";
        r0 = com.appnext.base.p005b.C0207f.m65b(r4, r0);
        if (r0 == 0) goto L_0x0054;
    L_0x000a:
        r0 = "android.permission.ACCESS_WIFI_STATE";
        r0 = com.appnext.base.p005b.C0207f.m65b(r4, r0);
        if (r0 == 0) goto L_0x0054;
    L_0x0012:
        r0 = r4.getApplicationContext();
        r2 = "wifi";
        r0 = r0.getSystemService(r2);
        r0 = (android.net.wifi.WifiManager) r0;
        r2 = 0;
        r0 = r0.getConnectionInfo();	 Catch:{ Throwable -> 0x0048 }
    L_0x0023:
        if (r0 == 0) goto L_0x0054;
    L_0x0025:
        r2 = r0.getSupplicantState();
        r3 = android.net.wifi.SupplicantState.COMPLETED;
        if (r2 != r3) goto L_0x0054;
    L_0x002d:
        r0 = r0.getSSID();
        r2 = android.text.TextUtils.isEmpty(r0);
        if (r2 != 0) goto L_0x0054;
    L_0x0037:
        r2 = "<unknown ssid>";
        r2 = r0.equalsIgnoreCase(r2);
        if (r2 != 0) goto L_0x0054;
    L_0x003f:
        r1 = "\"";
        r2 = "";
        r0 = r0.replace(r1, r2);
        return r0;
    L_0x0048:
        r0 = move-exception;
        r3 = TAG;
        r0 = r0.toString();
        com.appnext.base.p005b.C0213k.m107g(r3, r0);
        r0 = r2;
        goto L_0x0023;
    L_0x0054:
        r0 = r1;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appnext.base.b.f.h(android.content.Context):java.lang.String");
    }
}
