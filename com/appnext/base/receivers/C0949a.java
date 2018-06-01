package com.appnext.base.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.appnext.base.C0216b;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0212j;

public abstract class C0949a extends BroadcastReceiver implements C0224c {
    public abstract IntentFilter getBRFilter();

    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            try {
                C0205d.init(context);
                C0209h.bG().init(C0205d.getContext());
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
    }

    protected void m465a(String str, String str2, C0203a c0203a) {
        C0212j.m95c(str, str2, c0203a);
    }

    protected void m466b(String str, String str2, C0203a c0203a) {
        Object ar = C0212j.ar(str);
        if (TextUtils.isEmpty(ar) || !ar.equalsIgnoreCase(str2)) {
            m465a(str, str2, c0203a);
            C0212j.m98f(str, str2);
        }
    }

    public boolean register() {
        IntentFilter bRFilter = getBRFilter();
        if (bRFilter == null) {
            return false;
        }
        C0205d.getContext().registerReceiver(this, bRFilter);
        return true;
    }

    public void unregister() {
        C0205d.getContext().unregisterReceiver(this);
    }
}
