package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1207e;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p001a.p003b.C0935e;
import java.util.Calendar;
import java.util.Locale;

public class ddpos extends C1207e {
    public ddpos(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    public void mo5232d(C0935e c0935e) {
        int i = Calendar.getInstance(Locale.ENGLISH).get(7);
        if (i != 6 && i != 7 && i != 1) {
            C0192a.aE().aI().m458a(c0935e);
        }
    }
}
