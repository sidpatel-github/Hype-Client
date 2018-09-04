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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
		public static String ip="192.168.0.101";
		public static String NAMESPACE = "http://tempuri.org/";
		public static String URL = "http://"+ip.toString()+"/WebSite5/WebService.asmx";  
		public static String imgaddress ="http://"+ip.toString()+"/WebSite5/Uploads/";
		public static String urlServer = "http://"+ip.toString()+"/WebSite5/fileupload.aspx";
		public static String urlmap = "http://"+ip.toString()+"/";

		public static String clientusername,clientuid;

		public static String mypref="mypref";
		public static SharedPreferences pref,pref1;
		public Editor edit;	
		
	//public static String websiteURL = "http://192.168.2.104/hypenew/"; 
	public static String SOAP_ACTION = "http://tempuri.org/select";
	String METHOD_NAME = "select";
	Button login;
	TextView reg,forpass,about;
	EditText uname;
	EditText pass;
	EditText cpass;
	public static String user; 
	public static String uid,username;
	public static int srno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	 	setContentView(R.layout.login);				
		login = (Button)findViewById(R.id.button1);
		reg = (TextView)findViewById(R.id.link_signup);
		forpass = (TextView)findViewById(R.id.link_forgot);
		about = (TextView)findViewById(R.id.link_about);
		uname = (EditText) findViewById(R.id.editText1);
		pass = (EditText) findViewById(R.id.editText2);	
		user = uname.getText().toString();
		pref=getSharedPreferences(mypref, MODE_PRIVATE);
        edit=pref.edit();	
        String str=pref.getString("clientusername",clientusername);
        
        if(str!=null)
        {
        	Intent i1=new Intent(Login.this,Home.class);
			startActivity(i1);	
        }

		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				DoReg dr = new DoReg();
				dr.execute();
			}
		});		
		reg.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this,Register.class);
				startActivity(i);				
			}
		});
		/*
        */
		forpass.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this,Forpass.class);
				startActivity(i);				
			}
		});
		about.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this,About.class);
				startActivity(i);				
			}
		});	
		
	}
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS APPLICATION")
		        .setMessage("Are you sure you want to exit login?")
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
				
	class DoReg extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		protected String doInBackground(Void... params) 
		{
			try
			{			
				SoapObject request = new SoapObject("http://tempuri.org/","select");
				request.addProperty("username",uname.getText().toString());
				request.addProperty("password",pass.getText().toString());				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet=true;
				envelope.setOutputSoapObject(request);				
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);				
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
			pdia = new ProgressDialog(Login.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("Login...");
			pdia.show(); 
		}		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);				
			pdia.dismiss();				
			String data[]=result.split(",");
			if(data[0].equals("active"))
			{
				
				clientusername=uname.getText().toString();
				edit.putString("clientusername",clientusername);
				
			/*	clientuid=data[1].toString();
				edit.putString("clientuid",clientuid);*/
							
				edit.commit();
				
				
				
			uid= data[1];
			username = data[2];
			
			edit.putString("uid",uid.toString());
			edit.commit();
			
			
			Toast.makeText(Login.this, "Login Done", 2).show();	
			finish();
			Intent i1 = new Intent(Login.this,Home.class); 
			startActivity(i1);
			}
			else if(data[0].equals("deactive"))
			{
			Toast.makeText(Login.this, "varification", 2).show();	
			Intent i1 = new Intent(Login.this,Otp.class); 
			startActivity(i1);
			}
			else
			{
			Toast.makeText(Login.this, "Login not Done", 2).show();	
			}
		}
	}
}
