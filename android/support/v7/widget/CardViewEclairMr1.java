package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class CardViewEclairMr1 implements CardViewImpl {
    final RectF sCornerRect = new RectF();

    class C08961 implements RoundRectHelper {
        C08961() {
        }

        public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius, Paint paint) {
            float twoRadius = cornerRadius * 2.0f;
            float innerWidth = (bounds.width() - twoRadius) - TextTrackStyle.DEFAULT_FONT_SCALE;
            float innerHeight = (bounds.height() - twoRadius) - TextTrackStyle.DEFAULT_FONT_SCALE;
            if (cornerRadius >= TextTrackStyle.DEFAULT_FONT_SCALE) {
                float roundedCornerRadius = cornerRadius + 0.5f;
                CardViewEclairMr1.this.sCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius, roundedCornerRadius);
                int saved = canvas.save();
                canvas.translate(bounds.left + roundedCornerRadius, bounds.top + roundedCornerRadius);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, BitmapDescriptorFactory.HUE_CYAN, 90.0f, true, paint);
                canvas.translate(innerWidth, 0.0f);
                canvas.rotate(90.0f);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, BitmapDescriptorFactory.HUE_CYAN, 90.0f, true, paint);
                canvas.translate(innerHeight, 0.0f);
                canvas.rotate(90.0f);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, BitmapDescriptorFactory.HUE_CYAN, 90.0f, true, paint);
                canvas.translate(innerWidth, 0.0f);
                canvas.rotate(90.0f);
                canvas.drawArc(CardViewEclairMr1.this.sCornerRect, BitmapDescriptorFactory.HUE_CYAN, 90.0f, true, paint);
                canvas.restoreToCount(saved);
                canvas.drawRect((bounds.left + roundedCornerRadius) - TextTrackStyle.DEFAULT_FONT_SCALE, bounds.top, TextTrackStyle.DEFAULT_FONT_SCALE + (bounds.right - roundedCornerRadius), bounds.top + roundedCornerRadius, paint);
                canvas.drawRect((bounds.left + roundedCornerRadius) - TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE + (bounds.bottom - roundedCornerRadius), TextTrackStyle.DEFAULT_FONT_SCALE + (bounds.right - roundedCornerRadius), bounds.bottom, paint);
            }
            canvas.drawRect(bounds.left, Math.max(0.0f, cornerRadius - TextTrackStyle.DEFAULT_FONT_SCALE) + bounds.top, bounds.right, TextTrackStyle.DEFAULT_FONT_SCALE + (bounds.bottom - cornerRadius), paint);
        }
    }

    CardViewEclairMr1() {
    }

    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper = new C08961();
    }

    public void initialize(CardViewDelegate cardView, Context context, int backgroundColor, float radius, float elevation, float maxElevation) {
        RoundRectDrawableWithShadow background = createBackground(context, backgroundColor, radius, elevation, maxElevation);
        background.setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        cardView.setBackgroundDrawable(background);
        updatePadding(cardView);
    }

    RoundRectDrawableWithShadow createBackground(Context context, int backgroundColor, float radius, float elevation, float maxElevation) {
        return new RoundRectDrawableWithShadow(context.getResources(), backgroundColor, radius, elevation, maxElevation);
    }

    public void updatePadding(CardViewDelegate cardView) {
        Rect shadowPadding = new Rect();
        getShadowBackground(cardView).getMaxShadowAndCornerPadding(shadowPadding);
        cardView.setMinWidthHeightInternal((int) Math.ceil((double) getMinWidth(cardView)), (int) Math.ceil((double) getMinHeight(cardView)));
        cardView.setShadowPadding(shadowPadding.left, shadowPadding.top, shadowPadding.right, shadowPadding.bottom);
    }

    public void onCompatPaddingChanged(CardViewDelegate cardView) {
    }

    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        getShadowBackground(cardView).setAddPaddingForCorners(cardView.getPreventCornerOverlap());
        updatePadding(cardView);
    }

    public void setBackgroundColor(CardViewDelegate cardView, int color) {
        getShadowBackground(cardView).setColor(color);
    }

    public void setRadius(CardViewDelegate cardView, float radius) {
        getShadowBackground(cardView).setCornerRadius(radius);
        updatePadding(cardView);
    }

    public float getRadius(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getCornerRadius();
    }

    public void setElevation(CardViewDelegate cardView, float elevation) {
        getShadowBackground(cardView).setShadowSize(elevation);
    }

    public float getElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getShadowSize();
    }

    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        getShadowBackground(cardView).setMaxShadowSize(maxElevation);
        updatePadding(cardView);
    }

    public float getMaxElevation(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMaxShadowSize();
    }

    public float getMinWidth(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinWidth();
    }

    public float getMinHeight(CardViewDelegate cardView) {
        return getShadowBackground(cardView).getMinHeight();
    }

    private RoundRectDrawableWithShadow getShadowBackground(CardViewDelegate cardView) {
        return (RoundRectDrawableWithShadow) cardView.getBackground();
    }
}
