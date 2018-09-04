package com.example.hype;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hype.History1.product;
import com.squareup.picasso.Picasso;

public class Historycusadp extends BaseAdapter{
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/urlfetch";
	String METHOD_NAME = "urlfetch"; 
	Button button1;
	ArrayList<product> a2;
	Context c2;
	public static String hisid,hty,hsiz,hbd,hed,hadd,harea,hcity,hstate,himg,hstat,hprice;
	public static String url;
	
	 public Historycusadp() {
		// TODO Auto-generated constructor stub
	}
	public Historycusadp(ArrayList<product> a1,Context c1){
		a2=a1;
		c2=c1;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return a2.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub // TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) c2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View single_row = inflater.inflate(R.layout.historycusadp, null,true);
				 TextView status1 = (TextView) single_row.findViewById(R.id.textView1);
				 TextView date1 = (TextView) single_row.findViewById(R.id.textView2);
				 TextView area1 = (TextView) single_row.findViewById(R.id.textView3);
				 ImageView image1= (ImageView) single_row.findViewById(R.id.imageView1);
				 Button button1 = (Button) single_row.findViewById(R.id.button);
				 
				 date1.setText((a2.get(position)).date);
				 status1.setText(a2.get(position).status);
				 area1.setText(a2.get(position).area);
				 Picasso.with(c2).load(Login.imgaddress+a2.get(position).image).resize(100,100).into(image1);

	
	 
	 button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hty = a2.get(position).type;
				hsiz = a2.get(position).size;
				hbd = a2.get(position).date;
				hed = a2.get(position).edate;
				//hadd = a2.get(position).addr;
				harea = a2.get(position).area;
				hcity = a2.get(position).city;
				hstate = a2.get(position).state;
				himg = a2.get(position).image;
				hstat = a2.get(position).status;
				hisid = a2.get(position).hid;
				himg = a2.get(position).image;
				hprice=a2.get(position).price;
				Intent i = new Intent(c2,Historyfull.class);
				c2.startActivity(i);
			}
		});	
	 return single_row; 
}
}