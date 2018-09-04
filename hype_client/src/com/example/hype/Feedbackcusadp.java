package com.example.hype;
import java.util.ArrayList;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hype.Feedback1.product;
import com.squareup.picasso.Picasso;

public class Feedbackcusadp extends BaseAdapter{
	public static String NAMESPACE = "http://tempuri.org/";
	public static String SOAP_ACTION = "http://tempuri.org/feed";
	String METHOD_NAME = "feed"; 
	ArrayList<product> a3;
	Context c2;
	public static String holdid,oimages,orate,ofeed;
	public static String url;
	int a;
	
	public Feedbackcusadp() {
		// TODO Auto-generated constructor stub
	}
	public Feedbackcusadp(ArrayList<product> a1,Context c1){
		a3=a1;
		c2=c1;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return a3.size();
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
				 View single_row = inflater.inflate(R.layout.feedbackcusadp, null,true);
				 TextView date1 = (TextView) single_row.findViewById(R.id.textView1);
				 TextView area1 = (TextView) single_row.findViewById(R.id.textView2);
				 RatingBar rate1 = (RatingBar) single_row.findViewById(R.id.ratingBar1);
				 TextView feed1 = (TextView) single_row.findViewById(R.id.textView3);
				 ImageView image1= (ImageView) single_row.findViewById(R.id.imageView1);
				 Button feedback= (Button) single_row.findViewById(R.id.button1);
				 
				 date1.setText((a3.get(position)).date);
				 area1.setText((a3.get(position)).area);
				 Log.d("float",a3.get(position).rate);
				 rate1.setRating( Float.parseFloat( a3.get(position).rate));
				 feed1.setText(a3.get(position).feed);
				 Picasso.with(c2).load(Login.imgaddress+a3.get(position).image).resize(100,100).into(image1);
				 
				 
				 
				 feedback.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							oimages = a3.get(position).image;
							orate = a3.get(position).rate;
							ofeed = a3.get(position).feed;
							holdid = a3.get(position).hid;
							Intent i = new Intent(c2,Feedback.class);
							c2.startActivity(i);
						}
					});
								 
				 return single_row; 
				 			 
		}
}