package com.h2o.questions;

/**
 * This class represents a single Question object.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 16/10/2013
 * Last modified: 16/10/2013 09:30
 * 
 * TODO: 
 */
public class Question {
	
	public static final long timeToAnswer = 20*1000;
	public static final long initialPoints = timeToAnswer * 20;
	public static final int lastQuestion = 114;
	
	private int _questionNumber;
	
	private String _question_eng;
	private String _question_spa;
	
	private String _answer1_eng;
	private String _answer2_eng;
	private String _answer3_eng;
	
	private String _answer1_spa;
	private String _answer2_spa;
	private String _answer3_spa;
	
	private int _wrightAnswer;
	private int _playerAnswer;
	private int _timeToAnswer;
	
	private int _questionGrade;
	private int _messageTime;

	// Getters & Setters
	public int get_questionNumber() {
		return _questionNumber;
	}

	public void set_questionNumber(int _questionNumber) {
		this._questionNumber = _questionNumber;
	}

	public String get_question_eng() {
		return _question_eng;
	}

	public void set_question_eng(String _question_eng) {
		this._question_eng = _question_eng;
	}

	public String get_question_spa() {
		return _question_spa;
	}

	public void set_question_spa(String _question_spa) {
		this._question_spa = _question_spa;
	}

	public String get_answer1_eng() {
		return _answer1_eng;
	}

	public void set_answer1_eng(String _answer1_eng) {
		this._answer1_eng = _answer1_eng;
	}

	public String get_answer2_eng() {
		return _answer2_eng;
	}

	public void set_answer2_eng(String _answer2_eng) {
		this._answer2_eng = _answer2_eng;
	}

	public String get_answer3_eng() {
		return _answer3_eng;
	}

	public void set_answer3_eng(String _answer3_eng) {
		this._answer3_eng = _answer3_eng;
	}

	public String get_answer1_spa() {
		return _answer1_spa;
	}

	public void set_answer1_spa(String _answer1_spa) {
		this._answer1_spa = _answer1_spa;
	}

	public String get_answer2_spa() {
		return _answer2_spa;
	}

	public void set_answer2_spa(String _answer2_spa) {
		this._answer2_spa = _answer2_spa;
	}

	public String get_answer3_spa() {
		return _answer3_spa;
	}

	public void set_answer3_spa(String _answer3_spa) {
		this._answer3_spa = _answer3_spa;
	}

	public int get_wrightAnswer() {
		return _wrightAnswer;
	}

	public void set_wrightAnswer(int _wrightAnswer) {
		this._wrightAnswer = _wrightAnswer;
	}

	public int get_playerAnswer() {
		return _playerAnswer;
	}

	public void set_playerAnswer(int _playerAnswer) {
		this._playerAnswer = _playerAnswer;
	}

	public int get_timeToAnswer() {
		return _timeToAnswer;
	}

	public void set_timeToAnswer(int _timeToAnswer) {
		this._timeToAnswer = _timeToAnswer;
	}

	public int get_questionGrade() {
		return _questionGrade;
	}

	public void set_questionGrade(int _questionGrade) {
		this._questionGrade = _questionGrade;
	}

	public int get_messageTime() {
		return _messageTime;
	}

	public void set_messageTime(int _messageTime) {
		this._messageTime = _messageTime;
	}	
}
