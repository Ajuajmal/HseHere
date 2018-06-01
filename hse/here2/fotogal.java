package hse.here2;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class fotogal extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    String cbtn;
    String codigo;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    String fcrea;
    boolean finalizar = false;
    config globales;
    String idf;
    int idusu;
    String idusu_profile;
    int indf;
    String liked;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    WebView myWebView;
    String nlikes;
    boolean primer_load = true;
    SharedPreferences settings;
    View v_abrir_secc;

    class C06711 extends WebChromeClient {
        C06711() {
        }
    }

    class C06722 implements OnTouchListener {
        C06722() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                case 1:
                    if (!v.hasFocus()) {
                        v.requestFocus();
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    class C06733 extends WebViewClient {
        C06733() {
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!fotogal.this.finalizar && !fotogal.this.isFinishing() && !fotogal.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtube.com") && !fotogal.this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).contains("youtu.be")) {
                boolean aux1 = false;
                if (fotogal.this.primer_load) {
                    if (fotogal.this.extras == null || !fotogal.this.extras.containsKey("ad_entrar")) {
                        aux1 = false;
                    } else {
                        aux1 = true;
                    }
                    fotogal.this.primer_load = false;
                }
                fotogal.this.globales.toca_int(fotogal.this, aux1);
            }
        }
    }

    class C06744 implements DialogInterface.OnClickListener {
        C06744() {
        }

        public void onClick(DialogInterface dialog, int id) {
            fotogal.this.findViewById(C0627R.id.iv_reportar).setVisibility(8);
            config.mostrar_toast(Toast.makeText(fotogal.this, fotogal.this.getResources().getString(C0627R.string.enviando), 0));
            new enviar_reporte().execute(new String[0]);
        }
    }

    class C06766 implements DialogInterface.OnClickListener {
        C06766() {
        }

        public void onClick(DialogInterface dialog, int id) {
            new elim_foto(fotogal.this.idf).execute(new String[0]);
            Intent i = new Intent();
            i.putExtra("elim_foto", fotogal.this.idf);
            i.putExtra("indf", fotogal.this.indf);
            fotogal.this.setResult(-1, i);
            fotogal.this.finish();
        }
    }

    class C06788 implements OnItemClickListener {
        C06788() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (fotogal.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(fotogal.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(fotogal.this.globales.menu_a_secciones[position]));
            fotogal.this.onClick(view);
        }
    }

    private class elim_foto extends AsyncTask<String, Void, Byte> {
        String idfoto;

        elim_foto(String idfoto) {
            this.idfoto = idfoto;
        }

        protected Byte doInBackground(String... urls) {
            Byte valueOf;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL("http://srv1.androidcreator.com/srv/eliminar_fotogal.php?idusu=" + fotogal.this.idusu + "&c=" + fotogal.this.codigo + "&idf=" + this.idfoto).openConnection();
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

    private class enviar_like extends AsyncTask<String, Void, String> {
        private enviar_like() {
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/like_fotogal.php?idusu=" + fotogal.this.idusu + "&c=" + fotogal.this.codigo + "&idusu_pro=" + fotogal.this.idusu_profile + "&idf=" + fotogal.this.idf + "&modo=" + fotogal.this.liked);
                postRequest.setEntity(new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE));
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                httpClient.execute(postRequest);
                return "1";
            } catch (Exception e) {
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
        }
    }

    private class enviar_reporte extends AsyncTask<String, Void, String> {
        private enviar_reporte() {
        }

        protected String doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 20000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/reportar_fotogal.php?idusu=" + fotogal.this.idusu + "&c=" + fotogal.this.codigo + "&idusu_pro=" + fotogal.this.idusu_profile + "&idf=" + fotogal.this.idf);
                postRequest.setEntity(new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE));
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                httpClient.execute(postRequest);
                return "1";
            } catch (Exception e) {
                return "ANDROID:KO";
            }
        }

        protected void onPostExecute(String result) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.globales = (config) getApplicationContext();
        this.cbtn = config.aplicar_color_dialog("FFFFFFFF", this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.fotogal);
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
        incluir_menu_pre();
        this.adView = this.globales.mostrar_banner(this, false);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.codigo = this.settings.getString("cod", "");
        this.nlikes = this.extras.getString("nlikes");
        this.fcrea = this.extras.getString("fcrea");
        this.idf = this.extras.getString("idf");
        this.indf = this.extras.getInt("indf");
        this.liked = this.extras.getString("liked");
        this.idusu_profile = this.extras.getString("idusu_profile");
        String cad_aux = "";
        if (this.liked.equals("1")) {
            cad_aux = " - " + getResources().getString(C0627R.string.tegusta);
        }
        ((TextView) findViewById(C0627R.id.tv_likes)).setText(this.nlikes + cad_aux);
        findViewById(C0627R.id.iv_cerrar).setOnClickListener(this);
        findViewById(C0627R.id.ll_likes).setOnClickListener(this);
        if (this.idusu_profile.equals(this.idusu + "")) {
            findViewById(C0627R.id.iv_reportar).setVisibility(8);
            findViewById(C0627R.id.iv_elim).setOnClickListener(this);
            findViewById(C0627R.id.iv_elim).setVisibility(0);
        } else {
            findViewById(C0627R.id.iv_reportar).setOnClickListener(this);
        }
        this.myWebView = (WebView) findViewById(C0627R.id.webview);
        if (this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).startsWith("file://")) {
            registerForContextMenu(this.myWebView);
            String bg1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
            String bg2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
            if (this.extras.containsKey("bg1")) {
                bg1 = this.extras.getString("bg1");
                bg2 = this.extras.getString("bg2");
            }
            if (!bg1.equals("")) {
                findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + bg1), Color.parseColor("#" + bg2)}));
                this.myWebView.setBackgroundColor(0);
            }
        }
        this.myWebView.setWebChromeClient(new C06711());
        this.myWebView.getSettings().setBuiltInZoomControls(true);
        this.myWebView.getSettings().setSupportZoom(true);
        this.myWebView.setOnTouchListener(new C06722());
        this.myWebView.getSettings().setUseWideViewPort(true);
        this.myWebView.getSettings().setLoadWithOverviewMode(true);
        this.myWebView.setWebViewClient(new C06733());
        if (savedInstanceState == null) {
            this.myWebView.loadUrl(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL));
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("image/jpeg");
        ContentValues values = new ContentValues();
        values.put("mime_type", "image/jpeg");
        Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
        try {
            OutputStream outstream = getContentResolver().openOutputStream(uri);
            Options options = new Options();
            options.inPreferredConfig = Config.ARGB_8888;
            BitmapFactory.decodeFile(this.extras.getString(PlusShare.KEY_CALL_TO_ACTION_URL).replace("file://", ""), options).compress(CompressFormat.JPEG, 100, outstream);
            outstream.close();
            share.putExtra("android.intent.extra.STREAM", uri);
            startActivity(Intent.createChooser(share, getResources().getString(C0627R.string.compartir)));
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.myWebView.canGoBack()) {
            this.myWebView.goBack();
            return true;
        }
        fcerrar();
        return true;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void fcerrar() {
        Intent data = new Intent();
        data.putExtra("nlikes", this.nlikes);
        data.putExtra("liked", this.liked);
        data.putExtra("idf", this.idf);
        setResult(-1, data);
        finish();
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.iv_cerrar) {
            fcerrar();
        } else if (v.getId() == C0627R.id.ll_likes) {
            if (this.liked.equals("0")) {
                this.nlikes = (Integer.parseInt(this.nlikes) + 1) + "";
                this.liked = "1";
                if (!(this.settings.contains("usufav_" + this.idusu_profile) || this.settings.contains("usufav_noactivar_" + this.idusu_profile))) {
                    Editor e = this.settings.edit();
                    e.putBoolean("usufav_" + this.idusu_profile, true);
                    e.commit();
                }
            } else {
                this.nlikes = (Integer.parseInt(this.nlikes) - 1) + "";
                this.liked = "0";
            }
            String cad_aux = "";
            if (this.liked.equals("1")) {
                cad_aux = " - " + getResources().getString(C0627R.string.tegusta);
            }
            ((TextView) findViewById(C0627R.id.tv_likes)).setText(this.nlikes + cad_aux);
            new enviar_like().execute(new String[0]);
        } else if (v.getId() == C0627R.id.iv_reportar) {
            d_aux = new Builder(this).setNegativeButton(C0627R.string.cancelar, null).setPositiveButton(C0627R.string.aceptar, new C06744()).setMessage(C0627R.string.confirmar_reportarfoto).create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + fotogal.this.cbtn));
                        d_aux.getButton(-2).setTextColor(Color.parseColor("#" + fotogal.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e2) {
            }
        } else if (v.getId() == C0627R.id.iv_elim) {
            d_aux = new Builder(this).setNegativeButton(C0627R.string.no, null).setPositiveButton(C0627R.string.si, new C06766()).setMessage(C0627R.string.confirmar_elimfoto).create();
            if (!this.cbtn.equals("")) {
                d_aux.setOnShowListener(new OnShowListener() {
                    public void onShow(DialogInterface arg0) {
                        d_aux.getButton(-1).setTextColor(Color.parseColor("#" + fotogal.this.cbtn));
                        d_aux.getButton(-2).setTextColor(Color.parseColor("#" + fotogal.this.cbtn));
                    }
                });
            }
            d_aux.show();
            try {
                ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
            } catch (Exception e3) {
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
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
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
            this.mDrawerList.setOnItemClickListener(new C06788());
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

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myWebView.saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.myWebView.restoreState(savedInstanceState);
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
        if (!(this.globales.admob_pos == 0 || this.adView == null)) {
            this.adView.resume();
        }
        this.myWebView.onResume();
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
        this.adView = this.globales.mostrar_banner(this, false);
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
