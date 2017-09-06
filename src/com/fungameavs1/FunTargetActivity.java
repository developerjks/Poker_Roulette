package com.fungameavs1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.fungameavs1.FunRouletteActivity.ABC;
import com.fungameavs1.FunTargetActivity.A.StringInt_keys_Bet_Taken_Target;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;




public class FunTargetActivity extends Activity {
	
	public static final int[] bet_Values_Target = new int[]{ 0,0,0,0,0,0,0,0,0,0 };
	public static final ArrayList<StringInt_keys_Bet_Taken_Target> arl_target=new ArrayList<StringInt_keys_Bet_Taken_Target>();
	int amount_Won_Target = 0;
	
	/*start here*/
    public boolean Is_Bet_Taime_Over_Target = false;
    public boolean is_New_Game_Target = false;
    public boolean is_selected_bet_completed_Target = false;
    /*ends here */
	
	//starts here
	public static class CurrentTimer_Target
	{
		static int counter=60;
		static boolean application_interrupted=false;
		static int timer_thread_count=0, application_interrupted_count=0;
		static long date1_onpause, date2_onresume,total_time_application_on_wait;
	}
	
	Timer timer_time_Target;
    TimerTask timerTask_timer_time_Target;
    
    static int return_status_async_GAMEDETAILS_Target=0;
  //private AudioManager audioManager;
    private MediaPlayer mp1;
    

    
    
    private boolean flag_playing_actualdevice=true;
/*end here*/

    
	//we are going to use a handler to be able to run in our TimerTask
    Timer timer_slider_target=null;
    Handler myhandler_slider_funtarget=new Handler();
    private MyWorkerThread mWorkerThread_slider_funtarget;
    Runnable myupdateresults_slider_funtarget;


    public int currentimageindex=0;
    private int[] Image_ids={R.drawable.arrow,R.drawable.arrowglow};
    ImageView ivArrow;
	
	//ends here
	
    static int current_bet_amount_target=1;
		int row1_column2_height,row1_column2_width;
		int screenWidthDp,screenHeightDp;
	    LinearLayout third_row,target_increment_first_bet_layout,target_increment_second_bet_layout,txt_last_10_data_layout;

	    static TextView txt_score,txt_time,txt_winner,txt_bottom_won_amount,txt_bottom_bet_status;
	    ImageView btn_exit,btn_take,btn_betOK; 
	    static TextView[] txt_last_10_data=new TextView[10];
	    
	    static FunTargetMessages objFunTargetMessages=null;
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) 
	    {
	        super.onCreate(savedInstanceState);
	        
	        /* for full screen (requires uses-permission "android.permission.ACCESS_NETWORK_STATE" starts here*/
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	 		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	 		/* for full screen (requires uses-permission "android.permission.ACCESS_NETWORK_STATE" ends here*/
	 		
	        setContentView(R.layout.activity_funtarget);
	        
	        return_status_async_GAMEDETAILS_Target=0;
 		
	 		/* to get screen width and height of real device starts here  */
	 		Point size=new Point();
	        getWindowManager().getDefaultDisplay().getSize(size);
	        screenWidthDp=size.x;
	        screenHeightDp=size.y;
	        /* to get screen width and height of real device ends here  */
	        
	        
	        
	        
	        
	        /* code to place the slider for FunRoulette starts here */
	        mWorkerThread_slider_funtarget = new MyWorkerThread("myWorkerThread_sliderfuntarget");
	        myupdateresults_slider_funtarget = new Runnable() {
	            @Override
	            public void run()
	            {
	                        myhandler_slider_funtarget.post(new Runnable() {
											                        	@Override
											                        	public void run()
											                        	{
												                        	animateandslideshow();
											                        	}
		                        									});

	            
	            }
	        };
	        
	        
	        
	        
	        int delay=1000;//delay for 1 sec
	        int period=500; //repeat every 4 sec
	        timer_slider_target=new Timer();
	        timer_slider_target.scheduleAtFixedRate(new TimerTask() {
				        				    @Override
				        				    public void run()
				        				    {
				        				    	 mWorkerThread_slider_funtarget.postTask(myupdateresults_slider_funtarget);
				        				    }
	                                  },delay,period);
		    /* code to place the slider for FunRoulette ends here */

	        
	        /* code to place the title slider starts here  */
	    	//final Handler myhandler=new Handler();
		
		    //create runnable for posting
		   /* final Runnable mupdateresults=new Runnable() {
							    	@Override
									public void run() {		animateandslideshow(); }
		    					};
					int delay=1000;//delay for 1 sec
					int period=500; //repeat every 4 sec
					Timer timer=new Timer();
		
					timer.scheduleAtFixedRate(new TimerTask() {
									@Override
									public void run() {		myhandler.post(mupdateresults);		}
								},delay,period);*/
	    /* code to place the title slider ends here  */
	    }
	    
	    
	    private void animateandslideshow()
		{
		//	slidingimage=(ImageView) findViewById(Imageview3);
			//ivArrow.setImageResource(Image_ids[currentimageindex%Image_ids.length]);
	    	ivArrow.setBackgroundResource(Image_ids[currentimageindex%Image_ids.length]);
			currentimageindex++;
		}

	    
	    //to initialize target screen 
	    private void initialize_target_screen(LinearLayout mainlinearlayout,int screenWidthDp,int screenHeightDp)
	    {
	  		mainlinearlayout.setOrientation(LinearLayout.VERTICAL);
	  		//mainlinearlayout.setBackgroundColor(Color.RED);
	  		   
	  		   
	  	    /* to set background image bg in main grid starts here */
	  		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.funtarget_bg);
	  		mainlinearlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
	  		/* to set background image bg in main grid ends here  */
	       
	  	    /* calculations of screen widths & heights starts here*/
	  	        int row1_column1_height=(int)(screenHeightDp*0.50);
	  	        int row1_column1_width=(int)(screenWidthDp*0.32);
	  	        int row1_column3_height=row1_column1_height;
	  	        int row1_column3_width=row1_column1_width;
	  	        row1_column2_height=row1_column1_height;
	  	        row1_column2_width= screenWidthDp-(row1_column1_width*2);
	  	            
	  	        int row2_column1_height=(int)(screenHeightDp*0.27);
	  	        int row2_column1_width=(int)(screenWidthDp*0.14);
	  	        int row2_column3_height=row2_column1_height;
	  	        int row2_column3_width=row2_column1_width;
	  	        int row2_column2_height=row2_column1_height;
	  	        int row2_column2_width=screenWidthDp-(row2_column1_width*2);

	  	        int row3_height=(int)(screenHeightDp*0.16);
	  	        int row3_width=screenWidthDp;
	  	        
	  	        int row4_column1_height=(int)(screenHeightDp*0.07);
	  	        int row4_column1_width=(int)(screenWidthDp*0.10);
	  	        int row4_column3_height=row4_column1_height;
	  	        int row4_column3_width=row4_column1_width;
	  	        int row4_column2_height=row4_column1_height;
	  	        int row4_column2_width=screenWidthDp-(row4_column1_width*2);
	  	    /* calculations of screen widths & heights ends here*/
	  	                   
	  	        
	  		/*  first row starts here  */
	  		    LinearLayout first_row=(LinearLayout)mainlinearlayout.findViewById(R.id.first_row);
	  		    first_row.setLayoutParams(new LinearLayout.LayoutParams(screenWidthDp,row1_column1_height));
	  		    first_row.setOrientation(LinearLayout.HORIZONTAL);
	  			//first_row.setBackgroundColor(Color.GREEN);
	  		
	  		       /* first row first column starts here */
	  				     LinearLayout first_row_first_column=(LinearLayout)first_row.findViewById(R.id.first_row_first_column);
	  				     first_row_first_column.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column1_height));
	  				     first_row_first_column.setOrientation(LinearLayout.VERTICAL);
	  				     //first_row_first_column.setBackgroundColor(Color.MAGENTA);
	  				        
	  				        /* first row first column first cell starts here  */
	  				            int row1_column1_cell1_height=(int)(row1_column1_height*0.35);
	  					        LinearLayout txt_score_layout=(LinearLayout)first_row_first_column.findViewById(R.id.txt_score_layout);
	  					        txt_score_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column1_cell1_height));
	  					        txt_score_layout.setOrientation(LinearLayout.HORIZONTAL);
	  					        //txt_score_layout.setBackgroundColor(Color.RED);
	  					      
	  						        txt_score=(TextView)txt_score_layout.findViewById(R.id.txt_score);
	  						        txt_score.setPadding((int)(row1_column1_width*0.32),(int)( row1_column1_cell1_height*0.52), 0, 0);
	  						        txt_score.setLayoutParams(new LayoutParams(row1_column1_width,row1_column1_cell1_height));
	  						        txt_score.setTextColor(Color.BLACK);
	  						        txt_score.setTextSize(14);
	  						        txt_score.setGravity(Gravity.LEFT);
	  						        txt_score.setTypeface(null,Typeface.BOLD);
	  						        txt_score.setText(""+GlobalClass.getBalance()+"");
	  					    /* first row first column first cell ends here  */
	  					        
	  					    /* first row first column second cell starts here  */
	  					        LinearLayout txt_time_layout=(LinearLayout)first_row_first_column.findViewById(R.id.txt_time_layout);
	  					        txt_time_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column1_cell1_height));
	  					        txt_time_layout.setOrientation(LinearLayout.HORIZONTAL);
	  					        //txt_time_layout.setBackgroundColor(Color.WHITE);
	  					        
	  						        txt_time=(TextView) txt_time_layout.findViewById(R.id.txt_time);
	  						        txt_time.setPadding((int)(row1_column1_width*0.29),(int)( row1_column1_cell1_height*0.64), 0, 0);
	  						        txt_time.setLayoutParams(new LayoutParams(row1_column1_width,row1_column1_cell1_height));
	  						        txt_time.setTextColor(Color.BLACK);
	  						        txt_time.setTextSize(15);
	  						        txt_time.setGravity(Gravity.LEFT);
	  						        txt_time.setText("0  :  23");
	  						        txt_time.setTypeface(null,Typeface.BOLD);
	  					    /* first row first column second cell ends here  */
	  					            
	  					    /* first row first column third cell starts here  */
	  					      int row1_column1_cell3_height=(int)((row1_column1_height- row1_column1_cell1_height*2));
	  					      int row1_column1_cell3_width=(int)(row1_column1_width);
	  					           
	  					      target_increment_first_bet_layout=(LinearLayout)first_row_first_column.findViewById(R.id.target_increment_first_bet_layout);
	  					      target_increment_first_bet_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_cell3_width,row1_column1_cell3_height));
	  					      target_increment_first_bet_layout.setOrientation(LinearLayout.HORIZONTAL);
	  					      //target_increment_first_bet_layout.setBackgroundColor(Color.MAGENTA);
	  					        
	  					      LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  					      LinearLayout target_increment_bet_layout=(LinearLayout) inflater.inflate(R.layout.target_increment_bet_keypad, null);
	  					      initialize_increment_bet_keypad(target_increment_bet_layout,row1_column1_cell3_width,row1_column1_cell3_height);
	  						  target_increment_first_bet_layout.addView(target_increment_bet_layout);
	  					     /* first row first column third cell ends here  */
	  			        /* first row first column ends here */
	  				    
	  				 /* first row second column starts here */
	  				     LinearLayout first_row_second_column=(LinearLayout)first_row.findViewById(R.id.first_row_second_column);
	  				     first_row_second_column.setLayoutParams(new LinearLayout.LayoutParams( row1_column2_width,row1_column2_height));
	  				     first_row_second_column.setOrientation(LinearLayout.VERTICAL);
	  				     //first_row_second_column.setBackgroundColor(Color.GREEN);
	  				        
	  				    
	  				     //first_row_second_column_relative.setLayoutParams(new LinearLayout.LayoutParams( row1_column2_width,row1_column2_height));
	  				   
	  				   
	  				     
	  				     
/* instead of fragment starts here */
	  				     
	  				     try{
	  				    	 RelativeLayout first_row_second_column_relative=(RelativeLayout) first_row_second_column.findViewById(R.id.first_row_second_column_relative);
	  		  				 ImageView iv_nwheel=(ImageView) first_row_second_column_relative.findViewById(R.id.iv_nwheel);
	  		  				 iv_nwheel.setBackgroundResource(R.drawable.nwheel);
	  		  				 iv_nwheel.setScaleType(ScaleType.FIT_XY);
	  		  				 RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams) iv_nwheel.getLayoutParams();
	  		  				 params1.height=(int)(row1_column2_height);
	  		  				 params1.width=(int)(row1_column2_width);
	  		  				 params1.setMargins(0,(int)(row1_column2_height*0.03),0,0);
	  		  				 
	  		  				 
	  		  				ImageView iv_bggoldwheel2=(ImageView) first_row_second_column_relative.findViewById(R.id.iv_bggoldwheel2);
	  		  			     iv_bggoldwheel2.setBackgroundResource(R.drawable.bg_gold_wheel2);
	  		  		  		 RelativeLayout.LayoutParams params2=(RelativeLayout.LayoutParams) iv_bggoldwheel2.getLayoutParams();
	  		  				 params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
	  		  		  		 params2.height=(int)(row1_column2_height*0.35);
	  		  				 params2.width=(int)(row1_column2_width);
	  		  				 
	  		  				 
	  		  				 
	  		  				 
	  		  				ImageView iv_nlogo10=(ImageView) first_row_second_column_relative.findViewById(R.id.iv_nlogo10);
	  		  			iv_nlogo10.setBackgroundResource(R.drawable.nlogo10);
	  		  				 RelativeLayout.LayoutParams params3=(RelativeLayout.LayoutParams) iv_nlogo10.getLayoutParams();
	  		  				 params3.height=(int)(row1_column2_height*0.32);
	  		  				 params3.width=(int)(row1_column2_width*0.34);
	  		  				 params3.setMargins((int)(row1_column2_width*0.33),(int)(row1_column2_height*0.36),0,0);
	  		  				 
	  				         ivArrow=(ImageView) first_row_second_column_relative.findViewById(R.id.iv_arrow);
	  				         ivArrow.setBackgroundResource(R.drawable.arrowglow);
	  		  				 RelativeLayout.LayoutParams params4=(RelativeLayout.LayoutParams) ivArrow.getLayoutParams();
	  		  				params4.height=(int)(row1_column2_height*0.11);
	  		  				 params4.width=(int)(row1_column2_width*0.11);
	  		  				params4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	  		  				 params4.setMargins((int)(row1_column2_width*0.44),(int)(row1_column2_height*0.02),0,0);

	  				        // ivArrow.setBackgroundResource(R.drawable.arrowglow);
	  				     }catch(Exception e)
	  				     {
	  				    	Log.d("ERROR",e.getMessage()); 
	  				     }
