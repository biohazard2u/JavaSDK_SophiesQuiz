package com.h2o.extra;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * This class is to create square buttons.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 15/10/2013 21:00
 * 
 */ 
public class SquareButton extends Button {
	
	public SquareButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SquareButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareButton(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// Get canvas width and height
		int w = MeasureSpec.getSize(widthMeasureSpec);
		int h = MeasureSpec.getSize(heightMeasureSpec);

		w = Math.min(w, h);
		h = w;

		setMeasuredDimension(w, h);
	}
}
