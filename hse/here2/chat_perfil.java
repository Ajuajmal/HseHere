package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.plus.PlusShare;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpStatus;

public class chat_perfil extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private static final int SELECT_PHOTO = 2;
    private static final int TAKE_PHOTO_CODE = 1;
    AdView adView;
    boolean atras_pulsado = false;
    private ImageView btnfotos;
    private String c1;
    private String c2;
    Long captureTime;
    private String cbtn;
    private String cod_g;
    private String codigo;
    private int coments;
    private int descr;
    ProgressDialog dialog_cargando;
    ProgressDialog dialog_cargando_2;
    private ImageView elim1;
    boolean es_root;
    boolean externo;
    Bundle extras;
    boolean finalizar = false;
    private int fnac;
    private ImageView foto1;
    private String foto1_modif;
    private int fotos_perfil;
    private int galeria;
    config globales;
    private int idsecc;
    private int idusu;
    private int ind;
    InterstitialAd interstitial;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private int nfotos = 0;
    boolean nuevo;
    private int privados;
    private SharedPreferences settings;
    private int sexo;
    private Spinner spinner_a;
    private Spinner spinner_avisos;
    private Spinner spinner_coments;
    private Spinner spinner_d;
    private Spinner spinner_m;
    private Spinner spinner_privados;
    private Spinner spinner_sexo;
    View v_abrir_secc;

    class C06381 implements DialogInterface.OnClickListener {
        C06381() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

    class C06403 implements DialogInterface.OnClickListener {
        C06403() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent photoPickerIntent = new Intent("android.intent.action.PICK");
            photoPickerIntent.setType("image/*");
            chat_perfil.this.startActivityForResult(photoPickerIntent, 2);
        }
    }

    class C06414 implements DialogInterface.OnClickListener {
        C06414() {
        }

        public void onClick(DialogInterface dialog, int which) {
            chat_perfil.this.captureTime = Long.valueOf(System.currentTimeMillis());
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(chat_perfil.this.globales.getTempFile(chat_perfil.this, 1)));
            try {
                chat_perfil.this.startActivityForResult(intent, 1);
            } catch (ActivityNotFoundException e) {
            }
        }
    }

    class C06436 implements OnItemClickListener {
        C06436() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (chat_perfil.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(chat_perfil.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(chat_perfil.this.globales.menu_a_secciones[position]));
            chat_perfil.this.onClick(view);
        }
    }

    class C06447 implements DialogInterface.OnClickListener {
        C06447() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent i = new Intent(chat_perfil.this, eliminarperfil.class);
            if (chat_perfil.this.externo) {
                i = config.completar_externo(i, chat_perfil.this.extras);
            } else {
                i.putExtra("idsecc", chat_perfil.this.idsecc);
            }
            if (chat_perfil.this.globales.tipomenu != 2) {
                i.putExtra("es_root", true);
            }
            chat_perfil.this.es_root = false;
            chat_perfil.this.finalizar = true;
            Intent data = new Intent();
            data.putExtra("finalizar", true);
            chat_perfil.this.setResult(-1, data);
            chat_perfil.this.startActivity(i);
            chat_perfil.this.finish();
        }
    }

    private class cargarprivacidad extends AsyncTask<String, Void, Byte> {
        String result_http;

        class C06471 implements OnShowListener {
            C06471() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) chat_perfil.this.dialog_cargando_2.findViewById(16908301), chat_perfil.this.globales.c_icos);
            }
        }

        protected java.lang.Byte doInBackground(java.lang.String... r14) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x007b in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r13 = this;
            r12 = -1;
            r10 = new java.lang.StringBuilder;
            r10.<init>();
            r11 = "http://srv1.androidcreator.com/privacy.php?desde_app=1&ida=299914&idl=";
            r10 = r10.append(r11);
            r11 = java.util.Locale.getDefault();
            r11 = r11.getLanguage();
            r10 = r10.append(r11);
            r2 = r10.toString();
            r6 = 0;
            r7 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x007d }
            r7.<init>(r2);	 Catch:{ MalformedURLException -> 0x007d }
            r1 = 0;
            r10 = r7.openConnection();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r0 = r10;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r1 = r0;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = 1;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r1.setDoInput(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r1.setConnectTimeout(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r1.setReadTimeout(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = "User-Agent";	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r11 = "Android Vinebre Software";	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r1.setRequestProperty(r10, r11);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r4 = r1.getInputStream();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r8 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10.<init>(r4);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r8.<init>(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r9.<init>();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
        L_0x0053:
            r5 = r8.readLine();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            if (r5 == 0) goto L_0x0083;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
        L_0x0059:
            r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10.<init>();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = r10.append(r5);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r11 = "\n";	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = r10.append(r11);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = r10.toString();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r9.append(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            goto L_0x0053;
        L_0x0070:
            r3 = move-exception;
            r10 = -1;
            r10 = java.lang.Byte.valueOf(r10);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            if (r1 == 0) goto L_0x007b;
        L_0x0078:
            r1.disconnect();
        L_0x007b:
            r6 = r7;
        L_0x007c:
            return r10;
        L_0x007d:
            r3 = move-exception;
            r10 = java.lang.Byte.valueOf(r12);
            goto L_0x007c;
        L_0x0083:
            r10 = r9.toString();	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r13.result_http = r10;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = r13.result_http;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r11 = "@EURO@";	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r12 = "€";	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r10 = r10.replace(r11, r12);	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            r13.result_http = r10;	 Catch:{ Exception -> 0x0070, all -> 0x00a1 }
            if (r1 == 0) goto L_0x009a;
        L_0x0097:
            r1.disconnect();
        L_0x009a:
            r10 = 0;
            r10 = java.lang.Byte.valueOf(r10);
            r6 = r7;
            goto L_0x007c;
        L_0x00a1:
            r10 = move-exception;
            if (r1 == 0) goto L_0x00a7;
        L_0x00a4:
            r1.disconnect();
        L_0x00a7:
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.chat_perfil.cargarprivacidad.doInBackground(java.lang.String[]):java.lang.Byte");
        }

        private cargarprivacidad() {
        }

        protected void onPreExecute() {
            chat_perfil.this.dialog_cargando_2 = new ProgressDialog(chat_perfil.this);
            chat_perfil.this.dialog_cargando_2.setMessage(chat_perfil.this.getString(C0627R.string.cargando));
            chat_perfil.this.dialog_cargando_2.setIndeterminate(true);
            if (VERSION.SDK_INT > 20 && !chat_perfil.this.globales.c_icos.equals("")) {
                chat_perfil.this.dialog_cargando_2.setOnShowListener(new C06471());
            }
            chat_perfil.this.dialog_cargando_2.show();
        }

        protected void onPostExecute(Byte result) {
            try {
                chat_perfil.this.dialog_cargando_2.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() != (byte) 0) {
                chat_perfil.this.mostrar_error(chat_perfil.this.getString(C0627R.string.error_http_tit), chat_perfil.this.getString(C0627R.string.error_http));
                return;
            }
            final AlertDialog d_aux = new Builder(chat_perfil.this).setCancelable(true).setPositiveButton(chat_perfil.this.getString(C0627R.string.cerrar), null).setMessage(Html.fromHtml(this.result_http)).create();
            if (!chat_perfil.this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e2) {
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        int i;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.ind = this.globales.ind_secc_sel_2;
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        this.externo = this.extras.containsKey("externo");
        if (this.externo) {
            int i2;
            this.c1 = this.globales.secciones_a[this.ind].c1;
            this.c2 = this.globales.secciones_a[this.ind].c2;
            this.fotos_perfil = this.extras.getInt("fotos_perfil");
            this.fnac = this.extras.getInt("fnac");
            this.sexo = this.extras.getInt("sexo");
            this.descr = this.extras.getInt("descr");
            this.galeria = this.extras.getBoolean("galeria") ? 1 : 0;
            this.coments = this.extras.getBoolean("coments") ? 1 : 0;
            if (this.extras.getBoolean("privados")) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            this.privados = i2;
        } else {
            this.c1 = this.globales.secciones_a[this.ind].c1;
            this.c2 = this.globales.secciones_a[this.ind].c2;
            this.fotos_perfil = this.globales.secciones_a[this.ind].fotos_perfil;
            this.fnac = this.globales.secciones_a[this.ind].p_fnac;
            this.sexo = this.globales.secciones_a[this.ind].p_sexo;
            this.descr = this.globales.secciones_a[this.ind].p_descr;
            this.galeria = this.globales.secciones_a[this.ind].galeria ? 1 : 0;
            this.coments = this.globales.secciones_a[this.ind].coments ? 1 : 0;
            this.privados = this.globales.secciones_a[this.ind].privados ? 1 : 0;
            this.idsecc = this.extras.getInt("idsecc");
        }
        this.cbtn = config.aplicar_color_dialog(this.c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.c1)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.chat_perfil);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.codigo = this.settings.getString("cod", "");
        this.cod_g = this.settings.getString("cod_g", "");
        if (this.settings.getString("nick", "").equals("")) {
            this.nuevo = true;
        } else {
            this.nuevo = false;
        }
        if (!this.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + this.c2)}));
            int c = -1;
            if (config.esClaro("#" + this.c1)) {
                c = ViewCompat.MEASURED_STATE_MASK;
            }
            ((TextView) findViewById(C0627R.id.nombre)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.nombre_2)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.c_nombre_tv)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.descr)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.descr_2)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.fnac)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.fnac_2)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.sexo)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.sexo_2)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.c_sexo_tv)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.foto)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.foto_2)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_coments)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_permitirprivados)).setTextColor(c);
            ((TextView) findViewById(C0627R.id.tv_avisos)).setTextColor(c);
            if (VERSION.SDK_INT > 20) {
                config.edittext_color((EditText) findViewById(C0627R.id.c_nombre), Boolean.valueOf(!config.esClaro(new StringBuilder().append("#").append(this.c1).toString())), this.globales.c_ir_ofic);
                config.edittext_color((EditText) findViewById(C0627R.id.c_descr), Boolean.valueOf(!config.esClaro(new StringBuilder().append("#").append(this.c1).toString())), this.globales.c_ir_ofic);
            }
        }
        if (this.globales.fb_modo == 1 && !this.settings.getBoolean("logineado_fb", false)) {
            findViewById(C0627R.id.iv_fb).setVisibility(0);
            findViewById(C0627R.id.iv_fb).setOnClickListener(this);
            findViewById(C0627R.id.v_fb).setVisibility(0);
        }
        if (this.fotos_perfil == 0) {
            findViewById(C0627R.id.tr_foto).setVisibility(8);
        } else if (this.fotos_perfil == 2) {
            findViewById(C0627R.id.foto_2).setVisibility(8);
        }
        if (this.descr == 0) {
            findViewById(C0627R.id.tr_descr).setVisibility(8);
        } else if (this.descr == 2) {
            findViewById(C0627R.id.descr_2).setVisibility(8);
        }
        if (this.fnac == 0) {
            findViewById(C0627R.id.tr_fnac).setVisibility(8);
        } else if (this.fnac == 2) {
            findViewById(C0627R.id.fnac_2).setVisibility(8);
        }
        if (this.sexo == 0) {
            findViewById(C0627R.id.tr_sexo).setVisibility(8);
        } else if (this.sexo == 2) {
            findViewById(C0627R.id.sexo_2).setVisibility(8);
        }
        if (this.coments == 0) {
            findViewById(C0627R.id.tr_coments).setVisibility(8);
        } else {
            ((TextView) findViewById(C0627R.id.tv_coments)).setText(getResources().getString(C0627R.string.comentarios) + ":");
        }
        if (this.privados == 0) {
            findViewById(C0627R.id.tr_privados).setVisibility(8);
        }
        this.btnfotos = (ImageView) findViewById(C0627R.id.btnfotos);
        this.btnfotos.setOnClickListener(this);
        TextView btnenv = (TextView) findViewById(C0627R.id.btnenv);
        if (!this.globales.c_icos.equals("")) {
            btnenv.setBackgroundColor(Color.parseColor("#" + this.globales.c_icos));
            if (config.esClaro("#" + this.globales.c_icos)) {
                btnenv.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            }
        }
        btnenv.setOnClickListener(this);
        TextView btnprofile = (TextView) findViewById(C0627R.id.btnprofile);
        if (this.galeria == 1 || this.coments == 1) {
            if (this.galeria == 1 && this.coments == 1) {
                btnprofile.setText(getResources().getString(C0627R.string.imagenes).toUpperCase() + "/" + getResources().getString(C0627R.string.perfil).toUpperCase());
            } else if (this.galeria == 1 && this.coments == 0) {
                btnprofile.setText(getResources().getString(C0627R.string.imagenes).toUpperCase());
            } else if (this.galeria == 0 && this.coments == 1) {
                btnprofile.setText(getResources().getString(C0627R.string.comentarios).toUpperCase());
            }
            btnprofile.setOnClickListener(this);
        } else {
            btnprofile.setVisibility(8);
            findViewById(C0627R.id.btnprofile_sep).setVisibility(8);
        }
        TextView btnbaja = (TextView) findViewById(C0627R.id.btnbaja);
        if (this.nuevo) {
            btnbaja.setVisibility(8);
        } else {
            btnbaja.setOnClickListener(this);
            if (config.esClaro("#" + this.c2)) {
                btnbaja.setTextColor(config.GRIS_OSCURO);
            } else {
                btnbaja.setTextColor(config.GRIS_CLARO);
            }
        }
        TextView btnprivacidad = (TextView) findViewById(C0627R.id.btnprivacidad);
        if (this.globales.privacy_mostrar) {
            btnprivacidad.setOnClickListener(this);
            if (config.esClaro("#" + this.c2)) {
                btnprivacidad.setTextColor(config.GRIS_OSCURO);
            } else {
                btnprivacidad.setTextColor(config.GRIS_CLARO);
            }
        } else {
            btnprivacidad.setVisibility(8);
        }
        this.foto1 = (ImageView) findViewById(C0627R.id.foto1);
        this.elim1 = (ImageView) findViewById(C0627R.id.elim1);
        this.elim1.setOnClickListener(this);
        List<String> spinnerArray_d = new ArrayList();
        spinnerArray_d.add(getResources().getString(C0627R.string.dia));
        for (i = 1; i < 32; i++) {
            spinnerArray_d.add(i + "");
        }
        this.spinner_d = (Spinner) findViewById(C0627R.id.c_fnac_d);
        ArrayAdapter<String> adapter_d = new ArrayAdapter(this, 17367048, spinnerArray_d);
        adapter_d.setDropDownViewResource(17367049);
        this.spinner_d.setAdapter(adapter_d);
        List<String> spinnerArray_m = new ArrayList();
        spinnerArray_m.add(getResources().getString(C0627R.string.mes));
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        for (i = 0; i < 12; i++) {
            cal.set(2, i);
            String aux1 = cal.getDisplayName(2, 1, Locale.getDefault());
            try {
                aux1 = aux1.substring(0, 1).toUpperCase(Locale.getDefault()) + aux1.substring(1);
            } catch (Exception e) {
            }
            spinnerArray_m.add(aux1);
        }
        this.spinner_m = (Spinner) findViewById(C0627R.id.c_fnac_m);
        ArrayAdapter<String> adapter_m = new ArrayAdapter(this, 17367048, spinnerArray_m);
        adapter_m.setDropDownViewResource(17367049);
        this.spinner_m.setAdapter(adapter_m);
        List<String> spinnerArray_a = new ArrayList();
        spinnerArray_a.add(getResources().getString(C0627R.string.anyo));
        Calendar cal2 = Calendar.getInstance();
        for (i = 4; i < 100; i++) {
            spinnerArray_a.add((cal2.get(1) - i) + "");
        }
        this.spinner_a = (Spinner) findViewById(C0627R.id.c_fnac_a);
        ArrayAdapter<String> adapter_a = new ArrayAdapter(this, 17367048, spinnerArray_a);
        adapter_a.setDropDownViewResource(17367049);
        this.spinner_a.setAdapter(adapter_a);
        this.spinner_sexo = (Spinner) findViewById(C0627R.id.c_sexo);
        ArrayAdapter<CharSequence> adapter_sexo = ArrayAdapter.createFromResource(this, C0627R.array.sexo_a, 17367048);
        adapter_sexo.setDropDownViewResource(17367049);
        this.spinner_sexo.setAdapter(adapter_sexo);
        this.spinner_coments = (Spinner) findViewById(C0627R.id.c_coments);
        ArrayAdapter<CharSequence> adapter_coments = ArrayAdapter.createFromResource(this, C0627R.array.coments_a, 17367048);
        adapter_coments.setDropDownViewResource(17367049);
        this.spinner_coments.setAdapter(adapter_coments);
        this.spinner_privados = (Spinner) findViewById(C0627R.id.c_privados);
        ArrayAdapter<CharSequence> adapter_privados = ArrayAdapter.createFromResource(this, C0627R.array.privados_a, 17367048);
        adapter_privados.setDropDownViewResource(17367049);
        this.spinner_privados.setAdapter(adapter_privados);
        this.spinner_avisos = (Spinner) findViewById(C0627R.id.c_avisos);
        ArrayAdapter<CharSequence> adapter_avisos = ArrayAdapter.createFromResource(this, C0627R.array.avisos_a, 17367048);
        adapter_avisos.setDropDownViewResource(17367049);
        this.spinner_avisos.setAdapter(adapter_avisos);
        this.spinner_d.setSelection(this.settings.getInt("fnac_d", 0));
        this.spinner_m.setSelection(this.settings.getInt("fnac_m", 0));
        int pos_aux = adapter_a.getPosition(this.settings.getInt("fnac_a", 0) + "");
        if (pos_aux == -1) {
            this.spinner_a.setSelection(0);
        } else {
            this.spinner_a.setSelection(pos_aux);
        }
        this.spinner_sexo.setSelection(ArrayAdapter.createFromResource(this, C0627R.array.idsexo_a, 17367048).getPosition(this.settings.getInt("sexo", 0) + ""));
        ((TextView) findViewById(C0627R.id.c_sexo_tv)).setText("  " + this.spinner_sexo.getSelectedItem().toString());
        this.spinner_coments.setSelection(ArrayAdapter.createFromResource(this, C0627R.array.idcoments_a, 17367048).getPosition(this.settings.getInt("coments", 1) + ""));
        this.spinner_privados.setSelection(ArrayAdapter.createFromResource(this, C0627R.array.idprivados_a, 17367048).getPosition(this.settings.getInt("privados", 1) + ""));
        this.spinner_avisos.setSelection(ArrayAdapter.createFromResource(this, C0627R.array.idavisos_a, 17367048).getPosition(this.settings.getInt("avisos", 1) + ""));
        ((TextView) findViewById(C0627R.id.c_nombre)).setText(this.settings.getString("nick", ""));
        ((TextView) findViewById(C0627R.id.c_nombre_tv)).setText("  " + this.settings.getString("nick", ""));
        ((TextView) findViewById(C0627R.id.c_descr)).setText(this.settings.getString("descr", ""));
        if (this.globales.fb_modo > 0 && this.globales.fb_bloqdatos && this.settings.getBoolean("logineado_fb", false)) {
            findViewById(C0627R.id.nombre_2).setVisibility(8);
            findViewById(C0627R.id.c_nombre).setVisibility(8);
            findViewById(C0627R.id.c_nombre_tv).setVisibility(0);
            if (this.spinner_sexo.getSelectedItemPosition() > 0) {
                findViewById(C0627R.id.sexo_2).setVisibility(8);
                findViewById(C0627R.id.c_sexo).setVisibility(8);
                findViewById(C0627R.id.c_sexo_tv).setVisibility(0);
            }
            if (this.globales.getTempFile(this, 1).exists()) {
                findViewById(C0627R.id.foto_2).setVisibility(8);
                findViewById(C0627R.id.elim1).setVisibility(8);
                findViewById(C0627R.id.btnfotos).setVisibility(8);
            } else {
                findViewById(C0627R.id.tr_foto).setVisibility(8);
            }
        }
        this.foto1_modif = "0";
        if (savedInstanceState != null && savedInstanceState.containsKey("foto1_modif_saved")) {
            this.foto1_modif = savedInstanceState.getString("foto1_modif_saved");
        }
        if (this.globales.getTempFile(this, 1).exists()) {
            mostrar_foto_p(1, true);
            if (this.nuevo) {
                this.foto1_modif = "1";
            }
        }
        this.nfotos = contar_fotos();
        if (this.extras.getBoolean("nocompletar", false)) {
            getWindow().setSoftInputMode(2);
            return;
        }
        AlertDialog d_aux = new Builder(this).setCancelable(false).setPositiveButton(getString(C0627R.string.aceptar), new C06381()).setMessage(C0627R.string.completar_perfil).create();
        if (!this.cbtn.equals("")) {
            final AlertDialog alertDialog = d_aux;
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    alertDialog.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e2) {
        }
    }

    private int contar_fotos() {
        if (this.globales.getTempFile(this, 1).exists()) {
            return 0 + 1;
        }
        return 0;
    }

    private void tomar_foto() {
        this.foto1_modif = "1";
        final AlertDialog d_aux = new Builder(this).setCancelable(true).setPositiveButton(getString(C0627R.string.tomardecamara), new C06414()).setNegativeButton(getString(C0627R.string.tomardesd), new C06403()).setMessage(C0627R.string.tomarfoto).create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        Matrix matrix;
        int orientation;
        if (requestCode == 1) {
            Bitmap bm;
            int rotation = -1;
            try {
                long fileSize = new File(this.globales.getTempFile(this, 1).getAbsolutePath()).length();
                Cursor mediaCursor = getApplicationContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"orientation", "_size"}, "date_added>=?", new String[]{String.valueOf((this.captureTime.longValue() / 1000) - 1)}, "date_added desc");
                if (mediaCursor != null && this.captureTime.longValue() != 0 && mediaCursor.getCount() != 0) {
                    while (mediaCursor.moveToNext()) {
                        if (mediaCursor.getLong(1) == fileSize) {
                            rotation = mediaCursor.getInt(0);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rotation == -1) {
                try {
                    rotation = new ExifInterface(this.globales.getTempFile(this, 1).getAbsolutePath()).getAttributeInt("Orientation", -1);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            try {
                int width;
                int height;
                File file = this.globales.getTempFile(this, 1);
                Options bounds = new Options();
                bounds.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), bounds);
                if (bounds.outWidth == -1) {
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                } else {
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                }
                boolean withinBounds = width <= 300 && height <= HttpStatus.SC_BAD_REQUEST;
                if (withinBounds) {
                    bm = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 300, HttpStatus.SC_BAD_REQUEST)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    bm = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                try {
                    bm.compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile(this, 1)));
                } catch (Exception e3) {
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            if (rotation != -1) {
                matrix = new Matrix();
                orientation = 0;
                if (rotation == 3) {
                    orientation = 180;
                } else if (rotation == 8) {
                    orientation = 270;
                } else if (rotation == 6) {
                    orientation = 90;
                }
                if (orientation > 0) {
                    try {
                        bm = BitmapFactory.decodeFile(this.globales.getTempFile(this, 1).getAbsolutePath());
                        matrix.postRotate((float) orientation);
                        try {
                            Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true).compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile(this, 1)));
                        } catch (Exception e5) {
                        }
                    } catch (Exception e42) {
                        e42.printStackTrace();
                    }
                }
            }
            mostrar_foto_p(1, false);
            this.nfotos = contar_fotos();
        } else if (requestCode == 2) {
            Uri selectedImage = data.getData();
            try {
                String[] orientationColumn = new String[]{"orientation"};
                Cursor cur = getContentResolver().query(selectedImage, orientationColumn, null, null, null);
                orientation = -1;
                if (cur != null && cur.moveToFirst()) {
                    orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                }
                matrix = new Matrix();
                matrix.postRotate((float) orientation);
                Bitmap bmImg = this.globales.decodeSampledBitmapFromResource(selectedImage, 300, HttpStatus.SC_BAD_REQUEST);
                bmImg = Bitmap.createBitmap(bmImg, 0, 0, bmImg.getWidth(), bmImg.getHeight(), matrix, true);
                try {
                    bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile(this, 1)));
                } catch (Exception e6) {
                }
                bmImg.recycle();
                mostrar_foto_p(1, false);
                this.nfotos = contar_fotos();
            } catch (Exception e7) {
            }
        } else if (data != null) {
            if (data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
                if (!data.getExtras().getBoolean("finalizar_app")) {
                    this.es_root = false;
                }
                setResult(-1, data);
                finish();
            }
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C06436());
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
        int i = 1;
        final AlertDialog d_aux;
        if (v.getId() == C0627R.id.btnbaja) {
            d_aux = new Builder(this).setCancelable(true).setPositiveButton(getString(C0627R.string.si), new C06447()).setNegativeButton(getString(C0627R.string.no), null).setMessage(C0627R.string.confirmarbaja).create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        d_aux.getButton(-2).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e) {
            }
        } else if (v.getId() == C0627R.id.btnprivacidad) {
            new cargarprivacidad().execute(new String[0]);
        } else if (v.getId() == C0627R.id.btnfotos) {
            tomar_foto();
        } else if (v.getId() == C0627R.id.elim1) {
            this.foto1.setImageBitmap(null);
            this.foto1.setVisibility(8);
            this.elim1.setVisibility(8);
            this.globales.getTempFile(this, 1).delete();
            this.nfotos--;
            this.foto1_modif = "1";
            if (this.nfotos < 4) {
                this.btnfotos.setVisibility(0);
            }
        } else if (v.getId() == C0627R.id.btnenv) {
            Builder builder = new Builder(this);
            builder.setCancelable(false).setPositiveButton(getString(C0627R.string.aceptar), null);
            if (((TextView) findViewById(C0627R.id.c_nombre)).getText().toString().trim().length() < 3) {
                builder.setMessage(C0627R.string.falta_nombre);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            } else if (((TextView) findViewById(C0627R.id.c_descr)).getText().toString().trim().length() < 3 && this.descr == 2) {
                builder.setMessage(C0627R.string.falta_descr);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e3) {
                }
            } else if ((this.spinner_d.getSelectedItemPosition() == 0 || this.spinner_m.getSelectedItemPosition() == 0 || this.spinner_a.getSelectedItemPosition() == 0) && this.fnac == 2) {
                builder.setMessage(C0627R.string.falta_fnac);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e4) {
                }
            } else if (this.spinner_sexo.getSelectedItemPosition() == 0 && this.sexo == 2) {
                builder.setMessage(C0627R.string.falta_sexo);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e5) {
                }
            } else if (this.nfotos == 0 && this.fotos_perfil == 2) {
                builder.setMessage(C0627R.string.falta_fotos);
                d_aux = builder.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e6) {
                }
            } else {
                Editor editor = this.settings.edit();
                editor.putString("nick", ((TextView) findViewById(C0627R.id.c_nombre)).getText().toString().trim());
                editor.putString("descr", ((TextView) findViewById(C0627R.id.c_descr)).getText().toString().trim());
                editor.putInt("privados", Integer.parseInt(getResources().getStringArray(C0627R.array.idprivados_a)[this.spinner_privados.getSelectedItemPosition()]));
                editor.putInt("fnac_d", this.spinner_d.getSelectedItemPosition());
                editor.putInt("fnac_m", this.spinner_m.getSelectedItemPosition());
                if (this.spinner_a.getSelectedItemPosition() == 0) {
                    editor.putInt("fnac_a", 0);
                } else {
                    editor.putInt("fnac_a", Integer.parseInt(this.spinner_a.getSelectedItem().toString()));
                }
                editor.putInt("sexo", Integer.parseInt(getResources().getStringArray(C0627R.array.idsexo_a)[this.spinner_sexo.getSelectedItemPosition()]));
                editor.putInt("coments", Integer.parseInt(getResources().getStringArray(C0627R.array.idcoments_a)[this.spinner_coments.getSelectedItemPosition()]));
                editor.putInt("avisos", Integer.parseInt(getResources().getStringArray(C0627R.array.idavisos_a)[this.spinner_avisos.getSelectedItemPosition()]));
                Intent intent;
                Intent data;
                if (this.nuevo) {
                    editor.putString("foto1_modif", this.foto1_modif);
                    editor.commit();
                    intent = new Intent(this, guardarprimeravez.class);
                    if (this.externo) {
                        intent = config.completar_externo(intent, this.extras);
                    } else {
                        intent.putExtra("idsecc", this.idsecc);
                        intent.putExtra("desde_buscusus", this.extras.getBoolean("desde_buscusus", false));
                    }
                    if (this.globales.tipomenu != 2) {
                        intent.putExtra("es_root", true);
                    }
                    this.es_root = false;
                    this.finalizar = true;
                    data = new Intent();
                    data.putExtra("finalizar", true);
                    setResult(-1, data);
                    startActivity(intent);
                    finish();
                } else {
                    editor.commit();
                    intent = new Intent(this, s_guardarperfil.class);
                    intent.putExtra("foto1_modif", this.foto1_modif);
                    startService(intent);
                    if (this.extras.getBoolean("desde_buscusus", false)) {
                        intent = new Intent(this, t_buscusus.class);
                    } else {
                        intent = new Intent(this, t_chat.class);
                    }
                    intent.putExtra("desdeperfil", 1);
                    if (this.externo) {
                        intent = config.completar_externo(intent, this.extras);
                    } else {
                        intent.putExtra("idsecc", this.idsecc);
                    }
                    if (this.globales.tipomenu != 2) {
                        intent.putExtra("es_root", true);
                    }
                    this.es_root = false;
                    this.finalizar = true;
                    data = new Intent();
                    data.putExtra("finalizar", true);
                    setResult(-1, data);
                    startActivity(intent);
                    finish();
                }
                finish();
            }
        } else if (v.getId() == C0627R.id.btnprofile) {
            if (this.nuevo) {
                d_aux = new Builder(this).setCancelable(false).setPositiveButton(getString(C0627R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setMessage(C0627R.string.guardaprimero).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    return;
                } catch (Exception e7) {
                    return;
                }
            }
            boolean z;
            i = new Intent(this, profile.class);
            i.putExtra("id", this.settings.getInt("idusu", 0) + "");
            i.putExtra("privados", "0");
            i.putExtra("nombre", this.settings.getString("nick", ""));
            i.putExtra("coments", this.settings.getInt("coments", 1) + "");
            i.putExtra("fnac_d", this.settings.getInt("fnac_d", 0) + "");
            i.putExtra("fnac_m", this.settings.getInt("fnac_m", 0) + "");
            i.putExtra("fnac_a", this.settings.getInt("fnac_a", 0) + "");
            i.putExtra("sexo", this.settings.getInt("sexo", 0) + "");
            i.putExtra("vfoto", "99999999");
            i.putExtra("dist", "0");
            i.putExtra("p_fnac", this.fnac);
            i.putExtra("p_sexo", this.sexo);
            i.putExtra("p_descr", this.descr);
            i.putExtra("p_dist", 0);
            i.putExtra("coments_chat", this.coments == 1);
            String str = "galeria";
            if (this.galeria == 1) {
                z = true;
            } else {
                z = false;
            }
            i.putExtra(str, z);
            str = "privados_chat";
            if (this.privados == 1) {
                z = true;
            } else {
                z = false;
            }
            i.putExtra(str, z);
            i.putExtra("fotos_perfil", this.fotos_perfil);
            i.putExtra("fotos_chat", 1);
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.iv_fb) {
            StringBuilder append = new StringBuilder().append("https://mysocialapp.net/").append(this.globales.dominio).append("/?u=").append(this.idusu).append("&cod_g=").append(this.cod_g).append("&c=").append(this.codigo).append("&a=s").append("&x=").append(this.settings.getString("x", "")).append("&y=").append(this.settings.getString("y", "")).append("&c1_w=").append(this.c1).append("&c2_w=").append(this.c2).append("&c1_c=");
            if (!config.esClaro("#" + this.c1)) {
                i = 0;
            }
            String url = append.append(i).append("&idl=").append(Locale.getDefault().getLanguage()).toString();
            if (this.externo) {
                url = config.completar_externo_url(url, this.extras);
            } else {
                url = url + "&idsecc=" + this.idsecc + "&ind=" + this.ind;
                if (this.extras.getBoolean("desde_buscusus", false)) {
                    url = url + "&desde_buscusus=true";
                }
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

    private void mostrar_foto_p(int nfoto, boolean desdeini) {
        File file = this.globales.getTempFile(this, nfoto);
        try {
            int width;
            int height;
            Bitmap captureBmp;
            Options bounds = new Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getPath(), bounds);
            if (bounds.outWidth == -1) {
                width = bounds.outWidth;
                height = bounds.outHeight;
            } else {
                width = bounds.outWidth;
                height = bounds.outHeight;
            }
            boolean withinBounds = width <= 200 && height <= 200;
            if (withinBounds) {
                captureBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
            } else {
                int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 200, 200)));
                Options resample = new Options();
                resample.inSampleSize = sampleSize;
                captureBmp = BitmapFactory.decodeFile(file.getPath(), resample);
            }
            ImageView iv_foto = null;
            ImageView iv_elim = null;
            if (nfoto == 1) {
                iv_foto = this.foto1;
                iv_elim = this.elim1;
                if (!desdeini) {
                    this.foto1_modif = "1";
                }
            }
            iv_foto.setImageBitmap(captureBmp);
            iv_foto.setVisibility(0);
            if (!(this.globales.fb_modo > 0 && this.globales.fb_bloqdatos && this.settings.getBoolean("logineado_fb", false))) {
                iv_elim.setVisibility(0);
            }
            iv_foto.setBackgroundColor(0);
        } catch (FileNotFoundException e) {
        } catch (IOException e2) {
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

    void mostrar_error(String titulo, String texto) {
        Builder builder = new Builder(this);
        builder.setCancelable(true).setPositiveButton(getString(C0627R.string.cerrar), null).setMessage(texto);
        if (!titulo.equals("")) {
            builder.setTitle(titulo);
        }
        final AlertDialog d_aux = builder.create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + chat_perfil.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("foto1_modif_saved", this.foto1_modif);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
