package com.appnext.base.p005b;

import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import java.util.List;

public class C0206e {
    private static volatile C0206e fW;

    private C0206e() {
    }

    public static C0206e bB() {
        if (fW == null) {
            synchronized (C0206e.class) {
                if (fW == null) {
                    fW = new C0206e();
                }
            }
        }
        return fW;
    }

    public C0934c aj(String str) {
        return C0192a.aE().aJ().m452V(str);
    }

    public List<C0934c> bC() {
        return C0192a.aE().aJ().bb();
    }
}
