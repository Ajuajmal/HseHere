package com.appnext.base.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.appnext.base.C0198a;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C0218c;
import com.appnext.base.p001a.p002a.C0191a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0212j;
import com.appnext.base.p005b.C0213k;
import com.google.android.gms.location.ActivityRecognitionResult;
import java.util.ArrayList;

public class OperationService extends IntentService {
    private static final String TAG = OperationService.class.getSimpleName();

    public OperationService() {
        super(OperationService.class.getName());
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        if (m119b(intent)) {
            m121d(intent);
        } else {
            m120c(intent);
        }
    }

    private boolean m119b(@Nullable Intent intent) {
        if (intent == null) {
            return false;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return false;
        }
        String string = extras.getString(C0204c.fP);
        if (string == null || !string.equals(C0204c.fP)) {
            return false;
        }
        C0213k.m108h(TAG, "activities detected");
        return true;
    }

    private void m120c(@Nullable Intent intent) {
        C0934c c0934c = null;
        try {
            C0209h.bG().init(getApplicationContext());
            C0205d.init(getApplicationContext());
            C0191a.m28c(getApplicationContext());
            if (C0212j.m105p(getApplicationContext())) {
                C0209h.bG().putBoolean(C0209h.gj, true);
                return;
            }
            String stringExtra;
            String stringExtra2;
            Bundle extras;
            if (intent != null) {
                stringExtra = intent.getStringExtra(C0204c.fD);
                stringExtra2 = intent.getStringExtra(C0204c.fT);
                extras = intent.getExtras();
                c0934c = C0206e.bB().aj(stringExtra);
            } else {
                stringExtra2 = null;
                extras = null;
            }
            if (c0934c != null) {
                stringExtra = c0934c.aT();
                if (stringExtra == null || stringExtra.equalsIgnoreCase(C0204c.fJ) || !C0204c.fM.equalsIgnoreCase(c0934c.aV()) || System.currentTimeMillis() - C0209h.bG().getLong(c0934c.getKey() + C0209h.ga, 0) >= 4000) {
                    C0218c bn = C0218c.bn();
                    if (stringExtra2 == null) {
                        stringExtra2 = c0934c.getKey();
                    }
                    bn.m117a(stringExtra2, c0934c, extras);
                    if (c0934c != null && c0934c.aV().equals(C0204c.fM) && c0934c.aW()) {
                        C0198a.aA().m47a(c0934c);
                    }
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private void m121d(@Nullable Intent intent) {
        ActivityRecognitionResult extractResult = ActivityRecognitionResult.extractResult(intent);
        if (extractResult != null) {
            ArrayList arrayList = (ArrayList) extractResult.getProbableActivities();
            if (arrayList != null) {
                Intent intent2 = new Intent(C0204c.fQ);
                intent2.putExtra(C0204c.fR, arrayList);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
            }
        }
    }
}
