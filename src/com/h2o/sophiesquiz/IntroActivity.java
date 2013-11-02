package com.h2o.sophiesquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.h2o.user.User;

/**
 * This class is to launch the IntroActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 28/10/2013 19:00
 * 
 * TODO: 
 */
public class IntroActivity extends Activity {

	Button soundSettingsButton;
	Button languageButton;
	TextView title;
	Button buttonLauncher;
	Button sRLink;	
	Button creditsButton;
	Button shareScores;
	Button statisticsButton;
	// Audio
	private MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.intro_screen);
			
		//Audio
		mp = MediaPlayer.create(this, R.raw.fx_selectbutton_on);
		
		// Change Audio Settings Button.
		soundSettingsButton = (Button)findViewById(R.id.soundSettingsButton);
		setAudioImage();
		soundSettingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(User.PREFS_FILE_NAME, Context.MODE_PRIVATE);
				User.changeAudioPreferences(settings);
				setAudioImage();
			}
		});
		
		// Change Language Settings Button.
		languageButton = (Button)findViewById(R.id.langSettingsButton);
		setLangFlag();
		languageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(User.PREFS_FILE_NAME, Context.MODE_PRIVATE);
				User.changeLanguagePreferences(settings);
				setLangFlag();
				setLangStrings();
				playAudio();
			}
		});
		
		// Title
		title = (TextView)findViewById(R.id.titleView);
		
		// Start/Continue Button.
		buttonLauncher = (Button)findViewById(R.id.launchButton);
		setLangStrings();
		buttonLauncher.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playAudio();
				launchGame(v);
			}
		});
		
		// TODO: TopScores table + Go to top_scores_screen Button.		
		
		// Sophie's Realm game link to Android Store Button.
		sRLink = (Button)findViewById(R.id.sophiesRealmLinkButton);
		sRLink.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				playAudio();
				startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.H2OGames.SophiesRealm")));
			}
		});
		
		// Credits (with link to GitHub code) Button. 
		creditsButton = (Button)findViewById(R.id.creditsButton);
		creditsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playAudio();
				Intent intent = new Intent(IntroActivity.this, CreditsActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Share Results Button.
		shareScores = (Button)findViewById(R.id.shareButton);
		shareScores.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playAudio();
				Intent intent = new Intent(IntroActivity.this, ShareActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		// Statistics Button 
		statisticsButton = (Button)findViewById(R.id.statisticsButton);
		statisticsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				playAudio();
				Intent intent = new Intent(IntroActivity.this, StatisticsActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	// To launch a new game or continue an old one.
	private void launchGame(View view) {
		Log.i("launchGame", "acabas de clicar launchGame");
			
		// Check here what we're launching: create Intent + new activity
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	// Back Button
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle("Really Exit?")
	        .setMessage("Are you sure you want to exit?")
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).create().show();
	}
	
	// Method to change the language settings icon.
	private void setLangFlag(){
		if(User.getLanguageSettings() == User.ENGLISH){
			languageButton.setBackgroundResource(R.drawable.eng_icon);
		}else {
			languageButton.setBackgroundResource(R.drawable.spa_icon);
		}
	}
	
	// Method to change texts according to language.
	private void setLangStrings(){
		if(User.getLanguageSettings() == User.ENGLISH){
			if(User.getNextQuestion() != 1){
				buttonLauncher.setText(R.string.continue_button);
			}else {
				buttonLauncher.setText(R.string.start_button);
			}
			title.setText(R.string.game_title);
			
		}else {
			if(User.getNextQuestion() != 1){
				buttonLauncher.setText(R.string.continue_button_sp);
			}else {
				buttonLauncher.setText(R.string.start_button_sp);
			}
			title.setText(R.string.game_title_sp);
		}
	}
	
	// Method to change the audio settings icon.
	private void setAudioImage(){
		if(User.isAudioSettings())
			soundSettingsButton.setBackgroundResource(R.drawable.audio_on);
		else 			
			soundSettingsButton.setBackgroundResource(R.drawable.audio_off);
	}
	
	// Play audio according to settings.
	private void playAudio(){
		if(User.isAudioSettings())
			mp.start();
	}
}
