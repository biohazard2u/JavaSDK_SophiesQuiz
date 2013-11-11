package com.h2o.sophiesquiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h2o.db.DbHelper;
import com.h2o.questions.Question;
import com.h2o.user.User;

/**
 * This class is to launch the GameActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 16/10/2013
 * Last modified: 22/10/2013 09:00
 * 
 * TODO: 
 */
public class GameActivity extends Activity {
		
	DbHelper dbHelper = new DbHelper(this);
	Question currentQuestion;
	
	private long timeToAnswer = Question.timeToAnswer;
	private long initialPoints = Question.initialPoints;
	private int pointsToLoose = (int) ((initialPoints/200) + 100);
	private long pointsAchieved;
	CountDownTimer timer;
	private ProgressBar timerProgressBar;
	
	public static final String MESSAGE_ANSWER = "com.h2o.sophiesquiz.GameActivity.MESSAGE_ANSWER";
	public static final String MESSAGE_RIGHT = "com.h2o.sophiesquiz.GameActivity.MESSAGE_RIGHT";
	public static final String MESSAGE_WRONG = "com.h2o.sophiesquiz.GameActivity.MESSAGE_WRONG";
	
	private String exitQuestionWarningTitle, exitQuestionWarningMsg;   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.game_screen);
		
		currentQuestion = dbHelper.getSpecificQuestion(User.getNextQuestion());
		final View view = (View)findViewById(R.id.gameView);
		timerProgressBar = (ProgressBar)findViewById(R.id.timerProgressBar);		
		timerProgressBar.setMax(190);
		// Starting Timer here:
		pointsAchieved = initialPoints;		
		timer = new CountDownTimer(timeToAnswer, 100) {  
			int progressBarTime = 0;
			@Override
			public void onTick(long millisUntilFinished) {
				pointsAchieved -= pointsToLoose;
				//Log.i("TIME", "PointsAchieved: " + pointsAchieved);
				timerProgressBar.setProgress(progressBarTime);				
				progressBarTime++;
			}
			
			@Override
			public void onFinish() {
				//Log.i("TIME", "DONE");	
				pointsAchieved = 0;
				questionResult(view, 0);
			}
		};
		
		TextView question = (TextView)findViewById(R.id.questionText);
		Button answer1 = (Button)findViewById(R.id.answerButton1);
		Button answer2 = (Button)findViewById(R.id.answerButton2);
		Button answer3 = (Button)findViewById(R.id.answerButton3);
				
		// Check language here
		Log.i("LANGUAGE", Integer.toString(User.getLanguageSettings()));
		if(User.getLanguageSettings() == User.ENGLISH){
			question.setText(currentQuestion.get_question_eng());
			answer1.setText(currentQuestion.get_answer1_eng());
			answer2.setText(currentQuestion.get_answer2_eng());
			answer3.setText(currentQuestion.get_answer3_eng());
			exitQuestionWarningTitle = getString(R.string.exit_question_warning_title);
			exitQuestionWarningMsg = getString(R.string.exit_question_warning_msg);
		}else {
			question.setText(currentQuestion.get_question_spa());
			answer1.setText(currentQuestion.get_answer1_spa());
			answer2.setText(currentQuestion.get_answer2_spa());
			answer3.setText(currentQuestion.get_answer3_spa());
			exitQuestionWarningTitle = getString(R.string.exit_question_warning_title_sp);
			exitQuestionWarningMsg = getString(R.string.exit_question_warning_msg_sp);
		}
		
		timer.start();
			
		// Answer Buttons.
		answer1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				timer.cancel();
				questionResult(v, 1);
			}
		});
		answer2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				questionResult(v, 2);
			}
		});
		answer3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				questionResult(v, 3);
			}
		});
	}	
	
	// This method is to handle the answers: Update DataBase + launch QuestionResult with intent.
	private void questionResult(View view, int answerSelected) {
		
		//int i = dbHelper.updateQuestionDetails(User.getNextQuestion(), answerSelected, (int)pointsAchieved);	
		//Log.i("DATABASE", " DataBase updated. Affected rows: " + i);
		
		Intent intent = new Intent(this, QuestionResultActivity.class);
		if(answerSelected != 0){
			if(currentQuestion.get_wrightAnswer() == answerSelected){
				intent.putExtra(MESSAGE_ANSWER, MESSAGE_RIGHT);
				Log.i("ANSWER", "Right Answer");
				dbHelper.updateQuestionDetails(User.getNextQuestion(), answerSelected, (int)pointsAchieved);	
			}else {
				Log.i("ANSWER", "Wrong Answer");
				intent.putExtra(MESSAGE_ANSWER, MESSAGE_WRONG);
				dbHelper.updateQuestionDetails(User.getNextQuestion(), answerSelected, 0);
			}
		}else {
			Log.i("ANSWER", "Wrong Answer");
			intent.putExtra(MESSAGE_ANSWER, MESSAGE_WRONG);
			dbHelper.updateQuestionDetails(User.getNextQuestion(), answerSelected, 0);
		}
		
		// Shut timer + launch QuestionResult Activity + close current. 	
		timer.cancel();
		startActivity(intent);
		finish();
	}	
	
	// Back Button - Warning of exiting in the middle of the question
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setTitle(exitQuestionWarningTitle)
	        .setMessage(exitQuestionWarningMsg)
	        .setNegativeButton(android.R.string.no, null)
	        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					View view = (View)findViewById(R.id.gameView);
					questionResult(view, 0);
				}
			}).create().show();
	}
}
