package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.receivers.C0949a;
import java.util.Calendar;

public class scron extends C0949a {
    public static final String fa = scron.class.getSimpleName();
    private static final int iC = 5;
    private static final int iD = 9;

    public IntentFilter getBRFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        return intentFilter;
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if (cv()) {
                if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
                    m465a(fa, "true", C0203a.Boolean);
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private boolean cv() {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        int i = instance.get(11);
        if (i < 5 || i >= 9) {
            return false;
        }
        return true;
    }
}
