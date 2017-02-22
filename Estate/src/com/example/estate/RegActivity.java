package com.example.estate;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText fname;
	EditText lname;
	EditText username;
	EditText password;
	EditText location;
	EditText contact;
	
	Button btnreg;
	Button btncancel;

	// url to create new product
	private static String url_new_user = "http://172.16.3.82/Estate_Conny/new_user.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_activity);

		// Edit Text
		fname = (EditText) findViewById(R.id.fname);
		lname = (EditText) findViewById(R.id.lname);
		username = (EditText) findViewById(R.id.uname);
		password = (EditText) findViewById(R.id.pass);
		location = (EditText) findViewById(R.id.addr);
		contact = (EditText) findViewById(R.id.contact);
		

		// Create button
		btnreg = (Button) findViewById(R.id.btnreg);
		btncancel = (Button) findViewById(R.id.btncancel);

		// button click event
		btnreg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
		
		btncancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(RegActivity.this);
			pDialog.setMessage("Registering New User..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String Firstname = fname.getText().toString();
			String Lastname = lname.getText().toString();
			String Username = username.getText().toString();
			String Password = password.getText().toString();
			String Address = location.getText().toString();
			String Contact = contact.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("fname", Firstname));
			params.add(new BasicNameValuePair("lname", Lastname));
			params.add(new BasicNameValuePair("username", Username));
			params.add(new BasicNameValuePair("password", Password));
			params.add(new BasicNameValuePair("location", Address));
			params.add(new BasicNameValuePair("contact", Contact));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_new_user,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
