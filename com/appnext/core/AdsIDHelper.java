package com.appnext.core;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

public class AdsIDHelper {
    protected static String m123r(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return "";
        }
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            if (advertisingIdInfo == null || advertisingIdInfo.isLimitAdTrackingEnabled()) {
                return "";
            }
            return advertisingIdInfo.getId();
        } catch (IOException e) {
            return "";
        } catch (GooglePlayServicesRepairableException e2) {
            return "";
        } catch (GooglePlayServicesNotAvailableException e3) {
            return "";
        }
    }
}
