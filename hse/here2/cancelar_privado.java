package hse.here2;

import android.os.AsyncTask;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class cancelar_privado extends AsyncTask<String, Void, String> {
    private String codigo;
    private int idusu;
    private String idusu_conversante;

    public cancelar_privado(String idusu_conversante, int idusu, String codigo) {
        this.idusu_conversante = idusu_conversante;
        this.idusu = idusu;
        this.codigo = codigo;
    }

    protected String doInBackground(String... urls) {
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
            HttpConnectionParams.setSoTimeout(httpParameters, 20000);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/cancelar_privado.php");
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("idusu", new StringBody(this.idusu + ""));
            reqEntity.addPart("codigo", new StringBody(this.codigo));
            reqEntity.addPart("idusu_conversante", new StringBody(this.idusu_conversante));
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
        }
    }
}
