package com.example.hype;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hype.Forpass.Doemail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Otp extends Activity{
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/selectotp";
	Button sent,resent;
	EditText otp,email;
	
	
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.otp);
	email= (EditText)findViewById(R.id.editText1);
	otp= (EditText)findViewById(R.id.editText2);
	sent= (Button)findViewById(R.id.button1);
	resent= (Button)findViewById(R.id.button2);
	email.addTextChangedListener(new TextWatcher() {

		@Override
public void afterTextChanged(Editable s) {
// TODO Auto-generated method stub
				}
						@Override
public void beforeTextChanged(CharSequence s, int start,int count, int after) {
								// TODO Auto-generated method stub										
							}

							@Override
public void onTextChanged(CharSequence s, int start,int before, int count) {
								// TODO Auto-generated method stub												
	String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
		
	if (email.getText().toString().matches(EMAIL_PATTERN)) 
	{
	email.setError(null);
	}
	 else// 	if(email.getText().toString().trim().length()>0)
				{
				email.setError("email field in empty or Invalid Email abc@xyz.com");
				}	
		 }});

	sent.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			doselectotp dr = new doselectotp();
			dr.execute();
			
		}
	});
	
	resent.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Dootp dr = new Dootp();
			dr.execute();
		}
	});
}
@Override
public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("EXIT THIS OTP")
	        .setMessage("Are you sure you want to exit otp ?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	       /*     Intent i=new Intent(Otp.this,Login.class);
	            startActivity(i);*/		        	finish();

	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}		
			
class doselectotp extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	protected String doInBackground(Void... params) 
	{
		try
		{			
			SoapObject request = new SoapObject("http://tempuri.org/","selectotp");
			request.addProperty("otp",otp.getText().toString());
			request.addProperty("email",email.getText().toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=true;
			envelope.setOutputSoapObject(request);				
			HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
			androidHttpTransport.call("http://tempuri.org/selectotp", envelope);				
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
		pdia = new ProgressDialog(Otp.this);
		pdia.setCanceledOnTouchOutside(false);
		pdia.setMessage("verifing...");
		pdia.show(); 
	}		
	protected void onPostExecute(String result) {
		super.onPostExecute(result);				
		pdia.dismiss();				
		if(result.toString().equals("true"))
		{
		Toast.makeText(Otp.this, "verify", 2).show();
		Intent i=new Intent(Otp.this,Login.class);
		startActivity(i);
		}
		else
		{
		Toast.makeText(Otp.this, "not varify", 2).show();	
		}

	}
}


class Dootp extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	protected String doInBackground(Void... params) 
	{
		try
		{			
			SoapObject request = new SoapObject("http://tempuri.org/","sentotp");
			request.addProperty("email",email.getText().toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=true;
			envelope.setOutputSoapObject(request);				
			HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
			androidHttpTransport.call("http://tempuri.org/sentotp", envelope);				
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
		pdia = new ProgressDialog(Otp.this);
		pdia.setCanceledOnTouchOutside(false);
		pdia.setMessage("sending...");
		pdia.show(); 
	}		
	protected void onPostExecute(String result) {
		super.onPostExecute(result);				
		pdia.dismiss();				
		if(result.toString().equals("true"))
		{
		Toast.makeText(Otp.this, "sent", 2).show();	
		}
		else
		{
		Toast.makeText(Otp.this, "not sent", 2).show();	
		}	

	}
}
}
