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
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.ViewCompat;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import hse.here2.t_chat.cancelar_solicitud;
import java.io.File;
import java.util.Date;

public class privados extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private AdView adView;
    private boolean atras_pulsado = false;
    private boolean c1_esclaro;
    private String cbtn;
    private String codigo;
    ProgressDialog dialog_cargando;
    private boolean es_root;
    private Bundle extras;
    private boolean finalizar = false;
    private int fotos_perfil;
    private config globales;
    private int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private ProgressDialog pd;
    private SharedPreferences settings;
    View v_abrir_secc;
    private View v_sel;

    class C07081 implements OnItemClickListener {
        C07081() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (privados.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(privados.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(privados.this.globales.menu_a_secciones[position]));
            privados.this.onClick(view);
        }
    }

    class C07092 implements DialogInterface.OnClickListener {
        C07092() {
        }

        public void onClick(DialogInterface dialog, int id) {
            new cancelar_solicitud((String) privados.this.v_sel.getTag(C0627R.id.idaux1), 1, privados.this.idusu, privados.this.codigo).execute(new String[0]);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        int ind = this.globales.ind_secc_sel_2;
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[ind].c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.secciones_a[ind].c1)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.privados);
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
        this.codigo = this.settings.getString("cod", "");
        this.c1_esclaro = config.esClaro("#" + this.globales.secciones_a[ind].c1);
        if (!this.globales.secciones_a[ind].c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.secciones_a[ind].c1), Color.parseColor("#" + this.globales.secciones_a[ind].c2)}));
        }
        int c_aux = ViewCompat.MEASURED_STATE_MASK;
        if (!this.c1_esclaro) {
            c_aux = -1;
        }
        ((TextView) findViewById(C0627R.id.tv_listaprivados)).setTextColor(c_aux);
        findViewById(C0627R.id.v_listaprivados).setBackgroundColor(c_aux);
        ((TextView) findViewById(C0627R.id.tv_sinprivados)).setTextColor(c_aux);
        this.fotos_perfil = 1;
        mostrar_privados();
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
            this.mDrawerList.setOnItemClickListener(new C07081());
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

    private void mostrar_privados() {
        File path = new File(Environment.getExternalStorageDirectory(), getPackageName());
        LinearLayout llprivados = (LinearLayout) findViewById(C0627R.id.llprivados);
        llprivados.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        boolean hay = false;
        for (int i = 0; i < 100; i++) {
            if (this.settings.contains("privado" + i + "_id")) {
                hay = true;
                int c = ViewCompat.MEASURED_STATE_MASK;
                String dig = this.settings.getString("privado" + i + "_id", "");
                if (!dig.equals("")) {
                    dig = dig.substring(dig.length() - 1, dig.length());
                }
                if (this.c1_esclaro) {
                    if (dig.equals("0") || dig.equals("5")) {
                        c = Color.parseColor("#FF00698C");
                    } else if (dig.equals("1") || dig.equals("6")) {
                        c = Color.parseColor("#FF9933CC");
                    } else if (dig.equals("2") || dig.equals("7")) {
                        c = Color.parseColor("#FF3D5C00");
                    } else if (dig.equals("3") || dig.equals("8")) {
                        c = Color.parseColor("#FF9E5400");
                    } else if (dig.equals("4") || dig.equals("9")) {
                        c = Color.parseColor("#FFCC0000");
                    }
                } else if (dig.equals("0") || dig.equals("5")) {
                    c = Color.parseColor("#FFFF6633");
                } else if (dig.equals("1") || dig.equals("6")) {
                    c = Color.parseColor("#FF66CC33");
                } else if (dig.equals("2") || dig.equals("7")) {
                    c = Color.parseColor("#FFC9ADFF");
                } else if (dig.equals("3") || dig.equals("8")) {
                    c = Color.parseColor("#FF87BFFF");
                } else if (dig.equals("4") || dig.equals("9")) {
                    c = Color.parseColor("#FF33FFFF");
                }
                LinearLayout ll_privado = (LinearLayout) inflater.inflate(C0627R.layout.privado, null);
                ImageView iv = (ImageView) ll_privado.findViewById(C0627R.id.iv_avatar_f);
                if (this.fotos_perfil == 0) {
                    iv.setVisibility(8);
                    ll_privado.findViewById(C0627R.id.v_foto).setBackgroundColor(c);
                    ll_privado.findViewById(C0627R.id.v_foto).setVisibility(0);
                } else {
                    iv.setBackgroundColor(c);
                    try {
                        iv.setImageBitmap(Media.getBitmap(getContentResolver(), Uri.fromFile(new File(path, "fperfil_" + this.settings.getString("privado" + i + "_id", "") + ".jpg"))));
                    } catch (Exception e) {
                        if (this.c1_esclaro) {
                            iv.setImageDrawable(getResources().getDrawable(C0627R.drawable.sinfoto_light));
                        } else {
                            iv.setImageDrawable(getResources().getDrawable(C0627R.drawable.sinfoto));
                        }
                    }
                }
                TextView tv = (TextView) ll_privado.findViewById(C0627R.id.tv_nombre_f);
                tv.setText(this.settings.getString("privado" + i + "_nombre", ""));
                TextView tv_2 = (TextView) ll_privado.findViewById(C0627R.id.tv_nombre_2_f);
                Date date = new Date(this.settings.getLong("privado" + i + "_fultconex", 0));
                tv_2.setText(DateFormat.getDateFormat(getApplicationContext()).format(date) + " " + DateFormat.getTimeFormat(getApplicationContext()).format(date));
                tv.setTextColor(c);
                tv_2.setTextColor(c);
                ll_privado.setTag(C0627R.id.idaux1, this.settings.getString("privado" + i + "_id", ""));
                ll_privado.setTag(C0627R.id.idaux3, this.settings.getString("privado" + i + "_nombre", ""));
                registerForContextMenu(ll_privado);
                ll_privado.setOnClickListener(this);
                llprivados.addView(ll_privado);
            }
        }
        if (!hay) {
            findViewById(C0627R.id.tv_sinprivados).setVisibility(0);
        }
    }

    public void onClick(View v) {
        if (v.getTag(C0627R.id.idaux1) != null) {
            openContextMenu(v);
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.v_sel = v;
        menu.setHeaderTitle((String) v.getTag(C0627R.id.idaux3));
        getMenuInflater().inflate(C0627R.menu.m1_privado, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0627R.id.pedirprivado:
                config.anyadir_privado(this, (String) this.v_sel.getTag(C0627R.id.idaux1), (String) this.v_sel.getTag(C0627R.id.idaux3));
                Editor editor2 = getSharedPreferences("accion", 0).edit();
                editor2.putString("id_remit", (String) this.v_sel.getTag(C0627R.id.idaux1));
                editor2.putString("nombre_remit", (String) this.v_sel.getTag(C0627R.id.idaux3));
                editor2.commit();
                Intent data = new Intent();
                data.putExtra("abrir_privado", true);
                setResult(-1, data);
                finish();
                return true;
            case C0627R.id.eliminar:
                final AlertDialog d_aux_2 = new Builder(this).setNegativeButton(C0627R.string.no, null).setPositiveButton(C0627R.string.si, new C07092()).setMessage(C0627R.string.confirmar_bloquearprivado).create();
                if (!this.cbtn.equals("")) {
                    d_aux_2.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux_2.getButton(-1).setTextColor(Color.parseColor("#" + privados.this.cbtn));
                            d_aux_2.getButton(-2).setTextColor(Color.parseColor("#" + privados.this.cbtn));
                        }
                    });
                }
                final AlertDialog d_aux = new Builder(this).setNegativeButton(C0627R.string.no, null).setPositiveButton(C0627R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        config.elim_privado(privados.this, (String) privados.this.v_sel.getTag(C0627R.id.idaux1));
                        privados.this.mostrar_privados();
                        d_aux_2.show();
                        try {
                            ((TextView) d_aux_2.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                        } catch (Exception e) {
                        }
                    }
                }).setMessage(C0627R.string.confirmar_elimprivado).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + privados.this.cbtn));
                            d_aux.getButton(-2).setTextColor(Color.parseColor("#" + privados.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e) {
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        Editor editor2 = getSharedPreferences("accion", 0).edit();
        editor2.putInt("accion", 0);
        editor2.commit();
        Editor editor = getSharedPreferences("sh", 0).edit();
        editor.putBoolean("activa", true);
        editor.commit();
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
    }

    private void finalizar() {
        Editor editor = getSharedPreferences("sh", 0).edit();
        editor.putBoolean("activa", false);
        editor.commit();
    }

    public void onPause() {
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.pause();
        }
        super.onPause();
        if (isFinishing()) {
            finalizar();
        }
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
