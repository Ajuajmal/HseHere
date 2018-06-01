package hse.here2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.plus.PlusShare;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

public class t_and extends Activity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    AndItem ai;
    boolean atras_pulsado = false;
    Bitmap bm_foto;
    String cbtn;
    int[][] coord_fotoacargar;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    File file_global;
    boolean finalizar = false;
    config globales;
    int[] id_fotoacargar;
    int ind_abrir_secc = -1;
    int ind_fotoacargar;
    ImageView[] iv_fotoacargar;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    int n_fotoacargar;
    ProgressBar[] pb_fotoacargar;
    Seccion f63s;
    float scale;
    String url_global;
    View v_abrir_secc;
    int w_max;

    class C07321 implements OnClickListener {
        private String url = t_and.this.ai.fcab_url;

        C07321() {
        }

        public void onClick(View v) {
            t_and.this.tratar_url(this.url);
        }
    }

    class C07332 implements OnClickListener {
        C07332() {
        }

        public void onClick(View v) {
            File file = t_and.this.getFileStreamPath("it_fcab" + t_and.this.ai.fcab_id + ".png");
            if (file != null && file.exists()) {
                Intent i = new Intent(t_and.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + file);
                t_and.this.startActivityForResult(i, 0);
            }
        }
    }

    class C07343 implements OnClickListener {
        private String url = t_and.this.url_global;

        C07343() {
        }

        public void onClick(View v) {
            t_and.this.tratar_url(this.url);
        }
    }

    class C07354 implements OnClickListener {
        private File file = t_and.this.file_global;

        C07354() {
        }

        public void onClick(View v) {
            if (this.file != null && this.file.exists()) {
                Intent i = new Intent(t_and.this, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, "file://" + this.file);
                t_and.this.startActivityForResult(i, 0);
            }
        }
    }

    class C07365 extends ClickableSpan {
        String url = t_and.this.url_global;

        C07365() {
        }

        public void onClick(View textView) {
            t_and.this.tratar_url(this.url);
        }
    }

    class C07376 implements OnItemClickListener {
        C07376() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_and.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_and.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_and.this.globales.menu_a_secciones[position]));
            t_and.this.onClick(view);
        }
    }

    private class cargarfoto extends AsyncTask<String, Void, Byte> {
        String archivo_aux;

        private cargarfoto() {
        }

        protected Byte doInBackground(String... urls) {
            String url_aux;
            URL url;
            if (t_and.this.coord_fotoacargar[t_and.this.ind_fotoacargar][1] == 0) {
                url_aux = "http://srv1.androidcreator.com/srv/imgs/and_items/fcab" + t_and.this.id_fotoacargar[t_and.this.ind_fotoacargar] + ".png";
                this.archivo_aux = "it_fcab" + t_and.this.id_fotoacargar[t_and.this.ind_fotoacargar] + ".png";
            } else {
                url_aux = "http://imgs1.e-droid.net/srv/imgs/and_items/f" + t_and.this.id_fotoacargar[t_and.this.ind_fotoacargar] + ".png";
                this.archivo_aux = "it_f" + t_and.this.id_fotoacargar[t_and.this.ind_fotoacargar] + ".png";
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
                    t_and.this.bm_foto = BitmapFactory.decodeStream(is);
                    try {
                        FileOutputStream fos = t_and.this.openFileOutput(this.archivo_aux, 0);
                        t_and.this.bm_foto.compress(CompressFormat.PNG, 100, fos);
                        fos.close();
                        url = myFileUrl;
                        return Byte.valueOf((byte) 0);
                    } catch (Exception e) {
                        url = myFileUrl;
                        return Byte.valueOf((byte) -1);
                    }
                } catch (IOException e2) {
                    url = myFileUrl;
                    return Byte.valueOf((byte) -1);
                }
            } catch (MalformedURLException e3) {
                return Byte.valueOf((byte) -1);
            }
        }

        protected void onPreExecute() {
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 0) {
                int ind_item = t_and.this.coord_fotoacargar[t_and.this.ind_fotoacargar][0];
                int nfoto = t_and.this.coord_fotoacargar[t_and.this.ind_fotoacargar][1];
                int[] dimen = t_and.this.globales.file_to_iv(this.archivo_aux, t_and.this.iv_fotoacargar[t_and.this.ind_fotoacargar]);
                if (nfoto > 0 && dimen[0] > 0) {
                    if (dimen[0] > t_and.this.w_max) {
                        dimen[1] = (dimen[1] * t_and.this.w_max) / dimen[0];
                        dimen[0] = t_and.this.w_max;
                    }
                    int h = (int) ((((float) dimen[1]) * t_and.this.scale) + 0.5f);
                    t_and.this.iv_fotoacargar[t_and.this.ind_fotoacargar].getLayoutParams().width = (int) ((((float) dimen[0]) * t_and.this.scale) + 0.5f);
                    t_and.this.iv_fotoacargar[t_and.this.ind_fotoacargar].getLayoutParams().height = h;
                }
                t_and.this.pb_fotoacargar[t_and.this.ind_fotoacargar].setVisibility(8);
                t_and.this.iv_fotoacargar[t_and.this.ind_fotoacargar].setVisibility(0);
                Editor editor = t_and.this.getSharedPreferences("sh", 0).edit();
                if (nfoto == 0) {
                    editor.putInt("it" + t_and.this.f63s.and_items_a[ind_item].id + "_fcab_modif", 0);
                } else {
                    editor.putInt("it_f" + t_and.this.id_fotoacargar[t_and.this.ind_fotoacargar] + "_modif", 0);
                }
                editor.commit();
                if (nfoto == 0) {
                    t_and.this.f63s.and_items_a[ind_item].fcab_modif = 0;
                } else {
                    t_and.this.f63s.and_items_a[ind_item].fotos_a[nfoto - 1][1] = 0;
                }
            }
            t_and hse_here2_t_and = t_and.this;
            hse_here2_t_and.ind_fotoacargar++;
            if (t_and.this.ind_fotoacargar < t_and.this.n_fotoacargar) {
                new cargarfoto().execute(new String[0]);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        int i;
        this.globales = (config) getApplicationContext();
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            if (savedInstanceState.containsKey("es_root")) {
                if (savedInstanceState.getBoolean("es_root", false)) {
                    z = true;
                    this.es_root = z;
                }
            }
            z = false;
            this.es_root = z;
        }
        this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.globales.ind_secc_sel_2].c1, this.globales.c_icos);
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_and);
        incluir_menu_pre();
        if (savedInstanceState == null) {
            config hse_here2_config = this.globales;
            z = this.extras != null && this.extras.containsKey("ad_entrar");
            hse_here2_config.toca_int(this, z);
        }
        this.adView = this.globales.mostrar_banner(this, false);
        this.f63s = this.globales.secciones_a[getIntent().getExtras().getInt("idsecc")];
        this.scale = getResources().getDisplayMetrics().density;
        ScrollView sv_and = (ScrollView) findViewById(C0627R.id.ll_princ_scroll);
        LayoutInflater inflater = LayoutInflater.from(this);
        Drawable gd = null;
        if (!this.f63s.c1.equals("")) {
            Drawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.f63s.c1), Color.parseColor("#" + this.f63s.c2)});
        }
        int nfotos = 0;
        for (AndItem andItem : this.f63s.and_items_a) {
            nfotos += andItem.fotos_a.length + 1;
        }
        this.n_fotoacargar = 0;
        this.id_fotoacargar = new int[nfotos];
        this.iv_fotoacargar = new ImageView[nfotos];
        this.pb_fotoacargar = new ProgressBar[nfotos];
        this.coord_fotoacargar = (int[][]) Array.newInstance(Integer.TYPE, new int[]{nfotos, 2});
        for (i = 0; i < this.f63s.and_items_a.length; i++) {
            String f_aux;
            File f_png;
            this.ai = this.f63s.and_items_a[i];
            View v_item = inflater.inflate(C0627R.layout.t_and_item, null);
            this.w_max = 125;
            if (this.ai.txt.equals("")) {
                this.w_max = Callback.DEFAULT_SWIPE_ANIMATION_DURATION;
            }
            int ll_and_combi_gr = 3;
            if ((this.ai.txt_h == 1 && this.ai.fotos_c == 1) || (this.ai.fotos_h == 1 && this.ai.txt_c == 1)) {
                ll_and_combi_gr = 17;
            }
            if (!this.ai.tit1.equals("")) {
                if (this.ai.txt_h == 0 && this.ai.fotos_h == 0) {
                    if (this.ai.tit1_c == 0) {
                        ll_and_combi_gr = 3;
                    } else {
                        ll_and_combi_gr = 17;
                    }
                }
                TextView tv = (TextView) v_item.findViewById(C0627R.id.and_tit1);
                tratar_tv(tv, this.ai.tit1, this.ai.tit1_c, this.ai.tit1_b, this.ai.tit1_i, this.ai.tit1_f, this.ai.tit1_col, this.ai.tit1_u, this.ai.tit1_s, false);
                tv.setVisibility(0);
            }
            if (this.ai.fcab_id != 0 && this.ai.txt_h == 0 && this.ai.fotos_h == 0) {
                if (this.ai.fcab_c == 0) {
                    ll_and_combi_gr = 3;
                } else {
                    ll_and_combi_gr = 17;
                }
            }
            if (!this.ai.tit2.equals("")) {
                if (this.ai.txt_h == 0 && this.ai.fotos_h == 0) {
                    if (this.ai.tit2_c == 0) {
                        ll_and_combi_gr = 3;
                    } else {
                        ll_and_combi_gr = 17;
                    }
                }
                tv = (TextView) v_item.findViewById(C0627R.id.and_tit2);
                tratar_tv(tv, this.ai.tit2, this.ai.tit2_c, this.ai.tit2_b, this.ai.tit2_i, this.ai.tit2_f, this.ai.tit2_col, this.ai.tit2_u, this.ai.tit2_s, false);
                tv.setVisibility(0);
            }
            LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            if (ll_and_combi_gr == 3) {
                layoutParams.gravity = ll_and_combi_gr;
                ((LinearLayout) v_item.findViewById(C0627R.id.ll_and_combi)).setLayoutParams(layoutParams);
            }
            if (!this.ai.txt.equals("")) {
                v_item.findViewById(C0627R.id.ll_and_combi).setVisibility(0);
                tv = (TextView) v_item.findViewById(C0627R.id.and_txt);
                tratar_tv(tv, this.ai.txt, 0, this.ai.txt_b, this.ai.txt_i, this.ai.txt_f, this.ai.txt_col, this.ai.txt_u, this.ai.txt_s, true);
                tv.setVisibility(0);
            }
            boolean pb_inverse = false;
            if (!this.f63s.c1.equals("") && config.esClaro("#" + this.f63s.c1)) {
                pb_inverse = true;
            }
            if (this.ai.fcab_id != 0) {
                ProgressBar pb;
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
                if (this.ai.fcab_c == 0) {
                    layoutParams.gravity = 3;
                } else if (this.ai.fcab_c == 1) {
                    layoutParams.gravity = 17;
                }
                ImageView iv = (ImageView) v_item.findViewById(C0627R.id.and_img_cab);
                if (pb_inverse) {
                    pb = (ProgressBar) v_item.findViewById(C0627R.id.and_pb_cab_inverse);
                } else {
                    pb = (ProgressBar) v_item.findViewById(C0627R.id.and_pb_cab);
                }
                if (VERSION.SDK_INT > 20) {
                    config.progress_color(pb, this.globales.c_icos);
                }
                iv.setLayoutParams(layoutParams);
                pb.setLayoutParams(layoutParams);
                if (!this.ai.fcab_url.equals("")) {
                    iv.setOnClickListener(new C07321());
                } else if (this.ai.fcab_zoom) {
                    iv.setOnClickListener(new C07332());
                }
                if (this.ai.fcab_modif == 1) {
                    this.id_fotoacargar[this.n_fotoacargar] = this.ai.fcab_id;
                    this.iv_fotoacargar[this.n_fotoacargar] = iv;
                    this.pb_fotoacargar[this.n_fotoacargar] = pb;
                    this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                    this.coord_fotoacargar[this.n_fotoacargar][1] = 0;
                    this.n_fotoacargar++;
                    pb.setVisibility(0);
                } else {
                    f_aux = "it_fcab" + this.ai.fcab_id;
                    f_png = getFileStreamPath(f_aux + ".png");
                    if (!f_png.exists()) {
                        getFileStreamPath(f_aux).renameTo(f_png);
                    }
                    this.globales.file_to_iv("it_fcab" + this.ai.fcab_id + ".png", iv);
                    iv.setVisibility(0);
                }
            }
            if (this.ai.fotos_a.length > 0) {
                LinearLayout ll_imgs;
                if (this.ai.fotos_pos == 0) {
                    ll_imgs = (LinearLayout) v_item.findViewById(C0627R.id.ll_and_imgs);
                } else {
                    ll_imgs = (LinearLayout) v_item.findViewById(C0627R.id.ll_and_imgs_r);
                }
                layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                for (int j = 0; j < this.ai.fotos_a.length; j++) {
                    View imageView = new ImageView(this);
                    imageView.setPadding(0, 0, 0, (int) ((10.0f * this.scale) + 0.5f));
                    imageView.setVisibility(8);
                    imageView.setAdjustViewBounds(true);
                    if (!this.ai.fotos_str_a[j].equals("")) {
                        this.url_global = this.ai.fotos_str_a[j];
                        imageView.setOnClickListener(new C07343());
                    } else if (this.ai.fotos_zoom) {
                        this.file_global = getFileStreamPath("it_f" + this.ai.fotos_a[j][0] + ".png");
                        imageView.setOnClickListener(new C07354());
                    }
                    if (pb_inverse) {
                        imageView = new ProgressBar(this, null, 16843399);
                    } else {
                        imageView = new ProgressBar(this);
                    }
                    config.progress_color(pb, this.globales.c_icos);
                    pb.setPadding(0, 0, 0, 10);
                    pb.setVisibility(8);
                    ll_imgs.addView(pb, layoutParams);
                    ll_imgs.addView(imageView, layoutParams);
                    if (this.ai.fotos_a[j][1] == 1) {
                        this.id_fotoacargar[this.n_fotoacargar] = this.ai.fotos_a[j][0];
                        this.iv_fotoacargar[this.n_fotoacargar] = imageView;
                        this.pb_fotoacargar[this.n_fotoacargar] = pb;
                        this.coord_fotoacargar[this.n_fotoacargar][0] = i;
                        this.coord_fotoacargar[this.n_fotoacargar][1] = j + 1;
                        this.n_fotoacargar++;
                        pb.setVisibility(0);
                    } else {
                        f_aux = "it_f" + this.ai.fotos_a[j][0];
                        f_png = getFileStreamPath(f_aux + ".png");
                        if (!f_png.exists()) {
                            getFileStreamPath(f_aux).renameTo(f_png);
                        }
                        int[] dimen = this.globales.file_to_iv("it_f" + this.ai.fotos_a[j][0] + ".png", imageView);
                        if (dimen[0] > 0) {
                            if (dimen[0] > this.w_max) {
                                dimen[1] = (dimen[1] * this.w_max) / dimen[0];
                                dimen[0] = this.w_max;
                            }
                            int h = (int) ((((float) dimen[1]) * this.scale) + 0.5f);
                            imageView.getLayoutParams().width = (int) ((((float) dimen[0]) * this.scale) + 0.5f);
                            imageView.getLayoutParams().height = h;
                        }
                        imageView.setVisibility(0);
                    }
                }
                ll_imgs.setVisibility(0);
                v_item.findViewById(C0627R.id.ll_and_combi).setVisibility(0);
            }
            if (this.f63s.c_peritem == 1 && !this.f63s.c1.equals("")) {
                v_item.setBackgroundDrawable(gd);
            }
            sv_and.addView(v_item);
        }
        if (this.f63s.c_peritem == 0 && !this.f63s.c1.equals("")) {
            findViewById(C0627R.id.ll_princ_scroll).setBackgroundDrawable(gd);
        }
        if (this.n_fotoacargar > 0) {
            this.ind_fotoacargar = 0;
            t_and hse_here2_t_and = this;
            new cargarfoto().execute(new String[0]);
        }
    }

    private void tratar_url(String url) {
        Intent i;
        String tel;
        boolean tratado = false;
        String url_l = url.toLowerCase();
        String[] gdocs_ext = new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "pages", "ai", "psd", "tiff", "dxf", "svg", "eps", "ps", "ttf", "otf", "xps", "zip", "rar"};
        String url_ext = "";
        int pos_aux = url.lastIndexOf(".");
        if (pos_aux != -1) {
            url_ext = url_l.substring(pos_aux);
        }
        url_ext = url_ext.replace(".", "");
        if (!url_l.startsWith("tel:")) {
            if (!url_l.startsWith("http://tel:")) {
                String texte;
                int pos;
                int pos2;
                String email;
                if (!url_l.startsWith("mailto:")) {
                    if (!url_l.startsWith("http://mailto:")) {
                        Intent intent;
                        if (!url_l.startsWith("smsto:")) {
                            if (!url_l.startsWith("http://smsto:")) {
                                String idgo;
                                if (!url_l.startsWith("go:")) {
                                    if (!url_l.startsWith("http://go:")) {
                                        String url_aux;
                                        if (url_l.startsWith("vnd.youtube:")) {
                                            int n = url.indexOf("?");
                                            String idvideo = "";
                                            if (n > 0) {
                                                idvideo = url.substring(12, n);
                                            } else {
                                                idvideo = url.substring(12);
                                            }
                                            url_aux = "http://www.youtube.com/watch?v=" + idvideo;
                                            if (this.f63s.linksexternos == 1) {
                                                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url_aux)));
                                            } else {
                                                i = new Intent(this, t_url.class);
                                                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url_aux);
                                                startActivityForResult(i, 0);
                                            }
                                            tratado = true;
                                        } else {
                                            if (url_l.endsWith(".mp3")) {
                                                intent = new Intent("android.intent.action.VIEW");
                                                intent.setDataAndType(Uri.parse(url), "audio/*");
                                                startActivity(intent);
                                                tratado = true;
                                            } else {
                                                if (!url_l.endsWith(".mp4")) {
                                                    if (!url_l.endsWith(".3gp")) {
                                                        if (this.f63s.linksexternos != 1) {
                                                            if (!url_l.startsWith("rtsp://")) {
                                                                if (!url_l.startsWith("rtmp://")) {
                                                                    if (!url_l.startsWith("market://")) {
                                                                        if (!url_l.endsWith(".apk")) {
                                                                            if (!url_l.startsWith("whatsapp://")) {
                                                                                if (!url_l.endsWith(".m3u")) {
                                                                                    if (!url_l.endsWith(".m3u8")) {
                                                                                        if (Arrays.asList(gdocs_ext).contains(url_ext)) {
                                                                                            if (!url_l.contains("docs.google.com")) {
                                                                                                url_aux = "";
                                                                                                try {
                                                                                                    url_aux = URLEncoder.encode(url, "utf-8");
                                                                                                } catch (Exception e) {
                                                                                                    url_aux = url;
                                                                                                }
                                                                                                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://docs.google.com/viewer?embedded=true&url=" + url_aux)));
                                                                                                tratado = true;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        try {
                                                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                                                            tratado = true;
                                                        } catch (Exception e2) {
                                                        }
                                                    }
                                                }
                                                intent = new Intent("android.intent.action.VIEW");
                                                intent.setDataAndType(Uri.parse(url), "video/*");
                                                startActivity(intent);
                                                tratado = true;
                                            }
                                        }
                                        if (!tratado) {
                                            i = new Intent(this, t_url.class);
                                            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                                            startActivityForResult(i, 0);
                                        }
                                    }
                                }
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
                                } catch (Exception e3) {
                                }
                                for (int i2 = 0; i2 < this.globales.secciones_a.length; i2++) {
                                    if (this.globales.secciones_a[i2].idgo.equalsIgnoreCase(idgo)) {
                                        if ((this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals("")) && (this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
                                            abrir_go(i2);
                                        } else {
                                            if (!(this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals(""))) {
                                                this.mAd_appnext = new RewardedVideo(this, this.globales.appnext_rew_cod);
                                            }
                                            if (!(this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
                                                this.mAd = MobileAds.getRewardedVideoAdInstance(this);
                                            }
                                            this.dialog_cargando = new ProgressDialog(this);
                                            View v = new View(this);
                                            v.setId(i2);
                                            v.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(i2));
                                            this.v_abrir_secc = null;
                                            this.ind_abrir_secc = i2;
                                            if (!this.globales.rewarded(this, v, this.cbtn, this.dialog_cargando, this.mAd, this.mAd_appnext)) {
                                                abrir_go(i2);
                                            }
                                        }
                                        tratado = true;
                                    }
                                }
                                if (!tratado) {
                                    i = new Intent(this, t_url.class);
                                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                                    startActivityForResult(i, 0);
                                }
                            }
                        }
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
                            intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + tel));
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
                        if (!tratado) {
                            i = new Intent(this, t_url.class);
                            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                            startActivityForResult(i, 0);
                        }
                    }
                }
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
                        } catch (Exception e6) {
                        }
                        emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
                    }
                    if (!texte.equals("")) {
                        try {
                            texte = URLDecoder.decode(texte, "UTF-8");
                        } catch (Exception e7) {
                        }
                        emailIntent.putExtra("android.intent.extra.TEXT", texte);
                    }
                    startActivity(Intent.createChooser(emailIntent, getResources().getString(C0627R.string.enviar_email)));
                }
                tratado = true;
                if (!tratado) {
                    i = new Intent(this, t_url.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
                    startActivityForResult(i, 0);
                }
            }
        }
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
        } catch (Exception e8) {
        }
        tratado = true;
        if (!tratado) {
            i = new Intent(this, t_url.class);
            i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, url);
            startActivityForResult(i, 0);
        }
    }

    void abrir_go(int i) {
        startActivityForResult(this.globales.crear_rgi(Integer.valueOf(i), this).f34i, 0);
    }

    private void tratar_tv(TextView tv, String texto, int c, int b, int i, int f, String col, int u, int s, boolean esEnlazable) {
        Typeface tf;
        if (c == 0) {
            tv.setGravity(3);
        } else if (c == 1) {
            tv.setGravity(17);
        }
        if (f == 1) {
            tf = config.tf_monospace;
        } else if (f == 2) {
            tf = config.tf_sans_serif;
        } else if (f == 3) {
            tf = config.tf_serif;
        } else {
            tf = Typeface.DEFAULT;
        }
        if (b == 1 && i == 1) {
            tv.setTypeface(Typeface.create(tf, 3));
        } else if (b == 1 && i == 0) {
            tv.setTypeface(Typeface.create(tf, 1));
        } else if (b == 0 && i == 1) {
            tv.setTypeface(Typeface.create(tf, 2));
        } else {
            tv.setTypeface(tf);
        }
        if (!col.equals("")) {
            tv.setTextColor(Color.parseColor("#" + col));
            tv.setLinkTextColor(Color.parseColor("#" + col));
        }
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
            if (u == 1) {
                ss.setSpan(new UnderlineSpan(), 0, ss.length(), 0);
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
                            ss.setSpan(new C07365(), pos, pos2, 33);
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
        } else if (u == 1) {
            SpannableString content = new SpannableString(texto);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            tv.setText(content);
        } else {
            tv.setText(texto);
        }
        if (s != 0) {
            tv.setTextSize(0, tv.getTextSize() + ((float) (s * 2)));
        }
    }

    public void onClick(View v) {
        if ((this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals("")) && (this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            abrir_secc(v);
            return;
        }
        if (!(this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals(""))) {
            this.mAd_appnext = new RewardedVideo(this, this.globales.appnext_rew_cod);
        }
        if (!(this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            this.mAd = MobileAds.getRewardedVideoAdInstance(this);
        }
        this.dialog_cargando = new ProgressDialog(this);
        this.v_abrir_secc = v;
        this.ind_abrir_secc = -1;
        if (!this.globales.rewarded(this, v, this.cbtn, this.dialog_cargando, this.mAd, this.mAd_appnext)) {
            abrir_secc(v);
        }
    }

    public void abrir_secc(View v) {
        if (v != null) {
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
        } else if (this.ind_abrir_secc != -1) {
            abrir_go(this.ind_abrir_secc);
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
            this.mDrawerList.setOnItemClickListener(new C07376());
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
