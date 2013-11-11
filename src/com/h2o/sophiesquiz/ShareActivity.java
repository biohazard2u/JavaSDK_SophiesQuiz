package com.h2o.sophiesquiz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.achievement.OnAchievementUpdatedListener;
import com.google.android.gms.games.leaderboard.OnScoreSubmittedListener;
import com.google.android.gms.games.leaderboard.SubmitScoreResult;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.h2o.db.DbHelper;
import com.h2o.questions.Question;
import com.h2o.score.Score;
import com.h2o.score.ScoreTotal;
import com.h2o.user.User;

/**
 * This class is to launch the ShareActivity. 
 * 
 * @author Marcos Zalacain 
 * @version 1.0
 * Date created: 28/10/2013
 * Last modified: 11/11/2013 24:00
 * 
 */ 
public class ShareActivity extends BaseGameActivity implements  OnClickListener {
	 
	TextView topScoreTitle;
	Button shareButton, shareButtonAchievement;
	Button seeLeaderBoard;
	Button seeAchievements;
	
	DbHelper dbHelper = new DbHelper(this);
	// Audio
	private MediaPlayer audioToPlay;
	
    String leaderBoardId;    
    String scoreSubmitted = "", achievementSubmitted = ""; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top_scores);
		
		audioToPlay = MediaPlayer.create(this, R.raw.fx_selectbutton_on);
		leaderBoardId = getString(R.string.sophies_quiz_mastercup_id);
		
		// Getting Scores..................................................................
		// Create the Subject & Observer.
		Score sc = dbHelper.getScores();		
		ScoreTotal scoreTotalObserver = new ScoreTotal();
		// Add the Observer and make changes to the Subject.
		sc.addObserver(scoreTotalObserver);
		sc.setTotal_score_points();
		// Getting Scores..................................................................
		
		topScoreTitle = (TextView)findViewById(R.id.top_score_title);
		topScoreTitle.setText(R.string.login_to_see);
		
		shareButton = (Button)findViewById(R.id.shareButton);
		shareButtonAchievement = (Button)findViewById(R.id.shareButtonAchievement);
		seeLeaderBoard = (Button)findViewById(R.id.seeLeaderBoard);
		seeAchievements = (Button)findViewById(R.id.seeAchievements);
		
		findViewById(R.id.sign_in_button).setOnClickListener(this);
	    findViewById(R.id.sign_out_button).setOnClickListener(this); 
	    
	    setLangStrings();
	    
	    shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				playAudio();
				postCurrentScore();
			}
		});
	    
	    shareButtonAchievement.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				playAudio();
				postAchievement();
			}
		});
	    
	    seeLeaderBoard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				playAudio();
				seeLeaderBoard();
			}
		});
	    
	    seeAchievements.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				playAudio();
				seeAchievements();
			}
		});
	}

	// onBackPressed for Older than API level 5 method.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(ShareActivity.this,	IntroActivity.class);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// When connection can't to log in.
	@Override
	public void onSignInFailed() {
		// Set sign out GUI.
	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	    findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	    topScoreTitle.setText(R.string.login_to_see);	    
	    shareButton.setVisibility(View.INVISIBLE);
	    shareButtonAchievement.setVisibility(View.INVISIBLE);
	    seeLeaderBoard.setVisibility(View.INVISIBLE);
	    seeAchievements.setVisibility(View.INVISIBLE);
	}
	
	// When connection succeed to log in.
	@Override
	public void onSignInSucceeded() {  
		// Set sign in GUI.
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	    findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);	    
	    shareButton.setVisibility(View.VISIBLE);
	    shareButtonAchievement.setVisibility(View.VISIBLE);
	    seeLeaderBoard.setVisibility(View.VISIBLE);
	    seeAchievements.setVisibility(View.VISIBLE);
	    // Get and set player name.
	    Player cPlayer = getGamesClient().getCurrentPlayer();
	    String cPlayerName = cPlayer.getDisplayName();
	    topScoreTitle.setText(cPlayerName);
	}

	// When Log in/out button presses.
	@Override
	public void onClick(View view) {
		playAudio();
	    if (view.getId() == R.id.sign_in_button) {
	        // Start the asynchronous sign in flow
	        beginUserInitiatedSignIn(); 
	    }
	    else if (view.getId() == R.id.sign_out_button) {
	        // Sign out.
	        signOut();
	        // Change to sing out GUI.
	        shareButton.setVisibility(View.INVISIBLE);
	        shareButtonAchievement.setVisibility(View.INVISIBLE);
		    seeLeaderBoard.setVisibility(View.INVISIBLE);
		    seeAchievements.setVisibility(View.INVISIBLE);
		    topScoreTitle.setText(R.string.login_to_see);
	        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	    }
	}
	
	// Post CurrentScore immediate.
	public void postCurrentScore(){
		// Get Score
		long currentScore = 0;
		if(ScoreTotal.getTotalSore() != null){
			currentScore = ScoreTotal.getTotalSore();				
			//getGamesClient().submitScore(leaderBoardIdString, currentScore);			
			getGamesClient().submitScoreImmediate(new OnScoreSubmittedListener() {
				@Override
				public void onScoreSubmitted(int arg0, SubmitScoreResult arg1) {
					Toast.makeText(ShareActivity.this,  scoreSubmitted, Toast.LENGTH_LONG).show(); 
				}
		    }, leaderBoardId, currentScore);
		}
	}
	
	// Post achievement immediate 
	String achievementTitle = "";
	String achievementId = "";
	public void postAchievement(){				
		// Setting achievement id here
		switch (User.getNextQuestion() - 1) {
			case Question.lastQuestion:
				achievementId = getString(R.string.question_achievement_All_id);
				actualAchievementPost();
			case 100:
				achievementId = getString(R.string.question_achievement_100_id);
				actualAchievementPost();
			case 75:
				achievementId = getString(R.string.question_achievement_75_id);
				actualAchievementPost();
			case 50:
				achievementId = getString(R.string.question_achievement_50_id);
				actualAchievementPost();
			case 25:
				achievementId = getString(R.string.question_achievement_25_id);
				actualAchievementPost();
			case 10:
				achievementId = getString(R.string.question_achievement_10_id);
				actualAchievementPost();
		}	
		// Setting achievement title here
		switch (User.getNextQuestion() - 1) {
			case Question.lastQuestion:
				achievementTitle = getString(R.string.question_achievement_All);
				break;
			case 100:
				achievementTitle = getString(R.string.question_achievement_100);
				break;
			case 75:
				achievementTitle = getString(R.string.question_achievement_75);
				break;
			case 50:
				achievementTitle = getString(R.string.question_achievement_50);
				break;
			case 25:
				achievementTitle = getString(R.string.question_achievement_25);
				break;
			case 10:
				achievementTitle = getString(R.string.question_achievement_10);
				break;
		}		
	}
	
	// This makes the actual post to Google Game Services.
	private void actualAchievementPost(){ 
		getGamesClient().unlockAchievementImmediate(new OnAchievementUpdatedListener() {			
			@Override
			public void onAchievementUpdated(int arg0, String arg1) {
				Toast.makeText(ShareActivity.this,  scoreSubmitted + achievementTitle, Toast.LENGTH_LONG).show();				
			}
		}, achievementId);
	}	
	
	// Show LeaderBoard.
	public void seeLeaderBoard(){
	    startActivityForResult(getGamesClient().getLeaderboardIntent(leaderBoardId), 5001);
	}
	
	// Show Achievements.
	public void seeAchievements(){
		startActivityForResult(getGamesClient().getAchievementsIntent(), 5001);
	}
	
	// Play audio according to settings.
	private void playAudio(){
		if(User.isAudioSettings())
			audioToPlay.start();
	}
	
	// Method to change texts according to language.
	private void setLangStrings() {
		if (User.getLanguageSettings() == User.ENGLISH) {
			topScoreTitle.setText(R.string.login_to_see);
			shareButton.setText(R.string.post_score);
			shareButtonAchievement.setText(R.string.post_achievement);
			seeLeaderBoard.setText(R.string.see_leaderboard);
			seeAchievements.setText(R.string.see_achievements);
			scoreSubmitted = getString(R.string.scoreSubmitted);
			achievementSubmitted = getString(R.string.achievementSubmitted);
		} else {
			topScoreTitle.setText(R.string.login_to_see_sp);
			shareButton.setText(R.string.post_score_sp);
			shareButtonAchievement.setText(R.string.post_achievement_sp);
			seeLeaderBoard.setText(R.string.see_leaderboard_sp);
			seeAchievements.setText(R.string.see_achievements_sp);
			scoreSubmitted = getString(R.string.scoreSubmitted_sp);
			achievementSubmitted = getString(R.string.achievementSubmitted_sp);
		}
	}
}
