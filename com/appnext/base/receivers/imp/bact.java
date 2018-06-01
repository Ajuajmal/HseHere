package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.receivers.C0949a;

public class bact extends C0949a {
    public static final String fa = bact.class.getSimpleName();

    public IntentFilter getBRFilter() {
        if (!C0207f.m65b(C0205d.getContext(), "android.permission.BLUETOOTH")) {
            return null;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        return intentFilter;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            super.onReceive(context, intent);
            String str = null;
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                intent.getExtras();
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                if (intExtra == 10 || intExtra == 13) {
                    str = "false";
                } else if (intExtra == 12 || intExtra == 11) {
                    str = "true";
                }
            }
            m466b(fa, str, C0203a.Boolean);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
