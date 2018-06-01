package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fb extends Activity {
    boolean desde_oncreate = false;
    ProgressDialog dialog_enviando;
    Editor f38e;
    config globales;
    SharedPreferences sp;

    class C06701 implements OnShowListener {
        C06701() {
        }

        public void onShow(DialogInterface dialog) {
            config.progress_color((ProgressBar) fb.this.dialog_enviando.findViewById(16908301), fb.this.globales.c_icos);
        }
    }

    private class bajar_foto extends AsyncTask<String, Void, Byte> {
        Uri uri;

        public bajar_foto(Uri uri) {
            this.uri = uri;
        }

        protected Byte doInBackground(String... urls) {
            URL myFileUrl = null;
            try {
                myFileUrl = new URL("https://graph.facebook.com/" + this.uri.getQueryParameter("idfb") + "/picture?type=large");
            } catch (MalformedURLException e) {
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                conn.setReadTimeout(20000);
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bmImg = BitmapFactory.decodeStream(is);
                is.close();
                conn.disconnect();
                try {
                    bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(fb.this.globales.getTempFile(fb.this, 1)));
                } catch (Exception e2) {
                }
            } catch (IOException e3) {
            }
            return Byte.valueOf((byte) 1);
        }

        protected void onPostExecute(Byte result) {
            try {
                fb.this.dialog_enviando.dismiss();
            } catch (Exception e) {
            }
            fb.this.continuar(this.uri);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        super.onCreate(savedInstanceState);
        this.desde_oncreate = true;
        this.sp = getSharedPreferences("sh", 0);
        this.f38e = this.sp.edit();
        this.f38e.remove("fb_result");
        this.f38e.commit();
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(getIntent().getExtras().getString(PlusShare.KEY_CALL_TO_ACTION_URL) + "&ts=" + System.currentTimeMillis()));
        i.setFlags(DriveFile.MODE_READ_ONLY);
        i.setFlags(1073741824);
        startActivity(i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onResume() {
        /*
        r21 = this;
        super.onResume();
        r0 = r21;
        r0 = r0.desde_oncreate;
        r16 = r0;
        if (r16 == 0) goto L_0x0014;
    L_0x000b:
        r16 = 0;
        r0 = r16;
        r1 = r21;
        r1.desde_oncreate = r0;
    L_0x0013:
        return;
    L_0x0014:
        r0 = r21;
        r0 = r0.sp;
        r16 = r0;
        r17 = "fb_result";
        r16 = r16.contains(r17);
        if (r16 != 0) goto L_0x0026;
    L_0x0022:
        r21.finish();
        goto L_0x0013;
    L_0x0026:
        r0 = r21;
        r0 = r0.sp;
        r16 = r0;
        r17 = "fb_result";
        r18 = "";
        r16 = r16.getString(r17, r18);
        r13 = android.net.Uri.parse(r16);
        r16 = "action";
        r0 = r16;
        r14 = r13.getQueryParameter(r0);
        r16 = "cancel";
        r0 = r16;
        r16 = r14.equals(r0);
        if (r16 == 0) goto L_0x004e;
    L_0x004a:
        r21.finish();
        goto L_0x0013;
    L_0x004e:
        r16 = "ok";
        r0 = r16;
        r16 = r14.equals(r0);
        if (r16 == 0) goto L_0x0013;
    L_0x0058:
        r0 = r21;
        r0 = r0.sp;
        r16 = r0;
        r6 = r16.edit();
        r16 = "logineado_fb";
        r17 = 1;
        r0 = r16;
        r1 = r17;
        r6.putBoolean(r0, r1);
        r12 = 0;
        r16 = "gender";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "male";
        r16 = r16.equals(r17);
        if (r16 == 0) goto L_0x029b;
    L_0x007e:
        r12 = 1;
    L_0x007f:
        r16 = "codigo";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "";
        r16 = r16.equals(r17);
        if (r16 != 0) goto L_0x00a0;
    L_0x008f:
        r16 = "cod";
        r17 = "codigo";
        r0 = r17;
        r17 = r13.getQueryParameter(r0);
        r0 = r16;
        r1 = r17;
        r6.putString(r0, r1);
    L_0x00a0:
        r16 = "idusu";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "";
        r16 = r16.equals(r17);
        if (r16 != 0) goto L_0x017c;
    L_0x00b0:
        r16 = "idusu";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r0 = r21;
        r0 = r0.sp;
        r18 = r0;
        r19 = "idusu";
        r20 = 0;
        r18 = r18.getInt(r19, r20);
        r17 = r17.append(r18);
        r18 = "";
        r17 = r17.append(r18);
        r17 = r17.toString();
        r16 = r16.equals(r17);
        if (r16 != 0) goto L_0x017c;
    L_0x00df:
        r16 = "idusu";
        r17 = "idusu";
        r0 = r17;
        r17 = r13.getQueryParameter(r0);
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "cod_g";
        r0 = r16;
        r4 = r13.getQueryParameter(r0);
        r16 = "cod_g";
        r0 = r16;
        r6.putString(r0, r4);
        r16 = "idusu";
        r0 = r16;
        r3 = r13.getQueryParameter(r0);
        r16 = "";
        r0 = r16;
        r16 = r4.equals(r0);
        if (r16 != 0) goto L_0x0130;
    L_0x0115:
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r0 = r16;
        r16 = r0.append(r3);
        r17 = "@";
        r16 = r16.append(r17);
        r0 = r16;
        r16 = r0.append(r4);
        r3 = r16.toString();
    L_0x0130:
        r10 = 0;
        r7 = new java.io.File;	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r16 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r17 = r21.getPackageName();	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r0 = r16;
        r1 = r17;
        r7.<init>(r0, r1);	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r16 = r7.exists();	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        if (r16 != 0) goto L_0x0157;
    L_0x0148:
        r7.mkdir();	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r9 = new java.io.File;	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r16 = ".nomedia";
        r0 = r16;
        r9.<init>(r7, r0);	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r9.createNewFile();	 Catch:{ Exception -> 0x02ae, all -> 0x02bd }
    L_0x0157:
        r8 = new java.io.File;	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r16 = "vinebre_ac.txt";
        r0 = r16;
        r8.<init>(r7, r0);	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r11 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r11.<init>(r8);	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        r2 = new java.io.BufferedWriter;	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r16 = new java.io.OutputStreamWriter;	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r0 = r16;
        r0.<init>(r11);	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r0 = r16;
        r2.<init>(r0);	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r2.write(r3);	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r2.close();	 Catch:{ Exception -> 0x0322, all -> 0x031f }
        r11.close();	 Catch:{ IOException -> 0x031a }
    L_0x017c:
        r16 = "nick";
        r17 = "nombre";
        r0 = r17;
        r17 = r13.getQueryParameter(r0);
        r0 = r16;
        r1 = r17;
        r6.putString(r0, r1);
        r16 = "sexo";
        r0 = r16;
        r6.putInt(r0, r12);
        r16 = "datos_usu";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "";
        r16 = r16.equals(r17);
        if (r16 != 0) goto L_0x02cf;
    L_0x01a4:
        r16 = "datos_usu";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "@";
        r15 = r16.split(r17);
        r16 = "privados";
        r17 = 0;
        r17 = r15[r17];
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_d";
        r17 = 1;
        r17 = r15[r17];
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_m";
        r17 = 2;
        r17 = r15[r17];
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_a";
        r17 = 3;
        r17 = r15[r17];
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "coments";
        r17 = 4;
        r17 = r15[r17];
        r17 = java.lang.Integer.parseInt(r17);
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r0 = r15.length;
        r16 = r0;
        r17 = 5;
        r0 = r16;
        r1 = r17;
        if (r0 <= r1) goto L_0x02c2;
    L_0x0212:
        r16 = "descr";
        r17 = 5;
        r17 = r15[r17];
        r0 = r16;
        r1 = r17;
        r6.putString(r0, r1);
    L_0x021f:
        r6.commit();
        r16 = "tfoto";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "1";
        r16 = r16.equals(r17);
        if (r16 == 0) goto L_0x0313;
    L_0x0232:
        r16 = new android.app.ProgressDialog;
        r0 = r16;
        r1 = r21;
        r0.<init>(r1);
        r0 = r16;
        r1 = r21;
        r1.dialog_enviando = r0;
        r0 = r21;
        r0 = r0.dialog_enviando;
        r16 = r0;
        r17 = 2131361922; // 0x7f0a0082 float:1.834361E38 double:1.0530327045E-314;
        r0 = r21;
        r1 = r17;
        r17 = r0.getString(r1);
        r16.setMessage(r17);
        r0 = r21;
        r0 = r0.dialog_enviando;
        r16 = r0;
        r17 = 1;
        r16.setIndeterminate(r17);
        r16 = android.os.Build.VERSION.SDK_INT;
        r17 = 20;
        r0 = r16;
        r1 = r17;
        if (r0 <= r1) goto L_0x027c;
    L_0x026a:
        r0 = r21;
        r0 = r0.dialog_enviando;
        r16 = r0;
        r17 = new hse.here2.fb$1;
        r0 = r17;
        r1 = r21;
        r0.<init>();
        r16.setOnShowListener(r17);
    L_0x027c:
        r0 = r21;
        r0 = r0.dialog_enviando;
        r16 = r0;
        r16.show();
        r16 = new hse.here2.fb$bajar_foto;
        r0 = r16;
        r1 = r21;
        r0.<init>(r13);
        r17 = 0;
        r0 = r17;
        r0 = new java.lang.String[r0];
        r17 = r0;
        r16.execute(r17);
        goto L_0x0013;
    L_0x029b:
        r16 = "gender";
        r0 = r16;
        r16 = r13.getQueryParameter(r0);
        r17 = "female";
        r16 = r16.equals(r17);
        if (r16 == 0) goto L_0x007f;
    L_0x02ab:
        r12 = 2;
        goto L_0x007f;
    L_0x02ae:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ Exception -> 0x02b4, all -> 0x02bd }
        goto L_0x0157;
    L_0x02b4:
        r16 = move-exception;
    L_0x02b5:
        r10.close();	 Catch:{ IOException -> 0x02ba }
        goto L_0x017c;
    L_0x02ba:
        r16 = move-exception;
        goto L_0x017c;
    L_0x02bd:
        r16 = move-exception;
    L_0x02be:
        r10.close();	 Catch:{ IOException -> 0x031d }
    L_0x02c1:
        throw r16;
    L_0x02c2:
        r16 = "descr";
        r17 = "";
        r0 = r16;
        r1 = r17;
        r6.putString(r0, r1);
        goto L_0x021f;
    L_0x02cf:
        r16 = "privados";
        r17 = 1;
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_d";
        r17 = 0;
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_m";
        r17 = 0;
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "fnac_a";
        r17 = 0;
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "coments";
        r17 = 1;
        r0 = r16;
        r1 = r17;
        r6.putInt(r0, r1);
        r16 = "descr";
        r17 = "";
        r0 = r16;
        r1 = r17;
        r6.putString(r0, r1);
        goto L_0x021f;
    L_0x0313:
        r0 = r21;
        r0.continuar(r13);
        goto L_0x0013;
    L_0x031a:
        r16 = move-exception;
        goto L_0x017c;
    L_0x031d:
        r17 = move-exception;
        goto L_0x02c1;
    L_0x031f:
        r16 = move-exception;
        r10 = r11;
        goto L_0x02be;
    L_0x0322:
        r16 = move-exception;
        r10 = r11;
        goto L_0x02b5;
        */
        throw new UnsupportedOperationException("Method not decompiled: hse.here2.fb.onResume():void");
    }

    void continuar(Uri uri) {
        boolean z;
        Boolean es_root;
        Intent i;
        if (uri.getQueryParameter("desde_buscusus") != null) {
            if (uri.getQueryParameter("es_root") == null || !uri.getQueryParameter("es_root").equals("true")) {
                z = false;
            } else {
                z = true;
            }
            es_root = Boolean.valueOf(z);
            i = new Intent(this, t_buscusus.class);
            i.putExtra("ind", uri.getQueryParameter("desde_buscusus"));
            if (es_root.booleanValue()) {
                i.putExtra("es_root", es_root);
            }
            es_root = Boolean.valueOf(false);
            startActivity(i);
        } else {
            if (uri.getQueryParameter("es_root") == null || !uri.getQueryParameter("es_root").equals("true")) {
                z = false;
            } else {
                z = true;
            }
            es_root = Boolean.valueOf(z);
            if (uri.getQueryParameter("externo") == null || !uri.getQueryParameter("externo").equals("true")) {
                z = false;
            } else {
                z = true;
            }
            Boolean externo = Boolean.valueOf(z);
            int ind = 0;
            int idsecc = 0;
            Bundle extras = null;
            if (externo.booleanValue()) {
                extras = new Bundle();
                extras.putBoolean("externo", true);
                extras.putInt("idchat", Integer.parseInt(uri.getQueryParameter("idchat")));
                extras.putInt("idtema", Integer.parseInt(uri.getQueryParameter("idtema")));
                extras.putInt("fotos_perfil", Integer.parseInt(uri.getQueryParameter("fotos_perfil")));
                extras.putInt("fnac", Integer.parseInt(uri.getQueryParameter("fnac")));
                extras.putInt("sexo", Integer.parseInt(uri.getQueryParameter("sexo")));
                extras.putInt("descr", Integer.parseInt(uri.getQueryParameter("descr")));
                extras.putInt("dist", Integer.parseInt(uri.getQueryParameter("dist")));
                extras.putBoolean("privados", Boolean.parseBoolean(uri.getQueryParameter("privados")));
                extras.putBoolean("coments", Boolean.parseBoolean(uri.getQueryParameter("coments")));
                extras.putBoolean("galeria", Boolean.parseBoolean(uri.getQueryParameter("galeria")));
                extras.putInt("fotos_chat", Integer.parseInt(uri.getQueryParameter("fotos_chat")));
                extras.putString("c1", uri.getQueryParameter("c1") + "");
                extras.putString("c2", uri.getQueryParameter("c2"));
                if (uri.getQueryParameter("tit_cab") != null) {
                    extras.putString("tit_cab", uri.getQueryParameter("tit_cab"));
                }
            } else {
                ind = Integer.parseInt(uri.getQueryParameter("ind"));
                idsecc = Integer.parseInt(uri.getQueryParameter("idsecc"));
            }
            if (((!(externo.booleanValue() && extras.getInt("fotos_perfil") == 2) && (externo.booleanValue() || this.globales.secciones_a[ind].fotos_perfil != 2)) || this.globales.getTempFile(this, 1).exists()) && (((!(externo.booleanValue() && extras.getInt("fnac") == 2) && (externo.booleanValue() || this.globales.secciones_a[ind].p_fnac != 2)) || !(this.sp.getInt("fnac_d", 0) == 0 || this.sp.getInt("fnac_m", 0) == 0 || this.sp.getInt("fnac_a", 0) == 0)) && (((!(externo.booleanValue() && extras.getInt("sexo") == 2) && (externo.booleanValue() || this.globales.secciones_a[ind].p_sexo != 2)) || this.sp.getInt("sexo", 0) != 0) && ((!(externo.booleanValue() && extras.getInt("descr") == 2) && (externo.booleanValue() || this.globales.secciones_a[ind].p_descr != 2)) || !this.sp.getString("descr", "").equals(""))))) {
                i = new Intent(this, t_chat.class);
                if (externo.booleanValue()) {
                    i = config.completar_externo(i, extras);
                    if (extras.containsKey("tit_cab")) {
                        i.putExtra("tit_cab", extras.getString("tit_cab"));
                    }
                } else {
                    i.putExtra("idsecc", idsecc);
                }
                if (es_root.booleanValue()) {
                    i.putExtra("es_root", es_root);
                }
                es_root = Boolean.valueOf(false);
                startActivity(i);
            } else {
                i = new Intent(this, preperfil.class);
                if (externo.booleanValue()) {
                    i = config.completar_externo(i, extras);
                } else {
                    i.putExtra("idsecc", idsecc);
                }
                if (es_root.booleanValue()) {
                    i.putExtra("es_root", es_root);
                }
                es_root = Boolean.valueOf(false);
                startActivity(i);
            }
        }
        Intent data = new Intent();
        data.putExtra("finalizar", true);
        setResult(-1, data);
        finish();
    }
}
