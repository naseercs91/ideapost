package com.naseercs91.bytehost32.ideapost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IdeaEditActivity extends Activity {
	private static final String TAG = IdeaEditActivity.class.getName();
	String ideaId;
	String ideaName;
	String ideaDesc;
	public static final String IDEA_ID = "id";
	public static final String IDEA_NAME = "name";
	public static final String IDEA_DESC = "description";
	 
	Button btnIdeaUpdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_edit);
		

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		   ideaId = extras.getString(IdeaListActivity.IDEA_ID);
		    ideaName = extras.getString(IdeaListActivity.IDEA_NAME);
		    ideaDesc = extras.getString(IdeaListActivity.IDEA_DESC);
		    Log.d(TAG, "IdeaDetailsActivity.ideaId " + ideaId
		    		+ ", name : " + ideaName + ", description : " + ideaDesc);
		}
		
		EditText edit = (EditText)findViewById(R.id.btnIdeaEdit);
		TextView tview = (TextView)findViewById(R.id.btnUpdate);
		String result = edit.getText().toString();
		tview.setText(result);
		Log.d(TAG,"Idea Name" + ideaName + "Idea Desc" + ideaDesc);
		//edit.setText(ideaName);
		//edit.setText(ideaDesc);
			
		 btnIdeaUpdate = (Button) findViewById(R.id.btnIdeaEdit);
		btnIdeaUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent editIntent = new Intent(IdeaEditActivity.this, IdeaDetailsActivity.class);
				
				startActivity(editIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.idea_edit, menu);
		return true;
	}

}
