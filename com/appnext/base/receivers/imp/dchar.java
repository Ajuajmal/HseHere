package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.receivers.C0949a;

public class dchar extends C0949a {
    public static final String fa = dchar.class.getSimpleName();

    public IntentFilter getBRFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        return intentFilter;
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            m466b(fa, String.valueOf(Boolean.valueOf(C0207f.m69g(context.getApplicationContext()))), C0203a.Boolean);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
