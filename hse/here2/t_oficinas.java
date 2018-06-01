package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class t_oficinas extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private AdView adView;
    boolean atras_pulsado = false;
    Bitmap bm_foto;
    String cbtn;
    int[][] coord_fotoacargar;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    File file_global;
    boolean finalizar = false;
    config globales;
    int[] id_fotoacargar;
    int ind_fotoacargar;
    ImageView[] iv_fotoacargar;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    int n_fotoacargar;
    Oficina f65o;
    ProgressBar[] pb_fotoacargar;
    View v_abrir_secc;

    class C08121 implements OnClickListener {
        private File file = t_oficinas.this.file_global;

        C08121() {
        }

        public void onClick(View v) {
            if (this.file != null && this.file.exists()) {
                Intent i = new Intent(t_oficinas.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.file);
                i.putExtra("bg1", t_oficinas.this.globales.c1_ofic);
                i.putExtra("bg2", t_oficinas.this.globales.c2_ofic);
                t_oficinas.this.startActivityForResult(i, 0);
            }
        }
    }

    class C08132 implements OnClickListener {
        private File file = t_oficinas.this.file_global;

        C08132() {
        }

        public void onClick(View v) {
            if (this.file != null && this.file.exists()) {
                Intent i = new Intent(t_oficinas.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.file);
                i.putExtra("bg1", t_oficinas.this.globales.c1_ofic);
                i.putExtra("bg2", t_oficinas.this.globales.c2_ofic);
                t_oficinas.this.startActivityForResult(i, 0);
            }
        }
    }

    class C08143 implements OnClickListener {
        private File file = t_oficinas.this.file_global;

        C08143() {
        }

        public void onClick(View v) {
            if (this.file != null && this.file.exists()) {
                Intent i = new Intent(t_oficinas.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.file);
                i.putExtra("bg1", t_oficinas.this.globales.c1_ofic);
                i.putExtra("bg2", t_oficinas.this.globales.c2_ofic);
                t_oficinas.this.startActivityForResult(i, 0);
            }
        }
    }

    class C08154 implements OnClickListener {
        private File file = t_oficinas.this.file_global;

        C08154() {
        }

        public void onClick(View v) {
            if (this.file != null && this.file.exists()) {
                Intent i = new Intent(t_oficinas.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.file);
                i.putExtra("bg1", t_oficinas.this.globales.c1_ofic);
                i.putExtra("bg2", t_oficinas.this.globales.c2_ofic);
                t_oficinas.this.startActivityForResult(i, 0);
            }
        }
    }

    class C08165 implements OnItemClickListener {
        C08165() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_oficinas.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_oficinas.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_oficinas.this.globales.menu_a_secciones[position]));
            t_oficinas.this.onClick(view);
        }
    }

    private class cargarfoto extends AsyncTask<String, Void, Byte> {
        private cargarfoto() {
        }

        protected Byte doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/ofics/f" + t_oficinas.this.id_fotoacargar[t_oficinas.this.ind_fotoacargar] + ".png");
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    t_oficinas.this.bm_foto = BitmapFactory.decodeStream(is);
                    try {
                        FileOutputStream fos = t_oficinas.this.openFileOutput("o_f" + t_oficinas.this.id_fotoacargar[t_oficinas.this.ind_fotoacargar] + ".png", 0);
                        t_oficinas.this.bm_foto.compress(CompressFormat.PNG, 100, fos);
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
                t_oficinas.this.iv_fotoacargar[t_oficinas.this.ind_fotoacargar].setImageBitmap(t_oficinas.this.bm_foto);
                t_oficinas.this.pb_fotoacargar[t_oficinas.this.ind_fotoacargar].setVisibility(8);
                t_oficinas.this.iv_fotoacargar[t_oficinas.this.ind_fotoacargar].setVisibility(0);
                Editor editor = t_oficinas.this.getSharedPreferences("sh", 0).edit();
                editor.putBoolean("o_f" + t_oficinas.this.id_fotoacargar[t_oficinas.this.ind_fotoacargar] + "_modif", false);
                editor.commit();
                int ind_ofic = t_oficinas.this.coord_fotoacargar[t_oficinas.this.ind_fotoacargar][0];
                int nfoto = t_oficinas.this.coord_fotoacargar[t_oficinas.this.ind_fotoacargar][1];
                if (nfoto == 1) {
                    t_oficinas.this.globales.oficinas_a[ind_ofic].f1_modif = false;
                } else if (nfoto == 2) {
                    t_oficinas.this.globales.oficinas_a[ind_ofic].f2_modif = false;
                } else if (nfoto == 3) {
                    t_oficinas.this.globales.oficinas_a[ind_ofic].f3_modif = false;
                } else if (nfoto == 4) {
                    t_oficinas.this.globales.oficinas_a[ind_ofic].f4_modif = false;
                }
            }
            t_oficinas hse_here2_t_oficinas = t_oficinas.this;
            hse_here2_t_oficinas.ind_fotoacargar++;
            if (t_oficinas.this.ind_fotoacargar < t_oficinas.this.n_fotoacargar) {
                new cargarfoto().execute(new String[0]);
            }
        }
    }

    private class datosMapa {
        String cp;
        String dir1;
        String dir2;
        String pob;
        String prov;
        String titulo;
        int f44x;
        int f45y;
        int f46z;

        private datosMapa() {
        }
    }

    @TargetApi(21)
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        int w_aux;
        this.globales = (config) getApplicationContext();
        this.cbtn = config.aplicar_color_dialog("FFFFFFFF", this.globales.c_ir_ofic);
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_oficinas);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            config hse_here2_config = this.globales;
            z = this.extras != null && this.extras.containsKey("ad_entrar");
            hse_here2_config.toca_int(this, z);
        }
        this.adView = this.globales.mostrar_banner(this, false);
        LinearLayout ll_oficinas = (LinearLayout) findViewById(C0627R.id.ll_oficinas);
        Display display = getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            w_aux = size.x;
        } else {
            w_aux = display.getWidth();
        }
        int width_disponible = w_aux;
        LayoutInflater inflater = LayoutInflater.from(this);
        this.n_fotoacargar = 0;
        this.id_fotoacargar = new int[(this.globales.oficinas_a.length * 4)];
        this.iv_fotoacargar = new ImageView[(this.globales.oficinas_a.length * 4)];
        this.pb_fotoacargar = new ProgressBar[(this.globales.oficinas_a.length * 4)];
        this.coord_fotoacargar = (int[][]) Array.newInstance(Integer.TYPE, new int[]{this.globales.oficinas_a.length * 4, 2});
        for (int i = 0; i < this.globales.oficinas_a.length; i++) {
            View v_ofic;
            ImageView iv;
            ProgressBar pb;
            String f_aux;
            File f_png;
            this.f65o = this.globales.oficinas_a[i];
            if (width_disponible < ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
                v_ofic = inflater.inflate(C0627R.layout.t_oficina_v, null);
            } else {
                v_ofic = inflater.inflate(C0627R.layout.t_oficina_h, null);
            }
            TextView tv = (TextView) v_ofic.findViewById(C0627R.id.tv_tit);
            if (!this.globales.c_tit_ofic.equals("")) {
                tv.setTextColor(Color.parseColor("#" + this.globales.c_tit_ofic));
            }
            tv.setText(this.f65o.titulo);
            if (!this.globales.c_sep_ofic.equals("")) {
                v_ofic.findViewById(C0627R.id.v_sep_ofic).setBackgroundColor(Color.parseColor("#" + this.globales.c_sep_ofic));
            }
            if (!this.globales.c_ico_sep_ofic.equals("")) {
                Drawable d = getResources().getDrawable(C0627R.drawable.locate);
                d.setColorFilter(Color.parseColor("#" + this.globales.c_ico_sep_ofic), Mode.MULTIPLY);
                ((ImageView) v_ofic.findViewById(C0627R.id.ico_ofic)).setImageDrawable(d);
            }
            boolean t_dir = false;
            boolean t_contacto = false;
            if (!this.f65o.dir1.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_dir1);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.dir1);
                tv.setVisibility(0);
                t_dir = true;
            }
            if (!this.f65o.dir2.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_dir2);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.dir2);
                tv.setVisibility(0);
                t_dir = true;
            }
            String cp_pob = "";
            if (!this.f65o.cp.equals("")) {
                cp_pob = this.f65o.cp;
            }
            if (!this.f65o.pob.equals("")) {
                if (!cp_pob.equals("")) {
                    cp_pob = cp_pob + " ";
                }
                cp_pob = cp_pob + this.f65o.pob;
            }
            if (!cp_pob.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_cp_pob);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(cp_pob);
                tv.setVisibility(0);
                t_dir = true;
            }
            if (!this.f65o.prov.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_prov);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.prov);
                tv.setVisibility(0);
                t_dir = true;
            }
            if (!this.f65o.tel1.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_tel1);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.tel1);
                if (!this.globales.c_icos_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.tel);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_tel1)).setImageDrawable(d);
                }
                if (!this.globales.c_ir_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.ir);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_tel1_ir)).setImageDrawable(d);
                }
                v_ofic.findViewById(C0627R.id.ll_tel1).setOnClickListener(this);
                v_ofic.findViewById(C0627R.id.ll_tel1).setVisibility(0);
                t_contacto = true;
            }
            if (!this.f65o.tel2.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_tel2);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.tel2);
                if (!this.globales.c_icos_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.tel);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_tel2)).setImageDrawable(d);
                }
                if (!this.globales.c_ir_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.ir);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_tel2_ir)).setImageDrawable(d);
                }
                v_ofic.findViewById(C0627R.id.ll_tel2).setOnClickListener(this);
                v_ofic.findViewById(C0627R.id.ll_tel2).setVisibility(0);
                t_contacto = true;
            }
            if (!this.f65o.horario.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_horario);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.horario);
                if (!this.globales.c_icos_ofic.equals("")) {
                    if (this.globales.c_icos_ofic.equals("FF000000")) {
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_horario)).setImageDrawable(getResources().getDrawable(C0627R.drawable.reloj_negro));
                    } else {
                        d = getResources().getDrawable(C0627R.drawable.reloj);
                        d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_horario)).setImageDrawable(d);
                    }
                }
                v_ofic.findViewById(C0627R.id.ll_horario).setVisibility(0);
                t_contacto = true;
            }
            if (this.f65o.chat) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_chat);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                if (!this.globales.c_icos_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.chat);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_chat_ofic)).setImageDrawable(d);
                }
                if (!this.globales.c_ir_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.ir);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_chat_ir)).setImageDrawable(d);
                }
                v_ofic.findViewById(C0627R.id.ll_chat).setTag(Integer.valueOf(this.f65o.id));
                v_ofic.findViewById(C0627R.id.ll_chat).setOnClickListener(this);
                v_ofic.findViewById(C0627R.id.ll_chat).setVisibility(0);
                t_contacto = true;
            }
            if (this.f65o.t_email) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_email);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                if (this.f65o.email.equals("")) {
                    tv.setText(getResources().getString(C0627R.string.enviar_email));
                } else {
                    tv.setText(this.f65o.email);
                }
                if (!this.globales.c_icos_ofic.equals("")) {
                    if (this.globales.c_icos_ofic.equals("FF000000")) {
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_email)).setImageDrawable(getResources().getDrawable(C0627R.drawable.email_negro));
                    } else {
                        d = getResources().getDrawable(C0627R.drawable.email);
                        d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_email)).setImageDrawable(d);
                    }
                }
                if (!this.globales.c_ir_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.ir);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_email_ir)).setImageDrawable(d);
                }
                v_ofic.findViewById(C0627R.id.ll_email).setTag(Integer.valueOf(this.f65o.id));
                v_ofic.findViewById(C0627R.id.ll_email).setOnClickListener(this);
                v_ofic.findViewById(C0627R.id.ll_email).setVisibility(0);
                t_contacto = true;
            }
            if (!this.f65o.web.equals("")) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.tv_url);
                if (!this.globales.c_txt_ofic.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_ofic));
                }
                tv.setText(this.f65o.web);
                if (!this.globales.c_icos_ofic.equals("")) {
                    if (this.globales.c_icos_ofic.equals("FF000000")) {
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_url)).setImageDrawable(getResources().getDrawable(C0627R.drawable.url_negro));
                    } else {
                        d = getResources().getDrawable(C0627R.drawable.url_png);
                        d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_ofic), Mode.MULTIPLY);
                        ((ImageView) v_ofic.findViewById(C0627R.id.iv_url)).setImageDrawable(d);
                    }
                }
                if (!this.globales.c_ir_ofic.equals("")) {
                    d = getResources().getDrawable(C0627R.drawable.ir);
                    d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
                    ((ImageView) v_ofic.findViewById(C0627R.id.iv_url_ir)).setImageDrawable(d);
                }
                v_ofic.findViewById(C0627R.id.ll_url).setOnClickListener(this);
                v_ofic.findViewById(C0627R.id.ll_url).setVisibility(0);
                t_contacto = true;
            }
            if (this.f65o.f31x != 0) {
                tv = (TextView) v_ofic.findViewById(C0627R.id.btn_vermapa);
                datosMapa dm = new datosMapa();
                dm.f44x = this.f65o.f31x;
                dm.f45y = this.f65o.f32y;
                dm.f46z = this.f65o.f33z;
                dm.titulo = this.f65o.titulo;
                dm.dir1 = this.f65o.dir1;
                dm.dir2 = this.f65o.dir2;
                dm.cp = this.f65o.cp;
                dm.pob = this.f65o.pob;
                dm.prov = this.f65o.prov;
                if (!this.globales.c_ir_ofic.equals("")) {
                    tv.setBackgroundColor(Color.parseColor("#" + this.globales.c_ir_ofic));
                    if (config.esClaro("#" + this.globales.c_ir_ofic)) {
                        tv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    }
                }
                tv.setTag(dm);
                tv.setOnClickListener(this);
                tv.setVisibility(0);
                t_dir = true;
            }
            if (t_dir) {
                v_ofic.findViewById(C0627R.id.ll_dir_ofic).setVisibility(0);
            }
            if (t_contacto) {
                v_ofic.findViewById(C0627R.id.ll_contacto_ofic).setVisibility(0);
            }
            if (t_dir && t_contacto && width_disponible < ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
                v_ofic.findViewById(C0627R.id.v_sepdatos_ofic).setVisibility(0);
            }
            boolean pb_inverse = false;
            if (!this.globales.c1_ofic.equals("") && config.esClaro("#" + this.globales.c1_ofic)) {
                pb_inverse = true;
            }
            if (this.f65o.nfotos > 0) {
                v_ofic.findViewById(C0627R.id.ll_fotos_ofic).setVisibility(0);
                iv = (ImageView) v_ofic.findViewById(C0627R.id.iv_ofic_1);
                if (this.f65o.fotos_z) {
                    this.file_global = getFileStreamPath("o_f" + this.f65o.f1_id + ".png");
                    iv.setOnClickListener(new C08121());
                }
                if (pb_inverse) {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_1_inverse);
                } else {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_1);
                }
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(pb, this.globales.c_ir_ofic);
                }
                if (this.f65o.f1_modif) {
                    this.id_fotoacargar[this.n_fotoacargar] = this.f65o.f1_id;
                    this.iv_fotoacargar[this.n_fotoacargar] = iv;
                    this.pb_fotoacargar[this.n_fotoacargar] = pb;
                    this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                    this.coord_fotoacargar[this.n_fotoacargar][1] = 1;
                    this.n_fotoacargar++;
                    pb.setVisibility(0);
                } else {
                    f_aux = "o_f" + this.f65o.f1_id;
                    f_png = getFileStreamPath(f_aux + ".png");
                    if (!f_png.exists()) {
                        getFileStreamPath(f_aux).renameTo(f_png);
                    }
                    this.globales.file_to_iv("o_f" + this.f65o.f1_id + ".png", iv);
                    iv.setVisibility(0);
                }
            }
            if (this.f65o.nfotos > 1) {
                iv = (ImageView) v_ofic.findViewById(C0627R.id.iv_ofic_2);
                if (this.f65o.fotos_z) {
                    this.file_global = getFileStreamPath("o_f" + this.f65o.f2_id + ".png");
                    iv.setOnClickListener(new C08132());
                }
                if (pb_inverse) {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_2_inverse);
                } else {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_2);
                }
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(pb, this.globales.c_ir_ofic);
                }
                if (this.f65o.f2_modif) {
                    this.id_fotoacargar[this.n_fotoacargar] = this.f65o.f2_id;
                    this.iv_fotoacargar[this.n_fotoacargar] = iv;
                    this.pb_fotoacargar[this.n_fotoacargar] = pb;
                    this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                    this.coord_fotoacargar[this.n_fotoacargar][1] = 2;
                    this.n_fotoacargar++;
                    pb.setVisibility(0);
                } else {
                    f_aux = "o_f" + this.f65o.f2_id;
                    f_png = getFileStreamPath(f_aux + ".png");
                    if (!f_png.exists()) {
                        getFileStreamPath(f_aux).renameTo(f_png);
                    }
                    this.globales.file_to_iv("o_f" + this.f65o.f2_id + ".png", iv);
                    iv.setVisibility(0);
                }
            }
            if (this.f65o.nfotos > 2) {
                iv = (ImageView) v_ofic.findViewById(C0627R.id.iv_ofic_3);
                if (this.f65o.fotos_z) {
                    this.file_global = getFileStreamPath("o_f" + this.f65o.f3_id + ".png");
                    iv.setOnClickListener(new C08143());
                }
                if (pb_inverse) {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_3_inverse);
                } else {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_3);
                }
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(pb, this.globales.c_ir_ofic);
                }
                if (this.f65o.f3_modif) {
                    this.id_fotoacargar[this.n_fotoacargar] = this.f65o.f3_id;
                    this.iv_fotoacargar[this.n_fotoacargar] = iv;
                    this.pb_fotoacargar[this.n_fotoacargar] = pb;
                    this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                    this.coord_fotoacargar[this.n_fotoacargar][1] = 3;
                    this.n_fotoacargar++;
                    pb.setVisibility(0);
                } else {
                    f_aux = "o_f" + this.f65o.f3_id;
                    f_png = getFileStreamPath(f_aux + ".png");
                    if (!f_png.exists()) {
                        getFileStreamPath(f_aux).renameTo(f_png);
                    }
                    this.globales.file_to_iv("o_f" + this.f65o.f3_id + ".png", iv);
                    iv.setVisibility(0);
                }
            }
            if (this.f65o.nfotos > 3) {
                iv = (ImageView) v_ofic.findViewById(C0627R.id.iv_ofic_4);
                if (this.f65o.fotos_z) {
                    this.file_global = getFileStreamPath("o_f" + this.f65o.f4_id + ".png");
                    iv.setOnClickListener(new C08154());
                }
                if (pb_inverse) {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_4_inverse);
                } else {
                    pb = (ProgressBar) v_ofic.findViewById(C0627R.id.pb_ofic_4);
                }
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(pb, this.globales.c_ir_ofic);
                }
                if (this.f65o.f4_modif) {
                    this.id_fotoacargar[this.n_fotoacargar] = this.f65o.f4_id;
                    this.iv_fotoacargar[this.n_fotoacargar] = iv;
                    this.pb_fotoacargar[this.n_fotoacargar] = pb;
                    this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                    this.coord_fotoacargar[this.n_fotoacargar][1] = 4;
                    this.n_fotoacargar++;
                    pb.setVisibility(0);
                } else {
                    f_aux = "o_f" + this.f65o.f4_id;
                    f_png = getFileStreamPath(f_aux + ".png");
                    if (!f_png.exists()) {
                        getFileStreamPath(f_aux).renameTo(f_png);
                    }
                    this.globales.file_to_iv("o_f" + this.f65o.f4_id + ".png", iv);
                    iv.setVisibility(0);
                }
            }
            if (this.globales.c_perofic == 1 && !this.globales.c1_ofic.equals("")) {
                v_ofic.setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_ofic), Color.parseColor("#" + this.globales.c2_ofic)}));
                findViewById(C0627R.id.ll_princ).setBackgroundColor(Color.parseColor("#" + this.globales.c2_ofic));
            }
            ll_oficinas.addView(v_ofic);
        }
        if (this.globales.c_perofic == 0 && !this.globales.c1_ofic.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_ofic), Color.parseColor("#" + this.globales.c2_ofic)}));
        }
        if (this.n_fotoacargar > 0) {
            this.ind_fotoacargar = 0;
            new cargarfoto().execute(new String[0]);
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.ll_tel1) {
            try {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + ((TextView) v.findViewById(C0627R.id.tv_tel1)).getText())));
            } catch (Exception e) {
            }
        } else if (v.getId() == C0627R.id.ll_tel2) {
            try {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + ((TextView) v.findViewById(C0627R.id.tv_tel2)).getText())));
            } catch (Exception e2) {
            }
        } else if (v.getId() == C0627R.id.ll_chat) {
            startActivityForResult(new Intent(this, chat.class), 0);
        } else if (v.getId() == C0627R.id.ll_email) {
            i = new Intent(this, contactar.class);
            i.putExtra("idofic", (Integer) v.getTag());
            if (this.extras != null && this.extras.containsKey("msg_predefinido")) {
                i.putExtra("msg_predefinido", this.extras.getString("msg_predefinido"));
            }
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.ll_url) {
            String url = (String) ((TextView) v.findViewById(C0627R.id.tv_url)).getText();
            i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (v.getId() == C0627R.id.btn_vermapa) {
            datosMapa dm = (datosMapa) v.getTag();
            i = new Intent(this, t_mapa_web.class);
            i.putExtra("x", dm.f44x);
            i.putExtra("y", dm.f45y);
            i.putExtra("z", dm.f46z);
            i.putExtra("titulo", dm.titulo);
            i.putExtra("dir1", dm.dir1);
            i.putExtra("dir2", dm.dir2);
            i.putExtra("cp", dm.cp);
            i.putExtra("pob", dm.pob);
            i.putExtra("prov", dm.prov);
            startActivityForResult(i, 0);
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

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C08165());
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

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("es_root", this.es_root);
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
        if ((this.es_root && isFinishing()) || config.finalizar_app) {
            config.finalizar_app(this);
        }
        super.onDestroy();
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

    void mostrar_error(String titulo, String texto) {
        Builder builder = new Builder(this);
        builder.setCancelable(false).setPositiveButton(getString(C0627R.string.cerrar), null).setMessage(texto);
        if (!titulo.equals("")) {
            builder.setTitle(titulo);
        }
        final AlertDialog d_aux = builder.create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_oficinas.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
