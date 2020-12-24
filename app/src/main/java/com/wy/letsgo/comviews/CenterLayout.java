package com.wy.letsgo.comviews;

import com.wy.letsgo.util.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CenterLayout extends ViewGroup {
    int viewWidth, viewHeight;

    public CenterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childViewCount = this.getChildCount();
        LogUtil.i("centerLayout����", "onMeasure");
        for (int i = 0; i < childViewCount; i++) {
            View view = this.getChildAt(i);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(size,
                    MeasureSpec.AT_MOST);

            size = MeasureSpec.getSize(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(size,
                    MeasureSpec.AT_MOST);
            view.measure(widthMeasureSpec, heightMeasureSpec);

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.i("centerLayout����", "onLayout");

        int childViewCount = this.getChildCount();
        // �����ò���ģʽ���߶Ȳ���
        int sum = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.getChildAt(i);
            int height = view.getMeasuredHeight();
            sum = sum + height;
        }

        int y = (viewHeight - sum) / 2;
        for (int i = 0; i < childCount; i++) {
            View view = this.getChildAt(i);
            int height = view.getMeasuredHeight();
            int width = view.getMeasuredWidth();
            int left = (viewWidth - width) / 2;
            view.layout(left, y, left + width, y + height);
            y = y + height;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.i("�¼�", "centerLayout dispatchTouchEvent " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.i("�¼�", "centerLayout onInterceptTouchEvent " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.i("�¼�", "centerLayout onTouchEvent " + event.getAction());
        return super.onTouchEvent(event);
    }

}
