package com.example.hype;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Delete extends Activity {
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/delete";
	Button deact;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);				
		deact = (Button)findViewById(R.id.button1);
		Login.uid = Login.pref.getString("uid",null);
		deact.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub		
				
				    new AlertDialog.Builder(Delete.this)
				        .setIcon(android.R.drawable.ic_dialog_alert)
				        .setTitle("DELETE ACCOUNT")
				        .setMessage("Are you sure you want to delete account?")
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
				    {
				        @Override
				        public void onClick(DialogInterface dialog, int which) {
				        	DoReg dr = new DoReg();
							dr.execute();	
					            Intent i=new Intent(Delete.this,Login.class);
					            startActivity(i);
}

				    })
				    .setNegativeButton("No", null)
				    .show();
				}
		});		
	}
	class DoReg extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
			try
			{			
				SoapObject request = new SoapObject("http://tempuri.org/","delete");
				request.addProperty("uid",Login.uid);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);				
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
				androidHttpTransport.call("http://tempuri.org/delete", envelope);				
				SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();				
				return resultstr.toString();								
			}
			catch(Exception e)
			{
				Log.e("error", e.toString());
				return e.toString();
			}				
		}	
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pdia = new ProgressDialog(Delete.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("deactivating...");
			pdia.show(); 
		}		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);				
			pdia.dismiss();		
			if(result.toString().equals("true"))
			{
				
				Toast.makeText(Delete.this, "deactivated", 2).show();
				SharedPreferences preferences = getSharedPreferences(Login.mypref, 0);
				SharedPreferences.Editor editor = preferences.edit();
				editor.clear(); 
				editor.commit();
			
				 Intent i=new Intent(Delete.this,Login.class);
		            startActivity(i);
			/*	Intent i1 = new Intent(Delete.this,Login.class); 
				startActivity(i1);*/
			}
			else
			{
				Toast.makeText(Delete.this, "username wrong", 2).show();	
			}

			}
		}
}
