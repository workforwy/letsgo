//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wy.showgif;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.MediaController.MediaPlayerControl;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;

public class GifDrawable extends Drawable implements Animatable, MediaPlayerControl {
    private static final Handler UI_HANDLER;
    private volatile int mGifInfoPtr;
    private volatile boolean mIsRunning;
    private final int[] mMetaData;
    private final long mInputSourceLength;
    private float mSx;
    private float mSy;
    private boolean mApplyTransformation;
    private final Rect mDstRect;
    protected final Paint mPaint;
    private int[] mColors;
    private final Runnable mResetTask;
    private final Runnable mStartTask;
    private final Runnable mSaveRemainderTask;
    private final Runnable mInvalidateTask;

    private static native void renderFrame(int[] var0, int var1, int[] var2);

    private static native int openFd(int[] var0, FileDescriptor var1, long var2) throws GifIOException;

    private static native int openByteArray(int[] var0, byte[] var1) throws GifIOException;

    private static native int openDirectByteBuffer(int[] var0, ByteBuffer var1) throws GifIOException;

    private static native int openStream(int[] var0, InputStream var1) throws GifIOException;

    private static native int openFile(int[] var0, String var1) throws GifIOException;

    private static native void free(int var0);

    private static native boolean reset(int var0);

    private static native void setSpeedFactor(int var0, float var1);

    private static native String getComment(int var0);

    private static native int getLoopCount(int var0);

    private static native int getDuration(int var0);

    private static native int getCurrentPosition(int var0);

    private static native int seekToTime(int var0, int var1, int[] var2);

    private static native int seekToFrame(int var0, int var1, int[] var2);

    private static native int saveRemainder(int var0);

    private static native int restoreRemainder(int var0);

    private static native long getAllocationByteCount(int var0);

    private static void runOnUiThread(Runnable task) {
        if (Looper.myLooper() == UI_HANDLER.getLooper()) {
            task.run();
        } else {
            UI_HANDLER.post(task);
        }

    }

    public GifDrawable(Resources res, int id) throws NotFoundException, IOException {
        this(res.openRawResourceFd(id));
    }

    public GifDrawable(AssetManager assets, String assetName) throws IOException {
        this(assets.openFd(assetName));
    }

