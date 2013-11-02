package com.h2o.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.h2o.questions.Question;
import com.h2o.score.Score;

/**
 * This class is to Manage The Questions data base.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 16/10/2013 18:30
 * 
 * TODO:  
 */
public class DbHelper extends SQLiteOpenHelper implements BaseColumns {

	public static final String DATABASE = "questionsManager.sqlite";
	private static final int VERSION = 1;
	public static final String TABLE_NAME = "questions_manager";

	// DataBase columns:
    public static final int QUESTION_NUMBER = 0;
    public static final String QUESTION_ENG = "QUESTION_ENG";
    public static final String QUESTION_SPA = "QUESTION_SPA";
    public static final String ANSWER1_ENG = "ANSWER1_ENG";
    public static final String ANSWER2_ENG = "ANSWER2_ENG";
    public static final String ANSWER3_ENG = "ANSWER3_ENG";
    public static final String ANSWER1_SPA = "ANSWER1_SPA";
    public static final String ANSWER2_SPA = "ANSWER2_SPA";
    public static final String ANSWER3_SPA = "ANSWER3_SPA";
    public static final int WRIGHT_ANSWER = 0;
    public static final int PLAYER_ANSWER = 0;
    public static final int TIME_TO_ANSWER = 0;
    public static final int QUESTION_GRADE = 0;
    public static final int MESSAGE_TIME = 0;	
	
	
	private static Context mContext;
	List<XMLParserObject> questions = null;

	public DbHelper(Context context) { 
		super(context, DATABASE, null, VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String createTableQuery = "create table " + TABLE_NAME + " ("
				+ " QUESTION_NUMBER INTEGER PRIMARY KEY,"
				+ " QUESTION_ENG TEXT," 
				+ " QUESTION_SPA TEXT,"
				+ " ANSWER1_ENG TEXT," 
				+ " ANSWER2_ENG TEXT,"
				+ " ANSWER3_ENG TEXT," 
				+ " ANSWER1_SPA TEXT,"
				+ " ANSWER2_SPA TEXT," 
				+ " ANSWER3_SPA TEXT,"
				+ " WRIGHT_ANSWER INTEGER," 
				+ " PLAYER_ANSWER INTEGER,"
				+ " TIME_TO_ANSWER INTEGER," 
				+ " QUESTION_GRADE INTEGER,"
				+ " MESSAGE_TIME INTEGER)";
		db.execSQL(createTableQuery);		

		// Insert data from XML
		XMLParser parser = new XMLParser();
		// XML Source file:
		Resources xmlFile = mContext.getResources();
		questions = parser.parse(xmlFile);

		for (XMLParserObject question : questions) {
			// Toast.makeText(mContext, post.getpostTitle(),
			// Toast.LENGTH_SHORT).show();

			ContentValues values = new ContentValues();
			
			// Inserting values to DB.
			values.put("QUESTION_NUMBER", question.getQUESTION_NUMBER());
			values.put("QUESTION_ENG", question.getQUESTION_ENG());
			values.put("QUESTION_SPA", question.getQUESTION_SPA());
			values.put("ANSWER1_ENG", question.getANSWER1_ENG());
			values.put("ANSWER2_ENG", question.getANSWER2_ENG());
			values.put("ANSWER3_ENG", question.getANSWER3_ENG());
			values.put("ANSWER1_SPA", question.getANSWER1_SPA());
			values.put("ANSWER2_SPA", question.getANSWER2_SPA());
			values.put("ANSWER3_SPA", question.getANSWER3_SPA());
			values.put("WRIGHT_ANSWER", question.getWRIGHT_ANSWER());
			values.put("PLAYER_ANSWER", "0");
			values.put("TIME_TO_ANSWER", "0");
			values.put("QUESTION_GRADE", question.getQUESTION_GRADE());
			values.put("MESSAGE_TIME", "0");
		    
			//db.insert(TABLE_NAME, ID, values);
			db.insert(TABLE_NAME, null, values);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
	
	public Question getSpecificQuestion(int questionNumber){
		
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE QUESTION_NUMBER = " + questionNumber;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Question question = new Question();
		
		if(cursor.moveToFirst()){
			cursor.moveToFirst();
			question.set_questionNumber(questionNumber);
			question.set_question_eng(cursor.getString(1));
			question.set_question_spa(cursor.getString(2));
			question.set_answer1_eng(cursor.getString(3));
			question.set_answer2_eng(cursor.getString(4));
			question.set_answer3_eng(cursor.getString(5));
			question.set_answer1_spa(cursor.getString(6));
			question.set_answer2_spa(cursor.getString(7));
			question.set_answer3_spa(cursor.getString(8));
			question.set_wrightAnswer(Integer.parseInt(cursor.getString(9)));
			question.set_playerAnswer(Integer.parseInt(cursor.getString(10)));
			question.set_timeToAnswer(Integer.parseInt(cursor.getString(11)));
			question.set_questionGrade(Integer.parseInt(cursor.getString(12)));
			question.set_messageTime(Integer.parseInt(cursor.getString(13)));
			cursor.close();
		} else {
			question = null;
		}
		db.close();		
		return question; 
	}
	
	public int updateQuestionDetails(int questionNumber, int playerAnswer, int pointsAchieved) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("PLAYER_ANSWER", playerAnswer);
		cv.put("TIME_TO_ANSWER", pointsAchieved);	
		int i = db.update(TABLE_NAME, cv, "QUESTION_NUMBER =?", new String[] { Integer.toString(questionNumber) }); 
		db.close();
		return i;
	}
	
	public Score getScores(){
		
		String query = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		Score score = new Score();		
		List<Question> questionsList = new ArrayList<Question>();
		Question eachQuestion;
		
		if(cursor.moveToFirst()){
			
			do{
				eachQuestion = new Question();
				eachQuestion.set_questionNumber(Integer.parseInt(cursor.getString(0)));
				eachQuestion.set_question_eng(cursor.getString(1));
				eachQuestion.set_question_spa(cursor.getString(2));
				eachQuestion.set_answer1_eng(cursor.getString(3));
				eachQuestion.set_answer2_eng(cursor.getString(4));
				eachQuestion.set_answer3_eng(cursor.getString(5));
				eachQuestion.set_answer1_spa(cursor.getString(6));
				eachQuestion.set_answer2_spa(cursor.getString(7));
				eachQuestion.set_answer3_spa(cursor.getString(8));
				eachQuestion.set_wrightAnswer(Integer.parseInt(cursor.getString(9)));
				eachQuestion.set_playerAnswer(Integer.parseInt(cursor.getString(10)));
				eachQuestion.set_timeToAnswer(Integer.parseInt(cursor.getString(11)));
				eachQuestion.set_questionGrade(Integer.parseInt(cursor.getString(12)));
				eachQuestion.set_messageTime(Integer.parseInt(cursor.getString(13)));

				questionsList.add(eachQuestion);

			}while(cursor.moveToNext());
			
			score.setQuestions(questionsList);
		}
		
		if (cursor != null && !cursor.isClosed()){
	        cursor.close();
	    }
	
		db.close();		
		return score; 		
	}
}