package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.appnext.base.p005b.C0204c;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class zzd<T extends SafeParcelable> extends AbstractDataBuffer<T> {
    private static final String[] zzajg = new String[]{C0204c.fS};
    private final Creator<T> zzajh;

    public zzd(DataHolder dataHolder, Creator<T> creator) {
        super(dataHolder);
        this.zzajh = creator;
    }

    public /* synthetic */ Object get(int i) {
        return zzbG(i);
    }

    public T zzbG(int i) {
        byte[] zzg = this.zzahi.zzg(C0204c.fS, i, this.zzahi.zzbH(i));
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(zzg, 0, zzg.length);
        obtain.setDataPosition(0);
        SafeParcelable safeParcelable = (SafeParcelable) this.zzajh.createFromParcel(obtain);
        obtain.recycle();
        return safeParcelable;
    }
}
