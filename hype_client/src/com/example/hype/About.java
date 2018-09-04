package com.example.hype;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class About extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	 	setContentView(R.layout.about);
	 	}	
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS ABOUT US")
		        .setMessage("Are you sure you want to exit about us?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            Intent i=new Intent(About.this,Login.class);
		            startActivity(i);// finish();
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}		
				
}
