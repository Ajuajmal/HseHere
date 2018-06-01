package com.appnext.base.p001a.p002a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.appnext.base.C0216b;
import com.appnext.base.p001a.C0194b;
import com.appnext.base.p005b.C0205d;

public class C0191a {
    private static C0191a dM;
    private static C0194b dN;
    private Integer dL = Integer.valueOf(0);
    private SQLiteDatabase dO;

    public enum C0190a {
        Global,
        DatabaseOrDiskFull
    }

    private C0191a(Context context) {
        dN = C0194b.m30i(context);
    }

    public static synchronized void m28c(Context context) {
        synchronized (C0191a.class) {
            if (dM == null) {
                dM = new C0191a(context.getApplicationContext());
            }
        }
    }

    public static C0191a aL() {
        if (dM == null) {
            synchronized (C0191a.class) {
                if (dM == null) {
                    dM = new C0191a(C0205d.getContext().getApplicationContext());
                }
            }
        }
        return dM;
    }

    public synchronized SQLiteDatabase aM() {
        this.dL = Integer.valueOf(this.dL.intValue() + 1);
        if (this.dL.intValue() == 1) {
            this.dO = dN.getWritableDatabase();
        }
        return this.dO;
    }

    public synchronized void aN() {
        this.dL = Integer.valueOf(this.dL.intValue() - 1);
        if (this.dL.intValue() == 0) {
            this.dO.close();
        }
    }

    public void m29a(C0190a c0190a, Throwable th) {
        switch (c0190a) {
            case DatabaseOrDiskFull:
            case Global:
                C0216b.m114a(th);
                return;
            default:
                return;
        }
    }
}
