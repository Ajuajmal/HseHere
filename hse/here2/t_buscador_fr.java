package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class t_buscador_fr extends ListFragment implements LoaderCallbacks<Cursor> {
    private static final int TUTORIAL_LIST_LOADER = 1;
    private SimpleCursorAdapter adapter;
    cargaritems ci;
    cargarthumbs ct;
    Drawable d_item_sel;
    Drawable d_precio;
    Bundle extras;
    boolean fin;
    String fotoscargadas = "0";
    GradientDrawable gd;
    config globales;
    boolean hayfotos;
    int i_global;
    int idusu;
    boolean modo_h;
    boolean mostrar_loader;
    int ncargados;
    int pos_sel = -1;
    SharedPreferences settings;
    private OnTutSelectedListener tutSelectedListener;
    View v_footer;

    public interface OnTutSelectedListener {
        void onTutSelected(String str, long j);
    }

    private class cargaritems extends AsyncTask<String, Void, Byte> {
        int fila_global;
        String result_http;

        protected java.lang.Byte doInBackground(java.lang.String... r14) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x01d8 in list []
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
            r13 = this;
            r12 = 0;
            r6 = "";
            r9 = hse.here2.t_buscador_fr.this;
            r9 = r9.extras;
            r10 = "texto";
            r9 = r9.getString(r10);
            if (r9 == 0) goto L_0x0036;
        L_0x000f:
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01ff }
            r9.<init>();	 Catch:{ Exception -> 0x01ff }
            r9 = r9.append(r6);	 Catch:{ Exception -> 0x01ff }
            r10 = "&texto=";	 Catch:{ Exception -> 0x01ff }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01ff }
            r10 = hse.here2.t_buscador_fr.this;	 Catch:{ Exception -> 0x01ff }
            r10 = r10.extras;	 Catch:{ Exception -> 0x01ff }
            r11 = "texto";	 Catch:{ Exception -> 0x01ff }
            r10 = r10.getString(r11);	 Catch:{ Exception -> 0x01ff }
            r11 = "UTF-8";	 Catch:{ Exception -> 0x01ff }
            r10 = java.net.URLEncoder.encode(r10, r11);	 Catch:{ Exception -> 0x01ff }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01ff }
            r6 = r9.toString();	 Catch:{ Exception -> 0x01ff }
        L_0x0036:
            r9 = hse.here2.t_buscador_fr.this;
            r9 = r9.extras;
            r10 = "idcat";
            r9 = r9.getInt(r10, r12);
            if (r9 == 0) goto L_0x0063;
        L_0x0042:
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&idcat=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "idcat";
            r10 = r10.getInt(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
        L_0x0063:
            r9 = hse.here2.t_buscador_fr.this;
            r9 = r9.extras;
            r10 = "precio";
            r9 = r9.getString(r10);
            if (r9 == 0) goto L_0x0096;
        L_0x006f:
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01fc }
            r9.<init>();	 Catch:{ Exception -> 0x01fc }
            r9 = r9.append(r6);	 Catch:{ Exception -> 0x01fc }
            r10 = "&precio=";	 Catch:{ Exception -> 0x01fc }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01fc }
            r10 = hse.here2.t_buscador_fr.this;	 Catch:{ Exception -> 0x01fc }
            r10 = r10.extras;	 Catch:{ Exception -> 0x01fc }
            r11 = "precio";	 Catch:{ Exception -> 0x01fc }
            r10 = r10.getString(r11);	 Catch:{ Exception -> 0x01fc }
            r11 = "UTF-8";	 Catch:{ Exception -> 0x01fc }
            r10 = java.net.URLEncoder.encode(r10, r11);	 Catch:{ Exception -> 0x01fc }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01fc }
            r6 = r9.toString();	 Catch:{ Exception -> 0x01fc }
        L_0x0096:
            r9 = hse.here2.t_buscador_fr.this;
            r9 = r9.extras;
            r10 = "dia";
            r9 = r9.getInt(r10, r12);
            if (r9 == 0) goto L_0x0105;
        L_0x00a2:
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&dia=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "dia";
            r10 = r10.getInt(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&mes=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "mes";
            r10 = r10.getInt(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&anyo=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "anyo";
            r10 = r10.getInt(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
        L_0x0105:
            r9 = hse.here2.t_buscador_fr.this;
            r9 = r9.extras;
            r10 = "orden";
            r9 = r9.getInt(r10, r12);
            if (r9 == 0) goto L_0x0153;
        L_0x0111:
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&orden=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "orden";
            r10 = r10.getInt(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
            r9 = new java.lang.StringBuilder;
            r9.<init>();
            r9 = r9.append(r6);
            r10 = "&orden_tipo=";
            r9 = r9.append(r10);
            r10 = hse.here2.t_buscador_fr.this;
            r10 = r10.extras;
            r11 = "orden_tipo";
            r10 = r10.getBoolean(r11);
            r9 = r9.append(r10);
            r6 = r9.toString();
        L_0x0153:
            r1 = 0;
            r5 = new java.net.URL;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9.<init>();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = "http://srv1.androidcreator.com/srv/result.php?v=2&idapp=299914&idusu=";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = hse.here2.t_buscador_fr.this;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = r10.idusu;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = "&fila=";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = r13.fila_global;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r6);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.toString();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r5.<init>(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r5.openConnection();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r0 = r9;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r1 = r0;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = 1;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r1.setDoInput(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r1.setConnectTimeout(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r1.setReadTimeout(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = "User-Agent";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = "Android Vinebre Software";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r1.setRequestProperty(r9, r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r3 = r1.getInputStream();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r7 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9.<init>(r3);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r7.<init>(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r8.<init>();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
        L_0x01b0:
            r4 = r7.readLine();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            if (r4 == 0) goto L_0x01d9;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
        L_0x01b6:
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9.<init>();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r4);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = "\n";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.toString();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r8.append(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            goto L_0x01b0;
        L_0x01cd:
            r2 = move-exception;
            r9 = -1;
            r9 = java.lang.Byte.valueOf(r9);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            if (r1 == 0) goto L_0x01d8;
        L_0x01d5:
            r1.disconnect();
        L_0x01d8:
            return r9;
        L_0x01d9:
            r9 = r8.toString();	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r13.result_http = r9;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r13.result_http;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r10 = "@EURO@";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r11 = "€";	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r9 = r9.replace(r10, r11);	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            r13.result_http = r9;	 Catch:{ Exception -> 0x01cd, all -> 0x01f5 }
            if (r1 == 0) goto L_0x01f0;
        L_0x01ed:
            r1.disconnect();
        L_0x01f0:
            r9 = java.lang.Byte.valueOf(r12);
            goto L_0x01d8;
        L_0x01f5:
            r9 = move-exception;
            if (r1 == 0) goto L_0x01fb;
        L_0x01f8:
            r1.disconnect();
        L_0x01fb:
            throw r9;
        L_0x01fc:
            r9 = move-exception;
            goto L_0x0096;
        L_0x01ff:
            r9 = move-exception;
            goto L_0x0036;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.t_buscador_fr.cargaritems.doInBackground(java.lang.String[]):java.lang.Byte");
        }

        public cargaritems(int fila) {
            this.fila_global = fila;
        }

        protected void onPreExecute() {
            if (this.fila_global > 1 && t_buscador_fr.this.v_footer != null) {
                t_buscador_fr.this.getListView().addFooterView(t_buscador_fr.this.v_footer);
            }
        }

        protected void onPostExecute(Byte result) {
            if (t_buscador_fr.this.getActivity() != null) {
                t_buscador_fr.this.mostrar_loader = false;
                t_buscador_fr.this.setListShown(true);
                t_buscador_fr.this.getListView().removeFooterView(t_buscador_fr.this.v_footer);
                if (result.byteValue() == (byte) 0 && !this.result_http.contains("ANDROID:ERROR")) {
                    if (this.result_http.contains("ANDROID:VACIO")) {
                        t_buscador_fr.this.setEmptyText(t_buscador_fr.this.getResources().getString(C0627R.string.sin_resultados));
                        return;
                    }
                    String[] items = this.result_http.split(";");
                    for (int i = 0; i < items.length; i++) {
                        if (items[i].trim().equals("SIHAYFOTOS")) {
                            t_buscador_fr.this.hayfotos = true;
                        } else if (items[i].trim().equals("NOHAYFOTOS")) {
                            t_buscador_fr.this.hayfotos = false;
                        } else if (items[i].trim().equals("FIN")) {
                            t_buscador_fr.this.fin = true;
                        } else if (!items[i].trim().equals("")) {
                            t_buscador_fr hse_here2_t_buscador_fr = t_buscador_fr.this;
                            hse_here2_t_buscador_fr.i_global++;
                            String[] partes = items[i].split(",");
                            ContentValues itemData = new ContentValues();
                            itemData.put("_id", partes[0].trim());
                            itemData.put("ref", "");
                            itemData.put("titulo", partes[1].trim());
                            itemData.put("descr", "");
                            itemData.put("idcat", "0");
                            itemData.put("precio_descr", "");
                            itemData.put("url_detalle", partes[4].trim());
                            itemData.put("url_compra", "");
                            itemData.put("img1_id", partes[2].trim());
                            String aux1 = partes[3].trim();
                            if (aux1.equals("0")) {
                                itemData.put("img1_url", "");
                            } else {
                                itemData.put("img1_url", aux1);
                            }
                            itemData.put("det_cargado", Integer.valueOf(0));
                            aux1 = partes[5].trim();
                            if (!aux1.equals("-1")) {
                                itemData.put("precio", aux1);
                            }
                            itemData.put("anyo", partes[6].trim());
                            itemData.put("mes", partes[7].trim());
                            itemData.put("dia", partes[8].trim());
                            t_buscador_fr.this.getActivity().getContentResolver().insert(bd_provider.PRODUCTOS_URI, itemData);
                            hse_here2_t_buscador_fr = t_buscador_fr.this;
                            hse_here2_t_buscador_fr.ncargados++;
                        }
                    }
                    if (t_buscador_fr.this.ct == null || t_buscador_fr.this.ct.getStatus() != Status.RUNNING) {
                        t_buscador_fr.this.ct = new cargarthumbs();
                        t_buscador_fr.this.ct.execute(new String[0]);
                    }
                }
            }
        }
    }

    private class cargarthumbs extends AsyncTask<String, Void, Byte> {
        byte[] bitmapdata;
        String id_fotoacargar;

        private cargarthumbs() {
        }

        protected Byte doInBackground(String... urls) {
            if (t_buscador_fr.this.getActivity() == null) {
                return Byte.valueOf((byte) -1);
            }
            this.id_fotoacargar = "0";
            int idprod_aux = 0;
            String url_aux = "";
            SQLiteDatabase db = new bd(t_buscador_fr.this.getActivity()).getReadableDatabase();
            if (db != null) {
                Cursor c = db.query("productos", new String[]{"_id", "img1_id", "img1_url"}, "(img1_id NOT IN (" + t_buscador_fr.this.fotoscargadas + ")) AND (img1_p IS NULL)", null, null, null, "_id", "1");
                if (c.moveToFirst()) {
                    this.id_fotoacargar = c.getString(c.getColumnIndex("img1_id"));
                    StringBuilder stringBuilder = new StringBuilder();
                    t_buscador_fr hse_here2_t_buscador_fr = t_buscador_fr.this;
                    hse_here2_t_buscador_fr.fotoscargadas = stringBuilder.append(hse_here2_t_buscador_fr.fotoscargadas).append(",").append(this.id_fotoacargar).toString();
                    idprod_aux = c.getInt(c.getColumnIndex("_id"));
                    url_aux = c.getString(c.getColumnIndex("img1_url"));
                }
                c.close();
            }
            db.close();
            if (this.id_fotoacargar.equals("0")) {
                return Byte.valueOf((byte) 1);
            }
            if (url_aux.equals("")) {
                url_aux = "http://srv1.androidcreator.com/srv/imgs/prods/" + this.id_fotoacargar + "_" + idprod_aux + "_p.png";
            }
            try {
                URL url = new URL(url_aux);
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
                    config hse_here2_config = t_buscador_fr.this.globales;
                    options.inSampleSize = config.calculateInSampleSize(options, 300, 300);
                    HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                    conn2.setDoInput(true);
                    conn2.setConnectTimeout(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
                    conn2.setReadTimeout(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED);
                    conn2.connect();
                    InputStream is2 = conn2.getInputStream();
                    options.inJustDecodeBounds = false;
                    Bitmap bm_foto = BitmapFactory.decodeStream(is2, null, options);
                    is2.close();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        bm_foto.compress(CompressFormat.JPEG, 100, bos);
                        this.bitmapdata = bos.toByteArray();
                        try {
                            bos.close();
                        } catch (IOException e) {
                        }
                        return Byte.valueOf((byte) 0);
                    } catch (Exception e2) {
                        return Byte.valueOf((byte) -1);
                    }
                } catch (IOException e3) {
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e4) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPreExecute() {
        }

        protected void onPostExecute(Byte result) {
            if (t_buscador_fr.this.getActivity() != null) {
                if (result.byteValue() == (byte) 0) {
                    ContentValues itemData = new ContentValues();
                    itemData.put("img1_p", this.bitmapdata);
                    t_buscador_fr.this.getActivity().getContentResolver().update(bd_provider.PRODUCTOS_URI, itemData, "img1_id='" + this.id_fotoacargar + "'", null);
                }
                if (result.byteValue() != (byte) 1) {
                    t_buscador_fr.this.ct = new cargarthumbs();
                    t_buscador_fr.this.ct.execute(new String[0]);
                }
            }
        }
    }

    class C11891 implements ViewBinder {
        C11891() {
        }

        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            boolean ok;
            if (view.getId() == C0627R.id.iv_item) {
                if (!(t_buscador_fr.this.globales.c_perprod != 1 || t_buscador_fr.this.globales.c1_prods_l.equals("") || t_buscador_fr.this.globales.c2_prods_l.equals(""))) {
                    LinearLayout ll = null;
                    ok = true;
                    try {
                        ll = (LinearLayout) view.getParent().getParent().getParent().getParent();
                    } catch (Exception e) {
                        ok = false;
                    }
                    if (ok) {
                        ll.setBackgroundDrawable(t_buscador_fr.this.gd);
                    }
                }
                FrameLayout fl = (FrameLayout) view.getParent().getParent().getParent();
                if (t_buscador_fr.this.modo_h) {
                    ImageView iv_item_sel = (ImageView) fl.findViewById(C0627R.id.iv_item_sel);
                    if (cursor.getPosition() == t_buscador_fr.this.pos_sel) {
                        iv_item_sel.setImageDrawable(t_buscador_fr.this.d_item_sel);
                        iv_item_sel.setVisibility(0);
                    } else {
                        iv_item_sel.setVisibility(8);
                    }
                } else {
                    fl.findViewById(C0627R.id.ll_item_der).setVisibility(8);
                }
                ImageView iv_act = (ImageView) view;
                if (cursor.getInt(cursor.getColumnIndex("img1_id")) == 0) {
                    if (cursor.getString(cursor.getColumnIndex("img1_url")).equals("")) {
                        iv_act.setImageDrawable(null);
                        if (!t_buscador_fr.this.hayfotos) {
                            iv_act.setVisibility(8);
                        }
                        return true;
                    }
                }
                if (cursor.isNull(columnIndex)) {
                    iv_act.setImageDrawable(t_buscador_fr.this.getResources().getDrawable(C0627R.drawable.loader_g));
                } else {
                    byte[] byteArr = cursor.getBlob(columnIndex);
                    iv_act.setImageBitmap(BitmapFactory.decodeByteArray(byteArr, 0, byteArr.length));
                }
                if (t_buscador_fr.this.hayfotos) {
                    iv_act.setVisibility(8);
                }
                return true;
            } else if (view.getId() == C0627R.id.title) {
                ((TextView) view).setTypeface(Typeface.MONOSPACE);
                if (!t_buscador_fr.this.globales.c_tit_prods_l.equals("")) {
                    ((TextView) view).setTextColor(Color.parseColor("#" + t_buscador_fr.this.globales.c_tit_prods_l));
                }
                return false;
            } else if (view.getId() == C0627R.id.precio) {
                view.setVisibility(8);
                if (cursor.isNull(cursor.getColumnIndex("precio"))) {
                    return false;
                }
                DecimalFormat fmt = new DecimalFormat();
                fmt.setCurrency(Currency.getInstance(Locale.getDefault()));
                String v = fmt.format(cursor.getDouble(cursor.getColumnIndex("precio")));
                if (v.length() > 1) {
                    String aux1 = v.substring(v.length() - 2, v.length() - 1);
                    if (aux1.equals(",") || aux1.equals(".")) {
                        v = v + "0";
                    }
                }
                tv = (TextView) view;
                tv.setTypeface(Typeface.MONOSPACE);
                if (t_buscador_fr.this.globales.divisa.equals("")) {
                    tv.setText(v + ".");
                } else {
                    tv.setText(v + " " + Html.fromHtml(t_buscador_fr.this.globales.divisa));
                }
                if (!t_buscador_fr.this.globales.c_precio_prods_l.equals("")) {
                    tv.setTextColor(Color.parseColor("#" + t_buscador_fr.this.globales.c_precio_prods_l));
                }
                tv.setVisibility(0);
                return true;
            } else if (view.getId() == C0627R.id.antiguedad) {
                view.setVisibility(8);
                int anyo = cursor.getInt(cursor.getColumnIndex("anyo"));
                if (anyo != 0) {
                    String anyo_str = anyo + "";
                    int mes = cursor.getInt(cursor.getColumnIndex("mes"));
                    String mes_str = mes + "";
                    if (mes_str.equals("0")) {
                        mes_str = "1";
                    }
                    int dia = cursor.getInt(cursor.getColumnIndex("dia"));
                    String dia_str = dia + "";
                    if (dia_str.equals("0")) {
                        dia_str = "1";
                    }
                    ok = true;
                    Date d = null;
                    try {
                        d = DateFormat.getDateInstance(3, Locale.US).parse(mes_str + "/" + dia_str + "/" + anyo_str);
                    } catch (Exception e2) {
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
                        tv = (TextView) view;
                        tv.setTypeface(Typeface.MONOSPACE);
                        tv.setText(d_str);
                        if (!t_buscador_fr.this.globales.c_antiguedad_prods_l.equals("")) {
                            tv.setTextColor(Color.parseColor("#" + t_buscador_fr.this.globales.c_antiguedad_prods_l));
                        }
                        tv.setVisibility(0);
                        return true;
                    }
                }
                return false;
            } else if (view.getId() != C0627R.id.tv_aux) {
                return false;
            } else {
                if (cursor.getPosition() == cursor.getCount() - 1 && !t_buscador_fr.this.fin && (t_buscador_fr.this.ci == null || t_buscador_fr.this.ci.getStatus() != Status.RUNNING)) {
                    t_buscador_fr.this.i_global = cursor.getCount();
                    t_buscador_fr.this.ci = new cargaritems(cursor.getCount() + 1);
                    t_buscador_fr.this.ci.execute(new String[0]);
                }
                return false;
            }
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        if (!this.globales.c_prods_det.equals("")) {
            if (this.pos_sel != -1) {
                this.pos_sel = position;
                this.adapter.notifyDataSetChanged();
            } else {
                this.pos_sel = position;
                this.adapter.notifyDataSetChanged();
            }
        }
        Cursor tutorialCursor = getActivity().getContentResolver().query(Uri.withAppendedPath(bd_provider.PRODUCTOS_URI, String.valueOf(id)), new String[]{"url_detalle"}, null, null, null);
        if (tutorialCursor.moveToFirst()) {
            this.tutSelectedListener.onTutSelected(tutorialCursor.getString(0), id);
        }
        tutorialCursor.close();
    }

    @TargetApi(13)
    public void onCreate(Bundle savedInstanceState) {
        int w_aux;
        super.onCreate(savedInstanceState);
        this.globales = (config) getActivity().getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        String[] uiBindFrom = new String[]{"titulo", "img1_p", "precio", "anyo", "dia"};
        int[] uiBindTo = new int[]{C0627R.id.title, C0627R.id.iv_item, C0627R.id.precio, C0627R.id.antiguedad, C0627R.id.tv_aux};
        this.settings = getActivity().getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.extras = getActivity().getIntent().getExtras();
        this.modo_h = false;
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            w_aux = size.x;
        } else {
            w_aux = display.getWidth();
        }
        if (w_aux > ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
            this.modo_h = true;
        }
        this.d_item_sel = getResources().getDrawable(C0627R.drawable.item_sel);
        this.d_item_sel.setColorFilter(Color.parseColor("#" + this.globales.c_prods_det), Mode.MULTIPLY);
        if (!this.globales.c_precio_prods_l.equals("")) {
            this.d_precio = getResources().getDrawable(C0627R.drawable.precio);
            this.d_precio.setColorFilter(Color.parseColor("#" + this.globales.c_precio_prods_l), Mode.MULTIPLY);
        }
        if (savedInstanceState == null) {
            this.fin = false;
            this.mostrar_loader = true;
            this.ncargados = 0;
            SQLiteDatabase db = new bd(getActivity()).getWritableDatabase();
            if (db != null) {
                db.execSQL("DELETE FROM productos");
                db.execSQL("DELETE FROM fotos");
            }
            db.close();
        } else {
            this.hayfotos = savedInstanceState.getBoolean("hayfotos", true);
            this.mostrar_loader = savedInstanceState.getBoolean("mostrar_loader", false);
            this.fin = savedInstanceState.getBoolean("fin", false);
            this.ncargados = savedInstanceState.getInt("ncargados", 0);
        }
        if (!(this.globales.c1_prods_l.equals("") || this.globales.c2_prods_l.equals(""))) {
            this.gd = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_prods_l), Color.parseColor("#" + this.globales.c2_prods_l)});
        }
        getLoaderManager().initLoader(1, null, this);
        this.adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), C0627R.layout.list_item, null, uiBindFrom, uiBindTo, 2);
        this.adapter.setViewBinder(new C11891());
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.v_footer = ((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(C0627R.layout.list_footer, null, false);
        this.v_footer.findViewById(C0627R.id.tv_footer).setBackgroundColor(-1);
        if (!this.modo_h) {
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.setMargins(0, 0, 0, 0);
            ((TextView) this.v_footer.findViewById(C0627R.id.tv_footer)).setLayoutParams(layoutParams);
        }
        getListView().addFooterView(this.v_footer);
        setListAdapter(this.adapter);
        getListView().removeFooterView(this.v_footer);
        if (this.mostrar_loader) {
            setListShown(false);
        }
        getListView().setCacheColorHint(0);
        if (!(this.globales.c_perprod != 0 || this.globales.c1_prods_l.equals("") || this.globales.c2_prods_l.equals(""))) {
            getListView().setBackgroundDrawable(this.gd);
        }
        if (!this.fin && (this.ci == null || this.ci.getStatus() != Status.RUNNING)) {
            this.ci = new cargaritems(this.ncargados + 1);
            this.ci.execute(new String[0]);
        }
        if (this.ncargados <= 0) {
            return;
        }
        if (this.ct == null || this.ct.getStatus() != Status.RUNNING) {
            this.ct = new cargarthumbs();
            this.ct.execute(new String[0]);
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("hayfotos", this.hayfotos);
        savedInstanceState.putBoolean("mostrar_loader", this.mostrar_loader);
        savedInstanceState.putBoolean("fin", this.fin);
        savedInstanceState.putInt("ncargados", this.ncargados);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.tutSelectedListener = (OnTutSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnTutSelectedListener");
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), bd_provider.PRODUCTOS_URI, new String[]{"_id", "titulo", "precio", "anyo", "mes", "dia", "url_detalle", "img1_p", "img1_id", "img1_url"}, null, null, "orden");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        this.adapter.swapCursor(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        this.adapter.swapCursor(null);
    }
}
