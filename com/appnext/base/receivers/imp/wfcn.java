package com.appnext.base.receivers.imp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.os.Handler;
import android.text.TextUtils;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.p005b.C0207f;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0213k;
import com.appnext.base.receivers.C1208b;

public class wfcn extends C1208b {
    public static final String fa = wfcn.class.getSimpleName();
    private static final String fd = "LAST_WIFI_CONNECTION";
    private static final String fe = "<unknown ssid>";

    public IntentFilter getBRFilter() {
        return new IntentFilter("android.net.wifi.STATE_CHANGE");
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        try {
            if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                C0934c aj = C0206e.bB().aj(fa);
                if (aj != null && !C0204c.fO.equalsIgnoreCase(aj.aR())) {
                    if (C0207f.m65b(context.getApplicationContext(), "android.permission.ACCESS_NETWORK_STATE")) {
                        Boolean valueOf;
                        String str = "";
                        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        if (networkInfo != null && networkInfo.isConnected() && networkInfo.getDetailedState() == DetailedState.CONNECTED) {
                            valueOf = Boolean.valueOf(true);
                            str = networkInfo.getExtraInfo();
                            if (!TextUtils.isEmpty(str) && !str.equals(fe)) {
                                str = str.replace("\"", "");
                                C0209h.bG().putString(fd, str);
                            } else {
                                return;
                            }
                        } else if (networkInfo.getDetailedState() == DetailedState.DISCONNECTED) {
                            valueOf = Boolean.valueOf(false);
                            str = C0209h.bG().getString(fd, "");
                        } else {
                            return;
                        }
                        final boolean booleanValue = valueOf.booleanValue();
                        new Handler().postDelayed(new Runnable(this) {
                            final /* synthetic */ wfcn fh;

                            public void run() {
                                this.fh.m466b(wfcn.fa, this.fh.m494a(Boolean.valueOf(booleanValue), str), C0203a.JSONArray);
                            }
                        }, 15000);
                        return;
                    }
                    C0213k.m109i(fa, "No permission granted android.permission.ACCESS_NETWORK_STATE");
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }
}
