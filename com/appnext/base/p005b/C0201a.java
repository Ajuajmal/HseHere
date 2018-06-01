package com.appnext.base.p005b;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.appnext.base.C0216b;
import com.appnext.base.services.OperationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;
import java.util.ArrayList;
import java.util.List;

public class C0201a {
    protected static final String TAG = C0201a.class.getSimpleName();
    private static final long fo = 1000;
    private GoogleApiClient fl;
    private C0942b fm;
    private C0199a fn = null;
    private C0200c fp;

    private class C0199a extends BroadcastReceiver {
        final /* synthetic */ C0201a fq;

        private C0199a(C0201a c0201a) {
            this.fq = c0201a;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null) {
                C0205d.init(context);
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(C0204c.fR);
                synchronized (this.fq) {
                    this.fq.m56f(parcelableArrayListExtra);
                }
            }
        }
    }

    public interface C0200c {
        void mo4207b(List<DetectedActivity> list);

        void onError(String str);
    }

    class C09401 implements ResultCallback<Status> {
        final /* synthetic */ C0201a fq;

        C09401(C0201a c0201a) {
            this.fq = c0201a;
        }

        public void onResult(@NonNull Status status) {
            synchronized (this.fq) {
                if (!status.isSuccess()) {
                    this.fq.ae(C0201a.TAG + " " + status.getStatusMessage());
                }
            }
        }
    }

    class C09412 implements ResultCallback<Status> {
        final /* synthetic */ C0201a fq;

        C09412(C0201a c0201a) {
            this.fq = c0201a;
        }

        public void onResult(@NonNull Status status) {
            try {
                if (this.fq.fl != null && this.fq.fl.isConnected()) {
                    this.fq.fl.disconnect();
                }
                this.fq.fl = null;
                this.fq.fm = null;
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
    }

    private class C0942b implements ConnectionCallbacks, OnConnectionFailedListener {
        final /* synthetic */ C0201a fq;

        private C0942b(C0201a c0201a) {
            this.fq = c0201a;
        }

        public void onConnected(Bundle bundle) {
            synchronized (this.fq) {
                this.fq.bv();
            }
        }

        public void onConnectionSuspended(int i) {
            synchronized (this.fq) {
                if (this.fq.fl != null) {
                    this.fq.fl.connect();
                }
            }
        }

        public void onConnectionFailed(@Nullable ConnectionResult connectionResult) {
            synchronized (this.fq) {
                if (connectionResult != null) {
                    try {
                        this.fq.ae(connectionResult.getErrorMessage());
                    } catch (Throwable th) {
                        this.fq.ae(C0201a.TAG + " Error connecting GoogleApiClient");
                    }
                } else {
                    this.fq.ae(C0201a.TAG + " Error connecting GoogleApiClient");
                }
            }
        }
    }

    public void m57a(C0200c c0200c) {
        this.fp = c0200c;
    }

    public void init() {
        synchronized (this) {
            if (cw()) {
                this.fl.connect();
            } else {
                ae(TAG + " Google Api ActivityRecognition not available.");
            }
        }
    }

    public void stop() {
        synchronized (this) {
            if (this.fn != null) {
                LocalBroadcastManager.getInstance(C0205d.getContext()).unregisterReceiver(this.fn);
                this.fn = null;
            }
            bw();
        }
    }

    private boolean cw() {
        try {
            this.fm = new C0942b();
            this.fl = new Builder(C0205d.getContext()).addConnectionCallbacks(this.fm).addOnConnectionFailedListener(this.fm).addApi(ActivityRecognition.API).build();
            return true;
        } catch (Throwable th) {
            C0216b.m114a(th);
            return false;
        }
    }

    private static boolean bu() {
        return C0207f.m65b(C0205d.getContext(), "com.google.android.gms.permission.ACTIVITY_RECOGNITION");
    }

    @SuppressLint({"all"})
    private void bv() {
        try {
            if (!C0201a.bu()) {
                ae(TAG + " No permissions for activity recognition.");
            } else if (this.fl == null || !this.fl.isConnected()) {
                ae(TAG + " Google Api Client not connected.");
            } else {
                this.fn = new C0199a();
                LocalBroadcastManager.getInstance(C0205d.getContext()).registerReceiver(this.fn, new IntentFilter(C0204c.fQ));
                ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(this.fl, fo, bx()).setResultCallback(new C09401(this));
            }
        } catch (Throwable th) {
            ae(TAG + " Google Api Client not connected.");
        }
    }

    @SuppressLint({"all"})
    private void bw() {
        try {
            if (this.fl == null) {
                return;
            }
            if (!this.fl.isConnected()) {
                this.fl.disconnect();
                this.fl = null;
                this.fm = null;
            } else if (C0201a.bu()) {
                ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(this.fl, bx()).setResultCallback(new C09412(this));
            } else {
                this.fl.disconnect();
                this.fl = null;
                this.fm = null;
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private PendingIntent bx() {
        Intent intent = new Intent(C0205d.getContext(), OperationService.class);
        intent.putExtra(C0204c.fP, C0204c.fP);
        return PendingIntent.getService(C0205d.getContext(), 0, intent, 134217728);
    }

    private void m56f(ArrayList<DetectedActivity> arrayList) {
        if (this.fp != null) {
            this.fp.mo4207b(arrayList);
        }
    }

    private void ae(String str) {
        if (this.fp != null) {
            this.fp.onError(str);
        }
    }
}
