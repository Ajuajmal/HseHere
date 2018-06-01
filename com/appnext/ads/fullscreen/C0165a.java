package com.appnext.ads.fullscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.appnext.core.C0266f;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

@SuppressLint({"ViewConstructor"})
public class C0165a extends View {
    private static final int bJ = 180;
    private final Paint bK = new Paint();
    private final RectF bL;
    private float bM;

    public C0165a(Context context, AttributeSet attributeSet, String str) {
        super(context, attributeSet);
        float a = (float) C0165a.m11a(context, 2.0f);
        this.bK.setAntiAlias(true);
        this.bK.setStyle(Style.STROKE);
        this.bK.setStrokeWidth(a);
        this.bK.setColor(Color.parseColor(str));
        this.bK.setShadowLayer(2.0f, 2.0f, 2.0f, Color.argb(128, 0, 0, 0));
        setLayerType(1, this.bK);
        this.bL = new RectF(a, a, ((float) C0165a.m11a(context, 27.0f)) + a, ((float) C0165a.m11a(context, 27.0f)) + a);
        this.bM = 360.0f;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.bL, BitmapDescriptorFactory.HUE_CYAN, this.bM, false, this.bK);
    }

    public float m12L() {
        return this.bM;
    }

    public void m13c(float f) {
        this.bM = f;
    }

    private static int m11a(Context context, float f) {
        return C0266f.m195a(context, f);
    }
}
