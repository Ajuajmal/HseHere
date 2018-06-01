package com.appnext.base.p005b;

import com.appnext.core.C0270i;

public class C0204c {
    public static final String PACKAGE_NAME = "com.appnext.sdk";
    public static final int fA = 1024;
    public static final long fB = 1048576;
    public static final int fC = 15000;
    public static final String fD = "config_data_obj";
    public static final String fE = "second";
    public static final String fF = "minute";
    public static final String fG = "hour";
    public static final String fH = "day";
    public static final String fI = "month";
    public static final String fJ = "monitoring";
    public static final String fK = "time";
    public static final String fL = "once";
    public static final String fM = "interval";
    public static final String fN = "on";
    public static final String fO = "off";
    public static final String fP = "com.appnext.sdk.DETECTED_ACTIVITIES";
    public static final String fQ = "com.appnext.sdk.ACTIVITIES_BROADCAST_ACTION";
    public static final String fR = "com.appnext.sdk.ACTIVITIES_EXTRA";
    public static final String fS = "data";
    public static final String fT = "action";
    public static final String fU = "type";
    public static final String fr = "4.5.9";
    public static final String fs = "config.json";
    public static final String ft = "plist.zip";
    public static final String fu = "plist.json";
    public static final String fv = "/data/appnext/";
    public static final String fw = "videos/";
    public static final String fx = ".tmp";
    public static final String fy = "https://appnext.hs.llnwd.net/tools/services/4.5.6/config.json";
    public static final String fz = "https://appnext.hs.llnwd.net/tools/services/4.5.6/plist.zip";
    public static final long serialVersionUID = 3596288679259847957L;

    public enum C0203a {
        String("String"),
        Long("Long"),
        Integer("Integer"),
        Boolean("Boolean"),
        JSONArray("JSONArray");
        
        private String mDataType;

        private C0203a(String str) {
            this.mDataType = str;
        }

        public String bz() {
            return this.mDataType;
        }
    }

    public static final String by() {
        return C0270i.hL;
    }
}
