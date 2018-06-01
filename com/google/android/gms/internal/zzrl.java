package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.signin.internal.zzg;
import com.google.android.gms.signin.internal.zzh;

public final class zzrl {
    public static final Api<zzro> API = new Api("SignIn.API", zzUJ, zzUI);
    public static final zzc<zzh> zzUI = new zzc();
    public static final com.google.android.gms.common.api.Api.zza<zzh, zzro> zzUJ = new C11251();
    public static final Scope zzWW = new Scope(Scopes.PROFILE);
    public static final Scope zzWX = new Scope("email");
    public static final Api<zza> zzaoG = new Api("SignIn.INTERNAL_API", zzbgS, zzavN);
    public static final zzc<zzh> zzavN = new zzc();
    static final com.google.android.gms.common.api.Api.zza<zzh, zza> zzbgS = new C11262();
    public static final zzrm zzbgT = new zzg();

    static class C11251 extends com.google.android.gms.common.api.Api.zza<zzh, zzro> {
        C11251() {
        }

        public zzh zza(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, zzro com_google_android_gms_internal_zzro, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzh(context, looper, true, com_google_android_gms_common_internal_zzf, com_google_android_gms_internal_zzro == null ? zzro.zzbgV : com_google_android_gms_internal_zzro, connectionCallbacks, onConnectionFailedListener);
        }
    }

    static class C11262 extends com.google.android.gms.common.api.Api.zza<zzh, zza> {
        C11262() {
        }

        public zzh zza(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, zza com_google_android_gms_internal_zzrl_zza, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzh(context, looper, false, com_google_android_gms_common_internal_zzf, com_google_android_gms_internal_zzrl_zza.zzFF(), connectionCallbacks, onConnectionFailedListener);
        }
    }

    public static class zza implements HasOptions {
        private final Bundle zzbgU;

        public Bundle zzFF() {
            return this.zzbgU;
        }
    }
}
