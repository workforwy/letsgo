//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wy.showgif;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.ImageButton;

import java.io.IOException;

public class GifImageButton extends ImageButton {
    public GifImageButton(Context context) {
        super(context);
    }

    public GifImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.trySetGifDrawable(attrs, this.getResources());
    }

    public GifImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.trySetGifDrawable(attrs, this.getResources());
    }

    public void setImageResource(int resId) {
        this.setResource(true, resId, this.getResources());
    }

    public void setBackgroundResource(int resId) {
        this.setResource(false, resId, this.getResources());
    }

    void trySetGifDrawable(AttributeSet attrs, Resources res) {
        if (attrs != null && res != null && !this.isInEditMode()) {
            int resId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", -1);
            if (resId > 0 && "drawable".equals(res.getResourceTypeName(resId))) {
                this.setResource(true, resId, res);
            }

            resId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "background", -1);
            if (resId > 0 && "drawable".equals(res.getResourceTypeName(resId))) {
                this.setResource(false, resId, res);
            }
        }

    }

    @TargetApi(16)
    void setResource(boolean isSrc, int resId, Resources res) {
        try {
            com.wy.showgif.GifDrawable d = new com.wy.showgif.GifDrawable(res, resId);
            if (isSrc) {
                this.setImageDrawable(d);
            } else if (VERSION.SDK_INT >= 16) {
                this.setBackground(d);
            } else {
                this.setBackgroundDrawable(d);
            }

            return;
        } catch (IOException var5) {
        } catch (NotFoundException var6) {
        }

        if (isSrc) {
            super.setImageResource(resId);
        } else {
            super.setBackgroundResource(resId);
        }

    }

    public void setImageURI(Uri uri) {
        if (uri != null) {
            try {
                this.setImageDrawable(new com.wy.showgif.GifDrawable(this.getContext().getContentResolver(), uri));
                return;
            } catch (IOException var3) {
            }
        }

        super.setImageURI(uri);
    }
}
