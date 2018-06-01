package com.appnext.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.appnext.base.p001a.p002a.C0191a;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0212j;
import com.appnext.core.C0266f;

public class Appnext {
    @SuppressLint({"StaticFieldLeak"})
    private static final Appnext dy = new Appnext();
    private Context dx = null;
    private boolean dz = false;

    class C01881 implements Runnable {
        final /* synthetic */ Appnext dA;

        C01881(Appnext appnext) {
            this.dA = appnext;
        }

        public void run() {
            C0209h.bG().init(this.dA.dx);
            if (C0212j.m106q(this.dA.dx)) {
                this.dA.dz = false;
                C0209h.bG().putBoolean(C0209h.gj, true);
                return;
            }
            C0205d.init(this.dA.dx);
            C0209h.bG().init(this.dA.dx);
            this.dA.az();
            C0191a.m28c(this.dA.dx);
            C0212j.m104o(this.dA.dx);
        }
    }

    private Appnext() {
    }

    protected static Appnext getInstance() {
        return dy;
    }

    public static void init(Context context) {
        getInstance().m24D(context);
    }

    private void m24D(Context context) throws ExceptionInInitializerError {
        if (context == null) {
            throw new ExceptionInInitializerError("Cannot init Appnext with null context");
        } else if (!this.dz || this.dx == null) {
            this.dz = true;
            this.dx = context.getApplicationContext();
            C0205d.init(this.dx);
            HandlerThread handlerThread = new HandlerThread("DataLib thread");
            handlerThread.start();
            new Handler(handlerThread.getLooper()).postDelayed(new C01881(this), 100);
        } else {
            this.dx = context.getApplicationContext();
        }
    }

    private void az() {
        String w = C0266f.m207w(this.dx);
        if (!w.equals(C0209h.bG().getString(C0209h.gd, ""))) {
            C0209h.bG().clear();
            C0209h.bG().putString(C0209h.gd, w);
        }
    }
}
