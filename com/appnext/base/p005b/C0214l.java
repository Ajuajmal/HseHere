package com.appnext.base.p005b;

import java.util.Calendar;
import java.util.Date;

public class C0214l {
    public static Date m112c(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.getTime();
    }
}
