package com.h2o.score;

import java.util.Observable;
import java.util.Observer;
import android.util.Log;

/**
 * This class is a concrete representation of Observer, it's just to keep track of total score.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 10/11/2013
 * Last modified: 10/11/2013 21:30 
 */
public class ScoreTotal implements Observer{

	private static Integer totalSore;
	
	public ScoreTotal() {
		totalSore = 0;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		if(data instanceof Integer){
			totalSore = (Integer)data;
		}else {
			Log.i("PLAINSCORE", "Some other change to subject!");
		}
	}

	public static Integer getTotalSore() {
		return totalSore;
	}
}
