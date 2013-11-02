package com.h2o.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;

import com.h2o.questions.Question;

/**
 * This class is a concrete representation of Observer - Observer Pattern.
 * We implement java.util.Observable, rather than making our own Observer.
 * One of the many ways to display the same info.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 25/10/2013
 * Last modified: 25/10/2013 12:30
 * 
 * TODO: Must Complete this, barely started at this point.
 */
public class TableScoreDisplay  implements Observer {

	private Activity activity;
	private List<Question> questions;
	
	public TableScoreDisplay(Activity activity){
		this.activity = activity;
		this.questions = new ArrayList<Question>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object data) {
		if(data instanceof List<?>){
			for (Question question : ((List<Question>)data)) {
				this.questions.add((Question) question);
				//Log.i("PieChartScoreDisplay", "chartQuestion: " + question.get_answer1_eng());
			}
			//collectValues();
			
			/*TextView txt = (TextView)this.activity.findViewById(R.id.plainStatistics);
			txt.setText("Total Score: " + totalSore);*/
		}else {
			//Log.i("PLAINSCORE", "Some other change to subject!");
		}		
	}

	/*private void collectValues(){
		for (Question question : questions) {
			if(question.get_wrightAnswer() == question.get_playerAnswer()){
				Log.i("PieChartScoreDisplay", "chartQuestion: " + question.get_answer1_eng());
			}
		}
	}*/

}
