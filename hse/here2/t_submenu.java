package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
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
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class t_submenu extends Activity implements OnClickListener, OnSharedPreferenceChangeListener, OnItemClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    int H_MAXIMA = 80;
    int H_MAXIMA_ROW = 30;
    GridAdapter MyGridAdapter;
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    ProgressDialog dialog_cargando;
    boolean esMas = false;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    GridView gridView;
    int h_max;
    int ind;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    ProgressBar pb;
    Seccion f68s;
    Seccion[] seccs_a;
    String[] seccs_a_aux;
    int seccs_a_length = 0;
    View v_abrir_secc;
    int w_max;
    int w_txt_max;

    class C08311 implements OnItemClickListener {
        C08311() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_submenu.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_submenu.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_submenu.this.globales.menu_a_secciones[position]));
            t_submenu.this.onClick(view);
        }
    }

    public class GridAdapter extends BaseAdapter {
        private Context context;

        public GridAdapter(Context c) {
            this.context = c;
        }

        public int getCount() {
            return t_submenu.this.seccs_a_length;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView icon;
            View row = convertView;
            if (row == null) {
                int layout_aux;
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
                if (t_submenu.this.f68s.icos_izq) {
                    layout_aux = C0627R.layout.t_menugrid_row;
                } else if (t_submenu.this.f68s.estilo == 1) {
                    layout_aux = C0627R.layout.t_menugrid_row_formato2;
                } else {
                    layout_aux = C0627R.layout.t_menugrid_row_v;
                }
                row = inflater.inflate(layout_aux, parent, false);
                if (!t_submenu.this.f68s.icos_izq && t_submenu.this.f68s.estilo == 1) {
                    ((CardView) row.findViewById(C0627R.id.cv)).setRadius((float) t_submenu.this.f68s.txt_radius);
                }
                if (t_submenu.this.f68s.mostrar_icos) {
                    icon = (ImageView) row.findViewById(C0627R.id.iv_menugrid);
                    if (t_submenu.this.f68s.icos_izq) {
                        icon.getLayoutParams().height = t_submenu.this.h_max;
                        icon.getLayoutParams().width = t_submenu.this.w_max;
                    } else {
                        icon.getLayoutParams().height = t_submenu.this.h_max;
                        icon.getLayoutParams().width = t_submenu.this.w_max;
                    }
                    icon.setVisibility(0);
                }
                if (t_submenu.this.f68s.mostrar_txt) {
                    TextView label = (TextView) row.findViewById(C0627R.id.tv_menugrid);
                    if (t_submenu.this.f68s.txt_b) {
                        label.setTypeface(label.getTypeface(), 1);
                    }
                    if (!t_submenu.this.f68s.txt_col.equals("")) {
                        label.setTextColor(Color.parseColor("#" + t_submenu.this.f68s.txt_col));
                    }
                    label.getLayoutParams().width = t_submenu.this.w_txt_max;
                    if (t_submenu.this.f68s.txt_c) {
                        label.setGravity(17);
                    }
                    if (t_submenu.this.f68s.txt_bg.equals("")) {
                        label.setBackgroundDrawable(null);
                    } else {
                        ((GradientDrawable) label.getBackground()).setColor(Color.parseColor("#" + t_submenu.this.f68s.txt_bg));
                        ((GradientDrawable) label.getBackground()).setCornerRadius((float) t_submenu.this.f68s.txt_radius);
                    }
                    label.setVisibility(0);
                }
            }
            if (t_submenu.this.f68s.mostrar_icos) {
                icon = (ImageView) row.findViewById(C0627R.id.iv_menugrid);
                if (t_submenu.this.seccs_a[position].ico_cargando || t_submenu.this.seccs_a[position].ico == null) {
                    icon.setImageBitmap(null);
                } else {
                    icon.setImageDrawable(new BitmapDrawable(t_submenu.this.getResources(), t_submenu.this.seccs_a[position].ico));
                }
            }
            if (t_submenu.this.f68s.mostrar_txt) {
                ((TextView) row.findViewById(C0627R.id.tv_menugrid)).setText(t_submenu.this.seccs_a[position].titulo);
            }
            return row;
        }
    }

    private class cargarfoto extends AsyncTask<String, Void, Byte> {
        String idfoto;
        String ind;
        String vfoto;

        private cargarfoto() {
        }

        protected Byte doInBackground(String... params) {
            URL url;
            this.idfoto = params[0];
            this.ind = params[1];
            this.vfoto = params[2];
            String url_aux = "http://imgs1.e-droid.net/srv/imgs/fondos_submenu/" + this.idfoto + "_fondo.png?v=" + this.vfoto;
            String archivo_aux = "fondo_" + this.idfoto;
            try {
                URL myFileUrl = new URL(url_aux);
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    Bitmap bm_foto = BitmapFactory.decodeStream(conn.getInputStream());
                    try {
                        FileOutputStream fos = t_submenu.this.openFileOutput(archivo_aux, 0);
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
                    t_submenu.this.globales.file_to_iv("fondo_" + this.idfoto, (ImageView) t_submenu.this.findViewById(C0627R.id.iv_fondo));
                } catch (Exception e) {
                }
                Editor editor = t_submenu.this.getSharedPreferences("sh", 0).edit();
                editor.putInt("s" + this.idfoto + "_fondo_modif", 0);
                editor.commit();
                t_submenu.this.globales.secciones_a[Integer.parseInt(this.ind)].fondo_modif = false;
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
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
        this.extras = getIntent().getExtras();
        this.ind = this.extras.getInt("ind");
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.ind].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_menugrid);
        getSharedPreferences("sh", 0).registerOnSharedPreferenceChangeListener(this);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            config hse_here2_config = this.globales;
            if (this.extras == null || !this.extras.containsKey("ad_entrar")) {
                z = false;
            } else {
                z = true;
            }
            hse_here2_config.toca_int(this, z);
        }
        this.f68s = this.globales.secciones_a[this.ind];
        if (!this.f68s.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.f68s.c1), Color.parseColor("#" + this.f68s.c2)}));
        }
        this.seccs_a = new Seccion[this.globales.secciones_a.length];
        this.seccs_a_aux = this.f68s.seccs.split(",");
        this.seccs_a_length = 0;
        if (config.esClaro("#" + this.f68s.c1)) {
            this.pb = (ProgressBar) findViewById(C0627R.id.pb_inv);
        } else {
            this.pb = (ProgressBar) findViewById(C0627R.id.pb);
        }
        if (VERSION.SDK_INT > 20) {
            config.progress_color(this.pb, this.globales.c_icos);
        }
        mostrar_grid();
        if (this.f68s.mostrar_icos) {
            Intent i = new Intent(this, s_cargar_icos.class);
            i.putExtra("ind_submenu", this.ind);
            startService(i);
        }
    }

    private void banner(int col_width_aux) {
        boolean hay_nat_admob = (this.globales.admob_menu_cod == "" || this.globales.admob_menu_w == 0 || this.globales.admob_menu_h == 0) ? false : true;
        boolean hay_nat_appnext = (this.globales.appnext_menu_cod == "" || this.globales.appnext_ads == null || this.globales.appnext_ads.size() <= 0) ? false : true;
        if (hay_nat_admob && hay_nat_appnext) {
            if (new Random().nextInt(2) + 1 == 1) {
                hay_nat_admob = false;
            } else {
                hay_nat_appnext = false;
            }
        }
        if (hay_nat_admob) {
            NativeExpressAdView adView_nat = new NativeExpressAdView(this);
            adView_nat.setAdSize(new AdSize(this.globales.admob_menu_w, this.globales.admob_menu_h));
            adView_nat.setAdUnitId(this.globales.admob_menu_cod);
            findViewById(C0627R.id.ll_appsreco).setVisibility(8);
            ((LinearLayout) findViewById(C0627R.id.ll_nat)).setVisibility(0);
            ((LinearLayout) findViewById(C0627R.id.ll_nat)).removeViewAt(1);
            ((LinearLayout) findViewById(C0627R.id.ll_nat)).addView(adView_nat, 1);
            adView_nat.loadAd(new Builder().build());
        } else if (hay_nat_appnext) {
            if (!this.f68s.c1.equals("") && config.esClaro("#" + this.f68s.c1)) {
                ((TextView) findViewById(C0627R.id.tv_appsreco)).setTextColor(config.NEGRO);
                ((ImageView) findViewById(C0627R.id.appnext_logo)).setImageDrawable(getResources().getDrawable(C0627R.drawable.info));
            }
            for (int i = 0; i < this.globales.appnext_ads.size(); i++) {
                this.globales.appnext_mostrar_2(this, 2, -1, i, col_width_aux, this.f68s.txt_col, this.f68s.txt_bg, this.f68s.f35c, this.f68s.txt_c, this.f68s.txt_radius, this.f68s.txt_b);
            }
        } else {
            this.adView = this.globales.mostrar_banner(this, false);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (this.MyGridAdapter != null && key.equals("ico_cargado")) {
            this.MyGridAdapter.notifyDataSetChanged();
        }
    }

    @TargetApi(13)
    void mostrar_grid() {
        int layout_aux;
        int col_width_aux;
        float scale = getResources().getDisplayMetrics().density;
        this.H_MAXIMA = (int) ((((float) this.H_MAXIMA) * scale) + 0.5f);
        this.H_MAXIMA_ROW = (int) ((((float) this.H_MAXIMA_ROW) * scale) + 0.5f);
        this.h_max = 0;
        this.w_max = 0;
        this.w_txt_max = 0;
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        if (this.f68s.icos_izq) {
            layout_aux = C0627R.layout.t_menugrid_row;
        } else if (this.f68s.estilo == 1) {
            layout_aux = C0627R.layout.t_menugrid_row_formato2;
        } else {
            layout_aux = C0627R.layout.t_menugrid_row_v;
        }
        TextView tv_aux = (TextView) inflater.inflate(layout_aux, (LinearLayout) findViewById(C0627R.id.ll_princ), false).findViewById(C0627R.id.tv_menugrid);
        for (String equals : this.seccs_a_aux) {
            Seccion s_aux = null;
            for (int j = 0; j < this.globales.secciones_a.length; j++) {
                if (equals.equals(this.globales.secciones_a[j].id + "")) {
                    s_aux = this.globales.secciones_a[j];
                    this.seccs_a[this.seccs_a_length] = s_aux;
                    this.seccs_a_length++;
                    break;
                }
            }
            if (s_aux != null) {
                if (this.f68s.mostrar_txt) {
                    tv_aux.setText(s_aux.titulo);
                    if (this.f68s.txt_b) {
                        tv_aux.setTypeface(tv_aux.getTypeface(), 1);
                    }
                    tv_aux.measure(0, 0);
                    this.w_txt_max = Math.max(this.w_txt_max, tv_aux.getMeasuredWidth());
                }
                if (this.f68s.mostrar_icos) {
                    if (!s_aux.ico_cargando && s_aux.ico != null) {
                        this.h_max = Math.max(this.h_max, s_aux.ico.getHeight());
                        this.w_max = Math.max(this.w_max, s_aux.ico.getWidth());
                    } else if (!(!s_aux.ico_cargando || s_aux.w_ico == 0 || s_aux.h_ico == 0)) {
                        this.h_max = Math.max(this.h_max, s_aux.h_ico);
                        this.w_max = Math.max(this.w_max, s_aux.w_ico);
                    }
                }
            }
        }
        this.h_max = (int) ((((float) this.h_max) * scale) + 0.5f);
        this.w_max = (int) ((((float) this.w_max) * scale) + 0.5f);
        Display display = getWindowManager().getDefaultDisplay();
        int width_disponible;
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width_disponible = size.x;
        } else {
            width_disponible = display.getWidth();
        }
        if (this.f68s.icos_izq && this.h_max > this.H_MAXIMA_ROW) {
            this.w_max = (this.w_max * this.H_MAXIMA_ROW) / this.h_max;
            this.h_max = this.H_MAXIMA_ROW;
        } else if (!this.f68s.icos_izq && this.h_max > this.H_MAXIMA) {
            this.w_max = (this.w_max * this.H_MAXIMA) / this.h_max;
            this.h_max = this.H_MAXIMA;
        }
        if (!this.f68s.icos_izq && this.f68s.mostrar_txt && this.w_max > this.w_txt_max) {
            this.h_max = (this.h_max * this.w_txt_max) / this.w_max;
            this.w_max = this.w_txt_max;
        }
        this.gridView = (GridView) findViewById(C0627R.id.grid);
        if (this.f68s.f35c) {
            ((LinearLayout) findViewById(C0627R.id.ll_grid)).setGravity(1);
        }
        int spacing_aux = 1;
        if (this.f68s.padding == 1) {
            spacing_aux = 16;
        } else if (this.f68s.padding == 2) {
            spacing_aux = 24;
        }
        spacing_aux = (int) ((((float) spacing_aux) * scale) + 0.5f);
        this.gridView.setHorizontalSpacing(spacing_aux);
        this.gridView.setVerticalSpacing(spacing_aux);
        int padding_grid = (int) ((10.0f * scale) + 0.5f);
        this.gridView.setPadding(padding_grid, padding_grid, padding_grid, padding_grid);
        int leftmargin_tv = (int) ((4.0f * scale) + 0.5f);
        int ncols_aux = this.f68s.ncols;
        if (this.f68s.icos_izq) {
            col_width_aux = (this.w_max + this.w_txt_max) + spacing_aux;
            if (this.f68s.mostrar_txt) {
                col_width_aux += leftmargin_tv;
            }
            banner(this.w_max + this.w_txt_max);
        } else {
            int padding_aux = 0;
            if (this.f68s.estilo == 1) {
                padding_aux = config.dp_to_px(this, 10);
            }
            col_width_aux = (Math.max(this.w_max, this.w_txt_max) + spacing_aux) + padding_aux;
            banner(this.w_txt_max);
        }
        while (ncols_aux > 1 && (col_width_aux * ncols_aux) + (padding_grid * 2) > width_disponible) {
            ncols_aux--;
        }
        this.gridView.setNumColumns(ncols_aux);
        this.gridView.getLayoutParams().width = ((col_width_aux * ncols_aux) + (padding_grid * 2)) - spacing_aux;
        this.gridView.setOnItemClickListener(this);
        if (!this.f68s.anim) {
            this.gridView.setLayoutAnimation(null);
        }
        this.MyGridAdapter = new GridAdapter(this);
        this.gridView.setAdapter(this.MyGridAdapter);
        if (this.f68s.idfondo <= 0) {
            return;
        }
        if (this.f68s.fondo_modif) {
            new cargarfoto().execute(new String[]{this.f68s.idfondo + "", this.ind + "", this.f68s.vfondo + ""});
            return;
        }
        try {
            this.globales.file_to_iv("fondo_" + this.f68s.idfondo, (ImageView) findViewById(C0627R.id.iv_fondo));
        } catch (Exception e) {
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int ind_aux = 0;
        for (int i = 0; i < this.globales.secciones_a.length; i++) {
            if (this.seccs_a[position].id == this.globales.secciones_a[i].id) {
                ind_aux = i;
                break;
            }
        }
        view.setId(ind_aux);
        view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(ind_aux));
        view.setTag(C0627R.id.idaux1, Integer.valueOf(1));
        if (this.f68s.descargar && this.seccs_a[position].tipo == 1) {
            this.globales.fdescargar(this.seccs_a[position].url, "", "");
            return;
        }
        if (this.f68s.linksexternos == 1 && this.seccs_a[position].tipo == 1) {
            view.setTag(C0627R.id.idaux2, this.seccs_a[position].url);
        }
        onClick(view);
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
        if (v.getTag(C0627R.id.idaux2) != null) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String) v.getTag(C0627R.id.idaux2))));
            return;
        }
        ResultGetIntent rgi = this.globales.getIntent(v, this);
        if (v.getTag(C0627R.id.idaux1) != null) {
            rgi.finalizar = false;
        }
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
            if (this.finalizar) {
                this.es_root = false;
            }
            startActivityForResult(rgi.f34i, 0);
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
            this.mDrawerList.setOnItemClickListener(new C08311());
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
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
        ViewGroup vgroup = (ViewGroup) findViewById(C0627R.id.ll_nat);
        for (int i = 0; i < vgroup.getChildCount(); i++) {
            if (vgroup.getChildAt(i).findViewById(C0627R.id.ad_pb) != null) {
                vgroup.getChildAt(i).findViewById(C0627R.id.ad_pb).setVisibility(8);
            }
        }
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.resume();
        }
        this.atras_pulsado = false;
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
