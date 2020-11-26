package com.wy.letsgo.comviews;

import com.wy.letsgo.util.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		LogUtil.i("�¼�", "MyLinearLayout dispatchTouchEvent " + ev.getAction());
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		LogUtil.i("�¼�",
				"MyLinearLayout onInterceptTouchEvent " + ev.getAction());

		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		LogUtil.i("�¼�", "MyLinearLayout onTouchEvent " + event.getAction());

		return super.onTouchEvent(event);
	}

}
