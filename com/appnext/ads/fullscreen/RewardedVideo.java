package com.appnext.ads.fullscreen;

import android.content.Context;
import com.appnext.core.C0280o;

public class RewardedVideo extends Video {
    private RewardedServerSidePostback rewardedServerSidePostback;

    public RewardedVideo(Context context, String str) {
        super(context, 2, str);
    }

    public String getAUID() {
        return "800";
    }

    public RewardedVideo(Context context, String str, RewardedConfig rewardedConfig) {
        super(context, 2, str, rewardedConfig);
    }

    protected C0280o getConfig() {
        return C0916f.af();
    }

    protected RewardedServerSidePostback getRewardedServerSidePostback() {
        if (getRewardsTransactionId().equals("") && getRewardsUserId().equals("") && getRewardsRewardTypeCurrency().equals("") && getRewardsAmountRewarded().equals("") && getRewardsCustomParameter().equals("")) {
            return null;
        }
        return this.rewardedServerSidePostback;
    }

    protected void setRewardedServerSidePostback(RewardedServerSidePostback rewardedServerSidePostback) {
        this.rewardedServerSidePostback = rewardedServerSidePostback;
    }

    public void setRewardedServerSidePostback(String str, String str2, String str3, String str4, String str5) {
        setRewardsTransactionId(str);
        setRewardsUserId(str2);
        setRewardsRewardTypeCurrency(str3);
        setRewardsAmountRewarded(str4);
        setRewardsCustomParameter(str5);
    }

    public String getRewardsTransactionId() {
        if (this.rewardedServerSidePostback == null) {
            return "";
        }
        return this.rewardedServerSidePostback.getRewardsTransactionId();
    }

    public void setRewardsTransactionId(String str) {
        if (this.rewardedServerSidePostback == null) {
            this.rewardedServerSidePostback = new RewardedServerSidePostback();
        }
        this.rewardedServerSidePostback.setRewardsTransactionId(str);
    }

    public String getRewardsUserId() {
        if (this.rewardedServerSidePostback == null) {
            return "";
        }
        return this.rewardedServerSidePostback.getRewardsUserId();
    }

    public void setRewardsUserId(String str) {
        if (this.rewardedServerSidePostback == null) {
            this.rewardedServerSidePostback = new RewardedServerSidePostback();
        }
        this.rewardedServerSidePostback.setRewardsUserId(str);
    }

    public String getRewardsRewardTypeCurrency() {
        if (this.rewardedServerSidePostback == null) {
            return "";
        }
        return this.rewardedServerSidePostback.getRewardsRewardTypeCurrency();
    }

    public void setRewardsRewardTypeCurrency(String str) {
        if (this.rewardedServerSidePostback == null) {
            this.rewardedServerSidePostback = new RewardedServerSidePostback();
        }
        this.rewardedServerSidePostback.setRewardsRewardTypeCurrency(str);
    }

    public String getRewardsAmountRewarded() {
        if (this.rewardedServerSidePostback == null) {
            return "";
        }
        return this.rewardedServerSidePostback.getRewardsAmountRewarded();
    }

    public void setRewardsAmountRewarded(String str) {
        if (this.rewardedServerSidePostback == null) {
            this.rewardedServerSidePostback = new RewardedServerSidePostback();
        }
        this.rewardedServerSidePostback.setRewardsAmountRewarded(str);
    }

    public String getRewardsCustomParameter() {
        if (this.rewardedServerSidePostback == null) {
            return "";
        }
        return this.rewardedServerSidePostback.getRewardsCustomParameter();
    }

    public void setRewardsCustomParameter(String str) {
        if (this.rewardedServerSidePostback == null) {
            this.rewardedServerSidePostback = new RewardedServerSidePostback();
        }
        this.rewardedServerSidePostback.setRewardsCustomParameter(str);
    }
}
