package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.plus.PlusShare;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class preinicio extends Activity {
    public static final int REQUEST_GOOGLE_PLAY_SERVICES = 1972;
    Builder adb;
    boolean bienvenida_mostrar = false;
    Bitmap bm_icohome = null;
    Bitmap bm_splash = null;
    String c1_sp_orig = "";
    String c_icos_orig = "";
    String cbtn;
    String cod_g;
    String contra;
    boolean contra_ko = false;
    int desde_amaz;
    int desde_gplay;
    boolean desde_rate = false;
    ProgressDialog dialog_cargando;
    ProgressDialog dialog_enviando;
    boolean directo = false;
    CheckBox dontShowAgain;
    int espera;
    EditText et_contra;
    Bundle extras;
    GoogleCloudMessaging gcm;
    config globales;
    int idusu;
    boolean idusu_sd;
    boolean marcar_guardado = true;
    boolean mostrar_ad_entrar;
    int ord_secc_abrir;
    boolean osp_llamado = false;
    ProgressDialog pd_espera;
    String regid;
    String result_http = "";
    Map<String, String> result_http_map;
    SharedPreferences settings;
    Thread th_appnext;
    Thread th_espera;

    class C06861 implements OnClickListener {
        C06861() {
        }

        public void onClick(DialogInterface dialog, int id) {
            preinicio.this.finish();
        }
    }

    class C06893 implements OnCancelListener {
        C06893() {
        }

        public void onCancel(DialogInterface dialog) {
            try {
                preinicio.this.th_espera.interrupt();
            } catch (Exception e) {
            }
            preinicio.this.finish();
        }
    }

    class C06904 extends Handler {
        C06904() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (preinicio.this.espera > 0) {
                preinicio hse_here2_preinicio = preinicio.this;
                hse_here2_preinicio.espera--;
                try {
                    preinicio.this.pd_espera.setMessage(preinicio.this.espera + "s.");
                } catch (Exception e) {
                }
                if (preinicio.this.espera == 0) {
                    preinicio.this.preiniciar();
                }
            }
        }
    }

    class C06926 implements OnClickListener {
        C06926() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (preinicio.this.globales.bienvenida_nomas_mostrar && preinicio.this.dontShowAgain.isChecked()) {
                Editor editor = preinicio.this.settings.edit();
                editor.putBoolean("bienvenida_nomas", true);
                editor.commit();
            }
            preinicio.this.preiniciar_4();
        }
    }

    class C06957 extends Thread {

        class C06941 implements Runnable {
            C06941() {
            }

            public void run() {
                if (preinicio.this.bienvenida_mostrar) {
                    final AlertDialog adb_ad = preinicio.this.adb.create();
                    if (!preinicio.this.cbtn.equals("")) {
                        adb_ad.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                adb_ad.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                            }
                        });
                    }
                    try {
                        adb_ad.show();
                        if (!preinicio.this.globales.bienvenida_nomas_mostrar) {
                            try {
                                ((TextView) adb_ad.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                                return;
                            } catch (Exception e) {
                                return;
                            }
                        }
                        return;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        preinicio.this.preiniciar_4();
                        return;
                    }
                }
                preinicio.this.preiniciar_4();
            }
        }

        C06957() {
        }

        public void run() {
            try {
                C06957.sleep((long) preinicio.this.globales.splash_w);
            } catch (InterruptedException e) {
            }
            preinicio.this.runOnUiThread(new C06941());
        }
    }

    class C06979 implements OnClickListener {
        C06979() {
        }

        public void onClick(DialogInterface dialog, int id) {
            Intent goToMarket = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + preinicio.this.getPackageName()));
            goToMarket.addFlags(1208483840);
            Editor editor = preinicio.this.settings.edit();
            editor.putInt("rt_n", -1);
            editor.commit();
            preinicio.this.desde_rate = true;
            try {
                preinicio.this.startActivity(goToMarket);
            } catch (Exception e) {
                preinicio.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + preinicio.this.getPackageName())));
            }
        }
    }

    private class cargarconfig extends AsyncTask<String, Void, Byte> {

        class C06981 implements OnShowListener {
            C06981() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) preinicio.this.dialog_cargando.findViewById(16908301), preinicio.this.c_icos_orig);
            }
        }

        class C06992 implements OnClickListener {
            C06992() {
            }

            public void onClick(DialogInterface dialog, int id) {
                preinicio.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.androidcreator.com/apk/app299914.apk")));
                preinicio.this.finish();
            }
        }

        protected java.lang.Byte doInBackground(java.lang.String... r19) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r18 = this;
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.directo;
            if (r14 == 0) goto L_0x000e;
        L_0x0008:
            r14 = 0;
            r14 = java.lang.Byte.valueOf(r14);
        L_0x000d:
            return r14;
        L_0x000e:
            r12 = "";
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.idusu_sd;
            if (r14 == 0) goto L_0x001a;
        L_0x0018:
            r12 = "&recup_todo=1";
        L_0x001a:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.settings;
            r15 = "fultsync";
            r16 = "010100000000";
            r6 = r14.getString(r15, r16);
            r10 = "";
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.extras;
            if (r14 == 0) goto L_0x0073;
        L_0x0032:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.extras;
            r15 = "notif_id";
            r14 = r14.getString(r15);
            if (r14 == 0) goto L_0x0073;
        L_0x0040:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.extras;
            r15 = "notif_id";
            r14 = r14.getString(r15);
            r15 = "0";
            r14 = r14.equals(r15);
            if (r14 != 0) goto L_0x0073;
        L_0x0054:
            r14 = new java.lang.StringBuilder;
            r14.<init>();
            r15 = "&notif=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.extras;
            r16 = "notif_id";
            r15 = r15.getString(r16);
            r14 = r14.append(r15);
            r10 = r14.toString();
        L_0x0073:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.extras;
            if (r14 == 0) goto L_0x00ca;
        L_0x007b:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.extras;
            r15 = "idnotif_marcar";
            r14 = r14.containsKey(r15);
            if (r14 == 0) goto L_0x00ca;
        L_0x0089:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.settings;
            r5 = r14.edit();
            r14 = new java.lang.StringBuilder;
            r14.<init>();
            r15 = "notif_";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.extras;
            r16 = "idnotif_marcar";
            r15 = r15.getInt(r16);
            r14 = r14.append(r15);
            r15 = "_leida";
            r14 = r14.append(r15);
            r14 = r14.toString();
            r15 = 1;
            r5.putBoolean(r14, r15);
            r5.commit();
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.getApplicationContext();
            hse.here2.config.act_notif_noleidas(r14);
        L_0x00ca:
            r0 = r18;
            r14 = hse.here2.preinicio.this;
            r14 = r14.getContentResolver();
            r15 = "android_id";
            r1 = android.provider.Settings.Secure.getString(r14, r15);
            r14 = new java.lang.StringBuilder;
            r14.<init>();
            r15 = "http://srv1.androidcreator.com/srv/config.php?v=10&vname=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = hse.here2.config.getAppVersionName(r15);
            r14 = r14.append(r15);
            r15 = "&idapp=";
            r14 = r14.append(r15);
            r15 = 299914; // 0x4938a float:4.20269E-40 double:1.48177E-318;
            r14 = r14.append(r15);
            r15 = "&idusu=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.idusu;
            r14 = r14.append(r15);
            r15 = "&cod_g=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.cod_g;
            r14 = r14.append(r15);
            r15 = "&gp=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.desde_gplay;
            r14 = r14.append(r15);
            r15 = "&am=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.desde_amaz;
            r14 = r14.append(r15);
            r15 = "&pa_env=1&pa=";
            r14 = r14.append(r15);
            r15 = java.util.Locale.getDefault();
            r15 = r15.getCountry();
            r14 = r14.append(r15);
            r15 = "&pn=";
            r14 = r14.append(r15);
            r0 = r18;
            r15 = hse.here2.preinicio.this;
            r15 = r15.getPackageName();
            r14 = r14.append(r15);
            r15 = "&fus=";
            r14 = r14.append(r15);
            r14 = r14.append(r6);
            r14 = r14.append(r12);
            r14 = r14.append(r10);
            r15 = "&aid=";
            r14 = r14.append(r15);
            r14 = r14.append(r1);
            r3 = r14.toString();
            r9 = 0;
            r9 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x01e1 }
            r9.<init>(r3);	 Catch:{ MalformedURLException -> 0x01e1 }
            r2 = 0;
            r14 = r9.openConnection();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r0 = r14;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r2 = r0;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = 1;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r2.setDoInput(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r2.setConnectTimeout(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r2.setReadTimeout(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = "User-Agent";	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = "Android Vinebre Software";	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r2.setRequestProperty(r14, r15);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r7 = r2.getInputStream();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r11 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14.<init>(r7);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r11.<init>(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r13 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r13.<init>();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
        L_0x01b7:
            r8 = r11.readLine();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            if (r8 == 0) goto L_0x01e9;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
        L_0x01bd:
            r14 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14.<init>();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = r14.append(r8);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = "\n";	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = r14.append(r15);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = r14.toString();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r13.append(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            goto L_0x01b7;
        L_0x01d4:
            r4 = move-exception;
            r14 = -1;
            r14 = java.lang.Byte.valueOf(r14);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            if (r2 == 0) goto L_0x000d;
        L_0x01dc:
            r2.disconnect();
            goto L_0x000d;
        L_0x01e1:
            r4 = move-exception;
            r14 = -1;
            r14 = java.lang.Byte.valueOf(r14);
            goto L_0x000d;
        L_0x01e9:
            r0 = r18;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = r13.toString();	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14.result_http = r15;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r0 = r18;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r0 = r18;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = r15.result_http;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r16 = "@EURO@";	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r17 = "€";	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r15 = r15.replace(r16, r17);	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            r14.result_http = r15;	 Catch:{ Exception -> 0x01d4, all -> 0x0213 }
            if (r2 == 0) goto L_0x020c;
        L_0x0209:
            r2.disconnect();
        L_0x020c:
            r14 = 0;
            r14 = java.lang.Byte.valueOf(r14);
            goto L_0x000d;
        L_0x0213:
            r14 = move-exception;
            if (r2 == 0) goto L_0x0219;
        L_0x0216:
            r2.disconnect();
        L_0x0219:
            throw r14;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.preinicio.cargarconfig.doInBackground(java.lang.String[]):java.lang.Byte");
        }

        private cargarconfig() {
        }

        protected void onPreExecute() {
            if (!preinicio.this.directo) {
                preinicio.this.dialog_cargando = new ProgressDialog(preinicio.this);
                preinicio.this.dialog_cargando.setMessage(preinicio.this.getString(C0627R.string.inicializando));
                preinicio.this.dialog_cargando.setIndeterminate(true);
                if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                    preinicio.this.dialog_cargando.setOnShowListener(new C06981());
                }
                preinicio.this.dialog_cargando.show();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void onPostExecute(java.lang.Byte r73) {
            /*
            r72 = this;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.util.HashMap;
            r3.<init>();
            r2.result_http_map = r3;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http;
            r3 = "\\]";
            r10 = r2.split(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = 0;
            r2.result_http = r3;
            r29 = 0;
        L_0x0020:
            r2 = r10.length;
            r0 = r29;
            if (r0 >= r2) goto L_0x007c;
        L_0x0025:
            r2 = r10[r29];
            r3 = "=";
            r4 = 2;
            r11 = r2.split(r3, r4);
            r2 = 0;
            r10[r29] = r2;
            r2 = r11.length;
            if (r2 <= 0) goto L_0x0079;
        L_0x0034:
            r2 = 0;
            r2 = r11[r2];
            r3 = 0;
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x0079;
        L_0x003e:
            r2 = 0;
            r2 = r11[r2];
            r2 = r2.length();
            r3 = 1;
            if (r2 <= r3) goto L_0x0079;
        L_0x0048:
            r2 = 0;
            r2 = r11[r2];
            r3 = 1;
            r66 = r2.substring(r3);
            r67 = "";
            r2 = r11.length;
            r3 = 1;
            if (r2 <= r3) goto L_0x006c;
        L_0x0056:
            r2 = 1;
            r2 = r11[r2];
            r3 = 0;
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x006c;
        L_0x0060:
            r2 = 1;
            r2 = r11[r2];
            r2 = r2.length();
            if (r2 <= 0) goto L_0x006c;
        L_0x0069:
            r2 = 1;
            r67 = r11[r2];
        L_0x006c:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http_map;
            r0 = r66;
            r1 = r67;
            r2.put(r0, r1);
        L_0x0079:
            r29 = r29 + 1;
            goto L_0x0020;
        L_0x007c:
            r10 = 0;
            r11 = 0;
            r66 = 0;
            r67 = 0;
            r0 = r72;
            r2 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x268c }
            r2 = r2.dialog_cargando;	 Catch:{ Exception -> 0x268c }
            r2.dismiss();	 Catch:{ Exception -> 0x268c }
        L_0x008b:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http_map;
            r3 = "FIN";
            r2 = r2.containsKey(r3);
            if (r2 != 0) goto L_0x00d7;
        L_0x0099:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http_map;
            r2.clear();
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = 0;
            r2.marcar_guardado = r3;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.idusu;
            if (r2 == 0) goto L_0x00b9;
        L_0x00b1:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.idusu_sd;
            if (r2 == 0) goto L_0x00d7;
        L_0x00b9:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r4 = 2131361949; // 0x7f0a009d float:1.8343665E38 double:1.053032718E-314;
            r3 = r3.getString(r4);
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r6 = 2131361950; // 0x7f0a009e float:1.8343667E38 double:1.0530327183E-314;
            r4 = r4.getString(r6);
            r2.mostrar_error(r3, r4);
        L_0x00d6:
            return;
        L_0x00d7:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http_map;
            r3 = "APLICNODISP";
            r2 = r2.containsKey(r3);
            if (r2 == 0) goto L_0x00fa;
        L_0x00e5:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "";
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r6 = 2131361952; // 0x7f0a00a0 float:1.834367E38 double:1.0530327193E-314;
            r4 = r4.getString(r6);
            r2.mostrar_error(r3, r4);
            goto L_0x00d6;
        L_0x00fa:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "msg_err";
            r53 = r2.leer_map(r3);
            r2 = "";
            r0 = r53;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x0124;
        L_0x010e:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "msg_err_tit";
            r54 = r2.leer_map(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r54;
            r1 = r53;
            r2.mostrar_error(r0, r1);
            goto L_0x00d6;
        L_0x0124:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.directo;
            if (r2 != 0) goto L_0x0213;
        L_0x012c:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.desde_gplay;
            if (r2 != 0) goto L_0x0213;
        L_0x0134:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.extras;
            if (r2 == 0) goto L_0x0166;
        L_0x013c:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.extras;
            r3 = "notif_id";
            r2 = r2.getString(r3);
            if (r2 != 0) goto L_0x0213;
        L_0x014a:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.extras;
            r3 = "notif_idtema";
            r2 = r2.getString(r3);
            if (r2 != 0) goto L_0x0213;
        L_0x0158:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.extras;
            r3 = "id_remit";
            r2 = r2.getString(r3);
            if (r2 != 0) goto L_0x0213;
        L_0x0166:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "v";
            r71 = r2.leer_map(r3);
            r2 = "";
            r0 = r71;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x0213;
        L_0x017a:
            r69 = java.lang.Integer.parseInt(r71);
            r70 = -1;
            r0 = r72;
            r2 = hse.here2.preinicio.this;	 Catch:{ NameNotFoundException -> 0x2689 }
            r2 = r2.getPackageManager();	 Catch:{ NameNotFoundException -> 0x2689 }
            r0 = r72;
            r3 = hse.here2.preinicio.this;	 Catch:{ NameNotFoundException -> 0x2689 }
            r3 = r3.getPackageName();	 Catch:{ NameNotFoundException -> 0x2689 }
            r4 = 0;
            r2 = r2.getPackageInfo(r3, r4);	 Catch:{ NameNotFoundException -> 0x2689 }
            r0 = r2.versionCode;	 Catch:{ NameNotFoundException -> 0x2689 }
            r70 = r0;
        L_0x0199:
            r2 = -1;
            r0 = r70;
            if (r0 == r2) goto L_0x0213;
        L_0x019e:
            r0 = r70;
            r1 = r69;
            if (r0 >= r1) goto L_0x0213;
        L_0x01a4:
            r15 = new android.app.AlertDialog$Builder;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r15.<init>(r2);
            r2 = 0;
            r2 = r15.setCancelable(r2);
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r4 = 2131361955; // 0x7f0a00a3 float:1.8343677E38 double:1.053032721E-314;
            r3 = r3.getString(r4);
            r4 = new hse.here2.preinicio$cargarconfig$2;
            r0 = r72;
            r4.<init>();
            r2 = r2.setPositiveButton(r3, r4);
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.getResources();
            r4 = 2131361954; // 0x7f0a00a2 float:1.8343675E38 double:1.0530327203E-314;
            r3 = r3.getString(r4);
            r2 = r2.setMessage(r3);
            r20 = r2.create();
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.cbtn;
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x01fb;
        L_0x01ed:
            r2 = new hse.here2.preinicio$cargarconfig$3;
            r0 = r72;
            r1 = r20;
            r2.<init>(r1);
            r0 = r20;
            r0.setOnShowListener(r2);
        L_0x01fb:
            r20.show();
            r2 = 16908299; // 0x102000b float:2.387726E-38 double:8.3538097E-317;
            r0 = r20;
            r2 = r0.findViewById(r2);	 Catch:{ Exception -> 0x0210 }
            r2 = (android.widget.TextView) r2;	 Catch:{ Exception -> 0x0210 }
            r3 = android.graphics.Typeface.MONOSPACE;	 Catch:{ Exception -> 0x0210 }
            r2.setTypeface(r3);	 Catch:{ Exception -> 0x0210 }
            goto L_0x00d6;
        L_0x0210:
            r2 = move-exception;
            goto L_0x00d6;
        L_0x0213:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r23 = r2.edit();
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "idusu";
            r49 = r2.leer_map(r3);
            r2 = "";
            r0 = r49;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x0315;
        L_0x0231:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = java.lang.Integer.parseInt(r49);
            r2.idusu = r3;
            r2 = "idusu";
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.idusu;
            r0 = r23;
            r0.putInt(r2, r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "cod_g";
            r19 = r2.leer_map(r3);
            r2 = "";
            r0 = r19;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x0270;
        L_0x0268:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r2.cod_g;
            r19 = r0;
        L_0x0270:
            r2 = "";
            r0 = r19;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x028f;
        L_0x027a:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r19;
            r2.cod_g = r0;
            r2 = "cod_g";
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.cod_g;
            r0 = r23;
            r0.putString(r2, r3);
        L_0x028f:
            r60 = 0;
            r25 = new java.io.File;	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r2 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r0 = r72;
            r3 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r3 = r3.getPackageName();	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r0 = r25;
            r0.<init>(r2, r3);	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r2 = r25.exists();	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            if (r2 != 0) goto L_0x02bb;
        L_0x02aa:
            r25.mkdir();	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r55 = new java.io.File;	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r2 = ".nomedia";
            r0 = r55;
            r1 = r25;
            r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r55.createNewFile();	 Catch:{ Exception -> 0x0c2c, all -> 0x0c3b }
        L_0x02bb:
            r26 = new java.io.File;	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r2 = "vinebre_ac.txt";
            r0 = r26;
            r1 = r25;
            r0.<init>(r1, r2);	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r61 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r0 = r61;
            r1 = r26;
            r0.<init>(r1);	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            r14 = new java.io.BufferedWriter;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r2 = new java.io.OutputStreamWriter;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r0 = r61;
            r2.<init>(r0);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r14.<init>(r2);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r16 = r49;
            r0 = r72;
            r2 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r2 = r2.cod_g;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r3 = "";
            r2 = r2.equals(r3);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            if (r2 != 0) goto L_0x030a;
        L_0x02eb:
            r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r2.<init>();	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r0 = r16;
            r2 = r2.append(r0);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r3 = "@";
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r0 = r72;
            r3 = hse.here2.preinicio.this;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r3 = r3.cod_g;	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r16 = r2.toString();	 Catch:{ Exception -> 0x2684, all -> 0x267f }
        L_0x030a:
            r0 = r16;
            r14.write(r0);	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r14.close();	 Catch:{ Exception -> 0x2684, all -> 0x267f }
            r61.close();	 Catch:{ IOException -> 0x2679 }
        L_0x0315:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.directo;
            if (r2 != 0) goto L_0x037a;
        L_0x031d:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.checkPlayServices();
            if (r2 == 0) goto L_0x00d6;
        L_0x0327:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "SENT_TOKEN_TO_SERVER";
            r4 = 0;
            r2 = r2.getBoolean(r3, r4);
            if (r2 != 0) goto L_0x034c;
        L_0x0336:
            r50 = new android.content.Intent;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = hse.here2.RegistrationIntentService.class;
            r0 = r50;
            r0.<init>(r2, r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r50;
            r2.startService(r0);
        L_0x034c:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.getPackageManager();
            r3 = "android.permission.ACCESS_FINE_LOCATION";
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r4 = r4.getPackageName();
            r2 = r2.checkPermission(r3, r4);
            if (r2 != 0) goto L_0x037a;
        L_0x0364:
            r30 = new android.content.Intent;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = hse.here2.s_obtenerpos.class;
            r0 = r30;
            r0.<init>(r2, r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r30;
            r2.startService(r0);
        L_0x037a:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "f_p_elim";
            r2 = r2.leer_map(r3);
            r3 = "1";
            r2 = r2.equals(r3);
            if (r2 == 0) goto L_0x039e;
        L_0x038c:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r4 = 1;
            r2 = r2.getTempFile(r3, r4);
            r2.delete();
        L_0x039e:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "splash";
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x03c8;
        L_0x03b0:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c1_sp";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c2_sp";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
        L_0x03c8:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.result_http_map;
            r3 = "adotro2_cod";
            r2 = r2.containsKey(r3);
            if (r2 == 0) goto L_0x03ee;
        L_0x03d6:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "adotro2_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "adotro2_tipo";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
        L_0x03ee:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_share_ord";
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x0b44;
        L_0x0400:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "nd_t";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "nd_s";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "nd_u";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "nd_i";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_ofics_ord";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_share_ord";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_busc_ord";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_exit_ord";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ico_notif_ord";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "io1";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "io2";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "io3";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "io4";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ib1";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ib2";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ib3";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ib4";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "is1";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "is2";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "is3";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "is4";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ie1";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ie2";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ie3";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ie4";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "in1";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "in2";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "in3";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "in4";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "share_subject";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "share_text";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "tcn";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_pos";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_sma";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "a_p_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "n_p_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "a_m_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "n_m_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "a_r_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "n_r_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "a_p_s";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "a_m_s";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_mo";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_ms";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "our";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_int_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_int_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_inte_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_ch_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "b_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "l_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "fp";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_p";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_t_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_a_s";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_s_s";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_a_b";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_s_b";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_a_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "r_s_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "appnext_ch_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "appnext_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "appnext_cod_int_e";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "appnext_cod_int_ia";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "ap";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "mu";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "appnextb_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_int_exit_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "admob_int_exit_r";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "adotro_cod";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "mf_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "tipomenu";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "petic_ask_nombre";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "petic_ask_email";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "petic_ask_tel";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c1";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c2";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_icos";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_icos_t";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_secc_noactiv";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_secc_activ";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_linea";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "t_ind";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_ind";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "t_mas";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "sep_secc";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_sep_secc";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c1_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c2_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_ssl";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_tit_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_sep_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_ico_sep_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_txt_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_icos_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_ir_ofic";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c1_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c2_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_txt_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_icos_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_icos_2_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_tit_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_sep_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_ico_sep_prods";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c1_prods_l";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c2_prods_l";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_perprod";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_tit_prods_l";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_precio_prods_l";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_antiguedad_prods_l";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "c_prods_det";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "splash_w";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "s_v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "s_h";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_ncols";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_padding";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_txt_radius";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_mostrar_icos";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_mostrar_txt";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_icos_izq";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_anim";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_txt_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_txt_b";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_e";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_txt_bg";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_txt_col";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_c1";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "m_c2";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "msl_c";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "fm";
            r4 = 1;
            r0 = r23;
            r27 = r2.establec(r3, r4, r0);
            r2 = "1";
            r0 = r27;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x0c40;
        L_0x0a3b:
            r2 = "act_fm";
            r3 = 1;
            r0 = r23;
            r0.putBoolean(r2, r3);
        L_0x0a43:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "vf";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "dom";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "fb_m";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "fb_b";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "vcn";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "hcn";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "conf_ex";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "pp";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "notif_int";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "bv_txt";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "bv_tit";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "bv_nomas_mostrar";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "bv_nomas_def";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_tit";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_txt";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_ok";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_ko";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_1v";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rt_int";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "pwd";
            r4 = 1;
            r0 = r23;
            r13 = r2.establec(r3, r4, r0);
            r2 = "0";
            r2 = r13.equals(r2);
            if (r2 == 0) goto L_0x0c54;
        L_0x0b3c:
            r2 = "pwd_validado";
            r3 = 1;
            r0 = r23;
            r0.putBoolean(r2, r3);
        L_0x0b44:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "busc_campos";
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x0be6;
        L_0x0b56:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "divisa";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "busc_campos";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "busc_orden";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "busc_def";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_tit";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_masinfo";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_comprar";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_masinfo_mostrar";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_linksexternos";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "prods_adaptar_ancho";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "pp_email";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "pp_div";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
        L_0x0be6:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "nsecc";
            r4 = 0;
            r58 = r2.getInt(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "nsecc";
            r59 = r2.leer_map(r3);
            r2 = "";
            r0 = r59;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x0c14;
        L_0x0c07:
            r58 = java.lang.Integer.parseInt(r59);
            r2 = "nsecc";
            r0 = r23;
            r1 = r58;
            r0.putInt(r2, r1);
        L_0x0c14:
            if (r58 != 0) goto L_0x0c66;
        L_0x0c16:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "";
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r6 = 2131361953; // 0x7f0a00a1 float:1.8343673E38 double:1.05303272E-314;
            r4 = r4.getString(r6);
            r2.mostrar_error(r3, r4);
            goto L_0x00d6;
        L_0x0c2c:
            r22 = move-exception;
            r22.printStackTrace();	 Catch:{ Exception -> 0x0c32, all -> 0x0c3b }
            goto L_0x02bb;
        L_0x0c32:
            r2 = move-exception;
        L_0x0c33:
            r60.close();	 Catch:{ IOException -> 0x0c38 }
            goto L_0x0315;
        L_0x0c38:
            r2 = move-exception;
            goto L_0x0315;
        L_0x0c3b:
            r2 = move-exception;
        L_0x0c3c:
            r60.close();	 Catch:{ IOException -> 0x267c }
        L_0x0c3f:
            throw r2;
        L_0x0c40:
            r2 = "0";
            r0 = r27;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x0a43;
        L_0x0c4a:
            r2 = "act_fm";
            r3 = 0;
            r0 = r23;
            r0.putBoolean(r2, r3);
            goto L_0x0a43;
        L_0x0c54:
            r2 = "1";
            r2 = r13.equals(r2);
            if (r2 == 0) goto L_0x0b44;
        L_0x0c5c:
            r2 = "pwd_validado";
            r3 = 0;
            r0 = r23;
            r0.putBoolean(r2, r3);
            goto L_0x0b44;
        L_0x0c66:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "idseccs";
            r4 = "";
            r44 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "idseccs";
            r46 = r2.leer_map(r3);
            r2 = "";
            r0 = r46;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x0c93;
        L_0x0c88:
            r44 = r46;
            r2 = "idseccs";
            r0 = r23;
            r1 = r44;
            r0.putString(r2, r1);
        L_0x0c93:
            r2 = ",";
            r0 = r44;
            r45 = r0.split(r2);
            r24 = 0;
            r29 = 0;
        L_0x0c9f:
            r0 = r45;
            r2 = r0.length;
            r0 = r29;
            if (r0 >= r2) goto L_0x1f6f;
        L_0x0ca6:
            r43 = r45[r29];
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x1760;
        L_0x0cd3:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_tit";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_idgo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ocu";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_r";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_d";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_r_m";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ext";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_adapt";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_stream";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_idfondo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_vf";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fondo_modif";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_tipo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_url";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ua";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_html";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_pur";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_loa";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_zoo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c1";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c2";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c_peritem";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c_tit";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c_fecha";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_mostrar_img";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_mostrar_fecha";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_pwd";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r13 = r2.establec(r3, r4, r0);
            r2 = "0";
            r2 = r13.equals(r2);
            if (r2 == 0) goto L_0x1f3c;
        L_0x10e8:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "chat";
            r2 = r2.append(r3);
            r0 = r43;
            r2 = r2.append(r0);
            r3 = "_validado";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r3 = 1;
            r0 = r23;
            r0.putBoolean(r2, r3);
        L_0x1109:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_f_perfil";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_f_p_amp";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_f_chat";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_priv";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_accext";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_cat";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_sub";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fnac";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_sexo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_descr";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_dist";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_com";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_gal";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fdist";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fsexo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fedad1";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fedad2";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fdist_d";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fsexo_d";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fedad1_d";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_fedad2_d";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ncols";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_padding";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_txt_radius";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_mostrar_icos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_mostrar_txt";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_icos_izq";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_anim";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_txt_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_txt_b";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_e";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_txt_bg";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_txt_col";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_seccs";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ico_id";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_v_ico";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_w_ico";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_h_ico";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ico";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r12 = r2.establec(r3, r4, r0);
            r2 = "0";
            r2 = r12.equals(r2);
            if (r2 == 0) goto L_0x16fc;
        L_0x16da:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "img_s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ico";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2.deleteFile(r3);
        L_0x16fc:
            if (r24 != 0) goto L_0x1760;
        L_0x16fe:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "wv_sc";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "wv_sc_txt";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "wv_c";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rss_i";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rss_n";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "rss_t";
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "vfs";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "vls";
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r24 = 1;
        L_0x1760:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_ntemas";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_idtemas";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = "";
            r47 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_idtemas";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r48 = r2.leer_map(r3);
            r2 = "";
            r0 = r48;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x17fd;
        L_0x17d9:
            r47 = r48;
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "s";
            r2 = r2.append(r3);
            r0 = r43;
            r2 = r2.append(r0);
            r3 = "_idtemas";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r0 = r23;
            r1 = r47;
            r0.putString(r2, r1);
        L_0x17fd:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_nitems";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_iditems";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = "";
            r36 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "s";
            r3 = r3.append(r4);
            r0 = r43;
            r3 = r3.append(r0);
            r4 = "_iditems";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r38 = r2.leer_map(r3);
            r2 = "";
            r0 = r38;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x189a;
        L_0x1876:
            r36 = r38;
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "s";
            r2 = r2.append(r3);
            r0 = r43;
            r2 = r2.append(r0);
            r3 = "_iditems";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r0 = r23;
            r1 = r36;
            r0.putString(r2, r1);
        L_0x189a:
            r2 = "";
            r0 = r36;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x1f6b;
        L_0x18a4:
            r2 = ",";
            r0 = r36;
            r37 = r0.split(r2);
            r51 = 0;
        L_0x18ae:
            r0 = r37;
            r2 = r0.length;
            r0 = r51;
            if (r0 >= r2) goto L_0x1f6b;
        L_0x18b5:
            r35 = r37[r51];
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x1df1;
        L_0x18e2:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fcab_id";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fcab_modif";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fcab_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fcab_url";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_h";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fotos_pos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fotos_c";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fotos_h";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fcab_z";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_fotos_z";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_b";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_i";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_f";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_col";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_u";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit1_s";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_b";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_i";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_f";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_col";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_u";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_tit2_s";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_b";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_i";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_f";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_col";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_u";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_txt_s";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
        L_0x1df1:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_nfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_idfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = "";
            r32 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_idfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r34 = r2.leer_map(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it";
            r3 = r3.append(r4);
            r0 = r35;
            r3 = r3.append(r0);
            r4 = "_nfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x1eaf;
        L_0x1e8b:
            r32 = r34;
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "it";
            r2 = r2.append(r3);
            r0 = r35;
            r2 = r2.append(r0);
            r3 = "_idfotos";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r0 = r23;
            r1 = r32;
            r0.putString(r2, r1);
        L_0x1eaf:
            r2 = "";
            r0 = r32;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x1f67;
        L_0x1eb9:
            r2 = ",";
            r0 = r32;
            r33 = r0.split(r2);
            r52 = 0;
        L_0x1ec3:
            r0 = r33;
            r2 = r0.length;
            r0 = r52;
            if (r0 >= r2) goto L_0x1f67;
        L_0x1eca:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it_f";
            r3 = r3.append(r4);
            r4 = r33[r52];
            r3 = r3.append(r4);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it_f";
            r3 = r3.append(r4);
            r4 = r33[r52];
            r3 = r3.append(r4);
            r4 = "_modif";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "it_f";
            r3 = r3.append(r4);
            r4 = r33[r52];
            r3 = r3.append(r4);
            r4 = "_url";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r52 = r52 + 1;
            goto L_0x1ec3;
        L_0x1f3c:
            r2 = "1";
            r2 = r13.equals(r2);
            if (r2 == 0) goto L_0x1109;
        L_0x1f44:
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "chat";
            r2 = r2.append(r3);
            r0 = r43;
            r2 = r2.append(r0);
            r3 = "_validado";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r3 = 0;
            r0 = r23;
            r0.putBoolean(r2, r3);
            goto L_0x1109;
        L_0x1f67:
            r51 = r51 + 1;
            goto L_0x18ae;
        L_0x1f6b:
            r29 = r29 + 1;
            goto L_0x0c9f;
        L_0x1f6f:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "noficc";
            r4 = 0;
            r56 = r2.getInt(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "noficc";
            r57 = r2.leer_map(r3);
            r2 = "";
            r0 = r57;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x1f9d;
        L_0x1f90:
            r56 = java.lang.Integer.parseInt(r57);
            r2 = "noficc";
            r0 = r23;
            r1 = r56;
            r0.putInt(r2, r1);
        L_0x1f9d:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "idofics";
            r4 = "";
            r40 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "idofics";
            r42 = r2.leer_map(r3);
            r2 = "";
            r0 = r42;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x1fc9;
        L_0x1fbf:
            r2 = "";
            r0 = r57;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x1fd4;
        L_0x1fc9:
            r40 = r42;
            r2 = "idofics";
            r0 = r23;
            r1 = r40;
            r0.putString(r2, r1);
        L_0x1fd4:
            r2 = 0;
            r0 = new java.lang.String[r2];
            r41 = r0;
            r2 = "";
            r0 = r40;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x23e6;
        L_0x1fe3:
            r2 = ",";
            r0 = r40;
            r41 = r0.split(r2);
            r29 = 0;
        L_0x1fed:
            r0 = r41;
            r2 = r0.length;
            r0 = r29;
            if (r0 >= r2) goto L_0x23e6;
        L_0x1ff4:
            r39 = r41[r29];
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x22bb;
        L_0x2021:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_tit";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_dir1";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_dir2";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_cp";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_pob";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_prov";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_tel1";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_tel2";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_horario";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_x";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_y";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_z";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_email";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_t_email";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_web";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 2;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_chat";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_zoo";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
        L_0x22bb:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_nfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_idfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = "";
            r32 = r2.getString(r3, r4);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_idfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r34 = r2.leer_map(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o";
            r3 = r3.append(r4);
            r0 = r39;
            r3 = r3.append(r0);
            r4 = "_nfotos";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r2 = r2.leer_map(r3);
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x2379;
        L_0x2355:
            r32 = r34;
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "o";
            r2 = r2.append(r3);
            r0 = r39;
            r2 = r2.append(r0);
            r3 = "_idfotos";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r0 = r23;
            r1 = r32;
            r0.putString(r2, r1);
        L_0x2379:
            r2 = "";
            r0 = r32;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x23e2;
        L_0x2383:
            r2 = ",";
            r0 = r32;
            r33 = r0.split(r2);
            r51 = 0;
        L_0x238d:
            r0 = r33;
            r2 = r0.length;
            r0 = r51;
            if (r0 >= r2) goto L_0x23e2;
        L_0x2394:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o_f";
            r3 = r3.append(r4);
            r4 = r33[r51];
            r3 = r3.append(r4);
            r4 = "_ord";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 1;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "o_f";
            r3 = r3.append(r4);
            r4 = r33[r51];
            r3 = r3.append(r4);
            r4 = "_modif";
            r3 = r3.append(r4);
            r3 = r3.toString();
            r4 = 10;
            r0 = r23;
            r2.establec(r3, r4, r0);
            r51 = r51 + 1;
            goto L_0x238d;
        L_0x23e2:
            r29 = r29 + 1;
            goto L_0x1fed;
        L_0x23e6:
            r23.commit();
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "cats";
            r17 = r2.leer_map(r3);
            r2 = "";
            r0 = r17;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x2485;
        L_0x23fd:
            r65 = new hse.here2.bd;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.getApplicationContext();
            r0 = r65;
            r0.<init>(r2);
            r21 = r65.getWritableDatabase();
            if (r21 == 0) goto L_0x2485;
        L_0x2412:
            r2 = "DELETE FROM cats";
            r0 = r21;
            r0.execSQL(r2);
            r2 = "0";
            r0 = r17;
            r2 = r0.equals(r2);
            if (r2 != 0) goto L_0x2482;
        L_0x2423:
            r2 = ";";
            r0 = r17;
            r18 = r0.split(r2);
            r29 = 0;
        L_0x242d:
            r0 = r18;
            r2 = r0.length;
            r0 = r29;
            if (r0 >= r2) goto L_0x2482;
        L_0x2434:
            r2 = r18[r29];
            r3 = "@";
            r62 = r2.split(r3);
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r3 = "INSERT INTO cats(_id,idcat,descr)VALUES(";
            r2 = r2.append(r3);
            r3 = 0;
            r3 = r62[r3];
            r2 = r2.append(r3);
            r3 = ",";
            r2 = r2.append(r3);
            r3 = 1;
            r3 = r62[r3];
            r2 = r2.append(r3);
            r3 = ",'";
            r2 = r2.append(r3);
            r3 = 2;
            r3 = r62[r3];
            r4 = "'";
            r6 = "''";
            r3 = r3.replace(r4, r6);
            r2 = r2.append(r3);
            r3 = "')";
            r2 = r2.append(r3);
            r2 = r2.toString();
            r0 = r21;
            r0.execSQL(r2);
            r29 = r29 + 1;
            goto L_0x242d;
        L_0x2482:
            r21.close();
        L_0x2485:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.globales;
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r4 = r4.extras;
            r0 = r72;
            r6 = hse.here2.preinicio.this;
            r6 = r6.getIntent();
            r0 = r45;
            r1 = r41;
            r3 = r3.crear_globales(r0, r1, r4, r6);
            r2.ord_secc_abrir = r3;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2.obtener_appnext();
            r2 = android.os.Build.VERSION.SDK_INT;
            r3 = 20;
            if (r2 <= r3) goto L_0x24d5;
        L_0x24b6:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.c1;
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x24d5;
        L_0x24c6:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.globales;
            r3 = r3.c1;
            hse.here2.config.aplicar_color_top(r2, r3);
        L_0x24d5:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.settings;
            r3 = "hatenidonotif";
            r4 = 0;
            r2 = r2.getBoolean(r3, r4);
            if (r2 != 0) goto L_0x2567;
        L_0x24e4:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.notifdef_tit;
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x2567;
        L_0x24f4:
            r5 = new android.content.Intent;
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = hse.here2.preinicio.class;
            r5.<init>(r2, r3);
            r2 = android.os.Build.VERSION.SDK_INT;
            r3 = 11;
            if (r2 < r3) goto L_0x250b;
        L_0x2505:
            r2 = 268468224; // 0x10008000 float:2.5342157E-29 double:1.326409265E-315;
            r5.setFlags(r2);
        L_0x250b:
            r2 = "notif_id";
            r3 = "1";
            r5.putExtra(r2, r3);
            r64 = "0";
            r31 = "0";
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.notifdef_idabrir;
            if (r2 != 0) goto L_0x2578;
        L_0x2520:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.notifdef_url;
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x2578;
        L_0x2530:
            r64 = "2";
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r0 = r2.notifdef_url;
            r31 = r0;
        L_0x253c:
            r2 = "notif_idelem";
            r0 = r31;
            r5.putExtra(r2, r0);
            r2 = "notif_tipo";
            r0 = r64;
            r5.putExtra(r2, r0);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.globales;
            r3 = r3.notifdef_tit;
            r0 = r72;
            r4 = hse.here2.preinicio.this;
            r4 = r4.globales;
            r4 = r4.notifdef_txt;
            r6 = 0;
            r7 = 1;
            r8 = "0";
            r9 = "0";
            hse.here2.config.crear_notif(r2, r3, r4, r5, r6, r7, r8, r9);
        L_0x2567:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.directo;
            if (r2 == 0) goto L_0x25a0;
        L_0x256f:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2.iniciar();
            goto L_0x00d6;
        L_0x2578:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.notifdef_idabrir;
            if (r2 <= 0) goto L_0x253c;
        L_0x2582:
            r64 = "1";
            r2 = new java.lang.StringBuilder;
            r2.<init>();
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r3 = r3.globales;
            r3 = r3.notifdef_idabrir;
            r2 = r2.append(r3);
            r3 = "";
            r2 = r2.append(r3);
            r31 = r2.toString();
            goto L_0x253c;
        L_0x25a0:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "splash";
            r63 = r2.leer_map(r3);
            r2 = "1";
            r0 = r63;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x2643;
        L_0x25b4:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2 = r2.globales;
            r2 = r2.c1_splash;
            r3 = "";
            r2 = r2.equals(r3);
            if (r2 != 0) goto L_0x2624;
        L_0x25c4:
            r28 = new android.graphics.drawable.GradientDrawable;
            r2 = android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM;
            r3 = 2;
            r3 = new int[r3];
            r4 = 0;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "#";
            r6 = r6.append(r7);
            r0 = r72;
            r7 = hse.here2.preinicio.this;
            r7 = r7.globales;
            r7 = r7.c1_splash;
            r6 = r6.append(r7);
            r6 = r6.toString();
            r6 = android.graphics.Color.parseColor(r6);
            r3[r4] = r6;
            r4 = 1;
            r6 = new java.lang.StringBuilder;
            r6.<init>();
            r7 = "#";
            r6 = r6.append(r7);
            r0 = r72;
            r7 = hse.here2.preinicio.this;
            r7 = r7.globales;
            r7 = r7.c2_splash;
            r6 = r6.append(r7);
            r6 = r6.toString();
            r6 = android.graphics.Color.parseColor(r6);
            r3[r4] = r6;
            r0 = r28;
            r0.<init>(r2, r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = 2131165308; // 0x7f07007c float:1.794483E38 double:1.0529355643E-314;
            r2 = r2.findViewById(r3);
            r0 = r28;
            r2.setBackgroundDrawable(r0);
        L_0x2624:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "vsp";
            r68 = r2.leer_map(r3);
            r2 = new hse.here2.preinicio$cargarsplash;
            r0 = r72;
            r3 = hse.here2.preinicio.this;
            r4 = 0;
            r2.<init>();
            r3 = 1;
            r3 = new java.lang.String[r3];
            r4 = 0;
            r3[r4] = r68;
            r2.execute(r3);
            goto L_0x00d6;
        L_0x2643:
            r2 = "2";
            r0 = r63;
            r2 = r0.equals(r2);
            if (r2 == 0) goto L_0x2670;
        L_0x264d:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = 2131165490; // 0x7f070132 float:1.7945199E38 double:1.052935654E-314;
            r2 = r2.findViewById(r3);
            r2 = (android.widget.ImageView) r2;
            r3 = 0;
            r2.setImageBitmap(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r3 = "splash";
            r2.deleteFile(r3);
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2.mirar_font();
            goto L_0x00d6;
        L_0x2670:
            r0 = r72;
            r2 = hse.here2.preinicio.this;
            r2.mirar_font();
            goto L_0x00d6;
        L_0x2679:
            r2 = move-exception;
            goto L_0x0315;
        L_0x267c:
            r3 = move-exception;
            goto L_0x0c3f;
        L_0x267f:
            r2 = move-exception;
            r60 = r61;
            goto L_0x0c3c;
        L_0x2684:
            r2 = move-exception;
            r60 = r61;
            goto L_0x0c33;
        L_0x2689:
            r2 = move-exception;
            goto L_0x0199;
        L_0x268c:
            r2 = move-exception;
            goto L_0x008b;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.preinicio.cargarconfig.onPostExecute(java.lang.Byte):void");
        }
    }

    private class cargarfont extends AsyncTask<String, Void, Byte> {
        String vfont;

        class C07011 implements OnShowListener {
            C07011() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) preinicio.this.dialog_cargando.findViewById(16908301), preinicio.this.c_icos_orig);
            }
        }

        private cargarfont() {
        }

        protected Byte doInBackground(String... params) {
            this.vfont = params[0];
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/fonts/app299914.ttf?v=" + this.vfont);
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    try {
                        FileOutputStream fos = preinicio.this.openFileOutput("font", 0);
                        byte[] buf = new byte[1024];
                        while (true) {
                            int len = is.read(buf);
                            if (len > 0) {
                                fos.write(buf, 0, len);
                            } else {
                                fos.close();
                                is.close();
                                url = myFileUrl;
                                return Byte.valueOf((byte) 0);
                            }
                        }
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
            preinicio.this.dialog_cargando = new ProgressDialog(preinicio.this);
            preinicio.this.dialog_cargando.setMessage(preinicio.this.getResources().getString(C0627R.string.cargando_recursos));
            preinicio.this.dialog_cargando.setIndeterminate(true);
            if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                preinicio.this.dialog_cargando.setOnShowListener(new C07011());
            }
            preinicio.this.dialog_cargando.show();
        }

        protected void onPostExecute(Byte result) {
            try {
                preinicio.this.dialog_cargando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) -1) {
                preinicio.this.marcar_guardado = false;
            } else {
                Editor e2 = preinicio.this.settings.edit();
                e2.putString("vfu_act", this.vfont);
                e2.commit();
                preinicio.this.globales.establecerFuente(1);
            }
            preinicio.this.mirar_icos();
        }
    }

    private class cargaricohome extends AsyncTask<String, Void, Byte> {
        String vfoto;

        class C07021 implements OnShowListener {
            C07021() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) preinicio.this.dialog_cargando.findViewById(16908301), preinicio.this.c_icos_orig);
            }
        }

        private cargaricohome() {
        }

        protected Byte doInBackground(String... params) {
            this.vfoto = params[0];
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gen/299914_icohome.png?v=" + this.vfoto);
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    preinicio.this.bm_icohome = BitmapFactory.decodeStream(is);
                    try {
                        FileOutputStream fos = preinicio.this.openFileOutput("icohome", 0);
                        preinicio.this.bm_icohome.compress(CompressFormat.PNG, 100, fos);
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
            preinicio.this.dialog_cargando = new ProgressDialog(preinicio.this);
            preinicio.this.dialog_cargando.setMessage(preinicio.this.getString(C0627R.string.cargando_icohome));
            preinicio.this.dialog_cargando.setIndeterminate(true);
            if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                preinicio.this.dialog_cargando.setOnShowListener(new C07021());
            }
            preinicio.this.dialog_cargando.show();
        }

        protected void onPostExecute(Byte result) {
            try {
                preinicio.this.dialog_cargando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) -1) {
                preinicio.this.marcar_guardado = false;
            }
            preinicio.this.mirar_icosmenu();
        }
    }

    private class cargaricos extends AsyncTask<String, Void, Byte> {

        class C07031 implements OnShowListener {
            C07031() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) preinicio.this.dialog_cargando.findViewById(16908301), preinicio.this.c_icos_orig);
            }
        }

        private cargaricos() {
        }

        protected Byte doInBackground(String... params) {
            boolean imgperso = false;
            int imgperso_v = 0;
            int imgperso_v_act = 0;
            String aux1 = "";
            Editor editor = preinicio.this.settings.edit();
            for (int i = 0; i < 5; i++) {
                if (i == 0) {
                    aux1 = "ofics";
                    imgperso = preinicio.this.globales.ico_ofics_imgperso;
                    imgperso_v = preinicio.this.globales.ico_ofics_imgperso_v;
                    imgperso_v_act = preinicio.this.settings.getInt(aux1 + "_imgperso_v_act", 0);
                } else if (i == 1) {
                    aux1 = "busc";
                    imgperso = preinicio.this.globales.ico_busc_imgperso;
                    imgperso_v = preinicio.this.globales.ico_busc_imgperso_v;
                    imgperso_v_act = preinicio.this.settings.getInt(aux1 + "_imgperso_v_act", 0);
                } else if (i == 2) {
                    aux1 = "share";
                    imgperso = preinicio.this.globales.ico_share_imgperso;
                    imgperso_v = preinicio.this.globales.ico_share_imgperso_v;
                    imgperso_v_act = preinicio.this.settings.getInt(aux1 + "_imgperso_v_act", 0);
                } else if (i == 3) {
                    aux1 = "exit";
                    imgperso = preinicio.this.globales.ico_exit_imgperso;
                    imgperso_v = preinicio.this.globales.ico_exit_imgperso_v;
                    imgperso_v_act = preinicio.this.settings.getInt(aux1 + "_imgperso_v_act", 0);
                } else if (i == 4) {
                    aux1 = "notif";
                    imgperso = preinicio.this.globales.ico_notif_imgperso;
                    imgperso_v = preinicio.this.globales.ico_notif_imgperso_v;
                    imgperso_v_act = preinicio.this.settings.getInt(aux1 + "_imgperso_v_act", 0);
                }
                if (imgperso && imgperso_v > imgperso_v_act) {
                    try {
                        try {
                            HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/icos/app299914_" + aux1 + ".png?v=" + imgperso_v).openConnection();
                            conn.setDoInput(true);
                            conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                            conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                            conn.connect();
                            Bitmap bm = BitmapFactory.decodeStream(conn.getInputStream());
                            try {
                                FileOutputStream fos = preinicio.this.openFileOutput("ico_" + aux1, 0);
                                bm.compress(CompressFormat.PNG, 100, fos);
                                fos.close();
                                editor.putInt(aux1 + "_imgperso_v_act", imgperso_v);
                                editor.commit();
                            } catch (Exception e) {
                                return Byte.valueOf((byte) -1);
                            }
                        } catch (IOException e2) {
                            return Byte.valueOf((byte) -1);
                        }
                    } catch (MalformedURLException e3) {
                        return Byte.valueOf((byte) -1);
                    }
                }
            }
            return Byte.valueOf((byte) 0);
        }

        protected void onPreExecute() {
            preinicio.this.dialog_cargando = new ProgressDialog(preinicio.this);
            preinicio.this.dialog_cargando.setMessage(preinicio.this.getString(C0627R.string.cargando_recursos));
            preinicio.this.dialog_cargando.setIndeterminate(true);
            if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                preinicio.this.dialog_cargando.setOnShowListener(new C07031());
            }
            preinicio.this.dialog_cargando.show();
        }

        protected void onPostExecute(Byte result) {
            try {
                preinicio.this.dialog_cargando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) -1) {
                preinicio.this.marcar_guardado = false;
            }
            preinicio.this.mirar_icohome();
        }
    }

    private class cargarsplash extends AsyncTask<String, Void, Byte> {
        String vfoto;

        class C07041 implements OnShowListener {
            C07041() {
            }

            public void onShow(DialogInterface dialog) {
                config.progress_color((ProgressBar) preinicio.this.dialog_cargando.findViewById(16908301), preinicio.this.c_icos_orig);
            }
        }

        private cargarsplash() {
        }

        protected Byte doInBackground(String... params) {
            this.vfoto = params[0];
            try {
                URL myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gen/299914_splash.png?v=" + this.vfoto);
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    preinicio.this.bm_splash = BitmapFactory.decodeStream(is);
                    try {
                        FileOutputStream fos = preinicio.this.openFileOutput("splash", 0);
                        preinicio.this.bm_splash.compress(CompressFormat.PNG, 100, fos);
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
            preinicio.this.dialog_cargando = new ProgressDialog(preinicio.this);
            preinicio.this.dialog_cargando.setMessage(preinicio.this.getString(C0627R.string.cargando_splash));
            preinicio.this.dialog_cargando.setIndeterminate(true);
            if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                preinicio.this.dialog_cargando.setOnShowListener(new C07041());
            }
            preinicio.this.dialog_cargando.show();
        }

        protected void onPostExecute(Byte result) {
            try {
                preinicio.this.dialog_cargando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) -1) {
                preinicio.this.marcar_guardado = false;
            } else {
                ((ImageView) preinicio.this.findViewById(C0627R.id.iv_splash)).setImageBitmap(preinicio.this.bm_splash);
            }
            preinicio.this.mirar_font();
        }
    }

    private class enviarTask extends AsyncTask<String, Void, Byte> {
        private enviarTask() {
        }

        protected Byte doInBackground(String... urls) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://srv1.androidcreator.com/srv/comprobar_contra_app.php?v=1&idapp=299914&idusu=" + preinicio.this.idusu);
            String line = "";
            StringBuilder total = new StringBuilder();
            try {
                List<NameValuePair> nameValuePairs = new ArrayList(2);
                nameValuePairs.add(new BasicNameValuePair("contra", preinicio.this.contra));
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
                preinicio.this.dialog_enviando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) 1) {
                if (preinicio.this.settings.getBoolean("pwd_nomostrarmas_def", true)) {
                    Editor editor = preinicio.this.settings.edit();
                    editor.putBoolean("pwd_validado", true);
                    editor.commit();
                }
                preinicio.this.iniciar();
            } else if (result.byteValue() == (byte) 2) {
                preinicio.this.contra_ko = true;
                preinicio.this.preguntar_contra();
            } else {
                preinicio.this.mostrar_error(preinicio.this.getString(C0627R.string.error_http_tit), preinicio.this.getString(C0627R.string.error_http));
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.settings = getSharedPreferences("sh", 0);
        this.c1_sp_orig = this.settings.getString("c1_sp", "");
        this.c_icos_orig = this.settings.getString("c_icos", "");
        this.extras = getIntent().getExtras();
        this.cbtn = config.aplicar_color_dialog(this.c1_sp_orig, this.c_icos_orig);
        if (!(VERSION.SDK_INT <= 12 || this.c1_sp_orig.equals("") || config.esClaro("#" + this.c1_sp_orig))) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        if (this.extras != null && this.extras.containsKey("directo") && this.extras.getBoolean("directo", false)) {
            this.directo = true;
        }
        setContentView(C0627R.layout.preinicio);
        try {
            getWindow().setFlags(128, 128);
        } catch (Exception e) {
        }
        this.globales = (config) getApplicationContext();
        this.globales.establecerFuente(Integer.parseInt(this.settings.getString("ft", "0")));
        if ("mounted".equals(Environment.getExternalStorageState())) {
            if (!this.directo) {
                Editor editor = this.settings.edit();
                editor.putInt("n_opens", this.settings.getInt("n_opens", 0) + 1);
                editor.putLong("f_ult_notif", 0);
                editor.commit();
            }
            this.idusu = this.settings.getInt("idusu", 0);
            this.cod_g = this.settings.getString("cod_g", "");
            this.idusu_sd = false;
            if (this.idusu == 0) {
                try {
                    File f = new File(new File(Environment.getExternalStorageDirectory(), getPackageName()), "vinebre_ac.txt");
                    if (f.exists()) {
                        FileInputStream is = new FileInputStream(f);
                        String line = new BufferedReader(new InputStreamReader(is)).readLine();
                        is.close();
                        String[] line_a = line.split("@");
                        this.idusu = Integer.parseInt(line_a[0]);
                        if (line_a.length > 1) {
                            this.cod_g = line_a[1];
                        }
                        this.idusu_sd = true;
                    }
                } catch (FileNotFoundException e2) {
                } catch (IOException e3) {
                } catch (Exception e4) {
                }
            }
            if (this.idusu > 0) {
                if (VERSION.SDK_INT > 20 && !this.settings.getString("c1", "").equals("")) {
                    config.aplicar_color_top(this, this.settings.getString("c1", ""));
                }
                if (!this.directo) {
                    boolean hay_splash = false;
                    try {
                        FileInputStream fis = openFileInput("splash");
                        this.bm_splash = BitmapFactory.decodeFileDescriptor(fis.getFD());
                        fis.close();
                        hay_splash = true;
                    } catch (FileNotFoundException e5) {
                    } catch (IOException e6) {
                    }
                    if (hay_splash) {
                        if (!this.settings.getString("c1_sp", "").equals("")) {
                            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.settings.getString("c1_sp", "")), Color.parseColor("#" + this.settings.getString("c2_sp", ""))}));
                        }
                        ((ImageView) findViewById(C0627R.id.iv_splash)).setImageBitmap(this.bm_splash);
                    }
                }
            }
            this.desde_gplay = 0;
            this.desde_amaz = 0;
            PackageManager packageManager = getPackageManager();
            try {
                String instalador = packageManager.getInstallerPackageName(packageManager.getApplicationInfo(getPackageName(), 0).packageName);
                if (instalador != null) {
                    if ("com.android.vending".equals(instalador)) {
                        this.desde_gplay = 1;
                    } else if ("com.amazon.venezia".equals(instalador)) {
                        this.desde_amaz = 1;
                    }
                }
            } catch (Exception e7) {
            }
            config.interstitial_glob = null;
            config.finalizar_app = false;
            config.appnext_glob_int = null;
            config.appnext_glob_fs = null;
            this.globales.appnext_ads = null;
            this.globales.appnext_imgs = null;
            this.globales.appnext_imgs_g = null;
            new cargarconfig().execute(new String[0]);
            return;
        }
        final AlertDialog d_aux = new Builder(this).setPositiveButton(C0627R.string.aceptar, new C06861()).setMessage(C0627R.string.nosd).create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e8) {
        }
    }

    String establec(String var, int tipo, Editor e) {
        String valor = "";
        if (this.result_http_map.containsKey(var)) {
            valor = (String) this.result_http_map.get(var);
            if (tipo == 1) {
                e.putInt(var, Integer.parseInt(valor));
            } else if (tipo == 2) {
                e.putString(var, valor);
            } else if (tipo == 3) {
                e.putFloat(var, Float.parseFloat(valor));
            } else if (tipo == 10) {
                e.putBoolean(var, true);
                e.putBoolean(var + "_g", true);
            }
        }
        return valor;
    }

    void mirar_font() {
        String font_str = leer_map("ft");
        String v_font = leer_map("vfu");
        if (v_font.equals("")) {
            v_font = "0";
        }
        if (font_str.equals("")) {
            mirar_icos();
            return;
        }
        Editor editor = this.settings.edit();
        editor.putString("ft", font_str);
        editor.putString("vfu", v_font);
        editor.commit();
        if (font_str.equals("0")) {
            this.globales.establecerFuente(0);
            mirar_icos();
        } else if (font_str.equals("1")) {
            if (Integer.parseInt(v_font) > Integer.parseInt(this.settings.getString("vfu_act", "0"))) {
                new cargarfont().execute(new String[]{v_font});
                return;
            }
            this.globales.establecerFuente(1);
            mirar_icos();
        } else {
            this.globales.establecerFuente(Integer.parseInt(font_str));
            mirar_icos();
        }
    }

    void mirar_icos() {
        if ((!this.globales.ico_ofics_imgperso || this.globales.ico_ofics_imgperso_v <= this.settings.getInt("ofics_imgperso_v_act", 0)) && ((!this.globales.ico_busc_imgperso || this.globales.ico_busc_imgperso_v <= this.settings.getInt("busc_imgperso_v_act", 0)) && ((!this.globales.ico_share_imgperso || this.globales.ico_share_imgperso_v <= this.settings.getInt("share_imgperso_v_act", 0)) && ((!this.globales.ico_exit_imgperso || this.globales.ico_exit_imgperso_v <= this.settings.getInt("exit_imgperso_v_act", 0)) && (!this.globales.ico_notif_imgperso || this.globales.ico_notif_imgperso_v <= this.settings.getInt("notif_imgperso_v_act", 0)))))) {
            mirar_icohome();
        } else {
            new cargaricos().execute(new String[0]);
        }
    }

    void mirar_icohome() {
        String icohome_str = leer_map("icohome");
        if (icohome_str.equals("1")) {
            String v_icohome = leer_map("vih");
            new cargaricohome().execute(new String[]{v_icohome});
        } else if (icohome_str.equals("2")) {
            deleteFile("icohome");
            mirar_icosmenu();
        } else {
            mirar_icosmenu();
        }
    }

    void mirar_icosmenu() {
        if (this.globales.tipomenu == 2 && this.globales.icos_pendientes) {
            startService(new Intent(this, s_cargar_icos.class));
            fin_preinicio();
            return;
        }
        if (this.globales.tipomenu == 1 && this.globales.slider_v > 0 && this.settings.getInt("slider_v_act", 0) != this.globales.slider_v) {
            startService(new Intent(this, s_cargar_sliderheader.class));
        }
        fin_preinicio();
    }

    void fin_preinicio() {
        if (this.marcar_guardado) {
            String fus = leer_map("fus");
            if (!fus.equals("")) {
                Editor editor = this.settings.edit();
                editor.putString("fultsync", fus);
                editor.commit();
            }
        }
        if (this.result_http_map.containsKey("espera")) {
            this.espera = Integer.parseInt(leer_map("espera"));
            this.pd_espera = new ProgressDialog(this);
            this.pd_espera.setCancelable(true);
            this.pd_espera.setCanceledOnTouchOutside(false);
            this.pd_espera.setTitle(getResources().getString(C0627R.string.appnoreg));
            this.pd_espera.setMessage(this.espera + "s.");
            this.pd_espera.setIndeterminate(true);
            this.pd_espera.setOnCancelListener(new C06893());
            this.pd_espera.show();
            final Handler handle = new C06904();
            this.th_espera = new Thread(new Runnable() {
                public void run() {
                    while (preinicio.this.espera > 0) {
                        try {
                            Thread.sleep(1000);
                            handle.sendMessage(handle.obtainMessage());
                        } catch (Exception e) {
                            return;
                        }
                    }
                }
            });
            this.th_espera.start();
            return;
        }
        preiniciar();
    }

    private void preiniciar() {
        this.bienvenida_mostrar = false;
        if (!(this.globales.bienvenida_txt.equals("") || this.settings.getBoolean("bienvenida_nomas", false))) {
            this.bienvenida_mostrar = true;
            this.adb = new Builder(this);
            if (!this.globales.bienvenida_tit.equals("")) {
                this.adb.setTitle(this.globales.bienvenida_tit);
            }
            if (this.globales.bienvenida_nomas_mostrar) {
                View eulaLayout = getLayoutInflater().inflate(C0627R.layout.bienvenida, null);
                TextView tv_bv = (TextView) eulaLayout.findViewById(C0627R.id.message);
                tv_bv.setMovementMethod(new ScrollingMovementMethod());
                tv_bv.setText(Html.fromHtml(this.globales.bienvenida_txt));
                this.dontShowAgain = (CheckBox) eulaLayout.findViewById(C0627R.id.skip);
                if (!this.cbtn.equals("")) {
                    config.checkbox_color(this.dontShowAgain, this.cbtn);
                }
                this.dontShowAgain.setChecked(this.globales.bienvenida_nomas_def);
                this.adb.setView(eulaLayout);
            } else {
                this.adb.setMessage(Html.fromHtml(this.globales.bienvenida_txt));
            }
            this.adb.setCancelable(false);
            this.adb.setPositiveButton(getString(C0627R.string.aceptar), new C06926());
        }
        boolean tsplash_aux = false;
        Drawable d_aux = ((ImageView) findViewById(C0627R.id.iv_splash)).getDrawable();
        if (!(d_aux == null || ((BitmapDrawable) d_aux).getBitmap() == null)) {
            tsplash_aux = true;
        }
        if (this.globales.splash_w > 0 && tsplash_aux) {
            new C06957().start();
        } else if (this.bienvenida_mostrar) {
            final AlertDialog adb_ad = this.adb.create();
            if (!this.cbtn.equals("")) {
                adb_ad.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        adb_ad.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                    }
                });
            }
            try {
                adb_ad.show();
                if (!this.globales.bienvenida_nomas_mostrar) {
                    try {
                        ((TextView) adb_ad.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                preiniciar_4();
            }
        } else {
            preiniciar_4();
        }
    }

    private void preiniciar_4() {
        if (this.globales.rate_primeravez > 0) {
            int n_aux = this.settings.getInt("rt_n", 0);
            if (n_aux == -1) {
                preiniciar_2();
                return;
            }
            n_aux++;
            Editor editor = this.settings.edit();
            editor.putInt("rt_n", n_aux);
            editor.commit();
            if (n_aux < this.globales.rate_primeravez) {
                preiniciar_2();
                return;
            } else if (n_aux == this.globales.rate_primeravez || (this.globales.rate_int > 0 && (n_aux - this.globales.rate_primeravez) % this.globales.rate_int == 0)) {
                CharSequence string;
                Builder a = new Builder(this);
                a.setCancelable(false);
                a.setPositiveButton(this.globales.rate_ok.equals("") ? getResources().getString(C0627R.string.aceptar) : this.globales.rate_ok, new C06979());
                if (this.globales.rate_ko.equals("")) {
                    string = getResources().getString(C0627R.string.cancelar);
                } else {
                    string = this.globales.rate_ko;
                }
                a.setNegativeButton(string, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        preinicio.this.preiniciar_2();
                    }
                });
                if (!this.globales.rate_tit.equals("")) {
                    a.setTitle(this.globales.rate_tit);
                }
                if (!this.globales.rate_txt.equals("")) {
                    a.setMessage(this.globales.rate_txt);
                }
                final AlertDialog d_aux = a.create();
                if (!this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                            d_aux.getButton(-2).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                        }
                    });
                }
                try {
                    d_aux.show();
                } catch (Exception e) {
                }
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    return;
                } catch (Exception e2) {
                    return;
                }
            } else {
                preiniciar_2();
                return;
            }
        }
        preiniciar_2();
    }

    protected void preiniciar_3() {
        if (this.settings.getBoolean("pwd_validado", true)) {
            iniciar();
        } else {
            preguntar_contra();
        }
    }

    private void preiniciar_2() {
        this.mostrar_ad_entrar = false;
        int toca_intentrar = this.globales.toca_intentrar();
        if (toca_intentrar == 2) {
            if (config.hay_wifi(this)) {
                config.appnext_glob_int = new Interstitial(getApplicationContext(), config.appnext_cod_int_e);
                if (config.mute.booleanValue()) {
                    config.appnext_glob_int.setMute(true);
                }
                if (!config.autoplay.booleanValue()) {
                    config.appnext_glob_int.setAutoPlay(false);
                }
                config.appnext_glob_int.setBackButtonCanClose(true);
                config.appnext_glob_int.setSkipText(getResources().getString(C0627R.string.ad_saltar));
                config.appnext_glob_int.setButtonText(getResources().getString(C0627R.string.ad_instalar));
                config.appnext_glob_int.setOnAdLoadedCallback(new OnAdLoaded() {
                    public void adLoaded() {
                        config.appnext_glob_int.showAd();
                    }
                });
                config.appnext_glob_int.setOnAdClosedCallback(new OnAdClosed() {
                    public void onAdClosed() {
                        config.appnext_glob_int = null;
                        preinicio.this.preiniciar_3();
                    }
                });
                config.appnext_glob_int.setOnAdErrorCallback(new OnAdError() {
                    public void adError(String error) {
                        config.appnext_glob_int = null;
                        preinicio.this.preiniciar_3();
                    }
                });
                config.appnext_glob_int.loadAd();
            } else if (config.hay_4g(this)) {
                config.appnext_glob_int = new Interstitial(getApplicationContext(), config.appnext_cod_int_e);
                if (config.mute.booleanValue()) {
                    config.appnext_glob_int.setMute(true);
                }
                if (!config.autoplay.booleanValue()) {
                    config.appnext_glob_int.setAutoPlay(false);
                }
                config.appnext_glob_int.setBackButtonCanClose(true);
                config.appnext_glob_int.setSkipText(getResources().getString(C0627R.string.ad_saltar));
                config.appnext_glob_int.setButtonText(getResources().getString(C0627R.string.ad_instalar));
                config.appnext_glob_int.setOnAdLoadedCallback(new OnAdLoaded() {
                    public void adLoaded() {
                        config.appnext_glob_int.showAd();
                    }
                });
                config.appnext_glob_int.setOnAdClosedCallback(new OnAdClosed() {
                    public void onAdClosed() {
                        config.appnext_glob_int = null;
                    }
                });
                config.appnext_glob_int.setOnAdErrorCallback(new OnAdError() {
                    public void adError(String error) {
                        config.appnext_glob_int = null;
                    }
                });
                config.appnext_glob_int.loadAd();
                preiniciar_3();
            } else {
                preiniciar_3();
            }
        } else if (toca_intentrar == 1) {
            this.mostrar_ad_entrar = true;
            preiniciar_3();
        } else {
            preiniciar_3();
        }
    }

    private void iniciar() {
        Intent i;
        Editor e;
        Intent i_pre;
        if (this.extras != null && this.extras.getString("abrir_perfil") != null) {
            i = new Intent(this, profile.class);
            i.putExtra("id", this.extras.getString("abrir_perfil"));
            i.putExtra("privados", this.extras.getString("privados"));
            i.putExtra("nombre", this.extras.getString("nombre"));
            i.putExtra("coments", this.extras.getString("coments"));
            i.putExtra("fnac_d", this.extras.getString("fnac_d"));
            i.putExtra("fnac_m", this.extras.getString("fnac_m"));
            i.putExtra("fnac_a", this.extras.getString("fnac_a"));
            i.putExtra("sexo", this.extras.getString("sexo"));
            i.putExtra("vfoto", this.extras.getString("vfoto"));
            i.putExtra("p_fnac", this.extras.getInt("p_fnac"));
            i.putExtra("p_sexo", this.extras.getInt("p_sexo"));
            i.putExtra("p_descr", this.extras.getInt("p_descr"));
            i.putExtra("p_dist", this.extras.getInt("p_dist"));
            i.putExtra("coments_chat", this.extras.getBoolean("coments_chat"));
            i.putExtra("galeria", this.extras.getBoolean("galeria"));
            i.putExtra("privados_chat", this.extras.getBoolean("privados_chat"));
            i.putExtra("fotos_perfil", this.extras.getInt("fotos_perfil"));
            i.putExtra("fotos_chat", this.extras.getInt("fotos_chat"));
            this.globales.ind_secc_sel = this.ord_secc_abrir;
            this.globales.ind_secc_sel_2 = this.ord_secc_abrir;
            if (this.ord_secc_abrir == 998 || this.globales.secciones_a[this.ord_secc_abrir].oculta) {
                this.globales.ind_secc_sel = 900;
                if (this.ord_secc_abrir == 998) {
                    this.globales.ind_secc_sel_2 = 0;
                }
            }
            e = this.settings.edit();
            e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
            e.putInt("ind_secc_sel_2", this.globales.ind_secc_sel_2);
            e.commit();
            if (this.globales.tipomenu == 2) {
                i_pre = new Intent(this, t_menugrid.class);
                i_pre.putExtra("intent_abrir", i);
                i_pre.putExtra("es_root", true);
                startActivity(i_pre);
            } else {
                i.putExtra("es_root", true);
                startActivity(i);
            }
        } else if (this.extras != null && this.extras.getString("notif_id") != null && this.extras.getString("notif_tipo").equals("2")) {
            i = new Intent(this, t_url.class);
            if (this.mostrar_ad_entrar) {
                i.putExtra("ad_entrar", true);
            }
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.extras.getString("notif_idelem"));
            this.globales.ind_secc_sel = 900;
            e = getSharedPreferences("sh", 0).edit();
            e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
            e.commit();
            if (this.globales.tipomenu == 2) {
                i_pre = new Intent(this, t_menugrid.class);
                i_pre.putExtra("intent_abrir", i);
                i_pre.putExtra("es_root", true);
                startActivity(i_pre);
            } else {
                i.putExtra("es_root", true);
                startActivity(i);
            }
        } else if (this.extras != null && this.extras.getString("notif_id") != null && this.extras.getString("notif_tipo").equals("3")) {
            i = new Intent(this, chat.class);
            if (this.mostrar_ad_entrar) {
                i.putExtra("ad_entrar", true);
            }
            this.globales.ind_secc_sel = 900;
            e = this.settings.edit();
            e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
            e.commit();
            if (this.globales.tipomenu == 2) {
                i_pre = new Intent(this, t_menugrid.class);
                i_pre.putExtra("intent_abrir", i);
                i_pre.putExtra("es_root", true);
                startActivity(i_pre);
            } else {
                i.putExtra("es_root", true);
                startActivity(i);
            }
        } else if (this.globales.tipomenu == 2 && (((this.extras != null && this.extras.getString("notif_id") != null && this.extras.getString("notif_tipo").equals("0")) || this.extras == null || this.extras.getString("notif_id") == null) && ((this.extras == null || (this.extras.getString("notif_idtema") == null && this.extras.getString("id_remit") == null)) && (getIntent().getDataString() == null || getIntent().getDataString().endsWith("www.androidcreator.com/open299914/"))))) {
            i = new Intent(this, t_menugrid.class);
            if (this.mostrar_ad_entrar) {
                i.putExtra("ad_entrar", true);
            }
            this.globales.ind_secc_sel = 900;
            e = this.settings.edit();
            e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
            e.commit();
            i.putExtra("es_root", true);
            startActivity(i);
        } else {
            if (this.ord_secc_abrir == 998 || this.globales.secciones_a[this.ord_secc_abrir].oculta) {
                this.globales.ind_secc_sel = 900;
                e = this.settings.edit();
                e.putInt("ind_secc_sel", this.globales.ind_secc_sel);
                e.commit();
            }
            ResultGetIntent rgi = this.globales.crear_rgi(Integer.valueOf(this.ord_secc_abrir), this);
            if (this.mostrar_ad_entrar) {
                rgi.f34i.putExtra("ad_entrar", true);
            }
            if (!(this.extras == null || this.extras.getString("id_remit") == null)) {
                rgi.f34i.putExtra("id_remit", this.extras.getString("id_remit"));
                rgi.f34i.putExtra("nombre_remit", this.extras.getString("nombre_remit"));
                if (this.directo) {
                    rgi.f34i.putExtra("empezar_privado", true);
                }
            }
            if (!(this.ord_secc_abrir != 998 || this.extras == null || (this.extras.getString("id_remit") == null && this.extras.getString("notif_idtema") == null))) {
                rgi.f34i.putExtra("externo", true);
                if (this.extras.getString("id_remit") != null) {
                    String str;
                    rgi.f34i.putExtra("idchat", 0);
                    rgi.f34i.putExtra("idtema", 0);
                    rgi.f34i.putExtra("fotos_perfil", 1);
                    rgi.f34i.putExtra("fnac", 1);
                    rgi.f34i.putExtra("sexo", 1);
                    rgi.f34i.putExtra("descr", 1);
                    rgi.f34i.putExtra("dist", 1);
                    rgi.f34i.putExtra("privados", true);
                    rgi.f34i.putExtra("coments", true);
                    rgi.f34i.putExtra("galeria", true);
                    rgi.f34i.putExtra("fotos_chat", 1);
                    String c1_aux = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
                    String c2_aux = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
                    Intent intent = rgi.f34i;
                    String str2 = "c1";
                    if (c1_aux.equals("")) {
                        str = this.globales.c1;
                    } else {
                        str = c1_aux;
                    }
                    intent.putExtra(str2, str);
                    Intent intent2 = rgi.f34i;
                    String str3 = "c2";
                    if (c1_aux.equals("")) {
                        c2_aux = this.globales.c2;
                    }
                    intent2.putExtra(str3, c2_aux);
                } else {
                    rgi.f34i.putExtra("idchat", Integer.parseInt(this.extras.getString("notif_idsecc")));
                    rgi.f34i.putExtra("idtema", Integer.parseInt(this.extras.getString("notif_idtema")));
                    rgi.f34i.putExtra("fotos_perfil", this.extras.getInt("fotos_perfil"));
                    rgi.f34i.putExtra("fnac", this.extras.getInt("fnac"));
                    rgi.f34i.putExtra("sexo", this.extras.getInt("sexo"));
                    rgi.f34i.putExtra("descr", this.extras.getInt("descr"));
                    rgi.f34i.putExtra("dist", this.extras.getInt("dist"));
                    rgi.f34i.putExtra("privados", this.extras.getBoolean("privados"));
                    rgi.f34i.putExtra("coments", this.extras.getBoolean("coments"));
                    rgi.f34i.putExtra("galeria", this.extras.getBoolean("galeria"));
                    rgi.f34i.putExtra("fotos_chat", this.extras.getInt("fotos_chat"));
                    rgi.f34i.putExtra("c1", this.extras.getString("c1"));
                    rgi.f34i.putExtra("c2", this.extras.getString("c2"));
                }
            }
            if (this.globales.tipomenu == 2) {
                i_pre = new Intent(this, t_menugrid.class);
                i_pre.putExtra("intent_abrir", rgi.f34i);
                i_pre.putExtra("es_root", true);
                startActivity(i_pre);
            } else {
                rgi.f34i.putExtra("es_root", true);
                startActivity(rgi.f34i);
            }
        }
        finish();
    }

    String leer_map(String clave) {
        if (this.directo) {
            return "";
        }
        try {
            if (this.result_http_map.containsKey(clave)) {
                return (String) this.result_http_map.get(clave);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    void mostrar_error(String titulo, String texto) {
        Builder builder = new Builder(this);
        builder.setCancelable(false).setPositiveButton(getString(C0627R.string.cerrar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                preinicio.this.finish();
            }
        }).setMessage(texto);
        if (!titulo.equals("")) {
            builder.setTitle(titulo);
        }
        final AlertDialog d_aux = builder.create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
    }

    public void onResume() {
        super.onResume();
        config.onResume_global(this);
        if (this.desde_rate) {
            preiniciar_2();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result == 0) {
            return true;
        }
        if (googleAPI.isUserResolvableError(result)) {
            googleAPI.getErrorDialog(this, result, REQUEST_GOOGLE_PLAY_SERVICES).show();
        }
        return false;
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
            if (config.esClaro("#" + this.c1_sp_orig)) {
                z = false;
            } else {
                z = true;
            }
            config.edittext_color(editText, Boolean.valueOf(z), this.cbtn);
            config.checkbox_color(this.dontShowAgain, this.cbtn);
        }
        this.dontShowAgain.setChecked(this.settings.getBoolean("pwd_nomostrarmas_def", true));
        this.adb.setView(eulaLayout);
        this.adb.setCancelable(true);
        this.adb.setPositiveButton(getString(C0627R.string.aceptar), new OnClickListener() {

            class C06871 implements OnShowListener {
                C06871() {
                }

                public void onShow(DialogInterface dialog) {
                    config.progress_color((ProgressBar) preinicio.this.dialog_enviando.findViewById(16908301), preinicio.this.c_icos_orig);
                }
            }

            public void onClick(DialogInterface dialog, int which) {
                Editor editor = preinicio.this.settings.edit();
                if (preinicio.this.dontShowAgain.isChecked()) {
                    editor.putBoolean("pwd_nomostrarmas_def", true);
                } else {
                    editor.putBoolean("pwd_nomostrarmas_def", false);
                }
                editor.commit();
                preinicio.this.dialog_enviando = new ProgressDialog(preinicio.this);
                preinicio.this.dialog_enviando.setMessage(preinicio.this.getString(C0627R.string.comprobando));
                preinicio.this.dialog_enviando.setIndeterminate(true);
                if (VERSION.SDK_INT > 20 && !preinicio.this.c_icos_orig.equals("")) {
                    preinicio.this.dialog_enviando.setOnShowListener(new C06871());
                }
                preinicio.this.dialog_enviando.show();
                preinicio.this.contra = preinicio.this.et_contra.getText().toString();
                new enviarTask().execute(new String[0]);
            }
        });
        this.adb.setNegativeButton(getString(C0627R.string.cancelar), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                preinicio.this.finish();
            }
        });
        this.adb.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                preinicio.this.finish();
            }
        });
        final AlertDialog d_aux = this.adb.create();
        if (!this.cbtn.equals("")) {
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + preinicio.this.cbtn));
                }
            });
        }
        d_aux.show();
    }
}
