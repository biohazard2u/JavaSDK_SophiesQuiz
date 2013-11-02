package com.h2o.sophiesquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * This class is to launch the ShareActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 28/10/2013
 * Last modified: 28/10/2013 18:40
 * 
 * TODO: After registering game / Uploading to market => create login interface to allow users to log in and share their score online.
 * 													  => retrieve the score and show a table with top scores and player's score.
 */
public class ShareActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_scores);
	}

	// onBackPressed for Older than API level 5 method.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(ShareActivity.this,	IntroActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
