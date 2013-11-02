package com.h2o.sophiesquiz;

import com.h2o.user.User;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class is to launch the CreditsActivity.
 * 
 * @author Marcos Zalacain
 * @version 1.0
 * Date created: 22/10/2013
 * Last modified: 22/10/2013 15:00
 * 
 * TODO: 
 */
public class CreditsActivity extends Activity {

	TextView creditsTitle;
	TextView actualCredits;
	TextView lickToGitHubCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits_screen);
		
		// Credits texts
		creditsTitle = (TextView) findViewById(R.id.creditsTitle);
		actualCredits = (TextView) findViewById(R.id.actualCredits);
		lickToGitHubCode = (TextView) findViewById(R.id.gitHubCodeLink);

		setTexts();
		
		// H2O Logo Link
		ImageView h2oLink = (ImageView) findViewById(R.id.logoImage);
		h2oLink.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent h2oLinkIntent = new Intent("android.intent.action.VIEW",	Uri.parse("http://h2ogames.wordpress.com/"));
				startActivity(h2oLinkIntent);
				finish();
			}
		});

		// Github Link
		lickToGitHubCode.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	// Setting Language according to user settings here.
	private void setTexts(){
		if(User.getLanguageSettings() == User.SPANISH){
			creditsTitle.setText(R.string.credits_sp);
			actualCredits.setText(R.string.credits_string_sp);
			lickToGitHubCode.setText(R.string.github_code_link_sp);
		}else {
			creditsTitle.setText(R.string.credits);
			actualCredits.setText(R.string.credits_string);
			lickToGitHubCode.setText(R.string.github_code_link);
		}
	}
	
	// onBackPressed for Older than API level 5 method.
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	Log.i("BackButton",	"Pressed");
	    	Intent intent = new Intent(CreditsActivity.this, IntroActivity.class);
			startActivity(intent);
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
