package com.appnext.ads.fullscreen;

import java.io.Serializable;
import java.util.HashMap;

public class RewardedServerSidePostback implements Serializable {
    private static final long serialVersionUID = 1;
    private String cF = "";
    private String cG = "";
    private String cH = "";
    private String cI = "";
    private String cJ = "";

    public RewardedServerSidePostback(String str, String str2, String str3, String str4, String str5) {
        this.cF = str;
        this.cG = str2;
        this.cH = str3;
        this.cI = str4;
        this.cJ = str5;
    }

    public String getRewardsTransactionId() {
        return this.cF;
    }

    public void setRewardsTransactionId(String str) {
        this.cF = str;
    }

    public String getRewardsUserId() {
        return this.cG;
    }

    public void setRewardsUserId(String str) {
        this.cG = str;
    }

    public String getRewardsRewardTypeCurrency() {
        return this.cH;
    }

    public void setRewardsRewardTypeCurrency(String str) {
        this.cH = str;
    }

    public String getRewardsAmountRewarded() {
        return this.cI;
    }

    public void setRewardsAmountRewarded(String str) {
        this.cI = str;
    }

    public String getRewardsCustomParameter() {
        return this.cJ;
    }

    public void setRewardsCustomParameter(String str) {
        this.cJ = str;
    }

    protected HashMap<String, String> ae() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("rewardsTransactionId", this.cF);
        hashMap.put("rewardsUserId", this.cG);
        hashMap.put("rewardsRewardTypeCurrency", this.cH);
        hashMap.put("rewardsAmountRewarded", this.cI);
        hashMap.put("rewardsCustomParameter", this.cJ);
        return hashMap;
    }
}
