package com.example.hype;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.hype.Chatting.chaton;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.support.v7.app.ActionBarActivity;
//import android.support.v7.internal.widget.AdapterViewICS.OnItemSelectedListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Hoarding extends ActionBarActivity {

	private Calendar calendar;
	private DatePickerDialog bDatePickerDialog;
    private DatePickerDialog eDatePickerDialog;
    private SimpleDateFormat dateFormatter;
	
    public static String NAMESPACE = "http://tempuri.org/";
    public static String SOAP_ACTION = "http://tempuri.org/bookhoarding";
    String METHOD_NAME = "bookhoarding"; 
    ListView areanot;
	ArrayList<areaon> p1 = new ArrayList<areaon>();
    private static final int SELECT_PICTURE = 1;
    public long da,daa;
    private String seimg,imgname;
	public static String hid,ii,ii1,ii2,ii3,ii4,ii5;
	public static String arealanlat;
    TextView path,bdate,edate;
	Spinner btype,bsize,state,city,area;
	EditText price;
	Button chooseimage,uploadimage,book,bt,bt1,viewinmap;
	long days,bookdays;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hoarding);	
		
		Login.uid = Login.pref.getString("uid",null);
		
		btype=(Spinner)findViewById(R.id.spinner1);
		bsize=(Spinner)findViewById(R.id.spinner22);
		bdate=(TextView)findViewById(R.id.textviewb);
		edate=(TextView)findViewById(R.id.textviewe);
		state=(Spinner)findViewById(R.id.mySpinner1);
		city=(Spinner)findViewById(R.id.mySpinner2);
		area=(Spinner)findViewById(R.id.mySpinner3);
		price=(EditText)findViewById(R.id.editText1);
	//	state.setItems(getResources().getStringArray(R.array.state_array));
    //  city.setItems(getResources().getStringArray(R.array.city_array));
	//	area.setItems(getResources().getStringArray(R.array.area_array));
	    
		chooseimage=(Button)findViewById(R.id.button9c);
		path=(TextView)findViewById(R.id.textView9p);
		book=(Button)findViewById(R.id.button1);
		viewinmap=(Button)findViewById(R.id.buttonmap);
		areanot=(ListView)findViewById(R.id.listView1);
		new doban().execute();
		
		calendar = Calendar.getInstance();
	    calendar.get(Calendar.YEAR);
	    calendar.get(Calendar.MONTH);
	    calendar.get(Calendar.DAY_OF_MONTH);
	    dateFormatter = new SimpleDateFormat("MMM dd,yyyy", Locale.US);
	   
	    bdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				showDialog(1);
			}
		});
	    
	    edate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showDialog(2);
			}
		});
	      
	    viewinmap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Hoarding.this,Map.class);
				startActivity(intent);
				
			}
		});

	    
      chooseimage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
                intent.setType("image/*");
                Log.d("resultbook1", "sdad");
                Toast.makeText(Hoarding.this,"select from only galary",10000).show();
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent,SELECT_PICTURE);
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});
      
      book.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				try {
					calcdays();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					calcbookdays();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				if(area.toString().equals("========banner not free=======") || area.toString().equals("=========banner free=========") )
				{
					Toast.makeText(Hoarding.this,"u must select proper area", 2000).show();
				}
				
				else if(btype.toString().equals("") || bsize.toString().equals("") || bdate.getText().toString().equals("") || edate.getText().toString().equals("") || path.getText().toString().equals("") ||bdate.getText().toString().equals("") ||state.toString().equals("") ||city.toString().equals("") ||area.toString().equals("avalible only for surat"))
				{
					Toast.makeText(Hoarding.this,"field r empty", 2000).show();
				}
				else if(bookdays >=30)
				{
					Toast.makeText(Hoarding.this,"booking date should be within 30 days from now", 2000).show();
				}	
				else if(days >= 30  && days < 32)
				{
					    Log.d("resultbook2", "sdad");

						new AlertDialog.Builder(Hoarding.this)
				        .setIcon(android.R.drawable.ic_dialog_alert)
				        .setTitle("BOOKING HOARDING")
				        .setMessage("Are you sure you want to book hoarding?")
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
				    {
				        @Override
				        public void onClick(DialogInterface dialog, int which) {
				        	new douploaddata().execute();
				        	}
				    })
				    .setNegativeButton("No", null)
				    .show();
				}
				else if(days<-1)
				{
					Toast.makeText(Hoarding.this,"invalid date", 2000).show();
				}
				else
				{
					Toast.makeText(Hoarding.this,"banner avalable for 30 days", 2000).show();
				}
				}
});	
      
      
     btype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView , View selectedItemView,int pos, long id) {
				// TODO Auto-generated method stub
				
				ii = btype.getSelectedItem().toString();
			//	Toast.makeText(Hoarding.this, ii.toString(),2000).show();				
				//dobid.execute();	
			//	new dosize().execute();
		     	new dostate().execute();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	
    });    
      

     
     bsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView , View selectedItemView,int pos, long id) {
				// TODO Auto-generated method stub
				
				String ii2 = bsize.getSelectedItem().toString();
			//	Toast.makeText(Hoarding.this, iit.toString(),2000).show();				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	
 });    

     
     
      state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView , View selectedItemView,int pos, long id) {
				// TODO Auto-generated method stub
				ii1 = state.getSelectedItem().toString();
			//	Toast.makeText(Hoarding.this, ii1.toString(),2000).show();
				new docity().execute();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	
      });  
      
      
      city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView , View selectedItemView,int pos, long id) {
				// TODO Auto-generated method stub
				ii3 = city.getSelectedItem().toString();
			//	Toast.makeText(Hoarding.this, ii3.toString(),2000).show();
				new doarea().execute();
				new doareafetch().execute();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	
      });            

      
      area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> noAdapterView , View selectedItemView,int pos, long id) {
				// TODO Auto-generated method stub
				ii4 = area.getSelectedItem().toString();
				Log.d("ii4",area.getSelectedItem().toString());
				arealanlat= ii4.toString();
			//	Toast.makeText(Hoarding.this, ii4.toString(),2000).show();
				if(ii4.equals("avalible only for surat") || ii4.equals("========area not free=======") ||ii4.equals("=========area free=========") )
				{
					viewinmap.setEnabled(false);
					String data1="";
					ArrayList<String> data = new ArrayList<String>();
					data.add(data1);
					ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
			        bsize.setAdapter(NoCoreAdapter);
			       // price.setText("");
				}
				else
				{
				viewinmap.setEnabled(true);
				
				new dosize().execute();
				}//new doaddr().execute();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
    });      
      
      
      //==============================================================================================================================================
      bsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			ii5 = bsize.getSelectedItem().toString();
			// TODO Auto-generated method stub
			if(ii5.equals(""))
			{
				price.setText("");
			}
		/*	else if(bdate.getText().toString().equals("") || edate.getText().toString().equals(""))
			{
				price.setText(text);
			}*/
			else
			{
			new doprice().execute();
			
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	});
       
  		
  	}

      //===============================================================================================================================================
	
	@Override
	public void onBackPressed() {
		    new AlertDialog.Builder(this)
		        .setIcon(android.R.drawable.ic_dialog_alert)
		        .setTitle("EXIT THIS BOOKING")
		        .setMessage("Are you sure you want to exit booking?")
		        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
		    {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		       /*     Intent i=new Intent(Hoarding.this,Home.class);
		            startActivity(i);*/		        	finish();

		        }

		    })
		    .setNegativeButton("No", null)
		    .show();
		}		
	private void calcdays() throws Exception
	{
		// TODO Auto-generated method stub
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
  		try{
  			
  			Date expirydate=sd.parse(edate.getText().toString());
  			Date bookingdate=sd.parse(bdate.getText().toString());
  			Calendar excal = Calendar.getInstance();
  			excal.setTime(expirydate);
  			Calendar bookcal = Calendar.getInstance();
  			bookcal.setTime(bookingdate);
  			
  			long diff = excal.getTimeInMillis() - bookcal.getTimeInMillis();
  			days = diff/(24*60*60*1000);
  			Log.d("days",String.valueOf(days));
  			
  			
  		}
  		
  		 catch (ParseException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		//	return true;
  		}
   }
	
	private void calcbookdays() throws Exception
	{
		// TODO Auto-generated method stub
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
  		try{
  			
  			Date bookingdate=sd.parse(bdate.getText().toString());
  			Calendar bookcal = Calendar.getInstance();
  			bookcal.setTime(bookingdate);
  			
  			Calendar today = Calendar.getInstance();
  			
  			long diff = bookcal.getTimeInMillis()-today.getTimeInMillis() ;
  			bookdays = diff/(24*60*60*1000);
  			Log.d("bookingdate",String.valueOf(bookdays));
  			
  			
  		}
  		
  		 catch (ParseException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		//	return true;
  		}
   }
