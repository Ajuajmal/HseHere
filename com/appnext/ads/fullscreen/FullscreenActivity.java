package com.appnext.ads.fullscreen;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;
import com.appnext.ads.C0149a;
import com.appnext.ads.C0150b;
import com.appnext.ads.C0907c;
import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.p005b.C0204c;
import com.appnext.core.AppnextActivity;
import com.appnext.core.AppnextAd;
import com.appnext.core.AppnextError;
import com.appnext.core.C0249b.C0248a;
import com.appnext.core.C0262d.C0258a;
import com.appnext.core.C0266f;
import com.appnext.core.C0280o;
import com.appnext.core.callbacks.OnAdClosed;
import com.appnext.core.callbacks.OnAdError;
import com.appnext.core.webview.C0285a;
import com.google.android.gms.appinvite.PreviewActivity;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.identity.intents.AddressConstants.ErrorCodes;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class FullscreenActivity extends AppnextActivity implements C0258a {
    private C0165a bN;
    private final int bQ = 330;
    private MediaPlayer bR;
    private VideoView bS;
    private RelativeLayout bT;
    private RelativeLayout bU;
    private RelativeLayout bV;
    private ArrayList<AppnextAd> bW;
    private ProgressBar bX;
    private C0280o bY;
    private boolean bZ = true;
    private boolean ca = false;
    private TextView cc;
    private AppnextAd cd;
    private AppnextAd ce;
    private ImageView cf;
    private LinearLayout cg;
    private Button ch;
    private long ci = 0;
    private int cj = 0;
    ThankYouPage ck;
    C0907c cl;
    @SuppressLint({"SetTextI18n"})
    Runnable cm = new Runnable(this) {
        final /* synthetic */ FullscreenActivity cq;

        {
            this.cq = r1;
        }

        public void run() {
            C0266f.m194P("tick");
            if (this.cq.bS != null) {
                C0266f.m194P("" + this.cq.bS.getCurrentPosition() + " of " + this.cq.bS.getDuration());
                if (this.cq.bX != null && (this.cq.bX.getProgress() != this.cq.bS.getCurrentPosition() || this.cq.bS.getCurrentPosition() == 0)) {
                    ObjectAnimator ofInt = ObjectAnimator.ofInt(this.cq.bX, "progress", new int[]{this.cq.bS.getCurrentPosition() + 1});
                    ofInt.setDuration(330);
                    ofInt.setInterpolator(new LinearInterpolator());
                    ofInt.start();
                }
                if (this.cq.bN != null) {
                    Animation c0166b = new C0166b(this.cq.bN, 360.0f - ((((float) (this.cq.bS.getCurrentPosition() + 1)) / ((float) this.cq.bS.getDuration())) * 360.0f));
                    c0166b.setDuration(330);
                    this.cq.bN.startAnimation(c0166b);
                }
                if (this.cq.bS.getCurrentPosition() < this.cq.bS.getDuration()) {
                    if (this.cq.cc != null) {
                        this.cq.cc.setText("" + (((this.cq.bS.getDuration() - this.cq.bS.getCurrentPosition()) / 1000) + 1));
                    }
                    if (!this.cq.ca) {
                        this.cq.mHandler.postDelayed(this.cq.cm, 330);
                    }
                }
            }
        }
    };
    Runnable cn = new C01572(this);
    Runnable co = new C01583(this);
    private Handler mHandler;
    private boolean showClose;
    private int type;

    class C01572 implements Runnable {
        final /* synthetic */ FullscreenActivity cq;

        C01572(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void run() {
            C0266f.m194P("impression");
            this.cq.click.m187e(this.cq.cd);
        }
    }

    class C01583 implements Runnable {
        final /* synthetic */ FullscreenActivity cq;

        C01583(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void run() {
            C0266f.m194P("postview");
            this.cq.mo1191a(this.cq.cd, null);
        }
    }

    class C01594 implements OnClickListener {
        final /* synthetic */ FullscreenActivity cq;

        C01594(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void onClick(View view) {
            String str = "android.intent.action.VIEW";
            Intent intent = new Intent(str, Uri.parse("http://www.appnext.com/privacy_policy/index.html?z=" + new Random().nextInt(10) + new FullscreenAd(this.cq.cd).getZoneID() + new Random().nextInt(10)));
            intent.setFlags(DriveFile.MODE_READ_ONLY);
            this.cq.startActivityForResult(intent, 14857);
        }
    }

    class C01636 implements Runnable {
        final /* synthetic */ FullscreenActivity cq;

        C01636(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void run() {
            final Bitmap ay = C0266f.ay(this.cq.cd.getImageURL());
            if (this.cq.cf != null) {
                this.cq.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ C01636 cy;

                    public void run() {
                        this.cy.cq.cf.setImageBitmap(ay);
                    }
                });
            }
        }
    }

    class C01647 implements OnClickListener {
        final /* synthetic */ FullscreenActivity cq;

        C01647(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void onClick(View view) {
            this.cq.m291a(this.cq.cd);
        }
    }

    class C09098 implements OnAdClosed {
        final /* synthetic */ FullscreenActivity cq;

        C09098(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void onAdClosed() {
            this.cq.finish();
            this.cq.onClose();
        }
    }

    class C09109 implements OnAdError {
        final /* synthetic */ FullscreenActivity cq;

        C09109(FullscreenActivity fullscreenActivity) {
            this.cq = fullscreenActivity;
        }

        public void adError(String str) {
            this.cq.finish();
            this.cq.onClose();
        }
    }

    private class ThankYouPage extends Interstitial {
        final /* synthetic */ FullscreenActivity cq;

        public ThankYouPage(FullscreenActivity fullscreenActivity, Context context, String str) {
            this.cq = fullscreenActivity;
            super(context, str);
        }

        protected String getPageUrl() {
            C0266f.m194P("https://appnext.hs.llnwd.net/tools/sdk/rewarded/v63/script.min.js");
            return "https://appnext.hs.llnwd.net/tools/sdk/rewarded/v63/script.min.js";
        }

        protected Intent getActivityIntent() {
            currentAd = this;
            Intent activityIntent = super.getActivityIntent();
            Serializable arrayList = new ArrayList(this.cq.bW);
            int i = -1;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (((AppnextAd) arrayList.get(i2)).getBannerID().equals(this.cq.cd.getBannerID())) {
                    i = i2;
                }
            }
            if (i != -1) {
                AppnextAd appnextAd = (AppnextAd) arrayList.get(i);
                arrayList.remove(i);
                arrayList.add(0, appnextAd);
            } else {
                this.cq.finish();
            }
            activityIntent.putExtra("ads", arrayList);
            activityIntent.putExtra("orientation", false);
            activityIntent.putExtra("orientation_type", Video.currentAd.getOrientation());
            activityIntent.putExtra("product", this.cq.type == 1 ? "fullscreen" : "rewarded");
            activityIntent.putExtra("show_desc", this.cq.bY.get("show_desc"));
            if (!this.cq.gQ.equals("")) {
                activityIntent.putExtra("pview", this.cq.gQ);
                activityIntent.putExtra("banner", this.cq.dr);
                activityIntent.putExtra("guid", this.cq.guid);
            }
            return activityIntent;
        }

        protected C0285a getFallback() {
            return new C0915e();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onCreate(android.os.Bundle r8) {
        /*
        r7 = this;
        r6 = 6;
        r4 = 2;
        r3 = 1;
        r1 = 0;
        r2 = -1;
        super.onCreate(r8);
        r0 = com.appnext.ads.fullscreen.Video.currentAd;
        if (r0 == 0) goto L_0x0119;
    L_0x000c:
        r0 = com.appnext.ads.fullscreen.Video.currentAd;
        r0 = r0.getOrientation();
        r5 = r0.hashCode();
        switch(r5) {
            case 729267099: goto L_0x00ec;
            case 1430647483: goto L_0x00e1;
            case 1673671211: goto L_0x00d6;
            case 2129065206: goto L_0x00cb;
            default: goto L_0x0019;
        };
    L_0x0019:
        r0 = r2;
    L_0x001a:
        switch(r0) {
            case 0: goto L_0x00f7;
            case 1: goto L_0x00f7;
            case 2: goto L_0x010e;
            case 3: goto L_0x0113;
            default: goto L_0x001d;
        };
    L_0x001d:
        r7.m283Y();
        r0 = new android.os.Handler;
        r0.<init>();
        r7.mHandler = r0;
        r0 = new android.widget.RelativeLayout;
        r0.<init>(r7);
        r7.gS = r0;
        r0 = r7.gS;
        r4 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r0.setBackgroundColor(r4);
        r0 = r7.gS;
        r7.setContentView(r0);
        r0 = new android.widget.RelativeLayout;
        r0.<init>(r7);
        r7.bT = r0;
        r0 = r7.gS;
        r4 = r7.bT;
        r0.addView(r4);
        r0 = r7.bT;
        r0 = r0.getLayoutParams();
        r0.width = r2;
        r0 = r7.bT;
        r0 = r0.getLayoutParams();
        r0.height = r2;
        r0 = r7.getIntent();
        r0 = r0.getExtras();
        r2 = "id";
        r0 = r0.getString(r2);
        r7.placementID = r0;
        r0 = r7.getIntent();
        r0 = r0.getExtras();
        r2 = "type";
        r0 = r0.getInt(r2);
        r7.type = r0;
        r0 = r7.type;
        if (r0 != r3) goto L_0x011d;
    L_0x007c:
        r0 = com.appnext.ads.fullscreen.C0914d.ac();
        r7.bY = r0;
    L_0x0082:
        r0 = r7.placementID;
        r2 = "";
        r3 = "ad_displayed";
        r7.m292a(r0, r2, r3);
        r0 = r7.bY;
        r2 = "can_close";
        r0 = r0.get(r2);
        r0 = java.lang.Boolean.parseBoolean(r0);
        r7.bZ = r0;
        r0 = com.appnext.ads.fullscreen.Video.currentAd;
        r0 = r0.getCanClose();
        if (r0 == 0) goto L_0x00a9;
    L_0x00a1:
        r0 = com.appnext.ads.fullscreen.Video.currentAd;
        r0 = r0.isBackButtonCanClose();
        r7.bZ = r0;
    L_0x00a9:
        r0 = r7.getIntent();
        r2 = "show_close";
        r0 = r0.hasExtra(r2);
        if (r0 == 0) goto L_0x0125;
    L_0x00b5:
        r0 = r7.getIntent();
        r2 = "show_close";
        r0 = r0.getBooleanExtra(r2, r1);
        r7.showClose = r0;
    L_0x00c1:
        r0 = java.lang.System.currentTimeMillis();
        r7.ci = r0;
        r7.m273M();
    L_0x00ca:
        return;
    L_0x00cb:
        r5 = "not_set";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0019;
    L_0x00d3:
        r0 = r1;
        goto L_0x001a;
    L_0x00d6:
        r5 = "automatic";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0019;
    L_0x00de:
        r0 = r3;
        goto L_0x001a;
    L_0x00e1:
        r5 = "landscape";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0019;
    L_0x00e9:
        r0 = r4;
        goto L_0x001a;
    L_0x00ec:
        r5 = "portrait";
        r0 = r0.equals(r5);
        if (r0 == 0) goto L_0x0019;
    L_0x00f4:
        r0 = 3;
        goto L_0x001a;
    L_0x00f7:
        r0 = r7.getResources();
        r0 = r0.getConfiguration();
        r0 = r0.orientation;
        if (r0 != r4) goto L_0x0108;
    L_0x0103:
        r7.setRequestedOrientation(r6);
        goto L_0x001d;
    L_0x0108:
        r0 = 7;
        r7.setRequestedOrientation(r0);
        goto L_0x001d;
    L_0x010e:
        r7.setRequestedOrientation(r6);
        goto L_0x001d;
    L_0x0113:
        r0 = 7;
        r7.setRequestedOrientation(r0);
        goto L_0x001d;
    L_0x0119:
        r7.finish();
        goto L_0x00ca;
    L_0x011d:
        r0 = com.appnext.ads.fullscreen.C0916f.af();
        r7.bY = r0;
        goto L_0x0082;
    L_0x0125:
        r0 = r7.bY;
        r1 = "show_close";
        r0 = r0.get(r1);
        r0 = java.lang.Boolean.parseBoolean(r0);
        r7.showClose = r0;
        goto L_0x00c1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.appnext.ads.fullscreen.FullscreenActivity.onCreate(android.os.Bundle):void");
    }

    protected void onError(String str) {
        if (Video.currentAd != null && Video.currentAd.getOnAdErrorCallback() != null) {
            Video.currentAd.getOnAdErrorCallback().adError(str);
        }
    }

    private void m273M() {
        final C0913c aa = C0913c.aa();
        aa.m152a((Context) this, Video.currentAd, this.placementID, new C0248a(this) {
            final /* synthetic */ FullscreenActivity cq;

            class C01531 implements OnPreparedListener {
                final /* synthetic */ C09081 cr;

                class C01521 implements OnSeekCompleteListener {
                    final /* synthetic */ C01531 cs;

                    C01521(C01531 c01531) {
                        this.cs = c01531;
                    }

                    public void onSeekComplete(MediaPlayer mediaPlayer) {
                        this.cs.cr.cq.bR.start();
                    }
                }

                C01531(C09081 c09081) {
                    this.cr = c09081;
                }

                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (this.cr.cq.bR != null) {
                        this.cr.cq.bR = mediaPlayer;
                        if (this.cr.cq.cj > 0) {
                            this.cr.cq.bR.seekTo(this.cr.cq.cj);
                            this.cr.cq.bR.setOnSeekCompleteListener(new C01521(this));
                        } else {
                            this.cr.cq.bR.start();
                        }
                    } else {
                        this.cr.cq.bR = mediaPlayer;
                        this.cr.cq.m275O();
                        this.cr.cq.m322W();
                    }
                    this.cr.cq.mHandler.postDelayed(this.cr.cq.cm, 33);
                    if (Video.currentAd.getMute()) {
                        this.cr.cq.bR.setVolume(0.0f, 0.0f);
                    } else {
                        this.cr.cq.bR.setVolume(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
                    }
                }
            }

            class C01552 implements OnCompletionListener {
                final /* synthetic */ C09081 cr;

                class C01541 implements Runnable {
                    final /* synthetic */ C01552 ct;

                    C01541(C01552 c01552) {
                        this.ct = c01552;
                    }

                    public void run() {
                        if (Video.currentAd instanceof RewardedVideo) {
                            RewardedServerSidePostback rewardedServerSidePostback = ((RewardedVideo) Video.currentAd).getRewardedServerSidePostback();
                            if (rewardedServerSidePostback != null) {
                                HashMap ae = rewardedServerSidePostback.ae();
                                ae.put("placementId", this.ct.cr.cq.placementID);
                                try {
                                    C0266f.m196a("https://admin.appnext.com/adminService.asmx/SetRewards", ae);
                                } catch (IOException e) {
                                }
                            }
                        }
                    }
                }

                C01552(C09081 c09081) {
                    this.cr = c09081;
                }

                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (this.cr.cq.bR != null && this.cr.cq.bR.getCurrentPosition() != 0 && this.cr.cq.bR.getDuration() != 0 && !this.cr.cq.ca) {
                        C0266f.m194P("onCompletion. " + this.cr.cq.bR.getCurrentPosition() + "/" + this.cr.cq.bR.getDuration());
                        this.cr.cq.ca = true;
                        this.cr.cq.m323X();
                        if (Video.currentAd.getOnVideoEndedCallback() != null) {
                            Video.currentAd.getOnVideoEndedCallback().videoEnded();
                        }
                        this.cr.cq.bZ = true;
                        this.cr.cq.m292a(this.cr.cq.placementID, "", C0150b.bB);
                        new Thread(new C01541(this)).start();
                    }
                }
            }

            class C01563 implements OnErrorListener {
                final /* synthetic */ C09081 cr;

                C01563(C09081 c09081) {
                    this.cr = c09081;
                }

                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    if (!(i == -38 && i2 == 0)) {
                        C0266f.m194P("mp error: what: " + i + " extra: " + i2);
                        this.cr.cq.m292a(this.cr.cq.placementID, "mp error: what: " + i + " extra: " + i2, "error_video");
                    }
                    return true;
                }
            }

            public <T> void mo1187a(T t) {
                this.cq.bW = (ArrayList) t;
                this.cq.cd = aa.m334a(this.cq, Video.currentAd);
                if (this.cq.cd == null) {
                    this.cq.onError(AppnextError.NO_ADS);
                    this.cq.finish();
                } else {
                    aa.mo1210a(this.cq.cd.getBannerID(), this.cq.placementID);
                }
                try {
                    this.cq.bS = new VideoView(this.cq.getApplicationContext());
                } catch (Throwable th) {
                    this.cq.m292a(this.cq.placementID, C0266f.m202b(th), "error_videoView");
                    C0266f.m205c(th);
                    this.cq.finish();
                    this.cq.onError(AppnextError.INTERNAL_ERROR);
                    return;
                }
                this.cq.bS.setLayoutParams(new LayoutParams(-1, -2));
                this.cq.bT.addView(this.cq.bS);
                this.cq.bS.setId(ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
                ((LayoutParams) this.cq.bS.getLayoutParams()).addRule(13);
                this.cq.bS.setOnPreparedListener(new C01531(this));
                this.cq.bS.setOnCompletionListener(new C01552(this));
                this.cq.bS.setOnErrorListener(new C01563(this));
                this.cq.bS.setVideoURI(this.cq.m274N());
            }

            public void error(String str) {
                this.cq.m292a(this.cq.placementID, str, "error_loading_ads");
                if (Video.currentAd.getOnAdErrorCallback() != null) {
                    Video.currentAd.getOnAdErrorCallback().adError(str);
                }
                this.cq.finish();
            }
        });
    }

    protected C0280o getConfig() {
        return this.bY;
    }

    protected void mo1191a(AppnextAd appnextAd, C0258a c0258a) {
        super.mo1191a(appnextAd, new C0258a(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void onMarket(String str) {
            }

            public void error(String str) {
            }
        });
    }

    private Uri m274N() {
        String str;
        if (Video.currentAd.getVideoLength().equals("default") || Video.currentAd.getVideoLength().equals("managed")) {
            str = this.bY.get("video_length");
        } else {
            str = Video.currentAd.getVideoLength();
        }
        String a = C0913c.m329a(this.cd, str);
        str = C0913c.m328G(a);
        File file = new File(getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + str);
        if (file.exists()) {
            Uri parse = Uri.parse(getFilesDir().getAbsolutePath() + C0204c.fv + C0204c.fw + str);
            C0266f.m194P("playing video " + parse.getPath());
            return parse;
        }
        parse = Uri.parse(a);
        C0266f.m194P("playing video from web: " + a);
        C0266f.m194P("file not found: " + file.getAbsolutePath());
        return parse;
    }

    @SuppressLint({"SetTextI18n"})
    private void m275O() {
        long closeDelay;
        C0266f.m194P("onPrepared");
        m292a(this.placementID, "" + (System.currentTimeMillis() - this.ci), C0150b.bs);
        m276P();
        if (this.bX != null) {
            this.bX.setMax(this.bR.getDuration());
        }
        this.mHandler.postDelayed(new Runnable(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void run() {
                if (this.cq.bX != null) {
                    this.cq.bX.setVisibility(0);
                }
                if (this.cq.bN != null) {
                    this.cq.bN.setVisibility(0);
                    this.cq.cc.setVisibility(0);
                }
            }
        }, Long.parseLong(this.bY.get("progress_delay")));
        if (Video.currentAd.getCloseDelay() >= 0) {
            closeDelay = Video.currentAd.getCloseDelay();
        } else {
            closeDelay = Long.parseLong(this.bY.get("close_delay"));
        }
        if (this.showClose && closeDelay >= 0) {
            this.mHandler.postDelayed(new Runnable(this) {
                final /* synthetic */ FullscreenActivity cq;

                {
                    this.cq = r1;
                }

                public void run() {
                    if (this.cq.bV != null) {
                        this.cq.bV.setVisibility(0);
                    }
                }
            }, closeDelay);
        }
        this.bR.seekTo(0);
        this.bR.start();
        this.bR.seekTo(0);
        if (this.cc != null) {
            this.cc.setText("" + (this.bR.getDuration() / 1000));
        }
        this.mHandler.postDelayed(this.cn, Long.parseLong(this.bY.get("postpone_impression_sec")) * 1000);
        if (Video.currentAd.getOnAdOpenedCallback() != null) {
            Video.currentAd.getOnAdOpenedCallback().adOpened();
        }
    }

    private void m276P() {
        m280T();
        m279S();
        m281U();
        if (getResources().getConfiguration().orientation == 1) {
            m282V();
        }
        String progressType = Video.currentAd.getProgressType();
        Object obj = -1;
        switch (progressType.hashCode()) {
            case 97299:
                if (progressType.equals(Video.PROGRESS_BAR)) {
                    int i = 1;
                    break;
                }
                break;
            case 94755854:
                if (progressType.equals(Video.PROGRESS_CLOCK)) {
                    obj = 2;
                    break;
                }
                break;
            case 1544803905:
                if (progressType.equals("default")) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                if (!this.bY.get("progress_type").equals(Video.PROGRESS_BAR)) {
                    if (this.bY.get("progress_type").equals(Video.PROGRESS_CLOCK)) {
                        m278R();
                        break;
                    }
                }
                m277Q();
                break;
                break;
            case 1:
                m277Q();
                break;
            case 2:
                m278R();
                break;
        }
        this.mHandler.postDelayed(this.co, Long.parseLong(this.bY.get("postpone_vta_sec")) * 1000);
    }

    @SuppressLint({"RtlHardcoded"})
    private void m277Q() {
        String str;
        this.bX = new ProgressBar(this, null, 16842872);
        this.bX.setIndeterminate(false);
        this.bT.addView(this.bX);
        ((LayoutParams) this.bX.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
        ((LayoutParams) this.bX.getLayoutParams()).width = -1;
        ((LayoutParams) this.bX.getLayoutParams()).height = AppnextActivity.m131a((Context) this, 4.0f);
        Drawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));
        if (Video.currentAd.getProgressColor().equals("")) {
            str = this.bY.get("progress_color");
        } else {
            str = Video.currentAd.getProgressColor();
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        shapeDrawable.getPaint().setColor(Color.parseColor(str));
        this.bX.setProgressDrawable(new ClipDrawable(shapeDrawable, 3, 1));
        this.bX.setBackgroundColor(Color.parseColor("#77000000"));
        this.bX.setVisibility(8);
    }

    private void m278R() {
        String str;
        String str2;
        if (Video.currentAd.getProgressColor().equals("")) {
            str = this.bY.get("progress_color");
        } else {
            str = Video.currentAd.getProgressColor();
        }
        if (str.startsWith("#")) {
            str2 = str;
        } else {
            str2 = "#" + str;
        }
        this.bN = new C0165a(this, null, str2);
        this.bT.addView(this.bN);
        ((LayoutParams) this.bN.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
        ((LayoutParams) this.bN.getLayoutParams()).addRule(9);
        ((LayoutParams) this.bN.getLayoutParams()).width = AppnextActivity.m131a((Context) this, 34.0f);
        ((LayoutParams) this.bN.getLayoutParams()).height = AppnextActivity.m131a((Context) this, 34.0f);
        ((LayoutParams) this.bN.getLayoutParams()).setMargins(AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f));
        this.cc = new TextView(this);
        this.cc.setTextSize(2, 12.0f);
        this.cc.setTextColor(Color.parseColor(str2));
        this.bT.addView(this.cc);
        ((LayoutParams) this.cc.getLayoutParams()).width = AppnextActivity.m131a((Context) this, (float) BitmapDescriptorFactory.HUE_ORANGE);
        ((LayoutParams) this.cc.getLayoutParams()).height = AppnextActivity.m131a((Context) this, 34.0f);
        ((LayoutParams) this.cc.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
        ((LayoutParams) this.cc.getLayoutParams()).addRule(9);
        ((LayoutParams) this.cc.getLayoutParams()).setMargins(AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 14.0f));
        this.cc.setGravity(17);
        this.cc.setLayerType(1, new Paint());
        this.cc.setShadowLayer(2.0f, 2.0f, 2.0f, Color.argb(128, 0, 0, 0));
        this.cc.setVisibility(8);
        this.bN.setVisibility(8);
    }

    private void m279S() {
        this.bV = new RelativeLayout(this);
        this.bT.addView(this.bV);
        this.bV.getLayoutParams().width = -2;
        this.bV.getLayoutParams().height = -2;
        this.bV.setPadding(AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f));
        ((LayoutParams) this.bV.getLayoutParams()).setMargins(AppnextActivity.m131a((Context) this, 15.0f), AppnextActivity.m131a((Context) this, 15.0f), AppnextActivity.m131a((Context) this, 15.0f), AppnextActivity.m131a((Context) this, 15.0f));
        View button = new Button(this);
        this.bV.addView(button);
        byte[] decode = Base64.decode(C0149a.m9C(PreviewActivity.ON_CLICK_LISTENER_CLOSE), 0);
        button.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode, 0, decode.length)));
        button.getLayoutParams().width = AppnextActivity.m131a((Context) this, 25.0f);
        button.getLayoutParams().height = AppnextActivity.m131a((Context) this, 25.0f);
        String toLowerCase = this.bY.get("close_button_position").toLowerCase(Locale.getDefault());
        int i = -1;
        switch (toLowerCase.hashCode()) {
            case -1681838926:
                if (toLowerCase.equals("bottomleft")) {
                    i = 6;
                    break;
                }
                break;
            case -1383228885:
                if (toLowerCase.equals("bottom")) {
                    i = 7;
                    break;
                }
                break;
            case -1364013995:
                if (toLowerCase.equals("center")) {
                    i = 4;
                    break;
                }
                break;
            case -1139167524:
                if (toLowerCase.equals("topleft")) {
                    i = 0;
                    break;
                }
                break;
            case -948793881:
                if (toLowerCase.equals("topright")) {
                    i = 2;
                    break;
                }
                break;
            case -591738159:
                if (toLowerCase.equals("bottomright")) {
                    i = 8;
                    break;
                }
                break;
            case 115029:
                if (toLowerCase.equals("top")) {
                    i = 1;
                    break;
                }
                break;
            case 3317767:
                if (toLowerCase.equals("left")) {
                    i = 3;
                    break;
                }
                break;
            case 108511772:
                if (toLowerCase.equals("right")) {
                    i = 5;
                    break;
                }
                break;
        }
        switch (i) {
            case 1:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(14);
                break;
            case 2:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(11);
                break;
            case 3:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(15);
                break;
            case 4:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(13);
                break;
            case 5:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(15);
                ((LayoutParams) this.bV.getLayoutParams()).addRule(11);
                break;
            case 6:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(12);
                break;
            case 7:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(12);
                ((LayoutParams) this.bV.getLayoutParams()).addRule(14);
                break;
            case 8:
                ((LayoutParams) this.bV.getLayoutParams()).addRule(12);
                ((LayoutParams) this.bV.getLayoutParams()).addRule(11);
                break;
        }
        this.bV.setVisibility(8);
        button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void onClick(View view) {
                this.cq.m292a(this.cq.placementID, "", C0150b.bw);
                this.cq.onClose();
                this.cq.finish();
            }
        });
        this.bV.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void onClick(View view) {
                this.cq.m292a(this.cq.placementID, "", C0150b.bw);
                this.cq.onClose();
                this.cq.finish();
            }
        });
    }

    private void m280T() {
        String str;
        this.bU = new RelativeLayout(this);
        this.bT.addView(this.bU);
        this.bU.getLayoutParams().width = -2;
        this.bU.getLayoutParams().height = -2;
        this.bU.setPadding(AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 5.0f));
        ((LayoutParams) this.bU.getLayoutParams()).setMargins(AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f), AppnextActivity.m131a((Context) this, 12.0f));
        View button = new Button(this);
        if (Video.currentAd.getButtonText().equals("")) {
            FullscreenAd fullscreenAd = new FullscreenAd(this.cd);
            if (!fullscreenAd.getButtonText().equals("")) {
                button.setText(fullscreenAd.getButtonText());
            } else if (C0266f.m206c(this, fullscreenAd.getAdPackage())) {
                button.setText(this.bY.get("existing_button_text"));
            } else {
                button.setText(this.bY.get("new_button_text"));
            }
        } else {
            button.setText(Video.currentAd.getButtonText());
        }
        this.bU.addView(button);
        float e = (float) m136e(TextTrackStyle.DEFAULT_FONT_SCALE);
        float e2 = (float) m136e(7.0f);
        Drawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{e2, e2, e2, e2, e2, e2, e2, e2}, new RectF(e, e, e, e), new float[]{e2, e2, e2, e2, e2, e2, e2, e2}));
        if (Video.currentAd.getButtonColor().equals("")) {
            str = this.bY.get("button_color");
        } else {
            str = Video.currentAd.getButtonColor();
        }
        if (str == null || str.equals("")) {
            str = "ffffff";
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        shapeDrawable.getPaint().setColor(Color.parseColor(str));
        button.setBackgroundDrawable(shapeDrawable);
        button.setTypeface(null, 1);
        button.setTextColor(Color.parseColor(str));
        button.setTextSize(2, 16.0f);
        button.setPadding(AppnextActivity.m131a((Context) this, 15.0f), AppnextActivity.m131a((Context) this, 5.0f), AppnextActivity.m131a((Context) this, 15.0f), AppnextActivity.m131a((Context) this, 5.0f));
        int parseInt = Integer.parseInt(this.bY.get("button_width"));
        int parseInt2 = Integer.parseInt(this.bY.get("button_height"));
        if (parseInt == -1) {
            button.getLayoutParams().width = -2;
        } else {
            button.getLayoutParams().width = AppnextActivity.m131a((Context) this, (float) parseInt);
        }
        if (parseInt2 == -1) {
            button.getLayoutParams().height = -2;
        } else {
            button.getLayoutParams().height = AppnextActivity.m131a((Context) this, (float) parseInt2);
        }
        String toLowerCase = this.bY.get("button_position").toLowerCase(Locale.getDefault());
        parseInt = -1;
        switch (toLowerCase.hashCode()) {
            case -1681838926:
                if (toLowerCase.equals("bottomleft")) {
                    parseInt = 6;
                    break;
                }
                break;
            case -1383228885:
                if (toLowerCase.equals("bottom")) {
                    parseInt = 7;
                    break;
                }
                break;
            case -1364013995:
                if (toLowerCase.equals("center")) {
                    parseInt = 4;
                    break;
                }
                break;
            case -1139167524:
                if (toLowerCase.equals("topleft")) {
                    parseInt = 0;
                    break;
                }
                break;
            case -948793881:
                if (toLowerCase.equals("topright")) {
                    parseInt = 2;
                    break;
                }
                break;
            case -591738159:
                if (toLowerCase.equals("bottomright")) {
                    parseInt = 8;
                    break;
                }
                break;
            case 115029:
                if (toLowerCase.equals("top")) {
                    parseInt = 1;
                    break;
                }
                break;
            case 3317767:
                if (toLowerCase.equals("left")) {
                    parseInt = 3;
                    break;
                }
                break;
            case 108511772:
                if (toLowerCase.equals("right")) {
                    parseInt = 5;
                    break;
                }
                break;
        }
        switch (parseInt) {
            case 1:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(14);
                break;
            case 2:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(11);
                break;
            case 3:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(15);
                break;
            case 4:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(13);
                break;
            case 5:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(15);
                ((LayoutParams) this.bU.getLayoutParams()).addRule(11);
                break;
            case 6:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
                break;
            case 7:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
                ((LayoutParams) this.bU.getLayoutParams()).addRule(14);
                break;
            case 8:
                ((LayoutParams) this.bU.getLayoutParams()).addRule(8, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
                ((LayoutParams) this.bU.getLayoutParams()).addRule(11);
                break;
        }
        button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void onClick(View view) {
                this.cq.m291a(this.cq.cd);
            }
        });
        this.bU.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FullscreenActivity cq;

            {
                this.cq = r1;
            }

            public void onClick(View view) {
                this.cq.m291a(this.cq.cd);
            }
        });
        this.bU.setVisibility(8);
        if (Boolean.parseBoolean(this.bY.get("show_install")) && getResources().getConfiguration().orientation == 2) {
            this.bU.setVisibility(0);
        }
        button.setLayerType(1, shapeDrawable.getPaint());
        button.setShadowLayer(2.0f, 2.0f, 2.0f, Color.argb(128, 0, 0, 0));
    }

    private void m281U() {
        final View imageView = new ImageView(this);
        this.bT.addView(imageView);
        imageView.getLayoutParams().height = m136e(15.0f);
        imageView.getLayoutParams().width = m136e(BitmapDescriptorFactory.HUE_GREEN);
        imageView.setScaleType(ScaleType.FIT_START);
        ((LayoutParams) imageView.getLayoutParams()).setMargins(m136e(20.0f), m136e(20.0f), 0, 0);
        byte[] decode = Base64.decode(C0149a.m9C("privacy_opened"), 0);
        imageView.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode, 0, decode.length)));
        imageView.setOnClickListener(new C01594(this));
        imageView.setVisibility(8);
        final View imageView2 = new ImageView(this);
        this.bT.addView(imageView2);
        imageView2.getLayoutParams().height = m136e(15.0f);
        imageView2.getLayoutParams().width = m136e(20.0f);
        imageView2.setScaleType(ScaleType.FIT_START);
        ((LayoutParams) imageView2.getLayoutParams()).setMargins(m136e(20.0f), m136e(20.0f), 0, 0);
        decode = Base64.decode(C0149a.m9C("privacy_closed"), 0);
        imageView2.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode, 0, decode.length)));
        imageView2.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ FullscreenActivity cq;

            class C01601 implements Runnable {
                final /* synthetic */ C01615 cw;

                C01601(C01615 c01615) {
                    this.cw = c01615;
                }

                public void run() {
                    imageView.setVisibility(8);
                    imageView2.setVisibility(0);
                }
            }

            public void onClick(View view) {
                imageView.setVisibility(0);
                imageView2.setVisibility(8);
                new Handler().postDelayed(new C01601(this), 5000);
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    private void m282V() {
        String str;
        View linearLayout = new LinearLayout(this);
        this.bT.addView(linearLayout);
        linearLayout.getLayoutParams().width = -1;
        linearLayout.getLayoutParams().height = -2;
        ((LayoutParams) linearLayout.getLayoutParams()).addRule(3, ErrorCodes.ERROR_CODE_NO_APPLICABLE_ADDRESSES);
        linearLayout.setPadding(m136e(7.0f), m136e(10.0f), m136e(7.0f), 0);
        this.cf = new ImageView(this);
        new Thread(new C01636(this)).start();
        linearLayout.addView(this.cf);
        this.cf.getLayoutParams().width = m136e(BitmapDescriptorFactory.HUE_YELLOW);
        this.cf.getLayoutParams().height = m136e(BitmapDescriptorFactory.HUE_YELLOW);
        this.cf.setPadding(m136e(5.0f), m136e(5.0f), m136e(5.0f), m136e(5.0f));
        View linearLayout2 = new LinearLayout(this);
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2);
        linearLayout2.getLayoutParams().width = 0;
        linearLayout2.getLayoutParams().height = -2;
        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).weight = TextTrackStyle.DEFAULT_FONT_SCALE;
        ((LinearLayout.LayoutParams) linearLayout2.getLayoutParams()).gravity = 16;
        View textView = new TextView(this);
        linearLayout2.addView(textView);
        textView.setText(this.cd.getAdTitle());
        textView.setTextSize(2, 17.0f);
        textView.setTypeface(null, 1);
        textView.setTextColor(-1);
        textView.getLayoutParams().width = -1;
        textView.setSingleLine();
        textView.setEllipsize(TruncateAt.END);
        textView.setPadding(0, 0, m136e(3.0f), 0);
        if (!Boolean.parseBoolean(this.bY.get("show_nameApp"))) {
            textView.setVisibility(4);
        }
        this.cg = new LinearLayout(this);
        m298d(Float.parseFloat(this.cd.getStoreRating()));
        linearLayout2.addView(this.cg);
        if (!Boolean.parseBoolean(this.bY.get("show_rating"))) {
            this.cg.setVisibility(4);
        }
        View textView2 = new TextView(this);
        linearLayout2.addView(textView2);
        textView2.setTextSize(2, 10.0f);
        textView2.getLayoutParams().width = -1;
        textView2.setSingleLine();
        textView2.setTextColor(-1);
        textView2.setPadding(0, m136e(2.0f), 0, 0);
        try {
            int intValue = NumberFormat.getInstance().parse(this.cd.getStoreDownloads()).intValue();
            if (intValue > 1000000) {
                textView2.setText((intValue / 1000000) + "M downloads");
            } else if (intValue > 1000) {
                textView2.setText((intValue / 1000) + "K downloads");
            } else {
                textView2.setText(intValue + " downloads");
            }
        } catch (ParseException e) {
            textView2.setText(this.cd.getStoreDownloads() + " downloads");
        }
        this.ch = new Button(this);
        linearLayout.addView(this.ch);
        this.ch.getLayoutParams().width = -2;
        this.ch.getLayoutParams().height = -2;
        this.ch.setSingleLine();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (getResources().getConfiguration().orientation == 1) {
            this.ch.setMaxWidth(displayMetrics.widthPixels / 3);
        } else {
            this.ch.setMaxWidth(displayMetrics.heightPixels / 3);
        }
        this.ch.setEllipsize(TruncateAt.END);
        if (Video.currentAd.getButtonColor().equals("")) {
            str = this.bY.get("button_color");
        } else {
            str = Video.currentAd.getButtonColor();
        }
        if (!str.startsWith("#")) {
            str = "#" + str;
        }
        if (str.equals("#ffffff")) {
            str = "#66a031";
        }
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(Color.parseColor(str));
        gradientDrawable.setCornerRadius((float) m136e(4.0f));
        this.ch.setBackgroundDrawable(gradientDrawable);
        if (Video.currentAd.getButtonText().equals("")) {
            FullscreenAd fullscreenAd = new FullscreenAd(this.cd);
            if (!fullscreenAd.getButtonText().equals("")) {
                this.ch.setText(fullscreenAd.getButtonText());
            } else if (C0266f.m206c(this, fullscreenAd.getAdPackage())) {
                this.ch.setText(this.bY.get("existing_button_text"));
            } else {
                this.ch.setText(this.bY.get("new_button_text"));
            }
        } else {
            this.ch.setText(Video.currentAd.getButtonText());
        }
        this.ch.setPadding(m136e(15.0f), m136e(5.0f), m136e(15.0f), m136e(5.0f));
        ((LinearLayout.LayoutParams) this.ch.getLayoutParams()).setMargins(m136e(5.0f), 0, 0, m136e(5.0f));
        this.ch.setTextColor(-1);
        this.ch.setTextSize(2, 14.0f);
        ((LinearLayout.LayoutParams) this.ch.getLayoutParams()).gravity = 80;
        this.ch.setVisibility(4);
        if (Boolean.parseBoolean(this.bY.get("show_install")) && getResources().getConfiguration().orientation == 1) {
            this.ch.setVisibility(0);
        }
        this.ch.setOnClickListener(new C01647(this));
        if (!Boolean.parseBoolean(this.bY.get("show_icon"))) {
            this.cf.setVisibility(4);
            this.cg.setVisibility(4);
            textView2.setVisibility(4);
            textView.setVisibility(4);
        }
    }

    private LinearLayout m298d(float f) {
        C0266f.m194P("stars " + f);
        this.cg.removeAllViews();
        float f2 = f - ((float) ((int) f));
        if (f2 > 0.0f) {
            if (((double) f2) <= 0.5d) {
                f2 = 0.5f;
            } else {
                f += TextTrackStyle.DEFAULT_FONT_SCALE;
                f2 = 0.0f;
            }
        }
        for (int i = 0; i < ((int) f); i++) {
            View imageView = new ImageView(this);
            byte[] decode = Base64.decode(C0149a.m9C("star"), 0);
            imageView.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode, 0, decode.length)));
            this.cg.addView(imageView);
            imageView.getLayoutParams().width = m136e(10.0f);
            imageView.getLayoutParams().height = m136e(10.0f);
        }
        if (f2 > 0.0f) {
            View imageView2 = new ImageView(this);
            byte[] decode2 = Base64.decode(C0149a.m9C("half_star"), 0);
            imageView2.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode2, 0, decode2.length)));
            this.cg.addView(imageView2);
            imageView2.getLayoutParams().width = m136e(10.0f);
            imageView2.getLayoutParams().height = m136e(10.0f);
            f += TextTrackStyle.DEFAULT_FONT_SCALE;
        }
        for (int i2 = 0; ((float) i2) < 5.0f - f; i2++) {
            View imageView3 = new ImageView(this);
            byte[] decode3 = Base64.decode(C0149a.m9C("empty_star"), 0);
            imageView3.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(decode3, 0, decode3.length)));
            this.cg.addView(imageView3);
            imageView3.getLayoutParams().width = m136e(10.0f);
            imageView3.getLayoutParams().height = m136e(10.0f);
        }
        return this.cg;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m283Y();
        try {
            if (!Boolean.parseBoolean(this.bY.get("show_install"))) {
                this.bU.setVisibility(4);
                if (this.ch != null) {
                    this.ch.setVisibility(4);
                }
            } else if (configuration.orientation == 2) {
                this.bU.setVisibility(0);
                if (this.ch != null) {
                    this.ch.setVisibility(4);
                }
            } else {
                this.bU.setVisibility(4);
                if (this.ch != null) {
                    this.ch.setVisibility(0);
                }
            }
        } catch (Throwable th) {
        }
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled", "InlinedApi", "AddJavascriptInterface"})
    protected void m322W() {
        this.ck = new ThankYouPage(this, this, this.placementID);
        this.ck.setOnAdClosedCallback(new C09098(this));
        this.ck.setOnAdErrorCallback(new C09109(this));
        this.ck.setOnAdClickedCallback(Video.currentAd.getOnAdClickedCallback());
        this.ck.setBackButtonCanClose(true);
        if (Video.currentAd.getButtonText().equals("")) {
            FullscreenAd fullscreenAd = new FullscreenAd(this.cd);
            if (!fullscreenAd.getButtonText().equals("")) {
                this.ck.setButtonText(fullscreenAd.getButtonText());
            } else if (C0266f.m206c(this, fullscreenAd.getAdPackage())) {
                this.ck.setButtonText(this.bY.get("existing_button_text"));
            } else {
                this.ck.setButtonText(this.bY.get("new_button_text"));
            }
        } else {
            this.ck.setButtonText(Video.currentAd.getButtonText());
        }
        String buttonColor = Video.currentAd.getButtonColor().equals("") ? this.bY.get("button_color") : Video.currentAd.getButtonColor();
        if (!buttonColor.startsWith("#")) {
            buttonColor = "#" + buttonColor;
        }
        this.ck.setButtonColor(buttonColor.equals("#ffffff") ? "66a031" : buttonColor.substring(1));
        this.ck.setPostback(Video.currentAd.getPostback());
    }

    protected void m323X() {
        this.ck.showAd();
    }

    public void onBackPressed() {
        if (this.bZ) {
            if (this.bS != null) {
                m292a(this.placementID, "" + this.bS.getCurrentPosition(), C0150b.bE);
            }
            onClose();
            super.onBackPressed();
        }
    }

    protected void onPause() {
        super.onPause();
        m283Y();
        this.mHandler.removeCallbacks(this.cm);
        this.mHandler.removeCallbacks(this.cn);
        this.mHandler.removeCallbacks(this.co);
        if (this.bS != null) {
            this.bS.pause();
            this.cj = this.bS.getCurrentPosition();
        }
    }

    protected void onResume() {
        super.onResume();
        m283Y();
        bY();
        if (!(this.bS == null || this.ca)) {
            bZ();
            try {
                this.bR.start();
                this.mHandler.postDelayed(this.cm, 33);
            } catch (Throwable th) {
            }
        }
        if (this.ca) {
            bX();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        m283Y();
        try {
            if (this.mHandler != null) {
                this.mHandler.removeCallbacksAndMessages(null);
            }
            C0913c.aa().m165d(getApplicationContext(), Video.currentAd, this.placementID);
            this.mHandler = null;
            this.ce = null;
            this.cd = null;
            if (this.ck != null) {
                this.ck.destroy();
            }
            this.ck = null;
            if (this.bN != null) {
                this.bN.destroyDrawingCache();
                this.bN.clearAnimation();
                if (this.bN.getParent() != null) {
                    ((ViewGroup) this.bN.getParent()).removeView(this.bN);
                }
            }
            this.bN = null;
            this.cm = null;
        } catch (Throwable th) {
        }
        try {
            this.bW.clear();
            if (!(this.bT == null || this.bT.getParent() == null)) {
                ((ViewGroup) this.bT.getParent()).removeView(this.bT);
                this.bT = null;
            }
            if (!(this.bU == null || this.bU.getParent() == null)) {
                ((ViewGroup) this.bU.getParent()).removeView(this.bU);
                this.bU = null;
            }
            if (!(this.bV == null || this.bV.getParent() == null)) {
                ((ViewGroup) this.bV.getParent()).removeView(this.bV);
                this.bV = null;
            }
            if (!(this.bX == null || this.bX.getParent() == null)) {
                ((ViewGroup) this.bX.getParent()).removeView(this.bX);
                this.bX = null;
            }
            if (!(this.cc == null || this.cc.getParent() == null)) {
                ((ViewGroup) this.cc.getParent()).removeView(this.cc);
                this.cc = null;
            }
            if (!(this.cf == null || this.cf.getParent() == null)) {
                ((ViewGroup) this.cf.getParent()).removeView(this.cf);
                this.cf = null;
            }
            if (!(this.cg == null || this.cg.getParent() == null)) {
                ((ViewGroup) this.cg.getParent()).removeView(this.cg);
                this.cg = null;
            }
            if (!(this.ch == null || this.ch.getParent() == null)) {
                ((ViewGroup) this.ch.getParent()).removeView(this.ch);
                this.ch = null;
            }
            if (this.gS != null) {
                this.gS.removeAllViews();
                if (this.gS.getParent() != null) {
                    ((ViewGroup) this.gS.getParent()).removeView(this.gS);
                }
                this.gS = null;
            }
        } catch (Throwable th2) {
        }
        try {
            if (this.cl != null) {
                this.cl.mo1184b(this);
            }
            this.cl = null;
        } catch (Throwable th3) {
        }
        try {
            if (this.bS != null) {
                this.bS.setOnCompletionListener(null);
                this.bS.setOnErrorListener(null);
                this.bS.setOnPreparedListener(null);
                this.bS.suspend();
                this.bS = null;
            }
        } catch (Throwable th4) {
        }
        try {
            if (this.bR != null) {
                this.bR.release();
                this.bR = null;
            }
        } catch (Throwable th5) {
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 14857 && i2 == 0) {
            try {
                this.bR.start();
                this.mHandler.postDelayed(this.cm, 33);
            } catch (Throwable th) {
            }
        }
    }

    private void m291a(AppnextAd appnextAd) {
        m292a(this.placementID, "", C0150b.bt);
        mo1192b(appnextAd, (C0258a) this);
    }

    protected void mo1192b(AppnextAd appnextAd, C0258a c0258a) {
        if (appnextAd != null) {
            runOnUiThread(new Runnable(this) {
                final /* synthetic */ FullscreenActivity cq;

                {
                    this.cq = r1;
                }

                public void run() {
                    this.cq.m132a(this.cq.gS, Base64.decode(C0149a.m9C("loader"), 0));
                }
            });
            this.ce = appnextAd;
            if (Video.currentAd.getOnAdClickedCallback() != null) {
                Video.currentAd.getOnAdClickedCallback().adClicked();
            }
            super.mo1192b(appnextAd, c0258a);
        }
    }

    public void onMarket(String str) {
        Intent intent;
        if (!C0266f.m206c(this, this.ce.getAdPackage())) {
            try {
                if (!(this.ce == null || str.startsWith("market://details?id=" + this.ce.getAdPackage()))) {
                    this.click.m186a(C0266f.getCookie("admin.appnext.com", "applink"), this.ce.getBannerID(), this.placementID, Video.currentAd.getTID(), str, "SetROpenV1");
                }
            } catch (Throwable th) {
            }
            if (this.ce != null) {
                if (this.cl == null) {
                    this.cl = new C0907c();
                }
                this.cl.m262a(this.ce.getAdPackage(), str, C0266f.getCookie("admin.appnext.com", "applink"), this.ce.getBannerID(), this.placementID, Video.currentAd.getTID(), Video.currentAd.getVID(), Video.currentAd.getAUID(), null);
                this.cl.m224A(this);
            }
            intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            try {
                startActivity(intent);
            } catch (Throwable th2) {
                m292a(this.placementID, str, "cannot_open_market");
            }
        } else if (str.startsWith("market://")) {
            try {
                intent = getPackageManager().getLaunchIntentForPackage(this.ce.getAdPackage());
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                startActivity(intent);
            } catch (Throwable th3) {
                m292a(this.placementID, str, "cannot_open_installed_app");
            }
        } else {
            intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.addFlags(DriveFile.MODE_READ_ONLY);
            try {
                startActivity(intent);
            } catch (Throwable th4) {
                m292a(this.placementID, str, "cannot_open_deeplink");
            }
        }
        bZ();
    }

    public void error(String str) {
        if (this.ce == null || Video.currentAd == null) {
            bZ();
            return;
        }
        this.click.m186a(C0266f.getCookie("admin.appnext.com", "applink"), this.ce.getBannerID(), this.placementID, Video.currentAd.getTID(), str, "SetDOpenV1");
        m292a(this.placementID, new FullscreenAd(this.ce).getAppURL() + " " + str, C0150b.bv);
        try {
            mo1192b(this.ce, new C0258a(this) {
                final /* synthetic */ FullscreenActivity cq;

                {
                    this.cq = r1;
                }

                public void onMarket(String str) {
                    this.cq.onMarket(str);
                }

                public void error(String str) {
                    Intent intent;
                    if (Boolean.parseBoolean(this.cq.bY.get("urlApp_protection"))) {
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse("market://details?id=" + this.cq.ce.getAdPackage()));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        try {
                            this.cq.startActivity(intent);
                        } catch (Throwable th) {
                            this.cq.finish();
                        }
                    } else if (str == null) {
                        this.cq.finish();
                        return;
                    } else {
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        intent.addFlags(DriveFile.MODE_READ_ONLY);
                        try {
                            this.cq.startActivity(intent);
                        } catch (Throwable th2) {
                            this.cq.finish();
                        }
                    }
                    this.cq.bZ();
                }
            });
        } catch (Throwable th) {
        }
    }

    private void onClose() {
        if (Video.currentAd.getOnAdClosedCallback() != null) {
            Video.currentAd.getOnAdClosedCallback().onAdClosed();
        }
    }

    private void m283Y() {
    }

    private void m292a(String str, String str2, String str3) {
        C0266f.m201a(Video.currentAd.getTID(), Video.currentAd.getVID(), Video.currentAd.getAUID(), str, str2, str3, this.type == 1 ? "fullscreen" : "rewarded", this.cd != null ? this.cd.getBannerID() : "", this.cd != null ? this.cd.getCampaignID() : "");
    }
}
