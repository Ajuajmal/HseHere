package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import java.net.URLEncoder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class chat extends Activity implements OnKeyListener, OnClickListener, OnSharedPreferenceChangeListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    public Activity acti;
    AdView adView;
    private int altura;
    boolean atras_pulsado = false;
    private String cbtn;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    private config globales;
    private int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private String m_global;
    private SharedPreferences settings;
    View v_abrir_secc;

    class C06334 implements OnItemClickListener {
        C06334() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (chat.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(chat.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(chat.this.globales.menu_a_secciones[position]));
            chat.this.onClick(view);
        }
    }

    private class enviar extends AsyncTask<String, Void, String> {
        String f36m;

        public enviar(String m) {
            this.f36m = m;
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/enviarmensaje.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("idusu", new StringBody(chat.this.idusu + ""));
                reqEntity.addPart("idapp", new StringBody("299914"));
                reqEntity.addPart("nombre", new StringBody(URLEncoder.encode(chat.this.settings.getString("nombre", ""), "UTF-8")));
                reqEntity.addPart("m", new StringBody(URLEncoder.encode(this.f36m, "UTF-8")));
                postRequest.setEntity(reqEntity);
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.execute(postRequest).getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();
                while (true) {
                    String sResponse = reader.readLine();
                    if (sResponse == null) {
                        return s.toString();
                    }
                    s = s.append(sResponse);
                }
            } catch (Exception e) {
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
            Builder builder = new Builder(chat.this);
            try {
                chat.this.findViewById(C0627R.id.pb_chat_env).setVisibility(8);
                ((EditText) chat.this.findViewById(C0627R.id.c_mensaje)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            } catch (Exception e) {
            }
            final AlertDialog d_aux;
            if (result.indexOf("ANDROID:OK") == -1 && result.indexOf("ANDROID:KO") == -1) {
                d_aux = builder.setCancelable(false).setPositiveButton(chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                if (!chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            } else if (result.indexOf("ANDROID:OK") != -1) {
                ((EditText) chat.this.findViewById(C0627R.id.c_mensaje)).setText("");
                Editor editor = chat.this.settings.edit();
                editor.putString("conv", chat.this.settings.getString("conv", "") + "@" + chat.this.idusu + "@" + this.f36m);
                editor.putString("f_id", chat.this.idusu + "");
                editor.putString("f_frase", this.f36m);
                editor.putString("f_idfrase", config.idfrase_global + "");
                config.idfrase_global++;
                editor.commit();
                if (chat.this.settings.getBoolean("primer_msg_chat", true)) {
                    editor.putBoolean("primer_msg_chat", false);
                    editor.commit();
                    d_aux = builder.setCancelable(false).setPositiveButton(chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.primer_msg_chat).create();
                    if (!chat.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                            }
                        });
                    }
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e3) {
                    }
                }
            } else if (result.indexOf("ANDROID:KO MOTIVO:NOGCM") != -1) {
                d_aux = builder.setCancelable(false).setPositiveButton(chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.nogcm).create();
                if (!chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e4) {
                }
            } else {
                d_aux = builder.setCancelable(false).setPositiveButton(chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                if (!chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e5) {
                }
            }
        }
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.cbtn = config.aplicar_color_dialog(this.globales.c1_ofic, this.globales.c_ir_ofic);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.c1_ofic)) {
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
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.chat);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            config hse_here2_config = this.globales;
            z = this.extras != null && this.extras.containsKey("ad_entrar");
            hse_here2_config.toca_int(this, z);
        }
        this.adView = this.globales.mostrar_banner(this, false);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.acti = this;
        Display display = getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            this.altura = size.y;
        } else {
            this.altura = display.getHeight();
        }
        if (!this.globales.c1_ofic.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_ofic), Color.parseColor("#" + this.globales.c2_ofic)}));
        }
        if (!this.globales.c_ico_sep_ofic.equals("")) {
            Drawable dw = getResources().getDrawable(C0627R.drawable.chat_contactar);
            dw.setColorFilter(Color.parseColor("#" + this.globales.c_ico_sep_ofic), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_chat)).setImageDrawable(dw);
        }
        if (!this.globales.c_tit_ofic.equals("")) {
            ((TextView) findViewById(C0627R.id.tv_tit)).setTextColor(Color.parseColor("#" + this.globales.c_tit_ofic));
        }
        if (!this.globales.c_sep_ofic.equals("")) {
            findViewById(C0627R.id.v_sep).setBackgroundColor(Color.parseColor("#" + this.globales.c_sep_ofic));
        }
        if (VERSION.SDK_INT > 20) {
            config.progress_color((ProgressBar) findViewById(C0627R.id.pb_chat_env), this.globales.c_ir_ofic);
        }
        findViewById(C0627R.id.iv_env).setOnClickListener(this);
        if (VERSION.SDK_INT > 20) {
            config.edittext_color((EditText) findViewById(C0627R.id.c_mensaje), Boolean.valueOf(false), this.globales.c_ir_ofic);
        } else {
            ((EditText) findViewById(C0627R.id.c_mensaje)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
        }
        findViewById(C0627R.id.c_mensaje).setOnKeyListener(this);
        if (config.esClaro("#" + this.globales.c_ir_ofic)) {
            findViewById(C0627R.id.iv_btn_fondo_n).setVisibility(0);
        } else {
            findViewById(C0627R.id.iv_btn_fondo_b).setVisibility(0);
        }
        Drawable d = getResources().getDrawable(C0627R.drawable.btn_enviar);
        d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
        ((ImageView) findViewById(C0627R.id.iv_env)).setImageDrawable(d);
        this.settings.registerOnSharedPreferenceChangeListener(this);
        String conv = this.settings.getString("conv", "");
        int pos2 = 0;
        if (!conv.equals("")) {
            Editor editor = this.settings.edit();
            editor.putString("f_idfrase", "0");
            editor.commit();
            while (pos2 != -1) {
                int longitud;
                String m_aux;
                int pos = pos2;
                int pos_0 = conv.indexOf("@0@", pos + 1);
                int pos_idusu = conv.indexOf("@" + this.idusu + "@", pos + 1);
                if (pos_0 != -1 && pos_idusu != -1) {
                    pos2 = Math.min(pos_0, pos_idusu);
                } else if (pos_0 != -1) {
                    pos2 = pos_0;
                } else if (pos_idusu != -1) {
                    pos2 = pos_idusu;
                } else {
                    pos2 = -1;
                }
                if (conv.substring(pos, pos + 3).equals("@0@")) {
                    editor.putString("f_id", "0");
                    longitud = 3;
                } else {
                    editor.putString("f_id", this.idusu + "");
                    longitud = (this.idusu + "").length() + 2;
                }
                editor.putString("f_idfrase", config.idfrase_global + "");
                if (pos2 != -1) {
                    m_aux = conv.substring(pos + longitud, pos2);
                } else {
                    m_aux = conv.substring(pos + longitud);
                }
                editor.putString("f_frase", m_aux);
                editor.commit();
                config.idfrase_global++;
            }
        }
    }

    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (event.getAction() != 0 || keyCode != 66) {
            return false;
        }
        f_enviar(true);
        return true;
    }

    private void f_enviar(boolean desde_teclado) {
        this.m_global = ((EditText) findViewById(C0627R.id.c_mensaje)).getText().toString();
        this.m_global = this.m_global.replace("@", "");
        this.m_global = this.m_global.trim();
        if (!this.m_global.equals("")) {
            if (desde_teclado && this.altura < 600) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(((EditText) findViewById(C0627R.id.c_mensaje)).getWindowToken(), 0);
            }
            if (this.settings.getString("nombre", "").equals("")) {
                final EditText input = new EditText(this);
                input.setSingleLine();
                input.setInputType(8193);
                if (!this.cbtn.equals("")) {
                    boolean z;
                    if (config.esClaro("#" + this.globales.c1_ofic)) {
                        z = false;
                    } else {
                        z = true;
                    }
                    config.edittext_color(input, Boolean.valueOf(z), this.cbtn);
                }
                final AlertDialog d_aux = new Builder(this).setNegativeButton(getString(C0627R.string.cancelar), null).setPositiveButton(getString(C0627R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String nombre = input.getText().toString();
                        if (nombre.equals("")) {
                            final AlertDialog d_aux2 = new Builder(chat.this).setPositiveButton(chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.falta_nombre).create();
                            if (!chat.this.cbtn.equals("")) {
                                d_aux2.setOnShowListener(new OnShowListener() {
                                    public void onShow(DialogInterface arg0) {
                                        d_aux2.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                                    }
                                });
                            }
                            d_aux2.show();
                            try {
                                ((TextView) d_aux2.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                                return;
                            } catch (Exception e) {
                                return;
                            }
                        }
                        Editor editor = chat.this.settings.edit();
                        editor.putString("nombre", nombre);
                        editor.commit();
                        ((EditText) chat.this.findViewById(C0627R.id.c_mensaje)).setTextColor(-7829368);
                        chat.this.findViewById(C0627R.id.pb_chat_env).setVisibility(0);
                        new enviar(chat.this.m_global).execute(new String[0]);
                    }
                }).setMessage(C0627R.string.pon_nombre).setView(input).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                            d_aux.getButton(-2).setTextColor(Color.parseColor("#" + chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    return;
                } catch (Exception e) {
                    return;
                }
            }
            ((EditText) findViewById(C0627R.id.c_mensaje)).setTextColor(-7829368);
            findViewById(C0627R.id.pb_chat_env).setVisibility(0);
            new enviar(this.m_global).execute(new String[0]);
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.iv_env) {
            f_enviar(false);
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

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (prefs.contains("f_idfrase") && key.equals("f_idfrase") && !prefs.getString("f_idfrase", "0").equals("0")) {
            final SharedPreferences p = prefs;
            runOnUiThread(new Runnable() {

                class C06311 implements Runnable {
                    C06311() {
                    }

                    public void run() {
                        ((ScrollView) chat.this.findViewById(C0627R.id.sv_chat)).fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
                    }
                }

                public void run() {
                    LinearLayout ll_frase = (LinearLayout) ((LayoutInflater) chat.this.getSystemService("layout_inflater")).inflate(C0627R.layout.frase, null);
                    TextView tv = (TextView) ll_frase.findViewById(C0627R.id.message_text);
                    tv.setText(p.getString("f_frase", ""));
                    if (p.getString("f_id", "").equals(chat.this.idusu + "")) {
                        tv.setBackgroundResource(C0627R.drawable.speech_bubble_gray);
                    } else {
                        tv.setBackgroundResource(C0627R.drawable.speech_bubble_green);
                    }
                    LinearLayout llchat = (LinearLayout) chat.this.findViewById(C0627R.id.llchat);
                    llchat.addView(ll_frase);
                    ScrollView sc = (ScrollView) chat.this.findViewById(C0627R.id.sv_chat);
                    if (llchat.getMeasuredHeight() <= sc.getScrollY() + sc.getHeight()) {
                        sc.post(new C06311());
                    }
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        Editor editor = this.settings.edit();
        editor.putBoolean("activa", true);
        editor.commit();
        this.settings.registerOnSharedPreferenceChangeListener(this);
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
    }

    void finalizar() {
        Editor editor = this.settings.edit();
        editor.putBoolean("activa", false);
        editor.commit();
        this.settings.unregisterOnSharedPreferenceChangeListener(this);
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
            this.mDrawerList.setOnItemClickListener(new C06334());
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
        finalizar();
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
