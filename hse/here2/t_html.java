package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import java.io.File;
import java.util.Locale;
import java.util.regex.Pattern;

public class t_html extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    Builder adb;
    boolean atras_pulsado = false;
    String cbtn;
    boolean cl_history = false;
    String contra;
    boolean descargar;
    ProgressDialog dialog_cargando;
    boolean es_root;
    EditText et_contra;
    EditText et_usu;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    HttpAuthHandler handler_glob;
    boolean history_cleared = false;
    String html;
    int ind;
    int ind_abrir_secc = -1;
    int linksexternos = 0;
    boolean loader = true;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    private WebChromeClient mClient;
    private LinearLayout mContentView;
    private View mCustomView;
    private CustomViewCallback mCustomViewCallback;
    ListView mDrawerList;
    private ValueCallback<Uri[]> mFilePathCallback;
    private FrameLayout mTargetView;
    private ValueCallback<Uri> mUploadMessage;
    WebView myWebView;
    boolean primer_load = true;
    String usu;
    View v_abrir_secc;
    boolean zoom = true;

    class C08001 extends WebChromeClient {

        class C07991 extends WebViewClient {
            C07991() {
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                browserIntent.addCategory("android.intent.category.BROWSABLE");
                t_html.this.startActivity(browserIntent);
                return true;
            }
        }

        C08001() {
        }

        public boolean onJsBeforeUnload(WebView v, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView tempWebView = new WebView(t_html.this);
            tempWebView.setWebViewClient(new C07991());
            resultMsg.obj.setWebView(tempWebView);
            resultMsg.sendToTarget();
            return true;
        }

        @TargetApi(11)
        public void onShowCustomView(View view, CustomViewCallback callback) {
            t_html.this.mCustomViewCallback = callback;
            t_html.this.mTargetView.addView(view);
            t_html.this.mCustomView = view;
            t_html.this.mContentView.setVisibility(8);
            t_html.this.mTargetView.setVisibility(0);
            if (VERSION.SDK_INT < 16) {
                t_html.this.getWindow().setFlags(1024, 1024);
            } else {
                t_html.this.getWindow().getDecorView().setSystemUiVisibility(4);
            }
        }

        @TargetApi(11)
        public void onHideCustomView() {
            if (VERSION.SDK_INT < 16) {
                t_html.this.getWindow().clearFlags(1024);
            } else {
                t_html.this.getWindow().getDecorView().setSystemUiVisibility(0);
            }
            if (t_html.this.mCustomView != null) {
                t_html.this.mCustomView.setVisibility(8);
                t_html.this.mTargetView.removeView(t_html.this.mCustomView);
                t_html.this.mCustomView = null;
                t_html.this.mTargetView.setVisibility(8);
                t_html.this.mCustomViewCallback.onCustomViewHidden();
                t_html.this.mContentView.setVisibility(0);
            }
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            callback.invoke(origin, true, false);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            t_html.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_html.this.startActivityForResult(Intent.createChooser(i, t_html.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            t_html.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_html.this.startActivityForResult(Intent.createChooser(i, t_html.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            t_html.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_html.this.startActivityForResult(Intent.createChooser(i, t_html.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (t_html.this.mFilePathCallback != null) {
                t_html.this.mFilePathCallback.onReceiveValue(null);
            }
            t_html.this.mFilePathCallback = filePathCallback;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_html.this.startActivityForResult(Intent.createChooser(i, t_html.this.getResources().getString(C0627R.string.selecciona)), 10);
            return true;
        }
    }

    class C08012 implements OnTouchListener {
        C08012() {
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

    class C08023 implements DownloadListener {
        C08023() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (t_html.this.descargar || url.endsWith(".apk")) {
                String nombre_aux = "";
                try {
                    nombre_aux = URLUtil.guessFileName(url, contentDisposition, mimetype);
                } catch (Exception e) {
                }
                t_html.this.globales.fdescargar(url, mimetype, nombre_aux);
                return;
            }
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            t_html.this.startActivity(i);
        }
    }

    class C08075 implements OnItemClickListener {
        C08075() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_html.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_html.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_html.this.globales.menu_a_secciones[position]));
            t_html.this.onClick(view);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        this.extras = getIntent().getExtras();
        this.ind = this.extras.getInt("idsecc");
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.ind].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_url);
        if (savedInstanceState == null) {
            if (this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false)) {
                z = true;
            } else {
                z = false;
            }
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        incluir_menu_pre();
        this.html = this.globales.secciones_a[this.ind].html;
        config hse_here2_config = this.globales;
        if (this.html.contains("youtube.com") || this.html.contains("youtu.be")) {
            z = true;
        } else {
            z = false;
        }
        this.adView = hse_here2_config.mostrar_banner(this, z);
        if (this.globales.ind_secc_sel_2 != -1) {
            this.linksexternos = this.globales.secciones_a[this.globales.ind_secc_sel_2].linksexternos;
            this.loader = this.globales.secciones_a[this.globales.ind_secc_sel_2].loader;
            this.zoom = this.globales.secciones_a[this.globales.ind_secc_sel_2].zoom;
            this.descargar = this.globales.secciones_a[this.globales.ind_secc_sel_2].descargar;
        } else {
            this.linksexternos = 0;
            this.loader = true;
            this.zoom = true;
            this.descargar = false;
        }
        this.myWebView = (WebView) findViewById(C0627R.id.webview);
        if (this.linksexternos == 2) {
            this.myWebView.getSettings().setSupportMultipleWindows(true);
        }
        WebView webView = this.myWebView;
        WebChromeClient c08001 = new C08001();
        this.mClient = c08001;
        webView.setWebChromeClient(c08001);
        wv_set();
        this.myWebView.getSettings().setBuiltInZoomControls(this.zoom);
        this.myWebView.getSettings().setSupportZoom(this.zoom);
        this.myWebView.getSettings().setDomStorageEnabled(true);
        this.myWebView.setOnTouchListener(new C08012());
        this.myWebView.setDownloadListener(new C08023());
        if (this.globales.ind_secc_sel_2 != -1 && this.globales.secciones_a[this.globales.ind_secc_sel_2].adaptar_ancho) {
            this.myWebView.getSettings().setUseWideViewPort(true);
            this.myWebView.getSettings().setLoadWithOverviewMode(true);
        }
        final ProgressBar progess = (ProgressBar) findViewById(C0627R.id.pb_url);
        if (VERSION.SDK_INT > 20) {
            config.progress_color(progess, this.globales.c_icos);
        }
        this.myWebView.setWebViewClient(new WebViewClient() {

            class C08031 implements DialogInterface.OnClickListener {
                C08031() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    t_html.this.usu = t_html.this.et_usu.getText().toString();
                    t_html.this.contra = t_html.this.et_contra.getText().toString();
                    t_html.this.handler_glob.proceed(t_html.this.usu, t_html.this.contra);
                }
            }

            class C08042 implements DialogInterface.OnClickListener {
                C08042() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    t_html.this.handler_glob.cancel();
                }
            }

            class C08053 implements OnCancelListener {
                C08053() {
                }

                public void onCancel(DialogInterface dialog) {
                    t_html.this.handler_glob.cancel();
                }
            }

            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                t_html.this.handler_glob = handler;
                t_html.this.adb = new Builder(t_html.this);
                t_html.this.adb.setTitle(t_html.this.getResources().getString(C0627R.string.chat_acceso));
                View eulaLayout = t_html.this.getLayoutInflater().inflate(C0627R.layout.usucontra, null);
                t_html.this.et_usu = (EditText) eulaLayout.findViewById(C0627R.id.et_usu);
                t_html.this.et_contra = (EditText) eulaLayout.findViewById(C0627R.id.et_contra);
                t_html.this.adb.setView(eulaLayout);
                t_html.this.adb.setCancelable(true);
                t_html.this.adb.setPositiveButton(t_html.this.getString(C0627R.string.aceptar), new C08031());
                t_html.this.adb.setNegativeButton(t_html.this.getString(C0627R.string.cancelar), new C08042());
                t_html.this.adb.setOnCancelListener(new C08053());
                t_html.this.adb.create().show();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (!config.isNetworkAvailable(t_html.this)) {
                    if (t_html.this.globales.wv_sinconex_txt.equals("")) {
                        t_html.this.myWebView.loadData("<html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body>" + t_html.this.getResources().getString(C0627R.string.wv_sinconex) + "</body></html>", "text/html; charset=utf-8", "utf-8");
                    } else {
                        String str_aux = t_html.this.globales.wv_sinconex_txt;
                        if (!str_aux.toLowerCase(Locale.getDefault()).contains("<html")) {
                            str_aux = "<html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body>" + str_aux + "</body></html>";
                        }
                        t_html.this.myWebView.loadData(str_aux, "text/html; charset=utf-8", "utf-8");
                    }
                    t_html.this.myWebView.clearHistory();
                    t_html.this.history_cleared = true;
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!(t_html.this.finalizar || t_html.this.isFinishing() || t_html.this.html.contains("youtube.com") || t_html.this.html.contains("youtu.be"))) {
                    boolean aux1 = false;
                    if (t_html.this.primer_load) {
                        if (t_html.this.extras == null || !t_html.this.extras.containsKey("ad_entrar")) {
                            aux1 = false;
                        } else {
                            aux1 = true;
                        }
                        t_html.this.primer_load = false;
                    }
                    t_html.this.globales.toca_int(t_html.this, aux1);
                }
                if (t_html.this.loader) {
                    progess.setVisibility(0);
                }
            }

            public void onPageFinished(WebView view, String url) {
                progess.setVisibility(8);
                if (t_html.this.cl_history) {
                    t_html.this.cl_history = false;
                    t_html.this.myWebView.clearHistory();
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean shouldOverrideUrlLoading(android.webkit.WebView r40, java.lang.String r41) {
                /*
                r39 = this;
                r37 = r41.toLowerCase();
                r2 = 20;
                r14 = new java.lang.String[r2];
                r2 = 0;
                r3 = "doc";
                r14[r2] = r3;
                r2 = 1;
                r3 = "docx";
                r14[r2] = r3;
                r2 = 2;
                r3 = "xls";
                r14[r2] = r3;
                r2 = 3;
                r3 = "xlsx";
                r14[r2] = r3;
                r2 = 4;
                r3 = "ppt";
                r14[r2] = r3;
                r2 = 5;
                r3 = "pptx";
                r14[r2] = r3;
                r2 = 6;
                r3 = "pdf";
                r14[r2] = r3;
                r2 = 7;
                r3 = "pages";
                r14[r2] = r3;
                r2 = 8;
                r3 = "ai";
                r14[r2] = r3;
                r2 = 9;
                r3 = "psd";
                r14[r2] = r3;
                r2 = 10;
                r3 = "tiff";
                r14[r2] = r3;
                r2 = 11;
                r3 = "dxf";
                r14[r2] = r3;
                r2 = 12;
                r3 = "svg";
                r14[r2] = r3;
                r2 = 13;
                r3 = "eps";
                r14[r2] = r3;
                r2 = 14;
                r3 = "ps";
                r14[r2] = r3;
                r2 = 15;
                r3 = "ttf";
                r14[r2] = r3;
                r2 = 16;
                r3 = "otf";
                r14[r2] = r3;
                r2 = 17;
                r3 = "xps";
                r14[r2] = r3;
                r2 = 18;
                r3 = "zip";
                r14[r2] = r3;
                r2 = 19;
                r3 = "rar";
                r14[r2] = r3;
                r36 = "";
                r2 = ".";
                r0 = r41;
                r28 = r0.lastIndexOf(r2);
                r2 = -1;
                r0 = r28;
                if (r0 == r2) goto L_0x008f;
            L_0x0087:
                r0 = r37;
                r1 = r28;
                r36 = r0.substring(r1);
            L_0x008f:
                r2 = ".";
                r3 = "";
                r0 = r36;
                r36 = r0.replace(r2, r3);
                r2 = "tel:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x00ad;
            L_0x00a3:
                r2 = "http://tel:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0107;
            L_0x00ad:
                r2 = "tel:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x00fe;
            L_0x00b7:
                r2 = 4;
                r0 = r41;
                r32 = r0.substring(r2);
            L_0x00be:
                r2 = "/";
                r0 = r32;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x00d5;
            L_0x00c8:
                r2 = 0;
                r3 = r32.length();
                r3 = r3 + -1;
                r0 = r32;
                r32 = r0.substring(r2, r3);
            L_0x00d5:
                r10 = new android.content.Intent;
                r2 = "android.intent.action.DIAL";
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r5 = "tel:";
                r3 = r3.append(r5);
                r0 = r32;
                r3 = r3.append(r0);
                r3 = r3.toString();
                r3 = android.net.Uri.parse(r3);
                r10.<init>(r2, r3);
                r0 = r39;
                r2 = hse.here2.t_html.this;	 Catch:{ Exception -> 0x0706 }
                r2.startActivity(r10);	 Catch:{ Exception -> 0x0706 }
            L_0x00fc:
                r2 = 1;
            L_0x00fd:
                return r2;
            L_0x00fe:
                r2 = 11;
                r0 = r41;
                r32 = r0.substring(r2);
                goto L_0x00be;
            L_0x0107:
                r2 = "mailto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x011b;
            L_0x0111:
                r2 = "http://mailto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x02be;
            L_0x011b:
                r31 = "";
                r33 = "";
                r2 = "mailto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0212;
            L_0x0129:
                r2 = 7;
                r0 = r41;
                r2 = r0.substring(r2);
                r12 = r2.trim();
            L_0x0134:
                r2 = "";
                r2 = r12.equals(r2);
                if (r2 != 0) goto L_0x02bb;
            L_0x013c:
                r2 = "?";
                r26 = r12.indexOf(r2);
                if (r26 <= 0) goto L_0x0224;
            L_0x0144:
                r2 = "?subject=";
                r27 = r12.indexOf(r2);
                r2 = -1;
                r0 = r27;
                if (r0 == r2) goto L_0x017f;
            L_0x014f:
                r2 = r27 + 9;
                r2 = r12.substring(r2);
                r31 = r2.trim();
                r2 = "&body=";
                r0 = r31;
                r27 = r0.indexOf(r2);
                r2 = -1;
                r0 = r27;
                if (r0 == r2) goto L_0x017f;
            L_0x0166:
                r2 = r27 + 6;
                r0 = r31;
                r2 = r0.substring(r2);
                r33 = r2.trim();
                r2 = 0;
                r0 = r31;
                r1 = r27;
                r2 = r0.substring(r2, r1);
                r31 = r2.trim();
            L_0x017f:
                r2 = 0;
                r0 = r26;
                r2 = r12.substring(r2, r0);
                r12 = r2.trim();
                r2 = "";
                r0 = r33;
                r2 = r0.equals(r2);
                if (r2 == 0) goto L_0x0224;
            L_0x0194:
                r2 = android.net.Uri.parse(r41);	 Catch:{ Exception -> 0x0220 }
                r29 = r2.getQuery();	 Catch:{ Exception -> 0x0220 }
                r2 = "&";
                r0 = r29;
                r24 = r0.split(r2);	 Catch:{ Exception -> 0x0220 }
                r9 = r24;
                r0 = r9.length;	 Catch:{ Exception -> 0x0220 }
                r21 = r0;
                r16 = 0;
            L_0x01ab:
                r0 = r16;
                r1 = r21;
                if (r0 >= r1) goto L_0x0224;
            L_0x01b1:
                r23 = r9[r16];	 Catch:{ Exception -> 0x0220 }
                r2 = "=";
                r0 = r23;
                r19 = r0.indexOf(r2);	 Catch:{ Exception -> 0x0220 }
                r2 = 0;
                r0 = r23;
                r1 = r19;
                r2 = r0.substring(r2, r1);	 Catch:{ Exception -> 0x0220 }
                r3 = "UTF-8";
                r25 = java.net.URLDecoder.decode(r2, r3);	 Catch:{ Exception -> 0x0220 }
                r2 = r19 + 1;
                r0 = r23;
                r2 = r0.substring(r2);	 Catch:{ Exception -> 0x0220 }
                r3 = "UTF-8";
                r38 = java.net.URLDecoder.decode(r2, r3);	 Catch:{ Exception -> 0x0220 }
                r2 = "subject";
                r0 = r25;
                r2 = r0.equalsIgnoreCase(r2);	 Catch:{ Exception -> 0x0220 }
                if (r2 != 0) goto L_0x020f;
            L_0x01e2:
                r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0220 }
                r2.<init>();	 Catch:{ Exception -> 0x0220 }
                r0 = r33;
                r2 = r2.append(r0);	 Catch:{ Exception -> 0x0220 }
                r3 = "<b>";
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x0220 }
                r0 = r25;
                r2 = r2.append(r0);	 Catch:{ Exception -> 0x0220 }
                r3 = "</b>: ";
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x0220 }
                r0 = r38;
                r2 = r2.append(r0);	 Catch:{ Exception -> 0x0220 }
                r3 = "<br>";
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x0220 }
                r33 = r2.toString();	 Catch:{ Exception -> 0x0220 }
            L_0x020f:
                r16 = r16 + 1;
                goto L_0x01ab;
            L_0x0212:
                r2 = 14;
                r0 = r41;
                r2 = r0.substring(r2);
                r12 = r2.trim();
                goto L_0x0134;
            L_0x0220:
                r11 = move-exception;
                r11.printStackTrace();
            L_0x0224:
                r2 = "/";
                r2 = r12.endsWith(r2);
                if (r2 == 0) goto L_0x0237;
            L_0x022c:
                r2 = 0;
                r3 = r12.length();
                r3 = r3 + -1;
                r12 = r12.substring(r2, r3);
            L_0x0237:
                r13 = new android.content.Intent;
                r2 = "android.intent.action.SENDTO";
                r3 = "mailto";
                r5 = 0;
                r3 = android.net.Uri.fromParts(r3, r12, r5);
                r13.<init>(r2, r3);
                r2 = "";
                r0 = r31;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x025e;
            L_0x024f:
                r2 = "UTF-8";
                r0 = r31;
                r31 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x0703 }
            L_0x0257:
                r2 = "android.intent.extra.SUBJECT";
                r0 = r31;
                r13.putExtra(r2, r0);
            L_0x025e:
                r2 = "";
                r0 = r33;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x02a1;
            L_0x0268:
                r2 = "UTF-8";
                r0 = r33;
                r33 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x0700 }
            L_0x0270:
                r2 = "android.intent.extra.TEXT";
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r5 = new java.lang.StringBuilder;
                r5.<init>();
                r6 = "<p>";
                r5 = r5.append(r6);
                r0 = r33;
                r5 = r5.append(r0);
                r6 = "</p>";
                r5 = r5.append(r6);
                r5 = r5.toString();
                r3 = r3.append(r5);
                r3 = r3.toString();
                r3 = android.text.Html.fromHtml(r3);
                r13.putExtra(r2, r3);
            L_0x02a1:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r0 = r39;
                r3 = hse.here2.t_html.this;
                r3 = r3.getResources();
                r5 = 2131361974; // 0x7f0a00b6 float:1.8343715E38 double:1.05303273E-314;
                r3 = r3.getString(r5);
                r3 = android.content.Intent.createChooser(r13, r3);
                r2.startActivity(r3);
            L_0x02bb:
                r2 = 1;
                goto L_0x00fd;
            L_0x02be:
                r2 = "smsto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x02d2;
            L_0x02c8:
                r2 = "http://smsto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0393;
            L_0x02d2:
                r33 = "";
                r2 = "smsto:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0385;
            L_0x02de:
                r2 = 6;
                r0 = r41;
                r2 = r0.substring(r2);
                r32 = r2.trim();
            L_0x02e9:
                r2 = "";
                r0 = r32;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x0382;
            L_0x02f3:
                r2 = "?";
                r0 = r32;
                r26 = r0.indexOf(r2);
                if (r26 <= 0) goto L_0x0323;
            L_0x02fd:
                r2 = "?body=";
                r0 = r32;
                r27 = r0.indexOf(r2);
                r2 = -1;
                r0 = r27;
                if (r0 == r2) goto L_0x0316;
            L_0x030a:
                r2 = r27 + 6;
                r0 = r32;
                r2 = r0.substring(r2);
                r33 = r2.trim();
            L_0x0316:
                r2 = 0;
                r0 = r32;
                r1 = r26;
                r2 = r0.substring(r2, r1);
                r32 = r2.trim();
            L_0x0323:
                r2 = "/";
                r0 = r32;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x033a;
            L_0x032d:
                r2 = 0;
                r3 = r32.length();
                r3 = r3 + -1;
                r0 = r32;
                r32 = r0.substring(r2, r3);
            L_0x033a:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "smsto:";
                r2 = r2.append(r3);
                r0 = r32;
                r2 = r2.append(r0);
                r2 = r2.toString();
                r34 = android.net.Uri.parse(r2);
                r30 = new android.content.Intent;
                r2 = "android.intent.action.SENDTO";
                r0 = r30;
                r1 = r34;
                r0.<init>(r2, r1);
                r2 = "";
                r0 = r33;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x0379;
            L_0x0368:
                r2 = "UTF-8";
                r0 = r33;
                r33 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x06fd }
            L_0x0370:
                r2 = "sms_body";
                r0 = r30;
                r1 = r33;
                r0.putExtra(r2, r1);
            L_0x0379:
                r0 = r39;
                r2 = hse.here2.t_html.this;	 Catch:{ Exception -> 0x06fa }
                r0 = r30;
                r2.startActivity(r0);	 Catch:{ Exception -> 0x06fa }
            L_0x0382:
                r2 = 1;
                goto L_0x00fd;
            L_0x0385:
                r2 = 13;
                r0 = r41;
                r2 = r0.substring(r2);
                r32 = r2.trim();
                goto L_0x02e9;
            L_0x0393:
                r2 = "go:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x03a7;
            L_0x039d:
                r2 = "http://go:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0502;
            L_0x03a7:
                r2 = "go:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0435;
            L_0x03b1:
                r2 = 3;
                r0 = r41;
                r17 = r0.substring(r2);
            L_0x03b8:
                r2 = "/";
                r0 = r17;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x03cf;
            L_0x03c2:
                r2 = 0;
                r3 = r17.length();
                r3 = r3 + -1;
                r0 = r17;
                r17 = r0.substring(r2, r3);
            L_0x03cf:
                r2 = "UTF-8";
                r0 = r17;
                r17 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x06f7 }
            L_0x03d7:
                r15 = 0;
            L_0x03d8:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.secciones_a;
                r2 = r2.length;
                if (r15 >= r2) goto L_0x04ff;
            L_0x03e3:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.secciones_a;
                r2 = r2[r15];
                r2 = r2.idgo;
                r0 = r17;
                r2 = r2.equalsIgnoreCase(r0);
                if (r2 == 0) goto L_0x04fb;
            L_0x03f7:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                if (r2 == 0) goto L_0x0411;
            L_0x0401:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 == 0) goto L_0x043f;
            L_0x0411:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                if (r2 == 0) goto L_0x042b;
            L_0x041b:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 == 0) goto L_0x043f;
            L_0x042b:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2.abrir_go(r15);
            L_0x0432:
                r2 = 1;
                goto L_0x00fd;
            L_0x0435:
                r2 = 10;
                r0 = r41;
                r17 = r0.substring(r2);
                goto L_0x03b8;
            L_0x043f:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                if (r2 == 0) goto L_0x0470;
            L_0x0449:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 != 0) goto L_0x0470;
            L_0x0459:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r3 = new com.appnext.ads.fullscreen.RewardedVideo;
                r0 = r39;
                r5 = hse.here2.t_html.this;
                r0 = r39;
                r6 = hse.here2.t_html.this;
                r6 = r6.globales;
                r6 = r6.appnext_rew_cod;
                r3.<init>(r5, r6);
                r2.mAd_appnext = r3;
            L_0x0470:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                if (r2 == 0) goto L_0x0498;
            L_0x047a:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 != 0) goto L_0x0498;
            L_0x048a:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r0 = r39;
                r3 = hse.here2.t_html.this;
                r3 = com.google.android.gms.ads.MobileAds.getRewardedVideoAdInstance(r3);
                r2.mAd = r3;
            L_0x0498:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r3 = new android.app.ProgressDialog;
                r0 = r39;
                r5 = hse.here2.t_html.this;
                r3.<init>(r5);
                r2.dialog_cargando = r3;
                r4 = new android.view.View;
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r4.<init>(r2);
                r4.setId(r15);
                r2 = 2131165187; // 0x7f070003 float:1.7944584E38 double:1.0529355045E-314;
                r3 = java.lang.Integer.valueOf(r15);
                r4.setTag(r2, r3);
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r3 = 0;
                r2.v_abrir_secc = r3;
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2.ind_abrir_secc = r15;
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r0 = r39;
                r3 = hse.here2.t_html.this;
                r0 = r39;
                r5 = hse.here2.t_html.this;
                r5 = r5.cbtn;
                r0 = r39;
                r6 = hse.here2.t_html.this;
                r6 = r6.dialog_cargando;
                r0 = r39;
                r7 = hse.here2.t_html.this;
                r7 = r7.mAd;
                r0 = r39;
                r8 = hse.here2.t_html.this;
                r8 = r8.mAd_appnext;
                r2 = r2.rewarded(r3, r4, r5, r6, r7, r8);
                if (r2 != 0) goto L_0x0432;
            L_0x04f2:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2.abrir_go(r15);
                goto L_0x0432;
            L_0x04fb:
                r15 = r15 + 1;
                goto L_0x03d8;
            L_0x04ff:
                r2 = 0;
                goto L_0x00fd;
            L_0x0502:
                r2 = "vnd.youtube:";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0570;
            L_0x050c:
                r2 = "?";
                r0 = r41;
                r22 = r0.indexOf(r2);
                r18 = "";
                if (r22 <= 0) goto L_0x055b;
            L_0x0518:
                r2 = 12;
                r0 = r41;
                r1 = r22;
                r18 = r0.substring(r2, r1);
            L_0x0522:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "http://www.youtube.com/watch?v=";
                r2 = r2.append(r3);
                r0 = r18;
                r2 = r2.append(r0);
                r35 = r2.toString();
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.linksexternos;
                r3 = 1;
                if (r2 != r3) goto L_0x0564;
            L_0x0540:
                r34 = android.net.Uri.parse(r35);
                r20 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r20;
                r1 = r34;
                r0.<init>(r2, r1);
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r0 = r20;
                r2.startActivity(r0);
            L_0x0558:
                r2 = 1;
                goto L_0x00fd;
            L_0x055b:
                r2 = 12;
                r0 = r41;
                r18 = r0.substring(r2);
                goto L_0x0522;
            L_0x0564:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.myWebView;
                r0 = r35;
                r2.loadUrl(r0);
                goto L_0x0558;
            L_0x0570:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.descargar;
                if (r2 == 0) goto L_0x059e;
            L_0x0578:
                r2 = ".mp3";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x058c;
            L_0x0582:
                r2 = ".mp4";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x059e;
            L_0x058c:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.globales;
                r3 = "";
                r5 = "";
                r0 = r41;
                r2.fdescargar(r0, r3, r5);
                r2 = 1;
                goto L_0x00fd;
            L_0x059e:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x05d0;
            L_0x05a6:
                r2 = ".mp3";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x05d0;
            L_0x05b0:
                r20 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r20;
                r0.<init>(r2);
                r2 = android.net.Uri.parse(r41);
                r3 = "audio/*";
                r0 = r20;
                r0.setDataAndType(r2, r3);
                r2 = r40.getContext();
                r0 = r20;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x00fd;
            L_0x05d0:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x060c;
            L_0x05d8:
                r2 = ".mp4";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x05ec;
            L_0x05e2:
                r2 = ".3gp";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x060c;
            L_0x05ec:
                r20 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r20;
                r0.<init>(r2);
                r2 = android.net.Uri.parse(r41);
                r3 = "video/*";
                r0 = r20;
                r0.setDataAndType(r2, r3);
                r2 = r40.getContext();
                r0 = r20;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x00fd;
            L_0x060c:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.linksexternos;
                r3 = 1;
                if (r2 == r3) goto L_0x065b;
            L_0x0615:
                r2 = "rtsp://";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x061f:
                r2 = "rtmp://";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x0629:
                r2 = "market://";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x0633:
                r2 = "whatsapp://";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x063d:
                r2 = ".m3u";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x0647:
                r2 = ".m3u8";
                r0 = r37;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x065b;
            L_0x0651:
                r2 = "http://www.androidcreator.com/open";
                r0 = r37;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x067a;
            L_0x065b:
                r34 = android.net.Uri.parse(r41);
                r20 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r20;
                r1 = r34;
                r0.<init>(r2, r1);
                r0 = r39;
                r2 = hse.here2.t_html.this;	 Catch:{ Exception -> 0x0676 }
                r0 = r20;
                r2.startActivity(r0);	 Catch:{ Exception -> 0x0676 }
                r2 = 1;
                goto L_0x00fd;
            L_0x0676:
                r11 = move-exception;
                r2 = 0;
                goto L_0x00fd;
            L_0x067a:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x06f4;
            L_0x0682:
                r2 = java.util.Arrays.asList(r14);
                r0 = r36;
                r2 = r2.contains(r0);
                if (r2 == 0) goto L_0x06f4;
            L_0x068e:
                r2 = "docs.google.com";
                r0 = r37;
                r2 = r0.contains(r2);
                if (r2 != 0) goto L_0x06f4;
            L_0x0698:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.myWebView;
                r2 = r2.getUrl();
                r3 = "docs.google.com";
                r2 = r2.contains(r3);
                if (r2 != 0) goto L_0x06d9;
            L_0x06aa:
                r35 = "";
                r2 = "utf-8";
                r0 = r41;
                r35 = java.net.URLEncoder.encode(r0, r2);	 Catch:{ Exception -> 0x06d5 }
            L_0x06b4:
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r2 = r2.myWebView;
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r5 = "http://docs.google.com/viewer?embedded=true&url=";
                r3 = r3.append(r5);
                r0 = r35;
                r3 = r3.append(r0);
                r3 = r3.toString();
                r2.loadUrl(r3);
                r2 = 1;
                goto L_0x00fd;
            L_0x06d5:
                r11 = move-exception;
                r35 = r41;
                goto L_0x06b4;
            L_0x06d9:
                r34 = android.net.Uri.parse(r41);
                r20 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r20;
                r1 = r34;
                r0.<init>(r2, r1);
                r0 = r39;
                r2 = hse.here2.t_html.this;
                r0 = r20;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x00fd;
            L_0x06f4:
                r2 = 0;
                goto L_0x00fd;
            L_0x06f7:
                r2 = move-exception;
                goto L_0x03d7;
            L_0x06fa:
                r2 = move-exception;
                goto L_0x0382;
            L_0x06fd:
                r2 = move-exception;
                goto L_0x0370;
            L_0x0700:
                r2 = move-exception;
                goto L_0x0270;
            L_0x0703:
                r2 = move-exception;
                goto L_0x0257;
            L_0x0706:
                r2 = move-exception;
                goto L_0x00fc;
                */
                throw new UnsupportedOperationException("Method not decompiled: hse.here2.t_html.4.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
            }
        });
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.getSettings().setPluginState(PluginState.ON);
        if (!(this.globales.wv_cache || this.globales.wv_cache_limpiada || !config.isNetworkAvailable(this))) {
            this.myWebView.clearCache(true);
            this.globales.wv_cache_limpiada = true;
        }
        this.myWebView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        this.myWebView.getSettings().setAppCacheEnabled(true);
        this.myWebView.getSettings().setCacheMode(-1);
        if (!config.isNetworkAvailable(this)) {
            if (this.globales.wv_sinconex) {
                this.myWebView.getSettings().setCacheMode(1);
            } else {
                this.myWebView.getSettings().setCacheMode(2);
            }
        }
        this.myWebView.getSettings().setUserAgentString(this.myWebView.getSettings().getUserAgentString() + " Vinebre");
        if (savedInstanceState == null) {
            String ft = getSharedPreferences("sh", 0).getString("ft", "0");
            if (!(this.globales.secciones_a[this.ind].puroHTML || ft.equals("0") || ft.equals(""))) {
                String filePath = "";
                if (!ft.equals("1")) {
                    filePath = "file:///android_asset/fonts/" + this.globales.obtenerFuente(Integer.parseInt(ft));
                } else if (new File(getFilesDir(), "font").exists()) {
                    filePath = "file://" + getFilesDir().getAbsolutePath() + "/font";
                }
                if (!filePath.equals("")) {
                    this.html = this.html.replaceFirst(Pattern.quote("</head>"), "<style type=\"text/css\">@font-face {font-family: fontAC;src: url(\"" + filePath + "\")} body,input,textarea,select{font-family: fontAC;}</style></head>");
                }
            }
            this.myWebView.loadDataWithBaseURL(null, this.html.replace("@CCORCH@", "]"), "text/html", "utf-8", null);
            this.mContentView = (LinearLayout) findViewById(C0627R.id.ll_princ);
            this.mTargetView = (FrameLayout) findViewById(C0627R.id.target_view);
        }
    }

    void abrir_go(int i) {
        startActivityForResult(this.globales.crear_rgi(Integer.valueOf(i), this).f34i, 0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mCustomView != null) {
                this.atras_pulsado = false;
                this.mClient.onHideCustomView();
                return true;
            } else if (this.myWebView.canGoBack() && !this.history_cleared) {
                this.atras_pulsado = false;
                WebBackForwardList mWebBackForwardList = this.myWebView.copyBackForwardList();
                String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                if (historyUrl.equals("about:blank") || historyUrl.equals("data:text/html;charset=utf-8;base64,")) {
                    this.cl_history = true;
                    this.myWebView.clearHistory();
                    this.myWebView.loadDataWithBaseURL(null, this.html.replace("@CCORCH@", "]"), "text/html", "utf-8", null);
                    this.myWebView.clearHistory();
                } else {
                    this.myWebView.goBack();
                }
                return true;
            } else if (this.es_root && !this.atras_pulsado && this.globales.pedir_confirm_exit) {
                this.atras_pulsado = true;
                config.confirmar_exit(this);
                return true;
            } else if (this.es_root && this.myWebView.getUrl() != null && (this.myWebView.getUrl().toLowerCase(Locale.getDefault()).contains("youtube.com") || this.myWebView.getUrl().toLowerCase(Locale.getDefault()).contains("youtu.be"))) {
                startActivity(new Intent(this, finalizar.class));
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void abrir_secc(View v) {
        if (v != null) {
            ResultGetIntent rgi = this.globales.getIntent(v, this);
            if (rgi.finalizar) {
                this.finalizar = true;
                Intent data = new Intent();
                data.putExtra("finalizar", true);
                data.putExtra("finalizar_app", rgi.finalizar_app);
                setResult(-1, data);
            }
            if (rgi.esmas) {
                if (this.myWebView.getUrl() != null && (this.myWebView.getUrl().toLowerCase().contains("youtube.com") || this.myWebView.getUrl().toLowerCase().contains("youtu.be"))) {
                    this.myWebView.reload();
                }
                startActivityForResult(rgi.f34i, 0);
            } else if (rgi.f34i != null) {
                if (rgi.finalizar && this.globales.tipomenu != 2) {
                    rgi.f34i.putExtra("es_root", true);
                }
                this.es_root = false;
                startActivity(rgi.f34i);
            }
            if (this.finalizar) {
                finish();
            }
        } else if (this.ind_abrir_secc != -1) {
            abrir_go(this.ind_abrir_secc);
        }
    }

    public void onClick(View v) {
        if ((this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals("")) && (this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            abrir_secc(v);
            return;
        }
        if (!(this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals(""))) {
            this.mAd_appnext = new RewardedVideo(this, this.globales.appnext_rew_cod);
        }
        if (!(this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            this.mAd = MobileAds.getRewardedVideoAdInstance(this);
        }
        this.dialog_cargando = new ProgressDialog(this);
        this.v_abrir_secc = v;
        this.ind_abrir_secc = -1;
        if (!this.globales.rewarded(this, v, this.cbtn, this.dialog_cargando, this.mAd, this.mAd_appnext)) {
            abrir_secc(v);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (this.mUploadMessage != null || this.mFilePathCallback != null) {
                if (this.mUploadMessage != null) {
                    Uri result = (data == null || resultCode != -1) ? null : data.getData();
                    this.mUploadMessage.onReceiveValue(result);
                    this.mUploadMessage = null;
                } else if (this.mFilePathCallback != null) {
                    Uri[] results = null;
                    if (!(data == null || data.getDataString() == null)) {
                        results = new Uri[]{Uri.parse(data.getDataString())};
                    }
                    this.mFilePathCallback.onReceiveValue(results);
                    this.mFilePathCallback = null;
                }
            }
        } else if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C08075());
        } else if (this.globales.tipomenu == 0) {
            int nsecc_aux = 0;
            for (i = 0; i < this.globales.secciones_a.length; i++) {
                if (!this.globales.secciones_a[i].oculta) {
                    findViewById(i).setOnClickListener(this);
                    nsecc_aux++;
                    if (nsecc_aux == nsecc_mostradas) {
                        break;
                    }
                }
            }
            if (nsecc_mostradas < this.globales.nsecc_visibles) {
                findViewById(9999).setOnClickListener(this);
            }
        }
        for (i = 0; i < this.globales.icos_a.length; i++) {
            if (this.globales.icos_a[i] > 0) {
                findViewById(this.globales.icos_a[i]).setOnClickListener(this);
            }
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myWebView.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.myWebView.restoreState(savedInstanceState);
        if (this.myWebView.copyBackForwardList().getCurrentIndex() == 0) {
            this.myWebView.loadDataWithBaseURL(null, this.html.replace("@CCORCH@", "]"), "text/html", "utf-8", null);
        }
    }

    public void onStop() {
        super.onStop();
        if (this.finalizar) {
            finish();
        }
    }

    public void onPause() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.pause();
        }
        if (this.mCustomView != null) {
            this.mClient.onHideCustomView();
        }
        super.onPause();
        this.myWebView.onPause();
        if (this.finalizar || isFinishing()) {
            try {
                this.myWebView.loadData("", "text/html", "utf-8");
            } catch (Exception e) {
            }
        }
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.resume();
        }
        this.myWebView.onResume();
    }

    public void onDestroy() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.destroy();
        }
        if ((this.es_root && isFinishing()) || config.finalizar_app) {
            config.finalizar_app(this);
        }
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        boolean z;
        super.onConfigurationChanged(newConfig);
        ((LinearLayout) findViewById(C0627R.id.ll_princ)).removeViewAt(0);
        incluir_menu_pre();
        ((LinearLayout) findViewById(C0627R.id.ll_ad)).removeAllViews();
        if (this.adView != null) {
            try {
                this.adView.destroy();
            } catch (Exception e) {
            }
        }
        config hse_here2_config = this.globales;
        if (this.html.contains("youtube.com") || this.html.contains("youtu.be")) {
            z = true;
        } else {
            z = false;
        }
        this.adView = hse_here2_config.mostrar_banner(this, z);
    }

    @TargetApi(21)
    private void wv_set() {
        if (VERSION.SDK_INT > 20) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.myWebView, true);
        }
    }

    public void adLoaded() {
        this.dialog_cargando.cancel();
        this.mAd_appnext.showAd();
    }

    public void onAdClosed() {
        if (this.mAd_visto) {
            abrir_secc(this.v_abrir_secc);
        }
    }

    public void adError(String error) {
        this.dialog_cargando.cancel();
    }

    public void videoEnded() {
        this.mAd_visto = true;
        config.rew_visto(this);
    }

    public void onRewardedVideoAdLoaded() {
        this.dialog_cargando.cancel();
        this.mAd.show();
    }

    public void onRewarded(RewardItem reward) {
        this.mAd_visto = true;
        config.rew_visto(this);
    }

    public void onRewardedVideoAdClosed() {
        if (this.mAd_visto) {
            abrir_secc(this.v_abrir_secc);
        }
    }

    public void onRewardedVideoAdLeftApplication() {
        this.mAd_visto = false;
    }

    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        this.dialog_cargando.cancel();
    }

    public void onRewardedVideoAdOpened() {
    }

    public void onRewardedVideoStarted() {
    }
}
