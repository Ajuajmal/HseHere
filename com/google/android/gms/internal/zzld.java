package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.proxy.ProxyApi.ProxyResult;
import com.google.android.gms.auth.api.proxy.ProxyRequest;
import com.google.android.gms.auth.api.proxy.ProxyResponse;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzx;

public class zzld implements ProxyApi {
    public PendingResult<ProxyResult> performProxyRequest(GoogleApiClient client, final ProxyRequest request) {
        zzx.zzz(client);
        zzx.zzz(request);
        return client.zzb(new zzlc(this, client) {
            final /* synthetic */ zzld zzWI;

            class C13161 extends zzky {
                final /* synthetic */ C13371 zzWJ;

                C13161(C13371 c13371) {
                    this.zzWJ = c13371;
                }

                public void zza(ProxyResponse proxyResponse) {
                    this.zzWJ.zza(new zzle(proxyResponse));
                }
            }

            protected void zza(Context context, zzlb com_google_android_gms_internal_zzlb) throws RemoteException {
                com_google_android_gms_internal_zzlb.zza(new C13161(this), request);
            }
        });
    }
}
