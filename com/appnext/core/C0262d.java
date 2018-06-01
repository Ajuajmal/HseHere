package com.appnext.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.plus.PlusShare;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class C0262d {
    private static final long hh = 8000;
    private static final long hi = 15000;
    @SuppressLint({"StaticFieldLeak"})
    private static C0262d hp;
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());
    private WebView hj;
    private WebView hk;
    private C0258a hl = new C09511(this);
    private Runnable hm = new C02562(this);
    private final ArrayList<C0260c> hn = new ArrayList();
    private int ho = 0;

    class C02562 implements Runnable {
        final /* synthetic */ C0262d hq;

        C02562(C0262d c0262d) {
            this.hq = c0262d;
        }

        public void run() {
            if (this.hq.hl == null || this.hq.hj == null) {
                this.hq.cc();
                return;
            }
            this.hq.hl.error(this.hq.hj.getUrl());
            this.hq.hj.stopLoading();
        }
    }

    class C02573 extends WebViewClient {
        final /* synthetic */ C0262d hq;

        C02573(C0262d c0262d) {
            this.hq = c0262d;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str == null) {
                return false;
            }
            C0266f.m194P("redirect url: " + str);
            if (str.startsWith("https://play.google.com/store/apps/")) {
                str = str.replace("https://play.google.com/store/apps/", "market://");
            }
            if (str.contains("about:blank")) {
                return false;
            }
            if (!(str.startsWith("http://") || str.startsWith("https://"))) {
                Intent parseUri;
                if (str.startsWith("intent://")) {
                    try {
                        parseUri = Intent.parseUri(str, 1);
                        if (this.hq.context.getPackageManager().resolveActivity(parseUri, 65536) != null) {
                            this.hq.handler.removeCallbacksAndMessages(null);
                            if (this.hq.hl != null) {
                                this.hq.hl.onMarket(parseUri.getData().toString());
                            }
                            return true;
                        }
                    } catch (Throwable th) {
                        C0266f.m205c(th);
                        return false;
                    }
                }
                parseUri = new Intent("android.intent.action.VIEW");
                parseUri.setData(Uri.parse(str));
                try {
                    if (this.hq.context.getPackageManager().queryIntentActivities(parseUri, 0).size() <= 0) {
                        return false;
                    }
                    this.hq.handler.removeCallbacksAndMessages(null);
                    if (this.hq.hl != null) {
                        this.hq.hl.onMarket(str);
                    }
                    return true;
                } catch (Throwable th2) {
                    C0266f.m205c(th2);
                    return false;
                }
            }
            webView.loadUrl(str);
            return true;
        }
    }

    public interface C0258a {
        void error(String str);

        void onMarket(String str);
    }

    private class C0259b extends AsyncTask<AppnextAd, Void, String> {
        final /* synthetic */ C0262d hq;

        private C0259b(C0262d c0262d) {
            this.hq = c0262d;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m172a((AppnextAd[]) objArr);
        }

        protected String m172a(AppnextAd... appnextAdArr) {
            try {
                C0266f.m196a(appnextAdArr[0].getImpressionURL(), null);
            } catch (Throwable th) {
            }
            return "";
        }
    }

    private class C0260c {
        String aZ;
        C0258a hl;
        final /* synthetic */ C0262d hq;
        String hs;
        long iG;

        C0260c(C0262d c0262d, String str, String str2, C0258a c0258a, long j) {
            this.hq = c0262d;
            this.aZ = str;
            this.hl = c0258a;
            this.hs = str2;
            this.iG = j;
        }
    }

    private class C0261d extends AsyncTask<String, Void, Void> {
        final /* synthetic */ C0262d hq;

        private C0261d(C0262d c0262d) {
            this.hq = c0262d;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m173c((String[]) objArr);
        }

        protected Void m173c(String... strArr) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("guid", strArr[0]);
                hashMap.put("bannerId", strArr[1]);
                hashMap.put("placementId", strArr[2]);
                hashMap.put("vid", strArr[3]);
                hashMap.put(PlusShare.KEY_CALL_TO_ACTION_URL, strArr[4]);
                C0266f.m196a("https://admin.appnext.com/AdminService.asmx/" + strArr[5], hashMap);
            } catch (Throwable th) {
            }
            return null;
        }
    }

    class C09511 implements C0258a {
        final /* synthetic */ C0262d hq;

        class C02551 extends WebViewClient {
            final /* synthetic */ C09511 hr;

            C02551(C09511 c09511) {
                this.hr = c09511;
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (str == null || str.contains("about:blank")) {
                    return false;
                }
                webView.loadUrl(str);
                return true;
            }
        }

        C09511(C0262d c0262d) {
            this.hq = c0262d;
        }

        public void onMarket(String str) {
            if (this.hq.hn.size() != 0) {
                if (((C0260c) this.hq.hn.get(0)).hl != null) {
                    ((C0260c) this.hq.hn.get(0)).hl.onMarket(str);
                }
                try {
                    String str2 = "https://admin.appnext.com/tools/navtac.html?bid=" + ((C0260c) this.hq.hn.get(0)).hs + "&guid=" + C0266f.getCookie("admin.appnext.com", "applink") + "&url=" + URLEncoder.encode(str, "UTF-8");
                    if (this.hq.hk == null) {
                        this.hq.hk = new WebView(this.hq.context);
                        this.hq.hk.getSettings().setJavaScriptEnabled(true);
                        this.hq.hk.getSettings().setDomStorageEnabled(true);
                        if (VERSION.SDK_INT >= 21) {
                            this.hq.hk.getSettings().setMixedContentMode(0);
                        }
                        this.hq.hk.setWebViewClient(new C02551(this));
                    }
                    this.hq.hk.loadUrl("about:blank");
                    this.hq.hk.loadUrl(str2);
                    if (this.hq.handler != null) {
                        this.hq.handler.removeCallbacksAndMessages(null);
                    }
                    this.hq.cc();
                } catch (UnsupportedEncodingException e) {
                    this.hq.cc();
                }
            }
        }

        public void error(String str) {
            if (this.hq.hn.size() != 0) {
                if (((C0260c) this.hq.hn.get(0)).hl != null) {
                    ((C0260c) this.hq.hn.get(0)).hl.error(str);
                }
                this.hq.cc();
            }
        }
    }

    public static C0262d m183t(Context context) {
        if (hp == null) {
            hp = new C0262d(context);
        }
        return hp;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private C0262d(Context context) {
        this.context = context.getApplicationContext();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    public void m184a(String str, String str2, C0258a c0258a) {
        m185a(str, str2, c0258a, hh);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m185a(java.lang.String r11, java.lang.String r12, com.appnext.core.C0262d.C0258a r13, long r14) {
        /*
        r10 = this;
        r8 = r10.hn;
        monitor-enter(r8);
        r0 = r10.context;	 Catch:{ all -> 0x0027 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r8);	 Catch:{ all -> 0x0027 }
    L_0x0008:
        return;
    L_0x0009:
        if (r11 == 0) goto L_0x0041;
    L_0x000b:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r1 = r0.iterator();	 Catch:{ all -> 0x0027 }
    L_0x0011:
        r0 = r1.hasNext();	 Catch:{ all -> 0x0027 }
        if (r0 == 0) goto L_0x002a;
    L_0x0017:
        r0 = r1.next();	 Catch:{ all -> 0x0027 }
        r0 = (com.appnext.core.C0262d.C0260c) r0;	 Catch:{ all -> 0x0027 }
        r0 = r0.aZ;	 Catch:{ all -> 0x0027 }
        r0 = r0.equals(r11);	 Catch:{ all -> 0x0027 }
        if (r0 == 0) goto L_0x0011;
    L_0x0025:
        monitor-exit(r8);	 Catch:{ all -> 0x0027 }
        goto L_0x0008;
    L_0x0027:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0027 }
        throw r0;
    L_0x002a:
        r0 = "&ox=0";
        r0 = r11.endsWith(r0);	 Catch:{ all -> 0x0027 }
        if (r0 == 0) goto L_0x005f;
    L_0x0032:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r1 = new com.appnext.core.d$c;	 Catch:{ all -> 0x0027 }
        r2 = r10;
        r3 = r11;
        r4 = r12;
        r5 = r13;
        r6 = r14;
        r1.<init>(r2, r3, r4, r5, r6);	 Catch:{ all -> 0x0027 }
        r0.add(r1);	 Catch:{ all -> 0x0027 }
    L_0x0041:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r0 = r0.size();	 Catch:{ all -> 0x0027 }
        if (r0 <= 0) goto L_0x005d;
    L_0x0049:
        r0 = r10.ho;	 Catch:{ all -> 0x0027 }
        r1 = 1;
        if (r0 == r1) goto L_0x005d;
    L_0x004e:
        r0 = 1;
        r10.ho = r0;	 Catch:{ all -> 0x0027 }
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r1 = 0;
        r0 = r0.get(r1);	 Catch:{ all -> 0x0027 }
        r0 = (com.appnext.core.C0262d.C0260c) r0;	 Catch:{ all -> 0x0027 }
        r10.m176a(r0);	 Catch:{ all -> 0x0027 }
    L_0x005d:
        monitor-exit(r8);	 Catch:{ all -> 0x0027 }
        goto L_0x0008;
    L_0x005f:
        r0 = 0;
        r10.ho = r0;	 Catch:{ all -> 0x0027 }
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r0 = r0.size();	 Catch:{ all -> 0x0027 }
        if (r0 <= 0) goto L_0x0083;
    L_0x006a:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r1 = 0;
        r0 = r0.get(r1);	 Catch:{ all -> 0x0027 }
        r0 = (com.appnext.core.C0262d.C0260c) r0;	 Catch:{ all -> 0x0027 }
        r0 = r0.aZ;	 Catch:{ all -> 0x0027 }
        r1 = "&ox=0";
        r0 = r0.endsWith(r1);	 Catch:{ all -> 0x0027 }
        if (r0 != 0) goto L_0x0083;
    L_0x007d:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r1 = 0;
        r0.remove(r1);	 Catch:{ all -> 0x0027 }
    L_0x0083:
        r0 = r10.hn;	 Catch:{ all -> 0x0027 }
        r9 = 0;
        r1 = new com.appnext.core.d$c;	 Catch:{ all -> 0x0027 }
        r2 = r10;
        r3 = r11;
        r4 = r12;
        r5 = r13;
        r6 = r14;
        r1.<init>(r2, r3, r4, r5, r6);	 Catch:{ all -> 0x0027 }
        r0.add(r9, r1);	 Catch:{ all -> 0x0027 }
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appnext.core.d.a(java.lang.String, java.lang.String, com.appnext.core.d$a, long):void");
    }

    private void cc() {
        this.ho = 0;
        if (this.hn.size() != 0) {
            ((C0260c) this.hn.get(0)).hl = null;
            this.hn.remove(0);
            m184a(null, null, null);
        }
    }

    public void destroy() {
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void m176a(C0260c c0260c) {
        try {
            this.handler.removeCallbacksAndMessages(null);
            if (this.hj == null) {
                this.hj = new WebView(this.context);
                this.hj.getSettings().setJavaScriptEnabled(true);
                this.hj.getSettings().setDomStorageEnabled(true);
                if (VERSION.SDK_INT >= 21) {
                    this.hj.getSettings().setMixedContentMode(0);
                }
                this.hj.setWebViewClient(new C02573(this));
            }
            this.hj.stopLoading();
            this.hj.loadUrl("about:blank");
            this.hj.loadUrl(c0260c.aZ);
            C0266f.m194P("appurl: " + c0260c.aZ);
            this.handler.postDelayed(this.hm, c0260c.aZ.endsWith("&ox=0") ? hi : c0260c.iG);
        } catch (Throwable th) {
            if (this.hl != null) {
                this.hl.error(c0260c.aZ);
            }
            cc();
        }
    }

    public void m187e(AppnextAd appnextAd) {
        new C0259b().executeOnExecutor(C0266f.hA, new AppnextAd[]{appnextAd});
    }

    public void m186a(String str, String str2, String str3, String str4, String str5, String str6) {
        new C0261d().executeOnExecutor(C0266f.hA, new String[]{str, str2, str3, str4, str5, str6});
    }
}
