package com.h2o.sophiesquiz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.h2o.db.DbHelper;
import com.h2o.questions.Question;
import com.h2o.score.PieChartScoreDisplay;
import com.h2o.score.PlainScoreDisplay;
import com.h2o.score.Score;
import com.h2o.score.ScoreTotal;
import com.h2o.score.TableScoreDisplay;
import com.h2o.user.User;

/**
 * This class is to launch the QuestionResultActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 16/10/2013
 * Last modified: 11/11/2013 16:00
 * 
 */
public class QuestionResultActivity extends Activity{
	
	DbHelper dbHelper = new DbHelper(this);
	// Audio
	private MediaPlayer audioToPlay;
	Button questionResultButton;
	String achievementToastBegining = "", achievementToastEnd = "";

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.question_result_screen);
		
		// Getting Scores..................................................................
		// Create the Subject.
		Score sc = dbHelper.getScores();		
		// Create the Observers.
		PlainScoreDisplay plainObserver = new PlainScoreDisplay(this);
		PieChartScoreDisplay pieChartObserver = new PieChartScoreDisplay(this);
		TableScoreDisplay tableObserver = new TableScoreDisplay(this);
		ScoreTotal scoreTotalObserver = new ScoreTotal();
		// Add those Observers!
		sc.addObserver(plainObserver);
		sc.addObserver(pieChartObserver);
		sc.addObserver(tableObserver);
		sc.addObserver(scoreTotalObserver);
		// Make changes to the Subject.		
		sc.setTotal_score_points();
		//sc.setQuestions(sc.getQuestions());
		// Getting Scores..................................................................
		
		// Getting intent from Game Activity
		String rightOrWrong = getIntent().getStringExtra(GameActivity.MESSAGE_ANSWER);
	
		// View Items
		LinearLayout backgroundLayout = (LinearLayout)findViewById(R.id.backgroundLayout);
		questionResultButton = (Button)findViewById(R.id.nextQuestionPlease);
		
		if(rightOrWrong.equals(GameActivity.MESSAGE_RIGHT)){
			setLangStrings(GameActivity.MESSAGE_RIGHT);
			questionResultButton.setBackgroundColor(getResources().getColor(R.color.alpha_green));
			backgroundLayout.setBackgroundResource(R.drawable.right_answer);
			audioToPlay = MediaPlayer.create(this, R.raw.fx_answerbutton_right);
		}else {
			setLangStrings(GameActivity.MESSAGE_WRONG);
			questionResultButton.setBackgroundColor(getResources().getColor(R.color.alpha_red));
			backgroundLayout.setBackgroundResource(R.drawable.wrong_answer);
			audioToPlay = MediaPlayer.create(this, R.raw.fx_answerbutton_wrong);
		}
		
		playAudio();
		showAchievementToast();
		
		questionValuePlusOne();
		questionResultButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				nextQuestion(v);
			}
		});
	}
	
	// Launch next Question.
	private void nextQuestion(View v) {
		
		Intent intent = new Intent(this, GameActivity.class);
		Intent intentEnd = new Intent(this, StatisticsActivity.class);
		
		if(User.getNextQuestion() < Question.lastQuestion + 1)		// If we haven't arrived to last Question...
			startActivity(intent);
		else 
			startActivity(intentEnd);
	}
	
	// To Update next Question Value.
	private void questionValuePlusOne(){
		FileOutputStream fos;
		try {
			fos = openFileOutput(User.NEXT_QUESTION_FILE_NAME, Context.MODE_PRIVATE);
			User.updateNextQuestionValue(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	// Play audio according to settings.
	private void playAudio(){
		if(User.isAudioSettings())
			audioToPlay.start();
	}
	
	// Method to change texts according to language.
	private void setLangStrings(String rightOrWrong) {
		if (User.getLanguageSettings() == User.ENGLISH) {
			if(rightOrWrong == GameActivity.MESSAGE_RIGHT){
				questionResultButton.setText("Right");
			}else{
				questionResultButton.setText("Wrong");
			}
			achievementToastBegining = getString(R.string.achievementSubmitted);
			achievementToastEnd = getString(R.string.achievementToastEnd);
		} else {
			if(rightOrWrong == GameActivity.MESSAGE_RIGHT){
				questionResultButton.setText("Correcto");
			}else{
				questionResultButton.setText("Error");
			}
			achievementToastBegining = getString(R.string.achievementSubmitted_sp);
			achievementToastEnd = getString(R.string.achievementToastEnd_sp);
		}
	}
	
	// Show achievement toast
	private void showAchievementToast(){
		String achievementTitle = "";				
		switch (User.getNextQuestion()) {
			case Question.lastQuestion:
				achievementTitle = getString(R.string.question_achievement_All);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
			case 100:
				achievementTitle = getString(R.string.question_achievement_100);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
			case 75:
				achievementTitle = getString(R.string.question_achievement_75);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
			case 50:
				achievementTitle = getString(R.string.question_achievement_50);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
			case 25:
				achievementTitle = getString(R.string.question_achievement_25);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
			case 10:
				achievementTitle = getString(R.string.question_achievement_10);
				Toast.makeText(QuestionResultActivity.this,  achievementToastBegining + "\n" + achievementTitle + "\n" + achievementToastEnd, Toast.LENGTH_LONG).show();
				break;
		}
	}
}
