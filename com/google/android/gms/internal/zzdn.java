package com.google.android.gms.internal;

import android.text.TextUtils;
import com.appnext.base.p005b.C0204c;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

public class zzdn implements zzdf {
    private final zza zzzH;

    public interface zza {
        void zzb(RewardItemParcel rewardItemParcel);

        void zzbq();
    }

    public zzdn(zza com_google_android_gms_internal_zzdn_zza) {
        this.zzzH = com_google_android_gms_internal_zzdn_zza;
    }

    public static void zza(zzjp com_google_android_gms_internal_zzjp, zza com_google_android_gms_internal_zzdn_zza) {
        com_google_android_gms_internal_zzjp.zzhU().zza("/reward", new zzdn(com_google_android_gms_internal_zzdn_zza));
    }

    private void zze(Map<String, String> map) {
        RewardItemParcel rewardItemParcel;
        try {
            int parseInt = Integer.parseInt((String) map.get("amount"));
            String str = (String) map.get(C0204c.fU);
            if (!TextUtils.isEmpty(str)) {
                rewardItemParcel = new RewardItemParcel(str, parseInt);
                this.zzzH.zzb(rewardItemParcel);
            }
        } catch (Throwable e) {
            zzb.zzd("Unable to parse reward amount.", e);
        }
        rewardItemParcel = null;
        this.zzzH.zzb(rewardItemParcel);
    }

    private void zzf(Map<String, String> map) {
        this.zzzH.zzbq();
    }

    public void zza(zzjp com_google_android_gms_internal_zzjp, Map<String, String> map) {
        String str = (String) map.get(C0204c.fT);
        if ("grant".equals(str)) {
            zze(map);
        } else if ("video_start".equals(str)) {
            zzf(map);
        }
    }
}
