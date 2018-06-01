package hse.here2;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Handler;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import hse.here2.config.MenuOpcion;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class s_cargar_icos extends IntentService {
    Bitmap bm;
    config globales;
    Handler mHandler = new Handler();

    public s_cargar_icos() {
        super("s_cargar_icos");
    }

    private void pasar_ico(int i, Editor e) {
        if (this.globales != null && this.globales.c1 != null && this.globales.secciones_a[i] != null) {
            this.globales.secciones_a[i].ico_cargando = false;
            if (this.globales.tipomenu == 1) {
                ((MenuOpcion) this.globales.opcions.get(this.globales.secciones_a[i].ind_menu)).img = BitmapFactory.decodeResource(getResources(), C0627R.drawable.pixel500por1);
                ((MenuOpcion) this.globales.opcions.get(this.globales.secciones_a[i].ind_menu)).img_cargando = false;
                e.putLong("ico_cargado", System.currentTimeMillis());
                e.commit();
            }
        }
    }

    protected void onHandleIntent(Intent intent) {
        this.globales = (config) getApplicationContext();
        Editor editor = getSharedPreferences("sh", 0).edit();
        int ind_submenu = intent.getIntExtra("ind_submenu", -1);
        String seccs = "";
        if (ind_submenu > -1) {
            seccs = "," + this.globales.secciones_a[ind_submenu].seccs + ",";
        }
        while (this.globales != null && this.globales.secciones_a != null) {
            int i = 0;
            while (i < this.globales.secciones_a.length && (this.globales == null || this.globales.secciones_a[i] == null || !this.globales.secciones_a[i].ico_cargando || ((ind_submenu != -1 || this.globales.secciones_a[i].oculta) && (ind_submenu <= -1 || !seccs.contains("," + this.globales.secciones_a[i].id + ","))))) {
                i++;
            }
            if (this.globales != null && this.globales.secciones_a != null) {
                if (i < this.globales.secciones_a.length) {
                    String url_aux;
                    if (this.globales.secciones_a[i].ico_id == 0) {
                        url_aux = "http://imgs1.e-droid.net/srv/imgs/seccs/" + this.globales.secciones_a[i].id + "_ico.png?v=" + this.globales.secciones_a[i].v_ico;
                    } else {
                        url_aux = "http://imgs1.e-droid.net/android-app-creator/icos_secc/" + this.globales.secciones_a[i].ico_id + ".png";
                    }
                    try {
                        try {
                            HttpURLConnection conn = (HttpURLConnection) new URL(url_aux).openConnection();
                            conn.setDoInput(true);
                            conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                            conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                            conn.connect();
                            this.bm = BitmapFactory.decodeStream(conn.getInputStream());
                            try {
                                FileOutputStream fos = openFileOutput("img_s" + this.globales.secciones_a[i].id + "_ico", 0);
                                this.bm.compress(CompressFormat.PNG, 100, fos);
                                fos.close();
                                if (this.globales != null && this.globales.c1 != null) {
                                    this.globales.secciones_a[i].ico = this.bm;
                                    this.globales.secciones_a[i].ico_cargando = false;
                                    editor.putInt("s" + this.globales.secciones_a[i].id + "_ico", 0);
                                    editor.putLong("ico_cargado", System.currentTimeMillis());
                                    if (this.globales.tipomenu == 1 && !this.globales.secciones_a[i].oculta) {
                                        ((MenuOpcion) this.globales.opcions.get(this.globales.secciones_a[i].ind_menu)).img = this.bm;
                                        ((MenuOpcion) this.globales.opcions.get(this.globales.secciones_a[i].ind_menu)).img_cargando = false;
                                    }
                                    editor.commit();
                                } else {
                                    return;
                                }
                            } catch (Exception e) {
                                pasar_ico(i, editor);
                            }
                        } catch (IOException e2) {
                            pasar_ico(i, editor);
                        }
                    } catch (MalformedURLException e3) {
                        pasar_ico(i, editor);
                    }
                } else if (ind_submenu == -1) {
                    this.globales.icos_pendientes = false;
                    return;
                } else {
                    return;
                }
            }
            return;
        }
    }
}
