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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class s_cargar_sliderheader extends IntentService {
    Bitmap bm;
    config globales;
    Handler mHandler = new Handler();

    public s_cargar_sliderheader() {
        super("s_cargar_sliderheader");
    }

    protected void onHandleIntent(Intent intent) {
        this.globales = (config) getApplicationContext();
        boolean ok = true;
        URL myFileUrl = null;
        try {
            myFileUrl = new URL("http://imgs1.e-droid.net/srv/imgs/gen/299914_slider.png?v=" + this.globales.slider_v);
        } catch (MalformedURLException e) {
            ok = false;
        }
        if (ok) {
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                conn.connect();
                this.bm = BitmapFactory.decodeStream(conn.getInputStream());
                try {
                    FileOutputStream fos = openFileOutput("slider_header", 0);
                    this.bm.compress(CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e2) {
                    ok = false;
                }
            } catch (IOException e3) {
                ok = false;
            }
            if (this.globales != null && this.globales.c1 != null && ok) {
                Editor editor = getSharedPreferences("sh", 0).edit();
                editor.putInt("slider_v_act", this.globales.slider_v);
                editor.commit();
            }
        }
    }
}
