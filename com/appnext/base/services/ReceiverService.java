package com.appnext.base.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.receivers.C0224c;
import java.util.ArrayList;
import java.util.List;

public class ReceiverService extends Service {
    private static final String fj = "com.appnext.base.receivers.imp";
    private List<C0224c> iE;

    public void onCreate() {
        super.onCreate();
        C0205d.init(this);
        br();
    }

    public void onDestroy() {
        bs();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        return 1;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void br() {
        try {
            this.iE = new ArrayList();
            List<C0934c> bC = C0206e.bB().bC();
            if (bC != null) {
                for (C0934c c0934c : bC) {
                    if (c0934c != null && C0204c.fJ.equalsIgnoreCase(c0934c.aT()) && C0204c.fN.equalsIgnoreCase(c0934c.aR())) {
                        m122b(c0934c);
                    }
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private void bs() {
        try {
            for (C0224c unregister : this.iE) {
                unregister.unregister();
            }
            this.iE.clear();
        } catch (Throwable th) {
        }
    }

    private void m122b(C0934c c0934c) {
        if (c0934c != null) {
            try {
                C0224c c0224c = (C0224c) Class.forName(aa(c0934c.getKey())).getConstructor(new Class[0]).newInstance(new Object[0]);
                if (c0224c.register()) {
                    this.iE.add(c0224c);
                }
            } catch (ClassNotFoundException e) {
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
    }

    private String aa(String str) {
        return "com.appnext.base.receivers.imp." + str;
    }
}
