package com.appnext.ads.fullscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import com.appnext.ads.C0150b;
import com.appnext.base.p005b.C0204c;
import com.appnext.core.Ad;
import com.appnext.core.AppnextAd;
import com.appnext.core.C0241a;
import com.appnext.core.C0249b;
import com.appnext.core.C0266f;
import com.appnext.core.C0269h;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class C0913c extends C0249b {
    private static C0913c cz;
    private final int cA = 30;
    private HashMap<String, FullscreenAd> cB = new HashMap();

    class C01671 implements Comparator<File> {
        final /* synthetic */ C0913c f12X;

        C01671(C0913c c0913c) {
            this.f12X = c0913c;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return m14a((File) obj, (File) obj2);
        }

        public int m14a(File file, File file2) {
            return Long.valueOf(file.lastModified()).compareTo(Long.valueOf(file2.lastModified()));
        }
    }

    public static C0913c aa() {
        if (cz == null) {
            cz = new C0913c();
        }
        return cz;
    }

    private C0913c() {
    }

    protected String mo1206a(Context context, Ad ad, String str) {
        C0266f.m201a(ad != null ? ad.getTID() : "300", ad != null ? ad.getVID() : "2.0.3.459", ad != null ? ad.getAUID() : "700", str, "", C0150b.bA, "sdk", "", "");
        return "https://global.appnext.com" + "/offerWallApi.aspx?ext=t&pimp=1&igroup=sdk&m=1&osid=100&auid=" + (ad != null ? ad.getAUID() : "700") + "&type=json&id=" + str + "&cnt=" + 30 + "&tid=" + (ad != null ? ad.getTID() : "300") + "&vid=" + (ad != null ? ad.getVID() : "2.0.3.459") + "&cat=" + (ad != null ? ad.getCategories() : "") + "&pbk=" + (ad != null ? ad.getPostback() : "") + "&did=" + C0266f.m207w(context) + "&devn=" + C0266f.cf() + "&dosv=" + VERSION.SDK_INT + "&dct=" + C0266f.m209y(context) + "&dds=" + ((int) C0266f.cg()) + "&packageId=" + context.getPackageName() + "&rnd=" + new Random().nextInt();
    }

    protected boolean mo1211a(Context context, C0269h c0269h) {
        return m330a(context, (AppnextAd) c0269h);
    }

    protected boolean mo1212a(Ad ad) {
        return super.mo1212a(ad) && this.cB.containsKey(ad.getPlacementID());
    }

    protected void mo1207a(Context context, Ad ad, C0241a c0241a) throws Exception {
        m331b(context, ad);
        AppnextAd appnextAd = null;
        try {
            appnextAd = m334a(context, ad);
            if (appnextAd == null) {
                throw new Exception("No video ads");
            }
            String videoLength;
            if (!((Video) ad).getVideoLength().equals("default") && !((Video) ad).getVideoLength().equals("managed")) {
                videoLength = ((Video) ad).getVideoLength();
            } else if (ad instanceof FullScreenVideo) {
                videoLength = C0914d.ac().get("video_length");
            } else {
                videoLength = C0916f.af().get("video_length");
            }
            videoLength = C0913c.m329a(appnextAd, videoLength);
            String G = C0913c.m328G(videoLength);
            File file = new File(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + G);
            FullscreenAd fullscreenAd;
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                C0266f.m194P(file.getPath() + " exists");
                fullscreenAd = new FullscreenAd(appnextAd);
                fullscreenAd.m476F(file.getAbsolutePath());
                this.cB.put(ad.getPlacementID(), fullscreenAd);
                return;
            }
            new File(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw).mkdirs();
            URL url = new URL(videoLength);
            url.openConnection().connect();
            InputStream bufferedInputStream = new BufferedInputStream(url.openStream(), 1024);
            OutputStream fileOutputStream = new FileOutputStream(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + G + C0204c.fx);
            byte[] bArr = new byte[1024];
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    C0266f.m194P("downloaded " + context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + G);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    bufferedInputStream.close();
                    File file2 = new File(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + G + C0204c.fx);
                    file2.renameTo(new File(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + G));
                    file2.delete();
                    fullscreenAd = new FullscreenAd(appnextAd);
                    fullscreenAd.m476F(file.getAbsolutePath());
                    this.cB.put(ad.getPlacementID(), fullscreenAd);
                    return;
                }
            }
        } catch (Throwable th) {
            if (appnextAd != null) {
                mo1210a(appnextAd.getBannerID(), ad.getPlacementID());
            }
        }
    }

    private void m331b(Context context, Ad ad) {
        try {
            int i;
            File[] listFiles = new File(context.getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw).listFiles();
            Arrays.sort(listFiles, new C01671(this));
            if (!Video.getDisplaySaved()) {
                i = 1;
            } else if (ad instanceof FullScreenVideo) {
                i = Integer.parseInt(C0914d.ac().get("num_saved_videos"));
            } else {
                i = Integer.parseInt(C0916f.af().get("num_saved_videos"));
            }
            if (listFiles.length > i) {
                for (int i2 = 0; i2 < listFiles.length - i; i2++) {
                    listFiles[i2].delete();
                }
            }
        } catch (Throwable th) {
        }
    }

    protected void mo1208a(Ad ad, String str, String str2) {
        if (ad != null) {
            C0266f.m201a(ad.getTID(), ad.getVID(), ad.getAUID(), str2, str, C0150b.bz, "sdk", "", "");
            return;
        }
        C0266f.m201a("300", "2.0.3.459", "700", str2, str, C0150b.bz, "sdk", "", "");
    }

    protected <T> void mo1209a(String str, Ad ad, T t) {
        C0266f.m201a(ad.getTID(), ad.getVID(), ad.getAUID(), str, "", C0150b.by, "sdk", "", "");
    }

    protected static String m329a(AppnextAd appnextAd, String str) {
        String videoUrlHigh30Sec;
        if (str.equals(Video.VIDEO_LENGTH_LONG)) {
            videoUrlHigh30Sec = appnextAd.getVideoUrlHigh30Sec();
            if (videoUrlHigh30Sec.equals("")) {
                videoUrlHigh30Sec = appnextAd.getVideoUrlHigh();
            }
        } else {
            videoUrlHigh30Sec = appnextAd.getVideoUrlHigh();
            if (videoUrlHigh30Sec.equals("")) {
                videoUrlHigh30Sec = appnextAd.getVideoUrlHigh30Sec();
            }
        }
        C0266f.m194P("returning video url for: " + appnextAd.getBannerID() + " with len: " + str + " url: " + videoUrlHigh30Sec);
        return videoUrlHigh30Sec;
    }

    protected void mo1210a(String str, String str2) {
        super.mo1210a(str, str2);
        if (this.cB.containsKey(str2)) {
            this.cB.remove(str2);
        }
    }

    protected boolean m343e(Ad ad) {
        try {
            if (m167g(ad) && (m163d(ad).bS().longValue() + cx()) + 300000 >= System.currentTimeMillis() && m333f(ad)) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            C0266f.m205c(th);
            return false;
        }
    }

    protected boolean m342b(Ad ad) {
        try {
            return mo1212a(ad) && m333f(ad);
        } catch (Throwable th) {
            C0266f.m205c(th);
            return false;
        }
    }

    private boolean m333f(Ad ad) {
        if (this.cB.containsKey(ad.getPlacementID())) {
            return new File(((FullscreenAd) this.cB.get(ad.getPlacementID())).m477Z()).exists();
        }
        return false;
    }

    private boolean m332b(AppnextAd appnextAd) {
        return (appnextAd.getVideoUrlHigh().equals("") && appnextAd.getVideoUrlHigh30Sec().equals("")) ? false : true;
    }

    protected AppnextAd m334a(Context context, Ad ad) {
        if (m163d(ad) == null) {
            return null;
        }
        ArrayList g = m163d(ad).m143g();
        if (g == null) {
            return null;
        }
        Iterator it = g.iterator();
        while (it.hasNext()) {
            AppnextAd appnextAd = (AppnextAd) it.next();
            if (m332b(appnextAd) && !m169l(appnextAd.getBannerID(), ad.getPlacementID())) {
                return appnextAd;
            }
        }
        au(ad.getPlacementID());
        Iterator it2 = g.iterator();
        while (it2.hasNext()) {
            appnextAd = (AppnextAd) it2.next();
            if (m332b(appnextAd) && !m169l(appnextAd.getBannerID(), ad.getPlacementID())) {
                return appnextAd;
            }
        }
        return null;
    }

    private boolean m330a(Context context, AppnextAd appnextAd) {
        FullscreenAd fullscreenAd = new FullscreenAd(appnextAd);
        if (fullscreenAd.getCampaignGoal().equals("new") && C0266f.m206c(context, fullscreenAd.getAdPackage())) {
            return false;
        }
        if (!fullscreenAd.getCampaignGoal().equals("existing") || C0266f.m206c(context, fullscreenAd.getAdPackage())) {
            return true;
        }
        return false;
    }

    protected static String m328G(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        if (substring.contains("?")) {
            substring = substring.substring(0, substring.indexOf("?"));
        }
        try {
            String queryParameter = Uri.parse(str).getQueryParameter("rnd");
            if (!(queryParameter == null || queryParameter.equals(""))) {
                substring = substring.substring(0, substring.lastIndexOf(46)) + "_" + queryParameter + substring.substring(substring.lastIndexOf(46));
            }
        } catch (Throwable th) {
        }
        return substring;
    }
}
