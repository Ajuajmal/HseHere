package com.appnext.base.receivers.imp;

import android.os.Environment;
import android.os.FileObserver;
import com.appnext.base.p005b.C0204c.C0203a;
import com.appnext.base.p005b.C0212j;
import com.appnext.base.receivers.C0224c;

public class caact implements C0224c {
    public static final String fa = caact.class.getSimpleName();
    private static final String iA = "/Camera/";
    private static FileObserver iz;

    public boolean register() {
        iz = new FileObserver(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + iA) {
            final /* synthetic */ caact iB;

            public void onEvent(int i, String str) {
                if (str != null && !str.equals(".probe") && i == 256) {
                    this.iB.cu();
                }
            }
        };
        iz.startWatching();
        return true;
    }

    public void unregister() {
        if (iz != null) {
            iz.stopWatching();
        }
    }

    private void cu() {
        C0212j.m95c(fa, "true", C0203a.Boolean);
    }
}
