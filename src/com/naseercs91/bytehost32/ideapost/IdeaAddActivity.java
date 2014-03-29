package com.naseercs91.bytehost32.ideapost;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.apache.http.conn.HttpHostConnectException;
import android.app.Activity;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class IdeaAddActivity extends Activity {

	protected static final String TAG = IdeaAddActivity.class.getName();
	private EditText editName;
	private EditText editDescription;
	private Button button;
	protected EditText description;
	private TextView txtOutput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_idea_add);
		txtOutput = (TextView) findViewById(R.id.txtOutput);
		editName = (EditText) findViewById(R.id.editName);
		editDescription = (EditText) findViewById(R.id.editDescription);
		button = (Button) findViewById(R.id.btnAdd);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new IdeaAddTask().execute(new String[]{});
			}
		});
	}
	
	class IdeaAddTask extends AsyncTask<String, Void, String> {
			String status;
		    @Override
		    protected String doInBackground(String... dummyParam) {
		    	InputStream is = null;
				StringBuilder sb = null;
				String result1 = null;
				
				Log.d(TAG, "msg");

				try {
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(
							"http://azam.byethost7.com/ideabin/idea_add.php");
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							2);
					nameValuePairs.add(new BasicNameValuePair("name", editName
							.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("desc", 
							editDescription.getText().toString()));
					nameValuePairs.add(new BasicNameValuePair("userId", "123"));


					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					HttpEntity entity = response.getEntity();
					is = entity.getContent();

				} catch (Exception e) {
					Log.e("log_tag", "Error in http connection" + e.toString());
				}

				// convert response to string
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}

					is.close();
					result1 = sb.toString();

				} catch (Exception e) {
					Log.e("log_tag", "Error converting result " + e.toString());
				}

				try {
					JSONObject json_data = new JSONObject(result1);
					status = json_data.getString("status");
					Log.i(TAG, "status=" + status);
					//txtOutput.setText("Status: " + status);
				} catch (JSONException e1) {
					Toast.makeText(getBaseContext(), "No Idea Found",
							Toast.LENGTH_LONG).show();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
		    	return status;
		    }

		    @Override
		    protected void onPostExecute(String result) {
		    	txtOutput.setText(status);
		    }
		  }

}