package com.example.hype;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity{
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/select";
	String METHOD_NAME = "select"; 
	
	//Button ep,cp,bh,h,f,d,chat,review;
	TextView t1;
	ImageButton ep,cp,bh,h,f,d,chat,review,log;
	public static String regId;
	final String SENDER_ID = "82716677818";
	
	private NotificationManager mgr=null;
	
	
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		t1=(TextView)findViewById(R.id.textView1);
		
		
		ep=(ImageButton)findViewById(R.id.imageButton1);
		cp=(ImageButton)findViewById(R.id.imageButton2);
		bh=(ImageButton)findViewById(R.id.imageButton3);
		h= (ImageButton)findViewById(R.id.imageButton4);
		f= (ImageButton)findViewById(R.id.imageButton5);
		d= (ImageButton)findViewById(R.id.imageButton8);
		chat= (ImageButton)findViewById(R.id.imageButton6);
		review = (ImageButton)findViewById(R.id.imageButton7);
		log = (ImageButton)findViewById(R.id.imageButton9);
		
		
		
		
		
	/*	ep=(Button)findViewById(R.id.button1);
		cp=(Button)findViewById(R.id.button2);
		bh=(Button)findViewById(R.id.button3);
		h= (Button)findViewById(R.id.button4);
		f= (Button)findViewById(R.id.button5);
		d= (Button)findViewById(R.id.button7);
		chat= (Button)findViewById(R.id.button6);
		review = (Button)findViewById(R.id.button8);*/
		Login.clientusername = Login.pref.getString("clientusername",null);
		
	//	Login.clientuid = Login.pref.getString("clientuid",null);
		
		if(Login.clientusername==null)
		{
			Intent i = new Intent(Home.this,Login.class);
			startActivity(i);
		}
		
		t1.setText("WELCOME " + Login.clientusername);
		
		GCMRegistrar.checkDevice(Home.this);
		GCMRegistrar.checkManifest(Home.this);
		regId = GCMRegistrar.getRegistrationId(Home.this);
		if (regId.equals("")) 
		{
		  GCMRegistrar.register(this, SENDER_ID);
		} 
		else
		{
		  Log.v("Push", regId);
		  
		}
		new Doregid().execute();
		mgr=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		mgr.cancel(1);
		
		review.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Review.class);
				startActivity(i);
			}
		});
		
		bh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Hoarding.class);
				startActivity(i);
			}
		});
		
		h.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,History1.class);
				startActivity(i);
			}
		});
		
		f.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Feedback1.class);
				startActivity(i);
			}
		});
		
		ep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Updatepro.class);
				startActivity(i);
			}
		});
	
		d.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Delete.class);
				startActivity(i);				
			}
		});
		
		cp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Updatepass.class);
				startActivity(i);				
			}
		});
		
		chat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,Chatting.class);
				startActivity(i);				
			}
		});

		log.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SharedPreferences preferences = getSharedPreferences(Login.mypref, 0);
				SharedPreferences.Editor editor = preferences.edit();
				editor.clear(); 
				editor.commit();
			/*	Intent i = new Intent(Worker_home.this,Worker_login.class);
				startActivity(i);
				*/
				finish();
			}
		});
		
		
	 }	
class DoReglogout extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;
	protected String doInBackground(Void... params) 
	{
		try
		{			
			SoapObject request = new SoapObject("http://tempuri.org/","select");
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet=true;
			envelope.setOutputSoapObject(request);				
			HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
			androidHttpTransport.call("http://tempuri.org/select", envelope);				
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
		pdia = new ProgressDialog(Home.this);
		pdia.setCanceledOnTouchOutside(false);
		pdia.setMessage("loging off...");
		pdia.show(); 
	}		
	protected void onPostExecute(String result) {
		super.onPostExecute(result);				
		pdia.dismiss();				
		Toast.makeText(Home.this, "log off", 2).show();	
		finish();
		//Intent i1 = new Intent(Home.this,Login.class); 
		//startActivity(i1);
		}
	}

	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS APPLICATION")
		        .setMessage("Are you sure you want to exit hype?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            finish();    
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}
	
	class Doregid extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","updatedevice");
			request.addProperty("uid",Login.uid);
			request.addProperty("deviceid",regId.toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/updatedevice", envelope);
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
			pdia=new ProgressDialog(Home.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("loading......");
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);
			pdia.dismiss();	
		}
	}
}
