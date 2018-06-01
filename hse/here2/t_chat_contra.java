package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.android.gms.plus.PlusShare;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class t_chat_contra extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    Builder adb;
    String c1;
    boolean c1_esclaro;
    String c2;
    String cbtn;
    String cod_g;
    String codigo;
    String contra;
    boolean contra_ko = false;
    ProgressDialog dialog_cargando;
    private ProgressDialog dialog_enviando;
    CheckBox dontShowAgain;
    boolean es_root;
    EditText et_contra;
    boolean externo;
    Bundle extras;
    boolean finalizar = false;
    boolean finalizar_2 = false;
    config globales;
    int idsecc;
    int idusu;
    int ind;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    SharedPreferences sp;
    View v_abrir_secc;

    class C07831 implements DialogInterface.OnClickListener {

        class C07821 implements OnShowListener {
            C07821() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) t_chat_contra.this.dialog_enviando.findViewById(16908301), t_chat_contra.this.globales.c_icos);
            }
        }

        C07831() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Editor editor = t_chat_contra.this.sp.edit();
            if (t_chat_contra.this.dontShowAgain.isChecked()) {
                editor.putBoolean("chat" + t_chat_contra.this.idsecc + "_nomostrarmas_def", true);
            } else {
                editor.putBoolean("chat" + t_chat_contra.this.idsecc + "_nomostrarmas_def", false);
            }
            editor.commit();
            t_chat_contra.this.dialog_enviando = new ProgressDialog(t_chat_contra.this);
            t_chat_contra.this.dialog_enviando.setMessage(t_chat_contra.this.getString(C0627R.string.comprobando));
            t_chat_contra.this.dialog_enviando.setIndeterminate(true);
            if (VERSION.SDK_INT > 20) {
                t_chat_contra.this.dialog_enviando.setOnShowListener(new C07821());
            }
            t_chat_contra.this.dialog_enviando.show();
            t_chat_contra.this.contra = t_chat_contra.this.et_contra.getText().toString();
            new enviarTask().execute(new String[0]);
        }
    }

    class C07842 implements DialogInterface.OnClickListener {
        C07842() {
        }

        public void onClick(DialogInterface dialog, int which) {
            t_chat_contra.this.mostrar_msg(0);
        }
    }

    class C07853 implements OnCancelListener {
        C07853() {
        }

        public void onCancel(DialogInterface dialog) {
            t_chat_contra.this.mostrar_msg(0);
        }
    }

    class C07875 implements OnItemClickListener {
        C07875() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_chat_contra.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_chat_contra.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_chat_contra.this.globales.menu_a_secciones[position]));
            t_chat_contra.this.onClick(view);
        }
    }

    private class enviarTask extends AsyncTask<String, Void, Byte> {
        private enviarTask() {
        }

        protected Byte doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://srv1.androidcreator.com/srv/comprobar_contra.php?v=1&idapp=299914&idusu=" + t_chat_contra.this.idusu + "&idchat=" + t_chat_contra.this.idsecc);
            String line = "";
            StringBuilder total = new StringBuilder();
            try {
                List<NameValuePair> nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("contra", t_chat_contra.this.contra));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httppost.setHeader("User-Agent", "Android Vinebre Software");
                BufferedReader rd = new BufferedReader(new InputStreamReader(httpclient.execute(httppost).getEntity().getContent()));
                while (true) {
                    line = rd.readLine();
                    if (line == null) {
                        break;
                    }
                    total.append(line);
                }
                if (total.indexOf("ANDROID:KO") != -1) {
                    return Byte.valueOf((byte) 2);
                }
                if (total.indexOf("ANDROID:OK") != -1) {
                    return Byte.valueOf((byte) 1);
                }
                return Byte.valueOf((byte) 0);
            } catch (ClientProtocolException e) {
                return Byte.valueOf((byte) 0);
            } catch (IOException e2) {
                return Byte.valueOf((byte) 0);
            }
        }

        protected void onPostExecute(Byte result) {
            try {
                t_chat_contra.this.dialog_enviando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) 1) {
                if (t_chat_contra.this.sp.getBoolean("chat" + t_chat_contra.this.idsecc + "_nomostrarmas_def", true)) {
                    Editor editor = t_chat_contra.this.sp.edit();
                    editor.putBoolean("chat" + t_chat_contra.this.idsecc + "_validado", true);
                    editor.commit();
                }
                t_chat_contra.this.iniciar();
            } else if (result.byteValue() == (byte) 2) {
                t_chat_contra.this.contra_ko = true;
                t_chat_contra.this.preguntar_contra();
            } else {
                t_chat_contra.this.mostrar_msg(1);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.finalizar_2 = savedInstanceState.getBoolean("finalizar_2", false);
        }
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.extras = getIntent().getExtras();
        boolean z;
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        this.externo = this.extras.containsKey("externo");
        if (this.externo) {
            this.c1 = this.extras.getString("c1");
            this.c2 = this.extras.getString("c2");
        } else {
            this.ind = this.extras.getInt("ind");
            this.c1 = this.globales.secciones_a[this.ind].c1;
            this.c2 = this.globales.secciones_a[this.ind].c2;
            this.idsecc = this.globales.secciones_a[this.ind].id;
        }
        this.c1_esclaro = config.esClaro("#" + this.c1);
        this.cbtn = config.aplicar_color_dialog(this.c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !this.c1_esclaro) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_chat_contra);
        incluir_menu_pre();
        this.adView = this.globales.mostrar_banner(this, false);
        this.sp = getSharedPreferences("sh", 0);
        this.idusu = this.sp.getInt("idusu", 0);
        this.codigo = this.sp.getString("cod", "");
        this.cod_g = this.sp.getString("cod_g", "");
        if (!this.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + this.c2)}));
            if (config.esClaro("#" + this.c1)) {
                ((TextView) findViewById(C0627R.id.message_text)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
            } else {
                ((TextView) findViewById(C0627R.id.message_text)).setTextColor(-1);
            }
        }
        if (!this.externo && !this.sp.getBoolean("chat" + this.idsecc + "_validado", true)) {
            preguntar_contra();
        } else if (savedInstanceState == null) {
            iniciar();
        }
    }

    void preguntar_contra() {
        this.adb = new Builder(this);
        this.adb.setTitle(getResources().getString(C0627R.string.chat_acceso));
        View eulaLayout = getLayoutInflater().inflate(C0627R.layout.contra, null);
        ((TextView) eulaLayout.findViewById(C0627R.id.message)).setText(getResources().getString(C0627R.string.chat_introduce_contra));
        this.et_contra = (EditText) eulaLayout.findViewById(C0627R.id.et_contra);
        if (this.contra_ko) {
            eulaLayout.findViewById(C0627R.id.message_error).setVisibility(0);
        }
        this.dontShowAgain = (CheckBox) eulaLayout.findViewById(C0627R.id.skip);
        if (!this.cbtn.equals("")) {
            boolean z;
            EditText editText = this.et_contra;
            if (config.esClaro("#" + this.c1)) {
                z = false;
            } else {
                z = true;
            }
            config.edittext_color(editText, Boolean.valueOf(z), this.cbtn);
            config.checkbox_color(this.dontShowAgain, this.cbtn);
        }
        this.dontShowAgain.setChecked(this.sp.getBoolean("chat" + this.idsecc + "_nomostrarmas_def", true));
        this.adb.setView(eulaLayout);
        this.adb.setCancelable(true);
        this.adb.setPositiveButton(getString(C0627R.string.aceptar), new C07831());
        this.adb.setNegativeButton(getString(C0627R.string.cancelar), new C07842());
        this.adb.setOnCancelListener(new C07853());
        final AlertDialog d_aux = this.adb.create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat_contra.this.cbtn));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + t_chat_contra.this.cbtn));
                }
            });
        }
        d_aux.show();
    }

    void iniciar() {
        Intent i;
        if (this.sp.getString("nick", "").equals("")) {
            if (this.globales.fb_modo == 2) {
                int i2;
                StringBuilder append = new StringBuilder().append("https://mysocialapp.net/").append(this.globales.dominio).append("/?u=").append(this.idusu).append("&cod_g=").append(this.cod_g).append("&c=").append(this.codigo).append("&a=s").append("&x=").append(this.sp.getString("x", "")).append("&y=").append(this.sp.getString("y", "")).append("&c1_w=").append(this.c1).append("&c2_w=").append(this.c2).append("&c1_c=");
                if (this.c1_esclaro) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                String url = append.append(i2).append("&idl=").append(Locale.getDefault().getLanguage()).toString();
                if (this.externo) {
                    url = config.completar_externo_url(url, this.extras);
                } else {
                    url = url + "&idsecc=" + this.idsecc + "&ind=" + this.ind;
                }
                if (this.es_root) {
                    url = url + "&es_root=" + this.es_root;
                }
                this.es_root = false;
                if (this.externo && this.extras.containsKey("tit_cab")) {
                    url = url + "&tit_cab=" + this.extras.getString("tit_cab");
                }
                i = new Intent(this, fb.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                this.finalizar_2 = true;
                startActivityForResult(i, 0);
                return;
            }
            i = new Intent(this, chat_perfil.class);
            if (this.externo) {
                i = config.completar_externo(i, this.extras);
            } else {
                i.putExtra("idsecc", this.idsecc);
            }
            if (this.es_root) {
                i.putExtra("es_root", this.es_root);
            }
            this.es_root = false;
            this.finalizar_2 = true;
            startActivityForResult(i, 0);
        } else if (((!(this.externo && this.extras.getInt("fotos_perfil") == 2) && (this.externo || this.globales.secciones_a[this.ind].fotos_perfil != 2)) || this.globales.getTempFile(this, 1).exists()) && (((!(this.externo && this.extras.getInt("fnac") == 2) && (this.externo || this.globales.secciones_a[this.ind].p_fnac != 2)) || !(this.sp.getInt("fnac_d", 0) == 0 || this.sp.getInt("fnac_m", 0) == 0 || this.sp.getInt("fnac_a", 0) == 0)) && (((!(this.externo && this.extras.getInt("sexo") == 2) && (this.externo || this.globales.secciones_a[this.ind].p_sexo != 2)) || this.sp.getInt("sexo", 0) != 0) && ((!(this.externo && this.extras.getInt("descr") == 2) && (this.externo || this.globales.secciones_a[this.ind].p_descr != 2)) || !this.sp.getString("descr", "").equals(""))))) {
            i = new Intent(this, t_chat.class);
            if (this.externo) {
                i = config.completar_externo(i, this.extras);
                if (this.extras.containsKey("tit_cab")) {
                    i.putExtra("tit_cab", this.extras.getString("tit_cab"));
                }
            } else {
                i.putExtra("idsecc", this.idsecc);
            }
            if (this.es_root) {
                i.putExtra("es_root", this.es_root);
            }
            this.es_root = false;
            if (!(this.extras == null || this.extras.getString("id_remit") == null)) {
                i.putExtra("id_remit", this.extras.getString("id_remit"));
                i.putExtra("nombre_remit", this.extras.getString("nombre_remit"));
                if (this.extras.containsKey("empezar_privado")) {
                    i.putExtra("empezar_privado", true);
                }
            }
            this.finalizar_2 = true;
            startActivityForResult(i, 0);
        } else {
            i = new Intent(this, preperfil.class);
            if (this.externo) {
                i = config.completar_externo(i, this.extras);
            } else {
                i.putExtra("idsecc", this.idsecc);
            }
            if (this.es_root) {
                i.putExtra("es_root", this.es_root);
            }
            this.es_root = false;
            this.finalizar_2 = true;
            startActivityForResult(i, 0);
        }
    }

    void mostrar_msg(int tipo) {
        TextView tv = (TextView) findViewById(C0627R.id.message_text);
        if (tipo == 0) {
            tv.setText(C0627R.string.chat_contra_necesaria);
        } else if (tipo == 1) {
            tv.setText(C0627R.string.error_http);
        }
        tv.setVisibility(0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean finalizar_aux = false;
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            finalizar_aux = true;
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
        }
        if (!finalizar_aux && this.finalizar_2) {
            finish();
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C07875());
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("finalizar_2", this.finalizar_2);
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
