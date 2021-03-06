package com.google.android.gms.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.google.android.gms.common.internal.zzx;

@zzhb
public class zzix {
    private Handler mHandler = null;
    private HandlerThread zzMG = null;
    private int zzMH = 0;
    private final Object zzpV = new Object();

    class C05021 implements Runnable {
        final /* synthetic */ zzix zzMI;

        C05021(zzix com_google_android_gms_internal_zzix) {
            this.zzMI = com_google_android_gms_internal_zzix;
        }

        public void run() {
            synchronized (this.zzMI.zzpV) {
                zzin.m468v("Suspending the looper thread");
                while (this.zzMI.zzMH == 0) {
                    try {
                        this.zzMI.zzpV.wait();
                        zzin.m468v("Looper thread resumed");
                    } catch (InterruptedException e) {
                        zzin.m468v("Looper thread interrupted.");
                    }
                }
            }
        }
    }

    public Looper zzhC() {
        Looper looper;
        synchronized (this.zzpV) {
            if (this.zzMH != 0) {
                zzx.zzb(this.zzMG, (Object) "Invalid state: mHandlerThread should already been initialized.");
            } else if (this.zzMG == null) {
                zzin.m468v("Starting the looper thread.");
                this.zzMG = new HandlerThread("LooperProvider");
                this.zzMG.start();
                this.mHandler = new Handler(this.zzMG.getLooper());
                zzin.m468v("Looper thread started.");
            } else {
                zzin.m468v("Resuming the looper thread");
                this.zzpV.notifyAll();
            }
            this.zzMH++;
            looper = this.zzMG.getLooper();
        }
        return looper;
    }

    public void zzhD() {
        synchronized (this.zzpV) {
            zzx.zzb(this.zzMH > 0, (Object) "Invalid state: release() called more times than expected.");
            int i = this.zzMH - 1;
            this.zzMH = i;
            if (i == 0) {
                this.mHandler.post(new C05021(this));
            }
        }
    }
}
