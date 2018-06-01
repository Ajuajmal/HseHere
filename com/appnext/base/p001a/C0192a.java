package com.appnext.base.p001a;

import com.appnext.base.p001a.p004c.C0936a;
import com.appnext.base.p001a.p004c.C0937b;
import com.appnext.base.p001a.p004c.C0938c;
import com.appnext.base.p001a.p004c.C0939e;

public class C0192a {
    private static volatile C0192a dG;
    private C0936a dC;
    private C0937b dD;
    private C0939e dE;
    private C0938c dF;

    public static C0192a aE() {
        if (dG == null) {
            synchronized (C0192a.class) {
                if (dG == null) {
                    dG = new C0192a();
                }
            }
        }
        return dG;
    }

    private C0192a() {
        aF();
    }

    private void aF() {
        this.dC = new C0936a();
        this.dD = new C0937b();
        this.dE = new C0939e();
        this.dF = new C0938c();
    }

    public C0936a aG() {
        return this.dC;
    }

    public C0937b aH() {
        return this.dD;
    }

    public C0939e aI() {
        return this.dE;
    }

    public C0938c aJ() {
        return this.dF;
    }
}
