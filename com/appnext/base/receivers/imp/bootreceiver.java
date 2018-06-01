package com.appnext.base.receivers.imp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0212j;

public class bootreceiver extends BroadcastReceiver {
    public static final String TAG = bootreceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            try {
                if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                    C0205d.init(context);
                    C0212j.m104o(context);
                }
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
    }
}
