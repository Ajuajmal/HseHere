package com.appnext.core.webview;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.appnext.core.C0266f;

public class WebAppInterface {
    Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public int getAdCount() {
        C0266f.m194P("getAdCount");
        return 0;
    }

    @JavascriptInterface
    public String getAdAt(int i) {
        C0266f.m194P("getAdAt " + i);
        return "";
    }

    @JavascriptInterface
    public String init() {
        C0266f.m194P("init");
        return "";
    }

    @JavascriptInterface
    public void destroy() {
        C0266f.m194P("destroy");
    }

    @JavascriptInterface
    public void destroy(String str) {
        C0266f.m194P("destroy with error code: " + str);
    }

    @JavascriptInterface
    public void postView(String str) {
        C0266f.m194P("postView: " + str);
    }

    @JavascriptInterface
    public void openStore(String str) {
        C0266f.m194P("openStore: " + str);
    }

    @JavascriptInterface
    public void play() {
        C0266f.m194P("play");
    }

    @JavascriptInterface
    public String filterAds(String str) {
        C0266f.m194P("filterAds: " + str);
        return str;
    }

    @JavascriptInterface
    public String loadAds() {
        C0266f.m194P("loadAds");
        return "";
    }

    @JavascriptInterface
    public void videoPlayed() {
        C0266f.m194P("videoPlayed");
    }

    @JavascriptInterface
    public void openLink(String str) {
        C0266f.m194P("openLink " + str);
    }

    @JavascriptInterface
    public void gotoAppWall() {
        C0266f.m194P("gotoAppWall");
    }

    @JavascriptInterface
    public void notifyImpression(String str) {
        C0266f.m194P("notifyImpression");
    }

    @JavascriptInterface
    public void jsError(String str) {
        C0266f.m194P("jsError " + str);
    }
}
