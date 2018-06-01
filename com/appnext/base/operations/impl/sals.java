package com.appnext.base.operations.impl;

import android.os.Bundle;
import com.appnext.base.C0216b;
import com.appnext.base.operations.C1206d;
import com.appnext.base.p001a.C0192a;
import com.appnext.base.p001a.p003b.C0934c;
import com.appnext.base.p005b.C0204c;
import com.appnext.base.p005b.C0205d;
import com.appnext.base.p005b.C0209h;
import com.appnext.base.p005b.C0213k;
import com.appnext.base.p005b.C0215m;
import com.appnext.core.C0266f;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;

public class sals extends C1206d {
    private static final String eR = (C0205d.getContext().getFilesDir().getAbsolutePath() + C0204c.fv);
    private static final String eS = (eR + C0204c.ft);
    private static final String eT = (eR + C0204c.fu);

    public sals(C0934c c0934c, Bundle bundle) {
        super(c0934c, bundle);
    }

    protected String getData() {
        try {
            if (C0266f.cy() < 6) {
                C0213k.m107g("sals", "Free heap below 6 MB aborting download");
            } else {
                File file = new File(eT);
                if (!file.exists()) {
                    if (ac(C0204c.fz)) {
                        File file2 = new File(eS);
                        if (file2.exists()) {
                            new C0215m(file2.toString(), new File(eR).toString()).bR();
                            file2.delete();
                            C0213k.m107g("sals", "Delete zip file");
                        }
                    }
                }
                C0192a.aE().aG().delete();
                InputStream fileInputStream = new FileInputStream(eT);
                C0213k.m107g("sals", "Read from file");
                JSONArray jSONArray = new JSONArray(m507a(fileInputStream));
                C0213k.m107g("sals", "Finished read from file");
                C0192a.aE().aG().m441a(jSONArray);
                C0209h.bG().putBoolean(C0209h.gh, true);
                file.delete();
            }
        } catch (Throwable th) {
            C0213k.m107g("sals", "sals Failed");
            C0216b.m114a(th);
        }
        return null;
    }

    public static String m507a(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                stringBuilder.append(readLine);
            } else {
                bufferedReader.close();
                return stringBuilder.toString();
            }
        }
    }

    private static boolean ac(String str) {
        try {
            C0213k.m107g("sals", "Start downloading");
            File file = new File(eS);
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                return true;
            }
            new File(eR).mkdirs();
            byte[] b = C0266f.m204b(str, null, false, C0204c.fC);
            if (b == null) {
                return false;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(eS);
            fileOutputStream.write(b);
            fileOutputStream.flush();
            fileOutputStream.close();
            C0213k.m107g("sals", "Download finished");
            return true;
        } catch (Throwable th) {
        }
        return false;
    }
}
