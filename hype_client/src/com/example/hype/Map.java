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
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Map extends Activity {
	
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/lanlat";
	String METHOD_NAME = "lanlat"; 
	
	WebView viewinmap;
	//public double latitude=21.185171,longitude= 72.810376;
	public double latitude,longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		
		viewinmap = (WebView)findViewById(R.id.webView1);

		viewinmap.getSettings().setJavaScriptEnabled(true);
		String url = Login.urlmap.toString();

    	new DoMap().execute();
	/*
		String postData = "q=loc1-1.23-2.23-hello~loc2-2.23-3.23-hello";
		wv.postUrl(url,EncodingUtils.getBytes(postData, "BASE64"));
	*/
	}
	
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS MAP")
		        .setMessage("Are you sure you want to exit map?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		 /*           Intent i = new Intent(Map.this,Hoarding.class);
		            startActivity(i);*/		        	finish();

		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}	
	
		class DoMap extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
		
			protected String doInBackground(Void... params) 
			{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/","lanlat");
				
				request.addProperty("area",Hoarding.arealanlat);
				
				
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/lanlat", envelope);
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
				pdia = new ProgressDialog(Map.this);
				pdia.setCanceledOnTouchOutside(false);
				pdia.setMessage("opening map...");
				pdia.show(); 	
			}
			
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);				
				pdia.dismiss();	
				Log.e("jgshjdskkf", result.toString());
				String data1[]=result.split("`");
				longitude=Double.parseDouble(data1[1]);
				latitude=Double.parseDouble(data1[0]);
				String uri = "http://maps.google.com?q=" + latitude + "," + longitude;
				viewinmap.setWebViewClient(new webactivity());
				viewinmap.loadUrl(uri);

				/*
				String uri = "http://maps.google.com?q=" + latitude + "," + longitude;
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent); 
				*/
			}
		}
		class webactivity extends WebViewClient {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}

		}

		
	}


