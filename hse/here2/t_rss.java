package hse.here2;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.plus.PlusShare;

public class t_rss extends FragmentActivity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    boolean abrir_fuera;
    AdView adView;
    boolean atras_pulsado = false;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    View v_abrir_secc;
    t_rssdetalle_fr viewer;

    class C08241 implements OnItemClickListener {
        C08241() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_rss.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_rss.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_rss.this.globales.menu_a_secciones[position]));
            t_rss.this.onClick(view);
        }
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        boolean es_youtube;
        boolean z2 = true;
        this.globales = (config) getApplicationContext();
        this.extras = getIntent().getExtras();
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
        super.onCreate(savedInstanceState);
        if (this.globales.secciones_a[getIntent().getExtras().getInt("ind")].url.contains("youtube.com") || this.globales.secciones_a[getIntent().getExtras().getInt("ind")].url.contains("youtu.be")) {
            es_youtube = true;
        } else {
            es_youtube = false;
        }
        if (this.globales.secciones_a[getIntent().getExtras().getInt("ind")].linksexternos == 1) {
            z = true;
        } else {
            z = false;
        }
        this.abrir_fuera = z;
        boolean mostrar_banner = false;
        Display display = getWindowManager().getDefaultDisplay();
        int w_aux;
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            w_aux = size.x;
        } else {
            w_aux = display.getWidth();
        }
        if (this.abrir_fuera || w_aux <= ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
            setContentView(C0627R.layout.t_rss_v);
            mostrar_banner = true;
        } else {
            setContentView(C0627R.layout.t_rss_h);
        }
        this.viewer = (t_rssdetalle_fr) getSupportFragmentManager().findFragmentById(C0627R.id.rssdetalle_fr);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            config hse_here2_config = this.globales;
            if (this.extras == null || !this.extras.containsKey("ad_entrar")) {
                z2 = false;
            }
            hse_here2_config.toca_int(this, z2);
        }
        if (mostrar_banner) {
            this.adView = this.globales.mostrar_banner(this, es_youtube);
        }
    }

    public void onTutSelected(String url) {
        if (url == null) {
            return;
        }
        if (this.viewer != null && this.viewer.isInLayout()) {
            this.viewer.actContenido(url);
        } else if (this.abrir_fuera) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } else {
            Intent showContent = new Intent(getApplicationContext(), t_rssdetalle.class);
            showContent.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
            startActivityForResult(showContent, 0);
        }
    }

    public void onClick(View v) {
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.viewer == null || !this.viewer.isInLayout()) {
            if (keyCode != 4 || !this.es_root || this.atras_pulsado || !this.globales.pedir_confirm_exit) {
                return super.onKeyDown(keyCode, event);
            }
            this.atras_pulsado = true;
            config.confirmar_exit(this);
            return true;
        } else if (this.viewer.onKeyDown(keyCode, event)) {
            return true;
        } else {
            if (keyCode != 4 || !this.es_root || this.atras_pulsado || !this.globales.pedir_confirm_exit) {
                return super.onKeyDown(keyCode, event);
            }
            this.atras_pulsado = true;
            config.confirmar_exit(this);
            return true;
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C08241());
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

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("es_root", this.es_root);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
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
