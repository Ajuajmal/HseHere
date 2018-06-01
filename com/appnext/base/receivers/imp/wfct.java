package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.receivers.C0949a;

public class wfct extends C0949a {
    public static final String fa = wfct.class.getSimpleName();

    public IntentFilter getBRFilter() {
        return new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
    }

    public void onReceive(Context context, Intent intent) {
        boolean z = true;
        super.onReceive(context, intent);
        try {
            if (C0207f.m65b(context.getApplicationContext(), "android.permission.ACCESS_WIFI_STATE")) {
                int intExtra = intent.getIntExtra("wifi_state", 4);
                if (intExtra != 3) {
                    if (intExtra == 1) {
                        z = false;
                    } else {
                        return;
                    }
                }
                new Handler().postDelayed(new Runnable(this) {
                    final /* synthetic */ wfct fi;

                    public void run() {
                        this.fi.m466b(wfct.fa, String.valueOf(z), C0203a.Boolean);
                    }
                }, 15000);
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
