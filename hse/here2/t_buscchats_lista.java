package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;

public class t_buscchats_lista extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    private cargar_foto_async cfa;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    private boolean haymas = false;
    private int idcat;
    private int idchat_modificado;
    private int idsubcat;
    int idusu;
    int ind;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    private Adapter mAdapter;
    ListView mDrawerList;
    private LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private ArrayList<Integer> m_inds = null;
    private ArrayList<claseChat> m_orders = null;
    private ArrayList<claseChat> m_orders_temp = null;
    private boolean obtencion_ok = false;
    private Runnable returnRes = new C07515();
    SharedPreferences settings;
    private int sinfoto_global;
    private Thread thread;
    private int tipo;
    private boolean usu_inapropiado = false;
    View v_abrir_secc;
    private Runnable viewOrders;

    class C07471 implements OnClickListener {
        C07471() {
        }

        public void onClick(View v) {
            t_buscchats_lista.this.finish();
        }
    }

    class C07482 implements OnClickListener {
        C07482() {
        }

        public void onClick(View v) {
            t_buscchats_lista.this.finish();
        }
    }

    class C07493 implements Runnable {
        C07493() {
        }

        public void run() {
            t_buscchats_lista.this.getOrders();
        }
    }

    class C07504 implements OnItemClickListener {
        C07504() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_buscchats_lista.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_buscchats_lista.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_buscchats_lista.this.globales.menu_a_secciones[position]));
            t_buscchats_lista.this.onClick(view);
        }
    }

    class C07515 implements Runnable {
        C07515() {
        }

        public void run() {
            try {
                t_buscchats_lista.this.findViewById(C0627R.id.pb_chats).setVisibility(8);
            } catch (Exception e) {
            }
            if (t_buscchats_lista.this.m_orders_temp != null && t_buscchats_lista.this.m_orders_temp.size() > 0) {
                for (int i = 0; i < t_buscchats_lista.this.m_orders_temp.size(); i++) {
                    if (t_buscchats_lista.this.m_inds.indexOf(Integer.valueOf(((claseChat) t_buscchats_lista.this.m_orders_temp.get(i)).id)) == -1) {
                        t_buscchats_lista.this.m_orders.add(t_buscchats_lista.this.m_orders_temp.get(i));
                        t_buscchats_lista.this.m_inds.add(Integer.valueOf(((claseChat) t_buscchats_lista.this.m_orders_temp.get(i)).id));
                    }
                }
            }
            t_buscchats_lista.this.mAdapter.notifyDataSetChanged();
            if (t_buscchats_lista.this.m_orders != null && t_buscchats_lista.this.m_orders.size() == 0) {
                String msg_aux;
                if (!t_buscchats_lista.this.obtencion_ok) {
                    msg_aux = t_buscchats_lista.this.getResources().getString(C0627R.string.error_http);
                } else if (t_buscchats_lista.this.tipo == 3) {
                    msg_aux = t_buscchats_lista.this.getResources().getString(C0627R.string.lista_vacia_favoritos);
                } else {
                    msg_aux = t_buscchats_lista.this.getResources().getString(C0627R.string.lista_vacia_porcat);
                }
                Builder builder = new Builder(t_buscchats_lista.this);
                builder.setCancelable(true).setPositiveButton(t_buscchats_lista.this.getString(C0627R.string.aceptar), null).setMessage(msg_aux);
                try {
                    AlertDialog d_aux = builder.create();
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e2) {
                    }
                } catch (Exception e3) {
                }
            }
            if (t_buscchats_lista.this.m_orders != null && t_buscchats_lista.this.m_orders.size() > 0) {
                t_buscchats_lista.this.cfa = new cargar_foto_async();
                t_buscchats_lista.this.cfa.execute(new String[0]);
            }
        }
    }

    private class cargar_foto_async extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        int f41f;
        int idapp;
        int idfoto;
        int ind;

        private cargar_foto_async() {
        }

        protected void onPreExecute() {
            boolean cont = false;
            for (int i = 0; i < t_buscchats_lista.this.m_orders.size(); i++) {
                claseChat o = (claseChat) t_buscchats_lista.this.m_orders.get(i);
                if (!o.cargada_1 && o.nfoto1 != 0) {
                    this.f41f = 1;
                    this.idfoto = o.nfoto1;
                    this.idapp = o.idapp;
                    this.ind = i;
                    cont = true;
                    break;
                }
            }
            if (!cont) {
                cancel(true);
            }
        }

        protected String doInBackground(String... urls) {
            try {
                if (this.idfoto > 100) {
                    this.idfoto -= 100;
                }
                URL myFileUrl = null;
                try {
                    myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gen/" + this.idapp + "_ico.png");
                } catch (MalformedURLException e) {
                }
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
                    int width_max = Math.round(TypedValue.applyDimension(1, 200.0f, t_buscchats_lista.this.getResources().getDisplayMetrics()));
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
                    return "ANDROID:KO";
                }
            } catch (Exception e3) {
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
            try {
                if (this.f41f == 1) {
                    ((claseChat) t_buscchats_lista.this.m_orders.get(this.ind)).cargada_1 = true;
                }
                if (result.indexOf("ANDROID:OK") != -1 && this.f41f == 1) {
                    ((claseChat) t_buscchats_lista.this.m_orders.get(this.ind)).b1 = config.crop(this.bmImg, 1);
                }
                t_buscchats_lista.this.mAdapter.notifyDataSetChanged();
                t_buscchats_lista.this.cfa = new cargar_foto_async();
                t_buscchats_lista.this.cfa.execute(new String[0]);
            } catch (Exception e) {
            }
        }
    }

    public class MyAdapter extends Adapter<ViewHolder> {
        private ArrayList<claseChat> items;

        public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
            public ImageView iv1;
            public LinearLayout ll_fila;
            public TextView tt;
            public TextView tt2;

            public ViewHolder(View v) {
                super(v);
                this.ll_fila = (LinearLayout) v.findViewById(C0627R.id.fila);
                this.tt = (TextView) v.findViewById(C0627R.id.titulo);
                this.tt2 = (TextView) v.findViewById(C0627R.id.nusus);
                this.iv1 = (ImageView) v.findViewById(C0627R.id.f1);
            }
        }

        public MyAdapter(ArrayList<claseChat> myDataset) {
            this.items = myDataset;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(C0627R.layout.row_chat, parent, false);
            Drawable d = t_buscchats_lista.this.getResources().getDrawable(C0627R.drawable.ir);
            d.setColorFilter(Color.parseColor("#" + t_buscchats_lista.this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) v.findViewById(C0627R.id.iv_ir)).setImageDrawable(d);
            if (config.esClaro("#" + t_buscchats_lista.this.globales.secciones_a[t_buscchats_lista.this.ind].c1)) {
                ((TextView) v.findViewById(C0627R.id.titulo)).setTextColor(config.NEGRO);
                ((TextView) v.findViewById(C0627R.id.nusus)).setTextColor(config.NEGRO_2);
            } else {
                ((TextView) v.findViewById(C0627R.id.titulo)).setTextColor(-1);
                ((TextView) v.findViewById(C0627R.id.nusus)).setTextColor(config.BLANCO_2);
            }
            v.setOnClickListener(t_buscchats_lista.this);
            return new ViewHolder(v);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            claseChat o = (claseChat) this.items.get(position);
            holder.tt.setText(o.titulo);
            if (o.nusus < 10) {
                holder.tt2.setText("<10 " + t_buscchats_lista.this.getResources().getString(C0627R.string.usuarios));
            } else {
                holder.tt2.setText(NumberFormat.getInstance().format((long) o.nusus) + " " + t_buscchats_lista.this.getResources().getString(C0627R.string.usuarios));
            }
            if (o.nfoto1 == 0) {
                holder.iv1.setImageDrawable(t_buscchats_lista.this.getResources().getDrawable(t_buscchats_lista.this.sinfoto_global));
            } else if (o.b1 != null) {
                holder.iv1.setImageBitmap(o.b1);
            } else if (o.cargada_1) {
                holder.iv1.setImageDrawable(t_buscchats_lista.this.getResources().getDrawable(t_buscchats_lista.this.sinfoto_global));
            } else {
                holder.iv1.setImageDrawable(t_buscchats_lista.this.getResources().getDrawable(C0627R.drawable.cargando));
            }
            holder.ll_fila.setTag(Integer.valueOf(position));
            if (!t_buscchats_lista.this.haymas || position != t_buscchats_lista.this.m_orders.size() - 1) {
                return;
            }
            if (t_buscchats_lista.this.thread == null || !t_buscchats_lista.this.thread.isAlive()) {
                t_buscchats_lista.this.findViewById(C0627R.id.pb_chats).setVisibility(0);
                t_buscchats_lista.this.thread = new Thread(null, t_buscchats_lista.this.viewOrders, "buscandoelems");
                t_buscchats_lista.this.thread.start();
            }
        }

        public int getItemCount() {
            return this.items.size();
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
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
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
        setContentView(C0627R.layout.t_buscchats_lista);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.idcat = 0;
        this.idsubcat = 0;
        this.tipo = 0;
        if (this.extras.containsKey("idcat")) {
            this.idcat = this.extras.getInt("idcat");
        }
        if (this.extras.containsKey("idsubcat")) {
            this.idsubcat = this.extras.getInt("idsubcat");
        }
        if (this.extras.containsKey("tipo")) {
            this.tipo = this.extras.getInt("tipo");
        }
        this.adView = this.globales.mostrar_banner(this, false);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.sinfoto_global = C0627R.drawable.sinfoto_chat;
        if (!this.globales.secciones_a[this.ind].c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.secciones_a[this.ind].c1), Color.parseColor("#" + this.globales.secciones_a[this.ind].c2)}));
        }
        if (config.esClaro("#" + this.globales.secciones_a[this.ind].c1)) {
            findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_OSCURO);
            ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(-1);
        } else {
            findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_CLARO);
            ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(config.NEGRO);
        }
        if (this.extras.containsKey("tit_cab")) {
            ((TextView) findViewById(C0627R.id.tv_cabe)).setText(this.extras.getString("tit_cab"));
            findViewById(C0627R.id.tv_cabe).setOnClickListener(new C07471());
            findViewById(C0627R.id.iv_cabe).setOnClickListener(new C07482());
            findViewById(C0627R.id.ll_cabe).setVisibility(0);
        }
        this.m_orders = new ArrayList();
        this.m_inds = new ArrayList();
        this.mRecyclerView = (RecyclerView) findViewById(C0627R.id.my_recycler_view);
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mAdapter = new MyAdapter(this.m_orders);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.viewOrders = new C07493();
        this.thread = new Thread(null, this.viewOrders, "buscandoelems");
        findViewById(C0627R.id.pb_chats).setVisibility(0);
        this.thread.start();
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
            this.mDrawerList.setOnItemClickListener(new C07504());
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

    public void onClick(View v) {
        if (v.getId() == C0627R.id.fila) {
            int position = this.mRecyclerView.getChildPosition(v);
            if (position < this.m_orders.size()) {
                abrir_detalle((claseChat) this.m_orders.get(position), position);
            }
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    private void abrir_detalle(claseChat o, int idview) {
        Intent i = new Intent(this, t_chat_contra.class);
        i.putExtra("externo", true);
        i.putExtra("idchat", o.id);
        i.putExtra("idtema", o.idtema);
        i.putExtra("fotos_perfil", o.fotos_perfil);
        i.putExtra("fnac", o.p_fnac);
        i.putExtra("sexo", o.p_sexo);
        i.putExtra("descr", o.p_descr);
        i.putExtra("dist", o.p_dist);
        i.putExtra("privados", o.privados);
        i.putExtra("coments", o.coments);
        i.putExtra("galeria", o.galeria);
        i.putExtra("fotos_chat", o.fotos_chat);
        i.putExtra("c1", o.c1);
        i.putExtra("c2", o.c2);
        i.putExtra("tit_cab", o.titulo);
        startActivityForResult(i, 0);
    }

    private void getOrders() {
        int pos1;
        HttpURLConnection conn = null;
        String total = "";
        try {
            conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/obtenerchats.php?idusu=" + this.idusu + "&tipo=" + this.tipo + "&idcat=" + this.idcat + "&idsubcat=" + this.idsubcat + "&fila=" + this.m_orders.size()).openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
            conn.setReadTimeout(StatusCodes.AUTH_DISABLED);
            conn.setRequestProperty("User-Agent", "Android Vinebre Software");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb_total = new StringBuilder();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb_total.append(line + "\n");
            }
            total = sb_total.toString();
            if (conn != null) {
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (total.indexOf("ANDROID:KO MOTIVO:INAPROPIADO") != -1) {
                this.usu_inapropiado = true;
            }
            pos1 = total.indexOf("ANDROID:OK RESULT:");
            this.obtencion_ok = false;
            if (pos1 != -1) {
                Editor editor = this.settings.edit();
                this.obtencion_ok = true;
                this.m_orders_temp = new ArrayList();
                if (total.indexOf("ANDROID:OK RESULT:0") == -1) {
                    if (total.indexOf("ANDROID:OK RESULT:1") != -1) {
                        this.haymas = true;
                    }
                } else {
                    this.haymas = false;
                }
                pos1 = total.indexOf("@y@", pos1);
                while (pos1 != -1) {
                    pos1 += 3;
                    int pos2 = total.indexOf(";", pos1);
                    int idapp = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int id = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int idtema = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fotos_perfil = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int privados = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = total.indexOf(";", pos2 + 1) + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fotos_chat = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    String titulo = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    String c1 = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    String c2 = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int nusus = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int coments = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fnac = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int sexo = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int descr = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int galeria = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    this.m_orders_temp.add(new claseChat(idapp, id, idtema, titulo, c1, c2, fotos_perfil, fnac, sexo, descr, Integer.parseInt(total.substring(pos1, pos2)), fotos_chat, privados == 1, coments == 1, galeria == 1, nusus, null, 1, Boolean.valueOf(false).booleanValue()));
                    pos1 = total.indexOf("@y@", pos2);
                }
                editor.commit();
            }
            runOnUiThread(this.returnRes);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (total.indexOf("ANDROID:KO MOTIVO:INAPROPIADO") != -1) {
            this.usu_inapropiado = true;
        }
        pos1 = total.indexOf("ANDROID:OK RESULT:");
        this.obtencion_ok = false;
        if (pos1 != -1) {
            Editor editor2 = this.settings.edit();
            this.obtencion_ok = true;
            this.m_orders_temp = new ArrayList();
            if (total.indexOf("ANDROID:OK RESULT:0") == -1) {
                this.haymas = false;
            } else {
                if (total.indexOf("ANDROID:OK RESULT:1") != -1) {
                    this.haymas = true;
                }
            }
            pos1 = total.indexOf("@y@", pos1);
            while (pos1 != -1) {
                pos1 += 3;
                int pos22 = total.indexOf(";", pos1);
                int idapp2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int id2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int idtema2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int fotos_perfil2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int privados2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = total.indexOf(";", pos22 + 1) + 1;
                pos22 = total.indexOf(";", pos1);
                int fotos_chat2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                String titulo2 = total.substring(pos1, pos22);
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                String c12 = total.substring(pos1, pos22);
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                String c22 = total.substring(pos1, pos22);
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int nusus2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int coments2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int fnac2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int sexo2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int descr2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                int galeria2 = Integer.parseInt(total.substring(pos1, pos22));
                pos1 = pos22 + 1;
                pos22 = total.indexOf(";", pos1);
                if (privados2 == 1) {
                }
                if (coments2 == 1) {
                }
                if (galeria2 == 1) {
                }
                this.m_orders_temp.add(new claseChat(idapp2, id2, idtema2, titulo2, c12, c22, fotos_perfil2, fnac2, sexo2, descr2, Integer.parseInt(total.substring(pos1, pos22)), fotos_chat2, privados2 == 1, coments2 == 1, galeria2 == 1, nusus2, null, 1, Boolean.valueOf(false).booleanValue()));
                pos1 = total.indexOf("@y@", pos22);
            }
            editor2.commit();
        }
        runOnUiThread(this.returnRes);
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

    public void onStop() {
        if (this.usu_inapropiado) {
            finish();
        }
        super.onStop();
        if (this.finalizar) {
            finish();
        }
    }

    public void onPause() {
        if (this.cfa != null) {
            try {
                this.cfa.cancel(true);
            } catch (Exception e) {
            }
        }
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.pause();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (this.m_orders != null && this.m_orders.size() > 0) {
            this.cfa = new cargar_foto_async();
            this.cfa.execute(new String[0]);
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
