package com.h2o.sophiesquiz;


import com.h2o.db.DbHelper;
import com.h2o.score.PieChartScoreDisplay;
import com.h2o.score.PlainScoreDisplay;
import com.h2o.score.Score;
import com.h2o.score.TableScoreDisplay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * This class is to launch the Statistics Activity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 22/10/2013
 * Last modified: 22/10/2013 15:00
 * 
 * TODO: Make it look nicer and set strings according to language.
 */
public class StatisticsActivity extends Activity {

	DbHelper dbHelper = new DbHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistics_screen); 

		// Getting
		// Scores..................................................................
		// Create the Subject.
		Score sc = dbHelper.getScores();
		// Create the Observers.
		PlainScoreDisplay plainObserver = new PlainScoreDisplay(this);
		PieChartScoreDisplay pieChartObserver = new PieChartScoreDisplay(this);
		TableScoreDisplay tableObserver = new TableScoreDisplay(this);
		// Add those Observers!
		sc.addObserver(plainObserver);
		sc.addObserver(pieChartObserver);
		sc.addObserver(tableObserver);
		// Make changes to the Subject.
		// sc.setTotal_score_points();
		sc.setQuestions(sc.getQuestions());
	}

	// onBackPressed for Older than API level 5 method.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(StatisticsActivity.this,	IntroActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
