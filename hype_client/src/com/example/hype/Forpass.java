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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forpass extends Activity{
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/selectpass";
	Button sent;
	public static EditText e1;
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.email);
	
	e1= (EditText)findViewById(R.id.editText1);
	sent = (Button)findViewById(R.id.button1);
	
	sent.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			Doemail dr = new Doemail();
			dr.execute();
		}
	});
 }
 @Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS RECOVERY")
		        .setMessage("Are you sure you want to exit forgot password?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            Intent i=new Intent(Forpass.this,Login.class);
		            startActivity(i);//finish();
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}		
				
 class Doemail extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
			try
			{			
				SoapObject request = new SoapObject("http://tempuri.org/","selectemail");
				request.addProperty("email",e1.getText().toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);				
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
				androidHttpTransport.call("http://tempuri.org/selectemail", envelope);				
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
			pdia = new ProgressDialog(Forpass.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("checking...");
			pdia.show(); 
		}		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);				
			pdia.dismiss();				
			if(result.toString().equals("true"))
			{
			Toast.makeText(Forpass.this, "check Done", 2).show();	
			Intent i1 = new Intent(Forpass.this,Passotp.class); 
			startActivity(i1);
			}
			else
			{
				Toast.makeText(Forpass.this, "email not valid", 2).show();	
			}
		}
	}
}

