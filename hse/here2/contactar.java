package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class contactar extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    Button boton;
    Button boton2;
    private String cbtn;
    ProgressDialog dialog_cargando;
    private ProgressDialog dialog_enviando;
    private String email;
    boolean es_root;
    boolean finalizar = false;
    config globales;
    int idofic;
    int idusu;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private String nombre;
    private String observ;
    int result;
    private String telefono;
    View v_abrir_secc;

    class C06617 implements OnShowListener {
        C06617() {
        }

        public void onShow(DialogInterface dialog) {
            config.progress_color((ProgressBar) contactar.this.dialog_enviando.findViewById(16908301), contactar.this.globales.c_ir_ofic);
        }
    }

    class C06628 implements OnItemClickListener {
        C06628() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (contactar.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(contactar.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(contactar.this.globales.menu_a_secciones[position]));
            contactar.this.onClick(view);
        }
    }

    private class enviarTask extends AsyncTask<String, Void, Byte> {

        class C06631 implements DialogInterface.OnClickListener {
            C06631() {
            }

            public void onClick(DialogInterface dialog, int id) {
                contactar.this.finish();
            }
        }

        private enviarTask() {
        }

        protected Byte doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://srv1.androidcreator.com/srv/enviar_petic.php?v=1&idapp=299914&idusu=" + contactar.this.idusu + "&idofic=" + contactar.this.idofic);
            String line = "";
            StringBuilder total = new StringBuilder();
            try {
                List<NameValuePair> nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("nombre", contactar.this.nombre));
                nameValuePairs.add(new BasicNameValuePair("email", contactar.this.email));
                nameValuePairs.add(new BasicNameValuePair("tel", contactar.this.telefono));
                nameValuePairs.add(new BasicNameValuePair("observ", contactar.this.observ));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
                return Byte.valueOf((byte) 2);
            } catch (ClientProtocolException e) {
                return Byte.valueOf((byte) 2);
            } catch (IOException e2) {
                return Byte.valueOf((byte) 2);
            }
        }

        protected void onPostExecute(Byte result) {
            try {
                contactar.this.dialog_enviando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) 1) {
                final AlertDialog dialog = new Builder(contactar.this).setCancelable(false).setPositiveButton(C0627R.string.aceptar, new C06631()).setMessage(C0627R.string.mensaje_enviado).create();
                if (!contactar.this.cbtn.equals("")) {
                    dialog.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            dialog.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                dialog.show();
                try {
                    ((TextView) dialog.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    return;
                } catch (Exception e2) {
                    return;
                }
            }
            contactar.this.mostrar_error();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        Drawable d;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.cbtn = config.aplicar_color_dialog(this.globales.c1_ofic, this.globales.c_ir_ofic);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.c1_ofic)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.contactar);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            this.globales.toca_int(this, false);
        }
        this.adView = this.globales.mostrar_banner(this, false);
        Bundle extras = getIntent().getExtras();
        boolean z;
        if (savedInstanceState == null) {
            z = extras != null && extras.containsKey("es_root") && extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        this.idofic = extras.getInt("idofic");
        this.idusu = getSharedPreferences("sh", 0).getInt("idusu", 0);
        if (!this.globales.c_tit_ofic.equals("")) {
            ((TextView) findViewById(C0627R.id.tv_tit)).setTextColor(Color.parseColor("#" + this.globales.c_tit_ofic));
        }
        if (!this.globales.c_sep_ofic.equals("")) {
            findViewById(C0627R.id.v_sep).setBackgroundColor(Color.parseColor("#" + this.globales.c_sep_ofic));
        }
        if (!this.globales.c_ico_sep_ofic.equals("")) {
            d = getResources().getDrawable(C0627R.drawable.email_contactar);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_ico_sep_ofic), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_email)).setImageDrawable(d);
        }
        if (!this.globales.c_txt_ofic.equals("")) {
            int c = Color.parseColor("#" + this.globales.c_txt_ofic);
            ((TextView) findViewById(C0627R.id.tv_to_lit)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_to)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_nombre)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.opcional_nombre)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_email)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.opcional_email)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_tel)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.opcional_tel)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_msg)).setTextColor(c);
            if (VERSION.SDK_INT > 20) {
                config.edittext_color((EditText) findViewById(C0627R.id.c_nombre), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_ofic)), this.globales.c_ir_ofic);
                config.edittext_color((EditText) findViewById(C0627R.id.c_email), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_ofic)), this.globales.c_ir_ofic);
                config.edittext_color((EditText) findViewById(C0627R.id.c_telefono), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_ofic)), this.globales.c_ir_ofic);
                config.edittext_color((EditText) findViewById(C0627R.id.c_observ), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_ofic)), this.globales.c_ir_ofic);
            }
        }
        if (config.esClaro("#" + this.globales.c_ir_ofic)) {
            findViewById(C0627R.id.iv_btn_fondo_n).setVisibility(0);
        } else {
            findViewById(C0627R.id.iv_btn_fondo_b).setVisibility(0);
        }
        d = getResources().getDrawable(C0627R.drawable.enviar_btn);
        d.setColorFilter(Color.parseColor("#" + this.globales.c_ir_ofic), Mode.MULTIPLY);
        ImageView iv_enviar = (ImageView) findViewById(C0627R.id.button1);
        iv_enviar.setImageDrawable(d);
        iv_enviar.setOnClickListener(this);
        int i = 0;
        while (i < this.globales.oficinas_a.length) {
            if (this.globales.oficinas_a[i].id == this.idofic && this.globales.oficinas_a[i].t_email && !this.globales.oficinas_a[i].email.equals("")) {
                ((TextView) findViewById(C0627R.id.tv_to)).setText(this.globales.oficinas_a[i].email);
                findViewById(C0627R.id.tr_to).setVisibility(0);
                break;
            }
            i++;
        }
        if (this.globales.petic_ask_nombre > 0) {
            if (this.globales.petic_ask_nombre == 1) {
                findViewById(C0627R.id.opcional_nombre).setVisibility(0);
            }
            findViewById(C0627R.id.tr_nombre).setVisibility(0);
        }
        if (this.globales.petic_ask_email > 0) {
            if (this.globales.petic_ask_email == 1) {
                findViewById(C0627R.id.opcional_email).setVisibility(0);
            }
            findViewById(C0627R.id.tr_email).setVisibility(0);
        }
        if (this.globales.petic_ask_tel > 0) {
            if (this.globales.petic_ask_tel == 1) {
                findViewById(C0627R.id.opcional_tel).setVisibility(0);
            }
            findViewById(C0627R.id.tr_tel).setVisibility(0);
        }
        if (extras.containsKey("msg_predefinido")) {
            ((EditText) findViewById(C0627R.id.c_observ)).setText(getResources().getString(C0627R.string.masinfo_sobre) + " " + extras.getString("msg_predefinido"));
        }
        if (!this.globales.c1_ofic.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_ofic), Color.parseColor("#" + this.globales.c2_ofic)}));
        }
    }

    void mostrar_error() {
        final AlertDialog d_aux = new Builder(this).setCancelable(false).setPositiveButton(C0627R.string.cerrar, null).setMessage(C0627R.string.error_http).create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.button1) {
            Builder builder = new Builder(this);
            builder.setCancelable(false).setPositiveButton(getResources().getString(C0627R.string.cerrar), null);
            this.nombre = ((TextView) findViewById(C0627R.id.c_nombre)).getText().toString();
            this.email = ((TextView) findViewById(C0627R.id.c_email)).getText().toString();
            this.telefono = ((TextView) findViewById(C0627R.id.c_telefono)).getText().toString();
            this.observ = ((TextView) findViewById(C0627R.id.c_observ)).getText().toString();
            final AlertDialog d_aux;
            if (this.nombre.length() == 0 && this.globales.petic_ask_nombre == 2) {
                builder.setMessage(C0627R.string.falta_nombre);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e) {
                }
            } else if (this.email.length() == 0 && this.globales.petic_ask_email == 2) {
                builder.setMessage(C0627R.string.falta_email);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            } else if (this.telefono.length() == 0 && this.globales.petic_ask_tel == 2) {
                builder.setMessage(C0627R.string.falta_tel);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e3) {
                }
            } else if (this.observ.length() == 0) {
                builder.setMessage(C0627R.string.falta_msg);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e4) {
                }
            } else if (this.email.length() <= 0 || this.email.contains("@")) {
                this.dialog_enviando = new ProgressDialog(this);
                this.dialog_enviando.setMessage(getString(C0627R.string.enviando));
                this.dialog_enviando.setIndeterminate(true);
                if (VERSION.SDK_INT > 20) {
                    this.dialog_enviando.setOnShowListener(new C06617());
                }
                this.dialog_enviando.show();
                new enviarTask().execute(new String[0]);
            } else {
                builder.setMessage(C0627R.string.email_nook);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + contactar.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e5) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
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

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C06628());
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
