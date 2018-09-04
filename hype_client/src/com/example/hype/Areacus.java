package com.example.hype;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hype.Hoarding.areaon;

public class Areacus extends BaseAdapter {
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/areafetch";
	String METHOD_NAME = "areafetch"; 
	Button button1;
	ArrayList<areaon> a2;
	Context c2;
	
	public static String harea;
	public static String url;
	
	 public Areacus() {
		// TODO Auto-generated constructor stub
	 }
	 public Areacus(ArrayList<areaon> a1,Context c1){
		a2=a1;
		c2=c1;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return a2.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub // TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) c2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View single_row = inflater.inflate(R.layout.areacus, null,true);
				 TextView areanot = (TextView) single_row.findViewById(R.id.textView1);
				 areanot.setText(a2.get(position).area1);
				 Log.d("hello",areanot.toString());
				 
				 areanot.setText(a2.get(position).area1);
				 				
				 	return single_row; 
}

}
