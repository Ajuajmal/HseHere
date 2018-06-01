package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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

public class t_gal_foto extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    WebView myWebView;
    boolean primer_load = true;
    SharedPreferences settings;
    View v_abrir_secc;

    class C07951 extends WebChromeClient {
        C07951() {
        }
    }

    class C07962 implements OnTouchListener {
        C07962() {
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

    class C07973 extends WebViewClient {
        C07973() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!t_gal_foto.this.finalizar && !t_gal_foto.this.isFinishing() && !t_gal_foto.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtube.com") && !t_gal_foto.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtu.be")) {
                boolean aux1 = false;
                if (t_gal_foto.this.primer_load) {
                    if (t_gal_foto.this.extras == null || !t_gal_foto.this.extras.containsKey("ad_entrar")) {
                        aux1 = false;
                    } else {
                        aux1 = true;
                    }
                    t_gal_foto.this.primer_load = false;
                }
                t_gal_foto.this.globales.toca_int(t_gal_foto.this, aux1);
            }
        }
    }

    class C07984 implements OnItemClickListener {
        C07984() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_gal_foto.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_gal_foto.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_gal_foto.this.globales.menu_a_secciones[position]));
            t_gal_foto.this.onClick(view);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        this.cbtn = config.aplicar_color_dialog("FFFFFFFF", this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_gal_foto);
        this.extras = getIntent().getExtras();
        boolean z;
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
        this.adView = this.globales.mostrar_banner(this, false);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        findViewById(C0627R.id.iv_cerrar).setOnClickListener(this);
        this.myWebView = (WebView) findViewById(C0627R.id.webview);
        if (this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).startsWith("file://")) {
            registerForContextMenu(this.myWebView);
            String bg1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
            String bg2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
            if (this.extras.containsKey("bg1")) {
                bg1 = this.extras.getString("bg1");
                bg2 = this.extras.getString("bg2");
            }
            if (!bg1.equals("")) {
                findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + bg1), Color.parseColor("#" + bg2)}));
                this.myWebView.setBackgroundColor(0);
            }
        }
        this.myWebView.setWebChromeClient(new C07951());
        this.myWebView.getSettings().setBuiltInZoomControls(true);
        this.myWebView.getSettings().setSupportZoom(true);
        this.myWebView.setOnTouchListener(new C07962());
        this.myWebView.getSettings().setUseWideViewPort(true);
        this.myWebView.getSettings().setLoadWithOverviewMode(true);
        this.myWebView.setWebViewClient(new C07973());
        if (savedInstanceState == null) {
            this.myWebView.loadUrl(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL));
        }
        String descr = this.extras.getString("descr").trim();
        if (!descr.equals("")) {
            ((TextView) findViewById(C0627R.id.tv_descr)).setText(descr);
            findViewById(C0627R.id.ll_descr).setVisibility(0);
        }
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
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.myWebView.canGoBack()) {
            this.myWebView.goBack();
            return true;
        }
        fcerrar();
        return true;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void fcerrar() {
        finish();
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.iv_cerrar) {
            fcerrar();
        } else if ((this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals("")) && (this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            abrir_secc(v);
        } else {
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
            this.es_root = false;
            startActivity(rgi.f34i);
        }
        if (this.finalizar) {
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
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
            this.mDrawerList.setOnItemClickListener(new C07984());
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
        super.onPause();
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
