//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wy.showgif;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.TextView;
import java.io.IOException;

public class GifTextView extends TextView {
    public GifTextView(Context context) {
        super(context);
    }

    public GifTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.parseAttrs(attrs);
    }

    public GifTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.parseAttrs(attrs);
    }

    @TargetApi(17)
    private void parseAttrs(AttributeSet attrs) {
        if (attrs != null) {
            Drawable left = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0));
            Drawable right = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableRight", 0));
            Drawable top = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableTop", 0));
            Drawable bottom = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableBottom", 0));
            Drawable start = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableStart", 0));
            Drawable end = this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableEnd", 0));
            this.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
            if (VERSION.SDK_INT >= 17) {
                this.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
            }

            this.setBackgroundInternal(this.getGifOrDefaultDrawable(attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", 0)));
        }

    }

    @TargetApi(16)
    private void setBackgroundInternal(Drawable bg) {
        if (VERSION.SDK_INT >= 16) {
            this.setBackground(bg);
        } else {
            this.setBackgroundDrawable(bg);
        }

    }

    public void setBackgroundResource(int resid) {
        this.setBackgroundInternal(this.getGifOrDefaultDrawable(resid));
    }

    private Drawable getGifOrDefaultDrawable(int resId) {
        if (resId == 0) {
            return null;
        } else {
            Resources resources = this.getResources();
            if (!this.isInEditMode() && "drawable".equals(resources.getResourceTypeName(resId))) {
                try {
                    return new com.wy.showgif.GifDrawable(resources, resId);
                } catch (IOException var4) {
                } catch (NotFoundException var5) {
                }
            }

            return resources.getDrawable(resId);
        }
    }

    @TargetApi(17)
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int start, int top, int end, int bottom) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(this.getGifOrDefaultDrawable(start), this.getGifOrDefaultDrawable(top), this.getGifOrDefaultDrawable(end), this.getGifOrDefaultDrawable(bottom));
    }

    public void setCompoundDrawablesWithIntrinsicBounds(int left, int top, int right, int bottom) {
        this.setCompoundDrawablesWithIntrinsicBounds(this.getGifOrDefaultDrawable(left), this.getGifOrDefaultDrawable(top), this.getGifOrDefaultDrawable(right), this.getGifOrDefaultDrawable(bottom));
    }
}
