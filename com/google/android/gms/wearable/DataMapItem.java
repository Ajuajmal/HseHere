package com.google.android.gms.wearable;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.internal.zzsi;
import com.google.android.gms.internal.zzsi.zza;
import com.google.android.gms.internal.zzsj;
import com.google.android.gms.internal.zzst;
import java.util.ArrayList;
import java.util.List;

public class DataMapItem {
    private final Uri mUri;
    private final DataMap zzbrd;

    private DataMapItem(DataItem source) {
        this.mUri = source.getUri();
        this.zzbrd = zza((DataItem) source.freeze());
    }

    public static DataMapItem fromDataItem(DataItem dataItem) {
        if (dataItem != null) {
            return new DataMapItem(dataItem);
        }
        throw new IllegalStateException("provided dataItem is null");
    }

    private DataMap zza(DataItem dataItem) {
        Throwable e;
        if (dataItem.getData() == null && dataItem.getAssets().size() > 0) {
            throw new IllegalArgumentException("Cannot create DataMapItem from a DataItem  that wasn't made with DataMapItem.");
        } else if (dataItem.getData() == null) {
            return new DataMap();
        } else {
            try {
                List arrayList = new ArrayList();
                int size = dataItem.getAssets().size();
                for (int i = 0; i < size; i++) {
                    DataItemAsset dataItemAsset = (DataItemAsset) dataItem.getAssets().get(Integer.toString(i));
                    if (dataItemAsset == null) {
                        throw new IllegalStateException("Cannot find DataItemAsset referenced in data at " + i + " for " + dataItem);
                    }
                    arrayList.add(Asset.createFromRef(dataItemAsset.getId()));
                }
                return zzsi.zza(new zza(zzsj.zzA(dataItem.getData()), arrayList));
            } catch (zzst e2) {
                e = e2;
            } catch (NullPointerException e3) {
                e = e3;
            }
        }
        Log.w("DataItem", "Unable to parse datamap from dataItem. uri=" + dataItem.getUri() + ", data=" + Base64.encodeToString(dataItem.getData(), 0));
        throw new IllegalStateException("Unable to parse datamap from dataItem.  uri=" + dataItem.getUri(), e);
    }

    public DataMap getDataMap() {
        return this.zzbrd;
    }

    public Uri getUri() {
        return this.mUri;
    }
}
