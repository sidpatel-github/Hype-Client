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

public class History1 extends ActionBarActivity {
	public static String NAMESPACE = "http://tempuri.org/";
	ListView l1;
	ArrayList<product> p1 = new ArrayList<product>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history1);
        
        Login.uid = Login.pref.getString("uid",null);
        
//        clientuid=uid.toString();
        l1 =(ListView) findViewById(R.id.listView1);
       new DoReg().execute();
        
   }

class product 
{
String hid;
String type;
String size;
String edate;
String date;
//String addr;
String city;
String area;
String state;
String status;
String image;
String price;
}
/*
@Override
public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("EXIT THIS LIST")
	        .setMessage("Are you sure you want to exit list of history?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	         /*  Intent i = new Intent(History1.this,Home.class);
	           startActivity(i);*//* finish();
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
	SoapObject request = new SoapObject("http://tempuri.org/","urlfetch");
	
    request.addProperty("uid",Login.uid);
        
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(Login.URL);
        androidHttpTransport.call("http://tempuri.org/urlfetch", envelope);
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
		//result=result.replace(" ","");
		Log.d("data",result.toString());
		if(result.toString().equals("false"))
		{
			Toast.makeText(History1.this,"sorry no booking", 2000).show();
		}
		else
		{
		String rows[]=result.split(":");
		p1.clear();
		for(int i=0;i<rows.length;i++)
		{
			
		String cols[]=rows[i].split(",,");
		product pobj = new product();
		pobj.hid=cols[0];
		pobj.type=cols[2];
		pobj.size=cols[3];
		pobj.date=cols[4];
		pobj.edate=cols[5];
		//pobj.addr=cols[6];
		pobj.area=cols[6];
		pobj.city=cols[7];
		pobj.state=cols[8];
		pobj.image=cols[9];
		pobj.status=cols[10];
		pobj.price=cols[11];
		p1.add(pobj);
		
		}
		Historycusadp c1 = new Historycusadp(p1,History1.this);
        l1.setAdapter(c1);
		}
	}
}	
}