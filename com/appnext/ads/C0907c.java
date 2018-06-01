package com.appnext.ads;

import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import com.appnext.core.AdsService;
import com.appnext.core.C0276n;

public class C0907c extends C0276n {
    String aA;
    String aB;
    String aC;
    String aD;
    ResultReceiver aE;
    String az;
    String bH;
    String bI;
    String guid;

    public C0907c(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ResultReceiver resultReceiver) {
        this.az = str;
        this.aA = str2;
        this.guid = str3;
        this.aB = str4;
        this.aC = str5;
        this.aE = resultReceiver;
        this.aD = str6;
        this.bH = str7;
        this.bI = str8;
    }

    public void m262a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, ResultReceiver resultReceiver) {
        this.az = str;
        this.aA = str2;
        this.guid = str3;
        this.aB = str4;
        this.aC = str5;
        this.aE = resultReceiver;
        this.aD = str6;
        this.bH = str7;
        this.bI = str8;
    }

    protected void mo1183a(Intent intent) {
        intent.putExtra("added_info", AdsService.ADD_PACK);
        intent.putExtra("package", this.az);
        intent.putExtra("link", this.aA);
        intent.putExtra("guid", this.guid);
        intent.putExtra("bannerid", this.aB);
        intent.putExtra("placementid", this.aC);
        intent.putExtra("receiver", this.aE);
        intent.putExtra("vid", this.aD);
        intent.putExtra("tid", this.bH);
        intent.putExtra("auid", this.bI);
    }

    public void mo1184b(Context context) {
        super.mo1184b(context);
        this.aE = null;
    }
}
