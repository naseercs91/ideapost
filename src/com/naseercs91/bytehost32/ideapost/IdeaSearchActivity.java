package com.naseercs91.bytehost32.ideapost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class IdeaSearchActivity extends Activity {

	protected static final String TAG = IdeaSearchActivity.class.getName();
	EditText editSearch;
	ListView lvIdea;
	String search;
	List<Map<String, String>> ideaList = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_search);

		editSearch = (EditText) findViewById(R.id.editSearch);
		editSearch.setOnKeyListener(new OnKeyListener()
		{
		    public boolean onKey(View v, int keyCode, KeyEvent event)
		    {
		        if (event.getAction() == KeyEvent.ACTION_DOWN)
		        {
		            switch (keyCode)
		            {
		                case KeyEvent.KEYCODE_DPAD_CENTER:
		                case KeyEvent.KEYCODE_ENTER:
		                	search = editSearch.getText().toString();
		                	new IdeaSearchTask().execute(search);
		                    return true;
		                default:
		                    break;
		            }
		        }
		        return false;
		    }
		});
		

		lvIdea = (ListView) findViewById(R.id.lvIdea);
		lvIdea.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Map<String, String> mapIdea = ideaList.get((int) position);
				if (mapIdea != null) {
					String ideaId = mapIdea.get(IdeaListActivity.IDEA_ID);
					String ideaName = mapIdea.get(IdeaListActivity.IDEA_NAME);
					String ideaDesc = mapIdea.get(IdeaListActivity.IDEA_DESC);

					Log.d(TAG, "Inside onListItemClick Idea id : " + ideaId);

					Intent addIntent = new Intent(IdeaSearchActivity.this,
							IdeaDetailsActivity.class);
					addIntent.putExtra(IdeaListActivity.IDEA_ID, ideaId);
					addIntent.putExtra(IdeaListActivity.IDEA_NAME, ideaName);
					addIntent.putExtra(IdeaListActivity.IDEA_DESC, ideaDesc);

					startActivity(addIntent);
				}
			}
		});
	}

	class IdeaSearchTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... searchArg) {
			String jsonIdeaListStr = null;
			String search = null;
			if (searchArg != null & searchArg.length > 0) {
				search = searchArg[0];
			}else{
				Log.e(TAG, "Expected search word.");
				return null;
			}

			Log.d(TAG, "Hitting IdeaSearch api.");

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(
						"http://azam.byethost7.com/ideabin/idea_search.php?search="+ search);

				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				jsonIdeaListStr = EntityUtils.toString(entity);

				Log.d(TAG, "Idea List response : " + jsonIdeaListStr);
			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection" + e.toString());
			}

			try {
				JSONArray jsonIdeaList = new JSONArray(jsonIdeaListStr);
				ideaList = new ArrayList<Map<String, String>>();

				for (int i = 0; i < jsonIdeaList.length(); i++) {
					JSONObject jsonIdea = jsonIdeaList.getJSONObject(i);
					String id = jsonIdea.getString(IdeaListActivity.IDEA_ID);
					String name = jsonIdea
							.getString(IdeaListActivity.IDEA_NAME);
					String desc = jsonIdea
							.getString(IdeaListActivity.IDEA_DESC);

					// Create map one row
					Map<String, String> mapIdea = new HashMap<String, String>();
					mapIdea.put(IdeaListActivity.IDEA_ID, id);
					mapIdea.put(IdeaListActivity.IDEA_NAME, name);
					mapIdea.put(IdeaListActivity.IDEA_DESC, desc);

					// adding map to idealist
					ideaList.add(mapIdea);
				}
				Log.d(TAG, "Idea List : " + ideaList);
			} catch (JSONException e1) {
				Toast.makeText(IdeaSearchActivity.this, "No Idea Found",
						Toast.LENGTH_LONG).show();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			loadIdeaList();
		}
	}

	private void loadIdeaList() {
		String[] from = { "name" };
		int[] to = { android.R.id.text1 };

		SimpleAdapter adapter = new SimpleAdapter(this, ideaList,
				android.R.layout.simple_list_item_1, from, to);
		lvIdea.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.idea_search, menu);
		return true;
	}

}
