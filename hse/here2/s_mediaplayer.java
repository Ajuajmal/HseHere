package hse.here2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class s_mediaplayer extends Service implements OnErrorListener, OnCompletionListener {
    protected static final Handler TIME_THREAD = new Handler();
    protected static final Handler TIME_THREAD_MC = new Handler();
    protected static final Handler TIME_THREAD_MC_RELEASE = new Handler();
    Editor editor_mc;
    Editor editor_mp;
    boolean esStream = true;
    boolean foreground = false;
    IcyStreamMeta icy;
    int idsecc = 0;
    protected Runnable mcRunnable = new C07282();
    protected Runnable mcRunnableRelease = new C07304();
    MediaPlayer mp;
    boolean preparando;
    int radio_mostrar = 0;
    String secc_tit;
    SharedPreferences sh_mc;
    SharedPreferences sh_mp;
    Timer timer;
    protected Runnable updateTimeRunnable = new C07271();
    String url_act;
    WifiLock wifiLock;

    class C07271 implements Runnable {
        C07271() {
        }

        public void run() {
            s_mediaplayer.this.updateState();
            s_mediaplayer.TIME_THREAD.postDelayed(this, 200);
        }
    }

    class C07282 implements Runnable {
        C07282() {
        }

        public void run() {
            s_mediaplayer.this.update_mc();
        }
    }

    class C07293 extends TimerTask {
        C07293() {
        }

        public void run() {
            if (s_mediaplayer.this.esStream && s_mediaplayer.this.foreground && s_mediaplayer.this.radio_mostrar > 0) {
                boolean ok = false;
                String str = "";
                String title = "";
                try {
                    s_mediaplayer.this.icy = new IcyStreamMeta(new URL(s_mediaplayer.this.url_act));
                    try {
                        str = s_mediaplayer.this.icy.getArtist();
                        title = s_mediaplayer.this.icy.getTitle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (StringIndexOutOfBoundsException e2) {
                        e2.printStackTrace();
                    }
                    ok = true;
                } catch (MalformedURLException e3) {
                    e3.printStackTrace();
                }
                if (ok && s_mediaplayer.this.mp != null && s_mediaplayer.this.mp.isPlaying()) {
                    s_mediaplayer.this.editor_mc.putString("t_radio_artist", str);
                    s_mediaplayer.this.editor_mc.putString("t_radio_song", title);
                    s_mediaplayer.this.editor_mc.commit();
                    String txt = "";
                    if (s_mediaplayer.this.radio_mostrar == 1) {
                        txt = str;
                        if (!(str.equals("") || title.equals(""))) {
                            txt = txt + " - ";
                        }
                        txt = txt + title;
                    } else if (s_mediaplayer.this.radio_mostrar == 2) {
                        txt = str;
                    } else if (s_mediaplayer.this.radio_mostrar == 3) {
                        txt = title;
                    }
                    txt = txt.trim();
                    if (txt.equals("")) {
                        txt = s_mediaplayer.this.secc_tit;
                    }
                    NotificationManager mNotificationManager = (NotificationManager) s_mediaplayer.this.getSystemService("notification");
                    if (s_mediaplayer.this.foreground) {
                        mNotificationManager.notify(4, s_mediaplayer.this.crearNotif(txt));
                    }
                }
            }
        }
    }

    class C07304 implements Runnable {
        C07304() {
        }

        public void run() {
            s_mediaplayer.this.update_mc();
            try {
                s_mediaplayer.this.mp.release();
            } catch (Exception e) {
            }
            try {
                s_mediaplayer.this.mp = null;
            } catch (Exception e2) {
            }
        }
    }

    class C07315 implements OnPreparedListener {
        C07315() {
        }

        public void onPrepared(MediaPlayer mp) {
            s_mediaplayer.this.preparando = false;
            mp.start();
            s_mediaplayer.TIME_THREAD_MC.postDelayed(s_mediaplayer.this.mcRunnable, 200);
            s_mediaplayer.this.foreground_on();
        }
    }

    private void getMeta() {
        int timeinterval = StatusCodes.AUTH_DISABLED;
        NetworkInfo mWifi = null;
        try {
            mWifi = ((ConnectivityManager) getSystemService("connectivity")).getNetworkInfo(1);
        } catch (Exception e) {
        }
        if (mWifi != null && mWifi.isConnected()) {
            timeinterval = FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS;
        }
        this.timer = new Timer();
        this.timer.schedule(new C07293(), 0, (long) timeinterval);
    }

    public void onCreate() {
        this.sh_mp = getSharedPreferences("sh_mp", 0);
        this.sh_mc = getSharedPreferences("sh_mc", 0);
        this.editor_mp = this.sh_mp.edit();
        this.editor_mc = this.sh_mc.edit();
        if (VERSION.SDK_INT >= 12) {
            this.wifiLock = ((WifiManager) getSystemService("wifi")).createWifiLock(3, "acwifilock");
        } else {
            this.wifiLock = ((WifiManager) getSystemService("wifi")).createWifiLock(1, "acwifilock");
        }
        TIME_THREAD.postDelayed(this.updateTimeRunnable, 200);
        getMeta();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent i, int flags, int startId) {
        if (i == null) {
            stopSelf();
        } else {
            String accion = i.getStringExtra("accion");
            String url = i.getStringExtra(PlusShare.KEY_CALL_TO_ACTION_URL);
            this.idsecc = i.getIntExtra("idsecc", 0);
            this.radio_mostrar = i.getIntExtra("radio_mostrar", 0);
            this.secc_tit = i.getStringExtra("secc_tit");
            this.esStream = i.getBooleanExtra("esStream", true);
            if (accion.equals("iniciar")) {
                crear_mp(url);
            } else if (accion.equals("play")) {
                if (this.mp == null) {
                    crear_mp(url);
                } else {
                    try {
                        this.mp.start();
                        foreground_on();
                    } catch (Exception e) {
                    }
                    TIME_THREAD_MC.postDelayed(this.mcRunnable, 200);
                }
            } else if (accion.equals("pause")) {
                try {
                    this.mp.pause();
                } catch (Exception e2) {
                }
                if (this.esStream) {
                    TIME_THREAD_MC_RELEASE.postDelayed(this.mcRunnableRelease, 200);
                } else {
                    TIME_THREAD_MC.postDelayed(this.mcRunnable, 200);
                }
                foreground_off();
            } else if (accion.equals("seekto")) {
                try {
                    this.mp.seekTo(i.getIntExtra("valor", 0));
                } catch (Exception e3) {
                }
                TIME_THREAD_MC.postDelayed(this.mcRunnable, 200);
            }
        }
        return super.onStartCommand(i, flags, startId);
    }

    private void crear_mp(String url) {
        if (this.mp != null) {
            try {
                if (url.equals(this.url_act) && this.mp.isPlaying()) {
                    return;
                }
            } catch (Exception e) {
            }
            try {
                this.mp.release();
            } catch (Exception e2) {
            }
        }
        this.url_act = url;
        this.mp = new MediaPlayer();
        this.mp.setWakeMode(this, 1);
        try {
            TIME_THREAD_MC.postDelayed(this.mcRunnable, 200);
        } catch (Exception e3) {
        }
        this.mp.setOnPreparedListener(new C07315());
        this.mp.setOnCompletionListener(this);
        try {
            this.mp.setAudioStreamType(3);
            this.mp.setDataSource(url);
            this.mp.prepareAsync();
        } catch (IOException e4) {
        }
    }

    private Notification crearNotif(String notif_txt) {
        Intent i = new Intent(getApplicationContext(), preinicio.class);
        if (VERSION.SDK_INT >= 11) {
            i.setFlags(268468224);
        }
        if (this.idsecc != 0) {
            i.putExtra("notif_id", "0");
            i.putExtra("notif_tipo", "1");
            i.putExtra("notif_idelem", this.idsecc + "");
        }
        String tit = "";
        try {
            tit = getAppLabel(this);
        } catch (Exception e) {
        }
        if (tit.equals("")) {
            tit = "Playing";
        }
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, 134217728);
        Notification notification = new Notification();
        notification.tickerText = tit;
        notification.icon = C0627R.drawable.play;
        notification.flags |= 2;
        notification.setLatestEventInfo(getApplicationContext(), tit, notif_txt, pi);
        return notification;
    }

    private void foreground_on() {
        startForeground(4, crearNotif(this.secc_tit));
        this.foreground = true;
        this.wifiLock.acquire();
    }

    private void foreground_off() {
        try {
            this.wifiLock.release();
        } catch (Exception e) {
        }
        stopForeground(true);
        this.foreground = false;
    }

    public String getAppLabel(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (NameNotFoundException e) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "");
    }

    private void updateState() {
        if (this.editor_mp != null) {
            if (this.mp != null) {
                try {
                    this.editor_mp.putInt("position", this.mp.getCurrentPosition());
                } catch (Exception e) {
                }
                try {
                    this.editor_mp.putInt("duration", this.mp.getDuration());
                } catch (Exception e2) {
                }
                try {
                    this.editor_mp.putBoolean("isPlaying", this.mp.isPlaying());
                } catch (Exception e3) {
                }
            } else {
                try {
                    this.editor_mp.clear();
                } catch (Exception e4) {
                }
            }
            try {
                this.editor_mp.commit();
            } catch (Exception e5) {
            }
        }
    }

    private void update_mc() {
        if (this.editor_mc != null) {
            this.editor_mc.clear();
            this.editor_mc.putBoolean("act_mc", true);
            this.editor_mc.commit();
        }
        boolean isPlaying_aux = false;
        if (this.mp != null) {
            try {
                isPlaying_aux = this.mp.isPlaying();
            } catch (Exception e) {
            }
        }
        if (this.mp != null && isPlaying_aux && !this.foreground) {
            try {
                this.mp.stop();
            } catch (Exception e2) {
            }
            if (this.editor_mp != null) {
                try {
                    this.editor_mp.putInt("position", 0);
                } catch (Exception e3) {
                }
                try {
                    this.editor_mp.putInt("duration", -1);
                } catch (Exception e4) {
                }
                try {
                    this.editor_mp.putBoolean("isPlaying", false);
                } catch (Exception e5) {
                }
                try {
                    this.editor_mp.commit();
                } catch (Exception e6) {
                }
                if (this.editor_mc != null) {
                    this.editor_mc.clear();
                    this.editor_mc.putBoolean("act_mc", true);
                    this.editor_mc.commit();
                }
            }
            stopSelf();
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        foreground_off();
        return true;
    }

    public void onCompletion(MediaPlayer mp) {
        foreground_off();
    }

    public void onDestroy() {
        try {
            TIME_THREAD.removeCallbacks(this.updateTimeRunnable);
        } catch (Exception e) {
        }
        try {
            this.wifiLock.release();
        } catch (Exception e2) {
        }
        try {
            this.mp.release();
        } catch (Exception e3) {
        }
    }
}
