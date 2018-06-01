package com.appnext.base.p005b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

public final class C0205d {
    @SuppressLint({"StaticFieldLeak"})
    private static Context fV;

    private C0205d() {
    }

    public static void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context shouldn't be null");
        }
        fV = context.getApplicationContext();
    }

    public static void bA() {
        fV = null;
    }

    public static Context getContext() {
        return fV;
    }

    public static AssetManager getAssets() {
        return C0205d.getContext().getAssets();
    }

    public static PackageManager getPackageManager() {
        return C0205d.getContext().getPackageManager();
    }

    public static String getPackageName() {
        return C0205d.getContext().getPackageName();
    }

    public static SharedPreferences getSharedPreferences(String str, int i) {
        return C0205d.getContext().getSharedPreferences(str, i);
    }
}
