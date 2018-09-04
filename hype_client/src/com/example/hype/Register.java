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

import android.R.string;
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
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {
	public int p=0,cp=0,e=0,ph=0;
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/insert";
	String METHOD_NAME = "insert"; 
	
	EditText name,username,password,conpassword,address,phoneno,email,usertype;
	Spinner occupation;
	Button sub;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	
	private static final String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{6,20})";
	//SQLiteDatabase DB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		name = (EditText)findViewById(R.id.editText1);
		username = (EditText)findViewById(R.id.editText2);
		password = (EditText)findViewById(R.id.editText3);
		conpassword = (EditText)findViewById(R.id.editText4);
		occupation = (Spinner)findViewById(R.id.spinner6);
		address = (EditText)findViewById(R.id.editText7);
		phoneno = (EditText)findViewById(R.id.editText8);
		email = (EditText)findViewById(R.id.editText9);
		sub = (Button)findViewById(R.id.button1);
		
		
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
	
		password.addTextChangedListener(new TextWatcher() {

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
						
					if(password.getText().toString().matches(pattern))
					{
						password.setError(null);
					}
					else
					{
						password.setError("password must contains one digit, one lowercase characters, one uppercase characters, one special symbols like @#$% and lenght btw 6 to 20");
					}								
					}});
								
		conpassword.addTextChangedListener(new TextWatcher() {
						@Override
				public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
					}
						@Override
				public void beforeTextChanged(CharSequence s,int start, int count, int after) {
						// TODO Auto-generated method stub
						}

						@Override
				public void onTextChanged(CharSequence s, int start,int before, int count) {
							// TODO Auto-generated method stub
				if(conpassword.getText().toString().equals(password.getText().toString()))
					{
						conpassword.setError(null);
					}
					else
					{
					conpassword.setError("con password field in empty or should match password ");
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
			
				
	if(name.getText().toString().equals("") || username.getText().toString().equals("") || password.getText().toString().equals("") || address.getText().toString().equals("") || phoneno.getText().toString().equals("") || email.getText().toString().equals("")  )
	{
	Toast.makeText(Register.this,"field r empty", 2000).show();
	}
	else if(!conpassword.getText().toString().equals(password.getText().toString()))
	{
		Toast.makeText(Register.this,"confirm password field not match with password", 2000).show();
	}
	else if(!password.getText().toString().matches(pattern) || phoneno.getText().toString().trim().length()!=10 || !email.getText().toString().matches(EMAIL_PATTERN))
	{
		Toast.makeText(Register.this,"required pattern for password , email of phone is wrong", 2000).show();
	}
	else
	{
		new AlertDialog.Builder(Register.this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("REGISTER ACCOUNT")
        .setMessage("Are you sure you want to create account?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
        	DoReg dr = new DoReg();
			dr.execute();				        
		}
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
		        .setTitle("EXIT THIS REGISTRATION")
		        .setMessage("Are you sure you want to exit register?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            Intent i=new Intent(Register.this,Login.class);
		            startActivity(i);//	        	finish();

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
		SoapObject request = new SoapObject("http://tempuri.org/","insert");
			
			request.addProperty("name",name.getText().toString());
			request.addProperty("username",username.getText().toString());
	        request.addProperty("password",password.getText().toString());
	        //request.addProperty("conpassword",conpassword.getText().toString());
	        request.addProperty("occupation",occupation.getSelectedItem().toString());
	        request.addProperty("address",address.getText().toString());
	        request.addProperty("phoneno",phoneno.getText().toString());
	        request.addProperty("email",email.getText().toString());
	        
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/insert", envelope);
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
			pdia = new ProgressDialog(Register.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("registering...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);				
			pdia.dismiss();		
			if((result.equals("true")))
			{
			Toast.makeText(Register.this, "register Done", 2).show();
			Intent i1= new Intent(Register.this,Login.class);
			startActivity(i1);
			}
			else
			{
			Toast.makeText(Register.this, " username and email already used  please choose other", 2).show();
			}
		}
	}
	
}
		