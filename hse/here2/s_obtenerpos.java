package hse.here2;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import java.util.Calendar;
import java.util.List;

public class s_obtenerpos extends Service implements LocationListener {
    Editor editor;
    LocationManager lm;
    SharedPreferences settings;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.settings = getSharedPreferences("sh", 0);
        this.editor = this.settings.edit();
        LocationManager lm = (LocationManager) getSystemService("location");
        List<String> providers = lm.getProviders(true);
        boolean seguir = true;
        for (int i = providers.size() - 1; i >= 0; i--) {
            Location loc = lm.getLastKnownLocation((String) providers.get(i));
            if (loc != null) {
                this.editor.putString("x", loc.getLatitude() + "");
                this.editor.putString("y", loc.getLongitude() + "");
                this.editor.commit();
                if (loc.getTime() > Calendar.getInstance().getTimeInMillis() - 300000) {
                    guardarpos();
                    stopSelf();
                    seguir = false;
                    break;
                }
            }
        }
        if (seguir) {
            boolean ok = true;
            try {
                lm.requestLocationUpdates("network", 0, 0.0f, this);
            } catch (Exception e) {
                ok = false;
            }
            if (!ok) {
                ok = true;
                try {
                    lm.requestLocationUpdates("gps", 0, 0.0f, this);
                } catch (Exception e2) {
                    ok = false;
                }
                if (!ok) {
                    guardarpos();
                    stopSelf();
                }
            }
        }
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            this.editor.putString("x", location.getLatitude() + "");
            this.editor.putString("y", location.getLongitude() + "");
            this.editor.commit();
            ((LocationManager) getSystemService("location")).removeUpdates(this);
            guardarpos();
            stopSelf();
        }
    }

    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String arg0) {
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }

    private void guardarpos() {
        if (!this.settings.getString("x", "").equals("") && !this.settings.getString("nick", "").equals("")) {
            startService(new Intent(this, s_guardarpos.class));
        }
    }
}
