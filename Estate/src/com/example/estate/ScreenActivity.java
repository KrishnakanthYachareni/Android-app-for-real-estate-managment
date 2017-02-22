package com.example.estate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ScreenActivity extends Activity{
	
	Button btnView, btnNew;
	Button btnlogout;
	Button btncontact;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen1);
		
		// Buttons
		btnView = (Button) findViewById(R.id.btnViewProducts);
		btnNew = (Button) findViewById(R.id.btnAdd);
		btnlogout = (Button) findViewById(R.id.btnlogout);
		btncontact = (Button) findViewById(R.id.btnContact);
		
		// view products click event
		btnView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching All products Activity
				
				finish();
				Intent i = new Intent(getApplicationContext(), ReadComments.class);
				startActivity(i);
				
			}
		});
		
		btnNew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching All products Activity
				
				finish();
				Intent i = new Intent(getApplicationContext(), NewPropertyActivity.class);
				startActivity(i);
				
			}
		});
		
		// view products click event
		
		
           btncontact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				finish();
				// Launching create new product activity
				Intent i = new Intent(getApplicationContext(), MessageActivity.class);
				startActivity(i);
				
			}
		});
		
btnlogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// Launching create new product activity
				finish();
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
				
			}
		});
	}
}
