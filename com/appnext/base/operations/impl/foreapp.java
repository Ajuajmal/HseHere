package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0212j;
import java.util.List;

public class foreapp extends C1206d {
    private static final String TAG = foreapp.class.getSimpleName();

    public foreapp(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        StringBuilder stringBuilder = new StringBuilder();
        List m = C0212j.m102m(C0205d.getContext());
        if (m != null && m.size() > 0) {
            stringBuilder.append((String) m.get(m.size() - 1));
        }
        return stringBuilder.toString();
    }
}
