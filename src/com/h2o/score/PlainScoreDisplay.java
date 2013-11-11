package com.h2o.score;

import java.util.Observable;
import java.util.Observer;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.h2o.questions.Question;
import com.h2o.sophiesquiz.R;
import com.h2o.user.User;

/**
 * This class is the concrete representation of Observer - Observer Pattern.
 * We implement java.util.Observable, rather than making our own Observer.
 * One of the many ways to display the same info.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 11/11/2013
 * Last modified: 11/11/2013 21:30
 * 
 */
public class PlainScoreDisplay implements Observer {
	
	private Integer totalSore;
	private Activity activity;
	private String startString, endString;
	
	public PlainScoreDisplay(Activity activity){
		totalSore = 0;
		this.activity = activity;
	}
	
	@Override
	public void update(Observable observable, Object data) {
 
		if(data instanceof Integer){
			totalSore = (Integer)data;
			long totalPointsAvailable = Question.initialPoints*User.getNextQuestion();
			setLangStrings();
			// We set text here:
			TextView txt = (TextView)this.activity.findViewById(R.id.plainStatistics);
			txt.setText(startString + totalSore + endString + totalPointsAvailable);
			// We get custom View to set width.
			View ptsRect = (View)this.activity.findViewById(R.id.myRectangleView);			
			// We get the outer/bigger square's width from the screen width minus the applied padding. 
			int appliedPadding = 40;
			DisplayMetrics metrics = this.activity.getResources().getDisplayMetrics();
			int screenWidth = metrics.widthPixels; 
			int totalRectWidth = screenWidth - appliedPadding;
			// We calculate ptsRectWidth (the smaller inner rectangle) width.
			int ptsRectWidth = (int) (totalRectWidth * totalSore / totalPointsAvailable); 
			// We need LayoutParams to set our custom view width.
			LayoutParams params = ptsRect.getLayoutParams();
			params.width = ptsRectWidth;
			ptsRect.setLayoutParams(params);
		}else {
			Log.i("PLAINSCORE", "Some other change to subject!");
		}
	}
	
	private void setLangStrings(){
		if (User.getLanguageSettings() == User.ENGLISH) {
			startString = " Total Score:\n ";
			endString = " out of ";
		}else{
			startString = " Puntuación total:\n ";
			endString = " de ";
		}
	}
}
