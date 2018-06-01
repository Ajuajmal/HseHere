package com.appnext.base.operations.impl;

import android.location.Location;
import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0211i;
import java.util.Date;

public class savloc extends C1206d {
    private long eU = 0;

    public savloc(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        Location bI = C0211i.bI();
        if (bI == null) {
            return null;
        }
        this.eU = bI.getTime();
        return bI.getLatitude() + "," + bI.getLongitude();
    }

    protected Date getDate() {
        return new Date(this.eU);
    }
}
