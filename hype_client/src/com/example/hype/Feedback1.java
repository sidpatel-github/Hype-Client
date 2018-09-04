package com.example.hype;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class Feedback1 extends ActionBarActivity {
	public static String NAMESPACE = "http://tempuri.org/";
	 ListView l1;
	ArrayList<product> p1 = new ArrayList<product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback1);
        Login.uid = Login.pref.getString("uid",null);
        l1 =(ListView) findViewById(R.id.listView1);
       new DoReg().execute();
        
   }

class product 
{
String hid;
String date;
String area;
String feed;
String rate;
String image;
}/*
@Override
public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("EXIT THIS LIST")
	        .setMessage("Are you sure you want to exit list of feedback?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            /*Intent i=new Intent(Feedback1.this,Home.class);
	            startActivity(i);*//*finish();
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}
*/

class DoReg extends AsyncTask<Void, Void, String>
{
	private ProgressDialog pdia;

	protected String doInBackground(Void... params) 
	{
	try
	{			
	SoapObject request = new SoapObject("http://tempuri.org/","feed");
	
    request.addProperty("uid",Login.uid);
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
        androidHttpTransport.call("http://tempuri.org/feed", envelope);
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
	}
	
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);		
		Log.d("data",result.toString());
		if(result.toString().equals("false"))
		{
			Toast.makeText(Feedback1.this,"sorry no feedback", 2000).show();
		}
		else
		{
		String rows[]=result.split("::");
		p1.clear();
		for(int i =0;i<rows.length;i++)
		{			
		String cols[]=rows[i].split(",,");
		product pobj = new product();
		pobj.hid=cols[1];
		pobj.date=cols[3];
		pobj.area=cols[4];
		pobj.image=cols[5];
		pobj.rate=cols[6];
		pobj.feed=cols[7];
		p1.add(pobj);
		
		}
		Feedbackcusadp c2 = new Feedbackcusadp(p1,Feedback1.this);
        l1.setAdapter(c2);
        
	}}
}	
}