    public GifDrawable(String filePath) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (filePath == null) {
            throw new NullPointerException("Source is null");
        } else {
            this.mInputSourceLength = (new File(filePath)).length();
            this.mGifInfoPtr = openFile(this.mMetaData, filePath);
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
        }
    }

    public GifDrawable(File file) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (file == null) {
            throw new NullPointerException("Source is null");
        } else {
            this.mInputSourceLength = file.length();
            this.mGifInfoPtr = openFile(this.mMetaData, file.getPath());
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
        }
    }

    public GifDrawable(InputStream stream) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (stream == null) {
            throw new NullPointerException("Source is null");
        } else if (!stream.markSupported()) {
            throw new IllegalArgumentException("InputStream does not support marking");
        } else {
            this.mGifInfoPtr = openStream(this.mMetaData, stream);
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
            this.mInputSourceLength = -1L;
        }
    }

    public GifDrawable(AssetFileDescriptor afd) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (afd == null) {
            throw new NullPointerException("Source is null");
        } else {
            FileDescriptor fd = afd.getFileDescriptor();

            try {
                this.mGifInfoPtr = openFd(this.mMetaData, fd, afd.getStartOffset());
            } catch (IOException var4) {
                afd.close();
                throw var4;
            }

            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
            this.mInputSourceLength = afd.getLength();
        }
    }

    public GifDrawable(FileDescriptor fd) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (fd == null) {
            throw new NullPointerException("Source is null");
        } else {
            this.mGifInfoPtr = openFd(this.mMetaData, fd, 0L);
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
            this.mInputSourceLength = -1L;
        }
    }

    public GifDrawable(byte[] bytes) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();
        if (bytes == null) {
            throw new NullPointerException("Source is null");
        } else {
            this.mGifInfoPtr = openByteArray(this.mMetaData, bytes);
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
            this.mInputSourceLength = (long) bytes.length;
        }
    }

    class NamelessClass_1 implements Runnable {
        NamelessClass_1() {
        }

        public void run() {
            GifDrawable.this.invalidateSelf();
        }
    }

    class NamelessClass_2 implements Runnable {
        NamelessClass_2() {
        }

        public void run() {
            GifDrawable.saveRemainder(GifDrawable.this.mGifInfoPtr);
        }
    }

    class NamelessClass_3 implements Runnable {
        NamelessClass_3() {
        }

        public void run() {
            GifDrawable.restoreRemainder(GifDrawable.this.mGifInfoPtr);
            GifDrawable.this.invalidateSelf();
        }
    }

    class NamelessClass_4 implements Runnable {
        NamelessClass_4() {
        }

        public void run() {
            GifDrawable.reset(GifDrawable.this.mGifInfoPtr);
        }
    }

    public GifDrawable(ByteBuffer buffer) throws IOException {
        this.mIsRunning = true;
        this.mMetaData = new int[5];
        this.mSx = 1.0F;
        this.mSy = 1.0F;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);

        this.mResetTask = new NamelessClass_4();
        this.mStartTask = new NamelessClass_3();
        this.mSaveRemainderTask = new NamelessClass_2();
        this.mInvalidateTask = new NamelessClass_1();

        if (buffer == null) {
            throw new NullPointerException("Source is null");
        } else if (!buffer.isDirect()) {
            throw new IllegalArgumentException("ByteBuffer is not direct");
        } else {
            this.mGifInfoPtr = openDirectByteBuffer(this.mMetaData, buffer);
            this.mColors = new int[this.mMetaData[0] * this.mMetaData[1]];
            this.mInputSourceLength = (long) buffer.capacity();
        }
    }

    public GifDrawable(ContentResolver resolver, Uri uri) throws IOException {
        this(resolver.openAssetFileDescriptor(uri, "r"));
    }

    public void recycle() {
        this.mIsRunning = false;
        int tmpPtr = this.mGifInfoPtr;
        this.mGifInfoPtr = 0;
        this.mColors = null;
        free(tmpPtr);
    }

    protected void finalize() throws Throwable {
        try {
            this.recycle();
        } finally {
            super.finalize();
        }

    }

    public int getIntrinsicHeight() {
        return this.mMetaData[1];
    }

    public int getIntrinsicWidth() {
        return this.mMetaData[0];
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    @SuppressLint("WrongConstant")
    public int getOpacity() {
        return -2;
    }

    public void start() {
        this.mIsRunning = true;
        runOnUiThread(this.mStartTask);
    }

    public void reset() {
        runOnUiThread(this.mResetTask);
    }

    public void stop() {
        this.mIsRunning = false;
        runOnUiThread(this.mSaveRemainderTask);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public String getComment() {
        return getComment(this.mGifInfoPtr);
    }

    public int getLoopCount() {
        return getLoopCount(this.mGifInfoPtr);
    }

    public String toString() {
        return String.format(Locale.US, "Size: %dx%d, %d frames, error: %d", this.mMetaData[0], this.mMetaData[1], this.mMetaData[2], this.mMetaData[3]);
    }

    public int getNumberOfFrames() {
        return this.mMetaData[2];
    }

    public GifError getError() {
        return GifError.fromCode(this.mMetaData[3]);
    }

    public static GifDrawable createFromResource(Resources res, int resourceId) {
        try {
            return new GifDrawable(res, resourceId);
        } catch (IOException var3) {
            return null;
        }
    }

    public void setSpeed(float factor) {
        if (factor <= 0.0F) {
            throw new IllegalArgumentException("Speed factor is not positive");
        } else {
            setSpeedFactor(this.mGifInfoPtr, factor);
        }
    }

    public void pause() {
        this.stop();
    }

    public int getDuration() {
        return getDuration(this.mGifInfoPtr);
    }

    public int getCurrentPosition() {
        return getCurrentPosition(this.mGifInfoPtr);
    }

    public void seekTo(final int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position is not positive");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    GifDrawable.seekToTime(GifDrawable.this.mGifInfoPtr, position, GifDrawable.this.mColors);
                    GifDrawable.this.invalidateSelf();
                }
            });
        }
    }

    public void seekToFrame(final int frameIndex) {
        if (frameIndex < 0) {
            throw new IllegalArgumentException("frameIndex is not positive");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    GifDrawable.seekToFrame(GifDrawable.this.mGifInfoPtr, frameIndex, GifDrawable.this.mColors);
                    GifDrawable.this.invalidateSelf();
                }
            });
        }
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public int getBufferPercentage() {
        return 100;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return false;
    }

    public boolean canSeekForward() {
        return this.getNumberOfFrames() > 1;
    }

    public int getAudioSessionId() {
        return 0;
    }

    public int getFrameByteCount() {
        return this.mMetaData[0] * this.mMetaData[1] * 4;
    }

    public long getAllocationByteCount() {
        long nativeSize = getAllocationByteCount(this.mGifInfoPtr);
        int[] colors = this.mColors;
        return colors == null ? nativeSize : nativeSize + (long) colors.length * 4L;
    }

    public long getInputSourceByteCount() {
        return this.mInputSourceLength;
    }

    public void getPixels(int[] pixels) {
        int[] colors = this.mColors;
        if (colors != null) {
            if (pixels.length < colors.length) {
                throw new ArrayIndexOutOfBoundsException("Pixels array is too small. Required length: " + colors.length);
            } else {
                System.arraycopy(colors, 0, pixels, 0, colors.length);
            }
        }
    }

    public int getPixel(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("x must be >= 0");
        } else if (y < 0) {
            throw new IllegalArgumentException("y must be >= 0");
        } else if (x >= this.mMetaData[0]) {
            throw new IllegalArgumentException("x must be < GIF width");
        } else if (y >= this.mMetaData[1]) {
            throw new IllegalArgumentException("y must be < GIF height");
        } else {
            int[] colors = this.mColors;
            if (colors == null) {
                throw new IllegalArgumentException("GifDrawable is recycled");
            } else {
                return colors[this.mMetaData[1] * y + x];
            }
        }
    }

    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mApplyTransformation = true;
    }

    public void draw(Canvas canvas) {
        if (this.mApplyTransformation) {
            this.mDstRect.set(this.getBounds());
            this.mSx = (float) this.mDstRect.width() / (float) this.mMetaData[0];
            this.mSy = (float) this.mDstRect.height() / (float) this.mMetaData[1];
            this.mApplyTransformation = false;
        }

        if (this.mPaint.getShader() == null) {
            if (this.mIsRunning) {
                renderFrame(this.mColors, this.mGifInfoPtr, this.mMetaData);
            } else {
                this.mMetaData[4] = -1;
            }

            canvas.scale(this.mSx, this.mSy);
            int[] colors = this.mColors;
            if (colors != null) {
                canvas.drawBitmap(colors, 0, this.mMetaData[0], 0.0F, 0.0F, this.mMetaData[0], this.mMetaData[1], true, this.mPaint);
            }

            if (this.mMetaData[4] >= 0 && this.mMetaData[2] > 1) {
                UI_HANDLER.postDelayed(this.mInvalidateTask, (long) this.mMetaData[4]);
            }
        } else {
            canvas.drawRect(this.mDstRect, this.mPaint);
        }

    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setFilterBitmap(boolean filter) {
        this.mPaint.setFilterBitmap(filter);
        this.invalidateSelf();
    }

    public void setDither(boolean dither) {
        this.mPaint.setDither(dither);
        this.invalidateSelf();
    }

    public int getMinimumHeight() {
        return this.mMetaData[1];
    }

    public int getMinimumWidth() {
        return this.mMetaData[0];
    }

    static {
        System.loadLibrary("gif");
        UI_HANDLER = new Handler(Looper.getMainLooper());
    }
}
