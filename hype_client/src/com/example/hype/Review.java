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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class Review extends Activity{

	public static String statename,cityname,areaname,bannertype,ratingvalue;
	Spinner state, city, area,btype,rating;
	Button bfeedback;
	RadioGroup radioGroup;
	public static int flag=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review);
		
		state = (Spinner) findViewById(R.id.mySpinner1);
		city = (Spinner) findViewById(R.id.mySpinner2);
		area = (Spinner) findViewById(R.id.mySpinner3);
		btype=  (Spinner) findViewById(R.id.mySpinner4);
		rating= (Spinner) findViewById(R.id.mySpinner5);
		bfeedback=(Button)findViewById(R.id.bcommon);
		radioGroup=(RadioGroup)findViewById(R.id.radioGroup1);
		new doban().execute();
		new dostate().execute();
		 btype.setEnabled(false);
		 rating.setEnabled(false);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 RadioButton rb=(RadioButton)group.findViewById(checkedId);
		         
				 if(rb.getText().equals("Area"))
				 {	
					 flag=1;
					 area.setEnabled(true);
					 btype.setEnabled(false);
					 rating.setEnabled(false);
				 }
				 else if(rb.getText().equals("Banner Type"))
				 {
					 flag=2;
					 area.setEnabled(false);
					 btype.setEnabled(true);
					 rating.setEnabled(false);
				 }
				 else
				 {
					 flag=3;
					 area.setEnabled(false);
					 btype.setEnabled(false);
					 rating.setEnabled(true);
				 }
			}
		});
		
		
		bfeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Review.this,Review1.class);
				startActivity(i);		
			}
		});
		
		
		state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {

			public void onItemSelected(AdapterView<?> noAdapterView,
					View selectedItemView, int pos, long id) {
				// TODO Auto-generated method stub
				statename = state.getSelectedItem().toString();
				new docity().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}

		});
		
		city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView,
					View selectedItemView, int pos, long id) {
				// TODO Auto-generated method stub
				cityname = city.getSelectedItem().toString();
				// Toast.makeText(Hoarding.this, ii3.toString(),2000).show();
				new doarea().execute();
//				new doban().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			
			@Override
			public void onItemSelected(AdapterView<?> noAdapterView,
					View selectedItemView, int pos, long id) {
				// TODO Auto-generated method stub
				areaname = area.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub			
			}
		});
		
		btype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView,
					View selectedItemView, int pos, long id) {
				// TODO Auto-generated method stub
				bannertype = btype.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		rating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ratingvalue=rating.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}
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
		          /*  Intent i=new Intent(Review.this,Home.class);
		            startActivity(i);*/  finish();
		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}
//====================state=======================
	class dostate extends AsyncTask<Void, Void, String> {
		private ProgressDialog pdia;

		protected String doInBackground(Void... params) {
			try {
				SoapObject request = new SoapObject("http://tempuri.org/","state");
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
				androidHttpTransport.call("http://tempuri.org/state", envelope);
				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();
				return resultstr.toString();
			} catch (Exception e) {
				Log.e("error", e.toString());
				return e.toString();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(Review.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("state loading......");
			pdia.show();
		}

		protected void onPostExecute(String result) {
			String data1[] = result.split(":");
			super.onPostExecute(result);
			Log.d("result16", result);
			ArrayList<String> data = new ArrayList<String>();
			for (int i = 0; i < data1.length; i++)
				data.add(data1[i]);
			ArrayAdapter NoCoreAdapter = new ArrayAdapter(Review.this,android.R.layout.simple_spinner_dropdown_item, data);
			state.setAdapter(NoCoreAdapter);
			pdia.dismiss();

		}
	}
//====================city=========================
	class docity extends AsyncTask<Void, Void, String> {
		private ProgressDialog pdia;

		protected String doInBackground(Void... params) {
			try {
				SoapObject request = new SoapObject("http://tempuri.org/","city");
				request.addProperty("state", statename.toString());
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
				androidHttpTransport.call("http://tempuri.org/city", envelope);
				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();
				return resultstr.toString();
			} catch (Exception e) {
				Log.e("error", e.toString());
				return e.toString();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(Review.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("city loading......");
			pdia.show();
		}

		protected void onPostExecute(String result) {
			String data1[] = result.split(":");
			super.onPostExecute(result);
			Log.d("result17", result);
			ArrayList<String> data = new ArrayList<String>();
			for (int i = 0; i < data1.length; i++)
				data.add(data1[i]);
			ArrayAdapter NoCoreAdapter = new ArrayAdapter(Review.this,android.R.layout.simple_spinner_dropdown_item, data);
			city.setAdapter(NoCoreAdapter);
			pdia.dismiss();

		}
	}
//====================area==========================
	class doarea extends AsyncTask<Void, Void, String> {
		private ProgressDialog pdia;

		protected String doInBackground(Void... params) {
			try {
				SoapObject request = new SoapObject("http://tempuri.org/","feedbackarea");
				request.addProperty("city",cityname);
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
				androidHttpTransport.call("http://tempuri.org/feedbackarea", envelope);
				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();
				return resultstr.toString();
			} catch (Exception e) {
				Log.e("error", e.toString());
				return e.toString();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(Review.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("area loading......");
			pdia.show();
		}

		protected void onPostExecute(String result) {
			String data1[] = result.split(":");
			super.onPostExecute(result);
			Log.d("result18", result);
			ArrayList<String> data = new ArrayList<String>();
			for (int i = 0; i < data1.length; i++)
				data.add(data1[i]);
			ArrayAdapter NoCoreAdapter = new ArrayAdapter(Review.this,android.R.layout.simple_spinner_dropdown_item, data);
			area.setAdapter(NoCoreAdapter);
			pdia.dismiss();
		}
	}
//====================banner type==========================
	class doban extends AsyncTask<Void, Void, String> {
		private ProgressDialog pdia;

		protected String doInBackground(Void... params) {
			try {
				SoapObject request = new SoapObject("http://tempuri.org/","banner");
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
				androidHttpTransport.call("http://tempuri.org/banner", envelope);
				SoapPrimitive resultstr = (SoapPrimitive) envelope.getResponse();
				return resultstr.toString();
			} catch (Exception e) {
				Log.e("error", e.toString());
				return e.toString();
			}
		}

		protected void onPreExecute() {
			super.onPreExecute();
			pdia = new ProgressDialog(Review.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("banner loading...");
			pdia.show();
		}

		protected void onPostExecute(String result) {
			String data1[] = result.split(":");
			super.onPostExecute(result);
			Log.d("result15", result);

			ArrayList<String> data = new ArrayList<String>();
			for (int i = 0; i < data1.length; i++)
				data.add(data1[i]);
			ArrayAdapter NoCoreAdapter = new ArrayAdapter(Review.this,android.R.layout.simple_spinner_dropdown_item, data);
			btype.setAdapter(NoCoreAdapter);
			pdia.dismiss();
		}
	}
}
