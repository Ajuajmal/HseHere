package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

public class t_mapa_web extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    ProgressDialog dialog_cargando;
    Drawable drawable;
    boolean finalizar = false;
    config globales;
    LinearLayout linearLayout;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    WebView myWebView;
    View v_abrir_secc;

    class C08081 extends WebChromeClient {
        C08081() {
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    class C08103 implements OnItemClickListener {
        C08103() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_mapa_web.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_mapa_web.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_mapa_web.this.globales.menu_a_secciones[position]));
            t_mapa_web.this.onClick(view);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.globales.ind_secc_sel_2].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_mapa_web);
        incluir_menu_pre();
        this.globales.toca_int(this, false);
        this.adView = this.globales.mostrar_banner(this, false);
        this.myWebView = (WebView) findViewById(C0627R.id.webview);
        this.myWebView.setWebChromeClient(new C08081());
        final ProgressBar progess = (ProgressBar) findViewById(C0627R.id.pb_url);
        if (VERSION.SDK_INT > 20) {
            config.progress_color(progess, this.globales.c_icos);
        }
        this.myWebView.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progess.setVisibility(0);
            }

            public void onPageFinished(WebView view, String url) {
                progess.setVisibility(8);
            }
        });
        this.myWebView.getSettings().setJavaScriptEnabled(true);
        this.myWebView.getSettings().setPluginState(PluginState.ON);
        if (savedInstanceState == null) {
            this.myWebView.loadUrl("http://maps.google.com/maps?z=" + getIntent().getIntExtra("z", 0) + "&t=m&q=loc:" + (((float) getIntent().getIntExtra("x", 0)) / 1000000.0f) + "+" + (((float) getIntent().getIntExtra("y", 0)) / 1000000.0f));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            setResult(-1, data);
            finish();
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C08103());
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

    public void onClick(View v) {
        if (!(this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals(""))) {
            this.mAd_appnext = new RewardedVideo(this, this.globales.appnext_rew_cod);
        }
        if (!(this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            this.mAd = MobileAds.getRewardedVideoAdInstance(this);
        }
        this.dialog_cargando = new ProgressDialog(this);
        this.v_abrir_secc = v;
        if (!this.globales.rewarded(this, v, this.cbtn, this.dialog_cargando, this.mAd, this.mAd_appnext)) {
            abrir_secc(v);
        }
    }

    public void abrir_secc(View v) {
        ResultGetIntent rgi = this.globales.getIntent(v, this);
        if (rgi.finalizar) {
            this.finalizar = true;
            Intent data = new Intent();
            data.putExtra("finalizar", true);
            data.putExtra("finalizar_app", rgi.finalizar_app);
            setResult(-1, data);
        }
        if (rgi.esmas) {
            startActivityForResult(rgi.f34i, 0);
        } else if (rgi.f34i != null) {
            if (rgi.finalizar && this.globales.tipomenu != 2) {
                rgi.f34i.putExtra("es_root", true);
            }
            startActivity(rgi.f34i);
        }
        if (this.finalizar) {
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (this.myWebView.canGoBack()) {
                this.myWebView.goBack();
                return true;
            }
            this.atras_pulsado = true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myWebView.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.myWebView.restoreState(savedInstanceState);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
    }

    public void onDestroy() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.destroy();
        }
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
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
        this.adView = this.globales.mostrar_banner(this, false);
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
