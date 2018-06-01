package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class t_gal extends Activity implements OnClickListener, OnItemClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    boolean c1_esclaro;
    cargar_fotos c_f;
    cargar_usus c_u;
    boolean cargado_primeravez = false;
    String cbtn;
    String cod_g;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    boolean finalizar_2 = false;
    config globales;
    GridViewAdapter gridAdapter;
    GridView gridView;
    int idusu;
    int ind_secc;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    obtener_img_g o_i_g;
    File path;
    SharedPreferences settings;
    ArrayList<Usu> usus_a;
    boolean usus_a_completo;
    View v_abrir_secc;

    class C07941 implements OnItemClickListener {
        C07941() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_gal.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_gal.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_gal.this.globales.menu_a_secciones[position]));
            t_gal.this.onClick(view);
        }
    }

    public class GridViewAdapter extends ArrayAdapter<Usu> {
        private Context context;
        private ArrayList<Usu> data = new ArrayList();
        private int layoutResourceId;

        class ViewHolder {
            ImageView image;
            ProgressBar pb_foto;
            ProgressBar pb_foto_inv;

            ViewHolder() {
            }
        }

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Usu> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            if (!t_gal.this.usus_a_completo && t_gal.this.usus_a.size() - position < 10 && (t_gal.this.c_u == null || t_gal.this.c_u.getStatus() != Status.RUNNING)) {
                t_gal.this.c_u = new cargar_usus(t_gal.this.usus_a.size());
                t_gal.this.c_u.execute(new String[0]);
            }
            if (t_gal.this.cargado_primeravez && (t_gal.this.c_f == null || t_gal.this.c_f.getStatus() != Status.RUNNING)) {
                t_gal.this.c_f = new cargar_fotos();
                t_gal.this.c_f.execute(new String[0]);
            }
            if (row == null) {
                row = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) row.findViewById(C0627R.id.iv_usugrid);
                holder.pb_foto = (ProgressBar) row.findViewById(C0627R.id.pb_foto);
                holder.pb_foto_inv = (ProgressBar) row.findViewById(C0627R.id.pb_foto_inv);
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(holder.pb_foto, t_gal.this.globales.c_icos);
                    config.progress_color(holder.pb_foto_inv, t_gal.this.globales.c_icos);
                }
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            Usu item = (Usu) this.data.get(position);
            if (item.id.equals("-1")) {
                holder.image.setImageBitmap(null);
                holder.pb_foto.setVisibility(8);
                holder.pb_foto_inv.setVisibility(8);
                if (t_gal.this.c1_esclaro) {
                    holder.pb_foto_inv.setVisibility(0);
                } else {
                    holder.pb_foto.setVisibility(0);
                }
            } else {
                holder.pb_foto.setVisibility(8);
                holder.pb_foto_inv.setVisibility(8);
                boolean pendiente = false;
                if (item.cargando || item.foto_pendiente) {
                    pendiente = true;
                }
                if (pendiente) {
                    holder.image.setImageBitmap(null);
                    if (t_gal.this.c1_esclaro) {
                        holder.pb_foto_inv.setVisibility(0);
                    } else {
                        holder.pb_foto.setVisibility(0);
                    }
                } else {
                    Bitmap bm_aux;
                    try {
                        File file = new File(t_gal.this.path, "gal_" + item.id + ".jpg");
                        if (file.exists()) {
                            bm_aux = Media.getBitmap(t_gal.this.getContentResolver(), Uri.fromFile(file));
                        } else {
                            bm_aux = null;
                        }
                    } catch (Exception e) {
                        bm_aux = null;
                    }
                    holder.image.setImageBitmap(bm_aux);
                }
                if (item.cargando_grande) {
                    if (t_gal.this.c1_esclaro) {
                        holder.pb_foto_inv.setVisibility(0);
                    } else {
                        holder.pb_foto.setVisibility(0);
                    }
                }
            }
            return row;
        }
    }

    private class Usu {
        boolean cargando;
        boolean cargando_grande;
        String descr;
        boolean foto_pendiente;
        String id;

        private Usu() {
            this.foto_pendiente = true;
            this.cargando = false;
            this.cargando_grande = false;
        }
    }

    private class cargar_fotos extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        int i_cargando;
        String idusu_acargar;

        private cargar_fotos() {
            this.i_cargando = -1;
        }

        protected void onPreExecute() {
            int i = t_gal.this.gridView.getFirstVisiblePosition();
            while (i <= Math.max(t_gal.this.gridView.getLastVisiblePosition(), 30) && t_gal.this.usus_a != null && i < t_gal.this.usus_a.size()) {
                if (!((Usu) t_gal.this.usus_a.get(i)).foto_pendiente || ((Usu) t_gal.this.usus_a.get(i)).id.equals("-1")) {
                    i++;
                } else {
                    this.idusu_acargar = ((Usu) t_gal.this.usus_a.get(i)).id;
                    ((Usu) t_gal.this.usus_a.get(i)).cargando = true;
                    ((Usu) t_gal.this.usus_a.get(i)).foto_pendiente = false;
                    this.i_cargando = i;
                    return;
                }
            }
        }

        protected String doInBackground(String... urls) {
            if (this.idusu_acargar == null) {
                return "-1";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/gal/" + this.idusu_acargar + "_p.png").openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(t_gal.this.path, "gal_" + this.idusu_acargar + ".jpg")));
                        return "1";
                    } catch (Exception e) {
                        return "0";
                    }
                } catch (IOException e2) {
                    return "0";
                }
            } catch (MalformedURLException e3) {
                return "0";
            }
        }

        protected void onPostExecute(String result) {
            if (!(this.i_cargando == -1 || t_gal.this.usus_a == null)) {
                try {
                    ((Usu) t_gal.this.usus_a.get(this.i_cargando)).cargando = false;
                } catch (Exception e) {
                    return;
                }
            }
            if (this.idusu_acargar != null) {
                t_gal.this.gridAdapter.notifyDataSetChanged();
                new cargar_fotos().execute(new String[0]);
            }
        }
    }

    private class cargar_usus extends AsyncTask<String, Void, String> {
        int ind_ini;

        cargar_usus(int ind_ini) {
            this.ind_ini = ind_ini;
        }

        protected void onPreExecute() {
            if (t_gal.this.usus_a.isEmpty() || !((Usu) t_gal.this.usus_a.get(t_gal.this.usus_a.size() - 1)).id.equals("-1")) {
                Usu u = new Usu();
                u.id = "-1";
                u.descr = "";
                t_gal.this.usus_a.add(u);
                t_gal.this.gridAdapter.notifyDataSetChanged();
            }
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/obtener_gal.php?idusu=" + t_gal.this.idusu + "&ind_ini=" + this.ind_ini + "&idsec=" + t_gal.this.globales.secciones_a[t_gal.this.ind_secc].id).openConnection();
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
            if (t_gal.this.usus_a != null && ((Usu) t_gal.this.usus_a.get(t_gal.this.usus_a.size() - 1)).id.equals("-1")) {
                t_gal.this.usus_a.remove(t_gal.this.usus_a.size() - 1);
                t_gal.this.gridAdapter.notifyDataSetChanged();
            }
            if (result.contains("ANDROID:OK DATOS:")) {
                String[] usus_priv_a = result.substring(result.indexOf("DATOS:") + 6).split(";");
                int i = 0;
                while (!usus_priv_a[i].equals("S") && !usus_priv_a[i].equals("N")) {
                    String[] usu_a = usus_priv_a[i].split(",");
                    Usu u = new Usu();
                    u.id = usu_a[0];
                    if (usu_a.length <= 1 || usu_a[1].equals(" ")) {
                        u.descr = "";
                    } else {
                        u.descr = usu_a[1].replace("@X@", ",").replace("@Y@", ";");
                    }
                    if (new File(t_gal.this.path, "gal_" + usu_a[0] + ".jpg").exists()) {
                        u.cargando = false;
                        u.foto_pendiente = false;
                    }
                    t_gal.this.usus_a.add(u);
                    i++;
                }
                if (usus_priv_a[i].equals("N")) {
                    t_gal.this.usus_a_completo = true;
                }
                t_gal.this.gridAdapter.notifyDataSetChanged();
                if (i <= 0) {
                    return;
                }
                if (t_gal.this.c_f == null || t_gal.this.c_f.getStatus() != Status.RUNNING) {
                    t_gal.this.c_f = new cargar_fotos();
                    t_gal.this.c_f.execute(new String[0]);
                    t_gal.this.cargado_primeravez = true;
                }
            }
        }
    }

    private class obtener_img_g extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String descr;
        String idimg_acargar;
        int ind;

        public obtener_img_g(String id, int ind, String descr) {
            this.idimg_acargar = id;
            this.descr = descr;
            this.ind = ind;
        }

        protected void onPreExecute() {
            ((Usu) t_gal.this.usus_a.get(this.ind)).cargando_grande = true;
            t_gal.this.gridAdapter.notifyDataSetChanged();
        }

        protected String doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gal/" + this.idimg_acargar + ".png");
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(60000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(t_gal.this.globales.getTempFile_libre(t_gal.this, "gal_" + this.idimg_acargar + "_g.jpg")));
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
            ((Usu) t_gal.this.usus_a.get(this.ind)).cargando_grande = false;
            t_gal.this.gridAdapter.notifyDataSetChanged();
            Intent i = new Intent(t_gal.this, t_gal_foto.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + t_gal.this.globales.getTempFile_libre(t_gal.this, "gal_" + this.idimg_acargar + "_g.jpg").getAbsolutePath());
            i.putExtra("descr", this.descr);
            t_gal.this.startActivityForResult(i, 0);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        String c1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
        String c2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
        this.c1_esclaro = config.esClaro("#" + c1);
        this.cbtn = config.aplicar_color_dialog(c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !this.c1_esclaro) {
            setTheme(C0627R.style.holonolight);
        }
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        this.ind_secc = this.globales.ind_secc_sel_2;
        this.path = new File(Environment.getExternalStorageDirectory(), getPackageName());
        super.onCreate(savedInstanceState);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.cod_g = this.settings.getString("cod_g", "");
        setContentView(C0627R.layout.t_gal);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        if (!c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + c1), Color.parseColor("#" + c2)}));
        }
        this.usus_a = new ArrayList();
        this.usus_a_completo = false;
        this.gridView = (GridView) findViewById(C0627R.id.grid);
        this.gridAdapter = new GridViewAdapter(this, C0627R.layout.t_gal_row, this.usus_a);
        this.gridView.setAdapter(this.gridAdapter);
        this.gridView.setOnItemClickListener(this);
        this.cargado_primeravez = false;
        this.c_u = new cargar_usus(0);
        this.c_u.execute(new String[0]);
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
            this.mDrawerList.setOnItemClickListener(new C07941());
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

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Usu u = (Usu) this.usus_a.get(position);
        if (!u.id.equals("-1")) {
            if (this.o_i_g == null || this.o_i_g.getStatus() != Status.RUNNING) {
                File f = new File(this.path, "gal_" + u.id + "_g.jpg");
                if (f.exists()) {
                    Intent i = new Intent(this, t_gal_foto.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + f.getAbsolutePath());
                    i.putExtra("idf", u.id);
                    i.putExtra("descr", u.descr);
                    startActivityForResult(i, 0);
                    return;
                }
                this.o_i_g = new obtener_img_g(u.id, position, u.descr);
                this.o_i_g.execute(new String[0]);
            }
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
        if (this.gridAdapter != null) {
            this.gridAdapter.notifyDataSetChanged();
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
