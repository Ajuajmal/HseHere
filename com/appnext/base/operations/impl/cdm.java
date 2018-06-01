package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.C0198a;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0212j;
import com.appnext.base.p005b.C0213k;
import com.appnext.core.C0266f;
import java.util.List;
import org.apache.http.HttpStatus;
import org.json.JSONArray;

public class cdm extends C1206d {
    private final String TAG = "cdm";
    private final String eF = "[ { \"status\": \"on\", \"sample\":\"1\", \"sample_type\":\"hour\", \"cycle\":\"1\", \"cycle_type\":\"interval\", \"exact\":\"false\", \"key\":\"cdm\" } ]";

    public cdm(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
        C0209h.bG().init(C0205d.getContext());
    }

    protected String getData() {
        if (!C0212j.m106q(C0205d.getContext())) {
            String a;
            try {
                a = C0266f.m196a("https://appnext.hs.llnwd.net/tools/services/4.5.6/config.json?aid=" + C0266f.m207w(C0205d.getContext()) + "&luid=332820-329383&luver=1.3.3.9&pkg=" + C0205d.getContext().getPackageName(), null);
            } catch (Throwable e) {
                int responseCode = e.responseCode();
                if (responseCode == HttpStatus.SC_BAD_REQUEST || responseCode == HttpStatus.SC_UNAUTHORIZED || responseCode == 402 || responseCode == HttpStatus.SC_FORBIDDEN || responseCode == 404 || responseCode == 405 || responseCode == HttpStatus.SC_INTERNAL_SERVER_ERROR || responseCode == HttpStatus.SC_NOT_IMPLEMENTED || responseCode == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                    C0216b.m114a(e);
                }
                a = "[ { \"status\": \"on\", \"sample\":\"1\", \"sample_type\":\"hour\", \"cycle\":\"1\", \"cycle_type\":\"interval\", \"exact\":\"false\", \"key\":\"cdm\" } ]";
            } catch (Throwable e2) {
                C0213k.m110j("cdm", e2.toString());
                a = "[ { \"status\": \"on\", \"sample\":\"1\", \"sample_type\":\"hour\", \"cycle\":\"1\", \"cycle_type\":\"interval\", \"exact\":\"false\", \"key\":\"cdm\" } ]";
            }
            try {
                List ab = ab(a);
                if (ab != null) {
                    C0198a.aA().m48a(ab);
                }
                C0198a.aA().aB();
            } catch (Throwable e22) {
                C0216b.m114a(e22);
            }
        }
        return null;
    }

    private List<C0934c> ab(String str) {
        List<C0934c> bb = C0192a.aE().aJ().bb();
        try {
            JSONArray jSONArray = new JSONArray(str);
            C0192a.aE().aJ().delete();
            C0192a.aE().aJ().m453a(jSONArray);
            return bb;
        } catch (Throwable th) {
            C0213k.m110j("cdm", th.getMessage());
            return null;
        }
    }
}
