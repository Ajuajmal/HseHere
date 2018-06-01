package com.appnext.core;

import android.content.Context;
import com.appnext.core.C0268g.C0267a;

public class C0263e {
    public static final int ht = 0;
    private static String hu = "com.google.android.gms.common.GooglePlayServicesUtil";
    private static String hv = "com.google.android.gms.ads.identifier.AdvertisingIdClient";

    static boolean m191u(Context context) {
        try {
            Object cj = new C0267a(null, "isGooglePlayServicesAvailable").m211a(Class.forName(hu)).m212a(Context.class, context).cj();
            if (cj == null || ((Integer) cj).intValue() != 0) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    static String m192v(Context context) {
        String str = "";
        if (C0263e.m191u(context)) {
            if (C0263e.m190q(context)) {
                return "";
            }
            try {
                Object cj = new C0267a(null, "getAdvertisingIdInfo").m211a(Class.forName(hv)).m212a(Context.class, context).cj();
                if (cj != null) {
                    return C0263e.m188a(cj, null);
                }
            } catch (Throwable th) {
            }
        }
        return "";
    }

    public static boolean m190q(Context context) {
        boolean z = false;
        if (C0263e.m191u(context)) {
            try {
                Object cj = new C0267a(null, "getAdvertisingIdInfo").m211a(Class.forName(hv)).m212a(Context.class, context).cj();
                if (cj != null) {
                    z = C0263e.m189a(cj, false);
                }
            } catch (Throwable th) {
            }
        }
        return z;
    }

    static String m188a(Object obj, String str) {
        try {
            return (String) new C0267a(obj, "getId").cj();
        } catch (Throwable th) {
            return str;
        }
    }

    static boolean m189a(Object obj, boolean z) {
        try {
            Boolean bool = (Boolean) new C0267a(obj, "isLimitAdTrackingEnabled").cj();
            if (bool != null) {
                z = bool.booleanValue();
            }
        } catch (Throwable th) {
        }
        return z;
    }

    @Deprecated
    public static void cd() {
        String str = "java.lang.Class";
        hu = str;
        hv = str;
    }
}
