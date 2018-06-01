package com.appnext.ads.fullscreen;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class C0166b extends Animation {
    private C0165a bN;
    private float bO;
    private float bP;

    public C0166b(C0165a c0165a, float f) {
        setInterpolator(new LinearInterpolator());
        this.bO = c0165a.m12L();
        this.bP = f;
        this.bN = c0165a;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        this.bN.m13c(this.bO - ((this.bO - this.bP) * f));
        this.bN.invalidate();
        this.bN.requestLayout();
    }
}
