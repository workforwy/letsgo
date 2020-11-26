package com.wy.letsgo.util;

import android.content.Context;

public class DisplayUtil {
    public static int sp2px(Context context, int sp) {
        // metric ����
        // density �ܶ�
        float desity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * desity + 0.5f);
    }
}
