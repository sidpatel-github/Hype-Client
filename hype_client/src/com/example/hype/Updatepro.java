package com.example.hype;

import java.security.PublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;









import com.example.hype.Delete.DoReg;
import com.example.hype.Feedback.Dooverloadfeed;
import com.squareup.picasso.Picasso;






//import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Updatepro extends Activity {
	public int p=0,cp=0,e=0,ph=0;
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/upprofile";
	String METHOD_NAME = "insert"; 
	public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
	
	EditText name,username,password,conpassword,address,phoneno,email,deviceid,usertype;
	Spinner occupation;
	
	Button sub;
	
	//SQLiteDatabase DB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepro);
		Login.uid = Login.pref.getString("uid",null);
		name = (EditText)findViewById(R.id.editText1);
		username = (EditText)findViewById(R.id.editText2);
		occupation = (Spinner)findViewById(R.id.spinner6);
		address = (EditText)findViewById(R.id.editText7);
		phoneno = (EditText)findViewById(R.id.editText8);
		email = (EditText)findViewById(R.id.editText9);
		sub = (Button)findViewById(R.id.button1);
		Dooverloadprof obj1=new Dooverloadprof();
		obj1.execute();
		
		name.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				// TODO Auto-generated method stub
				if(name.getText().toString().trim().length()>0)
				{
					name.setError(null);
				}
				else
				{
					name.setError("name field in empty");
				}
				
			}	});
					
		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {
				// TODO Auto-generated method stub
				if(username.getText().toString().trim().length()>0)
				{
					username.setError(null);
				}
				else
				{
					username.setError("username field in empty");
				}
				
			}});
	
		
										
		address.addTextChangedListener(new TextWatcher() {
								@Override
				public void afterTextChanged(Editable s) {
									// TODO Auto-generated method stub	
					}

									@Override
				public void beforeTextChanged(
						CharSequence s, int start,int count, int after) {
										// TODO Auto-generated method stub							
						}

									@Override
				public void onTextChanged(CharSequence s,int start, int before, int count) {
					if(address.getText().toString().trim().length()>0)
					{
					address.setError(null);
					}
					else
					{
					address.setError("address field in empty");
					}	
				}});
				
		phoneno.addTextChangedListener(new TextWatcher() {

						@Override
				public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
							}
										@Override
				public void beforeTextChanged(
								CharSequence s, int start,int count, int after) {
							// TODO Auto-generated method stub
							
						}

							@Override
				public void onTextChanged(
						CharSequence s, int start,int before, int count) {
						if(phoneno.getText().toString().trim().length()==10)
							{
									phoneno.setError(null);
							}
								else 
							{
							phoneno.setError("phone field in empty or should be 10 digit");
							}
							}
							// TODO Auto-generated method stub
					});
									
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
								
					if (email.getText().toString().matches(EMAIL_PATTERN)) 
					{
					email.setError(null);
					}
					 else// 	if(email.getText().toString().trim().length()>0)
								{
								email.setError("email field in empty or Invalid Email abc@xyz.com");
								}	
						 }});

		
	
		
			
		sub.setOnClickListener(new View.OnClickListener() {
			
			//@SuppressLint("ShowToast") @Override
			public void onClick(View arg0) {
			
				
				if(name.getText().toString().equals("") || username.getText().toString().equals("") || address.getText().toString().equals("") || phoneno.getText().toString().equals("") || email.getText().toString().equals("")  )
				{
				Toast.makeText(Updatepro.this,"field r empty", 2000).show();
				}
				else if(phoneno.getText().toString().trim().length()!=10 || !email.getText().toString().matches(EMAIL_PATTERN)) 
				{
				Toast.makeText(Updatepro.this,"email or phone pattern does not match", 2000).show();
				}
				else
				{
				    new AlertDialog.Builder(Updatepro.this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle("UPDATE ACCOUNT")
			        .setMessage("Are you sure you want to update account?")
			        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			    {
			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			        	DoReg obj=new DoReg();
						obj.execute();				        }

			    })
			    .setNegativeButton("No", null)
			    .show();
			}
				
				}
		});
	}
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS PROFILE")
		        .setMessage("Are you sure you want to exit update?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		           /* Intent i=new Intent(Updatepro.this,Home.class);
		            startActivity(i);*/ finish();
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}		
	
	class Dooverloadprof extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","overloadprof");
			request.addProperty("uid",Login.uid);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/overloadprof", envelope);
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
			pdia = new ProgressDialog(Updatepro.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("update profile...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);	
			pdia.dismiss();	
			String data[]=result.split(",");
			username.setText(data[1]);
			username.setTextColor(Color.RED);
			name.setText(data[2]);
		
			for(int i =0;i<occupation.getCount();i++)
			{
				
				if(occupation.getItemAtPosition(i).equals(data[3]))
				{
					
					occupation.setSelection(i);
					
				}
			}

			occupation.setEnabled(false);
			address.setText(data[4]);
			phoneno.setText(data[5]);
			email.setText(data[6]);
		}
	}
	
	
				
class DoReg extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","upprofile");
			request.addProperty("uid",Login.uid);
        	//request.addProperty("usertype",usertype.getText().toString());
			request.addProperty("name",name.getText().toString());
			request.addProperty("username",username.getText().toString());
	        //request.addProperty("occupation",occupation.getSelectedItem().toString());
	        request.addProperty("address",address.getText().toString());
	        request.addProperty("phoneno",phoneno.getText().toString());
	        request.addProperty("email",email.getText().toString());
	        
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/upprofile", envelope);
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
			pdia = new ProgressDialog(Updatepro.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("updating...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);				
			pdia.dismiss();			
			Toast.makeText(Updatepro.this, "update Done", 2).show();
			finish();
		/*	Intent i1= new Intent(Updatepro.this,Home.class);
			startActivity(i1);*/
			
		}
	}
}


		
