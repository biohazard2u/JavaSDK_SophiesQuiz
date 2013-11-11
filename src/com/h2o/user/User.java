package com.h2o.user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * This class is to track User stuff.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 17/10/2013
 * Last modified: 17/10/2013 17:02
 * 
 */
public class User {
	
	public static final int ENGLISH = 1;
	public static final int SPANISH = 2;
	public static final boolean AUDIO_ON = true;
	public static final boolean AUDIO_OF = false;
	public static final String PREFS_FILE_NAME = "com.h2o.sophiesquiz.prefs";
	public static final String NEXT_QUESTION_FILE_NAME = "nqfn";
	
	private static int languageSettings; 
	private static int nextQuestion;
	private static boolean audioSettings; 
		
	// This is to set the Initial Settings (Language and audio) on or from a Preferences files 
	// and to set this preferences in User.languageSettings for easier access.
	public static void setInitialSettings(SharedPreferences settings){
		
		SharedPreferences.Editor editor = settings.edit();
		
		if(settings.getBoolean("my_first_time", true)){
			
			if(Locale.getDefault().getLanguage().equals("es")){
				editor.putInt("LanguageSettings", SPANISH).commit();
				setLanguageSettings(SPANISH);
			}else{
				editor.putInt("LanguageSettings", ENGLISH).commit();
				setLanguageSettings(ENGLISH);
			}			
			editor.putBoolean("audioSettings", AUDIO_ON);
			setAudioSettings(AUDIO_ON);
			editor.putBoolean("my_first_time", false).commit();
		}else {
			setLanguageSettings(settings.getInt("LanguageSettings", ENGLISH));
		}
	}
	
	// To change language preferences
	public static void changeLanguagePreferences(SharedPreferences settings){
		SharedPreferences.Editor editor = settings.edit();
		if(settings.getInt("LanguageSettings", ENGLISH) == ENGLISH){
			editor.putInt("LanguageSettings", SPANISH).commit();
			setLanguageSettings(SPANISH);
		}else {
			editor.putInt("LanguageSettings", ENGLISH).commit();
			setLanguageSettings(ENGLISH);
		}
	}
		
	// To change audio preferences
	public static void changeAudioPreferences(SharedPreferences settings){
		SharedPreferences.Editor editor = settings.edit();
		if(settings.getBoolean("audioSettings", AUDIO_ON) == AUDIO_ON){
			editor.putBoolean("audioSettings", AUDIO_OF).commit();
			setAudioSettings(AUDIO_OF);
		}else {
			editor.putBoolean("audioSettings", AUDIO_ON).commit();
			setAudioSettings(AUDIO_ON);
		}
	}
	
	// To Set the Next Question Value when we launch the application for the very first time.
	public static void setInitialNextQuestionValue(FileOutputStream fos){
		nextQuestion = 1;	// Initial value of ONE for 1st Question.
		String nextQuestionString = Integer.toString(nextQuestion);
		try {
			Log.i("NextQUESTIONVALUE", "SETTING INITIAL VALUE...");
			fos.write(nextQuestionString.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	// To get the Next Question Value when we launch application, not for the first time.
	// It stores its value into nextQuestion static variable for easy access.
	public static void getNextQuestionValue(FileInputStream fis){
		Log.i("NextQUESTIONVALUE", "RETRIEVING VALUE...");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = br.readLine()) != null) sb.append(line);
			nextQuestion = Integer.parseInt(sb.toString());
			Log.i("NextQUESTIONVALUE", sb.toString());
		} catch (Exception e) {
			Log.i("NextQUESTIONVALUE", "ERROR");
			e.printStackTrace();
		}		
	}
	
	// This is to update the Next Question Value (+1) so we can show next question.
	public static void updateNextQuestionValue(FileOutputStream fos){
		//Log.i("NextQUESTIONVALUE", "UPDATING VALUE...");
		//Log.i("NextQUESTIONVALUE", "CURRENT VALUE = " + User.getNextQuestion());
		nextQuestion++;
		String nextQuestionString = Integer.toString(nextQuestion);
		try {
			fos.write(nextQuestionString.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	// Getters and setters.
	public static int getLanguageSettings() {
		return languageSettings;
	}

	public static void setLanguageSettings(int languageSettings) {
		User.languageSettings = languageSettings;
	}

	public static int getNextQuestion() {
		return nextQuestion;
	}

	public static void setNextQuestion(int nextQuestion) {
		User.nextQuestion = nextQuestion;
	}

	public static boolean isAudioSettings() {
		return audioSettings;
	}

	public static void setAudioSettings(boolean audioSettings) {
		User.audioSettings = audioSettings;
	}
}
