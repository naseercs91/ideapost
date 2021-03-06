package com.naseercs91.bytehost32.ideapost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IdeaDetailsActivity extends Activity {

	//public static final String IDEA_ID = "id";
	private static final String TAG = IdeaDetailsActivity.class.getName();
	String ideaId;
	String ideaName;
	String ideaDesc;
	public static final String IDEA_ID = "id";
	public static final String IDEA_NAME = "name";
	public static final String IDEA_DESC = "description";
	
	Button btnIdeaEdit;
	
	TextView txtIdeaName;
	TextView txtIdeaDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_details);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
		    ideaId = extras.getString(IdeaListActivity.IDEA_ID);
		    ideaName = extras.getString(IdeaListActivity.IDEA_NAME);
		    ideaDesc = extras.getString(IdeaListActivity.IDEA_DESC);
		    Log.d(TAG, "IdeaDetailsActivity.ideaId " + ideaId
		    		+ ", name : " + ideaName + ", description : " + ideaDesc);
		}
		txtIdeaName = (TextView) findViewById(R.id.txtIdeaName);
		txtIdeaName.setText(ideaName);
		
		txtIdeaDescription = (TextView) findViewById(R.id.txtIdeaDescription);
		txtIdeaDescription.setText(ideaDesc);
		
		btnIdeaEdit = (Button) findViewById(R.id.btnIdeaEdit);
		btnIdeaEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent editIntent = new Intent(IdeaDetailsActivity.this, IdeaEditActivity.class);
				
				editIntent.putExtra(IDEA_ID, ideaId);
				editIntent.putExtra(IDEA_NAME, ideaName);
				editIntent.putExtra(IDEA_DESC, ideaDesc);
				
				startActivity(editIntent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.idea_details, menu);
		return true;
	}

}
