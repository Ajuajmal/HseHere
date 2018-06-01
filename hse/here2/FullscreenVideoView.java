package hse.here2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import java.io.IOException;

public class FullscreenVideoView extends RelativeLayout implements Callback, OnPreparedListener, OnErrorListener, OnSeekCompleteListener, OnCompletionListener, OnInfoListener, OnVideoSizeChangedListener {
    private static final String TAG = "FullscreenVideoView";
    protected Activity activity;
    protected OnCompletionListener completionListener;
    protected Context context;
    protected LayoutParams currentLayoutParams;
    protected State currentState;
    protected boolean detachedByFullscreen;
    protected OnErrorListener errorListener;
    protected boolean fullscreen;
    protected OnInfoListener infoListener;
    protected int initialConfigOrientation;
    protected int initialMovieHeight;
    protected int initialMovieWidth;
    protected State lastState;
    protected MediaPlayer mediaPlayer;
    protected View onProgressView;
    protected ViewGroup parentView;
    protected OnPreparedListener preparedListener;
    protected OnSeekCompleteListener seekCompleteListener;
    protected boolean shouldAutoplay;
    protected SurfaceHolder surfaceHolder;
    protected boolean surfaceIsReady;
    protected SurfaceView surfaceView;
    protected boolean videoIsReady;

    class C06252 implements Runnable {
        C06252() {
        }

        public void run() {
            View currentParent = (View) FullscreenVideoView.this.getParent();
            if (currentParent != null) {
                int newWidth;
                int newHeight;
                float videoProportion = ((float) FullscreenVideoView.this.initialMovieWidth) / ((float) FullscreenVideoView.this.initialMovieHeight);
                int screenWidth = currentParent.getWidth();
                int screenHeight = currentParent.getHeight();
                if (videoProportion > ((float) screenWidth) / ((float) screenHeight)) {
                    newWidth = screenWidth;
                    newHeight = (int) (((float) screenWidth) / videoProportion);
                } else {
                    newWidth = (int) (((float) screenHeight) * videoProportion);
                    newHeight = screenHeight;
                }
                LayoutParams lp = FullscreenVideoView.this.surfaceView.getLayoutParams();
                lp.width = newWidth;
                lp.height = newHeight;
                FullscreenVideoView.this.surfaceView.setLayoutParams(lp);
            }
        }
    }

    public enum State {
        IDLE,
        INITIALIZED,
        PREPARED,
        PREPARING,
        STARTED,
        STOPPED,
        PAUSED,
        PLAYBACKCOMPLETED,
        ERROR,
        END
    }

