package com.appnext.core.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.appnext.core.C0266f;
import java.io.IOException;
import java.net.HttpRetryException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.http.HttpHost;

public class AppnextWebView {
    public static final int ia = 1;
    public static final int ib = 2;
    private static AppnextWebView ic;
    private WebAppInterface cZ;
    private final HashMap<String, C0283b> id = new HashMap();
    private HashMap<String, WebView> ie;

    class C02811 extends WebViewClient {
        final /* synthetic */ AppnextWebView f28if;

        C02811(AppnextWebView appnextWebView) {
            this.f28if = appnextWebView;
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
    }

    private class C0282a extends AsyncTask<String, Void, String> {
        final /* synthetic */ AppnextWebView f29if;
        C0283b ig;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m239d((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            aD((String) obj);
        }

        public C0282a(AppnextWebView appnextWebView, C0283b c0283b) {
            this.f29if = appnextWebView;
            this.ig = c0283b;
        }

        protected String m239d(String... strArr) {
            try {
                C0283b c0283b = (C0283b) this.f29if.id.get(strArr[0]);
                c0283b.ih = C0266f.m197a(strArr[0], null, 3000);
                this.f29if.id.put(strArr[0], c0283b);
                return strArr[0];
            } catch (HttpRetryException e) {
                return "error: " + e.getReason();
            } catch (IOException e2) {
                return "error: network problem";
            } catch (Throwable th) {
                return "error: unknown error";
            }
        }

        protected void aD(String str) {
            super.onPostExecute(str);
            if (str.startsWith("error:")) {
                this.ig.state = 0;
                this.f29if.id.put(this.ig.aZ, this.ig);
                this.f29if.m244a(this.ig, str.substring("error: ".length()));
                return;
            }
            this.ig.state = 2;
            this.f29if.id.put(this.ig.aZ, this.ig);
            this.f29if.m247b(this.ig, str);
        }
    }

    private class C0283b {
        public String aZ;
        public ArrayList<C0284c> hY;
        final /* synthetic */ AppnextWebView f30if;
        public String ih;
        public int state;

        private C0283b(AppnextWebView appnextWebView) {
            this.f30if = appnextWebView;
            this.state = 0;
            this.ih = "";
            this.hY = new ArrayList();
        }
    }

    public interface C0284c {
        void mo1219J(String str);

        void error(String str);
    }

    private class WebInterface extends WebAppInterface {
        final /* synthetic */ AppnextWebView f60if;

        public WebInterface(AppnextWebView appnextWebView) {
            this.f60if = appnextWebView;
        }

        @JavascriptInterface
        public void destroy(String str) {
            super.destroy(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.destroy(str);
            }
        }

        @JavascriptInterface
        public void postView(String str) {
            super.postView(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.postView(str);
            }
        }

        @JavascriptInterface
        public void openStore(String str) {
            super.openStore(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.openStore(str);
            }
        }

        @JavascriptInterface
        public void play() {
            super.play();
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.play();
            }
        }

        @JavascriptInterface
        public String filterAds(String str) {
            super.filterAds(str);
            if (AppnextWebView.ic.cZ != null) {
                return AppnextWebView.ic.cZ.filterAds(str);
            }
            return str;
        }

        @JavascriptInterface
        public String loadAds() {
            super.loadAds();
            if (AppnextWebView.ic.cZ != null) {
                return AppnextWebView.ic.cZ.loadAds();
            }
            return "";
        }

        @JavascriptInterface
        public void openLink(String str) {
            super.openLink(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.openLink(str);
            }
        }

        @JavascriptInterface
        public void gotoAppWall() {
            super.gotoAppWall();
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.gotoAppWall();
            }
        }

        @JavascriptInterface
        public void videoPlayed() {
            super.videoPlayed();
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.videoPlayed();
            }
        }

        @JavascriptInterface
        public void notifyImpression(String str) {
            super.notifyImpression(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.notifyImpression(str);
            }
        }

        @JavascriptInterface
        public void jsError(String str) {
            super.jsError(str);
            if (AppnextWebView.ic.cZ != null) {
                AppnextWebView.ic.cZ.jsError(str);
            }
        }
    }

    public void m250a(WebAppInterface webAppInterface) {
        if (this.cZ == webAppInterface) {
            this.cZ = null;
        }
    }

    @SuppressLint({"NewApi"})
    public static AppnextWebView m241B(Context context) {
        if (ic == null) {
            ic = new AppnextWebView();
            ic.ie = new HashMap();
        }
        return ic;
    }

    private AppnextWebView() {
    }

    public synchronized void m251a(String str, C0284c c0284c) {
        C0283b c0283b;
        if (this.id.containsKey(str)) {
            c0283b = (C0283b) this.id.get(str);
        } else {
            c0283b = new C0283b();
            c0283b.aZ = str;
        }
        if (c0283b.state != 2) {
            if (c0283b.state == 0) {
                c0283b.state = 1;
                C0266f.m194P("start loading config");
                new C0282a(this, c0283b).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{str, null});
            }
            if (c0284c != null) {
                c0283b.hY.add(c0284c);
            }
            this.id.put(str, c0283b);
        } else if (c0284c != null) {
            c0284c.mo1219J(str);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi", "InlinedApi"})
    private WebView m242C(Context context) {
        WebView webView = new WebView(context.getApplicationContext());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        if (VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode(0);
        }
        if (VERSION.SDK_INT >= 17) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new C02811(this));
        return webView;
    }

    @SuppressLint({"AddJavascriptInterface"})
    public WebView m249a(Context context, String str, WebAppInterface webAppInterface, C0285a c0285a, String str2) {
        try {
            ic.cZ = webAppInterface;
            WebView webView = (WebView) this.ie.get(str2);
            if (webView == null) {
                String str3;
                URL url = new URL(str);
                String str4 = url.getProtocol() + "://" + url.getHost();
                webView = m242C(context);
                if (this.id.containsKey(str) && ((C0283b) this.id.get(str)).state == 2 && !((C0283b) this.id.get(str)).ih.equals("")) {
                    str3 = "<script>" + ((C0283b) this.id.get(str)).ih + "</script>";
                } else if (c0285a != null) {
                    str3 = "<script>" + c0285a.ad() + "</script>";
                } else {
                    str3 = "<script src='" + str + "'></script>";
                }
                webView.loadDataWithBaseURL(str4, "<html><body>" + str3 + "</body></html>", null, "UTF-8", null);
                this.ie.put(str2, webView);
                webView.addJavascriptInterface(new WebInterface(this), "Appnext");
                return webView;
            } else if (webView.getParent() == null) {
                return webView;
            } else {
                ((ViewGroup) webView.getParent()).removeView(webView);
                return webView;
            }
        } catch (Throwable th) {
            return null;
        }
    }

    private void m244a(C0283b c0283b, String str) {
        C0266f.m194P("webview error: " + str);
        synchronized (this.id) {
            Iterator it = c0283b.hY.iterator();
            while (it.hasNext()) {
                ((C0284c) it.next()).error(str);
            }
            c0283b.hY.clear();
            this.id.remove(c0283b.aZ);
        }
    }

    private void m247b(C0283b c0283b, String str) {
        C0266f.m194P("downloaded " + str);
        synchronized (this.id) {
            Iterator it = c0283b.hY.iterator();
            while (it.hasNext()) {
                ((C0284c) it.next()).mo1219J(str);
            }
            c0283b.hY.clear();
        }
    }
}
