package com.h2o.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.h2o.questions.Question;
import com.h2o.sophiesquiz.R;

/**
 * This class a the concrete representation of Observer - Observer Pattern.
 * We implement java.util.Observable, rather than making our own Observer.
 * One of the many ways to display the same info.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 25/10/2013
 * Last modified: 25/10/2013 11:30
 * 
 * TODO: Must find real device sizes/pixels.
 */
public class PieChartScoreDisplay  implements Observer {

	private Activity activity;
	private List<Question> questions;
	public float[] values = new float[6];
	public PieChartScoreDisplay(Activity activity){
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
			collectValues();
			
			LinearLayout linear = (LinearLayout)this.activity.findViewById(R.id.statistics);
			linear.addView(new MyGraphview(activity, values));
		}else {
			Log.i("PLAINSCORE", "Some other change to subject2!");
		}		
	}

	private void collectValues(){
		int scienceQ = 0;
		int historyQ = 0;
		int geographyQ = 0; 
		int entertainmentQ = 0; 
		int sportsQ = 0; 
		int wordsQ = 0;
		for (Question question : questions) {
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 1){
				scienceQ ++;
			}
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 2){
				historyQ ++;
			}
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 3){
				geographyQ ++;
			}
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 4){
				entertainmentQ ++;
			}
			
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 5){
				sportsQ ++;
			}
			if(question.get_wrightAnswer() == question.get_playerAnswer() && question.get_questionGrade() == 6){
				wordsQ ++;
			}
		}
		float total = scienceQ + historyQ + geographyQ + entertainmentQ + sportsQ + wordsQ;
		values[0] = 363*(scienceQ/total);
		values[1] = 363*(historyQ/total);
		values[2] = 363*(geographyQ/total);
		values[3] = 363*(entertainmentQ/total);
		values[4] = 363*(sportsQ/total);
		values[5] = 363*(wordsQ/total);
	}
	
	
	public class MyGraphview extends View {
		private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		private float[] value_degree;
		private int[] COLORS = { 
				getResources().getColor(R.color.sta_green), 
				getResources().getColor(R.color.sta_yellow), 
				getResources().getColor(R.color.sta_blue), 
				getResources().getColor(R.color.sta_magenta), 
				getResources().getColor(R.color.sta_red), 
				getResources().getColor(R.color.sta_cyan) };
		RectF rectf = new RectF(80, 20, 460, 400);		// TODO: Must find real device sizes/pixels.
		int temp = 0;

		public MyGraphview(Context context, float[] values) {
			super(context);
			value_degree = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				value_degree[i] = values[i];
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			for (int i = 0; i < value_degree.length; i++) {
				if (i == 0) {
					paint.setColor(COLORS[i]);
					canvas.drawArc(rectf, 0, value_degree[i], true, paint);
				} else {
					temp += (int) value_degree[i - 1];
					paint.setColor(COLORS[i]);
					canvas.drawArc(rectf, temp, value_degree[i], true, paint);
				}
			}
		}
	}
}
