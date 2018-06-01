package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.receivers.C0949a;

public class scror extends C0949a {
    public static final String fa = scror.class.getSimpleName();

    public IntentFilter getBRFilter() {
        return new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if ("android.intent.action.CONFIGURATION_CHANGED".equals(intent.getAction())) {
                String str;
                if (context.getResources().getConfiguration().orientation == 2) {
                    str = "LANDSCAPE";
                } else {
                    str = "PORTRAIT";
                }
                m466b(fa, str, C0203a.String);
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
