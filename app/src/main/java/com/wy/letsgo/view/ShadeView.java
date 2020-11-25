package com.wy.letsgo.view;

import com.wy.letsgo.R;
import com.wy.letsgo.util.DisplayUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ShadeView extends View {
	String text;
	int text_color, shade_color, text_size;
	int Stringheight, StringWidth;
	int shadeSize = 5;

	public ShadeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context
				.obtainStyledAttributes(attrs, R.styleable.Shade);
		text = ta.getString(R.styleable.Shade_text);
		text_color = ta.getColor(R.styleable.Shade_text_color, 0);
		shade_color = ta.getColor(R.styleable.Shade_shade_color, 0);
		text_size = (int) ta.getFloat(R.styleable.Shade_text_size, 12);
	}

	// ������onMeasure,������onDraw
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Paint paint = new Paint();
		//��ִ������
		int px=DisplayUtil.sp2px(getContext(), text_size);
		paint.setTextSize(px);
		Rect rect = new Rect();
		paint.getTextBounds(text, 0, text.length(), rect);
		StringWidth = rect.width();
		Stringheight = rect.height();

		int mode = MeasureSpec.getMode(widthMeasureSpec);
		if (mode == MeasureSpec.AT_MOST) {
			setMeasuredDimension(StringWidth + shadeSize, Stringheight
					+ shadeSize+10);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {

		try {
			Paint paint = new Paint();
			int px=DisplayUtil.sp2px(getContext(), text_size);
			paint.setTextSize(px);
			
			
			paint.setColor(shade_color);
			canvas.drawText(text, shadeSize, Stringheight + shadeSize, paint);

			paint.setColor(text_color);
			canvas.drawText(text, 0, Stringheight, paint);

		} catch (Exception e) {
		}

	}

}
