package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Calendar;

public class notifs extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private AdView adView;
    private boolean atras_pulsado = false;
    private boolean c1_esclaro;
    private String cbtn;
    ProgressDialog dialog_cargando;
    private boolean es_root;
    private Bundle extras;
    private boolean finalizar = false;
    private config globales;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private SharedPreferences settings;
    View v_abrir_secc;

    class C06821 implements OnItemClickListener {
        C06821() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (notifs.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(notifs.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(notifs.this.globales.menu_a_secciones[position]));
            notifs.this.onClick(view);
        }
    }

    class C06832 implements DialogInterface.OnClickListener {
        C06832() {
        }

        public void onClick(DialogInterface dialog, int id) {
            config.elim_notifs(notifs.this);
            notifs.this.mostrar_notifs();
            config.mostrar_notif_noleidas(notifs.this);
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
        setContentView(C0627R.layout.notifs);
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
        this.c1_esclaro = config.esClaro("#" + this.globales.c1);
        if (!this.globales.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1), Color.parseColor("#" + this.globales.c2)}));
        }
        int c_aux = ViewCompat.MEASURED_STATE_MASK;
        if (!this.c1_esclaro) {
            c_aux = -1;
        }
        ((TextView) findViewById(C0627R.id.tv_listanotifs)).setTextColor(c_aux);
        findViewById(C0627R.id.v_listanotifs).setBackgroundColor(c_aux);
        ((TextView) findViewById(C0627R.id.tv_sinnotifs)).setTextColor(c_aux);
        if (this.c1_esclaro) {
            ((ImageView) findViewById(C0627R.id.btntrash)).setImageDrawable(getResources().getDrawable(C0627R.drawable.trash));
            ((ImageView) findViewById(C0627R.id.btnsettings)).setImageDrawable(getResources().getDrawable(C0627R.drawable.settings));
        } else {
            ((ImageView) findViewById(C0627R.id.btntrash)).setImageDrawable(getResources().getDrawable(C0627R.drawable.trash_light));
            ((ImageView) findViewById(C0627R.id.btnsettings)).setImageDrawable(getResources().getDrawable(C0627R.drawable.settings_light));
        }
        findViewById(C0627R.id.btntrash).setOnClickListener(this);
        if (this.globales.hay_catsnotif) {
            findViewById(C0627R.id.btnsettings).setOnClickListener(this);
            findViewById(C0627R.id.btnsettings).setVisibility(0);
        }
        mostrar_notifs();
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
            this.mDrawerList.setOnItemClickListener(new C06821());
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

    private void mostrar_notifs() {
        LinearLayout llnotifs = (LinearLayout) findViewById(C0627R.id.llnotifs);
        llnotifs.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        int n = 0;
        for (int i = this.settings.getInt("id_ultnotif", 0); i > 0; i--) {
            if (this.settings.contains("notif_" + i + "_title")) {
                int c = config.NEGRO;
                int c2 = config.GRIS_OSCURO;
                if (!this.c1_esclaro) {
                    c = config.BLANCO;
                    c2 = config.GRIS_CLARO;
                }
                LinearLayout ll_notif = (LinearLayout) inflater.inflate(C0627R.layout.notif, null);
                TextView tv = (TextView) ll_notif.findViewById(C0627R.id.tv_title);
                tv.setText(this.settings.getString("notif_" + i + "_title", ""));
                TextView tv_2 = (TextView) ll_notif.findViewById(C0627R.id.tv_text);
                tv_2.setText(this.settings.getString("notif_" + i + "_text", ""));
                tv.setTextColor(c);
                tv_2.setTextColor(c);
                ll_notif.findViewById(C0627R.id.v_sepnotifs).setBackgroundColor(c2);
                if (!this.settings.getBoolean("notif_" + i + "_leida", false)) {
                    ll_notif.findViewById(C0627R.id.tv_nuevo).setVisibility(0);
                }
                DateFormat formato_fecha = android.text.format.DateFormat.getDateFormat(this);
                DateFormat formato_hora = android.text.format.DateFormat.getTimeFormat(this);
                TextView tv_f = (TextView) ll_notif.findViewById(C0627R.id.tv_f);
                if (System.currentTimeMillis() - this.settings.getLong("notif_" + i + "_fcrea", 0) < 300000) {
                    tv_f.setText(getResources().getString(C0627R.string.ahora));
                } else {
                    Calendar hoy = Calendar.getInstance();
                    Calendar d_cal = Calendar.getInstance();
                    d_cal.setTimeInMillis(this.settings.getLong("notif_" + i + "_fcrea", 0));
                    if (d_cal.get(1) == hoy.get(1) && d_cal.get(6) == hoy.get(6)) {
                        tv_f.setText(getResources().getString(C0627R.string.hoy) + " " + formato_hora.format(Long.valueOf(d_cal.getTimeInMillis())));
                    } else {
                        tv_f.setText(formato_fecha.format(Long.valueOf(d_cal.getTimeInMillis())) + " " + formato_hora.format(Long.valueOf(d_cal.getTimeInMillis())));
                    }
                }
                ll_notif.setTag(C0627R.id.idaux1, Integer.valueOf(i));
                ll_notif.setOnClickListener(this);
                llnotifs.addView(ll_notif);
                n++;
                if (n > 99) {
                    break;
                }
            }
        }
        if (n == 0) {
            findViewById(C0627R.id.tv_sinnotifs).setVisibility(0);
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.btntrash) {
            if (this.settings.getInt("id_ultnotif", 0) != 0) {
                final AlertDialog d_aux = new Builder(this).setNegativeButton(C0627R.string.cancelar, null).setPositiveButton(C0627R.string.aceptar, new C06832()).setMessage(C0627R.string.confirmar_elimnotifs).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + notifs.this.cbtn));
                            d_aux.getButton(-2).setTextColor(Color.parseColor("#" + notifs.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e) {
                }
            }
        } else if (v.getId() == C0627R.id.btnsettings) {
            startActivityForResult(new Intent(this, notifs_cats.class), 0);
        } else if (v.getTag(C0627R.id.idaux1) != null) {
            int numnotif = this.settings.getInt("notif_" + v.getTag(C0627R.id.idaux1) + "_numnotif", 0);
            if (numnotif != 0) {
                ((NotificationManager) getSystemService("notification")).cancel(numnotif);
            }
            Editor editor = this.settings.edit();
            editor.putBoolean("notif_" + v.getTag(C0627R.id.idaux1) + "_leida", true);
            editor.commit();
            config.act_notif_noleidas(this);
            String intent_str = this.settings.getString("notif_" + v.getTag(C0627R.id.idaux1) + "_intent", "");
            if (!intent_str.equals("")) {
                Intent i = null;
                boolean ok = true;
                try {
                    i = Intent.parseUri(intent_str, 0);
                } catch (URISyntaxException e2) {
                    ok = false;
                }
                if (ok) {
                    i.putExtra("directo", true);
                    startActivity(i);
                    Intent i_result = new Intent();
                    i_result.putExtra("finalizar", true);
                    i_result.putExtra("finalizar_app", true);
                    config.finalizar_app = true;
                    this.finalizar = true;
                    setResult(-1, i_result);
                }
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
