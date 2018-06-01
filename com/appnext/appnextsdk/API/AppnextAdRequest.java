package com.appnext.appnextsdk.API;

import android.util.Log;
import java.net.URLEncoder;

public class AppnextAdRequest {
    private String category = "";
    private int count = 1;
    private String postback = "";

    public AppnextAdRequest setPostback(String str) {
        String encode;
        if (str == null) {
            str = "";
        }
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (Throwable th) {
            Log.e("appnext native ads SDK", "postback text encoding error, please check your parameters.");
            encode = "";
        }
        this.postback = encode;
        return this;
    }

    public AppnextAdRequest setCategory(String str) {
        String encode;
        if (str == null) {
            str = "";
        }
        try {
            encode = URLEncoder.encode(str, "UTF-8");
        } catch (Throwable th) {
            Log.e("appnext native ads SDK", "categoty text encoding error, please check your parameters.");
            encode = "";
        }
        this.category = encode;
        return this;
    }

    public AppnextAdRequest setCount(int i) {
        if (i < 1) {
            i = 1;
        }
        this.count = i;
        return this;
    }

    public String getPostback() {
        return this.postback;
    }

    public int getCount() {
        return this.count;
    }

    public String getCategory() {
        return this.category;
    }
}
