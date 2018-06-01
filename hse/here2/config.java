package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.media.TransportMediator;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appnext.ads.fullscreen.FullScreenVideo;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.appnextsdk.API.AppnextAPI;
import com.appnext.appnextsdk.API.AppnextAPI.AppnextAdListener;
import com.appnext.appnextsdk.API.AppnextAd;
import com.appnext.appnextsdk.API.AppnextAdRequest;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class config extends Application implements OnSharedPreferenceChangeListener {
    public static final int BLANCO = Color.parseColor("#FFFFFF");
    public static final int BLANCO_2 = Color.parseColor("#FBFBFB");
    public static final String DOM_CDN = "imgs1.e-droid.net";
    public static final int FILECHOOSER_RESULTCODE = 10;
    public static final int GRIS_CLARO = Color.parseColor("#9E9E9E");
    public static final int GRIS_OSCURO = Color.parseColor("#757575");
    static final int IDAPP = 299914;
    public static final int NEGRO = Color.parseColor("#212121");
    public static final int NEGRO_2 = Color.parseColor("#727272");
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_REG_ID = "registration_id";
    static final int RSS_CONTENT_ENCODED = 6;
    static final int RSS_DATE = 3;
    static final int RSS_DESCRIPTION = 4;
    static final int RSS_IGNORETAG = 0;
    static final int RSS_LINK = 2;
    static final int RSS_OTHER = 5;
    static final int RSS_TITLE = 1;
    public static final String SENDER_ID = "751842291101";
    static final int WIDTH_PORTRAIT = 500;
    static String admob_int_cod;
    static String appnext_cod;
    static String appnext_cod_int_e;
    static String appnext_cod_int_ia;
    static FullScreenVideo appnext_glob_fs = null;
    static Interstitial appnext_glob_int = null;
    static Boolean autoplay;
    static boolean finalizar_app = false;
    static int idfrase_global = 1;
    static int intentos;
    static InterstitialAd interstitial_glob = null;
    static Boolean mute;
    static int our = 0;
    static Typeface tf_monospace;
    static Typeface tf_sans_serif;
    static Typeface tf_serif;
    static int toca_int = 0;
    static int toca_int_chat = 0;
    boolean act_fondomenu;
    int admob_chat_interv;
    String admob_cod;
    int admob_int_interv;
    int admob_intentrar_interv;
    String admob_menu_cod;
    int admob_menu_h;
    int admob_menu_w;
    int admob_pos;
    String admob_pro_cod;
    int admob_pro_h;
    int admob_pro_w;
    String admob_rew_cod;
    AdSize admob_tipo;
    ArrayList<AppnextAd> appnext_ads;
    AppnextAPI appnext_api;
    boolean[] appnext_imgs;
    boolean[] appnext_imgs_g;
    String appnext_menu_cod;
    String appnext_pro_cod;
    String appnext_rew_cod;
    boolean appnext_wide_cargado = false;
    String appnextb_cod;
    boolean banner_mostrando = false;
    boolean banners_enchats;
    boolean bienvenida_nomas_def;
    boolean bienvenida_nomas_mostrar;
    String bienvenida_tit;
    String bienvenida_txt;
    boolean busc_antiguedad;
    boolean busc_cat;
    boolean busc_precio;
    boolean busc_texto;
    String c1;
    String c1_ofic;
    String c1_prods;
    String c1_prods_l;
    String c1_splash;
    String c2;
    String c2_ofic;
    String c2_prods;
    String c2_prods_l;
    String c2_splash;
    String c_antiguedad_prods_l;
    String c_ico_sep_ofic;
    String c_ico_sep_prods;
    String c_icos;
    String c_icos_2_prods;
    String c_icos_ofic;
    String c_icos_prods;
    String c_icos_top;
    String c_ind;
    String c_ir_ofic;
    String c_linea;
    int c_perofic;
    int c_perprod;
    String c_precio_prods_l;
    String c_prods_det;
    String c_secc_activ;
    String c_secc_noactiv;
    String c_sep_ofic;
    String c_sep_prods;
    String c_sep_secc;
    String c_tit_ofic;
    String c_tit_prods;
    String c_tit_prods_l;
    String c_txt_ofic;
    String c_txt_prods;
    String catsnotif_tit;
    int catsnotif_v_bd;
    String divisa;
    String dominio;
    boolean fb_bloqdatos;
    int fb_modo;
    boolean fotos_privados;
    boolean hay_catsnotif;
    boolean hay_icos;
    boolean hay_icosmenu;
    boolean hay_submenu;
    int ico_busc_idsec;
    boolean ico_busc_imgperso;
    int ico_busc_imgperso_v;
    int ico_busc_ord;
    String ico_busc_url;
    int ico_exit_idsec;
    boolean ico_exit_imgperso;
    int ico_exit_imgperso_v;
    int ico_exit_ord;
    String ico_exit_url;
    int ico_notif_idsec;
    boolean ico_notif_imgperso;
    int ico_notif_imgperso_v;
    int ico_notif_ord;
    String ico_notif_url;
    int ico_ofics_idsec;
    boolean ico_ofics_imgperso;
    int ico_ofics_imgperso_v;
    int ico_ofics_ord;
    String ico_ofics_url;
    int ico_share_idsec;
    boolean ico_share_imgperso;
    int ico_share_imgperso_v;
    int ico_share_ord;
    String ico_share_url;
    int[] icos_a;
    boolean icos_pendientes;
    String idfotos_chat_temp = "";
    int ind_secc_buscador = -1;
    int ind_secc_ofics = -1;
    int ind_secc_sel = -1;
    int ind_secc_sel_2 = -1;
    boolean links_enchats;
    ListView mDrawerList;
    MenuAdapter ma;
    int[] menu_a_secciones;
    boolean menu_anim;
    boolean menu_c;
    String menu_c1;
    String menu_c2;
    int menu_estilo;
    boolean menu_icos_izq;
    boolean menu_mostrar_icos;
    boolean menu_mostrar_txt;
    int menu_ncols;
    int menu_padding;
    boolean menu_txt_b;
    String menu_txt_bg;
    boolean menu_txt_c;
    String menu_txt_col;
    int menu_txt_radius;
    String menusl_c;
    int notifdef_idabrir;
    String notifdef_tit;
    String notifdef_txt;
    String notifdef_url;
    int nsecc_mostradas;
    int nsecc_visibles;
    Oficina[] oficinas_a;
    ArrayList<MenuOpcion> opcions;
    boolean ord_antiguedad;
    String ord_def;
    boolean ord_precio;
    boolean ord_texto;
    boolean pedir_confirm_exit;
    int petic_ask_email;
    int petic_ask_nombre;
    int petic_ask_tel;
    String pp_divisa;
    String pp_email;
    boolean privacy_mostrar;
    boolean prods_adaptar_ancho;
    String prods_comprar;
    boolean prods_linksexternos;
    String prods_masinfo;
    boolean prods_masinfo_mostrar;
    String prods_tit;
    boolean radio_artist_b;
    String radio_artist_col;
    int radio_artist_size;
    int radio_pos;
    boolean radio_song_b;
    String radio_song_col;
    int radio_song_size;
    boolean radio_txt_c;
    int rate_int;
    String rate_ko;
    String rate_ok;
    int rate_primeravez;
    String rate_tit;
    String rate_txt;
    int rew_modo;
    String rew_msg;
    int rss_interv;
    int rss_notif;
    String rss_tit;
    Seccion[] secciones_a;
    ArrayList<Integer> secciones_alist;
    boolean sep_secc;
    String share_subject;
    String share_text;
    int slider_h;
    int slider_v;
    int splash_w;
    boolean t_fondomenu;
    int t_ind;
    int t_mas;
    int tipomenu;
    View v_cab;
    int v_fondomenu;
    boolean video_fs;
    boolean video_ls;
    boolean wv_cache;
    boolean wv_cache_limpiada;
    boolean wv_sinconex;
    String wv_sinconex_txt;

    class C06502 implements OnClickListener {
        C06502() {
        }

        public void onClick(View v) {
            v.findViewById(C0627R.id.ad_pb).setVisibility(0);
            config.this.appnext_api.adClicked((AppnextAd) v.getTag(C0627R.id.TAG_ADAPPNEXT));
        }
    }

    class C06513 implements OnClickListener {
        C06513() {
        }

        public void onClick(View v) {
            config.this.appnext_api.privacyClicked((AppnextAd) v.getTag(C0627R.id.TAG_ADAPPNEXT));
        }
    }

    class C06524 implements OnClickListener {
        C06524() {
        }

        public void onClick(View v) {
            config.this.appnext_api.privacyClicked((AppnextAd) v.getTag(C0627R.id.TAG_ADAPPNEXT));
        }
    }

    class C06535 implements OnClickListener {
        C06535() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    class C06546 implements OnClickListener {
        C06546() {
        }

        public void onClick(View v) {
            ((DrawerLayout) ((Activity) v.getContext()).findViewById(C0627R.id.drawer_layout)).openDrawer(3);
        }
    }

    public class MenuAdapter extends ArrayAdapter<MenuOpcion> {
        private Context context;
        private List<MenuOpcion> opcionList;

        public MenuAdapter(List<MenuOpcion> opcionList, Context ctx) {
            super(ctx, C0627R.layout.drawer_list_item, opcionList);
            this.opcionList = opcionList;
            this.context = ctx;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            MenuHolder holder = new MenuHolder();
            if (convertView == null) {
                v = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(C0627R.layout.drawer_list_item, null);
                TextView tv = (TextView) v.findViewById(C0627R.id.texto);
                ImageView img = (ImageView) v.findViewById(C0627R.id.img);
                ProgressBar pb = (ProgressBar) v.findViewById(C0627R.id.pb_img);
                holder.marcador = v.findViewById(C0627R.id.marcador);
                holder.texto = tv;
                holder.img = img;
                holder.pb = pb;
                if (config.this.t_ind == 0) {
                    holder.marcador.getLayoutParams().width = 1;
                    holder.marcador.setBackgroundColor(0);
                } else if (config.this.ind_secc_sel == 900) {
                    holder.marcador.setBackgroundColor(0);
                }
                if (!config.this.hay_icosmenu) {
                    holder.img.setVisibility(8);
                }
                v.setTag(C0627R.id.TAG_IDHOLDER, holder);
            } else {
                holder = (MenuHolder) v.getTag(C0627R.id.TAG_IDHOLDER);
            }
            MenuOpcion o = (MenuOpcion) this.opcionList.get(position);
            if (config.this.ind_secc_sel == config.this.menu_a_secciones[position]) {
                if (config.this.t_ind > 0) {
                    holder.marcador.setBackgroundColor(Color.parseColor("#" + config.this.c_ind));
                }
                holder.texto.setTextColor(Color.parseColor("#" + config.this.c_secc_activ));
            } else {
                holder.marcador.setBackgroundColor(0);
                holder.texto.setTextColor(Color.parseColor("#" + config.this.c_secc_noactiv));
            }
            holder.texto.setText(o.texto);
            if (config.this.hay_icosmenu) {
                if (o.img_cargando) {
                    holder.pb.setVisibility(0);
                    holder.img.setImageDrawable(config.this.getResources().getDrawable(C0627R.drawable.pixel500por1));
                } else {
                    holder.pb.setVisibility(8);
                    if (o.img != null) {
                        holder.img.setImageBitmap(o.img);
                    } else {
                        holder.img.setImageDrawable(config.this.getResources().getDrawable(C0627R.drawable.pixel500por1));
                    }
                }
            }
            return v;
        }
    }

    private static class MenuHolder {
        public ImageView img;
        public View marcador;
        public ProgressBar pb;
        public TextView texto;

        private MenuHolder() {
        }
    }

    public class MenuOpcion {
        public Bitmap img;
        public boolean img_cargando;
        public String texto;
    }

    public class MyOnClickListener implements OnClickListener {
        View[] array1;
        Context f37c;
        Boolean desde_ini;
        View v2;

        public MyOnClickListener(View v2, View[] array1, Context c, Boolean desde_ini) {
            this.array1 = array1;
            this.v2 = v2;
            this.f37c = c;
            this.desde_ini = desde_ini;
        }

        public void onClick(View v) {
            ((LinearLayout) v.getRootView().findViewById(C0627R.id.ll_princ)).removeView(this.v2);
            if (this.desde_ini.booleanValue()) {
                ((preinicio) this.f37c).preiniciar_3();
                return;
            }
            for (View v_aux : this.array1) {
                if (v_aux != null) {
                    v_aux.setVisibility(0);
                }
            }
        }
    }

    private class appnext_cargarimg extends AsyncTask<String, Void, Byte> {
        boolean cargar_grande = false;
        boolean cargar_normal = false;
        boolean cargar_wide = false;
        int ind_cargar = -1;

        protected java.lang.Byte doInBackground(java.lang.String... r13) {
            /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r12 = this;
            r11 = 1;
            r9 = 0;
            r10 = -1;
            r8 = hse.here2.config.this;
            r8 = r8.appnext_ads;
            if (r8 == 0) goto L_0x000d;
        L_0x0009:
            r8 = r12.ind_cargar;
            if (r8 != r10) goto L_0x0012;
        L_0x000d:
            r8 = java.lang.Byte.valueOf(r9);
        L_0x0011:
            return r8;
        L_0x0012:
            r6 = 0;
            r8 = r12.cargar_grande;
            if (r8 == 0) goto L_0x00a7;
        L_0x0017:
            r8 = hse.here2.config.this;
            r8 = r8.appnext_ads;
            r9 = r12.ind_cargar;
            r8 = r8.get(r9);
            r8 = (com.appnext.appnextsdk.API.AppnextAd) r8;
            r7 = r8.getImageURLWide();
        L_0x0027:
            r6 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00b9 }
            r6.<init>(r7);	 Catch:{ MalformedURLException -> 0x00b9 }
            r2 = 0;
            r8 = r6.openConnection();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r0 = r8;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r2 = r0;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = 1;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r2.setDoInput(r8);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r2.setConnectTimeout(r8);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r2.setReadTimeout(r8);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r2.connect();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r5 = r2.getInputStream();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r1 = android.graphics.BitmapFactory.decodeStream(r5);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r12.cargar_grande;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r8 == 0) goto L_0x00c0;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x0052:
            r8 = hse.here2.config.this;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9.<init>();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = "appnext_ad_g";	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = r12.ind_cargar;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.toString();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = 0;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r4 = r8.openFileOutput(r9, r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x006e:
            r8 = r12.cargar_grande;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r8 == 0) goto L_0x00f2;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x0072:
            r8 = hse.here2.config.this;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.appnext_ads;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r12.ind_cargar;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.get(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = (com.appnext.appnextsdk.API.AppnextAd) r8;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.getImageURLWide();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = java.util.Locale.getDefault();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.toLowerCase(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = ".png";	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.endsWith(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r8 == 0) goto L_0x00dd;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x0092:
            r8 = android.graphics.Bitmap.CompressFormat.PNG;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = 100;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r1.compress(r8, r9, r4);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x0099:
            r4.close();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r2 == 0) goto L_0x00a1;
        L_0x009e:
            r2.disconnect();
        L_0x00a1:
            r8 = java.lang.Byte.valueOf(r11);
            goto L_0x0011;
        L_0x00a7:
            r8 = hse.here2.config.this;
            r8 = r8.appnext_ads;
            r9 = r12.ind_cargar;
            r8 = r8.get(r9);
            r8 = (com.appnext.appnextsdk.API.AppnextAd) r8;
            r7 = r8.getImageURL();
            goto L_0x0027;
        L_0x00b9:
            r3 = move-exception;
            r8 = java.lang.Byte.valueOf(r10);
            goto L_0x0011;
        L_0x00c0:
            r8 = hse.here2.config.this;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9.<init>();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = "appnext_ad";	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = r12.ind_cargar;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.append(r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r9.toString();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r10 = 0;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r4 = r8.openFileOutput(r9, r10);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            goto L_0x006e;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x00dd:
            r8 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = 100;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r1.compress(r8, r9, r4);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            goto L_0x0099;
        L_0x00e5:
            r3 = move-exception;
            r8 = -1;
            r8 = java.lang.Byte.valueOf(r8);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r2 == 0) goto L_0x0011;
        L_0x00ed:
            r2.disconnect();
            goto L_0x0011;
        L_0x00f2:
            r8 = hse.here2.config.this;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.appnext_ads;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = r12.ind_cargar;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.get(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = (com.appnext.appnextsdk.API.AppnextAd) r8;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.getImageURL();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = java.util.Locale.getDefault();	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.toLowerCase(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = ".png";	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r8 = r8.endsWith(r9);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            if (r8 == 0) goto L_0x0121;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
        L_0x0112:
            r8 = android.graphics.Bitmap.CompressFormat.PNG;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = 100;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r1.compress(r8, r9, r4);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            goto L_0x0099;
        L_0x011a:
            r8 = move-exception;
            if (r2 == 0) goto L_0x0120;
        L_0x011d:
            r2.disconnect();
        L_0x0120:
            throw r8;
        L_0x0121:
            r8 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r9 = 100;	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            r1.compress(r8, r9, r4);	 Catch:{ Exception -> 0x00e5, all -> 0x011a }
            goto L_0x0099;
            */
            throw new UnsupportedOperationException("Method not decompiled: hse.here2.config.appnext_cargarimg.doInBackground(java.lang.String[]):java.lang.Byte");
        }

        appnext_cargarimg(boolean cargar_normal, boolean cargar_wide) {
            this.cargar_normal = cargar_normal;
            this.cargar_wide = cargar_wide;
        }

        protected void onPreExecute() {
            if (config.this.appnext_ads != null) {
                int i = 0;
                while (i < config.this.appnext_ads.size()) {
                    if (!this.cargar_wide || config.this.appnext_imgs_g[i] || ((AppnextAd) config.this.appnext_ads.get(i)).getImageURLWide().equals("")) {
                        if (this.cargar_normal && !config.this.appnext_imgs[i] && !((AppnextAd) config.this.appnext_ads.get(i)).getImageURL().equals("")) {
                            this.ind_cargar = i;
                            this.cargar_grande = false;
                            break;
                        }
                        i++;
                    } else {
                        this.ind_cargar = i;
                        this.cargar_grande = true;
                        break;
                    }
                }
            }
            if (this.ind_cargar == -1) {
                cancel(false);
            }
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() == (byte) 1) {
                if (this.cargar_grande) {
                    config.this.appnext_imgs_g[this.ind_cargar] = true;
                    config.this.appnext_wide_cargado = true;
                } else {
                    config.this.appnext_imgs[this.ind_cargar] = true;
                }
                new appnext_cargarimg(this.cargar_normal, this.cargar_wide).execute(new String[0]);
            }
        }
    }

    public static class modif_usuchat extends AsyncTask<String, Void, Byte> {
        String campo;
        String codigo;
        int idchat;
        int idusu;
        int modo;
        String resultado;

        modif_usuchat(int idusu, String codigo, int idchat, String campo, int modo) {
            this.idusu = idusu;
            this.codigo = codigo;
            this.idchat = idchat;
            this.campo = campo;
            this.modo = modo;
        }

        protected Byte doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, StatusCodes.AUTH_DISABLED);
                HttpConnectionParams.setSoTimeout(httpParameters, 60000);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost postRequest = new HttpPost("http://srv1.androidcreator.com/srv/modif_usuchats.php?idusu=" + this.idusu + "&c=" + this.codigo + "&idchat=" + this.idchat + "&campo=" + this.campo + "&modo=" + this.modo);
                postRequest.setEntity(new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE));
                postRequest.setHeader("User-Agent", "Android Vinebre Software");
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.execute(postRequest).getEntity().getContent(), "UTF-8"));
                StringBuilder s = new StringBuilder();
                while (true) {
                    String sResponse = reader.readLine();
                    if (sResponse == null) {
                        break;
                    }
                    s = s.append(sResponse);
                }
                if (s.indexOf("ANDROID:OK") == -1) {
                    return Byte.valueOf((byte) 0);
                }
                this.resultado = s.toString();
                return Byte.valueOf((byte) 1);
            } catch (Exception e) {
                return Byte.valueOf((byte) 0);
            }
        }

        protected void onPostExecute(Byte result) {
            if (result.byteValue() != (byte) 1) {
            }
        }
    }

    class C11851 implements AppnextAdListener {
        C11851() {
        }

        public void onError(String error) {
        }

        public void onAdsLoaded(ArrayList<AppnextAd> ads) {
            config.this.appnext_imgs = new boolean[5];
            config.this.appnext_imgs_g = new boolean[5];
            config.this.appnext_ads = ads;
            new appnext_cargarimg(true, false).execute(new String[0]);
        }
    }

    class C11867 extends AdListener {
        C11867() {
        }

        public void onAdFailedToLoad(int errorCode) {
            config.toca_int = 0;
            config.toca_int_chat = 0;
        }
    }

    class C11878 implements OnAdLoaded {
        C11878() {
        }

        public void adLoaded() {
            config.appnext_glob_int.showAd();
        }
    }

    class C11889 implements OnAdError {
        C11889() {
        }

        public void adError(String error) {
            config.appnext_glob_int = null;
        }
    }

    AdView mostrar_banner(Context c, boolean sinadmob) {
        this.banner_mostrando = false;
        AdView adView = null;
        if (!(this.admob_pos == 0 || sinadmob)) {
            int mostrar = 0;
            if (!sinadmob && !this.admob_cod.equals("") && this.appnext_ads != null && this.appnext_ads.size() > 0) {
                mostrar = new Random().nextInt(2) + 1;
            } else if (!sinadmob && !this.admob_cod.equals("")) {
                mostrar = 1;
            } else if (this.appnext_ads != null && this.appnext_ads.size() > 0) {
                mostrar = 2;
            }
            if (mostrar == 1) {
                adView = new AdView(c);
                AdSize size_aux = this.admob_tipo;
                if (this.admob_tipo == AdSize.LARGE_BANNER && getResources().getConfiguration().orientation == 2) {
                    size_aux = AdSize.SMART_BANNER;
                }
                adView.setAdSize(size_aux);
                adView.setAdUnitId(this.admob_cod);
                if (this.admob_pos == 1) {
                    adView.setTag(Integer.valueOf(C0627R.id.TAG_ESBANNER));
                    ((LinearLayout) this.v_cab).addView(adView);
                } else {
                    ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_ad)).addView(adView);
                    ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_ad)).setVisibility(0);
                }
                this.banner_mostrando = true;
                adView.loadAd(new Builder().build());
            } else if (mostrar == 2) {
                appnext_mostrar(c);
            }
        }
        return adView;
    }

    void obtener_appnext() {
        String cod_aux = "";
        if (this.appnextb_cod != null && !this.appnextb_cod.equals("")) {
            cod_aux = this.appnextb_cod;
        } else if (this.appnext_pro_cod != null && !this.appnext_pro_cod.equals("")) {
            cod_aux = this.appnext_pro_cod;
        } else if (!(this.appnext_menu_cod == null || this.appnext_menu_cod.equals(""))) {
            cod_aux = this.appnext_menu_cod;
        }
        if (!cod_aux.equals("")) {
            this.appnext_api = new AppnextAPI(getApplicationContext(), cod_aux);
            this.appnext_api.setAdListener(new C11851());
            this.appnext_api.loadAds(new AppnextAdRequest().setCount(5));
        }
    }

    protected void appnext_mostrar(Context c) {
        appnext_mostrar_2(c, 1, 1, -1, -1, null, null, false, false, -1, false);
    }

    @TargetApi(13)
    protected void appnext_mostrar_2(Context c, int formato, int formato2, int ind_ad, int w_param, String c_txt, String c_fondo, boolean menu_cen, boolean txt_cen, int redondeo, boolean negrita) {
        if (this.appnext_ads != null && this.appnext_ads.size() > 0) {
            int width;
            int ind;
            FrameLayout adVin;
            Display display = ((Activity) c).getWindowManager().getDefaultDisplay();
            if (VERSION.SDK_INT >= 13) {
                Point size = new Point();
                display.getSize(size);
                width = size.x;
            } else {
                width = display.getWidth();
            }
            if (ind_ad == -1) {
                ind = new Random().nextInt(((this.appnext_ads.size() - 1) - 0) + 1) + 0;
            } else {
                ind = ind_ad;
            }
            AppnextAd ad = (AppnextAd) this.appnext_ads.get(ind);
            LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
            if (formato == 1) {
                adVin = (FrameLayout) inflater.inflate(C0627R.layout.advin, null);
            } else {
                adVin = (FrameLayout) inflater.inflate(C0627R.layout.advin_formato2, null);
            }
            if (VERSION.SDK_INT > 20) {
                if (formato == 1) {
                    progress_color((ProgressBar) adVin.findViewById(C0627R.id.ad_pb), "757575");
                } else if (!(c_txt == null || c_txt == "")) {
                    if (esClaro("#" + c_txt)) {
                        progress_color((ProgressBar) adVin.findViewById(C0627R.id.ad_pb), "FBFBFB");
                    } else {
                        progress_color((ProgressBar) adVin.findViewById(C0627R.id.ad_pb), "727272");
                    }
                }
            }
            Typeface regular = Typeface.defaultFromStyle(0);
            TextView ad_tit = (TextView) adVin.findViewById(C0627R.id.ad_tit);
            ad_tit.setText(ad.getAdTitle());
            if (formato == 1) {
                ad_tit.setTypeface(regular, 1);
                ((TextView) adVin.findViewById(C0627R.id.ad_descr)).setTypeface(regular);
                ((TextView) adVin.findViewById(C0627R.id.ad_descr)).setText(ad.getAdDescription());
                String rank = ad.getStoreRating();
                if (rank != null && rank.length() == 3) {
                    int i = 0;
                    int rank_2 = 0;
                    try {
                        i = Integer.parseInt(rank.substring(0, 1));
                        rank_2 = Integer.parseInt(rank.substring(2, 3));
                    } catch (Exception e) {
                    }
                    if (rank_2 < 3) {
                        rank_2 = 0;
                    } else if (rank_2 > 7) {
                        rank_2 = 0;
                        i++;
                    } else {
                        rank_2 = 5;
                    }
                    if (i > 0 && i < 6) {
                        adVin.findViewById(C0627R.id.rank1).setVisibility(0);
                        if (i > 1) {
                            adVin.findViewById(C0627R.id.rank2).setVisibility(0);
                            if (i > 2) {
                                adVin.findViewById(C0627R.id.rank3).setVisibility(0);
                                if (i > 3) {
                                    adVin.findViewById(C0627R.id.rank4).setVisibility(0);
                                    if (i > 4) {
                                        adVin.findViewById(C0627R.id.rank5).setVisibility(0);
                                    } else if (rank_2 == 5) {
                                        adVin.findViewById(C0627R.id.rank5_medio).setVisibility(0);
                                    } else {
                                        adVin.findViewById(C0627R.id.rank5_g).setVisibility(0);
                                    }
                                } else {
                                    if (rank_2 == 5) {
                                        adVin.findViewById(C0627R.id.rank4_medio).setVisibility(0);
                                    } else {
                                        adVin.findViewById(C0627R.id.rank4_g).setVisibility(0);
                                    }
                                    adVin.findViewById(C0627R.id.rank5_g).setVisibility(0);
                                }
                            } else {
                                if (rank_2 == 5) {
                                    adVin.findViewById(C0627R.id.rank3_medio).setVisibility(0);
                                } else {
                                    adVin.findViewById(C0627R.id.rank3_g).setVisibility(0);
                                }
                                adVin.findViewById(C0627R.id.rank4_g).setVisibility(0);
                                adVin.findViewById(C0627R.id.rank5_g).setVisibility(0);
                            }
                        } else {
                            if (rank_2 == 5) {
                                adVin.findViewById(C0627R.id.rank2_medio).setVisibility(0);
                            } else {
                                adVin.findViewById(C0627R.id.rank2_g).setVisibility(0);
                            }
                            adVin.findViewById(C0627R.id.rank3_g).setVisibility(0);
                            adVin.findViewById(C0627R.id.rank4_g).setVisibility(0);
                            adVin.findViewById(C0627R.id.rank5_g).setVisibility(0);
                        }
                    }
                }
            } else {
                try {
                    ad_tit.setTypeface(Typeface.MONOSPACE);
                } catch (Exception e2) {
                }
            }
            if (this.appnext_imgs[ind]) {
                try {
                    FileInputStream fis = openFileInput("appnext_ad" + ind);
                    Bitmap bm_ad = BitmapFactory.decodeFileDescriptor(fis.getFD());
                    fis.close();
                    ((ImageView) adVin.findViewById(C0627R.id.ad_img)).setImageBitmap(bm_ad);
                    ((ImageView) adVin.findViewById(C0627R.id.ad_img)).setVisibility(0);
                } catch (FileNotFoundException e3) {
                } catch (IOException e4) {
                }
            }
            adVin.setTag(C0627R.id.TAG_ADAPPNEXT, ad);
            adVin.setOnClickListener(new C06502());
            if (formato == 1) {
                adVin.findViewById(C0627R.id.appnext_logo).setTag(C0627R.id.TAG_ADAPPNEXT, ad);
                adVin.findViewById(C0627R.id.appnext_logo).setOnClickListener(new C06513());
                int height_dp = 0;
                if (formato2 == 1) {
                    if (this.admob_pos == 1) {
                        adVin.setTag(Integer.valueOf(C0627R.id.TAG_ESBANNER));
                        ((LinearLayout) this.v_cab).addView(adVin);
                    } else {
                        ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_ad)).addView(adVin);
                        ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_ad)).setVisibility(0);
                    }
                    this.banner_mostrando = true;
                    height_dp = 60;
                    if (getResources().getConfiguration().orientation == 2) {
                        height_dp = 45;
                        ((TextView) adVin.findViewById(C0627R.id.ad_descr)).setMaxLines(1);
                    } else {
                        if (this.admob_tipo == AdSize.LARGE_BANNER) {
                            height_dp = 100;
                            ((TextView) adVin.findViewById(C0627R.id.ad_descr)).setTextSize(1, 16.0f);
                        }
                    }
                } else if (formato2 == 2) {
                    height_dp = 80;
                    ((Activity) c).findViewById(C0627R.id.ll_nat).setVisibility(0);
                    ((Activity) c).findViewById(C0627R.id.tv_appreco).setVisibility(0);
                    ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_nat_ad)).removeAllViews();
                    ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_nat_ad)).addView(adVin);
                }
                adVin.getLayoutParams().width = width;
                adVin.getLayoutParams().height = (int) ((((float) height_dp) * getResources().getDisplayMetrics().density) + 0.5f);
                this.appnext_api.adImpression(ad);
                return;
            }
            if (ind_ad == 0) {
                ((Activity) c).findViewById(C0627R.id.ll_nat).setVisibility(0);
                ((Activity) c).findViewById(C0627R.id.ll_appsreco).setVisibility(0);
                ((Activity) c).findViewById(C0627R.id.appnext_logo).setTag(C0627R.id.TAG_ADAPPNEXT, ad);
                ((Activity) c).findViewById(C0627R.id.appnext_logo).setOnClickListener(new C06524());
            }
            if (menu_cen) {
                ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_nat)).setGravity(17);
            }
            ((LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_nat)).addView(adVin);
            float width_dp = px_to_dp(c, (float) w_param);
            if (width_dp < 200.0f) {
                width_dp = 200.0f;
            }
            adVin.getLayoutParams().width = (int) ((getResources().getDisplayMetrics().density * width_dp) + 0.5f);
            adVin.getLayoutParams().height = (int) ((((float) 44) * getResources().getDisplayMetrics().density) + 0.5f);
            if (!(c_txt == null || c_txt.equals(""))) {
                ad_tit.setTextColor(Color.parseColor("#" + c_txt));
            }
            if (negrita) {
                ad_tit.setTypeface(ad_tit.getTypeface(), 1);
            }
            if (txt_cen) {
                ad_tit.setGravity(17);
            }
            if (c_fondo.equals("")) {
                ad_tit.setBackgroundDrawable(null);
            } else {
                ((GradientDrawable) ad_tit.getBackground()).setColor(Color.parseColor("#" + c_fondo));
                ((GradientDrawable) ad_tit.getBackground()).setCornerRadius((float) redondeo);
            }
            LayoutParams params = (LayoutParams) adVin.getLayoutParams();
            params.setMargins(0, 0, 0, (int) ((10.0f * getResources().getDisplayMetrics().density) + 0.5f));
            adVin.setLayoutParams(params);
            this.appnext_api.adImpression(ad);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (this.tipomenu == 1 && this.ma != null && key.equals("ico_cargado")) {
            this.ma.notifyDataSetChanged();
        } else if (this.tipomenu == 1 && this.mDrawerList != null && this.mDrawerList.getHeaderViewsCount() > 0 && key.equals("slider_v_act")) {
            RelativeLayout ll_aux = (RelativeLayout) this.mDrawerList.findViewById(C0627R.id.ll_drawer);
            ImageView iv_d = (ImageView) ll_aux.findViewById(C0627R.id.iv_drawer);
            try {
                FileInputStream fis = openFileInput("slider_header");
                Bitmap bm_aux = BitmapFactory.decodeFileDescriptor(fis.getFD());
                fis.close();
                iv_d.setImageBitmap(bm_aux);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.slider_h > 0) {
                ll_aux.setLayoutParams(new AbsListView.LayoutParams(-1, this.slider_h));
            }
            ll_aux.findViewById(C0627R.id.pb_drawer).setVisibility(8);
            Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, C0627R.anim.fade);
            iv_d.setVisibility(0);
            iv_d.startAnimation(myFadeInAnimation);
        }
    }

    void recuperar_vars() {
        SharedPreferences settings = getSharedPreferences("sh", 0);
        String[] idseccs_a = settings.getString("idseccs", "").split(",");
        String idofics = settings.getString("idofics", "");
        String[] idofics_a = new String[0];
        if (!idofics.equals("")) {
            idofics_a = idofics.split(",");
        }
        crear_globales(idseccs_a, idofics_a, null, null);
        this.ind_secc_sel = settings.getInt("ind_secc_sel", 0);
        this.ind_secc_sel_2 = settings.getInt("ind_secc_sel_2", 0);
    }

    @TargetApi(13)
    int incluir_menu(Context c) {
        int width_disponible;
        boolean es_v;
        ImageView iv;
        Integer i;
        try {
            ((Activity) c).getWindow().setFlags(128, 128);
        } catch (Exception e) {
        }
        if (this.c1 == null) {
            recuperar_vars();
        }
        if (VERSION.SDK_INT > 20) {
            aplicar_color_top((Activity) c, this.c1);
        }
        float scale = getResources().getDisplayMetrics().density;
        int w_btn_mas = (int) ((31.0f * scale) + 0.5f);
        int w_icon = (int) ((48.0f * scale) + 0.5f);
        int h_max_icohome = (int) ((48.0f * scale) + 0.5f);
        int w_max_icohome = (int) ((140.0f * scale) + 0.5f);
        DrawerLayout mDrawerLayout = (DrawerLayout) ((Activity) c).findViewById(C0627R.id.drawer_layout);
        if (!(this.tipomenu == 1 || mDrawerLayout == null)) {
            mDrawerLayout.setDrawerLockMode(1);
        }
        LinearLayout ll_princ = (LinearLayout) ((Activity) c).findViewById(C0627R.id.ll_princ);
        Display display = ((Activity) c).getWindowManager().getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width_disponible = size.x;
        } else {
            width_disponible = display.getWidth();
        }
        LayoutInflater inflater = LayoutInflater.from(c);
        Drawable gradientDrawable = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + this.c2)});
        GradientDrawable gd_cab_bottom = new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.c1), Color.parseColor("#" + this.c2)});
        if (this.tipomenu != 0 || width_disponible >= ((int) ((500.0f * getResources().getDisplayMetrics().density) + 0.5f))) {
            es_v = false;
            this.v_cab = inflater.inflate(C0627R.layout.cab, null);
            this.v_cab.setBackgroundDrawable(gradientDrawable);
        } else {
            es_v = true;
            this.v_cab = inflater.inflate(C0627R.layout.cab_peque, null);
            this.v_cab.findViewById(C0627R.id.cab_peque_top).setBackgroundDrawable(gradientDrawable);
            this.v_cab.findViewById(C0627R.id.cab_peque_bottom).setBackgroundDrawable(gd_cab_bottom);
        }
        ll_princ.addView(this.v_cab, 0);
        this.v_cab.findViewById(C0627R.id.hr_cab).setBackgroundColor(Color.parseColor("#" + this.c_linea));
        this.icos_a = new int[4];
        int icos_a_ind = 0;
        int ico = 0;
        int ico_rl = 0;
        boolean hay_icos = false;
        if (this.ico_ofics_ord > 0 && (this.oficinas_a.length > 0 || this.ico_ofics_idsec > 0)) {
            if (this.ico_ofics_ord == 1) {
                ico = C0627R.id.ico_1;
                ico_rl = C0627R.id.ico_rl_1;
            } else if (this.ico_ofics_ord == 2) {
                ico = C0627R.id.ico_2;
                ico_rl = C0627R.id.ico_rl_2;
            } else if (this.ico_ofics_ord == 3) {
                ico = C0627R.id.ico_3;
                ico_rl = C0627R.id.ico_rl_3;
            } else if (this.ico_ofics_ord == 4) {
                ico = C0627R.id.ico_4;
                ico_rl = C0627R.id.ico_rl_4;
            }
            iv = (ImageView) ((Activity) c).findViewById(ico);
            if (this.ico_ofics_imgperso) {
                file_to_iv("ico_ofics", iv);
            } else {
                Drawable d = ((Activity) c).getResources().getDrawable(C0627R.drawable.enviar);
                d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                iv.setImageDrawable(d);
            }
            iv.setId(this.ico_ofics_ord + 1000);
            iv.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.ico_ofics_ord + 1000));
            this.icos_a[0] = this.ico_ofics_ord + 1000;
            icos_a_ind = 0 + 1;
            ((Activity) c).findViewById(ico_rl).setVisibility(0);
            if (!es_v) {
                width_disponible -= w_icon;
            }
            hay_icos = true;
        }
        if (this.ico_share_ord > 0) {
            if (this.ico_share_ord == 1) {
                ico = C0627R.id.ico_1;
                ico_rl = C0627R.id.ico_rl_1;
            } else if (this.ico_share_ord == 2) {
                ico = C0627R.id.ico_2;
                ico_rl = C0627R.id.ico_rl_2;
            } else if (this.ico_share_ord == 3) {
                ico = C0627R.id.ico_3;
                ico_rl = C0627R.id.ico_rl_3;
            } else if (this.ico_share_ord == 4) {
                ico = C0627R.id.ico_4;
                ico_rl = C0627R.id.ico_rl_4;
            }
            iv = (ImageView) ((Activity) c).findViewById(ico);
            if (this.ico_share_imgperso) {
                file_to_iv("ico_share", iv);
            } else {
                d = ((Activity) c).getResources().getDrawable(C0627R.drawable.compartir);
                d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                iv.setImageDrawable(d);
            }
            iv.setId(this.ico_share_ord + 1000);
            iv.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.ico_share_ord + 1000));
            this.icos_a[icos_a_ind] = this.ico_share_ord + 1000;
            icos_a_ind++;
            ((Activity) c).findViewById(ico_rl).setVisibility(0);
            if (!es_v) {
                width_disponible -= w_icon;
            }
            hay_icos = true;
        }
        if (this.ico_busc_ord > 0) {
            boolean poner_icono_aux = false;
            if (this.ico_busc_idsec > 0) {
                poner_icono_aux = true;
            } else {
                for (Seccion seccion : this.secciones_a) {
                    if (seccion.tipo == 5) {
                        poner_icono_aux = true;
                        break;
                    }
                }
            }
            if (poner_icono_aux) {
                if (this.ico_busc_ord == 1) {
                    ico = C0627R.id.ico_1;
                    ico_rl = C0627R.id.ico_rl_1;
                } else if (this.ico_busc_ord == 2) {
                    ico = C0627R.id.ico_2;
                    ico_rl = C0627R.id.ico_rl_2;
                } else if (this.ico_busc_ord == 3) {
                    ico = C0627R.id.ico_3;
                    ico_rl = C0627R.id.ico_rl_3;
                } else if (this.ico_busc_ord == 4) {
                    ico = C0627R.id.ico_4;
                    ico_rl = C0627R.id.ico_rl_4;
                }
                iv = (ImageView) ((Activity) c).findViewById(ico);
                if (this.ico_busc_imgperso) {
                    file_to_iv("ico_busc", iv);
                } else {
                    d = ((Activity) c).getResources().getDrawable(C0627R.drawable.buscar);
                    d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                    iv.setImageDrawable(d);
                }
                iv.setId(this.ico_busc_ord + 1000);
                iv.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.ico_busc_ord + 1000));
                this.icos_a[icos_a_ind] = this.ico_busc_ord + 1000;
                icos_a_ind++;
                ((Activity) c).findViewById(ico_rl).setVisibility(0);
                if (!es_v) {
                    width_disponible -= w_icon;
                }
                hay_icos = true;
            }
        }
        if (this.ico_exit_ord > 0) {
            if (this.ico_exit_ord == 1) {
                ico = C0627R.id.ico_1;
                ico_rl = C0627R.id.ico_rl_1;
            } else if (this.ico_exit_ord == 2) {
                ico = C0627R.id.ico_2;
                ico_rl = C0627R.id.ico_rl_2;
            } else if (this.ico_exit_ord == 3) {
                ico = C0627R.id.ico_3;
                ico_rl = C0627R.id.ico_rl_3;
            } else if (this.ico_exit_ord == 4) {
                ico = C0627R.id.ico_4;
                ico_rl = C0627R.id.ico_rl_4;
            }
            iv = (ImageView) ((Activity) c).findViewById(ico);
            if (this.ico_exit_imgperso) {
                file_to_iv("ico_exit", iv);
            } else {
                d = ((Activity) c).getResources().getDrawable(C0627R.drawable.salir);
                d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                iv.setImageDrawable(d);
            }
            iv.setId(this.ico_exit_ord + 1000);
            iv.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.ico_exit_ord + 1000));
            this.icos_a[icos_a_ind] = this.ico_exit_ord + 1000;
            icos_a_ind++;
            ((Activity) c).findViewById(ico_rl).setVisibility(0);
            if (!es_v) {
                width_disponible -= w_icon;
            }
            hay_icos = true;
        }
        if (this.ico_notif_ord > 0) {
            if (this.ico_notif_ord == 1) {
                ico = C0627R.id.ico_1;
                ico_rl = C0627R.id.ico_rl_1;
            } else if (this.ico_notif_ord == 2) {
                ico = C0627R.id.ico_2;
                ico_rl = C0627R.id.ico_rl_2;
            } else if (this.ico_notif_ord == 3) {
                ico = C0627R.id.ico_3;
                ico_rl = C0627R.id.ico_rl_3;
            } else if (this.ico_notif_ord == 4) {
                ico = C0627R.id.ico_4;
                ico_rl = C0627R.id.ico_rl_4;
            }
            iv = (ImageView) ((Activity) c).findViewById(ico);
            if (this.ico_notif_imgperso) {
                file_to_iv("ico_notif", iv);
            } else {
                d = ((Activity) c).getResources().getDrawable(C0627R.drawable.notif_icotop);
                d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                iv.setImageDrawable(d);
            }
            iv.setId(this.ico_notif_ord + 1000);
            iv.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.ico_notif_ord + 1000));
            this.icos_a[icos_a_ind] = this.ico_notif_ord + 1000;
            icos_a_ind++;
            ((Activity) c).findViewById(ico_rl).setVisibility(0);
            mostrar_notif_noleidas(c);
            if (!es_v) {
                width_disponible -= w_icon;
            }
            hay_icos = true;
        }
        ImageView iv_icohome = (ImageView) ((Activity) c).findViewById(C0627R.id.icohome);
        int[] dimen = file_to_iv("icohome", iv_icohome);
        boolean hay_icohome = (dimen[0] == 0 && dimen[1] == 0) ? false : true;
        if (!es_v) {
            int dimen1_dpi = (int) ((((float) dimen[1]) * scale) + 0.5f);
            int w_icohome = (int) ((((float) dimen[0]) * scale) + 0.5f);
            if (dimen1_dpi > h_max_icohome) {
                w_icohome = (w_icohome * h_max_icohome) / dimen1_dpi;
            }
            width_disponible -= Math.min(w_icohome, w_max_icohome);
        } else if (!hay_icos && dimen[0] == 0 && dimen[1] == 0) {
            this.v_cab.findViewById(C0627R.id.cab_peque_top).setVisibility(8);
        }
        this.nsecc_mostradas = 0;
        if (this.tipomenu == 0) {
            LinearLayout ll_secc = (LinearLayout) ((Activity) c).findViewById(C0627R.id.Secciones);
            int ind_ultsecc_visible = -1;
            for (i = Integer.valueOf(0); i.intValue() < this.secciones_a.length; i = Integer.valueOf(i.intValue() + 1)) {
                if (!this.secciones_a[i.intValue()].oculta) {
                    ind_ultsecc_visible = i.intValue();
                }
            }
            int width_usado = 0;
            for (i = Integer.valueOf(0); i.intValue() <= ind_ultsecc_visible; i = Integer.valueOf(i.intValue() + 1)) {
                if (!this.secciones_a[i.intValue()].oculta) {
                    View v_secc_item = inflater.inflate(C0627R.layout.seccion_item, null);
                    if (i.intValue() > 0 && this.sep_secc) {
                        v_secc_item.findViewById(C0627R.id.sep_cab).setBackgroundColor(Color.parseColor("#" + this.c_sep_secc));
                        v_secc_item.findViewById(C0627R.id.sep_cab).setVisibility(0);
                    }
                    TextView tv = (TextView) v_secc_item.findViewById(C0627R.id.tv_seccion);
                    if (i.intValue() == this.ind_secc_sel) {
                        tv.setTextColor(Color.parseColor("#" + this.c_secc_activ));
                        if (this.t_ind > 0 && this.t_ind < 3) {
                            if (this.t_ind == 1) {
                                d = getResources().getDrawable(C0627R.drawable.secc_sel_tri);
                            } else {
                                d = getResources().getDrawable(C0627R.drawable.secc_sel_cuad);
                            }
                            d.setColorFilter(Color.parseColor("#" + this.c_ind), Mode.MULTIPLY);
                            ((ImageView) v_secc_item.findViewById(C0627R.id.iv_secc_sel)).setImageDrawable(d);
                            v_secc_item.findViewById(C0627R.id.iv_secc_sel).setVisibility(0);
                        } else if (this.t_ind == 3) {
                            v_secc_item.findViewById(C0627R.id.iv_secc_sel_2).setBackgroundColor(Color.parseColor("#" + this.c_ind));
                            v_secc_item.findViewById(C0627R.id.iv_secc_sel_2).setVisibility(0);
                        }
                    } else {
                        tv.setTextColor(Color.parseColor("#" + this.c_secc_noactiv));
                    }
                    tv.setText(this.secciones_a[i.intValue()].titulo);
                    ll_secc.addView(v_secc_item);
                    v_secc_item.measure(0, 0);
                    if (i.intValue() == ind_ultsecc_visible) {
                        w_btn_mas = 0;
                    }
                    if (this.nsecc_mostradas == 0) {
                        while ((v_secc_item.getMeasuredWidth() + width_usado) + w_btn_mas > width_disponible) {
                            tv.setText(tv.getText().subSequence(0, tv.getText().length() - 1).toString());
                            v_secc_item.measure(0, 0);
                        }
                    }
                    if ((v_secc_item.getMeasuredWidth() + width_usado) + w_btn_mas > width_disponible) {
                        tv.setVisibility(8);
                        v_secc_item.findViewById(C0627R.id.iv_secc_sel).setVisibility(8);
                        v_secc_item.findViewById(C0627R.id.iv_secc_sel_2).setVisibility(8);
                        ImageView iv_menu_mas = (ImageView) v_secc_item.findViewById(C0627R.id.iv_menu_mas);
                        iv_menu_mas.setId(9999);
                        iv_menu_mas.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(9999));
                        if (this.t_mas == 1) {
                            d = getResources().getDrawable(C0627R.drawable.mas_puntitos);
                        } else if (this.t_mas == 2) {
                            d = getResources().getDrawable(C0627R.drawable.mas_rayitas);
                        } else {
                            d = getResources().getDrawable(C0627R.drawable.mas);
                        }
                        d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
                        iv_menu_mas.setImageDrawable(d);
                        iv_menu_mas.setVisibility(0);
                        if (!(ind_ultsecc_visible != -1 || hay_icos || hay_icohome)) {
                            if (es_v) {
                                this.v_cab.findViewById(C0627R.id.cab_grande).setVisibility(8);
                                this.v_cab.findViewById(C0627R.id.hr_cab).setVisibility(8);
                            } else {
                                this.v_cab.findViewById(C0627R.id.cab_peque_top).setVisibility(8);
                                this.v_cab.findViewById(C0627R.id.cab_peque_bottom).setVisibility(8);
                            }
                        }
                    } else {
                        tv.setId(i.intValue());
                        tv.setTag(C0627R.id.TAG_IDSECC, i);
                        this.nsecc_mostradas++;
                        width_usado += v_secc_item.getMeasuredWidth();
                    }
                }
            }
            if (es_v) {
                this.v_cab.findViewById(C0627R.id.cab_grande).setVisibility(8);
                this.v_cab.findViewById(C0627R.id.hr_cab).setVisibility(8);
            } else {
                this.v_cab.findViewById(C0627R.id.cab_peque_top).setVisibility(8);
                this.v_cab.findViewById(C0627R.id.cab_peque_bottom).setVisibility(8);
            }
        } else if (this.tipomenu == 1) {
            iv_drawer = (ImageView) ((Activity) c).findViewById(C0627R.id.iv_drawer);
            d = ((Activity) c).getResources().getDrawable(C0627R.drawable.ic_drawer);
            d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
            iv_drawer.setImageDrawable(d);
            iv_drawer.setVisibility(0);
            iv_drawer.setOnClickListener(new C06535());
            iv_icohome.setOnClickListener(new C06546());
            this.mDrawerList = (ListView) ((Activity) c).findViewById(C0627R.id.left_drawer);
            this.mDrawerList.setCacheColorHint(0);
            this.mDrawerList.setBackgroundColor(Color.parseColor("#" + this.menusl_c));
            if (this.slider_v > 0) {
                RelativeLayout ll_aux;
                if (this.mDrawerList.getHeaderViewsCount() == 0) {
                    ll_aux = (RelativeLayout) ((Activity) c).getLayoutInflater().inflate(C0627R.layout.drawer_header, null);
                    this.mDrawerList.addHeaderView(ll_aux, null, false);
                } else {
                    ll_aux = (RelativeLayout) this.mDrawerList.findViewById(C0627R.id.ll_drawer);
                }
                SharedPreferences settings = getSharedPreferences("sh", 0);
                if (settings.getInt("slider_v_act", 0) == this.slider_v) {
                    ImageView iv_d = (ImageView) ll_aux.findViewById(C0627R.id.iv_drawer);
                    try {
                        FileInputStream fis = openFileInput("slider_header");
                        Bitmap bm_aux = BitmapFactory.decodeFileDescriptor(fis.getFD());
                        fis.close();
                        iv_d.setImageBitmap(bm_aux);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (this.slider_h > 0) {
                        ll_aux.setLayoutParams(new AbsListView.LayoutParams(-1, this.slider_h));
                    }
                    ll_aux.findViewById(C0627R.id.pb_drawer).setVisibility(8);
                    iv_d.setVisibility(0);
                } else {
                    if (settings.getInt("slider_v_act", 0) < this.slider_v) {
                        ProgressBar pb = (ProgressBar) ll_aux.findViewById(C0627R.id.pb_drawer);
                        if (this.slider_h > 0) {
                            ll_aux.setLayoutParams(new AbsListView.LayoutParams(-1, this.slider_h));
                        }
                        ll_aux.findViewById(C0627R.id.iv_drawer).setVisibility(8);
                        pb.setVisibility(0);
                    }
                }
            }
            if (this.sep_secc) {
                this.mDrawerList.setDivider(new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{Color.parseColor("#" + this.c_sep_secc), Color.parseColor("#" + this.c_sep_secc), Color.parseColor("#" + this.menusl_c)}));
                this.mDrawerList.setDividerHeight(1);
            }
            this.opcions = new ArrayList();
            for (i = Integer.valueOf(0); i.intValue() < this.secciones_a.length; i = Integer.valueOf(i.intValue() + 1)) {
                if (!this.secciones_a[i.intValue()].oculta) {
                    MenuOpcion menuOpcion = new MenuOpcion();
                    menuOpcion.texto = this.secciones_a[i.intValue()].titulo;
                    if (this.secciones_a[i.intValue()].ico_cargando) {
                        menuOpcion.img = null;
                        menuOpcion.img_cargando = true;
                    } else {
                        menuOpcion.img = this.secciones_a[i.intValue()].ico;
                        menuOpcion.img_cargando = false;
                    }
                    this.opcions.add(menuOpcion);
                }
            }
            this.ma = new MenuAdapter(this.opcions, c);
            this.mDrawerList.setAdapter(this.ma);
        } else if (this.tipomenu == 2 && !((Activity) c).getClass().getSimpleName().equals("t_menugrid")) {
            iv_drawer = (ImageView) ((Activity) c).findViewById(C0627R.id.iv_drawer);
            d = ((Activity) c).getResources().getDrawable(C0627R.drawable.ic_atras_light);
            d.setColorFilter(Color.parseColor("#" + this.c_icos_top), Mode.MULTIPLY);
            iv_drawer.setImageDrawable(d);
            iv_drawer.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(9998));
            iv_icohome.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(9998));
            iv_drawer.setVisibility(0);
            iv_drawer.setOnClickListener((OnClickListener) c);
            iv_icohome.setOnClickListener((OnClickListener) c);
        }
        if (this.tipomenu == 1 && this.icos_pendientes) {
            boolean aux1 = false;
            for (RunningServiceInfo service : ((ActivityManager) getSystemService("activity")).getRunningServices(Strategy.TTL_SECONDS_INFINITE)) {
                if (s_cargar_icos.class.getName().equals(service.service.getClassName())) {
                    aux1 = true;
                }
            }
            if (!aux1) {
                startService(new Intent(this, s_cargar_icos.class));
            }
        }
        return this.nsecc_mostradas;
    }

    ResultGetIntent crear_rgi(Integer ind, Context c) {
        ResultGetIntent rgi = new ResultGetIntent();
        rgi.finalizar = true;
        rgi.finalizar_app = false;
        rgi.esmas = false;
        if (ind.intValue() < 998) {
            Seccion s = this.secciones_a[ind.intValue()];
            if (!s.oculta) {
                this.ind_secc_sel = ind.intValue();
            }
            this.ind_secc_sel_2 = ind.intValue();
            Editor e = getSharedPreferences("sh", 0).edit();
            e.putInt("ind_secc_sel", this.ind_secc_sel);
            e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
            e.commit();
            Intent i;
            if (s.tipo == 1) {
                i = new Intent(c, t_url.class);
                i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, s.url);
                rgi.f34i = i;
                return rgi;
            } else if (s.tipo == 2) {
                i = new Intent(c, t_html.class);
                i.putExtra("idsecc", ind);
                rgi.f34i = i;
                return rgi;
            } else if (s.tipo == 3) {
                i = new Intent(c, t_and.class);
                i.putExtra("idsecc", ind);
                rgi.f34i = i;
                return rgi;
            } else if (s.tipo == 4) {
                return intent_ofics(c);
            } else {
                if (s.tipo == 5) {
                    return intent_prods(c);
                }
                if (s.tipo == 6) {
                    i = new Intent(c, t_video.class);
                    i.putExtra(PlusShare.KEY_CALL_TO_ACTION_URL, s.url);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 7) {
                    i = new Intent(c, t_radio.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 8) {
                    i = new Intent(c, t_rss.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 9) {
                    i = new Intent(c, t_chat_contra.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 10) {
                    if (s.idsubcat > 0) {
                        i = new Intent(c, t_buscchats_lista.class);
                        i.putExtra("ind", ind);
                        i.putExtra("idcat", s.idcat);
                        i.putExtra("idsubcat", s.idsubcat);
                    } else {
                        i = new Intent(c, t_buscchats.class);
                        i.putExtra("ind", ind);
                        i.putExtra("idcat", s.idcat);
                    }
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 11) {
                    i = new Intent(c, t_buscusus.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo == 12) {
                    i = new Intent(c, t_submenu.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                } else if (s.tipo != 13) {
                    return rgi;
                } else {
                    i = new Intent(c, t_gal.class);
                    i.putExtra("ind", ind);
                    rgi.f34i = i;
                    return rgi;
                }
            }
        }
        this.ind_secc_sel_2 = 0;
        e = getSharedPreferences("sh", 0).edit();
        e.putInt("ind_secc_sel", this.ind_secc_sel);
        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
        e.commit();
        rgi.f34i = new Intent(c, t_chat_contra.class);
        return rgi;
    }

    ResultGetIntent getIntent(View v, Context c) {
        ResultGetIntent rgi = new ResultGetIntent();
        rgi.finalizar = true;
        rgi.finalizar_app = false;
        rgi.esmas = false;
        Integer v_id = (Integer) v.getTag(C0627R.id.TAG_IDSECC);
        if (v_id.intValue() >= 0 && v_id.intValue() < this.secciones_a.length) {
            return crear_rgi(v_id, c);
        }
        if (v_id.intValue() == 9998) {
            rgi.finalizar = true;
            return rgi;
        } else if (v_id.intValue() == 9999) {
            i = new Intent(c, t_menu.class);
            rgi.finalizar = false;
            rgi.esmas = true;
            rgi.f34i = i;
            return rgi;
        } else {
            if (v_id.intValue() > 1000 && v_id.intValue() <= this.icos_a.length + 1000) {
                ResultGetIntent rgi_aux;
                Editor e;
                if (v_id.intValue() == this.ico_share_ord + 1000) {
                    if (this.ico_share_idsec <= 0) {
                        i = new Intent("android.intent.action.SEND");
                        i.setType(HTTP.PLAIN_TEXT_TYPE);
                        i.putExtra("android.intent.extra.SUBJECT", this.share_subject);
                        i.putExtra("android.intent.extra.TEXT", this.share_text);
                        rgi.f34i = Intent.createChooser(i, getString(C0627R.string.compartir));
                        rgi.finalizar = false;
                        return rgi;
                    } else if (this.ico_share_idsec == 1) {
                        rgi_aux = new ResultGetIntent();
                        rgi_aux.f34i = obtener_intent(this.ico_share_url);
                        rgi_aux.finalizar = false;
                        rgi_aux.finalizar_app = false;
                        rgi_aux.esmas = false;
                        return rgi_aux;
                    } else {
                        e = getSharedPreferences("sh", 0).edit();
                        this.ind_secc_sel_2 = this.secciones_alist.indexOf(Integer.valueOf(this.ico_share_idsec));
                        this.ind_secc_sel = this.ind_secc_sel_2;
                        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return crear_rgi(Integer.valueOf(this.ind_secc_sel), c);
                    }
                } else if (v_id.intValue() == this.ico_ofics_ord + 1000) {
                    e = getSharedPreferences("sh", 0).edit();
                    if (this.ico_ofics_idsec <= 0) {
                        this.ind_secc_sel = this.ind_secc_ofics;
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return intent_ofics(c);
                    } else if (this.ico_ofics_idsec == 1) {
                        rgi_aux = new ResultGetIntent();
                        rgi_aux.f34i = obtener_intent(this.ico_ofics_url);
                        rgi_aux.finalizar = false;
                        rgi_aux.finalizar_app = false;
                        rgi_aux.esmas = false;
                        return rgi_aux;
                    } else {
                        this.ind_secc_sel_2 = this.secciones_alist.indexOf(Integer.valueOf(this.ico_ofics_idsec));
                        this.ind_secc_sel = this.ind_secc_sel_2;
                        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return crear_rgi(Integer.valueOf(this.ind_secc_sel), c);
                    }
                } else if (v_id.intValue() == this.ico_busc_ord + 1000) {
                    e = getSharedPreferences("sh", 0).edit();
                    if (this.ico_busc_idsec <= 0) {
                        this.ind_secc_sel = this.ind_secc_buscador;
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return intent_prods(c);
                    } else if (this.ico_busc_idsec == 1) {
                        rgi_aux = new ResultGetIntent();
                        rgi_aux.f34i = obtener_intent(this.ico_busc_url);
                        rgi_aux.finalizar = false;
                        rgi_aux.finalizar_app = false;
                        rgi_aux.esmas = false;
                        return rgi_aux;
                    } else {
                        this.ind_secc_sel_2 = this.secciones_alist.indexOf(Integer.valueOf(this.ico_busc_idsec));
                        this.ind_secc_sel = this.ind_secc_sel_2;
                        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return crear_rgi(Integer.valueOf(this.ind_secc_sel), c);
                    }
                } else if (v_id.intValue() == this.ico_exit_ord + 1000) {
                    if (this.ico_exit_idsec <= 0) {
                        rgi.finalizar = true;
                        rgi.finalizar_app = true;
                        finalizar_app = true;
                        return rgi;
                    } else if (this.ico_exit_idsec == 1) {
                        rgi_aux = new ResultGetIntent();
                        rgi_aux.f34i = obtener_intent(this.ico_exit_url);
                        rgi_aux.finalizar = false;
                        rgi_aux.finalizar_app = false;
                        rgi_aux.esmas = false;
                        return rgi_aux;
                    } else {
                        e = getSharedPreferences("sh", 0).edit();
                        this.ind_secc_sel_2 = this.secciones_alist.indexOf(Integer.valueOf(this.ico_exit_idsec));
                        this.ind_secc_sel = this.ind_secc_sel_2;
                        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return crear_rgi(Integer.valueOf(this.ind_secc_sel), c);
                    }
                } else if (v_id.intValue() == this.ico_notif_ord + 1000) {
                    if (this.ico_notif_idsec <= 0) {
                        i = new Intent(c, notifs.class);
                        rgi.finalizar = false;
                        rgi.esmas = true;
                        rgi.f34i = i;
                        return rgi;
                    } else if (this.ico_notif_idsec == 1) {
                        rgi_aux = new ResultGetIntent();
                        rgi_aux.f34i = obtener_intent(this.ico_notif_url);
                        rgi_aux.finalizar = false;
                        rgi_aux.finalizar_app = false;
                        rgi_aux.esmas = false;
                        return rgi_aux;
                    } else {
                        e = getSharedPreferences("sh", 0).edit();
                        this.ind_secc_sel_2 = this.secciones_alist.indexOf(Integer.valueOf(this.ico_notif_idsec));
                        this.ind_secc_sel = this.ind_secc_sel_2;
                        e.putInt("ind_secc_sel_2", this.ind_secc_sel_2);
                        e.putInt("ind_secc_sel", this.ind_secc_sel);
                        e.commit();
                        return crear_rgi(Integer.valueOf(this.ind_secc_sel), c);
                    }
                }
            }
            return null;
        }
    }

    ResultGetIntent intent_prods(Context c) {
        ResultGetIntent rgi = new ResultGetIntent();
        rgi.finalizar = true;
        rgi.finalizar_app = false;
        Intent i = new Intent(c, t_buscador_form.class);
        if (!(this.busc_texto || this.busc_cat || this.busc_precio || this.busc_antiguedad || this.ord_texto || this.ord_precio || this.ord_antiguedad)) {
            int orden_aux;
            i = new Intent(c, t_buscador.class);
            if (this.ord_def.indexOf("TITULO") != -1) {
                orden_aux = 1;
            } else if (this.ord_def.indexOf("PRECIO") != -1) {
                orden_aux = 2;
            } else {
                orden_aux = 3;
            }
            i.putExtra("orden", orden_aux);
            i.putExtra("orden_tipo", this.ord_def.contains("ASC"));
        }
        rgi.f34i = i;
        return rgi;
    }

    ResultGetIntent intent_ofics(Context c) {
        boolean t_otros = true;
        ResultGetIntent rgi = new ResultGetIntent();
        rgi.finalizar = true;
        rgi.finalizar_app = false;
        Intent i = new Intent(c, t_oficinas.class);
        if (this.oficinas_a.length == 1) {
            if (this.oficinas_a[0].horario.equals("") && this.oficinas_a[0].dir1.equals("") && this.oficinas_a[0].dir2.equals("") && this.oficinas_a[0].cp.equals("") && this.oficinas_a[0].pob.equals("") && this.oficinas_a[0].prov.equals("") && this.oficinas_a[0].nfotos <= 0 && this.oficinas_a[0].f31x == 0) {
                t_otros = false;
            }
            if (this.oficinas_a[0].t_email && this.oficinas_a[0].tel1.equals("") && this.oficinas_a[0].tel2.equals("") && this.oficinas_a[0].web.equals("") && !this.oficinas_a[0].chat && !t_otros) {
                i = new Intent(c, contactar.class);
                i.putExtra("idofic", this.oficinas_a[0].id);
            } else if (!this.oficinas_a[0].t_email && !this.oficinas_a[0].tel1.equals("") && this.oficinas_a[0].tel2.equals("") && this.oficinas_a[0].web.equals("") && !this.oficinas_a[0].chat && !t_otros) {
                i = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + this.oficinas_a[0].tel1));
                rgi.finalizar = false;
            } else if (!this.oficinas_a[0].t_email && this.oficinas_a[0].tel1.equals("") && !this.oficinas_a[0].tel2.equals("") && this.oficinas_a[0].web.equals("") && !this.oficinas_a[0].chat && !t_otros) {
                i = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + this.oficinas_a[0].tel2));
                rgi.finalizar = false;
            } else if (!this.oficinas_a[0].t_email && this.oficinas_a[0].tel1.equals("") && this.oficinas_a[0].tel2.equals("") && !this.oficinas_a[0].web.equals("") && !this.oficinas_a[0].chat && !t_otros) {
                i = new Intent("android.intent.action.VIEW");
                i.setData(Uri.parse(this.oficinas_a[0].web));
                rgi.finalizar = false;
            } else if (!this.oficinas_a[0].t_email && this.oficinas_a[0].tel1.equals("") && this.oficinas_a[0].tel2.equals("") && this.oficinas_a[0].web.equals("") && this.oficinas_a[0].chat && !t_otros) {
                i = new Intent(c, chat.class);
            }
        }
        rgi.f34i = i;
        return rgi;
    }

    int[] file_to_iv(String archivo, ImageView iv) {
        int[] dimen = new int[]{0, 0};
        try {
            FileInputStream fis = openFileInput(archivo);
            Bitmap bm_aux = BitmapFactory.decodeFileDescriptor(fis.getFD());
            fis.close();
            iv.setImageBitmap(bm_aux);
            dimen[0] = bm_aux.getWidth();
            dimen[1] = bm_aux.getHeight();
        } catch (Exception e) {
        }
        return dimen;
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len > 0) {
                out.write(buf, 0, len);
            } else {
                in.close();
                out.close();
                return;
            }
        }
    }

    static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        boolean withinBounds = width <= reqWidth && height <= reqHeight;
        if (withinBounds) {
            return 1;
        }
        return Math.round(((float) width) / ((float) calculateNewWidth(width, height, reqWidth, reqHeight)));
    }

    static int calculateNewWidth(int w, int h, int w_max, int h_max) {
        int w_return = w;
        int h_return = h;
        if (w > w_max) {
            h_return = Math.round((float) ((h * w_max) / w));
            w_return = w_max;
        }
        if (h_return <= h_max) {
            return w_return;
        }
        w_return = Math.round((float) ((w_return * h_max) / h_return));
        h_return = h_max;
        return w_return;
    }

    public Bitmap decodeSampledBitmapFromResource(Uri selectedImage, int reqWidth, int reqHeight) throws IOException {
        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        imageStream = getContentResolver().openInputStream(selectedImage);
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();
        return bm;
    }

    File getTempFile(Context context, int n) {
        File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
            try {
                new File(path, ".nomedia").createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new File(path, "dt_foto" + n + "_temp.jpg");
    }

    File getTempFile_notemp(Context context, int n, boolean es_g) {
        String g = "";
        if (es_g) {
            g = "_g";
        }
        File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
            try {
                new File(path, ".nomedia").createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new File(path, "dt_foto" + n + g + ".jpg");
    }

    File getTempFile_libre(Context context, String archivo) {
        File path = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
            try {
                new File(path, ".nomedia").createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new File(path, archivo);
    }

    static boolean esClaro(String c) {
        if (c.equals("") || c.equals("#") || ((int) Math.sqrt(((((double) (Color.red(Color.parseColor(c)) * Color.red(Color.parseColor(c)))) * 0.241d) + (((double) (Color.green(Color.parseColor(c)) * Color.green(Color.parseColor(c)))) * 0.691d)) + (((double) (Color.blue(Color.parseColor(c)) * Color.blue(Color.parseColor(c)))) * 0.068d))) > TransportMediator.KEYCODE_MEDIA_RECORD) {
            return true;
        }
        return false;
    }

    int crear_globales(String[] idseccs_a, String[] idofics_a, Bundle extras, Intent i_orig) {
        int i;
        String[] fotos_a;
        this.wv_cache_limpiada = false;
        SharedPreferences settings = getSharedPreferences("sh", 0);
        settings.registerOnSharedPreferenceChangeListener(this);
        this.ico_busc_ord = settings.getInt("ico_busc_ord", 0);
        this.ico_share_ord = settings.getInt("ico_share_ord", 0);
        this.ico_ofics_ord = settings.getInt("ico_ofics_ord", 0);
        this.ico_exit_ord = settings.getInt("ico_exit_ord", 0);
        this.ico_notif_ord = settings.getInt("ico_notif_ord", 0);
        this.ico_ofics_imgperso = settings.getInt("io1", 0) == 1;
        this.ico_ofics_imgperso_v = settings.getInt("io2", 0);
        this.ico_ofics_idsec = settings.getInt("io3", 0);
        this.ico_ofics_url = settings.getString("io4", "");
        this.ico_busc_imgperso = settings.getInt("ib1", 0) == 1;
        this.ico_busc_imgperso_v = settings.getInt("ib2", 0);
        this.ico_busc_idsec = settings.getInt("ib3", 0);
        this.ico_busc_url = settings.getString("ib4", "");
        this.ico_exit_imgperso = settings.getInt("ie1", 0) == 1;
        this.ico_exit_imgperso_v = settings.getInt("ie2", 0);
        this.ico_exit_idsec = settings.getInt("ie3", 0);
        this.ico_exit_url = settings.getString("ie4", "");
        this.ico_notif_imgperso = settings.getInt("in1", 0) == 1;
        this.ico_notif_imgperso_v = settings.getInt("in2", 0);
        this.ico_notif_idsec = settings.getInt("in3", 0);
        this.ico_notif_url = settings.getString("in4", "");
        this.ico_share_imgperso = settings.getInt("is1", 0) == 1;
        this.ico_share_imgperso_v = settings.getInt("is2", 0);
        this.ico_share_idsec = settings.getInt("is3", 0);
        this.ico_share_url = settings.getString("is4", "");
        this.share_subject = settings.getString("share_subject", "");
        this.share_text = settings.getString("share_text", "");
        our = settings.getInt("our", 0);
        this.admob_cod = settings.getString("admob_cod", "");
        this.admob_pos = settings.getInt("admob_pos", 0);
        this.admob_tipo = AdSize.SMART_BANNER;
        this.admob_pro_cod = settings.getString("a_p_c", "");
        this.appnext_pro_cod = settings.getString("n_p_c", "");
        this.admob_menu_cod = settings.getString("a_m_c", "");
        this.appnext_menu_cod = settings.getString("n_m_c", "");
        this.admob_rew_cod = settings.getString("a_r_c", "");
        this.appnext_rew_cod = settings.getString("n_r_c", "");
        this.rew_modo = settings.getInt("r_mo", 0);
        this.rew_msg = settings.getString("r_ms", "");
        this.admob_pro_w = 0;
        this.admob_pro_h = 0;
        String[] aux_a = settings.getString("a_p_s", "0x0").split("x");
        if (aux_a.length == 2) {
            this.admob_pro_w = Integer.parseInt(aux_a[0]);
            this.admob_pro_h = Integer.parseInt(aux_a[1]);
        }
        this.admob_menu_w = 0;
        this.admob_menu_h = 0;
        aux_a = settings.getString("a_m_s", "0x0").split("x");
        if (aux_a.length == 2) {
            this.admob_menu_w = Integer.parseInt(aux_a[0]);
            this.admob_menu_h = Integer.parseInt(aux_a[1]);
        }
        if (settings.getInt("admob_sma", 1) == 0) {
            this.admob_tipo = AdSize.BANNER;
        } else if (settings.getInt("admob_sma", 1) == 2) {
            this.admob_tipo = AdSize.LARGE_BANNER;
        }
        admob_int_cod = settings.getString("admob_int_cod", "");
        this.catsnotif_tit = settings.getString("tcn", "");
        this.banners_enchats = settings.getInt("b_c", 1) == 1;
        this.links_enchats = settings.getInt("l_c", 1) == 1;
        this.fotos_privados = settings.getInt("fp", 1) == 1;
        this.radio_pos = settings.getInt("r_p", 0);
        this.radio_txt_c = settings.getInt("r_t_c", 0) == 1;
        this.radio_artist_size = settings.getInt("r_a_s", 0);
        this.radio_song_size = settings.getInt("r_s_s", 0);
        this.radio_artist_col = settings.getString("r_a_c", "");
        this.radio_song_col = settings.getString("r_s_c", "");
        this.radio_artist_b = settings.getInt("r_a_b", 0) == 1;
        this.radio_song_b = settings.getInt("r_s_b", 0) == 1;
        appnext_cod = settings.getString("appnext_cod", "");
        appnext_cod_int_e = settings.getString("appnext_cod_int_e", "");
        appnext_cod_int_ia = settings.getString("appnext_cod_int_ia", "");
        autoplay = Boolean.valueOf(settings.getInt("ap", 1) == 1);
        mute = Boolean.valueOf(settings.getInt("mu", 1) == 1);
        if (appnext_cod.equals("") && !appnext_cod_int_e.equals("")) {
            appnext_cod = appnext_cod_int_e;
        } else if (!appnext_cod.equals("") && appnext_cod_int_e.equals("")) {
            appnext_cod_int_e = appnext_cod;
            appnext_cod_int_ia = appnext_cod;
        }
        this.appnextb_cod = settings.getString("appnextb_cod", "");
        int i2 = (admob_int_cod.equals("") && appnext_cod.equals("")) ? 0 : settings.getInt("admob_int_v", 0);
        this.admob_int_interv = i2;
        i2 = (admob_int_cod.equals("") && appnext_cod.equals("")) ? 0 : settings.getInt("admob_inte_v", 0);
        this.admob_intentrar_interv = i2;
        i2 = (admob_int_cod.equals("") && appnext_cod.equals("")) ? 0 : settings.getInt("admob_ch_v", 0);
        this.admob_chat_interv = i2;
        this.tipomenu = settings.getInt("tipomenu", 0);
        this.petic_ask_nombre = settings.getInt("petic_ask_nombre", 0);
        this.petic_ask_email = settings.getInt("petic_ask_email", 0);
        this.petic_ask_tel = settings.getInt("petic_ask_tel", 0);
        if (settings.getInt("sep_secc", 0) == 0) {
            this.sep_secc = false;
        } else {
            this.sep_secc = true;
        }
        this.t_ind = settings.getInt("t_ind", 0);
        this.t_mas = settings.getInt("t_mas", 0);
        this.c1 = settings.getString("c1", "");
        this.c2 = settings.getString("c2", "");
        this.c_icos = settings.getString("c_icos", "");
        this.c_icos_top = settings.getString("c_icos_t", "");
        if (this.c_icos_top.equals("")) {
            this.c_icos_top = this.c_icos;
        }
        this.c_sep_secc = settings.getString("c_sep_secc", "");
        this.c_secc_noactiv = settings.getString("c_secc_noactiv", "");
        this.c_secc_activ = settings.getString("c_secc_activ", "");
        this.c_linea = settings.getString("c_linea", "");
        this.c_ind = settings.getString("c_ind", "");
        this.c1_ofic = settings.getString("c1_ofic", "");
        this.c2_ofic = settings.getString("c2_ofic", "");
        this.c_perofic = settings.getInt("c_perofic", 0);
        this.c_tit_ofic = settings.getString("c_tit_ofic", "");
        this.c_sep_ofic = settings.getString("c_sep_ofic", "");
        this.c_ico_sep_ofic = settings.getString("c_ico_sep_ofic", "");
        this.c_txt_ofic = settings.getString("c_txt_ofic", "");
        this.c_icos_ofic = settings.getString("c_icos_ofic", "");
        this.c_ir_ofic = settings.getString("c_ir_ofic", "");
        this.c1_splash = settings.getString("c1_sp", "");
        this.c2_splash = settings.getString("c2_sp", "");
        this.c1_prods = settings.getString("c1_prods", "");
        this.c2_prods = settings.getString("c2_prods", "");
        this.c_txt_prods = settings.getString("c_txt_prods", "");
        this.c_icos_prods = settings.getString("c_icos_prods", "");
        this.c_icos_2_prods = settings.getString("c_icos_2_prods", "");
        this.c_tit_prods = settings.getString("c_tit_prods", "");
        this.c_sep_prods = settings.getString("c_sep_prods", "");
        this.c_ico_sep_prods = settings.getString("c_ico_sep_prods", "");
        this.c1_prods_l = settings.getString("c1_prods_l", "");
        this.c2_prods_l = settings.getString("c2_prods_l", "");
        this.c_perprod = settings.getInt("c_perprod", 0);
        this.c_tit_prods_l = settings.getString("c_tit_prods_l", "");
        this.c_precio_prods_l = settings.getString("c_precio_prods_l", "");
        this.c_antiguedad_prods_l = settings.getString("c_antiguedad_prods_l", "");
        this.c_prods_det = settings.getString("c_prods_det", "");
        this.splash_w = settings.getInt("splash_w", 2000);
        this.slider_v = settings.getInt("s_v", 0);
        this.slider_h = settings.getInt("s_h", 0);
        this.bienvenida_txt = settings.getString("bv_txt", "");
        this.bienvenida_tit = settings.getString("bv_tit", "");
        if (settings.getInt("bv_nomas_mostrar", 0) == 0) {
            this.bienvenida_nomas_mostrar = false;
        } else {
            this.bienvenida_nomas_mostrar = true;
        }
        if (settings.getInt("bv_nomas_def", 0) == 0) {
            this.bienvenida_nomas_def = false;
        } else {
            this.bienvenida_nomas_def = true;
        }
        this.dominio = settings.getString("dom", "");
        this.fb_modo = settings.getInt("fb_m", 0);
        this.fb_bloqdatos = settings.getInt("fb_b", 0) == 1;
        this.catsnotif_v_bd = settings.getInt("vcn", 0);
        this.hay_catsnotif = settings.getInt("hcn", 0) == 1;
        this.notifdef_tit = settings.getString("nd_t", "");
        this.notifdef_txt = settings.getString("nd_s", "");
        this.notifdef_idabrir = settings.getInt("nd_i", 0);
        this.notifdef_url = settings.getString("nd_u", "");
        this.pedir_confirm_exit = settings.getInt("conf_ex", 0) == 1;
        this.privacy_mostrar = settings.getInt("pp", 1) == 1;
        this.rate_tit = settings.getString("rt_tit", "");
        this.rate_txt = settings.getString("rt_txt", "");
        this.rate_ok = settings.getString("rt_ok", "");
        this.rate_ko = settings.getString("rt_ko", "");
        this.rate_primeravez = settings.getInt("rt_1v", 0);
        this.rate_int = settings.getInt("rt_int", 0);
        this.wv_sinconex = settings.getInt("wv_sc", 1) == 1;
        this.wv_sinconex_txt = settings.getString("wv_sc_txt", "");
        this.wv_cache = settings.getInt("wv_c", 1) == 1;
        this.rss_interv = settings.getInt("rss_i", 0);
        this.rss_notif = settings.getInt("rss_n", 0);
        this.rss_tit = settings.getString("rss_t", "");
        if (this.rss_tit.equals("")) {
            this.rss_interv = 0;
        }
        new alarma_rss().SetAlarm(getApplicationContext(), this.rss_interv);
        this.video_fs = settings.getInt("vfs", 0) == 1;
        this.video_ls = settings.getInt("vls", 0) == 1;
        this.divisa = settings.getString("divisa", "").replace("puntoycoma", ";");
        String[] busc_campos_a = settings.getString("busc_campos", "").split(",");
        if (busc_campos_a.length == 4) {
            this.busc_texto = busc_campos_a[0].equals("1");
            this.busc_cat = busc_campos_a[1].equals("1");
            this.busc_precio = busc_campos_a[2].equals("1");
            this.busc_antiguedad = busc_campos_a[3].equals("1");
            if (this.busc_cat) {
                SQLiteDatabase db = new bd(getApplicationContext()).getReadableDatabase();
                if (db != null) {
                    Cursor c = db.rawQuery("SELECT _id FROM cats LIMIT 1", null);
                    if (!c.moveToFirst()) {
                        this.busc_cat = false;
                    }
                    c.close();
                    db.close();
                }
            }
        }
        String[] orden_campos_a = settings.getString("busc_orden", "").split(",");
        if (orden_campos_a.length == 3) {
            this.ord_texto = orden_campos_a[0].equals("1");
            this.ord_precio = orden_campos_a[1].equals("1");
            this.ord_antiguedad = orden_campos_a[2].equals("1");
        }
        this.ord_def = settings.getString("busc_def", "");
        this.prods_tit = settings.getString("prods_tit", "");
        this.prods_masinfo = settings.getString("prods_masinfo", "");
        this.prods_comprar = settings.getString("prods_comprar", "");
        this.prods_masinfo_mostrar = settings.getInt("prods_masinfo_mostrar", 1) == 1;
        this.prods_linksexternos = settings.getInt("prods_linksexternos", 1) == 1;
        this.prods_adaptar_ancho = settings.getInt("prods_adaptar_ancho", 1) == 1;
        this.pp_email = settings.getString("pp_email", "");
        this.pp_divisa = settings.getString("pp_div", "");
        this.menu_ncols = settings.getInt("m_ncols", 1);
        this.menu_padding = settings.getInt("m_padding", 2);
        this.menu_txt_radius = settings.getInt("m_txt_radius", 5);
        if (settings.getInt("m_mostrar_icos", 1) == 0) {
            this.menu_mostrar_icos = false;
        } else {
            this.menu_mostrar_icos = true;
        }
        if (settings.getInt("m_mostrar_txt", 1) == 0) {
            this.menu_mostrar_txt = false;
        } else {
            this.menu_mostrar_txt = true;
        }
        if (settings.getInt("m_icos_izq", 1) == 0) {
            this.menu_icos_izq = false;
        } else {
            this.menu_icos_izq = true;
        }
        if (settings.getInt("m_anim", 1) == 0) {
            this.menu_anim = false;
        } else {
            this.menu_anim = true;
        }
        if (settings.getInt("m_c", 1) == 0) {
            this.menu_c = false;
        } else {
            this.menu_c = true;
        }
        if (settings.getInt("m_txt_c", 1) == 0) {
            this.menu_txt_c = false;
        } else {
            this.menu_txt_c = true;
        }
        this.menu_txt_b = settings.getInt("m_txt_b", 0) == 1;
        this.menu_estilo = settings.getInt("m_e", 0);
        this.menu_txt_bg = settings.getString("m_txt_bg", "");
        this.menu_txt_col = settings.getString("m_txt_col", "");
        this.menu_c1 = settings.getString("m_c1", "");
        this.menu_c2 = settings.getString("m_c2", "");
        this.menusl_c = settings.getString("msl_c", "");
        this.t_fondomenu = settings.getInt("fm", 0) == 1;
        this.act_fondomenu = settings.getBoolean("act_fm", false);
        this.v_fondomenu = settings.getInt("vf", 0);
        this.secciones_a = new Seccion[idseccs_a.length];
        this.secciones_alist = new ArrayList();
        this.menu_a_secciones = new int[idseccs_a.length];
        int ord_secc_abrir = 999;
        String notif_idelem = "";
        if (extras != null && extras.getString("notif_id") != null && extras.getString("notif_tipo").equals("1")) {
            notif_idelem = extras.getString("notif_idelem");
        } else if (extras != null && extras.getString("notif_idtema") != null) {
            for (String str : idseccs_a) {
                if (extras.getString("notif_idsecc") == str) {
                    notif_idelem = extras.getString("notif_idsecc");
                    break;
                }
            }
            if (notif_idelem.equals("")) {
                ord_secc_abrir = 998;
            }
        } else if (extras != null && extras.getString("id_remit") != null) {
            i = 0;
            while (i < idseccs_a.length) {
                if (settings.getInt("s" + idseccs_a[i] + "_tipo", 0) == 9 && settings.getBoolean("chat" + idseccs_a[i] + "_validado", true)) {
                    notif_idelem = idseccs_a[i];
                    break;
                }
                i++;
            }
            if (notif_idelem.equals("")) {
                ord_secc_abrir = 998;
            }
        } else if (!(extras == null || extras.getString("abrir_perfil") == null)) {
            i = 0;
            while (i < idseccs_a.length) {
                if ((settings.getInt("s" + idseccs_a[i] + "_tipo", 0) == 9 && settings.getBoolean("chat" + idseccs_a[i] + "_validado", true)) || settings.getInt("s" + idseccs_a[i] + "_tipo", 0) == 10 || settings.getInt("s" + idseccs_a[i] + "_tipo", 0) == 11) {
                    notif_idelem = idseccs_a[i];
                    break;
                }
                i++;
            }
            if (notif_idelem.equals("")) {
                ord_secc_abrir = 998;
            }
        }
        this.nsecc_visibles = 0;
        this.icos_pendientes = false;
        this.hay_submenu = false;
        this.hay_icosmenu = false;
        int ord_menu = 0;
        for (String idsecc : idseccs_a) {
            String fotos;
            int ind = settings.getInt("s" + idsecc + "_ord", 0) - 1;
            if (!notif_idelem.equals("") || ord_secc_abrir == 998) {
                if (notif_idelem.equals(idsecc)) {
                    ord_secc_abrir = ind;
                }
            } else if (settings.getInt("s" + idsecc + "_ocu", 0) != 1) {
                ord_secc_abrir = Math.min(ord_secc_abrir, ind);
            }
            Seccion s = new Seccion();
            s.id = Integer.parseInt(idsecc);
            s.tipo = settings.getInt("s" + idsecc + "_tipo", 0);
            if (s.tipo == 12) {
                this.hay_submenu = true;
            }
            s.titulo = settings.getString("s" + idsecc + "_tit", "");
            s.idgo = settings.getString("s" + idsecc + "_idgo", "");
            if (settings.getInt("s" + idsecc + "_ocu", 0) == 1) {
                s.oculta = true;
            } else {
                s.oculta = false;
                s.ind_menu = this.nsecc_visibles;
                this.nsecc_visibles++;
            }
            s.rewarded = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_r").toString(), 0) == 1;
            s.descargar = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_d").toString(), 0) == 1;
            s.radio_mostrar = settings.getInt("s" + idsecc + "_r_m", 0);
            s.linksexternos = settings.getInt("s" + idsecc + "_ext", 0);
            if (settings.getInt("s" + idsecc + "_adapt", 0) == 1) {
                s.adaptar_ancho = true;
            } else {
                s.adaptar_ancho = false;
            }
            if (settings.getInt("s" + idsecc + "_stream", 0) == 1) {
                s.stream = true;
            } else {
                s.stream = false;
            }
            s.idfondo = settings.getInt("s" + idsecc + "_idfondo", 0);
            s.vfondo = settings.getInt("s" + idsecc + "_vf", 0);
            if (settings.getInt("s" + idsecc + "_fondo_modif", 0) == 1) {
                s.fondo_modif = true;
            } else {
                s.fondo_modif = false;
            }
            s.url = settings.getString("s" + idsecc + "_url", "");
            s.ua = settings.getString("s" + idsecc + "_ua", "");
            s.html = settings.getString("s" + idsecc + "_html", "");
            if (settings.getInt("s" + idsecc + "_pur", 1) == 1) {
                s.puroHTML = true;
            } else {
                s.puroHTML = false;
            }
            if (settings.getInt("s" + idsecc + "_loa", 1) == 1) {
                s.loader = true;
            } else {
                s.loader = false;
            }
            if (settings.getInt("s" + idsecc + "_zoo", 1) == 1) {
                s.zoom = true;
            } else {
                s.zoom = false;
            }
            s.c1 = settings.getString("s" + idsecc + "_c1", "");
            s.c2 = settings.getString("s" + idsecc + "_c2", "");
            s.c_peritem = settings.getInt("s" + idsecc + "_c_peritem", 0);
            s.c_tit = settings.getString("s" + idsecc + "_c_tit", "");
            s.c_fecha = settings.getString("s" + idsecc + "_c_fecha", "");
            if (settings.getInt("s" + idsecc + "_mostrar_img", 1) == 1) {
                s.mostrar_img = true;
            } else {
                s.mostrar_img = false;
            }
            if (settings.getInt("s" + idsecc + "_mostrar_fecha", 1) == 1) {
                s.mostrar_fecha = true;
            } else {
                s.mostrar_fecha = false;
            }
            s.fotos_perfil = settings.getInt("s" + idsecc + "_f_perfil", 0);
            s.p_fnac = settings.getInt("s" + idsecc + "_fnac", 0);
            s.p_sexo = settings.getInt("s" + idsecc + "_sexo", 0);
            s.p_descr = settings.getInt("s" + idsecc + "_descr", 0);
            s.p_dist = settings.getInt("s" + idsecc + "_dist", 0);
            s.coments = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_com").toString(), 1) == 1;
            s.galeria = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_gal").toString(), 1) == 1;
            s.fdist = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_fdist").toString(), 1) == 1;
            s.fsexo = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_fsexo").toString(), 1) == 1;
            s.fedad1 = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_fedad1").toString(), 1) == 1;
            s.fedad2 = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_fedad2").toString(), 1) == 1;
            s.fdist_def = settings.getInt("s" + idsecc + "_fdist_d", 0);
            s.fsexo_def = settings.getInt("s" + idsecc + "_fsexo_d", 0);
            s.fedad1_def = settings.getInt("s" + idsecc + "_fedad1_d", 0);
            s.fedad2_def = settings.getInt("s" + idsecc + "_fedad2_d", 0);
            s.fotos_chat = settings.getInt("s" + idsecc + "_f_chat", 0);
            if (settings.getInt("s" + idsecc + "_priv", 1) == 1) {
                s.privados = true;
            } else {
                s.privados = false;
            }
            s.acceso_a_externo = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_accext").toString(), 0) == 1;
            s.idcat = settings.getInt("s" + idsecc + "_cat", 0);
            s.idsubcat = settings.getInt("s" + idsecc + "_sub", 0);
            s.ncols = settings.getInt("s" + idsecc + "_ncols", 1);
            s.padding = settings.getInt("s" + idsecc + "_padding", 2);
            s.txt_radius = settings.getInt("s" + idsecc + "_txt_radius", 5);
            if (settings.getInt("s" + idsecc + "_mostrar_icos", 1) == 0) {
                s.mostrar_icos = false;
            } else {
                s.mostrar_icos = true;
            }
            if (settings.getInt("s" + idsecc + "_mostrar_txt", 1) == 0) {
                s.mostrar_txt = false;
            } else {
                s.mostrar_txt = true;
            }
            if (settings.getInt("s" + idsecc + "_icos_izq", 1) == 0) {
                s.icos_izq = false;
            } else {
                s.icos_izq = true;
            }
            if (settings.getInt("s" + idsecc + "_anim", 1) == 0) {
                s.anim = false;
            } else {
                s.anim = true;
            }
            if (settings.getInt("s" + idsecc + "_c", 1) == 0) {
                s.f35c = false;
            } else {
                s.f35c = true;
            }
            if (settings.getInt("s" + idsecc + "_txt_c", 1) == 0) {
                s.txt_c = false;
            } else {
                s.txt_c = true;
            }
            s.txt_b = settings.getInt(new StringBuilder().append("s").append(idsecc).append("_txt_b").toString(), 0) == 1;
            s.estilo = settings.getInt("s" + idsecc + "_e", 0);
            s.txt_bg = settings.getString("s" + idsecc + "_txt_bg", "");
            s.txt_col = settings.getString("s" + idsecc + "_txt_col", "");
            s.seccs = settings.getString("s" + idsecc + "_seccs", "");
            s.ico_id = settings.getInt("s" + idsecc + "_ico_id", 0);
            s.v_ico = settings.getInt("s" + idsecc + "_v_ico", 0);
            s.w_ico = settings.getInt("s" + idsecc + "_w_ico", 0);
            s.h_ico = settings.getInt("s" + idsecc + "_h_ico", 0);
            if (settings.getInt("s" + idsecc + "_ico", 0) == 1) {
                s.ico_cargando = true;
                if (!s.oculta) {
                    this.icos_pendientes = true;
                    this.hay_icosmenu = true;
                }
            } else {
                s.ico_cargando = false;
                try {
                    FileInputStream fis = openFileInput("img_s" + idsecc + "_ico");
                    s.ico = BitmapFactory.decodeFileDescriptor(fis.getFD());
                    fis.close();
                } catch (FileNotFoundException e) {
                } catch (IOException e2) {
                }
                if (!(s.ico == null || s.oculta)) {
                    this.hay_icosmenu = true;
                }
            }
            if (s.tipo == 5) {
                this.ind_secc_buscador = ind;
            } else if (s.tipo == 4) {
                this.ind_secc_ofics = ind;
            }
            String temas = settings.getString("s" + idsecc + "_idtemas", "");
            if (!temas.equals("")) {
                String[] temas_a = temas.split(",");
                s.temas_a = new Tema[temas_a.length];
                int j;
                for (j = 0; j < temas_a.length; j++) {
                    String idtema = temas_a[j];
                    Tema t = new Tema();
                    t.id = Integer.parseInt(idtema);
                    s.temas_a[j] = t;
                }
            }
            String items = settings.getString("s" + idsecc + "_iditems", "");
            if (!items.equals("")) {
                String[] items_a = items.split(",");
                s.and_items_a = new AndItem[items_a.length];
                for (String iditem : items_a) {
                    AndItem ai = new AndItem();
                    int ord = settings.getInt("it" + iditem + "_ord", 0);
                    ai.id = Integer.parseInt(iditem);
                    ai.tit1 = settings.getString("it" + iditem + "_tit1", "");
                    ai.tit1_c = settings.getInt("it" + iditem + "_tit1_c", 0);
                    ai.tit2 = settings.getString("it" + iditem + "_tit2", "");
                    ai.tit2_c = settings.getInt("it" + iditem + "_tit2_c", 0);
                    ai.txt = settings.getString("it" + iditem + "_txt", "");
                    ai.txt_c = settings.getInt("it" + iditem + "_txt_c", 0);
                    ai.txt_h = settings.getInt("it" + iditem + "_txt_h", 0);
                    ai.fcab_id = settings.getInt("it" + iditem + "_fcab_id", 0);
                    ai.fcab_modif = settings.getInt("it" + iditem + "_fcab_modif", 1);
                    ai.fcab_c = settings.getInt("it" + iditem + "_fcab_c", 0);
                    ai.fcab_url = settings.getString("it" + iditem + "_fcab_url", "");
                    ai.fotos_pos = settings.getInt("it" + iditem + "_fotos_pos", 0);
                    ai.fotos_c = settings.getInt("it" + iditem + "_fotos_c", 0);
                    ai.fotos_h = settings.getInt("it" + iditem + "_fotos_h", 0);
                    ai.fcab_zoom = settings.getInt(new StringBuilder().append("it").append(iditem).append("_fcab_z").toString(), 0) == 1;
                    ai.fotos_zoom = settings.getInt(new StringBuilder().append("it").append(iditem).append("_fotos_z").toString(), 1) == 1;
                    ai.tit1_b = settings.getInt("it" + iditem + "_tit1_b", 0);
                    ai.tit1_i = settings.getInt("it" + iditem + "_tit1_i", 0);
                    ai.tit1_f = settings.getInt("it" + iditem + "_tit1_f", 0);
                    ai.tit1_col = settings.getString("it" + iditem + "_tit1_col", "");
                    ai.tit1_u = settings.getInt("it" + iditem + "_tit1_u", 0);
                    ai.tit1_s = settings.getInt("it" + iditem + "_tit1_s", 0);
                    ai.tit2_b = settings.getInt("it" + iditem + "_tit2_b", 0);
                    ai.tit2_i = settings.getInt("it" + iditem + "_tit2_i", 0);
                    ai.tit2_f = settings.getInt("it" + iditem + "_tit2_f", 0);
                    ai.tit2_col = settings.getString("it" + iditem + "_tit2_col", "");
                    ai.tit2_u = settings.getInt("it" + iditem + "_tit2_u", 0);
                    ai.tit2_s = settings.getInt("it" + iditem + "_tit2_s", 0);
                    ai.txt_b = settings.getInt("it" + iditem + "_txt_b", 0);
                    ai.txt_i = settings.getInt("it" + iditem + "_txt_i", 0);
                    ai.txt_f = settings.getInt("it" + iditem + "_txt_f", 0);
                    ai.txt_col = settings.getString("it" + iditem + "_txt_col", "");
                    ai.txt_u = settings.getInt("it" + iditem + "_txt_u", 0);
                    ai.txt_s = settings.getInt("it" + iditem + "_txt_s", 0);
                    fotos = settings.getString("it" + iditem + "_idfotos", "");
                    if (fotos.equals("")) {
                        ai.fotos_a = (int[][]) Array.newInstance(Integer.TYPE, new int[]{0, 0});
                    } else {
                        fotos_a = fotos.split(",");
                        ai.fotos_a = (int[][]) Array.newInstance(Integer.TYPE, new int[]{fotos_a.length, 2});
                        ai.fotos_str_a = new String[fotos_a.length];
                        for (String idfoto : fotos_a) {
                            int ord2 = settings.getInt("it_f" + idfoto + "_ord", 0);
                            ai.fotos_a[ord2 - 1][0] = Integer.parseInt(idfoto);
                            ai.fotos_a[ord2 - 1][1] = settings.getInt("it_f" + idfoto + "_modif", 1);
                            ai.fotos_str_a[ord2 - 1] = settings.getString("it_f" + idfoto + "_url", "");
                        }
                    }
                    s.and_items_a[ord - 1] = ai;
                }
            }
            this.secciones_a[ind] = s;
            this.secciones_alist.add(Integer.valueOf(s.id));
            if (!s.oculta) {
                this.menu_a_secciones[ord_menu] = ind;
                ord_menu++;
            }
        }
        String deeplink = "www.androidcreator.com/open299914/";
        if (!(i_orig == null || i_orig.getDataString() == null || !i_orig.getDataString().contains(deeplink))) {
            String dataString = i_orig.getDataString().replace("http://", "").replace("https://", "");
            if (dataString.length() > deeplink.length()) {
                String[] partes = dataString.split("/");
                String ref = partes[partes.length - 1];
                i = 0;
                while (i < idseccs_a.length) {
                    if (this.secciones_a[i].idgo.equalsIgnoreCase(ref)) {
                        int ord_aux = settings.getInt("s" + this.secciones_a[i].id + "_ord", 0) - 1;
                        if (ord_aux != -1) {
                            ord_secc_abrir = ord_aux;
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        int[] temp_a = this.menu_a_secciones;
        this.menu_a_secciones = new int[ord_menu];
        for (i = 0; i < ord_menu; i++) {
            this.menu_a_secciones[i] = temp_a[i];
        }
        if (ord_secc_abrir == 999) {
            ord_secc_abrir = 0;
        }
        this.oficinas_a = new Oficina[idofics_a.length];
        for (String idofic : idofics_a) {
            Oficina o = new Oficina();
            o.id = Integer.parseInt(idofic);
            o.titulo = settings.getString("o" + idofic + "_tit", "");
            o.dir1 = settings.getString("o" + idofic + "_dir1", "");
            o.dir2 = settings.getString("o" + idofic + "_dir2", "");
            o.cp = settings.getString("o" + idofic + "_cp", "");
            o.pob = settings.getString("o" + idofic + "_pob", "");
            o.prov = settings.getString("o" + idofic + "_prov", "");
            o.tel1 = settings.getString("o" + idofic + "_tel1", "");
            o.tel2 = settings.getString("o" + idofic + "_tel2", "");
            o.horario = settings.getString("o" + idofic + "_horario", "");
            o.nfotos = settings.getInt("o" + idofic + "_nfotos", 0);
            o.f31x = settings.getInt("o" + idofic + "_x", 0);
            o.f32y = settings.getInt("o" + idofic + "_y", 0);
            o.f33z = settings.getInt("o" + idofic + "_z", 0);
            o.email = settings.getString("o" + idofic + "_email", "");
            if (settings.getInt("o" + idofic + "_t_email", 0) == 0) {
                o.t_email = false;
            } else {
                o.t_email = true;
            }
            o.web = settings.getString("o" + idofic + "_web", "");
            if (settings.getInt("o" + idofic + "_chat", 0) == 0) {
                o.chat = false;
            } else {
                o.chat = true;
            }
            o.fotos_z = settings.getInt(new StringBuilder().append("o").append(idofic).append("_zoo").toString(), 1) == 1;
            fotos = settings.getString("o" + idofic + "_idfotos", "");
            if (!fotos.equals("")) {
                fotos_a = fotos.split(",");
                for (String idfoto2 : fotos_a) {
                    ord = settings.getInt("o_f" + idfoto2 + "_ord", 0);
                    boolean modif = settings.getBoolean("o_f" + idfoto2 + "_modif", true);
                    boolean modif_g = settings.getBoolean("o_f" + idfoto2 + "_modif_g", true);
                    int idfoto_int = Integer.parseInt(idfoto2);
                    if (ord == 1) {
                        o.f1_id = idfoto_int;
                        o.f1_modif = modif;
                        o.f1_g_modif = modif_g;
                    } else if (ord == 2) {
                        o.f2_id = idfoto_int;
                        o.f2_modif = modif;
                        o.f2_g_modif = modif_g;
                    } else if (ord == 3) {
                        o.f3_id = idfoto_int;
                        o.f3_modif = modif;
                        o.f3_g_modif = modif_g;
                    } else if (ord == 4) {
                        o.f4_id = idfoto_int;
                        o.f4_modif = modif;
                        o.f4_g_modif = modif_g;
                    }
                }
            }
            this.oficinas_a[settings.getInt("o" + idofic + "_ord", 0) - 1] = o;
        }
        return ord_secc_abrir;
    }

    void cargar_int(Context c) {
        if (toca_int == 1 || toca_int_chat == 1) {
            interstitial_glob = new InterstitialAd(c);
            interstitial_glob.setAdUnitId(admob_int_cod);
            interstitial_glob.setAdListener(new C11867());
            interstitial_glob.loadAd(new Builder().build());
        } else if (toca_int != 2 && toca_int_chat == 2) {
        }
    }

    void mostrar_int(Context c) {
        if (toca_int == 1 && interstitial_glob != null && interstitial_glob.isLoaded()) {
            interstitial_glob.show();
            toca_int = 0;
            toca_int_chat = 0;
        } else if (toca_int == 2) {
            if (appnext_glob_int == null && (hay_wifi(this) || hay_4g(this))) {
                appnext_glob_int = new Interstitial(getApplicationContext(), appnext_cod_int_ia);
                if (mute.booleanValue()) {
                    appnext_glob_int.setMute(true);
                }
                if (!autoplay.booleanValue()) {
                    appnext_glob_int.setAutoPlay(false);
                }
                appnext_glob_int.setBackButtonCanClose(true);
                appnext_glob_int.setSkipText(getResources().getString(C0627R.string.ad_saltar));
                appnext_glob_int.setButtonText(getResources().getString(C0627R.string.ad_instalar));
                appnext_glob_int.setOnAdLoadedCallback(new C11878());
                appnext_glob_int.setOnAdErrorCallback(new C11889());
                appnext_glob_int.setOnAdClosedCallback(new OnAdClosed() {
                    public void onAdClosed() {
                        config.appnext_glob_int = null;
                    }
                });
                appnext_glob_int.loadAd();
            }
            toca_int = 0;
            toca_int_chat = 0;
        }
    }

    void mostrar_int_chat(Context c) {
        if (toca_int_chat == 1 && interstitial_glob != null && interstitial_glob.isLoaded()) {
            interstitial_glob.show();
            toca_int = 0;
            toca_int_chat = 0;
        } else if (toca_int_chat == 2) {
            if (appnext_glob_int == null && (hay_wifi(this) || hay_4g(this))) {
                appnext_glob_int = new Interstitial(getApplicationContext(), appnext_cod_int_ia);
                if (mute.booleanValue()) {
                    appnext_glob_int.setMute(true);
                }
                if (!autoplay.booleanValue()) {
                    appnext_glob_int.setAutoPlay(false);
                }
                appnext_glob_int.setBackButtonCanClose(true);
                appnext_glob_int.setSkipText(getResources().getString(C0627R.string.ad_saltar));
                appnext_glob_int.setButtonText(getResources().getString(C0627R.string.ad_instalar));
                appnext_glob_int.setOnAdLoadedCallback(new OnAdLoaded() {
                    public void adLoaded() {
                        config.appnext_glob_int.showAd();
                    }
                });
                appnext_glob_int.setOnAdErrorCallback(new OnAdError() {
                    public void adError(String error) {
                        config.appnext_glob_int = null;
                    }
                });
                appnext_glob_int.setOnAdClosedCallback(new OnAdClosed() {
                    public void onAdClosed() {
                        config.appnext_glob_int = null;
                    }
                });
                appnext_glob_int.loadAd();
            }
            toca_int = 0;
            toca_int_chat = 0;
        }
    }

    public void toca_int(Context c, boolean forzar_admob) {
        int i = 2;
        mostrar_int(c);
        if (toca_int <= 0) {
            Editor editor;
            if (!forzar_admob) {
                if (this.admob_int_interv > 0) {
                    SharedPreferences settings = getSharedPreferences("sh", 0);
                    int n_imp = settings.getInt("n_imp", 0) + 1;
                    editor = settings.edit();
                    editor.putInt("n_imp", n_imp);
                    editor.commit();
                    if (n_imp % this.admob_int_interv == 0) {
                        editor.putInt("n_imp_chat", 0);
                        editor.commit();
                        if (admob_int_cod.equals("") || appnext_cod.equals("")) {
                            if (!admob_int_cod.equals("")) {
                                i = 1;
                            }
                            toca_int = i;
                        } else if (toca_int_chat > 0) {
                            toca_int = toca_int_chat;
                        } else if (our == 1) {
                            toca_int = 1;
                        } else {
                            toca_int = new Random().nextInt(2) + 1;
                        }
                        if (toca_int_chat == 0) {
                            cargar_int(c);
                            return;
                        }
                        return;
                    }
                }
                toca_int = 0;
            } else if (toca_int_chat != 2) {
                toca_int = 1;
                if (toca_int_chat == 0) {
                    cargar_int(c);
                }
                editor = getSharedPreferences("sh", 0).edit();
                editor.putInt("n_imp", 0);
                editor.putInt("n_imp_chat", 0);
                editor.commit();
            }
        }
    }

    public void toca_int_chat(Context c) {
        int i = 2;
        mostrar_int_chat(c);
        if (toca_int_chat <= 0) {
            if (this.admob_chat_interv > 0) {
                SharedPreferences settings = getSharedPreferences("sh", 0);
                int n_imp = settings.getInt("n_imp_chat", 0) + 1;
                Editor editor = settings.edit();
                editor.putInt("n_imp_chat", n_imp);
                editor.commit();
                if (n_imp % this.admob_chat_interv == 0) {
                    editor.putInt("n_imp", 0);
                    editor.commit();
                    if (admob_int_cod.equals("") || appnext_cod.equals("")) {
                        if (!admob_int_cod.equals("")) {
                            i = 1;
                        }
                        toca_int_chat = i;
                    } else if (toca_int > 0) {
                        toca_int_chat = toca_int;
                    } else if (our == 1) {
                        toca_int_chat = 1;
                    } else {
                        toca_int_chat = new Random().nextInt(2) + 1;
                    }
                    if (toca_int == 0) {
                        cargar_int(c);
                        return;
                    }
                    return;
                }
            }
            toca_int_chat = 0;
        }
    }

    public int toca_intentrar() {
        toca_int = 0;
        toca_int_chat = 0;
        if (this.admob_intentrar_interv <= 0) {
            return 0;
        }
        SharedPreferences settings = getSharedPreferences("sh", 0);
        int n_imp = settings.getInt("n_impentrada", 0) + 1;
        Editor editor = settings.edit();
        editor.putInt("n_impentrada", n_imp);
        editor.commit();
        if (n_imp % this.admob_intentrar_interv != 0 && n_imp != 1) {
            return 0;
        }
        editor.putInt("n_imp", 0);
        editor.putInt("n_imp_chat", 0);
        editor.commit();
        if (admob_int_cod.equals("") || appnext_cod.equals("")) {
            if (admob_int_cod.equals("")) {
                return 2;
            }
            return 1;
        } else if (our == 1) {
            return 2;
        } else {
            return new Random().nextInt(2) + 1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void notificar(android.content.Context r65, java.lang.String r66) {
        /*
        r4 = "sh";
        r12 = 0;
        r0 = r65;
        r59 = r0.getSharedPreferences(r4, r12);
        r29 = r59.edit();
        r4 = "\\\"";
        r12 = "\"";
        r0 = r66;
        r66 = r0.replace(r4, r12);
        r4 = "@";
        r0 = r66;
        r55 = r0.indexOf(r4);
        r55 = r55 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r42 = r0.substring(r1, r2);
        r4 = "PRIVADO";
        r0 = r42;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x0040;
    L_0x003f:
        return;
    L_0x0040:
        r4 = "-";
        r0 = r66;
        r55 = r0.indexOf(r4);
        r55 = r55 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r31 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r57 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r32 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r40 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r58 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r39 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r22 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r23 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r37 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r38 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r35 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r61 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r24 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r25 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r36 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r62 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r27 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r41 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "-";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r28 = r0.substring(r1, r2);
        r4 = "@";
        r0 = r66;
        r55 = r0.indexOf(r4);
        r4 = "@";
        r12 = r55 + 1;
        r0 = r66;
        r56 = r0.indexOf(r4, r12);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r10 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r50 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r4 = r0.substring(r1, r2);
        r12 = "\\'";
        r13 = "'";
        r6 = r4.replace(r12, r13);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r45 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r44 = r0.substring(r1, r2);
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r63 = r0.substring(r1, r2);
        r20 = "";
        r55 = r56 + 1;
        r4 = "@";
        r0 = r66;
        r1 = r55;
        r56 = r0.indexOf(r4, r1);	 Catch:{ Exception -> 0x0a94 }
        r0 = r66;
        r1 = r55;
        r2 = r56;
        r20 = r0.substring(r1, r2);	 Catch:{ Exception -> 0x0a94 }
    L_0x02a0:
        r4 = "COMSELF:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 != 0) goto L_0x02d2;
    L_0x02aa:
        r4 = "COMFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 != 0) goto L_0x02d2;
    L_0x02b4:
        r4 = "FGALFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 != 0) goto L_0x02d2;
    L_0x02be:
        r4 = "FPERFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 != 0) goto L_0x02d2;
    L_0x02c8:
        r4 = "DESCRFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x0521;
    L_0x02d2:
        r49 = r50;
        r11 = r10;
        r4 = "avisos";
        r12 = 0;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r12 = 2;
        if (r4 >= r12) goto L_0x003f;
    L_0x02e1:
        r4 = ":";
        r0 = r42;
        r43 = r0.split(r4);
        r4 = 1;
        r50 = r43[r4];
        r4 = 2;
        r6 = r43[r4];
        r52 = "notification";
        r0 = r65;
        r1 = r52;
        r47 = r0.getSystemService(r1);
        r47 = (android.app.NotificationManager) r47;
        r5 = "";
        r8 = 0;
        r9 = 0;
        r4 = "COMFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x044c;
    L_0x0309:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r50;
        r4 = r4.append(r0);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361851; // 0x7f0a003b float:1.8343466E38 double:1.0530326694E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r12 = " ";
        r4 = r4.append(r12);
        r0 = r49;
        r4 = r4.append(r0);
        r5 = r4.toString();
        r8 = 5;
        r9 = 4;
    L_0x033b:
        r21 = 0;
        r4 = "icohome";
        r0 = r65;
        r34 = r0.openFileInput(r4);	 Catch:{ Exception -> 0x0a91 }
        r4 = r34.getFD();	 Catch:{ Exception -> 0x0a91 }
        r21 = android.graphics.BitmapFactory.decodeFileDescriptor(r4);	 Catch:{ Exception -> 0x0a91 }
        r34.close();	 Catch:{ Exception -> 0x0a91 }
    L_0x0350:
        r4 = new android.support.v4.app.NotificationCompat$Builder;
        r0 = r65;
        r4.<init>(r0);
        r12 = 2130837652; // 0x7f020094 float:1.7280264E38 double:1.0527736807E-314;
        r4 = r4.setSmallIcon(r12);
        r4 = r4.setContentTitle(r5);
        r4 = r4.setContentText(r6);
        r12 = 1;
        r4 = r4.setAutoCancel(r12);
        r0 = r21;
        r46 = r4.setLargeIcon(r0);
        r4 = "avisos";
        r12 = 1;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        if (r4 != 0) goto L_0x0382;
    L_0x037c:
        r4 = 5;
        r0 = r46;
        r0.setDefaults(r4);
    L_0x0382:
        r7 = new android.content.Intent;
        r4 = hse.here2.preinicio.class;
        r0 = r65;
        r7.<init>(r0, r4);
        r4 = "abrir_perfil";
        r7.putExtra(r4, r11);
        r4 = "privados";
        r0 = r57;
        r7.putExtra(r4, r0);
        r4 = "nombre";
        r0 = r49;
        r7.putExtra(r4, r0);
        r4 = "coments";
        r0 = r24;
        r7.putExtra(r4, r0);
        r4 = "fnac_d";
        r0 = r37;
        r7.putExtra(r4, r0);
        r4 = "fnac_m";
        r0 = r38;
        r7.putExtra(r4, r0);
        r4 = "fnac_a";
        r0 = r35;
        r7.putExtra(r4, r0);
        r4 = "sexo";
        r0 = r61;
        r7.putExtra(r4, r0);
        r4 = "vfoto";
        r0 = r63;
        r7.putExtra(r4, r0);
        r4 = "p_fnac";
        r12 = java.lang.Integer.parseInt(r36);
        r7.putExtra(r4, r12);
        r4 = "p_sexo";
        r12 = java.lang.Integer.parseInt(r62);
        r7.putExtra(r4, r12);
        r4 = "p_descr";
        r12 = java.lang.Integer.parseInt(r27);
        r7.putExtra(r4, r12);
        r4 = "p_dist";
        r12 = java.lang.Integer.parseInt(r28);
        r7.putExtra(r4, r12);
        r4 = "coments_chat";
        r12 = "1";
        r0 = r25;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "galeria";
        r12 = "1";
        r0 = r41;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "privados_chat";
        r12 = "1";
        r0 = r58;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "fotos_perfil";
        r12 = java.lang.Integer.parseInt(r40);
        r7.putExtra(r4, r12);
        r4 = "fotos_chat";
        r12 = java.lang.Integer.parseInt(r39);
        r7.putExtra(r4, r12);
        r0 = r65;
        r4 = existe_notif(r0, r9, r10, r11);
        if (r4 != 0) goto L_0x0432;
    L_0x042d:
        r4 = r65;
        crear_notif(r4, r5, r6, r7, r8, r9, r10, r11);
    L_0x0432:
        r4 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r0 = r65;
        r26 = android.app.PendingIntent.getActivity(r0, r8, r7, r4);
        r0 = r46;
        r1 = r26;
        r0.setContentIntent(r1);
        r4 = r46.build();
        r0 = r47;
        r0.notify(r8, r4);
        goto L_0x003f;
    L_0x044c:
        r4 = "FGALFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x0489;
    L_0x0456:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r50;
        r4 = r4.append(r0);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361852; // 0x7f0a003c float:1.8343468E38 double:1.05303267E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r4 = r65.getResources();
        r12 = 2131361887; // 0x7f0a005f float:1.834354E38 double:1.053032687E-314;
        r6 = r4.getString(r12);
        r8 = 5;
        r9 = 5;
        goto L_0x033b;
    L_0x0489:
        r4 = "FPERFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x04c6;
    L_0x0493:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r50;
        r4 = r4.append(r0);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361853; // 0x7f0a003d float:1.834347E38 double:1.0530326704E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r4 = r65.getResources();
        r12 = 2131361887; // 0x7f0a005f float:1.834354E38 double:1.053032687E-314;
        r6 = r4.getString(r12);
        r8 = 5;
        r9 = 6;
        goto L_0x033b;
    L_0x04c6:
        r4 = "DESCRFAV:";
        r0 = r42;
        r4 = r0.startsWith(r4);
        if (r4 == 0) goto L_0x04f8;
    L_0x04d0:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r50;
        r4 = r4.append(r0);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361854; // 0x7f0a003e float:1.8343472E38 double:1.053032671E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r8 = 5;
        r9 = 7;
        goto L_0x033b;
    L_0x04f8:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r50;
        r4 = r4.append(r0);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361850; // 0x7f0a003a float:1.8343464E38 double:1.053032669E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r8 = 4;
        r9 = 8;
        goto L_0x033b;
    L_0x0521:
        r4 = "";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x0533;
    L_0x0529:
        r4 = "";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 != 0) goto L_0x003f;
    L_0x0533:
        r4 = "0";
        r4 = r10.equals(r4);
        if (r4 != 0) goto L_0x0565;
    L_0x053b:
        r4 = "";
        r4 = r10.equals(r4);
        if (r4 != 0) goto L_0x0565;
    L_0x0543:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r12 = "idusu";
        r13 = 0;
        r0 = r59;
        r12 = r0.getInt(r12, r13);
        r4 = r4.append(r12);
        r12 = "";
        r4 = r4.append(r12);
        r4 = r4.toString();
        r4 = r10.equals(r4);
        if (r4 != 0) goto L_0x003f;
    L_0x0565:
        r4 = "0";
        r4 = r10.equals(r4);
        if (r4 != 0) goto L_0x05de;
    L_0x056d:
        r4 = "";
        r4 = r10.equals(r4);
        if (r4 != 0) goto L_0x05de;
    L_0x0575:
        r64 = 0;
        r48 = r6;
        r4 = "";
        r4 = r6.equals(r4);
        if (r4 == 0) goto L_0x0583;
    L_0x0581:
        r48 = r20;
    L_0x0583:
        r4 = "idremit_ult";
        r12 = "";
        r0 = r59;
        r4 = r0.getString(r4, r12);
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x05be;
    L_0x0593:
        r4 = "mensaje_ult";
        r12 = "";
        r0 = r59;
        r4 = r0.getString(r4, r12);
        r0 = r48;
        r4 = r4.equals(r0);
        if (r4 == 0) goto L_0x05be;
    L_0x05a5:
        r12 = java.lang.System.currentTimeMillis();
        r4 = "f_ult";
        r14 = java.lang.System.currentTimeMillis();
        r0 = r59;
        r14 = r0.getLong(r4, r14);
        r12 = r12 - r14;
        r14 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 >= 0) goto L_0x05be;
    L_0x05bc:
        r64 = 1;
    L_0x05be:
        if (r64 != 0) goto L_0x003f;
    L_0x05c0:
        r4 = "f_ult";
        r12 = java.lang.System.currentTimeMillis();
        r0 = r29;
        r0.putLong(r4, r12);
        r4 = "idremit_ult";
        r0 = r29;
        r0.putString(r4, r10);
        r4 = "mensaje_ult";
        r0 = r29;
        r1 = r48;
        r0.putString(r4, r1);
        r29.commit();
    L_0x05de:
        r53 = 0;
        r4 = "1";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x083f;
    L_0x05ea:
        r53 = 1;
        r0 = r65;
        r1 = r50;
        anyadir_privado(r0, r10, r1);
    L_0x05f3:
        if (r53 == 0) goto L_0x003f;
    L_0x05f5:
        r4 = "0";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 != 0) goto L_0x0624;
    L_0x05ff:
        r4 = "1";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x06c7;
    L_0x0609:
        r4 = "activa";
        r12 = 0;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 == 0) goto L_0x06c7;
    L_0x0614:
        r4 = "idprivado";
        r12 = "0";
        r0 = r59;
        r4 = r0.getString(r4, r12);
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x06c7;
    L_0x0624:
        r4 = "f2_idfrase";
        r0 = r29;
        r1 = r42;
        r0.putString(r4, r1);
        r4 = "f2_id";
        r0 = r29;
        r0.putString(r4, r10);
        r4 = "f2_nombre";
        r0 = r29;
        r1 = r50;
        r0.putString(r4, r1);
        r4 = "f2_privados";
        r0 = r29;
        r1 = r57;
        r0.putString(r4, r1);
        r4 = "f2_frase";
        r0 = r29;
        r0.putString(r4, r6);
        r4 = "f2_fcrea";
        r0 = r29;
        r1 = r32;
        r0.putString(r4, r1);
        r4 = "f2_b64";
        r0 = r29;
        r1 = r20;
        r0.putString(r4, r1);
        r4 = "f2_idtema";
        r0 = r29;
        r1 = r45;
        r0.putString(r4, r1);
        r4 = "f2_vfoto";
        r0 = r29;
        r1 = r63;
        r0.putString(r4, r1);
        r4 = "f2_fnac_d";
        r0 = r29;
        r1 = r37;
        r0.putString(r4, r1);
        r4 = "f2_fnac_m";
        r0 = r29;
        r1 = r38;
        r0.putString(r4, r1);
        r4 = "f2_fnac_a";
        r0 = r29;
        r1 = r35;
        r0.putString(r4, r1);
        r4 = "f2_sexo";
        r0 = r29;
        r1 = r61;
        r0.putString(r4, r1);
        r4 = "f2_coments";
        r0 = r29;
        r1 = r24;
        r0.putString(r4, r1);
        r4 = "f2_ultimas";
        r12 = 0;
        r0 = r29;
        r0.putBoolean(r4, r12);
        r4 = "f2_ts";
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r14 = java.lang.System.currentTimeMillis();
        r12 = r12.append(r14);
        r13 = "";
        r12 = r12.append(r13);
        r12 = r12.toString();
        r0 = r29;
        r0.putString(r4, r12);
        r29.commit();
    L_0x06c7:
        r4 = "0";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x08f4;
    L_0x06d1:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r12 = "activa";
        r4 = r4.append(r12);
        r0 = r45;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r12 = 0;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 != 0) goto L_0x08f4;
    L_0x06ef:
        r4 = "avisos";
        r12 = 0;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r12 = 2;
        if (r4 >= r12) goto L_0x08f4;
    L_0x06fb:
        r54 = 1;
        r4 = "notif_int";
        r12 = 0;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r12 = 9999; // 0x270f float:1.4012E-41 double:4.94E-320;
        if (r4 != r12) goto L_0x0884;
    L_0x070a:
        r54 = 0;
    L_0x070c:
        if (r54 == 0) goto L_0x003f;
    L_0x070e:
        r4 = "f_ult_notif";
        r12 = java.lang.System.currentTimeMillis();
        r0 = r29;
        r0.putLong(r4, r12);
        r29.commit();
        r52 = "notification";
        r0 = r65;
        r1 = r52;
        r47 = r0.getSystemService(r1);
        r47 = (android.app.NotificationManager) r47;
        r5 = r50;
        r4 = "";
        r4 = r6.equals(r4);
        if (r4 != 0) goto L_0x08c5;
    L_0x0732:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4 = r4.append(r5);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361907; // 0x7f0a0073 float:1.834358E38 double:1.053032697E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r12 = ":";
        r4 = r4.append(r12);
        r5 = r4.toString();
    L_0x075a:
        r21 = 0;
        r4 = "icohome";
        r0 = r65;
        r34 = r0.openFileInput(r4);	 Catch:{ Exception -> 0x0a8e }
        r4 = r34.getFD();	 Catch:{ Exception -> 0x0a8e }
        r21 = android.graphics.BitmapFactory.decodeFileDescriptor(r4);	 Catch:{ Exception -> 0x0a8e }
        r34.close();	 Catch:{ Exception -> 0x0a8e }
    L_0x076f:
        r4 = new android.support.v4.app.NotificationCompat$Builder;
        r0 = r65;
        r4.<init>(r0);
        r12 = 2130837652; // 0x7f020094 float:1.7280264E38 double:1.0527736807E-314;
        r4 = r4.setSmallIcon(r12);
        r4 = r4.setContentTitle(r5);
        r4 = r4.setContentText(r6);
        r12 = 1;
        r4 = r4.setAutoCancel(r12);
        r0 = r21;
        r46 = r4.setLargeIcon(r0);
        r4 = "avisos";
        r12 = 1;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        if (r4 != 0) goto L_0x07a1;
    L_0x079b:
        r4 = 5;
        r0 = r46;
        r0.setDefaults(r4);
    L_0x07a1:
        r7 = new android.content.Intent;
        r4 = hse.here2.preinicio.class;
        r0 = r65;
        r7.<init>(r0, r4);
        r4 = "notif_idtema";
        r0 = r45;
        r7.putExtra(r4, r0);
        r4 = "notif_idsecc";
        r0 = r44;
        r7.putExtra(r4, r0);
        r4 = "fotos_perfil";
        r12 = java.lang.Integer.parseInt(r40);
        r7.putExtra(r4, r12);
        r4 = "fnac";
        r12 = java.lang.Integer.parseInt(r36);
        r7.putExtra(r4, r12);
        r4 = "sexo";
        r12 = java.lang.Integer.parseInt(r62);
        r7.putExtra(r4, r12);
        r4 = "descr";
        r12 = java.lang.Integer.parseInt(r27);
        r7.putExtra(r4, r12);
        r4 = "dist";
        r12 = java.lang.Integer.parseInt(r28);
        r7.putExtra(r4, r12);
        r4 = "privados";
        r12 = "1";
        r0 = r58;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "coments";
        r12 = "1";
        r0 = r25;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "galeria";
        r12 = "1";
        r0 = r41;
        r12 = r0.equals(r12);
        r7.putExtra(r4, r12);
        r4 = "fotos_chat";
        r12 = java.lang.Integer.parseInt(r39);
        r7.putExtra(r4, r12);
        r4 = "c1";
        r0 = r22;
        r7.putExtra(r4, r0);
        r4 = "c2";
        r0 = r23;
        r7.putExtra(r4, r0);
        r4 = 1;
        r12 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r0 = r65;
        r26 = android.app.PendingIntent.getActivity(r0, r4, r7, r12);
        r0 = r46;
        r1 = r26;
        r0.setContentIntent(r1);
        r4 = 1;
        r12 = r46.build();
        r0 = r47;
        r0.notify(r4, r12);
        goto L_0x003f;
    L_0x083f:
        r4 = "idprivado";
        r12 = "0";
        r0 = r59;
        r4 = r0.getString(r4, r12);
        r12 = "0";
        r4 = r4.equals(r12);
        if (r4 == 0) goto L_0x0853;
    L_0x0851:
        r53 = 1;
    L_0x0853:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r12 = "chat";
        r4 = r4.append(r12);
        r0 = r44;
        r4 = r4.append(r0);
        r12 = "_validado";
        r4 = r4.append(r12);
        r4 = r4.toString();
        r12 = 1;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 == 0) goto L_0x003f;
    L_0x0877:
        r4 = "pwd_validado";
        r12 = 1;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 != 0) goto L_0x05f3;
    L_0x0882:
        goto L_0x003f;
    L_0x0884:
        r4 = "f_ult_notif";
        r12 = 0;
        r0 = r59;
        r12 = r0.getLong(r4, r12);
        r33 = java.lang.Long.valueOf(r12);
        r12 = r33.longValue();
        r14 = 0;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 <= 0) goto L_0x070c;
    L_0x089c:
        r4 = "notif_int";
        r12 = 0;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r4 = r4 * 60;
        r4 = r4 * 60;
        r4 = r4 * 1000;
        r12 = (long) r4;
        r51 = java.lang.Long.valueOf(r12);
        r12 = java.lang.System.currentTimeMillis();
        r14 = r33.longValue();
        r12 = r12 - r14;
        r14 = r51.longValue();
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 >= 0) goto L_0x070c;
    L_0x08c1:
        r54 = 0;
        goto L_0x070c;
    L_0x08c5:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4 = r4.append(r5);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361886; // 0x7f0a005e float:1.8343537E38 double:1.0530326867E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r4 = r65.getResources();
        r12 = 2131361887; // 0x7f0a005f float:1.834354E38 double:1.053032687E-314;
        r6 = r4.getString(r12);
        goto L_0x075a;
    L_0x08f4:
        r4 = "1";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x0976;
    L_0x08fe:
        r4 = "privados";
        r12 = 1;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r12 = 1;
        if (r4 != r12) goto L_0x0976;
    L_0x090a:
        r4 = "activa";
        r12 = 0;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 == 0) goto L_0x0976;
    L_0x0915:
        r4 = "idprivado";
        r12 = "0";
        r0 = r59;
        r4 = r0.getString(r4, r12);
        r4 = r4.equals(r10);
        if (r4 != 0) goto L_0x0976;
    L_0x0925:
        r12 = java.lang.System.currentTimeMillis();
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r14 = "f_ult_";
        r4 = r4.append(r14);
        r4 = r4.append(r10);
        r4 = r4.toString();
        r14 = 0;
        r0 = r59;
        r14 = r0.getLong(r4, r14);
        r12 = r12 - r14;
        r14 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 <= 0) goto L_0x003f;
    L_0x094c:
        r4 = "accion";
        r12 = 0;
        r0 = r65;
        r60 = r0.getSharedPreferences(r4, r12);
        r30 = r60.edit();
        r4 = "accion";
        r12 = 1;
        r0 = r30;
        r0.putInt(r4, r12);
        r4 = "id_remit";
        r0 = r30;
        r0.putString(r4, r10);
        r4 = "nombre_remit";
        r0 = r30;
        r1 = r50;
        r0.putString(r4, r1);
        r30.commit();
        goto L_0x003f;
    L_0x0976:
        r4 = "1";
        r0 = r31;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x003f;
    L_0x0980:
        r4 = "privados";
        r12 = 1;
        r0 = r59;
        r4 = r0.getInt(r4, r12);
        r12 = 1;
        if (r4 != r12) goto L_0x003f;
    L_0x098c:
        r4 = "activa";
        r12 = 0;
        r0 = r59;
        r4 = r0.getBoolean(r4, r12);
        if (r4 != 0) goto L_0x003f;
    L_0x0997:
        r12 = java.lang.System.currentTimeMillis();
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r14 = "f_ult_";
        r4 = r4.append(r14);
        r4 = r4.append(r10);
        r4 = r4.toString();
        r14 = 0;
        r0 = r59;
        r14 = r0.getLong(r4, r14);
        r12 = r12 - r14;
        r14 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r4 <= 0) goto L_0x003f;
    L_0x09be:
        r21 = 0;
        r4 = "icohome";
        r0 = r65;
        r34 = r0.openFileInput(r4);	 Catch:{ Exception -> 0x0a8b }
        r4 = r34.getFD();	 Catch:{ Exception -> 0x0a8b }
        r21 = android.graphics.BitmapFactory.decodeFileDescriptor(r4);	 Catch:{ Exception -> 0x0a8b }
        r34.close();	 Catch:{ Exception -> 0x0a8b }
    L_0x09d3:
        r52 = "notification";
        r0 = r65;
        r1 = r52;
        r47 = r0.getSystemService(r1);
        r47 = (android.app.NotificationManager) r47;
        r5 = r50;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r4 = r4.append(r5);
        r12 = " ";
        r4 = r4.append(r12);
        r12 = r65.getResources();
        r13 = 2131361900; // 0x7f0a006c float:1.8343565E38 double:1.0530326936E-314;
        r12 = r12.getString(r13);
        r4 = r4.append(r12);
        r5 = r4.toString();
        r4 = new android.support.v4.app.NotificationCompat$Builder;
        r0 = r65;
        r4.<init>(r0);
        r12 = 2130837652; // 0x7f020094 float:1.7280264E38 double:1.0527736807E-314;
        r4 = r4.setSmallIcon(r12);
        r12 = r65.getResources();
        r13 = 2131361895; // 0x7f0a0067 float:1.8343555E38 double:1.053032691E-314;
        r12 = r12.getString(r13);
        r4 = r4.setContentTitle(r12);
        r4 = r4.setContentText(r5);
        r12 = 1;
        r4 = r4.setAutoCancel(r12);
        r0 = r21;
        r46 = r4.setLargeIcon(r0);
        r4 = 5;
        r0 = r46;
        r0.setDefaults(r4);
        r7 = new android.content.Intent;
        r4 = hse.here2.preinicio.class;
        r0 = r65;
        r7.<init>(r0, r4);
        r4 = "id_remit";
        r7.putExtra(r4, r10);
        r4 = "nombre_remit";
        r0 = r50;
        r7.putExtra(r4, r0);
        r4 = 2;
        r12 = "0";
        r0 = r65;
        r4 = existe_notif(r0, r4, r10, r12);
        if (r4 != 0) goto L_0x0a6f;
    L_0x0a55:
        r4 = r65.getResources();
        r12 = 2131361895; // 0x7f0a0067 float:1.8343555E38 double:1.053032691E-314;
        r13 = r4.getString(r12);
        r16 = 3;
        r17 = 2;
        r19 = "0";
        r12 = r65;
        r14 = r5;
        r15 = r7;
        r18 = r10;
        crear_notif(r12, r13, r14, r15, r16, r17, r18, r19);
    L_0x0a6f:
        r4 = 3;
        r12 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        r0 = r65;
        r26 = android.app.PendingIntent.getActivity(r0, r4, r7, r12);
        r0 = r46;
        r1 = r26;
        r0.setContentIntent(r1);
        r4 = 3;
        r12 = r46.build();
        r0 = r47;
        r0.notify(r4, r12);
        goto L_0x003f;
    L_0x0a8b:
        r4 = move-exception;
        goto L_0x09d3;
    L_0x0a8e:
        r4 = move-exception;
        goto L_0x076f;
    L_0x0a91:
        r4 = move-exception;
        goto L_0x0350;
    L_0x0a94:
        r4 = move-exception;
        goto L_0x02a0;
        */
        throw new UnsupportedOperationException("Method not decompiled: hse.here2.config.notificar(android.content.Context, java.lang.String):void");
    }

    public static void anyadir_privado(Context c, String idusu_conv, String nombre_conv) {
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        boolean puesto = false;
        int i = 0;
        while (!puesto && i < 100) {
            puesto = settings.getString("privado" + i + "_id", "").equals(idusu_conv);
            i++;
        }
        Editor e = settings.edit();
        if (puesto) {
            i--;
            e.putString("privado" + i + "_id", idusu_conv);
            e.putString("privado" + i + "_nombre", nombre_conv);
            e.putLong("privado" + i + "_fultconex", System.currentTimeMillis());
        } else {
            i = 0;
            while (!puesto && i < 100) {
                if (!settings.contains("privado" + i + "_id")) {
                    e.putString("privado" + i + "_id", idusu_conv);
                    e.putString("privado" + i + "_nombre", nombre_conv);
                    e.putLong("privado" + i + "_fultconex", System.currentTimeMillis());
                    puesto = true;
                }
                i++;
            }
        }
        e.commit();
    }

    public static void elim_privado(Context c, String idelim) {
        SharedPreferences s = c.getSharedPreferences("sh", 0);
        boolean encontrado = false;
        int i = 0;
        while (!encontrado && i < 100) {
            if (s.getString("privado" + i + "_id", "").equals(idelim)) {
                Editor e = s.edit();
                e.remove("privado" + i + "_id");
                e.commit();
                encontrado = true;
            }
            i++;
        }
    }

    public static String convertir_fecha(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmm");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        try {
            Date value = formatter.parse(dateString);
            SimpleDateFormat formatter2 = new SimpleDateFormat("ddMMyyHHmm");
            formatter2.setTimeZone(TimeZone.getDefault());
            return formatter2.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertir_fecha_inv(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyHHmm");
        formatter.setTimeZone(TimeZone.getDefault());
        try {
            Date value = formatter.parse(dateString);
            SimpleDateFormat formatter2 = new SimpleDateFormat("ddMMyyHHmm");
            formatter2.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
            return formatter2.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getRegistrationId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("sh", 0);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.equals("")) {
            return "";
        }
        if (prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE) != getAppVersion(context)) {
            return "";
        }
        return registrationId;
    }

    public static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return "";
        }
    }

    @TargetApi(21)
    public static void progress_color(ProgressBar pb, String color) {
        if (VERSION.SDK_INT >= 21 && color != null && !color.equals("") && !color.equals("#")) {
            int[][] states = new int[4][];
            states[0] = new int[]{16842910};
            states[1] = new int[]{-16842910};
            states[2] = new int[]{-16842912};
            states[3] = new int[]{16842919};
            pb.setIndeterminateTintList(new ColorStateList(states, new int[]{Color.parseColor("#" + color), Color.parseColor("#" + color), Color.parseColor("#" + color), Color.parseColor("#" + color)}));
        }
    }

    @TargetApi(21)
    public static void edittext_color(EditText et, Boolean txt_claro, String c_focused) {
        if (VERSION.SDK_INT >= 21 && c_focused != null && !c_focused.equals("") && !c_focused.equals("#")) {
            String c_txt = "000000";
            String c_nofocused = "212121";
            if (txt_claro.booleanValue()) {
                c_txt = "FFFFFF";
                c_nofocused = "FAFAFA";
            }
            et.setTextColor(Color.parseColor("#" + c_txt));
            states = new int[2][];
            states[0] = new int[]{16842908};
            states[1] = new int[]{-16842908};
            et.setBackgroundTintList(new ColorStateList(states, new int[]{Color.parseColor("#" + c_focused), Color.parseColor("#" + c_nofocused)}));
        }
    }

    @TargetApi(21)
    public static void checkbox_color(CheckBox cb, String c_focused) {
        if (VERSION.SDK_INT >= 21 && c_focused != null && !c_focused.equals("") && !c_focused.equals("#")) {
            states = new int[2][];
            states[0] = new int[]{16842908};
            states[1] = new int[]{-16842908};
            cb.setButtonTintList(new ColorStateList(states, new int[]{Color.parseColor("#" + c_focused), Color.parseColor("#" + c_focused)}));
        }
    }

    @TargetApi(21)
    public static void aplicar_color_top(Activity a, String color) {
        if (VERSION.SDK_INT > 20) {
            float[] hsv = new float[3];
            Color.colorToHSV(Color.parseColor("#" + color), hsv);
            hsv[2] = hsv[2] * 0.8f;
            int color_aux = Color.HSVToColor(hsv);
            a.getWindow().addFlags(Integer.MIN_VALUE);
            a.getWindow().setStatusBarColor(color_aux);
        }
    }

    public static boolean isNetworkAvailable(Context c) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) c.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static ArrayList<rss_item> tratar_rss(InputStream is) {
        int currentTag = -1;
        ArrayList<rss_item> postDataList = new ArrayList();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);
            rss_item pdData = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
            DateFormat dateFormat_2 = SimpleDateFormat.getDateTimeInstance(3, 3);
            DateFormat dateFormat_3 = SimpleDateFormat.getDateInstance(3);
            Calendar calendar = Calendar.getInstance();
            boolean img_encontrada_en_content = false;
            for (int eventType = xpp.getEventType(); eventType != 1; eventType = xpp.next()) {
                if (eventType != 0) {
                    if (eventType == 2) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            pdData = new rss_item();
                            currentTag = 0;
                            img_encontrada_en_content = false;
                        } else if (xpp.getName().equalsIgnoreCase(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE)) {
                            currentTag = 1;
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            currentTag = 2;
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            currentTag = 3;
                        } else if (xpp.getName().equalsIgnoreCase(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION)) {
                            currentTag = 4;
                        } else if (xpp.getName().equalsIgnoreCase("encoded") && xpp.getPrefix().equalsIgnoreCase("content")) {
                            currentTag = 6;
                        } else {
                            currentTag = 5;
                        }
                    } else if (eventType == 3) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            if (pdData.postDate != null) {
                                try {
                                    Date postDate = dateFormat.parse(pdData.postDate.replaceAll("\\p{Cntrl}", ""));
                                    calendar.setTime(postDate);
                                    if (calendar.get(11) == 0 && calendar.get(12) == 0) {
                                        pdData.postDate = dateFormat_3.format(postDate);
                                    } else {
                                        pdData.postDate = dateFormat_2.format(postDate);
                                    }
                                } catch (ParseException e) {
                                    pdData.postDate = null;
                                    e.printStackTrace();
                                }
                            }
                            postDataList.add(pdData);
                        } else {
                            currentTag = 0;
                        }
                    } else if (eventType == 4) {
                        String content = xpp.getText().trim();
                        if (pdData != null) {
                            switch (currentTag) {
                                case 1:
                                    if (content.length() != 0 && (pdData.postTitle == null || pdData.postTitle.length() <= 0)) {
                                        pdData.postTitle = Html.fromHtml(content).toString();
                                        break;
                                    }
                                case 2:
                                    if (content.length() != 0 && (pdData.postLink == null || pdData.postLink.length() <= 0)) {
                                        pdData.postLink = content;
                                        break;
                                    }
                                case 3:
                                    if (content.length() != 0 && (pdData.postDate == null || pdData.postDate.length() <= 0)) {
                                        pdData.postDate = content;
                                        break;
                                    }
                                case 5:
                                    if (pdData.postThumbUrl != null) {
                                        break;
                                    }
                                case 4:
                                    if (pdData.postThumbUrl != null && img_encontrada_en_content) {
                                        break;
                                    }
                                case 6:
                                    if (content.length() == 0) {
                                        break;
                                    }
                                    int pos1 = content.indexOf(".jpg");
                                    int pos3 = content.indexOf(".png");
                                    if (pos1 == -1) {
                                        pos1 = 999999;
                                    }
                                    if (pos3 == -1) {
                                        pos3 = 999999;
                                    }
                                    int pos = Math.min(pos1, pos3);
                                    if (pos == 999999) {
                                        break;
                                    }
                                    int pos0 = pos;
                                    String car = content.substring(pos0, pos0 + 1);
                                    while (pos0 > 1 && !car.equals("'") && !car.equals("\"") && !car.equals("=")) {
                                        pos0--;
                                        car = content.substring(pos0, pos0 + 1);
                                    }
                                    if (pos0 <= 1) {
                                        break;
                                    }
                                    String url_aux = content.substring(pos0 + 1, pos + 4);
                                    if (!url_aux.startsWith(HttpHost.DEFAULT_SCHEME_NAME)) {
                                        break;
                                    }
                                    boolean ok_aux = true;
                                    int pos2 = content.indexOf("&gt;", pos + 4);
                                    pos3 = content.indexOf(">", pos + 4);
                                    if (pos2 == -1) {
                                        pos2 = 999999;
                                    }
                                    if (pos3 == -1) {
                                        pos3 = 999999;
                                    }
                                    pos2 = Math.min(pos2, pos3);
                                    if (pos2 != 999999) {
                                        String cad_aux = content.substring(pos + 4, pos2);
                                        if (!((cad_aux.indexOf("width=\"1\"") == -1 || cad_aux.indexOf("height=\"1\"") == -1) && (cad_aux.indexOf("width='1'") == -1 || cad_aux.indexOf("height='1'") == -1))) {
                                            ok_aux = false;
                                        }
                                    }
                                    if (!ok_aux) {
                                        break;
                                    }
                                    pdData.postThumbUrl = URLDecoder.decode(url_aux, "UTF-8");
                                    if (currentTag != 6) {
                                        break;
                                    }
                                    img_encontrada_en_content = true;
                                    break;
                                default:
                                    break;
                            }
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            }
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (XmlPullParserException e4) {
            e4.printStackTrace();
        } catch (android.net.ParseException e5) {
            e5.printStackTrace();
        }
        return postDataList;
    }

    public static Intent completar_externo(Intent in, Bundle ex) {
        in.putExtra("externo", true);
        in.putExtra("idchat", ex.getInt("idchat"));
        in.putExtra("idtema", ex.getInt("idtema"));
        in.putExtra("fotos_perfil", ex.getInt("fotos_perfil"));
        in.putExtra("fnac", ex.getInt("fnac"));
        in.putExtra("sexo", ex.getInt("sexo"));
        in.putExtra("descr", ex.getInt("descr"));
        in.putExtra("dist", ex.getInt("dist"));
        in.putExtra("privados", ex.getBoolean("privados"));
        in.putExtra("coments", ex.getBoolean("coments"));
        in.putExtra("galeria", ex.getBoolean("galeria"));
        in.putExtra("fotos_chat", ex.getInt("fotos_chat"));
        in.putExtra("c1", ex.getString("c1"));
        in.putExtra("c2", ex.getString("c2"));
        return in;
    }

    public static String completar_externo_url(String url, Bundle ex) {
        return (((((((((((((url + "&externo=true") + "&idchat=" + ex.getInt("idchat")) + "&idtema=" + ex.getInt("idtema")) + "&fotos_perfil=" + ex.getInt("fotos_perfil")) + "&fnac=" + ex.getInt("fnac")) + "&sexo=" + ex.getInt("sexo")) + "&descr=" + ex.getInt("descr")) + "&dist=" + ex.getInt("dist")) + "&privados=" + ex.getBoolean("privados")) + "&coments=" + ex.getBoolean("coments")) + "&galeria=" + ex.getBoolean("galeria")) + "&fotos_chat=" + ex.getInt("fotos_chat")) + "&c1=" + ex.getString("c1")) + "&c2=" + ex.getString("c2");
    }

    public static Bitmap crop(Bitmap bitmap, int redondez) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(0.0f, 0.0f, (float) bitmap.getWidth(), (float) bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        if (redondez == 1) {
            redondez = 5;
        } else if (redondez == 2) {
            redondez = 10;
        } else if (redondez == 3) {
            redondez = 20;
        }
        canvas.drawRoundRect(rectF, (float) redondez, (float) redondez, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    static void confirmar_exit(Context c) {
        mostrar_toast(Toast.makeText(c, C0627R.string.confirm_exit, 0));
    }

    static void onResume_global(Context c) {
        if (finalizar_app) {
            ((Activity) c).finish();
        }
        if (((Activity) c).findViewById(C0627R.id.ad_pb) != null) {
            ((Activity) c).findViewById(C0627R.id.ad_pb).setVisibility(8);
        }
        mostrar_notif_noleidas(c);
    }

    static void finalizar_app(Context c) {
    }

    static String aplicar_color_dialog(String c_fondo, String c_txt) {
        if (c_fondo == null || c_txt == null || c_fondo.equals("") || c_txt.equals("")) {
            return "";
        }
        if (VERSION.SDK_INT <= 20) {
            return "";
        }
        if (c_txt.equals("FFFFFFFF") && esClaro("#" + c_fondo)) {
            return "FF000000";
        }
        if (!c_txt.equals("FF000000") || esClaro("#" + c_fondo)) {
            return c_txt;
        }
        return "FFFFFFFF";
    }

    static boolean hay_wifi(Context c) {
        NetworkInfo mWifi = null;
        try {
            mWifi = ((ConnectivityManager) c.getSystemService("connectivity")).getNetworkInfo(1);
        } catch (Exception e) {
        }
        if (mWifi == null || !mWifi.isConnected()) {
            return false;
        }
        return true;
    }

    static boolean hay_4g(Context c) {
        return ((TelephonyManager) c.getSystemService("phone")).getNetworkType() == 13;
    }

    protected String obtenerFuente(int tipo) {
        String font = "";
        if (tipo == 2) {
            return "CaviarDreams.ttf";
        }
        if (tipo == 3) {
            return "Pacifico.ttf";
        }
        if (tipo == 4) {
            return "Sansation-Regular.ttf";
        }
        if (tipo == 5) {
            return "Walkway_Bold.ttf";
        }
        return font;
    }

    protected void setDefaultFont(Context context, String staticTypefaceFieldName, int tipo) {
        Typeface regular;
        if (tipo == 0) {
            regular = Typeface.defaultFromStyle(0);
        } else if (tipo == 1) {
            try {
                regular = Typeface.createFromFile(new File(getFilesDir(), "font"));
            } catch (Exception e) {
                regular = Typeface.defaultFromStyle(0);
            }
        } else {
            String font = obtenerFuente(tipo);
            if (font.equals("")) {
                regular = Typeface.defaultFromStyle(0);
            } else {
                regular = Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
            }
        }
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected void replaceFont(String staticTypefaceFieldName, Typeface newTypeface) {
        try {
            Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    protected void establecerFuente(int tipo) {
        if (tf_monospace == null) {
            tf_monospace = Typeface.create(Typeface.MONOSPACE, 0);
            tf_serif = Typeface.create(Typeface.SERIF, 0);
            tf_sans_serif = Typeface.create(Typeface.SANS_SERIF, 0);
        }
        setDefaultFont(this, "DEFAULT", tipo);
        setDefaultFont(this, "MONOSPACE", tipo);
        setDefaultFont(this, "SERIF", tipo);
        setDefaultFont(this, "SANS_SERIF", tipo);
    }

    static void mostrar_toast(Toast t) {
        try {
            ((TextView) ((ViewGroup) t.getView()).getChildAt(0)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
        t.show();
    }

    Intent obtener_intent(String url) {
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
            return new Intent("android.intent.action.DIAL", Uri.parse("tel:" + tel));
        } else if (url_l.startsWith("mailto:") || url_l.startsWith("http://mailto:")) {
            String email;
            String subject = "";
            texte = "";
            if (url_l.startsWith("mailto:")) {
                email = url.substring(7).trim();
            } else {
                email = url.substring(14).trim();
            }
            if (email.equals("")) {
                return new Intent("android.intent.action.SENDTO", Uri.parse(url));
            }
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
                } catch (Exception e) {
                }
                emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
            }
            if (!texte.equals("")) {
                try {
                    texte = URLDecoder.decode(texte, "UTF-8");
                } catch (Exception e2) {
                }
                emailIntent.putExtra("android.intent.extra.TEXT", texte);
            }
            return Intent.createChooser(emailIntent, getResources().getString(C0627R.string.enviar_email));
        } else if (url_l.startsWith("smsto:") || url_l.startsWith("http://smsto:")) {
            texte = "";
            if (url_l.startsWith("smsto:")) {
                tel = url.substring(6).trim();
            } else {
                tel = url.substring(13).trim();
            }
            if (tel.equals("")) {
                return new Intent("android.intent.action.SENDTO", Uri.parse(url));
            }
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
            Intent smsIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + tel));
            if (!texte.equals("")) {
                try {
                    texte = URLDecoder.decode(texte, "UTF-8");
                } catch (Exception e3) {
                }
                smsIntent.putExtra("sms_body", texte);
            }
            return smsIntent;
        } else if (url_l.startsWith("vnd.youtube:")) {
            int n = url.indexOf("?");
            String idvideo = "";
            if (n > 0) {
                idvideo = url.substring(12, n);
            } else {
                idvideo = url.substring(12);
            }
            return new Intent("android.intent.action.VIEW", Uri.parse("http://www.youtube.com/watch?v=" + idvideo));
        } else if (url_l.endsWith(".mp3")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), "audio/*");
            return intent;
        } else if (!url_l.endsWith(".mp4") && !url_l.endsWith(".3gp")) {
            return new Intent("android.intent.action.VIEW", Uri.parse(url));
        } else {
            intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(url), "video/*");
            return intent;
        }
    }

    static int crear_notif(Context c, String title, String text, Intent intent, int numnotif, int tipo, String idusu1, String idusu2) {
        if (idusu1.equals(null) || idusu1.equals("")) {
            idusu1 = "0";
        }
        if (idusu2.equals(null) || idusu2.equals("")) {
            idusu2 = "0";
        }
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        Editor editor = settings.edit();
        int id_ultnotif = settings.getInt("id_ultnotif", 0) + 1;
        intent.putExtra("idnotif_marcar", id_ultnotif);
        editor.putInt("id_ultnotif", id_ultnotif);
        editor.putBoolean("hatenidonotif", true);
        editor.putLong("notif_" + id_ultnotif + "_fcrea", System.currentTimeMillis());
        editor.putString("notif_" + id_ultnotif + "_title", title);
        editor.putString("notif_" + id_ultnotif + "_text", text);
        editor.putString("notif_" + id_ultnotif + "_intent", intent.toUri(0));
        editor.putInt("notif_" + id_ultnotif + "_numnotif", numnotif);
        editor.putBoolean("notif_" + id_ultnotif + "_leida", false);
        editor.putInt("notif_" + id_ultnotif + "_tipo", tipo);
        editor.putString("notif_" + id_ultnotif + "_idusu1", idusu1);
        editor.putString("notif_" + id_ultnotif + "_idusu2", idusu2);
        if (id_ultnotif > 100) {
            editor.remove("notif_" + (id_ultnotif - 100) + "_fcrea");
            editor.remove("notif_" + (id_ultnotif - 100) + "_title");
            editor.remove("notif_" + (id_ultnotif - 100) + "_text");
            editor.remove("notif_" + (id_ultnotif - 100) + "_intent");
            editor.remove("notif_" + (id_ultnotif - 100) + "_numnotif");
            editor.remove("notif_" + (id_ultnotif - 100) + "_leida");
            editor.remove("notif_" + (id_ultnotif - 100) + "_tipo");
            editor.remove("notif_" + (id_ultnotif - 100) + "_idusu1");
            editor.remove("notif_" + (id_ultnotif - 100) + "_idusu2");
        }
        editor.commit();
        act_notif_noleidas(c);
        return id_ultnotif;
    }

    static void act_notif_noleidas(Context c) {
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        Editor e = settings.edit();
        int n = 0;
        int noleidas = 0;
        for (int i = settings.getInt("id_ultnotif", 0); i > 0; i--) {
            if (!settings.getBoolean("notif_" + i + "_leida", false)) {
                noleidas++;
            }
            n++;
            if (n > 99) {
                break;
            }
        }
        e.putInt("notif_noleidas", noleidas);
        e.commit();
    }

    static void mostrar_notif_noleidas(Context c) {
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        int ico_tv = 0;
        int ico_notif_ord = settings.getInt("ico_notif_ord", 0);
        if (ico_notif_ord == 1) {
            ico_tv = C0627R.id.ico_tv_1;
        } else if (ico_notif_ord == 2) {
            ico_tv = C0627R.id.ico_tv_2;
        } else if (ico_notif_ord == 3) {
            ico_tv = C0627R.id.ico_tv_3;
        } else if (ico_notif_ord == 4) {
            ico_tv = C0627R.id.ico_tv_4;
        }
        if (ico_tv > 0) {
            try {
                TextView tv = (TextView) ((Activity) c).findViewById(ico_tv);
                int noleidas = settings.getInt("notif_noleidas", 0);
                if (noleidas > 99) {
                    noleidas = 99;
                }
                if (noleidas <= 0 || settings.getInt("ico_notif_idsec", 0) != 0) {
                    tv.setVisibility(8);
                    return;
                }
                int px_h;
                int px_v = dp_to_px(c, 3);
                if (noleidas < 10) {
                    px_h = dp_to_px(c, 8);
                } else {
                    px_h = dp_to_px(c, 4);
                }
                tv.setPadding(px_h, px_v, px_h, px_v);
                tv.setText("" + noleidas);
                tv.setVisibility(0);
            } catch (Exception e) {
            }
        }
    }

    static boolean existe_notif(Context c, int tipo, String idusu1, String idusu2) {
        if (idusu1.equals(null) || idusu1.equals("")) {
            idusu1 = "0";
        }
        if (idusu2.equals(null) || idusu2.equals("")) {
            idusu2 = "0";
        }
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        int n = 0;
        int i = settings.getInt("id_ultnotif", 0);
        while (i > 0) {
            if (!settings.getBoolean("notif_" + i + "_leida", false) && settings.getInt("notif_" + i + "_tipo", 0) == tipo && settings.getString("notif_" + i + "_idusu1", "").equals(idusu1) && settings.getString("notif_" + i + "_idusu2", "").equals(idusu2)) {
                return true;
            }
            n++;
            if (n > 99) {
                return false;
            }
            i--;
        }
        return false;
    }

    static void elim_notifs(Context c) {
        SharedPreferences settings = c.getSharedPreferences("sh", 0);
        Editor editor = settings.edit();
        int n = 0;
        for (int i = settings.getInt("id_ultnotif", 0); i > 0; i--) {
            editor.remove("notif_" + i + "_fcrea");
            editor.remove("notif_" + i + "_title");
            editor.remove("notif_" + i + "_text");
            editor.remove("notif_" + i + "_intent");
            editor.remove("notif_" + i + "_numnotif");
            editor.remove("notif_" + i + "_leida");
            editor.remove("notif_" + i + "_tipo");
            editor.remove("notif_" + i + "_idusu1");
            editor.remove("notif_" + i + "_idusu2");
            n++;
            if (n > 99) {
                break;
            }
        }
        editor.putInt("id_ultnotif", 0);
        editor.putInt("notif_noleidas", 0);
        editor.commit();
        act_notif_noleidas(c);
    }

    static int dp_to_px(Context c, int dp) {
        return (int) (((double) (((float) dp) * c.getResources().getDisplayMetrics().density)) + 0.5d);
    }

    static float px_to_dp(Context c, float px) {
        return px / (((float) c.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    boolean rewarded(Context c, View v, String cbtn, ProgressDialog dialog_cargando, RewardedVideoAd mAd, RewardedVideo mAd_appnext) {
        boolean mostrar;
        SharedPreferences settings = getSharedPreferences("sh", 0);
        if ((this.appnext_rew_cod == null || this.appnext_rew_cod == "") && (this.admob_rew_cod == null || this.admob_rew_cod == "")) {
            mostrar = false;
        } else {
            Long fult_rew = Long.valueOf(settings.getLong("fult_rew", 0));
            if (fult_rew.longValue() == 0) {
                mostrar = true;
            } else if (this.rew_modo == 0) {
                mostrar = false;
            } else if (this.rew_modo == 1) {
                mostrar = true;
            } else {
                int dias = 30;
                if (this.rew_modo == 2) {
                    dias = 1;
                } else if (this.rew_modo == 3) {
                    dias = 3;
                } else if (this.rew_modo == 4) {
                    dias = 7;
                } else if (this.rew_modo == 5) {
                    dias = 15;
                }
                if (System.currentTimeMillis() - fult_rew.longValue() > ((long) ((((dias * 24) * 60) * 60) * 1000))) {
                    mostrar = true;
                } else {
                    mostrar = false;
                }
            }
        }
        if (!mostrar) {
            return false;
        }
        Integer v_id = (Integer) v.getTag(C0627R.id.TAG_IDSECC);
        if (v_id.intValue() == this.ico_share_ord + 1000) {
            if (this.ico_share_idsec > 1) {
                v_id = Integer.valueOf(this.secciones_alist.indexOf(Integer.valueOf(this.ico_share_idsec)));
            } else {
                v_id = Integer.valueOf(-1);
            }
        } else if (v_id.intValue() == this.ico_exit_ord + 1000) {
            if (this.ico_exit_idsec > 1) {
                v_id = Integer.valueOf(this.secciones_alist.indexOf(Integer.valueOf(this.ico_exit_idsec)));
            } else {
                v_id = Integer.valueOf(-1);
            }
        } else if (v_id.intValue() == this.ico_notif_ord + 1000) {
            if (this.ico_notif_idsec > 1) {
                v_id = Integer.valueOf(this.secciones_alist.indexOf(Integer.valueOf(this.ico_notif_idsec)));
            } else {
                v_id = Integer.valueOf(-1);
            }
        } else if (v_id.intValue() == this.ico_ofics_ord + 1000) {
            if (this.ico_ofics_idsec > 1) {
                v_id = Integer.valueOf(this.secciones_alist.indexOf(Integer.valueOf(this.ico_ofics_idsec)));
            } else if (this.ico_ofics_idsec == 0) {
                v_id = Integer.valueOf(this.ind_secc_ofics);
            } else {
                v_id = Integer.valueOf(-1);
            }
        } else if (v_id.intValue() == this.ico_busc_ord + 1000) {
            if (this.ico_busc_idsec > 1) {
                v_id = Integer.valueOf(this.secciones_alist.indexOf(Integer.valueOf(this.ico_busc_idsec)));
            } else if (this.ico_busc_idsec == 0) {
                v_id = Integer.valueOf(this.ind_secc_buscador);
            } else {
                v_id = Integer.valueOf(-1);
            }
        }
        if (v_id.intValue() < 0 || v_id.intValue() >= this.secciones_a.length || !this.secciones_a[v_id.intValue()].rewarded) {
            return false;
        }
        String msg = getResources().getString(C0627R.string.rewarded_msg);
        if (!this.rew_msg.equals("")) {
            msg = this.rew_msg;
        }
        final ProgressDialog progressDialog = dialog_cargando;
        final RewardedVideo rewardedVideo = mAd_appnext;
        final Context context = c;
        final RewardedVideoAd rewardedVideoAd = mAd;
        final AlertDialog d_aux = new AlertDialog.Builder(c).setNegativeButton(C0627R.string.cancelar, null).setPositiveButton(C0627R.string.aceptar, new DialogInterface.OnClickListener() {

            class C06491 implements OnShowListener {
                C06491() {
                }

                public void onShow(DialogInterface dialog) {
                    config.progress_color((ProgressBar) progressDialog.findViewById(16908301), config.this.c_icos);
                }
            }

            public void mostrar_cargando() {
                progressDialog.setMessage(config.this.getString(C0627R.string.cargando));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                if (VERSION.SDK_INT > 20 && !config.this.c_icos.equals("")) {
                    progressDialog.setOnShowListener(new C06491());
                }
                progressDialog.show();
            }

            public void onClick(DialogInterface dialog, int id) {
                boolean mostrar_appnext_rew = false;
                boolean mostrar_admob_rew = false;
                if (!(config.this.appnext_rew_cod == null || config.this.appnext_rew_cod.equals(""))) {
                    mostrar_appnext_rew = true;
                }
                if (!(config.this.admob_rew_cod == null || config.this.admob_rew_cod.equals(""))) {
                    mostrar_admob_rew = true;
                }
                if (mostrar_appnext_rew && mostrar_admob_rew && new Random().nextInt(2) + 1 == 1) {
                    mostrar_appnext_rew = false;
                }
                if (mostrar_appnext_rew) {
                    mostrar_cargando();
                    rewardedVideo.setBackButtonCanClose(true);
                    rewardedVideo.setShowClose(true);
                    rewardedVideo.setButtonText(config.this.getResources().getString(C0627R.string.ad_instalar));
                    rewardedVideo.setOnAdLoadedCallback((OnAdLoaded) context);
                    rewardedVideo.setOnAdClosedCallback((OnAdClosed) context);
                    rewardedVideo.setOnAdErrorCallback((OnAdError) context);
                    rewardedVideo.setOnVideoEndedCallback((OnVideoEnded) context);
                    rewardedVideo.loadAd();
                } else if (mostrar_admob_rew) {
                    mostrar_cargando();
                    rewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) context);
                    rewardedVideoAd.loadAd(config.this.admob_rew_cod, new Builder().build());
                }
            }
        }).setMessage(msg).create();
        if (!cbtn.equals("")) {
            final String str = cbtn;
            d_aux.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface arg0) {
                    d_aux.getButton(-1).setTextColor(Color.parseColor("#" + str));
                    d_aux.getButton(-2).setTextColor(Color.parseColor("#" + str));
                }
            });
        }
        d_aux.show();
        try {
            ((TextView) d_aux.findViewById(16908299)).setTypeface(Typeface.MONOSPACE);
        } catch (Exception e) {
        }
        return true;
    }

    @TargetApi(11)
    void fdescargar(String url, String mimetype, String nombre_archivo) {
        String url_l = url.toLowerCase(Locale.getDefault());
        Request request = new Request(Uri.parse(url));
        if (VERSION.SDK_INT > 10) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
        }
        if (mimetype.equals("")) {
            if (url_l.endsWith(".mp3")) {
                mimetype = "audio/mpeg";
            } else if (url_l.endsWith(".mp4")) {
                mimetype = "video/mp4";
            } else if (url_l.endsWith(".apk")) {
                mimetype = "application/vnd.android.package-archive";
            }
        }
        if (!mimetype.equals("")) {
            try {
                request.setMimeType(mimetype);
            } catch (Exception e) {
            }
        }
        if (nombre_archivo.equals("") && (url_l.endsWith(".mp3") || url_l.endsWith(".mp4") || url_l.endsWith(".apk"))) {
            try {
                nombre_archivo = url.substring(url.lastIndexOf(47) + 1);
            } catch (Exception e2) {
            }
        }
        if (!nombre_archivo.equals("")) {
            try {
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombre_archivo);
            } catch (Exception e3) {
            }
        }
        ((DownloadManager) getSystemService("download")).enqueue(request);
        Toast.makeText(getApplicationContext(), getResources().getString(C0627R.string.downloading), 1).show();
    }

    static void rew_visto(Context c) {
        Editor ed = c.getSharedPreferences("sh", 0).edit();
        ed.putLong("fult_rew", System.currentTimeMillis());
        ed.putInt("n_imp", 0);
        ed.putInt("n_imp_chat", 0);
        ed.commit();
        toca_int = 0;
        toca_int_chat = 0;
    }
}
