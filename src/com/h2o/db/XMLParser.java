package com.h2o.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import com.h2o.sophiesquiz.R;

/**
 * This class is to parse the XML file.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 15/10/2013
 * Last modified: 15/10/2013 18:30
 * 
 * TODO: 
 */
public class XMLParser {
	
	List<XMLParserObject> questions;
    private XMLParserObject question_ref;

    public XMLParser() {
        questions = new ArrayList<XMLParserObject>();
    }

    public List<XMLParserObject> parse(Resources xmlFile) {    	
    
    	// Open XML file.
    	XmlResourceParser _xml = xmlFile.getXml(R.xml.the_questions);
    	try {
    		int eventType = _xml.getEventType();
    		
    		while (eventType != XmlResourceParser.END_DOCUMENT){
    				
                String tagname = _xml.getName();
                // System.out.println("=======" + tagname + "============");
                switch (eventType) {
                case XmlResourceParser.START_TAG:
                    // if <row> create a new instance of post
                    if (tagname.equalsIgnoreCase("row")) {
                        question_ref = new XMLParserObject();
                    }
                    // if <column>
                    if (tagname.equalsIgnoreCase("column")) {
                        if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("QUESTION_NUMBER")) {
                            // System.out.println("ID found! ");
                            question_ref.setQUESTION_NUMBER(Integer.parseInt(_xml.nextText()));
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("QUESTION_ENG")) {
                            // System.out.println("post_content found! ");
                            question_ref.setQUESTION_ENG(_xml.nextText());
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("QUESTION_SPA")) {
                            // System.out.println("post_title found! ");
                            question_ref.setQUESTION_SPA(_xml.nextText());                         
                            
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER1_ENG")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER1_ENG(_xml.nextText());
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER2_ENG")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER2_ENG(_xml.nextText());
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER3_ENG")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER3_ENG(_xml.nextText());
                            
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER1_SPA")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER1_SPA(_xml.nextText());
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER2_SPA")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER2_SPA(_xml.nextText());
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("ANSWER3_SPA")) {
                            // System.out.println("post_title found! ");
                            question_ref.setANSWER3_SPA(_xml.nextText());
                            
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("WRIGHT_ANSWER")) {
                            // System.out.println("post_title found! ");
                            question_ref.setWRIGHT_ANSWER(Integer.parseInt(_xml.nextText()));
                        } else if (_xml.getAttributeValue(null, "name").equalsIgnoreCase("QUESTION_GRADE")) {
                            // System.out.println("post_title found! ");
                            question_ref.setQUESTION_GRADE(Integer.parseInt(_xml.nextText()));
                        }                         
                    }
                    break;

                case XmlResourceParser.END_TAG:
                    if (tagname.equalsIgnoreCase("row")) {
                        questions.add(question_ref);
                        // System.out.println("Posts so far " + post_ref);
                    }
                    break;

                default:
                    break;
                }
                eventType = _xml.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
