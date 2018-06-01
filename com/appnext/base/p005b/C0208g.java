package com.appnext.base.p005b;

import android.text.TextUtils;
import android.util.Base64;
import com.appnext.base.C0216b;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class C0208g {
    private static final String TAG = "Generator";
    private static final C0208g fY = new C0208g();
    private static final String fa = "fqJfdzGDvfwbedsKSUGty3VZ9taXxMVw";

    private C0208g() {
        init();
    }

    public static C0208g bF() {
        return fY;
    }

    private void init() {
    }

    public String an(String str) {
        String str2 = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                byte[] bArr = new byte[16];
                new SecureRandom().nextBytes(bArr);
                Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
                instance.init(1, new SecretKeySpec(fa.getBytes("utf-8"), "AES"), new IvParameterSpec(bArr));
                str2 = Base64.encodeToString(m71a(bArr, instance.doFinal(str.getBytes("utf-8"))), 2);
            } catch (Throwable th) {
                C0216b.m114a(th);
            }
        }
        return str2;
    }

    public String ao(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(str, 2);
            byte[] copyOfRange = Arrays.copyOfRange(decode, 0, 16);
            byte[] copyOfRange2 = Arrays.copyOfRange(decode, 16, decode.length);
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, new SecretKeySpec(fa.getBytes("utf-8"), "AES"), new IvParameterSpec(copyOfRange));
            return new String(instance.doFinal(copyOfRange2), "utf-8");
        } catch (Throwable th) {
            C0216b.m114a(th);
            return null;
        }
    }

    private byte[] m71a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(bArr.length + bArr2.length)];
        int i = 0;
        while (i < bArr3.length) {
            bArr3[i] = i < bArr.length ? bArr[i] : bArr2[i - bArr.length];
            i++;
        }
        return bArr3;
    }
}
