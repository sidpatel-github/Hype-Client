package com.example.hype;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hype.Review1.product;
import com.squareup.picasso.Picasso;


public class Reviewcustomaddapter extends BaseAdapter{
	ArrayList<product> a2;
	Context c2;
	public Reviewcustomaddapter() {
		// TODO Auto-generated constructor stub
	}
	public Reviewcustomaddapter(ArrayList<product> a1,Context c1) {
		// TODO Auto-generated constructor stub
		a2=a1;
		c2=c1;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return a2.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(final int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
				 LayoutInflater inflater = (LayoutInflater)c2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View single_row = inflater.inflate(R.layout.reviewlistrow, null,true);
				 TextView ruser= (TextView) single_row.findViewById(R.id.rtextView2);
				 TextView rbanner= (TextView) single_row.findViewById(R.id.rtextView4);
				 TextView rarea = (TextView) single_row.findViewById(R.id.rtextView6);
				 TextView rcomment = (TextView) single_row.findViewById(R.id.rtextView8);
				 RatingBar rrating=(RatingBar)single_row.findViewById(R.id.rratingBar1);
				 
				 ruser.setText(a2.get(position).r_username);
				 rbanner.setText(a2.get(position).r_btype);
				 rarea.setText(a2.get(position).r_area);
				 rcomment.setText(a2.get(position).r_feed);		 
				 rrating.setRating( Float.parseFloat( a2.get(position).r_rating));
				 
				 return single_row; 
	}


}
