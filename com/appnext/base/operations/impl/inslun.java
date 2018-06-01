package com.appnext.base.operations.impl;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;

public class inslun extends C1206d {
    public inslun(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        PackageManager packageManager = C0205d.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        return String.valueOf(packageManager.queryIntentActivities(intent, 0).size());
    }

    public Object mo1242Z(String str) {
        return str;
    }
}
