package com.google.android.gms.gcm;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import org.apache.http.impl.client.cache.CacheConfig;

public class PeriodicTask extends Task {
    public static final Creator<PeriodicTask> CREATOR = new C04151();
    protected long mFlexInSeconds;
    protected long mIntervalInSeconds;

    static class C04151 implements Creator<PeriodicTask> {
        C04151() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzeK(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzhh(i);
        }

        public PeriodicTask zzeK(Parcel parcel) {
            return new PeriodicTask(parcel);
        }

        public PeriodicTask[] zzhh(int i) {
            return new PeriodicTask[i];
        }
    }

    public static class Builder extends com.google.android.gms.gcm.Task.Builder {
        private long zzaMa;
        private long zzaMb;

        public Builder() {
            this.zzaMa = -1;
            this.zzaMb = -1;
            this.isPersisted = true;
        }

        public PeriodicTask build() {
            checkConditions();
            return new PeriodicTask();
        }

        protected void checkConditions() {
            super.checkConditions();
            if (this.zzaMa == -1) {
                throw new IllegalArgumentException("Must call setPeriod(long) to establish an execution interval for this periodic task.");
            } else if (this.zzaMa <= 0) {
                throw new IllegalArgumentException("Period set cannot be less or equal to 0: " + this.zzaMa);
            } else if (this.zzaMb == -1) {
                this.zzaMb = (long) (((float) this.zzaMa) * CacheConfig.DEFAULT_HEURISTIC_COEFFICIENT);
            } else if (this.zzaMb > this.zzaMa) {
                this.zzaMb = this.zzaMa;
            }
        }

        public Builder setExtras(Bundle extras) {
            this.extras = extras;
            return this;
        }

        public Builder setFlex(long flexInSeconds) {
            this.zzaMb = flexInSeconds;
            return this;
        }

        public Builder setPeriod(long periodInSeconds) {
            this.zzaMa = periodInSeconds;
            return this;
        }

        public Builder setPersisted(boolean isPersisted) {
            this.isPersisted = isPersisted;
            return this;
        }

        public Builder setRequiredNetwork(int requiredNetworkState) {
            this.requiredNetworkState = requiredNetworkState;
            return this;
        }

        public Builder setRequiresCharging(boolean requiresCharging) {
            this.requiresCharging = requiresCharging;
            return this;
        }

        public Builder setService(Class<? extends GcmTaskService> gcmTaskService) {
            this.gcmTaskService = gcmTaskService.getName();
            return this;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setUpdateCurrent(boolean updateCurrent) {
            this.updateCurrent = updateCurrent;
            return this;
        }
    }

    @Deprecated
    private PeriodicTask(Parcel in) {
        super(in);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = in.readLong();
        this.mFlexInSeconds = Math.min(in.readLong(), this.mIntervalInSeconds);
    }

    private PeriodicTask(Builder builder) {
        super((com.google.android.gms.gcm.Task.Builder) builder);
        this.mIntervalInSeconds = -1;
        this.mFlexInSeconds = -1;
        this.mIntervalInSeconds = builder.zzaMa;
        this.mFlexInSeconds = Math.min(builder.zzaMb, this.mIntervalInSeconds);
    }

    public long getFlex() {
        return this.mFlexInSeconds;
    }

    public long getPeriod() {
        return this.mIntervalInSeconds;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putLong("period", this.mIntervalInSeconds);
        bundle.putLong("period_flex", this.mFlexInSeconds);
    }

    public String toString() {
        return super.toString() + " " + "period=" + getPeriod() + " " + "flex=" + getFlex();
    }

    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeLong(this.mIntervalInSeconds);
        parcel.writeLong(this.mFlexInSeconds);
    }
}
