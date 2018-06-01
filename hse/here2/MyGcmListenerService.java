package hse.here2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Builder;
import com.google.android.gms.gcm.GcmListenerService;
import java.io.FileInputStream;

public class MyGcmListenerService extends GcmListenerService {
    public void onMessageReceived(String from, Bundle data) {
        String cad = data.getString("message").replace("@EURO@", "€");
        String[] partes = cad.split(";");
        if (partes.length < 3) {
            cad = cad.replace("@x@", ";");
            int l_aux = cad.split("@").length;
            if (l_aux == 8 || l_aux == 5 || l_aux == 9) {
                config.notificar(this, cad);
            }
        } else if (partes[1].equals("0") || partes[1].equals("1") || partes[1].equals("2") || partes[1].equals("3")) {
            SharedPreferences settings = getSharedPreferences("sh", 0);
            Editor editor = settings.edit();
            if (partes[1].equals("3")) {
                String mensaje_aux = partes[4];
                if (settings.getString("mensajechat_ult", "").equals(mensaje_aux)) {
                    if (System.currentTimeMillis() - settings.getLong("fchat_ult", System.currentTimeMillis()) < 5000) {
                        return;
                    }
                }
                editor.putLong("fchat_ult", System.currentTimeMillis());
                editor.putString("mensajechat_ult", mensaje_aux);
                editor.putString("conv", settings.getString("conv", "") + "@0@" + partes[4].replace("@x@", ";"));
                editor.commit();
            }
            if (partes[1].equals("3")) {
                if (settings.getBoolean("activa", false)) {
                    editor.putString("f_id", "0");
                    editor.putString("f_idfrase", config.idfrase_global + "");
                    editor.putString("f_frase", partes[4].replace("@x@", ";"));
                    config.idfrase_global++;
                    editor.commit();
                    return;
                }
            }
            Bitmap bitmap = null;
            try {
                FileInputStream fis = openFileInput("icohome");
                bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD());
                fis.close();
            } catch (Exception e) {
            }
            Builder mBuilder = new Builder(this).setSmallIcon(C0627R.drawable.notif).setContentTitle(partes[2].replace("@x@", ";")).setContentText(partes[3].replace("@x@", ";")).setAutoCancel(true).setLargeIcon(bitmap);
            mBuilder.setDefaults(5);
            Intent resultIntent = new Intent(this, preinicio.class);
            if (VERSION.SDK_INT >= 11) {
                resultIntent.setFlags(268468224);
            }
            resultIntent.putExtra("notif_id", partes[0]);
            resultIntent.putExtra("notif_tipo", partes[1]);
            resultIntent.putExtra("notif_idelem", partes[4].replace("@x@", ";"));
            int numnotif = settings.getInt("numnotif", 20);
            if (partes[5].equals("0")) {
                numnotif++;
                if (numnotif > 99) {
                    numnotif = 20;
                }
                editor.putInt("numnotif", numnotif);
                editor.commit();
            }
            config.crear_notif(this, partes[2].replace("@x@", ";"), partes[3].replace("@x@", ";"), resultIntent, numnotif, 1, "0", "0");
            mBuilder.setContentIntent(PendingIntent.getActivity(this, numnotif, resultIntent, 134217728));
            ((NotificationManager) getSystemService("notification")).notify(numnotif, mBuilder.build());
        }
    }
}
