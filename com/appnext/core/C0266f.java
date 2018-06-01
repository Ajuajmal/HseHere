package com.appnext.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.webkit.WebView;
import com.appnext.base.p005b.C0204c;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.client.methods.HttpPost;

public class C0266f {
    private static final boolean DEBUG = false;
    public static final String VID = "2.0.3.459";
    static final int fC = 8000;
    public static final Executor hA = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1, (Runtime.getRuntime().availableProcessors() * 2) + 1, 1, TimeUnit.SECONDS, new LinkedBlockingQueue(128), new C02641());
    private static double hw = -1.0d;
    private static HashMap<String, Bitmap> hx = new HashMap();
    private static String hy = "";
    private static String hz = "";

    static class C02641 implements ThreadFactory {
        private final AtomicInteger hB = new AtomicInteger(1);

        C02641() {
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "AsyncTask #" + this.hB.getAndIncrement());
        }
    }

    private static class C0265a extends AsyncTask<String, Void, Void> {
        private C0265a() {
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m193c((String[]) objArr);
        }

        protected Void m193c(String... strArr) {
            String str = "";
            String str2 = "";
            try {
                str = URLEncoder.encode(strArr[4], "UTF-8");
            } catch (Throwable th) {
            }
            try {
                str2 = URLEncoder.encode(strArr[6], "UTF-8");
            } catch (Throwable th2) {
            }
            str2 = String.format("https://admin.appnext.com/tp12.aspx?tid=%s&vid=%s&osid=%s&auid=%s&session_id=%s&pid=%s&ref=%s&ads_type=%s&bid=%s&cid=%s", new Object[]{strArr[0], strArr[1], strArr[2], strArr[3], str, strArr[5], str2, strArr[7], strArr[8], strArr[9]});
            try {
                C0266f.m194P("report: " + str2);
                C0266f.m196a(str2, null);
            } catch (IOException e) {
                C0266f.m194P("report error: " + e.getMessage());
            }
            return null;
        }
    }

    static {
        CookieHandler.setDefault(new CookieManager());
        C0266f.ch();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String m207w(android.content.Context r3) {
        /*
        r1 = com.appnext.core.C0266f.class;
        monitor-enter(r1);
        if (r3 == 0) goto L_0x000b;
    L_0x0005:
        r0 = r3.getApplicationContext();	 Catch:{ all -> 0x006c }
        if (r0 != 0) goto L_0x000f;
    L_0x000b:
        r0 = "";
    L_0x000d:
        monitor-exit(r1);
        return r0;
    L_0x000f:
        r0 = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
        java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0051 }
        r0 = r3.getApplicationContext();	 Catch:{ ClassNotFoundException -> 0x0051 }
        r0 = com.appnext.core.AdsIDHelper.m123r(r0);	 Catch:{ ClassNotFoundException -> 0x0051 }
    L_0x001c:
        if (r0 == 0) goto L_0x0026;
    L_0x001e:
        r2 = "";
        r2 = r0.equals(r2);	 Catch:{ Throwable -> 0x005b }
        if (r2 == 0) goto L_0x0058;
    L_0x0026:
        r0 = r3.getApplicationContext();	 Catch:{ Throwable -> 0x005b }
        r0 = com.appnext.core.C0263e.m192v(r0);	 Catch:{ Throwable -> 0x005b }
        r2 = "";
        r2 = r0.equals(r2);	 Catch:{ Throwable -> 0x005b }
        if (r2 == 0) goto L_0x0058;
    L_0x0036:
        r2 = r3.getApplicationContext();	 Catch:{ Throwable -> 0x0055 }
        r2 = com.appnext.core.C0254c.m171s(r2);	 Catch:{ Throwable -> 0x0055 }
        r2 = r2.cb();	 Catch:{ Throwable -> 0x0055 }
        if (r2 != 0) goto L_0x0058;
    L_0x0044:
        r0 = r3.getApplicationContext();	 Catch:{ Throwable -> 0x0055 }
        r0 = com.appnext.core.C0254c.m171s(r0);	 Catch:{ Throwable -> 0x0055 }
        r0 = r0.getId();	 Catch:{ Throwable -> 0x0055 }
        goto L_0x000d;
    L_0x0051:
        r0 = move-exception;
        r0 = "";
        goto L_0x001c;
    L_0x0055:
        r0 = move-exception;
        r0 = "";
    L_0x0058:
        hz = r0;	 Catch:{ Throwable -> 0x005b }
        goto L_0x000d;
    L_0x005b:
        r0 = move-exception;
        r0 = hz;	 Catch:{ all -> 0x006c }
        r2 = "";
        r0 = r0.equals(r2);	 Catch:{ all -> 0x006c }
        if (r0 != 0) goto L_0x0069;
    L_0x0066:
        r0 = hz;	 Catch:{ all -> 0x006c }
        goto L_0x000d;
    L_0x0069:
        r0 = "";
        goto L_0x000d;
    L_0x006c:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appnext.core.f.w(android.content.Context):java.lang.String");
    }

    static void m208x(Context context) {
        try {
            WebView webView = new WebView(context);
            C0266f.ax(webView.getSettings().getUserAgentString());
            webView.destroy();
        } catch (Throwable th) {
        }
    }

    static String ce() {
        return hy;
    }

    private static void ax(String str) {
        hy = str;
    }

    public static String cf() {
        try {
            return URLEncoder.encode("android " + VERSION.SDK_INT + " " + Build.MANUFACTURER + " " + Build.MODEL, "UTF-8");
        } catch (Throwable th) {
            C0266f.m205c(th);
            return "android";
        }
    }

    public static int cy() {
        Runtime runtime = Runtime.getRuntime();
        return (int) ((runtime.maxMemory() / C0204c.fB) - ((runtime.totalMemory() - runtime.freeMemory()) / C0204c.fB));
    }

    public static String m196a(String str, HashMap<String, String> hashMap) throws IOException {
        return C0266f.m199a(str, hashMap, true, 8000);
    }

    public static String m198a(String str, HashMap<String, String> hashMap, boolean z) throws IOException {
        return C0266f.m199a(str, hashMap, z, 8000);
    }

    public static String m197a(String str, HashMap<String, String> hashMap, int i) throws IOException {
        return C0266f.m199a(str, hashMap, true, i);
    }

    public static String m199a(String str, HashMap<String, String> hashMap, boolean z, int i) throws IOException {
        return new String(C0266f.m204b(str, hashMap, z, i), "UTF-8");
    }

    public static byte[] m203b(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static byte[] m204b(String str, HashMap<String, String> hashMap, boolean z, int i) throws IOException {
        Throwable th;
        InputStream inputStream;
        Throwable th2;
        HttpURLConnection httpURLConnection = null;
        URL url = new URL(str);
        C0266f.m194P("performURLCall " + str);
        try {
            int responseCode;
            InputStream inputStream2;
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url.openConnection();
            try {
                httpURLConnection2.setReadTimeout(i);
                httpURLConnection2.setConnectTimeout(i);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setRequestProperty("User-Agent", C0266f.ce());
                if (hashMap != null) {
                    httpURLConnection2.setRequestMethod(HttpPost.METHOD_NAME);
                    httpURLConnection2.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection2.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    bufferedWriter.write(C0266f.m200a((HashMap) hashMap, z));
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                }
                responseCode = httpURLConnection2.getResponseCode();
                inputStream2 = httpURLConnection2.getInputStream();
            } catch (Throwable th3) {
                th = th3;
                inputStream = null;
                httpURLConnection = httpURLConnection2;
                th2 = th;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th2;
            }
            try {
                byte[] b = C0266f.m203b(inputStream2);
                if (responseCode == 200) {
                    if (inputStream2 != null) {
                        inputStream2.close();
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    return b;
                }
                throw new HttpRetryException(new String(b, "UTF-8"), responseCode);
            } catch (Throwable th32) {
                th = th32;
                inputStream = inputStream2;
                httpURLConnection = httpURLConnection2;
                th2 = th;
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                throw th2;
            }
        } catch (Throwable th4) {
            th2 = th4;
            inputStream = null;
            if (inputStream != null) {
                inputStream.close();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th2;
        }
    }

    public static Bitmap ay(String str) {
        HttpURLConnection httpURLConnection;
        Throwable th;
        HttpURLConnection httpURLConnection2 = null;
        try {
            if (hx.get(str) != null) {
                Bitmap bitmap = (Bitmap) hx.get(str);
                if (httpURLConnection2 == null) {
                    return bitmap;
                }
                httpURLConnection2.disconnect();
                return bitmap;
            }
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                if (decodeStream == null) {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return httpURLConnection2;
                }
                if (!hx.containsKey(str)) {
                    hx.put(str, decodeStream);
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return decodeStream;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                httpURLConnection2 = httpURLConnection;
                th = th3;
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            throw th;
        }
    }

    private static String m200a(HashMap<String, String> hashMap, boolean z) {
        Object obj;
        StringBuilder stringBuilder = new StringBuilder();
        Object obj2 = 1;
        for (Entry entry : hashMap.entrySet()) {
            Object obj3;
            try {
                if (!(entry.getKey() == null || entry.getValue() == null)) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    if (obj2 != null) {
                        obj = null;
                    } else {
                        stringBuilder2.append("&");
                        obj = obj2;
                    }
                    if (z) {
                        try {
                            stringBuilder2.append(URLEncoder.encode(URLDecoder.decode((String) entry.getKey(), "UTF-8"), "UTF-8"));
                            stringBuilder2.append("=");
                            stringBuilder2.append(URLEncoder.encode(URLDecoder.decode((String) entry.getValue(), "UTF-8"), "UTF-8"));
                        } catch (Throwable th) {
                            obj3 = obj;
                        }
                    } else {
                        stringBuilder2.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
                        stringBuilder2.append("=");
                        stringBuilder2.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
                    }
                    stringBuilder.append(stringBuilder2);
                    obj3 = obj;
                    obj2 = obj3;
                }
            } catch (Throwable th2) {
                obj3 = obj2;
            }
        }
        return stringBuilder.toString();
    }

    public static int m195a(Context context, float f) {
        return (int) ((((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f) * f);
    }

    public static void m201a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        try {
            if (str8.equals("")) {
                str8 = "0";
            }
            if (str9.equals("")) {
                str9 = "0";
            }
            new C0265a().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{str, str2, "100", str3, str5.replace(" ", "_"), str4, str6.replace(" ", "_"), str7, str8, str9});
        } catch (Throwable th) {
        }
    }

    public static boolean m206c(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 128);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static String getCookie(String str, String str2) {
        String cookie = android.webkit.CookieManager.getInstance().getCookie(str);
        if (cookie == null) {
            return "";
        }
        cookie = null;
        for (String str3 : cookie.split(";")) {
            if (str3.contains(str2)) {
                cookie = str3.split("=")[1];
            }
        }
        return cookie;
    }

    public static String m209y(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return "Unknown";
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "-";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        if (activeNetworkInfo.getType() == 0) {
            return "" + activeNetworkInfo.getSubtype();
        }
        return "Unknown";
    }

    public static String m210z(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") != 0) {
            return "Unknown";
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return "-";
        }
        if (activeNetworkInfo.getType() == 1) {
            return "wifi";
        }
        if (activeNetworkInfo.getType() != 0) {
            return "Unknown";
        }
        switch (activeNetworkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static int az(String str) {
        String toLowerCase = str.toLowerCase(Locale.US);
        int i = -1;
        switch (toLowerCase.hashCode()) {
            case 1653:
                if (toLowerCase.equals("2g")) {
                    i = 0;
                    break;
                }
                break;
            case 1684:
                if (toLowerCase.equals("3g")) {
                    i = 1;
                    break;
                }
                break;
            case 1715:
                if (toLowerCase.equals("4g")) {
                    i = 2;
                    break;
                }
                break;
            case 3649301:
                if (toLowerCase.equals("wifi")) {
                    i = 3;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                return 0;
        }
    }

    public static String m202b(Throwable th) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String obj = stringWriter.toString();
        return obj.length() > 512 ? obj.substring(0, 512) : obj;
    }

    public static double cg() {
        return hw;
    }

    private static synchronized double ch() {
        double d = -1.0d;
        synchronized (C0266f.class) {
            if (hw > -1.0d) {
                d = hw;
            } else if (hw > 0.0d) {
                d = hw;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                long totalRxBytes = TrafficStats.getTotalRxBytes();
                InputStream inputStream;
                try {
                    URLConnection openConnection = new URL("https://appnext.hs.llnwd.net/banner/appnext.mp4").openConnection();
                    openConnection.setUseCaches(false);
                    openConnection.setReadTimeout(4000);
                    openConnection.setConnectTimeout(4000);
                    openConnection.connect();
                    inputStream = openConnection.getInputStream();
                    do {
                    } while (inputStream.read(new byte[1024]) != -1);
                    try {
                        inputStream.close();
                    } catch (Throwable th) {
                    }
                    d = (((double) (TrafficStats.getTotalRxBytes() - totalRxBytes)) / (((double) (System.currentTimeMillis() - currentTimeMillis)) / 1000.0d)) / 1024.0d;
                    hw = d;
                } catch (Throwable th2) {
                }
            }
        }
        return d;
    }

    public static void m194P(String str) {
    }

    public static void m205c(Throwable th) {
    }
}
