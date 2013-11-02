package com.h2o.score;

import java.util.Observable;
import java.util.Observer;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import com.h2o.sophiesquiz.R;

/**
 * This class is the concrete representation of Observer - Observer Pattern.
 * We implement java.util.Observable, rather than making our own Observer.
 * One of the many ways to display the same info.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 21/10/2013
 * Last modified: 21/10/2013 21:30
 * 
 * TODO: Must show a sort of ProgressBar look alike image showing difference between total and achieved points.
 */
public class PlainScoreDisplay implements Observer {
	
	private Integer totalSore;
	private Activity activity;
	
	public PlainScoreDisplay(Activity activity){
		totalSore = 0;
		this.activity = activity;
	}
	
	@Override
	public void update(Observable observable, Object data) {

		if(data instanceof Integer){
			totalSore = (Integer)data;
			Log.i("PLAINSCORE", "ps: " + totalSore);
			TextView txt = (TextView)this.activity.findViewById(R.id.plainStatistics);
			txt.setText("Total Score: " + totalSore);
		}else {
			Log.i("PLAINSCORE", "Some other change to subject!");
		}
	}
}
