package com.appnext.base.operations;

import android.os.Bundle;
import android.text.TextUtils;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0202b;
import com.appnext.base.p005b.C0208g;
import com.appnext.base.p005b.C0209h;
import java.util.Date;
import java.util.List;

public abstract class C0948a implements C0217b {
    private final String TAG = C0948a.class.getSimpleName();
    private C0934c ex;

    public abstract void bl();

    public abstract void bm();

    protected abstract String getData();

    public C0948a(C0934c c0934c, Bundle bundle) {
        this.ex = c0934c;
    }

    protected void bh() {
        C0209h.bG().putLong(this.ex.getKey() + C0209h.ga, System.currentTimeMillis());
        String data = getData();
        if (TextUtils.isEmpty(data)) {
            bk();
            return;
        }
        if (C0202b.m58a(this.ex.getKey(), C0208g.bF().an(data), getDate()) < 0) {
            bk();
            return;
        }
        C0202b.ai(this.ex.getKey());
        if (C0202b.m62c(this.ex)) {
            List T = C0192a.aE().aH().m446T(this.ex.getKey());
            if (T == null || T.isEmpty()) {
                bk();
                return;
            } else if (mo1242Z(data) == null) {
                bk();
                return;
            } else {
                data = C0202b.m59a(T, this);
                C0202b.ah(this.ex.getKey());
                C0202b.m61c(T);
                C0202b.ag(this.ex.getKey());
                C0202b.m60c(this.ex.getKey(), data);
            }
        }
        bk();
    }

    protected C0934c bi() {
        return this.ex;
    }

    protected boolean bj() {
        return true;
    }

    private void bk() {
        if (bj()) {
            C0218c.bn().m116a(this);
        }
    }

    public Object mo1242Z(String str) {
        return str;
    }

    protected Date getDate() {
        return new Date();
    }
}
