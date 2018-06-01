package hse.here2;

import android.annotation.TargetApi;
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
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
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
import android.provider.MediaStore.Images.Media;
import android.support.v4.view.ViewCompat;
import android.text.format.DateFormat;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import hse.here2.t_chat.eliminar_usu;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class profile extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    private static final int SELECT_PHOTO = 2;
    private static final int TAKE_PHOTO_CODE = 1;
    AdView adView;
    int altura;
    boolean atras_pulsado = false;
    Bitmap bm_propia;
    String c1;
    boolean c1_esclaro;
    String c2;
    cargar_fotoscoments c_f;
    cargar_fotos_gal c_f_g;
    long captureTime;
    String cbtn;
    String codigo;
    ProgressDialog dialog_cargando;
    String dist;
    int edat = -1;
    enviar_coment env;
    boolean es_root;
    Bundle extras;
    ImageView f1;
    ImageView f2;
    ImageView f3;
    ImageView f4;
    boolean finalizar = false;
    ArrayList<Foto> fotos_glob_a = new ArrayList();
    boolean fotos_glob_a_completo;
    int fotos_perfil;
    config globales;
    String idcoment_ult;
    int idusu;
    String idusu_profile;
    int ind_global;
    ImageView iv;
    ImageView iv_favorito;
    LinearLayout ll_coments;
    LinearLayout ll_likes1;
    LinearLayout ll_likes2;
    LinearLayout ll_likes3;
    LinearLayout ll_likes4;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    obtener_img_g o_i_g;
    File path;
    ProgressBar pb1;
    ProgressBar pb2;
    ProgressBar pb3;
    ProgressBar pb4;
    ProgressBar pb_enviando;
    SharedPreferences settings;
    TextView tv_descr;
    TextView tv_likes1;
    TextView tv_likes2;
    TextView tv_likes3;
    TextView tv_likes4;
    View v_abrir_secc;
    String vfoto;

    class C07131 implements OnItemClickListener {
        C07131() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (profile.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(profile.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(profile.this.globales.menu_a_secciones[position]));
            profile.this.onClick(view);
        }
    }

    class C07142 implements DialogInterface.OnClickListener {
        C07142() {
        }

        public void onClick(DialogInterface dialog, int id) {
            config.mostrar_toast(Toast.makeText(profile.this, profile.this.getResources().getString(C0627R.string.eliminando), 0));
            config.elim_privado(profile.this, profile.this.idusu_profile);
            new eliminar_usu(profile.this.idusu_profile, profile.this.idusu, profile.this.codigo, profile.this).execute(new String[0]);
        }
    }

    class C07197 implements DialogInterface.OnClickListener {
        C07197() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent photoPickerIntent = new Intent("android.intent.action.PICK");
            photoPickerIntent.setType("image/*");
            profile.this.startActivityForResult(photoPickerIntent, 2);
        }
    }

    class C07208 implements DialogInterface.OnClickListener {
        C07208() {
        }

        public void onClick(DialogInterface dialog, int which) {
            profile.this.captureTime = System.currentTimeMillis();
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", Uri.fromFile(profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_temp")));
            try {
                profile.this.startActivityForResult(intent, 1);
            } catch (ActivityNotFoundException e) {
            }
        }
    }

    private class Foto {
        public boolean eliminada;
        public String fcrea;
        public String id;
        public String liked;
        public String nlikes;

        private Foto() {
            this.eliminada = false;
        }
    }

    private class aplic_favorito extends AsyncTask<String, Void, Byte> {
        String modo;

        aplic_favorito(Boolean modo) {
            this.modo = modo.booleanValue() ? "1" : "0";
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/favorito_usu.php?idusu=" + profile.this.idusu + "&c=" + profile.this.codigo + "&idusu_pro=" + profile.this.idusu_profile + "&modo=" + this.modo).openConnection();
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
                valueOf = Byte.valueOf((byte) 1);
                if (conn != null) {
                    conn.disconnect();
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
    }

    private class cargar_coments extends AsyncTask<String, Void, String> {
        private cargar_coments() {
        }

        protected void onPreExecute() {
            profile.this.findViewById(C0627R.id.fl_mascoments).setVisibility(8);
            if (profile.this.c1_esclaro) {
                profile.this.findViewById(C0627R.id.pb_mascoments_inv).setVisibility(0);
            } else {
                profile.this.findViewById(C0627R.id.pb_mascoments).setVisibility(0);
            }
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/coments_profile.php?idusu=" + profile.this.idusu + "&c=" + profile.this.codigo + "&idusu_pro=" + profile.this.idusu_profile + "&idc=" + profile.this.idcoment_ult).openConnection();
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
                stringBuilder = "";
                return stringBuilder;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return stringBuilder;
        }

        protected void onPostExecute(String result) {
            try {
                profile.this.findViewById(C0627R.id.pb_mascoments).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_mascoments_inv).setVisibility(8);
            } catch (Exception e) {
            }
            if (result.contains("ANDROID:OK DATOS:")) {
                int pos1 = result.indexOf("DATOS:") + 6;
                String[] coments_a = result.substring(pos1, result.indexOf(";", pos1)).split(",");
                int i = 0;
                while (!coments_a[i].equals("S") && !coments_a[i].equals("N")) {
                    String[] coment_a = coments_a[i].split("-");
                    profile.this.mostrar_coment(Boolean.valueOf(false), Boolean.valueOf(false), coment_a[0], coment_a[3], coment_a[4], coment_a[2], coment_a[1].replace("@X@", "-").replace("@Y@", ","), coment_a[5], coment_a[6], coment_a[7], coment_a[8], coment_a[9], coment_a[10], coment_a[11]);
                    i++;
                }
                if (profile.this.c_f == null || profile.this.c_f.getStatus() != Status.RUNNING) {
                    profile.this.c_f = new cargar_fotoscoments();
                    profile.this.c_f.execute(new String[0]);
                }
                if (coments_a[i].equals("S")) {
                    profile.this.findViewById(C0627R.id.fl_mascoments).setVisibility(0);
                }
            }
        }
    }

    private class cargar_fotos extends AsyncTask<String, Void, String> {
        boolean ejecutar;

        protected java.lang.String doInBackground(java.lang.String... r13) {
            /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r12 = this;
            r9 = r12.ejecutar;
            if (r9 != 0) goto L_0x0007;
        L_0x0004:
            r9 = "";
        L_0x0006:
            return r9;
        L_0x0007:
            r1 = 0;
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9.<init>();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "http://srv1.androidcreator.com/srv/fotos_profile.php?idusu=";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = r10.idusu;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "&c=";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = r10.codigo;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "&idusu_pro=";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = r10.idusu_profile;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "&idf=";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.ind_global;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            if (r9 <= 0) goto L_0x00b9;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
        L_0x0043:
            r9 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.fotos_glob_a;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r11 = hse.here2.profile.this;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r11 = r11.ind_global;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r11 = r11 + -1;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.get(r11);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = (hse.here2.profile.Foto) r9;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.id;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
        L_0x0055:
            r9 = r10.append(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r2 = r9.toString();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r6 = new java.net.URL;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r6.<init>(r2);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r6.openConnection();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r0 = r9;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r1 = r0;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = 1;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r1.setDoInput(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r1.setConnectTimeout(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r1.setReadTimeout(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = "User-Agent";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "Android Vinebre Software";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r1.setRequestProperty(r9, r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r4 = r1.getInputStream();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r7 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9.<init>(r4);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r7.<init>(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r8.<init>();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
        L_0x0092:
            r5 = r7.readLine();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            if (r5 == 0) goto L_0x00bf;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
        L_0x0098:
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9.<init>();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r5);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r10 = "\n";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r9 = r9.toString();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            r8.append(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            goto L_0x0092;
        L_0x00af:
            r3 = move-exception;
            r9 = "";	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            if (r1 == 0) goto L_0x0006;
        L_0x00b4:
            r1.disconnect();
            goto L_0x0006;
        L_0x00b9:
            r9 = 0;
            r9 = java.lang.Integer.valueOf(r9);	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            goto L_0x0055;	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
        L_0x00bf:
            r9 = r8.toString();	 Catch:{ Exception -> 0x00af, all -> 0x00ca }
            if (r1 == 0) goto L_0x0006;
        L_0x00c5:
            r1.disconnect();
            goto L_0x0006;
        L_0x00ca:
            r9 = move-exception;
            if (r1 == 0) goto L_0x00d0;
        L_0x00cd:
            r1.disconnect();
        L_0x00d0:
            throw r9;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.profile.cargar_fotos.doInBackground(java.lang.String[]):java.lang.String");
        }

        private cargar_fotos() {
        }

        protected void onPreExecute() {
            profile.this.findViewById(C0627R.id.fl_masfotos).setVisibility(4);
            profile.this.findViewById(C0627R.id.fl_menosfotos).setVisibility(4);
            this.ejecutar = true;
            if (profile.this.fotos_glob_a.size() > profile.this.ind_global) {
                profile.this.mostrar_fotos_dearray();
                this.ejecutar = false;
            } else if (profile.this.c1_esclaro) {
                profile.this.findViewById(C0627R.id.pb_masfotos_inv).setVisibility(0);
            } else {
                profile.this.findViewById(C0627R.id.pb_masfotos).setVisibility(0);
            }
        }

        protected void onPostExecute(String result) {
            if (profile.this.findViewById(C0627R.id.pb_masfotos_inv) != null) {
                profile.this.findViewById(C0627R.id.pb_masfotos_inv).setVisibility(4);
                profile.this.findViewById(C0627R.id.pb_masfotos).setVisibility(4);
            }
            if (result.contains("ANDROID:OK DATOS:")) {
                int pos1 = result.indexOf("DATOS:") + 6;
                String[] fotos_a = result.substring(pos1, result.indexOf(";", pos1)).split(",");
                int i = 0;
                while (!fotos_a[i].equals("S") && !fotos_a[i].equals("N")) {
                    String[] foto_a = fotos_a[i].split("-");
                    Foto f_nueva = new Foto();
                    f_nueva.id = foto_a[0];
                    f_nueva.fcrea = foto_a[1];
                    f_nueva.nlikes = foto_a[2];
                    f_nueva.liked = foto_a[3];
                    profile.this.fotos_glob_a.add(f_nueva);
                    i++;
                }
                if (fotos_a[i].equals("N")) {
                    profile.this.fotos_glob_a_completo = true;
                }
                profile.this.mostrar_fotos_dearray();
            }
        }
    }

    private class cargar_fotos_gal extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idf;
        int ind_aux;
        ImageView iv;
        ProgressBar pb_aux;

        private cargar_fotos_gal() {
        }

        protected void onPreExecute() {
            if (profile.this.f1.getVisibility() == 0 && profile.this.f1.getTag(C0627R.id.idaux2).equals("N")) {
                this.iv = profile.this.f1;
                this.ind_aux = 1;
                this.pb_aux = profile.this.pb1;
            } else if (profile.this.f2.getVisibility() == 0 && profile.this.f2.getTag(C0627R.id.idaux2).equals("N")) {
                this.iv = profile.this.f2;
                this.ind_aux = 2;
                this.pb_aux = profile.this.pb2;
            } else if (profile.this.f3.getVisibility() == 0 && profile.this.f3.getTag(C0627R.id.idaux2).equals("N")) {
                this.iv = profile.this.f3;
                this.ind_aux = 3;
                this.pb_aux = profile.this.pb3;
            } else if (profile.this.f4.getVisibility() == 0 && profile.this.f4.getTag(C0627R.id.idaux2).equals("N")) {
                this.iv = profile.this.f4;
                this.ind_aux = 4;
                this.pb_aux = profile.this.pb4;
            }
        }

        protected String doInBackground(String... urls) {
            if (this.iv == null) {
                return "-1";
            }
            this.idf = (String) this.iv.getTag(C0627R.id.idaux1);
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/ususgal/" + profile.this.idusu_profile + "_" + this.idf + "_p.jpg").openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(profile.this.path, "fperfilgal_" + this.idf + ".jpg")));
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
            if (this.iv != null && !result.equals("-1") && ((String) this.iv.getTag(C0627R.id.idaux1)).equals(this.idf)) {
                if (this.pb_aux != null) {
                    this.pb_aux.setVisibility(8);
                }
                this.iv.setTag(C0627R.id.idaux2, "S");
                if (result == "1") {
                    this.iv.setImageBitmap(this.bmImg);
                }
                new cargar_fotos_gal().execute(new String[0]);
            }
        }
    }

    private class cargar_fotoscoments extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idusu_acargar;
        String vfoto_bd;

        private cargar_fotoscoments() {
        }

        protected void onPreExecute() {
            int i = 0;
            while (i < profile.this.ll_coments.getChildCount()) {
                LinearLayout ll_coment = (LinearLayout) profile.this.ll_coments.getChildAt(i);
                String idusucoment = (String) ll_coment.findViewById(C0627R.id.iv_usucoment).getTag(C0627R.id.idaux1);
                this.vfoto_bd = (String) ll_coment.findViewById(C0627R.id.iv_usucoment).getTag(C0627R.id.idaux2);
                if (this.vfoto_bd == null || this.vfoto_bd.equals("0") || this.vfoto_bd.equals(profile.this.settings.getString("fperfil_" + idusucoment, "0"))) {
                    i++;
                } else {
                    this.idusu_acargar = idusucoment;
                    return;
                }
            }
        }

        protected String doInBackground(String... urls) {
            if (this.idusu_acargar == null) {
                return "-1";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/usus/" + this.idusu_acargar + "_1_p.jpg?v=" + this.vfoto_bd).openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(profile.this.path, "fperfil_" + this.idusu_acargar + ".jpg")));
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
            if (profile.this.ll_coments != null && this.idusu_acargar != null && !result.equals("-1")) {
                if (result == "1") {
                    Editor e = profile.this.settings.edit();
                    e.putString("fperfil_" + this.idusu_acargar, this.vfoto_bd);
                    e.commit();
                }
                int childCount = profile.this.ll_coments.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    String idusu_aux = (String) profile.this.ll_coments.getChildAt(i).findViewById(C0627R.id.iv_usucoment).getTag(C0627R.id.idaux1);
                    if (idusu_aux != null && idusu_aux.equals(this.idusu_acargar)) {
                        profile.this.ll_coments.getChildAt(i).findViewById(C0627R.id.iv_usucoment).setTag(C0627R.id.idaux2, this.vfoto_bd);
                        if (result == "1") {
                            ((ImageView) profile.this.ll_coments.getChildAt(i).findViewById(C0627R.id.iv_usucoment)).setImageBitmap(this.bmImg);
                        }
                    }
                }
                new cargar_fotoscoments().execute(new String[0]);
            }
        }
    }

    private class cargarprofile extends AsyncTask<String, Void, String> {
        String condist;

        private cargarprofile() {
        }

        protected void onPreExecute() {
            this.condist = "0";
            if (profile.this.extras.getInt("p_dist") > 0 && !profile.this.extras.containsKey("dist")) {
                this.condist = "1";
            }
            if (profile.this.c1_esclaro) {
                profile.this.findViewById(C0627R.id.pb_masfotos_inv).setVisibility(0);
                profile.this.findViewById(C0627R.id.pb_mascoments_inv).setVisibility(0);
                return;
            }
            profile.this.findViewById(C0627R.id.pb_masfotos).setVisibility(0);
            profile.this.findViewById(C0627R.id.pb_mascoments).setVisibility(0);
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/obtener_profile.php?idusu=" + profile.this.idusu + "&c=" + profile.this.codigo + "&idusu_pro=" + profile.this.idusu_profile + "&vdescr=" + profile.this.settings.getString("vdescr_" + profile.this.idusu_profile, "0") + "&idcoments=" + (((profile.this.settings.getString("idcom0_" + profile.this.idusu_profile, "") + "," + profile.this.settings.getString("idcom1_" + profile.this.idusu_profile, "")) + "," + profile.this.settings.getString("idcom2_" + profile.this.idusu_profile, "")) + "," + profile.this.settings.getString("idcom3_" + profile.this.idusu_profile, "")) + "&condist=" + this.condist).openConnection();
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
                stringBuilder = "";
                return stringBuilder;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return stringBuilder;
        }

        protected void onPostExecute(String result) {
            try {
                profile.this.findViewById(C0627R.id.pb_masfotos_inv).setVisibility(4);
                profile.this.findViewById(C0627R.id.pb_masfotos).setVisibility(4);
                profile.this.findViewById(C0627R.id.pb_mascoments_inv).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_mascoments).setVisibility(8);
            } catch (Exception e) {
            }
            if (result.contains("ANDROID:OK DATOSUSU:")) {
                int i;
                Editor e2 = profile.this.settings.edit();
                int pos1 = result.indexOf("DATOSUSU:") + 9;
                int pos2 = result.indexOf(";", pos1);
                if (this.condist.equals("1")) {
                    String dist_aux = result.substring(pos1, pos2);
                    if (dist_aux.equals("-1")) {
                        profile.this.dist = null;
                    } else {
                        profile.this.dist = dist_aux;
                    }
                    profile.this.mostrar_subtit();
                }
                pos1 = pos2 + 1;
                pos2 = result.indexOf(";", pos1);
                if (profile.this.extras.getInt("p_descr") > 0) {
                    String descr_cad = result.substring(pos1, pos2);
                    if (!descr_cad.equals("")) {
                        int pos3 = descr_cad.indexOf(",");
                        String vdescr = descr_cad.substring(0, pos3);
                        String descr = descr_cad.substring(pos3 + 1).replace("@X@", ",");
                        e2.putString("vdescr_" + profile.this.idusu_profile, vdescr);
                        e2.putString("descr_" + profile.this.idusu_profile, descr);
                        e2.commit();
                        profile.this.tv_descr.setText(descr);
                        profile.this.tv_descr.setVisibility(0);
                    }
                }
                if (profile.this.fotos_perfil == 0 || profile.this.vfoto.equals("0")) {
                    profile.this.mostrar_viewsaux();
                }
                pos1 = pos2 + 1;
                pos2 = result.indexOf(";", pos1);
                if (profile.this.extras.getBoolean("galeria")) {
                    String[] fotos_a = result.substring(pos1, pos2).split(",");
                    for (i = 0; i < 4; i++) {
                        e2.remove("idf" + i + "_" + profile.this.idusu_profile);
                        e2.remove("f" + i + "_fcrea_" + profile.this.idusu_profile);
                        e2.remove("f" + i + "_nlikes_" + profile.this.idusu_profile);
                        e2.remove("f" + i + "_liked_" + profile.this.idusu_profile);
                    }
                    i = 0;
                    while (!fotos_a[i].equals("S") && !fotos_a[i].equals("N")) {
                        String[] foto_a = fotos_a[i].split("-");
                        e2.putString("idf" + i + "_" + profile.this.idusu_profile, foto_a[0]);
                        e2.putString("f" + i + "_fcrea_" + profile.this.idusu_profile, foto_a[1]);
                        e2.putString("f" + i + "_nlikes_" + profile.this.idusu_profile, foto_a[2]);
                        e2.putString("f" + i + "_liked_" + profile.this.idusu_profile, foto_a[3]);
                        Foto f_nueva = new Foto();
                        f_nueva.id = foto_a[0];
                        f_nueva.fcrea = foto_a[1];
                        f_nueva.nlikes = foto_a[2];
                        f_nueva.liked = foto_a[3];
                        profile.this.fotos_glob_a.add(f_nueva);
                        i++;
                    }
                    e2.commit();
                    profile.this.mostrar_fotos();
                    profile.this.f1.setOnClickListener(profile.this);
                    profile.this.f2.setOnClickListener(profile.this);
                    profile.this.f3.setOnClickListener(profile.this);
                    profile.this.f4.setOnClickListener(profile.this);
                    profile.this.c_f_g = new cargar_fotos_gal();
                    profile.this.c_f_g.execute(new String[0]);
                    if (fotos_a[i].equals("S")) {
                        profile.this.findViewById(C0627R.id.fl_masfotos).setVisibility(0);
                    } else {
                        profile.this.fotos_glob_a_completo = true;
                    }
                }
                pos1 = pos2 + 1;
                pos2 = result.indexOf(";", pos1);
                if (profile.this.extras.getString("coments").equals("1") && profile.this.extras.getBoolean("coments_chat")) {
                    String[] coments_a = result.substring(pos1, pos2).split(",");
                    for (i = 0; i < 4; i++) {
                        e2.putString("idcom" + i + "_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("idcom" + i + "_" + profile.this.idusu_profile, ""));
                        e2.putString("com" + i + "_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_" + profile.this.idusu_profile, ""));
                        e2.putString("com" + i + "_fcrea_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_fcrea_" + profile.this.idusu_profile, ""));
                        e2.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_idusucrea_" + profile.this.idusu_profile, ""));
                        e2.putString("com" + i + "_nombre_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_nombre_" + profile.this.idusu_profile, ""));
                        e2.putString("com" + i + "_vfoto_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_vfoto_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_privados_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_privados_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_fnacd_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_fnacd_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_fnacm_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_fnacm_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_fnaca_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_fnaca_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_sexo_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_sexo_" + profile.this.idusu_profile, "0"));
                        e2.putString("com" + i + "_coments_" + profile.this.idusu_profile + "_copia", profile.this.settings.getString("com" + i + "_coments_" + profile.this.idusu_profile, "0"));
                        e2.remove("idcom" + i + "_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_fcrea_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_idusucrea_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_nombre_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_vfoto_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_privados_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_fnacd_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_fnacm_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_fnaca_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_sexo_" + profile.this.idusu_profile);
                        e2.remove("com" + i + "_coments_" + profile.this.idusu_profile);
                    }
                    e2.commit();
                    i = 0;
                    while (!coments_a[i].equals("S") && !coments_a[i].equals("N")) {
                        String[] coment_a = coments_a[i].split("-");
                        if (coment_a.length > 1) {
                            e2.putString("idcom" + i + "_" + profile.this.idusu_profile, coment_a[0]);
                            e2.putString("com" + i + "_" + profile.this.idusu_profile, coment_a[1].replace("@X@", "-").replace("@Y@", ","));
                            e2.putString("com" + i + "_fcrea_" + profile.this.idusu_profile, coment_a[2]);
                            e2.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile, coment_a[3]);
                            e2.putString("com" + i + "_nombre_" + profile.this.idusu_profile, coment_a[4]);
                            e2.putString("com" + i + "_vfoto_" + profile.this.idusu_profile, coment_a[5]);
                            e2.putString("com" + i + "_privados_" + profile.this.idusu_profile, coment_a[6]);
                            e2.putString("com" + i + "_fnacd_" + profile.this.idusu_profile, coment_a[7]);
                            e2.putString("com" + i + "_fnacm_" + profile.this.idusu_profile, coment_a[8]);
                            e2.putString("com" + i + "_fnaca_" + profile.this.idusu_profile, coment_a[9]);
                            e2.putString("com" + i + "_sexo_" + profile.this.idusu_profile, coment_a[10]);
                            e2.putString("com" + i + "_coments_" + profile.this.idusu_profile, coment_a[11]);
                        } else {
                            boolean encontrado = false;
                            int j = 0;
                            while (j < 5) {
                                if (profile.this.settings.getString("idcom" + j + "_" + profile.this.idusu_profile + "_copia", "").equals(coment_a[0])) {
                                    encontrado = true;
                                    break;
                                }
                                j++;
                            }
                            if (encontrado) {
                                e2.putString("idcom" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("idcom" + j + "_" + profile.this.idusu_profile + "_copia", ""));
                                e2.putString("com" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_" + profile.this.idusu_profile + "_copia", ""));
                                e2.putString("com" + i + "_fcrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_fcrea_" + profile.this.idusu_profile + "_copia", ""));
                                e2.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_idusucrea_" + profile.this.idusu_profile + "_copia", ""));
                                e2.putString("com" + i + "_nombre_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_nombre_" + profile.this.idusu_profile + "_copia", ""));
                                e2.putString("com" + i + "_vfoto_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_vfoto_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_privados_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_privados_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_fnacd_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_fnacd_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_fnacm_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_fnacm_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_fnaca_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_fnaca_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_sexo_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_sexo_" + profile.this.idusu_profile + "_copia", "0"));
                                e2.putString("com" + i + "_coments_" + profile.this.idusu_profile, profile.this.settings.getString("com" + j + "_coments_" + profile.this.idusu_profile + "_copia", "0"));
                            }
                        }
                        i++;
                    }
                    e2.commit();
                    profile.this.mostrar_coments(false);
                    new cargar_fotoscoments().execute(new String[0]);
                    if (coments_a[i].equals("S")) {
                        profile.this.findViewById(C0627R.id.fl_mascoments).setVisibility(0);
                    }
                }
                pos1 = pos2 + 1;
                pos2 = result.indexOf(";", pos1);
                profile.this.cambiar_favorito(result.substring(pos1, pos2).equals("1"));
                pos1 = pos2 + 1;
                pos2 = result.indexOf(";", pos1);
                if (!profile.this.idusu_profile.equals(profile.this.idusu + "") && result.substring(pos1, pos2).equals("1")) {
                    profile.this.findViewById(C0627R.id.iv_conectado).setVisibility(0);
                }
            }
        }
    }

    private class elim_coment extends AsyncTask<String, Void, Byte> {
        String idcoment;

        elim_coment(String idcoment) {
            this.idcoment = idcoment;
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/eliminar_coment.php?idusu=" + profile.this.idusu + "&c=" + profile.this.codigo + "&idc=" + this.idcoment).openConnection();
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
                valueOf = Byte.valueOf((byte) 1);
                if (conn != null) {
                    conn.disconnect();
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
    }

    private class enviar_coment extends AsyncTask<String, Void, String> {
        String coment;

        public enviar_coment(String coment) {
            this.coment = coment;
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/enviar_coment.php?idusu=" + profile.this.idusu + "&c=" + profile.this.codigo + "&idusu_pro=" + profile.this.idusu_profile);
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("m", new StringBody(URLEncoder.encode(this.coment, "UTF-8")));
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
            Builder builder = new Builder(profile.this);
            try {
                profile.this.pb_enviando.setVisibility(8);
                int pos1;
                String idc_nuevo;
                String date_str;
                Editor e;
                int i;
                AlertDialog d_aux;
                final AlertDialog alertDialog;
                if (profile.this.c1_esclaro || VERSION.SDK_INT <= 12) {
                    ((TextView) profile.this.findViewById(C0627R.id.et_coment_self)).setTextColor(ViewCompat.MEASURED_STATE_MASK);
                    if (result.indexOf("ANDROID:OK ID:") == -1) {
                        pos1 = result.indexOf("ANDROID:OK ID:") + 14;
                        idc_nuevo = result.substring(pos1, result.indexOf("-", pos1));
                        ((EditText) profile.this.findViewById(C0627R.id.et_coment_self)).setText("");
                        date_str = config.convertir_fecha_inv(new SimpleDateFormat("ddMMyyHHmm").format(new Date()));
                        profile.this.mostrar_coment(Boolean.valueOf(false), Boolean.valueOf(true), idc_nuevo, profile.this.idusu + "", profile.this.getResources().getString(C0627R.string.tu), date_str, this.coment, "0", "", "", "", "", "", "");
                        e = profile.this.settings.edit();
                        for (i = 3; i > 0; i--) {
                            e.putString("idcom" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("idcom" + (i - 1) + "_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_fcrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fcrea_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_idusucrea_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_nombre_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_nombre_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_vfoto_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_vfoto_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_privados_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_privados_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnacd_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnacd_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnacm_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnacm_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnaca_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnaca_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_sexo_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_sexo_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_coments_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_coments_" + profile.this.idusu_profile, "0"));
                        }
                        e.putString("idcom0_" + profile.this.idusu_profile, idc_nuevo);
                        e.putString("com0_" + profile.this.idusu_profile, this.coment);
                        e.putString("com0_fcrea_" + profile.this.idusu_profile, date_str);
                        e.putString("com0_idusucrea_" + profile.this.idusu_profile, profile.this.idusu + "");
                        e.putString("com0_nombre_" + profile.this.idusu_profile, profile.this.getResources().getString(C0627R.string.tu));
                        e.putString("com0_vfoto_" + profile.this.idusu_profile, "0");
                        e.commit();
                    }
                    if (result.indexOf("ANDROID:KO MOTIVO:NOADMITE") == -1) {
                        d_aux = builder.setCancelable(false).setPositiveButton(profile.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.noadmitecoments).create();
                        if (!profile.this.cbtn.equals("")) {
                            alertDialog = d_aux;
                            d_aux.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface arg0) {
                                    alertDialog.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                                }
                            });
                        }
                        d_aux.show();
                        try {
                            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                            return;
                        } catch (Exception e2) {
                            return;
                        }
                    }
                    d_aux = builder.setCancelable(false).setPositiveButton(profile.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                    if (!profile.this.cbtn.equals("")) {
                        alertDialog = d_aux;
                        d_aux.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                alertDialog.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                            }
                        });
                    }
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                        return;
                    } catch (Exception e3) {
                        return;
                    }
                }
                ((TextView) profile.this.findViewById(C0627R.id.et_coment_self)).setTextColor(-1);
                if (result.indexOf("ANDROID:OK ID:") == -1) {
                    if (result.indexOf("ANDROID:KO MOTIVO:NOADMITE") == -1) {
                        d_aux = builder.setCancelable(false).setPositiveButton(profile.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                        if (profile.this.cbtn.equals("")) {
                            alertDialog = d_aux;
                            d_aux.setOnShowListener(/* anonymous class already generated */);
                        }
                        d_aux.show();
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                        return;
                    }
                    d_aux = builder.setCancelable(false).setPositiveButton(profile.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.noadmitecoments).create();
                    if (profile.this.cbtn.equals("")) {
                        alertDialog = d_aux;
                        d_aux.setOnShowListener(/* anonymous class already generated */);
                    }
                    d_aux.show();
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    return;
                }
                pos1 = result.indexOf("ANDROID:OK ID:") + 14;
                idc_nuevo = result.substring(pos1, result.indexOf("-", pos1));
                ((EditText) profile.this.findViewById(C0627R.id.et_coment_self)).setText("");
                date_str = config.convertir_fecha_inv(new SimpleDateFormat("ddMMyyHHmm").format(new Date()));
                profile.this.mostrar_coment(Boolean.valueOf(false), Boolean.valueOf(true), idc_nuevo, profile.this.idusu + "", profile.this.getResources().getString(C0627R.string.tu), date_str, this.coment, "0", "", "", "", "", "", "");
                e = profile.this.settings.edit();
                for (i = 3; i > 0; i--) {
                    e.putString("idcom" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("idcom" + (i - 1) + "_" + profile.this.idusu_profile, ""));
                    e.putString("com" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_" + profile.this.idusu_profile, ""));
                    e.putString("com" + i + "_fcrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fcrea_" + profile.this.idusu_profile, ""));
                    e.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_idusucrea_" + profile.this.idusu_profile, ""));
                    e.putString("com" + i + "_nombre_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_nombre_" + profile.this.idusu_profile, ""));
                    e.putString("com" + i + "_vfoto_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_vfoto_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_privados_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_privados_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_fnacd_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnacd_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_fnacm_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnacm_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_fnaca_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_fnaca_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_sexo_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_sexo_" + profile.this.idusu_profile, "0"));
                    e.putString("com" + i + "_coments_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i - 1) + "_coments_" + profile.this.idusu_profile, "0"));
                }
                e.putString("idcom0_" + profile.this.idusu_profile, idc_nuevo);
                e.putString("com0_" + profile.this.idusu_profile, this.coment);
                e.putString("com0_fcrea_" + profile.this.idusu_profile, date_str);
                e.putString("com0_idusucrea_" + profile.this.idusu_profile, profile.this.idusu + "");
                e.putString("com0_nombre_" + profile.this.idusu_profile, profile.this.getResources().getString(C0627R.string.tu));
                e.putString("com0_vfoto_" + profile.this.idusu_profile, "0");
                e.commit();
            } catch (Exception e4) {
            }
        }
    }

    private class enviar_foto extends AsyncTask<String, Void, String> {
        AlertDialog d_aux;

        private enviar_foto() {
        }

        protected void onPreExecute() {
            this.d_aux = new Builder(profile.this).setCancelable(false).setMessage(C0627R.string.enviando).create();
            this.d_aux.show();
            try {
                ((TextView) this.d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e) {
            }
        }

        protected String doInBackground(String... urls) {
            try {
                int width;
                int height;
                Bitmap bm;
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 60000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/guardar_fotogal.php?idusu=" + profile.this.idusu + "&c=" + profile.this.settings.getString("cod", ""));
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                File file = profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_temp");
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
                    bm = Media.getBitmap(profile.this.getContentResolver(), Uri.fromFile(file));
                } else {
                    config hse_here2_config = profile.this.globales;
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 300, HttpStatus.SC_BAD_REQUEST)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    bm = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bm.compress(CompressFormat.JPEG, 75, bos);
                multipartEntity.addPart("foto1", new ByteArrayBody(bos.toByteArray(), "temporal.jpg"));
                postRequest.setEntity(multipartEntity);
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.execute(postRequest).getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();
                while (true) {
                    String sResponse = reader.readLine();
                    if (sResponse == null) {
                        break;
                    }
                    s = s.append(sResponse);
                }
                if (s.indexOf("ANDROID:OK") != -1) {
                    return s.toString();
                }
                return "";
            } catch (Exception e) {
                return "";
            }
        }

        protected void onPostExecute(String result) {
            if (this.d_aux != null && this.d_aux.isShowing()) {
                this.d_aux.cancel();
            }
            boolean ok = false;
            int pos = result.indexOf("ANDROID:OK ID:");
            if (pos != -1) {
                pos += 14;
                int pos2 = result.indexOf("-", pos);
                if (pos2 != -1) {
                    Foto f = new Foto();
                    f.id = result.substring(pos, pos2);
                    f.fcrea = DateFormat.format("ddMMyyHHmm", new Date()).toString();
                    f.nlikes = "0";
                    f.liked = "0";
                    profile.this.fotos_glob_a.add(0, f);
                    profile.this.ind_global = 0;
                    try {
                        config hse_here2_config = profile.this.globales;
                        config.copy(profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_temp"), new File(profile.this.path, "fperfilgal_" + f.id + ".jpg"));
                        hse_here2_config = profile.this.globales;
                        config.copy(profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_temp"), new File(profile.this.path, "fperfilgal_" + f.id + "_g.jpg"));
                    } catch (Exception e) {
                    }
                    profile.this.mostrar_fotos_dearray();
                    Editor e2 = profile.this.settings.edit();
                    for (int i = 3; i > 0; i--) {
                        e2.putString("idf" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("idf" + (i - 1) + "_" + profile.this.idusu_profile, ""));
                        e2.putString("f" + i + "_fcrea_" + profile.this.idusu_profile, profile.this.settings.getString("f" + (i - 1) + "_fcrea_" + profile.this.idusu_profile, ""));
                        e2.putString("f" + i + "_nlikes_" + profile.this.idusu_profile, profile.this.settings.getString("f" + (i - 1) + "_nlikes_" + profile.this.idusu_profile, ""));
                        e2.putString("f" + i + "_liked_" + profile.this.idusu_profile, profile.this.settings.getString("f" + (i - 1) + "_liked_" + profile.this.idusu_profile, ""));
                    }
                    e2.putString("idf0_" + profile.this.idusu_profile, f.id);
                    e2.putString("f0_fcrea_" + profile.this.idusu_profile, f.fcrea);
                    e2.putString("f0_nlikes_" + profile.this.idusu_profile, f.nlikes);
                    e2.putString("f0_liked_" + profile.this.idusu_profile, f.liked);
                    e2.commit();
                    ok = true;
                }
            }
            if (!ok) {
                final AlertDialog d_aux2 = new Builder(profile.this).setCancelable(false).setPositiveButton(profile.this.getString(C0627R.string.aceptar), null).setMessage(C0627R.string.error_http).create();
                if (!profile.this.cbtn.equals("")) {
                    d_aux2.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux2.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                        }
                    });
                }
                d_aux2.show();
                try {
                    ((TextView) d_aux2.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e3) {
                }
            }
        }
    }

    private class obtener_foto extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idusu_acargar;

        private obtener_foto() {
        }

        protected void onPreExecute() {
            this.idusu_acargar = profile.this.idusu_profile;
        }

        protected String doInBackground(String... urls) {
            if (this.idusu_acargar == null) {
                return "0";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/usus/" + this.idusu_acargar + "_1.jpg?v=" + profile.this.vfoto).openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(profile.this.path, "fperfil_" + this.idusu_acargar + "_g.jpg")));
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
            if (result.equals("1")) {
                Editor e = profile.this.settings.edit();
                e.putString("fperfil_" + this.idusu_acargar + "_g", profile.this.vfoto);
                e.commit();
            }
            profile.this.findViewById(C0627R.id.pb_foto).setVisibility(8);
            profile.this.findViewById(C0627R.id.pb_foto_inv).setVisibility(8);
            if (result.equals("1")) {
                profile.this.iv.setImageBitmap(this.bmImg);
            }
        }
    }

    private class obtener_img_g extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        String idimg_acargar;
        int ind;

        public obtener_img_g(String id, int ind) {
            this.idimg_acargar = id;
            this.ind = ind;
        }

        protected void onPreExecute() {
            int pb;
            int pb_inv;
            int ind_aux = this.ind % 4;
            if (ind_aux == 0) {
                pb = C0627R.id.pb_1;
                pb_inv = C0627R.id.pb_1_inv;
            } else if (ind_aux == 1) {
                pb = C0627R.id.pb_2;
                pb_inv = C0627R.id.pb_2_inv;
            } else if (ind_aux == 2) {
                pb = C0627R.id.pb_3;
                pb_inv = C0627R.id.pb_3_inv;
            } else if (ind_aux == 3) {
                pb = C0627R.id.pb_4;
                pb_inv = C0627R.id.pb_4_inv;
            } else {
                return;
            }
            if (profile.this.c1_esclaro) {
                profile.this.findViewById(pb_inv).setVisibility(0);
            } else {
                profile.this.findViewById(pb).setVisibility(0);
            }
        }

        protected String doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/ususgal/" + profile.this.idusu_profile + "_" + this.idimg_acargar + ".jpg");
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
                        this.bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_" + this.idimg_acargar + "_g.jpg")));
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
                profile.this.findViewById(C0627R.id.pb_1).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_1_inv).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_2).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_2_inv).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_3).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_3_inv).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_4).setVisibility(8);
                profile.this.findViewById(C0627R.id.pb_4_inv).setVisibility(8);
            } catch (Exception e) {
            }
            Intent i = new Intent(profile.this, fotogal.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + profile.this.globales.getTempFile_libre(profile.this, "fperfilgal_" + this.idimg_acargar + "_g.jpg").getAbsolutePath());
            i.putExtra("nlikes", ((Foto) profile.this.fotos_glob_a.get(this.ind)).nlikes);
            i.putExtra("liked", ((Foto) profile.this.fotos_glob_a.get(this.ind)).liked);
            i.putExtra("fcrea", ((Foto) profile.this.fotos_glob_a.get(this.ind)).fcrea);
            i.putExtra("idf", this.idimg_acargar);
            i.putExtra("indf", this.ind);
            i.putExtra("idusu_profile", profile.this.idusu_profile);
            profile.this.startActivityForResult(i, 0);
        }
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        File file;
        Options bounds;
        int width;
        int height;
        boolean withinBounds;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.c1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
        this.c2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
        this.c1_esclaro = config.esClaro("#" + this.c1);
        this.cbtn = config.aplicar_color_dialog(this.c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !this.c1_esclaro) {
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
        setContentView(C0627R.layout.profile);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.codigo = this.settings.getString("cod", "");
        this.idusu_profile = this.extras.getString("id");
        this.path = new File(Environment.getExternalStorageDirectory(), getPackageName());
        banner();
        if (!this.c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + this.c2)}));
        }
        Display display = getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            this.altura = size.y;
        } else {
            this.altura = display.getHeight();
        }
        ((TextView) findViewById(C0627R.id.tv_tit)).setText(this.extras.getString("nombre"));
        this.pb_enviando = (ProgressBar) findViewById(C0627R.id.pb_chat_env_inv);
        if (!this.c1_esclaro && VERSION.SDK_INT > 12) {
            this.pb_enviando = (ProgressBar) findViewById(C0627R.id.pb_chat_env);
            if (VERSION.SDK_INT > 20) {
                config.progress_color(this.pb_enviando, this.globales.c_icos);
            }
        }
        int fnac_d = Integer.parseInt(this.extras.getString("fnac_d"));
        int fnac_m = Integer.parseInt(this.extras.getString("fnac_m"));
        int fnac_a = Integer.parseInt(this.extras.getString("fnac_a"));
        if (this.extras.getInt("p_fnac") > 0 && fnac_d > 0 && fnac_m > 0 && fnac_a > 0) {
            Calendar c = Calendar.getInstance();
            c.set(fnac_a, fnac_m - 1, fnac_d);
            Calendar act = Calendar.getInstance();
            this.edat = act.get(1) - c.get(1);
            if (c.get(2) > act.get(2) || (c.get(2) == act.get(2) && c.get(5) > act.get(5))) {
                this.edat--;
            }
        }
        if (this.extras.getInt("p_dist") > 0 && this.extras.containsKey("dist") && !this.extras.getString("dist").equals("")) {
            this.dist = this.extras.getString("dist");
        }
        if (!this.idusu_profile.equals(this.idusu + "") && this.extras.containsKey("conectado") && this.extras.getBoolean("conectado")) {
            findViewById(C0627R.id.iv_conectado).setVisibility(0);
        }
        mostrar_subtit();
        this.tv_descr = (TextView) findViewById(C0627R.id.descr);
        if (this.extras.getInt("p_descr") > 0 && !this.settings.getString("descr_" + this.idusu_profile, "").equals("")) {
            this.tv_descr.setText(this.settings.getString("descr_" + this.idusu_profile, ""));
            this.tv_descr.setVisibility(0);
        }
        this.vfoto = this.extras.getString("vfoto");
        this.fotos_perfil = this.extras.getInt("fotos_perfil");
        this.iv = (ImageView) findViewById(C0627R.id.iv_usu);
        if (this.fotos_perfil == 0 || this.vfoto.equals("0")) {
            this.iv.setVisibility(8);
            mostrar_viewsaux();
        } else if (this.idusu_profile.equals(this.idusu + "")) {
            try {
                this.iv.setImageBitmap(Media.getBitmap(getContentResolver(), Uri.fromFile(this.globales.getTempFile(this, 1))));
            } catch (Exception e) {
            }
        } else {
            try {
                int width_max;
                int height_max;
                Bitmap bm_aux;
                file = new File(this.path, "fperfil_" + this.idusu_profile + "_g.jpg");
                bounds = new Options();
                bounds.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), bounds);
                if (bounds.outWidth == -1) {
                    width_max = (int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    height_max = (int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                } else {
                    width_max = (int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    height_max = (int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                }
                withinBounds = width <= width_max && height <= height_max;
                if (withinBounds) {
                    bm_aux = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, width_max, height_max)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    bm_aux = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                this.iv.setImageBitmap(bm_aux);
            } catch (Exception e2) {
            }
            if (!this.settings.getString("fperfil_" + this.idusu_profile + "_g", "").equals(this.vfoto)) {
                if (this.c1_esclaro) {
                    findViewById(C0627R.id.pb_foto_inv).setVisibility(0);
                } else {
                    findViewById(C0627R.id.pb_foto).setVisibility(0);
                }
                new obtener_foto().execute(new String[0]);
            }
        }
        if (this.fotos_perfil > 0) {
            file = this.globales.getTempFile(this, 1);
            try {
                bounds = new Options();
                bounds.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), bounds);
                if (bounds.outWidth == -1) {
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                } else {
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                }
                withinBounds = width <= 75 && height <= 100;
                if (withinBounds) {
                    this.bm_propia = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 75, 100)));
                    resample = new Options();
                    resample.inSampleSize = sampleSize;
                    this.bm_propia = BitmapFactory.decodeFile(file.getPath(), resample);
                }
            } catch (Exception e3) {
            }
        }
        this.ll_coments = (LinearLayout) findViewById(C0627R.id.ll_coments);
        if (this.extras.getString("coments").equals("1") && this.extras.getBoolean("coments_chat")) {
            int c2 = ViewCompat.MEASURED_STATE_MASK;
            if (!this.c1_esclaro) {
                c2 = -1;
            }
            ((TextView) findViewById(C0627R.id.tv_coments)).setTextColor(c2);
            if (config.esClaro("#" + this.globales.c_icos)) {
                findViewById(C0627R.id.iv_enviarcom_fondo_n).setVisibility(0);
            } else {
                findViewById(C0627R.id.iv_enviarcom_fondo_b).setVisibility(0);
            }
            Drawable d = getResources().getDrawable(C0627R.drawable.btn_enviar);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_enviarcoment)).setImageDrawable(d);
            if (config.esClaro("#" + this.globales.c_icos)) {
                findViewById(C0627R.id.iv_mascoments_fondo_n).setVisibility(0);
            } else {
                findViewById(C0627R.id.iv_mascoments_fondo_b).setVisibility(0);
            }
            d = getResources().getDrawable(C0627R.drawable.mas_btn);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_mascoments)).setImageDrawable(d);
            if (VERSION.SDK_INT > 20) {
                config.edittext_color((EditText) findViewById(C0627R.id.et_coment_self), Boolean.valueOf(!this.c1_esclaro), this.globales.c_icos);
            } else if (VERSION.SDK_INT > 12) {
                ((EditText) findViewById(C0627R.id.et_coment_self)).setTextColor(c2);
            }
            findViewById(C0627R.id.iv_enviarcoment).setOnClickListener(this);
            findViewById(C0627R.id.iv_mascoments).setOnClickListener(this);
            ((ImageView) findViewById(C0627R.id.iv_usucoment_self)).setImageBitmap(this.bm_propia);
            mostrar_coments(true);
        } else {
            findViewById(C0627R.id.tv_coments).setVisibility(8);
            findViewById(C0627R.id.rl_enviarcoment).setVisibility(8);
            this.ll_coments.setVisibility(8);
        }
        this.ind_global = 0;
        this.fotos_glob_a_completo = false;
        this.f1 = (ImageView) findViewById(C0627R.id.iv_f1);
        this.f2 = (ImageView) findViewById(C0627R.id.iv_f2);
        this.f3 = (ImageView) findViewById(C0627R.id.iv_f3);
        this.f4 = (ImageView) findViewById(C0627R.id.iv_f4);
        this.ll_likes1 = (LinearLayout) findViewById(C0627R.id.ll_likes1);
        this.ll_likes2 = (LinearLayout) findViewById(C0627R.id.ll_likes2);
        this.ll_likes3 = (LinearLayout) findViewById(C0627R.id.ll_likes3);
        this.ll_likes4 = (LinearLayout) findViewById(C0627R.id.ll_likes4);
        this.tv_likes1 = (TextView) findViewById(C0627R.id.tv_likes1);
        this.tv_likes2 = (TextView) findViewById(C0627R.id.tv_likes2);
        this.tv_likes3 = (TextView) findViewById(C0627R.id.tv_likes3);
        this.tv_likes4 = (TextView) findViewById(C0627R.id.tv_likes4);
        if (this.c1_esclaro) {
            this.pb1 = (ProgressBar) findViewById(C0627R.id.pb_1_inv);
            this.pb2 = (ProgressBar) findViewById(C0627R.id.pb_2_inv);
            this.pb3 = (ProgressBar) findViewById(C0627R.id.pb_3_inv);
            this.pb4 = (ProgressBar) findViewById(C0627R.id.pb_4_inv);
            findViewById(C0627R.id.rl_cabfotos).setBackgroundColor(Color.parseColor("#FFEEEEEE"));
            ((TextView) findViewById(C0627R.id.tv_imagenes)).setTextColor(config.NEGRO);
            ((TextView) findViewById(C0627R.id.tv_tu)).setTextColor(config.NEGRO);
            ((RelativeLayout) findViewById(C0627R.id.tl_fotos)).setBackgroundColor(config.BLANCO_2);
        } else {
            this.pb1 = (ProgressBar) findViewById(C0627R.id.pb_1);
            this.pb2 = (ProgressBar) findViewById(C0627R.id.pb_2);
            this.pb3 = (ProgressBar) findViewById(C0627R.id.pb_3);
            this.pb4 = (ProgressBar) findViewById(C0627R.id.pb_4);
            findViewById(C0627R.id.rl_cabfotos).setBackgroundColor(config.GRIS_OSCURO);
            ((TextView) findViewById(C0627R.id.tv_imagenes)).setTextColor(-1);
            ((TextView) findViewById(C0627R.id.tv_tu)).setTextColor(-1);
            ((RelativeLayout) findViewById(C0627R.id.tl_fotos)).setBackgroundColor(config.NEGRO);
        }
        if (this.extras.getBoolean("galeria")) {
            if (config.esClaro("#" + this.globales.c_icos)) {
                findViewById(C0627R.id.iv_masfotos_fondo_n).setVisibility(0);
                findViewById(C0627R.id.iv_menosfotos_fondo_n).setVisibility(0);
            } else {
                findViewById(C0627R.id.iv_masfotos_fondo_b).setVisibility(0);
                findViewById(C0627R.id.iv_menosfotos_fondo_b).setVisibility(0);
            }
            d = getResources().getDrawable(C0627R.drawable.sig_btn);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_masfotos)).setImageDrawable(d);
            findViewById(C0627R.id.iv_masfotos).setOnClickListener(this);
            d = getResources().getDrawable(C0627R.drawable.ant_btn);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_menosfotos)).setImageDrawable(d);
            findViewById(C0627R.id.iv_menosfotos).setOnClickListener(this);
            mostrar_fotos();
            if (this.idusu_profile.equals(this.idusu + "")) {
                ((TextView) findViewById(C0627R.id.btnfotos)).setText(getResources().getString(C0627R.string.anyadir).toUpperCase());
                ((TextView) findViewById(C0627R.id.btnfotos)).setBackgroundColor(Color.parseColor("#" + this.globales.c_icos));
                int c_aux = -1;
                if (config.esClaro("#" + this.globales.c_icos)) {
                    c_aux = config.NEGRO;
                }
                ((TextView) findViewById(C0627R.id.btnfotos)).setTextColor(c_aux);
                findViewById(C0627R.id.btnfotos).setOnClickListener(this);
                findViewById(C0627R.id.ll_anyadirfoto).setVisibility(0);
            }
        } else {
            findViewById(C0627R.id.tl_fotos).setVisibility(8);
        }
        if (this.idusu_profile.equals(this.idusu + "")) {
            findViewById(C0627R.id.iv_ban).setVisibility(8);
        } else {
            findViewById(C0627R.id.iv_ban).setOnClickListener(this);
        }
        if (this.extras.getBoolean("privados_chat") && this.extras.getString("privados").equals("1") && !this.idusu_profile.equals(this.idusu + "")) {
            if (config.esClaro("#" + this.globales.c_icos)) {
                findViewById(C0627R.id.iv_btn_fondo_n).setVisibility(0);
            } else {
                findViewById(C0627R.id.iv_btn_fondo_b).setVisibility(0);
            }
            d = getResources().getDrawable(C0627R.drawable.hablar_btn);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_privado)).setImageDrawable(d);
            findViewById(C0627R.id.iv_privado).setOnClickListener(this);
        } else {
            findViewById(C0627R.id.iv_privado).setVisibility(8);
        }
        this.iv_favorito = (ImageView) findViewById(C0627R.id.iv_favorito);
        if (this.idusu_profile.equals(this.idusu + "")) {
            this.iv_favorito.setVisibility(8);
        } else {
            if (findViewById(C0627R.id.iv_privado).getVisibility() == 8) {
                LayoutParams params = (RelativeLayout.LayoutParams) this.iv_favorito.getLayoutParams();
                params.rightMargin = (int) ((10.0f * getResources().getDisplayMetrics().density) + 0.5f);
                this.iv_favorito.setLayoutParams(params);
            }
            this.iv_favorito.setOnClickListener(this);
        }
        getWindow().setSoftInputMode(2);
        new cargarprofile().execute(new String[0]);
    }

    private void banner() {
        boolean hay_nat_admob = false;
        boolean hay_nat_appnext = false;
        if (!this.idusu_profile.equals(this.idusu + "")) {
            hay_nat_admob = (this.globales.admob_pro_cod == "" || this.globales.admob_pro_w == 0 || this.globales.admob_pro_h == 0) ? false : true;
            hay_nat_appnext = (this.globales.appnext_pro_cod == "" || this.globales.appnext_ads == null || this.globales.appnext_ads.size() <= 0) ? false : true;
        }
        if (hay_nat_admob && hay_nat_appnext) {
            if (new Random().nextInt(2) + 1 == 1) {
                hay_nat_admob = false;
            } else {
                hay_nat_appnext = false;
            }
        }
        if (hay_nat_admob) {
            NativeExpressAdView adView_nat = new NativeExpressAdView(this);
            adView_nat.setAdSize(new AdSize(this.globales.admob_pro_w, this.globales.admob_pro_h));
            adView_nat.setAdUnitId(this.globales.admob_pro_cod);
            findViewById(C0627R.id.tv_appreco).setVisibility(8);
            ((LinearLayout) findViewById(C0627R.id.ll_nat)).setVisibility(0);
            ((LinearLayout) findViewById(C0627R.id.ll_nat_ad)).removeAllViews();
            ((LinearLayout) findViewById(C0627R.id.ll_nat_ad)).addView(adView_nat);
            adView_nat.loadAd(new AdRequest.Builder().build());
        } else if (hay_nat_appnext) {
            if (config.esClaro("#" + this.c1)) {
                ((TextView) findViewById(C0627R.id.tv_appreco)).setTextColor(config.NEGRO);
            }
            this.globales.appnext_mostrar_2(this, 1, 2, -1, -1, null, null, false, false, -1, false);
        } else {
            this.adView = this.globales.mostrar_banner(this, false);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        Matrix matrix;
        int orientation;
        profile hse_here2_profile;
        if (requestCode == 1) {
            Bitmap bm;
            int rotation = -1;
            try {
                long fileSize = new File(this.globales.getTempFile_libre(this, "fperfilgal_temp").getAbsolutePath()).length();
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
                    rotation = new ExifInterface(this.globales.getTempFile_libre(this, "fperfilgal_temp").getAbsolutePath()).getAttributeInt("Orientation", -1);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            try {
                int width;
                int height;
                File file = this.globales.getTempFile_libre(this, "fperfilgal_temp");
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
                    bm.compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile_libre(this, "fperfilgal_temp")));
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
                        bm = BitmapFactory.decodeFile(this.globales.getTempFile_libre(this, "fperfilgal_temp").getAbsolutePath());
                        matrix.postRotate((float) orientation);
                        try {
                            Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true).compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile_libre(this, "fperfilgal_temp")));
                        } catch (Exception e5) {
                        }
                    } catch (Exception e42) {
                        e42.printStackTrace();
                    }
                }
            }
            hse_here2_profile = this;
            new enviar_foto().execute(new String[0]);
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
                    bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile_libre(this, "fperfilgal_temp")));
                } catch (Exception e6) {
                }
                bmImg.recycle();
                hse_here2_profile = this;
                new enviar_foto().execute(new String[0]);
            } catch (Exception e7) {
            }
        } else {
            int i;
            if (data != null) {
                if (data.hasExtra("nlikes")) {
                    boolean encontrada = false;
                    i = 0;
                    while (i < this.fotos_glob_a.size()) {
                        if (((Foto) this.fotos_glob_a.get(i)).id.equals(data.getExtras().getString("idf"))) {
                            ((Foto) this.fotos_glob_a.get(i)).nlikes = data.getExtras().getString("nlikes");
                            ((Foto) this.fotos_glob_a.get(i)).liked = data.getExtras().getString("liked");
                            encontrada = true;
                            break;
                        }
                        i++;
                    }
                    if (encontrada) {
                        int ind_aux = i - this.ind_global;
                        LinearLayout ll_likes = null;
                        TextView tv_likes = null;
                        if (ind_aux == 0) {
                            ll_likes = this.ll_likes1;
                            tv_likes = this.tv_likes1;
                        } else if (ind_aux == 1) {
                            ll_likes = this.ll_likes2;
                            tv_likes = this.tv_likes2;
                        } else if (ind_aux == 2) {
                            ll_likes = this.ll_likes3;
                            tv_likes = this.tv_likes3;
                        } else if (ind_aux == 3) {
                            ll_likes = this.ll_likes4;
                            tv_likes = this.tv_likes4;
                        }
                        if (ll_likes != null) {
                            tv_likes.setText(data.getExtras().getString("nlikes"));
                            ll_likes.setVisibility(0);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
            if (data != null) {
                if (data.hasExtra("elim_foto")) {
                    String idf = data.getStringExtra("elim_foto");
                    int indf = data.getIntExtra("indf", -1);
                    if (!idf.equals("") && indf != -1) {
                        File file2 = new File(this.path, "fperfilgal_" + idf + ".jpg");
                        if (file2.exists()) {
                            file2.delete();
                        }
                        file2 = new File(this.path, "fperfilgal_" + idf + "_g.jpg");
                        if (file2.exists()) {
                            file2.delete();
                        }
                        ((Foto) this.fotos_glob_a.get(indf)).eliminada = true;
                        mostrar_foto(indf, true);
                        Editor e8 = this.settings.edit();
                        for (i = indf; i <= 3; i++) {
                            e8.putString("idf" + i + "_" + this.idusu_profile, this.settings.getString("idf" + (i + 1) + "_" + this.idusu_profile, ""));
                            e8.putString("f" + i + "_fcrea_" + this.idusu_profile, this.settings.getString("f" + (i + 1) + "_fcrea_" + this.idusu_profile, ""));
                            e8.putString("f" + i + "_nlikes_" + this.idusu_profile, this.settings.getString("f" + (i + 1) + "_nlikes_" + this.idusu_profile, ""));
                            e8.putString("f" + i + "_liked_" + this.idusu_profile, this.settings.getString("f" + (i + 1) + "_liked_" + this.idusu_profile, ""));
                        }
                        e8.commit();
                        return;
                    }
                    return;
                }
            }
            if (data != null) {
                if (data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
                    if (!data.getExtras().getBoolean("finalizar_app")) {
                        this.es_root = false;
                    }
                    setResult(-1, data);
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
            this.mDrawerList.setOnItemClickListener(new C07131());
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

    void cambiar_favorito(boolean modo) {
        Editor e = this.settings.edit();
        if (modo) {
            e.putBoolean("usufav_" + this.idusu_profile, true);
            if (this.c1_esclaro) {
                this.iv_favorito.setImageDrawable(getResources().getDrawable(C0627R.drawable.favorito_activ_n));
            } else {
                this.iv_favorito.setImageDrawable(getResources().getDrawable(C0627R.drawable.favorito_activ_b));
            }
        } else {
            e.remove("usufav_" + this.idusu_profile);
            if (this.c1_esclaro) {
                this.iv_favorito.setImageDrawable(getResources().getDrawable(C0627R.drawable.favorito_noactiv_n));
            } else {
                this.iv_favorito.setImageDrawable(getResources().getDrawable(C0627R.drawable.favorito_noactiv_b));
            }
        }
        e.commit();
    }

    public void onClick(View v) {
        final AlertDialog d_aux;
        if (v.getId() == C0627R.id.iv_ban) {
            d_aux = new Builder(this).setNegativeButton(C0627R.string.cancelar, null).setPositiveButton(C0627R.string.aceptar, new C07142()).setMessage(C0627R.string.confirmar_elimusu).create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                        d_aux.getButton(-2).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e) {
            }
        } else if (v.getId() == C0627R.id.iv_favorito) {
            Editor e2;
            if (this.settings.contains("usufav_" + this.idusu_profile)) {
                cambiar_favorito(false);
                e2 = this.settings.edit();
                e2.putBoolean("usufav_noactivar_" + this.idusu_profile, true);
                e2.commit();
                new aplic_favorito(Boolean.valueOf(false)).execute(new String[0]);
                return;
            }
            cambiar_favorito(true);
            new aplic_favorito(Boolean.valueOf(true)).execute(new String[0]);
            if (!this.settings.contains("usufav_msgmostrado")) {
                e2 = this.settings.edit();
                e2.putBoolean("usufav_msgmostrado", true);
                e2.commit();
                d_aux = new Builder(this).setCancelable(false).setPositiveButton(C0627R.string.aceptar, null).setMessage(C0627R.string.favorito_activar).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e3) {
                }
            }
        } else if (v.getId() == C0627R.id.iv_privado) {
            i = new Intent(this, t_chat.class);
            i.putExtra("empezar_privado", true);
            i.putExtra("externo", true);
            i.putExtra("id_remit", this.idusu_profile);
            i.putExtra("nombre_remit", this.extras.getString("nombre"));
            i.putExtra("idchat", 0);
            i.putExtra("idtema", 0);
            i.putExtra("fotos_perfil", this.extras.getInt("fotos_perfil"));
            i.putExtra("fotos_chat", this.extras.getInt("fotos_chat"));
            i.putExtra("fnac", this.extras.getInt("p_fnac"));
            i.putExtra("sexo", this.extras.getInt("p_sexo"));
            i.putExtra("descr", this.extras.getInt("p_descr"));
            i.putExtra("dist", this.extras.getInt("p_dist"));
            i.putExtra("privados", this.extras.getBoolean("privados"));
            i.putExtra("coments", this.extras.getBoolean("coments"));
            i.putExtra("galeria", this.extras.getBoolean("galeria"));
            i.putExtra("c1", this.globales.secciones_a[this.globales.ind_secc_sel_2].c1);
            i.putExtra("c2", this.globales.secciones_a[this.globales.ind_secc_sel_2].c2);
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.iv_enviarcoment) {
            f_enviar(false);
        } else if (v.getId() == C0627R.id.iv_elimcoment) {
            View v_aux = (View) v.getParent().getParent();
            String idcoment = (String) v_aux.getTag(C0627R.id.idaux2);
            if (idcoment != null) {
                final View view = v_aux;
                final String str = idcoment;
                d_aux = new Builder(this).setNegativeButton(C0627R.string.no, null).setPositiveButton(C0627R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        view.setVisibility(8);
                        int ind_aux = ((LinearLayout) view.getParent()).indexOfChild(view);
                        Editor e = profile.this.settings.edit();
                        for (int i = ind_aux; i < 4; i++) {
                            e.putString("idcom" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("idcom" + (i + 1) + "_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_fcrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_fcrea_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_idusucrea_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_idusucrea_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_nombre_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_nombre_" + profile.this.idusu_profile, ""));
                            e.putString("com" + i + "_vfoto_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_vfoto_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_privados_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_privados_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnacd_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_fnacd_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnacm_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_fnacm_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_fnaca_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_fnaca_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_sexo_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_sexo_" + profile.this.idusu_profile, "0"));
                            e.putString("com" + i + "_coments_" + profile.this.idusu_profile, profile.this.settings.getString("com" + (i + 1) + "_coments_" + profile.this.idusu_profile, "0"));
                        }
                        e.commit();
                        new elim_coment(str).execute(new String[0]);
                    }
                }).setMessage(C0627R.string.confirmar_elimcoment).create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                            d_aux.getButton(-2).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e4) {
                }
            }
        } else if (v.getId() == C0627R.id.iv_mascoments) {
            r0 = this;
            new cargar_coments().execute(new String[0]);
        } else if (v.getId() == C0627R.id.iv_masfotos) {
            this.ind_global += 4;
            r0 = this;
            new cargar_fotos().execute(new String[0]);
        } else if (v.getId() == C0627R.id.iv_menosfotos) {
            this.ind_global -= 4;
            r0 = this;
            new cargar_fotos().execute(new String[0]);
        } else if (v.getId() == C0627R.id.iv_f1 || v.getId() == C0627R.id.iv_f2 || v.getId() == C0627R.id.iv_f3 || v.getId() == C0627R.id.iv_f4) {
            String id_img = (String) v.getTag(C0627R.id.idaux1);
            int ind = Integer.parseInt((String) v.getTag(C0627R.id.idaux3));
            if (id_img != null && !((Foto) this.fotos_glob_a.get(ind)).eliminada) {
                File f_aux = this.globales.getTempFile_libre(this, "fperfilgal_" + id_img + "_g.jpg");
                if (f_aux.exists()) {
                    i = new Intent(this, fotogal.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + f_aux.getAbsolutePath());
                    i.putExtra("nlikes", ((Foto) this.fotos_glob_a.get(ind)).nlikes);
                    i.putExtra("liked", ((Foto) this.fotos_glob_a.get(ind)).liked);
                    i.putExtra("fcrea", ((Foto) this.fotos_glob_a.get(ind)).fcrea);
                    i.putExtra("idf", id_img);
                    i.putExtra("indf", ind);
                    i.putExtra("idusu_profile", this.idusu_profile);
                    startActivityForResult(i, 0);
                } else if (this.o_i_g == null || this.o_i_g.getStatus() != Status.RUNNING) {
                    this.o_i_g = new obtener_img_g(id_img, ind);
                    this.o_i_g.execute(new String[0]);
                }
            }
        } else if (v.getId() == C0627R.id.btnfotos) {
            d_aux = new Builder(this).setCancelable(true).setPositiveButton(getString(C0627R.string.tomardecamara), new C07208()).setNegativeButton(getString(C0627R.string.tomardesd), new C07197()).setMessage(C0627R.string.tomarfoto).create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                        d_aux.getButton(-2).setTextColor(Color.parseColor("#" + profile.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e5) {
            }
        } else if (v.getTag(C0627R.id.idaux1) != null && v.getTag(C0627R.id.idaux1).equals("ESCOMENT")) {
            i = new Intent(this, profile.class);
            i.putExtra("id", (String) v.getTag(C0627R.id.idaux2));
            i.putExtra("privados", (String) v.getTag(C0627R.id.idaux5));
            i.putExtra("nombre", (String) v.getTag(C0627R.id.idaux3));
            i.putExtra("coments", (String) v.getTag(C0627R.id.idaux10));
            i.putExtra("fnac_d", (String) v.getTag(C0627R.id.idaux6));
            i.putExtra("fnac_m", (String) v.getTag(C0627R.id.idaux7));
            i.putExtra("fnac_a", (String) v.getTag(C0627R.id.idaux8));
            i.putExtra("sexo", (String) v.getTag(C0627R.id.idaux9));
            i.putExtra("vfoto", (String) v.getTag(C0627R.id.idaux4));
            i.putExtra("p_fnac", this.extras.getInt("p_fnac"));
            i.putExtra("p_sexo", this.extras.getInt("p_sexo"));
            i.putExtra("p_descr", this.extras.getInt("p_descr"));
            i.putExtra("p_dist", this.extras.getInt("p_dist"));
            i.putExtra("coments_chat", this.extras.getBoolean("coments_chat"));
            i.putExtra("galeria", this.extras.getBoolean("galeria"));
            i.putExtra("privados_chat", this.extras.getBoolean("privados_chat"));
            i.putExtra("fotos_perfil", this.fotos_perfil);
            i.putExtra("fotos_chat", this.extras.getInt("fotos_chat"));
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

    public void mostrar_subtit() {
        String cad = "";
        if (this.edat != -1) {
            cad = this.edat + getResources().getString(C0627R.string.anyos_abrev);
        }
        if (this.dist != null) {
            String pais = Locale.getDefault().getCountry();
            long dist_aux = Long.parseLong(this.dist);
            String unidad = "m.";
            if (pais.equals("US") || pais.equals("GB") || pais.equals("MM")) {
                if (dist_aux > 1600) {
                    dist_aux = (long) Math.round((float) (dist_aux / 1600));
                    unidad = "mi.";
                } else {
                    dist_aux = Math.round(((double) dist_aux) * 1.09d);
                    unidad = "yd.";
                }
            } else if (dist_aux > 999) {
                dist_aux = (long) Math.round((float) (dist_aux / 1000));
                unidad = "km.";
            }
            if (!cad.isEmpty()) {
                cad = cad + ", ";
            }
            cad = cad + dist_aux + unidad;
        }
        if (cad.isEmpty()) {
            ((TextView) findViewById(C0627R.id.tv_subtit)).setVisibility(8);
            return;
        }
        ((TextView) findViewById(C0627R.id.tv_subtit)).setText(cad);
        ((TextView) findViewById(C0627R.id.tv_subtit)).setVisibility(0);
    }

    public void mostrar_viewsaux() {
        if (((TextView) findViewById(C0627R.id.tv_subtit)).getVisibility() == 8) {
            findViewById(C0627R.id.v_aux1).setVisibility(0);
        } else {
            findViewById(C0627R.id.v_aux1).setVisibility(8);
        }
        if (((TextView) findViewById(C0627R.id.descr)).getVisibility() == 8) {
            findViewById(C0627R.id.v_aux2).setVisibility(0);
        } else {
            findViewById(C0627R.id.v_aux2).setVisibility(8);
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
        String m = ((TextView) findViewById(C0627R.id.et_coment_self)).getText().toString().replace("@", "").trim();
        if (!m.equals("")) {
            if (desde_teclado && this.altura < 600) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(((TextView) findViewById(C0627R.id.c_mensaje)).getWindowToken(), 0);
            }
            ((TextView) findViewById(C0627R.id.et_coment_self)).setTextColor(-7829368);
            this.pb_enviando.setVisibility(0);
            if (this.env == null || this.env.getStatus() != Status.RUNNING) {
                if (!(this.settings.contains("usufav_" + this.idusu_profile) || this.settings.contains("usufav_noactivar_" + this.idusu_profile))) {
                    cambiar_favorito(true);
                }
                this.env = new enviar_coment(m);
                this.env.execute(new String[0]);
            }
        }
    }

    void mostrar_coment(Boolean es_inicial, Boolean es_envio, String idcom, String idusucrea, String nombre, String fcrea, String coment, String vfoto, String privados, String fnac_d, String fnac_m, String fnac_a, String sexo, String coments) {
        View ll_coment = (LinearLayout) ((LayoutInflater) getSystemService("layout_inflater")).inflate(C0627R.layout.comentario, null);
        ll_coment.setTag(C0627R.id.idaux2, idcom);
        ImageView iv_usucoment = (ImageView) ll_coment.findViewById(C0627R.id.iv_usucoment);
        TextView tv_nombrecoment = (TextView) ll_coment.findViewById(C0627R.id.tv_nombrecoment);
        TextView tv_fcoment = (TextView) ll_coment.findViewById(C0627R.id.tv_fcoment);
        TextView tv_coment = (TextView) ll_coment.findViewById(C0627R.id.tv_coment);
        ImageView iv_elimcoment = (ImageView) ll_coment.findViewById(C0627R.id.iv_elimcoment);
        if (this.c1_esclaro) {
            tv_nombrecoment.setTextColor(config.NEGRO);
            tv_fcoment.setTextColor(config.GRIS_OSCURO);
            tv_coment.setTextColor(config.NEGRO);
        } else {
            tv_nombrecoment.setTextColor(-1);
            tv_fcoment.setTextColor(config.GRIS_CLARO);
            tv_coment.setTextColor(-1);
        }
        if (!es_inicial.booleanValue() && (idusucrea.equals(this.idusu + "") || this.idusu_profile.equals(this.idusu + ""))) {
            if (this.c1_esclaro) {
                iv_elimcoment.setImageDrawable(getResources().getDrawable(C0627R.drawable.eliminar_n));
            }
            iv_elimcoment.setOnClickListener(this);
            iv_elimcoment.setVisibility(0);
        }
        if (idusucrea.equals(this.idusu + "")) {
            tv_nombrecoment.setText(getResources().getString(C0627R.string.tu));
        } else {
            tv_nombrecoment.setText(nombre);
        }
        fcrea = config.convertir_fecha(fcrea);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyHHmm");
        try {
            java.text.DateFormat formato_fecha = DateFormat.getDateFormat(this);
            java.text.DateFormat formato_hora = DateFormat.getTimeFormat(this);
            Date d = dateFormat.parse(fcrea);
            Calendar hoy = Calendar.getInstance();
            Calendar d_cal = Calendar.getInstance();
            d_cal.setTime(d);
            File file;
            Options bounds;
            int width_max;
            int height_max;
            int width;
            int height;
            boolean withinBounds;
            Bitmap bm_aux;
            int sampleSize;
            Options resample;
            if (d_cal.get(1) == hoy.get(1) && d_cal.get(6) == hoy.get(6)) {
                tv_fcoment.setText(getResources().getString(C0627R.string.hoy) + " " + formato_hora.format(d));
                tv_coment.setText(coment);
                iv_usucoment.setTag(C0627R.id.idaux1, idusucrea);
                if (this.fotos_perfil > 0) {
                    iv_usucoment.setVisibility(8);
                } else if (idusucrea.equals(this.idusu + "")) {
                    iv_usucoment.setTag(C0627R.id.idaux2, "0");
                    iv_usucoment.setImageBitmap(this.bm_propia);
                } else {
                    iv_usucoment.setTag(C0627R.id.idaux2, vfoto);
                    file = new File(this.path, "fperfil_" + idusucrea + ".jpg");
                    bounds = new Options();
                    bounds.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getPath(), bounds);
                    if (bounds.outWidth != -1) {
                        width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        width = bounds.outWidth;
                        height = bounds.outHeight;
                    } else {
                        width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        width = bounds.outWidth;
                        height = bounds.outHeight;
                    }
                    if (width > width_max) {
                    }
                    if (withinBounds) {
                        bm_aux = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                    } else {
                        sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, width_max, height_max)));
                        resample = new Options();
                        resample.inSampleSize = sampleSize;
                        bm_aux = BitmapFactory.decodeFile(file.getPath(), resample);
                    }
                    iv_usucoment.setImageBitmap(bm_aux);
                }
                if (!idusucrea.equals(this.idusu + "")) {
                    ll_coment.setTag(C0627R.id.idaux1, "ESCOMENT");
                    ll_coment.setTag(C0627R.id.idaux2, idusucrea);
                    ll_coment.setTag(C0627R.id.idaux3, nombre);
                    ll_coment.setTag(C0627R.id.idaux4, vfoto);
                    ll_coment.setTag(C0627R.id.idaux5, privados);
                    ll_coment.setTag(C0627R.id.idaux6, fnac_d);
                    ll_coment.setTag(C0627R.id.idaux7, fnac_m);
                    ll_coment.setTag(C0627R.id.idaux8, fnac_a);
                    ll_coment.setTag(C0627R.id.idaux9, sexo);
                    ll_coment.setTag(C0627R.id.idaux10, coments);
                    ll_coment.setOnClickListener(this);
                }
                if (es_envio.booleanValue()) {
                    this.ll_coments.addView(ll_coment, 0);
                } else {
                    this.ll_coments.addView(ll_coment);
                }
                this.idcoment_ult = idcom;
            }
            tv_fcoment.setText(formato_fecha.format(d) + " " + formato_hora.format(d));
            tv_coment.setText(coment);
            iv_usucoment.setTag(C0627R.id.idaux1, idusucrea);
            if (this.fotos_perfil > 0) {
                iv_usucoment.setVisibility(8);
            } else if (idusucrea.equals(this.idusu + "")) {
                iv_usucoment.setTag(C0627R.id.idaux2, "0");
                iv_usucoment.setImageBitmap(this.bm_propia);
            } else {
                iv_usucoment.setTag(C0627R.id.idaux2, vfoto);
                try {
                    file = new File(this.path, "fperfil_" + idusucrea + ".jpg");
                    bounds = new Options();
                    bounds.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getPath(), bounds);
                    if (bounds.outWidth != -1) {
                        width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        width = bounds.outWidth;
                        height = bounds.outHeight;
                    } else {
                        width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                        width = bounds.outWidth;
                        height = bounds.outHeight;
                    }
                    withinBounds = width > width_max && height <= height_max;
                    if (withinBounds) {
                        sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, width_max, height_max)));
                        resample = new Options();
                        resample.inSampleSize = sampleSize;
                        bm_aux = BitmapFactory.decodeFile(file.getPath(), resample);
                    } else {
                        bm_aux = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                    }
                    iv_usucoment.setImageBitmap(bm_aux);
                } catch (Exception e) {
                }
            }
            if (idusucrea.equals(this.idusu + "")) {
                ll_coment.setTag(C0627R.id.idaux1, "ESCOMENT");
                ll_coment.setTag(C0627R.id.idaux2, idusucrea);
                ll_coment.setTag(C0627R.id.idaux3, nombre);
                ll_coment.setTag(C0627R.id.idaux4, vfoto);
                ll_coment.setTag(C0627R.id.idaux5, privados);
                ll_coment.setTag(C0627R.id.idaux6, fnac_d);
                ll_coment.setTag(C0627R.id.idaux7, fnac_m);
                ll_coment.setTag(C0627R.id.idaux8, fnac_a);
                ll_coment.setTag(C0627R.id.idaux9, sexo);
                ll_coment.setTag(C0627R.id.idaux10, coments);
                ll_coment.setOnClickListener(this);
            }
            if (es_envio.booleanValue()) {
                this.ll_coments.addView(ll_coment, 0);
            } else {
                this.ll_coments.addView(ll_coment);
            }
            this.idcoment_ult = idcom;
        } catch (Exception e2) {
        }
    }

    void mostrar_coments(boolean es_inicial) {
        this.ll_coments.removeAllViews();
        int i = 0;
        while (i < 4 && !this.settings.getString("idcom" + i + "_" + this.idusu_profile, "").equals("")) {
            mostrar_coment(Boolean.valueOf(es_inicial), Boolean.valueOf(false), this.settings.getString("idcom" + i + "_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_idusucrea_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_nombre_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_fcrea_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_vfoto_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_privados_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_fnacd_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_fnacm_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_fnaca_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_sexo_" + this.idusu_profile, ""), this.settings.getString("com" + i + "_coments_" + this.idusu_profile, ""));
            i++;
        }
    }

    void mostrar_fotos_dearray() {
        this.f1.setVisibility(8);
        this.f2.setVisibility(8);
        this.f3.setVisibility(8);
        this.f4.setVisibility(8);
        this.pb1.setVisibility(8);
        this.pb2.setVisibility(8);
        this.pb3.setVisibility(8);
        this.pb4.setVisibility(8);
        this.ll_likes1.setVisibility(8);
        this.ll_likes2.setVisibility(8);
        this.ll_likes3.setVisibility(8);
        this.ll_likes4.setVisibility(8);
        int i = 0;
        while (i < 4 && this.ind_global + i <= this.fotos_glob_a.size() - 1) {
            mostrar_foto(this.ind_global + i, true);
            i++;
        }
        if (!this.fotos_glob_a.isEmpty() || this.idusu_profile.equals(this.idusu + "")) {
            if (this.ind_global + i <= this.fotos_glob_a.size() - 1 || !this.fotos_glob_a_completo) {
                findViewById(C0627R.id.fl_masfotos).setVisibility(0);
            } else {
                findViewById(C0627R.id.fl_masfotos).setVisibility(4);
            }
            if (this.ind_global > 0) {
                findViewById(C0627R.id.fl_menosfotos).setVisibility(0);
            } else {
                findViewById(C0627R.id.fl_menosfotos).setVisibility(4);
            }
            findViewById(C0627R.id.tl_fotos).setVisibility(0);
        } else {
            findViewById(C0627R.id.tl_fotos).setVisibility(8);
        }
        this.c_f_g = new cargar_fotos_gal();
        this.c_f_g.execute(new String[0]);
    }

    void mostrar_fotos() {
        this.f1.setVisibility(8);
        this.f2.setVisibility(8);
        this.f3.setVisibility(8);
        this.f4.setVisibility(8);
        this.pb1.setVisibility(8);
        this.pb2.setVisibility(8);
        this.pb3.setVisibility(8);
        this.pb4.setVisibility(8);
        this.ll_likes1.setVisibility(8);
        this.ll_likes2.setVisibility(8);
        this.ll_likes3.setVisibility(8);
        this.ll_likes4.setVisibility(8);
        boolean hay_fotos = false;
        int i = 0;
        while (i < 4 && !this.settings.getString("idf" + i + "_" + this.idusu_profile, "").equals("")) {
            mostrar_foto(i, false);
            hay_fotos = true;
            i++;
        }
        if (hay_fotos || this.idusu_profile.equals(this.idusu + "")) {
            findViewById(C0627R.id.tl_fotos).setVisibility(0);
        } else {
            findViewById(C0627R.id.tl_fotos).setVisibility(8);
        }
    }

    void mostrar_foto(int i, boolean desde_array) {
        ImageView iv;
        LinearLayout ll_likes;
        TextView tv_likes;
        ProgressBar pb;
        String nlikes;
        int ind = i % 4;
        if (ind == 0) {
            iv = this.f1;
            ll_likes = this.ll_likes1;
            tv_likes = this.tv_likes1;
            pb = this.pb1;
        } else if (ind == 1) {
            iv = this.f2;
            ll_likes = this.ll_likes2;
            tv_likes = this.tv_likes2;
            pb = this.pb2;
        } else if (ind == 2) {
            iv = this.f3;
            ll_likes = this.ll_likes3;
            tv_likes = this.tv_likes3;
            pb = this.pb3;
        } else if (ind == 3) {
            iv = this.f4;
            ll_likes = this.ll_likes4;
            tv_likes = this.tv_likes4;
            pb = this.pb4;
        } else {
            return;
        }
        String idf = "";
        Boolean eliminada = Boolean.valueOf(false);
        if (desde_array) {
            idf = ((Foto) this.fotos_glob_a.get(i)).id;
            nlikes = ((Foto) this.fotos_glob_a.get(i)).nlikes;
            eliminada = Boolean.valueOf(((Foto) this.fotos_glob_a.get(i)).eliminada);
        } else {
            idf = this.settings.getString("idf" + i + "_" + this.idusu_profile, "");
            nlikes = this.settings.getString("f" + i + "_nlikes_" + this.idusu_profile, "");
        }
        pb.setVisibility(8);
        iv.setTag(C0627R.id.idaux1, idf);
        iv.setTag(C0627R.id.idaux2, "N");
        iv.setTag(C0627R.id.idaux3, i + "");
        if (eliminada.booleanValue()) {
            iv.setImageDrawable(null);
            iv.setTag(C0627R.id.idaux2, "S");
            ll_likes.setVisibility(8);
        } else {
            if (nlikes.equals("0")) {
                ll_likes.setVisibility(8);
            } else {
                tv_likes.setText(nlikes);
                ll_likes.setVisibility(0);
            }
            try {
                int width_max;
                int height_max;
                int width;
                int height;
                Bitmap bm_aux;
                File file = new File(this.path, "fperfilgal_" + idf + ".jpg");
                Options bounds = new Options();
                bounds.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getPath(), bounds);
                if (bounds.outWidth == -1) {
                    width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                } else {
                    width_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    height_max = (int) ((48.0f * getResources().getDisplayMetrics().density) + 0.5f);
                    width = bounds.outWidth;
                    height = bounds.outHeight;
                }
                boolean withinBounds = width <= width_max && height <= height_max;
                if (withinBounds) {
                    bm_aux = Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                } else {
                    int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, width_max, height_max)));
                    Options resample = new Options();
                    resample.inSampleSize = sampleSize;
                    bm_aux = BitmapFactory.decodeFile(file.getPath(), resample);
                }
                iv.setImageBitmap(bm_aux);
                iv.setTag(C0627R.id.idaux2, "S");
            } catch (Exception e) {
                iv.setImageDrawable(getResources().getDrawable(C0627R.drawable.sinfoto_chat));
                pb.setVisibility(0);
            }
        }
        iv.setVisibility(0);
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
        banner();
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
        cambiar_favorito(this.settings.contains("usufav_" + this.idusu_profile));
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
