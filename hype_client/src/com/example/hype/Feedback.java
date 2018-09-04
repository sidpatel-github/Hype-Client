package com.example.hype;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hype.Delete.DoReg;
import com.example.hype.History1.product;
import com.squareup.picasso.Picasso;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class Feedback extends Activity {
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/feedback";
	RatingBar ratingBar;
	EditText feed;
	Button sent;
	private String rate;
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		feed = (EditText)findViewById(R.id.editText1);
		sent = (Button)findViewById(R.id.button1);
		ratingBar=(RatingBar)findViewById(R.id.ratingBar);
		
		image=(ImageView)findViewById(R.id.imageView1);
		Dooverloadfeed obj1=new Dooverloadfeed();
		obj1.execute();
		addListenerOnRatingBar();
		Login.uid = Login.pref.getString("uid",null);
		sent.setOnClickListener(new View.OnClickListener() 
		{
			@Override
				public void onClick(View v) {
				new AlertDialog.Builder(Feedback.this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("FEEDBACK HOARDING")
		        .setMessage("Are you sure you want to update hoarding?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	Dofeed obj=new Dofeed();
					obj.execute();
		        }
		    })
		    .setNegativeButton("No", null)
		    .show();
		}
});		
	}
		@Override
		public void onBackPressed() {
			    new AlertDialog.Builder(this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle("EXIT THIS FEEDBACK")
			        .setMessage("Are you sure you want to exit feedback?")
			        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
			    {
			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			           /* Intent i=new Intent(Feedback.this,Feedback1.class);
			            startActivity(i);*/finish();
			        }

			    })
			    .setNegativeButton("No", null)
			    .show();
			}
		

				
		class Dofeed extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
		
			protected String doInBackground(Void... params) 
			{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/","feedback");
				request.addProperty("uid",Login.uid);
				request.addProperty("hid",Feedbackcusadp.holdid);

				request.addProperty("rating",String.valueOf(ratingBar.getRating()));
				Log.d("rate",String.valueOf(rate));
				request.addProperty("feed",feed.getText().toString());
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/feedback", envelope);
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
				pdia = new ProgressDialog(Feedback.this);
				pdia.setCanceledOnTouchOutside(false);
				pdia.setMessage("feedbacking...");
				pdia.show(); 	
			}
			
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);				
				pdia.dismiss();			
				Toast.makeText(Feedback.this, "feedback Done", 2).show();
			/*	Intent i1= new Intent(Feedback.this,Feedback1.class);
				startActivity(i1);*/ finish();
			}
		}
		
		class Dooverloadfeed extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
		
			protected String doInBackground(Void... params) 
			{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/","overloadfeedback");
				request.addProperty("uid",Login.uid);
				request.addProperty("hid",Feedbackcusadp.holdid);
				request.addProperty("image",Feedbackcusadp.oimages);
				request.addProperty("rating",rate.toString());
				request.addProperty("feed",feed.getText().toString());
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/overloadfeedback", envelope);
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
				pdia = new ProgressDialog(Feedback.this);
				pdia.setCanceledOnTouchOutside(false);
				pdia.setMessage("feedback...");
				ratingBar.setRating(Float.parseFloat(Feedbackcusadp.orate));
				feed.setText(Feedbackcusadp.ofeed);
				Picasso.with(Feedback.this).load(Login.imgaddress+Feedbackcusadp.oimages).resize(100,100).into(image);
				pdia.show(); 	
			}
			
			protected void onPostExecute(String result) 
			{
				super.onPostExecute(result);				
				pdia.dismiss();	
			}
		}
		
		public void addListenerOnRatingBar() {
			ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,  boolean fromUser) {
			rate = String.valueOf(rating);
			}
			});
		  }
}
			
