package com.appnext.base.p005b;

import android.text.TextUtils;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C0217b;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0933b;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p001a.p004c.C0937b;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class C0202b {
    public static boolean m62c(C0934c c0934c) {
        if (c0934c == null) {
            return false;
        }
        int i = C0209h.bG().getInt(c0934c.getKey() + C0209h.gb, 0);
        try {
            if (i >= Integer.parseInt(c0934c.aU()) || i == 0) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            C0216b.m114a(e);
            return true;
        }
    }

    private static List<C0933b> af(String str) {
        List<C0933b> list = null;
        try {
            list = C0192a.aE().aH().m446T(str);
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
        return list;
    }

    public static boolean ag(String str) {
        List af = C0202b.af(str);
        return af == null || af.size() == 0;
    }

    public static String m59a(List<C0933b> list, C0217b c0217b) {
        JSONArray jSONArray = new JSONArray();
        try {
            for (C0933b c0933b : list) {
                if (c0933b.aP() == null) {
                    return "";
                }
                Object ao = C0208g.bF().ao(c0933b.aP());
                if (TextUtils.isEmpty(ao)) {
                    return "";
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(C0204c.fS, c0217b.mo1242Z(ao));
                jSONObject.put("date", C0212j.m90a(c0933b.aQ()));
                jSONArray.put(jSONObject);
            }
        } catch (Throwable th) {
            C0216b.m114a(th);
        }
        return jSONArray.toString();
    }

    public static void ah(String str) {
        if (str != null) {
            C0209h.bG().putInt(str + C0209h.gb, 0);
        }
    }

    public static void ai(String str) {
        C0209h.bG().putLong(str + C0209h.ga, System.currentTimeMillis());
        String str2 = str + C0209h.gb;
        C0209h.bG().putInt(str2, C0209h.bG().getInt(str2, 0) + 1);
    }

    public static void m61c(List<C0933b> list) {
        C0937b aH = C0192a.aE().aH();
        for (C0933b b : list) {
            aH.m449b(b);
        }
    }

    public static long m58a(String str, String str2, Date date) {
        long j = -1;
        if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(str2))) {
            try {
                j = C0192a.aE().aH().m447a(new C0933b(str, str2, date));
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
        return j;
    }

    public static void m60c(String str, String str2) {
        C0212j.m97e(str, str2);
    }
}
