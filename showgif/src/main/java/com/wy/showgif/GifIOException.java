//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wy.showgif;

import java.io.IOException;

public class GifIOException extends IOException {
    private static final long serialVersionUID = 13038402904505L;
    public final com.wy.showgif.GifError reason;

    GifIOException(com.wy.showgif.GifError reason) {
        super(reason.getFormattedDescription());
        this.reason = reason;
    }

    GifIOException(int errorCode) {
        this(com.wy.showgif.GifError.fromCode(errorCode));
    }
}
