package com.fungameavs1;

import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.spec.IvParameterSpec;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;




public class fragment_main extends DialogFragment {
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	
        	
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_main, container,false);
		
		
		RelativeLayout rl=(RelativeLayout) view.findViewById(R.id.fragment1_main_row);
		rl.setLayoutParams(new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
		
		
		ImageView img1=(ImageView) rl.findViewById(R.id.imageView1);
		img1.setBackgroundResource(R.drawable.nwheel);
		img1.setScaleType(ScaleType.FIT_XY);
		RelativeLayout.LayoutParams params=(android.widget.RelativeLayout.LayoutParams) img1.getLayoutParams();
		params.setMargins(0,15, 0, 0);
		   
		
		
		ImageView img2=(ImageView) rl.findViewById(R.id.imageView2);
		img2.setBackgroundResource(R.drawable.nlogo10);
		RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams)img2.getLayoutParams();
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		
		
		ImageView img3=(ImageView) rl.findViewById(R.id.imageView3);
		img3.setBackgroundResource(R.drawable.bg_gold_wheel2);
		RelativeLayout.LayoutParams layoutParams1 =(RelativeLayout.LayoutParams)img3.getLayoutParams();
		layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		
		   
		
		ImageView ivArrow=(ImageView) rl.findViewById(R.id.ivArrow);
		ivArrow.setBackgroundResource(R.drawable.arrowglow);
		RelativeLayout.LayoutParams params1=(android.widget.RelativeLayout.LayoutParams) ivArrow.getLayoutParams();
		params1.setMargins(210,8, 0, 0);
		RelativeLayout.LayoutParams layoutParams2 =(RelativeLayout.LayoutParams)ivArrow.getLayoutParams();
		layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
			
			
			
			
		return view;
		//return inflater.inflate(R.layout.fragment_main, null);
	}

	
	
	
	
	@Override
	public void onPause() {
		super.onPause();
		
	}
	
	
	
	@Override
	public void onStart() {
		super.onStart();
		
	
	}


	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		
	}
	
}
