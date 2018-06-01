package com.appnext.base.p005b;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.appnext.base.C0216b;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

public class C0211i {
    protected static final String TAG = C0211i.class.getSimpleName();
    private static final long gn = 1000;
    private static final long go = 500;
    private GoogleApiClient fl;
    private C0946a gp;
    private LocationSettingsRequest gq;
    private C0947b gr;
    private boolean gs;
    private C0210c gt;

    public interface C0210c {
        void mo4204a(Location location);

        void onError(String str);
    }

    class C09431 implements ResultCallback<LocationSettingsResult> {
        final /* synthetic */ C0211i gu;

        C09431(C0211i c0211i) {
            this.gu = c0211i;
        }

        public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
            synchronized (this.gu) {
                this.gu.m80a(locationSettingsResult.getStatus());
            }
        }
    }

    class C09442 implements ResultCallback<Status> {
        final /* synthetic */ C0211i gu;

        C09442(C0211i c0211i) {
            this.gu = c0211i;
        }

        public void onResult(@NonNull Status status) {
            synchronized (this.gu) {
                this.gu.m84b(status);
            }
        }
    }

    class C09453 implements ResultCallback<Status> {
        final /* synthetic */ C0211i gu;

        C09453(C0211i c0211i) {
            this.gu = c0211i;
        }

        public void onResult(@NonNull Status status) {
            synchronized (this.gu) {
                try {
                    if (this.gu.fl != null && this.gu.fl.isConnected()) {
                        this.gu.fl.disconnect();
                    }
                    this.gu.fl = null;
                    this.gu.gp = null;
                    this.gu.gr = null;
                } catch (Throwable th) {
                    C0216b.m114a(th);
                }
            }
        }
    }

    private class C0946a implements ConnectionCallbacks, OnConnectionFailedListener {
        final /* synthetic */ C0211i gu;

        private C0946a(C0211i c0211i) {
            this.gu = c0211i;
        }

        @SuppressLint({"all"})
        public void onConnected(Bundle bundle) {
            synchronized (this.gu) {
                this.gu.bM();
            }
        }

        public void onConnectionSuspended(int i) {
            synchronized (this.gu) {
                this.gu.bO();
                this.gu.aq("Connection suspended");
            }
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            C0216b.m113P("Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
            synchronized (this.gu) {
                this.gu.bO();
                this.gu.aq(connectionResult.getErrorMessage());
            }
        }
    }

    private class C0947b implements LocationListener {
        final /* synthetic */ C0211i gu;

        private C0947b(C0211i c0211i) {
            this.gu = c0211i;
        }

        public void onLocationChanged(Location location) {
            synchronized (this.gu) {
                this.gu.bO();
                this.gu.m81b(location);
            }
        }
    }

    public void m86a(C0210c c0210c) {
        this.gt = c0210c;
    }

    public void init() {
        synchronized (this) {
            if (C0211i.bu()) {
                this.gs = false;
                bJ();
                return;
            }
            aq("no location permissions");
        }
    }

    public void bH() {
        synchronized (this) {
            this.gs = true;
            if (this.gr != null) {
                bO();
            }
        }
    }

    public static Location bI() {
        if (!C0211i.bu()) {
            return null;
        }
        Location ap = C0211i.ap("gps");
        Location ap2 = C0211i.ap("network");
        if (ap == null) {
            return ap2;
        }
        if (ap2 == null) {
            return ap;
        }
        if (ap.getTime() > ap2.getTime()) {
            return ap;
        }
        return ap2;
    }

    private void bJ() {
        if (cw()) {
            this.fl.connect();
        } else {
            aq(TAG + "  Google Api LocationServices not available");
        }
    }

    private boolean cw() {
        try {
            this.gp = new C0946a();
            this.fl = new Builder(C0205d.getContext()).addConnectionCallbacks(this.gp).addOnConnectionFailedListener(this.gp).addApi(LocationServices.API).build();
            return true;
        } catch (Throwable th) {
            C0216b.m114a(th);
            return false;
        }
    }

    private static LocationRequest bK() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(gn);
        locationRequest.setFastestInterval(go);
        locationRequest.setPriority(100);
        return locationRequest;
    }

    private void bL() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(C0211i.bK());
        this.gq = builder.build();
    }

    private void bM() {
        if (this.gs) {
            bO();
            return;
        }
        try {
            bL();
            LocationServices.SettingsApi.checkLocationSettings(this.fl, this.gq).setResultCallback(new C09431(this));
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private void m80a(Status status) {
        if (this.gs) {
            bO();
            return;
        }
        switch (status.getStatusCode()) {
            case 0:
                bN();
                return;
            default:
                bO();
                aq(status.getStatusMessage());
                return;
        }
    }

    @SuppressLint({"all"})
    private void bN() {
        if (this.gs) {
            bO();
            return;
        }
        try {
            this.gr = new C0947b();
            LocationServices.FusedLocationApi.requestLocationUpdates(this.fl, C0211i.bK(), this.gr).setResultCallback(new C09442(this));
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private void m84b(Status status) {
        if (this.gs) {
            bO();
        } else if (!status.isSuccess()) {
            bO();
            aq(status.getStatusMessage());
        }
    }

    private void bO() {
        try {
            if (this.gr != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(this.fl, this.gr).setResultCallback(new C09453(this));
            } else if (this.fl != null) {
                this.fl.disconnect();
                this.fl = null;
                this.gp = null;
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
    }

    private static boolean bu() {
        return C0207f.m65b(C0205d.getContext(), "android.permission.ACCESS_FINE_LOCATION") && C0207f.m65b(C0205d.getContext(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    @SuppressLint({"all"})
    private static Location ap(String str) {
        try {
            LocationManager locationManager = (LocationManager) C0205d.getContext().getSystemService("location");
            if (locationManager.isProviderEnabled(str)) {
                return locationManager.getLastKnownLocation(str);
            }
        } catch (Throwable e) {
            C0216b.m114a(e);
        }
        return null;
    }

    private void m81b(Location location) {
        if (this.gt != null) {
            this.gt.mo4204a(location);
        }
    }

    private void aq(String str) {
        if (this.gt != null) {
            this.gt.onError(str);
        }
    }
}
