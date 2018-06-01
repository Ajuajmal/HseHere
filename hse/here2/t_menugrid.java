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
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class t_menugrid extends Activity implements OnClickListener, OnSharedPreferenceChangeListener, OnItemClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    int H_MAXIMA = 80;
    int H_MAXIMA_ROW = 30;
    GridAdapter MyGridAdapter;
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    cargarfondo cf;
    ProgressDialog dialog_cargando;
    Bundle extras;
    config globales;
    MyGridView gridView;
    int h_max;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    View v_abrir_secc;
    int w_max;
    int w_txt_max;

    public class GridAdapter extends BaseAdapter {
        private Context context;

        public GridAdapter(Context c) {
            this.context = c;
        }

        public int getCount() {
            return t_menugrid.this.globales.menu_a_secciones.length;
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
                if (t_menugrid.this.globales.menu_icos_izq) {
                    layout_aux = C0627R.layout.t_menugrid_row;
                } else if (t_menugrid.this.globales.menu_estilo == 1) {
                    layout_aux = C0627R.layout.t_menugrid_row_formato2;
                } else {
                    layout_aux = C0627R.layout.t_menugrid_row_v;
                }
                row = inflater.inflate(layout_aux, parent, false);
                if (!t_menugrid.this.globales.menu_icos_izq && t_menugrid.this.globales.menu_estilo == 1) {
                    ((CardView) row.findViewById(C0627R.id.cv)).setRadius((float) t_menugrid.this.globales.menu_txt_radius);
                }
                if (t_menugrid.this.globales.menu_mostrar_icos) {
                    icon = (ImageView) row.findViewById(C0627R.id.iv_menugrid);
                    if (t_menugrid.this.globales.menu_icos_izq) {
                        icon.getLayoutParams().height = t_menugrid.this.h_max;
                        icon.getLayoutParams().width = t_menugrid.this.w_max;
                    } else {
                        icon.getLayoutParams().height = t_menugrid.this.h_max;
                        icon.getLayoutParams().width = t_menugrid.this.w_max;
                    }
                    icon.setVisibility(0);
                }
                if (t_menugrid.this.globales.menu_mostrar_txt) {
                    TextView label = (TextView) row.findViewById(C0627R.id.tv_menugrid);
                    if (t_menugrid.this.globales.menu_txt_b) {
                        label.setTypeface(label.getTypeface(), 1);
                    }
                    if (!t_menugrid.this.globales.menu_txt_col.equals("")) {
                        label.setTextColor(Color.parseColor("#" + t_menugrid.this.globales.menu_txt_col));
                    }
                    label.getLayoutParams().width = t_menugrid.this.w_txt_max;
                    if (t_menugrid.this.globales.menu_txt_c) {
                        label.setGravity(17);
                    }
                    if (t_menugrid.this.globales.menu_txt_bg.equals("")) {
                        label.setBackgroundDrawable(null);
                    } else {
                        ((GradientDrawable) label.getBackground()).setColor(Color.parseColor("#" + t_menugrid.this.globales.menu_txt_bg));
                        ((GradientDrawable) label.getBackground()).setCornerRadius((float) t_menugrid.this.globales.menu_txt_radius);
                    }
                    label.setVisibility(0);
                }
            }
            if (t_menugrid.this.globales.menu_mostrar_icos) {
                icon = (ImageView) row.findViewById(C0627R.id.iv_menugrid);
                if (t_menugrid.this.globales.secciones_a[t_menugrid.this.globales.menu_a_secciones[position]].ico_cargando || t_menugrid.this.globales.secciones_a[t_menugrid.this.globales.menu_a_secciones[position]].ico == null) {
                    icon.setImageBitmap(null);
                } else {
                    icon.setImageDrawable(new BitmapDrawable(t_menugrid.this.getResources(), t_menugrid.this.globales.secciones_a[t_menugrid.this.globales.menu_a_secciones[position]].ico));
                }
            }
            if (t_menugrid.this.globales.menu_mostrar_txt) {
                ((TextView) row.findViewById(C0627R.id.tv_menugrid)).setText(t_menugrid.this.globales.secciones_a[t_menugrid.this.globales.menu_a_secciones[position]].titulo);
            }
            return row;
        }
    }

    private class cargarfondo extends AsyncTask<String, Void, String> {
        private cargarfondo() {
        }

        protected String doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/fondos_menu/fm299914.png?v=" + t_menugrid.this.globales.v_fondomenu);
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(60000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        bmImg.compress(CompressFormat.PNG, 100, new FileOutputStream(t_menugrid.this.globales.getTempFile_libre(t_menugrid.this, "fondomenu.png")));
                        url = myFileUrl;
                        return "1";
                    } catch (Exception e) {
                        url = myFileUrl;
                        return "0";
                    }
                } catch (IOException e2) {
                    url = myFileUrl;
                    return "0";
                }
            } catch (MalformedURLException e3) {
                return "0";
            }
        }

        protected void onPostExecute(String result) {
            if (result.equals("1")) {
                t_menugrid.this.globales.act_fondomenu = false;
                Editor e = t_menugrid.this.getSharedPreferences("sh", 0).edit();
                e.putBoolean("act_fm", false);
                e.commit();
                if (!t_menugrid.this.globales.equals(null)) {
                    ((ImageView) t_menugrid.this.findViewById(C0627R.id.iv_fondo)).setImageBitmap(BitmapFactory.decodeFile(t_menugrid.this.globales.getTempFile_libre(t_menugrid.this, "fondomenu.png").getAbsolutePath()));
                }
            }
        }
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        int layout_aux;
        int col_width_aux;
        this.globales = (config) getApplicationContext();
        this.extras = getIntent().getExtras();
        this.cbtn = config.aplicar_color_dialog(this.globales.menu_c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_menugrid);
        this.globales.ind_secc_sel = 900;
        SharedPreferences settings = getSharedPreferences("sh", 0);
        settings.registerOnSharedPreferenceChangeListener(this);
        Editor e = settings.edit();
        e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
        e.commit();
        incluir_menu_pre();
        if (!this.globales.menu_c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.menu_c1), Color.parseColor("#" + this.globales.menu_c2)}));
        }
        float scale = getResources().getDisplayMetrics().density;
        this.H_MAXIMA = (int) ((((float) this.H_MAXIMA) * scale) + 0.5f);
        this.H_MAXIMA_ROW = (int) ((((float) this.H_MAXIMA_ROW) * scale) + 0.5f);
        this.h_max = 0;
        this.w_max = 0;
        this.w_txt_max = 0;
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        if (this.globales.menu_icos_izq) {
            layout_aux = C0627R.layout.t_menugrid_row;
        } else if (this.globales.menu_estilo == 1) {
            layout_aux = C0627R.layout.t_menugrid_row_formato2;
        } else {
            layout_aux = C0627R.layout.t_menugrid_row_v;
        }
        TextView tv_aux = (TextView) inflater.inflate(layout_aux, (LinearLayout) findViewById(C0627R.id.ll_princ), false).findViewById(C0627R.id.tv_menugrid);
        int i = 0;
        while (i < this.globales.secciones_a.length) {
            if (!this.globales.secciones_a[i].oculta) {
                if (this.globales.menu_mostrar_txt) {
                    tv_aux.setText(this.globales.secciones_a[i].titulo);
                    if (this.globales.menu_txt_b) {
                        tv_aux.setTypeface(tv_aux.getTypeface(), 1);
                    }
                    tv_aux.measure(0, 0);
                    this.w_txt_max = Math.max(this.w_txt_max, tv_aux.getMeasuredWidth());
                }
                if (this.globales.menu_mostrar_icos) {
                    if (!this.globales.secciones_a[i].ico_cargando && this.globales.secciones_a[i].ico != null) {
                        this.h_max = Math.max(this.h_max, this.globales.secciones_a[i].ico.getHeight());
                        this.w_max = Math.max(this.w_max, this.globales.secciones_a[i].ico.getWidth());
                    } else if (!(!this.globales.secciones_a[i].ico_cargando || this.globales.secciones_a[i].w_ico == 0 || this.globales.secciones_a[i].h_ico == 0)) {
                        this.h_max = Math.max(this.h_max, this.globales.secciones_a[i].h_ico);
                        this.w_max = Math.max(this.w_max, this.globales.secciones_a[i].w_ico);
                    }
                }
            }
            i++;
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
        if (this.globales.menu_icos_izq && this.h_max > this.H_MAXIMA_ROW) {
            this.w_max = (this.w_max * this.H_MAXIMA_ROW) / this.h_max;
            this.h_max = this.H_MAXIMA_ROW;
        } else if (!this.globales.menu_icos_izq && this.h_max > this.H_MAXIMA) {
            this.w_max = (this.w_max * this.H_MAXIMA) / this.h_max;
            this.h_max = this.H_MAXIMA;
        }
        if (!this.globales.menu_icos_izq && this.globales.menu_mostrar_txt && this.w_max > this.w_txt_max) {
            this.h_max = (this.h_max * this.w_txt_max) / this.w_max;
            this.w_max = this.w_txt_max;
        }
        this.gridView = (MyGridView) findViewById(C0627R.id.grid);
        if (this.globales.menu_c) {
            ((LinearLayout) findViewById(C0627R.id.ll_grid)).setGravity(1);
        }
        int spacing_aux = 1;
        if (this.globales.menu_padding == 1) {
            spacing_aux = 16;
        } else if (this.globales.menu_padding == 2) {
            spacing_aux = 24;
        }
        spacing_aux = (int) ((((float) spacing_aux) * scale) + 0.5f);
        this.gridView.setVerticalSpacing(spacing_aux);
        this.gridView.setHorizontalSpacing(spacing_aux);
        int padding_grid = (int) ((10.0f * scale) + 0.5f);
        this.gridView.setPadding(padding_grid, padding_grid, padding_grid, padding_grid);
        int ncols_aux = this.globales.menu_ncols;
        if (this.globales.menu_icos_izq) {
            col_width_aux = (this.w_max + this.w_txt_max) + spacing_aux;
            int leftmargin_tv = (int) ((4.0f * scale) + 0.5f);
            if (this.globales.menu_mostrar_txt) {
                col_width_aux += leftmargin_tv;
            }
            banner(this.w_max + this.w_txt_max);
        } else {
            int padding_aux = 0;
            if (this.globales.menu_estilo == 1) {
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
        if (!(this.globales.menu_anim && savedInstanceState == null)) {
            this.gridView.setLayoutAnimation(null);
        }
        this.MyGridAdapter = new GridAdapter(this);
        this.gridView.setAdapter(this.MyGridAdapter);
        if (this.globales.t_fondomenu) {
            if (!this.globales.act_fondomenu && this.globales.getTempFile_libre(this, "fondomenu.png").exists()) {
                ((ImageView) findViewById(C0627R.id.iv_fondo)).setImageBitmap(BitmapFactory.decodeFile(this.globales.getTempFile_libre(this, "fondomenu.png").getAbsolutePath()));
            } else if (this.cf == null || this.cf.getStatus() != Status.RUNNING) {
                this.cf = new cargarfondo();
                this.cf.execute(new String[0]);
            }
        }
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey("intent_abrir")) {
                startActivityForResult((Intent) extras.get("intent_abrir"), 0);
            } else if (savedInstanceState == null) {
                config hse_here2_config = this.globales;
                boolean z = extras != null && extras.containsKey("ad_entrar");
                hse_here2_config.toca_int(this, z);
            }
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
            if (!this.globales.menu_c1.equals("") && config.esClaro("#" + this.globales.menu_c1)) {
                ((TextView) findViewById(C0627R.id.tv_appsreco)).setTextColor(config.NEGRO);
                ((ImageView) findViewById(C0627R.id.appnext_logo)).setImageDrawable(getResources().getDrawable(C0627R.drawable.info));
            }
            for (int i = 0; i < this.globales.appnext_ads.size(); i++) {
                this.globales.appnext_mostrar_2(this, 2, -1, i, col_width_aux, this.globales.menu_txt_col, this.globales.menu_txt_bg, this.globales.menu_c, this.globales.menu_txt_c, this.globales.menu_txt_radius, this.globales.menu_txt_b);
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

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        view.setId(this.globales.menu_a_secciones[position]);
        view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.globales.menu_a_secciones[position]));
        onClick(view);
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
        if (rgi.finalizar_app) {
            finish();
        } else {
            startActivityForResult(rgi.f34i, 0);
        }
    }

    void incluir_menu_pre() {
        this.globales.incluir_menu(this);
        for (int i = 0; i < this.globales.icos_a.length; i++) {
            if (this.globales.icos_a[i] > 0) {
                findViewById(this.globales.icos_a[i]).setOnClickListener(this);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar_app") && data.getExtras().getBoolean("finalizar_app")) {
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
        if (isFinishing()) {
            config.finalizar_app(this);
        }
        super.onDestroy();
    }

    public void onBackPressed() {
        if (this.atras_pulsado || !this.globales.pedir_confirm_exit) {
            super.onBackPressed();
            return;
        }
        this.atras_pulsado = true;
        config.confirmar_exit(this);
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
