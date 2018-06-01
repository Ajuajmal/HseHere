package com.appnext.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class C0249b {
    private static int gF = 200;
    private final HashMap<Ad, C0241a> gG = new HashMap();

    public interface C0248a {
        <T> void mo1187a(T t);

        void error(String str);
    }

    protected abstract String mo1206a(Context context, Ad ad, String str);

    protected abstract void mo1207a(Context context, Ad ad, C0241a c0241a) throws Exception;

    protected abstract void mo1208a(Ad ad, String str, String str2);

    protected abstract <T> void mo1209a(String str, Ad ad, T t);

    protected abstract boolean mo1211a(Context context, C0269h c0269h);

    public void m152a(Context context, Ad ad, String str, C0248a c0248a) {
        final Ad ad2 = ad;
        final C0248a c0248a2 = c0248a;
        final Context context2 = context;
        final String str2 = str;
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            final /* synthetic */ C0249b gH;

            class C02421 implements Runnable {
                final /* synthetic */ C02431 gI;

                C02421(C02431 c02431) {
                    this.gI = c02431;
                }

                public void run() {
                    this.gI.gH.m148b(context2, ad2, str2, c0248a2);
                }
            }

            public void run() {
                if (this.gH.mo1212a(ad2)) {
                    if (c0248a2 != null) {
                        c0248a2.mo1187a(((C0241a) this.gH.gG.get(ad2)).m143g());
                    }
                } else if (C0266f.ce().equals("")) {
                    C0266f.m208x(context2);
                    new Thread(new C02421(this)).start();
                } else {
                    this.gH.m148b(context2, ad2, str2, c0248a2);
                }
            }
        });
    }

    private void m148b(Context context, Ad ad, String str, C0248a c0248a) {
        final Ad ad2 = ad;
        final C0248a c0248a2 = c0248a;
        final String str2 = str;
        final Context context2 = context;
        new Thread(this) {
            final /* synthetic */ C0249b gH;

            class C02441 implements Runnable {
                final /* synthetic */ C02452 gJ;

                C02441(C02452 c02452) {
                    this.gJ = c02452;
                }

                public void run() {
                    try {
                        ((C0241a) this.gJ.gH.gG.get(ad2)).m144g(((C0241a) this.gJ.gH.gG.get(ad2)).m143g());
                        this.gJ.gH.mo1209a(str2, ad2, ((C0241a) this.gJ.gH.gG.get(ad2)).m143g());
                    } catch (Throwable th) {
                        this.gJ.gH.m155a(AppnextError.INTERNAL_ERROR, ad2);
                    }
                }
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r8 = this;
                r7 = 2;
                r3 = 1;
                super.run();
                r0 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r1 = r0.gG;	 Catch:{ Throwable -> 0x00c1 }
                monitor-enter(r1);	 Catch:{ Throwable -> 0x00c1 }
                r0 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r0 = r0.gG;	 Catch:{ all -> 0x00e9 }
                r2 = r2;	 Catch:{ all -> 0x00e9 }
                r0 = r0.containsKey(r2);	 Catch:{ all -> 0x00e9 }
                if (r0 == 0) goto L_0x0047;
            L_0x001a:
                r0 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r0 = r0.gG;	 Catch:{ all -> 0x00e9 }
                r2 = r2;	 Catch:{ all -> 0x00e9 }
                r0 = r0.get(r2);	 Catch:{ all -> 0x00e9 }
                r0 = (com.appnext.core.C0241a) r0;	 Catch:{ all -> 0x00e9 }
                r0 = r0.getState();	 Catch:{ all -> 0x00e9 }
                if (r0 != r3) goto L_0x0047;
            L_0x002e:
                r0 = r3;	 Catch:{ all -> 0x00e9 }
                if (r0 == 0) goto L_0x0045;
            L_0x0032:
                r0 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r0 = r0.gG;	 Catch:{ all -> 0x00e9 }
                r2 = r2;	 Catch:{ all -> 0x00e9 }
                r0 = r0.get(r2);	 Catch:{ all -> 0x00e9 }
                r0 = (com.appnext.core.C0241a) r0;	 Catch:{ all -> 0x00e9 }
                r2 = r3;	 Catch:{ all -> 0x00e9 }
                r0.m138a(r2);	 Catch:{ all -> 0x00e9 }
            L_0x0045:
                monitor-exit(r1);	 Catch:{ all -> 0x00e9 }
            L_0x0046:
                return;
            L_0x0047:
                r0 = "start loading ads";
                com.appnext.core.C0266f.m194P(r0);	 Catch:{ all -> 0x00e9 }
                r0 = new com.appnext.core.a;	 Catch:{ all -> 0x00e9 }
                r0.<init>();	 Catch:{ all -> 0x00e9 }
                r2 = r3;	 Catch:{ all -> 0x00e9 }
                r0.m138a(r2);	 Catch:{ all -> 0x00e9 }
                r2 = r4;	 Catch:{ all -> 0x00e9 }
                r0.setPlacementID(r2);	 Catch:{ all -> 0x00e9 }
                r2 = 1;
                r0.setState(r2);	 Catch:{ all -> 0x00e9 }
                r2 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r2 = r2.gG;	 Catch:{ all -> 0x00e9 }
                r3 = r2;	 Catch:{ all -> 0x00e9 }
                r2 = r2.containsKey(r3);	 Catch:{ all -> 0x00e9 }
                if (r2 == 0) goto L_0x0078;
            L_0x006d:
                r2 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r2 = r2.gG;	 Catch:{ all -> 0x00e9 }
                r3 = r2;	 Catch:{ all -> 0x00e9 }
                r2.remove(r3);	 Catch:{ all -> 0x00e9 }
            L_0x0078:
                r2 = r8.gH;	 Catch:{ all -> 0x00e9 }
                r3 = r2;	 Catch:{ all -> 0x00e9 }
                r2.m153a(r3, r0);	 Catch:{ all -> 0x00e9 }
                monitor-exit(r1);	 Catch:{ all -> 0x00e9 }
                r0 = r8.gH;	 Catch:{ Throwable -> 0x00ec }
                r1 = r5;	 Catch:{ Throwable -> 0x00ec }
                r2 = r2;	 Catch:{ Throwable -> 0x00ec }
                r3 = r4;	 Catch:{ Throwable -> 0x00ec }
                r0 = r0.mo1206a(r1, r2, r3);	 Catch:{ Throwable -> 0x00ec }
                r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00ec }
                r1.<init>();	 Catch:{ Throwable -> 0x00ec }
                r2 = "AdsManager request url: ";
                r1 = r1.append(r2);	 Catch:{ Throwable -> 0x00ec }
                r1 = r1.append(r0);	 Catch:{ Throwable -> 0x00ec }
                r1 = r1.toString();	 Catch:{ Throwable -> 0x00ec }
                com.appnext.core.C0266f.m194P(r1);	 Catch:{ Throwable -> 0x00ec }
                r1 = 0;
                r0 = com.appnext.core.C0266f.m196a(r0, r1);	 Catch:{ Throwable -> 0x00ec }
                r1 = "{}";
                r1 = r0.equals(r1);	 Catch:{ Throwable -> 0x00c1 }
                if (r1 != 0) goto L_0x00b7;
            L_0x00af:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r1 = r1.m166d(r0);	 Catch:{ Throwable -> 0x00c1 }
                if (r1 == 0) goto L_0x011f;
            L_0x00b7:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r2 = "No Ads";
                r3 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r1.m158a(r2, r0, r3);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x00c1:
                r0 = move-exception;
                r1 = new java.lang.StringBuilder;
                r1.<init>();
                r2 = "finished custom after load with error ";
                r1 = r1.append(r2);
                r2 = com.appnext.core.C0266f.m202b(r0);
                r1 = r1.append(r2);
                r1 = r1.toString();
                com.appnext.core.C0266f.m194P(r1);
                r1 = r8.gH;
                r0 = r0.getMessage();
                r2 = r2;
                r1.m155a(r0, r2);
                goto L_0x0046;
            L_0x00e9:
                r0 = move-exception;
                monitor-exit(r1);	 Catch:{ all -> 0x00e9 }
                throw r0;	 Catch:{ Throwable -> 0x00c1 }
            L_0x00ec:
                r0 = move-exception;
                r1 = "AdsManager request error";
                com.appnext.core.C0266f.m194P(r1);	 Catch:{ Throwable -> 0x00c1 }
                com.appnext.core.C0266f.m205c(r0);	 Catch:{ Throwable -> 0x00c1 }
                r1 = r0.getMessage();	 Catch:{ Throwable -> 0x00c1 }
                r2 = "";
                r1 = r1.equals(r2);	 Catch:{ Throwable -> 0x00c1 }
                if (r1 == 0) goto L_0x0110;
            L_0x0101:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r2 = "Connection Error";
                r0 = com.appnext.core.C0266f.m202b(r0);	 Catch:{ Throwable -> 0x00c1 }
                r3 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r1.m158a(r2, r0, r3);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x0110:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r2 = "Internal error";
                r0 = com.appnext.core.C0266f.m202b(r0);	 Catch:{ Throwable -> 0x00c1 }
                r3 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r1.m158a(r2, r0, r3);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x011f:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x013a }
                r2 = r5;	 Catch:{ Throwable -> 0x013a }
                r3 = r2;	 Catch:{ Throwable -> 0x013a }
                r4 = com.appnext.core.C0249b.gF;	 Catch:{ Throwable -> 0x013a }
                r1 = r1.m150a(r2, r3, r0, r4);	 Catch:{ Throwable -> 0x013a }
                if (r1 != 0) goto L_0x014d;
            L_0x012f:
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r2 = "No Ads";
                r3 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r1.m158a(r2, r0, r3);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x013a:
                r0 = move-exception;
                com.appnext.core.C0266f.m205c(r0);	 Catch:{ Throwable -> 0x00c1 }
                r1 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r2 = "Internal error";
                r0 = com.appnext.core.C0266f.m202b(r0);	 Catch:{ Throwable -> 0x00c1 }
                r3 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r1.m158a(r2, r0, r3);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x014d:
                r0 = r1.size();	 Catch:{ Throwable -> 0x00c1 }
                if (r0 != 0) goto L_0x015e;
            L_0x0153:
                r0 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r1 = "No Ads";
                r2 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r0.m155a(r1, r2);	 Catch:{ Throwable -> 0x00c1 }
                goto L_0x0046;
            L_0x015e:
                r0 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r0 = r0.gG;	 Catch:{ Throwable -> 0x00c1 }
                r2 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r0 = r0.get(r2);	 Catch:{ Throwable -> 0x00c1 }
                r0 = (com.appnext.core.C0241a) r0;	 Catch:{ Throwable -> 0x00c1 }
                r0.m142b(r1);	 Catch:{ Throwable -> 0x00c1 }
                r2 = 3;
                r0 = 0;
                r1 = r0;
            L_0x0172:
                if (r1 >= r2) goto L_0x018b;
            L_0x0174:
                r3 = r8.gH;	 Catch:{ Throwable -> 0x01b5 }
                r4 = r5;	 Catch:{ Throwable -> 0x01b5 }
                r5 = r2;	 Catch:{ Throwable -> 0x01b5 }
                r0 = r8.gH;	 Catch:{ Throwable -> 0x01b5 }
                r0 = r0.gG;	 Catch:{ Throwable -> 0x01b5 }
                r6 = r2;	 Catch:{ Throwable -> 0x01b5 }
                r0 = r0.get(r6);	 Catch:{ Throwable -> 0x01b5 }
                r0 = (com.appnext.core.C0241a) r0;	 Catch:{ Throwable -> 0x01b5 }
                r3.mo1207a(r4, r5, r0);	 Catch:{ Throwable -> 0x01b5 }
            L_0x018b:
                r0 = r8.gH;	 Catch:{ Throwable -> 0x00c1 }
                r0 = r0.gG;	 Catch:{ Throwable -> 0x00c1 }
                r1 = r2;	 Catch:{ Throwable -> 0x00c1 }
                r0 = r0.get(r1);	 Catch:{ Throwable -> 0x00c1 }
                r0 = (com.appnext.core.C0241a) r0;	 Catch:{ Throwable -> 0x00c1 }
                r1 = 2;
                r0.setState(r1);	 Catch:{ Throwable -> 0x00c1 }
                r0 = new android.os.Handler;	 Catch:{ Throwable -> 0x00c1 }
                r1 = android.os.Looper.getMainLooper();	 Catch:{ Throwable -> 0x00c1 }
                r0.<init>(r1);	 Catch:{ Throwable -> 0x00c1 }
                r1 = new com.appnext.core.b$2$1;	 Catch:{ Throwable -> 0x00c1 }
                r1.<init>(r8);	 Catch:{ Throwable -> 0x00c1 }
                r0.post(r1);	 Catch:{ Throwable -> 0x00c1 }
                r0 = "finished loading ads";
                com.appnext.core.C0266f.m194P(r0);
                goto L_0x0046;
            L_0x01b5:
                r0 = move-exception;
                if (r1 != r7) goto L_0x01b9;
            L_0x01b8:
                throw r0;	 Catch:{ Throwable -> 0x00c1 }
            L_0x01b9:
                r0 = r1 + 1;
                r1 = r0;
                goto L_0x0172;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.appnext.core.b.2.run():void");
            }
        }.start();
    }

    protected boolean mo1212a(Ad ad) {
        return m167g(ad) && m168h(ad);
    }

    protected boolean m167g(Ad ad) {
        return this.gG != null && this.gG.containsKey(ad) && ((C0241a) this.gG.get(ad)).getState() == 2 && ((C0241a) this.gG.get(ad)).m143g() != null;
    }

    protected boolean m168h(Ad ad) {
        return this.gG != null && this.gG.containsKey(ad) && ((C0241a) this.gG.get(ad)).bS().longValue() + cx() > System.currentTimeMillis();
    }

    protected long cx() {
        return 900000;
    }

    public void m165d(Context context, Ad ad, String str) {
        if (this.gG.containsKey(ad)) {
            this.gG.remove(ad);
        }
        m148b(context, ad, str, null);
    }

    protected ArrayList<? extends C0269h> m150a(Context context, Ad ad, String str, int i) throws JSONException {
        ArrayList<? extends C0269h> arrayList = new ArrayList();
        JSONArray jSONArray = new JSONObject(str).getJSONArray("apps");
        int i2 = 0;
        while (i2 < jSONArray.length()) {
            try {
                C0269h c0269h = (AppnextAd) m161c(jSONArray.getJSONObject(i2).toString());
                c0269h.setAdID(arrayList.size());
                c0269h.setPlacementID(ad.getPlacementID());
                if (mo1211a(context, c0269h)) {
                    arrayList.add(c0269h);
                }
                if (arrayList.size() == i) {
                    break;
                }
                i2++;
            } catch (Throwable th) {
                C0266f.m205c(th);
            }
        }
        return arrayList;
    }

    protected boolean m166d(String str) {
        try {
            return new JSONObject(str).has("rnd");
        } catch (Throwable th) {
            return true;
        }
    }

    protected String mo1232c(ArrayList<AppnextAd> arrayList) {
        try {
            JSONArray jSONArray = new JSONArray();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                jSONArray.put(new JSONObject(((AppnextAd) it.next()).getAdJSON()));
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("apps", jSONArray);
            return jSONObject.toString().replace(" ", "\\u2028").replace(" ", "\\u2029");
        } catch (Throwable th) {
            return "";
        }
    }

    protected void m155a(final String str, final Ad ad) {
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            final /* synthetic */ C0249b gH;

            public void run() {
                C0241a c0241a = (C0241a) this.gH.gG.get(ad);
                if (c0241a != null) {
                    c0241a.m142b(new ArrayList());
                    c0241a.setState(2);
                    c0241a.at(str);
                    this.gH.mo1208a(ad, str, c0241a.getPlacementID());
                }
            }
        });
    }

    protected void m158a(final String str, final String str2, final Ad ad) {
        new Handler(Looper.getMainLooper()).post(new Runnable(this) {
            final /* synthetic */ C0249b gH;

            public void run() {
                C0241a c0241a = (C0241a) this.gH.gG.get(ad);
                c0241a.m142b(new ArrayList());
                c0241a.setState(2);
                c0241a.at(str);
                this.gH.mo1208a(ad, str + " " + str2, c0241a.getPlacementID());
            }
        });
    }

    protected C0241a m163d(Ad ad) {
        return (C0241a) this.gG.get(ad);
    }

    protected HashMap<Ad, C0241a> bT() {
        return this.gG;
    }

    protected void m153a(Ad ad, C0241a c0241a) {
        this.gG.put(ad, c0241a);
    }

    public String m164d(AppnextAd appnextAd) {
        return appnextAd.getAdJSON();
    }

    public C0269h m161c(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            AppnextAd appnextAd = (AppnextAd) C0272k.m218a(AppnextAd.class, jSONObject);
            if (appnextAd == null) {
                return appnextAd;
            }
            appnextAd.setAdJSON(jSONObject.toString());
            return appnextAd;
        } catch (Throwable th) {
            return null;
        }
    }

    protected boolean m169l(String str, String str2) {
        return C0271j.ck().m217n(str, str2);
    }

    protected void au(String str) {
        C0271j.ck().aB(str);
    }

    protected void mo1210a(String str, String str2) {
        C0271j.ck().m216m(str, str2);
    }
}
