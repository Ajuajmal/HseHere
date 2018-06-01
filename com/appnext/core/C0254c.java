package com.appnext.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public final class C0254c {
    static C0251a hb = null;

    public static final class C0251a {
        private final String hc;
        private final boolean hd;

        C0251a(String str, boolean z) {
            this.hc = str;
            this.hd = z;
        }

        public String getId() {
            return this.hc;
        }

        public boolean cb() {
            return this.hd;
        }
    }

    private static final class C0252b implements ServiceConnection {
        boolean he;
        private final LinkedBlockingQueue<IBinder> hf;

        private C0252b() {
            this.he = false;
            this.hf = new LinkedBlockingQueue(1);
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.hf.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }

        public IBinder getBinder() throws InterruptedException {
            if (this.he) {
                throw new IllegalStateException();
            }
            this.he = true;
            return (IBinder) this.hf.take();
        }
    }

    private static final class C0253c implements IInterface {
        private IBinder hg;

        public C0253c(IBinder iBinder) {
            this.hg = iBinder;
        }

        public IBinder asBinder() {
            return this.hg;
        }

        public String getId() throws RemoteException {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.hg.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                String readString = obtain2.readString();
                return readString;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        public boolean m170d(boolean z) throws RemoteException {
            boolean z2 = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(z ? 1 : 0);
                this.hg.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z2 = false;
                }
                obtain2.recycle();
                obtain.recycle();
                return z2;
            } catch (Throwable th) {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    public static C0251a m171s(Context context) throws Exception {
        if (hb != null) {
            return hb;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        context.getPackageManager().getPackageInfo("com.android.vending", 0);
        ServiceConnection c0252b = new C0252b();
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        if (context.bindService(intent, c0252b, 1)) {
            try {
                C0253c c0253c = new C0253c(c0252b.getBinder());
                hb = new C0251a(c0253c.getId(), c0253c.m170d(true));
                C0251a c0251a = hb;
                return c0251a;
            } finally {
                context.unbindService(c0252b);
            }
        } else {
            throw new IOException("Google Play connection failed");
        }
    }
}
