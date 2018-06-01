package hse.here2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.apache.http.HttpStatus;

public class t_buscusus extends Activity implements OnClickListener, OnItemClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    boolean atras_pulsado = false;
    boolean c1_esclaro;
    cargar_fotos c_f;
    cargar_usus c_u;
    boolean cargado_primeravez = false;
    String cbtn;
    String cod_g;
    String codigo;
    boolean coments_glob;
    ProgressDialog dialog_cargando;
    Dialog dialog_filtros;
    boolean es_root;
    Bundle extras;
    boolean fdist;
    int fdist_v;
    boolean fedad1;
    int fedad1_v;
    boolean fedad2;
    int fedad2_v;
    boolean finalizar = false;
    boolean finalizar_2 = false;
    int fotos_perfil;
    boolean fsexo;
    int fsexo_v;
    boolean galeria;
    config globales;
    GridViewAdapter gridAdapter;
    GridView gridView;
    int idusu;
    int ind_secc;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    int p_descr;
    int p_dist;
    int p_fnac;
    int p_sexo;
    int padding_1;
    int padding_2;
    String pais;
    File path;
    boolean privados_glob;
    SharedPreferences settings;
    Bitmap sinfoto;
    TextView tv_fdist;
    TextView tv_fedad1;
    TextView tv_fedad2;
    TextView tv_fsexo;
    ArrayList<Usu> usus_a;
    boolean usus_a_completo;
    View v_abrir_secc;

    class C07521 implements OnItemClickListener {
        C07521() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_buscusus.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_buscusus.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_buscusus.this.globales.menu_a_secciones[position]));
            t_buscusus.this.onClick(view);
        }
    }

    class C07532 implements OnClickListener {
        C07532() {
        }

        public void onClick(View v) {
            int pos_aux;
            int v_aux;
            Editor e = t_buscusus.this.settings.edit();
            boolean modif = false;
            if (t_buscusus.this.fdist) {
                pos_aux = ((Spinner) t_buscusus.this.dialog_filtros.findViewById(C0627R.id.sp_dist)).getSelectedItemPosition();
                if (pos_aux == 2) {
                    v_aux = 1;
                } else if (pos_aux == 3) {
                    v_aux = 2;
                } else if (pos_aux == 4) {
                    v_aux = 5;
                } else if (pos_aux == 5) {
                    v_aux = 10;
                } else if (pos_aux == 6) {
                    v_aux = 20;
                } else if (pos_aux == 7) {
                    v_aux = 50;
                } else if (pos_aux == 8) {
                    v_aux = 100;
                } else if (pos_aux == 9) {
                    v_aux = 200;
                } else if (pos_aux == 10) {
                    v_aux = HttpStatus.SC_INTERNAL_SERVER_ERROR;
                } else {
                    v_aux = 0;
                }
                if (t_buscusus.this.fdist_v != v_aux) {
                    t_buscusus.this.fdist_v = v_aux;
                    e.putInt("fdist_v_" + t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].id, t_buscusus.this.fdist_v);
                    modif = true;
                }
            }
            if (t_buscusus.this.fsexo) {
                pos_aux = ((Spinner) t_buscusus.this.dialog_filtros.findViewById(C0627R.id.sp_sexo)).getSelectedItemPosition();
                if (pos_aux == 2) {
                    v_aux = 1;
                } else if (pos_aux == 3) {
                    v_aux = 2;
                } else {
                    v_aux = 0;
                }
                if (t_buscusus.this.fsexo_v != v_aux) {
                    t_buscusus.this.fsexo_v = v_aux;
                    e.putInt("fsexo_v_" + t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].id, t_buscusus.this.fsexo_v);
                    modif = true;
                }
            }
            if (t_buscusus.this.fedad1) {
                pos_aux = ((Spinner) t_buscusus.this.dialog_filtros.findViewById(C0627R.id.sp_edad1)).getSelectedItemPosition();
                if (pos_aux == 2) {
                    v_aux = 18;
                } else if (pos_aux > 2) {
                    v_aux = ((pos_aux - 3) * 5) + 25;
                } else {
                    v_aux = 0;
                }
                if (t_buscusus.this.fedad1_v != v_aux) {
                    t_buscusus.this.fedad1_v = v_aux;
                    e.putInt("fedad1_v_" + t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].id, t_buscusus.this.fedad1_v);
                    modif = true;
                }
            }
            if (t_buscusus.this.fedad2) {
                pos_aux = ((Spinner) t_buscusus.this.dialog_filtros.findViewById(C0627R.id.sp_edad2)).getSelectedItemPosition();
                if (pos_aux == 2) {
                    v_aux = 18;
                } else if (pos_aux > 2) {
                    v_aux = ((pos_aux - 3) * 5) + 25;
                } else {
                    v_aux = 0;
                }
                if (t_buscusus.this.fedad2_v != v_aux) {
                    t_buscusus.this.fedad2_v = v_aux;
                    e.putInt("fedad2_v_" + t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].id, t_buscusus.this.fedad2_v);
                    modif = true;
                }
            }
            if (modif) {
                e.commit();
                t_buscusus.this.mostrar_filtros();
                t_buscusus.this.usus_a.clear();
                t_buscusus.this.usus_a_completo = false;
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
                t_buscusus.this.cargado_primeravez = false;
                if (t_buscusus.this.c_u != null) {
                    t_buscusus.this.c_u.cancel(true);
                }
                t_buscusus.this.c_u = new cargar_usus(0);
                t_buscusus.this.c_u.execute(new String[0]);
            }
            t_buscusus.this.dialog_filtros.dismiss();
        }
    }

    public class GridViewAdapter extends ArrayAdapter<Usu> {
        private Context context;
        private ArrayList<Usu> data = new ArrayList();
        private int layoutResourceId;

        class ViewHolder {
            ImageView image;
            TextView imageSubtitle;
            TextView imageTitle;
            LinearLayout ll_conectado;
            LinearLayout ll_favorito_bottom;
            LinearLayout ll_favorito_top;
            LinearLayout ll_txt;
            ProgressBar pb_foto;
            ProgressBar pb_foto_inv;

            ViewHolder() {
            }
        }

        public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Usu> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            View row = convertView;
            if (!t_buscusus.this.usus_a_completo && t_buscusus.this.usus_a.size() - position < 10 && (t_buscusus.this.c_u == null || t_buscusus.this.c_u.getStatus() != Status.RUNNING)) {
                t_buscusus.this.c_u = new cargar_usus(t_buscusus.this.usus_a.size());
                t_buscusus.this.c_u.execute(new String[0]);
            }
            if (t_buscusus.this.fotos_perfil > 0 && t_buscusus.this.cargado_primeravez && (t_buscusus.this.c_f == null || t_buscusus.this.c_f.getStatus() != Status.RUNNING)) {
                t_buscusus.this.c_f = new cargar_fotos();
                t_buscusus.this.c_f.execute(new String[0]);
            }
            if (row == null) {
                row = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, parent, false);
                holder = new ViewHolder();
                holder.imageTitle = (TextView) row.findViewById(C0627R.id.tv_usugrid);
                holder.imageSubtitle = (TextView) row.findViewById(C0627R.id.tv_usugrid2);
                holder.image = (ImageView) row.findViewById(C0627R.id.iv_usugrid);
                holder.pb_foto = (ProgressBar) row.findViewById(C0627R.id.pb_foto);
                holder.pb_foto_inv = (ProgressBar) row.findViewById(C0627R.id.pb_foto_inv);
                holder.ll_txt = (LinearLayout) row.findViewById(C0627R.id.ll_usugrid);
                holder.ll_favorito_top = (LinearLayout) row.findViewById(C0627R.id.ll_favorito_top);
                holder.ll_favorito_bottom = (LinearLayout) row.findViewById(C0627R.id.ll_favorito_bottom);
                holder.ll_conectado = (LinearLayout) row.findViewById(C0627R.id.ll_conectado);
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(holder.pb_foto, t_buscusus.this.globales.c_icos);
                    config.progress_color(holder.pb_foto_inv, t_buscusus.this.globales.c_icos);
                }
                row.setTag(holder);
            } else {
                holder = (ViewHolder) row.getTag();
            }
            Usu item = (Usu) this.data.get(position);
            if (item.id.equals("-1")) {
                holder.imageTitle.setText("");
                holder.imageSubtitle.setText("");
                holder.image.setImageBitmap(null);
                holder.pb_foto.setVisibility(8);
                holder.pb_foto_inv.setVisibility(8);
                if (t_buscusus.this.c1_esclaro) {
                    holder.pb_foto_inv.setVisibility(0);
                } else {
                    holder.pb_foto.setVisibility(0);
                }
                holder.ll_txt.setVisibility(8);
                holder.ll_favorito_top.setVisibility(8);
                holder.ll_favorito_bottom.setVisibility(8);
                holder.ll_conectado.setVisibility(8);
            } else {
                if (t_buscusus.this.fotos_perfil == 0) {
                    holder.ll_txt.setPadding(t_buscusus.this.padding_1, t_buscusus.this.padding_2, t_buscusus.this.padding_1, t_buscusus.this.padding_2);
                }
                holder.ll_txt.setVisibility(0);
                if (item.fav) {
                    holder.ll_favorito_bottom.setVisibility(8);
                    holder.ll_favorito_top.setVisibility(0);
                } else {
                    holder.ll_favorito_top.setVisibility(8);
                    holder.ll_favorito_bottom.setVisibility(8);
                }
                if (item.conectado) {
                    holder.ll_conectado.setVisibility(0);
                } else {
                    holder.ll_conectado.setVisibility(8);
                }
                holder.imageTitle.setText(item.nick);
                if (t_buscusus.this.p_fnac > 0 || t_buscusus.this.p_dist == 1) {
                    String cad_aux = "";
                    if (t_buscusus.this.p_fnac > 0 && item.edad > 0) {
                        cad_aux = cad_aux + item.edad + t_buscusus.this.getResources().getString(C0627R.string.anyos_abrev);
                    }
                    if (t_buscusus.this.p_dist == 1 && !item.dist.equals("")) {
                        if (!cad_aux.equals("")) {
                            cad_aux = cad_aux + ", ";
                        }
                        String u = "km.";
                        int dist = Integer.parseInt(item.dist) / 1000;
                        if (t_buscusus.this.pais.equals("US") || t_buscusus.this.pais.equals("GB") || t_buscusus.this.pais.equals("MM")) {
                            u = "mi.";
                            dist = (int) (((double) dist) / 1.6d);
                        }
                        if (dist == 0) {
                            cad_aux = cad_aux + "<1" + u;
                        } else {
                            cad_aux = cad_aux + dist + u;
                        }
                    }
                    holder.imageSubtitle.setText(cad_aux);
                    holder.imageSubtitle.setVisibility(0);
                }
                holder.pb_foto.setVisibility(8);
                holder.pb_foto_inv.setVisibility(8);
                if (t_buscusus.this.fotos_perfil > 0) {
                    boolean pendiente = false;
                    if (item.cargando || !(!item.foto_pendiente || item.vfoto.equals("0") || item.vfoto.equals(t_buscusus.this.settings.getString("fperfil_" + item.id, "0")))) {
                        pendiente = true;
                    }
                    if (pendiente) {
                        holder.image.setImageBitmap(null);
                        if (t_buscusus.this.c1_esclaro) {
                            holder.pb_foto_inv.setVisibility(0);
                        } else {
                            holder.pb_foto.setVisibility(0);
                        }
                    } else {
                        Bitmap bm_aux;
                        if (t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].fotos_perfil <= 0 || item.vfoto.equals("0")) {
                            bm_aux = t_buscusus.this.sinfoto;
                        } else {
                            try {
                                File file = new File(t_buscusus.this.path, "fperfil_" + item.id + ".jpg");
                                if (file.exists()) {
                                    bm_aux = Media.getBitmap(t_buscusus.this.getContentResolver(), Uri.fromFile(file));
                                } else {
                                    bm_aux = t_buscusus.this.sinfoto;
                                }
                            } catch (Exception e) {
                                bm_aux = t_buscusus.this.sinfoto;
                            }
                        }
                        holder.image.setImageBitmap(bm_aux);
                    }
                } else {
                    holder.image.setVisibility(8);
                }
            }
            return row;
        }
    }

    private class Usu {
        boolean cargando;
        String coments;
        boolean conectado;
        String dist;
        int edad;
        boolean fav;
        String fnac_a;
        String fnac_d;
        String fnac_m;
        boolean foto_pendiente;
        String id;
        String nick;
        String privados;
        String sexo;
        String vfoto;

        private Usu() {
            this.foto_pendiente = true;
            this.cargando = false;
            this.fav = false;
            this.conectado = false;
        }
    }

    private class cargar_fotos extends AsyncTask<String, Void, String> {
        Bitmap bmImg;
        int i_cargando;
        String idusu_acargar;
        String vfoto_bd;

        private cargar_fotos() {
            this.i_cargando = -1;
        }

        protected void onPreExecute() {
            int i = t_buscusus.this.gridView.getFirstVisiblePosition();
            while (i <= Math.max(t_buscusus.this.gridView.getLastVisiblePosition(), 30) && t_buscusus.this.usus_a != null && i < t_buscusus.this.usus_a.size()) {
                if (!((Usu) t_buscusus.this.usus_a.get(i)).foto_pendiente || ((Usu) t_buscusus.this.usus_a.get(i)).vfoto.equals("0") || ((Usu) t_buscusus.this.usus_a.get(i)).vfoto.equals(t_buscusus.this.settings.getString("fperfil_" + ((Usu) t_buscusus.this.usus_a.get(i)).id, "0"))) {
                    i++;
                } else {
                    this.idusu_acargar = ((Usu) t_buscusus.this.usus_a.get(i)).id + "";
                    this.vfoto_bd = ((Usu) t_buscusus.this.usus_a.get(i)).vfoto;
                    ((Usu) t_buscusus.this.usus_a.get(i)).cargando = true;
                    ((Usu) t_buscusus.this.usus_a.get(i)).foto_pendiente = false;
                    this.i_cargando = i;
                    return;
                }
            }
        }

        protected String doInBackground(String... urls) {
            if (this.idusu_acargar == null) {
                return "-1";
            }
            try {
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://imgs1.e-droid.net/srv/imgs/usus/" + this.idusu_acargar + "_1_p.jpg?v=" + this.vfoto_bd).openConnection();
                    conn.setDoInput(true);
                    conn.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                    conn.setReadTimeout(20000);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    this.bmImg = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();
                    try {
                        this.bmImg.compress(CompressFormat.JPEG, 70, new FileOutputStream(new File(t_buscusus.this.path, "fperfil_" + this.idusu_acargar + ".jpg")));
                        return "1";
                    } catch (Exception e) {
                        return "0";
                    }
                } catch (IOException e2) {
                    return "0";
                }
            } catch (MalformedURLException e3) {
                return "0";
            }
        }

        protected void onPostExecute(String result) {
            if (!(this.i_cargando == -1 || t_buscusus.this.usus_a == null)) {
                try {
                    ((Usu) t_buscusus.this.usus_a.get(this.i_cargando)).cargando = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            if (this.idusu_acargar != null && !result.equals("-1")) {
                if (result == "1") {
                    Editor e2 = t_buscusus.this.settings.edit();
                    e2.putString("fperfil_" + this.idusu_acargar, this.vfoto_bd);
                    e2.commit();
                }
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
                new cargar_fotos().execute(new String[0]);
            } else if (!result.equals("-1")) {
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
            }
        }
    }

    private class cargar_usus extends AsyncTask<String, Void, String> {
        int ind_ini;

        cargar_usus(int ind_ini) {
            this.ind_ini = ind_ini;
        }

        protected void onPreExecute() {
            if (t_buscusus.this.usus_a.isEmpty() || !((Usu) t_buscusus.this.usus_a.get(t_buscusus.this.usus_a.size() - 1)).id.equals("-1")) {
                Usu u = new Usu();
                u.id = "-1";
                u.vfoto = "0";
                t_buscusus.this.usus_a.add(u);
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
            }
        }

        protected String doInBackground(String... urls) {
            String stringBuilder;
            int i = 1;
            String u = "km";
            if (t_buscusus.this.pais.equals("US") || t_buscusus.this.pais.equals("GB") || t_buscusus.this.pais.equals("MM")) {
                u = "mi";
            }
            HttpURLConnection conn = null;
            try {
                StringBuilder append = new StringBuilder().append("http://srv1.androidcreator.com/srv/obtener_usus.php?idusu=").append(t_buscusus.this.idusu).append("&c=").append(t_buscusus.this.codigo).append("&ind_ini=").append(this.ind_ini).append("&accext=");
                if (!t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].acceso_a_externo) {
                    i = 0;
                }
                conn = (HttpURLConnection) new URL(append.append(i).append("&idsec=").append(t_buscusus.this.globales.secciones_a[t_buscusus.this.ind_secc].id).toString() + "&fdist_v=" + t_buscusus.this.fdist_v + "&fdist_u=" + u + "&fsexo_v=" + t_buscusus.this.fsexo_v + "&fedad1_v=" + t_buscusus.this.fedad1_v + "&fedad2_v=" + t_buscusus.this.fedad2_v).openConnection();
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
                stringBuilder = sb_total.toString();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                stringBuilder = "";
                return stringBuilder;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return stringBuilder;
        }

        protected void onPostExecute(String result) {
            if (t_buscusus.this.usus_a != null && ((Usu) t_buscusus.this.usus_a.get(t_buscusus.this.usus_a.size() - 1)).id.equals("-1")) {
                t_buscusus.this.usus_a.remove(t_buscusus.this.usus_a.size() - 1);
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
            }
            if (result.contains("ANDROID:OK DATOS:")) {
                String[] usus_priv_a = result.substring(result.indexOf("DATOS:") + 6).split(";");
                Editor e = t_buscusus.this.settings.edit();
                int i = 0;
                while (!usus_priv_a[i].equals("S") && !usus_priv_a[i].equals("N")) {
                    String[] usu_a = usus_priv_a[i].split("@");
                    Usu u = new Usu();
                    u.id = usu_a[0];
                    u.nick = usu_a[1];
                    u.vfoto = usu_a[2];
                    u.privados = usu_a[3];
                    u.fnac_d = usu_a[4];
                    u.fnac_m = usu_a[5];
                    u.fnac_a = usu_a[6];
                    int edad = 0;
                    if (!(u.fnac_d.equals("0") || u.fnac_m.equals("0") || u.fnac_a.equals("0"))) {
                        Calendar c = Calendar.getInstance();
                        c.set(Integer.parseInt(u.fnac_a), Integer.parseInt(u.fnac_m) - 1, Integer.parseInt(u.fnac_d));
                        Calendar act = Calendar.getInstance();
                        edad = act.get(1) - c.get(1);
                        if (c.get(2) > act.get(2) || (c.get(2) == act.get(2) && c.get(5) > act.get(5))) {
                            edad--;
                        }
                    }
                    u.edad = edad;
                    u.sexo = usu_a[7];
                    u.coments = usu_a[8];
                    u.dist = usu_a[9].equals("-1") ? "" : usu_a[9];
                    u.fav = usu_a[10].equals("1");
                    if (u.fav) {
                        e.putBoolean("usufav_" + u.id, true);
                    } else {
                        e.remove("usufav_" + u.id);
                    }
                    u.conectado = usu_a[11].equals("1");
                    t_buscusus.this.usus_a.add(u);
                    i++;
                }
                e.commit();
                if (usus_priv_a[i].equals("N")) {
                    t_buscusus.this.usus_a_completo = true;
                }
                t_buscusus.this.gridAdapter.notifyDataSetChanged();
                if (i > 0 && (t_buscusus.this.c_f == null || t_buscusus.this.c_f.getStatus() != Status.RUNNING)) {
                    t_buscusus.this.c_f = new cargar_fotos();
                    t_buscusus.this.c_f.execute(new String[0]);
                    t_buscusus.this.cargado_primeravez = true;
                }
                if (t_buscusus.this.usus_a.isEmpty()) {
                    final AlertDialog d_aux = new Builder(t_buscusus.this).setPositiveButton(C0627R.string.aceptar, null).setMessage(C0627R.string.sin_usus).create();
                    if (!t_buscusus.this.cbtn.equals("")) {
                        d_aux.setOnShowListener(new OnShowListener() {
                            public void onShow(DialogInterface arg0) {
                                d_aux.getButton(-1).setTextColor(Color.parseColor("#" + t_buscusus.this.cbtn));
                            }
                        });
                    }
                    d_aux.show();
                    try {
                        ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        String c1 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c1;
        String c2 = this.globales.secciones_a[this.globales.ind_secc_sel_2].c2;
        this.c1_esclaro = config.esClaro("#" + c1);
        this.cbtn = config.aplicar_color_dialog(c1, this.globales.c_icos);
        if (VERSION.SDK_INT > 12 && !this.c1_esclaro) {
            setTheme(C0627R.style.holonolight);
        }
        this.extras = getIntent().getExtras();
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
        this.ind_secc = this.globales.ind_secc_sel_2;
        this.path = new File(Environment.getExternalStorageDirectory(), getPackageName());
        super.onCreate(savedInstanceState);
        this.settings = getSharedPreferences("sh", 0);
        this.idusu = this.settings.getInt("idusu", 0);
        this.codigo = this.settings.getString("cod", "");
        this.cod_g = this.settings.getString("cod_g", "");
        float scale = getResources().getDisplayMetrics().density;
        this.padding_1 = (int) ((5.0f * scale) + 0.5f);
        this.padding_2 = (int) ((15.0f * scale) + 0.5f);
        this.pais = Locale.getDefault().getCountry();
        this.p_fnac = this.globales.secciones_a[this.ind_secc].p_fnac;
        this.p_sexo = this.globales.secciones_a[this.ind_secc].p_sexo;
        this.p_descr = this.globales.secciones_a[this.ind_secc].p_descr;
        this.p_dist = this.globales.secciones_a[this.ind_secc].p_dist;
        this.coments_glob = this.globales.secciones_a[this.ind_secc].coments;
        this.galeria = this.globales.secciones_a[this.ind_secc].galeria;
        this.privados_glob = this.globales.secciones_a[this.ind_secc].privados;
        this.fotos_perfil = this.globales.secciones_a[this.ind_secc].fotos_perfil;
        this.fsexo = this.globales.secciones_a[this.ind_secc].fsexo;
        this.fdist = this.globales.secciones_a[this.ind_secc].fdist;
        this.fedad1 = this.globales.secciones_a[this.ind_secc].fedad1;
        this.fedad2 = this.globales.secciones_a[this.ind_secc].fedad2;
        this.fsexo_v = this.globales.secciones_a[this.ind_secc].fsexo_def;
        this.fdist_v = this.globales.secciones_a[this.ind_secc].fdist_def;
        this.fedad1_v = this.globales.secciones_a[this.ind_secc].fedad1_def;
        this.fedad2_v = this.globales.secciones_a[this.ind_secc].fedad2_def;
        if (this.fdist) {
            this.fdist_v = this.settings.getInt("fdist_v_" + this.globales.secciones_a[this.ind_secc].id, this.fdist_v);
        }
        if (this.fsexo) {
            this.fsexo_v = this.settings.getInt("fsexo_v_" + this.globales.secciones_a[this.ind_secc].id, this.fsexo_v);
        }
        if (this.fedad1) {
            this.fedad1_v = this.settings.getInt("fedad1_v_" + this.globales.secciones_a[this.ind_secc].id, this.fedad1_v);
        }
        if (this.fedad2) {
            this.fedad2_v = this.settings.getInt("fedad2_v_" + this.globales.secciones_a[this.ind_secc].id, this.fedad2_v);
        }
        Intent i;
        if (this.settings.getString("nick", "").equals("")) {
            if (this.globales.fb_modo == 2) {
                int i2;
                StringBuilder append = new StringBuilder().append("https://mysocialapp.net/").append(this.globales.dominio).append("/?u=").append(this.idusu).append("&cod_g=").append(this.cod_g).append("&c=").append(this.codigo).append("&a=s").append("&x=").append(this.settings.getString("x", "")).append("&y=").append(this.settings.getString("y", "")).append("&c1_w=").append(c1).append("&c2_w=").append(c2).append("&c1_c=");
                if (this.c1_esclaro) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                String url = (append.append(i2).append("&idl=").append(Locale.getDefault().getLanguage()).toString() + "&idsecc=" + this.ind_secc) + "&desde_buscusus=true";
                if (this.es_root) {
                    url = url + "&es_root=" + this.es_root;
                }
                this.es_root = false;
                i = new Intent(this, fb.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                this.finalizar_2 = true;
                startActivityForResult(i, 0);
            } else {
                i = new Intent(this, chat_perfil.class);
                i.putExtra("idsecc", this.ind_secc);
                i.putExtra("desde_buscusus", true);
                if (this.es_root) {
                    i.putExtra("es_root", this.es_root);
                }
                this.es_root = false;
                this.finalizar_2 = true;
                startActivityForResult(i, 0);
            }
        } else if ((this.fotos_perfil == 2 && !this.globales.getTempFile(this, 1).exists()) || ((this.p_fnac == 2 && (this.settings.getInt("fnac_d", 0) == 0 || this.settings.getInt("fnac_m", 0) == 0 || this.settings.getInt("fnac_a", 0) == 0)) || ((this.p_sexo == 2 && this.settings.getInt("sexo", 0) == 0) || (this.p_descr == 2 && this.settings.getString("descr", "").equals(""))))) {
            i = new Intent(this, preperfil.class);
            i.putExtra("idsecc", this.ind_secc);
            i.putExtra("desde_buscusus", true);
            if (this.es_root) {
                i.putExtra("es_root", this.es_root);
            }
            this.es_root = false;
            this.finalizar_2 = true;
            startActivityForResult(i, 0);
        }
        setContentView(C0627R.layout.t_buscusus);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        if (this.extras == null || !this.extras.containsKey("ad_entrar")) {
            z = false;
        } else {
            z = true;
        }
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        if (!c1.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + c1), Color.parseColor("#" + c2)}));
        }
        if (this.c1_esclaro) {
            this.sinfoto = BitmapFactory.decodeResource(getResources(), C0627R.drawable.sinfoto_light);
        } else {
            this.sinfoto = BitmapFactory.decodeResource(getResources(), C0627R.drawable.sinfoto);
        }
        findViewById(C0627R.id.btnperfil).setOnClickListener(this);
        this.tv_fsexo = (TextView) findViewById(C0627R.id.tv_fsexo);
        this.tv_fdist = (TextView) findViewById(C0627R.id.tv_fdist);
        this.tv_fedad1 = (TextView) findViewById(C0627R.id.tv_fedad1);
        this.tv_fedad2 = (TextView) findViewById(C0627R.id.tv_fedad2);
        mostrar_filtros();
        this.usus_a = new ArrayList();
        this.usus_a_completo = false;
        this.gridView = (GridView) findViewById(C0627R.id.grid);
        this.gridAdapter = new GridViewAdapter(this, C0627R.layout.t_buscusus_row, this.usus_a);
        this.gridView.setAdapter(this.gridAdapter);
        this.gridView.setOnItemClickListener(this);
        this.dialog_filtros = crear_dialog_filtros();
        this.cargado_primeravez = false;
        this.c_u = new cargar_usus(0);
        this.c_u.execute(new String[0]);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean finalizar_aux = false;
        if (resultCode == -1 && data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
            finalizar_aux = true;
            if (!data.getExtras().getBoolean("finalizar_app")) {
                this.es_root = false;
            }
            setResult(-1, data);
            finish();
        }
        if (!finalizar_aux && this.finalizar_2) {
            finish();
        }
    }

    void incluir_menu_pre() {
        int i;
        int nsecc_mostradas = this.globales.incluir_menu(this);
        if (this.globales.tipomenu == 1) {
            this.mDrawerList = (ListView) findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setOnItemClickListener(new C07521());
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

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Usu u = (Usu) this.usus_a.get(position);
        if (!u.id.equals("-1")) {
            Intent i = new Intent(this, profile.class);
            i.putExtra("id", u.id);
            i.putExtra("privados", u.privados);
            i.putExtra("nombre", u.nick);
            i.putExtra("coments", u.coments);
            i.putExtra("fnac_d", u.fnac_d);
            i.putExtra("fnac_m", u.fnac_m);
            i.putExtra("fnac_a", u.fnac_a);
            i.putExtra("sexo", u.sexo);
            i.putExtra("vfoto", u.vfoto);
            i.putExtra("dist", u.dist);
            i.putExtra("p_fnac", this.p_fnac);
            i.putExtra("p_sexo", this.p_sexo);
            i.putExtra("p_descr", this.p_descr);
            i.putExtra("p_dist", this.p_dist);
            i.putExtra("coments_chat", this.coments_glob);
            i.putExtra("galeria", this.galeria);
            i.putExtra("privados_chat", this.privados_glob);
            i.putExtra("fotos_perfil", this.fotos_perfil);
            i.putExtra("fotos_chat", 1);
            i.putExtra("conectado", u.conectado);
            startActivityForResult(i, 0);
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.ll_filtros) {
            this.dialog_filtros.show();
        } else if (v.getId() == C0627R.id.btnperfil) {
            Intent intent = new Intent(this, preperfil.class);
            intent.putExtra("idsecc", this.ind_secc);
            intent.putExtra("nocompletar", true);
            intent.putExtra("desde_buscusus", true);
            startActivityForResult(intent, 0);
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

    Dialog crear_dialog_filtros() {
        Spinner sp;
        List<String> spinnerArray;
        int pos_aux;
        int i;
        Dialog dialog = new Dialog(this);
        if (getResources().getConfiguration().orientation == 2) {
            dialog.setContentView(C0627R.layout.filtros_h);
        } else {
            dialog.setContentView(C0627R.layout.filtros);
        }
        dialog.setTitle(getResources().getString(C0627R.string.selecciona));
        Button dialogButton = (Button) dialog.findViewById(C0627R.id.btn_aceptar);
        dialogButton.setOnClickListener(new C07532());
        if (VERSION.SDK_INT > 20) {
            dialogButton.setTextColor(Color.parseColor("#" + this.globales.c_icos));
        }
        if (this.fedad1) {
            sp = (Spinner) dialog.findViewById(C0627R.id.sp_edad1);
            spinnerArray = new ArrayList();
            spinnerArray.add(getResources().getString(C0627R.string.edad_min));
            spinnerArray.add(getResources().getString(C0627R.string.todo));
            spinnerArray.add(">18" + getResources().getString(C0627R.string.anyos_abrev));
            pos_aux = -1;
            for (i = 25; i < 76; i += 5) {
                spinnerArray.add(">" + i + getResources().getString(C0627R.string.anyos_abrev));
                if (this.fedad1_v == i) {
                    pos_aux = spinnerArray.size() - 1;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(this, 17367048, spinnerArray);
            adapter.setDropDownViewResource(17367049);
            sp.setAdapter(adapter);
            if (pos_aux != -1) {
                sp.setSelection(pos_aux);
            } else if (this.fedad1_v == 18) {
                sp.setSelection(2);
            } else {
                sp.setSelection(0);
            }
            sp.setVisibility(0);
        }
        if (this.fedad2) {
            sp = (Spinner) dialog.findViewById(C0627R.id.sp_edad2);
            spinnerArray = new ArrayList();
            spinnerArray.add(getResources().getString(C0627R.string.edad_max));
            spinnerArray.add(getResources().getString(C0627R.string.todo));
            spinnerArray.add("<18" + getResources().getString(C0627R.string.anyos_abrev));
            pos_aux = -1;
            for (i = 25; i < 76; i += 5) {
                spinnerArray.add("<" + i + getResources().getString(C0627R.string.anyos_abrev));
                if (this.fedad2_v == i) {
                    pos_aux = spinnerArray.size() - 1;
                }
            }
            adapter = new ArrayAdapter(this, 17367048, spinnerArray);
            adapter.setDropDownViewResource(17367049);
            sp.setAdapter(adapter);
            if (pos_aux != -1) {
                sp.setSelection(pos_aux);
            } else if (this.fedad2_v == 18) {
                sp.setSelection(2);
            } else {
                sp.setSelection(0);
            }
            sp.setVisibility(0);
        }
        if (this.fsexo) {
            sp = (Spinner) dialog.findViewById(C0627R.id.sp_sexo);
            spinnerArray = new ArrayList();
            spinnerArray.add(getResources().getString(C0627R.string.petic_sexo));
            spinnerArray.add(getResources().getString(C0627R.string.todo));
            spinnerArray.add(getResources().getString(C0627R.string.hombres));
            spinnerArray.add(getResources().getString(C0627R.string.mujeres));
            adapter = new ArrayAdapter(this, 17367048, spinnerArray);
            adapter.setDropDownViewResource(17367049);
            sp.setAdapter(adapter);
            if (this.fsexo_v == 1) {
                sp.setSelection(2);
            } else if (this.fsexo_v == 2) {
                sp.setSelection(3);
            } else {
                sp.setSelection(0);
            }
            sp.setVisibility(0);
        }
        if (this.fdist) {
            sp = (Spinner) dialog.findViewById(C0627R.id.sp_dist);
            spinnerArray = new ArrayList();
            spinnerArray.add(getResources().getString(C0627R.string.dist));
            spinnerArray.add(getResources().getString(C0627R.string.todo));
            String u = "km.";
            if (this.pais.equals("US") || this.pais.equals("GB") || this.pais.equals("MM")) {
                u = "mi.";
            }
            spinnerArray.add("<1" + u);
            spinnerArray.add("<2" + u);
            spinnerArray.add("<5" + u);
            spinnerArray.add("<10" + u);
            spinnerArray.add("<20" + u);
            spinnerArray.add("<50" + u);
            spinnerArray.add("<100" + u);
            spinnerArray.add("<200" + u);
            spinnerArray.add("<500" + u);
            adapter = new ArrayAdapter(this, 17367048, spinnerArray);
            adapter.setDropDownViewResource(17367049);
            sp.setAdapter(adapter);
            if (this.fdist_v == 1) {
                sp.setSelection(2);
            } else if (this.fdist_v == 2) {
                sp.setSelection(3);
            } else if (this.fdist_v == 5) {
                sp.setSelection(4);
            } else if (this.fdist_v == 10) {
                sp.setSelection(5);
            } else if (this.fdist_v == 20) {
                sp.setSelection(6);
            } else if (this.fdist_v == 50) {
                sp.setSelection(7);
            } else if (this.fdist_v == 100) {
                sp.setSelection(8);
            } else if (this.fdist_v == 200) {
                sp.setSelection(9);
            } else if (this.fdist_v == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
                sp.setSelection(10);
            } else {
                sp.setSelection(0);
            }
            sp.setVisibility(0);
        }
        return dialog;
    }

    void mostrar_filtros() {
        if (this.fsexo || this.fdist || this.fedad1 || this.fedad2) {
            findViewById(C0627R.id.ll_filtros).setOnClickListener(this);
            findViewById(C0627R.id.ll_filtros).setVisibility(0);
            boolean alguno = false;
            if (!this.fsexo || this.fsexo_v == 0) {
                this.tv_fsexo.setVisibility(8);
            } else {
                if (this.fsexo_v == 1) {
                    this.tv_fsexo.setText(getResources().getString(C0627R.string.hombres));
                } else {
                    this.tv_fsexo.setText(getResources().getString(C0627R.string.mujeres));
                }
                this.tv_fsexo.setVisibility(0);
                alguno = true;
            }
            if (!this.fdist || this.fdist_v == 0) {
                this.tv_fdist.setVisibility(8);
            } else {
                long dist = (long) (this.fdist_v * 1000);
                String unidad = "m.";
                if (this.pais.equals("US") || this.pais.equals("GB") || this.pais.equals("MM")) {
                    if (dist > 1600) {
                        dist = (long) Math.round((float) (dist / 1600));
                        unidad = "mi.";
                    } else {
                        dist = Math.round(((double) dist) * 1.09d);
                        unidad = "yd.";
                    }
                } else if (dist > 999) {
                    dist = (long) Math.round((float) (dist / 1000));
                    unidad = "km.";
                }
                this.tv_fdist.setText(dist + " " + unidad);
                this.tv_fdist.setVisibility(0);
                alguno = true;
            }
            if (!this.fedad1 || this.fedad1_v == 0) {
                this.tv_fedad1.setVisibility(8);
            } else {
                this.tv_fedad1.setText(">" + this.fedad1_v + getResources().getString(C0627R.string.anyos_abrev));
                this.tv_fedad1.setVisibility(0);
                alguno = true;
            }
            if (!this.fedad2 || this.fedad2_v == 0) {
                this.tv_fedad2.setVisibility(8);
            } else {
                this.tv_fedad2.setText("<" + this.fedad2_v + getResources().getString(C0627R.string.anyos_abrev));
                this.tv_fedad2.setVisibility(0);
                alguno = true;
            }
            if (alguno) {
                findViewById(C0627R.id.tv_filtrar).setVisibility(8);
                return;
            } else {
                findViewById(C0627R.id.tv_filtrar).setVisibility(0);
                return;
            }
        }
        findViewById(C0627R.id.ll_filtros).setVisibility(8);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ((LinearLayout) findViewById(C0627R.id.ll_princ)).removeViewAt(0);
        incluir_menu_pre();
        this.dialog_filtros.dismiss();
        this.dialog_filtros = crear_dialog_filtros();
        ((LinearLayout) findViewById(C0627R.id.ll_ad)).removeAllViews();
        if (this.adView != null) {
            try {
                this.adView.destroy();
            } catch (Exception e) {
            }
        }
        this.adView = this.globales.mostrar_banner(this, false);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("es_root", this.es_root);
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
        if (this.gridAdapter != null) {
            this.gridAdapter.notifyDataSetChanged();
        }
        if (this.globales.admob_pos != 0 && this.adView != null) {
            this.adView.resume();
        }
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

    public void onBackPressed() {
        if (this.es_root && !this.atras_pulsado && this.globales.pedir_confirm_exit) {
            this.atras_pulsado = true;
            config.confirmar_exit(this);
            return;
        }
        super.onBackPressed();
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
