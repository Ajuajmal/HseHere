package com.appnext.base.p001a.p003b;

import java.util.Date;

public class C0933b extends C0193d {
    private String dR;
    private String dS;
    private Date dT;

    public C0933b(String str, String str2, Date date) {
        this.dR = str;
        this.dS = str2;
        this.dT = date;
    }

    public String getType() {
        return this.dR;
    }

    public String aP() {
        return this.dS;
    }

    public Date aQ() {
        return this.dT;
    }
}
