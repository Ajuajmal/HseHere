package com.appnext.base.p005b;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import com.appnext.base.C0198a;
import com.appnext.base.C0216b;
import com.appnext.base.operations.impl.cdm;
import com.appnext.base.operations.impl.rcd;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.services.OperationService;
import com.appnext.core.C0266f;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;
import org.apache.http.HttpStatus;
import org.json.JSONObject;

public class C0212j {
    private static final String TAG = "SdkHelper";
    public static final int gv = 120000;
    private static final long gw = 1000;
    private static final long gx = 60000;
    private static final long gy = 3600000;
    private static final long gz = 86400000;
    private static Random iF = new Random();

    public static List<ApplicationInfo> m91a(PackageManager packageManager, int i) {
        List<ApplicationInfo> arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(128)) {
            if (applicationInfo != null && (applicationInfo.flags & 1) == i) {
                arrayList.add(applicationInfo);
            }
        }
        return arrayList;
    }

    public static SortedMap<Double, String> m99j(Context context) {
        SortedMap<Double, String> treeMap = new TreeMap(Collections.reverseOrder());
        List l = C0212j.m101l(context);
        for (int i = 0; i < l.size(); i++) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo((String) l.get(i), 0);
                if (packageInfo != null) {
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    if (applicationInfo != null && (applicationInfo.flags & 1) == 0) {
                        treeMap.put(Double.valueOf((double) (i + 1)), applicationInfo.packageName);
                    }
                }
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
        return treeMap;
    }

    public static double m100k(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (VERSION.SDK_INT < 16) {
            return (double) C0212j.bP();
        }
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return ((double) memoryInfo.totalMem) / 1024.0d;
    }

    public static SortedMap<Double, String> m92a(Context context, Double d) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        PackageManager packageManager = context.getPackageManager();
        SortedMap<Double, String> treeMap = new TreeMap(Collections.reverseOrder());
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return treeMap;
        }
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(runningAppProcessInfo.processName, 0);
                if (applicationInfo != null && (applicationInfo.flags & 1) == 0) {
                    treeMap.put(Double.valueOf(((double) Math.round(((((double) activityManager.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid})[0].getTotalPss()) / d.doubleValue()) * 100.0d) * 100.0d)) / 100.0d), applicationInfo.packageName);
                }
            } catch (Throwable th) {
            }
        }
        return treeMap;
    }

    @SuppressLint({"all"})
    private static List<String> m101l(Context context) {
        List<String> arrayList = new ArrayList();
        if (C0212j.m103n(context.getApplicationContext())) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
            long currentTimeMillis = System.currentTimeMillis();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(4, currentTimeMillis - 300000, currentTimeMillis);
            if (queryUsageStats != null && queryUsageStats.size() > 0) {
                for (UsageStats usageStats : queryUsageStats) {
                    if ((VERSION.SDK_INT <= 23 || !usageStatsManager.isAppInactive(usageStats.getPackageName())) && usageStats.getLastTimeUsed() >= currentTimeMillis - 300000) {
                        arrayList.add(usageStats.getPackageName());
                    }
                }
            }
        }
        return arrayList;
    }

    public static List<String> m102m(Context context) {
        List<String> arrayList = new ArrayList();
        if (context == null) {
            return arrayList;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        if (VERSION.SDK_INT < 21) {
            try {
                if (C0207f.m65b(C0205d.getContext(), "android.permission.GET_TASKS")) {
                    List runningTasks = activityManager.getRunningTasks(3);
                    if (!(runningTasks == null || runningTasks.isEmpty())) {
                        arrayList.add(((RunningTaskInfo) runningTasks.get(0)).baseActivity.getPackageName());
                    }
                }
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        } else if (VERSION.SDK_INT >= 21 && C0212j.m103n(context.getApplicationContext())) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
            long currentTimeMillis = System.currentTimeMillis();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(4, currentTimeMillis - gx, currentTimeMillis);
            if (queryUsageStats == null) {
                return arrayList;
            }
            int size = queryUsageStats.size() - 1;
            while (size >= 0) {
                if (((UsageStats) queryUsageStats.get(size)).getLastTimeUsed() < currentTimeMillis - 120000 || ((UsageStats) queryUsageStats.get(size)).getTotalTimeInForeground() == 0) {
                    queryUsageStats.remove(size);
                }
                size--;
            }
            if (queryUsageStats.size() > 0) {
                SortedMap treeMap = new TreeMap();
                for (UsageStats usageStats : queryUsageStats) {
                    treeMap.put(Long.valueOf(usageStats.getLastTimeUsed()), usageStats);
                }
                if (!treeMap.isEmpty()) {
                    arrayList.add(((UsageStats) treeMap.get(treeMap.lastKey())).getPackageName());
                }
            }
        }
        return arrayList;
    }

    @TargetApi(21)
    public static boolean m103n(Context context) {
        return ((AppOpsManager) context.getSystemService("appops")).checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) == 0;
    }

    protected static long bP() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            long intValue = (long) Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue();
            bufferedReader.close();
            return intValue;
        } catch (Throwable e) {
            C0216b.m114a(e);
            return -1;
        }
    }

    public static void m95c(String str, String str2, C0203a c0203a) {
        Intent intent = new Intent(C0205d.getContext(), OperationService.class);
        intent.putExtra(C0204c.fD, str);
        intent.putExtra(C0204c.fS, str2);
        intent.putExtra(C0204c.fU, c0203a);
        intent.putExtra(C0204c.fT, rcd.class.getSimpleName());
        C0205d.getContext().startService(intent);
    }

    public static long m96d(String str, String str2) {
        try {
            if (!TextUtils.isDigitsOnly(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                return 0;
            }
            long parseLong = Long.parseLong(str);
            if (C0204c.fK.equalsIgnoreCase(str2)) {
                if (str.length() == 4) {
                    return C0212j.m88a(Integer.parseInt(str.substring(0, 2)), Integer.parseInt(str.substring(2, 4)), 1800, 1800);
                }
                return -1;
            } else if (C0204c.fE.equalsIgnoreCase(str2)) {
                return gw * parseLong;
            } else {
                if (C0204c.fF.equalsIgnoreCase(str2)) {
                    return gx * parseLong;
                }
                if (C0204c.fG.equalsIgnoreCase(str2)) {
                    return gy * parseLong;
                }
                if (C0204c.fH.equalsIgnoreCase(str2)) {
                    return gz * parseLong;
                }
                return -1;
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
            return -1;
        }
    }

    public static long m87a(int i, int i2) {
        Calendar instance = Calendar.getInstance();
        instance.set(11, i);
        instance.set(12, i2);
        instance.set(13, 0);
        if (new Date().after(instance.getTime())) {
            instance.add(5, 1);
        }
        return instance.getTimeInMillis();
    }

    public static long m88a(int i, int i2, int i3, int i4) {
        return C0212j.m87a(i, i2) + (((long) (iF.nextInt(i3 + i4) - i3)) * gw);
    }

    public static void m104o(Context context) {
        try {
            List bb = C0192a.aE().aJ().bb();
            if (bb == null || bb.size() != 0) {
                C0198a.aA().m48a(C0192a.aE().aJ().bb());
                C0198a.aA().aB();
                return;
            }
            C0192a.aE().aJ().m454a(new JSONObject("{ \"status\": \"on\", \"sample\": \"1\", \"sample_type\": \"hour\", \"cycle\": \"1\", \"cycle_type\": \"interval\", \"exact\": \"false\", \"key\": \"cdm\" }"));
            Intent intent = new Intent(context, OperationService.class);
            intent.putExtra(C0204c.fD, cdm.class.getSimpleName());
            context.startService(intent);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    public static void m93a(Context context, Class cls, long j, C0934c c0934c) {
        if (context != null && c0934c != null && cls != null) {
            long j2;
            if (C0204c.fK.equalsIgnoreCase(c0934c.aT())) {
                j2 = gz;
            } else {
                j2 = C0212j.m96d(c0934c.aS(), c0934c.aT());
            }
            if (j2 != -1) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
                Intent intent = new Intent(context, cls);
                intent.putExtra(C0204c.fD, c0934c.getKey());
                PendingIntent service = PendingIntent.getService(context, c0934c.getKey().hashCode(), intent, 134217728);
                if (!c0934c.aW()) {
                    alarmManager.setInexactRepeating(1, j, j2, service);
                } else if (VERSION.SDK_INT >= 23) {
                    alarmManager.setExactAndAllowWhileIdle(0, j, service);
                } else if (VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(0, j, service);
                } else {
                    alarmManager.set(0, j, service);
                }
            }
        }
    }

    public static boolean m105p(Context context) throws Exception {
        Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
        return advertisingIdInfo != null && advertisingIdInfo.isLimitAdTrackingEnabled();
    }

    public static boolean m106q(Context context) {
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            if (advertisingIdInfo == null || advertisingIdInfo.isLimitAdTrackingEnabled()) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            C0216b.m114a(th);
            C0213k.m110j(TAG, th.toString());
            return true;
        }
    }

    public static void m97e(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            C0934c aj = C0206e.bB().aj(str);
            if (aj != null && !C0204c.fO.equalsIgnoreCase(aj.aR())) {
                String str3 = C0204c.by() + "/data";
                HashMap hashMap = new HashMap();
                Object w = C0266f.m207w(C0205d.getContext());
                if (TextUtils.isEmpty(w)) {
                    w = C0209h.bG().getString(C0209h.gd, "");
                }
                if (!TextUtils.isEmpty(w)) {
                    hashMap.put("aid", w);
                    hashMap.put("cuid", w + "_" + System.currentTimeMillis());
                    hashMap.put("lvid", "4.5.9");
                    try {
                        hashMap.put("localdate", C0212j.m90a(new Date()));
                        hashMap.put("timezone", C0212j.bQ());
                    } catch (Throwable th) {
                    }
                    try {
                        hashMap.put("app_package", C0205d.getPackageName());
                    } catch (Throwable th2) {
                        C0216b.m114a(th2);
                        hashMap.put("app_package", "");
                    }
                    String str4 = "";
                    hashMap.put(str, str2);
                    C0213k.m107g(str, "-------Sending to server data for key = " + str + " ----------");
                    for (Entry entry : hashMap.entrySet()) {
                        C0213k.m107g(str, "---- " + ((String) entry.getKey()) + " : " + ((String) entry.getValue()) + " ----");
                    }
                    try {
                        C0213k.m107g(str, "result send data: " + C0266f.m199a(str3, hashMap, false, C0204c.fC));
                    } catch (Throwable th22) {
                        int responseCode = th22.responseCode();
                        str3 = th22.getMessage();
                        if (responseCode == HttpStatus.SC_BAD_REQUEST || responseCode == HttpStatus.SC_UNAUTHORIZED || responseCode == 402 || responseCode == HttpStatus.SC_FORBIDDEN || responseCode == 404 || responseCode == 405 || responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR || responseCode == HttpStatus.SC_NOT_IMPLEMENTED || responseCode == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                            C0216b.m114a(th22);
                        }
                        C0213k.m110j(str, "server error (Type:HttpRetryException)" + str3 + "  " + responseCode);
                    } catch (Throwable th222) {
                        C0213k.m110j(str, "server error (Type:Exception) " + th222.getMessage());
                    }
                }
            }
        }
    }

    public static String bQ() {
        Calendar instance = Calendar.getInstance(TimeZone.getDefault(), Locale.US);
        int i = (instance.get(16) + instance.get(15)) / 60000;
        char c = '+';
        if (i < 0) {
            c = '-';
            i = -i;
        }
        StringBuilder stringBuilder = new StringBuilder(9);
        stringBuilder.append("GMT");
        stringBuilder.append(c);
        C0212j.m94a(stringBuilder, 2, i / 60);
        stringBuilder.append(':');
        C0212j.m94a(stringBuilder, 2, i % 60);
        return stringBuilder.toString();
    }

    private static void m94a(StringBuilder stringBuilder, int i, int i2) {
        String num = Integer.toString(i2);
        for (int i3 = 0; i3 < i - num.length(); i3++) {
            stringBuilder.append('0');
        }
        stringBuilder.append(num);
    }

    public static String m90a(Date date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.US).format(date));
        stringBuilder.append(" ");
        stringBuilder.append(C0212j.bQ());
        stringBuilder.append(" ");
        stringBuilder.append(new SimpleDateFormat("yyyy", Locale.US).format(date));
        return stringBuilder.toString();
    }

    public static void m98f(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            C0209h.bG().putString(str + C0209h.gc, str2);
        }
    }

    public static String ar(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return C0209h.bG().getString(str + C0209h.gc, null);
    }

    public static Location m89a(double d, double d2, float f) {
        Location location = new Location("");
        location.setLatitude(d);
        location.setLongitude(d2);
        location.setAccuracy(f);
        return location;
    }
}
