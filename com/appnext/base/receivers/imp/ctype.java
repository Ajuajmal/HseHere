package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.p005b.C0212j;
import com.appnext.base.receivers.C0949a;

public class ctype extends C0949a {
    public static final String fa = ctype.class.getSimpleName();

    public IntentFilter getBRFilter() {
        return new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equalsIgnoreCase(intent.getAction()) && C0207f.m65b(context.getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE")) {
                C0934c aj = C0206e.bB().aj("ctype");
                if (aj == null || C0204c.fO.equalsIgnoreCase(aj.aR())) {
                    C0212j.m98f(fa, "");
                } else {
                    ad("ctype");
                }
                aj = C0206e.bB().aj("mctype");
                if (aj == null || C0204c.fO.equalsIgnoreCase(aj.aR())) {
                    C0212j.m98f(fa, "");
                } else {
                    ad("mctype");
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private void ad(String str) {
        String str2 = null;
        NetworkInfo f = C0207f.m68f(C0205d.getContext());
        if (f != null && f.isConnected()) {
            Object obj = -1;
            switch (str.hashCode()) {
                case -1079385648:
                    if (str.equals("mctype")) {
                        obj = null;
                        break;
                    }
                    break;
                case 95004189:
                    if (str.equals("ctype")) {
                        obj = 1;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    str2 = f.getSubtypeName();
                    break;
                case 1:
                    str2 = f.getTypeName();
                    break;
            }
        }
        if (str2 != null) {
            m466b(str, str2, C0203a.String);
        }
    }
}
