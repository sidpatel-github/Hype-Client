package com.example.hype;

import java.util.ArrayList;

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
import android.widget.ListView;
import android.widget.Toast;

public class Review1 extends Activity{
	
	ArrayList<product> p1 = new ArrayList<product>();
	ListView l1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewreview);
	    l1 = (ListView) findViewById(R.id.listView1);
		DoReg dr = new DoReg();
		dr.execute();
	}
	/*
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS REVIEW")
		        .setMessage("Are you sure you want to exit review?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		         /*   Intent i=new Intent(Review1.this,Review.class);
		            startActivity(i);*//*    	finish();

		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}*/
	 class DoReg extends AsyncTask<Void, Void, String>
	{
		
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
			if(Review.flag==1)
			{
				SoapObject request = new SoapObject("http://tempuri.org/","reviewbyarea");
				request.addProperty("area",Review.areaname);
				request.addProperty("city",Review.cityname);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/reviewbyarea", envelope);
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        return resultstr.toString();	
			}
			else if(Review.flag==2)
			{
				SoapObject request = new SoapObject("http://tempuri.org/","reviewbybanner");
				request.addProperty("banner",Review.bannertype);
				request.addProperty("city",Review.cityname);
				request.addProperty("state",Review.statename);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/reviewbybanner", envelope);
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        return resultstr.toString();	
			}
			else
			{
				SoapObject request = new SoapObject("http://tempuri.org/","reviewbyrating");
				request.addProperty("rating",Review.ratingvalue);
				request.addProperty("city",Review.cityname);
				request.addProperty("state",Review.statename);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/reviewbyrating", envelope);
		        SoapPrimitive resultstr = (SoapPrimitive)envelope.getResponse();
		        return resultstr.toString();	
			}
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
			pdia = new ProgressDialog(Review1.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("processing...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);				
			pdia.dismiss();						
			
		if(result.equals("falsearea"))
			{
			   Toast.makeText(Review1.this, "no review is available for selected  area", 500000).show();	
			   finish();
			}
		 else if(result.equals("falsebanner"))
			{
		       Toast.makeText(Review1.this, "no review is available for selected banner", 500000).show();	
			   finish();
			}
			
		 else if(result.equals("falserating"))
		{
		    Toast.makeText(Review1.this, "no review is available for selected rating", 500000).show();	
			finish();
		}
		
		 else if(result.equals("false"))
		 {
			Toast.makeText(Review1.this, "no review is available currently at "+Review.cityname, 200000).show();	
			finish();
				
		 }
		
		else
		{
			String arr1[]=result.split("#");				
			
			for(int i =0;i<arr1.length;i++)
			{
			product p2 = new product();
			String arr2[]=arr1[i].split(",,");

			p2.r_username=arr2[0];
			p2.r_btype=arr2[1];
			p2.r_area=arr2[2];
			p2.r_rating=arr2[3];
			p2.r_feed=arr2[4];
			p1.add(p2);
//			p2.equals(null);
			}
			
		Reviewcustomaddapter c1 = new Reviewcustomaddapter (p1, Review1.this);
		l1.setAdapter(c1);
	

		}
	
	  }
	}		
	 

public class product 
{
String r_username;
String r_btype;
String r_area;
String r_rating;
String r_feed;
}

}
