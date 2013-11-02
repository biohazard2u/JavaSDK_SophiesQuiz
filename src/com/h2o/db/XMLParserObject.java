package com.h2o.db;

/**
 * This class is a single parsed object.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 15/10/2013 18:30
 * 
 * TODO: 
 */
public class XMLParserObject {	
    
    private int QUESTION_NUMBER ;
    private String QUESTION_ENG;
    private String QUESTION_SPA;
    private String ANSWER1_ENG; 
    private String ANSWER2_ENG;
    private String ANSWER3_ENG; 
    private String ANSWER1_SPA;
    private String ANSWER2_SPA; 
    private String ANSWER3_SPA;
    private int WRIGHT_ANSWER; 
    
    //private int PLAYER_ANSWER;
    //private int TIME_TO_ANSWER; 
    private int QUESTION_GRADE;
    //private int MESSAGE_TIME;    

	public int getQUESTION_NUMBER() {
		return QUESTION_NUMBER;
	}

	public void setQUESTION_NUMBER(int qUESTION_NUMBER) {
		QUESTION_NUMBER = qUESTION_NUMBER;
	}

	public String getQUESTION_ENG() {
		return QUESTION_ENG;
	}

	public void setQUESTION_ENG(String qUESTION_ENG) {
		QUESTION_ENG = qUESTION_ENG;
	}

	public String getQUESTION_SPA() {
		return QUESTION_SPA;
	}

	public void setQUESTION_SPA(String qUESTION_SPA) {
		QUESTION_SPA = qUESTION_SPA;
	}

	public String getANSWER1_ENG() {
		return ANSWER1_ENG;
	}

	public void setANSWER1_ENG(String aNSWER1_ENG) {
		ANSWER1_ENG = aNSWER1_ENG;
	}

	public String getANSWER2_ENG() {
		return ANSWER2_ENG;
	}

	public void setANSWER2_ENG(String aNSWER2_ENG) {
		ANSWER2_ENG = aNSWER2_ENG;
	}

	public String getANSWER3_ENG() {
		return ANSWER3_ENG;
	}

	public void setANSWER3_ENG(String aNSWER3_ENG) {
		ANSWER3_ENG = aNSWER3_ENG;
	}

	public String getANSWER1_SPA() {
		return ANSWER1_SPA;
	}

	public void setANSWER1_SPA(String aNSWER1_SPA) {
		ANSWER1_SPA = aNSWER1_SPA;
	}

	public String getANSWER2_SPA() {
		return ANSWER2_SPA;
	}

	public void setANSWER2_SPA(String aNSWER2_SPA) {
		ANSWER2_SPA = aNSWER2_SPA;
	}

	public String getANSWER3_SPA() {
		return ANSWER3_SPA;
	}

	public void setANSWER3_SPA(String aNSWER3_SPA) {
		ANSWER3_SPA = aNSWER3_SPA;
	}

	public int getWRIGHT_ANSWER() {
		return WRIGHT_ANSWER;
	}

	public void setWRIGHT_ANSWER(int wRIGHT_ANSWER) {
		WRIGHT_ANSWER = wRIGHT_ANSWER;
	}

	public int getQUESTION_GRADE() {
		return QUESTION_GRADE;
	}

	public void setQUESTION_GRADE(int qUESTION_GRADE) {
		QUESTION_GRADE = qUESTION_GRADE;
	}
	
	@Override
    public String toString() {
        return QUESTION_ENG;
    }
}
