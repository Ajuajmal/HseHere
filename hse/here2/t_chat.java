package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.ViewCompat;
import android.text.Selection;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import hse.here2.config.modif_usuchat;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class t_chat extends Activity implements OnKeyListener, OnClickListener, OnSharedPreferenceChangeListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private static final int SELECT_PHOTO = 2;
    private static final int TAKE_PHOTO_CODE = 1;
    private int TV_MARGIN;
    private int TV_PADDING;
    private int WIDTH_IMGS;
    Map<String, String> acargar_m;
    public Activity acti;
    private AdView adView;
    private int altura;
    boolean atras_pulsado = false;
    Bitmap bm_propia;
    boolean c1_esclaro;
    boolean c2_esclaro;
    int c_activ;
    long captureTime;
    private String cbtn;
    private Handler closeHandler;
    private String codigo;
    private String codigo_global;
    boolean coments;
    private AlertDialog d_confirm;
    private Dialog d_solic_privado;
    private int descr;
    private String dia_act;
    ProgressDialog dialog_cargando;
    private int dist;
    enviar env;
    private boolean es_privado;
    boolean es_root;
    boolean externo;
    Bundle extras;
    boolean fav;
    boolean finalizar = false;
    private Handler finishHandler;
    private int fnac;
    private int fotos_chat;
    Map<String, Bitmap> fotos_m;
    private int fotos_perfil;
    boolean galeria;
    private config globales;
    private String id_remit_global;
    private int idchat;
    private int idfrase_masantigua_glob;
    private int idsecc;
    private int idtema;
    private int idusu;
    private int idusu_global;
    private String imgs_acargar;
    Map<String, String> intentados_m;
    ImageView iv_notif_noactiv;
    boolean ll_cabe_mostrar;
    LinearLayout llchat;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    private int nfrases;
    private String nombre_remit_global;
    boolean notif;
    obtener_foto o_f;
    obtener_img o_i;
    obtener_img_g o_i_g;
    File path;
    ProgressBar pb_ant;
    ProgressBar pb_enviando;
    private ProgressDialog pd;
    private ProgressDialog pd_2;
    boolean privados;
    float scale;
    Seccion secc_act;
    private SharedPreferences settings;
    private SharedPreferences settings2;
    private int sexo;
    View v_abrir_secc;
    private View v_sel;

    class C07632 implements OnClickListener {
        C07632() {
        }

        public void onClick(View v) {
            t_chat.this.finish();
        }
    }

    class C07643 implements OnClickListener {
        C07643() {
        }

        public void onClick(View v) {
            t_chat.this.finish();
        }
    }

    class C07654 implements OnClickListener {
        C07654() {
        }

        public void onClick(View v) {
            t_chat.this.ir_a_externo();
        }
    }

    class C07665 implements OnClickListener {
        C07665() {
        }

        public void onClick(View v) {
            t_chat.this.ir_a_externo();
        }
    }

    class C07676 implements OnClickListener {
        C07676() {
        }

        public void onClick(View v) {
            int modo;
            if (t_chat.this.fav) {
                if (t_chat.this.c1_esclaro) {
                    ((ImageView) v).setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.fav_noactiv));
                } else {
                    ((ImageView) v).setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.fav_noactiv_light));
                }
                t_chat.this.fav = false;
                modo = 0;
            } else {
                Drawable d = t_chat.this.getResources().getDrawable(C0627R.drawable.fav_blanco);
                d.setColorFilter(t_chat.this.c_activ, Mode.MULTIPLY);
                ((ImageView) v).setImageDrawable(d);
                t_chat.this.fav = true;
                modo = 1;
            }
            Editor e = t_chat.this.settings.edit();
            e.putBoolean("fav" + t_chat.this.idchat, t_chat.this.fav);
            e.commit();
            new modif_usuchat(t_chat.this.idusu, t_chat.this.codigo, t_chat.this.idchat, "fav", modo).execute(new String[0]);
        }
    }

    class C07687 implements OnClickListener {
        C07687() {
        }

        public void onClick(View v) {
            int modo;
            if (t_chat.this.notif) {
                if (t_chat.this.c1_esclaro) {
                    ((ImageView) v).setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.notif_noactiv));
                } else {
                    ((ImageView) v).setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.notif_noactiv_light));
                }
                t_chat.this.notif = false;
                modo = 0;
            } else {
                Drawable d = t_chat.this.getResources().getDrawable(C0627R.drawable.notif_blanco);
                d.setColorFilter(t_chat.this.c_activ, Mode.MULTIPLY);
                ((ImageView) v).setImageDrawable(d);
                t_chat.this.notif = true;
                modo = 1;
            }
            Editor e = t_chat.this.settings.edit();
            e.putBoolean("notif" + t_chat.this.idchat, t_chat.this.notif);
            e.commit();
            new modif_usuchat(t_chat.this.idusu, t_chat.this.codigo, t_chat.this.idchat, "notif", modo).execute(new String[0]);
        }
    }

    class C07708 extends Handler {
        C07708() {
        }

        public void handleMessage(Message msg) {
            if (t_chat.this.pd != null && t_chat.this.pd.isShowing()) {
                try {
                    t_chat.this.pd.dismiss();
                } catch (Exception e) {
                }
                final AlertDialog d_aux = new Builder(t_chat.this).setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.sincontesta_esperandopriv).create();
                if (!t_chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
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
    }

    class C07719 extends Handler {
        C07719() {
        }

        public void handleMessage(Message msg) {
            t_chat.this.finalizar();
            try {
                t_chat.this.acti.finish();
            } catch (Exception e) {
            }
        }
    }

    private class EmoticonesMenu extends Dialog implements OnClickListener {
        private EditText et;

        public EmoticonesMenu(EditText et) {
            super(t_chat.this);
            this.et = et;
        }

        protected void onStart() {
            super.onStart();
            setTitle(t_chat.this.getResources().getString(C0627R.string.selecciona));
            setContentView(C0627R.layout.emoticones);
            ImageButton btnSmile2 = (ImageButton) findViewById(C0627R.id.btn_smile2);
            ImageButton btnSmile3 = (ImageButton) findViewById(C0627R.id.btn_smile3);
            ImageButton btnSmile4 = (ImageButton) findViewById(C0627R.id.btn_smile4);
            ImageButton btnSmile5 = (ImageButton) findViewById(C0627R.id.btn_smile5);
            ImageButton btnSmile6 = (ImageButton) findViewById(C0627R.id.btn_smile6);
            ImageButton btnSmile7 = (ImageButton) findViewById(C0627R.id.btn_smile7);
            ImageButton btnSmile8 = (ImageButton) findViewById(C0627R.id.btn_smile8);
            ImageButton btnSmile9 = (ImageButton) findViewById(C0627R.id.btn_smile9);
            ImageButton btnSmile10 = (ImageButton) findViewById(C0627R.id.btn_smile10);
            ImageButton btnSmile11 = (ImageButton) findViewById(C0627R.id.btn_smile11);
            ImageButton btnSmile12 = (ImageButton) findViewById(C0627R.id.btn_smile12);
            ImageButton btnSmile13 = (ImageButton) findViewById(C0627R.id.btn_smile13);
            ImageButton btnSmile14 = (ImageButton) findViewById(C0627R.id.btn_smile14);
            ImageButton btnSmile15 = (ImageButton) findViewById(C0627R.id.btn_smile15);
            ((ImageButton) findViewById(C0627R.id.btn_smile1)).setOnClickListener(this);
            btnSmile2.setOnClickListener(this);
            btnSmile3.setOnClickListener(this);
            btnSmile4.setOnClickListener(this);
            btnSmile5.setOnClickListener(this);
            btnSmile6.setOnClickListener(this);
            btnSmile7.setOnClickListener(this);
            btnSmile8.setOnClickListener(this);
            btnSmile9.setOnClickListener(this);
            btnSmile10.setOnClickListener(this);
            btnSmile11.setOnClickListener(this);
            btnSmile12.setOnClickListener(this);
            btnSmile13.setOnClickListener(this);
            btnSmile14.setOnClickListener(this);
            btnSmile15.setOnClickListener(this);
        }

        public void onClick(View v) {
            String emotic = "";
            switch (v.getId()) {
                case C0627R.id.btn_smile1:
                    emotic = ":-D|";
                    break;
                case C0627R.id.btn_smile2:
                    emotic = ":-O|";
                    break;
                case C0627R.id.btn_smile3:
                    emotic = ":-(|";
                    break;
                case C0627R.id.btn_smile4:
                    emotic = ":-)|";
                    break;
                case C0627R.id.btn_smile5:
                    emotic = ";-)|";
                    break;
                case C0627R.id.btn_smile6:
                    emotic = ":-|";
                    break;
                case C0627R.id.btn_smile7:
                    emotic = "B-D|";
                    break;
                case C0627R.id.btn_smile8:
                    emotic = "8-)";
                    break;
                case C0627R.id.btn_smile9:
                    emotic = ":-p|";
                    break;
                case C0627R.id.btn_smile10:
                    emotic = ":\")";
                    break;
                case C0627R.id.btn_smile11:
                    emotic = ":'(|";
                    break;
                case C0627R.id.btn_smile12:
                    emotic = "X(";
                    break;
                case C0627R.id.btn_smile13:
                    emotic = ":x|";
                    break;
                case C0627R.id.btn_smile14:
                    emotic = ">:)|";
                    break;
                case C0627R.id.btn_smile15:
                    emotic = "O:)|";
                    break;
            }
            if (!emotic.equals("")) {
                int start = this.et.getSelectionStart();
                int end = this.et.getSelectionEnd();
                this.et.getText().replace(Math.min(start, end), Math.max(start, end), emotic, 0, emotic.length());
                this.et.setText(t_chat.addSmileySpans(t_chat.this, this.et.getText()));
                Selection.setSelection(this.et.getText(), Math.min(start, end) + emotic.length());
            }
            dismiss();
        }
    }

    public static class cancelar_solicitud extends AsyncTask<String, Void, String> {
        private String codigo;
        private int idusu;
        private String idusu_conversante;
        private int modo;

        public cancelar_solicitud(String idusu_conversante, int modo, int idusu, String codigo) {
            this.idusu_conversante = idusu_conversante;
            this.modo = modo;
            this.idusu = idusu;
            this.codigo = codigo;
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/cancelar_solicitud.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("v", new StringBody("2"));
                reqEntity.addPart("idusu", new StringBody(this.idusu + ""));
                reqEntity.addPart("codigo", new StringBody(this.codigo));
                reqEntity.addPart("idusu_conversante", new StringBody(this.idusu_conversante));
                reqEntity.addPart("modo", new StringBody(this.modo + ""));
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
                e.printStackTrace();
                return "ANDROID:KO 99";
            }
        }

        protected void onPostExecute(String result) {
            if ((result.indexOf("ANDROID:OK") != -1 || result.indexOf("ANDROID:KO") != -1) && result.indexOf("ANDROID:OK") != -1) {
            }
        }
    }

    private class cargar_ultimas extends AsyncTask<String, Void, String> {
        private int btncargarmas_vis;
        private String idconversante;
        private int idfrase_masantigua;
        private String idprivado_finalizar;

        class C07721 implements DialogInterface.OnClickListener {
            C07721() {
            }

            public void onClick(DialogInterface dialog, int id) {
            }
        }

        public cargar_ultimas(String idprivado_finalizar, String idconversante, int idfrase_masantigua) {
            this.idprivado_finalizar = idprivado_finalizar;
            this.idconversante = idconversante;
            this.idfrase_masantigua = idfrase_masantigua;
        }

        protected void onPreExecute() {
            if (this.idfrase_masantigua > -1) {
                this.btncargarmas_vis = t_chat.this.findViewById(C0627R.id.btncargarmas).getVisibility();
                t_chat.this.findViewById(C0627R.id.btncargarmas).setVisibility(8);
                t_chat.this.pb_ant.setVisibility(0);
            }
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/ultimas_frases.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("idusu", new StringBody(t_chat.this.idusu + ""));
                reqEntity.addPart("c", new StringBody(t_chat.this.codigo));
                reqEntity.addPart("idtema", new StringBody(t_chat.this.idtema + ""));
                reqEntity.addPart("idf", new StringBody(this.idfrase_masantigua + ""));
                reqEntity.addPart("idprivado_finalizar", new StringBody(this.idprivado_finalizar));
                reqEntity.addPart("idconversante", new StringBody(this.idconversante));
                reqEntity.addPart("externo", new StringBody(t_chat.this.externo + ""));
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
            if (this.idfrase_masantigua != -1) {
                try {
                    t_chat.this.pb_ant.setVisibility(8);
                } catch (Exception e) {
                }
                if (result.indexOf("ANDROID:OK") == -1 && result.indexOf("ANDROID:KO") == -1 && result.indexOf("ANDROID:PERFILKO") == -1) {
                    t_chat.this.findViewById(C0627R.id.btncargarmas).setVisibility(this.btncargarmas_vis);
                } else if (result.indexOf("ANDROID:PERFILKO") != -1) {
                    final AlertDialog d_aux = new Builder(t_chat.this).setPositiveButton(C0627R.string.aceptar, new C07721()).setMessage(C0627R.string.perfil_desactivado).create();
                    if (!t_chat.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                                d_aux.getButton(-2).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                            }
                        });
                    }
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e2) {
                    }
                } else if (result.indexOf("ANDROID:OK") != -1) {
                    int pos2;
                    int pos = result.indexOf("@XYY53@");
                    String frase_ult = "";
                    String id_ult = "";
                    while (pos != -1) {
                        pos = result.indexOf("@", pos + 1) + 1;
                        pos2 = result.indexOf("@", pos);
                        String idfrase = result.substring(pos, pos2);
                        t_chat.this.idfrase_masantigua_glob = Integer.parseInt(idfrase);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String id = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String nombre = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String frase = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String vfoto = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String privados = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String f = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String fnac_d = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String fnac_m = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String fnac_a = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String sexo = result.substring(pos, pos2);
                        pos = pos2 + 1;
                        pos2 = result.indexOf("@", pos);
                        String coments = result.substring(pos, pos2);
                        if (frase_ult.equals("") || frase.equals("") || !frase_ult.equals(frase) || id_ult.equals("") || id.equals("") || !id_ult.equals(id)) {
                            frase_ult = frase;
                            id_ult = id;
                            Editor e3 = t_chat.this.settings.edit();
                            e3.putString("f2_idfrase", idfrase);
                            e3.putString("f2_id", id);
                            e3.putString("f2_nombre", nombre);
                            e3.putString("f2_privados", privados);
                            e3.putString("f2_frase", frase);
                            e3.putString("f2_fcrea", config.convertir_fecha(f));
                            e3.putString("f2_b64", "");
                            e3.putString("f2_idtema", t_chat.this.idtema + "");
                            e3.putString("f2_vfoto", vfoto);
                            e3.putString("f2_fnac_d", fnac_d);
                            e3.putString("f2_fnac_m", fnac_m);
                            e3.putString("f2_fnac_a", fnac_a);
                            e3.putString("f2_sexo", sexo);
                            e3.putString("f2_coments", coments);
                            e3.putBoolean("f2_ultimas", true);
                            e3.putString("f2_ts", System.currentTimeMillis() + "");
                            e3.commit();
                        }
                        pos = result.indexOf("@XYY53@", pos2);
                    }
                    if (result.indexOf("ZXRT4@1@") != -1) {
                        t_chat.this.findViewById(C0627R.id.btncargarmas).setVisibility(0);
                    } else {
                        t_chat.this.mostrar_fecha();
                        t_chat.this.findViewById(C0627R.id.btncargarmas).setVisibility(8);
                    }
                    if (t_chat.this.externo) {
                        pos = result.indexOf("ZXRT4@");
                        if (pos != -1) {
                            pos += 8;
                            pos2 = result.indexOf("@", pos);
                            int idfondo_aux = Integer.parseInt(result.substring(pos, pos2));
                            pos = pos2 + 1;
                            pos2 = result.indexOf("@", pos);
                            boolean fondo_modif_aux = result.substring(pos, pos2).equals("1");
                            pos = pos2 + 1;
                            int vfondo_aux = Integer.parseInt(result.substring(pos, result.indexOf("@", pos)));
                            if (idfondo_aux <= 0) {
                                return;
                            }
                            if (fondo_modif_aux) {
                                new cargarfoto().execute(new String[]{idfondo_aux + "", "", vfondo_aux + ""});
                                return;
                            }
                            try {
                                t_chat.this.globales.file_to_iv("fondo_" + idfondo_aux, (ImageView) t_chat.this.findViewById(C0627R.id.iv_fondo));
                            } catch (Exception e4) {
                            }
                        }
                    }
                } else {
                    t_chat.this.findViewById(C0627R.id.btncargarmas).setVisibility(this.btncargarmas_vis);
                }
            }
        }
    }

    private class cargarfoto extends AsyncTask<String, Void, Byte> {
        String idfoto;
        String ind;
        String vfoto;

        private cargarfoto() {
        }

        protected Byte doInBackground(String... params) {
            URL url;
            this.idfoto = params[0];
            this.ind = params[1];
            this.vfoto = params[2];
            String url_aux = "http://imgs1.e-droid.net/srv/imgs/chat/" + this.idfoto + "_fondo.png?v=" + this.vfoto;
            String archivo_aux = "fondo_" + this.idfoto;
            try {
                URL myFileUrl = new URL(url_aux);
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(StatusCodes.AUTH_DISABLED);
                    conn.connect();
                    Bitmap bm_foto = BitmapFactory.decodeStream(conn.getInputStream());
                    try {
                        FileOutputStream fos = t_chat.this.openFileOutput(archivo_aux, 0);
                        bm_foto.compress(CompressFormat.PNG, 100, fos);
                        fos.close();
                        url = myFileUrl;
                        return Byte.valueOf((byte) 0);
                    } catch (Exception e) {
                        url = myFileUrl;
                        return Byte.valueOf((byte) -1);
                    }
                } catch (IOException e2) {
                    url = myFileUrl;
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e3) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPreExecute() {
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 0) {
                try {
                    t_chat.this.globales.file_to_iv("fondo_" + this.idfoto, (ImageView) t_chat.this.findViewById(C0627R.id.iv_fondo));
                } catch (Exception e) {
                }
                if (t_chat.this.externo) {
                    new marcar_fondo_guardado().execute(new String[]{this.idfoto});
                    return;
                }
                Editor editor = t_chat.this.getSharedPreferences("sh", 0).edit();
                editor.putInt("s" + this.idfoto + "_fondo_modif", 0);
                editor.commit();
                t_chat.this.globales.secciones_a[Integer.parseInt(this.ind)].fondo_modif = false;
            }
        }
    }

    public static class eliminar_usu extends AsyncTask<String, Void, String> {
        private Context f42c;
        private String codigo;
        private int idusu;
        private String idusu_elim;

        public eliminar_usu(String idusu_elim, int idusu, String codigo, Context c) {
            this.idusu_elim = idusu_elim;
            this.idusu = idusu;
            this.codigo = codigo;
            this.f42c = c;
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/eliminar_usu.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("idusu", new StringBody(this.idusu + ""));
                reqEntity.addPart("c", new StringBody(this.codigo));
                reqEntity.addPart("idusu_elim", new StringBody(this.idusu_elim));
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
            if ((result.indexOf("ANDROID:OK") != -1 || result.indexOf("ANDROID:KO") != -1) && result.indexOf("ANDROID:OK") != -1) {
                config.mostrar_toast(Toast.makeText(this.f42c, this.f42c.getString(C0627R.string.usu_eliminado), 0));
            }
        }
    }

    private class enviar extends AsyncTask<String, Void, String> {
        String f43m;

        public enviar(String m) {
            this.f43m = m;
        }

        protected void onPreExecute() {
            if (!t_chat.this.notif) {
                Drawable d = t_chat.this.getResources().getDrawable(C0627R.drawable.notif_blanco);
                d.setColorFilter(t_chat.this.c_activ, Mode.MULTIPLY);
                t_chat.this.iv_notif_noactiv.setImageDrawable(d);
                t_chat.this.notif = true;
                Editor e = t_chat.this.settings.edit();
                e.putBoolean("notif" + t_chat.this.idchat, true);
                e.commit();
                new modif_usuchat(t_chat.this.idusu, t_chat.this.codigo, t_chat.this.idchat, "notif", 1).execute(new String[0]);
            }
            if (t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                t_chat.this.globales.toca_int_chat(t_chat.this);
            }
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/enviar_mensaje.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("v", new StringBody("2"));
                reqEntity.addPart("idusu", new StringBody(t_chat.this.idusu + ""));
                reqEntity.addPart("c", new StringBody(t_chat.this.codigo));
                reqEntity.addPart("idtema", new StringBody(t_chat.this.idtema + ""));
                reqEntity.addPart("idusudest", new StringBody(t_chat.this.settings.getString("idprivado", "0")));
                reqEntity.addPart("externo", new StringBody(t_chat.this.externo + ""));
                reqEntity.addPart("m", new StringBody(URLEncoder.encode(this.f43m, "UTF-8")));
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
            Builder builder = new Builder(t_chat.this);
            try {
                t_chat.this.pb_enviando.setVisibility(8);
                final AlertDialog d_aux;
                int pos;
                int pos2;
                Editor editor;
                if (t_chat.this.c1_esclaro || VERSION.SDK_INT <= 12) {
                    ((TextView) t_chat.this.findViewById(C0627R.id.c_mensaje)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    if (result.indexOf("ANDROID:OK IDFRASE:") != -1 && result.indexOf("ANDROID:KO") == -1) {
                        d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                        if (!t_chat.this.cbtn.equals("")) {
                            d_aux.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface arg0) {
                                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
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
                    } else if (result.indexOf("ANDROID:OK IDFRASE:") != -1) {
                        ((EditText) t_chat.this.findViewById(C0627R.id.c_mensaje)).setText("");
                        pos = result.indexOf("ANDROID:OK IDFRASE:") + 19;
                        pos2 = result.indexOf("@", pos);
                        editor = t_chat.this.settings.edit();
                        editor.putString("f2_id", t_chat.this.idusu + "");
                        editor.putString("f2_frase", this.f43m);
                        editor.putString("f2_fcrea", new SimpleDateFormat("ddMMyyHHmm").format(new Date()));
                        editor.putString("f2_b64", "");
                        editor.putString("f2_idfrase", result.substring(pos, pos2));
                        editor.putString("f2_idtema", t_chat.this.idtema + "");
                        editor.putString("f2_vfoto", "0");
                        editor.putBoolean("f2_ultimas", false);
                        editor.putString("f2_ts", System.currentTimeMillis() + "");
                        editor.commit();
                    } else if (result.indexOf("ANDROID:KO MOTIVO:NOGCM") == -1) {
                        d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.nogcm).create();
                        if (!t_chat.this.cbtn.equals("")) {
                            d_aux.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface arg0) {
                                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                                }
                            });
                        }
                        d_aux.show();
                        try {
                            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                        } catch (Exception e2) {
                            return;
                        }
                    } else if (result.indexOf("ANDROID:KO MOTIVO:ABANDONADO") == -1 && !t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                        return;
                    } else {
                        if (result.indexOf("ANDROID:KO MOTIVO:NOADMITE") != -1 || t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                            d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                            if (!t_chat.this.cbtn.equals("")) {
                                d_aux.setOnShowListener(new OnShowListener() {
                                    public void onShow(DialogInterface arg0) {
                                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                                    }
                                });
                            }
                            d_aux.show();
                            try {
                                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                            } catch (Exception e3) {
                                return;
                            }
                        }
                        d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.noadmiteprivados).create();
                        if (!t_chat.this.cbtn.equals("")) {
                            d_aux.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface arg0) {
                                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                                }
                            });
                        }
                        d_aux.show();
                        try {
                            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                            return;
                        } catch (Exception e4) {
                            return;
                        }
                    }
                }
                ((TextView) t_chat.this.findViewById(C0627R.id.c_mensaje)).setTextColor(-1);
                if (result.indexOf("ANDROID:OK IDFRASE:") != -1) {
                }
                if (result.indexOf("ANDROID:OK IDFRASE:") != -1) {
                    ((EditText) t_chat.this.findViewById(C0627R.id.c_mensaje)).setText("");
                    pos = result.indexOf("ANDROID:OK IDFRASE:") + 19;
                    pos2 = result.indexOf("@", pos);
                    editor = t_chat.this.settings.edit();
                    editor.putString("f2_id", t_chat.this.idusu + "");
                    editor.putString("f2_frase", this.f43m);
                    editor.putString("f2_fcrea", new SimpleDateFormat("ddMMyyHHmm").format(new Date()));
                    editor.putString("f2_b64", "");
                    editor.putString("f2_idfrase", result.substring(pos, pos2));
                    editor.putString("f2_idtema", t_chat.this.idtema + "");
                    editor.putString("f2_vfoto", "0");
                    editor.putBoolean("f2_ultimas", false);
                    editor.putString("f2_ts", System.currentTimeMillis() + "");
                    editor.commit();
                } else if (result.indexOf("ANDROID:KO MOTIVO:NOGCM") == -1) {
                    if (result.indexOf("ANDROID:KO MOTIVO:ABANDONADO") == -1) {
                    }
                    if (result.indexOf("ANDROID:KO MOTIVO:NOADMITE") != -1) {
                    }
                    d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                    if (t_chat.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(/* anonymous class already generated */);
                    }
                    d_aux.show();
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } else {
                    d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.nogcm).create();
                    if (t_chat.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(/* anonymous class already generated */);
                    }
                    d_aux.show();
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                }
            } catch (Exception e5) {
            }
        }
    }

    private class enviar_foto extends AsyncTask<String, Void, String> {
        int id_temp;

        public enviar_foto(int id) {
            this.id_temp = id;
        }

        protected void onPreExecute() {
            if (!t_chat.this.notif) {
                Drawable d = t_chat.this.getResources().getDrawable(C0627R.drawable.notif_blanco);
                d.setColorFilter(t_chat.this.c_activ, Mode.MULTIPLY);
                t_chat.this.iv_notif_noactiv.setImageDrawable(d);
                t_chat.this.notif = true;
                Editor e = t_chat.this.settings.edit();
                e.putBoolean("notif" + t_chat.this.idchat, true);
                e.commit();
                new modif_usuchat(t_chat.this.idusu, t_chat.this.codigo, t_chat.this.idchat, "notif", 1).execute(new String[0]);
            }
            if (t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                t_chat.this.globales.toca_int_chat(t_chat.this);
            }
            Editor editor = t_chat.this.settings.edit();
            editor.putString("f2_id", t_chat.this.idusu + "");
            editor.putString("f2_frase", "@@idfoto_temp:" + this.id_temp + "@@");
            editor.putString("f2_fcrea", new SimpleDateFormat("ddMMyyHHmm").format(new Date()));
            editor.putString("f2_b64", "");
            editor.putString("f2_idfrase", "");
            editor.putString("f2_idtema", t_chat.this.idtema + "");
            editor.putString("f2_vfoto", "0");
            editor.putBoolean("f2_ultimas", false);
            editor.putString("f2_ts", System.currentTimeMillis() + "");
            editor.commit();
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 60000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/enviar_foto.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                Bitmap bm = Media.getBitmap(t_chat.this.getContentResolver(), Uri.fromFile(t_chat.this.globales.getTempFile(t_chat.this, this.id_temp)));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bm.compress(CompressFormat.JPEG, 100, bos);
                reqEntity.addPart("foto", new ByteArrayBody(bos.toByteArray(), "temporal.jpg"));
                reqEntity.addPart("v", new StringBody("2"));
                reqEntity.addPart("idusu", new StringBody(t_chat.this.idusu + ""));
                reqEntity.addPart("c", new StringBody(t_chat.this.codigo));
                reqEntity.addPart("idtema", new StringBody(t_chat.this.idtema + ""));
                reqEntity.addPart("idusudest", new StringBody(t_chat.this.settings.getString("idprivado", "0")));
                reqEntity.addPart("externo", new StringBody(t_chat.this.externo + ""));
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
                e.printStackTrace();
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
            Builder builder = new Builder(t_chat.this);
            try {
                t_chat.this.llchat.findViewWithTag(this.id_temp + "").setVisibility(8);
                t_chat.this.llchat.findViewWithTag(this.id_temp + "").setTag(null);
            } catch (Exception e) {
            }
            final AlertDialog d_aux;
            if (result.indexOf("ANDROID:OK IDFRASE:") == -1 && result.indexOf("ANDROID:KO") == -1) {
                d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                if (!t_chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            } else if (result.indexOf("ANDROID:OK IDFRASE:") != -1) {
                int pos = result.indexOf("ANDROID:OK IDFRASE:") + 19;
                int pos2 = result.indexOf("@", pos);
                t_chat.this.globales.getTempFile(t_chat.this, this.id_temp).renameTo(t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(result.substring(pos, pos2)), false));
                try {
                    t_chat.this.llchat.findViewWithTag("imgtemp" + this.id_temp).setTag("img" + result.substring(pos, pos2));
                    t_chat.this.llchat.findViewWithTag("img" + result.substring(pos, pos2)).setOnClickListener(t_chat.this);
                } catch (Exception e3) {
                }
            } else if (result.indexOf("ANDROID:KO MOTIVO:NOGCM") != -1) {
                d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.nogcm).create();
                if (!t_chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e4) {
                }
            } else if (result.indexOf("ANDROID:KO MOTIVO:ABANDONADO") != -1 && !t_chat.this.settings.getString("idprivado", "0").equals("0")) {
            } else {
                if (result.indexOf("ANDROID:KO MOTIVO:NOADMITE") == -1 || t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                    d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                    if (!t_chat.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                            }
                        });
                    }
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                        return;
                    } catch (Exception e5) {
                        return;
                    }
                }
                d_aux = builder.setCancelable(false).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.noadmiteprivados).create();
                if (!t_chat.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e6) {
                }
            }
        }
    }

    private class marcar_fondo_guardado extends AsyncTask<String, Void, Byte> {
        String idfoto;

        private marcar_fondo_guardado() {
        }

        protected Byte doInBackground(String... params) {
            this.idfoto = params[0];
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/fondo_guardado.php");
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("idusu", new StringBody(t_chat.this.idusu + ""));
                reqEntity.addPart("c", new StringBody(t_chat.this.codigo));
                reqEntity.addPart("idfoto", new StringBody(this.idfoto + ""));
                postRequest.setEntity(reqEntity);
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                HttpResponse response = httpClient.execute(postRequest);
                return Byte.valueOf((byte) 0);
            } catch (Exception e) {
                return Byte.valueOf((byte) -1);
            }
        }
    }

    private class obtener_foto extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idusu_acargar;
        String vfoto_acargar;

        private obtener_foto() {
        }

        protected void onPreExecute() {
            if (t_chat.this.acargar_m.equals("")) {
                cancel(false);
                return;
            }
            try {
                this.idusu_acargar = (String) t_chat.this.acargar_m.keySet().toArray()[0];
            } catch (Exception e) {
                cancel(false);
            }
            if (this.idusu_acargar != null) {
                this.vfoto_acargar = (String) t_chat.this.acargar_m.get(this.idusu_acargar);
                t_chat.this.intentados_m.put(this.idusu_acargar, t_chat.this.acargar_m.get(this.idusu_acargar));
                t_chat.this.acargar_m.remove(this.idusu_acargar);
            }
        }

        protected String doInBackground(String... urls) {
            if (this.idusu_acargar == null || this.vfoto_acargar == null) {
                return "0";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/usus/" + this.idusu_acargar + "_1_p.jpg?v=" + this.vfoto_acargar).openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(t_chat.this.path, "fperfil_" + this.idusu_acargar + ".jpg")));
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
            if (t_chat.this.fotos_m != null) {
                if (result == "1") {
                    t_chat.this.fotos_m.put(this.idusu_acargar, this.bmImg);
                    Editor e = t_chat.this.settings.edit();
                    e.putString("fperfil_" + this.idusu_acargar, (String) t_chat.this.intentados_m.get(this.idusu_acargar));
                    e.commit();
                }
                int childCount = t_chat.this.llchat.getChildCount();
                int i = 0;
                while (i < childCount) {
                    try {
                        String idusu_aux = (String) t_chat.this.llchat.getChildAt(i).getTag();
                        if (idusu_aux != null && idusu_aux.equals(this.idusu_acargar)) {
                            t_chat.this.llchat.getChildAt(i).findViewById(C0627R.id.pb_foto).setVisibility(8);
                            t_chat.this.llchat.getChildAt(i).findViewById(C0627R.id.pb_foto_inv).setVisibility(8);
                            if (result == "1") {
                                ((ImageView) t_chat.this.llchat.getChildAt(i).findViewById(C0627R.id.iv_avatar_f)).setImageBitmap(this.bmImg);
                            }
                        }
                        i++;
                    } catch (Exception e2) {
                    }
                }
                if (!t_chat.this.acargar_m.equals("")) {
                    t_chat.this.o_f = new obtener_foto();
                    t_chat.this.o_f.execute(new String[0]);
                }
            }
        }
    }

    private class obtener_img extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idimg_acargar;

        private obtener_img() {
        }

        protected void onPreExecute() {
            if (t_chat.this.imgs_acargar.equals("")) {
                cancel(false);
                return;
            }
            int pos1 = t_chat.this.imgs_acargar.indexOf(",");
            if (pos1 == -1) {
                cancel(false);
                return;
            }
            int pos2 = t_chat.this.imgs_acargar.indexOf(",", pos1 + 1);
            if (pos2 == -1) {
                cancel(false);
                return;
            }
            this.idimg_acargar = t_chat.this.imgs_acargar.substring(pos1 + 1, pos2);
            if (this.idimg_acargar.equals("")) {
                cancel(false);
            }
        }

        protected String doInBackground(String... urls) {
            if (this.idimg_acargar == null || this.idimg_acargar.equals("")) {
                return "0";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/frases/f" + this.idimg_acargar + "_p.jpg").openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(this.idimg_acargar), false)));
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
            if (t_chat.this.imgs_acargar != null) {
                t_chat.this.imgs_acargar = t_chat.this.imgs_acargar.replace("," + this.idimg_acargar + ",", "");
                try {
                    t_chat.this.llchat.findViewWithTag("pb" + this.idimg_acargar).setVisibility(8);
                } catch (Exception e) {
                }
                t_chat.this.mostrar_foto_f(Integer.parseInt(this.idimg_acargar), false, (ImageView) t_chat.this.llchat.findViewWithTag("img" + this.idimg_acargar));
                try {
                    t_chat.this.llchat.findViewWithTag("img" + this.idimg_acargar).setOnClickListener(t_chat.this);
                } catch (Exception e2) {
                }
                if (!t_chat.this.imgs_acargar.equals("")) {
                    t_chat.this.o_i = new obtener_img();
                    t_chat.this.o_i.execute(new String[0]);
                }
            }
        }
    }

    private class obtener_img_g extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idimg_acargar;

        public obtener_img_g(String id) {
            this.idimg_acargar = id;
        }

        protected String doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/frases/f" + this.idimg_acargar + ".jpg");
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
                        this.bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(this.idimg_acargar), true)));
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
            try {
                t_chat.this.llchat.findViewWithTag("pb" + this.idimg_acargar).setVisibility(8);
            } catch (Exception e) {
            }
            Intent i = new Intent(t_chat.this, t_url.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(this.idimg_acargar), true).getAbsolutePath());
            t_chat.this.startActivityForResult(i, 0);
        }
    }

    static /* synthetic */ String access$2084(t_chat x0, Object x1) {
        String str = x0.imgs_acargar + x1;
        x0.imgs_acargar = str;
        return str;
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        String c1_aux;
        String c1;
        String c2;
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
        this.externo = this.extras.containsKey("externo");
        this.secc_act = this.globales.secciones_a[this.globales.ind_secc_sel_2];
        if (this.externo) {
            c1_aux = this.extras.getString("c1");
            this.c1_esclaro = config.esClaro("#" + this.extras.getString("c1"));
            this.c2_esclaro = config.esClaro("#" + this.extras.getString("c2"));
        } else {
            c1_aux = this.secc_act.c1;
            this.c1_esclaro = config.esClaro("#" + this.secc_act.c1);
            this.c2_esclaro = config.esClaro("#" + this.secc_act.c2);
        }
        this.cbtn = config.aplicar_color_dialog(c1_aux, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !this.c1_esclaro) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_chat);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        if (this.globales.admob_pos != 0) {
            this.scale = getResources().getDisplayMetrics().density;
            View vheight = findViewById(C0627R.id.sv_chat);
            final View view = vheight;
            vheight.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    int dp_aux = (int) ((((float) view.getHeight()) - 0.5f) / t_chat.this.scale);
                    View v_aux;
                    if (dp_aux <= 270 || t_chat.this.globales.banner_mostrando) {
                        if (dp_aux <= 213 && t_chat.this.globales.banner_mostrando) {
                            if (t_chat.this.globales.admob_pos == 1) {
                                v_aux = t_chat.this.findViewById(C0627R.id.ll_princ).findViewWithTag(Integer.valueOf(C0627R.id.TAG_ESBANNER));
                                if (v_aux != null) {
                                    v_aux.setVisibility(8);
                                    t_chat.this.globales.banner_mostrando = false;
                                }
                            } else if (t_chat.this.globales.admob_pos == 2) {
                                t_chat.this.findViewById(C0627R.id.ll_ad).setVisibility(8);
                                t_chat.this.globales.banner_mostrando = false;
                            }
                        }
                    } else if (t_chat.this.globales.admob_pos == 1) {
                        v_aux = t_chat.this.findViewById(C0627R.id.ll_princ).findViewWithTag(Integer.valueOf(C0627R.id.TAG_ESBANNER));
                        if (v_aux != null) {
                            v_aux.setVisibility(0);
                            t_chat.this.globales.banner_mostrando = true;
                        }
                    } else if (t_chat.this.globales.admob_pos == 2) {
                        t_chat.this.findViewById(C0627R.id.ll_ad).setVisibility(0);
                        t_chat.this.globales.banner_mostrando = true;
                    }
                }
            });
        }
        if (this.globales.banners_enchats) {
            this.adView = this.globales.mostrar_banner(this, false);
        }
        int ind = this.globales.ind_secc_sel_2;
        if (this.externo) {
            this.idchat = this.extras.getInt("idchat");
            this.idtema = this.extras.getInt("idtema");
            this.fotos_perfil = this.extras.getInt("fotos_perfil");
            this.privados = this.extras.getBoolean("privados");
            this.coments = this.extras.getBoolean("coments");
            this.galeria = this.extras.getBoolean("galeria");
            this.fnac = this.extras.getInt("fnac");
            this.sexo = this.extras.getInt("sexo");
            this.dist = this.extras.getInt("dist");
            this.descr = this.extras.getInt("descr");
        } else {
            this.idsecc = this.extras.getInt("idsecc");
            this.idchat = this.idsecc;
            this.idtema = this.globales.secciones_a[ind].temas_a[0].id;
            this.fotos_perfil = this.globales.secciones_a[ind].fotos_perfil;
            this.privados = this.globales.secciones_a[ind].privados;
            this.coments = this.globales.secciones_a[ind].coments;
            this.galeria = this.globales.secciones_a[ind].galeria;
            this.fnac = this.globales.secciones_a[ind].p_fnac;
            this.sexo = this.globales.secciones_a[ind].p_sexo;
            this.dist = this.globales.secciones_a[ind].p_dist;
            this.descr = this.globales.secciones_a[ind].p_descr;
        }
        if (!this.privados) {
            findViewById(C0627R.id.btnfavoritos).setVisibility(8);
        }
        if (this.c1_esclaro) {
            this.c_activ = Color.parseColor("#B71C1C");
        } else {
            this.c_activ = Color.parseColor("#FF5252");
        }
        this.fotos_chat = 1;
        if ((this.externo && this.extras.getInt("fotos_chat") == 0) || (!this.externo && this.globales.secciones_a[ind].fotos_chat == 0)) {
            this.fotos_chat = 0;
        }
        if (this.fotos_chat == 0) {
            findViewById(C0627R.id.btnfoto).setVisibility(8);
        }
        this.WIDTH_IMGS = (int) ((100.0f * getResources().getDisplayMetrics().density) + 0.5f);
        this.TV_PADDING = (int) ((3.0f * getResources().getDisplayMetrics().density) + 0.5f);
        this.TV_MARGIN = (int) ((3.0f * getResources().getDisplayMetrics().density) + 0.5f);
        this.path = new File(Environment.getExternalStorageDirectory(), getPackageName());
        this.nfrases = 0;
        this.dia_act = "";
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.codigo = this.settings.getString("cod", "");
        this.settings2 = getSharedPreferences("accion", 0);
        this.acti = this;
        this.llchat = (LinearLayout) findViewById(C0627R.id.llchat);
        this.ll_cabe_mostrar = false;
        if (this.extras.containsKey("tit_cab")) {
            ((TextView) findViewById(C0627R.id.tv_cabe)).setText(this.extras.getString("tit_cab"));
            findViewById(C0627R.id.tv_cabe).setOnClickListener(new C07632());
            findViewById(C0627R.id.iv_cabe).setOnClickListener(new C07643());
            findViewById(C0627R.id.ll_cabe).setVisibility(0);
            this.ll_cabe_mostrar = true;
            if (this.externo) {
                Editor e = this.settings.edit();
                e.putString("tit_chat" + this.idchat, this.extras.getString("tit_cab"));
                e.commit();
            }
        } else if (!this.externo && this.globales.secciones_a[ind].acceso_a_externo) {
            findViewById(C0627R.id.tv_cabe_der).setOnClickListener(new C07654());
            findViewById(C0627R.id.iv_cabe_der).setOnClickListener(new C07665());
            findViewById(C0627R.id.ll_cabe_der).setVisibility(0);
        } else if (this.externo && !this.settings.getString("tit_chat" + this.idchat, "").equals("")) {
            ((TextView) findViewById(C0627R.id.tv_cabe)).setText("  " + this.settings.getString("tit_chat" + this.idchat, ""));
            findViewById(C0627R.id.iv_cabe).setVisibility(8);
            findViewById(C0627R.id.ll_cabe).setVisibility(0);
            this.ll_cabe_mostrar = true;
        }
        int i = 0;
        while (i < this.globales.secciones_a.length) {
            if (this.globales.secciones_a[i].tipo == 10 || (this.globales.secciones_a[i].tipo == 9 && this.globales.secciones_a[i].acceso_a_externo)) {
                findViewById(C0627R.id.fav_noactiv).setVisibility(0);
            }
            i++;
        }
        if (this.extras.containsKey("fav")) {
            this.fav = this.extras.getBoolean("fav");
        } else {
            this.fav = this.settings.getBoolean("fav" + this.idchat, false);
        }
        ImageView iv_fav_noactiv = (ImageView) findViewById(C0627R.id.fav_noactiv);
        iv_fav_noactiv.setOnClickListener(new C07676());
        if (this.fav) {
            Drawable d = getResources().getDrawable(C0627R.drawable.fav_blanco);
            d.setColorFilter(this.c_activ, Mode.MULTIPLY);
            iv_fav_noactiv.setImageDrawable(d);
        } else if (this.c1_esclaro) {
            iv_fav_noactiv.setImageDrawable(getResources().getDrawable(C0627R.drawable.fav_noactiv));
        } else {
            iv_fav_noactiv.setImageDrawable(getResources().getDrawable(C0627R.drawable.fav_noactiv_light));
        }
        if (this.extras.containsKey("notif")) {
            this.notif = this.extras.getBoolean("notif");
        } else {
            boolean notif_def = true;
            if (this.externo) {
                notif_def = false;
                if (!this.settings.contains("notif" + this.idchat)) {
                    for (Seccion seccion : this.globales.secciones_a) {
                        if (seccion.id == this.idchat) {
                            notif_def = true;
                            break;
                        }
                    }
                }
            }
            this.notif = this.settings.getBoolean("notif" + this.idchat, notif_def);
        }
        this.iv_notif_noactiv = (ImageView) findViewById(C0627R.id.notif_noactiv);
        this.iv_notif_noactiv.setOnClickListener(new C07687());
        if (this.notif) {
            d = getResources().getDrawable(C0627R.drawable.notif_blanco);
            d.setColorFilter(this.c_activ, Mode.MULTIPLY);
            this.iv_notif_noactiv.setImageDrawable(d);
        } else if (this.c1_esclaro) {
            this.iv_notif_noactiv.setImageDrawable(getResources().getDrawable(C0627R.drawable.notif_noactiv));
        } else {
            this.iv_notif_noactiv.setImageDrawable(getResources().getDrawable(C0627R.drawable.notif_noactiv_light));
        }
        this.pb_ant = (ProgressBar) findViewById(C0627R.id.pb_chat_inv);
        this.pb_enviando = (ProgressBar) findViewById(C0627R.id.pb_chat_env_inv);
        if (this.externo) {
            c1 = this.extras.getString("c1");
            c2 = this.extras.getString("c2");
        } else {
            c1 = this.globales.secciones_a[ind].c1;
            c2 = this.globales.secciones_a[ind].c2;
        }
        if (!c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + c1), Color.parseColor("#" + c2)}));
            int c = ViewCompat.MEASURED_STATE_MASK;
            if (this.c1_esclaro) {
                findViewById(C0627R.id.ll_cabe_der).setBackgroundColor(config.GRIS_CLARO);
                ((TextView) findViewById(C0627R.id.tv_cabe_der)).setTextColor(config.NEGRO);
                findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_CLARO);
                ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(config.NEGRO);
                findViewById(C0627R.id.ll_cabe_priv).setBackgroundColor(config.GRIS_CLARO);
                ((TextView) findViewById(C0627R.id.tv_cabe_priv)).setTextColor(config.NEGRO);
            } else {
                c = -1;
                ((ImageView) findViewById(C0627R.id.btnperfil)).setImageDrawable(getResources().getDrawable(C0627R.drawable.perfil_light));
                ((ImageView) findViewById(C0627R.id.btnfavoritos)).setImageDrawable(getResources().getDrawable(C0627R.drawable.privados_light));
                ((ImageView) findViewById(C0627R.id.btnfoto)).setImageDrawable(getResources().getDrawable(C0627R.drawable.attach_light));
                this.pb_ant = (ProgressBar) findViewById(C0627R.id.pb_chat);
                findViewById(C0627R.id.ll_cabe_der).setBackgroundColor(config.GRIS_OSCURO);
                ((TextView) findViewById(C0627R.id.tv_cabe_der)).setTextColor(-1);
                findViewById(C0627R.id.ll_cabe).setBackgroundColor(config.GRIS_OSCURO);
                ((TextView) findViewById(C0627R.id.tv_cabe)).setTextColor(-1);
                findViewById(C0627R.id.ll_cabe_priv).setBackgroundColor(config.GRIS_OSCURO);
                ((TextView) findViewById(C0627R.id.tv_cabe_priv)).setTextColor(-1);
            }
            if (VERSION.SDK_INT > 20) {
                config.edittext_color((EditText) findViewById(C0627R.id.c_mensaje), Boolean.valueOf(!this.c1_esclaro), this.globales.c_icos);
            } else if (VERSION.SDK_INT > 12) {
                ((EditText) findViewById(C0627R.id.c_mensaje)).setTextColor(c);
            }
            if (!this.c2_esclaro) {
                ((ImageView) findViewById(C0627R.id.btnemotic)).setImageDrawable(getResources().getDrawable(C0627R.drawable.emoticono_sel_light));
                if (VERSION.SDK_INT > 12) {
                    this.pb_enviando = (ProgressBar) findViewById(C0627R.id.pb_chat_env);
                }
            }
            if (VERSION.SDK_INT > 20) {
                config.progress_color(this.pb_ant, this.globales.c_icos);
                config.progress_color(this.pb_enviando, this.globales.c_icos);
            }
            if (config.esClaro("#" + this.globales.c_icos)) {
                findViewById(C0627R.id.iv_btn_fondo_n).setVisibility(0);
            } else {
                findViewById(C0627R.id.iv_btn_fondo_b).setVisibility(0);
            }
            d = getResources().getDrawable(C0627R.drawable.btn_enviar);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.btnenv)).setImageDrawable(d);
        }
        if (!this.externo) {
            int idfondo = this.globales.secciones_a[ind].idfondo;
            int vfondo = this.globales.secciones_a[ind].vfondo;
            boolean fondo_modif = this.globales.secciones_a[ind].fondo_modif;
            if (idfondo > 0) {
                if (fondo_modif) {
                    new cargarfoto().execute(new String[]{idfondo + "", ind + "", vfondo + ""});
                } else {
                    try {
                        this.globales.file_to_iv("fondo_" + idfondo, (ImageView) findViewById(C0627R.id.iv_fondo));
                    } catch (Exception e2) {
                    }
                }
            }
        }
        this.closeHandler = new C07708();
        this.finishHandler = new C07719();
        Display display = getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            this.altura = size.y;
        } else {
            this.altura = display.getHeight();
        }
        this.es_privado = false;
        if (!this.settings.getString("idprivado", "0").equals("0")) {
            this.es_privado = true;
            findViewById(C0627R.id.btnfavoritos).setVisibility(8);
            findViewById(C0627R.id.btnperfil).setVisibility(8);
            if (this.globales.fotos_privados) {
                findViewById(C0627R.id.btnfoto).setVisibility(0);
            } else {
                findViewById(C0627R.id.btnfoto).setVisibility(8);
            }
            ((TextView) findViewById(C0627R.id.tv_cabe_priv)).setText(getResources().getString(C0627R.string.chatprivadocon) + " " + this.settings.getString("nombreprivado", ""));
            findViewById(C0627R.id.ll_cabe_priv).setVisibility(0);
            findViewById(C0627R.id.ll_favnotif).setVisibility(8);
            findViewById(C0627R.id.ll_cabe).setVisibility(8);
            findViewById(C0627R.id.ll_cabe_der).setVisibility(8);
        }
        findViewById(C0627R.id.btnperfil).setOnClickListener(this);
        findViewById(C0627R.id.btnfavoritos).setOnClickListener(this);
        findViewById(C0627R.id.btnfoto).setOnClickListener(this);
        findViewById(C0627R.id.iv_cabe_priv).setOnClickListener(this);
        findViewById(C0627R.id.tv_cabe_priv).setOnClickListener(this);
        findViewById(C0627R.id.btncargarmas).setOnClickListener(this);
        findViewById(C0627R.id.btnemotic).setOnClickListener(this);
        findViewById(C0627R.id.btnenv).setOnClickListener(this);
        findViewById(C0627R.id.c_mensaje).setOnKeyListener(this);
        this.fotos_m = new HashMap();
        this.acargar_m = new HashMap();
        this.intentados_m = new HashMap();
        if (this.c1_esclaro) {
            this.bm_propia = BitmapFactory.decodeResource(getResources(), C0627R.drawable.sinfoto_light);
        } else {
            this.bm_propia = BitmapFactory.decodeResource(getResources(), C0627R.drawable.sinfoto);
        }
        if (this.fotos_perfil > 0) {
            File file = this.globales.getTempFile(this, 1);
            try {
                int width;
                int height;
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
                boolean withinBounds = width <= 75 && height <= 100;
                if (withinBounds) {
                    this.bm_propia = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 75, 100)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    this.bm_propia = BitmapFactory.decodeFile(file.getPath(), resample);
                }
            } catch (Exception e3) {
            }
        }
        if (this.extras == null || !this.extras.containsKey("empezar_privado")) {
            this.idfrase_masantigua_glob = 0;
            if (!(this.externo && this.extras.containsKey("id_remit"))) {
                new cargar_ultimas("0", this.settings.getString("idprivado", "0"), this.idfrase_masantigua_glob).execute(new String[0]);
            }
            if (this.extras != null && this.extras.getString("id_remit") != null) {
                mostrar_solicprivado(this.extras.getString("id_remit"), this.extras.getString("nombre_remit"));
                return;
            }
            return;
        }
        iniciar_privado(this.extras.getString("id_remit"), this.extras.getString("nombre_remit"));
    }

    private void ir_a_externo() {
        Intent i;
        Intent data = new Intent();
        data.putExtra("finalizar", true);
        setResult(-1, data);
        if (this.secc_act.idsubcat > 0) {
            i = new Intent(this, t_buscchats_lista.class);
            i.putExtra("ind", this.globales.ind_secc_sel_2);
            i.putExtra("idcat", this.secc_act.idcat);
            i.putExtra("idsubcat", this.secc_act.idsubcat);
        } else {
            i = new Intent(this, t_buscchats.class);
            i.putExtra("ind", this.globales.ind_secc_sel_2);
            i.putExtra("idcat", this.secc_act.idcat);
        }
        if (this.globales.tipomenu != 2) {
            i.putExtra("es_root", true);
        }
        this.es_root = false;
        this.finalizar = true;
        finalizar();
        finish();
        startActivity(i);
    }

    public boolean onKey(View view, int keyCode, KeyEvent event) {
        if (event.getAction() != 0 || keyCode != 66) {
            return false;
        }
        f_enviar(true);
        return true;
    }

    private void f_enviar(boolean desde_teclado) {
        String m = ((TextView) findViewById(C0627R.id.c_mensaje)).getText().toString().replace("@", "").trim();
        if (!m.equals("")) {
            if (desde_teclado && this.altura < 600) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(((TextView) findViewById(C0627R.id.c_mensaje)).getWindowToken(), 0);
            }
            ((TextView) findViewById(C0627R.id.c_mensaje)).setTextColor(-7829368);
            this.pb_enviando.setVisibility(0);
            if (this.env == null || this.env.getStatus() != Status.RUNNING) {
                String idusupriv_aux = this.settings.getString("idprivado", "0");
                if (!(idusupriv_aux.equals("0") || this.settings.contains("usufav_" + idusupriv_aux) || this.settings.contains("usufav_noactivar_" + idusupriv_aux))) {
                    Editor e = this.settings.edit();
                    e.putBoolean("usufav_" + idusupriv_aux, true);
                    e.commit();
                }
                this.env = new enviar(m);
                this.env.execute(new String[0]);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        Matrix matrix;
        int orientation;
        int i_aux;
        if (requestCode == 1) {
            Bitmap bm;
            int rotation = -1;
            try {
                long fileSize = new File(this.globales.getTempFile(this, 99).getAbsolutePath()).length();
                Cursor mediaCursor = getApplicationContext().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"orientation", "_size"}, "date_added>=?", new String[]{String.valueOf((this.captureTime / 1000) - 1)}, "date_added desc");
                if (mediaCursor != null && this.captureTime != 0 && mediaCursor.getCount() != 0) {
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
                    rotation = new ExifInterface(this.globales.getTempFile(this, 99).getAbsolutePath()).getAttributeInt("Orientation", -1);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            int q_opt = 75;
            try {
                int width;
                int height;
                File file = this.globales.getTempFile(this, 99);
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
                boolean withinBounds = width <= 800 && height <= 800;
                if (withinBounds) {
                    bm = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 800, 800)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    bm = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                try {
                    bm.compress(CompressFormat.JPEG, 75, new FileOutputStream(this.globales.getTempFile(this, 99)));
                    q_opt = 100;
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
                        bm = BitmapFactory.decodeFile(this.globales.getTempFile(this, 99).getAbsolutePath());
                        matrix.postRotate((float) orientation);
                        try {
                            Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true).compress(CompressFormat.JPEG, q_opt, new FileOutputStream(this.globales.getTempFile(this, 99)));
                        } catch (Exception e5) {
                        }
                    } catch (Exception e42) {
                        e42.printStackTrace();
                    }
                }
            }
            i_aux = 100;
            while (this.globales.getTempFile(this, i_aux).exists()) {
                i_aux++;
            }
            this.globales.getTempFile(this, 99).renameTo(this.globales.getTempFile(this, i_aux));
            new enviar_foto(i_aux).execute(new String[0]);
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
                Bitmap bmImg = this.globales.decodeSampledBitmapFromResource(selectedImage, 800, 800);
                bmImg = Bitmap.createBitmap(bmImg, 0, 0, bmImg.getWidth(), bmImg.getHeight(), matrix, true);
                i_aux = 100;
                while (this.globales.getTempFile(this, i_aux).exists()) {
                    i_aux++;
                }
                try {
                    bmImg.compress(CompressFormat.JPEG, 75, new FileOutputStream(this.globales.getTempFile(this, i_aux)));
                } catch (Exception e6) {
                }
                bmImg.recycle();
                new enviar_foto(i_aux).execute(new String[0]);
            } catch (Exception e7) {
            }
        } else {
            if (data != null) {
                if (data.hasExtra("abrir_privado")) {
                    iniciar_privado(this.settings2.getString("id_remit", ""), this.settings2.getString("nombre_remit", ""));
                    return;
                }
            }
            if (data != null) {
                if (data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
                    if (!data.getExtras().getBoolean("finalizar_app")) {
                        this.es_root = false;
                    }
                    setResult(-1, data);
                    finalizar();
                    finish();
                }
            }
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    if (t_chat.this.globales.slider_v > 0) {
                        position--;
                    }
                    view.setId(t_chat.this.globales.menu_a_secciones[position]);
                    view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_chat.this.globales.menu_a_secciones[position]));
                    t_chat.this.onClick(view);
                }
            });
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

    private void tomar_foto() {
        final AlertDialog d_aux = new Builder(this).setCancelable(true).setPositiveButton(getString(C0627R.string.tomardecamara), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                t_chat.this.captureTime = System.currentTimeMillis();
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", Uri.fromFile(t_chat.this.globales.getTempFile(t_chat.this, 99)));
                try {
                    t_chat.this.startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException e) {
                }
            }
        }).setNegativeButton(getString(C0627R.string.selimg), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent photoPickerIntent = new Intent("android.intent.action.PICK");
                photoPickerIntent.setType("image/*");
                t_chat.this.startActivityForResult(photoPickerIntent, 2);
            }
        }).setMessage(C0627R.string.enviarimg).create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
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
        if (v.getId() == C0627R.id.btnfoto) {
            tomar_foto();
        } else if (v.getId() == C0627R.id.btnfavoritos) {
            intent = new Intent(this, privados.class);
            finalizar();
            startActivityForResult(intent, 0);
        } else if (v.getId() == C0627R.id.btnperfil) {
            intent = new Intent(this, preperfil.class);
            if (this.externo) {
                intent = config.completar_externo(intent, this.extras);
            } else {
                intent.putExtra("idsecc", this.idsecc);
            }
            intent.putExtra("nocompletar", true);
            finalizar();
            startActivityForResult(intent, 0);
        } else if (v.getId() == C0627R.id.iv_cabe_priv || v.getId() == C0627R.id.tv_cabe_priv) {
            finalizar_privado(this.settings.getString("idprivado", "0"));
        } else if (v.getId() == C0627R.id.btncargarmas) {
            new cargar_ultimas("0", this.settings.getString("idprivado", "0"), this.idfrase_masantigua_glob).execute(new String[0]);
        } else if (v.getId() == C0627R.id.btnemotic) {
            new EmoticonesMenu((EditText) findViewById(C0627R.id.c_mensaje)).show();
        } else if (v.getId() == C0627R.id.btnenv) {
            f_enviar(false);
        } else if (v.getTag(C0627R.id.idaux1) != null) {
            i = new Intent(this, profile.class);
            i.putExtra("id", (String) v.getTag(C0627R.id.idaux1));
            i.putExtra("privados", (String) v.getTag(C0627R.id.idaux2));
            i.putExtra("nombre", (String) v.getTag(C0627R.id.idaux3));
            i.putExtra("coments", (String) v.getTag(C0627R.id.idaux4));
            i.putExtra("fnac_d", (String) v.getTag(C0627R.id.idaux5));
            i.putExtra("fnac_m", (String) v.getTag(C0627R.id.idaux6));
            i.putExtra("fnac_a", (String) v.getTag(C0627R.id.idaux7));
            i.putExtra("sexo", (String) v.getTag(C0627R.id.idaux8));
            i.putExtra("vfoto", (String) v.getTag(C0627R.id.idaux9));
            i.putExtra("p_fnac", this.fnac);
            i.putExtra("p_sexo", this.sexo);
            i.putExtra("p_descr", this.descr);
            i.putExtra("p_dist", this.dist);
            i.putExtra("coments_chat", this.coments);
            i.putExtra("galeria", this.galeria);
            i.putExtra("privados_chat", this.privados);
            i.putExtra("fotos_perfil", this.fotos_perfil);
            i.putExtra("fotos_chat", this.fotos_chat);
            startActivityForResult(i, 0);
        } else if (v.getTag() != null && ((String) v.getTag()).length() > 3 && ((String) v.getTag()).substring(0, 3).equals("img")) {
            String id_img = ((String) v.getTag()).substring(3);
            boolean ver_grande = true;
            if (v.getTag(C0627R.id.TAG_ESIMGPROPIA) != null) {
                ver_grande = false;
            }
            if (this.globales.getTempFile_notemp(this, Integer.parseInt(id_img), ver_grande).exists()) {
                i = new Intent(this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.globales.getTempFile_notemp(this, Integer.parseInt(id_img), ver_grande).getAbsolutePath());
                startActivityForResult(i, 0);
            } else if (this.o_i_g == null || this.o_i_g.getStatus() != Status.RUNNING) {
                try {
                    this.llchat.findViewWithTag("pb" + id_img).setVisibility(0);
                } catch (Exception e) {
                }
                this.o_i_g = new obtener_img_g(id_img);
                this.o_i_g.execute(new String[0]);
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
            finalizar();
            finish();
        }
    }

    void mostrar_solicprivado(String id_remit, String nombre_remit) {
        this.id_remit_global = id_remit;
        this.nombre_remit_global = nombre_remit;
        this.idusu_global = this.idusu;
        this.codigo_global = this.codigo;
        Builder builder = new Builder(this);
        builder.setTitle(nombre_remit + " " + getResources().getString(C0627R.string.solicitaprivado)).setItems(C0627R.array.privado_opcs, new DialogInterface.OnClickListener() {

            class C07551 implements DialogInterface.OnClickListener {
                C07551() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    config.elim_privado(t_chat.this, t_chat.this.id_remit_global);
                    new cancelar_solicitud(t_chat.this.id_remit_global, 1, t_chat.this.idusu_global, t_chat.this.codigo_global).execute(new String[0]);
                    if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                        t_chat.this.finalizar = true;
                        t_chat.this.finalizar();
                        t_chat.this.finish();
                    }
                }
            }

            class C07562 implements DialogInterface.OnClickListener {
                C07562() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                        t_chat.this.finalizar = true;
                        t_chat.this.finalizar();
                        t_chat.this.finish();
                    }
                }
            }

            class C07573 implements OnShowListener {
                C07573() {
                }

                public void onShow(DialogInterface arg0) {
                    t_chat.this.d_confirm.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                    t_chat.this.d_confirm.getButton(-2).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                }
            }

            class C07584 implements DialogInterface.OnClickListener {
                C07584() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    new cancelar_solicitud(t_chat.this.id_remit_global, 3, t_chat.this.idusu_global, t_chat.this.codigo_global).execute(new String[0]);
                    Editor editor = t_chat.this.settings.edit();
                    editor.putInt("privados", 0);
                    editor.commit();
                    if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                        t_chat.this.finalizar = true;
                        t_chat.this.finalizar();
                        t_chat.this.finish();
                    }
                }
            }

            class C07595 implements DialogInterface.OnClickListener {
                C07595() {
                }

                public void onClick(DialogInterface dialog, int id) {
                    if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                        t_chat.this.finalizar = true;
                        t_chat.this.finalizar();
                        t_chat.this.finish();
                    }
                }
            }

            class C07606 implements OnShowListener {
                C07606() {
                }

                public void onShow(DialogInterface arg0) {
                    t_chat.this.d_confirm.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                    t_chat.this.d_confirm.getButton(-2).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                }
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    t_chat.this.iniciar_privado(t_chat.this.id_remit_global, t_chat.this.nombre_remit_global);
                } else if (which == 1) {
                    Editor editor = t_chat.this.settings.edit();
                    editor.putLong("f_ult_" + t_chat.this.id_remit_global, System.currentTimeMillis());
                    editor.commit();
                    if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                        t_chat.this.finalizar = true;
                        t_chat.this.finalizar();
                        t_chat.this.finish();
                    }
                } else if (which == 2) {
                    t_chat.this.d_confirm = new Builder(t_chat.this).setCancelable(true).setNegativeButton(t_chat.this.getString(C0627R.string.cancelar), new C07562()).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), new C07551()).setMessage(C0627R.string.avisodenegarprivados).create();
                    if (!t_chat.this.cbtn.equals("")) {
                        t_chat.this.d_confirm.setOnShowListener(new C07573());
                    }
                    t_chat.this.d_confirm.show();
                    try {
                        ((TextView) t_chat.this.d_confirm.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e) {
                    }
                } else if (which == 3) {
                    t_chat.this.d_confirm = new Builder(t_chat.this).setCancelable(true).setNegativeButton(t_chat.this.getString(C0627R.string.cancelar), new C07595()).setPositiveButton(t_chat.this.getString(C0627R.string.aceptar), new C07584()).setMessage(C0627R.string.confirmar_ningunprivado).create();
                    if (!t_chat.this.cbtn.equals("")) {
                        t_chat.this.d_confirm.setOnShowListener(new C07606());
                    }
                    t_chat.this.d_confirm.show();
                    try {
                        ((TextView) t_chat.this.d_confirm.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e2) {
                    }
                }
            }
        }).setCancelable(false);
        try {
            this.d_solic_privado = builder.show();
            try {
                ((TextView) this.d_solic_privado.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (prefs.contains("accion") && this.privados) {
            if (key.equals("accion")) {
                if (prefs.getInt("accion", 0) == 1) {
                    if (this.settings.getString("idprivado", "0").equals("0") && this.settings.getInt("privados", 1) == 1 && ((this.d_solic_privado == null || !this.d_solic_privado.isShowing()) && (this.d_confirm == null || !this.d_confirm.isShowing()))) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                t_chat.this.mostrar_solicprivado(t_chat.this.settings2.getString("id_remit", ""), t_chat.this.settings2.getString("nombre_remit", ""));
                            }
                        });
                    }
                } else if (!(prefs.getInt("accion", 0) == 2 || prefs.getInt("accion", 0) == 3 || prefs.getInt("accion", 0) != 4)) {
                }
                if (prefs.getInt("accion", 0) != 0) {
                    Editor editor2 = this.settings2.edit();
                    editor2.putInt("accion", 0);
                    editor2.commit();
                }
            }
        } else if (prefs.contains("f2_idfrase")) {
            String idprivado_aux = this.settings.getString("idprivado", "0");
            if (!key.equals("f2_ts")) {
                return;
            }
            if ((idprivado_aux.equals("0") && !this.es_privado) || (!idprivado_aux.equals("0") && this.es_privado)) {
                if (!prefs.getString("f2_idtema", "0").equals(this.idtema + "") && (idprivado_aux.equals("0") || !this.es_privado)) {
                    return;
                }
                if (idprivado_aux.equals("0") || idprivado_aux.equals(prefs.getString("f2_id", "")) || prefs.getString("f2_id", "").equals(this.idusu + "")) {
                    final SharedPreferences p = prefs;
                    runOnUiThread(new Runnable() {

                        class C07611 implements Runnable {
                            C07611() {
                            }

                            public void run() {
                                ((ScrollView) t_chat.this.findViewById(C0627R.id.sv_chat)).fullScroll(TransportMediator.KEYCODE_MEDIA_RECORD);
                            }
                        }

                        public void run() {
                            LayoutInflater inflater = (LayoutInflater) t_chat.this.getSystemService("layout_inflater");
                            if (p.getBoolean("f2_ultimas", false)) {
                                String f_aux = p.getString("f2_fcrea", "");
                                if (!f_aux.equals("")) {
                                    f_aux = f_aux.substring(0, 6);
                                    if (!f_aux.equals(t_chat.this.dia_act)) {
                                        t_chat.this.mostrar_fecha();
                                        t_chat.this.dia_act = f_aux;
                                    }
                                }
                            }
                            View ll_frase = (LinearLayout) inflater.inflate(C0627R.layout.frase_t_chat, null);
                            if (VERSION.SDK_INT > 20) {
                                config.progress_color((ProgressBar) ll_frase.findViewById(C0627R.id.pb_foto), t_chat.this.globales.c_icos);
                                config.progress_color((ProgressBar) ll_frase.findViewById(C0627R.id.pb_foto_inv), t_chat.this.globales.c_icos);
                                config.progress_color((ProgressBar) ll_frase.findViewById(C0627R.id.pb_foto_f), t_chat.this.globales.c_icos);
                                config.progress_color((ProgressBar) ll_frase.findViewById(C0627R.id.pb_foto_f_inv), t_chat.this.globales.c_icos);
                            }
                            ImageView iv = (ImageView) ll_frase.findViewById(C0627R.id.iv_avatar_f);
                            TextView tv = (TextView) ll_frase.findViewById(C0627R.id.tv_nombre_f);
                            TextView tv_fcrea = (TextView) ll_frase.findViewById(C0627R.id.tv_fcrea_f);
                            int c = ViewCompat.MEASURED_STATE_MASK;
                            int c_gris = Color.parseColor("#555555");
                            if (!t_chat.this.c1_esclaro) {
                                c_gris = Color.parseColor("#999999");
                            }
                            tv.setTextColor(c_gris);
                            tv_fcrea.setTextColor(c_gris);
                            if (p.getString("f2_id", "").equals(t_chat.this.idusu + "")) {
                                if (!t_chat.this.c1_esclaro) {
                                    c = -1;
                                }
                                tv.setText(t_chat.this.getResources().getString(C0627R.string.tu));
                                if (t_chat.this.fotos_perfil > 0 && t_chat.this.bm_propia != null) {
                                    iv.setImageBitmap(t_chat.this.bm_propia);
                                }
                            } else {
                                tv.setText(p.getString("f2_nombre", "").trim());
                                if (t_chat.this.settings.getString("idprivado", "0").equals("0")) {
                                    ll_frase.setTag(C0627R.id.idaux1, p.getString("f2_id", "0"));
                                    ll_frase.setTag(C0627R.id.idaux2, p.getString("f2_privados", "1"));
                                    ll_frase.setTag(C0627R.id.idaux3, p.getString("f2_nombre", ""));
                                    ll_frase.setTag(C0627R.id.idaux4, p.getString("f2_coments", "1"));
                                    ll_frase.setTag(C0627R.id.idaux5, p.getString("f2_fnac_d", "0"));
                                    ll_frase.setTag(C0627R.id.idaux6, p.getString("f2_fnac_m", "0"));
                                    ll_frase.setTag(C0627R.id.idaux7, p.getString("f2_fnac_a", "0"));
                                    ll_frase.setTag(C0627R.id.idaux8, p.getString("f2_sexo", "0"));
                                    ll_frase.setTag(C0627R.id.idaux9, p.getString("f2_vfoto", "0"));
                                    ll_frase.setOnClickListener(t_chat.this);
                                }
                                String idaux = p.getString("f2_id", "");
                                String dig = idaux.substring(idaux.length() - 1, idaux.length());
                                if (t_chat.this.c1_esclaro) {
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
                                String vfoto = p.getString("f2_vfoto", "0");
                                if (t_chat.this.fotos_perfil <= 0 || !vfoto.equals("0")) {
                                    if (t_chat.this.fotos_perfil > 0 && !vfoto.equals("0")) {
                                        Bitmap bm_aux = (Bitmap) t_chat.this.fotos_m.get(idaux);
                                        if (bm_aux != null) {
                                            iv.setImageBitmap(bm_aux);
                                        } else {
                                            try {
                                                int width_max;
                                                int height_max;
                                                int width;
                                                int height;
                                                File file = new File(t_chat.this.path, "fperfil_" + idaux + ".jpg");
                                                Options bounds = new Options();
                                                bounds.inJustDecodeBounds = true;
                                                BitmapFactory.decodeFile(file.getPath(), bounds);
                                                if (bounds.outWidth == -1) {
                                                    width_max = (int) ((48.0f * t_chat.this.getResources().getDisplayMetrics().density) + 0.5f);
                                                    height_max = (int) ((48.0f * t_chat.this.getResources().getDisplayMetrics().density) + 0.5f);
                                                    width = bounds.outWidth;
                                                    height = bounds.outHeight;
                                                } else {
                                                    width_max = (int) ((48.0f * t_chat.this.getResources().getDisplayMetrics().density) + 0.5f);
                                                    height_max = (int) ((48.0f * t_chat.this.getResources().getDisplayMetrics().density) + 0.5f);
                                                    width = bounds.outWidth;
                                                    height = bounds.outHeight;
                                                }
                                                boolean withinBounds = width <= width_max && height <= height_max;
                                                if (withinBounds) {
                                                    bm_aux = Media.getBitmap(t_chat.this.getContentResolver(), Uri.fromFile(file));
                                                } else {
                                                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, width_max, height_max)));
                                                    Options resample = new Options();
                                                    resample.inSampleSize = sampleSize;
                                                    bm_aux = BitmapFactory.decodeFile(file.getPath(), resample);
                                                }
                                                iv.setImageBitmap(bm_aux);
                                                t_chat.this.fotos_m.put(idaux, bm_aux);
                                            } catch (Exception e) {
                                                if (t_chat.this.c1_esclaro) {
                                                    iv.setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.sinfoto_light));
                                                } else {
                                                    iv.setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.sinfoto));
                                                }
                                            }
                                        }
                                        if (!p.getString("fperfil_" + idaux, "").equals(vfoto)) {
                                            if (t_chat.this.c1_esclaro) {
                                                ll_frase.findViewById(C0627R.id.pb_foto_inv).setVisibility(0);
                                            } else {
                                                ll_frase.findViewById(C0627R.id.pb_foto).setVisibility(0);
                                            }
                                            if (!(t_chat.this.acargar_m.containsKey(idaux) || (t_chat.this.intentados_m.containsKey(idaux) && ((String) t_chat.this.intentados_m.get(idaux)).equals(vfoto)))) {
                                                t_chat.this.acargar_m.put(idaux, vfoto);
                                                if (t_chat.this.o_f == null || t_chat.this.o_f.getStatus() != Status.RUNNING) {
                                                    t_chat.this.o_f = new obtener_foto();
                                                    t_chat.this.o_f.execute(new String[0]);
                                                }
                                            }
                                        }
                                    }
                                } else if (t_chat.this.c1_esclaro) {
                                    iv.setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.sinfoto_light));
                                } else {
                                    iv.setImageDrawable(t_chat.this.getResources().getDrawable(C0627R.drawable.sinfoto));
                                }
                            }
                            String h_aux = p.getString("f2_fcrea", "");
                            if (!h_aux.equals("")) {
                                try {
                                    tv_fcrea.setText(DateFormat.getTimeFormat(t_chat.this).format(new SimpleDateFormat("HHmm").parse(h_aux.substring(6, 10))));
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                            if (t_chat.this.fotos_perfil > 0) {
                                iv.setBackgroundColor(c);
                            } else {
                                iv.setVisibility(8);
                                ll_frase.findViewById(C0627R.id.v_foto).setBackgroundColor(c);
                                ll_frase.findViewById(C0627R.id.v_foto).setVisibility(0);
                            }
                            tv = (TextView) ll_frase.findViewById(C0627R.id.tv_frase_f);
                            String m_aux = p.getString("f2_frase", "");
                            ImageView iv_f;
                            if (m_aux.equals("")) {
                                if (!p.getString("f2_b64", "").equals("")) {
                                    byte[] decodedImage = Base64.decode(p.getString("f2_b64", ""), 0);
                                    try {
                                        BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length).compress(CompressFormat.JPEG, 100, new FileOutputStream(t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(p.getString("f2_idfrase", "")), false)));
                                    } catch (Exception e3) {
                                    }
                                    tv.setVisibility(8);
                                    iv_f = (ImageView) ll_frase.findViewById(C0627R.id.iv_foto_f);
                                    iv_f.setTag("img" + p.getString("f2_idfrase", ""));
                                    if (t_chat.this.c1_esclaro) {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setTag("pb" + p.getString("f2_idfrase", ""));
                                    } else {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f).setTag("pb" + p.getString("f2_idfrase", ""));
                                    }
                                    t_chat.this.mostrar_foto_f(Integer.parseInt(p.getString("f2_idfrase", "")), false, iv_f);
                                    iv_f.setOnClickListener(t_chat.this);
                                    ll_frase.findViewById(C0627R.id.fl_f).setVisibility(0);
                                } else if (t_chat.this.globales.getTempFile_notemp(t_chat.this, Integer.parseInt(p.getString("f2_idfrase", "")), false).exists()) {
                                    tv.setVisibility(8);
                                    iv_f = (ImageView) ll_frase.findViewById(C0627R.id.iv_foto_f);
                                    iv_f.setTag("img" + p.getString("f2_idfrase", ""));
                                    if (p.getString("f2_id", "").equals(t_chat.this.idusu + "")) {
                                        iv_f.setTag(C0627R.id.TAG_ESIMGPROPIA, Integer.valueOf(1));
                                    }
                                    if (t_chat.this.c1_esclaro) {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setTag("pb" + p.getString("f2_idfrase", ""));
                                    } else {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f).setTag("pb" + p.getString("f2_idfrase", ""));
                                    }
                                    t_chat.this.mostrar_foto_f(Integer.parseInt(p.getString("f2_idfrase", "")), false, iv_f);
                                    iv_f.setOnClickListener(t_chat.this);
                                    ll_frase.findViewById(C0627R.id.fl_f).setVisibility(0);
                                } else {
                                    tv.setVisibility(8);
                                    iv_f = (ImageView) ll_frase.findViewById(C0627R.id.iv_foto_f);
                                    iv_f.setTag("img" + p.getString("f2_idfrase", ""));
                                    if (p.getString("f2_id", "").equals(t_chat.this.idusu + "")) {
                                        iv_f.setTag(C0627R.id.TAG_ESIMGPROPIA, Integer.valueOf(1));
                                    }
                                    ll_frase.findViewById(C0627R.id.fl_f).setVisibility(0);
                                    if (t_chat.this.c1_esclaro) {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setVisibility(0);
                                        ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setTag("pb" + p.getString("f2_idfrase", ""));
                                    } else {
                                        ll_frase.findViewById(C0627R.id.pb_foto_f).setVisibility(0);
                                        ll_frase.findViewById(C0627R.id.pb_foto_f).setTag("pb" + p.getString("f2_idfrase", ""));
                                    }
                                    t_chat.access$2084(t_chat.this, "," + p.getString("f2_idfrase", "") + ",");
                                    if (t_chat.this.o_i == null || t_chat.this.o_i.getStatus() != Status.RUNNING) {
                                        t_chat.this.o_i = new obtener_img();
                                        t_chat.this.o_i.execute(new String[0]);
                                    }
                                }
                            } else if (m_aux.length() <= 14 || !m_aux.substring(0, 14).equals("@@idfoto_temp:")) {
                                CharSequence cs = t_chat.addSmileySpans(t_chat.this, m_aux);
                                if (t_chat.this.globales.links_enchats && !t_chat.this.externo) {
                                    tv.setAutoLinkMask(1);
                                    tv.setLinkTextColor(c);
                                }
                                tv.setText(cs);
                                tv.setTextColor(c);
                            } else {
                                String id_temp = m_aux.substring(14, m_aux.indexOf("@@", 2));
                                tv.setVisibility(8);
                                iv_f = (ImageView) ll_frase.findViewById(C0627R.id.iv_foto_f);
                                t_chat.this.mostrar_foto_f(Integer.parseInt(id_temp), true, iv_f);
                                iv_f.setTag("imgtemp" + id_temp);
                                iv_f.setTag(C0627R.id.TAG_ESIMGPROPIA, Integer.valueOf(1));
                                ll_frase.findViewById(C0627R.id.fl_f).setVisibility(0);
                                if (t_chat.this.c1_esclaro) {
                                    ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setVisibility(0);
                                    ll_frase.findViewById(C0627R.id.pb_foto_f_inv).setTag(id_temp);
                                } else {
                                    ll_frase.findViewById(C0627R.id.pb_foto_f).setVisibility(0);
                                    ll_frase.findViewById(C0627R.id.pb_foto_f).setTag(id_temp);
                                }
                            }
                            ll_frase.setTag(p.getString("f2_id", ""));
                            if (p.getBoolean("f2_ultimas", false)) {
                                t_chat.this.llchat.addView(ll_frase, 0);
                            } else {
                                t_chat.this.llchat.addView(ll_frase);
                            }
                            t_chat.this.nfrases = t_chat.this.nfrases + 1;
                            if (t_chat.this.nfrases == 1) {
                                t_chat.this.findViewById(C0627R.id.view_aux_1).setVisibility(8);
                            } else if (t_chat.this.nfrases == 2) {
                                t_chat.this.findViewById(C0627R.id.view_aux_2).setVisibility(8);
                            }
                            ScrollView sc = (ScrollView) t_chat.this.findViewById(C0627R.id.sv_chat);
                            if (t_chat.this.llchat.getMeasuredHeight() <= sc.getScrollY() + sc.getHeight()) {
                                sc.post(new C07611());
                            }
                        }
                    });
                }
            }
        }
    }

    private void mostrar_foto_f(int nfoto, boolean es_temp, ImageView iv) {
        if (iv != null) {
            File file;
            if (es_temp) {
                file = this.globales.getTempFile(this, nfoto);
            } else {
                file = this.globales.getTempFile_notemp(this, nfoto, false);
            }
            try {
                int width;
                Bitmap captureBmp;
                Options bounds = new Options();
                bounds.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), bounds);
                int newHeight;
                if (bounds.outWidth == -1) {
                    width = bounds.outWidth;
                    newHeight = (this.WIDTH_IMGS * bounds.outHeight) / width;
                    iv.getLayoutParams().width = this.WIDTH_IMGS;
                    iv.getLayoutParams().height = newHeight;
                } else {
                    width = bounds.outWidth;
                    newHeight = (this.WIDTH_IMGS * bounds.outHeight) / width;
                    iv.getLayoutParams().width = this.WIDTH_IMGS;
                    iv.getLayoutParams().height = newHeight;
                }
                if (width <= this.WIDTH_IMGS) {
                    captureBmp = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) this.WIDTH_IMGS));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    captureBmp = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                iv.setImageDrawable(new BitmapDrawable(getResources(), captureBmp));
            } catch (Exception e) {
            }
        }
    }

    void mostrar_fecha() {
        if (!this.dia_act.equals("")) {
            boolean ok = true;
            Date d = null;
            try {
                d = new SimpleDateFormat("ddMMyy").parse(this.dia_act);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
            if (ok) {
                TextView tv_aux = new TextView(this);
                LayoutParams params = new LayoutParams(-2, -2);
                params.setMargins(this.TV_MARGIN, this.TV_MARGIN, this.TV_MARGIN, this.TV_MARGIN);
                tv_aux.setLayoutParams(params);
                tv_aux.setPadding(this.TV_PADDING, this.TV_PADDING, this.TV_PADDING, this.TV_PADDING);
                tv_aux.setBackgroundColor(-3355444);
                tv_aux.setTextSize(1, 13.0f);
                tv_aux.setTypeface(Typeface.DEFAULT, 2);
                tv_aux.setTextColor(-12303292);
                Calendar hoy = Calendar.getInstance();
                Calendar d_cal = Calendar.getInstance();
                d_cal.setTime(d);
                if (d_cal.get(1) == hoy.get(1) && d_cal.get(6) == hoy.get(6)) {
                    tv_aux.setText(getResources().getString(C0627R.string.hoy));
                } else {
                    tv_aux.setText(DateFormat.getDateFormat(this).format(d));
                }
                this.llchat.addView(tv_aux, 0);
            }
        }
    }

    private void iniciar_privado(String idusu_conv, String nombre_conv) {
        ((LinearLayout) findViewById(C0627R.id.llchat)).removeAllViews();
        findViewById(C0627R.id.btnfavoritos).setVisibility(8);
        findViewById(C0627R.id.btnperfil).setVisibility(8);
        if (this.globales.fotos_privados) {
            findViewById(C0627R.id.btnfoto).setVisibility(0);
        } else {
            findViewById(C0627R.id.btnfoto).setVisibility(8);
        }
        ((TextView) findViewById(C0627R.id.tv_cabe_priv)).setText(getResources().getString(C0627R.string.chatprivadocon) + " " + nombre_conv);
        findViewById(C0627R.id.ll_cabe_priv).setVisibility(0);
        findViewById(C0627R.id.ll_favnotif).setVisibility(8);
        findViewById(C0627R.id.ll_cabe).setVisibility(8);
        findViewById(C0627R.id.ll_cabe_der).setVisibility(8);
        this.idfrase_masantigua_glob = 0;
        new cargar_ultimas("0", idusu_conv, this.idfrase_masantigua_glob).execute(new String[0]);
        this.nfrases = 0;
        this.dia_act = "";
        findViewById(C0627R.id.view_aux_1).setVisibility(0);
        findViewById(C0627R.id.view_aux_2).setVisibility(0);
        this.es_privado = true;
        Editor e = this.settings.edit();
        e.putString("idprivado", idusu_conv);
        e.putString("nombreprivado", nombre_conv);
        e.commit();
        config.anyadir_privado(this, idusu_conv, nombre_conv);
    }

    private void finalizar_privado(String idprivado) {
        String idprivado_aux = idprivado;
        final AlertDialog d_aux = new Builder(this).setNegativeButton(C0627R.string.no, null).setPositiveButton(C0627R.string.si, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                boolean finalizar_aux = false;
                if (t_chat.this.externo && t_chat.this.extras.containsKey("id_remit")) {
                    t_chat.this.idfrase_masantigua_glob = -1;
                    finalizar_aux = true;
                } else {
                    ((LinearLayout) t_chat.this.findViewById(C0627R.id.llchat)).removeAllViews();
                    t_chat.this.nfrases = 0;
                    t_chat.this.dia_act = "";
                    t_chat.this.findViewById(C0627R.id.view_aux_1).setVisibility(0);
                    t_chat.this.findViewById(C0627R.id.view_aux_2).setVisibility(0);
                    t_chat.this.findViewById(C0627R.id.btnfavoritos).setVisibility(0);
                    t_chat.this.findViewById(C0627R.id.btnperfil).setVisibility(0);
                    if (t_chat.this.fotos_chat == 1) {
                        t_chat.this.findViewById(C0627R.id.btnfoto).setVisibility(0);
                    } else {
                        t_chat.this.findViewById(C0627R.id.btnfoto).setVisibility(8);
                    }
                    t_chat.this.findViewById(C0627R.id.ll_cabe_priv).setVisibility(8);
                    t_chat.this.findViewById(C0627R.id.ll_favnotif).setVisibility(0);
                    if (t_chat.this.ll_cabe_mostrar) {
                        t_chat.this.findViewById(C0627R.id.ll_cabe).setVisibility(0);
                    } else if (!t_chat.this.externo && t_chat.this.secc_act.acceso_a_externo) {
                        t_chat.this.findViewById(C0627R.id.ll_cabe_der).setVisibility(0);
                    }
                    t_chat.this.idfrase_masantigua_glob = 0;
                }
                if (t_chat.this.idfrase_masantigua_glob != -1) {
                    new cargar_ultimas("0", "0", t_chat.this.idfrase_masantigua_glob).execute(new String[0]);
                }
                t_chat.this.es_privado = false;
                Editor e = t_chat.this.settings.edit();
                e.putString("idprivado", "0");
                e.commit();
                if (finalizar_aux) {
                    t_chat.this.finalizar = true;
                    t_chat.this.finalizar();
                    t_chat.this.finish();
                }
            }
        }).setMessage(C0627R.string.confirmar_salirprivado).create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + t_chat.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    public void onBackPressed() {
        String idprivado_aux = this.settings.getString("idprivado", "0");
        if (!idprivado_aux.equals("0")) {
            finalizar_privado(idprivado_aux);
        } else if (this.es_root && !this.atras_pulsado && this.globales.pedir_confirm_exit) {
            this.atras_pulsado = true;
            config.confirmar_exit(this);
        } else {
            super.onBackPressed();
        }
    }

    public static CharSequence addSmileySpans(Context ch, CharSequence your_recieved_message) {
        HashMap<String, Integer> smilyRegexMap = new HashMap();
        smilyRegexMap.put(":-D\\|", Integer.valueOf(C0627R.drawable.smile1));
        smilyRegexMap.put(":-O\\|", Integer.valueOf(C0627R.drawable.smile2));
        smilyRegexMap.put(":-\\(\\|", Integer.valueOf(C0627R.drawable.smile3));
        smilyRegexMap.put(":-\\)\\|", Integer.valueOf(C0627R.drawable.smile4));
        smilyRegexMap.put(";-\\)\\|", Integer.valueOf(C0627R.drawable.smile5));
        smilyRegexMap.put(":-\\|", Integer.valueOf(C0627R.drawable.smile6));
        smilyRegexMap.put("B-D\\|", Integer.valueOf(C0627R.drawable.smile7));
        smilyRegexMap.put("8-\\)", Integer.valueOf(C0627R.drawable.smile8));
        smilyRegexMap.put(":-p\\|", Integer.valueOf(C0627R.drawable.smile9));
        smilyRegexMap.put(":\"\\)", Integer.valueOf(C0627R.drawable.smile10));
        smilyRegexMap.put(":'\\(\\|", Integer.valueOf(C0627R.drawable.smile11));
        smilyRegexMap.put("X\\(", Integer.valueOf(C0627R.drawable.smile12));
        smilyRegexMap.put(":x\\|", Integer.valueOf(C0627R.drawable.smile13));
        smilyRegexMap.put(">:\\)\\|", Integer.valueOf(C0627R.drawable.smile14));
        smilyRegexMap.put("O:\\)\\|", Integer.valueOf(C0627R.drawable.smile15));
        SpannableStringBuilder builder = new SpannableStringBuilder(your_recieved_message);
        for (Entry pairs : smilyRegexMap.entrySet()) {
            Matcher matcher = Pattern.compile((String) pairs.getKey(), 2).matcher(your_recieved_message);
            while (matcher.find()) {
                Bitmap smiley = BitmapFactory.decodeResource(ch.getResources(), ((Integer) pairs.getValue()).intValue());
                Object[] spans = builder.getSpans(matcher.start(), matcher.end(), ImageSpan.class);
                if (spans == null || spans.length == 0) {
                    builder.setSpan(new ImageSpan(ch, smiley), matcher.start(), matcher.end(), 33);
                }
            }
        }
        return builder;
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        this.finishHandler.removeMessages(0);
        Editor editor2 = this.settings2.edit();
        editor2.putInt("accion", 0);
        editor2.commit();
        Editor editor = this.settings.edit();
        editor.putBoolean("activa" + this.idtema, true);
        editor.putBoolean("activa", true);
        editor.commit();
        this.settings.registerOnSharedPreferenceChangeListener(this);
        this.settings2.registerOnSharedPreferenceChangeListener(this);
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    void finalizar() {
        this.es_privado = false;
        Editor editor = this.settings.edit();
        editor.putBoolean("activa" + this.idtema, false);
        editor.putBoolean("activa", false);
        editor.putString("idprivado", "0");
        editor.commit();
        this.settings.unregisterOnSharedPreferenceChangeListener(this);
        this.settings2.unregisterOnSharedPreferenceChangeListener(this);
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
        if (isFinishing()) {
            finalizar();
        } else {
            this.finishHandler.sendEmptyMessageDelayed(0, 300000);
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
        if (this.globales.banners_enchats) {
            this.adView = this.globales.mostrar_banner(this, false);
        }
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
