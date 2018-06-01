package hse.here2;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.plus.PlusShare;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class t_detalle_fr extends Fragment implements OnClickListener {
    cargardetalle cd;
    cargarfotogrande cfg;
    private String[] cols = new String[]{"_id", "ref", "titulo", "descr", "idcat", "precio", "precio_descr", "url_compra", "img1_p", "img1_id", "img1_url", "det_cargado", "anyo", "mes", "dia"};
    cargarthumbs ct;
    String fotoscargadas = "0";
    config globales;
    private long idprod_global;
    int idusu;
    boolean limpiar_hist = true;
    boolean linksexternos = false;
    private FrameLayout ll = null;
    private boolean pb_inverse;
    boolean primera = true;
    private ProgressBar progess_2;
    String ref;
    private RelativeLayout rl_web;
    float scale;
    SharedPreferences settings;
    String titulo;
    String url_compra;
    boolean url_compra_espp = false;
    String url_global;
    private View f64v = null;
    private WebView viewer = null;
    int w_global;

    class C07891 extends WebChromeClient {
        C07891() {
        }

        public boolean onJsBeforeUnload(WebView v, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }

        public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    class C07902 implements OnTouchListener {
        C07902() {
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

    class C07913 implements DownloadListener {
        C07913() {
        }

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            t_detalle_fr.this.startActivity(i);
        }
    }

    class C07935 extends ClickableSpan {
        String url = t_detalle_fr.this.url_global;

        C07935() {
        }

        public void onClick(View textView) {
            t_detalle_fr.this.tratar_url(this.url, false);
        }
    }

    private class cargardetalle extends AsyncTask<String, Void, Byte> {
        long idprod;
        String result_http;

        protected java.lang.Byte doInBackground(java.lang.String... r15) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0081 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r14 = this;
            r2 = 0;
            r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10.<init>();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = "http://srv1.androidcreator.com/srv/prod_detalle.php?v=1&idapp=299914&idusu=";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r11);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = hse.here2.t_detalle_fr.this;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = r11.idusu;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r11);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = "&idprod=";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r11);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r12 = r14.idprod;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r12);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r3 = r10.toString();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r7 = new java.net.URL;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r7.<init>(r3);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r7.openConnection();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r0 = r10;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r2 = r0;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = 1;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r2.setDoInput(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r2.setConnectTimeout(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r2.setReadTimeout(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = "User-Agent";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = "Android Vinebre Software";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r2.setRequestProperty(r10, r11);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r5 = r2.getInputStream();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r8 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10.<init>(r5);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r8.<init>(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r9.<init>();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
        L_0x0059:
            r6 = r8.readLine();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            if (r6 == 0) goto L_0x0082;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
        L_0x005f:
            r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10.<init>();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r6);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = "\n";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.append(r11);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.toString();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r9.append(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            goto L_0x0059;
        L_0x0076:
            r4 = move-exception;
            r10 = -1;
            r10 = java.lang.Byte.valueOf(r10);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            if (r2 == 0) goto L_0x0081;
        L_0x007e:
            r2.disconnect();
        L_0x0081:
            return r10;
        L_0x0082:
            r10 = r9.toString();	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r14.result_http = r10;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r14.result_http;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r11 = "@EURO@";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r12 = "€";	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r10 = r10.replace(r11, r12);	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            r14.result_http = r10;	 Catch:{ Exception -> 0x0076, all -> 0x009f }
            if (r2 == 0) goto L_0x0099;
        L_0x0096:
            r2.disconnect();
        L_0x0099:
            r10 = 0;
            r10 = java.lang.Byte.valueOf(r10);
            goto L_0x0081;
        L_0x009f:
            r10 = move-exception;
            if (r2 == 0) goto L_0x00a5;
        L_0x00a2:
            r2.disconnect();
        L_0x00a5:
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.t_detalle_fr.cargardetalle.doInBackground(java.lang.String[]):java.lang.Byte");
        }

        private cargardetalle() {
        }

        protected void onPreExecute() {
            t_detalle_fr.this.ll.findViewById(C0627R.id.sv_det).setVisibility(8);
            t_detalle_fr.this.progess_2.setVisibility(0);
            this.idprod = t_detalle_fr.this.idprod_global;
        }

        protected void onPostExecute(Byte result) {
            if (t_detalle_fr.this.getActivity() != null) {
                if (result.byteValue() == (byte) 0 && !this.result_http.contains("-KO-")) {
                    String[] partes = this.result_http.split(",");
                    ContentValues itemData = new ContentValues();
                    if (!partes[0].trim().equals("0")) {
                        itemData.put("ref", partes[0].trim().replace("@x@", ","));
                    }
                    if (!partes[1].trim().equals("0")) {
                        itemData.put("descr", partes[1].trim().replace("@x@", ","));
                    }
                    itemData.put("idcat", partes[2].trim());
                    if (!partes[3].trim().equals("0")) {
                        itemData.put("precio_descr", partes[3].trim().replace("@x@", ","));
                    }
                    if (!partes[4].trim().equals("0")) {
                        itemData.put("url_compra", partes[4].trim().replace("@x@", ","));
                    }
                    itemData.put("det_cargado", Integer.valueOf(1));
                    t_detalle_fr.this.getActivity().getContentResolver().update(bd_provider.PRODUCTOS_URI, itemData, "_id=" + this.idprod, null);
                    if (!partes[5].equals("0")) {
                        String[] fotos_a = partes[5].split("@z@");
                        for (int i = 0; i < fotos_a.length; i++) {
                            String[] partes_f = fotos_a[i].split(";");
                            itemData.clear();
                            itemData.put("_id", partes_f[0]);
                            itemData.put("idprod", Long.valueOf(this.idprod));
                            String url_aux = partes_f[1].replace("@y@", ";").replace("@x@", ",");
                            if (url_aux.equals("0")) {
                                url_aux = "";
                            }
                            itemData.put(PlusShare.KEY_CALL_TO_ACTION_URL, url_aux);
                            itemData.put("orden", Integer.valueOf(i));
                            t_detalle_fr.this.getActivity().getContentResolver().insert(bd_provider.FOTOS_URI, itemData);
                        }
                    }
                    if (this.idprod == t_detalle_fr.this.idprod_global) {
                        SQLiteDatabase db = new bd(t_detalle_fr.this.getActivity()).getReadableDatabase();
                        if (db != null) {
                            Cursor c = db.query("productos", t_detalle_fr.this.cols, "_id=" + this.idprod, null, null, null, "_id", "1");
                            if (c.moveToFirst()) {
                                t_detalle_fr.this.mostrar_prod(c);
                            }
                            c.close();
                        }
                        db.close();
                    }
                }
                t_detalle_fr.this.progess_2.setVisibility(8);
                t_detalle_fr.this.ll.findViewById(C0627R.id.sv_det).setVisibility(0);
            }
        }
    }

    private class cargarfotogrande extends AsyncTask<String, Void, Byte> {
        byte[] bitmapdata;
        boolean guardar_foto = false;
        String id_fotoacargar;
        public ProgressBar pb_foto;
        int tipo;

        public cargarfotogrande(ImageView v_foto) {
            this.id_fotoacargar = ((Integer) v_foto.getTag(C0627R.id.TAG_IDFOTO)) + "";
            this.pb_foto = (ProgressBar) ((RelativeLayout) v_foto.getParent()).getChildAt(1);
        }

        protected void onPreExecute() {
            this.bitmapdata = null;
            SQLiteDatabase db = new bd(t_detalle_fr.this.getActivity()).getReadableDatabase();
            if (db != null) {
                Cursor c = db.rawQuery("SELECT 1 AS tipo,img1_url AS url,img1 AS img,img1_p AS img_p FROM productos WHERE img1_id=" + this.id_fotoacargar + " UNION" + " SELECT 2 AS tipo,url,img,img_p" + " FROM fotos" + " WHERE _id=" + this.id_fotoacargar, null);
                if (c.moveToFirst()) {
                    if (!c.isNull(c.getColumnIndex("img"))) {
                        this.bitmapdata = c.getBlob(c.getColumnIndex("img"));
                    } else if (c.getString(c.getColumnIndex(PlusShare.KEY_CALL_TO_ACTION_URL)).equals("")) {
                        this.tipo = c.getInt(c.getColumnIndex("tipo"));
                    } else {
                        this.bitmapdata = c.getBlob(c.getColumnIndex("img_p"));
                    }
                }
                c.close();
            }
            db.close();
            if (this.bitmapdata != null) {
                File file = t_detalle_fr.this.globales.getTempFile_libre(t_detalle_fr.this.getActivity(), "temp.jpg");
                boolean ok = true;
                try {
                    FileOutputStream fos = new FileOutputStream(file.getPath());
                    fos.write(this.bitmapdata);
                    fos.close();
                } catch (Exception e) {
                    ok = false;
                    e.printStackTrace();
                }
                if (ok && file != null && file.exists()) {
                    Intent i = new Intent(t_detalle_fr.this.getActivity(), t_url.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + file);
                    i.putExtra("bg1", t_detalle_fr.this.globales.c_prods_det);
                    i.putExtra("bg2", t_detalle_fr.this.globales.c_prods_det);
                    t_detalle_fr.this.startActivityForResult(i, 0);
                }
                cancel(true);
                return;
            }
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.addRule(13);
            this.pb_foto.setLayoutParams(lp);
            this.pb_foto.setVisibility(0);
        }

        protected Byte doInBackground(String... urls) {
            try {
                URL myFileUrl = new URL("http://srv1.androidcreator.com/srv/imgs/prods/" + this.id_fotoacargar + "_" + t_detalle_fr.this.idprod_global + ".png");
                URL url;
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bm_foto = BitmapFactory.decodeStream(is);
                    is.close();
                    if (bm_foto == null) {
                        url = myFileUrl;
                        return Byte.valueOf((byte) -1);
                    }
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm_foto.compress(CompressFormat.JPEG, 100, bos);
                    this.bitmapdata = bos.toByteArray();
                    try {
                        bos.close();
                    } catch (IOException e) {
                    }
                    this.guardar_foto = true;
                    url = myFileUrl;
                    return Byte.valueOf((byte) 0);
                } catch (IOException e2) {
                    url = myFileUrl;
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e3) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPostExecute(Byte result) {
            if (t_detalle_fr.this.getActivity() != null && result.byteValue() == (byte) 0) {
                if (this.guardar_foto) {
                    ContentValues itemData = new ContentValues();
                    if (this.tipo == 1) {
                        itemData.put("img1", this.bitmapdata);
                        t_detalle_fr.this.getActivity().getContentResolver().update(bd_provider.PRODUCTOS_URI, itemData, "img1_id='" + this.id_fotoacargar + "'", null);
                    } else if (this.tipo == 2) {
                        itemData.put("img", this.bitmapdata);
                        t_detalle_fr.this.getActivity().getContentResolver().update(bd_provider.FOTOS_URI, itemData, "_id='" + this.id_fotoacargar + "'", null);
                    }
                }
                this.pb_foto.setVisibility(8);
                File file = t_detalle_fr.this.globales.getTempFile_libre(t_detalle_fr.this.getActivity(), "temp.jpg");
                boolean ok = true;
                try {
                    FileOutputStream fos = new FileOutputStream(file.getPath());
                    fos.write(this.bitmapdata);
                    fos.close();
                } catch (Exception e) {
                    ok = false;
                    e.printStackTrace();
                }
                if (ok && file != null && file.exists()) {
                    Intent i = new Intent(t_detalle_fr.this.getActivity(), t_url.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + file);
                    i.putExtra("bg1", t_detalle_fr.this.globales.c_prods_det);
                    i.putExtra("bg2", t_detalle_fr.this.globales.c_prods_det);
                    t_detalle_fr.this.startActivityForResult(i, 0);
                }
            }
        }
    }

    private class cargarthumbs extends AsyncTask<String, Void, Byte> {
        byte[] bitmapdata;
        String id_fotoacargar;
        long idprod;
        int nintento;
        int tipo;
        String url_foto;

        public cargarthumbs(int nintento) {
            this.nintento = nintento;
        }

        protected void onPreExecute() {
            this.idprod = t_detalle_fr.this.idprod_global;
        }

        protected Byte doInBackground(String... urls) {
            if (t_detalle_fr.this.getActivity() == null) {
                return Byte.valueOf((byte) -1);
            }
            this.id_fotoacargar = "0";
            String url_aux = "";
            SQLiteDatabase db = new bd(t_detalle_fr.this.getActivity()).getReadableDatabase();
            if (db != null) {
                Cursor c = db.rawQuery("SELECT 1 AS tipo,img1_id AS id,0 AS orden,img1_url AS url FROM productos WHERE (_id=" + t_detalle_fr.this.idprod_global + ") AND (img1_id NOT IN (" + t_detalle_fr.this.fotoscargadas + ")) AND (img1_p IS NULL)" + " UNION" + " SELECT 2 AS tipo,_id AS id,orden,url" + " FROM fotos" + " WHERE (idprod=" + t_detalle_fr.this.idprod_global + ") AND (_id NOT IN (" + t_detalle_fr.this.fotoscargadas + ")) AND (img_p IS NULL)" + " ORDER BY orden", null);
                if (c.moveToFirst()) {
                    this.id_fotoacargar = c.getString(c.getColumnIndex("id"));
                    this.tipo = c.getInt(c.getColumnIndex("tipo"));
                    url_aux = c.getString(c.getColumnIndex(PlusShare.KEY_CALL_TO_ACTION_URL));
                }
                c.close();
            }
            db.close();
            if (this.id_fotoacargar.equals("0")) {
                return Byte.valueOf((byte) 1);
            }
            this.url_foto = url_aux;
            if (url_aux.equals("")) {
                url_aux = "http://srv1.androidcreator.com/srv/imgs/prods/" + this.id_fotoacargar + "_" + t_detalle_fr.this.idprod_global + "_p.png";
                this.url_foto = "http://srv1.androidcreator.com/srv/imgs/prods/" + this.id_fotoacargar + "_" + t_detalle_fr.this.idprod_global + ".png";
            }
            try {
                URL myFileUrl = new URL(url_aux);
                try {
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Options options = new Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(is, null, options);
                    is.close();
                    conn.disconnect();
                    config hse_here2_config = t_detalle_fr.this.globales;
                    options.inSampleSize = config.calculateInSampleSize(options, 300, 300);
                    HttpURLConnection conn2 = (HttpURLConnection) myFileUrl.openConnection();
                    conn2.setDoInput(true);
                    conn2.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn2.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn2.connect();
                    InputStream is2 = conn2.getInputStream();
                    options.inJustDecodeBounds = false;
                    Bitmap bm_foto = BitmapFactory.decodeStream(is2, null, options);
                    is2.close();
                    if (bm_foto == null) {
                        return Byte.valueOf((byte) -1);
                    }
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm_foto.compress(CompressFormat.JPEG, 100, bos);
                    this.bitmapdata = bos.toByteArray();
                    try {
                        bos.close();
                    } catch (IOException e) {
                    }
                    return Byte.valueOf((byte) 0);
                } catch (IOException e2) {
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e3) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPostExecute(Byte result) {
            if (t_detalle_fr.this.getActivity() != null) {
                if (result.byteValue() == (byte) 0) {
                    ContentValues itemData = new ContentValues();
                    if (this.tipo == 1) {
                        itemData.put("img1_p", this.bitmapdata);
                        t_detalle_fr.this.getActivity().getContentResolver().update(bd_provider.PRODUCTOS_URI, itemData, "img1_id='" + this.id_fotoacargar + "'", null);
                    } else if (this.tipo == 2) {
                        itemData.put("img_p", this.bitmapdata);
                        t_detalle_fr.this.getActivity().getContentResolver().update(bd_provider.FOTOS_URI, itemData, "_id='" + this.id_fotoacargar + "'", null);
                    }
                    if (this.idprod == t_detalle_fr.this.idprod_global) {
                        StringBuilder stringBuilder = new StringBuilder();
                        t_detalle_fr hse_here2_t_detalle_fr = t_detalle_fr.this;
                        hse_here2_t_detalle_fr.fotoscargadas = stringBuilder.append(hse_here2_t_detalle_fr.fotoscargadas).append(",").append(this.id_fotoacargar).toString();
                        t_detalle_fr.this.mostrar_foto(this.bitmapdata, Integer.parseInt(this.id_fotoacargar), this.url_foto);
                    }
                }
                if (result.byteValue() != (byte) 1 && this.idprod == t_detalle_fr.this.idprod_global) {
                    if (result.byteValue() == (byte) -1) {
                        this.nintento++;
                    } else {
                        this.nintento = 0;
                    }
                    if (this.nintento < 3) {
                        t_detalle_fr.this.ct = new cargarthumbs(this.nintento);
                        t_detalle_fr.this.ct.execute(new String[0]);
                    }
                }
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.globales = (config) getActivity().getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.settings = getActivity().getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.pb_inverse = false;
        if (!this.globales.c_prods_det.equals("") && config.esClaro("#" + this.globales.c_prods_det)) {
            this.pb_inverse = true;
        }
        this.linksexternos = this.globales.prods_linksexternos;
        this.scale = getResources().getDisplayMetrics().density;
        this.w_global = (int) ((100.0f * this.scale) + 0.5f);
        this.f64v = inflater.inflate(C0627R.layout.detalle, container, false);
        this.rl_web = (RelativeLayout) this.f64v.findViewById(C0627R.id.rl_web);
        this.viewer = (WebView) this.f64v.findViewById(C0627R.id.ll_det_web);
        this.viewer.setWebChromeClient(new C07891());
        wv_set();
        this.viewer.getSettings().setBuiltInZoomControls(true);
        this.viewer.getSettings().setSupportZoom(true);
        this.viewer.getSettings().setDomStorageEnabled(true);
        this.viewer.setOnTouchListener(new C07902());
        this.viewer.setDownloadListener(new C07913());
        if (this.globales.prods_adaptar_ancho) {
            this.viewer.getSettings().setUseWideViewPort(true);
            this.viewer.getSettings().setLoadWithOverviewMode(true);
        }
        final ProgressBar progess = (ProgressBar) this.f64v.findViewById(C0627R.id.pb_url);
        if (this.pb_inverse) {
            this.progess_2 = (ProgressBar) this.f64v.findViewById(C0627R.id.pb_det_inverse);
        } else {
            this.progess_2 = (ProgressBar) this.f64v.findViewById(C0627R.id.pb_det);
        }
        if (VERSION.SDK_INT > 20) {
            config.progress_color(progess, this.globales.c_icos);
            config.progress_color(this.progess_2, this.globales.c_icos);
        }
        this.viewer.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progess.setVisibility(0);
            }

            public void onPageFinished(WebView view, String url) {
                progess.setVisibility(8);
                if (t_detalle_fr.this.limpiar_hist) {
                    view.clearHistory();
                    t_detalle_fr.this.limpiar_hist = false;
                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return t_detalle_fr.this.tratar_url(url, true);
            }
        });
        this.viewer.getSettings().setJavaScriptEnabled(true);
        this.viewer.getSettings().setPluginState(PluginState.ON);
        this.viewer.getSettings().setUserAgentString(this.viewer.getSettings().getUserAgentString() + " Vinebre");
        this.ll = (FrameLayout) this.f64v.findViewById(C0627R.id.ll_det_cont);
        return this.f64v;
    }

    private boolean tratar_url(String url, boolean desde_viewer) {
        Intent i;
        boolean tratado = false;
        String url_l = url.toLowerCase();
        String[] gdocs_ext = new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "pages", "ai", "psd", "tiff", "dxf", "svg", "eps", "ps", "ttf", "otf", "xps", "zip", "rar"};
        String url_ext = "";
        int pos_aux = url.lastIndexOf(".");
        if (pos_aux != -1) {
            url_ext = url_l.substring(pos_aux);
        }
        url_ext = url_ext.replace(".", "");
        String tel;
        if (url_l.startsWith("tel:") || url_l.startsWith("http://tel:")) {
            if (url_l.startsWith("tel:")) {
                tel = url.substring(4);
            } else {
                tel = url.substring(11);
            }
            if (tel.endsWith("/")) {
                tel = tel.substring(0, tel.length() - 1);
            }
            try {
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel)));
            } catch (Exception e) {
            }
            tratado = true;
        } else if (url_l.startsWith("mailto:") || url_l.startsWith("http://mailto:")) {
            String email;
            String subject = "";
            texte = "";
            if (url_l.startsWith("mailto:")) {
                email = url.substring(7).trim();
            } else {
                email = url.substring(14).trim();
            }
            if (!email.equals("")) {
                pos = email.indexOf("?");
                if (pos > 0) {
                    pos2 = email.indexOf("?subject=");
                    if (pos2 != -1) {
                        subject = email.substring(pos2 + 9).trim();
                        pos2 = subject.indexOf("&body=");
                        if (pos2 != -1) {
                            texte = subject.substring(pos2 + 6).trim();
                            subject = subject.substring(0, pos2).trim();
                        }
                    }
                    email = email.substring(0, pos).trim();
                }
                if (email.endsWith("/")) {
                    email = email.substring(0, email.length() - 1);
                }
                Intent emailIntent = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", email, null));
                if (!subject.equals("")) {
                    try {
                        subject = URLDecoder.decode(subject, "UTF-8");
                    } catch (Exception e2) {
                    }
                    emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
                }
                if (!texte.equals("")) {
                    try {
                        texte = URLDecoder.decode(texte, "UTF-8");
                    } catch (Exception e3) {
                    }
                    emailIntent.putExtra("android.intent.extra.TEXT", texte);
                }
                startActivity(Intent.createChooser(emailIntent, getResources().getString(C0627R.string.enviar_email)));
            }
            tratado = true;
        } else if (url_l.startsWith("smsto:") || url_l.startsWith("http://smsto:")) {
            texte = "";
            if (url_l.startsWith("smsto:")) {
                tel = url.substring(6).trim();
            } else {
                tel = url.substring(13).trim();
            }
            if (!tel.equals("")) {
                pos = tel.indexOf("?");
                if (pos > 0) {
                    pos2 = tel.indexOf("?body=");
                    if (pos2 != -1) {
                        texte = tel.substring(pos2 + 6).trim();
                    }
                    tel = tel.substring(0, pos).trim();
                }
                if (tel.endsWith("/")) {
                    tel = tel.substring(0, tel.length() - 1);
                }
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + tel));
                if (!texte.equals("")) {
                    try {
                        texte = URLDecoder.decode(texte, "UTF-8");
                    } catch (Exception e4) {
                    }
                    intent.putExtra("sms_body", texte);
                }
                try {
                    startActivity(intent);
                } catch (Exception e5) {
                }
            }
            tratado = true;
        } else if (url_l.startsWith("go:") || url_l.startsWith("http://go:")) {
            String idgo;
            if (url_l.startsWith("go:")) {
                idgo = url.substring(3);
            } else {
                idgo = url.substring(10);
            }
            if (idgo.endsWith("/")) {
                idgo = idgo.substring(0, idgo.length() - 1);
            }
            try {
                idgo = URLDecoder.decode(idgo, "UTF-8");
            } catch (Exception e6) {
            }
            for (int i2 = 0; i2 < this.globales.secciones_a.length; i2++) {
                if (this.globales.secciones_a[i2].idgo.equalsIgnoreCase(idgo)) {
                    startActivityForResult(this.globales.crear_rgi(Integer.valueOf(i2), this.viewer.getContext()).f34i, 0);
                    tratado = true;
                }
            }
            if (!tratado) {
                this.primera = false;
            }
        } else if (url_l.startsWith("vnd.youtube:")) {
            int n = url.indexOf("?");
            String idvideo = "";
            if (n > 0) {
                idvideo = url.substring(12, n);
            } else {
                idvideo = url.substring(12);
            }
            url_aux = "http://www.youtube.com/watch?v=" + idvideo;
            if (this.linksexternos) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url_aux)));
            } else {
                this.primera = false;
                i = new Intent(this.viewer.getContext(), t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url_aux);
                startActivityForResult(i, 0);
            }
            tratado = true;
        } else if (url_l.endsWith(".mp3")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), "audio/*");
            startActivity(intent);
            tratado = true;
        } else if (url_l.endsWith(".mp4") || url_l.endsWith(".3gp")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), "video/*");
            startActivity(intent);
            tratado = true;
        } else if (this.linksexternos || url_l.startsWith("rtsp://") || url_l.startsWith("rtmp://") || url_l.startsWith("market://") || url_l.endsWith(".apk") || url_l.startsWith("whatsapp://") || url_l.endsWith(".m3u") || url_l.endsWith(".m3u8")) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                tratado = true;
            } catch (Exception e7) {
            }
        } else if (!Arrays.asList(gdocs_ext).contains(url_ext) || url_l.contains("docs.google.com")) {
            this.primera = false;
        } else if (desde_viewer && this.viewer.getUrl().contains("docs.google.com")) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            tratado = true;
        } else {
            url_aux = "";
            try {
                url_aux = URLEncoder.encode(url, "utf-8");
            } catch (Exception e8) {
                url_aux = url;
            }
            url_aux = "http://docs.google.com/viewer?embedded=true&url=" + url_aux;
            if (desde_viewer) {
                this.viewer.loadUrl(url_aux);
            } else {
                i = new Intent(this.viewer.getContext(), t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url_aux);
                startActivityForResult(i, 0);
            }
            this.primera = false;
            tratado = true;
        }
        if (!(desde_viewer || tratado)) {
            i = new Intent(this.viewer.getContext(), t_url.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
            startActivityForResult(i, 0);
        }
        return tratado;
    }

    public void actContenido(String url, long idprod) {
        this.idprod_global = idprod;
        if (this.f64v != null) {
            this.primera = true;
            this.limpiar_hist = true;
            if (url.equals("")) {
                this.rl_web.setVisibility(8);
                SQLiteDatabase db = new bd(getActivity()).getReadableDatabase();
                if (db != null) {
                    Cursor c = db.query("productos", this.cols, "_id=" + idprod, null, null, null, "_id", "1");
                    if (!c.moveToFirst()) {
                        c.close();
                    } else if (c.getInt(c.getColumnIndex("det_cargado")) == 1) {
                        mostrar_prod(c);
                        c.close();
                    } else {
                        c.close();
                        if (!(this.cd == null || this.cd.getStatus() == Status.FINISHED)) {
                            this.cd.cancel(true);
                        }
                        this.cd = new cargardetalle();
                        this.cd.execute(new String[0]);
                    }
                }
                db.close();
                this.ll.setVisibility(0);
                return;
            }
            this.ll.setVisibility(8);
            this.viewer.loadUrl(url);
            this.rl_web.setVisibility(0);
        }
    }

    private void mostrar_prod(Cursor c) {
        this.ref = c.getString(c.getColumnIndex("ref"));
        TextView tv = (TextView) this.ll.findViewById(C0627R.id.tv_det_ref);
        tv.setVisibility(8);
        if (!this.ref.equals("")) {
            tv.setText(this.ref);
            if (!this.globales.c_txt_prods.equals("")) {
                tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_prods));
            }
            tv.setVisibility(0);
        }
        this.titulo = c.getString(c.getColumnIndex("titulo"));
        tv = (TextView) this.ll.findViewById(C0627R.id.tv_det_tit);
        tv.setText(this.titulo);
        if (!this.globales.c_tit_prods_l.equals("")) {
            tv.setTextColor(Color.parseColor("#" + this.globales.c_tit_prods_l));
        }
        tv = (TextView) this.ll.findViewById(C0627R.id.tv_det_antig);
        tv.setVisibility(8);
        int anyo = c.getInt(c.getColumnIndex("anyo"));
        if (anyo != 0) {
            String anyo_str = anyo + "";
            int mes = c.getInt(c.getColumnIndex("mes"));
            String mes_str = mes + "";
            if (mes_str.equals("0")) {
                mes_str = "1";
            }
            int dia = c.getInt(c.getColumnIndex("dia"));
            String dia_str = dia + "";
            if (dia_str.equals("0")) {
                dia_str = "1";
            }
            boolean ok = true;
            Date d = null;
            try {
                d = DateFormat.getDateInstance(3, Locale.US).parse(mes_str + "/" + dia_str + "/" + anyo_str);
            } catch (Exception e) {
                ok = false;
            }
            if (ok) {
                String d_str;
                if (mes == 0) {
                    d_str = anyo + "";
                } else if (dia == 0) {
                    d_str = String.format(Locale.getDefault(), "%tB", new Object[]{d});
                    d_str = (d_str.substring(0, 1).toUpperCase() + d_str.substring(1)) + ", " + anyo;
                } else {
                    d_str = DateFormat.getDateInstance().format(d);
                }
                tv.setText(d_str);
                if (!this.globales.c_antiguedad_prods_l.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_antiguedad_prods_l));
                }
                tv.setVisibility(0);
            }
        }
        tv = (TextView) this.ll.findViewById(C0627R.id.tv_det_descr);
        if (!this.globales.c_txt_prods.equals("")) {
            tv.setTextColor(Color.parseColor("#" + this.globales.c_txt_prods));
        }
        tratar_tv(tv, c.getString(c.getColumnIndex("descr")), true);
        String pp_precio = "";
        tv = (TextView) this.ll.findViewById(C0627R.id.tv_det_precio);
        tv.setVisibility(8);
        if (!c.isNull(c.getColumnIndex("precio"))) {
            boolean precio_ok = true;
            double precio = 0.0d;
            try {
                precio = c.getDouble(c.getColumnIndex("precio"));
            } catch (Exception e2) {
                precio_ok = false;
            }
            if (precio_ok) {
                if (precio > 0.0d) {
                    DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setDecimalSeparator('.');
                    DecimalFormat fmt2 = new DecimalFormat();
                    fmt2.setGroupingUsed(false);
                    fmt2.setMinimumFractionDigits(2);
                    fmt2.setMaximumFractionDigits(2);
                    fmt2.setDecimalFormatSymbols(dfs);
                    pp_precio = fmt2.format(precio);
                }
                DecimalFormat fmt = new DecimalFormat();
                fmt.setCurrency(Currency.getInstance(Locale.getDefault()));
                String cad = fmt.format(precio);
                if (cad.length() > 1) {
                    String aux1 = cad.substring(cad.length() - 2, cad.length() - 1);
                    if (aux1.equals(",") || aux1.equals(".")) {
                        cad = cad + "0";
                    }
                }
                if (this.globales.divisa.equals("")) {
                    cad = cad + ".";
                } else {
                    cad = cad + Html.fromHtml("&nbsp;" + this.globales.divisa);
                }
                tv.setText(Html.fromHtml((cad + " " + c.getString(c.getColumnIndex("precio_descr"))).trim()));
                if (!this.globales.c_precio_prods_l.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + this.globales.c_precio_prods_l));
                    getResources().getDrawable(C0627R.drawable.precio).setColorFilter(Color.parseColor("#" + this.globales.c_precio_prods_l), Mode.MULTIPLY);
                }
                tv.setVisibility(0);
            }
        }
        this.url_compra = "";
        if (this.globales.pp_email.equals("") || this.globales.pp_divisa.equals("")) {
            this.url_compra = c.getString(c.getColumnIndex("url_compra"));
        } else if (!pp_precio.equals("")) {
            this.url_compra_espp = true;
            String pp_email_enc = this.globales.pp_email;
            try {
                pp_email_enc = URLEncoder.encode(this.globales.pp_email, "utf-8");
            } catch (Exception e3) {
            }
            String titulo_enc = this.titulo;
            try {
                titulo_enc = URLEncoder.encode(this.titulo, "utf-8");
            } catch (Exception e4) {
            }
            String ref_enc = this.ref;
            try {
                ref_enc = URLEncoder.encode(this.ref, "utf-8");
            } catch (Exception e5) {
            }
            this.url_compra = "https://www.paypal.com/cgi-bin/webscr/?rm=2&cmd=_xclick&business=" + pp_email_enc + "&item_name=" + titulo_enc.substring(0, Math.min(titulo_enc.length(), TransportMediator.KEYCODE_MEDIA_PAUSE)) + "&item_number=" + ref_enc.substring(0, Math.min(ref_enc.length(), TransportMediator.KEYCODE_MEDIA_PAUSE)) + "&amount=" + pp_precio + "&no_shipping=0&no_note=1&currency_code=" + this.globales.pp_divisa + "&bn=PP-BuyNowBF";
            if (!this.linksexternos && VERSION.SDK_INT >= 15) {
                String url_aux = "http://CLOSETHIS";
                try {
                    url_aux = URLEncoder.encode(url_aux, "utf-8");
                } catch (Exception e6) {
                }
                this.url_compra += "&return=" + url_aux + "&cancel_return=" + url_aux;
            }
        }
        TextView boton = (TextView) this.ll.findViewById(C0627R.id.btn_det_comprar);
        boton.setVisibility(8);
        if (!this.url_compra.equals("")) {
            if (!this.globales.c_icos_2_prods.equals("")) {
                boton.setBackgroundColor(Color.parseColor("#" + this.globales.c_icos_2_prods));
                if (config.esClaro("#" + this.globales.c_icos_2_prods)) {
                    boton.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
            }
            if (!this.globales.prods_comprar.equals("")) {
                boton.setText(this.globales.prods_comprar);
            }
            boton.setOnClickListener(this);
            boton.setVisibility(0);
        }
        boton = (TextView) this.ll.findViewById(C0627R.id.btn_det_masinfo);
        boton.setVisibility(8);
        if (this.globales.oficinas_a.length > 0 && this.globales.prods_masinfo_mostrar) {
            if (!this.globales.c_icos_2_prods.equals("")) {
                boton.setBackgroundColor(Color.parseColor("#" + this.globales.c_icos_2_prods));
                if (config.esClaro("#" + this.globales.c_icos_2_prods)) {
                    boton.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
            }
            if (!this.globales.prods_masinfo.equals("")) {
                boton.setText(this.globales.prods_masinfo);
            }
            boton.setOnClickListener(this);
            boton.setVisibility(0);
        }
        ((LinearLayout) this.ll.findViewById(C0627R.id.ll_det_imgs)).removeAllViews();
        SQLiteDatabase db = new bd(getActivity()).getReadableDatabase();
        SQLiteDatabase sQLiteDatabase = db;
        Cursor c2 = sQLiteDatabase.rawQuery("SELECT img1_id AS id FROM productos WHERE (_id=" + this.idprod_global + ") AND img1_id>0" + " UNION" + " SELECT _id AS id" + " FROM fotos" + " WHERE (idprod=" + this.idprod_global + ")", null);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-2, -2);
        LinearLayout ll_fotos = (LinearLayout) this.ll.findViewById(C0627R.id.ll_det_imgs);
        for (int i = 0; i < c2.getCount(); i++) {
            View imageSwitcher = new ImageSwitcher(getActivity());
            imageSwitcher.setLayoutParams(layoutParams);
            imageSwitcher.getLayoutParams().width = this.w_global;
            imageSwitcher.getLayoutParams().height = this.w_global;
            if (this.pb_inverse) {
                imageSwitcher = new ProgressBar(getActivity(), null, 16843399);
            } else {
                imageSwitcher = new ProgressBar(getActivity(), null, 16842871);
            }
            if (VERSION.SDK_INT > 20) {
                config.progress_color(pb_foto, this.globales.c_icos);
            }
            pb_foto.setLayoutParams(layoutParams);
            int padding_aux = (int) ((20.0f * this.scale) + 0.5f);
            pb_foto.setPadding(padding_aux, padding_aux, padding_aux, padding_aux);
            imageSwitcher.addView(pb_foto);
            ll_fotos.addView(imageSwitcher);
            ll_fotos.setVisibility(0);
        }
        if (!c.isNull(c.getColumnIndex("img1_p"))) {
            mostrar_foto(c.getBlob(c.getColumnIndex("img1_p")), c.getInt(c.getColumnIndex("img1_id")), "");
        }
        sQLiteDatabase = db;
        c2 = sQLiteDatabase.rawQuery("SELECT _id,img_p FROM fotos WHERE (idprod=" + this.idprod_global + ") AND (img_p IS NOT NULL)" + " ORDER BY orden", null);
        if (c2.moveToFirst()) {
            while (!c2.isAfterLast()) {
                mostrar_foto(c2.getBlob(c2.getColumnIndex("img_p")), c2.getInt(c2.getColumnIndex("_id")), "");
                c2.moveToNext();
            }
        }
        c2.close();
        db.close();
        if (!(this.ct == null || this.ct.getStatus() == Status.FINISHED)) {
            this.ct.cancel(true);
        }
        this.ct = new cargarthumbs(0);
        this.ct.execute(new String[0]);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!this.globales.c_prods_det.equals("")) {
            getView().setBackgroundColor(Color.parseColor("#" + this.globales.c_prods_det));
        }
        if (savedInstanceState != null) {
            this.viewer.restoreState(savedInstanceState);
        }
    }

    public void onClick(View v) {
        Intent i;
        if (v.getId() == C0627R.id.btn_det_comprar) {
            if (this.linksexternos) {
                i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(this.url_compra));
                startActivity(i);
                return;
            }
            i = new Intent(getActivity(), t_url.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, this.url_compra);
            if (this.url_compra_espp) {
                i.putExtra("linksexternos", false);
                i.putExtra("adaptar_ancho", false);
            } else {
                i.putExtra("linksexternos", this.globales.prods_linksexternos);
                i.putExtra("adaptar_ancho", this.globales.prods_adaptar_ancho);
            }
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.btn_det_masinfo) {
            i = this.globales.intent_ofics(getActivity()).f34i;
            String msg_predefinido = this.titulo;
            if (!this.ref.equals("")) {
                msg_predefinido = msg_predefinido + "(Ref." + this.ref + ")";
            }
            i.putExtra("msg_predefinido", msg_predefinido);
            startActivityForResult(i, 0);
        } else if (v.getTag(C0627R.id.TAG_IDFOTO) != null) {
            if (!(this.cfg == null || this.cfg.getStatus() == Status.FINISHED)) {
                try {
                    this.cfg.pb_foto.setVisibility(8);
                } catch (Exception e) {
                }
                this.cfg.cancel(true);
            }
            this.cfg = new cargarfotogrande((ImageView) v);
            this.cfg.execute(new String[0]);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            getActivity().setResult(-1, data);
            getActivity().finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.rl_web.getVisibility() != 0 || this.primera || keyCode != 4 || !this.viewer.canGoBack()) {
            return false;
        }
        this.viewer.goBack();
        return true;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.viewer.saveState(outState);
    }

    void mostrar_foto(byte[] bitmapdata, int idfoto, String url_foto) {
        ProgressBar pb_foto;
        RelativeLayout relativeLayout = new RelativeLayout(getActivity());
        ImageView iv = new ImageView(getActivity());
        Bitmap bmp = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        iv.setImageDrawable(new BitmapDrawable(getResources(), bmp));
        iv.setTag(C0627R.id.TAG_IDFOTO, Integer.valueOf(idfoto));
        iv.setOnClickListener(this);
        iv.setAdjustViewBounds(true);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.addRule(13);
        iv.setLayoutParams(lp);
        iv.getLayoutParams().width = this.w_global;
        int h_aux = (bmp.getHeight() * this.w_global) / bmp.getWidth();
        iv.getLayoutParams().height = h_aux;
        iv.setPadding(0, (int) ((4.0f * this.scale) + 0.5f), 0, (int) ((4.0f * this.scale) + 0.5f));
        relativeLayout.addView(iv);
        if (this.pb_inverse) {
            pb_foto = new ProgressBar(getActivity(), null, 16843399);
        } else {
            pb_foto = new ProgressBar(getActivity(), null, 16842871);
        }
        if (VERSION.SDK_INT > 20) {
            config.progress_color(pb_foto, this.globales.c_icos);
        }
        pb_foto.setVisibility(8);
        pb_foto.setLayoutParams(lp);
        relativeLayout.addView(pb_foto);
        LinearLayout ll_fotos = (LinearLayout) this.ll.findViewById(C0627R.id.ll_det_imgs);
        for (int i = 0; i < ll_fotos.getChildCount(); i++) {
            ImageSwitcher is = (ImageSwitcher) ll_fotos.getChildAt(i);
            if (is.getChildCount() == 1) {
                is.getLayoutParams().height = h_aux;
                is.addView(relativeLayout);
                is.showNext();
                return;
            }
        }
    }

    private void tratar_tv(TextView tv, String texto, boolean esEnlazable) {
        if (esEnlazable) {
            if (texto.toUpperCase().indexOf("<BR") == -1) {
                texto = texto.replace("\n", "<br>");
            }
            SpannableStringBuilder ss = new SpannableStringBuilder(Html.fromHtml(texto.replace("<a href", "@AHREF_INI@").replace("<A HREF", "@AHREF_INI@").replace("</a>", "@AHREF_FIN@").replace("</A>", "@AHREF_FIN@").replace("</ a>", "@AHREF_FIN@").replace("</ A>", "@AHREF_FIN@")));
            texto = ss.toString();
            int pos = texto.indexOf("@AHREF_INI@");
            while (pos != -1) {
                ss.replace(pos, pos + 11, "<a href");
                texto = ss.toString();
                pos = texto.indexOf("@AHREF_INI@");
            }
            for (pos = texto.indexOf("@AHREF_FIN@"); pos != -1; pos = ss.toString().indexOf("@AHREF_FIN@")) {
                ss.replace(pos, pos + 11, "</a>");
            }
            String cad = ss.toString();
            String cad_u = cad.toUpperCase();
            int pos2;
            for (pos = cad_u.indexOf("<A HREF="); pos != -1; pos = cad_u.indexOf("<A HREF=", pos2)) {
                int pos0 = pos;
                pos += 8;
                String car = cad.substring(pos, pos + 1);
                if (car.equals("\"") || car.equals("'")) {
                    pos++;
                }
                if (car.equals("\"")) {
                    pos2 = cad.indexOf("\"", pos);
                } else if (car.equals("'")) {
                    pos2 = cad.indexOf("'", pos);
                } else {
                    pos2 = cad.indexOf(">", pos);
                }
                if (pos2 != -1) {
                    this.url_global = cad.substring(pos, pos2).trim();
                    pos2 = cad.indexOf(">", pos2);
                    if (pos2 != -1) {
                        pos = pos2;
                        int pos2_aux1 = cad_u.indexOf("</A>", pos2);
                        if (pos2_aux1 == -1) {
                            pos2_aux1 = Strategy.TTL_SECONDS_INFINITE;
                        }
                        int pos2_aux2 = cad_u.indexOf("</ A>", pos2);
                        if (pos2_aux2 == -1) {
                            pos2_aux2 = Strategy.TTL_SECONDS_INFINITE;
                        }
                        if (!(pos2_aux1 == -1 && pos2_aux2 == -1)) {
                            pos2 = Math.min(pos2_aux1, pos2_aux2);
                            int pos3 = cad.indexOf(">", pos2);
                            ss.setSpan(new C07935(), pos, pos2, 33);
                            ss.delete(pos2, pos3 + 1);
                            pos2 -= (pos3 + 1) - pos2;
                            ss.delete(pos0, pos + 1);
                            pos2 -= (pos + 1) - pos0;
                            cad = ss.toString();
                            cad_u = cad.toUpperCase();
                        }
                    }
                }
                if (pos2 == -1) {
                    pos2 = pos;
                }
            }
            tv.setText(ss);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @TargetApi(21)
    private void wv_set() {
        if (VERSION.SDK_INT > 20) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.viewer, true);
        }
    }
}
