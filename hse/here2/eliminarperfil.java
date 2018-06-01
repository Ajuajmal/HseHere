package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class eliminarperfil extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    String cbtn;
    ProgressDialog dialog_cargando;
    ProgressDialog dialog_eliminando;
    boolean es_root;
    boolean externo;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    int idsecc;
    int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    SharedPreferences settings;
    View v_abrir_secc;

    class C06651 implements OnShowListener {
        C06651() {
        }

        public void onShow(DialogInterface dialog) {
            config.progress_color((ProgressBar) eliminarperfil.this.dialog_eliminando.findViewById(16908301), eliminarperfil.this.globales.c_icos);
        }
    }

    class C06662 implements OnItemClickListener {
        C06662() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (eliminarperfil.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(eliminarperfil.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(eliminarperfil.this.globales.menu_a_secciones[position]));
            eliminarperfil.this.onClick(view);
        }
    }

    private class eliminar extends AsyncTask<String, Void, Byte> {

        class C06671 implements DialogInterface.OnClickListener {
            C06671() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(eliminarperfil.this, preperfil.class);
                if (eliminarperfil.this.externo) {
                    intent = config.completar_externo(intent, eliminarperfil.this.extras);
                } else {
                    intent.putExtra("idsecc", eliminarperfil.this.idsecc);
                }
                if (eliminarperfil.this.globales.tipomenu != 2) {
                    intent.putExtra("es_root", true);
                    eliminarperfil.this.es_root = false;
                }
                eliminarperfil.this.finalizar = true;
                eliminarperfil.this.startActivity(intent);
            }
        }

        private eliminar() {
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/eliminar_perfil.php?idusu=" + eliminarperfil.this.idusu + "&idapp=" + 299914 + "&c=" + eliminarperfil.this.settings.getString("cod", "")).openConnection();
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
                if (sb_total.toString().indexOf("ANDROID:OK") != -1) {
                    valueOf = Byte.valueOf((byte) 1);
                    if (conn != null) {
                        conn.disconnect();
                    }
                } else {
                    valueOf = Byte.valueOf((byte) 2);
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
            if (eliminarperfil.this.dialog_eliminando.isShowing()) {
                eliminarperfil.this.dialog_eliminando.dismiss();
            }
            if (result.byteValue() == (byte) 1) {
                Editor e = eliminarperfil.this.settings.edit();
                try {
                    eliminarperfil.this.globales.getTempFile(eliminarperfil.this, 1).delete();
                } catch (Exception e2) {
                }
                e.remove("nick");
                e.commit();
                eliminarperfil.this.mostrar_msg();
                return;
            }
            final AlertDialog d_aux = new Builder(eliminarperfil.this).setCancelable(false).setPositiveButton(eliminarperfil.this.getString(C0627R.string.aceptar), new C06671()).setMessage(C0627R.string.error_http).create();
            if (!eliminarperfil.this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + eliminarperfil.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e3) {
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        String c1;
        String c2;
        this.globales = (config) getApplicationContext();
        this.cbtn = config.aplicar_color_dialog("FFFFFFFF", this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.eliminarperfil);
        incluir_menu_pre();
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
        this.externo = this.extras.containsKey("externo");
        int ind = this.globales.ind_secc_sel_2;
        if (this.externo) {
            c1 = this.globales.secciones_a[ind].c1;
            c2 = this.globales.secciones_a[ind].c2;
        } else {
            c1 = this.globales.secciones_a[ind].c1;
            c2 = this.globales.secciones_a[ind].c2;
            this.idsecc = this.extras.getInt("idsecc");
        }
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        if (!c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + c1), Color.parseColor("#" + c2)}));
            if (config.esClaro("#" + c1)) {
                ((TextView) findViewById(C0627R.id.message_text)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            } else {
                ((TextView) findViewById(C0627R.id.message_text)).setTextColor(-1);
            }
        }
        this.dialog_eliminando = new ProgressDialog(this);
        this.dialog_eliminando.setMessage(getString(C0627R.string.eliminando));
        this.dialog_eliminando.setIndeterminate(true);
        if (VERSION.SDK_INT > 20) {
            this.dialog_eliminando.setOnShowListener(new C06651());
        }
        this.dialog_eliminando.show();
        new eliminar().execute(new String[0]);
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
            this.mDrawerList.setOnItemClickListener(new C06662());
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

    void mostrar_msg() {
        TextView tv = (TextView) findViewById(C0627R.id.message_text);
        tv.setText(C0627R.string.perfileliminado);
        tv.setVisibility(0);
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
