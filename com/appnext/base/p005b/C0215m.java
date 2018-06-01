package com.appnext.base.p005b;

import com.appnext.base.C0216b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class C0215m {
    private String gA;
    private String gB;

    public C0215m(String str, String str2) {
        this.gA = str;
        this.gB = str2;
        if (this.gB.charAt(this.gB.length() - 1) != '/') {
            this.gB += "/";
        }
        as("");
    }

    private void as(String str) {
        File file = new File(this.gB + str);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    public void bR() {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        Throwable th;
        FileInputStream fileInputStream2;
        ZipInputStream zipInputStream = null;
        C0213k.m107g("sals", "Unzipped started");
        if (this.gA == null || this.gA.length() == 0 || this.gB == null || this.gB.length() == 0) {
            C0213k.m107g("sals", "Unzipped abort");
            return;
        }
        ZipInputStream zipInputStream2;
        try {
            byte[] bArr = new byte[2048];
            fileInputStream = new FileInputStream(this.gA);
            try {
                zipInputStream2 = new ZipInputStream(fileInputStream);
                fileOutputStream = null;
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream2.getNextEntry();
                        if (nextEntry == null) {
                            break;
                        }
                        if (nextEntry.isDirectory()) {
                            as(nextEntry.getName());
                        } else {
                            try {
                                String str = this.gB + "/" + nextEntry.getName();
                                C0213k.m111k("slas", "Unzipping ");
                                if (nextEntry.isDirectory()) {
                                    as(nextEntry.getName());
                                } else {
                                    FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                                    while (true) {
                                        try {
                                            int read = zipInputStream2.read(bArr);
                                            if (read <= 0) {
                                                break;
                                            }
                                            fileOutputStream2.write(bArr, 0, read);
                                        } catch (Throwable th2) {
                                            th = th2;
                                            fileOutputStream = fileOutputStream2;
                                        }
                                    }
                                    fileOutputStream = fileOutputStream2;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                            fileOutputStream = null;
                        }
                        C0213k.m107g("sals", "Unzipped finished");
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th5) {
                        C0213k.m107g("sals", "Unzipped failed 2");
                        C0216b.m114a(th5);
                        return;
                    }
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (zipInputStream2 != null) {
                    zipInputStream2.close();
                    return;
                }
                return;
            } catch (Throwable th6) {
                th5 = th6;
                fileOutputStream = null;
                zipInputStream2 = null;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (zipInputStream2 != null) {
                    zipInputStream2.close();
                }
                throw th5;
            }
        } catch (Throwable th7) {
            th5 = th7;
            fileOutputStream = null;
            zipInputStream2 = null;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (zipInputStream2 != null) {
                zipInputStream2.close();
            }
            throw th5;
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
            ZipInputStream zipInputStream3 = null;
        }
        C0213k.m107g("sals", "Unzipped finished");
        throw th5;
    }
}
