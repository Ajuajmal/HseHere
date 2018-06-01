package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C0218c;
import com.appnext.base.operations.C0948a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0201a;
import com.appnext.base.p005b.C0201a.C0200c;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0213k;
import com.google.android.gms.location.DetectedActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class dmstat extends C0948a implements C0200c {
    protected static final String TAG = dmstat.class.getSimpleName();
    private static final String cC = "last_dmstat";
    private static final String cD = "value";
    private static final String eG = "accuracy";
    private static final int eI = 40;
    private static final int fk = 2;
    private C0201a eH = null;
    private String ix = null;

    class C02201 implements Runnable {
        final /* synthetic */ dmstat eJ;

        C02201(dmstat com_appnext_base_operations_impl_dmstat) {
            this.eJ = com_appnext_base_operations_impl_dmstat;
        }

        public void run() {
            this.eJ.bh();
        }
    }

    private class DetectedActivityComparator implements Comparator<DetectedActivity> {
        final /* synthetic */ dmstat eJ;

        private DetectedActivityComparator(dmstat com_appnext_base_operations_impl_dmstat) {
            this.eJ = com_appnext_base_operations_impl_dmstat;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m118a((DetectedActivity) obj, (DetectedActivity) obj2);
        }

        public int m118a(DetectedActivity detectedActivity, DetectedActivity detectedActivity2) {
            return detectedActivity.getConfidence() > detectedActivity2.getConfidence() ? 1 : 0;
        }
    }

    public dmstat(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    public void bl() {
        synchronized (this) {
            this.eH = new C0201a();
            this.eH.m57a((C0200c) this);
            this.eH.init();
        }
    }

    public void bm() {
        synchronized (this) {
            if (this.eH != null) {
                this.eH.m57a(null);
                this.eH.stop();
                this.eH = null;
            }
        }
    }

    protected String getData() {
        if (this.ix == null) {
            return null;
        }
        C0209h.bG().putString(cC, this.ix);
        return this.ix;
    }

    public void mo4207b(List<DetectedActivity> list) {
        this.eH.m57a(null);
        if (list == null || list.size() == 0) {
            C0218c.bn().m116a(this);
            return;
        }
        try {
            Collections.sort(list, new DetectedActivityComparator());
            Iterator listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                int type = ((DetectedActivity) listIterator.next()).getType();
                if (!(type == 0 || type == 3 || type == 1 || type == 7 || type == 8)) {
                    listIterator.remove();
                }
            }
            if (list.size() == 0) {
                C0218c.bn().m116a(this);
                return;
            }
            DetectedActivity detectedActivity = (DetectedActivity) list.get(0);
            list.remove(0);
            if (detectedActivity.getConfidence() < 40) {
                C0218c.bn().m116a(this);
                new Thread(new C02201(this)).start();
                return;
            }
            this.ix = m483a(detectedActivity, list);
            new Thread(new C02201(this)).start();
        } catch (Throwable th) {
            this.ix = null;
            C0213k.m110j(TAG, th.toString());
        } finally {
            new Thread(new C02201(this)).start();
        }
    }

    public void onError(String str) {
        this.eH.m57a(null);
        C0218c.bn().m116a(this);
    }

    public Object mo1242Z(String str) {
        try {
            return new JSONArray(str);
        } catch (Throwable th) {
            return null;
        }
    }

    private String m483a(DetectedActivity detectedActivity, List<DetectedActivity> list) {
        if (detectedActivity == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(m485b(detectedActivity.getType(), detectedActivity.getConfidence()));
        if (list != null && list.size() > 0) {
            for (int i = 0; i < 1; i++) {
                DetectedActivity detectedActivity2 = (DetectedActivity) list.get(i);
                if (detectedActivity2 != null && detectedActivity.getConfidence() - detectedActivity2.getConfidence() <= 10) {
                    jSONArray.put(m485b(detectedActivity2.getType(), detectedActivity2.getConfidence()));
                }
            }
        }
        String string = C0209h.bG().getString(cC, null);
        C0209h.bG().putString(cC, jSONArray.toString());
        if (string == null) {
            return jSONArray.toString();
        }
        try {
            JSONArray jSONArray2 = new JSONArray(string);
            if (jSONArray2.length() != jSONArray.length()) {
                return jSONArray.toString();
            }
            int length = jSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                if (jSONArray2.getJSONObject(i2).getInt(cD) != jSONArray.getJSONObject(i2).getInt(cD)) {
                    return jSONArray.toString();
                }
            }
            return null;
        } catch (Throwable th) {
            return jSONArray.toString();
        }
    }

    private JSONObject m485b(int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(cD, i);
            jSONObject.put(eG, i2);
            return jSONObject;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
