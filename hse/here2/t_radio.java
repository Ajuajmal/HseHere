package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout.LayoutParams;
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
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.plus.PlusShare;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class t_radio extends Activity implements OnClickListener, MediaPlayerControl, OnSharedPreferenceChangeListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
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
    private MediaController mMediaController;
    int radio_mostrar;
    SharedPreferences sh_mc;
    SharedPreferences sh_mp;
    TextView tv_artist;
    TextView tv_song;
    View v_abrir_secc;

    class C08181 implements OnClickListener {
        C08181() {
        }

        public void onClick(View v) {
            if (t_radio.this.mMediaController != null) {
                t_radio.this.mMediaController.hide();
            }
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08192 implements OnClickListener {
        C08192() {
        }

        public void onClick(View v) {
            if (t_radio.this.mMediaController != null) {
                t_radio.this.mMediaController.hide();
            }
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08203 implements OnClickListener {
        C08203() {
        }

        public void onClick(View v) {
            t_radio.this.mMediaController.show(0);
        }
    }

    class C08214 implements OnItemClickListener {
        C08214() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_radio.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_radio.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_radio.this.globales.menu_a_secciones[position]));
            t_radio.this.onClick(view);
        }
    }

    class C08225 implements OnClickListener {
        C08225() {
        }

        public void onClick(View v) {
            if (t_radio.this.mMediaController != null) {
                t_radio.this.mMediaController.hide();
            }
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C08236 implements OnClickListener {
        C08236() {
        }

        public void onClick(View v) {
            if (t_radio.this.mMediaController != null) {
                t_radio.this.mMediaController.hide();
            }
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    private class cargarfoto extends AsyncTask<String, Void, Byte> {
        String idfoto;
        String ind;
        String vfoto;

        private cargarfoto() {
        }

        protected Byte doInBackground(String... params) {
            this.idfoto = params[0];
            this.ind = params[1];
            this.vfoto = params[2];
            String url_aux = "http://imgs1.e-droid.net/srv/imgs/radio/" + this.idfoto + "_fondo.png?v=" + this.vfoto;
            String archivo_aux = "fondo_" + this.idfoto;
            try {
                URL myFileUrl = new URL(url_aux);
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    Bitmap bm_foto = BitmapFactory.decodeStream(conn.getInputStream());
                    try {
                        FileOutputStream fos = t_radio.this.openFileOutput(archivo_aux, 0);
                        bm_foto.compress(CompressFormat.PNG, 100, fos);
                        fos.close();
                        url = myFileUrl;
                        return Byte.valueOf((byte) 0);
                    } catch (Exception e) {
                        url = myFileUrl;
                        return Byte.valueOf((byte) -1);
                    }
                } catch (IOException e2) {
                    url = myFileUrl;
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e3) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPreExecute() {
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 0) {
                try {
                    t_radio.this.globales.file_to_iv("fondo_" + this.idfoto, (ImageView) t_radio.this.findViewById(C0627R.id.iv_radio));
                } catch (Exception e) {
                }
                Editor editor = t_radio.this.getSharedPreferences("sh", 0).edit();
                editor.putInt("s" + this.idfoto + "_fondo_modif", 0);
                editor.commit();
                t_radio.this.globales.secciones_a[Integer.parseInt(this.ind)].fondo_modif = false;
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
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
        this.extras = getIntent().getExtras();
        this.ind = this.extras.getInt("ind");
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.ind].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_radio);
        incluir_menu_pre();
        if (this.globales.tipomenu == 1) {
            ImageView iv_icohome = (ImageView) findViewById(C0627R.id.icohome);
            ((ImageView) findViewById(C0627R.id.iv_drawer)).setOnClickListener(new C08181());
            iv_icohome.setOnClickListener(new C08192());
        }
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        if (!this.globales.secciones_a[this.ind].c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.secciones_a[this.ind].c1), Color.parseColor("#" + this.globales.secciones_a[this.ind].c2)}));
        }
        if (this.globales.secciones_a[this.ind].idfondo > 0) {
            if (this.globales.secciones_a[this.ind].fondo_modif) {
                t_radio hse_here2_t_radio = this;
                new cargarfoto().execute(new String[]{this.globales.secciones_a[this.ind].idfondo + "", this.ind + "", this.globales.secciones_a[this.ind].vfondo + ""});
            } else {
                try {
                    this.globales.file_to_iv("fondo_" + this.globales.secciones_a[this.ind].idfondo, (ImageView) findViewById(C0627R.id.iv_radio));
                } catch (Exception e) {
                }
            }
        }
        this.sh_mp = getSharedPreferences("sh_mp", 0);
        this.sh_mc = getSharedPreferences("sh_mc", 0);
        this.esStream = this.globales.secciones_a[this.ind].stream;
        this.radio_mostrar = this.globales.secciones_a[this.ind].radio_mostrar;
        if (this.radio_mostrar > 0) {
            this.tv_artist = (TextView) findViewById(C0627R.id.tv_artist);
            this.tv_song = (TextView) findViewById(C0627R.id.tv_song);
            this.sh_mc.registerOnSharedPreferenceChangeListener(this);
            Editor e_mc = this.sh_mc.edit();
            e_mc.remove("t_radio_artist");
            e_mc.remove("t_radio_song");
            e_mc.commit();
            if (this.globales.radio_artist_b) {
                this.tv_artist.setTypeface(this.tv_artist.getTypeface(), 1);
            }
            if (this.globales.radio_song_b) {
                this.tv_song.setTypeface(this.tv_song.getTypeface(), 1);
            }
            if (this.globales.radio_artist_size != 0) {
                this.tv_artist.setTextSize(2, (float) this.globales.radio_artist_size);
            }
            if (this.globales.radio_song_size != 0) {
                this.tv_song.setTextSize(2, (float) this.globales.radio_song_size);
            }
            LinearLayout ll_info = (LinearLayout) findViewById(C0627R.id.ll_info);
            LayoutParams relativeParams = new LayoutParams(-2, -2);
            int marge = (int) ((10.0f * getResources().getDisplayMetrics().density) + 0.5f);
            relativeParams.setMargins(marge, marge, marge, marge);
            if (this.globales.radio_pos == 1) {
                relativeParams.addRule(10);
                relativeParams.addRule(14);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 2) {
                relativeParams.addRule(10);
                relativeParams.addRule(11);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 3) {
                relativeParams.addRule(15);
                relativeParams.addRule(11);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 4) {
                relativeParams.addRule(12);
                relativeParams.addRule(11);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 5) {
                relativeParams.addRule(12);
                relativeParams.addRule(14);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 6) {
                relativeParams.addRule(12);
                relativeParams.addRule(9);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 7) {
                relativeParams.addRule(15);
                relativeParams.addRule(9);
                ll_info.setLayoutParams(relativeParams);
            } else if (this.globales.radio_pos == 8) {
                relativeParams.addRule(10);
                relativeParams.addRule(9);
                ll_info.setLayoutParams(relativeParams);
            }
            if (this.globales.radio_txt_c) {
                ll_info.setGravity(1);
            }
            String c_aux = this.globales.radio_artist_col;
            int c_aux_int = 0;
            boolean c_aux_ok = false;
            if (!c_aux.equals("")) {
                c_aux_int = Color.parseColor("#" + c_aux);
                c_aux_ok = true;
            } else if (!this.globales.secciones_a[this.ind].c1.equals("")) {
                if (config.esClaro("#" + this.globales.secciones_a[this.ind].c1)) {
                    c_aux_int = config.NEGRO;
                } else {
                    c_aux_int = config.BLANCO;
                }
                c_aux_ok = true;
            }
            if (c_aux_ok) {
                this.tv_artist.setTextColor(c_aux_int);
            }
            c_aux = this.globales.radio_song_col;
            c_aux_int = 0;
            c_aux_ok = false;
            if (!c_aux.equals("")) {
                c_aux_int = Color.parseColor("#" + c_aux);
                c_aux_ok = true;
            } else if (!this.globales.secciones_a[this.ind].c1.equals("")) {
                if (config.esClaro("#" + this.globales.secciones_a[this.ind].c1)) {
                    c_aux_int = config.NEGRO_2;
                } else {
                    c_aux_int = config.BLANCO_2;
                }
                c_aux_ok = true;
            }
            if (c_aux_ok) {
                this.tv_song.setTextColor(c_aux_int);
            }
            if (this.radio_mostrar == 1 || this.radio_mostrar == 2) {
                findViewById(C0627R.id.tv_artist).setVisibility(0);
            }
            if (this.radio_mostrar == 1 || this.radio_mostrar == 3) {
                findViewById(C0627R.id.tv_song).setVisibility(0);
            }
            findViewById(C0627R.id.ll_info).setVisibility(0);
        }
        if (this.esStream) {
            this.mMediaController = new MediaController(this, false);
        } else {
            this.mMediaController = new MediaController(this);
        }
        this.mMediaController.setMediaPlayer(this);
        this.mMediaController.setAnchorView(findViewById(C0627R.id.rl_radio));
        findViewById(C0627R.id.rl_radio).setOnClickListener(new C08203());
        Intent i = new Intent(this, s_mediaplayer.class);
        i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.globales.secciones_a[this.ind].url);
        i.putExtra("accion", "iniciar");
        i.putExtra("idsecc", this.globales.secciones_a[this.ind].id);
        i.putExtra("radio_mostrar", this.radio_mostrar);
        i.putExtra("secc_tit", this.globales.secciones_a[this.ind].titulo);
        i.putExtra("esStream", this.esStream);
        startService(i);
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
            this.mDrawerList.setOnItemClickListener(new C08214());
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
        try {
            this.mMediaController.show(0);
        } catch (Exception e) {
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        String aux1;
        if (key.equals("t_radio_artist")) {
            aux1 = prefs.getString("t_radio_artist", "");
            if (aux1.contains("�")) {
                try {
                    aux1 = new String(aux1.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            this.tv_artist.setText(aux1);
        } else if (key.equals("t_radio_song")) {
            aux1 = prefs.getString("t_radio_song", "");
            if (aux1.contains("�")) {
                try {
                    aux1 = new String(aux1.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
            this.tv_song.setText(aux1);
        } else if (this.mMediaController.isShowing()) {
            this.mMediaController.show(0);
        }
    }

    public void onPause() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.pause();
        }
        super.onPause();
        this.sh_mc.unregisterOnSharedPreferenceChangeListener(this);
        this.mMediaController.hide();
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
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.resume();
        }
        if (!(this.radio_mostrar <= 0 || this.tv_artist == null || this.tv_song == null || this.sh_mc == null)) {
            String aux1 = this.sh_mc.getString("t_radio_artist", "");
            if (aux1.contains("�")) {
                try {
                    aux1 = new String(aux1.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            this.tv_artist.setText(aux1);
            aux1 = this.sh_mc.getString("t_radio_song", "");
            if (aux1.contains("�")) {
                try {
                    aux1 = new String(aux1.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e2) {
                    e2.printStackTrace();
                }
            }
            this.tv_song.setText(aux1);
        }
        this.sh_mc.registerOnSharedPreferenceChangeListener(this);
    }

    public void onDestroy() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.destroy();
        }
        if (!(this.es_root && isFinishing()) && config.finalizar_app) {
            super.onDestroy();
        } else {
            super.onDestroy();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mMediaController != null) {
            this.mMediaController.hide();
        }
        ((LinearLayout) findViewById(C0627R.id.ll_princ)).removeViewAt(0);
        incluir_menu_pre();
        if (this.globales.tipomenu == 1) {
            ImageView iv_icohome = (ImageView) findViewById(C0627R.id.icohome);
            ((ImageView) findViewById(C0627R.id.iv_drawer)).setOnClickListener(new C08225());
            iv_icohome.setOnClickListener(new C08236());
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

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getBufferPercentage() {
        try {
            if (getDuration() > 0) {
                return (getCurrentPosition() * 100) / getDuration();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getCurrentPosition() {
        return this.sh_mp.getInt("position", 0);
    }

    public int getDuration() {
        return this.sh_mp.getInt("duration", -1);
    }

    public boolean isPlaying() {
        return this.sh_mp.getBoolean("isPlaying", false);
    }

    public void pause() {
        Intent i = new Intent(this, s_mediaplayer.class);
        i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.globales.secciones_a[this.ind].url);
        i.putExtra("accion", "pause");
        i.putExtra("idsecc", this.globales.secciones_a[this.ind].id);
        i.putExtra("radio_mostrar", this.radio_mostrar);
        i.putExtra("secc_tit", this.globales.secciones_a[this.ind].titulo);
        i.putExtra("esStream", this.esStream);
        startService(i);
    }

    public void seekTo(int pos) {
        Intent i = new Intent(this, s_mediaplayer.class);
        i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.globales.secciones_a[this.ind].url);
        i.putExtra("accion", "seekto");
        i.putExtra("valor", pos);
        i.putExtra("idsecc", this.globales.secciones_a[this.ind].id);
        i.putExtra("secc_tit", this.globales.secciones_a[this.ind].titulo);
        i.putExtra("esStream", this.esStream);
        i.putExtra("radio_mostrar", this.radio_mostrar);
        startService(i);
    }

    public void start() {
        Intent i = new Intent(this, s_mediaplayer.class);
        i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.globales.secciones_a[this.ind].url);
        i.putExtra("accion", "play");
        i.putExtra("idsecc", this.globales.secciones_a[this.ind].id);
        i.putExtra("radio_mostrar", this.radio_mostrar);
        i.putExtra("secc_tit", this.globales.secciones_a[this.ind].titulo);
        i.putExtra("esStream", this.esStream);
        startService(i);
    }

    public int getAudioSessionId() {
        return 0;
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
