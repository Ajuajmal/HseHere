package hse.here2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.android.gms.plus.PlusShare;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

public class t_rssdetalle_fr extends Fragment {
    Bundle extras;
    boolean finalizar = false;
    config globales;
    boolean limpiar_hist = true;
    boolean linksexternos = false;
    WebChromeClient mClient;
    LinearLayout mContentView;
    View mCustomView;
    CustomViewCallback mCustomViewCallback;
    ListView mDrawerList;
    FrameLayout mTargetView;
    WebView myWebView;
    boolean primera = true;
    View f67v;

    class C08271 extends WebChromeClient {
        C08271() {
        }

        public boolean onJsBeforeUnload(WebView v, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        @TargetApi(11)
        public void onShowCustomView(View view, CustomViewCallback callback) {
            t_rssdetalle_fr.this.mCustomViewCallback = callback;
            t_rssdetalle_fr.this.mTargetView.addView(view);
            t_rssdetalle_fr.this.mCustomView = view;
            t_rssdetalle_fr.this.mContentView.setVisibility(8);
            t_rssdetalle_fr.this.mTargetView.setVisibility(0);
            if (VERSION.SDK_INT < 16) {
                t_rssdetalle_fr.this.getActivity().getWindow().setFlags(1024, 1024);
            } else {
                t_rssdetalle_fr.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(4);
            }
        }

        @TargetApi(11)
        public void onHideCustomView() {
            if (t_rssdetalle_fr.this.getActivity() != null) {
                if (VERSION.SDK_INT < 16) {
                    t_rssdetalle_fr.this.getActivity().getWindow().clearFlags(1024);
                } else {
                    t_rssdetalle_fr.this.getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
                }
                if (t_rssdetalle_fr.this.mCustomView != null) {
                    t_rssdetalle_fr.this.mCustomView.setVisibility(8);
                    t_rssdetalle_fr.this.mTargetView.removeView(t_rssdetalle_fr.this.mCustomView);
                    t_rssdetalle_fr.this.mCustomView = null;
                    t_rssdetalle_fr.this.mTargetView.setVisibility(8);
                    t_rssdetalle_fr.this.mCustomViewCallback.onCustomViewHidden();
                    t_rssdetalle_fr.this.mContentView.setVisibility(0);
                }
            }
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    class C08282 implements OnTouchListener {
        C08282() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                case 1:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    class C08293 implements DownloadListener {
        C08293() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            t_rssdetalle_fr.this.startActivity(i);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getActivity().getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.f67v = inflater.inflate(C0627R.layout.rssdetalle, container, false);
        this.extras = getActivity().getIntent().getExtras();
        if (this.globales.secciones_a[this.globales.ind_secc_sel_2].linksexternos == 1) {
            z = true;
        } else {
            z = false;
        }
        this.linksexternos = z;
        this.myWebView = (WebView) this.f67v.findViewById(C0627R.id.webview);
        WebView webView = this.myWebView;
        WebChromeClient c08271 = new C08271();
        this.mClient = c08271;
        webView.setWebChromeClient(c08271);
        wv_set();
        this.myWebView.getSettings().setBuiltInZoomControls(true);
        this.myWebView.getSettings().setSupportZoom(true);
        this.myWebView.getSettings().setDomStorageEnabled(true);
        this.myWebView.setOnTouchListener(new C08282());
        this.myWebView.setDownloadListener(new C08293());
        if (this.globales.secciones_a[this.globales.ind_secc_sel_2].adaptar_ancho) {
            this.myWebView.getSettings().setUseWideViewPort(true);
            this.myWebView.getSettings().setLoadWithOverviewMode(true);
        }
        final ProgressBar progess = (ProgressBar) this.f67v.findViewById(C0627R.id.pb_url);
        if (VERSION.SDK_INT > 20) {
            config.progress_color(progess, this.globales.c_icos);
        }
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progess.setVisibility(0);
            }

            public void onPageFinished(WebView view, String url) {
                progess.setVisibility(8);
                if (t_rssdetalle_fr.this.limpiar_hist) {
                    view.clearHistory();
                    t_rssdetalle_fr.this.limpiar_hist = false;
                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String url_l = url.toLowerCase();
                String[] gdocs_ext = new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "pages", "ai", "psd", "tiff", "dxf", "svg", "eps", "ps", "ttf", "otf", "xps", "zip", "rar"};
                String url_ext = "";
                int pos_aux = url.lastIndexOf(".");
                if (pos_aux != -1) {
                    url_ext = url_l.substring(pos_aux);
                }
                url_ext = url_ext.replace(".", "");
                String tel;
                if (url_l.startsWith("tel:") || url_l.startsWith("http://tel:")) {
                    if (url_l.startsWith("tel:")) {
                        tel = url.substring(4);
                    } else {
                        tel = url.substring(11);
                    }
                    if (tel.endsWith("/")) {
                        tel = tel.substring(0, tel.length() - 1);
                    }
                    try {
                        t_rssdetalle_fr.this.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel)));
                    } catch (Exception e) {
                    }
                    return true;
                } else if (url_l.startsWith("mailto:") || url_l.startsWith("http://mailto:")) {
                    String email;
                    String subject = "";
                    texte = "";
                    if (url_l.startsWith("mailto:")) {
                        email = url.substring(7).trim();
                    } else {
                        email = url.substring(14).trim();
                    }
                    if (!email.equals("")) {
                        pos = email.indexOf("?");
                        if (pos > 0) {
                            pos2 = email.indexOf("?subject=");
                            if (pos2 != -1) {
                                subject = email.substring(pos2 + 9).trim();
                                pos2 = subject.indexOf("&body=");
                                if (pos2 != -1) {
                                    texte = subject.substring(pos2 + 6).trim();
                                    subject = subject.substring(0, pos2).trim();
                                }
                            }
                            email = email.substring(0, pos).trim();
                        }
                        if (email.endsWith("/")) {
                            email = email.substring(0, email.length() - 1);
                        }
                        Intent emailIntent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", email, null));
                        if (!subject.equals("")) {
                            try {
                                subject = URLDecoder.decode(subject, "UTF-8");
                            } catch (Exception e2) {
                            }
                            emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
                        }
                        if (!texte.equals("")) {
                            try {
                                texte = URLDecoder.decode(texte, "UTF-8");
                            } catch (Exception e3) {
                            }
                            emailIntent.putExtra("android.intent.extra.TEXT", texte);
                        }
                        t_rssdetalle_fr.this.startActivity(Intent.createChooser(emailIntent, t_rssdetalle_fr.this.getResources().getString(C0627R.string.enviar_email)));
                    }
                    return true;
                } else if (url_l.startsWith("smsto:") || url_l.startsWith("http://smsto:")) {
                    texte = "";
                    if (url_l.startsWith("smsto:")) {
                        tel = url.substring(6).trim();
                    } else {
                        tel = url.substring(13).trim();
                    }
                    if (!tel.equals("")) {
                        pos = tel.indexOf("?");
                        if (pos > 0) {
                            pos2 = tel.indexOf("?body=");
                            if (pos2 != -1) {
                                texte = tel.substring(pos2 + 6).trim();
                            }
                            tel = tel.substring(0, pos).trim();
                        }
                        if (tel.endsWith("/")) {
                            tel = tel.substring(0, tel.length() - 1);
                        }
                        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + tel));
                        if (!texte.equals("")) {
                            try {
                                texte = URLDecoder.decode(texte, "UTF-8");
                            } catch (Exception e4) {
                            }
                            intent.putExtra("sms_body", texte);
                        }
                        try {
                            t_rssdetalle_fr.this.startActivity(intent);
                        } catch (Exception e5) {
                        }
                    }
                    return true;
                } else if (url_l.startsWith("go:") || url_l.startsWith("http://go:")) {
                    String idgo;
                    if (url_l.startsWith("go:")) {
                        idgo = url.substring(3);
                    } else {
                        idgo = url.substring(10);
                    }
                    if (idgo.endsWith("/")) {
                        idgo = idgo.substring(0, idgo.length() - 1);
                    }
                    try {
                        idgo = URLDecoder.decode(idgo, "UTF-8");
                    } catch (Exception e6) {
                    }
                    for (int i = 0; i < t_rssdetalle_fr.this.globales.secciones_a.length; i++) {
                        if (t_rssdetalle_fr.this.globales.secciones_a[i].idgo.equalsIgnoreCase(idgo)) {
                            t_rssdetalle_fr.this.startActivityForResult(t_rssdetalle_fr.this.globales.crear_rgi(Integer.valueOf(i), t_rssdetalle_fr.this.f67v.getContext()).f34i, 0);
                            return true;
                        }
                    }
                    t_rssdetalle_fr.this.primera = false;
                    return false;
                } else if (url_l.startsWith("vnd.youtube:")) {
                    int n = url.indexOf("?");
                    String idvideo = "";
                    if (n > 0) {
                        idvideo = url.substring(12, n);
                    } else {
                        idvideo = url.substring(12);
                    }
                    url_aux = "http://www.youtube.com/watch?v=" + idvideo;
                    if (t_rssdetalle_fr.this.linksexternos) {
                        t_rssdetalle_fr.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url_aux)));
                    } else {
                        t_rssdetalle_fr.this.primera = false;
                        t_rssdetalle_fr.this.myWebView.loadUrl(url_aux);
                    }
                    return true;
                } else if (url_l.endsWith(".mp3")) {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(url), "audio/*");
                    view.getContext().startActivity(intent);
                    return true;
                } else if (url_l.endsWith(".mp4") || url_l.endsWith(".3gp")) {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setDataAndType(Uri.parse(url), "video/*");
                    view.getContext().startActivity(intent);
                    return true;
                } else if (t_rssdetalle_fr.this.linksexternos || url_l.startsWith("rtsp://") || url_l.startsWith("rtmp://") || url_l.startsWith("market://") || url_l.endsWith(".apk") || url_l.startsWith("whatsapp://") || url_l.endsWith(".m3u") || url_l.endsWith(".m3u8")) {
                    try {
                        t_rssdetalle_fr.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        return true;
                    } catch (Exception e7) {
                        return false;
                    }
                } else if (!Arrays.asList(gdocs_ext).contains(url_ext) || url_l.contains("docs.google.com")) {
                    t_rssdetalle_fr.this.primera = false;
                    return false;
                } else if (t_rssdetalle_fr.this.myWebView.getUrl().contains("docs.google.com")) {
                    t_rssdetalle_fr.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                    return true;
                } else {
                    url_aux = "";
                    try {
                        url_aux = URLEncoder.encode(url, "utf-8");
                    } catch (Exception e8) {
                        url_aux = url;
                    }
                    t_rssdetalle_fr.this.myWebView.loadUrl("http://docs.google.com/viewer?embedded=true&url=" + url_aux);
                    t_rssdetalle_fr.this.primera = false;
                    return true;
                }
            }
        });
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.getSettings().setPluginState(PluginState.ON);
        this.myWebView.getSettings().setUserAgentString(this.myWebView.getSettings().getUserAgentString() + " Vinebre");
        return this.f67v;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.myWebView.restoreState(savedInstanceState);
        } else if (!(this.extras == null || this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL) == null)) {
            this.myWebView.loadUrl(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL));
        }
        this.mContentView = (LinearLayout) getActivity().findViewById(C0627R.id.ll_princ);
        this.mTargetView = (FrameLayout) getActivity().findViewById(C0627R.id.target_view);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mCustomView != null) {
                this.mClient.onHideCustomView();
                return true;
            } else if (!this.primera && this.myWebView.canGoBack()) {
                this.myWebView.goBack();
                return true;
            }
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            getActivity().setResult(-1, data);
            getActivity().finish();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myWebView.saveState(outState);
    }

    public void actContenido(String url) {
        if (this.f67v != null) {
            this.primera = true;
            this.limpiar_hist = true;
            this.myWebView.loadUrl(url);
        }
    }

    public void onResume() {
        super.onResume();
        this.myWebView.onResume();
    }

    public void onPause() {
        if (this.mCustomView != null) {
            this.mClient.onHideCustomView();
        }
        super.onPause();
        this.myWebView.onPause();
        if (this.finalizar || getActivity().isFinishing()) {
            try {
                this.myWebView.loadData("", "text/html", "utf-8");
            } catch (Exception e) {
            }
        }
    }

    @TargetApi(21)
    private void wv_set() {
        if (VERSION.SDK_INT > 20) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.myWebView, true);
        }
    }
}
