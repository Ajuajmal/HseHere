package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzbk extends zzak {
    private static final String ID = zzad.MOBILE_ADWORDS_UNIQUE_ID.toString();
    private final Context mContext;

    public zzbk(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public boolean zzFW() {
        return true;
    }

    public zza zzP(Map<String, zza> map) {
        String zzaY = zzaY(this.mContext);
        return zzaY == null ? zzdf.zzHF() : zzdf.zzR(zzaY);
    }

    protected String zzaY(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }
}
