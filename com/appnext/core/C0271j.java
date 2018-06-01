package com.appnext.core;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class C0271j {
    private static C0271j hN;
    private int dp = 24;
    private HashMap<String, SharedPreferences> hO = new HashMap();

    private C0271j() {
    }

    public void m215d(Context context, String str) {
        if (!this.hO.containsKey(str)) {
            this.hO.put(str, context.getSharedPreferences("apnxt_cap" + str, 0));
        }
    }

    public static C0271j ck() {
        if (hN == null) {
            hN = new C0271j();
        }
        return hN;
    }

    public void m216m(String str, String str2) {
        ((SharedPreferences) this.hO.get(str2)).edit().putLong(str, System.currentTimeMillis()).apply();
    }

    public boolean m217n(String str, String str2) {
        long j = ((SharedPreferences) this.hO.get(str2)).getLong(str, -1);
        return j != -1 && System.currentTimeMillis() - ((long) (3600000 * this.dp)) <= j;
    }

    public void aB(String str) {
        ((SharedPreferences) this.hO.get(str)).edit().clear().apply();
    }

    public void m214d(int i) {
        this.dp = i;
    }
}
