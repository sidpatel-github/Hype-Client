package com.example.hype;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hype.History1.product;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Historyfull extends Activity{
	
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/select";
	String METHOD_NAME = "select"; 
	
	
	TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
	ImageView i1;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historyfull);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		t3=(TextView)findViewById(R.id.textView3);
		t4=(TextView)findViewById(R.id.textView4);
		//t5=(TextView)findViewById(R.id.textView5);
		t6=(TextView)findViewById(R.id.textView6);
		t7=(TextView)findViewById(R.id.textView7);
		t8=(TextView)findViewById(R.id.textView8);
		t9=(TextView)findViewById(R.id.textView9);
		t10=(TextView)findViewById(R.id.textView110);
		Login.uid = Login.pref.getString("uid",null);
		i1=(ImageView)findViewById(R.id.imageView1);
		new Dohistory().execute();

//		t1.setText("WELCOME " + Login.username.toUpperCase());
	}
	 
	 
	 @Override
		public void onBackPressed() {
			    new AlertDialog.Builder(this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle("EXIT THIS HISTORY")
			        .setMessage("Are you sure you want to exit history?")
			        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			    {
			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			         /*   Intent i=new Intent(Historyfull.this,History1.class);
			            startActivity(i);*/finish();
			        }

			    })
			    .setNegativeButton("No", null)
			    .show();
			}
	 class Dohistory extends AsyncTask<Void, Void, String>
	 {
	 	private ProgressDialog pdia;

	 	protected String doInBackground(Void... params) 
	 	{
	 	try
	 	{			
	 	SoapObject request = new SoapObject("http://tempuri.org/","historyfetch");
	     request.addProperty("uid",Login.uid);
	     request.addProperty("hid",Historycusadp.hisid);
	     SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	     envelope.dotNet=true;
	     envelope.setOutputSoapObject(request);
	     HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	     androidHttpTransport.call("http://tempuri.org/historyfetch", envelope);
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
			pdia = new ProgressDialog(Historyfull.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("History details...");
			t1.setText("TYPE	:  "+Historycusadp.hty);
			t2.setText("SIZE	:  "+Historycusadp.hsiz);
			t3.setText("BOOKDATE:  "+Historycusadp.hbd);
			t4.setText("EXPIREDATE:  "+Historycusadp.hed);
			//t5.setText("ADDRESS  :  "+Historycusadp.hadd);
			t6.setText("AREA	:  "+Historycusadp.harea);
			t7.setText("CITY	:  "+Historycusadp.hcity);
			t8.setText("STATE	:  "+Historycusadp.hstate);
			t9.setText("STATUS	:  "+Historycusadp.hstat);
			t10.setText("PRICE	:  "+Historycusadp.hprice);
			
			Picasso.with(Historyfull.this).load(Login.imgaddress+Historycusadp.himg).resize(100,100).into(i1);
			
			pdia.show(); 
	 	}
	 	
	 	protected void onPostExecute(String result) 
	 	{
	 		super.onPostExecute(result);		
	 		pdia.dismiss();
	 	}
	 		
	 	}
	 }	