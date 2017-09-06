package com.fungameavs1;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyFragment extends DialogFragment{


	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		
		
		ScaleAnimation ani1=new ScaleAnimation(2.1f,1.0f,2.1f,1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
		ani1.setDuration(1000);
		ani1.setFillAfter(true);
		ani1.setFillEnabled(true);
		ani1.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {   }
					@Override
					public void onAnimationRepeat(Animation animation) {	}
					@Override
					public void onAnimationEnd(Animation animation) {	}
			});
		FunRouletteActivity.overlay_two_imageviews.startAnimation(ani1);

	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ImageView wheel=FunRouletteActivity.wheel;
		ImageView ball=FunRouletteActivity.ball;
		
		wheel.animate().x(wheel.getX()+0).y(wheel.getY()+0).setDuration(2000).rotationBy(159);
		
        
		ball.animate().x(ball.getLeft()+2).y(ball.getY()+2).setDuration(2000).rotationBy(2000);
		
		ScaleAnimation ani2=new ScaleAnimation(2.1f,2.1f,2.1f,2.1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
		ani2.setDuration(1000);
		ani2.setFillAfter(true);
		ani2.setFillEnabled(true);
		ani2.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {  }
			@Override
			public void onAnimationRepeat(Animation animation) {	}
			@Override
			public void onAnimationEnd(Animation animation) {
				animation.cancel();
				getDialog().dismiss();
			}
		});
		FunRouletteActivity.overlay_two_imageviews.startAnimation(ani2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v= inflater.inflate(R.layout.myfragment, null, false);
		
		LinearLayout.LayoutParams layoutparams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
		layoutparams.gravity=Gravity.CENTER;

		v.setLayoutParams(layoutparams);
		return v;
	}

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(DialogFragment.STYLE_NO_TITLE);
        View view = View.inflate(getActivity(), R.layout.myfragment, null);
        dialog.setContentView(view);  
        return dialog;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		}

	

	
	@Override
	public void onResume() {
		super.onResume();
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
	}

   
}
