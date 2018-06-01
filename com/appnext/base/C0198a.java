package com.appnext.base;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import com.appnext.base.operations.C0218c;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0206e;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0212j;
import com.appnext.base.p005b.C0213k;
import com.appnext.base.services.OperationService;
import com.appnext.base.services.ReceiverService;
import java.util.List;

public class C0198a {
    public static final String TAG = C0198a.class.getSimpleName();
    private static volatile C0198a dB;

    public static C0198a aA() {
        if (dB == null) {
            synchronized (C0198a.class) {
                if (dB == null) {
                    dB = new C0198a();
                }
            }
        }
        return dB;
    }

    private C0198a() {
    }

    public void m48a(List<C0934c> list) {
        for (C0934c c0934c : list) {
            if (c0934c != null) {
                String key = c0934c.getKey();
                PendingIntent service = PendingIntent.getService(C0205d.getContext(), key.hashCode(), new Intent(C0205d.getContext(), OperationService.class), 134217728);
                if (service != null) {
                    ((AlarmManager) C0205d.getContext().getSystemService("alarm")).cancel(service);
                }
            }
        }
        C0205d.getContext().stopService(new Intent(C0205d.getContext(), OperationService.class));
        C0205d.getContext().stopService(new Intent(C0205d.getContext(), ReceiverService.class));
        C0218c.bn().bo();
    }

    public void aB() {
        aC();
        aD();
    }

    private void aC() {
        try {
            List<C0934c> bC = C0206e.bB().bC();
            if (bC != null) {
                for (C0934c c0934c : bC) {
                    if (!(c0934c == null || C0204c.fJ.equalsIgnoreCase(c0934c.aT()))) {
                        String key = c0934c.getKey();
                        Intent intent = new Intent(C0205d.getContext(), OperationService.class);
                        if (C0204c.fN.equalsIgnoreCase(c0934c.aR())) {
                            long d = C0212j.m96d(c0934c.aS(), c0934c.aT());
                            if (!C0204c.fK.equalsIgnoreCase(c0934c.aT())) {
                                long j = C0209h.bG().getLong(key + C0209h.ga, 0);
                                d += j;
                                if (!c0934c.aV().equalsIgnoreCase(C0204c.fL)) {
                                    C0212j.m93a(C0205d.getContext(), OperationService.class, Math.max(d, System.currentTimeMillis()), c0934c);
                                } else if (j == 0) {
                                    intent.putExtra(C0204c.fD, key);
                                    C0205d.getContext().startService(intent);
                                }
                            } else if (d > 0) {
                                C0212j.m93a(C0205d.getContext(), OperationService.class, d, c0934c);
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
            C0213k.m107g(TAG, th.toString());
        }
    }

    private void aD() {
        C0205d.getContext().startService(new Intent(C0205d.getContext(), ReceiverService.class));
    }

    public void m47a(C0934c c0934c) {
        new Intent(C0205d.getContext(), OperationService.class).putExtra(C0204c.fD, c0934c.getKey());
        C0212j.m93a(C0205d.getContext(), OperationService.class, C0212j.m96d(c0934c.aS(), c0934c.aT()) + System.currentTimeMillis(), c0934c);
    }
}