    public FullscreenVideoView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FullscreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FullscreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        resize();
    }

    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.detachedByFullscreen) {
            if (this.mediaPlayer != null) {
                this.mediaPlayer.setOnPreparedListener(null);
                this.mediaPlayer.setOnErrorListener(null);
                this.mediaPlayer.setOnSeekCompleteListener(null);
                this.mediaPlayer.setOnCompletionListener(null);
                this.mediaPlayer.setOnInfoListener(null);
                if (this.mediaPlayer.isPlaying()) {
                    this.mediaPlayer.stop();
                }
                this.mediaPlayer.release();
                this.mediaPlayer = null;
            }
            this.videoIsReady = false;
            this.surfaceIsReady = false;
            this.currentState = State.END;
        }
        this.detachedByFullscreen = false;
    }

    public synchronized void surfaceCreated(SurfaceHolder holder) {
        this.mediaPlayer.setDisplay(this.surfaceHolder);
        if (!this.surfaceIsReady) {
            this.surfaceIsReady = true;
            if (!(this.currentState == State.PREPARED || this.currentState == State.PAUSED || this.currentState == State.STARTED || this.currentState == State.PLAYBACKCOMPLETED)) {
                tryToPrepare();
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        resize();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
        this.surfaceIsReady = false;
    }

    public synchronized void onPrepared(MediaPlayer mp) {
        this.videoIsReady = true;
        tryToPrepare();
    }

    public void onSeekComplete(MediaPlayer mp) {
        stopLoading();
        if (this.lastState != null) {
            switch (this.lastState) {
                case STARTED:
                    start();
                    break;
                case PLAYBACKCOMPLETED:
                    this.currentState = State.PLAYBACKCOMPLETED;
                    break;
                case PREPARED:
                    this.currentState = State.PREPARED;
                    break;
            }
        }
        if (this.seekCompleteListener != null) {
            this.seekCompleteListener.onSeekComplete(mp);
        }
    }

    public void onCompletion(MediaPlayer mp) {
        if (!(this.mediaPlayer == null || this.currentState == State.ERROR)) {
            if (this.mediaPlayer.isLooping()) {
                start();
            } else {
                this.currentState = State.PLAYBACKCOMPLETED;
            }
        }
        if (this.completionListener != null) {
            this.completionListener.onCompletion(mp);
        }
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
        if (this.infoListener != null) {
            return this.infoListener.onInfo(mediaPlayer, what, extra);
        }
        return false;
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        stopLoading();
        this.currentState = State.ERROR;
        if (this.errorListener != null) {
            return this.errorListener.onError(mp, what, extra);
        }
        return false;
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        if (this.initialMovieWidth == 0 && this.initialMovieHeight == 0) {
            this.initialMovieWidth = width;
            this.initialMovieHeight = height;
            resize();
        }
    }

    protected void init() {
        if (!isInEditMode()) {
            this.shouldAutoplay = true;
            this.currentState = State.IDLE;
            this.fullscreen = false;
            this.initialConfigOrientation = -1;
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
            initObjects();
        }
    }

    protected void initObjects() {
        this.mediaPlayer = new MediaPlayer();
        this.surfaceView = new SurfaceView(this.context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        this.surfaceView.setLayoutParams(layoutParams);
        addView(this.surfaceView);
        this.surfaceHolder = this.surfaceView.getHolder();
        this.surfaceHolder.setType(3);
        this.surfaceHolder.addCallback(this);
        if (this.onProgressView == null) {
            this.onProgressView = new ProgressBar(this.context);
        }
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        this.onProgressView.setLayoutParams(layoutParams);
        addView(this.onProgressView);
    }

    protected void releaseObjects() {
        if (this.surfaceHolder != null) {
            this.surfaceHolder.removeCallback(this);
            this.surfaceHolder = null;
        }
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
        }
        if (this.surfaceView != null) {
            removeView(this.surfaceView);
        }
        if (this.onProgressView != null) {
            removeView(this.onProgressView);
        }
    }

    protected void prepare() throws IllegalStateException {
        startLoading();
        this.videoIsReady = false;
        this.initialMovieHeight = -1;
        this.initialMovieWidth = -1;
        this.mediaPlayer.setOnPreparedListener(this);
        this.mediaPlayer.setOnErrorListener(this);
        this.mediaPlayer.setOnSeekCompleteListener(this);
        this.mediaPlayer.setOnInfoListener(this);
        this.mediaPlayer.setOnVideoSizeChangedListener(this);
        this.mediaPlayer.setAudioStreamType(3);
        this.currentState = State.PREPARING;
        this.mediaPlayer.prepareAsync();
    }

    protected void tryToPrepare() {
        if (this.surfaceIsReady && this.videoIsReady) {
            if (this.mediaPlayer != null) {
                this.initialMovieWidth = this.mediaPlayer.getVideoWidth();
                this.initialMovieHeight = this.mediaPlayer.getVideoHeight();
            }
            resize();
            stopLoading();
            this.currentState = State.PREPARED;
            if (this.shouldAutoplay) {
                start();
            }
            if (this.preparedListener != null) {
                this.preparedListener.onPrepared(this.mediaPlayer);
            }
        }
    }

    protected void startLoading() {
        if (this.onProgressView != null) {
            this.onProgressView.setVisibility(0);
        }
    }

    protected void stopLoading() {
        if (this.onProgressView != null) {
            this.onProgressView.setVisibility(8);
        }
    }

    public synchronized State getCurrentState() {
        return this.currentState;
    }

    public boolean isFullscreen() {
        return this.fullscreen;
    }

    @TargetApi(16)
    public void setFullscreen(boolean fullscreen) throws RuntimeException {
        if (this.mediaPlayer == null) {
            throw new RuntimeException("Media Player is not initialized");
        } else if (this.currentState != State.ERROR) {
            boolean hardware_menu = true;
            if (VERSION.SDK_INT >= 16) {
                hardware_menu = ViewConfiguration.get(getContext()).hasPermanentMenuKey();
            }
            if (!hardware_menu) {
                int uiOptions;
                View decorView = ((Activity) getContext()).getWindow().getDecorView();
                if (fullscreen) {
                    uiOptions = 6;
                } else {
                    uiOptions = 0;
                }
                decorView.setSystemUiVisibility(uiOptions);
            } else if (fullscreen) {
                ((Activity) getContext()).getWindow().setFlags(1024, 1024);
            } else {
                ((Activity) getContext()).getWindow().clearFlags(1024);
            }
            if (this.fullscreen != fullscreen) {
                this.fullscreen = fullscreen;
                final boolean wasPlaying = this.mediaPlayer.isPlaying();
                if (wasPlaying) {
                    pause();
                }
                ViewParent viewParent;
                if (this.fullscreen) {
                    if (this.activity != null) {
                        this.activity.setRequestedOrientation(-1);
                    }
                    View v = getRootView().findViewById(16908290);
                    viewParent = getParent();
                    if (viewParent instanceof ViewGroup) {
                        if (this.parentView == null) {
                            this.parentView = (ViewGroup) viewParent;
                        }
                        this.detachedByFullscreen = true;
                        this.currentLayoutParams = getLayoutParams();
                        this.parentView.removeView(this);
                    }
                    if (v instanceof ViewGroup) {
                        ((ViewGroup) v).addView(this);
                    }
                } else {
                    if (this.activity != null) {
                        this.activity.setRequestedOrientation(this.initialConfigOrientation);
                    }
                    viewParent = getParent();
                    if (viewParent instanceof ViewGroup) {
                        boolean parentHasParent = false;
                        if (!(this.parentView == null || this.parentView.getParent() == null)) {
                            parentHasParent = true;
                            this.detachedByFullscreen = true;
                        }
                        ((ViewGroup) viewParent).removeView(this);
                        if (parentHasParent) {
                            this.parentView.addView(this);
                            setLayoutParams(this.currentLayoutParams);
                        }
                    }
                }
                resize();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (wasPlaying && FullscreenVideoView.this.mediaPlayer != null) {
                            FullscreenVideoView.this.start();
                        }
                    }
                });
            }
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        this.initialConfigOrientation = activity.getRequestedOrientation();
    }

    public void resize() {
        if (this.initialMovieHeight != -1 && this.initialMovieWidth != -1 && this.surfaceView != null) {
            new Handler(Looper.getMainLooper()).post(new C06252());
        }
    }

    public boolean isShouldAutoplay() {
        return this.shouldAutoplay;
    }

    public void setShouldAutoplay(boolean shouldAutoplay) {
        this.shouldAutoplay = shouldAutoplay;
    }

    @Deprecated
    public void fullscreen() throws IllegalStateException {
        setFullscreen(!this.fullscreen);
    }

    public int getCurrentPosition() {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.getCurrentPosition();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public int getDuration() {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.getDuration();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public int getVideoHeight() {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.getVideoHeight();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public int getVideoWidth() {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.getVideoWidth();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public boolean isLooping() {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.isLooping();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public boolean isPlaying() throws IllegalStateException {
        if (this.mediaPlayer != null) {
            return this.mediaPlayer.isPlaying();
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void pause() throws IllegalStateException {
        if (this.mediaPlayer != null) {
            this.currentState = State.PAUSED;
            this.mediaPlayer.pause();
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void reset() {
        if (this.mediaPlayer != null) {
            this.currentState = State.IDLE;
            releaseObjects();
            initObjects();
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void start() throws IllegalStateException {
        if (this.mediaPlayer != null) {
            State currentState_orig = this.currentState;
            this.currentState = State.STARTED;
            this.mediaPlayer.setOnCompletionListener(this);
            this.mediaPlayer.start();
            if (currentState_orig == State.PREPARED) {
                ((config) this.context.getApplicationContext()).toca_int(this.context, false);
                return;
            }
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void stop() throws IllegalStateException {
        if (this.mediaPlayer != null) {
            this.currentState = State.STOPPED;
            this.mediaPlayer.stop();
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void seekTo(int msec) throws IllegalStateException {
        if (this.mediaPlayer == null) {
            throw new RuntimeException("Media Player is not initialized");
        } else if (this.mediaPlayer.getDuration() > -1 && msec <= this.mediaPlayer.getDuration()) {
            this.lastState = this.currentState;
            pause();
            this.mediaPlayer.seekTo(msec);
            startLoading();
        }
    }

    public void setOnCompletionListener(OnCompletionListener l) {
        if (this.mediaPlayer != null) {
            this.completionListener = l;
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnErrorListener(OnErrorListener l) {
        if (this.mediaPlayer != null) {
            this.errorListener = l;
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setOnBufferingUpdateListener(l);
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnInfoListener(OnInfoListener l) {
        if (this.mediaPlayer != null) {
            this.infoListener = l;
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener l) {
        if (this.mediaPlayer != null) {
            this.seekCompleteListener = l;
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener l) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setOnVideoSizeChangedListener(l);
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setOnPreparedListener(OnPreparedListener l) {
        if (this.mediaPlayer != null) {
            this.preparedListener = l;
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setLooping(boolean looping) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setLooping(looping);
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setVolume(float leftVolume, float rightVolume) {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.setVolume(leftVolume, rightVolume);
            return;
        }
        throw new RuntimeException("Media Player is not initialized");
    }

    public void setVideoPath(String path) throws IOException, IllegalStateException, SecurityException, IllegalArgumentException, RuntimeException {
        if (this.mediaPlayer == null) {
            throw new RuntimeException("Media Player is not initialized");
        } else if (this.currentState != State.IDLE) {
            throw new IllegalStateException("FullscreenVideoView Invalid State: " + this.currentState);
        } else {
            this.mediaPlayer.setDataSource(path);
            this.currentState = State.INITIALIZED;
            prepare();
        }
    }

    public void setVideoURI(Uri uri) throws IOException, IllegalStateException, SecurityException, IllegalArgumentException, RuntimeException {
        if (this.mediaPlayer == null) {
            throw new RuntimeException("Media Player is not initialized");
        } else if (this.currentState != State.IDLE) {
            throw new IllegalStateException("FullscreenVideoView Invalid State: " + this.currentState);
        } else {
            this.mediaPlayer.setDataSource(this.context, uri);
            this.currentState = State.INITIALIZED;
            prepare();
        }
    }

    public void setOnProgressView(View v) {
        if (this.onProgressView != null) {
            removeView(this.onProgressView);
        }
        this.onProgressView = v;
        if (this.onProgressView != null) {
            addView(this.onProgressView);
        }
    }
}
