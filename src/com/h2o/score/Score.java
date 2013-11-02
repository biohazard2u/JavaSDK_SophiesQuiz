package com.h2o.score;

import java.util.List;
import java.util.Observable;
import com.h2o.questions.Question;
import com.h2o.user.User;

/**
 * This class is the concrete representation of Subject - Observer Pattern.
 *  We extend java.util.Observable rather than making a Subject.java class & implementing the pattern from scratch.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 21/10/2013
 * Last modified: 21/10/2013 21:30
 * 
 * TODO: 
 */
public class Score extends Observable{
	
	private int questionsAnswered = User.getNextQuestion() - 1; 
	private long total_available_points = questionsAnswered * Question.initialPoints;
	private int total_score_points;
	private List<Question> questions;	
	
	// Getters and Setters.
	public void setTotal_score_points() {
		//this.total_score_points = total_score_points;
		for(Question question: this.questions){
			this.total_score_points += question.get_timeToAnswer();
		}
		setChanged();
		notifyObservers(total_score_points);
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
		setChanged();
		notifyObservers(questions);
	}
	
	public int getQuestionsAnswered() {
		return questionsAnswered;
	}

	public int getTotal_score_points() {
		return total_score_points;
	}
	
	public long getTotal_available_points() {
		return total_available_points;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
}
