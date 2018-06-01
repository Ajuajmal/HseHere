package com.google.android.gms.internal;

import android.os.Handler;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzk;
import com.google.android.gms.ads.internal.zzr;
import java.util.LinkedList;
import java.util.List;

@zzhb
class zzdw {
    private final List<zza> zzpH = new LinkedList();

    interface zza {
        void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException;
    }

    class C12631 extends com.google.android.gms.ads.internal.client.zzq.zza {
        final /* synthetic */ zzdw zzAc;

        class C10621 implements zza {
            final /* synthetic */ C12631 zzAd;

            C10621(C12631 c12631) {
                this.zzAd = c12631;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzpK != null) {
                    com_google_android_gms_internal_zzdx.zzpK.onAdClosed();
                }
                zzr.zzbN().zzee();
            }
        }

        class C10643 implements zza {
            final /* synthetic */ C12631 zzAd;

            C10643(C12631 c12631) {
                this.zzAd = c12631;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzpK != null) {
                    com_google_android_gms_internal_zzdx.zzpK.onAdLeftApplication();
                }
            }
        }

        class C10654 implements zza {
            final /* synthetic */ C12631 zzAd;

            C10654(C12631 c12631) {
                this.zzAd = c12631;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzpK != null) {
                    com_google_android_gms_internal_zzdx.zzpK.onAdLoaded();
                }
            }
        }

        class C10665 implements zza {
            final /* synthetic */ C12631 zzAd;

            C10665(C12631 c12631) {
                this.zzAd = c12631;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzpK != null) {
                    com_google_android_gms_internal_zzdx.zzpK.onAdOpened();
                }
            }
        }

        C12631(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void onAdClosed() throws RemoteException {
            this.zzAc.zzpH.add(new C10621(this));
        }

        public void onAdFailedToLoad(final int errorCode) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12631 zzAd;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzpK != null) {
                        com_google_android_gms_internal_zzdx.zzpK.onAdFailedToLoad(errorCode);
                    }
                }
            });
            zzin.m468v("Pooled interstitial failed to load.");
        }

        public void onAdLeftApplication() throws RemoteException {
            this.zzAc.zzpH.add(new C10643(this));
        }

        public void onAdLoaded() throws RemoteException {
            this.zzAc.zzpH.add(new C10654(this));
            zzin.m468v("Pooled interstitial loaded.");
        }

        public void onAdOpened() throws RemoteException {
            this.zzAc.zzpH.add(new C10665(this));
        }
    }

    class C12642 extends com.google.android.gms.ads.internal.client.zzw.zza {
        final /* synthetic */ zzdw zzAc;

        C12642(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void onAppEvent(final String name, final String info) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12642 zzAg;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzAq != null) {
                        com_google_android_gms_internal_zzdx.zzAq.onAppEvent(name, info);
                    }
                }
            });
        }
    }

    class C12653 extends com.google.android.gms.internal.zzgd.zza {
        final /* synthetic */ zzdw zzAc;

        C12653(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void zza(final zzgc com_google_android_gms_internal_zzgc) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12653 zzAi;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzAr != null) {
                        com_google_android_gms_internal_zzdx.zzAr.zza(com_google_android_gms_internal_zzgc);
                    }
                }
            });
        }
    }

    class C12664 extends com.google.android.gms.internal.zzcf.zza {
        final /* synthetic */ zzdw zzAc;

        C12664(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void zza(final zzce com_google_android_gms_internal_zzce) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12664 zzAk;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzAs != null) {
                        com_google_android_gms_internal_zzdx.zzAs.zza(com_google_android_gms_internal_zzce);
                    }
                }
            });
        }
    }

    class C12675 extends com.google.android.gms.ads.internal.client.zzp.zza {
        final /* synthetic */ zzdw zzAc;

        class C10701 implements zza {
            final /* synthetic */ C12675 zzAl;

            C10701(C12675 c12675) {
                this.zzAl = c12675;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAt != null) {
                    com_google_android_gms_internal_zzdx.zzAt.onAdClicked();
                }
            }
        }

        C12675(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void onAdClicked() throws RemoteException {
            this.zzAc.zzpH.add(new C10701(this));
        }
    }

    class C12686 extends com.google.android.gms.ads.internal.reward.client.zzd.zza {
        final /* synthetic */ zzdw zzAc;

        class C10711 implements zza {
            final /* synthetic */ C12686 zzAm;

            C10711(C12686 c12686) {
                this.zzAm = c12686;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAu != null) {
                    com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoAdLoaded();
                }
            }
        }

        class C10722 implements zza {
            final /* synthetic */ C12686 zzAm;

            C10722(C12686 c12686) {
                this.zzAm = c12686;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAu != null) {
                    com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoAdOpened();
                }
            }
        }

        class C10733 implements zza {
            final /* synthetic */ C12686 zzAm;

            C10733(C12686 c12686) {
                this.zzAm = c12686;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAu != null) {
                    com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoStarted();
                }
            }
        }

        class C10744 implements zza {
            final /* synthetic */ C12686 zzAm;

            C10744(C12686 c12686) {
                this.zzAm = c12686;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAu != null) {
                    com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoAdClosed();
                }
            }
        }

        class C10766 implements zza {
            final /* synthetic */ C12686 zzAm;

            C10766(C12686 c12686) {
                this.zzAm = c12686;
            }

            public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                if (com_google_android_gms_internal_zzdx.zzAu != null) {
                    com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoAdLeftApplication();
                }
            }
        }

        C12686(zzdw com_google_android_gms_internal_zzdw) {
            this.zzAc = com_google_android_gms_internal_zzdw;
        }

        public void onRewardedVideoAdClosed() throws RemoteException {
            this.zzAc.zzpH.add(new C10744(this));
        }

        public void onRewardedVideoAdFailedToLoad(final int errorCode) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12686 zzAm;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzAu != null) {
                        com_google_android_gms_internal_zzdx.zzAu.onRewardedVideoAdFailedToLoad(errorCode);
                    }
                }
            });
        }

        public void onRewardedVideoAdLeftApplication() throws RemoteException {
            this.zzAc.zzpH.add(new C10766(this));
        }

        public void onRewardedVideoAdLoaded() throws RemoteException {
            this.zzAc.zzpH.add(new C10711(this));
        }

        public void onRewardedVideoAdOpened() throws RemoteException {
            this.zzAc.zzpH.add(new C10722(this));
        }

        public void onRewardedVideoStarted() throws RemoteException {
            this.zzAc.zzpH.add(new C10733(this));
        }

        public void zza(final com.google.android.gms.ads.internal.reward.client.zza com_google_android_gms_ads_internal_reward_client_zza) throws RemoteException {
            this.zzAc.zzpH.add(new zza(this) {
                final /* synthetic */ C12686 zzAm;

                public void zzb(zzdx com_google_android_gms_internal_zzdx) throws RemoteException {
                    if (com_google_android_gms_internal_zzdx.zzAu != null) {
                        com_google_android_gms_internal_zzdx.zzAu.zza(com_google_android_gms_ads_internal_reward_client_zza);
                    }
                }
            });
        }
    }

    zzdw() {
    }

    void zza(final zzdx com_google_android_gms_internal_zzdx) {
        Handler handler = zzir.zzMc;
        for (final zza com_google_android_gms_internal_zzdw_zza : this.zzpH) {
            handler.post(new Runnable(this) {
                final /* synthetic */ zzdw zzAc;

                public void run() {
                    try {
                        com_google_android_gms_internal_zzdw_zza.zzb(com_google_android_gms_internal_zzdx);
                    } catch (Throwable e) {
                        zzb.zzd("Could not propagate interstitial ad event.", e);
                    }
                }
            });
        }
    }

    void zzc(zzk com_google_android_gms_ads_internal_zzk) {
        com_google_android_gms_ads_internal_zzk.zza(new C12631(this));
        com_google_android_gms_ads_internal_zzk.zza(new C12642(this));
        com_google_android_gms_ads_internal_zzk.zza(new C12653(this));
        com_google_android_gms_ads_internal_zzk.zza(new C12664(this));
        com_google_android_gms_ads_internal_zzk.zza(new C12675(this));
        com_google_android_gms_ads_internal_zzk.zza(new C12686(this));
    }
}
