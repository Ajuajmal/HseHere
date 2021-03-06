package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaf.zzj;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzrr;

public class zzp extends com.google.android.gms.common.api.internal.zzb<ContainerHolder> {
    private final Context mContext;
    private final Looper zzagr;
    private final String zzbhM;
    private long zzbhR;
    private final TagManager zzbhY;
    private final zzd zzbib;
    private final zzcd zzbic;
    private final int zzbid;
    private zzf zzbie;
    private zzrr zzbif;
    private volatile zzo zzbig;
    private volatile boolean zzbih;
    private zzj zzbii;
    private String zzbij;
    private zze zzbik;
    private zza zzbil;
    private final zzmq zzqW;

    class C05871 {
    }

    interface zza {
        boolean zzb(Container container);
    }

    class C11652 implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp zzbim;

        C11652(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzbim = com_google_android_gms_tagmanager_zzp;
        }

        public String zzGd() {
            return this.zzbim.zzGd();
        }

        public void zzGf() {
            zzbg.zzaK("Refresh ignored: container loaded as default only.");
        }

        public void zzfT(String str) {
            this.zzbim.zzfT(str);
        }
    }

    private class zzb implements zzbf<com.google.android.gms.internal.zzrq.zza> {
        final /* synthetic */ zzp zzbim;

        private zzb(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzbim = com_google_android_gms_tagmanager_zzp;
        }

        public void zzGk() {
        }

        public /* synthetic */ void zzI(Object obj) {
            zza((com.google.android.gms.internal.zzrq.zza) obj);
        }

        public void zza(com.google.android.gms.internal.zzrq.zza com_google_android_gms_internal_zzrq_zza) {
            zzj com_google_android_gms_internal_zzaf_zzj;
            if (com_google_android_gms_internal_zzrq_zza.zzbme != null) {
                com_google_android_gms_internal_zzaf_zzj = com_google_android_gms_internal_zzrq_zza.zzbme;
            } else {
                com.google.android.gms.internal.zzaf.zzf com_google_android_gms_internal_zzaf_zzf = com_google_android_gms_internal_zzrq_zza.zzju;
                com_google_android_gms_internal_zzaf_zzj = new zzj();
                com_google_android_gms_internal_zzaf_zzj.zzju = com_google_android_gms_internal_zzaf_zzf;
                com_google_android_gms_internal_zzaf_zzj.zzjt = null;
                com_google_android_gms_internal_zzaf_zzj.zzjv = com_google_android_gms_internal_zzaf_zzf.version;
            }
            this.zzbim.zza(com_google_android_gms_internal_zzaf_zzj, com_google_android_gms_internal_zzrq_zza.zzbmd, true);
        }

        public void zza(com.google.android.gms.tagmanager.zzbf.zza com_google_android_gms_tagmanager_zzbf_zza) {
            if (!this.zzbim.zzbih) {
                this.zzbim.zzak(0);
            }
        }
    }

    private class zzc implements zzbf<zzj> {
        final /* synthetic */ zzp zzbim;

        private zzc(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzbim = com_google_android_gms_tagmanager_zzp;
        }

        public void zzGk() {
        }

        public /* synthetic */ void zzI(Object obj) {
            zzb((zzj) obj);
        }

        public void zza(com.google.android.gms.tagmanager.zzbf.zza com_google_android_gms_tagmanager_zzbf_zza) {
            synchronized (this.zzbim) {
                if (!this.zzbim.isReady()) {
                    if (this.zzbim.zzbig != null) {
                        this.zzbim.zza(this.zzbim.zzbig);
                    } else {
                        this.zzbim.zza(this.zzbim.zzbn(Status.zzagF));
                    }
                }
            }
            this.zzbim.zzak(3600000);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void zzb(com.google.android.gms.internal.zzaf.zzj r6) {
            /*
            r5 = this;
            r1 = r5.zzbim;
            monitor-enter(r1);
            r0 = r6.zzju;	 Catch:{ all -> 0x0065 }
            if (r0 != 0) goto L_0x002a;
        L_0x0007:
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r0 = r0.zzbii;	 Catch:{ all -> 0x0065 }
            r0 = r0.zzju;	 Catch:{ all -> 0x0065 }
            if (r0 != 0) goto L_0x0020;
        L_0x0011:
            r0 = "Current resource is null; network resource is also null";
            com.google.android.gms.tagmanager.zzbg.m254e(r0);	 Catch:{ all -> 0x0065 }
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r2 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
            r0.zzak(r2);	 Catch:{ all -> 0x0065 }
            monitor-exit(r1);	 Catch:{ all -> 0x0065 }
        L_0x001f:
            return;
        L_0x0020:
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r0 = r0.zzbii;	 Catch:{ all -> 0x0065 }
            r0 = r0.zzju;	 Catch:{ all -> 0x0065 }
            r6.zzju = r0;	 Catch:{ all -> 0x0065 }
        L_0x002a:
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r2 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r2 = r2.zzqW;	 Catch:{ all -> 0x0065 }
            r2 = r2.currentTimeMillis();	 Catch:{ all -> 0x0065 }
            r4 = 0;
            r0.zza(r6, r2, r4);	 Catch:{ all -> 0x0065 }
            r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0065 }
            r0.<init>();	 Catch:{ all -> 0x0065 }
            r2 = "setting refresh time to current time: ";
            r0 = r0.append(r2);	 Catch:{ all -> 0x0065 }
            r2 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r2 = r2.zzbhR;	 Catch:{ all -> 0x0065 }
            r0 = r0.append(r2);	 Catch:{ all -> 0x0065 }
            r0 = r0.toString();	 Catch:{ all -> 0x0065 }
            com.google.android.gms.tagmanager.zzbg.m255v(r0);	 Catch:{ all -> 0x0065 }
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r0 = r0.zzGj();	 Catch:{ all -> 0x0065 }
            if (r0 != 0) goto L_0x0063;
        L_0x005e:
            r0 = r5.zzbim;	 Catch:{ all -> 0x0065 }
            r0.zza(r6);	 Catch:{ all -> 0x0065 }
        L_0x0063:
            monitor-exit(r1);	 Catch:{ all -> 0x0065 }
            goto L_0x001f;
        L_0x0065:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0065 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzp.zzc.zzb(com.google.android.gms.internal.zzaf$zzj):void");
        }
    }

    private class zzd implements com.google.android.gms.tagmanager.zzo.zza {
        final /* synthetic */ zzp zzbim;

        private zzd(zzp com_google_android_gms_tagmanager_zzp) {
            this.zzbim = com_google_android_gms_tagmanager_zzp;
        }

        public String zzGd() {
            return this.zzbim.zzGd();
        }

        public void zzGf() {
            if (this.zzbim.zzbic.zzlw()) {
                this.zzbim.zzak(0);
            }
        }

        public void zzfT(String str) {
            this.zzbim.zzfT(str);
        }
    }

    interface zze extends Releasable {
        void zza(zzbf<zzj> com_google_android_gms_tagmanager_zzbf_com_google_android_gms_internal_zzaf_zzj);

        void zzf(long j, String str);

        void zzfW(String str);
    }

    interface zzf extends Releasable {
        void zzGl();

        void zza(zzbf<com.google.android.gms.internal.zzrq.zza> com_google_android_gms_tagmanager_zzbf_com_google_android_gms_internal_zzrq_zza);

        void zzb(com.google.android.gms.internal.zzrq.zza com_google_android_gms_internal_zzrq_zza);

        com.google.android.gms.internal.zzrs.zzc zzke(int i);
    }

    zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzf com_google_android_gms_tagmanager_zzp_zzf, zze com_google_android_gms_tagmanager_zzp_zze, zzrr com_google_android_gms_internal_zzrr, zzmq com_google_android_gms_internal_zzmq, zzcd com_google_android_gms_tagmanager_zzcd) {
        super(looper == null ? Looper.getMainLooper() : looper);
        this.mContext = context;
        this.zzbhY = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzagr = looper;
        this.zzbhM = str;
        this.zzbid = i;
        this.zzbie = com_google_android_gms_tagmanager_zzp_zzf;
        this.zzbik = com_google_android_gms_tagmanager_zzp_zze;
        this.zzbif = com_google_android_gms_internal_zzrr;
        this.zzbib = new zzd();
        this.zzbii = new zzj();
        this.zzqW = com_google_android_gms_internal_zzmq;
        this.zzbic = com_google_android_gms_tagmanager_zzcd;
        if (zzGj()) {
            zzfT(zzcb.zzGU().zzGW());
        }
    }

    public zzp(Context context, TagManager tagManager, Looper looper, String str, int i, zzs com_google_android_gms_tagmanager_zzs) {
        this(context, tagManager, looper, str, i, new zzcn(context, str), new zzcm(context, str, com_google_android_gms_tagmanager_zzs), new zzrr(context), zzmt.zzsc(), new zzbe(30, 900000, 5000, "refreshing", zzmt.zzsc()));
        this.zzbif.zzgB(com_google_android_gms_tagmanager_zzs.zzGm());
    }

    private boolean zzGj() {
        zzcb zzGU = zzcb.zzGU();
        return (zzGU.zzGV() == zza.CONTAINER || zzGU.zzGV() == zza.CONTAINER_DEBUG) && this.zzbhM.equals(zzGU.getContainerId());
    }

    private synchronized void zza(zzj com_google_android_gms_internal_zzaf_zzj) {
        if (this.zzbie != null) {
            com.google.android.gms.internal.zzrq.zza com_google_android_gms_internal_zzrq_zza = new com.google.android.gms.internal.zzrq.zza();
            com_google_android_gms_internal_zzrq_zza.zzbmd = this.zzbhR;
            com_google_android_gms_internal_zzrq_zza.zzju = new com.google.android.gms.internal.zzaf.zzf();
            com_google_android_gms_internal_zzrq_zza.zzbme = com_google_android_gms_internal_zzaf_zzj;
            this.zzbie.zzb(com_google_android_gms_internal_zzrq_zza);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void zza(com.google.android.gms.internal.zzaf.zzj r9, long r10, boolean r12) {
        /*
        r8 = this;
        r6 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        monitor-enter(r8);
        if (r12 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r8.zzbih;	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r8);
        return;
    L_0x000c:
        r0 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r8.zzbig;	 Catch:{ all -> 0x006a }
        if (r0 != 0) goto L_0x0016;
    L_0x0016:
        r8.zzbii = r9;	 Catch:{ all -> 0x006a }
        r8.zzbhR = r10;	 Catch:{ all -> 0x006a }
        r0 = 0;
        r2 = 43200000; // 0x2932e00 float:2.1626111E-37 double:2.1343636E-316;
        r4 = r8.zzbhR;	 Catch:{ all -> 0x006a }
        r4 = r4 + r6;
        r6 = r8.zzqW;	 Catch:{ all -> 0x006a }
        r6 = r6.currentTimeMillis();	 Catch:{ all -> 0x006a }
        r4 = r4 - r6;
        r2 = java.lang.Math.min(r2, r4);	 Catch:{ all -> 0x006a }
        r0 = java.lang.Math.max(r0, r2);	 Catch:{ all -> 0x006a }
        r8.zzak(r0);	 Catch:{ all -> 0x006a }
        r0 = new com.google.android.gms.tagmanager.Container;	 Catch:{ all -> 0x006a }
        r1 = r8.mContext;	 Catch:{ all -> 0x006a }
        r2 = r8.zzbhY;	 Catch:{ all -> 0x006a }
        r2 = r2.getDataLayer();	 Catch:{ all -> 0x006a }
        r3 = r8.zzbhM;	 Catch:{ all -> 0x006a }
        r4 = r10;
        r6 = r9;
        r0.<init>(r1, r2, r3, r4, r6);	 Catch:{ all -> 0x006a }
        r1 = r8.zzbig;	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x006d;
    L_0x0049:
        r1 = new com.google.android.gms.tagmanager.zzo;	 Catch:{ all -> 0x006a }
        r2 = r8.zzbhY;	 Catch:{ all -> 0x006a }
        r3 = r8.zzagr;	 Catch:{ all -> 0x006a }
        r4 = r8.zzbib;	 Catch:{ all -> 0x006a }
        r1.<init>(r2, r3, r0, r4);	 Catch:{ all -> 0x006a }
        r8.zzbig = r1;	 Catch:{ all -> 0x006a }
    L_0x0056:
        r1 = r8.isReady();	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x000a;
    L_0x005c:
        r1 = r8.zzbil;	 Catch:{ all -> 0x006a }
        r0 = r1.zzb(r0);	 Catch:{ all -> 0x006a }
        if (r0 == 0) goto L_0x000a;
    L_0x0064:
        r0 = r8.zzbig;	 Catch:{ all -> 0x006a }
        r8.zza(r0);	 Catch:{ all -> 0x006a }
        goto L_0x000a;
    L_0x006a:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x006d:
        r1 = r8.zzbig;	 Catch:{ all -> 0x006a }
        r1.zza(r0);	 Catch:{ all -> 0x006a }
        goto L_0x0056;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzp.zza(com.google.android.gms.internal.zzaf$zzj, long, boolean):void");
    }

    private synchronized void zzak(long j) {
        if (this.zzbik == null) {
            zzbg.zzaK("Refresh requested, but no network load scheduler.");
        } else {
            this.zzbik.zzf(j, this.zzbii.zzjv);
        }
    }

    private void zzaw(final boolean z) {
        this.zzbie.zza(new zzb());
        this.zzbik.zza(new zzc());
        com.google.android.gms.internal.zzrs.zzc zzke = this.zzbie.zzke(this.zzbid);
        if (zzke != null) {
            this.zzbig = new zzo(this.zzbhY, this.zzagr, new Container(this.mContext, this.zzbhY.getDataLayer(), this.zzbhM, 0, zzke), this.zzbib);
        }
        this.zzbil = new zza(this) {
            final /* synthetic */ zzp zzbim;

            public boolean zzb(Container container) {
                return z ? container.getLastRefreshTime() + 43200000 >= this.zzbim.zzqW.currentTimeMillis() : !container.isDefault();
            }
        };
        if (zzGj()) {
            this.zzbik.zzf(0, "");
        } else {
            this.zzbie.zzGl();
        }
    }

    synchronized String zzGd() {
        return this.zzbij;
    }

    public void zzGg() {
        com.google.android.gms.internal.zzrs.zzc zzke = this.zzbie.zzke(this.zzbid);
        if (zzke != null) {
            zza(new zzo(this.zzbhY, this.zzagr, new Container(this.mContext, this.zzbhY.getDataLayer(), this.zzbhM, 0, zzke), new C11652(this)));
        } else {
            String str = "Default was requested, but no default container was found";
            zzbg.m254e(str);
            zza(zzbn(new Status(10, str, null)));
        }
        this.zzbik = null;
        this.zzbie = null;
    }

    public void zzGh() {
        zzaw(false);
    }

    public void zzGi() {
        zzaw(true);
    }

    protected ContainerHolder zzbn(Status status) {
        if (this.zzbig != null) {
            return this.zzbig;
        }
        if (status == Status.zzagF) {
            zzbg.m254e("timer expired: setting result to failure");
        }
        return new zzo(status);
    }

    protected /* synthetic */ Result zzc(Status status) {
        return zzbn(status);
    }

    synchronized void zzfT(String str) {
        this.zzbij = str;
        if (this.zzbik != null) {
            this.zzbik.zzfW(str);
        }
    }
}
