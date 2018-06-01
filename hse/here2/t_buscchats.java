package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class t_buscchats extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    static final int GV_H_DP = 40;
    static final int GV_MARGIN_DP = 10;
    static final int GV_W_DP = 149;
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    ProgressDialog dialog_cargando;
    int dp30;
    int dp5;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    GridView gridview;
    int gv_margin_px;
    int gv_w_px;
    int id_arr_cats;
    int id_arr_idcats;
    int idcat;
    int idfoto_acargar = 1;
    int idusu;
    int ind;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    boolean mostrar_cards;
    SharedPreferences settings;
    View v_abrir_secc;

    class C07401 implements OnClickListener {
        C07401() {
        }

        public void onClick(View v) {
            t_buscchats.this.finish();
        }
    }

    class C07412 implements OnClickListener {
        C07412() {
        }

        public void onClick(View v) {
            t_buscchats.this.finish();
        }
    }

    class C07423 implements OnItemClickListener {
        C07423() {
        }

        public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
            Intent i;
            if (t_buscchats.this.idcat == 0) {
                i = new Intent(t_buscchats.this, t_buscchats.class);
                i.putExtra("idcat", Integer.parseInt(t_buscchats.this.getResources().getStringArray(C0627R.array.idcats_menu_a)[position]));
                i.putExtra("tit_cab", t_buscchats.this.getResources().getStringArray(C0627R.array.cats_menu_a)[position]);
            } else {
                i = new Intent(t_buscchats.this, t_buscchats_lista.class);
                i.putExtra("idcat", t_buscchats.this.idcat);
                i.putExtra("idsubcat", Integer.parseInt(t_buscchats.this.getResources().getStringArray(t_buscchats.this.id_arr_idcats)[position]));
                i.putExtra("tit_cab", t_buscchats.this.getResources().getStringArray(t_buscchats.this.id_arr_cats)[position]);
            }
            i.putExtra("ind", t_buscchats.this.ind);
            t_buscchats.this.startActivityForResult(i, 0);
        }
    }

    class C07434 implements OnItemClickListener {
        C07434() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_buscchats.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_buscchats.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_buscchats.this.globales.menu_a_secciones[position]));
            t_buscchats.this.onClick(view);
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private int[] colores_a = new int[]{C0627R.drawable.bg_1, C0627R.drawable.bg_2, C0627R.drawable.bg_3, C0627R.drawable.bg_4, C0627R.drawable.bg_5, C0627R.drawable.bg_6, C0627R.drawable.bg_7, C0627R.drawable.bg_8, C0627R.drawable.bg_9, C0627R.drawable.bg_10, C0627R.drawable.bg_11, C0627R.drawable.bg_12, C0627R.drawable.bg_13, C0627R.drawable.bg_14, C0627R.drawable.bg_1, C0627R.drawable.bg_2, C0627R.drawable.bg_3, C0627R.drawable.bg_4, C0627R.drawable.bg_5, C0627R.drawable.bg_6, C0627R.drawable.bg_7, C0627R.drawable.bg_8, C0627R.drawable.bg_9, C0627R.drawable.bg_10, C0627R.drawable.bg_11, C0627R.drawable.bg_12, C0627R.drawable.bg_13, C0627R.drawable.bg_14, C0627R.drawable.bg_1, C0627R.drawable.bg_2, C0627R.drawable.bg_3, C0627R.drawable.bg_4};
        private Context mContext;

        public ImageAdapter(Context c) {
            this.mContext = c;
        }

        public int getCount() {
            if (t_buscchats.this.idcat == 0) {
                return t_buscchats.this.getResources().getStringArray(C0627R.array.cats_menu_a).length;
            }
            return t_buscchats.this.getResources().getStringArray(t_buscchats.this.id_arr_cats).length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
            if (convertView == null) {
                tv = new TextView(this.mContext);
                tv.setSingleLine(true);
                tv.setHeight(t_buscchats.this.dp30);
                tv.setBackgroundResource(this.colores_a[position]);
                tv.setTextColor(-1);
                tv.setPadding(t_buscchats.this.dp5, 0, t_buscchats.this.dp5, 0);
                tv.setGravity(16);
            } else {
                tv = (TextView) convertView;
            }
            if (t_buscchats.this.idcat == 0) {
                tv.setText(t_buscchats.this.getResources().getStringArray(C0627R.array.cats_menu_a)[position].toUpperCase(Locale.getDefault()));
            } else {
                tv.setText(t_buscchats.this.getResources().getStringArray(t_buscchats.this.id_arr_cats)[position].toUpperCase(Locale.getDefault()));
            }
            return tv;
        }
    }

    private class cargar_chats extends AsyncTask<String, Void, String> {

        class C07461 implements OnClickListener {

            class C07441 implements DialogInterface.OnClickListener {
                C07441() {
                }

                public void onClick(DialogInterface dialog, int which) {
                }
            }

            C07461() {
            }

            public void onClick(View v) {
                final AlertDialog d_aux = new Builder(t_buscchats.this).setCancelable(true).setPositiveButton(t_buscchats.this.getString(C0627R.string.aceptar), new C07441()).setMessage(C0627R.string.cat_sinchats).create();
                if (!t_buscchats.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_buscchats.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e) {
                }
            }
        }

        private cargar_chats() {
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            int i = 1;
            HttpURLConnection conn = null;
            try {
                StringBuilder append = new StringBuilder().append("http://srv1.androidcreator.com/srv/chats_home.php?idusu=").append(t_buscchats.this.idusu).append("&idcat=").append(t_buscchats.this.idcat).append("&chats=");
                if (!t_buscchats.this.mostrar_cards) {
                    i = 0;
                }
                conn = (HttpURLConnection) new URL(append.append(i).toString()).openConnection();
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
                stringBuilder = "ANDROID:KO";
                return stringBuilder;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return stringBuilder;
        }

        protected void onPostExecute(String result) {
            if (result.indexOf("ANDROID:OK RESULT:") == -1) {
                if (result.indexOf("ANDROID:KO") == -1) {
                    return;
                }
            }
            int pos1 = result.indexOf("ANDROID:OK RESULT:");
            if (pos1 != -1) {
                TextView tv_tit = null;
                TextView tv_nusus = null;
                Editor editor = t_buscchats.this.settings.edit();
                pos1 = result.indexOf("@y@", pos1);
                while (pos1 != -1) {
                    pos1 += 3;
                    int pos2 = result.indexOf(";", pos1);
                    int idespacio = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int idapp = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int id = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int idtema = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int fotos_perfil = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int privados = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = result.indexOf(";", pos2 + 1) + 1;
                    pos2 = result.indexOf(";", pos1);
                    int fotos_chat = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    String titulo = result.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    String c1 = result.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    String c2 = result.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int nusus = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int coments = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int fnac = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int sexo = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int descr = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int galeria = Integer.parseInt(result.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = result.indexOf(";", pos1);
                    int dist = Integer.parseInt(result.substring(pos1, pos2));
                    if (idespacio == 1) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_1);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_1);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_1, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    } else if (idespacio == 2) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_2);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_2);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_2, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    } else if (idespacio == 3) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_3);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_3);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_3, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    } else if (idespacio == 4) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_4);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_4);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_4, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    } else if (idespacio == 5) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_5);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_5);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_5, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    } else if (idespacio == 6) {
                        tv_tit = (TextView) t_buscchats.this.findViewById(C0627R.id.tv_chat_6);
                        tv_nusus = (TextView) t_buscchats.this.findViewById(C0627R.id.pie_chat_6);
                        t_buscchats.this.asignar_cardview(C0627R.id.card_view_6, id, idtema, idapp, fotos_perfil, fnac, sexo, descr, dist, privados, coments, galeria, fotos_chat, titulo, c1, c2, nusus);
                    }
                    if (tv_tit != null) {
                        tv_tit.setTextColor(config.NEGRO);
                        tv_nusus.setTextColor(config.NEGRO_2);
                        nusus = Math.min(nusus, 99999);
                        tv_tit.setText(titulo);
                        if (nusus < 10) {
                            tv_nusus.setText("<10 " + t_buscchats.this.getResources().getString(C0627R.string.usuarios));
                        } else {
                            tv_nusus.setText(NumberFormat.getInstance().format((long) nusus) + " " + t_buscchats.this.getResources().getString(C0627R.string.usuarios));
                        }
                    }
                    pos1 = result.indexOf("@y@", pos2);
                }
                editor.commit();
                t_buscchats.this.ocultar_chats();
                int pos = result.indexOf("@z@");
                if (pos != -1) {
                    String[] ids_a;
                    String cats_activ = result.substring(pos + 2);
                    if (t_buscchats.this.idcat == 0) {
                        ids_a = t_buscchats.this.getResources().getStringArray(C0627R.array.idcats_menu_a);
                    } else {
                        ids_a = t_buscchats.this.getResources().getStringArray(t_buscchats.this.id_arr_idcats);
                    }
                    for (int i = 0; i < ids_a.length; i++) {
                        if (cats_activ.indexOf("@" + ids_a[i] + "@") == -1) {
                            t_buscchats.this.gridview.getChildAt(i).setBackgroundColor(-3355444);
                            ((TextView) t_buscchats.this.gridview.getChildAt(i)).setTextColor(-12303292);
                            t_buscchats.this.gridview.getChildAt(i).setOnClickListener(new C07461());
                        }
                    }
                }
                new cargar_foto().execute(new String[0]);
            }
        }
    }

    private class cargar_foto extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        int idapp;
        ImageView iv;

        private cargar_foto() {
        }

        protected void onPreExecute() {
            View v = null;
            do {
                if (t_buscchats.this.idfoto_acargar == 1) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_1);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_1);
                } else if (t_buscchats.this.idfoto_acargar == 2) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_2);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_2);
                } else if (t_buscchats.this.idfoto_acargar == 3) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_3);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_3);
                } else if (t_buscchats.this.idfoto_acargar == 4) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_4);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_4);
                } else if (t_buscchats.this.idfoto_acargar == 5) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_5);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_5);
                } else if (t_buscchats.this.idfoto_acargar == 6) {
                    v = t_buscchats.this.findViewById(C0627R.id.card_view_6);
                    this.iv = (ImageView) t_buscchats.this.findViewById(C0627R.id.iv_chat_6);
                }
                t_buscchats hse_here2_t_buscchats = t_buscchats.this;
                hse_here2_t_buscchats.idfoto_acargar++;
                if (v.getTag() != null) {
                    break;
                }
            } while (t_buscchats.this.idfoto_acargar < 7);
            if (v.getTag() == null) {
                cancel(true);
                return;
            }
            hse_here2_t_buscchats = t_buscchats.this;
            hse_here2_t_buscchats.idfoto_acargar--;
            this.idapp = Integer.parseInt(v.getTag(C0627R.id.tag_idapp) + "");
        }

        protected String doInBackground(String... urls) {
            Exception e;
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gen/" + this.idapp + "_ico.png");
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Options options = new Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(is, null, options);
                    is.close();
                    conn.disconnect();
                    int width_max = Math.round(TypedValue.applyDimension(1, 200.0f, t_buscchats.this.getResources().getDisplayMetrics()));
                    options.inSampleSize = config.calculateInSampleSize(options, width_max, width_max);
                    HttpURLConnection conn2 = (HttpURLConnection) myFileUrl.openConnection();
                    conn2.setDoInput(true);
                    conn2.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn2.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn2.connect();
                    InputStream is2 = conn2.getInputStream();
                    options.inJustDecodeBounds = false;
                    this.bmImg = BitmapFactory.decodeStream(is2, null, options);
                    is2.close();
                    return "ANDROID:OK";
                } catch (Exception e2) {
                    e = e2;
                    URL url = myFileUrl;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
            if (result.indexOf("ANDROID:OK") != -1) {
                this.iv.setImageBitmap(this.bmImg);
            }
            if (t_buscchats.this.idfoto_acargar < 6) {
                t_buscchats hse_here2_t_buscchats = t_buscchats.this;
                hse_here2_t_buscchats.idfoto_acargar++;
                new cargar_foto().execute(new String[0]);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
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
        this.ind = this.extras.getInt("ind");
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.ind].c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.secciones_a[this.ind].c1)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_buscchats);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        int c_texto = config.NEGRO;
        if (!this.globales.secciones_a[this.ind].c1.equals("")) {
            if (config.esClaro("#" + this.globales.secciones_a[this.ind].c1)) {
                findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_OSCURO);
                ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(-1);
            } else {
                c_texto = -1;
                findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_CLARO);
                ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(config.NEGRO);
            }
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.secciones_a[this.ind].c1), Color.parseColor("#" + this.globales.secciones_a[this.ind].c2)}));
        }
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.idcat = this.extras.getInt("idcat", 0);
        ((TextView) findViewById(C0627R.id.tv_cats)).setTextColor(c_texto);
        ((TextView) findViewById(C0627R.id.tv_nuevos)).setTextColor(c_texto);
        ((TextView) findViewById(C0627R.id.tv_populares)).setTextColor(c_texto);
        ((TextView) findViewById(C0627R.id.tv_fav)).setBackgroundColor(Color.parseColor("#" + this.globales.c_icos));
        ((TextView) findViewById(C0627R.id.tv_mas_nuevos)).setBackgroundColor(Color.parseColor("#" + this.globales.c_icos));
        ((TextView) findViewById(C0627R.id.tv_mas_pop)).setBackgroundColor(Color.parseColor("#" + this.globales.c_icos));
        int c_aux = -1;
        if (config.esClaro("#" + this.globales.c_icos)) {
            c_aux = config.NEGRO;
        }
        ((TextView) findViewById(C0627R.id.tv_fav)).setTextColor(c_aux);
        ((TextView) findViewById(C0627R.id.tv_mas_nuevos)).setTextColor(c_aux);
        ((TextView) findViewById(C0627R.id.tv_mas_pop)).setTextColor(c_aux);
        if (this.extras.containsKey("tit_cab")) {
            ((TextView) findViewById(C0627R.id.tv_cabe)).setText(this.extras.getString("tit_cab"));
            findViewById(C0627R.id.tv_cabe).setOnClickListener(new C07401());
            findViewById(C0627R.id.iv_cabe).setOnClickListener(new C07412());
            findViewById(C0627R.id.ll_cabe).setVisibility(0);
        }
        this.mostrar_cards = true;
        if (this.idcat > 0) {
            if (this.idcat == 1) {
                this.id_arr_cats = C0627R.array.cats_1_a;
                this.id_arr_idcats = C0627R.array.idcats_1_a;
            } else if (this.idcat == 2) {
                this.id_arr_cats = C0627R.array.cats_2_a;
                this.id_arr_idcats = C0627R.array.idcats_2_a;
            } else if (this.idcat == 3) {
                this.id_arr_cats = C0627R.array.cats_3_a;
                this.id_arr_idcats = C0627R.array.idcats_3_a;
            } else if (this.idcat == 4) {
                this.id_arr_cats = C0627R.array.cats_4_a;
                this.id_arr_idcats = C0627R.array.idcats_4_a;
            } else if (this.idcat == 5) {
                this.id_arr_cats = C0627R.array.cats_5_a;
                this.id_arr_idcats = C0627R.array.idcats_5_a;
            } else if (this.idcat == 6) {
                this.id_arr_cats = C0627R.array.cats_6_a;
                this.id_arr_idcats = C0627R.array.idcats_6_a;
            } else if (this.idcat == 7) {
                this.id_arr_cats = C0627R.array.cats_7_a;
                this.id_arr_idcats = C0627R.array.idcats_7_a;
            } else if (this.idcat == 9) {
                this.id_arr_cats = C0627R.array.cats_9_a;
                this.id_arr_idcats = C0627R.array.idcats_9_a;
            } else if (this.idcat == 10) {
                this.id_arr_cats = C0627R.array.cats_10_a;
                this.id_arr_idcats = C0627R.array.idcats_10_a;
            } else if (this.idcat == 11) {
                this.id_arr_cats = C0627R.array.cats_11_a;
                this.id_arr_idcats = C0627R.array.idcats_11_a;
            } else if (this.idcat == 12) {
                this.id_arr_cats = C0627R.array.cats_12_a;
                this.id_arr_idcats = C0627R.array.idcats_12_a;
            } else if (this.idcat == 13) {
                this.id_arr_cats = C0627R.array.cats_13_a;
                this.id_arr_idcats = C0627R.array.idcats_13_a;
            } else if (this.idcat == 14) {
                this.id_arr_cats = C0627R.array.cats_14_a;
                this.id_arr_idcats = C0627R.array.idcats_14_a;
            } else if (this.idcat == 15) {
                this.id_arr_cats = C0627R.array.cats_15_a;
                this.id_arr_idcats = C0627R.array.idcats_15_a;
            }
            if (this.globales.secciones_a[this.ind].idcat == 0) {
                this.mostrar_cards = false;
                findViewById(C0627R.id.rl_chats_fila1).setVisibility(8);
                findViewById(C0627R.id.ll_chats_fila1).setVisibility(8);
                findViewById(C0627R.id.rl_chats_fila2).setVisibility(8);
                findViewById(C0627R.id.ll_chats_fila2).setVisibility(8);
            }
        }
        this.gridview = (GridView) findViewById(C0627R.id.gridview);
        this.gridview.setAdapter(new ImageAdapter(this));
        this.gridview.setOnItemClickListener(new C07423());
        this.dp30 = (int) ((BitmapDescriptorFactory.HUE_ORANGE * getResources().getDisplayMetrics().density) + 0.5f);
        this.dp5 = (int) ((5.0f * getResources().getDisplayMetrics().density) + 0.5f);
        this.gv_w_px = convertDpToPixels(149.0f, this);
        this.gv_margin_px = convertDpToPixels(10.0f, this);
        redim_gridview();
        findViewById(C0627R.id.tv_fav).setOnClickListener(this);
        findViewById(C0627R.id.tv_mas_nuevos).setOnClickListener(this);
        findViewById(C0627R.id.tv_mas_pop).setOnClickListener(this);
        new cargar_chats().execute(new String[0]);
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
            this.mDrawerList.setOnItemClickListener(new C07434());
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
        boolean z = true;
        Intent i;
        if (v.getId() == C0627R.id.tv_fav) {
            i = new Intent(this, t_buscchats_lista.class);
            i.putExtra("ind", this.ind);
            i.putExtra("idcat", 0);
            i.putExtra("tipo", 3);
            i.putExtra("tit_cab", getResources().getString(C0627R.string.favoritos).charAt(0) + getResources().getString(C0627R.string.favoritos).substring(1).toLowerCase());
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.tv_mas_nuevos) {
            i = new Intent(this, t_buscchats_lista.class);
            i.putExtra("ind", this.ind);
            i.putExtra("idcat", this.idcat);
            i.putExtra("tipo", 1);
            i.putExtra("tit_cab", getResources().getString(C0627R.string.chats_nuevos));
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.tv_mas_pop) {
            i = new Intent(this, t_buscchats_lista.class);
            i.putExtra("ind", this.ind);
            i.putExtra("idcat", this.idcat);
            i.putExtra("tipo", 2);
            i.putExtra("tit_cab", getResources().getString(C0627R.string.chats_maspopulares));
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.card_view_1 || v.getId() == C0627R.id.card_view_2 || v.getId() == C0627R.id.card_view_3 || v.getId() == C0627R.id.card_view_4 || v.getId() == C0627R.id.card_view_5 || v.getId() == C0627R.id.card_view_6) {
            boolean z2;
            i = new Intent(this, t_chat_contra.class);
            i.putExtra("externo", true);
            i.putExtra("idapp", Integer.parseInt(v.getTag(C0627R.id.tag_idapp) + ""));
            i.putExtra("idchat", Integer.parseInt(v.getTag() + ""));
            i.putExtra("idtema", Integer.parseInt(v.getTag(C0627R.id.tag_idtema) + ""));
            i.putExtra("fotos_perfil", Integer.parseInt(v.getTag(C0627R.id.tag_fotos_perfil) + ""));
            i.putExtra("fnac", Integer.parseInt(v.getTag(C0627R.id.tag_fnac) + ""));
            i.putExtra("sexo", Integer.parseInt(v.getTag(C0627R.id.tag_sexo) + ""));
            i.putExtra("descr", Integer.parseInt(v.getTag(C0627R.id.tag_descr) + ""));
            i.putExtra("dist", Integer.parseInt(v.getTag(C0627R.id.tag_dist) + ""));
            i.putExtra("privados", Integer.parseInt(new StringBuilder().append(v.getTag(C0627R.id.tag_privados)).append("").toString()) == 1);
            String str = "coments";
            if (Integer.parseInt(v.getTag(C0627R.id.tag_coments) + "") == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            i.putExtra(str, z2);
            String str2 = "galeria";
            if (Integer.parseInt(v.getTag(C0627R.id.tag_galeria) + "") != 1) {
                z = false;
            }
            i.putExtra(str2, z);
            i.putExtra("fotos_chat", Integer.parseInt(v.getTag(C0627R.id.tag_fotos_chat) + ""));
            i.putExtra("c1", v.getTag(C0627R.id.tag_c1) + "");
            i.putExtra("c2", v.getTag(C0627R.id.tag_c2) + "");
            i.putExtra("tit_cab", v.getTag(C0627R.id.tag_titulo) + "");
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

    private void asignar_cardview(int idcard, int id, int idtema, int idapp, int fotos_perfil, int fnac, int sexo, int descr, int dist, int privados, int coments, int galeria, int fotos_chat, String titulo, String c1, String c2, int nusus) {
        findViewById(idcard).setTag(Integer.valueOf(id));
        findViewById(idcard).setTag(C0627R.id.tag_idtema, Integer.valueOf(idtema));
        findViewById(idcard).setTag(C0627R.id.tag_idapp, Integer.valueOf(idapp));
        findViewById(idcard).setTag(C0627R.id.tag_fotos_perfil, Integer.valueOf(fotos_perfil));
        findViewById(idcard).setTag(C0627R.id.tag_fnac, Integer.valueOf(fnac));
        findViewById(idcard).setTag(C0627R.id.tag_sexo, Integer.valueOf(sexo));
        findViewById(idcard).setTag(C0627R.id.tag_descr, Integer.valueOf(descr));
        findViewById(idcard).setTag(C0627R.id.tag_dist, Integer.valueOf(dist));
        findViewById(idcard).setTag(C0627R.id.tag_privados, Integer.valueOf(privados));
        findViewById(idcard).setTag(C0627R.id.tag_coments, Integer.valueOf(coments));
        findViewById(idcard).setTag(C0627R.id.tag_galeria, Integer.valueOf(galeria));
        findViewById(idcard).setTag(C0627R.id.tag_fotos_chat, Integer.valueOf(fotos_chat));
        findViewById(idcard).setTag(C0627R.id.tag_titulo, titulo);
        findViewById(idcard).setTag(C0627R.id.tag_c1, c1);
        findViewById(idcard).setTag(C0627R.id.tag_c2, c2);
        findViewById(idcard).setTag(C0627R.id.tag_nusus, Integer.valueOf(nusus));
        findViewById(idcard).setOnClickListener(this);
    }

    void ocultar_chats() {
        if (this.mostrar_cards) {
            int ancho_px = obtener_ancho();
            int dp10px = convertDpToPixels(10.0f, this);
            int nchats = (ancho_px - (dp10px * 2)) / (convertDpToPixels(BitmapDescriptorFactory.HUE_GREEN, this) + dp10px);
            findViewById(C0627R.id.ll_chats_fila1).setVisibility(0);
            findViewById(C0627R.id.rl_chats_fila1).setVisibility(0);
            findViewById(C0627R.id.ll_chats_fila2).setVisibility(0);
            findViewById(C0627R.id.rl_chats_fila2).setVisibility(0);
            findViewById(C0627R.id.card_view_1).setVisibility(0);
            findViewById(C0627R.id.card_view_2).setVisibility(0);
            findViewById(C0627R.id.card_view_3).setVisibility(0);
            findViewById(C0627R.id.card_view_4).setVisibility(0);
            findViewById(C0627R.id.card_view_5).setVisibility(0);
            findViewById(C0627R.id.card_view_6).setVisibility(0);
            if (nchats < 3) {
                findViewById(C0627R.id.card_view_3).setVisibility(8);
                findViewById(C0627R.id.card_view_6).setVisibility(8);
                if (nchats < 2) {
                    findViewById(C0627R.id.card_view_2).setVisibility(8);
                    findViewById(C0627R.id.card_view_5).setVisibility(8);
                }
            }
            if (findViewById(C0627R.id.card_view_3).getTag() == null) {
                findViewById(C0627R.id.card_view_3).setVisibility(8);
                if (findViewById(C0627R.id.card_view_2).getTag() == null) {
                    findViewById(C0627R.id.card_view_2).setVisibility(8);
                    if (findViewById(C0627R.id.card_view_1).getTag() == null) {
                        findViewById(C0627R.id.ll_chats_fila1).setVisibility(8);
                        findViewById(C0627R.id.rl_chats_fila1).setVisibility(8);
                    }
                }
            }
            if (findViewById(C0627R.id.card_view_6).getTag() == null) {
                findViewById(C0627R.id.card_view_6).setVisibility(8);
                if (findViewById(C0627R.id.card_view_5).getTag() == null) {
                    findViewById(C0627R.id.card_view_5).setVisibility(8);
                    if (findViewById(C0627R.id.card_view_4).getTag() == null) {
                        findViewById(C0627R.id.ll_chats_fila2).setVisibility(8);
                        findViewById(C0627R.id.rl_chats_fila2).setVisibility(8);
                    }
                }
            }
        }
    }

    void redim_gridview() {
        int cols = (obtener_ancho() - this.gv_margin_px) / this.gv_w_px;
        int id_aux = C0627R.array.cats_menu_a;
        if (this.idcat > 0) {
            id_aux = this.id_arr_idcats;
        }
        int filas = (int) Math.ceil((double) (((float) getResources().getStringArray(id_aux).length) / ((float) cols)));
        LayoutParams layoutParams = this.gridview.getLayoutParams();
        layoutParams.height = convertDpToPixels((float) ((filas * 40) - 5), this);
        this.gridview.setLayoutParams(layoutParams);
    }

    public int convertDpToPixels(float dp, Context context) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }

    @TargetApi(13)
    public int obtener_ancho() {
        Display display = getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT < 13) {
            return display.getWidth();
        }
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((LinearLayout) findViewById(C0627R.id.ll_princ)).removeViewAt(0);
        incluir_menu_pre();
        redim_gridview();
        ocultar_chats();
        ((LinearLayout) findViewById(C0627R.id.ll_ad)).removeAllViews();
        if (this.adView != null) {
            try {
                this.adView.destroy();
            } catch (Exception e) {
            }
        }
        this.adView = this.globales.mostrar_banner(this, false);
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
