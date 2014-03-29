package com.naseercs91.bytehost32.ideapost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	Button btnIdeaAdd;
	Button btnIdeaList;
	Button btnIdeaSearch;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		btnIdeaAdd = (Button) findViewById(R.id.btnIdeaAdd);
		btnIdeaAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(MenuActivity.this, IdeaAddActivity.class);
				startActivity(addIntent);
			}
		});
		
		btnIdeaList = (Button) findViewById(R.id.btnIdeaList);
		btnIdeaList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(MenuActivity.this, IdeaListActivity.class);
				startActivity(addIntent);
			}
		});
		
		btnIdeaSearch = (Button) findViewById(R.id.btnIdeaSearch);
		btnIdeaSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addIntent = new Intent(MenuActivity.this, IdeaSearchActivity.class);
				startActivity(addIntent);
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

}
