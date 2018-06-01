package hse.here2;

import android.app.IntentService;

public class s_guardarpos extends IntentService {
    String codigo;
    int idusu;
    String[] usu;
    String f39x;
    String f40y;

    protected void onHandleIntent(android.content.Intent r12) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r11 = this;
        r10 = 0;
        r9 = "sh";
        r8 = r11.getSharedPreferences(r9, r10);
        r9 = "idusu";
        r9 = r8.getInt(r9, r10);
        r11.idusu = r9;
        r9 = "cod";
        r10 = "";
        r9 = r8.getString(r9, r10);
        r11.codigo = r9;
        r9 = "x";
        r10 = "";
        r9 = r8.getString(r9, r10);
        r11.f39x = r9;
        r9 = "y";
        r10 = "";
        r9 = r8.getString(r9, r10);
        r11.f40y = r9;
        r9 = r11.idusu;
        if (r9 <= 0) goto L_0x00d7;
    L_0x0031:
        r9 = r11.f39x;
        r10 = "";
        r9 = r9.equals(r10);
        if (r9 != 0) goto L_0x00d7;
    L_0x003b:
        r9 = r11.f40y;
        r10 = "";
        r9 = r9.equals(r10);
        if (r9 != 0) goto L_0x00d7;
    L_0x0045:
        r1 = 0;
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9.<init>();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "http://srv1.androidcreator.com/srv/guardarpos.php?idusu=";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = r11.idusu;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "&c=";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = r11.codigo;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "&x=";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = r11.f39x;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "&y=";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = r11.f40y;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r2 = r9.toString();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r5 = new java.net.URL;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r5.<init>(r2);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r5.openConnection();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r0 = r9;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r1 = r0;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = 1;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r1.setDoInput(r9);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r1.setConnectTimeout(r9);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r1.setReadTimeout(r9);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = "User-Agent";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "Android Vinebre Software";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r1.setRequestProperty(r9, r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r3 = r1.getInputStream();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r6 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9.<init>(r3);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r6.<init>(r9);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r7.<init>();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
    L_0x00b4:
        r4 = r6.readLine();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        if (r4 == 0) goto L_0x00d8;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
    L_0x00ba:
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9.<init>();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r4);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r10 = "\n";	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r9 = r9.toString();	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        r7.append(r9);	 Catch:{ Exception -> 0x00d1, all -> 0x00de }
        goto L_0x00b4;
    L_0x00d1:
        r9 = move-exception;
        if (r1 == 0) goto L_0x00d7;
    L_0x00d4:
        r1.disconnect();
    L_0x00d7:
        return;
    L_0x00d8:
        if (r1 == 0) goto L_0x00d7;
    L_0x00da:
        r1.disconnect();
        goto L_0x00d7;
    L_0x00de:
        r9 = move-exception;
        if (r1 == 0) goto L_0x00e4;
    L_0x00e1:
        r1.disconnect();
    L_0x00e4:
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: hse.here2.s_guardarpos.onHandleIntent(android.content.Intent):void");
    }

    public s_guardarpos() {
        super("s_guardarpos");
    }
}
