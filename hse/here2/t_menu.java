package hse.here2;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.appnext.ads.fullscreen.RewardedVideo;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.callbacks.OnAdLoaded;
import com.appnext.core.callbacks.OnVideoEnded;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class t_menu extends ListActivity implements RewardedVideoAdListener, OnAdLoaded, OnAdClosed, OnVideoEnded, OnAdError {
    String cbtn;
    ProgressDialog dialog_cargando;
    boolean finalizar = false;
    config globales;
    RewardedVideoAd mAd;
    RewardedVideo mAd_appnext;
    boolean mAd_visto = false;
    int position_abrir_secc;
    int[] values_ind;

    public void onCreate(Bundle icicle) {
        this.globales = (config) getApplicationContext();
        if (this.globales.c1 == null) {
            this.globales.recuperar_vars();
        }
        if (this.globales.ind_secc_sel_2 != -1) {
            this.cbtn = config.aplicar_color_dialog(this.globales.c1, this.globales.c_icos);
        } else {
            this.cbtn = config.aplicar_color_dialog(this.globales.secciones_a[this.globales.ind_secc_sel_2].c1, this.globales.c_icos);
        }
        super.onCreate(icicle);
        config.aplicar_color_top(this, this.globales.c1);
        int j = 0;
        String[] values = new String[this.globales.nsecc_visibles];
        this.values_ind = new int[this.globales.nsecc_visibles];
        for (int i = 0; i < this.globales.secciones_a.length; i++) {
            if (!this.globales.secciones_a[i].oculta) {
                values[j] = this.globales.secciones_a[i].titulo;
                this.values_ind[j] = i;
                j++;
            }
        }
        setListAdapter(new ArrayAdapter(this, 17367043, values));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        v.setId(this.globales.menu_a_secciones[position]);
        v.setTag(C0627R.id.TAG_IDSECC, Integer.valueOf(this.globales.menu_a_secciones[position]));
        if ((this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals("")) && (this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            abrir_secc(position);
            return;
        }
        if (!(this.globales.appnext_rew_cod == null || this.globales.appnext_rew_cod.equals(""))) {
            this.mAd_appnext = new RewardedVideo(this, this.globales.appnext_rew_cod);
        }
        if (!(this.globales.admob_rew_cod == null || this.globales.admob_rew_cod.equals(""))) {
            this.mAd = MobileAds.getRewardedVideoAdInstance(this);
        }
        this.dialog_cargando = new ProgressDialog(this);
        this.position_abrir_secc = position;
        if (!this.globales.rewarded(this, v, this.cbtn, this.dialog_cargando, this.mAd, this.mAd_appnext)) {
            abrir_secc(position);
        }
    }

    public void abrir_secc(int position) {
        ResultGetIntent rgi = this.globales.crear_rgi(Integer.valueOf(this.values_ind[position]), this);
        this.finalizar = true;
        Intent data = new Intent();
        data.putExtra("finalizar", rgi.finalizar);
        setResult(-1, data);
        if (this.globales.tipomenu != 2) {
            rgi.f34i.putExtra("es_root", true);
        }
        startActivity(rgi.f34i);
        finish();
    }

    public void onStop() {
        super.onStop();
        if (this.finalizar) {
            finish();
        }
    }

    public void adLoaded() {
        this.dialog_cargando.cancel();
        this.mAd_appnext.showAd();
    }

    public void onAdClosed() {
        if (this.mAd_visto) {
            abrir_secc(this.position_abrir_secc);
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
            abrir_secc(this.position_abrir_secc);
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
