package hse.here2;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.widget.Toast;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URLEncoder;
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

public class s_guardarperfil extends IntentService {
    Bitmap bm;
    String codigo;
    Bundle extras;
    config globales;
    int idusu;
    Handler mHandler = new Handler();
    SharedPreferences settings;
    String[] usu;

    private class DisplayToast implements Runnable {
        String mText;
        Integer mmodo;

        public DisplayToast(Integer idstring, Integer modo) {
            this.mText = s_guardarperfil.this.getString(idstring.intValue());
            this.mmodo = modo;
        }

        public void run() {
            config.mostrar_toast(Toast.makeText(s_guardarperfil.this.getApplicationContext(), this.mText, this.mmodo.intValue()));
        }
    }

    private class guardar extends AsyncTask<String, Void, Byte> {
        private guardar() {
        }

        protected Byte doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 60000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://srv1.androidcreator.com/srv/guardar_perfil.php?idusu=" + s_guardarperfil.this.idusu + "&idapp=" + 299914 + "&c=" + s_guardarperfil.this.settings.getString("cod", ""));
                MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                if (s_guardarperfil.this.extras.getString("foto" + 1 + "_modif").equals("1")) {
                    if (s_guardarperfil.this.globales.getTempFile(s_guardarperfil.this, 1).exists()) {
                        int width;
                        int height;
                        Bitmap bm;
                        File file = s_guardarperfil.this.globales.getTempFile(s_guardarperfil.this, 1);
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
                            bm = Media.getBitmap(s_guardarperfil.this.getContentResolver(), Uri.fromFile(file));
                        } else {
                            config hse_here2_config = s_guardarperfil.this.globales;
                            int sampleSize = Math.round(((float) width) / ((float) config.calculateNewWidth(width, height, 300, HttpStatus.SC_BAD_REQUEST)));
                            Options resample = new Options();
                            resample.inSampleSize = sampleSize;
                            bm = BitmapFactory.decodeFile(file.getPath(), resample);
                        }
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bm.compress(CompressFormat.JPEG, 75, bos);
                        multipartEntity.addPart("foto" + 1, new ByteArrayBody(bos.toByteArray(), "temporal.jpg"));
                    } else {
                        multipartEntity.addPart("foto" + 1 + "_elim", new StringBody("1"));
                    }
                }
                multipartEntity.addPart("nombre", new StringBody(URLEncoder.encode(s_guardarperfil.this.settings.getString("nick", ""), "UTF-8")));
                multipartEntity.addPart("descr", new StringBody(URLEncoder.encode(s_guardarperfil.this.settings.getString("descr", ""), "UTF-8")));
                multipartEntity.addPart("privados", new StringBody(s_guardarperfil.this.settings.getInt("privados", 1) + ""));
                multipartEntity.addPart("foto1_modif", new StringBody(s_guardarperfil.this.extras.getString("foto1_modif")));
                multipartEntity.addPart("fnac_d", new StringBody(s_guardarperfil.this.settings.getInt("fnac_d", 0) + ""));
                multipartEntity.addPart("fnac_m", new StringBody(s_guardarperfil.this.settings.getInt("fnac_m", 0) + ""));
                multipartEntity.addPart("fnac_a", new StringBody(s_guardarperfil.this.settings.getInt("fnac_a", 0) + ""));
                multipartEntity.addPart("sexo", new StringBody(s_guardarperfil.this.settings.getInt("sexo", 0) + ""));
                multipartEntity.addPart("coments", new StringBody(s_guardarperfil.this.settings.getInt("coments", 1) + ""));
                httpPost.setEntity(multipartEntity);
                httpPost.setHeader("User-Agent", "Android Vinebre Software");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpClient.execute(httpPost).getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();
                while (true) {
                    String sResponse = bufferedReader.readLine();
                    if (sResponse == null) {
                        break;
                    }
                    s = s.append(sResponse);
                }
                if (s.indexOf("ANDROID:OK") != -1) {
                    return Byte.valueOf((byte) 1);
                }
                return Byte.valueOf((byte) 0);
            } catch (Exception e) {
                return Byte.valueOf((byte) 0);
            }
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 1) {
                s_guardarperfil.this.mHandler.post(new DisplayToast(Integer.valueOf(C0627R.string.guardado), Integer.valueOf(0)));
            } else {
                s_guardarperfil.this.mHandler.post(new DisplayToast(Integer.valueOf(C0627R.string.error_http), Integer.valueOf(1)));
            }
        }
    }

    public s_guardarperfil() {
        super("s_guardarperfil");
    }

    protected void onHandleIntent(Intent intent) {
        this.globales = (config) getApplicationContext();
        this.mHandler.post(new DisplayToast(Integer.valueOf(C0627R.string.guardando), Integer.valueOf(0)));
        this.extras = intent.getExtras();
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        try {
            new guardar().execute(new String[0]);
        } catch (Exception e) {
        }
    }
}