//complete===================================
	class areaon 
	{
		String area1;
	}
	class doareafetch extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","areafetch");
			request.addProperty("state",ii1.toString());
			request.addProperty("uid",Login.uid);
			request.addProperty("city",ii3.toString());
			request.addProperty("bann",ii.toString());
	        
			//request.addProperty("msg",msg.getText().toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/areafetch", envelope);
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
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("data running......");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);	
			Log.d("data",result.toString());
			
			String rows[]=result.split(":");
			p1.clear();
			for(int i=0;i<rows.length;i++)
			{
			Log.d("lengthc",String.valueOf(rows.length));
			areaon aobj = new areaon();
			aobj.area1=rows[i];
			p1.add(aobj);
			}
			Areacus c1 = new Areacus(p1,Hoarding.this);
	        areanot.setAdapter(c1);
	        pdia.dismiss();
            
		}
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =new DatePickerDialog.OnDateSetListener() 
	{
		public void onDateSet(DatePicker view, int yearSelected,int monthOfYear, int dayOfMonth) 
		{
		int	year = yearSelected;
		int	month = monthOfYear;
		int	day = dayOfMonth;
		int n_mon = monthOfYear+1;
		bdate.setText(day+"/"+n_mon+"/"+year);
		}
	};
	
	@Override
	protected Dialog onCreateDialog(int id) {
		int mYear = 0;
		int mMonth = 0;
		int mDay = 0;
		// TODO Auto-generated method stub
		if(id ==1)
		{
		DatePickerDialog da = new DatePickerDialog(this, mDateSetListener,mYear, mMonth, mDay);
		Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 7);
        Date newDate = c.getTime();
        da.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
        
        // da.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return da;
		}
		else
		{
			DatePickerDialog da1 = new DatePickerDialog(this, mDateSetListener1,mYear, mMonth, mDay);
	        Calendar c = Calendar.getInstance();
	        c.add(Calendar.DATE, 7);
	        Date newDate = c.getTime();
	        da1.getDatePicker().setMinDate(newDate.getTime()-(newDate.getTime()%(24*60*60*1000)));
	       
	        // da.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
	        return da1;
						
		}
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener1 =new DatePickerDialog.OnDateSetListener() 
	{
		public void onDateSet(DatePicker view, int yearSelected,int monthOfYear, int dayOfMonth) 
		{
		int	year = yearSelected;
		int	month = monthOfYear;
		int	day = dayOfMonth;
		int n_mon = monthOfYear+1;
		
		edate.setText(day+"/"+n_mon+"/"+year);
		
		}
		
	};
	
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);           
		if(requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK  )
		
		{ 			Log.d("resultbook3", "sdad");

				// result was ok            
				seimg = getAbsolutePath(data.getData());
	               File f1=new File(seimg);
	               imgname= f1.getName();
	   			Log.e("result1","");

	              	               
				Uri selectedImage = data.getData();            
				Intent i = new Intent(getApplicationContext(),ImageView.class); 
				i.putExtra("PICTURE_LOCATION", selectedImage.toString());
			
		
				path.setText(seimg);
				
				
				
				//+"a");
}

		}
	
	public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            cursor.moveToFirst();			
            Log.d("resultbook4", "sdad");
            return cursor.getString(column_index);
        } else
            return null;
    }
	
	
	
	class douploaddata extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;
	 
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary =  "*****";
		String FileName ; 
	 
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1*1024*1024;
	 		
		protected String doInBackground(Void... params) 
		{
			try
			{	
				       FileInputStream fileInputStream = new FileInputStream(new File(seimg));
				    
				       URL url = new URL(Login.urlServer);
				       connection = (HttpURLConnection) url.openConnection();
		               Log.d("resultbook5", "sdad");

				       // Allow Inputs &amp; Outputs.
				       connection.setDoInput(true);
				       connection.setDoOutput(true);
				       connection.setUseCaches(false);
				    
				       // Set HTTP method to POST.
				       connection.setRequestMethod("POST");
				    
				       connection.setRequestProperty("Connection", "Keep-Alive");
				       connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
				    
				       outputStream = new DataOutputStream( connection.getOutputStream() );
				       outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				       Log.d("resultbook6", "sdad");
				       outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + seimg +"\"" + lineEnd);
				       outputStream.writeBytes(lineEnd);
				    
				       bytesAvailable = fileInputStream.available();
				       bufferSize = Math.min(bytesAvailable, maxBufferSize);
				       buffer = new byte[bufferSize];
				    
				       // Read file
				       bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				    
				       while (bytesRead > 0)
				       {
				           outputStream.write(buffer, 0, bufferSize);
				           bytesAvailable = fileInputStream.available();
				           bufferSize = Math.min(bytesAvailable, maxBufferSize);
				           bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				           
				       }
				    
				       outputStream.writeBytes(lineEnd);
				       outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
				    
				       // Responses from the server (code and message)
				       int serverResponseCode = connection.getResponseCode();
				       String serverResponseMessage = connection.getResponseMessage();
				    
				       fileInputStream.close();
				       outputStream.flush();
				       outputStream.close();
				       return serverResponseMessage;
				       
				   }
				   catch (Exception ex)
				   {
					   return(ex.getMessage());
				       //Log.d("error", ex.getMessage());
				   }
		}
		

		protected void onPreExecute() 
		{
			super.onPreExecute();
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.setMessage("Uploading in...");
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);
			Log.d("resultbook7", result);
			pdia.dismiss();
			new Dobookhoarding().execute();
			
		}	
	}
	class Dobookhoarding extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","bookhoarding");
			request.addProperty("uid",Login.uid);
        	request.addProperty("btype",btype.getSelectedItem().toString());
			request.addProperty("size",bsize.getSelectedItem().toString());
			request.addProperty("bookdate",bdate.getText().toString());
	        request.addProperty("expiredate",edate.getText().toString());
	        request.addProperty("area",area.getSelectedItem().toString());
	        request.addProperty("city",city.getSelectedItem().toString());
	        request.addProperty("state",state.getSelectedItem().toString());
	        request.addProperty("price",price.getText().toString());
	        
	        request.addProperty("image",imgname.toString());
	        Log.d("uid",Login.uid + btype.getSelectedItem().toString() + bsize.getSelectedItem().toString() + bdate.getText().toString() + edate.getText().toString() + area.getSelectedItem().toString() + city.getSelectedItem().toString() + state.getSelectedItem().toString() + imgname.toString() + price.getText().toString() );
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/bookhoarding", envelope);
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
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("booking...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{	
			super.onPostExecute(result);	
			Log.d("rerer8",result);
			if(result.toString().contains("false"))
			{
				Toast.makeText(Hoarding.this,"sorry we cant book you hoarding",2000).show();
				Intent i= new Intent(Hoarding.this,Home.class);
				startActivity(i);
			}
			else	
			{
			String data1[]=result.split(",");
			Log.d("resultbook9",data1[1]);
			
			hid = data1[1];
			
			Log.d("resultbook9",hid);
			Log.d("resultbook10", result);
			pdia.dismiss();		
			Log.d("resultbook11", result);
			new Dostatus().execute();
			}

			//finish();
			
		}
	}

	class Dostatus extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","bookstatus");
			request.addProperty("uid",Login.uid);
			request.addProperty("hid",hid.toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/bookstatus", envelope);
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
			pdia=new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);
			
            Log.d("resultbook12", "sdad");

			pdia.dismiss();	
			Log.d("resultbook13", result);

			new Dofeedback().execute();
		}
	}
	class Dofeedback extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","bookfeedback");
			request.addProperty("uid",Login.uid);
			request.addProperty("hid",hid.toString());
	        request.addProperty("image",imgname.toString());
	        request.addProperty("banner",ii.toString());
	        request.addProperty("area",ii4.toString());
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/bookfeedback", envelope);
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
			pdia=new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
	        pdia.show(); 
		}
		
		protected void onPostExecute(String result) 
		{
			super.onPostExecute(result);
			
            Log.d("resultbook14", "sdad");
			pdia.dismiss();	
		/*	Intent i1= new Intent(Hoarding.this,Home.class);
			startActivity(i1);*/		        	finish();

		}
	}
		public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    
	
    class doban extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","banner");
	        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/banner", envelope);
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
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("data loading...");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{	
			String data1[]=result.split(":");
			super.onPostExecute(result);	
			Log.d("result15",result);
			
			ArrayList<String> data = new ArrayList<String>();
			for(int i=0;i<data1.length;i++)
			data.add(data1[i]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);            
            btype.setAdapter(NoCoreAdapter);       
            pdia.dismiss();
        	//new dostate().execute();

            
		}
	}   
    
    class dosize extends AsyncTask<Void, Void, String>
   	{
   		private ProgressDialog pdia;
   	
   		protected String doInBackground(Void... params) 
   		{
   		try
   		{			
   		SoapObject request = new SoapObject("http://tempuri.org/","size");
   	        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;
   			request.addProperty("area",ii4.toString());
   	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
   	        envelope.dotNet=true;
   	        envelope.setOutputSoapObject(request);
   	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
   	        androidHttpTransport.call("http://tempuri.org/size", envelope);
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
   			pdia = new ProgressDialog(Hoarding.this);
   			pdia.setCanceledOnTouchOutside(false);
   			pdia.setMessage("data loading......");
   			pdia.show(); 	
   		}
   		
   		protected void onPostExecute(String result) 
   		{	
   			String data1[]=result.split(":");
			super.onPostExecute(result);	
			if(result.equals("========area not free=======") || result.equals("=========area free=========") )
			{
				ArrayList<String> data = new ArrayList<String>();
				data.add(data1[0]);
				ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
				bsize.setAdapter(NoCoreAdapter);
			}
			else 
			{
			ArrayList<String> data = new ArrayList<String>();
			for(int i=0;i<data1.length;i++)
			data.add(data1[i]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
            bsize.setAdapter(NoCoreAdapter);
			}
            pdia.dismiss();
   			
   		}
   	}
    
    class dostate extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","state");
	        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;

	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/state", envelope);
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
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("data loading......");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{	
			String data1[]=result.split(":");
			super.onPostExecute(result);	
			Log.d("result16",result);
			ArrayList<String> data = new ArrayList<String>();
			for(int i=0;i<data1.length;i++)
			data.add(data1[i]);
			ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
	        state.setAdapter(NoCoreAdapter);
			pdia.dismiss();
            
		}
	}   
                     		
	class docity extends AsyncTask<Void, Void, String>
		{
			private ProgressDialog pdia;
		
			protected String doInBackground(Void... params) 
			{
			try
			{			
			SoapObject request = new SoapObject("http://tempuri.org/","city");
		        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;
				request.addProperty("state",ii1.toString());
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/city", envelope);
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
				pdia = new ProgressDialog(Hoarding.this);
				pdia.setCanceledOnTouchOutside(false);
				pdia.setMessage("data loading......");
				pdia.show(); 	
			}
			
			protected void onPostExecute(String result) 
			{	
				String data1[]=result.split(":");
				super.onPostExecute(result);	
				Log.d("result17",result);
				ArrayList<String> data = new ArrayList<String>();
				for(int i=0;i<data1.length;i++)
				data.add(data1[i]);
				ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
		        city.setAdapter(NoCoreAdapter);
		        
		        pdia.dismiss();
	            
			}
		} 
 
	class doarea extends AsyncTask<Void, Void, String>
		{
		
			private ProgressDialog pdia;
			
			protected String doInBackground(Void... params) 
			{
			try
			{		
				
			    SoapObject request = new SoapObject("http://tempuri.org/","area");
		        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;
				request.addProperty("state",ii1.toString());
				request.addProperty("uid",Login.uid);
				request.addProperty("city",ii3.toString());
				request.addProperty("bann",ii.toString());
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.dotNet=true;
		        envelope.setOutputSoapObject(request);
		        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
		        androidHttpTransport.call("http://tempuri.org/area", envelope);
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
				pdia = new ProgressDialog(Hoarding.this);
				pdia.setCanceledOnTouchOutside(false);
				pdia.setMessage("data loading......");
				//new doareafetch().execute();
				pdia.show(); 	
			}
			
			protected void onPostExecute(String result) 
			{	

				if(result.equals("avalible only for surat") || result.equals("========area not free=======") || result.equals("=========area free=========") )
				{
					viewinmap.setEnabled(false);
					String data1[]=result.split(":");
					ArrayList<String> data = new ArrayList<String>();
					data.add(data1[0]);
					ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
					area.setAdapter(NoCoreAdapter);
				}
				else
				{
				String data1[]=result.split(":");
				super.onPostExecute(result);	
				Log.d("result18",result);
				ArrayList<String> data = new ArrayList<String>();
				for(int i=0;i<data1.length;i++)
				data.add(data1[i]);
				ArrayAdapter NoCoreAdapter = new ArrayAdapter(Hoarding.this,android.R.layout.simple_spinner_dropdown_item, data);
				area.setAdapter(NoCoreAdapter);
				//area.setBackgroundColor(Color.parseColor("#636161"));
				
			//	new doareafetch().execute();
				//new dosize().execute();
				
				}
				pdia.dismiss();
			}
			
		}   
	
	
		class doprice extends AsyncTask<Void, Void, String>
	{
		private ProgressDialog pdia;
	
		protected String doInBackground(Void... params) 
		{
		try
		{			
		SoapObject request = new SoapObject("http://tempuri.org/","getprice");
	        //Select state.sid,state.sname,city.cid,city.cname,area.aid,area.aname from state, city, area where state.sid = city.sid and city.cid = area.cid;
			request.addProperty("size",ii5.toString());
			request.addProperty("area",ii4.toString());
		//	request.addProperty("day",days);
			
	        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.dotNet=true;
	        envelope.setOutputSoapObject(request);
	        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
	        androidHttpTransport.call("http://tempuri.org/getprice", envelope);
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
			pdia = new ProgressDialog(Hoarding.this);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setMessage("data loading......");
			pdia.show(); 	
		}
		
		protected void onPostExecute(String result) 
		{	
			String data1=result;
			super.onPostExecute(result);	
			Log.d("result17",result);
			price.setText(data1);
			
			pdia.dismiss();
            
		}
	} 
	
	
	                      	               
}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	




