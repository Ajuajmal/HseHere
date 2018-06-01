package hse.here2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import hse.here2.FullscreenVideoView.State;
import java.util.Locale;

public class FullscreenVideoLayout extends FullscreenVideoView implements OnClickListener, OnSeekBarChangeListener, OnPreparedListener, OnTouchListener {
    private static final String TAG = "FullscreenVideoLayout";
    protected static final Handler TIME_THREAD = new Handler();
    protected ImageButton imgfullscreen;
    protected ImageButton imgplay;
    protected SeekBar seekBar;
    protected TextView textElapsed;
    protected TextView textTotal;
    protected OnTouchListener touchListener;
    protected Runnable updateTimeRunnable = new C06231();
    protected View videoControlsView;

    class C06231 implements Runnable {
        C06231() {
        }

        public void run() {
            FullscreenVideoLayout.this.updateCounter();
            FullscreenVideoLayout.TIME_THREAD.postDelayed(this, 200);
        }
    }

    public FullscreenVideoLayout(Context context) {
        super(context);
    }

    public FullscreenVideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullscreenVideoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {
        Log.d(TAG, "init");
        super.init();
        if (!isInEditMode()) {
            super.setOnTouchListener(this);
        }
    }

    protected void initObjects() {
        super.initObjects();
        if (this.videoControlsView == null) {
            this.videoControlsView = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(C0627R.layout.view_videocontrols, this, false);
        }
        if (this.videoControlsView != null) {
            LayoutParams params = new LayoutParams(-1, -2);
            params.addRule(12);
            addView(this.videoControlsView, params);
            this.seekBar = (SeekBar) this.videoControlsView.findViewById(C0627R.id.vcv_seekbar);
            this.imgfullscreen = (ImageButton) this.videoControlsView.findViewById(C0627R.id.vcv_img_fullscreen);
            this.imgplay = (ImageButton) this.videoControlsView.findViewById(C0627R.id.vcv_img_play);
            this.textTotal = (TextView) this.videoControlsView.findViewById(C0627R.id.vcv_txt_total);
            this.textElapsed = (TextView) this.videoControlsView.findViewById(C0627R.id.vcv_txt_elapsed);
        }
        if (this.imgplay != null) {
            this.imgplay.setOnClickListener(this);
        }
        if (this.imgfullscreen != null) {
            this.imgfullscreen.setOnClickListener(this);
        }
        if (this.seekBar != null) {
            this.seekBar.setOnSeekBarChangeListener(this);
        }
        if (this.videoControlsView != null) {
            this.videoControlsView.setVisibility(4);
        }
    }

    protected void releaseObjects() {
        super.releaseObjects();
        if (this.videoControlsView != null) {
            removeView(this.videoControlsView);
        }
    }

    protected void startCounter() {
        Log.d(TAG, "startCounter");
        TIME_THREAD.postDelayed(this.updateTimeRunnable, 200);
    }

    protected void stopCounter() {
        Log.d(TAG, "stopCounter");
        TIME_THREAD.removeCallbacks(this.updateTimeRunnable);
    }

    protected void updateCounter() {
        if (this.textElapsed != null) {
            int elapsed = getCurrentPosition();
            if (elapsed > 0 && elapsed < getDuration()) {
                this.seekBar.setProgress(elapsed);
                elapsed = Math.round(((float) elapsed) / 1000.0f);
                long s = (long) (elapsed % 60);
                long m = (long) ((elapsed / 60) % 60);
                if (((long) ((elapsed / 3600) % 24)) > 0) {
                    this.textElapsed.setText(String.format(Locale.US, "%d:%02d:%02d", new Object[]{Long.valueOf(h), Long.valueOf(m), Long.valueOf(s)}));
                    return;
                }
                this.textElapsed.setText(String.format(Locale.US, "%02d:%02d", new Object[]{Long.valueOf(m), Long.valueOf(s)}));
            }
        }
    }

