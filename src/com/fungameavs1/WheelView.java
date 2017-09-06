package com.fungameavs1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;




public class WheelView extends SurfaceView implements Runnable{

	static int columntwowidth;
    static int columntwoheight;
    Bitmap ball,wheel,wheel_background,total_wheel_background;
	float x,y;
	
	Thread t=null;
	SurfaceHolder holder;
	boolean isItOK=false;
	
	public WheelView(Context context) {
		super(context);
		holder=getHolder();
		
		
		ball=BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		ball=Bitmap.createScaledBitmap(ball, (int)(columntwowidth*0.74)/17, (int)(columntwoheight*0.65)/17, true);
		
		wheel=BitmapFactory.decodeResource(getResources(), R.drawable.wheel);
		wheel=Bitmap.createScaledBitmap(wheel, (int)(columntwowidth-(columntwowidth*0.18)), (int)(columntwoheight-(columntwoheight*0.25)), true);
		
		wheel_background=BitmapFactory.decodeResource(getResources(), R.drawable.bg_wheel);
		wheel_background=Bitmap.createScaledBitmap(wheel_background, (int)(columntwowidth), (int)(columntwoheight), true);
		
		total_wheel_background=BitmapFactory.decodeResource(getResources(), R.drawable.bg_wheel_roulette);
		total_wheel_background=Bitmap.createScaledBitmap(total_wheel_background, (int)(columntwowidth), (int)(columntwoheight), true);
		x=y=0;
		
	}

	@Override
	public void run() {
	   while(isItOK==true){
		   //perform canvas drawing
		   if(!holder.getSurface().isValid()){
			   continue;
		   }
		   
		   Canvas c=holder.lockCanvas();
		   c.drawBitmap(total_wheel_background, 0,0, null); 
		   c.drawBitmap(wheel_background, 0,0, null); //c.drawBitmap(bitmap,left,top,paint) 
		  
		   //set margin to number_wheel in the cell and creating number_wheel bitmap
		   float left_margin_number_wheel=(float) ((columntwowidth)*0.10);
		   float top_margin_number_wheel=(float)(columntwoheight*0.05);
		   c.drawBitmap(wheel,left_margin_number_wheel, top_margin_number_wheel,null);
		 
		  //set margin to ball in the cell and creating ball bitmap
		   float left_margin_ball=(float) (columntwowidth/7.7);
		   float top_margin_ball=(float) (columntwoheight/3.1);
		   
		   c.drawBitmap(ball,(float) ((columntwowidth*0.10)+left_margin_ball), (float)((columntwoheight*0.05)+top_margin_ball),null);  // margins to number_wheel + margins to ball
		   holder.unlockCanvasAndPost(c);
	
	   }
		
	}
	
	public void pause(){
		isItOK=false;
		while(true){
			try{
				t.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			break;
		}
		t=null;
	}
	
	public void resume(){
		isItOK=true;
		t=new Thread(this);
		t.start();
	}
	
}