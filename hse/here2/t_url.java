package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
import com.google.android.gms.plus.PlusShare;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Locale;

public class t_url extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean adaptar_ancho = true;
    Builder adb;
    boolean atras_pulsado = false;
    String cbtn;
    String contra;
    boolean descargar;
    ProgressDialog dialog_cargando;
    boolean es_root;
    EditText et_contra;
    EditText et_usu;
    Bundle extras;
    boolean finalizar = false;
    String[] gdocs_ext = new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "pages", "ai", "psd", "tiff", "dxf", "svg", "eps", "ps", "ttf", "otf", "xps", "zip", "rar"};
    config globales;
    HttpAuthHandler handler_glob;
    boolean history_cleared = false;
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

    class C08331 extends WebChromeClient {

        class C08321 extends WebViewClient {
            C08321() {
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                browserIntent.addCategory("android.intent.category.BROWSABLE");
                t_url.this.startActivity(browserIntent);
                return true;
            }
        }

        C08331() {
        }

        public boolean onJsBeforeUnload(WebView v, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView tempWebView = new WebView(t_url.this);
            tempWebView.setWebViewClient(new C08321());
            resultMsg.obj.setWebView(tempWebView);
            resultMsg.sendToTarget();
            return true;
        }

        public void onShowCustomView(View view, CustomViewCallback callback) {
            t_url.this.mCustomViewCallback = callback;
            t_url.this.mTargetView.addView(view);
            t_url.this.mCustomView = view;
            t_url.this.mContentView.setVisibility(8);
            t_url.this.mTargetView.setVisibility(0);
            t_url.this.getWindow().setFlags(1024, 1024);
        }

        public void onHideCustomView() {
            t_url.this.getWindow().clearFlags(1024);
            if (t_url.this.mCustomView != null) {
                t_url.this.mCustomView.setVisibility(8);
                t_url.this.mTargetView.removeView(t_url.this.mCustomView);
                t_url.this.mCustomView = null;
                t_url.this.mTargetView.setVisibility(8);
                t_url.this.mCustomViewCallback.onCustomViewHidden();
                t_url.this.mContentView.setVisibility(0);
            }
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            callback.invoke(origin, true, false);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            t_url.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_url.this.startActivityForResult(Intent.createChooser(i, t_url.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            t_url.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_url.this.startActivityForResult(Intent.createChooser(i, t_url.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            t_url.this.mUploadMessage = uploadMsg;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_url.this.startActivityForResult(Intent.createChooser(i, t_url.this.getResources().getString(C0627R.string.selecciona)), 10);
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (t_url.this.mFilePathCallback != null) {
                t_url.this.mFilePathCallback.onReceiveValue(null);
            }
            t_url.this.mFilePathCallback = filePathCallback;
            Intent i = new Intent("android.intent.action.GET_CONTENT");
            i.addCategory("android.intent.category.OPENABLE");
            i.setType("*/*");
            t_url.this.startActivityForResult(Intent.createChooser(i, t_url.this.getResources().getString(C0627R.string.selecciona)), 10);
            return true;
        }
    }

    class C08342 implements OnTouchListener {
        C08342() {
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

    class C08353 implements DownloadListener {
        C08353() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            if (t_url.this.descargar || url.endsWith(".apk")) {
                String nombre_aux = "";
                try {
                    nombre_aux = URLUtil.guessFileName(url, contentDisposition, mimetype);
                } catch (Exception e) {
                }
                t_url.this.globales.fdescargar(url, mimetype, nombre_aux);
                return;
            }
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            t_url.this.startActivity(i);
        }
    }

    class C08405 implements OnItemClickListener {
        C08405() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_url.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_url.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_url.this.globales.menu_a_secciones[position]));
            t_url.this.onClick(view);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        if (this.globales.ind_secc_sel_2 != -1) {
            this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.globales.ind_secc_sel_2].c1, this.globales.c_icos);
        } else {
            this.cbtn = config.aplicar_color_dialog(this.globales.c1, this.globales.c_icos);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_url);
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            if (savedInstanceState.containsKey("es_root")) {
                if (savedInstanceState.getBoolean("es_root", false)) {
                    z = true;
                    this.es_root = z;
                }
            }
            z = false;
            this.es_root = z;
        }
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtube.com") || this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtu.be");
        this.adView = hse_here2_config.mostrar_banner(this, z);
        if (this.extras.containsKey("linksexternos") && this.extras.containsKey("adaptar_ancho")) {
            if (this.extras.getBoolean("linksexternos")) {
                this.linksexternos = 1;
            } else {
                this.linksexternos = 0;
            }
            this.adaptar_ancho = this.extras.getBoolean("adaptar_ancho");
            this.descargar = false;
        } else if (this.globales.ind_secc_sel_2 != -1) {
            this.linksexternos = this.globales.secciones_a[this.globales.ind_secc_sel_2].linksexternos;
            this.adaptar_ancho = this.globales.secciones_a[this.globales.ind_secc_sel_2].adaptar_ancho;
            this.descargar = this.globales.secciones_a[this.globales.ind_secc_sel_2].descargar;
        } else {
            this.linksexternos = 0;
            this.adaptar_ancho = false;
            this.descargar = false;
        }
        if (this.globales.ind_secc_sel_2 != -1) {
            this.loader = this.globales.secciones_a[this.globales.ind_secc_sel_2].loader;
            this.zoom = this.globales.secciones_a[this.globales.ind_secc_sel_2].zoom;
        } else {
            this.loader = true;
            this.zoom = true;
        }
        this.myWebView = (WebView) findViewById(C0627R.id.webview);
        if (this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).startsWith("file://")) {
            this.loader = false;
            this.zoom = true;
            this.adaptar_ancho = true;
            registerForContextMenu(this.myWebView);
            this.adaptar_ancho = true;
            String bg1 = "";
            String bg2 = "";
            if (this.extras.containsKey("bg1")) {
                bg1 = this.extras.getString("bg1");
                bg2 = this.extras.getString("bg2");
            } else if (this.globales.ind_secc_sel_2 != -1) {
                bg1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
                bg2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
            }
            if (!bg1.equals("")) {
                findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + bg1), Color.parseColor("#" + bg2)}));
                this.myWebView.setBackgroundColor(0);
            }
        }
        if (this.linksexternos == 2) {
            this.myWebView.getSettings().setSupportMultipleWindows(true);
        }
        WebView webView = this.myWebView;
        WebChromeClient c08331 = new C08331();
        this.mClient = c08331;
        webView.setWebChromeClient(c08331);
        wv_set();
        this.myWebView.getSettings().setBuiltInZoomControls(this.zoom);
        this.myWebView.getSettings().setSupportZoom(this.zoom);
        this.myWebView.getSettings().setDomStorageEnabled(true);
        this.myWebView.setOnTouchListener(new C08342());
        this.myWebView.setDownloadListener(new C08353());
        if (this.adaptar_ancho) {
            this.myWebView.getSettings().setUseWideViewPort(true);
            this.myWebView.getSettings().setLoadWithOverviewMode(true);
        }
        final ProgressBar progess = (ProgressBar) findViewById(C0627R.id.pb_url);
        if (VERSION.SDK_INT > 20) {
            config.progress_color(progess, this.globales.c_icos);
        }
        this.myWebView.setWebViewClient(new WebViewClient() {

            class C08361 implements DialogInterface.OnClickListener {
                C08361() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    t_url.this.usu = t_url.this.et_usu.getText().toString();
                    t_url.this.contra = t_url.this.et_contra.getText().toString();
                    t_url.this.handler_glob.proceed(t_url.this.usu, t_url.this.contra);
                }
            }

            class C08372 implements DialogInterface.OnClickListener {
                C08372() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    t_url.this.handler_glob.cancel();
                }
            }

            class C08383 implements OnCancelListener {
                C08383() {
                }

                public void onCancel(DialogInterface dialog) {
                    t_url.this.handler_glob.cancel();
                }
            }

            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                t_url.this.handler_glob = handler;
                t_url.this.adb = new Builder(t_url.this);
                t_url.this.adb.setTitle(t_url.this.getResources().getString(C0627R.string.chat_acceso));
                View eulaLayout = t_url.this.getLayoutInflater().inflate(C0627R.layout.usucontra, null);
                t_url.this.et_usu = (EditText) eulaLayout.findViewById(C0627R.id.et_usu);
                t_url.this.et_contra = (EditText) eulaLayout.findViewById(C0627R.id.et_contra);
                t_url.this.adb.setView(eulaLayout);
                t_url.this.adb.setCancelable(true);
                t_url.this.adb.setPositiveButton(t_url.this.getString(C0627R.string.aceptar), new C08361());
                t_url.this.adb.setNegativeButton(t_url.this.getString(C0627R.string.cancelar), new C08372());
                t_url.this.adb.setOnCancelListener(new C08383());
                t_url.this.adb.create().show();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (!config.isNetworkAvailable(t_url.this)) {
                    if (t_url.this.globales.wv_sinconex_txt.equals("")) {
                        t_url.this.myWebView.loadData("<html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body>" + t_url.this.getResources().getString(C0627R.string.wv_sinconex) + "</body></html>", "text/html; charset=utf-8", "utf-8");
                    } else {
                        String str_aux = t_url.this.globales.wv_sinconex_txt;
                        if (!str_aux.toLowerCase(Locale.getDefault()).contains("<html")) {
                            str_aux = "<html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></head><body>" + str_aux + "</body></html>";
                        }
                        t_url.this.myWebView.loadData(str_aux, "text/html; charset=utf-8", "utf-8");
                    }
                    t_url.this.myWebView.clearHistory();
                    t_url.this.history_cleared = true;
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!(t_url.this.finalizar || t_url.this.isFinishing() || t_url.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtube.com") || t_url.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtu.be"))) {
                    boolean aux1 = false;
                    if (t_url.this.primer_load) {
                        if (t_url.this.extras == null || !t_url.this.extras.containsKey("ad_entrar")) {
                            aux1 = false;
                        } else {
                            aux1 = true;
                        }
                        t_url.this.primer_load = false;
                    }
                    t_url.this.globales.toca_int(t_url.this, aux1);
                }
                if (t_url.this.loader) {
                    progess.setVisibility(0);
                }
            }

            public void onPageFinished(WebView view, String url) {
                progess.setVisibility(8);
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean shouldOverrideUrlLoading(android.webkit.WebView r30, java.lang.String r31) {
                /*
                r29 = this;
                r28 = r31.toLowerCase();
                r27 = "";
                r2 = ".";
                r0 = r31;
                r20 = r0.lastIndexOf(r2);
                r2 = -1;
                r0 = r20;
                if (r0 == r2) goto L_0x001b;
            L_0x0013:
                r0 = r28;
                r1 = r20;
                r27 = r0.substring(r1);
            L_0x001b:
                r2 = ".";
                r3 = "";
                r0 = r27;
                r27 = r0.replace(r2, r3);
                r2 = "http://closethis";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0038;
            L_0x002f:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2.finish();
                r2 = 1;
            L_0x0037:
                return r2;
            L_0x0038:
                r2 = "tel:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x004c;
            L_0x0042:
                r2 = "http://tel:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x00a6;
            L_0x004c:
                r2 = "tel:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x009d;
            L_0x0056:
                r2 = 4;
                r0 = r31;
                r23 = r0.substring(r2);
            L_0x005d:
                r2 = "/";
                r0 = r23;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x0074;
            L_0x0067:
                r2 = 0;
                r3 = r23.length();
                r3 = r3 + -1;
                r0 = r23;
                r23 = r0.substring(r2, r3);
            L_0x0074:
                r9 = new android.content.Intent;
                r2 = "android.intent.action.DIAL";
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r5 = "tel:";
                r3 = r3.append(r5);
                r0 = r23;
                r3 = r3.append(r0);
                r3 = r3.toString();
                r3 = android.net.Uri.parse(r3);
                r9.<init>(r2, r3);
                r0 = r29;
                r2 = hse.here2.t_url.this;	 Catch:{ Exception -> 0x05ea }
                r2.startActivity(r9);	 Catch:{ Exception -> 0x05ea }
            L_0x009b:
                r2 = 1;
                goto L_0x0037;
            L_0x009d:
                r2 = 11;
                r0 = r31;
                r23 = r0.substring(r2);
                goto L_0x005d;
            L_0x00a6:
                r2 = "mailto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x00ba;
            L_0x00b0:
                r2 = "http://mailto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x01a7;
            L_0x00ba:
                r22 = "";
                r24 = "";
                r2 = "mailto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0199;
            L_0x00c8:
                r2 = 7;
                r0 = r31;
                r2 = r0.substring(r2);
                r11 = r2.trim();
            L_0x00d3:
                r2 = "";
                r2 = r11.equals(r2);
                if (r2 != 0) goto L_0x0196;
            L_0x00db:
                r2 = "?";
                r18 = r11.indexOf(r2);
                if (r18 <= 0) goto L_0x0129;
            L_0x00e3:
                r2 = "?subject=";
                r19 = r11.indexOf(r2);
                r2 = -1;
                r0 = r19;
                if (r0 == r2) goto L_0x011e;
            L_0x00ee:
                r2 = r19 + 9;
                r2 = r11.substring(r2);
                r22 = r2.trim();
                r2 = "&body=";
                r0 = r22;
                r19 = r0.indexOf(r2);
                r2 = -1;
                r0 = r19;
                if (r0 == r2) goto L_0x011e;
            L_0x0105:
                r2 = r19 + 6;
                r0 = r22;
                r2 = r0.substring(r2);
                r24 = r2.trim();
                r2 = 0;
                r0 = r22;
                r1 = r19;
                r2 = r0.substring(r2, r1);
                r22 = r2.trim();
            L_0x011e:
                r2 = 0;
                r0 = r18;
                r2 = r11.substring(r2, r0);
                r11 = r2.trim();
            L_0x0129:
                r2 = "/";
                r2 = r11.endsWith(r2);
                if (r2 == 0) goto L_0x013c;
            L_0x0131:
                r2 = 0;
                r3 = r11.length();
                r3 = r3 + -1;
                r11 = r11.substring(r2, r3);
            L_0x013c:
                r12 = new android.content.Intent;
                r2 = "android.intent.action.SENDTO";
                r3 = "mailto";
                r5 = 0;
                r3 = android.net.Uri.fromParts(r3, r11, r5);
                r12.<init>(r2, r3);
                r2 = "";
                r0 = r22;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x0163;
            L_0x0154:
                r2 = "UTF-8";
                r0 = r22;
                r22 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x05e7 }
            L_0x015c:
                r2 = "android.intent.extra.SUBJECT";
                r0 = r22;
                r12.putExtra(r2, r0);
            L_0x0163:
                r2 = "";
                r0 = r24;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x017c;
            L_0x016d:
                r2 = "UTF-8";
                r0 = r24;
                r24 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x05e4 }
            L_0x0175:
                r2 = "android.intent.extra.TEXT";
                r0 = r24;
                r12.putExtra(r2, r0);
            L_0x017c:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r0 = r29;
                r3 = hse.here2.t_url.this;
                r3 = r3.getResources();
                r5 = 2131361974; // 0x7f0a00b6 float:1.8343715E38 double:1.05303273E-314;
                r3 = r3.getString(r5);
                r3 = android.content.Intent.createChooser(r12, r3);
                r2.startActivity(r3);
            L_0x0196:
                r2 = 1;
                goto L_0x0037;
            L_0x0199:
                r2 = 14;
                r0 = r31;
                r2 = r0.substring(r2);
                r11 = r2.trim();
                goto L_0x00d3;
            L_0x01a7:
                r2 = "smsto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x01bb;
            L_0x01b1:
                r2 = "http://smsto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x027c;
            L_0x01bb:
                r24 = "";
                r2 = "smsto:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x026e;
            L_0x01c7:
                r2 = 6;
                r0 = r31;
                r2 = r0.substring(r2);
                r23 = r2.trim();
            L_0x01d2:
                r2 = "";
                r0 = r23;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x026b;
            L_0x01dc:
                r2 = "?";
                r0 = r23;
                r18 = r0.indexOf(r2);
                if (r18 <= 0) goto L_0x020c;
            L_0x01e6:
                r2 = "?body=";
                r0 = r23;
                r19 = r0.indexOf(r2);
                r2 = -1;
                r0 = r19;
                if (r0 == r2) goto L_0x01ff;
            L_0x01f3:
                r2 = r19 + 6;
                r0 = r23;
                r2 = r0.substring(r2);
                r24 = r2.trim();
            L_0x01ff:
                r2 = 0;
                r0 = r23;
                r1 = r18;
                r2 = r0.substring(r2, r1);
                r23 = r2.trim();
            L_0x020c:
                r2 = "/";
                r0 = r23;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x0223;
            L_0x0216:
                r2 = 0;
                r3 = r23.length();
                r3 = r3 + -1;
                r0 = r23;
                r23 = r0.substring(r2, r3);
            L_0x0223:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "smsto:";
                r2 = r2.append(r3);
                r0 = r23;
                r2 = r2.append(r0);
                r2 = r2.toString();
                r25 = android.net.Uri.parse(r2);
                r21 = new android.content.Intent;
                r2 = "android.intent.action.SENDTO";
                r0 = r21;
                r1 = r25;
                r0.<init>(r2, r1);
                r2 = "";
                r0 = r24;
                r2 = r0.equals(r2);
                if (r2 != 0) goto L_0x0262;
            L_0x0251:
                r2 = "UTF-8";
                r0 = r24;
                r24 = java.net.URLDecoder.decode(r0, r2);	 Catch:{ Exception -> 0x05e1 }
            L_0x0259:
                r2 = "sms_body";
                r0 = r21;
                r1 = r24;
                r0.putExtra(r2, r1);
            L_0x0262:
                r0 = r29;
                r2 = hse.here2.t_url.this;	 Catch:{ Exception -> 0x05de }
                r0 = r21;
                r2.startActivity(r0);	 Catch:{ Exception -> 0x05de }
            L_0x026b:
                r2 = 1;
                goto L_0x0037;
            L_0x026e:
                r2 = 13;
                r0 = r31;
                r2 = r0.substring(r2);
                r23 = r2.trim();
                goto L_0x01d2;
            L_0x027c:
                r2 = "go:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x0290;
            L_0x0286:
                r2 = "http://go:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x03e2;
            L_0x0290:
                r2 = "go:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0316;
            L_0x029a:
                r2 = 3;
                r0 = r31;
                r14 = r0.substring(r2);
            L_0x02a1:
                r2 = "/";
                r2 = r14.endsWith(r2);
                if (r2 == 0) goto L_0x02b4;
            L_0x02a9:
                r2 = 0;
                r3 = r14.length();
                r3 = r3 + -1;
                r14 = r14.substring(r2, r3);
            L_0x02b4:
                r2 = "UTF-8";
                r14 = java.net.URLDecoder.decode(r14, r2);	 Catch:{ Exception -> 0x05db }
            L_0x02ba:
                r13 = 0;
            L_0x02bb:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.secciones_a;
                r2 = r2.length;
                if (r13 >= r2) goto L_0x03df;
            L_0x02c6:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.secciones_a;
                r2 = r2[r13];
                r2 = r2.idgo;
                r2 = r2.equalsIgnoreCase(r14);
                if (r2 == 0) goto L_0x03db;
            L_0x02d8:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                if (r2 == 0) goto L_0x02f2;
            L_0x02e2:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 == 0) goto L_0x031f;
            L_0x02f2:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                if (r2 == 0) goto L_0x030c;
            L_0x02fc:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 == 0) goto L_0x031f;
            L_0x030c:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2.abrir_go(r13);
            L_0x0313:
                r2 = 1;
                goto L_0x0037;
            L_0x0316:
                r2 = 10;
                r0 = r31;
                r14 = r0.substring(r2);
                goto L_0x02a1;
            L_0x031f:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                if (r2 == 0) goto L_0x0350;
            L_0x0329:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.appnext_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 != 0) goto L_0x0350;
            L_0x0339:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r3 = new com.appnext.ads.fullscreen.RewardedVideo;
                r0 = r29;
                r5 = hse.here2.t_url.this;
                r0 = r29;
                r6 = hse.here2.t_url.this;
                r6 = r6.globales;
                r6 = r6.appnext_rew_cod;
                r3.<init>(r5, r6);
                r2.mAd_appnext = r3;
            L_0x0350:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                if (r2 == 0) goto L_0x0378;
            L_0x035a:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r2 = r2.admob_rew_cod;
                r3 = "";
                r2 = r2.equals(r3);
                if (r2 != 0) goto L_0x0378;
            L_0x036a:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r0 = r29;
                r3 = hse.here2.t_url.this;
                r3 = com.google.android.gms.ads.MobileAds.getRewardedVideoAdInstance(r3);
                r2.mAd = r3;
            L_0x0378:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r3 = new android.app.ProgressDialog;
                r0 = r29;
                r5 = hse.here2.t_url.this;
                r3.<init>(r5);
                r2.dialog_cargando = r3;
                r4 = new android.view.View;
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r4.<init>(r2);
                r4.setId(r13);
                r2 = 2131165187; // 0x7f070003 float:1.7944584E38 double:1.0529355045E-314;
                r3 = java.lang.Integer.valueOf(r13);
                r4.setTag(r2, r3);
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r3 = 0;
                r2.v_abrir_secc = r3;
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2.ind_abrir_secc = r13;
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r0 = r29;
                r3 = hse.here2.t_url.this;
                r0 = r29;
                r5 = hse.here2.t_url.this;
                r5 = r5.cbtn;
                r0 = r29;
                r6 = hse.here2.t_url.this;
                r6 = r6.dialog_cargando;
                r0 = r29;
                r7 = hse.here2.t_url.this;
                r7 = r7.mAd;
                r0 = r29;
                r8 = hse.here2.t_url.this;
                r8 = r8.mAd_appnext;
                r2 = r2.rewarded(r3, r4, r5, r6, r7, r8);
                if (r2 != 0) goto L_0x0313;
            L_0x03d2:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2.abrir_go(r13);
                goto L_0x0313;
            L_0x03db:
                r13 = r13 + 1;
                goto L_0x02bb;
            L_0x03df:
                r2 = 0;
                goto L_0x0037;
            L_0x03e2:
                r2 = "vnd.youtube:";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x044e;
            L_0x03ec:
                r2 = "?";
                r0 = r31;
                r17 = r0.indexOf(r2);
                r15 = "";
                if (r17 <= 0) goto L_0x0439;
            L_0x03f8:
                r2 = 12;
                r0 = r31;
                r1 = r17;
                r15 = r0.substring(r2, r1);
            L_0x0402:
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "http://www.youtube.com/watch?v=";
                r2 = r2.append(r3);
                r2 = r2.append(r15);
                r26 = r2.toString();
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.linksexternos;
                r3 = 1;
                if (r2 != r3) goto L_0x0442;
            L_0x041e:
                r25 = android.net.Uri.parse(r26);
                r16 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r16;
                r1 = r25;
                r0.<init>(r2, r1);
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r0 = r16;
                r2.startActivity(r0);
            L_0x0436:
                r2 = 1;
                goto L_0x0037;
            L_0x0439:
                r2 = 12;
                r0 = r31;
                r15 = r0.substring(r2);
                goto L_0x0402;
            L_0x0442:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.myWebView;
                r0 = r26;
                r2.loadUrl(r0);
                goto L_0x0436;
            L_0x044e:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.descargar;
                if (r2 == 0) goto L_0x047c;
            L_0x0456:
                r2 = ".mp3";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x046a;
            L_0x0460:
                r2 = ".mp4";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x047c;
            L_0x046a:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.globales;
                r3 = "";
                r5 = "";
                r0 = r31;
                r2.fdescargar(r0, r3, r5);
                r2 = 1;
                goto L_0x0037;
            L_0x047c:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x04ae;
            L_0x0484:
                r2 = ".mp3";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x04ae;
            L_0x048e:
                r16 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r16;
                r0.<init>(r2);
                r2 = android.net.Uri.parse(r31);
                r3 = "audio/*";
                r0 = r16;
                r0.setDataAndType(r2, r3);
                r2 = r30.getContext();
                r0 = r16;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x0037;
            L_0x04ae:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x04ea;
            L_0x04b6:
                r2 = ".mp4";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x04ca;
            L_0x04c0:
                r2 = ".3gp";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 == 0) goto L_0x04ea;
            L_0x04ca:
                r16 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r16;
                r0.<init>(r2);
                r2 = android.net.Uri.parse(r31);
                r3 = "video/*";
                r0 = r16;
                r0.setDataAndType(r2, r3);
                r2 = r30.getContext();
                r0 = r16;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x0037;
            L_0x04ea:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.linksexternos;
                r3 = 1;
                if (r2 == r3) goto L_0x0539;
            L_0x04f3:
                r2 = "rtsp://";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x04fd:
                r2 = "rtmp://";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x0507:
                r2 = "market://";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x0511:
                r2 = "whatsapp://";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x051b:
                r2 = ".m3u";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x0525:
                r2 = ".m3u8";
                r0 = r28;
                r2 = r0.endsWith(r2);
                if (r2 != 0) goto L_0x0539;
            L_0x052f:
                r2 = "http://www.androidcreator.com/open";
                r0 = r28;
                r2 = r0.startsWith(r2);
                if (r2 == 0) goto L_0x0558;
            L_0x0539:
                r25 = android.net.Uri.parse(r31);
                r16 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r16;
                r1 = r25;
                r0.<init>(r2, r1);
                r0 = r29;
                r2 = hse.here2.t_url.this;	 Catch:{ Exception -> 0x0554 }
                r0 = r16;
                r2.startActivity(r0);	 Catch:{ Exception -> 0x0554 }
                r2 = 1;
                goto L_0x0037;
            L_0x0554:
                r10 = move-exception;
                r2 = 0;
                goto L_0x0037;
            L_0x0558:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.descargar;
                if (r2 != 0) goto L_0x05d8;
            L_0x0560:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.gdocs_ext;
                r2 = java.util.Arrays.asList(r2);
                r0 = r27;
                r2 = r2.contains(r0);
                if (r2 == 0) goto L_0x05d8;
            L_0x0572:
                r2 = "docs.google.com";
                r0 = r28;
                r2 = r0.contains(r2);
                if (r2 != 0) goto L_0x05d8;
            L_0x057c:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.myWebView;
                r2 = r2.getUrl();
                r3 = "docs.google.com";
                r2 = r2.contains(r3);
                if (r2 != 0) goto L_0x05bd;
            L_0x058e:
                r26 = "";
                r2 = "utf-8";
                r0 = r31;
                r26 = java.net.URLEncoder.encode(r0, r2);	 Catch:{ Exception -> 0x05b9 }
            L_0x0598:
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r2 = r2.myWebView;
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r5 = "http://docs.google.com/viewer?embedded=true&url=";
                r3 = r3.append(r5);
                r0 = r26;
                r3 = r3.append(r0);
                r3 = r3.toString();
                r2.loadUrl(r3);
                r2 = 1;
                goto L_0x0037;
            L_0x05b9:
                r10 = move-exception;
                r26 = r31;
                goto L_0x0598;
            L_0x05bd:
                r25 = android.net.Uri.parse(r31);
                r16 = new android.content.Intent;
                r2 = "android.intent.action.VIEW";
                r0 = r16;
                r1 = r25;
                r0.<init>(r2, r1);
                r0 = r29;
                r2 = hse.here2.t_url.this;
                r0 = r16;
                r2.startActivity(r0);
                r2 = 1;
                goto L_0x0037;
            L_0x05d8:
                r2 = 0;
                goto L_0x0037;
            L_0x05db:
                r2 = move-exception;
                goto L_0x02ba;
            L_0x05de:
                r2 = move-exception;
                goto L_0x026b;
            L_0x05e1:
                r2 = move-exception;
                goto L_0x0259;
            L_0x05e4:
                r2 = move-exception;
                goto L_0x0175;
            L_0x05e7:
                r2 = move-exception;
                goto L_0x015c;
            L_0x05ea:
                r2 = move-exception;
                goto L_0x009b;
                */
                throw new UnsupportedOperationException("Method not decompiled: hse.here2.t_url.4.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
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
        String ua_aux = "";
        if (!(this.globales.ind_secc_sel_2 == -1 || this.globales.secciones_a[this.globales.ind_secc_sel_2].ua.equals(""))) {
            ua_aux = this.globales.secciones_a[this.globales.ind_secc_sel_2].ua;
        }
        if (ua_aux.equals("")) {
            this.myWebView.getSettings().setUserAgentString(this.myWebView.getSettings().getUserAgentString() + " Vinebre");
        } else {
            this.myWebView.getSettings().setUserAgentString(ua_aux);
        }
        if (savedInstanceState == null) {
            String url_orig = this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL);
            boolean con_goog = false;
            if (this.linksexternos != 1) {
                String url_l = url_orig.toLowerCase();
                String url_ext = "";
                int pos_aux = url_l.lastIndexOf(".");
                if (pos_aux != -1) {
                    url_ext = url_l.substring(pos_aux);
                }
                if (Arrays.asList(this.gdocs_ext).contains(url_ext.replace(".", "")) && !url_l.contains("docs.google.com")) {
                    con_goog = true;
                }
            }
            if (con_goog) {
                String url_aux = "";
                try {
                    url_aux = URLEncoder.encode(url_orig, "utf-8");
                } catch (Exception e) {
                    url_aux = url_orig;
                }
                this.myWebView.loadUrl("http://docs.google.com/viewer?embedded=true&url=" + url_aux);
            } else {
                this.myWebView.loadUrl(url_orig);
            }
            this.mContentView = (LinearLayout) findViewById(C0627R.id.ll_princ);
            this.mTargetView = (FrameLayout) findViewById(C0627R.id.target_view);
        }
    }

    void abrir_go(int i) {
        startActivityForResult(this.globales.crear_rgi(Integer.valueOf(i), this).f34i, 0);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("image/jpeg");
        ContentValues values = new ContentValues();
        values.put("mime_type", "image/jpeg");
        Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream outstream = getContentResolver().openOutputStream(uri);
            Options options = new Options();
            options.inPreferredConfig = Config.ARGB_8888;
            BitmapFactory.decodeFile(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).replace("file://", ""), options).compress(CompressFormat.JPEG, 100, outstream);
            outstream.close();
            share.putExtra("android.intent.extra.STREAM", uri);
            startActivity(Intent.createChooser(share, getResources().getString(C0627R.string.compartir)));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.mCustomView != null) {
                this.atras_pulsado = false;
                this.mClient.onHideCustomView();
                return true;
            } else if (this.myWebView.canGoBack() && !this.history_cleared) {
                this.atras_pulsado = false;
                this.myWebView.goBack();
                return true;
            } else if (this.es_root && !this.atras_pulsado && this.globales.pedir_confirm_exit) {
                this.atras_pulsado = true;
                config.confirmar_exit(this);
                return true;
            } else if (this.es_root && this.myWebView.getUrl() != null && (this.myWebView.getUrl().toLowerCase().contains("youtube.com") || this.myWebView.getUrl().toLowerCase().contains("youtu.be"))) {
                startActivity(new Intent(this, finalizar.class));
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
            this.mDrawerList.setOnItemClickListener(new C08405());
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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myWebView.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.myWebView.restoreState(savedInstanceState);
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

    public void onStart() {
        super.onStart();
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
        if (this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtube.com") || this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtu.be")) {
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
