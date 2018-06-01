package com.appnext.appnextsdk.API;

import android.content.Context;
import android.content.SharedPreferences;
import com.appnext.core.AppnextAd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class C0187a {
    private static C0187a dn;
    private SharedPreferences f15do;
    private int dp = 6;
    private ArrayList<C0186a> dq;

    public class C0186a implements Comparable<C0186a> {
        String dr;
        long ds = 0;
        final /* synthetic */ C0187a dt;

        public C0186a(C0187a c0187a) {
            this.dt = c0187a;
        }

        public /* synthetic */ int compareTo(Object obj) {
            return m17a((C0186a) obj);
        }

        public int m17a(C0186a c0186a) {
            return (int) (this.ds - c0186a.ds);
        }
    }

    private C0187a() {
    }

    public void init(Context context) {
        this.f15do = context.getSharedPreferences("native_ads_cap", 0);
        this.dq = aw();
    }

    public static C0187a at() {
        if (dn == null) {
            dn = new C0187a();
        }
        return dn;
    }

    public void m20K(String str) {
        int i = 0;
        while (i < this.dq.size()) {
            if (((C0186a) this.dq.get(i)).dr.equals(str) || System.currentTimeMillis() - ((long) (3600000 * this.dp)) > ((C0186a) this.dq.get(i)).ds) {
                this.dq.remove(i);
            }
            i++;
        }
        C0186a c0186a = new C0186a(this);
        c0186a.dr = str;
        c0186a.ds = System.currentTimeMillis();
        this.dq.add(c0186a);
        m19d(this.dq);
    }

    private void m19d(ArrayList<C0186a> arrayList) {
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("banner", ((C0186a) arrayList.get(i)).dr);
                jSONObject.put("date", ((C0186a) arrayList.get(i)).ds);
                jSONArray.put(jSONObject);
            } catch (Throwable th) {
            }
        }
        this.f15do.edit().putString("list", jSONArray.toString()).apply();
    }

    public boolean m21L(String str) {
        int i = 0;
        while (i < this.dq.size()) {
            if (((C0186a) this.dq.get(i)).dr.equals(str) && System.currentTimeMillis() - ((long) (3600000 * this.dp)) < ((C0186a) this.dq.get(i)).ds) {
                return true;
            }
            i++;
        }
        return false;
    }

    public void au() {
        this.dq.clear();
        this.f15do.edit().clear().apply();
    }

    public int av() {
        int i = 0;
        for (int i2 = 0; i2 < this.dq.size(); i2++) {
            if (System.currentTimeMillis() - ((long) (3600000 * this.dp)) < ((C0186a) this.dq.get(i2)).ds) {
                i++;
            }
        }
        return i;
    }

    public void m22d(int i) {
        this.dp = i;
    }

    public ArrayList<C0186a> m23e(ArrayList<AppnextAd> arrayList) {
        Object arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            AppnextAd appnextAd = (AppnextAd) it.next();
            if (m21L(appnextAd.getBannerID())) {
                C0186a c0186a = new C0186a(this);
                c0186a.dr = appnextAd.getBannerID();
                c0186a.ds = m18M(appnextAd.getBannerID());
                arrayList2.add(c0186a);
            }
        }
        Collections.sort(arrayList2);
        return arrayList2;
    }

    private long m18M(String str) {
        Iterator it = this.dq.iterator();
        while (it.hasNext()) {
            C0186a c0186a = (C0186a) it.next();
            if (c0186a.dr.equals(str)) {
                return c0186a.ds;
            }
        }
        return -1;
    }

    private ArrayList<C0186a> aw() {
        ArrayList<C0186a> arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(this.f15do.getString("list", "[]"));
            for (int i = 0; i < jSONArray.length(); i++) {
                C0186a c0186a = new C0186a(this);
                c0186a.dr = jSONArray.getJSONObject(i).getString("banner");
                c0186a.ds = jSONArray.getJSONObject(i).getLong("date");
                arrayList.add(c0186a);
            }
        } catch (Throwable th) {
        }
        return arrayList;
    }
}
