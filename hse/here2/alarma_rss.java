package hse.here2;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat.Builder;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class alarma_rss extends BroadcastReceiver {
    public SharedPreferences settings;

    private class RssDataController extends AsyncTask<String, Integer, ArrayList<rss_item>> {
        private Context c2;
        private String idsecc;
        private int n_rss;

        public RssDataController(Context context, String idsecc, int n_rss) {
            this.c2 = context;
            this.idsecc = idsecc;
            this.n_rss = n_rss;
        }

        protected ArrayList<rss_item> doInBackground(String... params) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                connection.setReadTimeout(StatusCodes.AUTH_DISABLED);
                connection.setConnectTimeout(StatusCodes.AUTH_DISABLED);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                return config.tratar_rss(connection.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<rss_item> result) {
            if (result != null && result.size() > 0 && ((rss_item) result.get(0)).postLink != null && !((rss_item) result.get(0)).postLink.equals("") && !((rss_item) result.get(0)).postLink.equals(alarma_rss.this.settings.getString("rss_" + this.idsecc + "_ultpost", ""))) {
                boolean ok = false;
                String str = "";
                int i = 0;
                int i2 = 0;
                try {
                    SharedPreferences settings2 = this.c2.getSharedPreferences("sh", 0);
                    i = settings2.getInt("rss_i", 0);
                    i2 = settings2.getInt("rss_n", 0);
                    str = settings2.getString("rss_t", "");
                    Editor e = settings2.edit();
                    e.putString("rss_" + this.idsecc + "_ultpost", ((rss_item) result.get(0)).postLink);
                    e.commit();
                    ok = true;
                } catch (Exception err) {
                    err.printStackTrace();
                }
                if (ok && rss_interv > 0 && !str.equals("")) {
                    NotificationManager mNotificationManager = (NotificationManager) this.c2.getSystemService("notification");
                    Bitmap bitmap = null;
                    try {
                        FileInputStream fis = this.c2.openFileInput("icohome");
                        bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD());
                        fis.close();
                    } catch (Exception e2) {
                    }
                    Builder mBuilder = new Builder(this.c2).setSmallIcon(C0627R.drawable.notif).setContentTitle(str).setContentText(((rss_item) result.get(0)).postTitle).setAutoCancel(true).setLargeIcon(bitmap);
                    if (i2 == 1) {
                        mBuilder.setDefaults(5);
                    }
                    Intent resultIntent = new Intent(this.c2, preinicio.class);
                    resultIntent.putExtra("notif_id", "0");
                    resultIntent.putExtra("notif_tipo", "1");
                    resultIntent.putExtra("notif_idelem", this.idsecc);
                    config.crear_notif(this.c2, str, ((rss_item) result.get(0)).postTitle, resultIntent, this.n_rss + 100, 3, "0", "0");
                    mBuilder.setContentIntent(PendingIntent.getActivity(this.c2, this.n_rss + 100, resultIntent, 134217728));
                    mNotificationManager.notify(this.n_rss + 100, mBuilder.build());
                }
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        this.settings = context.getSharedPreferences("sh", 0);
        if (System.currentTimeMillis() - this.settings.getLong("f_ult_rss", 0) >= 120000) {
            Editor e = this.settings.edit();
            e.putLong("f_ult_rss", System.currentTimeMillis());
            e.commit();
            String idseccs = this.settings.getString("idseccs", "");
            if (!idseccs.equals("")) {
                String[] idseccs_a = idseccs.split(",");
                int n_rss = 0;
                for (int i = 0; i < idseccs_a.length; i++) {
                    if (this.settings.getInt("s" + idseccs_a[i] + "_tipo", 0) == 8) {
                        String idsecc = idseccs_a[i];
                        n_rss++;
                        if (!this.settings.getString("rss_" + idsecc + "_ultpost", "").equals("")) {
                            if (!this.settings.getString("s" + idsecc + "_url", "").equals("")) {
                                new RssDataController(context, idsecc, n_rss).execute(new String[]{url_aux});
                            }
                        }
                    }
                }
            }
        }
    }

    public void SetAlarm(Context context, int horas) {
        AlarmManager am = (AlarmManager) context.getSystemService("alarm");
        PendingIntent pi = PendingIntent.getBroadcast(context, 1, new Intent(context, alarma_rss.class), 134217728);
        if (horas == 0) {
            am.cancel(pi);
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(13, 60);
        am.setInexactRepeating(1, cal.getTimeInMillis(), (long) (3600000 * horas), pi);
    }
}
