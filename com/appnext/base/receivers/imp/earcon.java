package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.receivers.C0949a;

public class earcon extends C0949a {
    public static final String fa = earcon.class.getSimpleName();

    public IntentFilter getBRFilter() {
        return new IntentFilter("android.intent.action.HEADSET_PLUG");
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                m466b(fa, intent.getIntExtra("state", -1) == 0 ? "false" : "true", C0203a.Boolean);
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
