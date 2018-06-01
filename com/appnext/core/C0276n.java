package com.appnext.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.appnext.core.AdsService.C0230a;

public abstract class C0276n {
    private AdsService mBoundService;
    private ServiceConnection mConnection = new C02751(this);

    class C02751 implements ServiceConnection {
        final /* synthetic */ C0276n hV;

        C02751(C0276n c0276n) {
            this.hV = c0276n;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            this.hV.mBoundService = ((C0230a) iBinder).bW();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            this.hV.mBoundService = null;
        }
    }

    protected abstract void mo1183a(Intent intent);

    public void m224A(Context context) {
        try {
            Intent intent = new Intent(context, m227q());
            mo1183a(intent);
            context.bindService(intent, this.mConnection, 1);
            context.startService(intent);
        } catch (Throwable th) {
            C0266f.m205c(th);
        }
    }

    protected Class<?> m227q() {
        return AdsService.class;
    }

    public void mo1184b(Context context) {
        try {
            context.unbindService(this.mConnection);
            this.mBoundService.stopSelf();
            this.mConnection = null;
        } catch (Throwable th) {
        }
    }
}
