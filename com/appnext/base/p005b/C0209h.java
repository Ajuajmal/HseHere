package com.appnext.base.p005b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint({"CommitPrefEdits"})
public class C0209h {
    private static final String TAG = "LibrarySettings";
    public static final String ga = "_lastupdate";
    public static final String gb = "_cycles";
    public static final String gc = "_hash";
    public static final String gd = "google_ads_id";
    public static final String ge = "_sent_once";
    public static final String gf = "generated";
    public static final String gg = "db_init";
    public static final String gh = "apps_category_saved";
    public static final String gi = "moments_sdk_version";
    public static final String gj = "lat";
    public static final String gk = "usloc_status";
    public static final String gl = "lib_shared_preferences";
    @SuppressLint({"StaticFieldLeak"})
    private static final C0209h gm = new C0209h();
    private SharedPreferences fZ;
    private Context mContext = C0205d.getContext();

    private C0209h() {
        if (this.mContext != null) {
            this.fZ = this.mContext.getSharedPreferences(gl, 0);
        }
    }

    public static C0209h bG() {
        return gm;
    }

    public void clear() {
        this.fZ.edit().clear().apply();
    }

    public void init(Context context) {
        if (context != null) {
            this.mContext = context;
            this.fZ = this.mContext.getSharedPreferences(gl, 0);
        }
    }

    public String getString(String str, String str2) {
        if (this.fZ != null) {
            return this.fZ.getString(str, str2);
        }
        return str2;
    }

    public long getLong(String str, long j) {
        if (this.fZ != null) {
            return this.fZ.getLong(str, j);
        }
        return j;
    }

    public int getInt(String str, int i) {
        if (this.fZ != null) {
            return this.fZ.getInt(str, i);
        }
        return i;
    }

    public boolean getBoolean(String str, boolean z) {
        if (this.fZ != null) {
            return this.fZ.getBoolean(str, z);
        }
        return z;
    }

    public void putLong(String str, long j) {
        if (this.fZ != null) {
            this.fZ.edit().putLong(str, j).apply();
        }
    }

    public void putInt(String str, int i) {
        if (this.fZ != null) {
            this.fZ.edit().putInt(str, i).apply();
        }
    }

    public void putBoolean(String str, boolean z) {
        if (this.fZ != null) {
            this.fZ.edit().putBoolean(str, z).apply();
        }
    }

    public void putString(String str, String str2) {
        if (this.fZ != null) {
            this.fZ.edit().putString(str, str2).apply();
        }
    }
}
