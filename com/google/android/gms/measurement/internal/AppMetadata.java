package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class AppMetadata implements SafeParcelable {
    public static final zzb CREATOR = new zzb();
    public final String packageName;
    public final int versionCode;
    public final String zzaMV;
    public final String zzaVt;
    public final String zzaVu;
    public final long zzaVv;
    public final long zzaVw;
    public final String zzaVx;
    public final boolean zzaVy;
    public final boolean zzaVz;

    AppMetadata(int versionCode, String packageName, String gmpAppId, String appVersion, String appStore, long gmpVersion, long devCertHash, String healthMonitor, boolean measurementEnabled, boolean firstOpen) {
        this.versionCode = versionCode;
        this.packageName = packageName;
        this.zzaVt = gmpAppId;
        this.zzaMV = appVersion;
        this.zzaVu = appStore;
        this.zzaVv = gmpVersion;
        this.zzaVw = devCertHash;
        this.zzaVx = healthMonitor;
        if (versionCode >= 3) {
            this.zzaVy = measurementEnabled;
        } else {
            this.zzaVy = true;
        }
        this.zzaVz = firstOpen;
    }

    AppMetadata(String packageName, String gmpAppId, String appVersion, String appStore, long gmpVersion, long devCertHash, String healthMonitor, boolean measurementEnabled, boolean firstOpen) {
        zzx.zzcM(packageName);
        this.versionCode = 4;
        this.packageName = packageName;
        if (TextUtils.isEmpty(gmpAppId)) {
            gmpAppId = null;
        }
        this.zzaVt = gmpAppId;
        this.zzaMV = appVersion;
        this.zzaVu = appStore;
        this.zzaVv = gmpVersion;
        this.zzaVw = devCertHash;
        this.zzaVx = healthMonitor;
        this.zzaVy = measurementEnabled;
        this.zzaVz = firstOpen;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        zzb.zza(this, out, flags);
    }
}
