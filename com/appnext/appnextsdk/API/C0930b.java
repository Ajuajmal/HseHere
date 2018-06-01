package com.appnext.appnextsdk.API;

import android.content.Context;
import android.os.Build.VERSION;
import com.appnext.appnextsdk.API.C0187a.C0186a;
import com.appnext.core.Ad;
import com.appnext.core.AppnextAd;
import com.appnext.core.C0241a;
import com.appnext.core.C0249b;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0266f;
import com.appnext.core.C0269h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class C0930b extends C0249b {
    private static C0930b du;
    private final int cA = 50;
    AppnextAdRequest dv;

    private C0930b() {
    }

    public static C0930b ax() {
        if (du == null) {
            du = new C0930b();
        }
        return du;
    }

    protected String mo1206a(Context context, Ad ad, String str) {
        return "https://global.appnext.com" + "/offerWallApi.aspx?ext=t&type=json&igroup=sdk&m=1&osid=100&auid=" + ad.getAUID() + "&pimp=1&id=" + str + "&cnt=" + ((this.dv.getCount() + 5) + C0187a.at().av()) + "&vid=" + ad.getVID() + "&tid=" + ad.getTID() + "&cat=" + this.dv.getCategory() + "&pbk=" + this.dv.getPostback() + "&did=" + C0266f.m207w(context) + "&devn=" + C0266f.cf() + "&dosv=" + VERSION.SDK_INT + "&packageId=" + context.getPackageName() + "&rnd=" + new Random().nextInt();
    }

    public void m429a(Context context, Ad ad, String str, C0248a c0248a, AppnextAdRequest appnextAdRequest) {
        this.dv = appnextAdRequest;
        if (mo1212a(ad) && c0248a != null) {
            m424a(ad, ((C0241a) bT().get(ad)).m143g());
        }
        m152a(context, ad, str, c0248a);
    }

    protected void mo1207a(Context context, Ad ad, C0241a c0241a) throws Exception {
        m424a(ad, c0241a.m143g());
    }

    protected boolean mo1211a(Context context, C0269h c0269h) {
        AppnextAd appnextAd = new AppnextAd((AppnextAd) c0269h);
        if (appnextAd.getCampaignGoal().equals("new") && C0266f.m206c(context, appnextAd.getAdPackage())) {
            return false;
        }
        if (!appnextAd.getCampaignGoal().equals("existing") || C0266f.m206c(context, appnextAd.getAdPackage())) {
            return true;
        }
        return false;
    }

    protected void mo1208a(Ad ad, String str, String str2) {
    }

    protected boolean mo1212a(Ad ad) {
        return bT() != null && bT().containsKey(ad) && ((C0241a) bT().get(ad)).bS().longValue() + 600000 > System.currentTimeMillis() && ((C0241a) bT().get(ad)).getState() == 2 && ((C0241a) bT().get(ad)).m143g() != null;
    }

    protected <T> void mo1209a(String str, Ad ad, T t) {
    }

    private void m424a(Ad ad, ArrayList<AppnextAd> arrayList) {
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AppnextAd appnextAd = (AppnextAd) it.next();
            if (!m425N(appnextAd.getBannerID())) {
                arrayList2.add(new AppnextAd(appnextAd));
            }
        }
        if (arrayList2.size() == 0) {
            C0187a.at().au();
            return;
        }
        Iterator it2 = C0187a.at().m23e(arrayList).iterator();
        while (it2.hasNext()) {
            C0186a c0186a = (C0186a) it2.next();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                AppnextAd appnextAd2 = (AppnextAd) it3.next();
                if (c0186a.dr.equals(appnextAd2.getBannerID())) {
                    arrayList2.add(new AppnextAd(appnextAd2));
                }
            }
        }
        ((C0241a) bT().get(ad)).m140a(arrayList2, false);
    }

    protected boolean m425N(String str) {
        return C0187a.at().m21L(str);
    }

    protected void m426O(String str) {
        C0187a.at().m20K(str);
    }
}
