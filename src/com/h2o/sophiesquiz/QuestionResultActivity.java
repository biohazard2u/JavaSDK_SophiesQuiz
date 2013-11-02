package com.h2o.sophiesquiz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.h2o.db.DbHelper;
import com.h2o.questions.Question;
import com.h2o.score.PieChartScoreDisplay;
import com.h2o.score.PlainScoreDisplay;
import com.h2o.score.Score;
import com.h2o.score.TableScoreDisplay;
import com.h2o.user.User;

/**
 * This class is to launch the QuestionResultActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 16/10/2013
 * Last modified: 28/10/2013 19:00
 * 
 * TODO: 
 */
public class QuestionResultActivity extends Activity{
	
	DbHelper dbHelper = new DbHelper(this);
	// Audio
	private MediaPlayer audioToPlay;

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
		// Add those Observers!
		sc.addObserver(plainObserver);
		sc.addObserver(pieChartObserver);
		sc.addObserver(tableObserver);
		// Make changes to the Subject.		
		sc.setTotal_score_points();
		//sc.setQuestions(sc.getQuestions());
		// Getting Scores..................................................................
		
		// Getting intent from Game Activity
		String rightOrWrong = getIntent().getStringExtra(GameActivity.MESSAGE_ANSWER);
	
		// View Items
		LinearLayout backgroundLayout = (LinearLayout)findViewById(R.id.backgroundLayout);
		Button questionResultButton = (Button)findViewById(R.id.nextQuestionPlease);
		
		if(rightOrWrong.equals(GameActivity.MESSAGE_RIGHT)){
			questionResultButton.setText("Right");
			questionResultButton.setBackgroundColor(getResources().getColor(R.color.alpha_green));
			backgroundLayout.setBackgroundResource(R.drawable.right_answer);
			audioToPlay = MediaPlayer.create(this, R.raw.fx_answerbutton_right);
		}else {
			questionResultButton.setText("Wrong");
			questionResultButton.setBackgroundColor(getResources().getColor(R.color.alpha_red));
			backgroundLayout.setBackgroundResource(R.drawable.wrong_answer);
			audioToPlay = MediaPlayer.create(this, R.raw.fx_answerbutton_wrong);
		}
		
		playAudio();
		
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
}