    public void setOnTouchListener(OnTouchListener l) {
        this.touchListener = l;
    }

    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");
        super.onCompletion(mp);
        stopCounter();
        updateControls();
        if (this.currentState != State.ERROR) {
            updateCounter();
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        boolean result = super.onError(mp, what, extra);
        stopCounter();
        updateControls();
        return result;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getCurrentState() == State.END) {
            Log.d(TAG, "onDetachedFromWindow END");
            stopCounter();
        }
    }

    protected void tryToPrepare() {
        Log.d(TAG, "tryToPrepare");
        super.tryToPrepare();
        if ((getCurrentState() == State.PREPARED || getCurrentState() == State.STARTED) && this.textElapsed != null && this.textTotal != null) {
            int total = getDuration();
            if (total > 0) {
                this.seekBar.setMax(total);
                this.seekBar.setProgress(0);
                total /= 1000;
                long s = (long) (total % 60);
                long m = (long) ((total / 60) % 60);
                if (((long) ((total / 3600) % 24)) > 0) {
                    this.textElapsed.setText("00:00:00");
                    this.textTotal.setText(String.format(Locale.US, "%d:%02d:%02d", new Object[]{Long.valueOf(h), Long.valueOf(m), Long.valueOf(s)}));
                    return;
                }
                this.textElapsed.setText("00:00");
                this.textTotal.setText(String.format(Locale.US, "%02d:%02d", new Object[]{Long.valueOf(m), Long.valueOf(s)}));
            }
        }
    }

    public void start() throws IllegalStateException {
        Log.d(TAG, "start");
        if (!isPlaying()) {
            super.start();
            startCounter();
            updateControls();
        }
    }

    public void pause() throws IllegalStateException {
        Log.d(TAG, "pause");
        if (isPlaying()) {
            stopCounter();
            super.pause();
            updateControls();
        }
    }

    public void reset() {
        Log.d(TAG, "reset");
        super.reset();
        stopCounter();
        updateControls();
    }

    public void stop() throws IllegalStateException {
        Log.d(TAG, "stop");
        super.stop();
        stopCounter();
        updateControls();
    }

    protected void updateControls() {
        if (this.imgplay != null) {
            Drawable icon;
            if (getCurrentState() == State.STARTED) {
                icon = this.context.getResources().getDrawable(C0627R.drawable.fvl_selector_pause);
            } else {
                icon = this.context.getResources().getDrawable(C0627R.drawable.fvl_selector_play);
            }
            this.imgplay.setBackgroundDrawable(icon);
        }
    }

    public void hideControlsStream() {
        if (this.videoControlsView != null) {
            this.videoControlsView.findViewById(C0627R.id.vcv_txt_elapsed).setVisibility(4);
            this.videoControlsView.findViewById(C0627R.id.vcv_txt_total).setVisibility(4);
            this.videoControlsView.findViewById(C0627R.id.vcv_seekbar).setVisibility(4);
        }
    }

    public void hideControls() {
        Log.d(TAG, "hideControls");
        if (this.videoControlsView != null) {
            this.videoControlsView.setVisibility(4);
        }
    }

    public void showControls() {
        Log.d(TAG, "showControls");
        if (this.videoControlsView != null) {
            this.videoControlsView.setVisibility(0);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == 0 && this.videoControlsView != null) {
            if (this.videoControlsView.getVisibility() == 0) {
                hideControls();
            } else {
                showControls();
            }
        }
        if (this.touchListener != null) {
            return this.touchListener.onTouch(this, event);
        }
        return false;
    }

    public void onClick(View v) {
        if (v.getId() != C0627R.id.vcv_img_play) {
            if (!isFullscreen()) {
                hideControls();
            }
            setFullscreen(!isFullscreen());
        } else if (isPlaying()) {
            pause();
        } else {
            start();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d(TAG, "onProgressChanged " + progress);
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        stopCounter();
        Log.d(TAG, "onStartTrackingTouch");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        seekTo(seekBar.getProgress());
        Log.d(TAG, "onStopTrackingTouch");
    }
}
