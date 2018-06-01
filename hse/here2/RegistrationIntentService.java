package hse.here2;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

public class RegistrationIntentService extends IntentService {
    String accion = "A";
    String codigo;
    int idusu;
    String[] usu;

    private void sendRegistrationToServer(java.lang.String r12) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x007d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r11 = this;
        r8 = r11.idusu;
        if (r8 <= 0) goto L_0x007d;
    L_0x0004:
        r8 = "";
        r8 = r12.equals(r8);
        if (r8 != 0) goto L_0x007d;
    L_0x000c:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "http://srv1.androidcreator.com/srv/guardargcmid.php?idusu=";
        r8 = r8.append(r9);
        r9 = r11.idusu;
        r8 = r8.append(r9);
        r9 = "&gcmid=";
        r8 = r8.append(r9);
        r8 = r8.append(r12);
        r9 = "&accion=";
        r8 = r8.append(r9);
        r9 = r11.accion;
        r8 = r8.append(r9);
        r2 = r8.toString();
        r1 = 0;
        r5 = new java.net.URL;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r5.<init>(r2);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r5.openConnection();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r0 = r8;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r1 = r0;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = 1;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r1.setDoInput(r8);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r1.setConnectTimeout(r8);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r1.setReadTimeout(r8);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = "User-Agent";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = "Android Vinebre Software";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r1.setRequestProperty(r8, r9);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r3 = r1.getInputStream();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r6 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8.<init>(r3);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r6.<init>(r8);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r7 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r7.<init>();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
    L_0x006d:
        r4 = r6.readLine();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        if (r4 == 0) goto L_0x007e;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
    L_0x0073:
        r7.append(r4);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        goto L_0x006d;
    L_0x0077:
        r8 = move-exception;
        if (r1 == 0) goto L_0x007d;
    L_0x007a:
        r1.disconnect();
    L_0x007d:
        return;
    L_0x007e:
        r8 = r7.toString();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = "ANDROID:OK";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r8.indexOf(r9);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = -1;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        if (r8 == r9) goto L_0x00aa;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
    L_0x008b:
        r8 = r11.accion;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = "A";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r8.equals(r9);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        if (r8 == 0) goto L_0x00aa;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
    L_0x0095:
        r8 = "sh";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = 0;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r11.getSharedPreferences(r8, r9);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r8.edit();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r9 = "SENT_TOKEN_TO_SERVER";	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r10 = 1;	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8 = r8.putBoolean(r9, r10);	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
        r8.apply();	 Catch:{ Exception -> 0x0077, all -> 0x00b0 }
    L_0x00aa:
        if (r1 == 0) goto L_0x007d;
    L_0x00ac:
        r1.disconnect();
        goto L_0x007d;
    L_0x00b0:
        r8 = move-exception;
        if (r1 == 0) goto L_0x00b6;
    L_0x00b3:
        r1.disconnect();
    L_0x00b6:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: hse.here2.RegistrationIntentService.sendRegistrationToServer(java.lang.String):void");
    }

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }

    protected void onHandleIntent(Intent intent) {
        SharedPreferences settings = getSharedPreferences("sh", 0);
        this.idusu = settings.getInt("idusu", 0);
        this.codigo = settings.getString("cod", "");
        if (this.idusu > 0) {
            try {
                sendRegistrationToServer(InstanceID.getInstance(this).getToken(config.SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null));
            } catch (Exception e) {
                settings.edit().putBoolean("SENT_TOKEN_TO_SERVER", false).apply();
            }
        }
    }
}
