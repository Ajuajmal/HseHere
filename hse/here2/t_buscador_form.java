package hse.here2;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class t_buscador_form extends FragmentActivity implements OnClickListener, RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    AdView adView;
    int anyo = 0;
    boolean atras_pulsado = false;
    String cbtn;
    int dia = 0;
    ProgressDialog dialog_cargando;
    boolean es_root;
    Bundle extras;
    boolean finalizar = false;
    config globales;
    int[] id_cats_a;
    int[] id_orden_a;
    int idcat = 0;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    ListView mDrawerList;
    int mes = 0;
    View v_abrir_secc;

    class C07391 implements OnItemClickListener {
        C07391() {
        }

        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (t_buscador_form.this.globales.slider_v > 0) {
                position--;
            }
            view.setId(t_buscador_form.this.globales.menu_a_secciones[position]);
            view.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(t_buscador_form.this.globales.menu_a_secciones[position]));
            t_buscador_form.this.onClick(view);
        }
    }

    @SuppressLint({"ValidFragment"})
    public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int year;
            int month;
            int day;
            Calendar c = Calendar.getInstance();
            if (t_buscador_form.this.dia == 0) {
                year = c.get(1);
                month = c.get(2);
                day = c.get(5);
            } else {
                year = t_buscador_form.this.anyo;
                month = t_buscador_form.this.mes;
                day = t_buscador_form.this.dia;
            }
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String fecha;
            t_buscador_form.this.dia = day;
            t_buscador_form.this.mes = month;
            t_buscador_form.this.anyo = year;
            boolean ok = true;
            Date d = null;
            try {
                d = DateFormat.getDateInstance(3, Locale.US).parse((t_buscador_form.this.mes + 1) + "/" + t_buscador_form.this.dia + "/" + t_buscador_form.this.anyo);
            } catch (Exception e) {
                ok = false;
            }
            if (ok) {
                fecha = DateFormat.getDateInstance().format(d);
            } else {
                fecha = "";
            }
            ((TextView) t_buscador_form.this.findViewById(C0627R.id.c_busc_antiguedad)).setText(fecha);
            t_buscador_form.this.findViewById(C0627R.id.tl_busc_antiguedad).setVisibility(0);
            ((ImageView) t_buscador_form.this.findViewById(C0627R.id.iv_antiguedad_limpiar)).setVisibility(0);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        Drawable d;
        Spinner spinner;
        CharSequence[] itemArray;
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        this.extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            z = this.extras != null && this.extras.containsKey("es_root") && this.extras.getBoolean("es_root", false);
            this.es_root = z;
        } else {
            z = savedInstanceState.containsKey("es_root") && savedInstanceState.getBoolean("es_root", false);
            this.es_root = z;
        }
        this.cbtn = config.aplicar_color_dialog(this.globales.c1_prods, this.globales.c_icos_prods);
        if (VERSION.SDK_INT > 12 && !config.esClaro("#" + this.globales.c1_prods)) {
            setTheme(C0627R.style.holonolight);
        }
        super.onCreate(savedInstanceState);
        setContentView(C0627R.layout.t_buscador_form);
        incluir_menu_pre();
        config hse_here2_config = this.globales;
        z = this.extras != null && this.extras.containsKey("ad_entrar");
        hse_here2_config.toca_int(this, z);
        this.adView = this.globales.mostrar_banner(this, false);
        getWindow().setSoftInputMode(2);
        if (!this.globales.prods_tit.equals("")) {
            ((TextView) findViewById(C0627R.id.tv_tit)).setText(this.globales.prods_tit);
        }
        if (!this.globales.c_tit_prods.equals("")) {
            ((TextView) findViewById(C0627R.id.tv_tit)).setTextColor(Color.parseColor("#" + this.globales.c_tit_prods));
        }
        if (!this.globales.c_sep_prods.equals("")) {
            findViewById(C0627R.id.v_sep).setBackgroundColor(Color.parseColor("#" + this.globales.c_sep_prods));
        }
        if (!this.globales.c_ico_sep_prods.equals("")) {
            d = getResources().getDrawable(C0627R.drawable.search_white);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_ico_sep_prods), Mode.MULTIPLY);
            ((ImageView) findViewById(C0627R.id.iv_search)).setImageDrawable(d);
        }
        if (!this.globales.c_txt_prods.equals("")) {
            int c_aux = Color.parseColor("#" + this.globales.c_txt_prods);
            ((TextView) findViewById(C0627R.id.tv_busc_texto)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.tv_busc_cat)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.c_busc_cat)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.tv_busc_precio)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.tv_busc_antiguedad)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.c_busc_antiguedad)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.tv_busc_orden)).setTextColor(c_aux);
            ((TextView) findViewById(C0627R.id.busc_divisa)).setTextColor(c_aux);
            if (VERSION.SDK_INT > 20) {
                config.edittext_color((EditText) findViewById(C0627R.id.c_busc_texto), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_prods)), this.globales.c_icos_2_prods);
                config.edittext_color((EditText) findViewById(C0627R.id.c_busc_precio), Boolean.valueOf(config.esClaro("#" + this.globales.c_txt_prods)), this.globales.c_icos_2_prods);
            }
        }
        if (this.globales.busc_texto) {
            findViewById(C0627R.id.tr_texto).setVisibility(0);
        }
        if (this.globales.busc_cat) {
            SQLiteDatabase db = new bd(getApplicationContext()).getReadableDatabase();
            Cursor c = db.rawQuery("SELECT _id FROM cats WHERE idcat>0 LIMIT 1", null);
            if (c.moveToFirst()) {
                findViewById(C0627R.id.c_busc_cat).setVisibility(0);
                ImageView iv = (ImageView) findViewById(C0627R.id.iv_cat);
                d = getResources().getDrawable(C0627R.drawable.tree);
                d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_prods), Mode.MULTIPLY);
                iv.setImageDrawable(d);
                iv.setOnClickListener(this);
                iv.setVisibility(0);
                iv = (ImageView) findViewById(C0627R.id.iv_cat_limpiar);
                d = getResources().getDrawable(C0627R.drawable.reload);
                d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_2_prods), Mode.MULTIPLY);
                iv.setImageDrawable(d);
                iv.setOnClickListener(this);
                findViewById(C0627R.id.tl_busc_cat).setOnClickListener(this);
            } else {
                spinner = (Spinner) findViewById(C0627R.id.sp_busc_cat);
                c = db.rawQuery("SELECT * FROM cats ORDER BY descr", null);
                if (c.moveToFirst()) {
                    itemArray = new CharSequence[(c.getCount() + 1)];
                    itemArray[0] = "Todo";
                    this.id_cats_a = new int[(c.getCount() + 1)];
                    this.id_cats_a[0] = 0;
                    while (!c.isAfterLast()) {
                        itemArray[c.getPosition() + 1] = c.getString(c.getColumnIndex("descr"));
                        this.id_cats_a[c.getPosition() + 1] = c.getInt(c.getColumnIndex("_id"));
                        c.moveToNext();
                    }
                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, 17367048, new ArrayList(Arrays.asList(itemArray)));
                    adapter.setDropDownViewResource(17367049);
                    spinner.setAdapter(adapter);
                    spinner.setVisibility(0);
                    findViewById(C0627R.id.iv_cat).setVisibility(8);
                }
            }
            c.close();
            db.close();
            findViewById(C0627R.id.tr_cat).setVisibility(0);
        }
        if (this.globales.busc_precio) {
            ((TextView) findViewById(C0627R.id.busc_divisa)).setText(Html.fromHtml(this.globales.divisa));
            findViewById(C0627R.id.tr_precio).setVisibility(0);
        }
        if (this.globales.busc_antiguedad) {
            iv = (ImageView) findViewById(C0627R.id.iv_antiguedad);
            d = getResources().getDrawable(C0627R.drawable.calendar);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_prods), Mode.MULTIPLY);
            iv.setImageDrawable(d);
            iv.setOnClickListener(this);
            iv = (ImageView) findViewById(C0627R.id.iv_antiguedad_limpiar);
            d = getResources().getDrawable(C0627R.drawable.reload);
            d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_2_prods), Mode.MULTIPLY);
            iv.setImageDrawable(d);
            iv.setOnClickListener(this);
            findViewById(C0627R.id.tl_busc_antiguedad).setOnClickListener(this);
            findViewById(C0627R.id.tr_antiguedad).setVisibility(0);
        }
        if (this.globales.ord_texto || this.globales.ord_precio || this.globales.ord_antiguedad) {
            spinner = (Spinner) findViewById(C0627R.id.c_busc_orden);
            int tam = 0;
            if (this.globales.ord_texto) {
                tam = 0 + 1;
            }
            if (this.globales.ord_precio) {
                tam++;
            }
            if (this.globales.ord_antiguedad) {
                tam++;
            }
            itemArray = new CharSequence[tam];
            this.id_orden_a = new int[tam];
            int ind = 0;
            int ind_def = 0;
            if (this.globales.ord_texto) {
                itemArray[0] = getString(C0627R.string.titulo);
                this.id_orden_a[0] = 1;
                if (this.globales.ord_def.indexOf("TITULO") != -1) {
                    ind_def = 0;
                }
                ind = 0 + 1;
            }
            if (this.globales.ord_precio) {
                itemArray[ind] = getString(C0627R.string.precio);
                this.id_orden_a[ind] = 2;
                if (this.globales.ord_def.indexOf("PRECIO") != -1) {
                    ind_def = ind;
                }
                ind++;
            }
            if (this.globales.ord_antiguedad) {
                itemArray[ind] = getString(C0627R.string.antiguedad);
                this.id_orden_a[ind] = 3;
                if (this.globales.ord_def.indexOf("FANTIGUEDAD") != -1) {
                    ind_def = ind;
                }
                ind++;
            }
            adapter = new ArrayAdapter(this, 17367048, new ArrayList(Arrays.asList(itemArray)));
            adapter.setDropDownViewResource(17367049);
            spinner.setAdapter(adapter);
            spinner.setSelection(ind_def);
            ((ToggleButton) findViewById(C0627R.id.togglebutton)).setChecked(this.globales.ord_def.contains("ASC"));
            findViewById(C0627R.id.tr_orden).setVisibility(0);
        }
        if (config.esClaro("#" + this.globales.c_icos_2_prods)) {
            findViewById(C0627R.id.iv_btn_fondo_n).setVisibility(0);
        } else {
            findViewById(C0627R.id.iv_btn_fondo_b).setVisibility(0);
        }
        d = getResources().getDrawable(C0627R.drawable.buscar_btn);
        d.setColorFilter(Color.parseColor("#" + this.globales.c_icos_2_prods), Mode.MULTIPLY);
        ImageView iv_enviar = (ImageView) findViewById(C0627R.id.button1);
        iv_enviar.setImageDrawable(d);
        iv_enviar.setOnClickListener(this);
        if (!this.globales.c1_prods.equals("") && !this.globales.c2_prods.equals("")) {
            findViewById(C0627R.id.ll_princ).setBackgroundDrawable(new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#" + this.globales.c1_prods), Color.parseColor("#" + this.globales.c2_prods)}));
        }
    }

    public void onClick(View v) {
        if (v.getId() == C0627R.id.button1) {
            Intent i = new Intent(this, t_buscador.class);
            if (this.globales.busc_texto) {
                i.putExtra("texto", ((EditText) findViewById(C0627R.id.c_busc_texto)).getText().toString());
            }
            if (this.globales.busc_cat) {
                Spinner sp = (Spinner) findViewById(C0627R.id.sp_busc_cat);
                if (sp.getVisibility() == 0) {
                    i.putExtra("idcat", this.id_cats_a[sp.getSelectedItemPosition()]);
                } else {
                    i.putExtra("idcat", this.idcat);
                }
            }
            if (this.globales.busc_precio) {
                i.putExtra("precio", ((EditText) findViewById(C0627R.id.c_busc_precio)).getText().toString());
            }
            if (this.globales.busc_antiguedad) {
                i.putExtra("dia", this.dia);
                i.putExtra("mes", this.mes + 1);
                i.putExtra("anyo", this.anyo);
            }
            if (this.globales.ord_texto || this.globales.ord_precio || this.globales.ord_antiguedad) {
                i.putExtra("orden", this.id_orden_a[((Spinner) findViewById(C0627R.id.c_busc_orden)).getSelectedItemPosition()]);
                i.putExtra("orden_tipo", ((ToggleButton) findViewById(C0627R.id.togglebutton)).isChecked());
            } else {
                int orden_aux;
                if (this.globales.ord_def.indexOf("TITULO") != -1) {
                    orden_aux = 1;
                } else if (this.globales.ord_def.indexOf("PRECIO") != -1) {
                    orden_aux = 2;
                } else {
                    orden_aux = 3;
                }
                i.putExtra("orden", orden_aux);
                i.putExtra("orden_tipo", this.globales.ord_def.contains("ASC"));
            }
            startActivityForResult(i, 0);
        } else if (v.getId() == C0627R.id.iv_cat || v.getId() == C0627R.id.tl_busc_cat) {
            startActivityForResult(new Intent(this, cats.class), 0);
        } else if (v.getId() == C0627R.id.iv_cat_limpiar) {
            this.idcat = 0;
            findViewById(C0627R.id.tl_busc_cat).setVisibility(8);
            ((TextView) findViewById(C0627R.id.c_busc_cat)).setText("");
            ((ImageView) findViewById(C0627R.id.iv_cat_limpiar)).setVisibility(8);
        } else if (v.getId() == C0627R.id.iv_antiguedad || v.getId() == C0627R.id.tl_busc_antiguedad) {
            DatePickerFragment newFragment = new DatePickerFragment();
            newFragment.setCancelable(true);
            newFragment.show(getSupportFragmentManager(), "datePicker");
        } else if (v.getId() == C0627R.id.iv_antiguedad_limpiar) {
            this.dia = 0;
            this.mes = 0;
            this.anyo = 0;
            findViewById(C0627R.id.tl_busc_antiguedad).setVisibility(8);
            ((TextView) findViewById(C0627R.id.c_busc_antiguedad)).setText("");
            ((ImageView) findViewById(C0627R.id.iv_antiguedad_limpiar)).setVisibility(8);
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
        if (resultCode != -1) {
            return;
        }
        if (data != null && data.hasExtra("idcat")) {
            Bundle b = data.getExtras();
            this.idcat = b.getInt("idcat", 0);
            ((TextView) findViewById(C0627R.id.c_busc_cat)).setText(b.getString("cat"));
            findViewById(C0627R.id.tl_busc_cat).setVisibility(0);
            ((ImageView) findViewById(C0627R.id.iv_cat_limpiar)).setVisibility(0);
        } else if (data != null && data.hasExtra("finalizar") && data.getExtras().getBoolean("finalizar")) {
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
            this.mDrawerList.setOnItemClickListener(new C07391());
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

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
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
