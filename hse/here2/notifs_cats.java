package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;
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
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class notifs_cats extends Activity implements OnClickListener, OnCheckedChangeListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private AdView adView;
    private boolean atras_pulsado = false;
    private boolean c1_esclaro;
    private String cbtn;
    private String cod_g;
    ProgressDialog dialog_cargando;
    private boolean es_root;
    private Bundle extras;
    private boolean finalizar = false;
    private config globales;
    private int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private SharedPreferences settings;
    View v_abrir_secc;

    class C06851 implements OnItemClickListener {
        C06851() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (notifs_cats.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(notifs_cats.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(notifs_cats.this.globales.menu_a_secciones[position]));
            notifs_cats.this.onClick(view);
        }
    }

    private class aplic_modo extends AsyncTask<String, Void, Byte> {
        String idcat;
        String modo;

        aplic_modo(String idcat, Boolean modo) {
            this.modo = modo.booleanValue() ? "1" : "0";
            this.idcat = idcat;
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/usu_catnotif.php?v=2&idusu=" + notifs_cats.this.idusu + "&c=" + notifs_cats.this.cod_g + "&idcat=" + this.idcat + "&modo=" + this.modo).openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                conn.setReadTimeout(StatusCodes.AUTH_DISABLED);
                conn.setRequestProperty("User-Agent", "Android Vinebre Software");
                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb_total = new StringBuilder();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    sb_total.append(line + "\n");
                }
                if (sb_total.indexOf("ANDROID:OK") != -1) {
                    valueOf = Byte.valueOf((byte) 1);
                    if (conn != null) {
                        conn.disconnect();
                    }
                } else {
                    valueOf = Byte.valueOf((byte) 0);
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            } catch (Exception e) {
                valueOf = Byte.valueOf((byte) 2);
                return valueOf;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return valueOf;
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 1) {
                Editor e = notifs_cats.this.settings.edit();
                e.putString("catsnotif_" + this.idcat + "_def", this.modo);
                e.commit();
            }
        }
    }

    private class cargar_cats extends AsyncTask<String, Void, String> {
        private cargar_cats() {
        }

        protected void onPreExecute() {
            if (notifs_cats.this.c1_esclaro) {
                notifs_cats.this.findViewById(C0627R.id.pb_cargarcats_inv).setVisibility(0);
            } else {
                notifs_cats.this.findViewById(C0627R.id.pb_cargarcats).setVisibility(0);
            }
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/cargar_catsnotif.php?v=2&idusu=" + notifs_cats.this.idusu + "&c=" + notifs_cats.this.cod_g).openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                conn.setReadTimeout(StatusCodes.AUTH_DISABLED);
                conn.setRequestProperty("User-Agent", "Android Vinebre Software");
                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb_total = new StringBuilder();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    sb_total.append(line + "\n");
                }
                stringBuilder = sb_total.toString();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                stringBuilder = "";
                return stringBuilder;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return stringBuilder;
        }

        protected void onPostExecute(String result) {
            try {
                notifs_cats.this.findViewById(C0627R.id.pb_cargarcats).setVisibility(8);
                notifs_cats.this.findViewById(C0627R.id.pb_cargarcats_inv).setVisibility(8);
            } catch (Exception e) {
            }
            if (result.contains("ANDROID:OK DATOS:")) {
                int i;
                int pos1 = result.indexOf("DATOS:") + 6;
                String[] cats_a = result.substring(pos1, result.indexOf(";", pos1)).split(",");
                String[] ids_a = notifs_cats.this.settings.getString("catsnotif_ids", "").split(",");
                Editor e2 = notifs_cats.this.settings.edit();
                for (i = 0; i < ids_a.length; i++) {
                    e2.remove("catsnotif_" + ids_a[i] + "_descr");
                    e2.remove("catsnotif_" + ids_a[i] + "_def");
                    e2.remove("catsnotif_" + ids_a[i] + "_sep");
                }
                String ids = "";
                for (i = 0; i < cats_a.length; i++) {
                    if (!cats_a[i].equals("")) {
                        String[] cat_a = cats_a[i].split("-");
                        if (cat_a.length == 2) {
                            notifs_cats.this.globales.catsnotif_v_bd = Integer.parseInt(cat_a[1]);
                            e2.putInt("vcn", Integer.parseInt(cat_a[1]));
                            e2.putInt("catsnotif_v_aplic", Integer.parseInt(cat_a[1]));
                        } else {
                            String id = cat_a[0];
                            if (!ids.equals("")) {
                                ids = ids + ",";
                            }
                            ids = ids + id;
                            e2.putString("catsnotif_" + id + "_descr", cat_a[2].replace("@X@", "-").replace("@Y@", ",").replace("@Z@", ";"));
                            e2.putString("catsnotif_" + id + "_def", cat_a[3]);
                            e2.putString("catsnotif_" + id + "_sep", cat_a[1]);
                        }
                    }
                }
                e2.putString("catsnotif_ids", ids);
                notifs_cats.this.globales.hay_catsnotif = !ids.equals("");
                if (ids.equals("")) {
                    e2.putInt("hcn", 0);
                } else {
                    e2.putInt("hcn", 1);
                }
                e2.commit();
                notifs_cats.this.mostrar_cats();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.cbtn = config.aplicar_color_dialog(this.globales.c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.c1)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.notifs_cats);
        incluir_menu_pre();
        this.globales.toca_int(this, false);
        this.adView = this.globales.mostrar_banner(this, false);
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
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.cod_g = this.settings.getString("cod_g", "");
        this.c1_esclaro = config.esClaro("#" + this.globales.c1);
        if (!this.globales.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1), Color.parseColor("#" + this.globales.c2)}));
        }
        int c_aux = ViewCompat.MEASURED_STATE_MASK;
        if (!this.c1_esclaro) {
            c_aux = -1;
        }
        TextView tv_listacatsnotifs = (TextView) findViewById(C0627R.id.tv_listacatsnotifs);
        tv_listacatsnotifs.setTextColor(c_aux);
        if (!this.globales.catsnotif_tit.equals("")) {
            tv_listacatsnotifs.setText(this.globales.catsnotif_tit);
        }
        findViewById(C0627R.id.v_listacatsnotifs).setBackgroundColor(c_aux);
        if (this.settings.getInt("catsnotif_v_aplic", 0) == this.globales.catsnotif_v_bd) {
            mostrar_cats();
        } else {
            new cargar_cats().execute(new String[0]);
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
            this.mDrawerList.setOnItemClickListener(new C06851());
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

    @TargetApi(14)
    private void mostrar_cats() {
        LinearLayout llnotifs = (LinearLayout) findViewById(C0627R.id.llnotifs);
        llnotifs.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        String[] ids_a = this.settings.getString("catsnotif_ids", "").split(",");
        for (int i = 0; i < ids_a.length; i++) {
            if (!ids_a[i].equals("")) {
                String id = ids_a[i];
                int c = config.NEGRO;
                int c2 = config.GRIS_CLARO;
                if (!this.c1_esclaro) {
                    c = config.BLANCO;
                    c2 = config.GRIS_OSCURO;
                }
                TextView tv;
                if (this.settings.getString("catsnotif_" + id + "_sep", "0").equals("1")) {
                    LinearLayout ll_notif = (LinearLayout) inflater.inflate(C0627R.layout.notif_sep, null);
                    tv = (TextView) ll_notif.findViewById(C0627R.id.tv_sep);
                    tv.setText(this.settings.getString("catsnotif_" + id + "_descr", ""));
                    tv.setTextColor(c);
                    ll_notif.findViewById(C0627R.id.v_sep).setBackgroundColor(c2);
                    llnotifs.addView(ll_notif);
                } else {
                    LinearLayout rl_notif = (LinearLayout) inflater.inflate(C0627R.layout.notif_cat, null);
                    tv = (TextView) rl_notif.findViewById(C0627R.id.tv_cat);
                    tv.setText(this.settings.getString("catsnotif_" + id + "_descr", ""));
                    tv.setTextColor(c);
                    LinearLayout ll_sc = (LinearLayout) rl_notif.findViewById(C0627R.id.ll_sc);
                    if (VERSION.SDK_INT > 13) {
                        Switch sc = new Switch(this);
                        sc.setChecked(this.settings.getString("catsnotif_" + id + "_def", "0").equals("1"));
                        sc.setOnCheckedChangeListener(this);
                        sc.setTag(C0627R.id.idaux1, id);
                        ll_sc.addView(sc);
                    } else {
                        ToggleButton tb = new ToggleButton(this);
                        tb.setChecked(this.settings.getString("catsnotif_" + id + "_def", "0").equals("1"));
                        tb.setOnCheckedChangeListener(this);
                        tb.setTag(C0627R.id.idaux1, id);
                        ll_sc.addView(tb);
                    }
                    rl_notif.findViewById(C0627R.id.v_sep).setBackgroundColor(c2);
                    llnotifs.addView(rl_notif);
                }
            }
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        new aplic_modo((String) buttonView.getTag(C0627R.id.idaux1), Boolean.valueOf(isChecked)).execute(new String[0]);
    }

    public void onClick(View v) {
        if (v.getTag(C0627R.id.idaux1) == null) {
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
    }

    public void abrir_secc(View v) {
        ResultGetIntent rgi = this.globales.getIntent(v, this);
        try {
            if (rgi.f34i != null && rgi.f34i.getComponent().getClassName().endsWith(".notifs")) {
                return;
            }
        } catch (Exception e) {
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
            this.es_root = false;
            startActivity(rgi.f34i);
        }
        if (this.finalizar) {
            finish();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
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
