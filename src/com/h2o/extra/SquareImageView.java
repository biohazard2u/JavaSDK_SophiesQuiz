package com.h2o.extra;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This class is to create square Images.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 11/11/2013
 * Last modified: 11/11/2013 14:00
 * 
 */
public class SquareImageView extends ImageView {

	public SquareImageView(Context context) {
		super(context);
	}

	public SquareImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/*
		 * int width = getMeasuredWidth(); setMeasuredDimension(width, width);
		 */

		// Get canvas width and height
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);

		w = Math.min(w, h);
		h = w;

		setMeasuredDimension(w, h);
	}

}
