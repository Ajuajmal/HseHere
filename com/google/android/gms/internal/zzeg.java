package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.search.SearchAuth.StatusCodes;
import java.util.Map;

@zzhb
public class zzeg {
    private final Context mContext;
    private final String zzAY;
    private zzb<zzed> zzAZ;
    private zzb<zzed> zzBa;
    private zze zzBb;
    private int zzBc;
    private final VersionInfoParcel zzpT;
    private final Object zzpV;

    static class zza {
        static int zzBm = 60000;
        static int zzBn = StatusCodes.AUTH_DISABLED;
    }

    public interface zzb<T> {
        void zze(T t);
    }

    public static class zzc<T> implements zzb<T> {
        public void zze(T t) {
        }
    }

    public static class zzd extends zzjj<zzeh> {
        private final zze zzBo;
        private boolean zzBp;
        private final Object zzpV = new Object();

        class C10851 implements com.google.android.gms.internal.zzji.zzc<zzeh> {
            final /* synthetic */ zzd zzBq;

            C10851(zzd com_google_android_gms_internal_zzeg_zzd) {
                this.zzBq = com_google_android_gms_internal_zzeg_zzd;
            }

            public void zzd(zzeh com_google_android_gms_internal_zzeh) {
                zzin.m468v("Ending javascript session.");
                ((zzei) com_google_android_gms_internal_zzeh).zzew();
            }

            public /* synthetic */ void zze(Object obj) {
                zzd((zzeh) obj);
            }
        }

        class C10862 implements com.google.android.gms.internal.zzji.zzc<zzeh> {
            final /* synthetic */ zzd zzBq;

            C10862(zzd com_google_android_gms_internal_zzeg_zzd) {
                this.zzBq = com_google_android_gms_internal_zzeg_zzd;
            }

            public void zzd(zzeh com_google_android_gms_internal_zzeh) {
                zzin.m468v("Releasing engine reference.");
                this.zzBq.zzBo.zzet();
            }

            public /* synthetic */ void zze(Object obj) {
                zzd((zzeh) obj);
            }
        }

        class C10873 implements com.google.android.gms.internal.zzji.zza {
            final /* synthetic */ zzd zzBq;

            C10873(zzd com_google_android_gms_internal_zzeg_zzd) {
                this.zzBq = com_google_android_gms_internal_zzeg_zzd;
            }

            public void run() {
                this.zzBq.zzBo.zzet();
            }
        }

        public zzd(zze com_google_android_gms_internal_zzeg_zze) {
            this.zzBo = com_google_android_gms_internal_zzeg_zze;
        }

        public void release() {
            synchronized (this.zzpV) {
                if (this.zzBp) {
                    return;
                }
                this.zzBp = true;
                zza(new C10851(this), new com.google.android.gms.internal.zzji.zzb());
                zza(new C10862(this), new C10873(this));
            }
        }
    }

    public static class zze extends zzjj<zzed> {
        private zzb<zzed> zzBa;
        private boolean zzBr;
        private int zzBs;
        private final Object zzpV = new Object();

        class C10903 implements com.google.android.gms.internal.zzji.zzc<zzed> {
            final /* synthetic */ zze zzBu;

            C10903(zze com_google_android_gms_internal_zzeg_zze) {
                this.zzBu = com_google_android_gms_internal_zzeg_zze;
            }

            public void zza(final zzed com_google_android_gms_internal_zzed) {
                zzir.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ C10903 zzBw;

                    public void run() {
                        this.zzBw.zzBu.zzBa.zze(com_google_android_gms_internal_zzed);
                        com_google_android_gms_internal_zzed.destroy();
                    }
                });
            }

