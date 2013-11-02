package com.h2o.sophiesquiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.h2o.db.DbHelper;
import com.h2o.user.User;

/**
 * This class is to launch the splash screen and create database on first launch.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 24/10/2013 16:30
 * 
 * TODO: Check for online scores.
 */
public class SplashScreen extends Activity{

	private static int SPLAH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		// Preferences		
		SharedPreferences settings = getSharedPreferences(User.PREFS_FILE_NAME, Context.MODE_PRIVATE);
		User.setInitialSettings(settings);
		// Next Question Value
		FileOutputStream fos;
		FileInputStream fis;
		File file;
		try {
			file = getFileStreamPath(User.NEXT_QUESTION_FILE_NAME);
			if(file.length() == 0){
				// We write initial values.
				fos = openFileOutput(User.NEXT_QUESTION_FILE_NAME, Context.MODE_PRIVATE);
				User.setInitialNextQuestionValue(fos);
			}else {
				// We get existing values.
				fis = openFileInput(User.NEXT_QUESTION_FILE_NAME);
				User.getNextQuestionValue(fis);
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Creating/Opening database here (if doesn't exist/ exits). 
        SQLiteDatabase db = new DbHelper(getApplicationContext()).getWritableDatabase();
        db.close();
		
		// Launches main/intro activity after 3 seconds.
		new Handler().postDelayed(new Runnable() {			
			@Override
			public void run() {
				// Starting Main/Intro Activity.
				Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
				startActivity(intent);
				// Closing SpashScreen Activity
				finish();
			}
		}, SPLAH_TIME_OUT);
	}	
}
