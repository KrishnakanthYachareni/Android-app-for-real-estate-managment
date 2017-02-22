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
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewPropertyActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;
	
	 private static int RESULT_LOAD_IMAGE = 1;

	JSONParser jsonParser = new JSONParser();
	EditText inputName;
	EditText inputContact;
	EditText inputLocation;
	EditText inputStatus;
	EditText inputPrice;
	EditText inputDesc;
	
	Button btnAdd;
	Button btnCancel;
	Button btnLoad;

	// url to create new product
	private static String url_new_property = "http://172.16.3.82/Estate_Conny/new_property.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_property);

		// Edit Text
		inputName = (EditText) findViewById(R.id.name);
		inputLocation = (EditText) findViewById(R.id.location);
		inputStatus = (EditText) findViewById(R.id.status);
		inputPrice = (EditText) findViewById(R.id.price);
		inputDesc = (EditText) findViewById(R.id.desc);
		inputContact = (EditText) findViewById(R.id.cont);

		// Create button
		btnAdd = (Button) findViewById(R.id.btnNew);
		btnCancel = (Button) findViewById(R.id.btnKanso);
		btnLoad = (Button) findViewById(R.id.browse);
		

		// button click event
		btnAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
		
		btnLoad.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
                
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
		});
		
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(), ScreenActivity.class);
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
			pDialog = new ProgressDialog(NewPropertyActivity.this);
			pDialog.setMessage("Adding Property Record..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String Contact = inputContact.getText().toString();
			String Name = inputName.getText().toString();
			String Location = inputLocation.getText().toString();
			String Status = inputStatus.getText().toString();
			String Price = inputPrice.getText().toString();
			String Description = inputDesc.getText().toString();
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("contact", Contact));
			params.add(new BasicNameValuePair("name", Name));
			params.add(new BasicNameValuePair("location", Location));
			params.add(new BasicNameValuePair("status", Status));
			params.add(new BasicNameValuePair("price", Price));
			params.add(new BasicNameValuePair("description", Description));
			

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_new_property,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), ScreenActivity.class);
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
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         
        }
     
     
    }
}