            public /* synthetic */ void zze(Object obj) {
                zza((zzed) obj);
            }
        }

        public zze(zzb<zzed> com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed) {
            this.zzBa = com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed;
            this.zzBr = false;
            this.zzBs = 0;
        }

        public zzd zzes() {
            final zzd com_google_android_gms_internal_zzeg_zzd = new zzd(this);
            synchronized (this.zzpV) {
                zza(new com.google.android.gms.internal.zzji.zzc<zzed>(this) {
                    final /* synthetic */ zze zzBu;

                    public void zza(zzed com_google_android_gms_internal_zzed) {
                        zzin.m468v("Getting a new session for JS Engine.");
                        com_google_android_gms_internal_zzeg_zzd.zzh(com_google_android_gms_internal_zzed.zzen());
                    }

                    public /* synthetic */ void zze(Object obj) {
                        zza((zzed) obj);
                    }
                }, new com.google.android.gms.internal.zzji.zza(this) {
                    final /* synthetic */ zze zzBu;

                    public void run() {
                        zzin.m468v("Rejecting reference for JS Engine.");
                        com_google_android_gms_internal_zzeg_zzd.reject();
                    }
                });
                zzx.zzab(this.zzBs >= 0);
                this.zzBs++;
            }
            return com_google_android_gms_internal_zzeg_zzd;
        }

        protected void zzet() {
            boolean z = true;
            synchronized (this.zzpV) {
                if (this.zzBs < 1) {
                    z = false;
                }
                zzx.zzab(z);
                zzin.m468v("Releasing 1 reference for JS Engine");
                this.zzBs--;
                zzev();
            }
        }

        public void zzeu() {
            boolean z = true;
            synchronized (this.zzpV) {
                if (this.zzBs < 0) {
                    z = false;
                }
                zzx.zzab(z);
                zzin.m468v("Releasing root reference. JS Engine will be destroyed once other references are released.");
                this.zzBr = true;
                zzev();
            }
        }

        protected void zzev() {
            synchronized (this.zzpV) {
                zzx.zzab(this.zzBs >= 0);
                if (this.zzBr && this.zzBs == 0) {
                    zzin.m468v("No reference is left (including root). Cleaning up engine.");
                    zza(new C10903(this), new com.google.android.gms.internal.zzji.zzb());
                } else {
                    zzin.m468v("There are still references to the engine. Not destroying.");
                }
            }
        }
    }

    public zzeg(Context context, VersionInfoParcel versionInfoParcel, String str) {
        this.zzpV = new Object();
        this.zzBc = 1;
        this.zzAY = str;
        this.mContext = context.getApplicationContext();
        this.zzpT = versionInfoParcel;
        this.zzAZ = new zzc();
        this.zzBa = new zzc();
    }

    public zzeg(Context context, VersionInfoParcel versionInfoParcel, String str, zzb<zzed> com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed, zzb<zzed> com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed2) {
        this(context, versionInfoParcel, str);
        this.zzAZ = com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed;
        this.zzBa = com_google_android_gms_internal_zzeg_zzb_com_google_android_gms_internal_zzed2;
    }

    private zze zzep() {
        final zze com_google_android_gms_internal_zzeg_zze = new zze(this.zzBa);
        zzir.runOnUiThread(new Runnable(this) {
            final /* synthetic */ zzeg zzBe;

            public void run() {
                final zzed zza = this.zzBe.zza(this.zzBe.mContext, this.zzBe.zzpT);
                zza.zza(new com.google.android.gms.internal.zzed.zza(this) {
                    final /* synthetic */ C04501 zzBg;

                    class C04471 implements Runnable {
                        final /* synthetic */ C10801 zzBh;

                        class C04461 implements Runnable {
                            final /* synthetic */ C04471 zzBi;

                            C04461(C04471 c04471) {
                                this.zzBi = c04471;
                            }

                            public void run() {
                                zza.destroy();
                            }
                        }

                        C04471(C10801 c10801) {
                            this.zzBh = c10801;
                        }

                        /* JADX WARNING: inconsistent code. */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public void run() {
                            /*
                            r3 = this;
                            r0 = r3.zzBh;
                            r0 = r0.zzBg;
                            r0 = r0.zzBe;
                            r1 = r0.zzpV;
                            monitor-enter(r1);
                            r0 = r3.zzBh;	 Catch:{ all -> 0x003f }
                            r0 = r0.zzBg;	 Catch:{ all -> 0x003f }
                            r0 = r0;	 Catch:{ all -> 0x003f }
                            r0 = r0.getStatus();	 Catch:{ all -> 0x003f }
                            r2 = -1;
                            if (r0 == r2) goto L_0x0025;
                        L_0x0018:
                            r0 = r3.zzBh;	 Catch:{ all -> 0x003f }
                            r0 = r0.zzBg;	 Catch:{ all -> 0x003f }
                            r0 = r0;	 Catch:{ all -> 0x003f }
                            r0 = r0.getStatus();	 Catch:{ all -> 0x003f }
                            r2 = 1;
                            if (r0 != r2) goto L_0x0027;
                        L_0x0025:
                            monitor-exit(r1);	 Catch:{ all -> 0x003f }
                        L_0x0026:
                            return;
                        L_0x0027:
                            r0 = r3.zzBh;	 Catch:{ all -> 0x003f }
                            r0 = r0.zzBg;	 Catch:{ all -> 0x003f }
                            r0 = r0;	 Catch:{ all -> 0x003f }
                            r0.reject();	 Catch:{ all -> 0x003f }
                            r0 = new com.google.android.gms.internal.zzeg$1$1$1$1;	 Catch:{ all -> 0x003f }
                            r0.<init>(r3);	 Catch:{ all -> 0x003f }
                            com.google.android.gms.internal.zzir.runOnUiThread(r0);	 Catch:{ all -> 0x003f }
                            r0 = "Could not receive loaded message in a timely manner. Rejecting.";
                            com.google.android.gms.internal.zzin.m468v(r0);	 Catch:{ all -> 0x003f }
                            monitor-exit(r1);	 Catch:{ all -> 0x003f }
                            goto L_0x0026;
                        L_0x003f:
                            r0 = move-exception;
                            monitor-exit(r1);	 Catch:{ all -> 0x003f }
                            throw r0;
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzeg.1.1.1.run():void");
                        }
                    }

                    public void zzeo() {
                        zzir.zzMc.postDelayed(new C04471(this), (long) zza.zzBn);
                    }
                });
                zza.zza("/jsLoaded", new zzdf(this) {
                    final /* synthetic */ C04501 zzBg;

                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void zza(com.google.android.gms.internal.zzjp r4, java.util.Map<java.lang.String, java.lang.String> r5) {
                        /*
                        r3 = this;
                        r0 = r3.zzBg;
                        r0 = r0.zzBe;
                        r1 = r0.zzpV;
                        monitor-enter(r1);
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0;	 Catch:{ all -> 0x0051 }
                        r0 = r0.getStatus();	 Catch:{ all -> 0x0051 }
                        r2 = -1;
                        if (r0 == r2) goto L_0x001f;
                    L_0x0014:
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0;	 Catch:{ all -> 0x0051 }
                        r0 = r0.getStatus();	 Catch:{ all -> 0x0051 }
                        r2 = 1;
                        if (r0 != r2) goto L_0x0021;
                    L_0x001f:
                        monitor-exit(r1);	 Catch:{ all -> 0x0051 }
                    L_0x0020:
                        return;
                    L_0x0021:
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0.zzBe;	 Catch:{ all -> 0x0051 }
                        r2 = 0;
                        r0.zzBc = r2;	 Catch:{ all -> 0x0051 }
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0.zzBe;	 Catch:{ all -> 0x0051 }
                        r0 = r0.zzAZ;	 Catch:{ all -> 0x0051 }
                        r2 = r0;	 Catch:{ all -> 0x0051 }
                        r0.zze(r2);	 Catch:{ all -> 0x0051 }
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0;	 Catch:{ all -> 0x0051 }
                        r2 = r0;	 Catch:{ all -> 0x0051 }
                        r0.zzh(r2);	 Catch:{ all -> 0x0051 }
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r0 = r0.zzBe;	 Catch:{ all -> 0x0051 }
                        r2 = r3.zzBg;	 Catch:{ all -> 0x0051 }
                        r2 = r0;	 Catch:{ all -> 0x0051 }
                        r0.zzBb = r2;	 Catch:{ all -> 0x0051 }
                        r0 = "Successfully loaded JS Engine.";
                        com.google.android.gms.internal.zzin.m468v(r0);	 Catch:{ all -> 0x0051 }
                        monitor-exit(r1);	 Catch:{ all -> 0x0051 }
                        goto L_0x0020;
                    L_0x0051:
                        r0 = move-exception;
                        monitor-exit(r1);	 Catch:{ all -> 0x0051 }
                        throw r0;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzeg.1.2.zza(com.google.android.gms.internal.zzjp, java.util.Map):void");
                    }
                });
                final zzja com_google_android_gms_internal_zzja = new zzja();
                zzdf c10823 = new zzdf(this) {
                    final /* synthetic */ C04501 zzBg;

                    public void zza(zzjp com_google_android_gms_internal_zzjp, Map<String, String> map) {
                        synchronized (this.zzBg.zzBe.zzpV) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzaJ("JS Engine is requesting an update");
                            if (this.zzBg.zzBe.zzBc == 0) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzaJ("Starting reload.");
                                this.zzBg.zzBe.zzBc = 2;
                                this.zzBg.zzBe.zzeq();
                            }
                            zza.zzb("/requestReload", (zzdf) com_google_android_gms_internal_zzja.get());
                        }
                    }
                };
                com_google_android_gms_internal_zzja.set(c10823);
                zza.zza("/requestReload", c10823);
                if (this.zzBe.zzAY.endsWith(".js")) {
                    zza.zzZ(this.zzBe.zzAY);
                } else if (this.zzBe.zzAY.startsWith("<html>")) {
                    zza.zzab(this.zzBe.zzAY);
                } else {
                    zza.zzaa(this.zzBe.zzAY);
                }
                zzir.zzMc.postDelayed(new Runnable(this) {
                    final /* synthetic */ C04501 zzBg;

                    class C04481 implements Runnable {
                        final /* synthetic */ C04494 zzBk;

                        C04481(C04494 c04494) {
                            this.zzBk = c04494;
                        }

                        public void run() {
                            zza.destroy();
                        }
                    }

                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                        r3 = this;
                        r0 = r3.zzBg;
                        r0 = r0.zzBe;
                        r1 = r0.zzpV;
                        monitor-enter(r1);
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0037 }
                        r0 = r0;	 Catch:{ all -> 0x0037 }
                        r0 = r0.getStatus();	 Catch:{ all -> 0x0037 }
                        r2 = -1;
                        if (r0 == r2) goto L_0x001f;
                    L_0x0014:
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0037 }
                        r0 = r0;	 Catch:{ all -> 0x0037 }
                        r0 = r0.getStatus();	 Catch:{ all -> 0x0037 }
                        r2 = 1;
                        if (r0 != r2) goto L_0x0021;
                    L_0x001f:
                        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
                    L_0x0020:
                        return;
                    L_0x0021:
                        r0 = r3.zzBg;	 Catch:{ all -> 0x0037 }
                        r0 = r0;	 Catch:{ all -> 0x0037 }
                        r0.reject();	 Catch:{ all -> 0x0037 }
                        r0 = new com.google.android.gms.internal.zzeg$1$4$1;	 Catch:{ all -> 0x0037 }
                        r0.<init>(r3);	 Catch:{ all -> 0x0037 }
                        com.google.android.gms.internal.zzir.runOnUiThread(r0);	 Catch:{ all -> 0x0037 }
                        r0 = "Could not receive loaded message in a timely manner. Rejecting.";
                        com.google.android.gms.internal.zzin.m468v(r0);	 Catch:{ all -> 0x0037 }
                        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
                        goto L_0x0020;
                    L_0x0037:
                        r0 = move-exception;
                        monitor-exit(r1);	 Catch:{ all -> 0x0037 }
                        throw r0;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzeg.1.4.run():void");
                    }
                }, (long) zza.zzBm);
            }
        });
        return com_google_android_gms_internal_zzeg_zze;
    }

    protected zzed zza(Context context, VersionInfoParcel versionInfoParcel) {
        return new zzef(context, versionInfoParcel, null);
    }

    protected zze zzeq() {
        final zze zzep = zzep();
        zzep.zza(new com.google.android.gms.internal.zzji.zzc<zzed>(this) {
            final /* synthetic */ zzeg zzBe;

            public void zza(zzed com_google_android_gms_internal_zzed) {
                synchronized (this.zzBe.zzpV) {
                    this.zzBe.zzBc = 0;
                    if (!(this.zzBe.zzBb == null || zzep == this.zzBe.zzBb)) {
                        zzin.m468v("New JS engine is loaded, marking previous one as destroyable.");
                        this.zzBe.zzBb.zzeu();
                    }
                    this.zzBe.zzBb = zzep;
                }
            }

            public /* synthetic */ void zze(Object obj) {
                zza((zzed) obj);
            }
        }, new com.google.android.gms.internal.zzji.zza(this) {
            final /* synthetic */ zzeg zzBe;

            public void run() {
                synchronized (this.zzBe.zzpV) {
                    this.zzBe.zzBc = 1;
                    zzin.m468v("Failed loading new engine. Marking new engine destroyable.");
                    zzep.zzeu();
                }
            }
        });
        return zzep;
    }

    public zzd zzer() {
        zzd zzes;
        synchronized (this.zzpV) {
            if (this.zzBb == null || this.zzBb.getStatus() == -1) {
                this.zzBc = 2;
                this.zzBb = zzeq();
                zzes = this.zzBb.zzes();
            } else if (this.zzBc == 0) {
                zzes = this.zzBb.zzes();
            } else if (this.zzBc == 1) {
                this.zzBc = 2;
                zzeq();
                zzes = this.zzBb.zzes();
            } else if (this.zzBc == 2) {
                zzes = this.zzBb.zzes();
            } else {
                zzes = this.zzBb.zzes();
            }
        }
        return zzes;
    }
}
