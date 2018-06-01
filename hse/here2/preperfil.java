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
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class preperfil extends Activity implements OnClickListener {
    String cbtn;
    boolean desde_buscusus = false;
    private ProgressDialog dialog_enviando;
    boolean es_root;
    boolean externo;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    int idsecc;
    int idusu;
    int ind;
    ListView mDrawerList;
    boolean nocompletar = false;
    SharedPreferences sp;

    class C07051 implements OnShowListener {
        C07051() {
        }

        public void onShow(DialogInterface dialog) {
            config.progress_color((ProgressBar) preperfil.this.dialog_enviando.findViewById(16908301), preperfil.this.globales.c_icos);
        }
    }

    class C07073 implements OnItemClickListener {
        C07073() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (preperfil.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(preperfil.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(preperfil.this.globales.menu_a_secciones[position]));
            preperfil.this.onClick(view);
        }
    }

    private class cargarperfilTask extends AsyncTask<String, Void, Byte> {
        private cargarperfilTask() {
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/obtener_perfil.php?v=1&idusu=" + preperfil.this.idusu + "&c=" + preperfil.this.sp.getString("cod", "")).openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                conn.setReadTimeout(StatusCodes.AUTH_DISABLED);
                conn.setRequestProperty("User-Agent", "Android Vinebre Software");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb_total = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb_total.append(line + "\n");
                }
                String total = sb_total.toString();
                if (total.indexOf("ANDROID:PERFILKO") != -1) {
                    valueOf = Byte.valueOf((byte) 3);
                    if (conn != null) {
                        conn.disconnect();
                    }
                } else if (total.indexOf("ANDROID:OK") == -1) {
                    valueOf = Byte.valueOf((byte) 2);
                    if (conn != null) {
                        conn.disconnect();
                    }
                } else {
                    int pos1 = total.indexOf("DATOSUSU:") + 9;
                    int pos2 = total.indexOf(";", pos1);
                    String nombre = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    String f1 = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int privados = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fnac_d = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fnac_m = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int fnac_a = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int sexo = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    int coments = Integer.parseInt(total.substring(pos1, pos2));
                    pos1 = pos2 + 1;
                    pos2 = total.indexOf(";", pos1);
                    String descr = total.substring(pos1, pos2);
                    pos1 = pos2 + 1;
                    String vfoto = total.substring(pos1, total.indexOf(";", pos1));
                    Editor editor = preperfil.this.sp.edit();
                    editor.putString("nick", nombre);
                    editor.putInt("privados", privados);
                    editor.putInt("fnac_d", fnac_d);
                    editor.putInt("fnac_m", fnac_m);
                    editor.putInt("fnac_a", fnac_a);
                    editor.putInt("sexo", sexo);
                    editor.putInt("coments", coments);
                    editor.putString("descr", descr);
                    editor.commit();
                    if (f1.equals("1")) {
                        preperfil.this.bajar_foto(1, vfoto);
                    } else {
                        preperfil.this.globales.getTempFile(preperfil.this, 1).delete();
                    }
                    valueOf = Byte.valueOf((byte) 1);
                    if (conn != null) {
                        conn.disconnect();
                    }
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

        protected void onPostExecute(Byte result) {
            try {
                preperfil.this.dialog_enviando.dismiss();
            } catch (Exception e) {
            }
            if (result.byteValue() == (byte) 1) {
                Intent intent = new Intent(preperfil.this, chat_perfil.class);
                if (preperfil.this.externo) {
                    intent = config.completar_externo(intent, preperfil.this.extras);
                } else {
                    intent.putExtra("idsecc", preperfil.this.idsecc);
                }
                intent.putExtra("nocompletar", preperfil.this.nocompletar);
                intent.putExtra("desde_buscusus", preperfil.this.desde_buscusus);
                preperfil.this.startActivityForResult(intent, 0);
            } else if (result.byteValue() == (byte) 3) {
                preperfil.this.mostrar_error(3);
            } else {
                preperfil.this.mostrar_error(2);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        String c1;
        String c2;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.ind = this.globales.ind_secc_sel_2;
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
        this.externo = this.extras.containsKey("externo");
        if (this.externo) {
            c1 = this.globales.secciones_a[this.ind].c1;
            c2 = this.globales.secciones_a[this.ind].c2;
        } else {
            this.idsecc = this.extras.getInt("idsecc");
            c1 = this.globales.secciones_a[this.ind].c1;
            c2 = this.globales.secciones_a[this.ind].c2;
        }
        boolean c1_esclaro = config.esClaro("#" + c1);
        this.cbtn = config.aplicar_color_dialog(c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !c1_esclaro) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.preperfil);
        incluir_menu_pre();
        this.nocompletar = this.extras.getBoolean("nocompletar", false);
        this.desde_buscusus = this.extras.getBoolean("desde_buscusus", false);
        this.sp = getSharedPreferences("sh", 0);
        this.idusu = this.sp.getInt("idusu", 0);
        if (!c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + c1), Color.parseColor("#" + c2)}));
        }
        if (this.sp.getString("nick", "").equals("")) {
            Intent intent = new Intent(this, chat_perfil.class);
            if (this.externo) {
                intent = config.completar_externo(intent, this.extras);
            } else {
                intent.putExtra("idsecc", this.idsecc);
            }
            intent.putExtra("nocompletar", this.nocompletar);
            intent.putExtra("desde_buscusus", this.desde_buscusus);
            if (this.globales.tipomenu != 2) {
                intent.putExtra("es_root", true);
            }
            this.es_root = false;
            this.finalizar = true;
            Intent data = new Intent();
            data.putExtra("finalizar", true);
            setResult(-1, data);
            startActivityForResult(intent, 0);
            finish();
            return;
        }
        this.dialog_enviando = new ProgressDialog(this);
        this.dialog_enviando.setMessage(getString(C0627R.string.recuperando));
        this.dialog_enviando.setIndeterminate(true);
        if (VERSION.SDK_INT > 20) {
            this.dialog_enviando.setOnShowListener(new C07051());
        }
        this.dialog_enviando.show();
        new cargarperfilTask().execute(new String[0]);
    }

    void mostrar_error(int idmsg) {
        if (idmsg == 3) {
            idmsg = C0627R.string.perfil_desactivado;
        } else {
            idmsg = C0627R.string.error_http;
        }
        Builder builder = new Builder(this);
        builder.setCancelable(false).setPositiveButton(getString(C0627R.string.aceptar), null).setMessage(idmsg);
        try {
            final AlertDialog d_aux = builder.create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + preperfil.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e) {
            }
        } catch (Exception e2) {
        }
    }

    private void bajar_foto(int n, String vfoto) {
        URL myFileUrl = null;
        try {
            myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/usus/" + this.idusu + "_" + n + ".jpg?v=" + vfoto);
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
                bmImg.compress(CompressFormat.JPEG, 100, new FileOutputStream(this.globales.getTempFile(this, n)));
            } catch (Exception e2) {
            }
        } catch (IOException e3) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            finish();
        } else if (data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
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
            this.mDrawerList.setOnItemClickListener(new C07073());
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

    public void onStop() {
        super.onStop();
        if (this.finalizar) {
            finish();
        }
    }

    public void onDestroy() {
        if ((this.es_root && isFinishing()) || config.finalizar_app) {
            config.finalizar_app(this);
        }
        super.onDestroy();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((LinearLayout) findViewById(C0627R.id.ll_princ)).removeViewAt(0);
        incluir_menu_pre();
    }
}
