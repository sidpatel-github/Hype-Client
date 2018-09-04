package com.example.hype;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;









import com.example.hype.Delete.DoReg;

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

public class Updatepass extends Activity{

	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/uppassword";
	private static final String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&]).{6,20})";

	Button update;
	EditText user,npass,cnpass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepass);
		
		//user=(EditText)findViewById(R.id.editText1);
		npass=(EditText)findViewById(R.id.editText2);
		cnpass=(EditText)findViewById(R.id.editText3);
		update=(Button)findViewById(R.id.button1);
	
		Login.uid = Login.pref.getString("uid",null);

		npass.addTextChangedListener(new TextWatcher() {

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
						
					if(npass.getText().toString().matches(pattern))
					{
						npass.setError(null);
					}
					else
					{
						npass.setError("password must contains one digit, one lowercase characters, one uppercase characters, one special symbols like @#$% and lenght btw 6 to 20");
					}								
					}});
								
		cnpass.addTextChangedListener(new TextWatcher() {
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
				if(cnpass.getText().toString().equals(npass.getText().toString()))
					{
					cnpass.setError(null);
					}
					else
					{
						cnpass.setError("con password field in empty or should match password ");
					}
				}});
		
		update.setOnClickListener(new View.OnClickListener() 
		{
	
			@Override
			public void onClick(View arg0) {
				if(!(npass.getText().toString().equals(cnpass.getText().toString())))
				 {
					 Toast.makeText(Updatepass.this,"password and confirm password must match", 2000).show();
				 }
				else if((npass.getText().toString().trim().length()==0) && (cnpass.getText().toString().trim().length()==0))
				{
					 Toast.makeText(Updatepass.this,"blank field", 2000).show();			
				}
				else 
				{
					new AlertDialog.Builder(Updatepass.this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle("UPDATE PASSWORD")
			        .setMessage("Are you sure you want to update password?")
			        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			    {
			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			        	DoReguppass dr = new DoReguppass();
						dr.execute();				        }

			    })
			    .setNegativeButton("No", null)
			    .show();
				}}
	});		

	}
		
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS PASSWORD")
		        .setMessage("Are you sure you want to exit update?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        /*    Intent i=new Intent(Updatepass.this,Home.class);
		            startActivity(i);*/
		        	finish();
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}		
		class DoReguppass extends AsyncTask<Void, Void, String>
{
private ProgressDialog pdia;
protected String doInBackground(Void... params) 
{
	try
	{			
		SoapObject request = new SoapObject("http://tempuri.org/","uppassword");
	//	request.addProperty("username",user.getText().toString());
	    request.addProperty("password",npass.getText().toString());
        request.addProperty("conpassword",cnpass.getText().toString());
        request.addProperty("uid",Login.uid);
        
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet=true;
		envelope.setOutputSoapObject(request);				
		HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);				
		androidHttpTransport.call("http://tempuri.org/uppassword", envelope);				
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
	pdia = new ProgressDialog(Updatepass.this);
	pdia.setCanceledOnTouchOutside(false);
	pdia.setMessage("updating...");
	pdia.show(); 
}		
protected void onPostExecute(String result) {
	super.onPostExecute(result);				
	pdia.dismiss();		
	if(result.toString().equals("true"))
	{
		Toast.makeText(Updatepass.this, "updated", 2).show();	
		finish();
	/*	Intent i1 = new Intent(Updatepass.this,Home.class); 
		startActivity(i1);*/
	}
	else
	{
		Toast.makeText(Updatepass.this, "username wrong", 2).show();	
	}
}}


}
