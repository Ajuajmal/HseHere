package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class guardarprimeravez extends Activity {
    String cbtn;
    ProgressDialog dialog_guardando;
    boolean externo;
    Bundle extras;
    config globales;
    int idsecc;
    int idusu;
    int ind;
    SharedPreferences settings;

    class C06791 implements OnShowListener {
        C06791() {
        }

        public void onShow(DialogInterface dialog) {
            config.progress_color((ProgressBar) guardarprimeravez.this.dialog_guardando.findViewById(16908301), guardarprimeravez.this.globales.c_icos);
        }
    }

    private class guardar extends AsyncTask<String, Void, Byte> {

        class C06801 implements OnClickListener {
            C06801() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(guardarprimeravez.this, chat_perfil.class);
                if (guardarprimeravez.this.externo) {
                    intent = config.completar_externo(intent, guardarprimeravez.this.extras);
                } else {
                    intent.putExtra("idsecc", guardarprimeravez.this.idsecc);
                }
                if (guardarprimeravez.this.globales.tipomenu != 2) {
                    intent.putExtra("es_root", true);
                }
                guardarprimeravez.this.startActivity(intent);
            }
        }

        private guardar() {
        }

        protected Byte doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/guardar_primeravez.php?idusu=" + guardarprimeravez.this.idusu + "&idapp=" + 299914);
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("nombre", new StringBody(URLEncoder.encode(guardarprimeravez.this.settings.getString("nick", ""), "UTF-8")));
                reqEntity.addPart("descr", new StringBody(URLEncoder.encode(guardarprimeravez.this.settings.getString("descr", ""), "UTF-8")));
                reqEntity.addPart("privados", new StringBody(guardarprimeravez.this.settings.getInt("privados", 1) + ""));
                reqEntity.addPart("fnac_d", new StringBody(guardarprimeravez.this.settings.getInt("fnac_d", 0) + ""));
                reqEntity.addPart("fnac_m", new StringBody(guardarprimeravez.this.settings.getInt("fnac_m", 0) + ""));
                reqEntity.addPart("fnac_a", new StringBody(guardarprimeravez.this.settings.getInt("fnac_a", 0) + ""));
                reqEntity.addPart("sexo", new StringBody(guardarprimeravez.this.settings.getInt("sexo", 0) + ""));
                reqEntity.addPart("coments", new StringBody(guardarprimeravez.this.settings.getInt("coments", 1) + ""));
                if (!guardarprimeravez.this.settings.getString("x", "").equals("")) {
                    reqEntity.addPart("x", new StringBody(guardarprimeravez.this.settings.getString("x", "")));
                    reqEntity.addPart("y", new StringBody(guardarprimeravez.this.settings.getString("y", "")));
                }
                postRequest.setEntity(reqEntity);
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
                if (s.indexOf("ANDROID:OK COD:") != -1) {
                    String cod = s.substring(15, 21);
                    Editor ed = guardarprimeravez.this.settings.edit();
                    ed.putString("cod", cod);
                    ed.commit();
                    return Byte.valueOf((byte) 1);
                } else if (s.indexOf("ANDROID:OK") != -1) {
                    return Byte.valueOf((byte) 1);
                } else {
                    return Byte.valueOf((byte) 2);
                }
            } catch (Exception e) {
                return Byte.valueOf((byte) 2);
            }
        }

        protected void onPostExecute(Byte result) {
            Intent intent;
            try {
                guardarprimeravez.this.dialog_guardando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) 1) {
                intent = new Intent(guardarprimeravez.this, s_guardarperfil.class);
                intent.putExtra("foto1_modif", guardarprimeravez.this.settings.getString("foto1_modif", "0"));
                guardarprimeravez.this.startService(intent);
                if (guardarprimeravez.this.extras.getBoolean("desde_buscusus", false)) {
                    intent = new Intent(guardarprimeravez.this, t_buscusus.class);
                } else {
                    intent = new Intent(guardarprimeravez.this, t_chat.class);
                }
                if (guardarprimeravez.this.externo) {
                    intent = config.completar_externo(intent, guardarprimeravez.this.extras);
                } else {
                    intent.putExtra("idsecc", guardarprimeravez.this.idsecc);
                }
                if (guardarprimeravez.this.globales.tipomenu != 2) {
                    intent.putExtra("es_root", true);
                }
                guardarprimeravez.this.startActivity(intent);
                return;
            }
            Editor editor = guardarprimeravez.this.settings.edit();
            editor.remove("nick");
            editor.commit();
            try {
                final AlertDialog d_aux = new Builder(guardarprimeravez.this).setCancelable(false).setPositiveButton(guardarprimeravez.this.getString(C0627R.string.aceptar), new C06801()).setMessage(C0627R.string.error_http).create();
                if (!guardarprimeravez.this.cbtn.equals("")) {
                    d_aux.setOnShowListener(new OnShowListener() {
                        public void onShow(DialogInterface arg0) {
                            d_aux.getButton(-1).setTextColor(Color.parseColor("#" + guardarprimeravez.this.cbtn));
                        }
                    });
                }
                d_aux.show();
                try {
                    ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
                intent = new Intent(guardarprimeravez.this, chat_perfil.class);
                if (guardarprimeravez.this.externo) {
                    intent = config.completar_externo(intent, guardarprimeravez.this.extras);
                } else {
                    intent.putExtra("idsecc", guardarprimeravez.this.idsecc);
                }
                if (guardarprimeravez.this.globales.tipomenu != 2) {
                    intent.putExtra("es_root", true);
                }
                guardarprimeravez.this.startActivity(intent);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        String c1;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.extras = getIntent().getExtras();
        this.externo = this.extras.containsKey("externo");
        if (this.externo) {
            c1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
        } else {
            c1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
            this.idsecc = this.extras.getInt("idsecc");
        }
        this.cbtn = config.aplicar_color_dialog(c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + c1)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.dialog_guardando = new ProgressDialog(this);
        this.dialog_guardando.setMessage(getString(C0627R.string.guardando));
        this.dialog_guardando.setIndeterminate(true);
        if (VERSION.SDK_INT > 20) {
            this.dialog_guardando.setOnShowListener(new C06791());
        }
        this.dialog_guardando.show();
        new guardar().execute(new String[0]);
    }

    public void onStop() {
        super.onStop();
        finish();
    }
}
