package com.naseercs91.bytehost32.ideapost;

import java.io.InputStream;
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

import android.app.ListActivity;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class IdeaListActivity extends ListActivity {
	private static final String TAG = IdeaListActivity.class.getName();
	public static final String IDEA_ID = "id";
	public static final String IDEA_NAME = "name";
	public static final String IDEA_DESC = "description";
	List<Map<String, String>> ideaList = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadIdeaList();
		new IdeaListTask().execute();
	}

	private void loadIdeaList() {
		String[] from = { "name" };
		int[] to = { android.R.id.text1 };

		SimpleAdapter adapter = new SimpleAdapter(this, ideaList,
				android.R.layout.simple_list_item_1, from, to);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, String> mapIdea = ideaList.get((int) id);
		if (mapIdea != null) {
			String ideaId = mapIdea.get(IDEA_ID);
			String ideaName = mapIdea.get(IDEA_NAME);
			String ideaDesc = mapIdea.get(IDEA_DESC);

			Log.d(TAG, "Inside onListItemClick position: " + position
					+ ", idea id : " + ideaId);

			Intent addIntent = new Intent(IdeaListActivity.this,
					IdeaDetailsActivity.class);
			addIntent.putExtra(IDEA_ID, ideaId);
			addIntent.putExtra(IDEA_NAME, ideaName);
			addIntent.putExtra(IDEA_DESC, ideaDesc);

			startActivity(addIntent);
		}
	}

	class IdeaListTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... dummyParam) {
			InputStream is = null;
			StringBuilder sb = null;
			String jsonIdeaListStr = null;

			Log.d(TAG, "Hitting IdeaList api.");

			try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(
						"http://azam.byethost7.com/ideabin/idea_list.php");
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
					String id = jsonIdea.getString(IDEA_ID);
					String name = jsonIdea.getString(IDEA_NAME);
					String desc = jsonIdea.getString(IDEA_DESC);

					// Create map one row
					Map<String, String> mapIdea = new HashMap<String, String>();
					mapIdea.put(IDEA_ID, id);
					mapIdea.put(IDEA_NAME, name);
					mapIdea.put(IDEA_DESC, desc);

					// adding map to idealist
					ideaList.add(mapIdea);
				}
				Log.d(TAG, "Idea List : " + ideaList);
			} catch (JSONException e1) {
				Toast.makeText(IdeaListActivity.this, "No Idea Found",
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.idea_list, menu);
		return true;
	}
}
