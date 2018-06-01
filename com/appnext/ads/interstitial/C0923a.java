package com.appnext.ads.interstitial;

import android.content.Context;
import android.os.Build.VERSION;
import com.appnext.ads.C0150b;
import com.appnext.core.Ad;
import com.appnext.core.AppnextAd;
import com.appnext.core.C0241a;
import com.appnext.core.C0249b;
import com.appnext.core.C0266f;
import com.appnext.core.C0269h;
import com.appnext.core.webview.AppnextWebView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class C0923a extends C0249b {
    private static final int cA = 30;
    private static C0923a dh;
    private HashMap<String, InterstitialAd> cB = new HashMap();

    public static C0923a aq() {
        if (dh == null) {
            dh = new C0923a();
        }
        return dh;
    }

    private C0923a() {
    }

    protected String mo1206a(Context context, Ad ad, String str) {
        String str2;
        C0266f.m201a(ad != null ? ad.getTID() : "300", ad != null ? ad.getVID() : "2.0.3.459", ad != null ? ad.getAUID() : "600", str, "", C0150b.bA, "sdk", "", "");
        StringBuilder append = new StringBuilder().append("https://global.appnext.com").append("/offerWallApi.aspx?ext=t&pimp=1&type=json&igroup=sdk&m=1&osid=100&auid=").append(ad != null ? ad.getAUID() : "600").append("&id=").append(str).append("&cnt=").append(30).append("&vid=").append(ad != null ? ad.getVID() : "2.0.3.459").append("&tid=").append(ad != null ? ad.getTID() : "300").append("&cat=");
        if (ad == null) {
            str2 = "";
        } else {
            str2 = ad.getCategories();
        }
        append = append.append(str2).append("&pbk=");
        if (ad == null) {
            str2 = "";
        } else {
            str2 = ad.getPostback();
        }
        return append.append(str2).append("&did=").append(C0266f.m207w(context)).append("&devn=").append(C0266f.cf()).append("&dosv=").append(VERSION.SDK_INT).append("&dct=").append(C0266f.m209y(context)).append("&dds=").append((int) C0266f.cg()).append("&packageId=").append(context.getPackageName()).append("&rnd=").append(new Random().nextInt()).toString();
    }

    protected void mo1207a(Context context, Ad ad, C0241a c0241a) throws Exception {
        AppnextWebView.m241B(context).m251a(((Interstitial) ad).getPageUrl(), null);
    }

    protected boolean mo1211a(Context context, C0269h c0269h) {
        return m406a(context, (AppnextAd) c0269h);
    }

    protected void mo1208a(Ad ad, String str, String str2) {
        if (ad != null) {
            C0266f.m201a(ad.getTID(), ad.getVID(), ad.getAUID(), str2, str, C0150b.bz, "sdk", "", "");
            return;
        }
        C0266f.m201a("300", "2.0.3.459", "600", str2, str, C0150b.bz, "sdk", "", "");
    }

    protected <T> void mo1209a(String str, Ad ad, T t) {
        C0266f.m201a(ad.getTID(), ad.getVID(), ad.getAUID(), str, "", C0150b.by, "sdk", "", "");
    }

    protected boolean m416b(Ad ad) {
        return mo1212a(ad) && m163d(ad).m143g() != null && m163d(ad).m143g().size() > 0;
    }

    protected ArrayList<AppnextAd> m415b(Context context, Ad ad, String str) {
        if (m163d(ad) == null) {
            return null;
        }
        ArrayList g = m163d(ad).m143g();
        if (g == null) {
            return null;
        }
        AppnextAd a = m408a(context, g, str, ad);
        if (a == null) {
            au(ad.getPlacementID());
            a = m408a(context, g, str, ad);
        }
        if (a != null) {
            return m405a(g, a);
        }
        return null;
    }

    protected String m417c(Context context, Ad ad, String str) {
        if (m163d(ad) == null) {
            return null;
        }
        ArrayList g = m163d(ad).m143g();
        if (g == null) {
            return null;
        }
        AppnextAd a = m408a(context, g, str, ad);
        if (a == null) {
            au(ad.getPlacementID());
            a = m408a(context, g, str, ad);
        }
        if (a != null) {
            return mo1232c(m405a(g, a));
        }
        return null;
    }

    private ArrayList<AppnextAd> m405a(ArrayList<AppnextAd> arrayList, AppnextAd appnextAd) {
        arrayList.remove(appnextAd);
        arrayList.add(0, appnextAd);
        return arrayList;
    }

    protected String mo1232c(ArrayList<AppnextAd> arrayList) {
        return super.mo1232c((ArrayList) arrayList);
    }

    protected void mo1210a(String str, String str2) {
        super.mo1210a(str, str2);
        if (this.cB.containsKey(str2)) {
            this.cB.remove(str2);
        }
    }

    private boolean hasVideo(AppnextAd appnextAd) {
        return (appnextAd.getVideoUrl().equals("") && appnextAd.getVideoUrlHigh().equals("") && appnextAd.getVideoUrl30Sec().equals("") && appnextAd.getVideoUrlHigh30Sec().equals("")) ? false : true;
    }

    private boolean m407c(AppnextAd appnextAd) {
        return !appnextAd.getWideImageURL().equals("");
    }

    protected AppnextAd m408a(Context context, ArrayList<AppnextAd> arrayList, String str, Ad ad) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AppnextAd appnextAd = (AppnextAd) it.next();
            Object obj = -1;
            switch (str.hashCode()) {
                case -892481938:
                    if (str.equals("static")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 112202875:
                    if (str.equals("video")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 835260319:
                    if (str.equals("managed")) {
                        obj = null;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    if ((m407c(appnextAd) || hasVideo(appnextAd)) && !m169l(appnextAd.getBannerID(), ad.getPlacementID())) {
                        return appnextAd;
                    }
                case 1:
                    if (hasVideo(appnextAd) && !m169l(appnextAd.getBannerID(), ad.getPlacementID())) {
                        return appnextAd;
                    }
                case 2:
                    if (m407c(appnextAd) && !m169l(appnextAd.getBannerID(), ad.getPlacementID())) {
                        return appnextAd;
                    }
                default:
                    break;
            }
        }
        return null;
    }

    private boolean m406a(Context context, AppnextAd appnextAd) {
        InterstitialAd interstitialAd = new InterstitialAd(appnextAd);
        if (interstitialAd.getCampaignGoal().equals("new") && C0266f.m206c(context, interstitialAd.getAdPackage())) {
            return false;
        }
        if (!interstitialAd.getCampaignGoal().equals("existing") || C0266f.m206c(context, interstitialAd.getAdPackage())) {
            return true;
        }
        return false;
    }
}