/* instead of fragment ends here */	  				 
	  			     /* first row second column ends here */
	  		 
	  				 /* first row third column starts here */
	  				     LinearLayout first_row_third_column=(LinearLayout)first_row.findViewById(R.id.first_row_third_column);
	  				     first_row_third_column.setLayoutParams(new LinearLayout.LayoutParams( row1_column3_width,row1_column3_height));
	  				     first_row_third_column.setOrientation(LinearLayout.VERTICAL);
	  				     //first_row_third_column.setBackgroundColor(Color.YELLOW);
	  				        
	  				      /* first row third column first cell starts here  */
	  					        int row1_column3_cell1_height=(int)(row1_column3_height*0.35);
	  						    LinearLayout txt_winner_layout=(LinearLayout)first_row_third_column.findViewById(R.id.txt_winner_layout);
	  						    txt_winner_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column3_cell1_height));
	  						    txt_winner_layout.setOrientation(LinearLayout.HORIZONTAL);
	  						    //txt_winner_layout.setBackgroundColor(Color.RED);
	  						        
	  						        txt_winner=(TextView) txt_winner_layout.findViewById(R.id.txt_winner);
	  						        txt_winner.setPadding((int)(row1_column1_width*0.60),(int)( row1_column3_cell1_height*0.50), 0, 0);
	  						        txt_winner.setLayoutParams(new LayoutParams(row1_column1_width,row1_column3_cell1_height));
	  						        txt_winner.setTextColor(Color.BLACK);
	  						        txt_winner.setTextSize(14);
	  						        txt_winner.setGravity(Gravity.LEFT);
	  						        txt_winner.setText("0");
	  						        txt_winner.setTypeface(null,Typeface.BOLD);
	  				      /* first row third column first cell ends here  */
	  						        
	  					  /* first row third column second cell starts here  */     
	  						    LinearLayout txt_last_10_data_layout=(LinearLayout)first_row_third_column.findViewById(R.id.txt_last_10_data_layout);
	  						    txt_last_10_data_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column3_cell1_height));
	  						    txt_last_10_data_layout.setOrientation(LinearLayout.HORIZONTAL);
	  						    //txt_last_10_data_layout.setBackgroundColor(Color.WHITE);
	  						        
  
	  						    /*first row third column second cell vertical starts here*/
	  						    LinearLayout txt_last_10_data_vertical_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_vertical_layout);
	  						  txt_last_10_data_vertical_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,row1_column3_cell1_height));
	  						txt_last_10_data_vertical_layout.setOrientation(LinearLayout.VERTICAL);
	  						    //txt_last_10_data_vertical_layout.setBackgroundColor(Color.MAGENTA);
	  						
	  						
	  						
	  							
	  							/* first row third column second cell first row starts here*/
	  							int horizon1_height=(int)(row1_column3_cell1_height*0.70);
	  							LinearLayout horizon1=(LinearLayout)txt_last_10_data_vertical_layout.findViewById(R.id.horizon1);
	  							horizon1.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,horizon1_height));
	  							horizon1.setOrientation(LinearLayout.HORIZONTAL);
	  						    //horizon1.setBackgroundColor(Color.YELLOW);
	  						    /* first row third column second cell first row ends here*/
	  						    
	  						   /*first row third column second cell second row starts here*/
	  							int horizon2_height=(int)(row1_column3_cell1_height*0.26);
	  							LinearLayout horizon2=(LinearLayout)txt_last_10_data_vertical_layout.findViewById(R.id.horizon2);
	  							horizon2.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,horizon2_height));
	  							horizon2.setOrientation(LinearLayout.HORIZONTAL);
	  						    //horizon2.setBackgroundColor(Color.RED);
	  							
	  							
	  							LinearLayout empty_before_txt_last_10_data_0_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.empty_before_txt_last_10_data_0_layout);
						        empty_before_txt_last_10_data_0_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.36),row1_column3_cell1_height));
						        empty_before_txt_last_10_data_0_layout.setOrientation(LinearLayout.VERTICAL);
						        //empty_before_txt_last_10_data_0_layout.setBackgroundColor(Color.RED);   

						        LinearLayout txt_last_10_data_0_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_0_layout);
				        		txt_last_10_data_0_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.05),row1_column3_cell1_height));
						        txt_last_10_data_0_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_0_layout.setBackgroundColor(Color.GREEN);
						        
						        LinearLayout txt_last_10_data_1_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_1_layout);
				        		txt_last_10_data_1_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.05),row1_column3_cell1_height));
						        txt_last_10_data_1_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_1_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_10_data_2_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_2_layout);
				        		txt_last_10_data_2_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_2_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_2_layout.setBackgroundColor(Color.GREEN);
						        
						        LinearLayout txt_last_10_data_3_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_3_layout);
				        		txt_last_10_data_3_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_3_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_3_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_10_data_4_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_4_layout);
				        		txt_last_10_data_4_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_4_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_4_layout.setBackgroundColor(Color.GREEN);
						        

		        		        LinearLayout txt_last_10_data_5_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_5_layout);
				        		txt_last_10_data_5_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_5_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_5_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_10_data_6_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_6_layout);
				        		txt_last_10_data_6_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_6_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_6_layout.setBackgroundColor(Color.GREEN);
						        
						        LinearLayout txt_last_10_data_7_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_7_layout);
				        		txt_last_10_data_7_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.06),row1_column3_cell1_height));
						        txt_last_10_data_7_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_7_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_10_data_8_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_8_layout);
				        		txt_last_10_data_8_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.05),row1_column3_cell1_height));
						        txt_last_10_data_8_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_8_layout.setBackgroundColor(Color.GREEN);
						        
						        LinearLayout txt_last_10_data_9_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.txt_last_10_data_9_layout);
				        		txt_last_10_data_9_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.05),row1_column3_cell1_height));
						        txt_last_10_data_9_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_10_data_9_layout.setBackgroundColor(Color.WHITE);

						        
						        LinearLayout empty_after_txt_last_10_data_9_layout=(LinearLayout)txt_last_10_data_layout.findViewById(R.id.empty_after_txt_last_10_data_9_layout);
						        empty_after_txt_last_10_data_9_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(row1_column1_width*0.08),row1_column3_cell1_height));
						        empty_after_txt_last_10_data_9_layout.setOrientation(LinearLayout.VERTICAL);
						        //empty_after_txt_last_10_data_9_layout.setBackgroundColor(Color.RED);

								
								txt_last_10_data[0]=(TextView)txt_last_10_data_0_layout.findViewById(R.id.txt_last_10_data_0);
								//txt_last_10_data[0].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[0].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.10),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[0].setTextColor(Color.MAGENTA);
								txt_last_10_data[0].setTextSize(10);
								txt_last_10_data[0].setGravity(Gravity.LEFT);
								txt_last_10_data[0].setText("20");
								txt_last_10_data[0].setTypeface(null,Typeface.BOLD);
								
		
								
								txt_last_10_data[1]=(TextView)txt_last_10_data_1_layout.findViewById(R.id.txt_last_10_data_1);
								//txt_last_10_data[1].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[1].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[1].setTextColor(Color.BLACK);
								txt_last_10_data[1].setTextSize(10);
								txt_last_10_data[1].setGravity(Gravity.LEFT);
								txt_last_10_data[1].setText("20");
								txt_last_10_data[1].setTypeface(null,Typeface.BOLD);
		
								
								
								txt_last_10_data[2]=(TextView)txt_last_10_data_2_layout.findViewById(R.id.txt_last_10_data_2);
								//txt_last_10_data[2].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[2].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[2].setTextColor(Color.BLACK);
								txt_last_10_data[2].setTextSize(10);
								txt_last_10_data[2].setGravity(Gravity.LEFT);
								//txt_last_10_data[2].setText("20");
								txt_last_10_data[2].setTypeface(null,Typeface.BOLD);
		
								
								
								
								txt_last_10_data[3]=(TextView)txt_last_10_data_3_layout.findViewById(R.id.txt_last_10_data_3);
								//txt_last_10_data[3].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[3].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[3].setTextColor(Color.BLACK);
								txt_last_10_data[3].setTextSize(10);
								txt_last_10_data[3].setGravity(Gravity.LEFT);
								//txt_last_10_data[3].setText("20");
								txt_last_10_data[3].setTypeface(null,Typeface.BOLD);
		
							   	
								
								
								txt_last_10_data[4]=(TextView)txt_last_10_data_4_layout.findViewById(R.id.txt_last_10_data_4);
								//txt_last_10_data[4].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[4].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[4].setTextColor(Color.BLACK);
								txt_last_10_data[4].setTextSize(10);
								txt_last_10_data[4].setGravity(Gravity.LEFT);
								//txt_last_10_data[4].setText("20");
								txt_last_10_data[4].setTypeface(null,Typeface.BOLD);


								
								txt_last_10_data[5]=(TextView)txt_last_10_data_5_layout.findViewById(R.id.txt_last_10_data_5);
								//txt_last_10_data[5].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[5].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[5].setTextColor(Color.BLACK);
								txt_last_10_data[5].setTextSize(10);
								txt_last_10_data[5].setGravity(Gravity.LEFT);
								//txt_last_10_data[5].setText("20");
								txt_last_10_data[5].setTypeface(null,Typeface.BOLD);
		
								
								txt_last_10_data[6]=(TextView)txt_last_10_data_6_layout.findViewById(R.id.txt_last_10_data_6);
								//txt_last_10_data[6].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[6].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[6].setTextColor(Color.BLACK);
								txt_last_10_data[6].setTextSize(10);
								txt_last_10_data[6].setGravity(Gravity.LEFT);
								//txt_last_10_data[6].setText("20");
								txt_last_10_data[6].setTypeface(null,Typeface.BOLD);
		
								
								
								txt_last_10_data[7]=(TextView)txt_last_10_data_7_layout.findViewById(R.id.txt_last_10_data_7);
								//txt_last_10_data[7].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[7].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[7].setTextColor(Color.BLUE);
								txt_last_10_data[7].setTextSize(10);
								txt_last_10_data[7].setGravity(Gravity.LEFT);
								//txt_last_10_data[7].setText("20");
								txt_last_10_data[7].setTypeface(null,Typeface.BOLD);
		
								
								
								
								txt_last_10_data[8]=(TextView)txt_last_10_data_8_layout.findViewById(R.id.txt_last_10_data_8);
								//txt_last_10_data[8].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[8].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[8].setTextColor(Color.BLACK);
								txt_last_10_data[8].setTextSize(10);
								txt_last_10_data[8].setGravity(Gravity.LEFT);
								//txt_last_10_data[8].setText("20");
								txt_last_10_data[8].setTypeface(null,Typeface.BOLD);
		
							   	
								
								
								txt_last_10_data[9]=(TextView)txt_last_10_data_9_layout.findViewById(R.id.txt_last_10_data_9);
								//txt_last_10_data[9].setPadding((int)((row1_column1_width*0.12)*0.30),(int)( row1_column3_cell1_height*0.42), 0, 0);
								txt_last_10_data[9].setLayoutParams(new LayoutParams((int)(row1_column1_width*0.11),row1_column3_cell1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								txt_last_10_data[9].setTextColor(Color.BLACK);
								txt_last_10_data[9].setTextSize(10);
								txt_last_10_data[9].setGravity(Gravity.LEFT);
								//txt_last_10_data[9].setText("20");
								txt_last_10_data[9].setTypeface(null,Typeface.BOLD);
	  						    /* first row third column second cell second row ends here*/
	  						    
	  						  /*first row third column second cell third row starts here*/
	  							int horizon3_height=(int)(row1_column3_cell1_height*0.05);
	  							LinearLayout horizon3=(LinearLayout)txt_last_10_data_vertical_layout.findViewById(R.id.horizon3);
	  							horizon3.setLayoutParams(new LinearLayout.LayoutParams( row1_column1_width,horizon3_height));
	  							horizon3.setOrientation(LinearLayout.HORIZONTAL);
	  							//horizon3.setBackgroundColor(Color.BLUE);
	  						    /* first row third column second cell third row ends here*/
	  						    /*first row third column second cell vertical ends here*/
	  						         
	  						       
	  						    

	  						    
	  						    
	  					  /* first row third column second cell ends here  */
	  						        
	  					  /* first row third column third cell starts here  */
	  						     int row1_column3_cell3_width=row1_column1_width;
	  						     int row1_column3_cell3_height=(int)((row1_column1_height- row1_column1_cell1_height*2));
	  						        
	  						     target_increment_second_bet_layout=(LinearLayout) first_row_third_column.findViewById(R.id.target_increment_second_bet_layout);
	  						     target_increment_second_bet_layout.setLayoutParams(new LinearLayout.LayoutParams( row1_column3_cell3_width,row1_column3_cell3_height));
	  						     target_increment_second_bet_layout.setOrientation(LinearLayout.HORIZONTAL);
	  						     //target_increment_second_bet_layout.setBackgroundColor(Color.BLUE);
	  						        
	  						     LayoutInflater target_inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  						     LinearLayout target_increment_bet2_layout=(LinearLayout) target_inflater.inflate(R.layout.target_increment_bet2_keypad, null);
	  						     initialize_increment_bet2_keypad(target_increment_bet2_layout,row1_column3_cell3_width,row1_column3_cell3_height);
	  							 B inc_obj=new B();
	  							 inc_obj.calculate_keypad_bet(target_increment_bet_layout,target_increment_bet2_layout);   // to calculate bet on each key
	  						     target_increment_second_bet_layout.addView(target_increment_bet2_layout);
	  					  /* first row third column third cell ends here  */
	  					        
	  				        
	  			        /* first row third column ends here */
	  				        
	  		     
	  		     /* first row ends here  */
	  	         
	  						    int second_row_first_column_width=(int)(screenWidthDp*0.11);
	  				  	        int second_row_second_column_width=(int)(screenWidthDp*0.07);
	  				  	        int second_row_third_column_width=(int)(screenWidthDp*0.15);
	  				  	        int second_row_fourth_column_width=(int)(screenWidthDp*0.34);
	  				  	        int second_row_fifth_column_width=(int)(screenWidthDp*0.15);
	  				  	        int second_row_sixth_column_width=(int)(screenWidthDp*0.07);
	  				  	        int second_row_seventh_column_width=(int)(screenWidthDp*0.12);
	  		     /*  second row starts here  */
	  		     LinearLayout second_row=(LinearLayout)mainlinearlayout.findViewById(R.id.second_row);
	  		     second_row.setLayoutParams(new LinearLayout.LayoutParams(screenWidthDp,row2_column2_height));
	  		     second_row.setOrientation(LinearLayout.HORIZONTAL);
	  		     //second_row.setBackgroundColor(Color.RED);
	  		     second_row.setBackgroundResource(R.drawable.funtargetback);
	  		     
	  		     		/*second_row_first_column starts here*/
	  		     		LinearLayout second_row_first_column=(LinearLayout) second_row.findViewById(R.id.secondrow_firstcolumn);
	  		     		second_row_first_column.setLayoutParams(new LayoutParams(second_row_first_column_width, row2_column2_height));
	  		     		second_row_first_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_first_column.setBackgroundColor(Color.RED);
	  		     		
	  		     				/*secondrow_firstcolumn_firstcell starts here*/
	  		     				LinearLayout secondrow_firstcolumn_firstcell=(LinearLayout) second_row_first_column.findViewById(R.id.secondrow_firstcolumn_firstcell);
	  		     				secondrow_firstcolumn_firstcell.setLayoutParams(new LayoutParams(second_row_first_column_width,(int) (row2_column2_height*0.65)));
	  		     				secondrow_firstcolumn_firstcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				//secondrow_firstcolumn_firstcell.setBackgroundColor(Color.RED);
	  		     				/*secondrow_firstcolumn_firstcell ends here*/
	  		     				
	  		     				/*secondrow_firstcolumn_secondcell starts here*/
	  		     				LinearLayout secondrow_firstcolumn_secondcell=(LinearLayout) second_row_first_column.findViewById(R.id.secondrow_firstcolumn_secondcell);
	  		     				secondrow_firstcolumn_secondcell.setLayoutParams(new LayoutParams(second_row_first_column_width,(int) (row2_column2_height*0.18)));
	  		     				secondrow_firstcolumn_secondcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				
	  		     					/*btn_take imageview starts here*/
	  		     					ImageView btn_take=(ImageView) secondrow_firstcolumn_secondcell.findViewById(R.id.btntake);
	  		     					btn_take.setLayoutParams(new LayoutParams(second_row_first_column_width,(int) (row2_column2_height*0.18)));
	  		     					btn_take.setBackgroundResource(R.drawable.btn_take);
	  		     					btn_take.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											
											Toast.makeText(getApplicationContext(), "Take button clicked",Toast.LENGTH_SHORT).show();
										}
									});
	  		     					/*btn_take imageview ends here*/
	  		     				/*secondrow_firstcolumn_secondcell ends here*/
	  		     				
	  		     				/*secondrow_firstcolumn_thirdcell starts here*/
	  		     				LinearLayout secondrow_firstcolumn_thirdcell=(LinearLayout) second_row_first_column.findViewById(R.id.secondrow_firstcolumn_thirdcell);
	  		     				secondrow_firstcolumn_thirdcell.setLayoutParams(new LayoutParams(second_row_first_column_width,(int) (row2_column2_height*0.18)));
	  		     				secondrow_firstcolumn_thirdcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				//secondrow_firstcolumn_thirdcell.setBackgroundColor(Color.MAGENTA);
	  		     				/*secondrow_firstcolumn_thirdcell ends here*/
	  		     		/*second_row_first_column ends here*/
	  		     		
	  		     		/*second_row_second_column starts here*/
	  		     		LinearLayout second_row_second_column=(LinearLayout) second_row.findViewById(R.id.secondrow_secondcolumn);
	  		     		second_row_second_column.setLayoutParams(new LayoutParams(second_row_second_column_width, row2_column2_height));
	  		     		second_row_second_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_second_column.setBackgroundColor(Color.GREEN);
	  		     		/*second_row_second_column ends here*/
	  		     		
	  		     		/*second_row_third_column starts here*/
	  		     		LinearLayout second_row_third_column=(LinearLayout) second_row.findViewById(R.id.secondrow_thirdcolumn);
	  		     		second_row_third_column.setLayoutParams(new LayoutParams(second_row_third_column_width, row2_column2_height));
	  		     		second_row_third_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_third_column.setBackgroundColor(Color.CYAN);
	  		     		
	  		     		
	  		     				/*secondrow_thirdcolumn_firstcell starts here*/
	  		     				LinearLayout secondrow_thirdcolumn_firstcell=(LinearLayout) second_row_third_column.findViewById(R.id.secondrow_thirdcolumn_firstcell);
	  		     				secondrow_thirdcolumn_firstcell.setLayoutParams(new LayoutParams(second_row_third_column_width, (int)(row2_column2_height*0.71)));
	  		     				secondrow_thirdcolumn_firstcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				//secondrow_thirdcolumn_firstcell.setBackgroundColor(Color.GREEN);
	  		     				/*secondrow_thirdcolumn_firstcell ends here*/
	  		     				
	  		     				/*secondrow_thirdcolumn_secondcell starts here*/
	  		     				LinearLayout secondrow_thirdcolumn_secondcell=(LinearLayout) second_row_third_column.findViewById(R.id.secondrow_thirdcolumn_secondcell);
	  		     				secondrow_thirdcolumn_secondcell.setLayoutParams(new LayoutParams(second_row_third_column_width, (int)(row2_column2_height*0.18)));
	  		     				secondrow_thirdcolumn_secondcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				//secondrow_thirdcolumn_secondcell.setBackgroundColor(Color.RED);
	  		     				
	  		     				
	  		     				     /*btn_cancelbet imageview starts here*/
	  		     				     ImageView btn_cancelbet=(ImageView) secondrow_thirdcolumn_secondcell.findViewById(R.id.btn_cancelbet);
	  		     				     btn_cancelbet.setLayoutParams(new LayoutParams(second_row_third_column_width, (int)(row2_column2_height*0.18)));
	  		     				     btn_cancelbet.setBackgroundResource(R.drawable.btn_cancel);
	  		     				     btn_cancelbet.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View v) {
											
											Toast.makeText(getApplicationContext(), "cancel bet button clicked", Toast.LENGTH_SHORT).show();
										}
									});
	  		     				  /*btn_cancelbet imageview ends here*/
	  		     				/*secondrow_thirdcolumn_secondcell ends here*/
	  		     				
	  		     				/*secondrow_thirdcolumn_thirdcell starts here*/
	  		     				LinearLayout secondrow_thirdcolumn_thirdcell=(LinearLayout) second_row_third_column.findViewById(R.id.secondrow_thirdcolumn_thirdcell);
	  		     				secondrow_thirdcolumn_thirdcell.setLayoutParams(new LayoutParams(second_row_third_column_width, (int)(row2_column2_height*0.15)));
	  		     				secondrow_thirdcolumn_thirdcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     				//secondrow_thirdcolumn_thirdcell.setBackgroundColor(Color.YELLOW);
	  		     				/*secondrow_thirdcolumn_thirdcell ends here*/
	  		     		/*second_row_third_column ends here*/
	  		     		
	  		     		/*second_row_fourth_column starts here*/
	  		     		LinearLayout second_row_fourth_column=(LinearLayout) second_row.findViewById(R.id.secondrow_fourthcolumn);
	  		     		second_row_fourth_column.setLayoutParams(new LayoutParams(second_row_fourth_column_width, row2_column2_height));
	  		     		second_row_fourth_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_fourth_column.setBackgroundColor(Color.MAGENTA);
	  		     		/*second_row_fourth_column ends here*/
	  		     		
	  		     		/*second_row_fifth_column starts here*/
	  		     		LinearLayout second_row_fifth_column=(LinearLayout) second_row.findViewById(R.id.secondrow_fifthcolumn);
	  		     		second_row_fifth_column.setLayoutParams(new LayoutParams(second_row_fifth_column_width, row2_column2_height));
	  		     		second_row_fifth_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_fifth_column.setBackgroundColor(Color.BLUE);
	  		     		
	  		     			/*secondrow_fifthcolumn_firstcell starts here*/
	  		     			LinearLayout secondrow_fifthcolumn_firstcell=(LinearLayout) second_row_fifth_column.findViewById(R.id.secondrow_fifthcolumn_firstcell);
	  		     			secondrow_fifthcolumn_firstcell.setLayoutParams(new LayoutParams(second_row_fifth_column_width, (int)(row2_column2_height*0.70)));
	  		     			secondrow_fifthcolumn_firstcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_fifthcolumn_firstcell.setBackgroundColor(Color.GREEN);
	  		     			/*secondrow_fifthcolumn_firstcell ends here*/
	  		     			
	  		     			/*secondrow_fifthcolumn_secondcell starts here*/
	  		     			LinearLayout secondrow_fifthcolumn_secondcell=(LinearLayout) second_row_fifth_column.findViewById(R.id.secondrow_fifthcolumn_secondcell);
	  		     			secondrow_fifthcolumn_secondcell.setLayoutParams(new LayoutParams(second_row_fifth_column_width, (int)(row2_column2_height*0.18)));
	  		     			secondrow_fifthcolumn_secondcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_fifthcolumn_secondcell.setBackgroundColor(Color.RED);
	  		     			
	  		     				/*btn_cancelspecificbet imageview starts here*/
	  		     				ImageView btn_cancelspecificbet=(ImageView) secondrow_fifthcolumn_secondcell.findViewById(R.id.btn_cancelspecificbet);
	  		     				btn_cancelspecificbet.setLayoutParams(new LayoutParams(second_row_fifth_column_width, (int)(row2_column2_height*0.18)));
	  		     				btn_cancelspecificbet.setBackgroundResource(R.drawable.btn_cancelspecificbet);
	  		     				btn_cancelspecificbet.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										
										Toast.makeText(getApplicationContext(), "Cancel specific bet button clicked", Toast.LENGTH_SHORT).show();
									}
								});
	  		     				/*btn_cancelspecificbet imageview ends here*/
	  		     			/*secondrow_fifthcolumn_secondcell ends here*/
	  		     			
	  		     			/*secondrow_fifthcolumn_thirdcell starts here*/
	  		     			LinearLayout secondrow_fifthcolumn_thirdcell=(LinearLayout) second_row_fifth_column.findViewById(R.id.secondrow_fifthcolumn_thirdcell);
	  		     			secondrow_fifthcolumn_thirdcell.setLayoutParams(new LayoutParams(second_row_fifth_column_width, (int)(row2_column2_height*0.15)));
	  		     			secondrow_fifthcolumn_thirdcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_fifthcolumn_thirdcell.setBackgroundColor(Color.CYAN);
	  		     			/*secondrow_fifthcolumn_thirdcell ends here*/
	  		     		/*second_row_fifth_column ends here*/
	  		     		
	  		     		/*second_row_sixth_column starts here*/
	  		     		LinearLayout second_row_sixth_column=(LinearLayout) second_row.findViewById(R.id.secondrow_sixthcolumn);
	  		     		second_row_sixth_column.setLayoutParams(new LayoutParams(second_row_sixth_column_width, row2_column2_height));
	  		     		second_row_sixth_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_sixth_column.setBackgroundColor(Color.YELLOW);
	  		     		/*second_row_sixth_column ends here*/
	  		     		
	  		     		/*second_row_seventh_column starts here*/
	  		     		LinearLayout second_row_seventh_column=(LinearLayout) second_row.findViewById(R.id.secondrow_seventhcolumn);
	  		     		second_row_seventh_column.setLayoutParams(new LayoutParams(second_row_seventh_column_width, row2_column2_height));
	  		     		second_row_seventh_column.setOrientation(LinearLayout.VERTICAL);
	  		     		//second_row_seventh_column.setBackgroundColor(Color.WHITE);
	  		     		
	  		     			/*secondrow_seventhcolumn_firstcell starts here*/
	  		     			LinearLayout secondrow_seventhcolumn_firstcell=(LinearLayout) second_row_seventh_column.findViewById(R.id.secondrow_seventhcolumn_firstcell);
	  		     			secondrow_seventhcolumn_firstcell.setLayoutParams(new LayoutParams(second_row_seventh_column_width, (int)(row2_column2_height*0.66)));
	  		     			secondrow_seventhcolumn_firstcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_seventhcolumn_firstcell.setBackgroundColor(Color.GRAY);
	  		     			/*secondrow_seventhcolumn_firstcell ends here*/
	  		     			
	  		     			/*secondrow_seventhcolumn_secondcell starts here*/
	  		     			LinearLayout secondrow_seventhcolumn_secondcell=(LinearLayout) second_row_seventh_column.findViewById(R.id.secondrow_seventhcolumn_secondcell);
	  		     			secondrow_seventhcolumn_secondcell.setLayoutParams(new LayoutParams(second_row_seventh_column_width, (int)(row2_column2_height*0.17)));
	  		     			secondrow_seventhcolumn_secondcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_seventhcolumn_secondcell.setBackgroundColor(Color.GRAY);
	  		     			
	  		     				/*bet_ok imageview starts here*/
	  		     				ImageView btn_betok=(ImageView) secondrow_seventhcolumn_secondcell.findViewById(R.id.btn_betok);
	  		     				btn_betok.setLayoutParams(new LayoutParams(second_row_seventh_column_width, (int)(row2_column2_height*0.17)));
	  		     				btn_betok.setBackgroundResource(R.drawable.btn_betok);
	  		     				btn_betok.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										
										Toast.makeText(getApplicationContext(), "Bet ok clicked", Toast.LENGTH_SHORT).show();
									}
								});
	  		     				/*bet_ok imageview ends here*/
	  		     			/*secondrow_seventhcolumn_secondcell ends here*/
	  		     			
	  		     			/*secondrow_seventhcolumn_thirdcell starts here*/
	  		     			LinearLayout secondrow_seventhcolumn_thirdcell=(LinearLayout) second_row_seventh_column.findViewById(R.id.secondrow_seventhcolumn_thirdcell);
	  		     			secondrow_seventhcolumn_thirdcell.setLayoutParams(new LayoutParams(second_row_seventh_column_width, (int)(row2_column2_height*0.18)));
	  		     			secondrow_seventhcolumn_thirdcell.setOrientation(LinearLayout.HORIZONTAL);
	  		     			//secondrow_seventhcolumn_thirdcell.setBackgroundColor(Color.GREEN);
	  		     			/*secondrow_seventhcolumn_thirdcell ends here*/
	  		     		/*second_row_seventh_column ends here*/
	  		     
	  		     
	  		     /*code for slider starts here*/
	  		     	//backgroundimageview=(ImageView)second_row.findViewById(R.id.backgroundimageview);
	  		     	//backgroundimageview.setLayoutParams(new LayoutParams(screenWidthDp, row2_column2_height));
	  		   /*code for slider ends here*/
	  		     /* second row ends here  */
	  	        
	  	        
	  		     /*third row starts here  */  
	  		     third_row=(LinearLayout)mainlinearlayout.findViewById(R.id.third_row);
	  		     third_row.setLayoutParams(new LinearLayout.LayoutParams(row3_width,row3_height));
	  		     third_row.setOrientation(LinearLayout.HORIZONTAL);
	  			 //third_row.setBackgroundColor(Color.CYAN);
	  				       
	  		       
	  		     LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  			 LinearLayout funtarget_keypad_layout=(LinearLayout) inflate.inflate(R.layout.target_keypad, null);
	  			 initialize_keypad(funtarget_keypad_layout,row3_width,row3_height);  // to initialize keypad
	  			 //funtarget_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  			 A obj=new A();
	  			 obj.calculate_keypad_bet(funtarget_keypad_layout);   // to calculate bet on each key
	  			 third_row.addView(funtarget_keypad_layout);
	  		     /* third row ends here  */
	  		      
	  		      
	  		      
	  		      /*  fourth row starts here  */
	  		       LinearLayout fourth_row=(LinearLayout)mainlinearlayout.findViewById(R.id.fourth_row);
	  		       fourth_row.setLayoutParams(new LinearLayout.LayoutParams(screenWidthDp,row4_column1_height));
	  		       fourth_row.setOrientation(LinearLayout.HORIZONTAL);
	  			   //fourth_row.setBackgroundColor(Color.MAGENTA);
	  		
	  			
	  					 /* fourth row first column starts here */
	  				       LinearLayout txt_bottom_won_amount_layout=(LinearLayout) fourth_row.findViewById(R.id.txt_bottom_won_amount_layout);
	  				       txt_bottom_won_amount_layout.setLayoutParams(new LinearLayout.LayoutParams( row4_column1_width,row4_column1_height));
	  				       txt_bottom_won_amount_layout.setOrientation(LinearLayout.VERTICAL);
	  				        //txt_bottom_won_amount_layout.setBackgroundColor(Color.YELLOW);
	  				        
	  				        /*To set textview in fourthrow first vertical linear layout starts here*/
	  						txt_bottom_won_amount=(TextView)txt_bottom_won_amount_layout.findViewById(R.id.txt_bottom_won_amount);
	  						txt_bottom_won_amount.setText("0");
	  						txt_bottom_won_amount.setTextColor(Color.WHITE);
	  						//txt_bottom_won_amount.setGravity(Gravity.CENTER);
	  						txt_bottom_won_amount.setTextSize(getResources().getDimension(R.dimen.targetfourthrowtextsize));
	  						txt_bottom_won_amount.setPadding(45,10,25,0);
	  						/*To set textview in fourthrow first vertical linear layout ends here*/
	  						
	  						
	  			         /* fourth row first column ends here */
	  				    
	  				    /* fourth row second column starts here */
	  				        LinearLayout txt_bottom_bet_status_layout=(LinearLayout)fourth_row.findViewById(R.id.txt_bottom_bet_status_layout);
	  				        txt_bottom_bet_status_layout.setLayoutParams(new LinearLayout.LayoutParams( row4_column2_width,row4_column2_height));
	  				        txt_bottom_bet_status_layout.setOrientation(LinearLayout.VERTICAL);
	  				        //txt_bottom_bet_status_layout.setBackgroundColor(Color.GREEN);
	  				        
	  				        /*To set textview in fourthrow second vertical linear layout starts here*/
	  						txt_bottom_bet_status=(TextView)txt_bottom_bet_status_layout.findViewById(R.id.txt_bottom_bet_status);
	  						txt_bottom_bet_status.setText("Please Bet to Start Game Minimum Bet=1");
	  						txt_bottom_bet_status.setTextColor(Color.BLACK);
	  						txt_bottom_bet_status.setGravity(Gravity.LEFT);
	  						txt_bottom_bet_status.setTextSize(getResources().getDimension(R.dimen.targetfourthrowtextsize));
	  						txt_bottom_bet_status.setPadding(250,12,20,0);
	  						txt_bottom_bet_status.setTypeface(null,Typeface.BOLD);
	  						/*To set textview in fourthrow second vertical linear layout ends here*/
	  						
	  						
	  			      /* fourth row second column ends here */
	  			
	  				    /* fourth row third column starts here */
	  				        LinearLayout fourth_row_third_column=(LinearLayout)fourth_row.findViewById(R.id.fourth_row_third_column);
	  				        fourth_row_third_column.setLayoutParams(new LinearLayout.LayoutParams( row4_column3_width,row4_column3_height));
	  				        fourth_row_third_column.setOrientation(LinearLayout.VERTICAL);
	  				        //fourth_row_third_column.setBackgroundColor(Color.YELLOW);
	  				        
	  				        /*fourth row third column first horizontal layout starts here*/
	  				        int row4_column3_horizon_height=(int)(row4_column3_height*0.21);
	  				        LinearLayout empty_row_above_exit_button=(LinearLayout)fourth_row_third_column.findViewById(R.id.empty_row_above_exit_button);
	  				        empty_row_above_exit_button.setLayoutParams(new LinearLayout.LayoutParams(row4_column3_width,row4_column3_horizon_height));
	  				        empty_row_above_exit_button.setOrientation(LinearLayout.HORIZONTAL);
	  				        //empty_row_above_exit_button.setBackgroundColor(Color.GREEN);
	  				        /*fourth row third column first horizontal layout ends here*/
	  				        
	  				        /*fourth row third column second horizontal layout starts here*/
	  				        LinearLayout btn_exit_layout=(LinearLayout)fourth_row_third_column.findViewById(R.id.btn_exit_layout);
	  				        btn_exit_layout.setLayoutParams(new LinearLayout.LayoutParams(row4_column3_width, (int)(row4_column3_height*0.79)));
	  				        btn_exit_layout.setOrientation(LinearLayout.HORIZONTAL);
	  				        //btn_exit_layout.setBackgroundColor(Color.RED);
	  				        
	  				      /*fourth row third column second horizontal first vertical layout starts here*/
	  				        LinearLayout btn_exit_vertical1_layout=(LinearLayout)btn_exit_layout.findViewById(R.id.btn_exit_vertical1);
	  				        btn_exit_vertical1_layout.setLayoutParams(new LinearLayout.LayoutParams((int)(row4_column3_width*0.12), (int)(row4_column3_height*0.79)));
	  				        btn_exit_vertical1_layout.setOrientation(LinearLayout.VERTICAL);
	  				    	//btn_exit_vertical1_layout.setBackgroundColor(Color.MAGENTA);
	  				      /*fourth row third column second horizontal first vertical layout ends here*/
	  				    	   
	  				    	
	  				    	/*fourth row third column second horizontal second vertical layout starts here*/
	  				        LinearLayout btn_exit_vertical2_layout=(LinearLayout)btn_exit_layout.findViewById(R.id.btn_exit_vertical2);
	  				        btn_exit_vertical2_layout.setLayoutParams(new LinearLayout.LayoutParams((int)(row4_column3_width*0.88), (int)(row4_column3_height*0.79)));
	  				    	btn_exit_vertical2_layout.setOrientation(LinearLayout.VERTICAL);
	  				    	//btn_exit_vertical2_layout.setBackgroundColor(Color.YELLOW);
	  				    	
	  				    	
	  				    	
	  				    	/*To set imageview in fourthrow third vertical linear layout starts here*/
								btn_exit=(ImageView)btn_exit_layout.findViewById(R.id.btn_exit);
								btn_exit.setLayoutParams(new LinearLayout.LayoutParams(row4_column3_width, (int)(row4_column3_height*0.79)));
								//btn_exit.setBackgroundResource(R.drawable.exit);
								btn_exit.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										btn_exit.setBackgroundResource(R.drawable.exit_glow);
										Toast.makeText(v.getContext(), "Exit clicked", Toast.LENGTH_LONG).show();
									}
								});
								/*To set imageview in fourthrow third vertical linear layout ends here*/
						
	  				      /*fourth row third column second horizontal second vertical layout ends here*/
	  				        
	  				        
	  						        
	  				        /*fourth row third column second horizontal layout ends here*/
	  				        
	  			      /* fourth row third column ends here */
	  		
	  		     /* fourth row ends here  */

	  		
	  		}
	  		
	  			
	  		//to initialize target_increment_bet_keypad starts here
	  		private void initialize_increment_bet_keypad(LinearLayout target_increment_bet_layout,int row1_column1_cell3_width,int row1_column1_cell3_height   )
	  		{
	  			target_increment_bet_layout.setLayoutParams(new LinearLayout.LayoutParams(row1_column1_cell3_width,row1_column1_cell3_height));
	  			
	  			/*finding horizontal rows for 1,5,10,50 textboxes layouts starts here*/
	  			int target_increment_bet_horizontal_row_height=(int)(row1_column1_cell3_height*0.10);
	  			int target_increment_bet_horizontal_row_width=(int)(row1_column1_cell3_width);
	  			
	  			final LinearLayout target_first_empty_horizontal_row=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_increment_bet_empty_layout_before_1_5_10_50);
	  			target_first_empty_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet_horizontal_row_width,(int)(target_increment_bet_horizontal_row_height*5.00)));
	  			//target_first_empty_horizontal_row.setBackgroundColor(Color.GREEN);
	  			
	  			final LinearLayout target_second_horizontal_row=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_increment_bet_layout_1_5_10_50);
	  			target_second_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet_horizontal_row_width,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_second_horizontal_row.setBackgroundColor(Color.RED);
	  			
	  			final LinearLayout target_third_empty_horizontal_row=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_increment_bet_empty_layout_after_1_5_10_50);
	  			target_third_empty_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet_horizontal_row_width,(int)(target_increment_bet_horizontal_row_height*1.20)));
	  			//target_third_empty_horizontal_row.setBackgroundColor(Color.WHITE);
	  			/*finding horizontal rows for 1,5,10,50 textboxes layouts ends here*/
	  			
	  			
	  			/*finding textboxes vertical empty layout for 1,5,10,50 starts here*/
	  			final LinearLayout target_empty_before_1_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_empty_before_1_layout);
	  			target_empty_before_1_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.01) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_empty_before_1_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_1_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_empty_after_1_layout);
	  			target_empty_after_1_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.03) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_empty_after_1_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_5_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_empty_after_5_layout);
	  			target_empty_after_5_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.03) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_empty_after_5_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_10_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_empty_after_10_layout);
	  			target_empty_after_10_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.03) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_empty_after_10_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_50_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_empty_after_50_layout);
	  			target_empty_after_50_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.14) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_empty_after_50_layout.setBackgroundColor(Color.CYAN);
	  			/*finding textboxes vertical empty layout for 1,5,10,50 ends here*/
	  			
	  			
	  			/*finding textboxes vertical layout for 1,5,10,50 starts here*/
	  			final LinearLayout target_txt_1_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_txt_1_layout);
	  			target_txt_1_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.20) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_txt_1_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_5_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_txt_5_layout);
	  			target_txt_5_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.19) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_txt_5_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_10_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_txt_10_layout);
	  			target_txt_10_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.19) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_txt_10_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_50_layout=(LinearLayout) target_increment_bet_layout.findViewById(R.id.target_txt_50_layout);
	  			target_txt_50_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet_horizontal_row_width*0.19) ,(int)(target_increment_bet_horizontal_row_height*4.05)));
	  			//target_txt_50_layout.setBackgroundColor(Color.BLUE);
	  			/*finding textboxes vertical layout for 1,5,10,50 ends here*/
	  		}
	  		//to initialize target_increment_bet_keypad ends here
	  		
	  		
	  		/*to initialize target_increment_bet2_keypad starts here*/
	  		private void initialize_increment_bet2_keypad(LinearLayout target_increment_bet2_layout,int row1_column3_cell3_width,int row1_column3_cell3_height )
	  		{
	  			target_increment_bet2_layout.setLayoutParams(new LinearLayout.LayoutParams(row1_column3_cell3_width,row1_column3_cell3_height));
	  			
	  			/*finding horizontal rows for 100,500,1000,5000 textboxes layouts starts here*/
	  			int target_increment_bet2_horizontal_row_height=(int)(row1_column3_cell3_height*0.10);
	  			int target_increment_bet2_horizontal_row_width=(int)(row1_column3_cell3_width);
	  			
	  			final LinearLayout target_bet2_first_empty_horizontal_row=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_increment_bet2_empty_layout_before_100_500_1000_5000);
	  			target_bet2_first_empty_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet2_horizontal_row_width,(int)(target_increment_bet2_horizontal_row_height*5.00)));
	  			//target_bet2_first_empty_horizontal_row.setBackgroundColor(Color.MAGENTA);
	  			
	  			final LinearLayout target_bet2_second_horizontal_row=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_increment_bet2_layout_100_500_1000_5000);
	  			target_bet2_second_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet2_horizontal_row_width,(int)(target_increment_bet2_horizontal_row_height*4.10)));
	  			//target_bet2_second_horizontal_row.setBackgroundColor(Color.RED);
	  			
	  			final LinearLayout target_bet2_third_empty_horizontal_row=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_increment_bet2_empty_layout_after_100_500_1000_5000);
	  			target_bet2_third_empty_horizontal_row.setLayoutParams(new LayoutParams(target_increment_bet2_horizontal_row_width,(int)(target_increment_bet2_horizontal_row_height*1.02)));
	  			//target_bet2_third_empty_horizontal_row.setBackgroundColor(Color.WHITE);
	  			/*finding horizontal rows for 100,500,1000,5000 textboxes layouts ends here*/
	  			
	  			/*finding textboxes vertical empty layout for 100,500,1000,5000 starts here*/
	  		  	int target_increment_bet2_vertical_row_width=(int)(target_increment_bet2_horizontal_row_width*0.05);
	  			
	  			final LinearLayout target_empty_before_100_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_empty_before_100_layout);
	  			target_empty_before_100_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.13) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_empty_before_100_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_100_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_empty_after_100_layout);
	  			target_empty_after_100_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.04) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_empty_after_100_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_500_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_empty_after_500_layout);
	  			target_empty_after_500_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.03) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_empty_after_500_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_1000_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_empty_after_1000_layout);
	  			target_empty_after_1000_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.03) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_empty_after_1000_layout.setBackgroundColor(Color.CYAN);
	  			
	  			final LinearLayout target_empty_after_5000_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_empty_after_5000_layout);
	  			target_empty_after_5000_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.02) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_empty_after_5000_layout.setBackgroundColor(Color.CYAN);
	  			/*finding textboxes vertical empty layout for 100,500,1000,5000 ends here*/
	  			
	  			/*finding textboxes vertical layout for 100,500,1000,5000 starts here*/
	  			final LinearLayout target_txt_100_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_txt_100_layout);
	  			target_txt_100_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.19) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  		    //target_txt_100_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_500_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_txt_500_layout);
	  			target_txt_500_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.19) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_txt_500_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_1000_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_txt_1000_layout);
	  			target_txt_1000_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.19) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_txt_1000_layout.setBackgroundColor(Color.BLUE);
	  			
	  			final LinearLayout target_txt_5000_layout=(LinearLayout) target_increment_bet2_layout.findViewById(R.id.target_txt_5000_layout);
	  			target_txt_5000_layout.setLayoutParams(new LayoutParams((int)(target_increment_bet2_horizontal_row_width*0.19) ,(int)(target_increment_bet2_horizontal_row_height*4.05)));
	  			//target_txt_5000_layout.setBackgroundColor(Color.BLUE);
	  			/*finding textboxes vertical layout for 100,500,1000,5000 ends here*/
	  		}
	  		/*to initialize target_increment_bet2_keypad ends here*/
	  		
	  		
	  		
	  		public class Get_FunTarget_GameDetails_Class extends AsyncTask<Void, Void, Void>
	  		{
	  			  int status=0;
	  	          Service_Client_Interaction_Target client=new Service_Client_Interaction_Target();
	  			  @Override
	  			  protected Void doInBackground(Void... arg0)
	  			  {
	  				  Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	  			      try
	  			      {
	  			    	  client.Get_FunTarget_Game_Details();
	  			    	  status=1;
	  		          }
	  			      catch (Exception e)   {
	  			            e.printStackTrace();
	  			         }
	  			      return_status_async_GAMEDETAILS_Target=status;
	  			      return null;
	  			   }

	  			   protected void onPostExecute(Void result) {		  }

	  			 }


	  		
	  		
	  		// to initialize target keypad
	  		private void initialize_keypad(LinearLayout funtarget_keypad_layout,int row3_width,int row3_height)
	  		{
	  			funtarget_keypad_layout.setLayoutParams(new LinearLayout.LayoutParams(row3_width,row3_height));
	  			 /* to set background image target_keypad_bg in main grid starts  */
	  			    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.target_keypad_bg);
	  			    //funtarget_keypad_layout.setBackgroundDrawable(new BitmapDrawable(bitmap));
	  		     /* to set background image bg in main grid ends  */

	  			
	  		    /*   first horizontal linear layout for target betbox starts here  */
	                       int layout_keypad_target_betbox_height=(int)(row3_height*0.4);
	  		             int layout_keypad_target_betbox_width=(int)(row3_width);
	  		             final LinearLayout layout_keypad_target_betbox=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.layout_keypad_target_betbox);
	  		     
	  		             layout_keypad_target_betbox.setLayoutParams(new LayoutParams(layout_keypad_target_betbox_width ,layout_keypad_target_betbox_height ));
	  		             //layout_keypad_target_betbox.setBackgroundColor(Color.RED);

	  		             
	  		             
	  		             /* finding layout of betbox for target keypad where bet amount will be displayed starts here */ 
	  			         
	  			                 /*   betbox_1_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_1_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_1_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_1_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_1_target_keypad_layout);
	  						     
	  						             betbox_1_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_1_target_keypad_layout_width ,betbox_1_target_keypad_layout_height ));
	  						             //betbox_1_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				             /*   betbox_1_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						             
	  						     /*   betbox_2_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_2_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_2_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_2_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_2_target_keypad_layout);
	  						     
	  						             betbox_2_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_2_target_keypad_layout_width ,betbox_2_target_keypad_layout_height ));
	  						             //betbox_2_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				             /*   betbox_2_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						    /*   betbox_3_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_3_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_3_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_3_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_3_target_keypad_layout);
	  						     
	  						             betbox_3_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_3_target_keypad_layout_width ,betbox_3_target_keypad_layout_height ));
	  						             //betbox_3_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				             /*   betbox_3_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						             
	  						    /*   betbox_4_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_4_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_4_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_4_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_4_target_keypad_layout);
	  						     
	  						             betbox_4_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_4_target_keypad_layout_width ,betbox_4_target_keypad_layout_height ));
	  						             //betbox_4_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				             /*   betbox_4_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						             
	  						    /*   betbox_5_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_5_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_5_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_5_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_5_target_keypad_layout);
	  						     
	  						             betbox_5_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_5_target_keypad_layout_width ,betbox_5_target_keypad_layout_height ));
	  						             //betbox_5_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				             /*   betbox_5_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						             
	  						    /*   betbox_6_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_6_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_6_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_6_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_6_target_keypad_layout);
	  						     
	  						             betbox_6_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_6_target_keypad_layout_width ,betbox_6_target_keypad_layout_height ));
	  						             //betbox_6_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				             /*   betbox_6_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						    /*   betbox_7_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_7_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_7_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_7_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_7_target_keypad_layout);
	  						     
	  						             betbox_7_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_7_target_keypad_layout_width ,betbox_7_target_keypad_layout_height ));
	  						             //betbox_7_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				             /*   betbox_7_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						    /*   betbox_8_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_8_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_8_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_8_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_8_target_keypad_layout);
	  						     
	  						             betbox_8_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_8_target_keypad_layout_width ,betbox_8_target_keypad_layout_height ));
	  						             //betbox_8_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				             /*   betbox_8_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						     /*   betbox_9_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_9_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_9_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_9_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_9_target_keypad_layout);
	  						     
	  						             betbox_9_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_9_target_keypad_layout_width ,betbox_9_target_keypad_layout_height ));
	  						             //betbox_9_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				             /*   betbox_9_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  						     /*   betbox_0_target_keypad vertical linear layout for target betbox starts here */
	  						             int betbox_0_target_keypad_layout_height=(int)(layout_keypad_target_betbox_height);
	  						             int betbox_0_target_keypad_layout_width=(int)(layout_keypad_target_betbox_width*0.10);
	  						             final LinearLayout betbox_0_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betbox_0_target_keypad_layout);
	  						     
	  						             betbox_0_target_keypad_layout.setLayoutParams(new LayoutParams(betbox_0_target_keypad_layout_width ,betbox_0_target_keypad_layout_height ));
	  						             //betbox_0_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				             /*   betbox_0_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  		             /* finding layout of betbox for target keypad where bet amount will be displayed ends here */
	  		             
	  		             
	  			/*   first horizontal linear layout for target betbox ends here  */

	  		             

	  		             
	  		    /*   second horizontal linear layout for target betkeys starts here  */
	                       int layout_keypad_target_betkeys_height=(int)(row3_height*0.58);
	  		             int layout_keypad_target_betkeys_width=(int)(row3_width);
	  		             final LinearLayout layout_keypad_target_betkeys=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.layout_keypad_target_betkeys);
	  		     
	  		             layout_keypad_target_betkeys.setLayoutParams(new LayoutParams(layout_keypad_target_betkeys_width ,layout_keypad_target_betkeys_height ));
	  		            // layout_keypad_target_betkeys.setBackgroundColor(Color.RED);

	  		             
	  		             /* finding layout of betkey for target keypad where bet amount buttons will be displayed starts here */ 
	  			         
	  					             /* empty layout before  betkey_1_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_1_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_1_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.02);
	  							             final LinearLayout empty_betkey_1_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_1_target_keypad_layout);
	  							     
	  							             empty_betkey_1_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_1_target_keypad_layout_width ,empty_betkey_1_target_keypad_layout_height ));
	  							             //empty_betkey_1_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  					            /*   empty layout before betkey_1_target_keypad vertical linear layout for target betbox ends here */
	  			             
	  			                    /*   betkey_1_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_1_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_1_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_1_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_1_target_keypad_layout);
	  							     
	  							             betkey_1_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_1_target_keypad_layout_width ,betkey_1_target_keypad_layout_height ));
	  						                //betkey_1_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  						       /*   betkey_1_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						             
	  						       /*  empty layout before betkey_2_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_2_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_2_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_2_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_2_target_keypad_layout);
	  							     
	  							             empty_betkey_2_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_2_target_keypad_layout_width ,empty_betkey_2_target_keypad_layout_height ));
	  							             //empty_betkey_2_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				               /*  empty layout before betkey_2_target_keypad vertical linear layout for target betkey ends here */
	  						             
	  						       /*   betkey_2_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_2_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_2_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_2_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_2_target_keypad_layout);
	  							     
	  							             betkey_2_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_2_target_keypad_layout_width ,betkey_2_target_keypad_layout_height ));
	  							             //betkey_2_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				               /*   betkey_2_target_keypad vertical linear layout for target betbox ends here */
	  						     
	  						             
	  						       /*  empty layout before betkey_3_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_3_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_3_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_3_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_3_target_keypad_layout);
	  							     
	  							             empty_betkey_3_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_3_target_keypad_layout_width ,empty_betkey_3_target_keypad_layout_height ));
	  							             //empty_betkey_3_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				               /*  empty layout before betkey_3_target_keypad vertical linear layout for target betkey ends here */
	  						      
	  						             
	  						             
	  						       /*   betkey_3_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_3_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_3_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_3_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_3_target_keypad_layout);
	  							     
	  							             betkey_3_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_3_target_keypad_layout_width ,betkey_3_target_keypad_layout_height ));
	  							             //betkey_3_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				              /*   betkey_3_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						      /*  empty layout before betkey_4_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_4_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_4_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_4_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_4_target_keypad_layout);
	  							     
	  							             empty_betkey_4_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_4_target_keypad_layout_width ,empty_betkey_4_target_keypad_layout_height ));
	  							             //empty_betkey_4_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_4_target_keypad vertical linear layout for target betkey ends here */
	  						     
	  						      /*   betkey_4_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_4_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_4_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_4_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_4_target_keypad_layout);
	  							     
	  							             betkey_4_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_4_target_keypad_layout_width ,betkey_4_target_keypad_layout_height ));
	  							             //betkey_4_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*   betkey_4_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						             
	  						             
	  						      /*  empty layout before betkey_5_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_5_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_5_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_5_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_5_target_keypad_layout);
	  							     
	  							             empty_betkey_5_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_5_target_keypad_layout_width ,empty_betkey_5_target_keypad_layout_height ));
	  							             //empty_betkey_5_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_5_target_keypad vertical linear layout for target betkey ends here */
	  						     
	  						             
	  						      /*   betkey_5_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_5_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_5_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_5_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_5_target_keypad_layout);
	  							     
	  							             betkey_5_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_5_target_keypad_layout_width ,betkey_5_target_keypad_layout_height ));
	  							             //betkey_5_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				              /*   betkey_5_target_keypad vertical linear layout for target betbox ends here */
	  						     
	  						             
	  						             
	  						      /*  empty layout before betkey_6_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_6_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_6_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_6_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_6_target_keypad_layout);
	  							     
	  							             empty_betkey_6_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_6_target_keypad_layout_width ,empty_betkey_6_target_keypad_layout_height ));
	  							             //empty_betkey_6_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_6_target_keypad vertical linear layout for target betkey ends here */
	  						    
	  						             
	  						      /*   betkey_6_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_6_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_6_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_6_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_6_target_keypad_layout);
	  							     
	  							             betkey_6_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_6_target_keypad_layout_width ,betkey_6_target_keypad_layout_height ));
	  							             //betkey_6_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*   betkey_6_target_keypad vertical linear layout for target betbox ends here */
	  						     
	  						             
	  						             
	  						      /*  empty layout before betkey_7_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_7_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_7_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_7_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_7_target_keypad_layout);
	  							     
	  							             empty_betkey_7_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_7_target_keypad_layout_width ,empty_betkey_7_target_keypad_layout_height ));
	  							             //empty_betkey_7_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_7_target_keypad vertical linear layout for target betkey ends here */
	  						             
	  						      /*   betkey_7_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_7_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_7_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_7_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_7_target_keypad_layout);
	  							     
	  							             betkey_7_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_7_target_keypad_layout_width ,betkey_7_target_keypad_layout_height ));
	  							             //betkey_7_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				              /*   betkey_7_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						      /*  empty layout before betkey_8_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_8_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_8_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_8_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_8_target_keypad_layout);
	  							     
	  							             empty_betkey_8_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_8_target_keypad_layout_width ,empty_betkey_8_target_keypad_layout_height ));
	  							             //empty_betkey_8_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_8_target_keypad vertical linear layout for target betkey ends here */
	  						             
	  						      /*   betkey_8_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_8_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_8_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_8_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_8_target_keypad_layout);
	  							     
	  							             betkey_8_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_8_target_keypad_layout_width ,betkey_8_target_keypad_layout_height ));
	  							             //betkey_8_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*   betkey_8_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						             
	  						      /*  empty layout before betkey_9_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_9_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_9_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_9_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_9_target_keypad_layout);
	  							     
	  							             empty_betkey_9_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_9_target_keypad_layout_width ,empty_betkey_9_target_keypad_layout_height ));
	  							             //empty_betkey_9_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_9_target_keypad vertical linear layout for target betkey ends here */
	  	
	  						      /*   betkey_9_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_9_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_9_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_9_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_9_target_keypad_layout);
	  							     
	  							             betkey_9_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_9_target_keypad_layout_width ,betkey_9_target_keypad_layout_height ));
	  							             //betkey_9_target_keypad_layout.setBackgroundColor(Color.TRANSPARENT);
	  				              /*   betkey_9_target_keypad vertical linear layout for target betbox ends here */
	  						     
	  						             
	  						             
	  						      /*  empty layout before betkey_0_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_betkey_0_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_betkey_0_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.04);
	  							             final LinearLayout empty_betkey_0_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_betkey_0_target_keypad_layout);
	  							     
	  							             empty_betkey_0_target_keypad_layout.setLayoutParams(new LayoutParams(empty_betkey_0_target_keypad_layout_width ,empty_betkey_0_target_keypad_layout_height ));
	  							             //empty_betkey_0_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout before betkey_0_target_keypad vertical linear layout for target betkey ends here */
	  						     
	  						      /*   betkey_0_target_keypad vertical linear layout for target betbox starts here */
	  							             int betkey_0_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int betkey_0_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.06);
	  							             final LinearLayout betkey_0_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.betkey_0_target_keypad_layout);
	  							     
	  							             betkey_0_target_keypad_layout.setLayoutParams(new LayoutParams(betkey_0_target_keypad_layout_width ,betkey_0_target_keypad_layout_height ));
	  							             //betkey_0_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*   betkey_0_target_keypad vertical linear layout for target betbox ends here */
	  						             
	  						             
	  						      /*  empty layout after betkey_0_target_keypad vertical linear layout for target betkey starts here */
	  							             int empty_after_betkey_0_target_keypad_layout_height=(int)(layout_keypad_target_betkeys_height);
	  							             int empty_after_betkey_0_target_keypad_layout_width=(int)(layout_keypad_target_betkeys_width*0.03);
	  							             final LinearLayout empty_after_betkey_0_target_keypad_layout=(LinearLayout) funtarget_keypad_layout.findViewById(R.id.empty_after_betkey_0_target_keypad_layout);
	  							     
	  							             empty_after_betkey_0_target_keypad_layout.setLayoutParams(new LayoutParams(empty_after_betkey_0_target_keypad_layout_width ,empty_after_betkey_0_target_keypad_layout_height ));
	  							             //empty_after_betkey_0_target_keypad_layout.setBackgroundColor(Color.WHITE);
	  				              /*  empty layout after betkey_0_target_keypad vertical linear layout for target betkey ends here */
	  						     					             
	  				     /* finding layout of betbox for target keypad where bet amount buttons will be displayed ends here */
	  		             
	  			/*   second horizontal linear layout for target betkeys ends here  */
	  		             
	  		             
	  		             
	  		}
	  		
	  		
	  		
	  		class A implements OnClickListener{
	  			TextView txt_betkey_1_target_keypad,txt_betkey_2_target_keypad,txt_betkey_3_target_keypad,txt_betkey_4_target_keypad,txt_betkey_5_target_keypad,txt_betkey_6_target_keypad,txt_betkey_7_target_keypad,txt_betkey_8_target_keypad,txt_betkey_9_target_keypad,txt_betkey_0_target_keypad;
	  			TextView txt_1,txt_2,txt_3,txt_4,txt_5,txt_6,txt_7,txt_8,txt_9,txt_0;
	  			
	  			private void calculate_keypad_bet(LinearLayout main_yellow_layout)   {
	  			
	  				is_New_Game_Target=true;
	  				
	  				/* finding textboxes to apply bet on keys starts here  */
	  				          /* setting properties of betkey_1 starts here  */
	  								txt_betkey_1_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_1_target_keypad);
	  								txt_betkey_1_target_keypad.setClickable(true);
	  								txt_betkey_1_target_keypad.setOnClickListener(this);
	  								txt_betkey_1_target_keypad.setText("1");
	  								txt_betkey_1_target_keypad.setTextColor(Color.WHITE);
	  								txt_betkey_1_target_keypad.setGravity(Gravity.CENTER);
	  								txt_betkey_1_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  				          /* setting properties of betkey_1 ends here  */


	    			              /* setting properties of betkey_2 starts here  */
	  								txt_betkey_2_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_2_target_keypad);
	  								txt_betkey_2_target_keypad.setClickable(true);
	  								txt_betkey_2_target_keypad.setOnClickListener(this);
	  								txt_betkey_2_target_keypad.setText("2");
	  								txt_betkey_2_target_keypad.setTextColor(Color.WHITE);
	  								txt_betkey_2_target_keypad.setGravity(Gravity.CENTER);
	  								txt_betkey_2_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  					     /* setting properties of betkey_2 ends here  */


	  					     /* setting properties of betkey_3 starts here  */
	  								txt_betkey_3_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_3_target_keypad);
	  								txt_betkey_3_target_keypad.setClickable(true);
	  								txt_betkey_3_target_keypad.setOnClickListener(this);
	  								txt_betkey_3_target_keypad.setText("3");
	  								txt_betkey_3_target_keypad.setTextColor(Color.WHITE);
	  								txt_betkey_3_target_keypad.setGravity(Gravity.CENTER);
	  								txt_betkey_3_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  					     /* setting properties of betkey_3 ends here  */


	  						  /* setting properties of betkey_4 starts here  */
	  								txt_betkey_4_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_4_target_keypad);
	  								txt_betkey_4_target_keypad.setClickable(true);
	  								txt_betkey_4_target_keypad.setOnClickListener(this);
	  								txt_betkey_4_target_keypad.setText("4");
	  								txt_betkey_4_target_keypad.setTextColor(Color.WHITE);
	  								txt_betkey_4_target_keypad.setGravity(Gravity.CENTER);
	  								txt_betkey_4_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						  /* setting properties of betkey_4 ends here  */


	  						  /* setting properties of betkey_5 starts here  */
	  								txt_betkey_5_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_5_target_keypad);
	  								txt_betkey_5_target_keypad.setClickable(true);
	  								txt_betkey_5_target_keypad.setOnClickListener(this);
	  								txt_betkey_5_target_keypad.setText("5");
	  								txt_betkey_5_target_keypad.setTextColor(Color.WHITE);
	  								txt_betkey_5_target_keypad.setGravity(Gravity.CENTER);
	  								txt_betkey_5_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						  /* setting properties of betkey_5 ends here  */


	  						  /* setting properties of betkey_6 starts here  */
	  						     	 txt_betkey_6_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_6_target_keypad);
	  								 txt_betkey_6_target_keypad.setClickable(true);
	  								 txt_betkey_6_target_keypad.setOnClickListener(this);
	  								 txt_betkey_6_target_keypad.setText("6");
	  								 txt_betkey_6_target_keypad.setTextColor(Color.WHITE);
	  								 txt_betkey_6_target_keypad.setGravity(Gravity.CENTER);
	  								 txt_betkey_6_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						  /* setting properties of betkey_6 ends here  */
	  											

	  						  /* setting properties of betkey_7 starts here  */
	  						    	 txt_betkey_7_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_7_target_keypad);
	  								 txt_betkey_7_target_keypad.setClickable(true);
	  								 txt_betkey_7_target_keypad.setOnClickListener(this);
	  								 txt_betkey_7_target_keypad.setText("7");
	  								 txt_betkey_7_target_keypad.setTextColor(Color.WHITE);
	  								 txt_betkey_7_target_keypad.setGravity(Gravity.CENTER);
	  								 txt_betkey_7_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						   /* setting properties of betkey_7 ends here  */

	  								 
	  						   /* setting properties of betkey_8 starts here  */
	  						    	 txt_betkey_8_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_8_target_keypad);
	  								 txt_betkey_8_target_keypad.setClickable(true);
	  								 txt_betkey_8_target_keypad.setOnClickListener(this);
	  								 txt_betkey_8_target_keypad.setText("8");
	  								 txt_betkey_8_target_keypad.setTextColor(Color.WHITE);
	  								 txt_betkey_8_target_keypad.setGravity(Gravity.CENTER);
	  								 txt_betkey_8_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						   /* setting properties of betkey_8 ends here  */

	  						   /* setting properties of betkey_9 starts here  */
	  						    	 txt_betkey_9_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_9_target_keypad);
	  								 txt_betkey_9_target_keypad.setClickable(true);
	  								 txt_betkey_9_target_keypad.setOnClickListener(this);
	  								 txt_betkey_9_target_keypad.setText("9");
	  								 txt_betkey_9_target_keypad.setTextColor(Color.WHITE);
	  								 txt_betkey_9_target_keypad.setGravity(Gravity.CENTER);
	  								 txt_betkey_9_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						   /* setting properties of betkey_9 ends here  */

	  						   /* setting properties of betkey_0 starts here  */
	  						    	 txt_betkey_0_target_keypad=(TextView) main_yellow_layout.findViewById(R.id.txt_betkey_0_target_keypad);
	  								 txt_betkey_0_target_keypad.setClickable(true);
	  								 txt_betkey_0_target_keypad.setOnClickListener(this);
	  								 txt_betkey_0_target_keypad.setText("0");
	  								 txt_betkey_0_target_keypad.setTextColor(Color.WHITE);
	  								 txt_betkey_0_target_keypad.setGravity(Gravity.CENTER);
	  								 txt_betkey_0_target_keypad.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						   /* setting properties of betkey_0 ends here  */
	  								 
	  				/* finding textboxes to apply bet on keys ends here  */

	  			
	  			
	  				/* finding textboxes to display applied bet on each betkey in respective betbox starts here  */
	  						          /* setting properties of betbox_1 starts here  */
	  								        txt_1=(TextView) main_yellow_layout.findViewById(R.id.txt_1);
	  								        txt_1.setText("0");
	  								        txt_1.setTextColor(Color.WHITE);
	  								        txt_1.setGravity(Gravity.CENTER);
	  								        txt_1.setTextSize(getResources().getDimension(R.dimen.textsize));
	  						          /* setting properties of betbox_1 ends here  */


	  								  /* setting properties of betbox_2 starts here  */
	  									     txt_2=(TextView) main_yellow_layout.findViewById(R.id.txt_2);
	  									     txt_2.setText("0");
	  									     txt_2.setTextColor(Color.WHITE);
	  									     txt_2.setGravity(Gravity.CENTER);
	  									     txt_2.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								   /* setting properties of betbox_2 ends here  */


	  							     /* setting properties of betbox_3 starts here  */
	  									    txt_3=(TextView) main_yellow_layout.findViewById(R.id.txt_3);
	  									    txt_3.setText("0");
	  									    txt_3.setTextColor(Color.WHITE);
	  									    txt_3.setGravity(Gravity.CENTER);
	  									    txt_3.setTextSize(getResources().getDimension(R.dimen.textsize));
	  							     /* setting properties of betbox_3 ends here  */


	  								  /* setting properties of betbox_4 starts here  */
	  									    txt_4=(TextView) main_yellow_layout.findViewById(R.id.txt_4);
	  									    txt_4.setText("0");
	  									    txt_4.setTextColor(Color.WHITE);
	  									    txt_4.setGravity(Gravity.CENTER);
	  									    txt_4.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								  /* setting properties of betbox_4 ends here  */


	  								  /* setting properties of betbox_5 starts here  */
	  									    txt_5=(TextView) main_yellow_layout.findViewById(R.id.txt_5);
	  									    txt_5.setText("0");
	  									    txt_5.setTextColor(Color.WHITE);
	  									    txt_5.setGravity(Gravity.CENTER);
	  									    txt_5.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								  /* setting properties of betbox_5 ends here  */


	  								  /* setting properties of betbox_6 starts here  */
	  									     txt_6=(TextView) main_yellow_layout.findViewById(R.id.txt_6);
	  									     txt_6.setText("0");
	  									     txt_6.setTextColor(Color.WHITE);
	  									     txt_6.setGravity(Gravity.CENTER);
	  									     txt_6.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								  /* setting properties of betbox_6 ends here  */
	  													

	  								  /* setting properties of betbox_7 starts here  */
	  										 txt_7=(TextView) main_yellow_layout.findViewById(R.id.txt_7);
	  										 txt_7.setText("0");
	  										 txt_7.setTextColor(Color.WHITE);
	  										 txt_7.setGravity(Gravity.CENTER);
	  										 txt_7.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								   /* setting properties of betbox_7 ends here  */

	  										 
	  								   /* setting properties of betbox_8 starts here  */
	  								    	 txt_8=(TextView) main_yellow_layout.findViewById(R.id.txt_8);
	  								    	 txt_8.setText("0");
	  								    	 txt_8.setTextColor(Color.WHITE);
	  								    	 txt_8.setGravity(Gravity.CENTER);
	  								    	 txt_8.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								   /* setting properties of betbox_8 ends here  */

	  								   /* setting properties of betbox_9 starts here  */
	  								    	 txt_9=(TextView) main_yellow_layout.findViewById(R.id.txt_9);
	  								    	 txt_9.setText("0");
	  								    	 txt_9.setTextColor(Color.WHITE);
	  								    	 txt_9.setGravity(Gravity.CENTER);
	  								    	 txt_9.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								   /* setting properties of betbox_9 ends here  */

	  								   /* setting properties of betbox_0 starts here  */
	  								    	 txt_0=(TextView) main_yellow_layout.findViewById(R.id.txt_0);
	  								    	 txt_0.setText("0");
	  								    	 txt_0.setTextColor(Color.WHITE);
	  								    	 txt_0.setGravity(Gravity.CENTER);
	  								    	 txt_0.setTextSize(getResources().getDimension(R.dimen.textsize));
	  								   /* setting properties of betbox_0 ends here  */
	  										 
	   				     /* finding textboxes to display applied bet on each betkey in respective betbox ends here  */

	  			}
	  			
	  			
	  		 public void reset_all_bet_text_boxes_target()
	  	     {
		  			txt_1.setText("0");
		  			txt_2.setText("0");
		  			txt_3.setText("0");
		  			txt_4.setText("0");
		  			txt_5.setText("0");
		  			txt_6.setText("0");
		  			txt_7.setText("0");
		  			txt_8.setText("0");
		  			txt_9.setText("0");
		  			txt_0.setText("0");
	  		    
	  	 }

	  		 
	  		 
	  	    public void calculate_total_bet_target()
	  	     {  	 
	  	    	int arl_length_target=arl_target.size();
	  	    	for(int i=0;i<arl_length_target;i++)
	  	    	{	    		
	  	    		String mm=arl_target.get(i).getString();
	  	            int mm_value=arl_target.get(i).getInt();
	  	            
	  		    	String nn=mm;
	  		    	String[] parts = mm.split("_");
	  		    	String extractedResult = "";
	  		    	for(int j=1;j<parts.length;j++){
	  		    			extractedResult += parts[j] + " "; // prasad_hv is captured here.
	  		    			String bet_key=parts[j];
	  		    			int bet_key_individual_weightage=mm_value;
	  		    			if(bet_key_individual_weightage>0)
	  		    					Prepare_Bets_Array_Target(bet_key,bet_key_individual_weightage);
	  		    	    }
	  	    	}
	  	    	 
	  	    	 
	  	     }
	  	  
	  		   
	  		    public int Prepare_Bets_Array_Target(String bet_key,int bet_key_individual_weightage)
	  		    {
	  		    	 switch(bet_key){
	  		    	 
	  				    	 case "0":
	  				    		 bet_Values_Target[0]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "1":
	  				    		 bet_Values_Target[1]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "2":
	  				    		 bet_Values_Target[2]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "3":
	  				    		 bet_Values_Target[3]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "4":
	  				    		 bet_Values_Target[4]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "5":
	  				    		 bet_Values_Target[5]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "6":
	  				    		 bet_Values_Target[6]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "7":
	  				    		 bet_Values_Target[7]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "8":
	  				    		 bet_Values_Target[8]+=bet_key_individual_weightage;
	  				    		 break;
	  				    	 case "9":
	  				    		 bet_Values_Target[9]+=bet_key_individual_weightage;
	  				    		 break;
	  		    	     }
	  		    	 
	  		    	 
	  		    	 
	  		    	 return 0;
	  		     }
	  		     
	  			 @Override
	  		     public void onClick(View v) {
	  			  //Toast.makeText(getApplicationContext(),v.getId()+"clicked", Toast.LENGTH_LONG).show();
	  				//findViewById(v.getId()).setBackgroundResource(R.drawable.betbg);
	  				
	  				// Release any resources from previous MediaPlayer
	  				   if (mp1 != null) {
	  				      mp1.release();
	  				   }

	  				
	  				   try
	  				   {
	  					   // Create a new MediaPlayer to play this sound
	  					   if (flag_playing_actualdevice==true)
	  					   {
	  						   mp1 = MediaPlayer.create(getApplicationContext(), R.raw.bet);
	  						   mp1.start();
	  						   
	  						   mp1.setOnCompletionListener(new OnCompletionListener() {
	  															@Override
	  															public void onCompletion(MediaPlayer mp) {	mp.release();	}
	  						   								});
	  					   }
	  				    }
	  				   catch(Exception e)
	  				   {
	  					   Toast.makeText(getApplicationContext(), "Media Player can't be tested in emulator as...."+e.getMessage()+"", Toast.LENGTH_SHORT).show();
	  				   }

	  				
	  				
	  				
	  						/* to get name & split it into parts separated by "_" and stored into array of clicked button id starts here */
	  						String name="";
	  						try {
	  							name = getIDName(v, R.id.class);
	  						} catch (Exception e) {
	  									e.printStackTrace();
	  						   }
	  						
	  						
	  						
	  						
	  					
	  						String[] parts = name.split("_");
	  						String extractedResult = "";
	  						for(int i=1;i<parts.length;i++)
	  							   extractedResult += parts[i] + " "; // prasad_hv is captured here.
	  						/* to get name & split it into parts separated by "_" and stored into array of clicked button id ends here */
	  				
	  					
	  			
	  						 Prepare_New_Bet_Target();
	  						
	  						 //int current_score_on_txtscore=Integer.parseInt(txt_score.getText().toString());//balancetochange
	  						 int current_score_on_txtscore_target=GlobalClass.getBalance();//balancetochange
	  						   if(current_score_on_txtscore_target- current_bet_amount_target>0)
	  						   {
	  							   int new_score_target=current_score_on_txtscore_target- current_bet_amount_target;
	  							   txt_score.setText(String.valueOf(new_score_target));
	  							   FunTargetMessages.total_Bet_Amount_On_Current_Game_Target+=current_bet_amount_target;
	  							   txt_bottom_won_amount.setText(String.valueOf(FunTargetMessages.total_Bet_Amount_On_Current_Game_Target));
	  							   
	  							   GlobalClass.setBalance(new_score_target);
	  							   //findViewById(v.getId()).setBackgroundResource(R.drawable.betbg);
	  							   			Update_bet_labels_Target(v);

	  							   			
	  							  			TextView txt_betkey_1_target_keypad,txt_betkey_2_target_keypad,txt_betkey_3_target_keypad,txt_betkey_4_target_keypad,txt_betkey_5_target_keypad,txt_betkey_6_target_keypad,txt_betkey_7_target_keypad,txt_betkey_8_target_keypad,txt_betkey_9_target_keypad,txt_betkey_0_target_keypad;
	  							  			TextView txt_1,txt_2,txt_3,txt_4,txt_5,txt_6,txt_7,txt_8,txt_9,txt_0;

	  							  			if(name.equalsIgnoreCase("txt_betkey_1_target_keypad"))	  							   			
	  										     Update_Bet_Taken_List_Target("txt_1");
	  							  			else if(name.equalsIgnoreCase("txt_betkey_2_target_keypad"))	  							   			
		  										Update_Bet_Taken_List_Target("txt_2");
	  							  		    else if(name.equalsIgnoreCase("txt_betkey_3_target_keypad"))	  							   			
	  								 		    Update_Bet_Taken_List_Target("txt_3");
	  							  		    else if(name.equalsIgnoreCase("txt_betkey_4_target_keypad"))	  							   			
 										        Update_Bet_Taken_List_Target("txt_4");
 							  			    else if(name.equalsIgnoreCase("txt_betkey_5_target_keypad"))	  							   			
	  										    Update_Bet_Taken_List_Target("txt_5");
 							  		        else if(name.equalsIgnoreCase("txt_betkey_6_target_keypad"))	  							   			
 								 		        Update_Bet_Taken_List_Target("txt_6");
 							  		        else if(name.equalsIgnoreCase("txt_betkey_7_target_keypad"))	  							   			
		  										Update_Bet_Taken_List_Target("txt_7");
	  							  		    else if(name.equalsIgnoreCase("txt_betkey_8_target_keypad"))	  							   			
	  								 		    Update_Bet_Taken_List_Target("txt_8");
	  							  		    else if(name.equalsIgnoreCase("txt_betkey_9_target_keypad"))	  							   			
										        Update_Bet_Taken_List_Target("txt_9");
							  			    else if(name.equalsIgnoreCase("txt_betkey_0_target_keypad"))	  							   			
	  										    Update_Bet_Taken_List_Target("txt_0");
	  							  			
	  							  			
	  										
	  					       }
	  						   else
	  						   {
	  								Toast.makeText(getApplicationContext(),"Not enough Balance to BET", Toast.LENGTH_SHORT).show();
	  						   }


	  						
	  						
	  						
	  						
	  				Toast.makeText(getApplicationContext(),extractedResult+" "+name, Toast.LENGTH_SHORT).show();
	  			}

	  			
	  			 public void Update_bet_labels_Target(View v)
	  			 {
	  				 switch(v.getId())
	  				   {
						     case R.id.txt_betkey_1_target_keypad:
		  							if(txt_1.getText()=="0")
		  							{
		  								txt_1.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_1.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_1.setText(""+ latest_button_text_value  );
		  							}
		  							break;
		  							
		  				     case R.id.txt_betkey_2_target_keypad:
		  				    	 if(txt_2.getText()=="0")
		  							{
		  								txt_2.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_2.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_2.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_3_target_keypad:
		  				    	 if(txt_3.getText()=="0")
		  							{
		  								txt_3.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_3.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_3.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_4_target_keypad:
		  				    	 if(txt_4.getText()=="0")
		  							{
		  								txt_4.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_4.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_4.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_5_target_keypad:
		  				    	 if(txt_5.getText()=="0")
		  							{
		  								txt_5.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_5.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_5.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_6_target_keypad:
		  				    	 if(txt_6.getText()=="0")
		  							{
		  								txt_6.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_6.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_6.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_7_target_keypad:
		  				    	 if(txt_7.getText()=="0")
		  							{
		  								txt_7.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_7.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_7.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_8_target_keypad:
		  				    	 if(txt_8.getText()=="0")
		  							{
		  								txt_8.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_8.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_8.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_9_target_keypad:
		  				    	 if(txt_9.getText()=="0")
		  							{
		  								txt_9.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_9.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_9.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
		  				
		  				     case R.id.txt_betkey_0_target_keypad:
		  				    	 if(txt_0.getText()=="0")
		  							{
		  								txt_0.setText(current_bet_amount_target+"");
		  							}
		  							else
		  							{
		  								int latest_button_text_value=Integer.parseInt(txt_0.getText().toString());
		  								latest_button_text_value +=current_bet_amount_target;
		  							    txt_0.setText(""+ latest_button_text_value  );
		  							}
		  				    	   break;
	  				}

	  			 }
	  			 
	  			 
	  			    /*    It's only for internal purpose     */
	  		        void Update_Bet_Taken_List_Target(String keyname) {
	  					int total_Price_Per_Rupee_target;
	  					String[] keyname_without_number=keyname.split("_number");      // to delete _number if attached in given string
	  					
	  				    /* starts converting String to textview by finding on based of its name*/
	  					String resourceName = keyname_without_number[0];
	  				    int resourceID = getResources().getIdentifier(resourceName, "id",getPackageName());
	  				    String current_text_on_bet_textview="";
	  				    if (resourceID != 0) {
	  				        TextView tv = (TextView) findViewById(resourceID);
	  				        if (tv != null) {
	  				            // Take action on TextView tv here...
	  				        	current_text_on_bet_textview=tv.getText().toString();
	  				        }
	  				        else
	  				        {
	  				        	
	  				        }
	  				    }
	  					/* ends converting String to textview by finding on based of its name*/
	  				    		

	  					String[] parts = resourceName.split("_");
	  					String extractedResult = "";
	  					for(int i=1;i<parts.length;i++)
	  						   extractedResult += parts[i] + " "; // prasad_hv is captured here.
	  					
	  					
	  		            // Step 1: How much money / 1 Rupee
	  					     int keys_Last_Visited_count;
	  		 
	  		            	 if(parts[0].equalsIgnoreCase("txt"))
	  		            	 {
	  		            	    keys_Last_Visited_count=parts.length-1;	 
	  		            	 }
	  		            	 else
	  		            	 {
	  		            		 keys_Last_Visited_count=parts.length;
	  		            	 }
	  		            	 total_Price_Per_Rupee_target = 9 /keys_Last_Visited_count ;	 
	  		            	 
	  		            	 
	  		            	// The Coint (Price ) currenlty selected
	  		                 int bet_Price_target =  Integer.parseInt(current_text_on_bet_textview);
	  		                 int total_price_per_key_target = bet_Price_target * total_Price_Per_Rupee_target;

	  		   
	  		            // add this total Amount to each key
	  		                 //make a loop to update the list
	  		                 int StringInt_keys_Bet_Taken_target_length=arl_target.size();
	  		            	for(int i=0;i<StringInt_keys_Bet_Taken_target_length;i++)
	  		            	{
	  		            		if(arl_target.get(i).keybet_name_target.equalsIgnoreCase(resourceName))
	  		            		{	            			
	  		            			arl_target.get(i).setString(resourceName);
	  		            			arl_target.get(i).setInt(total_price_per_key_target);
	  		            		}
	  		            	}
	  		      
	  		            	
	  		        }

	  		        /* internal class to store bet values on keys in ArrayList<string,int> starts here  */
	  		        public class StringInt_keys_Bet_Taken_Target{
	  		        	         private String keybet_name_target;
	  		        	         private int keybet_value_target;

	  		        	public StringInt_keys_Bet_Taken_Target(String o1, int o2){		keybet_name_target = o1;	keybet_value_target = o2;       	}

	  		        	public String getString(){ 	return keybet_name_target;   	}
	  		        	public int getInt(){   	return keybet_value_target;     	}
	  		        	public void setString(String s){   	this.keybet_name_target = s;  	}
	  		        	public void setInt(int i){    	this.keybet_value_target = i;     	}
	  		        	} 
	  			      /* internal class to store bet values on keys in ArrayList<string,int> ends here  */
	  		        
	  		        public void Prepare_New_Bet_Target()
	  		        {
	  		            // stop blinking the last win number
	  		                      //Stop_Hilighting_Win_Number();
	  		            is_selected_bet_completed_Target = true;
	  		            // check if 
	  		            if (Is_It_New_Game_Target() )
	  		            {	  
	  		            	Reset_StringInt_keys_Bet_Taken_Target();
	  		                //Clear_Last_Game_Betting();
	  		                //Clear_Previous_Bet_Details();
	  		               is_New_Game_Target = false;
	  		               FunTargetMessages.total_Bet_Amount_On_Current_Game_Target = 0;
	  		            }

	  		        }

	  		       // public static final ArrayList<StringInt_keys_Bet_Taken> arl=new ArrayList<StringInt_keys_Bet_Taken>();
	  		        
	  		        //String[] keybet_names={"txt_00","txt_00_number","txt_00_0","txt_0","txt_0_number","txt_00_3","txt_3_number","txt_3","txt_3_6","txt_6_number","txt_6","txt_6_9","txt_9_number","txt_9","txt_9_12","txt_12_number","txt_12","txt_12_15","txt_15_number","txt_15","txt_15_18","txt_18_number","txt_18","txt_18_21","txt_21_number","txt_21","txt_21_24","txt_24_number","txt_24","txt_24_27","txt_27_number","txt_27","txt_27_30","txt_30_number","txt_30","txt_30_33","txt_33_number","txt_33","txt_33_36","txt_36_number","txt_36","txt_first2to1","txt_00_3_2","txt_3_2","txt_3_6_2_5","txt_6_5","txt_6_9_5_8","txt_9_8","txt_9_12_8_11","txt_12_11","txt_12_15_11_14","txt_15_14","txt_15_18_14_17","txt_18_17","txt_18_21_17_20","txt_21_20","txt_21_24_20_23","txt_24_23","txt_24_27_23_26","txt_27_26","txt_27_30_26_29","txt_30_29","txt_30_33_29_32","txt_33_32","txt_33_36_32_35","txt_36_35","txt_00_0_2","txt_2_number","txt_2","txt_2_5","txt_5_number","txt_5","txt_5_8","txt_8_number","txt_8","txt_8_11","txt_11_number","txt_11","txt_11_14","txt_14_number","txt_14","txt_14_17","txt_17_number","txt_17","txt_17_20","txt_20_number","txt_20","txt_20_23","txt_23_number","txt_23","txt_23_26","txt_26_number","txt_26","txt_26_29","txt_29_number","txt_29","txt_29_32","txt_32_number","txt_32","txt_32_35","txt_35_number","txt_35","txt_second2to1","txt_0_2_1","txt_2_1","txt_2_5_1_4","txt_5_4","txt_5_8_4_7","txt_8_7","txt_8_11_7_10","txt_11_10","txt_11_14_10_13","txt_14_13","txt_14_17_13_16","txt_17_16","txt_17_20_16_19","txt_20_19","txt_20_23_19_22","txt_23_22","txt_23_26_22_25","txt_26_25","txt_26_29_25_28","txt_29_28","txt_29_32_28_31","txt_32_31","txt_32_35_31_34","txt_35_34","txt_0_1","txt_1_number","txt_1","txt_1_4","txt_4_number","txt_4","txt_4_7","txt_7_number","txt_7","txt_7_10","txt_10_number","txt_10","txt_10_13","txt_13_number","txt_13","txt_13_16","txt_16_number","txt_16","txt_16_19","txt_19_number","txt_19","txt_19_22","txt_22_number","txt_22","txt_22_25","txt_25_number","txt_25","txt_25_28","txt_28_number","txt_28","txt_28_31","txt_31_number","txt_31","txt_31_34","txt_34_number","txt_34","txt_third2to1","txt_00_0_3_2_1","txt_3_2_1","txt_3_2_1_6_5_4","txt_6_5_4","txt_6_5_4_9_8_7","txt_9_8_7","txt_9_8_7_12_11_10","txt_12_11_10","txt_12_11_10_15_14_13","txt_15_14_13","txt_15_14_13_18_17_16","txt_18_17_16","txt_18_17_16_21_20_19","txt_21_20_19","txt_21_20_19_24_23_22","txt_24_23_22","txt_24_23_22_27_26_25","txt_27_26_25","txt_27_26_25_30_29_28","txt_30_29_28","txt_30_29_28_33_32_31","txt_33_32_31","txt_33_32_31_36_35_34","txt_36_35_34","txt_1st12","txt_2nd12","txt_3rd12","txt_1to18","txt_Even","txt_Red","txt_Black","txt_Odd","txt_19to36"};
	  		        String[] keybet_names_target={"txt_0","txt_1","txt_2","txt_3","txt_4","txt_5","txt_6","txt_7","txt_8","txt_9"};
	  		        public void Reset_StringInt_keys_Bet_Taken_Target()
	  		        {    
	  		            for(int i=0;i<keybet_names_target.length;i++)
	  		            {
	  		               arl_target.add(new StringInt_keys_Bet_Taken_Target(keybet_names_target[i],0));
	  		            }
	  		        }
	  		       
	  		        public boolean Is_It_New_Game()
	  		        {
	  		        	boolean status_is_New_Game=is_New_Game_Target;
	  		            return status_is_New_Game;
	  		        }

	  		        
	  		        
	  		        
	  			/* function to retieve the name of a given id of button when clicked starts here */
	  			public String getIDName(View view, Class<?> clazz) throws Exception
	  						{
	  					        Integer id = view.getId();
	  					        Field[] ids = clazz.getFields();
	  					        for (int i = 0; i < ids.length; i++)  {
	  					            Object val = ids[i].get(null);
	  					            if (val != null && val instanceof Integer && ((Integer) val).intValue() == id.intValue())  {
	  					                return ids[i].getName();
	  					            }
	  					        }
	  					     return "";
	  					    }
	  			/* function to retieve the name of a given id of button when clicked ends here */

	  		   
	  		   
	  			
	  			/*
	  			@Override
	  			public void onClick(View v) {
	  				
	  				switch(v.getId())
	  				{
	  				     case R.id.txt_betkey_1_target_keypad:
	  							if(txt_betbox_1_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_1_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_1_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_1_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  							break;
	  							
	  				     case R.id.txt_betkey_2_target_keypad:
	  				    	 if(txt_betbox_2_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_2_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_2_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_2_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_3_target_keypad:
	  				    	 if(txt_betbox_3_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_3_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_3_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_3_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_4_target_keypad:
	  				    	 if(txt_betbox_4_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_4_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_4_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_4_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_5_target_keypad:
	  				    	 if(txt_betbox_5_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_5_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_5_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_5_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_6_target_keypad:
	  				    	 if(txt_betbox_6_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_6_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_6_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_6_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_7_target_keypad:
	  				    	 if(txt_betbox_7_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_7_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_7_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_7_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_8_target_keypad:
	  				    	 if(txt_betbox_8_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_8_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_8_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_8_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_9_target_keypad:
	  				    	 if(txt_betbox_9_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_9_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_9_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_9_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				     case R.id.txt_betkey_0_target_keypad:
	  				    	 if(txt_betbox_0_target_keypad.getText()=="0")
	  							{
	  								txt_betbox_0_target_keypad.setText(current_bet_amount+"");
	  							}
	  							else
	  							{
	  								int latest_button_text_value=Integer.parseInt(txt_betbox_0_target_keypad.getText().toString());
	  								latest_button_text_value +=current_bet_amount;
	  							    txt_betbox_0_target_keypad.setText(""+ latest_button_text_value  );
	  							}
	  				    	   break;
	  				
	  				
	  			}
	  		}
	  		*/
	  		
	  		
	  		}
	  		
	  		
	  		
	  		
	  		class B implements OnClickListener
	  		{
	  			TextView target_txt_1,target_txt_5,target_txt_10,target_txt_50;
	  			TextView target_txt_100,target_txt_500,target_txt_1000,target_txt_5000;
	  			
	  			private void calculate_keypad_bet(LinearLayout target_increment_bet_layout,LinearLayout target_increment_bet2_layout )
	  			{
	  				target_txt_1=(TextView) target_increment_bet_layout.findViewById(R.id.target_txt_1);
	  				target_txt_1.setBackgroundResource(R.drawable.target_onecoin);
	  				target_txt_1.setClickable(true);
	  				target_txt_1.setOnClickListener(this);
	  				
	  				target_txt_5=(TextView) target_increment_bet_layout.findViewById(R.id.target_txt_5);
	  				target_txt_5.setClickable(true);
	  				target_txt_5.setOnClickListener(this);
	  				
	  				target_txt_10=(TextView) target_increment_bet_layout.findViewById(R.id.target_txt_10);
	  				target_txt_10.setClickable(true);
	  				target_txt_10.setOnClickListener(this);
	  				
	  				target_txt_50=(TextView) target_increment_bet_layout.findViewById(R.id.target_txt_50);
	  				target_txt_50.setClickable(true);
	  				target_txt_50.setOnClickListener(this);	
	  				
	  				target_txt_100=(TextView) target_increment_bet2_layout.findViewById(R.id.target_txt_100);
	  				target_txt_100.setClickable(true);
	  				target_txt_100.setOnClickListener(this);
	  				
	  				target_txt_500=(TextView) target_increment_bet2_layout.findViewById(R.id.target_txt_500);
	  				target_txt_500.setClickable(true);
	  				target_txt_500.setOnClickListener(this);
	  				
	  				target_txt_1000=(TextView) target_increment_bet2_layout.findViewById(R.id.target_txt_1000);
	  				target_txt_1000.setClickable(true);
	  				target_txt_1000.setOnClickListener(this);
	  				
	  				target_txt_5000=(TextView) target_increment_bet2_layout.findViewById(R.id.target_txt_5000);
	  				target_txt_5000.setClickable(true);	
	  				target_txt_5000.setOnClickListener(this);
	  				
	  			}

	  			@Override
	  			public void onClick(View v) {
	  				
	  				//int current_bet_amount=0;
	  				switch(v.getId())
	  				{
	  				case R.id.target_txt_1:
	  					current_bet_amount_target=1;	
	  					highlight_increment_bet_keypad(target_txt_1);
	  					break;
	  				case R.id.target_txt_5:
	  					current_bet_amount_target=5;	 
	  					highlight_increment_bet_keypad(target_txt_5);
	  					break;
	  				case R.id.target_txt_10:
	  					current_bet_amount_target=10;	 
	  					highlight_increment_bet_keypad(target_txt_10);
	  					break;
	  				case R.id.target_txt_50:
	  					current_bet_amount_target=50;	 
	  					highlight_increment_bet_keypad(target_txt_50);
	  					break;
	  				case R.id.target_txt_100:
	  					current_bet_amount_target=100;	 
	  					highlight_increment_bet_keypad(target_txt_100);
	  					break;
	  				case R.id.target_txt_500:
	  					current_bet_amount_target=500;	 
	  					highlight_increment_bet_keypad(target_txt_500);
	  					break;
	  				case R.id.target_txt_1000:
	  					current_bet_amount_target=1000;	 
	  					highlight_increment_bet_keypad(target_txt_1000);
	  					break;
	  				case R.id.target_txt_5000:
	  					current_bet_amount_target=5000;	 
	  					highlight_increment_bet_keypad(target_txt_5000);
	  					break;
	  			    }
	  				
	  				Toast.makeText(getApplicationContext(),current_bet_amount_target+"Clicked", Toast.LENGTH_SHORT).show();
	  		}
	  		
	  			private void highlight_increment_bet_keypad(TextView textview_to_highlight)
	  			{
	  				if(textview_to_highlight.getId()==R.id.target_txt_1)
	  				{
	  				textview_to_highlight.setBackgroundResource(R.drawable.target_onecoin);
	  				target_txt_5.setBackgroundResource(0);
	  				target_txt_10.setBackgroundResource(0);
	  				target_txt_50.setBackgroundResource(0);
	  				target_txt_100.setBackgroundResource(0);
	  				target_txt_500.setBackgroundResource(0);
	  				target_txt_1000.setBackgroundResource(0);
	  				target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_5)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_fivecoin);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_50.setBackgroundResource(0);
	  					target_txt_100.setBackgroundResource(0);
	  					target_txt_500.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_10)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_5.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_tencoin);
	  					target_txt_50.setBackgroundResource(0);
	  					target_txt_100.setBackgroundResource(0);
	  					target_txt_500.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_50)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_5.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_fiftycoin);
	  					target_txt_100.setBackgroundResource(0);
	  					target_txt_500.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_5000.setBackgroundResource(0);
	  				}

	  				else if(textview_to_highlight.getId()==R.id.target_txt_100)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_5.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_50.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_hundredcoin);
	  					target_txt_500.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_500)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_5.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_50.setBackgroundResource(0);
	  					target_txt_100.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_fivehundredcoin);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_1000)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_5.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_50.setBackgroundResource(0);
	  					target_txt_100.setBackgroundResource(0);
	  					target_txt_500.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_thousandcoin);
	  					target_txt_5000.setBackgroundResource(0);
	  				}
	  				else if(textview_to_highlight.getId()==R.id.target_txt_5000)
	  				{
	  					target_txt_1.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_10.setBackgroundResource(0);
	  					target_txt_50.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					target_txt_500.setBackgroundResource(0);
	  					target_txt_1000.setBackgroundResource(0);
	  					textview_to_highlight.setBackgroundResource(R.drawable.target_fivethousandcoin);
	  				}

	  				
	  				// Release any resources from previous MediaPlayer
	 			   if (mp1 != null) {
	 			      mp1.release();
	 			   }
	 	
	 			
	 			   try
	 			   {
	 				   // Create a new MediaPlayer to play this sound
	 				   if (flag_playing_actualdevice==true)
	 				   {
	 					   mp1 = MediaPlayer.create(getApplicationContext(), R.raw.button);
	 					   mp1.start();
	 					   
	 					   mp1.setOnCompletionListener(new OnCompletionListener() {
	 														@Override
	 														public void onCompletion(MediaPlayer mp1) {	mp1.release();	}
	 					   								});
	 				   }
	 			    }
	 			   catch(Exception e)
	 			   {
	 				   Toast.makeText(getApplicationContext(), "Media Player can't be tested in emulator as...."+e.getMessage()+"", Toast.LENGTH_SHORT).show();

	 			   }

	  				
	  			}
	  		
	  		}
	  		
	  		
	  		
	  		
	  		@Override
	  		protected void onPause() {
	  			super.onPause();
	  			
	  		   if (mp1 != null) {
	  		      mp1.release();
	  		     }
	
	  			
	  			 if (timer_time_Target != null) {
	  	    		 timer_time_Target.cancel();
	  	    		 timer_time_Target = null;
	  	             CurrentTimer_Target.timer_thread_count--;
	  	         }
	  	    	 
	  	    	 if (mp1 != null) {
	  			      mp1.release();
	  			   }
	  	    	 
	  	    	 if((CurrentTimer_Target.application_interrupted==false) && ( CurrentTimer_Target.application_interrupted_count==0))
	  	    	 {
	  	    		 CurrentTimer_Target.application_interrupted_count++;
	  		    	 CurrentTimer_Target.application_interrupted=true;
	  		    	 Calendar startDateTime = Calendar.getInstance();
	  		    	 CurrentTimer_Target.date1_onpause= startDateTime.getTimeInMillis();
	  	    	
	  	    	 }

	  	    	 
				 
	  	    	boolean mWorkerThread_status_target=mWorkerThread_slider_funtarget.isAlive();
	  			if(mWorkerThread_status_target==true)
	  	        {
	  				try{
	  			        myhandler_slider_funtarget.removeCallbacks(myupdateresults_slider_funtarget)	;	
	  					mWorkerThread_slider_funtarget.quit();
	  				}catch(Exception e){
	  					
	  				}
	  	        }			
		
	  	    A obj=new A();	 
            obj.calculate_total_bet_target();
            
            int aa[]=bet_Values_Target;
            int bb[]=aa;
	  		}


	  		@Override
	  		protected void onStart() {
	  			super.onStart();
	  			
	  		  try
		        {
				  if(return_status_async_GAMEDETAILS_Target==0)
					  Get_FunTarget_GameDetails();
		        }
		        catch(Exception e)
		        {
		        	
		        }

	  		  
	  		  boolean mWorkerThread_status_target=mWorkerThread_slider_funtarget.isAlive();
				if(mWorkerThread_status_target==false)
		        {
		              mWorkerThread_slider_funtarget.start();
		              mWorkerThread_slider_funtarget.onLooperPrepared();
		        }

	  		}
	  		
	  	
	  		
	  		
	  		@Override
	  		protected void onStop() {
	  			super.onStop();
	  			

	  			
	  			boolean mWorkerThread_status_target=mWorkerThread_slider_funtarget.isAlive();
	  			if(mWorkerThread_status_target==true)
	  	        {
	  				try{
	  			        myhandler_slider_funtarget.removeCallbacks(myupdateresults_slider_funtarget)	;	
	  					mWorkerThread_slider_funtarget.quit();
	  				}catch(Exception e){
	  					
	  				}
	  	        }			

	  			
	  		}
	  		
	  		private void Get_FunTarget_GameDetails()
	  	    {        
	  	    	/* To generate transfer datagrid starts here */
	  			runOnUiThread(new Runnable() {
	  				
	  				@Override
	  				public void run() {
	  					new Get_FunTarget_GameDetails_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);	
	  					//new Get_Transfer_Datagrid_Class().execute();	
	  				}
	  			});
	  		       
	  	    }

	  		
	  		
	  		@Override
	  		protected void onResume() {
	  			super.onResume();
	  			
	  			LinearLayout fun_target_mainlayout=(LinearLayout) findViewById(R.id.fun_target_mainlayout);//TARGET main layout
	  			
	  			
	  			try {
		        	while(true)
		        	{	
				        	if(return_status_async_GAMEDETAILS_Target==0)
				        	{
				        	}
				        	else if(return_status_async_GAMEDETAILS_Target==1)
				        	{
					        	break;
				        	}
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}

			   
	 		   
		    	//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
	  		   int Time_Seconds_Target=Service_Client_Interaction_Target.Game_Details_Funtarget.Time_Seconds_Target;
	 		   int[] Recent_Values_10_Target=Service_Client_Interaction_Target.Game_Details_Funtarget.Recent_Values_10_Target;
	 		   int current_Game_Result_OR_Next_Game_Prepare_Target=Service_Client_Interaction_Target.Game_Details_Funtarget.current_Game_Result_OR_Next_Game_Prepare_Target;
	 		   int game_Id_Target=Service_Client_Interaction_Target.Game_Details_Funtarget.game_Id_Target;
	 		   CurrentTimer_Target.counter=Time_Seconds_Target;

	  			
	  		    initialize_target_screen(fun_target_mainlayout,screenWidthDp,screenHeightDp);
	  	    
	  			/*code for recent list starts here*/
			       Start_target(Time_Seconds_Target, Recent_Values_10_Target, current_Game_Result_OR_Next_Game_Prepare_Target);
			    /*code for recent list ends here*/
	  		    
	  		    
	  	/*code to handle timer while phone call comes starts here*/
		        if((CurrentTimer_Target.application_interrupted==true)&&(CurrentTimer_Target.application_interrupted_count==1) )
		        {
			        CurrentTimer_Target.application_interrupted=false;
			        Calendar startDateTime = Calendar.getInstance();
			        
			        CurrentTimer_Target.date2_onresume= startDateTime.getTimeInMillis();
			        
				   	 long total_time_application_on_wait=CurrentTimer_Target.date2_onresume-CurrentTimer_Target.date1_onpause;
				   	 int hours = (int)total_time_application_on_wait / (60 * 60 * 1000);
			         int minutes = (int) (total_time_application_on_wait / (60 * 1000)); minutes = minutes - 60 * hours;
			         long seconds = total_time_application_on_wait / (1000);
			         long seconds_to_add_in_timer=seconds%60;
			   	 
			   	
				    CurrentTimer_Target.counter-=seconds_to_add_in_timer;
				   	CurrentTimer_Target.counter=CurrentTimer_Target.counter%60;
			   	    CurrentTimer_Target.application_interrupted_count--;
		        }

		        //onResume we start our timer so it can start when the app comes from the background
			    if(CurrentTimer_Target.timer_thread_count==0)     {      startTimer_target();       }

			 /*code to handle timer while phone call comes ends here*/

	  		    
	  			
	  		}
	  		
	  		
	  	  public void startTimer_target() {
	      	
	 	   	 if (timer_time_Target!=null){
	 	   		timer_time_Target.cancel();
	 	   		timer_time_Target = null;
	 	   	     CurrentTimer_Target.timer_thread_count--;
	 	   	    }
	   	 
	       //set a new Timer
	 	   	{
	 	   		timer_time_Target = new Timer();
	 	       CurrentTimer_Target.timer_thread_count++;
	 	   	}        
	 	   	//initialize the TimerTask's job
	       initializeTimerTaskTarget();
	      
	       timer_time_Target.schedule(timerTask_timer_time_Target, 1000, 1000); 
	   }
	      
	  	  public void initializeTimerTaskTarget() {
	  	       if(CurrentTimer_Target.timer_thread_count==1)
	  	       {
	  	  		timerTask_timer_time_Target = new TimerTask() {
	  	  			            @Override
	  	          				public void run() {            					           					
	  	          					  	final String secs=Integer.toString(CurrentTimer_Target.counter);
	  	          					  	final String mins="0";
	  	          						//use a handler to run a toast that shows the current timestamp
	  	          					  	runOnUiThread(new Runnable() {
	  	          												@Override
	  	          												public void run() {
	  	          													 if(CurrentTimer_Target.counter==60)
	  	          													 {
	  	          											    	          if(secs.equalsIgnoreCase("60"))
	  	          											    	          {
	  	          											    	        	 final String strDate1="01:00";
	  	          											    	        	 txt_time.setText(strDate1);
	  	          											    	          }
	  	          											    	          
	  	          											    	          CurrentTimer_Target.counter--;
	  	          													   }
	  	          												      else if(CurrentTimer_Target.counter==59)
	  	     													      {
	  		      														   final String strDate1=mins+":"+secs;
	  		      														   txt_time.setText(strDate1);
	  		      														  // new AttemptUpdate().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	  		      														   
	  		      														   CurrentTimer_Target.counter--;
	  	     													     }
	  	         												     else if(CurrentTimer_Target.counter>=0 && CurrentTimer_Target.counter<=58)
	  	          												     {
	  	          														   final String strDate1=mins+" : "+secs;
	  	          														   txt_time.setText(strDate1);
	  	          														   CurrentTimer_Target.counter--;
	  	          												     }
	  	          													 
	  	          													 if(CurrentTimer_Target.counter<0)
	  	          													 {
	  	          														   CurrentTimer_Target.counter=59;
	  	          														   //show the toast
	  	         									                          int duration = Toast.LENGTH_SHORT;  
	  	         									                          Toast toast = Toast.makeText(getApplicationContext(), "rescheduling timer target", duration);
	  	         									                          toast.show();
	  	          													 }
	  	          													
	  	          													// Release any resources from previous MediaPlayer
	  	          													 if (mp1 != null)
	  	          													 {
	  	          													      mp1.release();
	  	          													 }
	  	          													   
	  	          													 // Create a new MediaPlayer to play this sound
	  	       													 try
	  	       													 {
	  	           													   // Create a new MediaPlayer to play this sound
	  	           													   if (flag_playing_actualdevice==true)
	  	           													   {

	  			           													   mp1 = MediaPlayer.create(getApplicationContext(), R.raw.clock);
	  			           													   mp1.start();
	  			           													   
	  			           													   mp1.setOnCompletionListener(new OnCompletionListener() {
	  																												@Override
	  																												public void onCompletion(MediaPlayer mp) {	mp.release();	}
	  			           													   								});
	  	           													   }
	  	       													 }
	  	       													 catch(Exception e)
	  	       													 {
	  	       														   Toast.makeText(getApplicationContext(), "Media Player can't be tested in emulator as...."+e.getMessage()+"", Toast.LENGTH_SHORT).show();
	  		           												        //stop the timer, if it's not already null
	  		           												        if (timer_time_Target != null) {
	  		           												        	timer_time_Target.cancel();
	  		           												        	timer_time_Target = null;
	  		           												            CurrentTimer_Target.timer_thread_count--;
	  		           												        }
	  	       													 }									                        
	  	              											  }
	  	              											});
	  	          								}
	  	      							};
	  	  	    }
	  	       else if(CurrentTimer_Target.timer_thread_count>1)
	  	       {
	  	      	 Toast toast = Toast.makeText(getApplicationContext(), "more than one thread of timertask", Toast.LENGTH_SHORT);
	  	           toast.show();
	  	       }
	  	       else if(CurrentTimer_Target.timer_thread_count<1)
	  	       {
	  	      	 Toast toast = Toast.makeText(getApplicationContext(), "less than one thread of timertask", Toast.LENGTH_SHORT);
	  	           toast.show();
	  	       }
	  	       }
	  	   
	  	    public void stoptimertasktarget(View v) {
	  	       //stop the timer, if it's not already null
	  	       if (timer_time_Target != null) {
	  	       	timer_time_Target.cancel();
	  	       	timer_time_Target = null;
	  	           CurrentTimer_Target.timer_thread_count--;
	  	       }
	  	       
	  	       if (mp1 != null) {
	  			      mp1.release();
	  			   }
	  	   }
	  	    
	  	    
	  	    
	  
	  	    
	  	    
	  	    
	  	  /*code for recent list starts here*/
			public void Start_target(int time_Seconds, int[] recent_List, int current_Game_Result_OR_Next_Game_Prepare)
	        {
	            Set_Up_target(time_Seconds, recent_List, current_Game_Result_OR_Next_Game_Prepare);
	        }  	
			
			
			public void Set_Up_target(int time_Seconds, int[] recent_List, int current_Game_Result_OR_Next_Game_Prepare)
	        {
	            Set_Recent_List_Target(recent_List);
	            //objFunTargetMessages.Start_Game_Timer_Target(time_Seconds, current_Game_Result_OR_Next_Game_Prepare);            
	        }
			
			
			List<Integer> recent_Won_List_Target = new ArrayList<Integer>();
		
			public void Set_Recent_List_Target(int[] recent_List)
		    {
		            if (recent_List != null)
		            {
		                recent_Won_List_Target.clear();

		                for(int i=0; i<recent_List.length;i++)
		                {
		                    recent_Won_List_Target.add(recent_List[i]);
		                }

		                Update_Recent_List_Target();
		            }
		            //double Acount_Balance = globalclass.getBalance();
		            //txt_score.setText(""+Acount_Balance);
		            Update_Balance_Label_Target();
		    }
			
			public void Update_Recent_List_Target()
	        {       
	            String list = " ";
        
	            int index=0;
	            
	            for(int j=0;j<recent_Won_List_Target.size();j++)
	            {
	            	int number=recent_Won_List_Target.get(j);
	            
	                	//Resources res=getResources();
	                    list = number + "  ";
	                    /*txt_last_10_data[index].setTextColor(res.getColor(R.color.Yellow));*/
	                
	                txt_last_10_data[index].setTextColor(Color.BLACK);
	                txt_last_10_data[index].setText(list);
		            ++index;
	            }

	                         
	            
	            
	            //String M=list;
	           // label_Recent_list_4.Content = list;
	        }

    /*code for recent list ends here*/


			
			// this is to display 3 variety of messages only
	        void Update_Hint_Msg_Target(String str_Msg,int colorname)
	        {
	            if (str_Msg == "You have lost. Game Over")
	            {
	            	play_music(R.raw.loose);
	            }
	            	                
             txt_bottom_bet_status.setText(str_Msg);
             txt_bottom_bet_status.setTextColor(colorname);	            
	        }
	        
			
	        public void play_music(int mp3_file_id)
	        {
	        	if (mp1 != null) {
    	 			mp1.release();
    	 		}

		          try
		          {
       			   // Create a new MediaPlayer to play this sound
       			   if (flag_playing_actualdevice==true)
       			   {
       				   mp1 = MediaPlayer.create(getApplicationContext(), mp3_file_id);
       				   mp1.start();
       				   
       				   mp1.setOnCompletionListener(new OnCompletionListener() {
       													@Override
       													public void onCompletion(MediaPlayer mp) {	mp.release();	}
       				   								});
       			   }
       		    }
       		   catch(Exception e)
       		   {
       			   Toast.makeText(getApplicationContext(), "Media Player can't be tested in emulator as...."+e.getMessage()+"", Toast.LENGTH_SHORT).show();
       		   }

	        }
	        
	
	        
	        
			
			public int[] get_Bet_Values_Array_Target()
		    {
		   	 
		   	 A objA=new A();
		   	 objA.calculate_total_bet_target();
		   	 
		   	 return bet_Values_Target;
		    }

			
			
			
			/* code for start game timer starts here*/
			public void Update_Balance_Label_Target()
	        {
				double Acount_Balance = GlobalClass.getBalance();
	            txt_score.setText(""+Acount_Balance);
	        }
	
	
			
			public void Bet_Time_Over_Target( boolean is_bet_time_over){
	            Is_Bet_Taime_Over_Target = is_bet_time_over;
	        }
			/* code for start game timer ends here*/
			
			
			public boolean Is_It_New_Game_Target()
	        {
	            return is_New_Game_Target;
	        }
	  		
	}
 