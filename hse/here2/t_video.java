package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class t_video extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    ProgressDialog dialog_cargando;
    boolean esStream = false;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    int ind;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    int stopPosition = 0;
    View v_abrir_secc;
    FullscreenVideoLayout videoView;

    class C08411 implements OnClickListener {
        C08411() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08422 implements OnClickListener {
        C08422() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08433 implements OnSystemUiVisibilityChangeListener {
        C08433() {
        }

        public void onSystemUiVisibilityChange(int vis) {
            if ((vis & 2) == 0 && t_video.this.videoView != null && t_video.this.videoView.isFullscreen()) {
                t_video.this.videoView.setFullscreen(false);
                t_video.this.videoView.showControls();
            }
        }
    }

    class C08444 implements OnPreparedListener {
        C08444() {
        }

        public void onPrepared(MediaPlayer mp) {
        }
    }

    class C08455 implements OnErrorListener {
        C08455() {
        }

        public boolean onError(MediaPlayer mp, int i1, int i2) {
            if (i1 == -38 && i2 == 0) {
            }
            return false;
        }
    }

    class C08466 implements OnItemClickListener {
        C08466() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_video.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_video.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_video.this.globales.menu_a_secciones[position]));
            t_video.this.onClick(view);
        }
    }

    class C08477 implements OnClickListener {
        C08477() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08488 implements OnClickListener {
        C08488() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    @TargetApi(14)
    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
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
        this.extras = getIntent().getExtras();
        this.ind = this.extras.getInt("ind");
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.ind].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_video);
        incluir_menu_pre();
        if (this.globales.tipomenu == 1) {
            ImageView iv_icohome = (ImageView) findViewById(C0627R.id.icohome);
            ((ImageView) findViewById(C0627R.id.iv_drawer)).setOnClickListener(new C08411());
            iv_icohome.setOnClickListener(new C08422());
        }
        this.adView = this.globales.mostrar_banner(this, false);
        if (!this.globales.secciones_a[this.ind].c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.secciones_a[this.ind].c1), Color.parseColor("#" + this.globales.secciones_a[this.ind].c2)}));
        }
        this.videoView = (FullscreenVideoLayout) findViewById(C0627R.id.vv);
        if (VERSION.SDK_INT >= 16 && !ViewConfiguration.get(this).hasPermanentMenuKey()) {
            this.videoView.setOnSystemUiVisibilityChangeListener(new C08433());
        }
        this.esStream = this.globales.secciones_a[this.ind].stream;
        if (this.esStream) {
            this.videoView.hideControlsStream();
        }
        this.videoView.setOnPreparedListener(new C08444());
        this.videoView.setOnErrorListener(new C08455());
        try {
            this.videoView.setVideoURI(Uri.parse(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.globales.video_ls) {
            setRequestedOrientation(0);
        }
        if (this.globales.video_fs) {
            this.videoView.setFullscreen(true);
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
            this.mDrawerList.setOnItemClickListener(new C08466());
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

    public void onPause() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.pause();
        }
        super.onPause();
        this.stopPosition = this.videoView.getCurrentPosition();
        this.videoView.pause();
    }

    public void onStop() {
        super.onStop();
        if (this.finalizar) {
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (this.videoView != null && this.videoView.videoIsReady) {
            this.videoView.seekTo(this.stopPosition);
            this.videoView.start();
        }
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
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
        if (this.globales.tipomenu == 1) {
            ImageView iv_icohome = (ImageView) findViewById(C0627R.id.icohome);
            ((ImageView) findViewById(C0627R.id.iv_drawer)).setOnClickListener(new C08477());
            iv_icohome.setOnClickListener(new C08488());
        }
        ((LinearLayout) findViewById(C0627R.id.ll_ad)).removeAllViews();
        if (this.adView != null) {
            try {
                this.adView.destroy();
            } catch (Exception e) {
            }
        }
        this.adView = this.globales.mostrar_banner(this, false);
    }

    public void onBackPressed() {
        if (this.es_root && !this.atras_pulsado && this.globales.pedir_confirm_exit) {
            this.atras_pulsado = true;
            config.confirmar_exit(this);
            return;
        }
        super.onBackPressed();
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
