package com.fungameavs1;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.fungameavs1.FunRouletteActivity.ABC.StringInt_keys_Bet_Taken;
import com.fungameavs1.FunRouletteMessages.Button_Group;
import com.fungameavs1.Result_Request_Worker_Thread.Request_Type;
import com.fungameavs1.Service_Client_Interaction_Target.Next_Game_ID_Time_FunTarget;
   
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

//ZOOM kEYPAD STARTS HERE

//ZOOM kEYPAD ENDS HERE

class MyWorkerThread_BetOk extends HandlerThread {

    private Handler mWorkerHandler;

    public MyWorkerThread_BetOk(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task){
        mWorkerHandler.post(task);
    }
}




class MyWorkerThread_TakeBet extends HandlerThread {

    private Handler mWorkerHandler;

    public MyWorkerThread_TakeBet(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task){
        mWorkerHandler.post(task);
    }
}



class MyWorkerThread_Time extends HandlerThread {

    private Handler mWorkerHandler;

    public MyWorkerThread_Time(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task){
        mWorkerHandler.post(task);
    }
}




class MyWorkerThread_SecondsTimer extends HandlerThread {

    private Handler mWorkerHandler;

    public MyWorkerThread_SecondsTimer(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler = new Handler(getLooper());
    }

    public void postTask(Runnable task){
        mWorkerHandler.post(task);
    }
}



public class FunRouletteActivity extends Activity implements AnimationListener{

	static ViewGroup container=null;
	Number_Wheel number_Wheel=null;
	static RelativeLayout overlay_two_imageviews;
	static ImageView wheelImageView,wheel,ball;
    static LinearLayout main_yellow_layout,mainlinearlayout;
    LinearLayout main_thirdrowlayout;
	public static Result_Request_Worker_Thread result_Request_Thred = null;

	int last_Result = -1;
    
	
	//ZOOM kEYPAD STARTS HERE
	// bet_Values[0]->"0",bet_Values[37]->"00"	
	public static final int[] bet_Values = new int[]{ 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 };
	public static final ArrayList<StringInt_keys_Bet_Taken> arl=new ArrayList<StringInt_keys_Bet_Taken>();

    int[] red_Ids = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    int[] black_Ids = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };
    int stop_Ball_At = -1;
	public static boolean Is_first2to1_Checked=false,Is_second2to1_Checked=false,Is_third2to1_Checked=false,Is_1st12_Checked=false,Is_2nd12_Checked=false,Is_3rd12_Checked=false,Is_1to18_Checked=false,Is_Even_Checked=false,Is_Red_Checked=false,Is_Black_Checked=false,Is_Odd_Checked=false,Is_19to36_Checked=false;

	WheelView wheel_v;	
	//ZOOM kEYPAD ENDS HERE

	boolean is_Bet_Accpeted = false; // on winning game whether user has clicked on Take button or not
	/*start here*/
    public boolean Is_Bet_Taime_Over = false;
    public boolean is_New_Game = false;
    public boolean is_selected_bet_completed = false;
    
	public static class CurrentTimer
	{
		static int counter=60;
		static boolean application_interrupted=false;
		static int timer_thread_count=0, application_interrupted_count=0;
		static long date1_onpause, date2_onresume,total_time_application_on_wait;
	}
	
	Timer timer_time;
    TimerTask timerTask;
    
    static int return_status_async_GAMEDETAILS=0;
  //private AudioManager audioManager;
    private MediaPlayer mp,mp1;
    
    //we are going to use a handler to be able to run in our TimerTask
    Timer timer_slider_funroulette=null;
    Handler myhandler_slider_funroulette=new Handler();
    private MyWorkerThread mWorkerThread_slider_funroulette;
    Runnable myupdateresults_slider_funroulette;

    Timer timer_Bet_OK_Blink=null;
    Handler myhandler_Timer_Bet_OK=new Handler();
    private MyWorkerThread_BetOk mWorkerThread_timer_Bet_OK_Blink;
    Runnable myupdateresults_timer_Bet_OK_Blink;

    
        
    Timer timer_Take_Bet_Blink=null;
    Handler myhandler_Timer_Take_Bet=new Handler();
    private MyWorkerThread_TakeBet mWorkerThread_timer_Take_Bet_Blink;
    Runnable myupdateresults_timer_Take_Bet_Blink;

    
    Timer timer_Timelayout_Blink=null;
    Handler myhandler_Timer_TimeLayout=new Handler();
    public MyWorkerThread_Time mWorkerThread_timer_timelayout_Blink;
    Runnable myupdateresults_timer_Timelayout_Blink;


    Timer timer_secondstimer_funroulette=null;
    Handler myhandler_secondstimer_funroulette=new Handler();
    private MyWorkerThread_SecondsTimer mWorkerThread_secondstimer_funroulette;
    Runnable myupdateresults_secondstimer_funroulette;

   
    
    private boolean flag_playing_actualdevice=true;
/*end here*/



int columntwowidth;
int columntwoheight;
int screenWidthDp,screenHeightDp;
int third_row_total_height,third_row_height,third_row_width;

static int current_bet_amount=1;
public int currentimageindex=0;
private int[] Image_ids={R.drawable.title0,R.drawable.title1};


public int currentimageindex_bet_Ok=0;
private int[] Image_ids_bet_Ok={R.drawable.betok,R.drawable.betokglow};

public int currentimageindex_Take_Bet=0;
private int[] Image_ids_take_bet={R.drawable.take,R.drawable.takeglow};

public int currentimageindex_timelayout=0;
//private int[] Image_ids_time={R.drawable.betok,R.drawable.betokglow};

static LinearLayout txt_time_layout;
static ImageView tileimageview,btn_exit,btn_bet_OK,btn_take;
LinearLayout txt_score_layout,increment_bet_keypad_layout,txt_winner_layout,txt_last_5_data_layout,btn_betOK_take_layout,btn_take_layout,btn_betOK_layout;
static LinearLayout bet_keypad_layout,btn_exit_layout,txt_bottom_won_amount_layout,txt_bottom_bet_status_layout;
static TextView txt_score,txt_time,txt_winner,txt_bottom_won_amount,txt_bottom_bet_status;
static TextView[] txt_last_5_data=new TextView[5];
static FunRouletteMessages objFunRouletteMessages=null;
	
	
	
	
	public FunRouletteActivity() {
		
	}

	
    public void Bet_Ok_Take_EVent_Handler()
    {
        if (CurrentTimer.counter > 45 && FunRouletteMessages.enum_Bet_OK_Button_Group != (int)Button_Group.BET_TAKE.button_group_value)
            return;

        //Stop_Bet_Ok_Take_Buttons_Group();

        
    	String current_activity_name=this.getClass().getSimpleName();
    	
        if (objFunRouletteMessages.Is_Bet_OK_Blinking())
        {
            // first stop blinking & detach all event handlers and bet ok button working
            if (current_activity_name.equalsIgnoreCase("FunRouletteActivity"))
            {	
                objFunRouletteMessages.Set_Amount_Won(0);
                play_music(R.raw.bet);
            }                
                            
            Send_Bet_Values(false);
            FunRouletteMessages.enum_Bet_OK_Button_Group = -1; // stop hilightin all bet buttons
        }
            //prev button working
        else if (objFunRouletteMessages.Is_Bet_Previous_Blinking())
        {

        	if (current_activity_name.equalsIgnoreCase("FunRouletteActivity"))
            {
                objFunRouletteMessages.Set_Amount_Won(0);
                // update the amount to according to new value
               // Stop_Hilighting_Win_Number();
                play_music(R.raw.bet);
            }
            is_selected_bet_completed = false;
            Send_Bet_Values(true);
            FunRouletteMessages.enum_Bet_OK_Button_Group = -1; // stop hilightin all bet buttons
        }
            //working of take button after win
        else if (FunRouletteMessages.enum_Bet_OK_Button_Group == (int)Button_Group.BET_TAKE.button_group_value)
        {               
            is_Bet_Accpeted = true;
            FunRouletteMessages.is_Bet_Taken = false;
            Bet_Time_Over(false);
            ABC obj=new ABC();
            obj.reset_all_bet_text_boxes();
            //number_Pad.Clear_Last_Game_Betting_Labels_Only();
        	if (current_activity_name.equalsIgnoreCase("FunRouletteActivity"))
            {
                if(objFunRouletteMessages.is_winning_amount_take == false)
                	GlobalClass.setBalance(GlobalClass.getBalance()+FunRouletteMessages.amount_Won);
                    
                objFunRouletteMessages.is_winning_amount_take = false;
                
                Update_Balance_On_Result_Win();
                play_music(R.raw.take);
            }
            if (CurrentTimer.counter > 10)
                Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_PREVIOUS_OK);
            else
            {
                Bet_Time_Over(true);
                FunRouletteMessages.enum_Bet_OK_Button_Group = -1;
                
            }
          //  Stop_Bet_Ok_Take_Buttons_Group();
            
        }
        
    }
	
    private void Update_Balance_On_Result_Win()
    {
       
        //result_Request_Thred.Start(Request_Type.UPDATE_USER_ACCOUNT);
        Update_Balance_Label();
       FunRouletteMessages.amount_Won = 0;
        Update_Amount_Won();
                   
    }

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* for full screen (requires uses-permission "android.permission.ACCESS_NETWORK_STATE"  starts*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		/* for full screen (requires uses-permission "android.permission.ACCESS_NETWORK_STATE"  ends*/
 
		setContentView(R.layout.activity_funroulette);
	
		return_status_async_GAMEDETAILS=0;
		
		

		objFunRouletteMessages=new FunRouletteMessages();
		result_Request_Thred=new Result_Request_Worker_Thread(objFunRouletteMessages);
		
		
		
		
  		
 		/* to get screen width and height of real device starts  */
 		Point size=new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidthDp=size.x;
        screenHeightDp=size.y;
        /* to get screen width and height of real device ends  */

       
       // new Get_FunRoulette_GameDetails_Class().execute();
	    

        
        /* code to place the slider for FunRoulette starts here */
        mWorkerThread_slider_funroulette = new MyWorkerThread("myWorkerThread_sliderfunroulette");
        myupdateresults_slider_funroulette = new Runnable() {
            @Override
            public void run()
            {
                        myhandler_slider_funroulette.post(new Runnable() {
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
        timer_slider_funroulette=new Timer();
        timer_slider_funroulette.scheduleAtFixedRate(new TimerTask() {
			        				    @Override
			        				    public void run()
			        				    {
			        				    	 mWorkerThread_slider_funroulette.postTask(myupdateresults_slider_funroulette);
			        				    }
                                  },delay,period);
	    /* code to place the slider for FunRoulette ends here */
	        
    

        
	}

	
	
    public class ABC implements OnClickListener{

    	
    	
    	
	TextView txt_00,txt_00_0,txt_0,txt_00_3,txt_3,txt_3_6,txt_6,txt_6_9,txt_9,txt_9_12,txt_12,txt_12_15,txt_15,txt_15_18,txt_18,txt_18_21,txt_21,txt_21_24,
	txt_24,txt_24_27,txt_27,txt_27_30,txt_30,txt_30_33,txt_33,txt_33_36,txt_36,txt_first2to1;

	TextView txt_00_3_2,txt_3_2,txt_3_6_2_5,txt_6_5,txt_6_9_5_8,txt_9_8,txt_9_12_8_11,txt_12_11,txt_12_15_11_14,txt_15_14,txt_15_18_14_17,
	txt_18_17,txt_18_21_17_20,txt_21_20,txt_21_24_20_23,txt_24_23,txt_24_27_23_26,txt_27_26,txt_27_30_26_29,txt_30_29,txt_30_33_29_32,
	txt_33_32,txt_33_36_32_35,txt_36_35;
	
	TextView txt_00_0_2,txt_2,txt_2_5,txt_5,txt_5_8,txt_8,txt_8_11,txt_11,txt_11_14,txt_14,txt_14_17,txt_17,txt_17_20,txt_20,txt_20_23,txt_23,txt_23_26,
	txt_26,txt_26_29,txt_29,txt_29_32,txt_32,txt_32_35,txt_35,txt_second2to1;

	TextView txt_0_2_1,txt_2_1,txt_2_5_1_4,txt_5_4,txt_5_8_4_7,txt_8_7,txt_8_11_7_10,txt_11_10,txt_11_14_10_13,txt_14_13,
	txt_14_17_13_16,txt_17_16,txt_17_20_16_19,txt_20_19,txt_20_23_19_22,txt_23_22,txt_23_26_22_25,txt_26_25,txt_26_29_25_28,
    txt_29_28,txt_29_32_28_31,txt_32_31,txt_32_35_31_34,txt_35_34;

	TextView txt_0_1,txt_1,txt_1_4,txt_4,txt_4_7,txt_7,txt_7_10,txt_10,txt_10_13,txt_13,txt_13_16,txt_16,txt_16_19,txt_19,txt_19_22,txt_22,txt_22_25,txt_25,txt_25_28,txt_28,txt_28_31,txt_31,txt_31_34,txt_34,txt_third2to1;

	TextView txt_3_2_1,txt_3_2_1_6_5_4,txt_6_5_4,txt_6_5_4_9_8_7,txt_9_8_7,txt_9_8_7_12_11_10,txt_12_11_10,
	txt_12_11_10_15_14_13,txt_15_14_13,txt_15_14_13_18_17_16,txt_18_17_16,txt_18_17_16_21_20_19,txt_21_20_19,txt_21_20_19_24_23_22,
	txt_24_23_22,txt_24_23_22_27_26_25,txt_27_26_25,txt_27_26_25_30_29_28,txt_30_29_28,txt_30_29_28_33_32_31,txt_33_32_31,
    txt_33_32_31_36_35_34,txt_36_35_34;
										
	TextView txt_1st12,txt_2nd12,txt_3rd12,txt_1to18,txt_Even,txt_Red,txt_Black,txt_Odd,txt_19to36;

     public void calculate_keypad_bet(LinearLayout main_yellow_layout)
     {
    	 is_New_Game=true;
	  /* texboxes i,ii,iii,....xxiii,xxiv starts here  */
	
			txt_00=(TextView) main_yellow_layout.findViewById(R.id.txt_00);
			txt_00.setClickable(true);
			txt_00.setOnClickListener(this);
			txt_00.setText("00");
			txt_00.setTextColor(Color.TRANSPARENT);
			txt_00.setGravity(Gravity.CENTER);
			txt_00.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00.setBackgroundResource(0);
            
			
			
			
			
			txt_00_0=(TextView) main_yellow_layout.findViewById(R.id.txt_00_0);
			txt_00_0.setClickable(true);
			txt_00_0.setOnClickListener(this);
			txt_00_0.setText("0");
			txt_00_0.setTextColor(Color.BLACK);
			txt_00_0.setGravity(Gravity.CENTER);
			txt_00_0.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00_0.setBackgroundResource(0);
		
			
			txt_0=(TextView) main_yellow_layout.findViewById(R.id.txt_0);
			txt_0.setClickable(true);
			txt_0.setOnClickListener(this);
			txt_0.setText("0");
			txt_0.setTextColor(Color.TRANSPARENT);
			txt_0.setGravity(Gravity.CENTER);
			txt_0.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_0.setBackgroundResource(0);
			
							
			txt_00_3=(TextView) main_yellow_layout.findViewById(R.id.txt_00_3);
			txt_00_3.setClickable(true);
			txt_00_3.setOnClickListener(this);
			txt_00_3.setText("0");
			txt_00_3.setTextColor(Color.BLACK);
            txt_00_3.setGravity(Gravity.CENTER);
			txt_00_3.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00_3.setBackgroundResource(0);
			
			
			
			
			txt_3=(TextView) main_yellow_layout.findViewById(R.id.txt_3);
			txt_3.setClickable(true);
			txt_3.setOnClickListener(this);
			txt_3.setText("0");
			txt_3.setTextColor(Color.TRANSPARENT);
			txt_3.setGravity(Gravity.CENTER);
			txt_3.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3.setBackgroundResource(0);
			
			
			txt_3_6=(TextView) main_yellow_layout.findViewById(R.id.txt_3_6);
			txt_3_6.setClickable(true);
			txt_3_6.setOnClickListener(this);
			txt_3_6.setText("0");
			txt_3_6.setTextColor(Color.BLACK);
			txt_3_6.setGravity(Gravity.CENTER);
			txt_3_6.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3_6.setBackgroundResource(0);
			
			
			
			txt_6=(TextView) main_yellow_layout.findViewById(R.id.txt_6);
			txt_6.setClickable(true);
			txt_6.setOnClickListener(this);
			txt_6.setText("0");
			txt_6.setTextColor(Color.TRANSPARENT);
			txt_6.setGravity(Gravity.CENTER);
			txt_6.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6.setBackgroundResource(0);
			
			txt_6_9=(TextView) main_yellow_layout.findViewById(R.id.txt_6_9);
			txt_6_9.setClickable(true);
			txt_6_9.setOnClickListener(this);
			txt_6_9.setText("0");
			txt_6_9.setTextColor(Color.BLACK);
			txt_6_9.setGravity(Gravity.CENTER);
			txt_6_9.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6_9.setBackgroundResource(0);
			
			
			
			txt_9=(TextView) main_yellow_layout.findViewById(R.id.txt_9);
			txt_9.setClickable(true);
			txt_9.setOnClickListener(this);
			txt_9.setText("0");
			txt_9.setTextColor(Color.TRANSPARENT);
			txt_9.setGravity(Gravity.CENTER);
			txt_9.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9.setBackgroundResource(0);
			
			txt_9_12=(TextView) main_yellow_layout.findViewById(R.id.txt_9_12);
			txt_9_12.setClickable(true);
			txt_9_12.setOnClickListener(this);
			txt_9_12.setText("0");
			txt_9_12.setTextColor(Color.BLACK);
			txt_9_12.setGravity(Gravity.CENTER);
			txt_9_12.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9_12.setBackgroundResource(0);
			
			
			txt_12=(TextView) main_yellow_layout.findViewById(R.id.txt_12);
			txt_12.setClickable(true);
			txt_12.setOnClickListener(this);
			txt_12.setText("0");
			txt_12.setTextColor(Color.TRANSPARENT);
			txt_12.setGravity(Gravity.CENTER);
			txt_12.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12.setBackgroundResource(0);
			
			txt_12_15=(TextView) main_yellow_layout.findViewById(R.id.txt_12_15);
			txt_12_15.setClickable(true);
			txt_12_15.setOnClickListener(this);
			txt_12_15.setText("0");
			txt_12_15.setTextColor(Color.BLACK);
			txt_12_15.setGravity(Gravity.CENTER);
			txt_12_15.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12_15.setBackgroundResource(0);
			
			txt_15=(TextView) main_yellow_layout.findViewById(R.id.txt_15);
			txt_15.setClickable(true);
			txt_15.setOnClickListener(this);
			txt_15.setText("0");
			txt_15.setTextColor(Color.TRANSPARENT);
			txt_15.setGravity(Gravity.CENTER);
			txt_15.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15.setBackgroundResource(0);
			
			txt_15_18=(TextView) main_yellow_layout.findViewById(R.id.txt_15_18);
			txt_15_18.setClickable(true);
			txt_15_18.setOnClickListener(this);
			txt_15_18.setText("0");
			txt_15_18.setTextColor(Color.BLACK);
			txt_15_18.setGravity(Gravity.CENTER);
			txt_15_18.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15_18.setBackgroundResource(0);
			
			
			
			txt_18=(TextView) main_yellow_layout.findViewById(R.id.txt_18);
			txt_18.setClickable(true);
			txt_18.setOnClickListener(this);
			txt_18.setText("0");
			txt_18.setTextColor(Color.TRANSPARENT);
			txt_18.setGravity(Gravity.CENTER);
			txt_18.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18.setBackgroundResource(0);
			
			txt_18_21=(TextView) main_yellow_layout.findViewById(R.id.txt_18_21);
			txt_18_21.setClickable(true);
			txt_18_21.setOnClickListener(this);
			txt_18_21.setText("0");
			txt_18_21.setTextColor(Color.BLACK);
			txt_18_21.setGravity(Gravity.CENTER);
			txt_18_21.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18_21.setBackgroundResource(0);
			
			
			txt_21=(TextView) main_yellow_layout.findViewById(R.id.txt_21);
			txt_21.setClickable(true);
			txt_21.setOnClickListener(this);
			txt_21.setText("0");
			txt_21.setTextColor(Color.TRANSPARENT);
			txt_21.setGravity(Gravity.CENTER);
			txt_21.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21.setBackgroundResource(0);
			
			txt_21_24=(TextView) main_yellow_layout.findViewById(R.id.txt_21_24);
			txt_21_24.setClickable(true);
			txt_21_24.setOnClickListener(this);
			txt_21_24.setText("0");
			txt_21_24.setTextColor(Color.BLACK);
			txt_21_24.setGravity(Gravity.CENTER);
			txt_21_24.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21_24.setBackgroundResource(0);
			
			
			txt_24=(TextView) main_yellow_layout.findViewById(R.id.txt_24);
			txt_24.setClickable(true);
			txt_24.setOnClickListener(this);
			txt_24.setText("0");
			txt_24.setTextColor(Color.TRANSPARENT);
			txt_24.setGravity(Gravity.CENTER);
			txt_24.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24.setBackgroundResource(0);
			
			txt_24_27=(TextView) main_yellow_layout.findViewById(R.id.txt_24_27);
			txt_24_27.setClickable(true);
			txt_24_27.setOnClickListener(this);
			txt_24_27.setText("0");
			txt_24_27.setTextColor(Color.BLACK);
			txt_24_27.setGravity(Gravity.CENTER);
			txt_24_27.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24_27.setBackgroundResource(0);
			
			txt_27=(TextView) main_yellow_layout.findViewById(R.id.txt_27);
			txt_27.setClickable(true);
			txt_27.setOnClickListener(this);
			txt_27.setText("0");
			txt_27.setTextColor(Color.TRANSPARENT);
			txt_27.setGravity(Gravity.CENTER);
			txt_27.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27.setBackgroundResource(0);
			
			txt_27_30=(TextView) main_yellow_layout.findViewById(R.id.txt_27_30);
			txt_27_30.setClickable(true);
			txt_27_30.setOnClickListener(this);
			txt_27_30.setText("0");
			txt_27_30.setTextColor(Color.BLACK);
			txt_27_30.setGravity(Gravity.CENTER);
			txt_27_30.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27_30.setBackgroundResource(0);
			
			txt_30=(TextView) main_yellow_layout.findViewById(R.id.txt_30);
			txt_30.setClickable(true);
			txt_30.setOnClickListener(this);
			txt_30.setText("0");
			txt_30.setTextColor(Color.TRANSPARENT);
			txt_30.setGravity(Gravity.CENTER);
			txt_30.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30.setBackgroundResource(0);
			
			txt_30_33=(TextView) main_yellow_layout.findViewById(R.id.txt_30_33);
			txt_30_33.setClickable(true);
			txt_30_33.setOnClickListener(this);
			txt_30_33.setText("0");
			txt_30_33.setTextColor(Color.BLACK);
			txt_30_33.setGravity(Gravity.CENTER);
			txt_30_33.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30_33.setBackgroundResource(0);
			
			txt_33=(TextView) main_yellow_layout.findViewById(R.id.txt_33);
			txt_33.setClickable(true);
			txt_33.setOnClickListener(this);
			txt_33.setText("0");
			txt_33.setTextColor(Color.TRANSPARENT);
			txt_33.setGravity(Gravity.CENTER);
			txt_33.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33.setBackgroundResource(0);
			
			txt_33_36=(TextView) main_yellow_layout.findViewById(R.id.txt_33_36);
			txt_33_36.setClickable(true);
			txt_33_36.setOnClickListener(this);
			txt_33_36.setText("0");
			txt_33_36.setTextColor(Color.BLACK);
			txt_33_36.setGravity(Gravity.CENTER);
			txt_33_36.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33_36.setBackgroundResource(0);
			
			txt_36=(TextView) main_yellow_layout.findViewById(R.id.txt_36);
			txt_36.setClickable(true);
			txt_36.setOnClickListener(this);
			txt_36.setText("0");
			txt_36.setTextColor(Color.TRANSPARENT);
			txt_36.setGravity(Gravity.CENTER);
			txt_36.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_36.setBackgroundResource(0);
			
			txt_first2to1=(TextView) main_yellow_layout.findViewById(R.id.txt_first2to1);	
			txt_first2to1.setClickable(true);
			txt_first2to1.setOnClickListener(this);
			txt_first2to1.setText("0");
			txt_first2to1.setTextColor(Color.TRANSPARENT);
			txt_first2to1.setGravity(Gravity.CENTER);
			txt_first2to1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_first2to1.setBackgroundResource(0);
	/* texboxes i,ii,iii,....xxiii,xxiv starts here  */
	
			
	/* textboxes A,B,C,......W,X starts here  */
			txt_00_3_2=(TextView) main_yellow_layout.findViewById(R.id.txt_00_3_2);
			txt_00_3_2.setClickable(true);
			txt_00_3_2.setOnClickListener(this);
			txt_00_3_2.setText("0");
			txt_00_3_2.setTextColor(Color.BLACK);
			txt_00_3_2.setGravity(Gravity.CENTER);
			txt_00_3_2.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00_3_2.setBackgroundResource(0);
			
			txt_3_2=(TextView) main_yellow_layout.findViewById(R.id.txt_3_2);
			txt_3_2.setClickable(true);
			txt_3_2.setOnClickListener(this);
			txt_3_2.setText("0");
			txt_3_2.setTextColor(Color.BLACK);
			txt_3_2.setGravity(Gravity.CENTER);
			txt_3_2.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3_2.setBackgroundResource(0);
			
			txt_3_6_2_5=(TextView) main_yellow_layout.findViewById(R.id.txt_3_6_2_5);
			txt_3_6_2_5.setClickable(true);
			txt_3_6_2_5.setOnClickListener(this);
			txt_3_6_2_5.setText("0");
			txt_3_6_2_5.setTextColor(Color.BLACK);
			txt_3_6_2_5.setGravity(Gravity.CENTER);
			txt_3_6_2_5.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3_6_2_5.setBackgroundResource(0);
			
			txt_6_5=(TextView) main_yellow_layout.findViewById(R.id.txt_6_5);
			txt_6_5.setClickable(true);
			txt_6_5.setOnClickListener(this);
			txt_6_5.setText("0");
			txt_6_5.setTextColor(Color.BLACK);
			txt_6_5.setGravity(Gravity.CENTER);
			txt_6_5.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6_5.setBackgroundResource(0);
			
			txt_6_9_5_8=(TextView) main_yellow_layout.findViewById(R.id.txt_6_9_5_8);
			txt_6_9_5_8.setClickable(true);
			txt_6_9_5_8.setOnClickListener(this);
			txt_6_9_5_8.setText("0");
			txt_6_9_5_8.setTextColor(Color.BLACK);
			txt_6_9_5_8.setGravity(Gravity.CENTER);
			txt_6_9_5_8.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6_9_5_8.setBackgroundResource(0);
			
			txt_9_8=(TextView) main_yellow_layout.findViewById(R.id.txt_9_8);
			txt_9_8.setClickable(true);
			txt_9_8.setOnClickListener(this);
			txt_9_8.setText("0");
			txt_9_8.setTextColor(Color.BLACK);
			txt_9_8.setGravity(Gravity.CENTER);
			txt_9_8.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9_8.setBackgroundResource(0);
			
			txt_9_12_8_11=(TextView) main_yellow_layout.findViewById(R.id.txt_9_12_8_11);
			txt_9_12_8_11.setClickable(true);
			txt_9_12_8_11.setOnClickListener(this);
			txt_9_12_8_11.setText("0");
			txt_9_12_8_11.setTextColor(Color.BLACK);
			txt_9_12_8_11.setGravity(Gravity.CENTER);
			txt_9_12_8_11.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9_12_8_11.setBackgroundResource(0);
			
			txt_12_11=(TextView) main_yellow_layout.findViewById(R.id.txt_12_11);
			txt_12_11.setClickable(true);
			txt_12_11.setOnClickListener(this);
			txt_12_11.setText("0");
			txt_12_11.setTextColor(Color.BLACK);
			txt_12_11.setGravity(Gravity.CENTER);
			txt_12_11.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12_11.setBackgroundResource(0);
			
			txt_12_15_11_14=(TextView) main_yellow_layout.findViewById(R.id.txt_12_15_11_14);
			txt_12_15_11_14.setClickable(true);
			txt_12_15_11_14.setOnClickListener(this);
			txt_12_15_11_14.setText("0");
			txt_12_15_11_14.setTextColor(Color.BLACK);
			txt_12_15_11_14.setGravity(Gravity.CENTER);
			txt_12_15_11_14.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12_15_11_14.setBackgroundResource(0);
			
			txt_15_14=(TextView) main_yellow_layout.findViewById(R.id.txt_15_14);
			txt_15_14.setClickable(true);
			txt_15_14.setOnClickListener(this);
			txt_15_14.setText("0");
			txt_15_14.setTextColor(Color.BLACK);
			txt_15_14.setGravity(Gravity.CENTER);
			txt_15_14.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15_14.setBackgroundResource(0);
			
			txt_15_18_14_17=(TextView) main_yellow_layout.findViewById(R.id.txt_15_18_14_17);
			txt_15_18_14_17.setClickable(true);
			txt_15_18_14_17.setOnClickListener(this);
			txt_15_18_14_17.setText("0");
			txt_15_18_14_17.setTextColor(Color.BLACK);
			txt_15_18_14_17.setGravity(Gravity.CENTER);
			txt_15_18_14_17.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15_18_14_17.setBackgroundResource(0);
			
			txt_18_17=(TextView) main_yellow_layout.findViewById(R.id.txt_18_17);
			txt_18_17.setClickable(true);
			txt_18_17.setOnClickListener(this);
			txt_18_17.setText("0");
			txt_18_17.setTextColor(Color.BLACK);
			txt_18_17.setGravity(Gravity.CENTER);
			txt_18_17.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18_17.setBackgroundResource(0);
			
			txt_18_21_17_20=(TextView) main_yellow_layout.findViewById(R.id.txt_18_21_17_20);
			txt_18_21_17_20.setClickable(true);
			txt_18_21_17_20.setOnClickListener(this);
			txt_18_21_17_20.setText("0");
			txt_18_21_17_20.setTextColor(Color.BLACK);
			txt_18_21_17_20.setGravity(Gravity.CENTER);
			txt_18_21_17_20.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18_21_17_20.setBackgroundResource(0);
			
			txt_21_20=(TextView) main_yellow_layout.findViewById(R.id.txt_21_20);
			txt_21_20.setClickable(true);
			txt_21_20.setOnClickListener(this);
			txt_21_20.setText("0");
			txt_21_20.setTextColor(Color.BLACK);
			txt_21_20.setGravity(Gravity.CENTER);
			txt_21_20.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21_20.setBackgroundResource(0);
			
			txt_21_24_20_23=(TextView) main_yellow_layout.findViewById(R.id.txt_21_24_20_23);
			txt_21_24_20_23.setClickable(true);
			txt_21_24_20_23.setOnClickListener(this);
			txt_21_24_20_23.setText("0");
			txt_21_24_20_23.setTextColor(Color.BLACK);
			txt_21_24_20_23.setGravity(Gravity.CENTER);
			txt_21_24_20_23.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21_24_20_23.setBackgroundResource(0);
			
			txt_24_23=(TextView) main_yellow_layout.findViewById(R.id.txt_24_23);
			txt_24_23.setClickable(true);
			txt_24_23.setOnClickListener(this);
			txt_24_23.setText("0");
			txt_24_23.setTextColor(Color.BLACK);
			txt_24_23.setGravity(Gravity.CENTER);
			txt_24_23.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24_23.setBackgroundResource(0);
			
			txt_24_27_23_26=(TextView) main_yellow_layout.findViewById(R.id.txt_24_27_23_26);
			txt_24_27_23_26.setClickable(true);
			txt_24_27_23_26.setOnClickListener(this);
			txt_24_27_23_26.setText("0");
			txt_24_27_23_26.setTextColor(Color.BLACK);
			txt_24_27_23_26.setGravity(Gravity.CENTER);
			txt_24_27_23_26.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24_27_23_26.setBackgroundResource(0);
			
			txt_27_26=(TextView) main_yellow_layout.findViewById(R.id.txt_27_26);
			txt_27_26.setClickable(true);
			txt_27_26.setOnClickListener(this);
			txt_27_26.setText("0");
			txt_27_26.setTextColor(Color.BLACK);
			txt_27_26.setGravity(Gravity.CENTER);
			txt_27_26.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27_26.setBackgroundResource(0);
			
			txt_27_30_26_29=(TextView) main_yellow_layout.findViewById(R.id.txt_27_30_26_29);
			txt_27_30_26_29.setClickable(true);
			txt_27_30_26_29.setOnClickListener(this);
			txt_27_30_26_29.setText("0");
			txt_27_30_26_29.setTextColor(Color.BLACK);
			txt_27_30_26_29.setGravity(Gravity.CENTER);
			txt_27_30_26_29.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27_30_26_29.setBackgroundResource(0);
			
			txt_30_29=(TextView) main_yellow_layout.findViewById(R.id.txt_30_29);
			txt_30_29.setClickable(true);
			txt_30_29.setOnClickListener(this);
			txt_30_29.setText("0");
			txt_30_29.setTextColor(Color.BLACK);
			txt_30_29.setGravity(Gravity.CENTER);
			txt_30_29.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30_29.setBackgroundResource(0);
			
			txt_30_33_29_32=(TextView) main_yellow_layout.findViewById(R.id.txt_30_33_29_32);
			txt_30_33_29_32.setClickable(true);
			txt_30_33_29_32.setOnClickListener(this);
			txt_30_33_29_32.setText("0");
			txt_30_33_29_32.setTextColor(Color.BLACK);
			txt_30_33_29_32.setGravity(Gravity.CENTER);
			txt_30_33_29_32.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30_33_29_32.setBackgroundResource(0);
			
			txt_33_32=(TextView) main_yellow_layout.findViewById(R.id.txt_33_32);
			txt_33_32.setClickable(true);
			txt_33_32.setOnClickListener(this);
			txt_33_32.setText("0");
			txt_33_32.setTextColor(Color.BLACK);
			txt_33_32.setGravity(Gravity.CENTER);
			txt_33_32.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33_32.setBackgroundResource(0);
			
			txt_33_36_32_35=(TextView) main_yellow_layout.findViewById(R.id.txt_33_36_32_35);
			txt_33_36_32_35.setClickable(true);
			txt_33_36_32_35.setOnClickListener(this);
			txt_33_36_32_35.setText("0");
			txt_33_36_32_35.setTextColor(Color.BLACK);
			txt_33_36_32_35.setGravity(Gravity.CENTER);
			txt_33_36_32_35.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33_36_32_35.setBackgroundResource(0);
			
			txt_36_35=(TextView) main_yellow_layout.findViewById(R.id.txt_36_35);
			txt_36_35.setClickable(true);
			txt_36_35.setOnClickListener(this);
			txt_36_35.setText("0");
			txt_36_35.setTextColor(Color.BLACK);
			txt_36_35.setGravity(Gravity.CENTER);
			txt_36_35.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_36_35.setBackgroundResource(0);
	/* textboxes A,B,C,......W,X ends here  */
	
	/* textboxes a,b,c.....w,x starts here  */
			txt_00_0_2=(TextView) main_yellow_layout.findViewById(R.id.txt_00_0_2);
			txt_00_0_2.setClickable(true);
			txt_00_0_2.setOnClickListener(this);
			txt_00_0_2.setText("0");
			txt_00_0_2.setTextColor(Color.BLACK);
			txt_00_0_2.setGravity(Gravity.CENTER);
			txt_00_0_2.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00_0_2.setBackgroundResource(0);
			
			
			
			txt_2=(TextView) main_yellow_layout.findViewById(R.id.txt_2);
			txt_2.setClickable(true);
			txt_2.setOnClickListener(this);
			txt_2.setText("0");
			txt_2.setTextColor(Color.TRANSPARENT);
			txt_2.setGravity(Gravity.CENTER);
			txt_2.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_2.setBackgroundResource(0);
			
			txt_2_5=(TextView) main_yellow_layout.findViewById(R.id.txt_2_5);
			txt_2_5.setClickable(true);
			txt_2_5.setOnClickListener(this);
			txt_2_5.setText("0");
			txt_2_5.setTextColor(Color.BLACK);
			txt_2_5.setGravity(Gravity.CENTER);
			txt_2_5.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_2_5.setBackgroundResource(0);
			
			
			txt_5=(TextView) main_yellow_layout.findViewById(R.id.txt_5);
			txt_5.setClickable(true);
			txt_5.setOnClickListener(this);
			txt_5.setText("0");
			txt_5.setTextColor(Color.TRANSPARENT);
			txt_5.setGravity(Gravity.CENTER);
			txt_5.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_5.setBackgroundResource(0);
			
			txt_5_8=(TextView) main_yellow_layout.findViewById(R.id.txt_5_8);
			txt_5_8.setClickable(true);
			txt_5_8.setOnClickListener(this);
			txt_5_8.setText("0");
			txt_5_8.setTextColor(Color.BLACK);
			txt_5_8.setGravity(Gravity.CENTER);
			txt_5_8.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_5_8.setBackgroundResource(0);
			
			
			txt_8=(TextView) main_yellow_layout.findViewById(R.id.txt_8);
			txt_8.setClickable(true);
			txt_8.setOnClickListener(this);
			txt_8.setText("0");
			txt_8.setTextColor(Color.TRANSPARENT);
			txt_8.setGravity(Gravity.CENTER);
			txt_8.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_8.setBackgroundResource(0);
			
			txt_8_11=(TextView) main_yellow_layout.findViewById(R.id.txt_8_11);
			txt_8_11.setClickable(true);
			txt_8_11.setOnClickListener(this);
			txt_8_11.setText("0");
			txt_8_11.setTextColor(Color.BLACK);
			txt_8_11.setGravity(Gravity.CENTER);
			txt_8_11.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_8_11.setBackgroundResource(0);
			
			
			txt_11=(TextView) main_yellow_layout.findViewById(R.id.txt_11);
			txt_11.setClickable(true);
			txt_11.setOnClickListener(this);
			txt_11.setText("0");
			txt_11.setTextColor(Color.TRANSPARENT);
			txt_11.setGravity(Gravity.CENTER);
			txt_11.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_11.setBackgroundResource(0);
			
			txt_11_14=(TextView) main_yellow_layout.findViewById(R.id.txt_11_14);
			txt_11_14.setClickable(true);
			txt_11_14.setOnClickListener(this);
			txt_11_14.setText("0");
			txt_11_14.setTextColor(Color.BLACK);
			txt_11_14.setGravity(Gravity.CENTER);
			txt_11_14.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_11_14.setBackgroundResource(0);
			
			
			txt_14=(TextView) main_yellow_layout.findViewById(R.id.txt_14);
			txt_14.setClickable(true);
			txt_14.setOnClickListener(this);
			txt_14.setText("0");
			txt_14.setTextColor(Color.TRANSPARENT);
			txt_14.setGravity(Gravity.CENTER);
			txt_14.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_14.setBackgroundResource(0);
			
			txt_14_17=(TextView) main_yellow_layout.findViewById(R.id.txt_14_17);
			txt_14_17.setClickable(true);
			txt_14_17.setOnClickListener(this);
			txt_14_17.setText("0");
			txt_14_17.setTextColor(Color.BLACK);
			txt_14_17.setGravity(Gravity.CENTER);
			txt_14_17.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_14_17.setBackgroundResource(0);
			
			
			txt_17=(TextView) main_yellow_layout.findViewById(R.id.txt_17);
			txt_17.setClickable(true);
			txt_17.setOnClickListener(this);
			txt_17.setText("0");
			txt_17.setTextColor(Color.TRANSPARENT);
			txt_17.setGravity(Gravity.CENTER);
			txt_17.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_17.setBackgroundResource(0);
			
			txt_17_20=(TextView) main_yellow_layout.findViewById(R.id.txt_17_20);
			txt_17_20.setClickable(true);
			txt_17_20.setOnClickListener(this);
			txt_17_20.setText("0");
			txt_17_20.setTextColor(Color.BLACK);
			txt_17_20.setGravity(Gravity.CENTER);
			txt_17_20.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_17_20.setBackgroundResource(0);
			
			
			txt_20=(TextView) main_yellow_layout.findViewById(R.id.txt_20);
			txt_20.setClickable(true);
			txt_20.setOnClickListener(this);
			txt_20.setText("0");
			txt_20.setTextColor(Color.TRANSPARENT);
			txt_20.setGravity(Gravity.CENTER);
			txt_20.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_20.setBackgroundResource(0);
			
			txt_20_23=(TextView) main_yellow_layout.findViewById(R.id.txt_20_23);
			txt_20_23.setClickable(true);
			txt_20_23.setOnClickListener(this);
			txt_20_23.setText("0");
			txt_20_23.setTextColor(Color.BLACK);
			txt_20_23.setGravity(Gravity.CENTER);
			txt_20_23.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_20_23.setBackgroundResource(0);
			
			
			txt_23=(TextView) main_yellow_layout.findViewById(R.id.txt_23);
			txt_23.setClickable(true);
			txt_23.setOnClickListener(this);
			txt_23.setText("0");
			txt_23.setTextColor(Color.TRANSPARENT);
			txt_23.setGravity(Gravity.CENTER);
			txt_23.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_23.setBackgroundResource(0);
			
			txt_23_26=(TextView) main_yellow_layout.findViewById(R.id.txt_23_26);
			txt_23_26.setClickable(true);
			txt_23_26.setOnClickListener(this);
			txt_23_26.setText("0");
			txt_23_26.setTextColor(Color.BLACK);
			txt_23_26.setGravity(Gravity.CENTER);
			txt_23_26.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_23_26.setBackgroundResource(0);
			
			
			txt_26=(TextView) main_yellow_layout.findViewById(R.id.txt_26);
			txt_26.setClickable(true);
			txt_26.setOnClickListener(this);
			txt_26.setText("0");
			txt_26.setTextColor(Color.TRANSPARENT);
			txt_26.setGravity(Gravity.CENTER);
			txt_26.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_26.setBackgroundResource(0);
			
			txt_26_29=(TextView) main_yellow_layout.findViewById(R.id.txt_26_29);
			txt_26_29.setClickable(true);
			txt_26_29.setOnClickListener(this);
			txt_26_29.setText("0");
			txt_26_29.setTextColor(Color.BLACK);
			txt_26_29.setGravity(Gravity.CENTER);
			txt_26_29.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_26_29.setBackgroundResource(0);
			
			
			txt_29=(TextView) main_yellow_layout.findViewById(R.id.txt_29);
			txt_29.setClickable(true);
			txt_29.setOnClickListener(this);
			txt_29.setText("0");
			txt_29.setTextColor(Color.TRANSPARENT);
			txt_29.setGravity(Gravity.CENTER);
			txt_29.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_29.setBackgroundResource(0);
			
			txt_29_32=(TextView) main_yellow_layout.findViewById(R.id.txt_29_32);
			txt_29_32.setClickable(true);
			txt_29_32.setOnClickListener(this);
			txt_29_32.setText("0");
			txt_29_32.setTextColor(Color.BLACK);
			txt_29_32.setGravity(Gravity.CENTER);
			txt_29_32.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_29_32.setBackgroundResource(0);
			
			
			txt_32=(TextView) main_yellow_layout.findViewById(R.id.txt_32);
			txt_32.setClickable(true);
			txt_32.setOnClickListener(this);
			txt_32.setText("0");
			txt_32.setTextColor(Color.TRANSPARENT);
			txt_32.setGravity(Gravity.CENTER);
			txt_32.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_32.setBackgroundResource(0);
			
			txt_32_35=(TextView) main_yellow_layout.findViewById(R.id.txt_32_35);
			txt_32_35.setClickable(true);
			txt_32_35.setOnClickListener(this);
			txt_32_35.setText("0");
			txt_32_35.setTextColor(Color.BLACK);
			txt_32_35.setGravity(Gravity.CENTER);
			txt_32_35.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_32_35.setBackgroundResource(0);
			
			
			
			txt_35=(TextView) main_yellow_layout.findViewById(R.id.txt_35);
			txt_35.setClickable(true);
			txt_35.setOnClickListener(this);
			txt_35.setText("0");
			txt_35.setTextColor(Color.TRANSPARENT);
			txt_35.setGravity(Gravity.CENTER);
			txt_35.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_35.setBackgroundResource(0);
			
			txt_second2to1=(TextView) main_yellow_layout.findViewById(R.id.txt_second2to1);
			txt_second2to1.setClickable(true);
			txt_second2to1.setOnClickListener(this);
			txt_second2to1.setText("0");
			txt_second2to1.setTextColor(Color.TRANSPARENT);
			txt_second2to1.setGravity(Gravity.CENTER);
			txt_second2to1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_second2to1.setBackgroundResource(0);
	/* textboxes a,b,c.....w,x ends here  */
	
	
	/* textboxes A1,B1,C1,......W1,X1 starts here   */
			txt_0_2_1=(TextView) main_yellow_layout.findViewById(R.id.txt_0_2_1);
			txt_0_2_1.setClickable(true);
			txt_0_2_1.setOnClickListener(this);
			txt_0_2_1.setText("0");
			txt_0_2_1.setTextColor(Color.BLACK);
			txt_0_2_1.setGravity(Gravity.CENTER);
			txt_0_2_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_0_2_1.setBackgroundResource(0);
			
			
			txt_2_1=(TextView) main_yellow_layout.findViewById(R.id.txt_2_1);
			txt_2_1.setClickable(true);
			txt_2_1.setOnClickListener(this);
			txt_2_1.setText("0");
			txt_2_1.setTextColor(Color.BLACK);
			txt_2_1.setGravity(Gravity.CENTER);
			txt_2_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_2_1.setBackgroundResource(0);
			
			
			txt_2_5_1_4=(TextView) main_yellow_layout.findViewById(R.id.txt_2_5_1_4);
			txt_2_5_1_4.setClickable(true);
			txt_2_5_1_4.setOnClickListener(this);
			txt_2_5_1_4.setText("0");
			txt_2_5_1_4.setTextColor(Color.BLACK);
			txt_2_5_1_4.setGravity(Gravity.CENTER);
			txt_2_5_1_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_2_5_1_4.setBackgroundResource(0);
			
			txt_5_4=(TextView) main_yellow_layout.findViewById(R.id.txt_5_4);
			txt_5_4.setClickable(true);
			txt_5_4.setOnClickListener(this);
			txt_5_4.setText("0");
			txt_5_4.setTextColor(Color.BLACK);
			txt_5_4.setGravity(Gravity.CENTER);
			txt_5_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_5_4.setBackgroundResource(0);
			
			txt_5_8_4_7=(TextView) main_yellow_layout.findViewById(R.id.txt_5_8_4_7);
			txt_5_8_4_7.setClickable(true);
			txt_5_8_4_7.setOnClickListener(this);
			txt_5_8_4_7.setText("0");
			txt_5_8_4_7.setTextColor(Color.BLACK);
			txt_5_8_4_7.setGravity(Gravity.CENTER);
			txt_5_8_4_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_5_8_4_7.setBackgroundResource(0);
			
			txt_8_7=(TextView) main_yellow_layout.findViewById(R.id.txt_8_7);
			txt_8_7.setClickable(true);
			txt_8_7.setOnClickListener(this);
			txt_8_7.setText("0");
			txt_8_7.setTextColor(Color.BLACK);
			txt_8_7.setGravity(Gravity.CENTER);
			txt_8_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_8_7.setBackgroundResource(0);
			
			txt_8_11_7_10=(TextView) main_yellow_layout.findViewById(R.id.txt_8_11_7_10);
			txt_8_11_7_10.setClickable(true);
			txt_8_11_7_10.setOnClickListener(this);
			txt_8_11_7_10.setText("0");
			txt_8_11_7_10.setTextColor(Color.BLACK);
			txt_8_11_7_10.setGravity(Gravity.CENTER);
			txt_8_11_7_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_8_11_7_10.setBackgroundResource(0);
			
			txt_11_10=(TextView) main_yellow_layout.findViewById(R.id.txt_11_10);
			txt_11_10.setClickable(true);
			txt_11_10.setOnClickListener(this);
			txt_11_10.setText("0");
			txt_11_10.setTextColor(Color.BLACK);
			txt_11_10.setGravity(Gravity.CENTER);
			txt_11_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_11_10.setBackgroundResource(0);
			
			txt_11_14_10_13=(TextView) main_yellow_layout.findViewById(R.id.txt_11_14_10_13);
			txt_11_14_10_13.setClickable(true);
			txt_11_14_10_13.setOnClickListener(this);
			txt_11_14_10_13.setText("0");
			txt_11_14_10_13.setTextColor(Color.BLACK);
			txt_11_14_10_13.setGravity(Gravity.CENTER);
			txt_11_14_10_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_11_14_10_13.setBackgroundResource(0);
			
			txt_14_13=(TextView) main_yellow_layout.findViewById(R.id.txt_14_13);
			txt_14_13.setClickable(true);
			txt_14_13.setOnClickListener(this);
			txt_14_13.setText("0");
			txt_14_13.setTextColor(Color.BLACK);
			txt_14_13.setGravity(Gravity.CENTER);
			txt_14_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_14_13.setBackgroundResource(0);
			
			txt_14_17_13_16=(TextView) main_yellow_layout.findViewById(R.id.txt_14_17_13_16);
			txt_14_17_13_16.setClickable(true);
			txt_14_17_13_16.setOnClickListener(this);
			txt_14_17_13_16.setText("0");
			txt_14_17_13_16.setTextColor(Color.BLACK);
			txt_14_17_13_16.setGravity(Gravity.CENTER);
			txt_14_17_13_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_14_17_13_16.setBackgroundResource(0);
			
			txt_17_16=(TextView) main_yellow_layout.findViewById(R.id.txt_17_16);
			txt_17_16.setClickable(true);
			txt_17_16.setOnClickListener(this);
			txt_17_16.setText("0");
			txt_17_16.setTextColor(Color.BLACK);
			txt_17_16.setGravity(Gravity.CENTER);
			txt_17_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_17_16.setBackgroundResource(0);
			
			txt_17_20_16_19=(TextView) main_yellow_layout.findViewById(R.id.txt_17_20_16_19);
			txt_17_20_16_19.setClickable(true);
			txt_17_20_16_19.setOnClickListener(this);
			txt_17_20_16_19.setText("0");
			txt_17_20_16_19.setTextColor(Color.BLACK);
			txt_17_20_16_19.setGravity(Gravity.CENTER);
			txt_17_20_16_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_17_20_16_19.setBackgroundResource(0);
			
			txt_20_19=(TextView) main_yellow_layout.findViewById(R.id.txt_20_19);
			txt_20_19.setClickable(true);
			txt_20_19.setOnClickListener(this);
			txt_20_19.setText("0");
			txt_20_19.setTextColor(Color.BLACK);
			txt_20_19.setGravity(Gravity.CENTER);
			txt_20_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_20_19.setBackgroundResource(0);
			
			txt_20_23_19_22=(TextView) main_yellow_layout.findViewById(R.id.txt_20_23_19_22);
			txt_20_23_19_22.setClickable(true);
			txt_20_23_19_22.setOnClickListener(this);
			txt_20_23_19_22.setText("0");
			txt_20_23_19_22.setTextColor(Color.BLACK);
			txt_20_23_19_22.setGravity(Gravity.CENTER);
			txt_20_23_19_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_20_23_19_22.setBackgroundResource(0);
			
			txt_23_22=(TextView) main_yellow_layout.findViewById(R.id.txt_23_22);
			txt_23_22.setClickable(true);
			txt_23_22.setOnClickListener(this);
			txt_23_22.setText("0");
			txt_23_22.setTextColor(Color.BLACK);
			txt_23_22.setGravity(Gravity.CENTER);
			txt_23_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_23_22.setBackgroundResource(0);
			
			txt_23_26_22_25=(TextView) main_yellow_layout.findViewById(R.id.txt_23_26_22_25);
			txt_23_26_22_25.setClickable(true);
			txt_23_26_22_25.setOnClickListener(this);
			txt_23_26_22_25.setText("0");
			txt_23_26_22_25.setTextColor(Color.BLACK);
			txt_23_26_22_25.setGravity(Gravity.CENTER);
			txt_23_26_22_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_23_26_22_25.setBackgroundResource(0);
			
			txt_26_25=(TextView) main_yellow_layout.findViewById(R.id.txt_26_25);
			txt_26_25.setClickable(true);
			txt_26_25.setOnClickListener(this);
			txt_26_25.setText("0");
			txt_26_25.setTextColor(Color.BLACK);
			txt_26_25.setGravity(Gravity.CENTER);
			txt_26_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_26_25.setBackgroundResource(0);
			
			txt_26_29_25_28=(TextView) main_yellow_layout.findViewById(R.id.txt_26_29_25_28);
			txt_26_29_25_28.setClickable(true);
			txt_26_29_25_28.setOnClickListener(this);
			txt_26_29_25_28.setText("0");
			txt_26_29_25_28.setTextColor(Color.BLACK);
			txt_26_29_25_28.setGravity(Gravity.CENTER);
			txt_26_29_25_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_26_29_25_28.setBackgroundResource(0);
			
			txt_29_28=(TextView) main_yellow_layout.findViewById(R.id.txt_29_28);
			txt_29_28.setClickable(true);
			txt_29_28.setOnClickListener(this);
			txt_29_28.setText("0");
			txt_29_28.setTextColor(Color.BLACK);
			txt_29_28.setGravity(Gravity.CENTER);
			txt_29_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_29_28.setBackgroundResource(0);
			
			txt_29_32_28_31=(TextView) main_yellow_layout.findViewById(R.id.txt_29_32_28_31);
			txt_29_32_28_31.setClickable(true);
			txt_29_32_28_31.setOnClickListener(this);
			txt_29_32_28_31.setText("0");
			txt_29_32_28_31.setTextColor(Color.BLACK);
			txt_29_32_28_31.setGravity(Gravity.CENTER);
			txt_29_32_28_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_29_32_28_31.setBackgroundResource(0);
			
			txt_32_31=(TextView) main_yellow_layout.findViewById(R.id.txt_32_31);
			txt_32_31.setClickable(true);
			txt_32_31.setOnClickListener(this);
			txt_32_31.setText("0");
			txt_32_31.setTextColor(Color.BLACK);
			txt_32_31.setGravity(Gravity.CENTER);
			txt_32_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_32_31.setBackgroundResource(0);
			
			txt_32_35_31_34=(TextView) main_yellow_layout.findViewById(R.id.txt_32_35_31_34);
			txt_32_35_31_34.setClickable(true);
			txt_32_35_31_34.setOnClickListener(this);
			txt_32_35_31_34.setText("0");
			txt_32_35_31_34.setTextColor(Color.BLACK);
			txt_32_35_31_34.setGravity(Gravity.CENTER);
			txt_32_35_31_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_32_35_31_34.setBackgroundResource(0);
			
			txt_35_34=(TextView) main_yellow_layout.findViewById(R.id.txt_35_34);
			txt_35_34.setClickable(true);
			txt_35_34.setOnClickListener(this);
			txt_35_34.setText("0");
			txt_35_34.setTextColor(Color.BLACK);
			txt_35_34.setGravity(Gravity.CENTER);
			txt_35_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_35_34.setBackgroundResource(0);
	/* textboxes A1,B1,C1,......W1,X1 starts here   */
	
	
	/*  textboxes a1,b1,c1,.....w1,x1 starts here  */
			txt_0_1=(TextView) main_yellow_layout.findViewById(R.id.txt_0_1);
			txt_0_1.setClickable(true);
			txt_0_1.setOnClickListener(this);
			txt_0_1.setText("0");
			txt_0_1.setTextColor(Color.BLACK);
			txt_0_1.setGravity(Gravity.CENTER);
			txt_0_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_0_1.setBackgroundResource(0);
			
			
			
			txt_1=(TextView) main_yellow_layout.findViewById(R.id.txt_1);
			txt_1.setClickable(true);
			txt_1.setOnClickListener(this);
			txt_1.setText("0");
			txt_1.setTextColor(Color.TRANSPARENT);
			txt_1.setGravity(Gravity.CENTER);
			txt_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_1.setBackgroundResource(0);
			
			txt_1_4=(TextView) main_yellow_layout.findViewById(R.id.txt_1_4);
			txt_1_4.setClickable(true);
			txt_1_4.setOnClickListener(this);
			txt_1_4.setText("0");
			txt_1_4.setTextColor(Color.BLACK);
			txt_1_4.setGravity(Gravity.CENTER);
			txt_1_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_1_4.setBackgroundResource(0);
			
			
			txt_4=(TextView) main_yellow_layout.findViewById(R.id.txt_4);
			txt_4.setClickable(true);
			txt_4.setOnClickListener(this);
			txt_4.setText("0");
			txt_4.setTextColor(Color.TRANSPARENT);
			txt_4.setGravity(Gravity.CENTER);
			txt_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_4.setBackgroundResource(0);
			
			txt_4_7=(TextView) main_yellow_layout.findViewById(R.id.txt_4_7);
			txt_4_7.setClickable(true);
			txt_4_7.setOnClickListener(this);
			txt_4_7.setText("0");
			txt_4_7.setTextColor(Color.BLACK);
			txt_4_7.setGravity(Gravity.CENTER);
			txt_4_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_4_7.setBackgroundResource(0);
			
			
			txt_7=(TextView) main_yellow_layout.findViewById(R.id.txt_7);
			txt_7.setClickable(true);
			txt_7.setOnClickListener(this);
			txt_7.setText("0");
			txt_7.setTextColor(Color.TRANSPARENT);
			txt_7.setGravity(Gravity.CENTER);
			txt_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_7.setBackgroundResource(0);
			
			txt_7_10=(TextView) main_yellow_layout.findViewById(R.id.txt_7_10);
			txt_7_10.setClickable(true);
			txt_7_10.setOnClickListener(this);
			txt_7_10.setText("0");
			txt_7_10.setTextColor(Color.BLACK);
			txt_7_10.setGravity(Gravity.CENTER);
			txt_7_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_7_10.setBackgroundResource(0);
			
			
			txt_10=(TextView) main_yellow_layout.findViewById(R.id.txt_10);
			txt_10.setClickable(true);
			txt_10.setOnClickListener(this);
			txt_10.setText("0");
			txt_10.setTextColor(Color.TRANSPARENT);
			txt_10.setGravity(Gravity.CENTER);
			txt_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_10.setBackgroundResource(0);
			
			txt_10_13=(TextView) main_yellow_layout.findViewById(R.id.txt_10_13);
			txt_10_13.setClickable(true);
			txt_10_13.setOnClickListener(this);
			txt_10_13.setText("0");
			txt_10_13.setTextColor(Color.BLACK);
			txt_10_13.setGravity(Gravity.CENTER);
			txt_10_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_10_13.setBackgroundResource(0);
			
			
			txt_13=(TextView) main_yellow_layout.findViewById(R.id.txt_13);
			txt_13.setClickable(true);
			txt_13.setOnClickListener(this);
			txt_13.setText("0");
			txt_13.setTextColor(Color.TRANSPARENT);
			txt_13.setGravity(Gravity.CENTER);
			txt_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_13.setBackgroundResource(0);
			
			txt_13_16=(TextView) main_yellow_layout.findViewById(R.id.txt_13_16);
			txt_13_16.setClickable(true);
			txt_13_16.setOnClickListener(this);
			txt_13_16.setText("0");
			txt_13_16.setTextColor(Color.BLACK);
			txt_13_16.setGravity(Gravity.CENTER);
			txt_13_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_13_16.setBackgroundResource(0);
			
			
			
			txt_16=(TextView) main_yellow_layout.findViewById(R.id.txt_16);
			txt_16.setClickable(true);
			txt_16.setOnClickListener(this);
			txt_16.setText("0");
			txt_16.setTextColor(Color.TRANSPARENT);
			txt_16.setGravity(Gravity.CENTER);
			txt_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_16.setBackgroundResource(0);
			
			txt_16_19=(TextView) main_yellow_layout.findViewById(R.id.txt_16_19);
			txt_16_19.setClickable(true);
			txt_16_19.setOnClickListener(this);
			txt_16_19.setText("0");
			txt_16_19.setTextColor(Color.BLACK);
			txt_16_19.setGravity(Gravity.CENTER);
			txt_16_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_16_19.setBackgroundResource(0);
			
			
			txt_19=(TextView) main_yellow_layout.findViewById(R.id.txt_19);
			txt_19.setClickable(true);
			txt_19.setOnClickListener(this);
			txt_19.setText("0");
			txt_19.setTextColor(Color.TRANSPARENT);
			txt_19.setGravity(Gravity.CENTER);
			txt_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_19.setBackgroundResource(0);
			
			txt_19_22=(TextView) main_yellow_layout.findViewById(R.id.txt_19_22);
			txt_19_22.setClickable(true);
			txt_19_22.setOnClickListener(this);
			txt_19_22.setText("0");
			txt_19_22.setTextColor(Color.BLACK);
			txt_19_22.setGravity(Gravity.CENTER);
			txt_19_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_19_22.setBackgroundResource(0);
			
			
			
			txt_22=(TextView) main_yellow_layout.findViewById(R.id.txt_22);
			txt_22.setClickable(true);
			txt_22.setOnClickListener(this);
			txt_22.setText("0");
			txt_22.setTextColor(Color.TRANSPARENT);
			txt_22.setGravity(Gravity.CENTER);
			txt_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_22.setBackgroundResource(0);
			
			txt_22_25=(TextView) main_yellow_layout.findViewById(R.id.txt_22_25);
			txt_22_25.setClickable(true);
			txt_22_25.setOnClickListener(this);
			txt_22_25.setText("0");
			txt_22_25.setTextColor(Color.BLACK);
			txt_22_25.setGravity(Gravity.CENTER);
			txt_22_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_22_25.setBackgroundResource(0);
			
			
			txt_25=(TextView) main_yellow_layout.findViewById(R.id.txt_25);
			txt_25.setClickable(true);
			txt_25.setOnClickListener(this);
			txt_25.setText("0");
			txt_25.setTextColor(Color.TRANSPARENT);
			txt_25.setGravity(Gravity.CENTER);
			txt_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_25.setBackgroundResource(0);
			
			txt_25_28=(TextView) main_yellow_layout.findViewById(R.id.txt_25_28);
			txt_25_28.setClickable(true);
			txt_25_28.setOnClickListener(this);
			txt_25_28.setText("0");
			txt_25_28.setTextColor(Color.BLACK);
			txt_25_28.setGravity(Gravity.CENTER);
			txt_25_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_25_28.setBackgroundResource(0);
			
			
			txt_28=(TextView) main_yellow_layout.findViewById(R.id.txt_28);
			txt_28.setClickable(true);
			txt_28.setOnClickListener(this);
			txt_28.setText("0");
			txt_28.setTextColor(Color.TRANSPARENT);
			txt_28.setGravity(Gravity.CENTER);
			txt_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_28.setBackgroundResource(0);
			
			txt_28_31=(TextView) main_yellow_layout.findViewById(R.id.txt_28_31);
			txt_28_31.setClickable(true);
			txt_28_31.setOnClickListener(this);
			txt_28_31.setText("0");
			txt_28_31.setTextColor(Color.BLACK);
			txt_28_31.setGravity(Gravity.CENTER);
			txt_28_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_28_31.setBackgroundResource(0);
			
			
			txt_31=(TextView) main_yellow_layout.findViewById(R.id.txt_31);
			txt_31.setClickable(true);
			txt_31.setOnClickListener(this);
			txt_31.setText("0");
			txt_31.setTextColor(Color.TRANSPARENT);
			txt_31.setGravity(Gravity.CENTER);
			txt_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_31.setBackgroundResource(0);
			
			txt_31_34=(TextView) main_yellow_layout.findViewById(R.id.txt_31_34);
			txt_31_34.setClickable(true);
			txt_31_34.setOnClickListener(this);
			txt_31_34.setText("0");
			txt_31_34.setTextColor(Color.BLACK);
			txt_31_34.setGravity(Gravity.CENTER);
			txt_31_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_31_34.setBackgroundResource(0);
			
			
			txt_34=(TextView) main_yellow_layout.findViewById(R.id.txt_34);
			txt_34.setClickable(true);
			txt_34.setOnClickListener(this);
			txt_34.setText("0");
			txt_34.setTextColor(Color.TRANSPARENT);
			txt_34.setGravity(Gravity.CENTER);
			txt_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_34.setBackgroundResource(0);
			
			txt_third2to1=(TextView) main_yellow_layout.findViewById(R.id.txt_third2to1);
			txt_third2to1.setClickable(true);
			txt_third2to1.setOnClickListener(this);
			txt_third2to1.setText("0");
			txt_third2to1.setTextColor(Color.TRANSPARENT);
			txt_third2to1.setGravity(Gravity.CENTER);
			txt_third2to1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_third2to1.setBackgroundResource(0);
	/*  textboxes a1,b1,c1,.....w1,x1 ends here  */
	
	
			
   /*  textboxes A2,B2,C2,.....W2,X2 starts here */
		/*	txt_00_0_3_2_1=(TextView) main_yellow_layout.findViewById(R.id.txt_00_0_3_2_1);
			txt_00_0_3_2_1.setClickable(true);
			txt_00_0_3_2_1.setOnClickListener(this);
			txt_00_0_3_2_1.setText("0");
			txt_00_0_3_2_1.setTextColor(Color.BLACK);
			txt_00_0_3_2_1.setGravity(Gravity.CENTER);
			txt_00_0_3_2_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_00_0_3_2_1.setBackgroundResource(0);*/
			
			txt_3_2_1=(TextView) main_yellow_layout.findViewById(R.id.txt_3_2_1);
			txt_3_2_1.setClickable(true);
			txt_3_2_1.setOnClickListener(this);
			txt_3_2_1.setText("0");
			txt_3_2_1.setTextColor(Color.BLACK);
			txt_3_2_1.setGravity(Gravity.CENTER);
			txt_3_2_1.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3_2_1.setBackgroundResource(0);
			
			txt_3_2_1_6_5_4=(TextView) main_yellow_layout.findViewById(R.id.txt_3_2_1_6_5_4);
			txt_3_2_1_6_5_4.setClickable(true);
			txt_3_2_1_6_5_4.setOnClickListener(this);
			txt_3_2_1_6_5_4.setText("0");
			txt_3_2_1_6_5_4.setTextColor(Color.BLACK);
			txt_3_2_1_6_5_4.setGravity(Gravity.CENTER);
			txt_3_2_1_6_5_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3_2_1_6_5_4.setBackgroundResource(0);
			
			txt_6_5_4=(TextView) main_yellow_layout.findViewById(R.id.txt_6_5_4);
			txt_6_5_4.setClickable(true);
			txt_6_5_4.setOnClickListener(this);
			txt_6_5_4.setText("0");
			txt_6_5_4.setTextColor(Color.BLACK);
			txt_6_5_4.setGravity(Gravity.CENTER);
			txt_6_5_4.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6_5_4.setBackgroundResource(0);
			
			txt_6_5_4_9_8_7=(TextView) main_yellow_layout.findViewById(R.id.txt_6_5_4_9_8_7);
			txt_6_5_4_9_8_7.setClickable(true);
			txt_6_5_4_9_8_7.setOnClickListener(this);
			txt_6_5_4_9_8_7.setText("0");
			txt_6_5_4_9_8_7.setTextColor(Color.BLACK);
			txt_6_5_4_9_8_7.setGravity(Gravity.CENTER);
			txt_6_5_4_9_8_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_6_5_4_9_8_7.setBackgroundResource(0);
			
			txt_9_8_7=(TextView) main_yellow_layout.findViewById(R.id.txt_9_8_7);
			txt_9_8_7.setClickable(true);
			txt_9_8_7.setOnClickListener(this);
			txt_9_8_7.setText("0");
			txt_9_8_7.setTextColor(Color.BLACK);
			txt_9_8_7.setGravity(Gravity.CENTER);
			txt_9_8_7.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9_8_7.setBackgroundResource(0);
			
			txt_9_8_7_12_11_10=(TextView) main_yellow_layout.findViewById(R.id.txt_9_8_7_12_11_10);
			txt_9_8_7_12_11_10.setClickable(true);
			txt_9_8_7_12_11_10.setOnClickListener(this);
			txt_9_8_7_12_11_10.setText("0");
			txt_9_8_7_12_11_10.setTextColor(Color.BLACK);
			txt_9_8_7_12_11_10.setGravity(Gravity.CENTER);
			txt_9_8_7_12_11_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_9_8_7_12_11_10.setBackgroundResource(0);
			
			txt_12_11_10=(TextView) main_yellow_layout.findViewById(R.id.txt_12_11_10);
			txt_12_11_10.setClickable(true);
			txt_12_11_10.setOnClickListener(this);
			txt_12_11_10.setText("0");
			txt_12_11_10.setTextColor(Color.BLACK);
			txt_12_11_10.setGravity(Gravity.CENTER);
			txt_12_11_10.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12_11_10.setBackgroundResource(0);
			
			txt_12_11_10_15_14_13=(TextView) main_yellow_layout.findViewById(R.id.txt_12_11_10_15_14_13);
			txt_12_11_10_15_14_13.setClickable(true);
			txt_12_11_10_15_14_13.setOnClickListener(this);
			txt_12_11_10_15_14_13.setText("0");
			txt_12_11_10_15_14_13.setTextColor(Color.BLACK);
			txt_12_11_10_15_14_13.setGravity(Gravity.CENTER);
			txt_12_11_10_15_14_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_12_11_10_15_14_13.setBackgroundResource(0);
			
			txt_15_14_13=(TextView) main_yellow_layout.findViewById(R.id.txt_15_14_13);
			txt_15_14_13.setClickable(true);
			txt_15_14_13.setOnClickListener(this);
			txt_15_14_13.setText("0");
			txt_15_14_13.setTextColor(Color.BLACK);
			txt_15_14_13.setGravity(Gravity.CENTER);
			txt_15_14_13.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15_14_13.setBackgroundResource(0);
			
			txt_15_14_13_18_17_16=(TextView) main_yellow_layout.findViewById(R.id.txt_15_14_13_18_17_16);
			txt_15_14_13_18_17_16.setClickable(true);
			txt_15_14_13_18_17_16.setOnClickListener(this);
			txt_15_14_13_18_17_16.setText("0");
			txt_15_14_13_18_17_16.setTextColor(Color.BLACK);
			txt_15_14_13_18_17_16.setGravity(Gravity.CENTER);
			txt_15_14_13_18_17_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_15_14_13_18_17_16.setBackgroundResource(0);
			
			txt_18_17_16=(TextView) main_yellow_layout.findViewById(R.id.txt_18_17_16);
			txt_18_17_16.setClickable(true);
			txt_18_17_16.setOnClickListener(this);
			txt_18_17_16.setText("0");
			txt_18_17_16.setTextColor(Color.BLACK);
			txt_18_17_16.setGravity(Gravity.CENTER);
			txt_18_17_16.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18_17_16.setBackgroundResource(0);
			
			txt_18_17_16_21_20_19=(TextView) main_yellow_layout.findViewById(R.id.txt_18_17_16_21_20_19);
			txt_18_17_16_21_20_19.setClickable(true);
			txt_18_17_16_21_20_19.setOnClickListener(this);
			txt_18_17_16_21_20_19.setText("0");
			txt_18_17_16_21_20_19.setTextColor(Color.BLACK);
			txt_18_17_16_21_20_19.setGravity(Gravity.CENTER);
			txt_18_17_16_21_20_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_18_17_16_21_20_19.setBackgroundResource(0);
			
			txt_21_20_19=(TextView) main_yellow_layout.findViewById(R.id.txt_21_20_19);
			txt_21_20_19.setClickable(true);
			txt_21_20_19.setOnClickListener(this);
			txt_21_20_19.setText("0");
			txt_21_20_19.setTextColor(Color.BLACK);
			txt_21_20_19.setGravity(Gravity.CENTER);
			txt_21_20_19.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21_20_19.setBackgroundResource(0);
			
			txt_21_20_19_24_23_22=(TextView) main_yellow_layout.findViewById(R.id.txt_21_20_19_24_23_22);
			txt_21_20_19_24_23_22.setClickable(true);
			txt_21_20_19_24_23_22.setOnClickListener(this);
			txt_21_20_19_24_23_22.setText("0");
			txt_21_20_19_24_23_22.setTextColor(Color.BLACK);
			txt_21_20_19_24_23_22.setGravity(Gravity.CENTER);
			txt_21_20_19_24_23_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_21_20_19_24_23_22.setBackgroundResource(0);
			
			txt_24_23_22=(TextView) main_yellow_layout.findViewById(R.id.txt_24_23_22);
			txt_24_23_22.setClickable(true);
			txt_24_23_22.setOnClickListener(this);
			txt_24_23_22.setText("0");
			txt_24_23_22.setTextColor(Color.BLACK);
			txt_24_23_22.setGravity(Gravity.CENTER);
			txt_24_23_22.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24_23_22.setBackgroundResource(0);
			
			txt_24_23_22_27_26_25=(TextView) main_yellow_layout.findViewById(R.id.txt_24_23_22_27_26_25);
			txt_24_23_22_27_26_25.setClickable(true);
			txt_24_23_22_27_26_25.setOnClickListener(this);
			txt_24_23_22_27_26_25.setText("0");
			txt_24_23_22_27_26_25.setTextColor(Color.BLACK);
			txt_24_23_22_27_26_25.setGravity(Gravity.CENTER);
			txt_24_23_22_27_26_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_24_23_22_27_26_25.setBackgroundResource(0);
			
			txt_27_26_25=(TextView) main_yellow_layout.findViewById(R.id.txt_27_26_25);
			txt_27_26_25.setClickable(true);
			txt_27_26_25.setOnClickListener(this);
			txt_27_26_25.setText("0");
			txt_27_26_25.setTextColor(Color.BLACK);
			txt_27_26_25.setGravity(Gravity.CENTER);
			txt_27_26_25.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27_26_25.setBackgroundResource(0);
			
			txt_27_26_25_30_29_28=(TextView) main_yellow_layout.findViewById(R.id.txt_27_26_25_30_29_28);
			txt_27_26_25_30_29_28.setClickable(true);
			txt_27_26_25_30_29_28.setOnClickListener(this);
			txt_27_26_25_30_29_28.setText("0");
			txt_27_26_25_30_29_28.setTextColor(Color.BLACK);
			txt_27_26_25_30_29_28.setGravity(Gravity.CENTER);
			txt_27_26_25_30_29_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_27_26_25_30_29_28.setBackgroundResource(0);
			
			txt_30_29_28=(TextView) main_yellow_layout.findViewById(R.id.txt_30_29_28);
			txt_30_29_28.setClickable(true);
			txt_30_29_28.setOnClickListener(this);
			txt_30_29_28.setText("0");
			txt_30_29_28.setTextColor(Color.BLACK);
			txt_30_29_28.setGravity(Gravity.CENTER);
			txt_30_29_28.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30_29_28.setBackgroundResource(0);
			
			txt_30_29_28_33_32_31=(TextView) main_yellow_layout.findViewById(R.id.txt_30_29_28_33_32_31);
			txt_30_29_28_33_32_31.setClickable(true);
			txt_30_29_28_33_32_31.setOnClickListener(this);
			txt_30_29_28_33_32_31.setText("0");
			txt_30_29_28_33_32_31.setTextColor(Color.BLACK);
			txt_30_29_28_33_32_31.setGravity(Gravity.CENTER);
			txt_30_29_28_33_32_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_30_29_28_33_32_31.setBackgroundResource(0);
			
			txt_33_32_31=(TextView) main_yellow_layout.findViewById(R.id.txt_33_32_31);
			txt_33_32_31.setClickable(true);
			txt_33_32_31.setOnClickListener(this);
			txt_33_32_31.setText("0");
			txt_33_32_31.setTextColor(Color.BLACK);
			txt_33_32_31.setGravity(Gravity.CENTER);
			txt_33_32_31.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33_32_31.setBackgroundResource(0);
			
			txt_33_32_31_36_35_34=(TextView) main_yellow_layout.findViewById(R.id.txt_33_32_31_36_35_34);
			txt_33_32_31_36_35_34.setClickable(true);
			txt_33_32_31_36_35_34.setOnClickListener(this);
			txt_33_32_31_36_35_34.setText("0");
			txt_33_32_31_36_35_34.setTextColor(Color.BLACK);
			txt_33_32_31_36_35_34.setGravity(Gravity.CENTER);
			txt_33_32_31_36_35_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_33_32_31_36_35_34.setBackgroundResource(0);
			
			txt_36_35_34=(TextView) main_yellow_layout.findViewById(R.id.txt_36_35_34);
			txt_36_35_34.setClickable(true);
			txt_36_35_34.setOnClickListener(this);
			txt_36_35_34.setText("0");
			txt_36_35_34.setTextColor(Color.BLACK);
			txt_36_35_34.setGravity(Gravity.CENTER);
			txt_36_35_34.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_36_35_34.setBackgroundResource(0);
	/*  textboxes A2,B2,C2,.....W2,X2 ends here */
					
					
					
			txt_1st12=(TextView) main_yellow_layout.findViewById(R.id.txt_1st12);
			txt_1st12.setClickable(true);
			txt_1st12.setOnClickListener(this);
			txt_1st12.setText("0");
			txt_1st12.setTextColor(Color.TRANSPARENT);
			txt_1st12.setGravity(Gravity.CENTER);
			txt_1st12.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_1st12.setBackgroundResource(0);
			
			txt_2nd12=(TextView) main_yellow_layout.findViewById(R.id.txt_2nd12);
			txt_2nd12.setClickable(true);
			txt_2nd12.setOnClickListener(this);
			txt_2nd12.setText("0");
			txt_2nd12.setTextColor(Color.TRANSPARENT);
			txt_2nd12.setGravity(Gravity.CENTER);
			txt_2nd12.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_2nd12.setBackgroundResource(0);
			
			txt_3rd12=(TextView) main_yellow_layout.findViewById(R.id.txt_3rd12);
			txt_3rd12.setClickable(true);
			txt_3rd12.setOnClickListener(this);
			txt_3rd12.setText("0");
			txt_3rd12.setTextColor(Color.TRANSPARENT);
			txt_3rd12.setGravity(Gravity.CENTER);
			txt_3rd12.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_3rd12.setBackgroundResource(0);
			
			txt_1to18=(TextView) main_yellow_layout.findViewById(R.id.txt_1to18);
			txt_1to18.setClickable(true);
			txt_1to18.setOnClickListener(this);
			txt_1to18.setText("0");
			txt_1to18.setTextColor(Color.TRANSPARENT);
			txt_1to18.setGravity(Gravity.CENTER);
			txt_1to18.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_1to18.setBackgroundResource(0);
			
			txt_Even=(TextView) main_yellow_layout.findViewById(R.id.txt_Even);
			txt_Even.setClickable(true);
			txt_Even.setOnClickListener(this);
			txt_Even.setText("0");
			txt_Even.setTextColor(Color.TRANSPARENT);
			txt_Even.setGravity(Gravity.CENTER);
			//txt_Even.setPadding(20, 10, 10, 0);
			txt_Even.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_Even.setBackgroundResource(0);
			
			txt_Red=(TextView) main_yellow_layout.findViewById(R.id.txt_Red);
			txt_Red.setClickable(true);
			txt_Red.setOnClickListener(this);
			txt_Red.setText("0");
			txt_Red.setTextColor(Color.TRANSPARENT);
			txt_Red.setGravity(Gravity.CENTER);
			txt_Red.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_Red.setBackgroundResource(0);
			
			txt_Black=(TextView) main_yellow_layout.findViewById(R.id.txt_Black);
			txt_Black.setClickable(true);
			txt_Black.setOnClickListener(this);
			txt_Black.setText("0");
			txt_Black.setTextColor(Color.TRANSPARENT);
			txt_Black.setGravity(Gravity.CENTER);
			txt_Black.setTextSize(getResources().getDimension(R.dimen.textsize));
			txt_Black.setBackgroundResource(0);
			
			txt_Odd=(TextView) main_yellow_layout.findViewById(R.id.txt_Odd);
			txt_Odd.setClickable(true);
			txt_Odd.setOnClickListener(this);
			txt_Odd.setText("0");
			txt_Odd.setTextColor(Color.TRANSPARENT);
			txt_Odd.setGravity(Gravity.CENTER);
		    txt_Odd.setTextSize(getResources().getDimension(R.dimen.textsize));
		    txt_Odd.setBackgroundResource(0);
		    
			txt_19to36=(TextView) main_yellow_layout.findViewById(R.id.txt_19to36);
			txt_19to36.setClickable(true);
			txt_19to36.setOnClickListener(this);
			txt_19to36.setText("0");
			txt_19to36.setTextColor(Color.TRANSPARENT);
	        txt_19to36.setGravity(Gravity.CENTER);
	       // txt_19to36.setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.textsize));
	        txt_19to36.setTextSize(getResources().getDimension(R.dimen.textsize));
	        txt_19to36.setBackgroundResource(0);
}

     public void reset_all_bet_text_boxes()
     {
    	 Is_first2to1_Checked=false;
    	 Is_second2to1_Checked=false;
    	 Is_third2to1_Checked=false;
    	 Is_1st12_Checked=false;
    	 Is_2nd12_Checked=false;
    	 Is_3rd12_Checked=false;
    	 Is_1to18_Checked=false;
    	 Is_Even_Checked=false;
    	 Is_Red_Checked=false;
    	 Is_Black_Checked=false;
    	 Is_Odd_Checked=false;
    	 Is_19to36_Checked=false;

	  
    	/* texboxes i,ii,iii,....xxiii,xxiv starts here  */
			 
			 
			
			
			 
			 
			 
			 
			 txt_00.setVisibility(Color.RED);
			 txt_0.setVisibility(Color.RED);
			 txt_00_0.setTextColor(Color.TRANSPARENT);
           	 txt_00_3.setTextColor(Color.TRANSPARENT);
			txt_3.setVisibility(Color.TRANSPARENT);
			  txt_3_6.setTextColor(Color.TRANSPARENT);
			txt_6.setVisibility(Color.TRANSPARENT);
			  txt_6_9.setTextColor(Color.TRANSPARENT);
			txt_9.setVisibility(Color.TRANSPARENT);
			  txt_9_12.setTextColor(Color.TRANSPARENT);
			txt_12.setVisibility(Color.TRANSPARENT);
			  txt_12_15.setTextColor(Color.TRANSPARENT);
			txt_15.setVisibility(Color.TRANSPARENT);
			  txt_15_18.setTextColor(Color.TRANSPARENT);
			txt_18.setVisibility(Color.TRANSPARENT);
			  txt_18_21.setTextColor(Color.TRANSPARENT);
			txt_21.setVisibility(Color.TRANSPARENT);
			  txt_21_24.setTextColor(Color.TRANSPARENT);
			txt_24.setVisibility(Color.TRANSPARENT);
			  txt_24_27.setTextColor(Color.TRANSPARENT);
			txt_27.setVisibility(Color.TRANSPARENT);
			  txt_27_30.setTextColor(Color.TRANSPARENT);
			txt_30.setVisibility(Color.TRANSPARENT);
			  txt_30_33.setTextColor(Color.TRANSPARENT);
			txt_33.setVisibility(Color.TRANSPARENT);
			  txt_33_36.setTextColor(Color.TRANSPARENT);
			txt_36.setVisibility(Color.TRANSPARENT);
			  txt_first2to1.setTextColor(Color.TRANSPARENT);
	/* texboxes i,ii,iii,....xxiii,xxiv starts here  */
	
			
	/* textboxes A,B,C,......W,X starts here  */
			txt_00_3_2.setTextColor(Color.TRANSPARENT);
			txt_3_2.setTextColor(Color.TRANSPARENT);
			txt_3_6_2_5.setTextColor(Color.TRANSPARENT);
			txt_6_5.setTextColor(Color.TRANSPARENT);
			txt_6_9_5_8.setTextColor(Color.TRANSPARENT);
			txt_9_8.setTextColor(Color.TRANSPARENT);
			txt_9_12_8_11.setTextColor(Color.TRANSPARENT);
			txt_12_11.setTextColor(Color.TRANSPARENT);
			txt_12_15_11_14.setTextColor(Color.TRANSPARENT);
			txt_15_14.setTextColor(Color.TRANSPARENT);
			txt_15_18_14_17.setTextColor(Color.TRANSPARENT);
			txt_18_17.setTextColor(Color.TRANSPARENT);
			txt_18_21_17_20.setTextColor(Color.TRANSPARENT);
			txt_21_20.setTextColor(Color.TRANSPARENT);
			txt_21_24_20_23.setTextColor(Color.TRANSPARENT);
			txt_24_23.setTextColor(Color.TRANSPARENT);
			txt_24_27_23_26.setTextColor(Color.TRANSPARENT);
			txt_27_26.setTextColor(Color.TRANSPARENT);
			txt_27_30_26_29.setTextColor(Color.TRANSPARENT);
			txt_30_29.setTextColor(Color.TRANSPARENT);
			txt_30_33_29_32.setTextColor(Color.TRANSPARENT);
			txt_33_32.setTextColor(Color.TRANSPARENT);
			txt_33_36_32_35.setTextColor(Color.TRANSPARENT);
			txt_36_35.setTextColor(Color.TRANSPARENT);
	/* textboxes A,B,C,......W,X ends here  */
	
	/* textboxes a,b,c.....w,x starts here  */
			txt_00_0_2.setTextColor(Color.TRANSPARENT);
			txt_2.setVisibility(Color.TRANSPARENT);
			txt_2_5.setTextColor(Color.TRANSPARENT);
			txt_5.setVisibility(Color.TRANSPARENT);
			txt_5_8.setTextColor(Color.TRANSPARENT);
			txt_8.setVisibility(Color.TRANSPARENT);
			txt_8_11.setTextColor(Color.TRANSPARENT);
			txt_11.setVisibility(Color.TRANSPARENT);
			txt_11_14.setTextColor(Color.TRANSPARENT);
			txt_14.setVisibility(Color.TRANSPARENT);
			txt_14_17.setTextColor(Color.TRANSPARENT);
			txt_17.setVisibility(Color.TRANSPARENT);
			txt_17_20.setTextColor(Color.TRANSPARENT);
			txt_20.setVisibility(Color.TRANSPARENT);
			txt_20_23.setTextColor(Color.TRANSPARENT);
			txt_23.setVisibility(Color.TRANSPARENT);
			txt_23_26.setTextColor(Color.TRANSPARENT);
			txt_26.setVisibility(Color.TRANSPARENT);
			txt_26_29.setTextColor(Color.TRANSPARENT);
			txt_29.setVisibility(Color.TRANSPARENT);
			txt_29_32.setTextColor(Color.TRANSPARENT);
			txt_32.setVisibility(Color.TRANSPARENT);
			txt_32_35.setTextColor(Color.TRANSPARENT);
			txt_35.setVisibility(Color.TRANSPARENT);
			txt_second2to1.setTextColor(Color.TRANSPARENT);
	/* textboxes a,b,c.....w,x ends here  */
	
	
	/* textboxes A1,B1,C1,......W1,X1 starts here   */
			txt_0_2_1.setTextColor(Color.TRANSPARENT);
			txt_2_1.setTextColor(Color.TRANSPARENT);
			txt_2_5_1_4.setTextColor(Color.TRANSPARENT);
			txt_5_4.setTextColor(Color.TRANSPARENT);
			txt_5_8_4_7.setTextColor(Color.TRANSPARENT);
			txt_8_7.setTextColor(Color.TRANSPARENT);
			txt_8_11_7_10.setTextColor(Color.TRANSPARENT);
			txt_11_10.setTextColor(Color.TRANSPARENT);
			txt_11_14_10_13.setTextColor(Color.TRANSPARENT);
			txt_14_13.setTextColor(Color.TRANSPARENT);
			txt_14_17_13_16.setTextColor(Color.TRANSPARENT);
			txt_17_16.setTextColor(Color.TRANSPARENT);
			txt_17_20_16_19.setTextColor(Color.TRANSPARENT);
			txt_20_19.setTextColor(Color.TRANSPARENT);
			txt_20_23_19_22.setTextColor(Color.TRANSPARENT);
			txt_23_22.setTextColor(Color.TRANSPARENT);
			txt_23_26_22_25.setTextColor(Color.TRANSPARENT);
			txt_26_25.setTextColor(Color.TRANSPARENT);
			txt_26_29_25_28.setTextColor(Color.TRANSPARENT);
			txt_29_28.setTextColor(Color.TRANSPARENT);
			txt_29_32_28_31.setTextColor(Color.TRANSPARENT);
			txt_32_31.setTextColor(Color.TRANSPARENT);
			txt_32_35_31_34.setTextColor(Color.TRANSPARENT);
			txt_35_34.setTextColor(Color.TRANSPARENT);
	/* textboxes A1,B1,C1,......W1,X1 starts here   */
	
	
	/*  textboxes a1,b1,c1,.....w1,x1 starts here  */
			txt_0_1.setTextColor(Color.TRANSPARENT);
			txt_1.setVisibility(Color.TRANSPARENT);
			txt_1_4.setTextColor(Color.TRANSPARENT);
			txt_4.setVisibility(Color.TRANSPARENT);
			txt_4_7.setTextColor(Color.TRANSPARENT);
			txt_7.setVisibility(Color.TRANSPARENT);
			txt_7_10.setTextColor(Color.TRANSPARENT);
			txt_10.setVisibility(Color.TRANSPARENT);
			txt_10_13.setTextColor(Color.TRANSPARENT);
			txt_13.setVisibility(Color.TRANSPARENT);
			txt_13_16.setTextColor(Color.TRANSPARENT);
			txt_16.setVisibility(Color.TRANSPARENT);
			txt_16_19.setTextColor(Color.TRANSPARENT);
			txt_19.setVisibility(Color.TRANSPARENT);
			txt_19_22.setTextColor(Color.TRANSPARENT);
			txt_22.setVisibility(Color.TRANSPARENT);
			txt_22_25.setTextColor(Color.TRANSPARENT);
			txt_25.setVisibility(Color.TRANSPARENT);
			txt_25_28.setTextColor(Color.TRANSPARENT);
			txt_28.setVisibility(Color.TRANSPARENT);
			txt_28_31.setTextColor(Color.TRANSPARENT);
			txt_31.setVisibility(Color.TRANSPARENT);
			txt_31_34.setTextColor(Color.TRANSPARENT);
			txt_34.setVisibility(Color.TRANSPARENT);
			txt_third2to1.setTextColor(Color.TRANSPARENT);
	/*  textboxes a1,b1,c1,.....w1,x1 ends here  */
			
   /*  textboxes A2,B2,C2,.....W2,X2 starts here */
			//txt_00_0_3_2_1.setTextColor(Color.TRANSPARENT);
			txt_3_2_1.setTextColor(Color.TRANSPARENT);
			txt_3_2_1_6_5_4.setTextColor(Color.TRANSPARENT);
			txt_6_5_4.setTextColor(Color.TRANSPARENT);
			txt_6_5_4_9_8_7.setTextColor(Color.TRANSPARENT);
			txt_9_8_7.setTextColor(Color.TRANSPARENT);
			txt_9_8_7_12_11_10.setTextColor(Color.TRANSPARENT);
			txt_12_11_10.setTextColor(Color.TRANSPARENT);
			txt_12_11_10_15_14_13.setTextColor(Color.TRANSPARENT);
			txt_15_14_13.setTextColor(Color.TRANSPARENT);
			txt_15_14_13_18_17_16.setTextColor(Color.TRANSPARENT);
			txt_18_17_16.setTextColor(Color.TRANSPARENT);
			txt_18_17_16_21_20_19.setTextColor(Color.TRANSPARENT);
			txt_21_20_19.setTextColor(Color.TRANSPARENT);
			txt_21_20_19_24_23_22.setTextColor(Color.TRANSPARENT);
			txt_24_23_22.setTextColor(Color.TRANSPARENT);
			txt_24_23_22_27_26_25.setTextColor(Color.TRANSPARENT);
			txt_27_26_25.setTextColor(Color.TRANSPARENT);
			txt_27_26_25_30_29_28.setTextColor(Color.TRANSPARENT);
			txt_30_29_28.setTextColor(Color.TRANSPARENT);
			txt_30_29_28_33_32_31.setTextColor(Color.TRANSPARENT);
			txt_33_32_31.setTextColor(Color.TRANSPARENT);
			txt_33_32_31_36_35_34.setTextColor(Color.TRANSPARENT);
			txt_36_35_34.setTextColor(Color.TRANSPARENT);
	/*  textboxes A2,B2,C2,.....W2,X2 ends here */
					
			txt_1st12.setTextColor(Color.TRANSPARENT);
			txt_2nd12.setTextColor(Color.TRANSPARENT);
			txt_3rd12.setTextColor(Color.TRANSPARENT);
			txt_1to18.setTextColor(Color.TRANSPARENT);
			txt_Even.setTextColor(Color.TRANSPARENT);
			txt_Red.setTextColor(Color.TRANSPARENT);
			txt_Black.setTextColor(Color.TRANSPARENT);
			txt_Odd.setTextColor(Color.TRANSPARENT);
		    txt_19to36.setTextColor(Color.TRANSPARENT);
	    
 }

     public void calculate_total_bet()
     {  	 
    	int arl_length=arl.size();
    	for(int i=0;i<arl_length;i++)
    	{	    		
    		String mm=arl.get(i).getString();
            int mm_value=arl.get(i).getInt();
            
	    	String nn=mm;
	    	String[] parts = mm.split("_");
	    	String extractedResult = "";
	    	for(int j=1;j<parts.length;j++){
	    			extractedResult += parts[j] + " "; // prasad_hv is captured here.
	    			String bet_key=parts[j];
	    			int bet_key_individual_weightage=mm_value;
	    			if(bet_key_individual_weightage>0)
	    					Prepare_Bets_Array(bet_key,bet_key_individual_weightage);
	    	    }
    	}
    	 
    
     }
     
     
     
     
     public int Prepare_Bets_Array(String bet_key,int bet_key_individual_weightage)
     {
    	 switch(bet_key){
    	 
		    	 case "00":
		    		 bet_Values[37]+=bet_key_individual_weightage;
		    		 break;
		    	 case "0":
		    		 bet_Values[0]+=bet_key_individual_weightage;
		    		 break;
		    	 case "1":
		    		 bet_Values[1]+=bet_key_individual_weightage;
		    		 break;
		    	 case "2":
		    		 bet_Values[2]+=bet_key_individual_weightage;
		    		 break;
		    	 case "3":
		    		 bet_Values[3]+=bet_key_individual_weightage;
		    		 break;
		    	 case "4":
		    		 bet_Values[4]+=bet_key_individual_weightage;
		    		 break;
		    	 case "5":
		    		 bet_Values[5]+=bet_key_individual_weightage;
		    		 break;
		    	 case "6":
		    		 bet_Values[6]+=bet_key_individual_weightage;
		    		 break;
		    	 case "7":
		    		 bet_Values[7]+=bet_key_individual_weightage;
		    		 break;
		    	 case "8":
		    		 bet_Values[8]+=bet_key_individual_weightage;
		    		 break;
		    	 case "9":
		    		 bet_Values[9]+=bet_key_individual_weightage;
		    		 break;
		    	 case "10":
		    		 bet_Values[10]+=bet_key_individual_weightage;
		    		 break;
		    	 case "11":
		    		 bet_Values[11]+=bet_key_individual_weightage;
		    		 break;
		    	 case "12":
		    		 bet_Values[12]+=bet_key_individual_weightage;
		    		 break;
		    	 case "13":
		    		 bet_Values[13]+=bet_key_individual_weightage;
		    		 break;
		    	 case "14":
		    		 bet_Values[14]+=bet_key_individual_weightage;
		    		 break;
		    	 case "15":
		    		 bet_Values[15]+=bet_key_individual_weightage;
		    		 break;
		    	 case "16":
		    		 bet_Values[16]+=bet_key_individual_weightage;
		    		 break;
		    	 case "17":
		    		 bet_Values[17]+=bet_key_individual_weightage;
		    		 break;
		    	 case "18":
		    		 bet_Values[18]+=bet_key_individual_weightage;
		    		 break;
		    	 case "19":
		    		 bet_Values[19]+=bet_key_individual_weightage;
		    		 break;
		    	 case "20":
		    		 bet_Values[20]+=bet_key_individual_weightage;
		    		 break;
		    	 case "21":
		    		 bet_Values[21]+=bet_key_individual_weightage;
		    		 break;
		    	 case "22":
		    		 bet_Values[22]+=bet_key_individual_weightage;
		    		 break;
		    	 case "23":
		    		 bet_Values[23]+=bet_key_individual_weightage;
		    		 break;
		    	 case "24":
		    		 bet_Values[24]+=bet_key_individual_weightage;
		    		 break;
		    	 case "25":
		    		 bet_Values[25]+=bet_key_individual_weightage;
		    		 break;
		    	 case "26":
		    		 bet_Values[26]+=bet_key_individual_weightage;
		    		 break;
		    	 case "27":
		    		 bet_Values[27]+=bet_key_individual_weightage;
		    		 break;
		    	 case "28":
		    		 bet_Values[28]+=bet_key_individual_weightage;
		    		 break;
		    	 case "29":
		    		 bet_Values[29]+=bet_key_individual_weightage;
		    		 break;
		    	 case "30":
		    		 bet_Values[30]+=bet_key_individual_weightage;
		    		 break;
		    	 case "31":
		    		 bet_Values[31]+=bet_key_individual_weightage;
		    		 break;
		    	 case "32":
		    		 bet_Values[32]+=bet_key_individual_weightage;
		    		 break;
		    	 case "33":
		    		 bet_Values[33]+=bet_key_individual_weightage;
		    		 break;
		    	 case "34":
		    		 bet_Values[34]+=bet_key_individual_weightage;
		    		 break;
		    	 case "35":
		    		 bet_Values[35]+=bet_key_individual_weightage;
		    		 break;
		    	 case "36":
		    		 bet_Values[36]+=bet_key_individual_weightage;
		    		 break;
		    	
		    	 case "1st12":			    		 
                     for(int i=1;i<=12;i++) { bet_Values[i]+=(bet_key_individual_weightage/12); }
		    		 break;
		    	 case "2nd12":
                     for(int i=13;i<=24;i++){ bet_Values[i]+=(bet_key_individual_weightage/12); }
		    		 break;
		    	 case "3rd12":
                     for(int i=25;i<=36;i++){ bet_Values[i]+=(bet_key_individual_weightage/12); }
		    		 break;
		    	 case "1to18":
                     for(int i=1;i<=18;i++) { bet_Values[i]+=(bet_key_individual_weightage/18); }
		    		 break;
		    	 case "Even":
	                    for(int i=1;i<=36;i++) {
	                    	       if(i%2==0){	bet_Values[i]+=(bet_key_individual_weightage/18); }
	                             }
			    		 break;
		    	 case "Red":
                     int red_ids_length=red_Ids.length;
                     for(int i=0;i<red_ids_length;i++){ int red_id_pointer=red_Ids[i]; bet_Values[red_id_pointer]+=(bet_key_individual_weightage/18); }
		    		 break;
		    	 case "Black":
		    		 int black_ids_length=black_Ids.length;
                     for(int i=0;i<black_ids_length;i++){ int black_id_pointer=black_Ids[i]; bet_Values[black_id_pointer]+=(bet_key_individual_weightage/18); }
		    		 break;
		    	 case "Odd":
	                    for(int i=1;i<=36;i++) {
                    	       if(i%2!=0){	bet_Values[i]+=(bet_key_individual_weightage/18); }
                             }	 
		    		 break;
		    	 case "19to36":
                     for(int i=19;i<=36;i++){ bet_Values[i]+=(bet_key_individual_weightage/18);  }
		    		 break;
		    	 case "first2to1":
                     for(int i=3;i<=36;i=i+3){bet_Values[i]+=(bet_key_individual_weightage/12); }			    		  
		    		 break;
		    	 case "second2to1":
                     for(int i=2;i<=35;i=i+3){ bet_Values[i]+=(bet_key_individual_weightage/12); }			    		 
		    		 break;
		    	 case "third2to1":
                     for(int i=1;i<=34;i=i+3)  {  bet_Values[i]+=(bet_key_individual_weightage/12);   }
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
		
			
	
				 Prepare_New_Bet();
				
				 //int current_score_on_txtscore=Integer.parseInt(txt_score.getText().toString());//balancetochange
				 int current_score_on_txtscore=GlobalClass.getBalance();//balancetochange
				   if(current_score_on_txtscore- current_bet_amount>0)
				   {
					   int new_score=current_score_on_txtscore- current_bet_amount;
					   txt_score.setText(String.valueOf(new_score));
					   FunRouletteMessages.total_Bet_Amount_On_Current_Game+=current_bet_amount;
					   
					   txt_bottom_won_amount.setText(String.valueOf(FunRouletteMessages.total_Bet_Amount_On_Current_Game));
					   
					   GlobalClass.setBalance(new_score);
					   findViewById(v.getId()).setBackgroundResource(R.drawable.betbg);
					   			Update_bet_labels(v);
					   			
								Update_Bet_Taken_List(name);
			       }
				   else
				   {
						Toast.makeText(getApplicationContext(),"Not enough Balance to BET", Toast.LENGTH_SHORT).show();
				   }


				
				
				
				
		Toast.makeText(getApplicationContext(),extractedResult+" "+name, Toast.LENGTH_SHORT).show();
	}

	
	 public void Update_bet_labels(View v)
	 {
		 switch(v.getId())
		   {
		        case R.id.txt_00:
					if(txt_00.getText()=="00")
					{
						//txt_00.setBackgroundResource(R.drawable.betbg);
						txt_00.setText(current_bet_amount+"");
					}    
					else
					{
						//txt_00.setBackgroundResource(R.drawable.betbg);
						int latest_button_text_value=Integer.parseInt(txt_00.getText().toString());
						latest_button_text_value +=current_bet_amount;
					    txt_00.setText(""+ latest_button_text_value  );
					}
					txt_00.setTextColor(Color.BLACK);
					break;

				case R.id.txt_0:
					if(txt_0.getText()=="0")
					{
						//txt_00.setBackgroundResource(R.drawable.betbg);
						txt_0.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_0.getText().toString());
						latest_button_text_value +=current_bet_amount;
					    txt_0.setText(""+ latest_button_text_value  );
					}
					txt_0.setTextColor(Color.BLACK);
					break;
				case R.id.txt_00_0:
					if(txt_00_0.getText()=="0")
					{
						txt_00_0.setText(current_bet_amount+"");
					}
					else
					{						
					    int latest_button_text_value=Integer.parseInt(txt_00_0.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_00_0.setText(""+ latest_button_text_value  );
					}
					txt_00_0.setTextColor(Color.BLACK);
					break;

				case R.id.txt_00_3:
					if(txt_00_3.getText()=="0")
					{
						txt_00_3.setText(current_bet_amount+"");
					}
					else
					{						
					    int latest_button_text_value=Integer.parseInt(txt_00_3.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_00_3.setText(""+ latest_button_text_value  );
					}
					txt_00_3.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3:
					if(txt_3.getText()=="0")
					{
						txt_3.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3.setText(""+ latest_button_text_value  );
					}
					txt_3.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3_6:
					if(txt_3_6.getText()=="0")
					{
						txt_3_6.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3_6.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3_6.setText(""+ latest_button_text_value  );
					}
					txt_3_6.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6:
					if(txt_6.getText()=="0")
					{
						txt_6.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6.setText(""+ latest_button_text_value  );
					}
					txt_6.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6_9:
					if(txt_6_9.getText()=="0")
					{
					    txt_6_9.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6_9.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6_9.setText(""+ latest_button_text_value  );
					}
					txt_6_9.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9:
					if(txt_9.getText()=="0")
					{	
						txt_9.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9.setText(""+ latest_button_text_value  );
					}
					txt_9.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9_12:
					if(txt_9_12.getText()=="0")
					{
						txt_9_12.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9_12.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9_12.setText(""+ latest_button_text_value  );
					}
					txt_9_12.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12:
					if(txt_12.getText()=="0")
					{
						txt_12.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12.setText(""+ latest_button_text_value  );
					}
					txt_12.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12_15:
					if(txt_12_15.getText()=="0")
					{
						txt_12_15.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12_15.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12_15.setText(""+ latest_button_text_value  );
					}
					txt_12_15.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15:
					if(txt_15.getText()=="0")
					{
						txt_15.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15.setText(""+ latest_button_text_value  );
					}
					txt_15.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15_18:
					if(txt_15_18.getText()=="0")
					{
						txt_15_18.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15_18.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15_18.setText(""+ latest_button_text_value  );
					}
					txt_15_18.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18:
					if(txt_18.getText()=="0")
					{
						txt_18.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18.setText(""+ latest_button_text_value  );
					}
					txt_18.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18_21:
					if(txt_18_21.getText()=="0")
					{
						txt_18_21.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18_21.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18_21.setText(""+ latest_button_text_value  );
					}
					txt_18_21.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21:
					if(txt_21.getText()=="0")
					{
						txt_21.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21.setText(""+ latest_button_text_value  );
					}   
					txt_21.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21_24:
					if(txt_21_24.getText()=="0")
					{
						txt_21_24.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21_24.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21_24.setText(""+ latest_button_text_value  );
					}
					txt_21_24.setTextColor(Color.BLACK);
					break;
				case R.id.txt_24:
					if(txt_24.getText()=="0")
					{
						txt_24.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24.setText(""+ latest_button_text_value  );
					}
					txt_24.setTextColor(Color.BLACK);
					break;
				case R.id.txt_24_27:
					if(txt_24_27.getText()=="0")
					{
						txt_24_27.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24_27.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24_27.setText(""+ latest_button_text_value  );
					}
					txt_24_27.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27:
					if(txt_27.getText()=="0")
					{
						txt_27.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27.setText(""+ latest_button_text_value  );
					}
					txt_27.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27_30:
					if(txt_27_30.getText()=="0")
					{
						txt_27_30.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27_30.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27_30.setText(""+ latest_button_text_value  );
					}
					txt_27_30.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30:
					if(txt_30.getText()=="0")
					{
						txt_30.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30.setText(""+ latest_button_text_value  );
					}
					txt_30.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30_33:
					if(txt_30_33.getText()=="0")
					{
						txt_30_33.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30_33.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30_33.setText(""+ latest_button_text_value  );
					}
					txt_30_33.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33:
					if(txt_33.getText()=="0")
					{
						txt_33.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33.setText(""+ latest_button_text_value  );
					}
					txt_33.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33_36:
					if(txt_33_36.getText()=="0")
					{
						txt_33_36.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33_36.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33_36.setText(""+ latest_button_text_value  );
					}
					txt_33_36.setTextColor(Color.BLACK);
					break;
				case R.id.txt_36:
					if(txt_36.getText()=="0")
					{
						txt_36.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_36.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_36.setText(""+ latest_button_text_value  );
					}
					txt_36.setTextColor(Color.BLACK);
					break;
				case R.id.txt_00_3_2:
					if(txt_00_3_2.getText()=="0")
					{
						txt_00_3_2.setText(current_bet_amount+"");
					}
					else
			 		{
						int latest_button_text_value=Integer.parseInt(txt_00_3_2.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_00_3_2.setText(""+ latest_button_text_value  );
					}
					txt_00_3_2.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3_2:
					if(txt_3_2.getText()=="0")
					{
						txt_3_2.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3_2.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3_2.setText(""+ latest_button_text_value  );
					}
					txt_3_2.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3_6_2_5:
					if(txt_3_6_2_5.getText()=="0")
					{
						txt_3_6_2_5.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3_6_2_5.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3_6_2_5.setText(""+ latest_button_text_value  );
					}
					txt_3_6_2_5.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6_5:
					if(txt_6_5.getText()=="0")
					{
						txt_6_5.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6_5.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6_5.setText(""+ latest_button_text_value  );
					}
					txt_6_5.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6_9_5_8:
					if(txt_6_9_5_8.getText()=="0")
					{
						txt_6_9_5_8.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6_9_5_8.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6_9_5_8.setText(""+ latest_button_text_value  );
					}
					txt_6_9_5_8.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9_8:
					if(txt_9_8.getText()=="0")
					{
						txt_9_8.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9_8.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9_8.setText(""+ latest_button_text_value  );
					}
					txt_9_8.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9_12_8_11:
					if(txt_9_12_8_11.getText()=="0")
					{
						txt_9_12_8_11.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9_12_8_11.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9_12_8_11.setText(""+ latest_button_text_value  );
					}
					txt_9_12_8_11.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12_11:
					if(txt_12_11.getText()=="0")
					{
						txt_12_11.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12_11.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12_11.setText(""+ latest_button_text_value  );
					}
					txt_12_11.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12_15_11_14:
					if(txt_12_15_11_14.getText()=="0")
					{
						txt_12_15_11_14.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12_15_11_14.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12_15_11_14.setText(""+ latest_button_text_value  );
					}
					txt_12_15_11_14.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15_14:
					if(txt_15_14.getText()=="0")
					{
						txt_15_14.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15_14.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15_14.setText(""+ latest_button_text_value  );
					}
					txt_15_14.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15_18_14_17:
					if(txt_15_18_14_17.getText()=="0")
					{
						txt_15_18_14_17.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15_18_14_17.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15_18_14_17.setText(""+ latest_button_text_value  );
					}
					txt_15_18_14_17.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18_17:
					if(txt_18_17.getText()=="0")
					{
						txt_18_17.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18_17.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18_17.setText(""+ latest_button_text_value  );
					}
					txt_18_17.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18_21_17_20:
					if(txt_18_21_17_20.getText()=="0")
					{
						txt_18_21_17_20.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18_21_17_20.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18_21_17_20.setText(""+ latest_button_text_value  );
					}
					txt_18_21_17_20.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21_20:
					if(txt_21_20.getText()=="0")
					{
						txt_21_20.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21_20.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21_20.setText(""+ latest_button_text_value  );
					}
					txt_21_20.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21_24_20_23:
					if(txt_21_24_20_23.getText()=="0")
					{
						txt_21_24_20_23.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21_24_20_23.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21_24_20_23.setText(""+ latest_button_text_value  );
					}
					txt_21_24_20_23.setTextColor(Color.BLACK);
					break;
				case R.id.txt_24_23:
					if(txt_24_23.getText()=="0")
					{
						txt_24_23.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24_23.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24_23.setText(""+ latest_button_text_value  );
					}
					txt_24_23.setTextColor(Color.BLACK);
					break;
					
				case R.id.txt_24_27_23_26:
					if(txt_24_27_23_26.getText()=="0")
					{
						txt_24_27_23_26.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24_27_23_26.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24_27_23_26.setText(""+ latest_button_text_value  );
					}
					txt_24_27_23_26.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27_26:
					if(txt_27_26.getText()=="0")
					{
						txt_27_26.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27_26.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27_26.setText(""+ latest_button_text_value  );
					}
					txt_27_26.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27_30_26_29:
					if(txt_27_30_26_29.getText()=="0")
					{
						txt_27_30_26_29.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27_30_26_29.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27_30_26_29.setText(""+ latest_button_text_value  );
					}
					txt_27_30_26_29.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30_29:
					if(txt_30_29.getText()=="0")
					{
						txt_30_29.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30_29.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30_29.setText(""+ latest_button_text_value  );
					}
					txt_30_29.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30_33_29_32:
					if(txt_30_33_29_32.getText()=="0")
					{
						txt_30_33_29_32.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30_33_29_32.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30_33_29_32.setText(""+ latest_button_text_value  );
					}
					txt_30_33_29_32.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33_32:
					if(txt_33_32.getText()=="0")
					{
						txt_33_32.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33_32.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33_32.setText(""+ latest_button_text_value  );
					}
					txt_33_32.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33_36_32_35:
					if(txt_33_36_32_35.getText()=="0")
					{
						txt_33_36_32_35.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33_36_32_35.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33_36_32_35.setText(""+ latest_button_text_value  );
					}
					txt_33_36_32_35.setTextColor(Color.BLACK);
					break;
				case R.id.txt_36_35:
					if(txt_36_35.getText()=="0")
					{
						txt_36_35.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_36_35.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_36_35.setText(""+ latest_button_text_value  );
					}
					txt_36_35.setTextColor(Color.BLACK);
					break;
				case R.id.txt_00_0_2:
					if(txt_00_0_2.getText()=="0")
					{
						txt_00_0_2.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_00_0_2.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_00_0_2.setText(""+ latest_button_text_value  );
					}
					txt_00_0_2.setTextColor(Color.BLACK);
					break;
				case R.id.txt_2:
					if(txt_2.getText()=="0")
					{
						txt_2.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_2.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_2.setText(""+ latest_button_text_value  );
					}
					txt_2.setTextColor(Color.BLACK);
					break;
				case R.id.txt_2_5:
					if(txt_2_5.getText()=="0")
					{
						txt_2_5.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_2_5.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_2_5.setText(""+ latest_button_text_value  );
					}
					txt_2_5.setTextColor(Color.BLACK);
					break;
				case R.id.txt_5:
					if(txt_5.getText()=="0")
					{
						txt_5.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_5.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_5.setText(""+ latest_button_text_value  );
					}
					txt_5.setTextColor(Color.BLACK);
					break;
				case R.id.txt_5_8:
					if(txt_5_8.getText()=="0")
					{
						txt_5_8.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_5_8.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_5_8.setText(""+ latest_button_text_value  );
					}
					txt_5_8.setTextColor(Color.BLACK);
					break;
				case R.id.txt_8:
					if(txt_8.getText()=="0")
					{
						txt_8.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_8.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_8.setText(""+ latest_button_text_value  );
					}
					txt_8.setTextColor(Color.BLACK);
					break;
				case R.id.txt_8_11:
					if(txt_8_11.getText()=="0")
					{
						txt_8_11.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_8_11.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_8_11.setText(""+ latest_button_text_value  );
					}
					txt_8_11.setTextColor(Color.BLACK);
					break;
				case R.id.txt_11:
					if(txt_11.getText()=="0")
					{
						txt_11.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_11.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_11.setText(""+ latest_button_text_value  );
					}
					txt_11.setTextColor(Color.BLACK);
					break;
				case R.id.txt_11_14:
					if(txt_11_14.getText()=="0")
					{
						txt_11_14.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_11_14.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_11_14.setText(""+ latest_button_text_value  );
					}
					txt_11_14.setTextColor(Color.BLACK);
					break;
				case R.id.txt_14:
					if(txt_14.getText()=="0")
					{
						txt_14.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_14.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_14.setText(""+ latest_button_text_value  );
					}
					txt_14.setTextColor(Color.BLACK);
					break;
				case R.id.txt_14_17:
					if(txt_14_17.getText()=="0")
					{
						txt_14_17.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_14_17.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_14_17.setText(""+ latest_button_text_value  );
					}
					txt_14_17.setTextColor(Color.BLACK);
					break;
				case R.id.txt_17:
					if(txt_17.getText()=="0")
					{
						txt_17.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_17.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_17.setText(""+ latest_button_text_value  );
					}
					txt_17.setTextColor(Color.BLACK);
					break;
				case R.id.txt_17_20:
					if(txt_17_20.getText()=="0")
					{
						txt_17_20.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_17_20.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_17_20.setText(""+ latest_button_text_value  );
					}
					txt_17_20.setTextColor(Color.BLACK);
					break;
				case R.id.txt_20:
					if(txt_20.getText()=="0")
					{
						txt_20.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_20.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_20.setText(""+ latest_button_text_value  );
					}
					txt_20.setTextColor(Color.BLACK);
					break;
				case R.id.txt_20_23:
					if(txt_20_23.getText()=="0")
					{
						txt_20_23.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_20_23.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_20_23.setText(""+ latest_button_text_value  );
					}
					txt_20_23.setTextColor(Color.BLACK);
					break;
				case R.id.txt_23:
					if(txt_23.getText()=="0")
					{
						txt_23.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_23.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_23.setText(""+ latest_button_text_value  );
					}
					txt_23.setTextColor(Color.BLACK);
					break;
				case R.id.txt_23_26:
					if(txt_23_26.getText()=="0")
					{
						txt_23_26.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_23_26.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_23_26.setText(""+ latest_button_text_value  );
					}
					txt_23_26.setTextColor(Color.BLACK);
					break;
				case R.id.txt_26:
					if(txt_26.getText()=="0")
					{
						txt_26.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_26.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_26.setText(""+ latest_button_text_value  );
					}
					txt_26.setTextColor(Color.BLACK);
					break;
				case R.id.txt_26_29:
					if(txt_26_29.getText()=="0")
					{
						txt_26_29.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_26_29.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_26_29.setText(""+ latest_button_text_value  );
					}
					txt_26_29.setTextColor(Color.BLACK);
					break;
				case R.id.txt_29:
					if(txt_29.getText()=="0")
					{
						txt_29.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_29.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_29.setText(""+ latest_button_text_value  );
					}
					txt_29.setTextColor(Color.BLACK);
					break;
				case R.id.txt_29_32:
					if(txt_29_32.getText()=="0")
					{
						txt_29_32.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_29_32.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_29_32.setText(""+ latest_button_text_value  );
					}
					txt_29_32.setTextColor(Color.BLACK);
					break;
				case R.id.txt_32:
					if(txt_32.getText()=="0")
					{
						txt_32.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_32.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_32.setText(""+ latest_button_text_value  );
					}
					txt_32.setTextColor(Color.BLACK);
					break;
				case R.id.txt_32_35:
					if(txt_32_35.getText()=="0")
					{
						txt_32_35.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_32_35.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_32_35.setText(""+ latest_button_text_value  );
					}
					txt_32_35.setTextColor(Color.BLACK);
					break;
				case R.id.txt_35:
					if(txt_35.getText()=="0")
					{
						txt_35.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_35.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_35.setText(""+ latest_button_text_value  );
					}
					txt_35.setTextColor(Color.BLACK);
					break;
				case R.id.txt_0_2_1:
					if(txt_0_2_1.getText()=="0")
					{
						txt_0_2_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_0_2_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_0_2_1.setText(""+ latest_button_text_value  );
					}
					txt_0_2_1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_2_1:
					if(txt_2_1.getText()=="0")
					{
						txt_2_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_2_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_2_1.setText(""+ latest_button_text_value  );
					}
					txt_2_1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_2_5_1_4:
					if(txt_2_5_1_4.getText()=="0")
					{
						txt_2_5_1_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_2_5_1_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_2_5_1_4.setText(""+ latest_button_text_value  );
					}
					txt_2_5_1_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_5_4:
					if(txt_5_4.getText()=="0")
					{
						txt_5_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_5_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_5_4.setText(""+ latest_button_text_value  );
					}
					txt_5_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_5_8_4_7:
					if(txt_5_8_4_7.getText()=="0")
					{
						txt_5_8_4_7.setText(current_bet_amount+"");
					}     
					else   
					{
						int latest_button_text_value=Integer.parseInt(txt_5_8_4_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_5_8_4_7.setText(""+ latest_button_text_value  );
					}    
					txt_5_8_4_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_8_7:
					if(txt_8_7.getText()=="0")
					{
						txt_8_7.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_8_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_8_7.setText(""+ latest_button_text_value  );
					}
					txt_8_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_8_11_7_10:
					if(txt_8_11_7_10.getText()=="0")
					{
						txt_8_11_7_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_8_11_7_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_8_11_7_10.setText(""+ latest_button_text_value  );
					}
					txt_8_11_7_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_11_10:
					if(txt_11_10.getText()=="0")
					{
						txt_11_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_11_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_11_10.setText(""+ latest_button_text_value  );
					}
					txt_11_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_11_14_10_13:
					if(txt_11_14_10_13.getText()=="0")
					{
						txt_11_14_10_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_11_14_10_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_11_14_10_13.setText(""+ latest_button_text_value  );
					}
					txt_11_14_10_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_14_13:
					if(txt_14_13.getText()=="0")
					{
						txt_14_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_14_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_14_13.setText(""+ latest_button_text_value  );
					}
					txt_14_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_14_17_13_16:
					if(txt_14_17_13_16.getText()=="0")
					{
						txt_14_17_13_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_14_17_13_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_14_17_13_16.setText(""+ latest_button_text_value  );
					}
					txt_14_17_13_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_17_16:
					if(txt_17_16.getText()=="0")
					{
						txt_17_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_17_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_17_16.setText(""+ latest_button_text_value  );
					}
					txt_17_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_17_20_16_19:
					if(txt_17_20_16_19.getText()=="0")
					{
						txt_17_20_16_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_17_20_16_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_17_20_16_19.setText(""+ latest_button_text_value  );
					}
					txt_17_20_16_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_20_19:
					if(txt_20_19.getText()=="0")
					{
						txt_20_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_20_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_20_19.setText(""+ latest_button_text_value  );
					}
					txt_20_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_20_23_19_22:
					if(txt_20_23_19_22.getText()=="0")
					{
						txt_20_23_19_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_20_23_19_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_20_23_19_22.setText(""+ latest_button_text_value  );
					}
					txt_20_23_19_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_23_22:
					if(txt_23_22.getText()=="0")
					{
						txt_23_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_23_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_23_22.setText(""+ latest_button_text_value  );
					}
					txt_23_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_23_26_22_25:
					if(txt_23_26_22_25.getText()=="0")
					{
						txt_23_26_22_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_23_26_22_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_23_26_22_25.setText(""+ latest_button_text_value  );
					}
					txt_23_26_22_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_26_25:
					if(txt_26_25.getText()=="0")
					{
						txt_26_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_26_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_26_25.setText(""+ latest_button_text_value  );
					}
					txt_26_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_26_29_25_28:
					if(txt_26_29_25_28.getText()=="0")
					{
						txt_26_29_25_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_26_29_25_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_26_29_25_28.setText(""+ latest_button_text_value  );
					}
					txt_26_29_25_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_29_28:
					if(txt_29_28.getText()=="0")
					{
						txt_29_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_29_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_29_28.setText(""+ latest_button_text_value  );
					}
					txt_29_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_29_32_28_31:
					if(txt_29_32_28_31.getText()=="0")
					{
						txt_29_32_28_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_29_32_28_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_29_32_28_31.setText(""+ latest_button_text_value  );
					}
					txt_29_32_28_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_32_31:
					if(txt_32_31.getText()=="0")
					{
						txt_32_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_32_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_32_31.setText(""+ latest_button_text_value  );
					}
					txt_32_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_32_35_31_34:
					if(txt_32_35_31_34.getText()=="0")
					{
						txt_32_35_31_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_32_35_31_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_32_35_31_34.setText(""+ latest_button_text_value  );
					}
					txt_32_35_31_34.setTextColor(Color.BLACK);
					break;
				case R.id.txt_35_34:
					if(txt_35_34.getText()=="0")
					{
						txt_35_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_35_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_35_34.setText(""+ latest_button_text_value  );
					}
					txt_35_34.setTextColor(Color.BLACK);
					break;
				case R.id.txt_0_1:
					if(txt_0_1.getText()=="0")
					{
						txt_0_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_0_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_0_1.setText(""+ latest_button_text_value  );
					}
					txt_0_1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_1:
					if(txt_1.getText()=="0")
					{
						txt_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_1.setText(""+ latest_button_text_value  );
					}
					txt_1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_1_4:
					if(txt_1_4.getText()=="0")
					{
						txt_1_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_1_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_1_4.setText(""+ latest_button_text_value  );
					}
					txt_1_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_4:
					if(txt_4.getText()=="0")
					{
						txt_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_4.setText(""+ latest_button_text_value  );
					}
					txt_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_4_7:
					if(txt_4_7.getText()=="0")
					{
						txt_4_7.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_4_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_4_7.setText(""+ latest_button_text_value  );
					}
					txt_4_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_7:
					if(txt_7.getText()=="0")
					{
						txt_7.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_7.setText(""+ latest_button_text_value  );
					}
					txt_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_7_10:
					if(txt_7_10.getText()=="0")
					{
						txt_7_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_7_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_7_10.setText(""+ latest_button_text_value  );
					}
					txt_7_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_10:
					if(txt_10.getText()=="0")
					{
						txt_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_10.setText(""+ latest_button_text_value  );
					}
					txt_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_10_13:
					if(txt_10_13.getText()=="0")
					{
						txt_10_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_10_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_10_13.setText(""+ latest_button_text_value  );
					}
					txt_10_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_13:
					if(txt_13.getText()=="0")
					{
						txt_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_13.setText(""+ latest_button_text_value  );
					}
					txt_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_13_16:
					if(txt_13_16.getText()=="0")
					{
						txt_13_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_13_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_13_16.setText(""+ latest_button_text_value  );
					}
					txt_13_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_16:
					if(txt_16.getText()=="0")
					{
						txt_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_16.setText(""+ latest_button_text_value  );
					}
					txt_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_16_19:
					if(txt_16_19.getText()=="0")
					{
						txt_16_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_16_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_16_19.setText(""+ latest_button_text_value  );
					}
					txt_16_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_19:
					if(txt_19.getText()=="0")
					{
						txt_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_19.setText(""+ latest_button_text_value  );
					}
					txt_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_19_22:
					if(txt_19_22.getText()=="0")
					{
						txt_19_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_19_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_19_22.setText(""+ latest_button_text_value  );
					}
					txt_19_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_22:
					if(txt_22.getText()=="0")
					{
						txt_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_22.setText(""+ latest_button_text_value  );
					}
					txt_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_22_25:
					if(txt_22_25.getText()=="0")
					{
						txt_22_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_22_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_22_25.setText(""+ latest_button_text_value  );
					}
					txt_22_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_25:
					if(txt_25.getText()=="0")
					{
						txt_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_25.setText(""+ latest_button_text_value  );
					}
					txt_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_25_28:
					if(txt_25_28.getText()=="0")
					{
						txt_25_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_25_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_25_28.setText(""+ latest_button_text_value  );
					}
					txt_25_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_28:
					if(txt_28.getText()=="0")
					{
						txt_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_28.setText(""+ latest_button_text_value  );
					}
					txt_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_28_31:
					if(txt_28_31.getText()=="0")
					{
						txt_28_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_28_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_28_31.setText(""+ latest_button_text_value  );
					}
					txt_28_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_31:
					if(txt_31.getText()=="0")
					{
						txt_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_31.setText(""+ latest_button_text_value  );
					}
					txt_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_31_34:
					if(txt_31_34.getText()=="0")
					{
						txt_31_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_31_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_31_34.setText(""+ latest_button_text_value  );
					}
					txt_31_34.setTextColor(Color.BLACK);
					break;
				case R.id.txt_34:
					if(txt_34.getText()=="0")
					{
						txt_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_34.setText(""+ latest_button_text_value  );
					}
					txt_34.setTextColor(Color.BLACK);
					break;
/*				case R.id.txt_00_0_3_2_1:
					if(txt_00_0_3_2_1.getText()=="0")
					{
						txt_00_0_3_2_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_00_0_3_2_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_00_0_3_2_1.setText(""+ latest_button_text_value  );
					}
					txt_00_0_3_2_1.setTextColor(Color.BLACK);
					break;
*/				case R.id.txt_3_2_1:
					if(txt_3_2_1.getText()=="0")
					{
						txt_3_2_1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3_2_1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3_2_1.setText(""+ latest_button_text_value  );
					}
					txt_3_2_1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3_2_1_6_5_4:
					if(txt_3_2_1_6_5_4.getText()=="0")
					{
						txt_3_2_1_6_5_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3_2_1_6_5_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3_2_1_6_5_4.setText(""+ latest_button_text_value  );
					}
					txt_3_2_1_6_5_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6_5_4:
					if(txt_6_5_4.getText()=="0")
					{
						txt_6_5_4.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6_5_4.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6_5_4.setText(""+ latest_button_text_value  );
					}
					txt_6_5_4.setTextColor(Color.BLACK);
					break;
				case R.id.txt_6_5_4_9_8_7:
					if(txt_6_5_4_9_8_7.getText()=="0")
					{
						txt_6_5_4_9_8_7.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_6_5_4_9_8_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_6_5_4_9_8_7.setText(""+ latest_button_text_value  );
					}
					txt_6_5_4_9_8_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9_8_7:
					if(txt_9_8_7.getText()=="0")
					{
						txt_9_8_7.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9_8_7.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9_8_7.setText(""+ latest_button_text_value  );
					}
					txt_9_8_7.setTextColor(Color.BLACK);
					break;
				case R.id.txt_9_8_7_12_11_10:
					if(txt_9_8_7_12_11_10.getText()=="0")
					{
						txt_9_8_7_12_11_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_9_8_7_12_11_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_9_8_7_12_11_10.setText(""+ latest_button_text_value  );
					}
					txt_9_8_7_12_11_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12_11_10:
					if(txt_12_11_10.getText()=="0")
					{
						txt_12_11_10.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12_11_10.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12_11_10.setText(""+ latest_button_text_value  );
					}
					txt_12_11_10.setTextColor(Color.BLACK);
					break;
				case R.id.txt_12_11_10_15_14_13:
					if(txt_12_11_10_15_14_13.getText()=="0")
					{
						txt_12_11_10_15_14_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_12_11_10_15_14_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_12_11_10_15_14_13.setText(""+ latest_button_text_value  );
					}
					txt_12_11_10_15_14_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15_14_13:
					if(txt_15_14_13.getText()=="0")
					{
						txt_15_14_13.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15_14_13.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15_14_13.setText(""+ latest_button_text_value  );
					}
					txt_15_14_13.setTextColor(Color.BLACK);
					break;
				case R.id.txt_15_14_13_18_17_16:
					if(txt_15_14_13_18_17_16.getText()=="0")
					{
						txt_15_14_13_18_17_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_15_14_13_18_17_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_15_14_13_18_17_16.setText(""+ latest_button_text_value  );
					}
					txt_15_14_13_18_17_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18_17_16:
					if(txt_18_17_16.getText()=="0")
					{
						txt_18_17_16.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18_17_16.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18_17_16.setText(""+ latest_button_text_value  );
					}
					txt_18_17_16.setTextColor(Color.BLACK);
					break;
				case R.id.txt_18_17_16_21_20_19:
					if(txt_18_17_16_21_20_19.getText()=="0")
					{
						txt_18_17_16_21_20_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_18_17_16_21_20_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_18_17_16_21_20_19.setText(""+ latest_button_text_value  );
					}
					txt_18_17_16_21_20_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21_20_19:
					if(txt_21_20_19.getText()=="0")
					{
						txt_21_20_19.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21_20_19.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21_20_19.setText(""+ latest_button_text_value  );
					}
					txt_21_20_19.setTextColor(Color.BLACK);
					break;
				case R.id.txt_21_20_19_24_23_22:
					if(txt_21_20_19_24_23_22.getText()=="0")
					{
						txt_21_20_19_24_23_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_21_20_19_24_23_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_21_20_19_24_23_22.setText(""+ latest_button_text_value  );
					}
					txt_21_20_19_24_23_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_24_23_22:
					if(txt_24_23_22.getText()=="0")
					{
						txt_24_23_22.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24_23_22.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24_23_22.setText(""+ latest_button_text_value  );
					}
					txt_24_23_22.setTextColor(Color.BLACK);
					break;
				case R.id.txt_24_23_22_27_26_25:
					if(txt_24_23_22_27_26_25.getText()=="0")
					{
						txt_24_23_22_27_26_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_24_23_22_27_26_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_24_23_22_27_26_25.setText(""+ latest_button_text_value  );
					}
					txt_24_23_22_27_26_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27_26_25:
					if(txt_27_26_25.getText()=="0")
					{
						txt_27_26_25.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27_26_25.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27_26_25.setText(""+ latest_button_text_value  );
					}
					txt_27_26_25.setTextColor(Color.BLACK);
					break;
				case R.id.txt_27_26_25_30_29_28:
					if(txt_27_26_25_30_29_28.getText()=="0")
					{
						txt_27_26_25_30_29_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_27_26_25_30_29_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_27_26_25_30_29_28.setText(""+ latest_button_text_value  );
					}
					txt_27_26_25_30_29_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30_29_28:
					if(txt_30_29_28.getText()=="0")
					{
						txt_30_29_28.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30_29_28.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30_29_28.setText(""+ latest_button_text_value  );
					}
					txt_30_29_28.setTextColor(Color.BLACK);
					break;
				case R.id.txt_30_29_28_33_32_31:
					if(txt_30_29_28_33_32_31.getText()=="0")
					{
						txt_30_29_28_33_32_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_30_29_28_33_32_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_30_29_28_33_32_31.setText(""+ latest_button_text_value  );
					}
					txt_30_29_28_33_32_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33_32_31:
					if(txt_33_32_31.getText()=="0")
					{
						txt_33_32_31.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33_32_31.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33_32_31.setText(""+ latest_button_text_value  );
					}
					txt_33_32_31.setTextColor(Color.BLACK);
					break;
				case R.id.txt_33_32_31_36_35_34:
					if(txt_33_32_31_36_35_34.getText()=="0")
					{
						txt_33_32_31_36_35_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_33_32_31_36_35_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_33_32_31_36_35_34.setText(""+ latest_button_text_value  );
					}
					txt_33_32_31_36_35_34.setTextColor(Color.BLACK);
					break;
				case R.id.txt_36_35_34:
					if(txt_36_35_34.getText()=="0")
					{
						txt_36_35_34.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_36_35_34.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_36_35_34.setText(""+ latest_button_text_value  );
					}
					txt_36_35_34.setTextColor(Color.BLACK);
					break;
				case R.id.txt_first2to1:
					if(txt_first2to1.getText()=="0")
					{
						txt_first2to1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_first2to1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_first2to1.setText(""+ latest_button_text_value  );
					}
					txt_first2to1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_second2to1:
					if(txt_second2to1.getText()=="0")
					{
						txt_second2to1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_second2to1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_second2to1.setText(""+ latest_button_text_value  );
					}
					txt_second2to1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_third2to1:
					if(txt_third2to1.getText()=="0")
					{
						txt_third2to1.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_third2to1.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_third2to1.setText(""+ latest_button_text_value  );
					}
					txt_third2to1.setTextColor(Color.BLACK);
					break;
				case R.id.txt_1st12:
					if(txt_1st12.getText()=="0")
					{
						txt_1st12.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_1st12.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_1st12.setText(""+ latest_button_text_value  );
					}
					txt_1st12.setTextColor(Color.BLACK);
					break;
				case R.id.txt_2nd12:
					if(txt_2nd12.getText()=="0")
					{
						txt_2nd12.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_2nd12.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_2nd12.setText(""+ latest_button_text_value  );
					}
					txt_2nd12.setTextColor(Color.BLACK);
					break;
				case R.id.txt_3rd12:
					if(txt_3rd12.getText()=="0")
					{
						txt_3rd12.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_3rd12.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_3rd12.setText(""+ latest_button_text_value  );
					}
					txt_3rd12.setTextColor(Color.BLACK);
					break;
				case R.id.txt_1to18:
					if(txt_1to18.getText()=="0")
					{
						txt_1to18.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_1to18.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_1to18.setText(""+ latest_button_text_value  );
					}
					txt_1to18.setTextColor(Color.BLACK);
					break;
				case R.id.txt_Even:
					if(txt_Even.getText()=="0")
					{
						txt_Even.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_Even.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_Even.setText(""+ latest_button_text_value  );
					}
					txt_Even.setTextColor(Color.BLACK);
					break;
				case R.id.txt_Red:
					if(txt_Red.getText()=="0")
					{
						txt_Red.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_Red.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_Red.setText(""+ latest_button_text_value  );
					}
					txt_Red.setTextColor(Color.BLACK);
					break;
				case R.id.txt_Black:
					if(txt_Black.getText()=="0")
					{
						txt_Black.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_Black.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_Black.setText(""+ latest_button_text_value  );
					}
					txt_Black.setTextColor(Color.BLACK);
					break;
				case R.id.txt_Odd:
					if(txt_Odd.getText()=="0")
					{
						txt_Odd.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_Odd.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_Odd.setText(""+ latest_button_text_value  );
					}
					txt_Odd.setTextColor(Color.BLACK);
					break;
				case R.id.txt_19to36:
					if(txt_19to36.getText()=="0")
					{
						txt_19to36.setText(current_bet_amount+"");
					}
					else
					{
						int latest_button_text_value=Integer.parseInt(txt_19to36.getText().toString());
						latest_button_text_value +=current_bet_amount;
						txt_19to36.setText(""+ latest_button_text_value  );
					}
					txt_19to36.setTextColor(Color.BLACK);
					break;
			}

	 }
	 
	 
	    /*    It's only for internal purpose     */
        void Update_Bet_Taken_List(String keyname) {
			int total_Price_Per_Rupee;
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
            	 total_Price_Per_Rupee = 36 /keys_Last_Visited_count ;	 
            	 
            	 
            	// The Coint (Price ) currenlty selected
                 int bet_Price =  Integer.parseInt(current_text_on_bet_textview);
                 int total_price_per_key = bet_Price * total_Price_Per_Rupee;

   
            // add this total Amount to each key
                 //make a loop to update the list
                 int StringInt_keys_Bet_Taken_length=arl.size();
            	for(int i=0;i<StringInt_keys_Bet_Taken_length;i++)
            	{
            		if(arl.get(i).keybet_name.equalsIgnoreCase(resourceName))
            		{	            			
            			arl.get(i).setString(resourceName);
            			arl.get(i).setInt(total_price_per_key);
            		}
            	}
      
            	
        }

        /* internal class to store bet values on keys in ArrayList<string,int> starts here  */
        public class StringInt_keys_Bet_Taken{
        	         private String keybet_name;
        	         private int keybet_value;

        	public StringInt_keys_Bet_Taken(String o1, int o2){		keybet_name = o1;	keybet_value = o2;       	}

        	public String getString(){ 	return keybet_name;   	}
        	public int getInt(){   	return keybet_value;     	}
        	public void setString(String s){   	this.keybet_name = s;  	}
        	public void setInt(int i){    	this.keybet_value = i;     	}
        	} 
	      /* internal class to store bet values on keys in ArrayList<string,int> ends here  */
        
        public void Prepare_New_Bet()
        {
            // stop blinking the last win number
                      //Stop_Hilighting_Win_Number();
            is_selected_bet_completed = true;
            // check if 
            if (Is_It_New_Game() )
            {	  
            	Reset_StringInt_keys_Bet_Taken();
                //Clear_Last_Game_Betting();
                //Clear_Previous_Bet_Details();
               is_New_Game = false;
               FunRouletteMessages.total_Bet_Amount_On_Current_Game = 0;
            }

            
         Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_OK);
         
        }

       // public static final ArrayList<StringInt_keys_Bet_Taken> arl=new ArrayList<StringInt_keys_Bet_Taken>();
        
        //String[] keybet_names={"txt_00","txt_00_number","txt_00_0","txt_0","txt_0_number","txt_00_3","txt_3_number","txt_3","txt_3_6","txt_6_number","txt_6","txt_6_9","txt_9_number","txt_9","txt_9_12","txt_12_number","txt_12","txt_12_15","txt_15_number","txt_15","txt_15_18","txt_18_number","txt_18","txt_18_21","txt_21_number","txt_21","txt_21_24","txt_24_number","txt_24","txt_24_27","txt_27_number","txt_27","txt_27_30","txt_30_number","txt_30","txt_30_33","txt_33_number","txt_33","txt_33_36","txt_36_number","txt_36","txt_first2to1","txt_00_3_2","txt_3_2","txt_3_6_2_5","txt_6_5","txt_6_9_5_8","txt_9_8","txt_9_12_8_11","txt_12_11","txt_12_15_11_14","txt_15_14","txt_15_18_14_17","txt_18_17","txt_18_21_17_20","txt_21_20","txt_21_24_20_23","txt_24_23","txt_24_27_23_26","txt_27_26","txt_27_30_26_29","txt_30_29","txt_30_33_29_32","txt_33_32","txt_33_36_32_35","txt_36_35","txt_00_0_2","txt_2_number","txt_2","txt_2_5","txt_5_number","txt_5","txt_5_8","txt_8_number","txt_8","txt_8_11","txt_11_number","txt_11","txt_11_14","txt_14_number","txt_14","txt_14_17","txt_17_number","txt_17","txt_17_20","txt_20_number","txt_20","txt_20_23","txt_23_number","txt_23","txt_23_26","txt_26_number","txt_26","txt_26_29","txt_29_number","txt_29","txt_29_32","txt_32_number","txt_32","txt_32_35","txt_35_number","txt_35","txt_second2to1","txt_0_2_1","txt_2_1","txt_2_5_1_4","txt_5_4","txt_5_8_4_7","txt_8_7","txt_8_11_7_10","txt_11_10","txt_11_14_10_13","txt_14_13","txt_14_17_13_16","txt_17_16","txt_17_20_16_19","txt_20_19","txt_20_23_19_22","txt_23_22","txt_23_26_22_25","txt_26_25","txt_26_29_25_28","txt_29_28","txt_29_32_28_31","txt_32_31","txt_32_35_31_34","txt_35_34","txt_0_1","txt_1_number","txt_1","txt_1_4","txt_4_number","txt_4","txt_4_7","txt_7_number","txt_7","txt_7_10","txt_10_number","txt_10","txt_10_13","txt_13_number","txt_13","txt_13_16","txt_16_number","txt_16","txt_16_19","txt_19_number","txt_19","txt_19_22","txt_22_number","txt_22","txt_22_25","txt_25_number","txt_25","txt_25_28","txt_28_number","txt_28","txt_28_31","txt_31_number","txt_31","txt_31_34","txt_34_number","txt_34","txt_third2to1","txt_00_0_3_2_1","txt_3_2_1","txt_3_2_1_6_5_4","txt_6_5_4","txt_6_5_4_9_8_7","txt_9_8_7","txt_9_8_7_12_11_10","txt_12_11_10","txt_12_11_10_15_14_13","txt_15_14_13","txt_15_14_13_18_17_16","txt_18_17_16","txt_18_17_16_21_20_19","txt_21_20_19","txt_21_20_19_24_23_22","txt_24_23_22","txt_24_23_22_27_26_25","txt_27_26_25","txt_27_26_25_30_29_28","txt_30_29_28","txt_30_29_28_33_32_31","txt_33_32_31","txt_33_32_31_36_35_34","txt_36_35_34","txt_1st12","txt_2nd12","txt_3rd12","txt_1to18","txt_Even","txt_Red","txt_Black","txt_Odd","txt_19to36"};
        String[] keybet_names={"txt_00","txt_00_0","txt_0","txt_00_3","txt_3","txt_3_6","txt_6","txt_6_9","txt_9","txt_9_12","txt_12","txt_12_15","txt_15","txt_15_18","txt_18","txt_18_21","txt_21","txt_21_24","txt_24","txt_24_27","txt_27","txt_27_30","txt_30","txt_30_33","txt_33","txt_33_36","txt_36","txt_first2to1","txt_00_3_2","txt_3_2","txt_3_6_2_5","txt_6_5","txt_6_9_5_8","txt_9_8","txt_9_12_8_11","txt_12_11","txt_12_15_11_14","txt_15_14","txt_15_18_14_17","txt_18_17","txt_18_21_17_20","txt_21_20","txt_21_24_20_23","txt_24_23","txt_24_27_23_26","txt_27_26","txt_27_30_26_29","txt_30_29","txt_30_33_29_32","txt_33_32","txt_33_36_32_35","txt_36_35","txt_00_0_2","txt_2","txt_2_5","txt_5","txt_5_8","txt_8","txt_8_11","txt_11","txt_11_14","txt_14","txt_14_17","txt_17","txt_17_20","txt_20","txt_20_23","txt_23","txt_23_26","txt_26","txt_26_29","txt_29","txt_29_32","txt_32","txt_32_35","txt_35","txt_second2to1","txt_0_2_1","txt_2_1","txt_2_5_1_4","txt_5_4","txt_5_8_4_7","txt_8_7","txt_8_11_7_10","txt_11_10","txt_11_14_10_13","txt_14_13","txt_14_17_13_16","txt_17_16","txt_17_20_16_19","txt_20_19","txt_20_23_19_22","txt_23_22","txt_23_26_22_25","txt_26_25","txt_26_29_25_28","txt_29_28","txt_29_32_28_31","txt_32_31","txt_32_35_31_34","txt_35_34","txt_0_1","txt_1","txt_1_4","txt_4","txt_4_7","txt_7","txt_7_10","txt_10","txt_10_13","txt_13","txt_13_16","txt_16","txt_16_19","txt_19","txt_19_22","txt_22","txt_22_25","txt_25","txt_25_28","txt_28","txt_28_31","txt_31","txt_31_34","txt_34","txt_third2to1","txt_00_0_3_2_1","txt_3_2_1","txt_3_2_1_6_5_4","txt_6_5_4","txt_6_5_4_9_8_7","txt_9_8_7","txt_9_8_7_12_11_10","txt_12_11_10","txt_12_11_10_15_14_13","txt_15_14_13","txt_15_14_13_18_17_16","txt_18_17_16","txt_18_17_16_21_20_19","txt_21_20_19","txt_21_20_19_24_23_22","txt_24_23_22","txt_24_23_22_27_26_25","txt_27_26_25","txt_27_26_25_30_29_28","txt_30_29_28","txt_30_29_28_33_32_31","txt_33_32_31","txt_33_32_31_36_35_34","txt_36_35_34","txt_1st12","txt_2nd12","txt_3rd12","txt_1to18","txt_Even","txt_Red","txt_Black","txt_Odd","txt_19to36"};
        public void Reset_StringInt_keys_Bet_Taken()
        {    
            for(int i=0;i<keybet_names.length;i++)
            {
               arl.add(new StringInt_keys_Bet_Taken(keybet_names[i],0));
            }
        }
       
        
        
        
        public boolean Is_It_New_Game()
        {
        	boolean status_is_New_Game=is_New_Game;
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
	
	
	
	
	
 }

    public void Update_Time_2_Start(int seconds)
    {
        CurrentTimer.counter =  seconds;
    }
	
	
	
		public void initialise_BetOk_slider()
		{
			
		    try{
		    /* code to place the slider for BetOKBlink starts here */
		    mWorkerThread_timer_Bet_OK_Blink = new MyWorkerThread_BetOk("myWorkerThread_timer_Bet_OK_Blink");
		    myupdateresults_timer_Bet_OK_Blink = new Runnable() {
		        @Override
		        public void run()
		        {
		        	myhandler_Timer_Bet_OK.post(new Runnable() {
											                        	@Override
											                        	public void run()
											                        	{									                        	
											                        		animateandslideshow_Bet_OK();
											                        	}
		                        									});
		        }
		    };
		    
		    
		    
		    
		    int delay_betOK=1000;//delay for 1 sec
		    int period_betOK=500; //repeat every 4 sec
		    timer_Bet_OK_Blink=new Timer();
		    timer_Bet_OK_Blink.scheduleAtFixedRate(new TimerTask() {
				        				    @Override
				        				    public void run()
				        				    {
				        				        mWorkerThread_timer_Bet_OK_Blink.postTask(myupdateresults_timer_Bet_OK_Blink);
				        				    }
		                              },delay_betOK,period_betOK);
		    /* code to place the slider for BetOk ends here */
		
		
		    }catch(Exception e){
		    	Log.d("ERR2",e.getMessage());
		    }
		
		}

		
		public void initialise_Take_Bet()
		{
	        /* code to place the slider for Take_Bet starts here */
	        mWorkerThread_timer_Take_Bet_Blink = new MyWorkerThread_TakeBet("myWorkerThread_timer_Take_Bet_Blink");
	        myupdateresults_timer_Take_Bet_Blink = new Runnable() {
	            @Override
	            public void run()
	            {
	            	myhandler_Timer_Take_Bet.post(new Runnable() {
											                        	@Override
											                        	public void run()
											                        	{									                        	
											                        		animateandslideshow_Take_Bet();
											                        	}
		                        									});
	            }
	        };
	        
	        
	        
	        
	        int delay_Take_bet=1000;//delay for 1 sec
	        int period_Take_bet=500; //repeat every 4 sec
	        timer_Take_Bet_Blink=new Timer();
	        timer_Take_Bet_Blink.scheduleAtFixedRate(new TimerTask() {
				        				    @Override
				        				    public void run()
				        				    {
				        				        mWorkerThread_timer_Take_Bet_Blink.postTask(myupdateresults_timer_Take_Bet_Blink);
				        				    }
	                                  },delay_Take_bet,period_Take_bet);
	        /* code to place the slider for Take_Bet ends here */

	
			
		}

		
		public void initialise_timelayout_slider()
		{
			
		    try{
		    /* code to place the slider for timelayoutblink starts here */
		    mWorkerThread_timer_timelayout_Blink = new MyWorkerThread_Time("myWorkerThread_timer_timelayout_Blink");
		    myupdateresults_timer_Timelayout_Blink = new Runnable() {
		        @Override
		        public void run()
		        {
		        	myhandler_Timer_TimeLayout.post(new Runnable() {
											                        	@Override
											                        	public void run()
											                        	{									                        	
											                        		animateandslideshow_timelayout();
											                        	}
		                        									});
		        }
		    };
		    
		    
		    
		    
		    int delay_timelayout=1000;//delay for 1 sec
		    int period_timelayout=500; //repeat every 4 sec
		    timer_Timelayout_Blink=new Timer();
		    timer_Timelayout_Blink.scheduleAtFixedRate(new TimerTask() {
				        				    @Override
				        				    public void run()
				        				    {
				        				        mWorkerThread_timer_timelayout_Blink.postTask(myupdateresults_timer_Timelayout_Blink);
				        				    }
		                              },delay_timelayout,period_timelayout);
		    /* code to place the slider for timelayoutblink ends here */
		
		
		    }catch(Exception e){
		    	Log.d("ERR2",e.getMessage());
		    }
		
		}

			
		
	// to initialize keypad
    private void initialize_zoomkeypad(LinearLayout main_yellow_layout,int third_row_width,int third_row_height)
	{    
    	/*Main linear layout starts here*/
    	main_yellow_layout.setLayoutParams(new LinearLayout.LayoutParams(third_row_width,third_row_height));
    	main_yellow_layout.setOrientation(LinearLayout.VERTICAL);
    	//main_yellow_layout.setBackgroundColor(Color.RED); 
    	   
    	
    	
    	/*first_yellow_row starts here*/
    	int first_yellow_row_height=(int)(third_row_height*0.04);
    	LinearLayout first_yellow_row=(LinearLayout) main_yellow_layout.findViewById(R.id.first_yellow_row);
    	first_yellow_row.setLayoutParams(new LayoutParams(third_row_width, first_yellow_row_height));
    	//first_yellow_row.setBackgroundColor(Color.MAGENTA);
    	/*first_yellow_row ends here*/
    	   
    	/*second_yellow_row starts here*/
    	int second_yellow_row_height=(int)(third_row_height*0.91);
    	LinearLayout second_yellow_row=(LinearLayout) main_yellow_layout.findViewById(R.id.second_yellow_row);
    	second_yellow_row.setLayoutParams(new LayoutParams(third_row_width, second_yellow_row_height));
    	//second_yellow_row.setBackgroundColor(Color.CYAN);
    	
    	
    		/*second_yellow_row_firstvertical starts here*/
    		int second_yellow_row_firstvertical_height=second_yellow_row_height;
    		int second_yellow_row_firstvertical_width=(int)(third_row_width*0.01);
    		LinearLayout second_yellow_row_firstvertical=(LinearLayout) second_yellow_row.findViewById(R.id.second_yellow_row_firstvertical);
    		second_yellow_row_firstvertical.setLayoutParams(new LayoutParams(second_yellow_row_firstvertical_width, second_yellow_row_firstvertical_height));
    		//second_yellow_row_firstvertical.setBackgroundColor(Color.GREEN);
    		/*second_yellow_row_firstvertical ends here*/
    		
    		
    		/*second_yellow_row_secondvertical starts here*/
    		int second_yellow_row_secondvertical_height=second_yellow_row_height;
    		int second_yellow_row_secondvertical_width=(int)(third_row_width*0.98);
    		LinearLayout second_yellow_row_secondvertical=(LinearLayout) second_yellow_row.findViewById(R.id.second_yellow_row_secondvertical);
    		second_yellow_row_secondvertical.setLayoutParams(new LayoutParams(second_yellow_row_secondvertical_width, second_yellow_row_secondvertical_height));
    		//second_yellow_row_secondvertical.setBackgroundColor(Color.BLUE);
    		
    		/*total height & width of keys 00,0,1to36,2to1 starts here*/
    		int keypad_withkeys_total_height=second_yellow_row_secondvertical_height;
	        int keypad_withkeys_total_width=second_yellow_row_secondvertical_width;  
	        /*total height & width of keys 00,0,1to36,2to1 ends here*/
	        
	        
	        
	        /*first horizontal linear layout for keys with numbers 00,0, 1to36 , 2to1,2to1,2to1 start here*/
	        int keypad_withkeys_1to36_height=(int)(keypad_withkeys_total_height*0.68);
	        int keypad_withkeys_1to36_width=(int)(keypad_withkeys_total_width);
	        final LinearLayout layout_keypad_keys_0to36_keypad=(LinearLayout) second_yellow_row_secondvertical.findViewById(R.id.layout_keypad_keys_0to36_keypad);
	        layout_keypad_keys_0to36_keypad.setLayoutParams(new LayoutParams(keypad_withkeys_1to36_width ,keypad_withkeys_1to36_height ));
	        //layout_keypad_keys_0to36_keypad.setBackgroundColor(Color.BLUE);
	        
	        
	        
	        	/*linear layout for keys with numbers including 00,0 start here*/
	        	int keypad_withkeys_0and00_height=(int)(keypad_withkeys_1to36_height);
	        	int keypad_withkeys_0and00_width=(int)(keypad_withkeys_1to36_width*0.06);
	        	final LinearLayout keypad_withkeys_0and00=(LinearLayout) layout_keypad_keys_0to36_keypad.findViewById(R.id.layout_keypad_withkeys_0and00);
	        	keypad_withkeys_0and00.setLayoutParams(new LayoutParams(keypad_withkeys_0and00_width ,keypad_withkeys_0and00_height ));
	        	//keypad_withkeys_0and00.setBackgroundColor(Color.WHITE);
	        	
	        			/* linear layout for keys with number 00 start here  */
		        		int keypad_withkeys_00_height=(int)(keypad_withkeys_0and00_height*0.42);
		        		int keypad_withkeys_00_width=(int)(keypad_withkeys_0and00_width);
		        		final LinearLayout txt_00_layout=(LinearLayout) keypad_withkeys_0and00.findViewById(R.id.txt_00_layout);
		        		txt_00_layout.setLayoutParams(new LayoutParams(keypad_withkeys_00_width ,keypad_withkeys_00_height));
		        		//txt_00_layout.setBackgroundColor(Color.GREEN);
		        		txt_00_layout.setPadding((int)(keypad_withkeys_00_width*0.30), (int)(keypad_withkeys_00_height*0.42), (int)(keypad_withkeys_00_width*0.05), (int)(keypad_withkeys_00_height*0.21));
		        		/* linear layout for keys with number 00 ends here  */
		        

		        		/* linear layout for keys with number between 00 & 0 start here  */
		        		int txt_00_0_layout_height=(int)(keypad_withkeys_0and00_height*0.16);
		        		int txt_00_0_layout_width=(int)(keypad_withkeys_0and00_width);
		        		final LinearLayout txt_00_0_layout=(LinearLayout) keypad_withkeys_0and00.findViewById(R.id.txt_00_0_layout);
		        		txt_00_0_layout.setLayoutParams(new LayoutParams(txt_00_0_layout_width ,txt_00_0_layout_height ));
		        		//txt_00_0_layout.setBackgroundColor(Color.BLUE);
		        		txt_00_0_layout.setPadding((int)(keypad_withkeys_0and00_width*0.30),0, (int)(keypad_withkeys_00_width*0.03),0);
		        		/* linear layout for keys with number between 00 & 0 ends here  */
		          
		        		/* linear layout for keys with number 0 start here  */
		        		int keypad_withkeys_0_height=(int)(keypad_withkeys_0and00_height*0.42);
		        		int keypad_withkeys_0_width=(int)(keypad_withkeys_0and00_width);
		        		final LinearLayout txt_0_layout=(LinearLayout) keypad_withkeys_0and00.findViewById(R.id.txt_0_layout);
		        		txt_0_layout.setLayoutParams(new LayoutParams(keypad_withkeys_0_width ,keypad_withkeys_0_height ));
		        		//txt_0_layout.setBackgroundColor(Color.WHITE);
		        		txt_0_layout.setPadding((int)(keypad_withkeys_0_width*0.32), (int)(keypad_withkeys_0_height*0.14), (int)(keypad_withkeys_0_width*0.05), (int)(keypad_withkeys_0_height*0.49));
		        		/* linear layout for keys with number 0 ends here  */   
		        /* linear layout for keys with numbers including 00,0 ends here*/
		        		       
		        		
		        /* linear layout for keys with numbers other than 0 & 00  starts here  */
				int keypad_withkeys_excluding0and00_height=(int)(keypad_withkeys_1to36_height);
				int keypad_withkeys_excluding0and00_width=(int)(keypad_withkeys_1to36_width*0.94); 
		        final LinearLayout keypad_withkeys_excluding0and00=(LinearLayout) layout_keypad_keys_0to36_keypad.findViewById(R.id.keypad_withkeys_excluding0and00);
		        keypad_withkeys_excluding0and00.setLayoutParams(new LayoutParams(keypad_withkeys_excluding0and00_width ,keypad_withkeys_excluding0and00_height));
			    //keypad_withkeys_excluding0and00.setBackgroundColor(Color.MAGENTA);
		        
		        int empty_horizontal_row_height=(int)(keypad_withkeys_excluding0and00_height*0.09);
		        int empty_horizontal_row_width=(int)(keypad_withkeys_excluding0and00_width);
		        
		        final LinearLayout first_empty_horizontal_row=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.first_empty_horizontal_row);
		        first_empty_horizontal_row.setLayoutParams(new LayoutParams(empty_horizontal_row_width ,(int)(empty_horizontal_row_height) ));
		        //first_empty_horizontal_row.setBackgroundColor(Color.BLUE);
		            
		        
		        
		        /* finding horizontal rows to set height for textboxes 1 to 36 starts here*/
			       //int keypad_withkeys_height=(int)(keypad_withkeys_excluding0and00_height*0.15);  
	               int keypad_withkeys_width=(int)(keypad_withkeys_excluding0and00_width);
			        
			       
			        
			        final LinearLayout first_horizontal_row_keys_i_xxiv=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.first_horizontal_row_keys_i_xxiv);
			        first_horizontal_row_keys_i_xxiv.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.15) ));
			        //first_horizontal_row_keys_i_xxiv.setBackgroundColor(Color.WHITE);
			           
			        final LinearLayout second_horizontal_row_keys_A_X=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.second_horizontal_row_keys_A_X);
			        second_horizontal_row_keys_A_X.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.15) ));
			        //second_horizontal_row_keys_A_X.setBackgroundColor(Color.YELLOW);
			        
			           
			        final LinearLayout third_empty_horizontal_row=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.third_empty_horizontal_row);
			        third_empty_horizontal_row.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.01) ));
			        //third_empty_horizontal_row.setBackgroundColor(Color.MAGENTA);
			        
			        
			        final LinearLayout third_horizontal_row_keys_a_x=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.third_horizontal_row_keys_a_x);
			        third_horizontal_row_keys_a_x.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.15) ));
			        //third_horizontal_row_keys_a_x.setBackgroundColor(Color.GREEN);
			        
			        final LinearLayout fourth_horizontal_row_keys_A1_X1=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.fourth_horizontal_row_keys_A1_X1);
			        fourth_horizontal_row_keys_A1_X1.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.15) ));
			        //fourth_horizontal_row_keys_A1_X1.setBackgroundColor(Color.WHITE);
			        
			        final LinearLayout fifth_empty_horizontal_row=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.fifth_empty_horizontal_row);
			        fifth_empty_horizontal_row.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.02) ));
			        //fifth_empty_horizontal_row.setBackgroundColor(Color.MAGENTA);
			        
			        final LinearLayout fifth_horizontal_row_keys_a1_x1=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.fifth_horizontal_row_keys_a1_x1);
			        fifth_horizontal_row_keys_a1_x1.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.15) ));
			        //fifth_horizontal_row_keys_a1_x1.setBackgroundColor(Color.YELLOW);
			       
			        
			        final LinearLayout sixth_horizontal_row_keys_A2_X2=(LinearLayout) keypad_withkeys_excluding0and00.findViewById(R.id.sixth_horizontal_row_keys_A2_X2);
			        sixth_horizontal_row_keys_A2_X2.setLayoutParams(new LayoutParams(keypad_withkeys_width ,(int)(keypad_withkeys_excluding0and00_height*0.14) ));
			        //sixth_horizontal_row_keys_A2_X2.setBackgroundColor(Color.DKGRAY);
			        
			        
			        
			        /* finding horizontal rows to set height for textboxes 1 to 36 ends here*/
			        
			        
			        
			        
			          
			          
			        /* finding textboxes layout to set width for textboxes 1 to 36 starts here*/
			           
			           /* textboxes layouts for i,ii,iii,.......xxiv ,first 2to1 starts here  */
			            int keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.15) ;
				        int keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
				         
				          
				        final LinearLayout txt_00_3_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_00_3_layout);
				        txt_00_3_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_00_3_layout.setBackgroundColor(Color.WHITE);
				        
				        final LinearLayout txt_3_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_3_layout);
				        txt_3_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_3_layout.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_3_6_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_3_6_layout);
				        txt_3_6_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_3_6_layout.setBackgroundColor(Color.BLUE);
				        
				        
				        final LinearLayout txt_6_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_6_layout);
				        txt_6_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_6_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_6_9_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_6_9_layout);
				        txt_6_9_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_6_9_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty1=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty1);
				        empty1.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty1.setBackgroundColor(Color.GREEN);
				        
				              
				        final LinearLayout txt_9_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_9_layout);
				        txt_9_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_9_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_9_12_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_9_12_layout);
				        txt_9_12_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_9_12_layout.setBackgroundColor(Color.MAGENTA);
				          
				        final LinearLayout empty2=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty2);
				        empty2.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty2.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_12_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_12_layout);
				        txt_12_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_12_layout.setBackgroundColor(Color.MAGENTA);
				           
				        final LinearLayout txt_12_15_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_12_15_layout);
				        txt_12_15_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_12_15_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_15_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_15_layout);
				        txt_15_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_15_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_15_18_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_15_18_layout);
				        txt_15_18_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_15_18_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty3=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty3);
				        empty3.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty3.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_18_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_18_layout);
				        txt_18_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_18_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_18_21_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_18_21_layout);
				        txt_18_21_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				       // txt_18_21_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_21_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_21_layout);
				        txt_21_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_21_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_21_24_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_21_24_layout);
				        txt_21_24_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_21_24_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty4=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty4);
				        empty4.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty4.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_24_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_24_layout);
				        txt_24_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_24_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_24_27_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_24_27_layout);
				        txt_24_27_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_24_27_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_27_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_27_layout);
				        txt_27_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_27_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_27_30_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_27_30_layout);
				        txt_27_30_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_27_30_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty5=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty5);
				        empty5.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty5.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_30_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_30_layout);
				        txt_30_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_30_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_30_33_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_30_33_layout);
				        txt_30_33_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_30_33_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty6=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.empty6);
				        empty6.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty6.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_33_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_33_layout);
				        txt_33_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				       // txt_33_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_33_36_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_33_36_layout);
				        txt_33_36_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_33_36_layout.setBackgroundColor(Color.MAGENTA);
				        
				        
				        final LinearLayout txt_36_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_36_layout);
				        txt_36_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_36_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_before_first_2to1_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_before_first_2to1_layout);
				        txt_before_first_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_before_first_2to1_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_first_2to1_layout=(LinearLayout) first_horizontal_row_keys_i_xxiv.findViewById(R.id.txt_first_2to1_layout);
				        txt_first_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_first_2to1_layout.setBackgroundColor(Color.MAGENTA);
				   /* textboxes layouts for i,ii,iii,.......xxiv ends here  */
				        
				        
				        
				        
				   /* textboxes layouts for A,B,C,.......W,X starts here  */
				        
				        keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.15);
				        keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
				       
				        
				        final LinearLayout txt_00_3_2_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_00_3_2_layout);
				        txt_00_3_2_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_00_3_2_layout.setBackgroundColor(Color.WHITE);
				        
				        final LinearLayout txt_3_2_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_3_2_layout);
				        txt_3_2_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_3_2_layout.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_3_6_2_5_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_3_6_2_5_layout);
				        txt_3_6_2_5_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_3_6_2_5_layout.setBackgroundColor(Color.BLUE);
				        
				        
				        final LinearLayout txt_6_5_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_6_5_layout);
				        txt_6_5_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_6_5_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_6_9_5_8_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_6_9_5_8_layout);
				        txt_6_9_5_8_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_6_9_5_8_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty7=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty7);
				        empty7.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty7.setBackgroundColor(Color.GREEN);
				          
				        final LinearLayout txt_9_8_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_9_8_layout);
				        txt_9_8_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_9_8_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_9_12_8_11_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_9_12_8_11_layout);
				        txt_9_12_8_11_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_9_12_8_11_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty8=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty8);
				        empty8.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty8.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_12_11_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_12_11_layout);
				        txt_12_11_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_12_11_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_12_15_11_14_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_12_15_11_14_layout);
				        txt_12_15_11_14_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_12_15_11_14_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_15_14_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_15_14_layout);
				        txt_15_14_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_15_14_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_15_18_14_17_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_15_18_14_17_layout);
				        txt_15_18_14_17_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_15_18_14_17_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty9=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty9);
				        empty9.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty9.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_18_17_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_18_17_layout);
				        txt_18_17_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_18_17_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_18_21_17_20_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_18_21_17_20_layout);
				        txt_18_21_17_20_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				       // txt_18_21_17_20_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_21_20_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_21_20_layout);
				        txt_21_20_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_21_20_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_21_24_20_23_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_21_24_20_23_layout);
				        txt_21_24_20_23_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_21_24_20_23_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty10=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty10);
				        empty10.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty10.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_24_23_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_24_23_layout);
				        txt_24_23_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_24_23_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_24_27_23_26_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_24_27_23_26_layout);
				        txt_24_27_23_26_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_24_27_23_26_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_27_26_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_27_26_layout);
				        txt_27_26_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_27_26_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_27_30_26_29_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_27_30_26_29_layout);
				        txt_27_30_26_29_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_27_30_26_29_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty11=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty11);
				        empty11.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty11.setBackgroundColor(Color.GREEN);
				        
				        final LinearLayout txt_30_29_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_30_29_layout);
				        txt_30_29_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_30_29_layout.setBackgroundColor(Color.MAGENTA);   
				        
				        final LinearLayout txt_30_33_29_32_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_30_33_29_32_layout);
				        txt_30_33_29_32_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_30_33_29_32_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout empty12=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.empty12);
				        empty12.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
				        //empty12.setBackgroundColor(Color.GREEN);
		        
				        final LinearLayout txt_33_32_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_33_32_layout);
				        txt_33_32_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				       // txt_33_32_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_33_36_32_35_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_33_36_32_35_layout);
				        txt_33_36_32_35_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_33_36_32_35_layout.setBackgroundColor(Color.MAGENTA);
				        
				        final LinearLayout txt_36_35_layout=(LinearLayout) second_horizontal_row_keys_A_X.findViewById(R.id.txt_36_35_layout);
				        txt_36_35_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
				        //txt_36_35_layout.setBackgroundColor(Color.MAGENTA);
				        
				   /* textboxes layouts for A,B,C,.......W,X ends here  */
				        
				       
				        
				        
				        
				        
				        
						   /* textboxes layouts for a,b,c,.......w,x starts here  */
						        
						        keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.15);
						        keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
						       
						        
						        final LinearLayout txt_00_0_2_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_00_0_2_layout);
						        txt_00_0_2_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_00_0_2_layout.setBackgroundColor(Color.WHITE);
						        
						        final LinearLayout txt_2_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_2_layout);
						        txt_2_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_2_layout.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_2_5_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_2_5_layout);
						        txt_2_5_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_2_5_layout.setBackgroundColor(Color.BLUE);
						        
						        
						        final LinearLayout txt_5_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_5_layout);
						        txt_5_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_5_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_5_8_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_5_8_layout);
						        txt_5_8_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_5_8_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty13=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty13);
						        empty13.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty13.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_8_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_8_layout);
						        txt_8_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_8_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_8_11_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_8_11_layout);
						        txt_8_11_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_8_11_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty14=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty14);
						        empty14.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty14.setBackgroundColor(Color.GREEN); 
						        
						        final LinearLayout txt_11_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_11_layout);
						        txt_11_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_11_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_11_14_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_11_14_layout);
						        txt_11_14_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_11_14_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_14_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_14_layout);
						        txt_14_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_14_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_14_17_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_14_17_layout);
						        txt_14_17_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_14_17_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty15=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty15);
						        empty15.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty15.setBackgroundColor(Color.GREEN); 
						        
						        final LinearLayout txt_17_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_17_layout);
						        txt_17_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_17_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_17_20_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_17_20_layout);
						        txt_17_20_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_17_20_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_20_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_20_layout);
						        txt_20_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_20_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_20_23_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_20_23_layout);
						        txt_20_23_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_20_23_layout.setBackgroundColor(Color.MAGENTA);
						        
						        
						        final LinearLayout empty16=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty16);
						        empty16.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty16.setBackgroundColor(Color.GREEN); 
						        
						        final LinearLayout txt_23_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_23_layout);
						        txt_23_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_23_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_23_26_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_23_26_layout);
						        txt_23_26_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_23_26_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_26_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_26_layout);
						        txt_26_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_26_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_26_29_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_26_29_layout);
						        txt_26_29_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_26_29_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty17=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty17);
						        empty17.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty17.setBackgroundColor(Color.GREEN); 
						        
						        final LinearLayout txt_29_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_29_layout);
						        txt_29_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_29_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_29_32_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_29_32_layout);
						        txt_29_32_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_29_32_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty18=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.empty18);
						        empty18.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty18.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_32_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_32_layout);
						        txt_32_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_32_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_32_35_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_32_35_layout);
						        txt_32_35_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_32_35_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_35_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_35_layout);
						        txt_35_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_35_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_before_second_2to1_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_before_second_2to1_layout);
						        txt_before_second_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_before_second_2to1_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_second_2to1_layout=(LinearLayout) third_horizontal_row_keys_a_x.findViewById(R.id.txt_second_2to1_layout);
						        txt_second_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_second_2to1_layout.setBackgroundColor(Color.MAGENTA);
						   /* textboxes layouts for a,b,c,.......w,x ends here  */
						        
				        
						        
						        
						        
						        
						        
	                      /* textboxes layouts for A1,B1,C1,.......W1,X1 starts here  */
						        
						        keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.15);
						        keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
						       
						        
						        final LinearLayout txt_0_2_1_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_0_2_1_layout);
						        txt_0_2_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_0_2_1_layout.setBackgroundColor(Color.WHITE);
						        
						        final LinearLayout txt_2_1_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_2_1_layout);
						        txt_2_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_2_1_layout.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_2_5_1_4_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_2_5_1_4_layout);
						        txt_2_5_1_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_2_5_1_4_layout.setBackgroundColor(Color.BLUE);
						        
						        
						        final LinearLayout txt_5_4_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_5_4_layout);
						        txt_5_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_5_4_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_5_8_4_7_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_5_8_4_7_layout);
						        txt_5_8_4_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_5_8_4_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty19=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty19);
						        empty19.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty19.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_8_7_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_8_7_layout);
						        txt_8_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_8_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_8_11_7_10_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_8_11_7_10_layout);
						        txt_8_11_7_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_8_11_7_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty20=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty20);
						        empty20.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty20.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_11_10_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_11_10_layout);
						        txt_11_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_11_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_11_14_10_13_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_11_14_10_13_layout);
						        txt_11_14_10_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_11_14_10_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_14_13_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_14_13_layout);
						        txt_14_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_14_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_14_17_13_16_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_14_17_13_16_layout);
						        txt_14_17_13_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_14_17_13_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty21=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty21);
						        empty21.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty21.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_17_16_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_17_16_layout);
						        txt_17_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_17_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_17_20_16_19_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_17_20_16_19_layout);
						        txt_17_20_16_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_17_20_16_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_20_19_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_20_19_layout);
						        txt_20_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_20_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_20_23_19_22_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_20_23_19_22_layout);
						        txt_20_23_19_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_20_23_19_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty22=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty22);
						        empty22.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty22.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_23_22_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_23_22_layout);
						        txt_23_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_23_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_23_26_22_25_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_23_26_22_25_layout);
						        txt_23_26_22_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_23_26_22_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_26_25_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_26_25_layout);
						        txt_26_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_26_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_26_29_25_28_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_26_29_25_28_layout);
						        txt_26_29_25_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_26_29_25_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty23=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty23);
						        empty23.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty23.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_29_28_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_29_28_layout);
						        txt_29_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_29_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_29_32_28_31_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_29_32_28_31_layout);
						        txt_29_32_28_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_29_32_28_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty24=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.empty24);
						        empty24.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty24.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_32_31_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_32_31_layout);
						        txt_32_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_32_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_32_35_31_34_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_32_35_31_34_layout);
						        txt_32_35_31_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_32_35_31_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_35_34_layout=(LinearLayout) fourth_horizontal_row_keys_A1_X1.findViewById(R.id.txt_35_34_layout);
						        txt_35_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_35_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						   /* textboxes layouts for A1,B1,C1,.......W1,X1 ends here  */
						        
				        
	                      /* textboxes layouts for a1,b1,c1,.......w1,x1 starts here  */
						        
						        keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.15);
						        keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
						       
						        
						        final LinearLayout txt_0_1_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_0_1_layout);
						        txt_0_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_0_1_layout.setBackgroundColor(Color.WHITE);
						        
						        final LinearLayout txt_1_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_1_layout);
						        txt_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_1_layout.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_1_4_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_1_4_layout);
						        txt_1_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_1_4_layout.setBackgroundColor(Color.BLUE);
						        
						        
						        final LinearLayout txt_4_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_4_layout);
						        txt_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_4_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_4_7_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_4_7_layout);
						        txt_4_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_4_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty25=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty25);
						        empty25.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty25.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_7_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_7_layout);
						        txt_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_7_10_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_7_10_layout);
						        txt_7_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_7_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty26=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty26);
						        empty26.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty26.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_10_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_10_layout);
						        txt_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_10_13_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_10_13_layout);
						        txt_10_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_10_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_13_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_13_layout);
						        txt_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_13_16_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_13_16_layout);
						        txt_13_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_13_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty27=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty27);
						        empty27.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty27.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_16_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_16_layout);
						        txt_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_16_19_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_16_19_layout);
						        txt_16_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_16_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_19_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_19_layout);
						        txt_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_19_22_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_19_22_layout);
						        txt_19_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_19_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty28=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty28);
						        empty28.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty28.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_22_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_22_layout);
						        txt_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_22_25_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_22_25_layout);
						        txt_22_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_22_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_25_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_25_layout);
						        txt_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_25_28_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_25_28_layout);
						        txt_25_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_25_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty29=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty29);
						        empty29.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty29.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_28_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_28_layout);
						        txt_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_28_31_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_28_31_layout);
						        txt_28_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_28_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty30=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.empty30);
						        empty30.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty30.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_31_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_31_layout);
						        txt_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_31_34_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_31_34_layout);
						        txt_31_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_31_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_34_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_34_layout);
						        txt_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_before_third_2to1_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_before_third_2to1_layout);
						        txt_before_third_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_before_third_2to1_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_third_2to1_layout=(LinearLayout) fifth_horizontal_row_keys_a1_x1.findViewById(R.id.txt_third_2to1_layout);
						        txt_third_2to1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_third_2to1_layout.setBackgroundColor(Color.MAGENTA);
						           
						   /* textboxes layouts for a1,b1,c1,.......w1,x1 ends here  */
						   
						        
						        
						        
	                      /* textboxes layouts for A2,B2,C2,.......W2,X2 starts here  */
						        
						        keypad_layout_withkey_height=(int)(keypad_withkeys_excluding0and00_height*0.14);
						        keypad_layout_withkey_width=(int)(keypad_withkeys_width*0.03711050);
						       
						        
						        final LinearLayout txt_00_0_3_2_1_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_00_0_3_2_1_layout);
						        txt_00_0_3_2_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_00_0_3_2_1_layout.setBackgroundColor(Color.WHITE);
						        
						        final LinearLayout txt_3_2_1_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_3_2_1_layout);
						        txt_3_2_1_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_3_2_1_layout.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_3_2_1_6_5_4_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_3_2_1_6_5_4_layout);
						        txt_3_2_1_6_5_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_3_2_1_6_5_4_layout.setBackgroundColor(Color.BLUE);
						        
						        
						        final LinearLayout txt_6_5_4_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_6_5_4_layout);
						        txt_6_5_4_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_6_5_4_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_6_5_4_9_8_7_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_6_5_4_9_8_7_layout);
						        txt_6_5_4_9_8_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_6_5_4_9_8_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        
						        final LinearLayout empty31=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty31);
						        empty31.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty31.setBackgroundColor(Color.GREEN);
						        
						        
						        final LinearLayout txt_9_8_7_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_9_8_7_layout);
						        txt_9_8_7_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_9_8_7_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_9_8_7_12_11_10_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_9_8_7_12_11_10_layout);
						        txt_9_8_7_12_11_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_9_8_7_12_11_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty32=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty32);
						        empty32.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty32.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_12_11_10_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_12_11_10_layout);
						        txt_12_11_10_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_12_11_10_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_12_11_10_15_14_13_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_12_11_10_15_14_13_layout);
						        txt_12_11_10_15_14_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_12_11_10_15_14_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_15_14_13_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_15_14_13_layout);
						        txt_15_14_13_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_15_14_13_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_15_14_13_18_17_16_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_15_14_13_18_17_16_layout);
						        txt_15_14_13_18_17_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_15_14_13_18_17_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty33=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty33);
						        empty33.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty33.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_18_17_16_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_18_17_16_layout);
						        txt_18_17_16_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_18_17_16_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_18_17_16_21_20_19_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_18_17_16_21_20_19_layout);
						        txt_18_17_16_21_20_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_18_17_16_21_20_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_21_20_19_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_21_20_19_layout);
						        txt_21_20_19_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_21_20_19_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_21_20_19_24_23_22_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_21_20_19_24_23_22_layout);
						        txt_21_20_19_24_23_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_21_20_19_24_23_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty34=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty34);
						        empty34.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty34.setBackgroundColor(Color.GREEN);
						        
						        
						        final LinearLayout txt_24_23_22_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_24_23_22_layout);
						        txt_24_23_22_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_24_23_22_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_24_23_22_27_26_25_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_24_23_22_27_26_25_layout);
						        txt_24_23_22_27_26_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_24_23_22_27_26_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_27_26_25_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_27_26_25_layout);
						        txt_27_26_25_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_27_26_25_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_27_26_25_30_29_28_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_27_26_25_30_29_28_layout);
						        txt_27_26_25_30_29_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_27_26_25_30_29_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        
						        final LinearLayout empty35=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty35);
						        empty35.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty35.setBackgroundColor(Color.GREEN);
						        
						        final LinearLayout txt_30_29_28_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_30_29_28_layout);
						        txt_30_29_28_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_30_29_28_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_30_29_28_33_32_31_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_30_29_28_33_32_31_layout);
						        txt_30_29_28_33_32_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_30_29_28_33_32_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout empty36=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.empty36);
						        empty36.setLayoutParams(new LayoutParams((int)(keypad_withkeys_width*0.005) ,(int)(keypad_layout_withkey_height) ));
						        //empty36.setBackgroundColor(Color.GREEN);

						        
						        final LinearLayout txt_33_32_31_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_33_32_31_layout);
						        txt_33_32_31_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						       // txt_33_32_31_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_33_32_31_36_35_34_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_33_32_31_36_35_34_layout);
						        txt_33_32_31_36_35_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_33_32_31_36_35_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						        final LinearLayout txt_36_35_34_layout=(LinearLayout) sixth_horizontal_row_keys_A2_X2.findViewById(R.id.txt_36_35_34_layout);
						        txt_36_35_34_layout.setLayoutParams(new LayoutParams(keypad_layout_withkey_width ,(int)(keypad_layout_withkey_height) ));
						        //txt_36_35_34_layout.setBackgroundColor(Color.MAGENTA);
						        
						   /* textboxes layouts for A2,B2,C2,.......W2,X2 ends here  */
						      
						        
			   /* finding textboxes layout to set width for textboxes 1 to 36 ends here*/
				       
				/* linear layout for keys with numbers other than 0 & 00  ends here  */
			/*first horizontal linear layout for keys with numbers 00,0, 1to36 , 2to1,2to1,2to1  ends here*/
            
	        
	            
	        
	        /*second horizontal linear layout for keys with numbers 1st12,2nd12,3rd12, 1to18,Even,Odd,Red, Black,19to36 start here*/
	        int keypad_withkeys_excluding_1to36_height=(int)(keypad_withkeys_total_height*0.32);
	        int keypad_withkeys_excluding_1to36_width=(int)(keypad_withkeys_total_width);
	        final LinearLayout layout_keypad_keys_excluding_0to36_keypad=(LinearLayout) second_yellow_row_secondvertical.findViewById(R.id.layout_keypad_keys_excluding_0to36_keypad);
	        layout_keypad_keys_excluding_0to36_keypad.setLayoutParams(new LayoutParams(keypad_withkeys_excluding_1to36_width ,keypad_withkeys_excluding_1to36_height ));
	        //layout_keypad_keys_excluding_0to36_keypad.setBackgroundColor(Color.YELLOW);
	        
	        
	        
	        
	        /* finding empty row above 1st 12 starts here*/
	        int empty_horizontal_row_height_1=(int)(keypad_withkeys_excluding_1to36_height*0.07);
	       /* int empty_horizontal_row_width1=(int)(keypad_withkeys_excluding_1to36_width);
	        final LinearLayout layout_empty_row_above_1st12=(LinearLayout) layout_keypad_keys_excluding_0to36_keypad.findViewById(R.id.layout_empty_row_above_1st12);
	        layout_empty_row_above_1st12.setLayoutParams(new LayoutParams(empty_horizontal_row_width1 ,empty_horizontal_row_height_1 ));
	      */ //layout_empty_row_above_1st12.setBackgroundColor(Color.WHITE);
	        /* finding empty row above 1st 12 ends here*/
	        
             
		        
	        /*linear layout for keys with keys including 1st12,2nd12,3rd12 start here*/
	        int keypad_keys_1st12_2nd12_3rd12_keypad_height=(int)(keypad_withkeys_excluding_1to36_height*0.30);
	        int keypad_keys_1st12_2nd12_3rd12_keypad_width=(int)(keypad_withkeys_excluding_1to36_width);
	        final LinearLayout keypad_withkeys_1st12_2nd12_3rd12=(LinearLayout) layout_keypad_keys_excluding_0to36_keypad.findViewById(R.id.layout_keypad_keys_1st12_2nd12_3rd12_keypad);
	        keypad_withkeys_1st12_2nd12_3rd12.setLayoutParams(new LayoutParams(keypad_keys_1st12_2nd12_3rd12_keypad_width ,keypad_keys_1st12_2nd12_3rd12_keypad_height ));
	        //keypad_withkeys_1st12_2nd12_3rd12.setBackgroundColor(Color.MAGENTA);
	        /* linear layout for keys with keys including 1st12,2nd12,3rd12  ends here*/
	



		        
	        
	        /* finding textboxes layout to set width for textboxes 1st12,2nd12,3rd12 starts here*/
	        
	        int keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
	        int keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.18);
	        final LinearLayout txt_before_1st12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_before_1st12_layout);
	        txt_before_1st12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
	        //txt_before_1st12_layout.setBackgroundColor(Color.BLUE);
	        
	        keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
	        keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.04);
	        final LinearLayout txt_1st12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_1st12_layout);
	        txt_1st12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
	        //txt_1st12_layout.setBackgroundColor(Color.BLACK);
		    	 
	        keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
	        keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.25);
	        final LinearLayout txt_after_1st12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_after_1st12_layout);
	        txt_after_1st12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
	        //txt_after_1st12_layout.setBackgroundColor(Color.RED);
	        
	         
	         keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
		        keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.04);
		        final LinearLayout txt_2nd12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_2nd12_layout);
		        txt_2nd12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
		        //txt_2nd12_layout.setBackgroundColor(Color.BLACK);
		           
		     keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
		     keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.24);
		     final LinearLayout txt_after_2nd12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_after_2nd12_layout);
		     txt_after_2nd12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
		     //txt_after_2nd12_layout.setBackgroundColor(Color.RED); 
		       
		    keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
		    keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.04);
		    final LinearLayout txt_3rd12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_3rd12_layout);
		    txt_3rd12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
		    //txt_3rd12_layout.setBackgroundColor(Color.BLACK);
		        
		     keypad_keys_1st12_height=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_height);
			 keypad_keys_1st12_width=(int)(keypad_keys_1st12_2nd12_3rd12_keypad_width*0.24);
			 final LinearLayout txt_after_3rd12_layout=(LinearLayout) keypad_withkeys_1st12_2nd12_3rd12.findViewById(R.id.txt_after_3rd12_layout);
			 txt_after_3rd12_layout.setLayoutParams(new LayoutParams(keypad_keys_1st12_width ,keypad_keys_1st12_height ));
			// txt_after_3rd12_layout.setBackgroundColor(Color.RED);    
		        
		        
		     /* finding textboxes layout to set width for textboxes 1st12,2nd12,3rd12 ends here*/
	        
	        
			 
			 
			 /* finding empty row between 1st12 & even/odd starts here*/
		       int empty_horizontal_row_height2=(int)(keypad_withkeys_excluding_1to36_height*0.27);
		       int empty_horizontal_row_width2=(int)(keypad_withkeys_excluding_1to36_width);
		        final LinearLayout layout_empty_row_1st12_even_odd=(LinearLayout) layout_keypad_keys_excluding_0to36_keypad.findViewById(R.id.layout_empty_row_1st12_even_odd);
		        layout_empty_row_1st12_even_odd.setLayoutParams(new LayoutParams(empty_horizontal_row_width2 ,empty_horizontal_row_height2 ));
		        //layout_empty_row_1st12_even_odd.setBackgroundColor(Color.WHITE);
			    
		        /* finding empty row between 1st12 & even/odd ends here*/
	        
	        

		        /*linear layout for keys with keys including 1to18,Even,Red,Black,Odd, 19to36 starts here*/
		        int keypad_keys_1to18_Even_Red_Black_Odd_19to36_height=(int)(keypad_withkeys_excluding_1to36_height-(keypad_keys_1st12_2nd12_3rd12_keypad_height+empty_horizontal_row_height_1+empty_horizontal_row_height2));
		        int keypad_keys_1to18_Even_Red_Black_Odd_19to36_width=(int)(keypad_withkeys_excluding_1to36_width);
		        final LinearLayout keypad_withkeys_1to18_Even_Red_Black_Odd_19to36=(LinearLayout) layout_keypad_keys_excluding_0to36_keypad.findViewById(R.id.layout_keypad_keys_1to18_Even_Red_Black_Odd_19to36_keypad);
		        keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.setLayoutParams(new LayoutParams(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width ,keypad_keys_1to18_Even_Red_Black_Odd_19to36_height ));
		        //keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.setBackgroundColor(Color.GREEN);
		        //keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.setPadding(0, 0, 0, 10);
		        /*linear layout for keys with keys including 1to18,Even,Red,Black,Odd, 19to36 ends here*/
	        
	        
	        
	        
		        int keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        int keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.15);
		        final LinearLayout txt_before_1to18_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_before_1to18_layout);
		        txt_before_1to18_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_before_1to18_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_1to18_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_1to18_layout);
		        txt_1to18_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_1to18_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        //txt_1to18_layout.setBackgroundColor(Color.WHITE);
		        
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.11);
		        final LinearLayout txt_after_1to18_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_1to18_layout);
		        txt_after_1to18_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_1to18_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_Even_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_Even_layout);
		        txt_Even_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_Even_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        //txt_Even_layout.setBackgroundColor(Color.WHITE);
		        
		           
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.076);
		        final LinearLayout txt_after_Even_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_Even_layout);
		        txt_after_Even_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_Even_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_Red_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_Red_layout);
		        txt_Red_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_Red_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        //txt_Red_layout.setBackgroundColor(Color.WHITE);
		        
	        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.09);
		        final LinearLayout txt_after_Red_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_Red_layout);
		        txt_after_Red_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_Red_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_Black_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_Black_layout);
		        txt_Black_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_Black_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        //txt_Black_layout.setBackgroundColor(Color.WHITE);
		        
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.069);
		        final LinearLayout txt_after_black_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_black_layout);
		        txt_after_black_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_black_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_Odd_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_Odd_layout);
		        txt_Odd_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_Odd_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        //txt_Odd_layout.setBackgroundColor(Color.BLUE);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.080);
		        final LinearLayout txt_after_Odd_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_Odd_layout);
		        txt_after_Odd_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_Odd_layout.setBackgroundColor(Color.RED);
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.04);
		        final LinearLayout txt_19to36_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_19to36_layout);
		        txt_19to36_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        txt_19to36_layout.setPadding(0, 0, 0,(int)(keypad_keys_1to18_height* 0.30));
		        
		        keypad_keys_1to18_height=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_height);
		        keypad_keys_1to18_width=(int)(keypad_keys_1to18_Even_Red_Black_Odd_19to36_width*0.30);
		        final LinearLayout txt_after_19to36_layout=(LinearLayout) keypad_withkeys_1to18_Even_Red_Black_Odd_19to36.findViewById(R.id.txt_after_19to36_layout);
		        txt_after_19to36_layout.setLayoutParams(new LayoutParams(keypad_keys_1to18_width ,keypad_keys_1to18_height ));
		        //txt_after_19to36_layout.setBackgroundColor(Color.RED);
		        
		        
		        
		        

	        /*second horizontal linear layout for keys with numbers 1st12,2nd12,3rd12, 1to18,Even,Odd,Red, Black,19to36 ends here*/
	        
	        /*second_yellow_row_secondvertical ends here*/
    		
    		/*second_yellow_row_thirdvertical starts here*/
    		int second_yellow_row_thirdvertical_height=second_yellow_row_height;
    		int second_yellow_row_thirdvertical_width=(int)(third_row_width*0.01);
    		LinearLayout second_yellow_row_thirdvertical=(LinearLayout) second_yellow_row.findViewById(R.id.second_yellow_row_thirdvertical);
    		second_yellow_row_thirdvertical.setLayoutParams(new LayoutParams(second_yellow_row_thirdvertical_width, second_yellow_row_thirdvertical_height));
    		//second_yellow_row_thirdvertical.setBackgroundColor(Color.RED);
    		/*second_yellow_row_thirdvertical ends here*/
    	/*second_yellow_row ends here*/
    	
    	/*third_yellow_row starts here*/
    	int third_yellow_row_height=(int)(third_row_height*0.05);
    	LinearLayout third_yellow_row=(LinearLayout) main_yellow_layout.findViewById(R.id.third_yellow_row);
    	third_yellow_row.setLayoutParams(new LayoutParams(third_row_width, third_yellow_row_height));
    	//third_yellow_row.setBackgroundColor(Color.MAGENTA);
    	/*third_yellow_row ends here*/
    	   
    	/*Main linear layout ends here*/
    	
	}
  	
	
	private void initialize_roulette_screen(LinearLayout mainlinearlayout,int screenWidthDp,int screenHeightDp)
	{
	    mainlinearlayout.setOrientation(LinearLayout.VERTICAL);
		 /* to set background image bg in main grid starts  */
	       Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.nroulettebg);
	       mainlinearlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
	     /* to set background image bg in main grid ends  */
		
		    LinearLayout first_row_layout,second_row_layout,fourth_row_layout;
			LinearLayout first_row_first_empty_cell_layout,first_row_title_imageview_layout,first_row_second_empty_cell_layout,second_row_first_column,second_row_second_column,second_row_third_column;
		   
		   
		       int first_row_height=(int)(screenHeightDp*0.117);
		       int first_row_width=(int)(screenWidthDp);
		       int columnonewidth=(int)(screenWidthDp*0.3);
		       int columnoneheight=(int)(screenHeightDp*0.340);
	       
		   		/*  first row starts here  */
				       first_row_layout= (LinearLayout) mainlinearlayout.findViewById(R.id.first_row);
				       first_row_layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidthDp,first_row_height));
				       first_row_layout.setOrientation(LinearLayout.HORIZONTAL);
					   //first_row_layout.setBackgroundColor(Color.GREEN);

		
					     /* first row first column starts here */
					       first_row_first_empty_cell_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.first_row_first_empty_cell);
					       first_row_first_empty_cell_layout.setLayoutParams(new LinearLayout.LayoutParams( columnonewidth,first_row_height));
					       first_row_first_empty_cell_layout.setOrientation(LinearLayout.VERTICAL);
							//first_row_first_empty_cell_layout.setBackgroundColor(Color.WHITE);
					    /* first row first column ends here */
			  
			  
		
					    columntwowidth=(int)(screenWidthDp-columnonewidth*2);
				        columntwoheight=columnoneheight;
				    /* first row second column starts here */
				        first_row_title_imageview_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.first_row_title_imageview_layout);
				        first_row_title_imageview_layout.setLayoutParams(new LinearLayout.LayoutParams( columntwowidth,first_row_height));
				        first_row_title_imageview_layout.setOrientation(LinearLayout.VERTICAL);
						//first_row_title_imageview_layout.setBackgroundColor(Color.MAGENTA);
				
						tileimageview=(ImageView)first_row_title_imageview_layout.findViewById(R.id.tileimageview);
						tileimageview.setPadding((int)(columntwowidth*0.07),0, 0, 0);
						tileimageview.setLayoutParams(new LayoutParams((int)(columntwowidth*0.84),first_row_height));
				    /* first row second column ends here */
			  
		
			  
				        int columnthreewidth=(int)(screenWidthDp*0.3);        
				        int columnthreeheight=columnoneheight;
		
			       /* first row third column starts here */
				        first_row_second_empty_cell_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.first_row_second_empty_cell);
				        first_row_second_empty_cell_layout.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth,first_row_height ));
				        first_row_second_empty_cell_layout.setOrientation(LinearLayout.VERTICAL);
					    //first_row_second_empty_cell_layout.setBackgroundColor(Color.RED);
				   /*  first row third column ends here */
	  
	  
	         /* first row ends here  */
		   
		   
	         /*  second row starts here  */
					second_row_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.second_row);
					second_row_layout.setLayoutParams(new LinearLayout.LayoutParams(screenWidthDp,columnoneheight));
					second_row_layout.setOrientation(LinearLayout.HORIZONTAL);
				  // second_row_layout.setBackgroundColor(Color.GREEN);

			
		    	     /* second row first column starts here */
						second_row_first_column=(LinearLayout)second_row_layout.findViewById(R.id.second_row_first_column);
						second_row_first_column.setLayoutParams(new LinearLayout.LayoutParams( columnonewidth,columnoneheight));
						second_row_first_column.setOrientation(LinearLayout.VERTICAL);
						//second_row_first_column.setBackgroundColor(Color.RED);
					
								
						       
								
								int secondrow_firstcolumn1_height=(int)(columnoneheight*0.25);
								int secondrow_firstcolumn2_height=(int)(columnoneheight*0.35);
								int secondrow_firstcolumn3_height=(int)(columnoneheight*0.40);
								
								/* second row first column first cell starts here   */
								txt_score_layout =(LinearLayout) second_row_first_column.findViewById(R.id.txt_score_layout);
								txt_score_layout.setLayoutParams(new LinearLayout.LayoutParams( columnonewidth,secondrow_firstcolumn1_height));
								txt_score_layout.setOrientation(LinearLayout.HORIZONTAL);
					            //txt_score_layout.setBackgroundColor(Color.WHITE);
								
								    txt_score=(TextView) txt_score_layout.findViewById(R.id.txt_score);
							        txt_score.setPadding((int)(columnonewidth*0.40),(int)( secondrow_firstcolumn1_height*0.37), 0, 0);
							        txt_score.setLayoutParams(new LayoutParams(columnonewidth,secondrow_firstcolumn1_height));
							        //txt_score.setBackgroundColor(Color.parseColor("#0099cc"));
							        txt_score.setTextColor(Color.WHITE);
							        txt_score.setTextSize(12);
							        txt_score.setGravity(Gravity.LEFT);
							        txt_score.setText(""+GlobalClass.getBalance());	
								/* second row first column first cell ends here   */
								
								/* second row first column second cell starts here   */
								txt_time_layout=(LinearLayout)second_row_first_column.findViewById(R.id.txt_time_layout);
								txt_time_layout.setLayoutParams(new LinearLayout.LayoutParams( columnonewidth,secondrow_firstcolumn2_height));
								txt_time_layout.setOrientation(LinearLayout.HORIZONTAL);
								//txt_time_layout.setBackgroundColor(Color.MAGENTA);				
							//txt_time_layout.setBackgroundResource(R.drawable.clockglow);
								
								txt_time=(TextView) txt_time_layout.findViewById(R.id.txt_time);
						        txt_time.setPadding((int)(columnonewidth*0.35),(int)( secondrow_firstcolumn2_height*0.48), 0, 0);
						        txt_time.setLayoutParams(new LayoutParams(columnonewidth,secondrow_firstcolumn2_height));
							    //txt_time.setBackgroundColor(Color.GRAY);
						        txt_time.setTextColor(Color.WHITE);
						        txt_time.setTextSize(12);
						        txt_time.setGravity(Gravity.LEFT);
						        txt_time.setText("");
						        //txt_time_layout.addView(txt_time);
								
								
						        //second_row_first_column.addView(txt_time_layout);
								/* second row first column second cell ends here   */
								
								/* second row first column third cell starts here   */
						        increment_bet_keypad_layout=(LinearLayout)second_row_first_column.findViewById(R.id.increment_bet_keypad_layout);
						        increment_bet_keypad_layout.setLayoutParams(new LinearLayout.LayoutParams( columnonewidth,secondrow_firstcolumn3_height));
						        increment_bet_keypad_layout.setOrientation(LinearLayout.HORIZONTAL);
								//increment_bet_keypad_layout.setBackgroundColor(Color.YELLOW);
								
								LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
								LinearLayout roulette_increment_bet_layout=(LinearLayout) inflater.inflate(
								        R.layout.roulette_increment_bet_keypad, null);
								initialize_increment_bet_keypad(roulette_increment_bet_layout,columnonewidth,secondrow_firstcolumn3_height);
								A obj=new A();
								obj.calculate_keypad_bet(roulette_increment_bet_layout);   // to calculate bet on each key
								increment_bet_keypad_layout.addView(roulette_increment_bet_layout);
							/* second row first column third cell ends here   */
				    /* second row first column ends here */
			   
			   
	
			   
				    /* second row second column starts here */
						second_row_second_column=(LinearLayout)second_row_layout.findViewById(R.id.second_row_second_column);
						second_row_second_column.setLayoutParams(new LinearLayout.LayoutParams( columntwowidth,columntwoheight));
						second_row_second_column.setOrientation(LinearLayout.VERTICAL);
						//second_row_second_column.setBackgroundColor(Color.MAGENTA);

						overlay_two_imageviews=(RelativeLayout) second_row_second_column.findViewById(R.id.overlay_two_imageviews);
		
						
						int numberwheelheight=overlay_two_imageviews.getHeight();
						int numberwheelwidth= overlay_two_imageviews.getWidth();
						number_Wheel = new Number_Wheel(overlay_two_imageviews, columntwowidth, columntwoheight);
						
						
						wheelImageView=(ImageView)overlay_two_imageviews.findViewById(R.id.wheel_bg_imageview);
						wheel=(ImageView)overlay_two_imageviews.findViewById(R.id.wheel);
						ball=(ImageView)overlay_two_imageviews.findViewById(R.id.ball);
				    /* second row second column ends here */
		   
				    
				    
						/* second row third column starts here */
					  
							second_row_third_column=(LinearLayout) second_row_layout.findViewById(R.id.second_row_third_column);
							second_row_third_column.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth,columnthreeheight ));
							second_row_third_column.setOrientation(LinearLayout.VERTICAL);
							//second_row_third_column.setBackgroundColor(Color.WHITE);
						 
					
								
								int secondrow_thirdcolumn1_height=(int)(columnthreeheight*0.30);
								int secondrow_thirdcolumn2_height=secondrow_thirdcolumn1_height;
								int secondrow_thirdcolumn3_height=columnthreeheight-(secondrow_thirdcolumn1_height*2);
								
								/* second row third column first cell starts here   */
								txt_winner_layout=(LinearLayout) second_row_third_column.findViewById(R.id.txt_winner_layout);
								txt_winner_layout.setLayoutParams(new LinearLayout.LayoutParams( columnthreewidth,secondrow_thirdcolumn1_height));
								txt_winner_layout.setOrientation(LinearLayout.HORIZONTAL);
								//txt_winner_layout.setBackgroundColor(Color.WHITE);
								
								txt_winner=(TextView)txt_winner_layout.findViewById(R.id.txt_winner);
						        txt_winner.setPadding((int)(columnthreewidth*0.47),(int)( secondrow_thirdcolumn1_height*0.30), 0, 0);
						        txt_winner.setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn1_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
						        txt_winner.setTextColor(Color.WHITE);
						        txt_winner.setTextSize(12);
						        txt_winner.setGravity(Gravity.LEFT);
						        txt_winner.setText("0");
						        
								/* second row third column first cell ends here   */
								
								
								/* second row third column second cell starts here   */
						        txt_last_5_data_layout=(LinearLayout)second_row_third_column.findViewById(R.id.txt_last_5_data_layout);
						        txt_last_5_data_layout.setLayoutParams(new LinearLayout.LayoutParams( columnthreewidth,secondrow_thirdcolumn2_height));
						        txt_last_5_data_layout.setOrientation(LinearLayout.HORIZONTAL);
								//txt_last_5_data_layout.setBackgroundColor(Color.GREEN);				

								
						        LinearLayout empty_before_txt_last_5_data_0_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.empty_before_txt_last_5_data_0_layout);
						        empty_before_txt_last_5_data_0_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.32),secondrow_thirdcolumn2_height));
						        empty_before_txt_last_5_data_0_layout.setOrientation(LinearLayout.VERTICAL);
						        //empty_before_txt_last_5_data_0_layout.setBackgroundColor(Color.RED);

						        LinearLayout txt_last_5_data_0_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.txt_last_5_data_0_layout);
				        		txt_last_5_data_0_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.10),secondrow_thirdcolumn2_height));
						        txt_last_5_data_0_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_5_data_0_layout.setBackgroundColor(Color.RED);
						        
						        LinearLayout txt_last_5_data_1_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.txt_last_5_data_1_layout);
				        		txt_last_5_data_1_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.11),secondrow_thirdcolumn2_height));
						        txt_last_5_data_1_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_5_data_1_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_5_data_2_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.txt_last_5_data_2_layout);
				        		txt_last_5_data_2_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.11),secondrow_thirdcolumn2_height));
						        txt_last_5_data_2_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_5_data_2_layout.setBackgroundColor(Color.RED);
						        
						        LinearLayout txt_last_5_data_3_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.txt_last_5_data_3_layout);
				        		txt_last_5_data_3_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.11),secondrow_thirdcolumn2_height));
						        txt_last_5_data_3_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_5_data_3_layout.setBackgroundColor(Color.WHITE);
						        
						        LinearLayout txt_last_5_data_4_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.txt_last_5_data_4_layout);
				        		txt_last_5_data_4_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.11),secondrow_thirdcolumn2_height));
						        txt_last_5_data_4_layout.setOrientation(LinearLayout.VERTICAL);
								//txt_last_5_data_4_layout.setBackgroundColor(Color.RED);
						        

						        LinearLayout empty_after_txt_last_5_data_4_layout=(LinearLayout)txt_last_5_data_layout.findViewById(R.id.empty_after_txt_last_5_data_4_layout);
						        empty_after_txt_last_5_data_4_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.15),secondrow_thirdcolumn2_height));
						        empty_after_txt_last_5_data_4_layout.setOrientation(LinearLayout.VERTICAL);
						        //empty_after_txt_last_5_data_4_layout.setBackgroundColor(Color.RED);

								
								txt_last_5_data[0]=(TextView)txt_last_5_data_0_layout.findViewById(R.id.txt_last_5_data_0);
								txt_last_5_data[0].setPadding((int)((columnthreewidth*0.12)*0.30),(int)( secondrow_thirdcolumn2_height*0.42), 0, 0);
								txt_last_5_data[0].setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn2_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								//txt_last_5_data[0].setTextColor(Color.WHITE);
								txt_last_5_data[0].setTextSize(11);
								txt_last_5_data[0].setGravity(Gravity.LEFT);
								//txt_last_5_data[0].setText("20");
		
								
								txt_last_5_data[1]=(TextView)txt_last_5_data_1_layout.findViewById(R.id.txt_last_5_data_1);
								txt_last_5_data[1].setPadding((int)((columnthreewidth*0.12)*0.30),(int)( secondrow_thirdcolumn2_height*0.42), 0, 0);
								txt_last_5_data[1].setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn2_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								//txt_last_5_data[1].setTextColor(Color.WHITE);
								txt_last_5_data[1].setTextSize(11);
								txt_last_5_data[1].setGravity(Gravity.LEFT);
								//txt_last_5_data[1].setText("20");
		
								
								
								txt_last_5_data[2]=(TextView)txt_last_5_data_2_layout.findViewById(R.id.txt_last_5_data_2);
								txt_last_5_data[2].setPadding((int)((columnthreewidth*0.12)*0.30),(int)( secondrow_thirdcolumn2_height*0.42), 0, 0);
								txt_last_5_data[2].setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn2_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								//txt_last_5_data[2].setTextColor(Color.WHITE);
								txt_last_5_data[2].setTextSize(11);
								txt_last_5_data[2].setGravity(Gravity.LEFT);
								//txt_last_5_data[2].setText("20");
		
								
								
								
								txt_last_5_data[3]=(TextView)txt_last_5_data_3_layout.findViewById(R.id.txt_last_5_data_3);
								txt_last_5_data[3].setPadding((int)((columnthreewidth*0.12)*0.30),(int)( secondrow_thirdcolumn2_height*0.42), 0, 0);
								txt_last_5_data[3].setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn2_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								//txt_last_5_data[3].setTextColor(Color.WHITE);
								txt_last_5_data[3].setTextSize(11);
								txt_last_5_data[3].setGravity(Gravity.LEFT);
								//txt_last_5_data[3].setText("20");
		
							   	
								
								
								txt_last_5_data[4]=(TextView)txt_last_5_data_4_layout.findViewById(R.id.txt_last_5_data_4);
								txt_last_5_data[4].setPadding((int)((columnthreewidth*0.12)*0.30),(int)( secondrow_thirdcolumn2_height*0.42), 0, 0);
								txt_last_5_data[4].setLayoutParams(new LayoutParams(columnthreewidth,secondrow_thirdcolumn2_height));
						        //txt_winner.setBackgroundColor(Color.parseColor("#0099cc"));
								//txt_last_5_data[4].setTextColor(Color.WHITE);
								txt_last_5_data[4].setTextSize(11);
								txt_last_5_data[4].setGravity(Gravity.LEFT);
								//txt_last_5_data[4].setText("20");
		   
								/* second row third column second cell ends here   */
								
								/* second row third column third cell starts here   */
								btn_betOK_take_layout=	(LinearLayout) second_row_third_column.findViewById(R.id.btn_betOK_take_layout);
								btn_betOK_take_layout.setLayoutParams(new LinearLayout.LayoutParams( columnthreewidth,secondrow_thirdcolumn3_height));
								btn_betOK_take_layout.setOrientation(LinearLayout.HORIZONTAL);
								//btn_betOK_take_layout.setBackgroundColor(Color.YELLOW);
								
								LinearLayout secondrow_thirdcolumn_vertical=(LinearLayout)btn_betOK_take_layout.findViewById(R.id.secondrow_thirdcolumn_vertical);
								secondrow_thirdcolumn_vertical.setLayoutParams(new LinearLayout.LayoutParams( columnthreewidth,secondrow_thirdcolumn3_height));
								secondrow_thirdcolumn_vertical.setOrientation(LinearLayout.VERTICAL);
								//secondrow_thirdcolumn_vertical.setBackgroundColor(Color.BLUE);
								
								
								/*second row third column third cell first row starts here*/
								LinearLayout emptyrow_above_cancelbet=(LinearLayout) secondrow_thirdcolumn_vertical.findViewById(R.id.empty_row_above_cancelbet);
								emptyrow_above_cancelbet.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth, (int)(secondrow_thirdcolumn3_height*0.07)));
								emptyrow_above_cancelbet.setOrientation(LinearLayout.HORIZONTAL);
								//emptyrow_above_cancelbet.setBackgroundColor(Color.LTGRAY);
								/*second row third column third cell first row ends here*/
								
								
								/*second row third column third cell second row starts here*/
								LinearLayout row_with_cancelbet=(LinearLayout) secondrow_thirdcolumn_vertical.findViewById(R.id.row_with_cancelbet);
								row_with_cancelbet.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth, (int)(secondrow_thirdcolumn3_height*0.47)));
								row_with_cancelbet.setOrientation(LinearLayout.HORIZONTAL);
								//row_with_cancelbet.setBackgroundColor(Color.CYAN);
								
								
										/*first cancel bet starts here*/
										LinearLayout first_cancel_bet=(LinearLayout) row_with_cancelbet.findViewById(R.id.firstcancelbet);
										first_cancel_bet.setLayoutParams(new LinearLayout.LayoutParams((int)(columnthreewidth*0.32), (int)(secondrow_thirdcolumn3_height*0.47)));
										first_cancel_bet.setOrientation(LinearLayout.VERTICAL);
										//first_cancel_bet.setBackgroundColor(Color.RED);
										/*first cancel bet ends here*/
										
										/*second cancel bet starts here*/
										LinearLayout second_cancel_bet=(LinearLayout) row_with_cancelbet.findViewById(R.id.secondcancelbet);
										second_cancel_bet.setLayoutParams(new LinearLayout.LayoutParams((int)(columnthreewidth*0.60), (int)(secondrow_thirdcolumn3_height*0.47)));
										second_cancel_bet.setOrientation(LinearLayout.VERTICAL);
										//second_cancel_bet.setBackgroundColor(Color.GRAY);
										
										
										/*To set imageview in second cancel bet starts here*/
								        ImageView cancelbet=(ImageView)second_cancel_bet.findViewById(R.id.cancelbetimage);
										//cancelbet.setBackgroundResource(R.drawable.betok);
								        cancelbet.setOnClickListener(new OnClickListener() {
											
											@Override
											public void onClick(View v) {
												Toast.makeText(v.getContext(), "cancel bet toast fired", Toast.LENGTH_LONG).show();
											}
										});
										/*To set imageview in second cancel bet ends here*/
										
										/*second cancel bet ends here*/
										
										/*third cancel bet starts here*/
										LinearLayout third_cancel_bet=(LinearLayout) row_with_cancelbet.findViewById(R.id.thirdcancelbet);
										third_cancel_bet.setLayoutParams(new LinearLayout.LayoutParams((int)(columnthreewidth*0.10), (int)(secondrow_thirdcolumn3_height*0.47)));
										third_cancel_bet.setOrientation(LinearLayout.VERTICAL);
										//third_cancel_bet.setBackgroundColor(Color.RED);
										/*third cancel bet ends here*/
								/*second row third column third cell second row ends here*/
								
								
								/*second row third column third cell third row starts here*/
								LinearLayout emptyrow_above_betok=(LinearLayout) secondrow_thirdcolumn_vertical.findViewById(R.id.empty_row_above_betOK);
								emptyrow_above_betok.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth, (int)(secondrow_thirdcolumn3_height*0.07)));
								emptyrow_above_betok.setOrientation(LinearLayout.HORIZONTAL);
								//emptyrow_above_betok.setBackgroundColor(Color.MAGENTA);
								/*second row third column third cell third row ends here*/
								
								
								/*second row third column third cell fourth row starts here*/
								LinearLayout row_with_take_betok=(LinearLayout) secondrow_thirdcolumn_vertical.findViewById(R.id.row_with_betOK_take);
								row_with_take_betok.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth, (int)(secondrow_thirdcolumn3_height*0.40)));
								row_with_take_betok.setOrientation(LinearLayout.HORIZONTAL);
								//row_with_take_betok.setBackgroundColor(Color.BLACK);
								
								
								
								/*second row third column second horizontal first vertical starts here*/
								LinearLayout empty_space_before_take_layout=(LinearLayout)row_with_take_betok.findViewById(R.id.empty_space_before_take_layout);
								empty_space_before_take_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.17),(int)(secondrow_thirdcolumn3_height*0.40)));
								empty_space_before_take_layout.setOrientation(LinearLayout.VERTICAL);
								//empty_space_before_take_layout.setBackgroundColor(Color.RED);
								/*second row third column second horizontal first vertical ends here*/
								
								/*second row third column second horizontal second vertical starts here*/
								LinearLayout space_take_layout=(LinearLayout)row_with_take_betok.findViewById(R.id.space_take_layout);
								space_take_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.36),(int)(secondrow_thirdcolumn3_height*0.40)));
								space_take_layout.setOrientation(LinearLayout.VERTICAL);
								//space_take_layout.setBackgroundColor(Color.WHITE);
								
								/*To set imageview in second row third column second horizontal second vertical linear layout starts here*/
						        btn_take=(ImageView)space_take_layout.findViewById(R.id.btn_take);
								//btn_take.setBackgroundResource(R.drawable.betok);
						        btn_take.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										Toast.makeText(v.getContext(), "take toast fired", Toast.LENGTH_LONG).show();
									}
								});
								/*To set imageview in second row third column second horizontal second vertical linear layout ends here*/
								
								/*second row third column second horizontal second vertical ends here*/
								
								
								/*second row third column second horizontal third vertical starts here*/
								LinearLayout empty_space_before_betOK_layout=(LinearLayout)row_with_take_betok.findViewById(R.id.empty_space_before_betOK_layout);
								empty_space_before_betOK_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.11),(int)(secondrow_thirdcolumn3_height*0.40)));
								empty_space_before_betOK_layout.setOrientation(LinearLayout.VERTICAL);
								//empty_space_before_betOK_layout.setBackgroundColor(Color.RED);
								/*second row third column second horizontal third vertical ends here*/
								
								
								
								/*second row third column second horizontal fourth vertical starts here*/
								LinearLayout btn_betOK_layout=(LinearLayout)row_with_take_betok.findViewById(R.id.btn_betOK_layout);
								btn_betOK_layout.setLayoutParams(new LinearLayout.LayoutParams( (int)(columnthreewidth*0.36),(int)(secondrow_thirdcolumn3_height*0.40)));
								btn_betOK_layout.setOrientation(LinearLayout.VERTICAL);
								//btn_betOK_layout.setBackgroundColor(Color.GREEN);
								
								
								/*To set imageview in second row third column second horizontal fourth vertical linear layout starts here*/
						        btn_bet_OK=(ImageView)btn_betOK_layout.findViewById(R.id.btn_bet_OK);
								//btn_bet_OK.setBackgroundResource(R.drawable.betok);
								btn_bet_OK.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
											Bet_Ok_Take_EVent_Handler();									
										Toast.makeText(v.getContext(), "Bet Ok toast fired", Toast.LENGTH_LONG).show();
									}
								});
								/*To set imageview in second row third column second horizontal fourth vertical linear layout ends here*/
								
								/*second row third column second horizontal fourth vertical ends here*/
								
								
								   
								
								/*second row third column third cell fourth row ends here*/
								  
								
								
								/*second row third column third cell fifth row starts here*/
								LinearLayout emptyrow_after_betok=(LinearLayout) secondrow_thirdcolumn_vertical.findViewById(R.id.empty_row_down_betOK);
								emptyrow_after_betok.setLayoutParams(new LinearLayout.LayoutParams(columnthreewidth, (int)(secondrow_thirdcolumn3_height*0.07)));
								emptyrow_after_betok.setOrientation(LinearLayout.HORIZONTAL);
								//emptyrow_after_betok.setBackgroundColor(Color.WHITE);
								/*second row third column third cell fifth row ends here*/
								
								/*second row third column third cell ends here */
								
								
		
				    /* second row third column ends here */
			    
			/* second row ends here  */
			   

		   
		   
		   
		   
		   third_row_total_height=screenHeightDp-(first_row_height+columnoneheight);
	       third_row_height=(int)(third_row_total_height*0.89);
	       third_row_width=(int)(screenWidthDp);
	       
		/*  third row starts here  */
	       bet_keypad_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.third_row);
	       bet_keypad_layout.setLayoutParams(new LinearLayout.LayoutParams(third_row_width,third_row_height));
	       bet_keypad_layout.setOrientation(LinearLayout.HORIZONTAL);
	       //bet_keypad_layout.setBackgroundColor(Color.GREEN);
			
			
			//keypad_withoutbet.third_row_width=third_row_width;  // initializing the width of keypad
			//keypad_withoutbet.third_row_height=third_row_height; // initializing the height of keypad
			
			//third_row_layout.addView(new keypad_withoutbet(this));    // instantiating the keypad
			LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			main_thirdrowlayout=(LinearLayout) inflate.inflate(R.layout.activity_funroulette_thirdrowlayout, null);
			
			initialize_keypad(main_thirdrowlayout,third_row_width,third_row_height);  // to initialize keypad
			bet_keypad_layout.addView(main_thirdrowlayout);
			
			bet_keypad_layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
			
					LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					main_yellow_layout=(LinearLayout) inflate.inflate(R.layout.activity_yellowbutton, null);
					
					initialize_zoomkeypad(main_yellow_layout, third_row_width, third_row_height);
										
				    ABC obj=new ABC();
					obj.calculate_keypad_bet(main_yellow_layout);   // to calculate bet on each key
				    obj.reset_all_bet_text_boxes();
				    
				    
				    bet_keypad_layout.removeView(main_thirdrowlayout);
				    bet_keypad_layout.setClickable(false);				    
				    
				    bet_keypad_layout.addView(main_yellow_layout);    // instantiating the keypad

					Toast.makeText(v.getContext(), "keypad clicked", Toast.LENGTH_LONG).show();
					/* to redirect to another MenuActivity containing user balance with it starts  */
         			//Intent call_zoomkeypad_activity_intent=new Intent(FunRouletteActivity.this,ZoomKeypadActivity.class);
         			//startActivity(call_zoomkeypad_activity_intent);
            /* to redirect to another MenuActivity containing user balance with it ends  */
				}
			});

		/* third row ends here  */



		    
		    
		    int fourth_row_height=(int)(third_row_total_height-third_row_height);
	        int fourth_row_width=(int)(screenWidthDp);
		   
		/*  fourth row starts here  */
	        fourth_row_layout=(LinearLayout)mainlinearlayout.findViewById(R.id.fourth_row);
	        fourth_row_layout.setLayoutParams(new LinearLayout.LayoutParams( fourth_row_width,fourth_row_height));
	        fourth_row_layout.setOrientation(LinearLayout.HORIZONTAL);
			//fourth_row_layout.setBackgroundColor(Color.RED);
			
			/*fourth row first column starts here*/
			int fourthrow_firstcolumn_width=(int)(fourth_row_width*0.12);
			txt_bottom_won_amount_layout=(LinearLayout)fourth_row_layout.findViewById(R.id.txt_bottom_won_amount_layout);
			txt_bottom_won_amount_layout.setLayoutParams(new LinearLayout.LayoutParams(fourthrow_firstcolumn_width, fourth_row_height));
			txt_bottom_won_amount_layout.setOrientation(LinearLayout.VERTICAL);
			//txt_bottom_won_amount_layout.setBackgroundColor(Color.YELLOW);
			
					/*To set textview in fourthrow first vertical linear layout starts here*/
			        txt_bottom_won_amount=(TextView)txt_bottom_won_amount_layout.findViewById(R.id.txt_bottom_won_amount);
					txt_bottom_won_amount.setText("0");
					txt_bottom_won_amount.setTextColor(Color.WHITE);
					txt_bottom_won_amount.setGravity(Gravity.CENTER);
					txt_bottom_won_amount.setTextSize(getResources().getDimension(R.dimen.targetfourthrowtextsize));
					txt_bottom_won_amount.setPadding(30,6,0,5);
					/*To set textview in fourthrow first vertical linear layout ends here*/
			
			/*fourth row first column ends here*/
			
			/*fourth row second column starts here*/
			int fourthrow_secondcolumn_width=(int)(fourth_row_width*0.75);
			txt_bottom_bet_status_layout=(LinearLayout)fourth_row_layout.findViewById(R.id.txt_bottom_bet_status_layout);
			txt_bottom_bet_status_layout.setLayoutParams(new LinearLayout.LayoutParams(fourthrow_secondcolumn_width, fourth_row_height));
			txt_bottom_bet_status_layout.setOrientation(LinearLayout.VERTICAL);
			//txt_bottom_bet_status_layout.setBackgroundColor(Color.WHITE);
			
					/*To set textview in fourthrow second vertical linear layout starts here*/
			        txt_bottom_bet_status=(TextView) txt_bottom_bet_status_layout.findViewById(R.id.txt_bottom_bet_status);
					txt_bottom_bet_status.setText("Welcome to the funroulette screen");
					txt_bottom_bet_status.setTextColor(Color.WHITE);
					txt_bottom_bet_status.setGravity(Gravity.CENTER);
					txt_bottom_bet_status.setTextSize(getResources().getDimension(R.dimen.targetfourthrowtextsize));
					txt_bottom_bet_status.setPadding(0,0,20,8);
					/*To set textview in fourthrow second vertical linear layout ends here*/
			/*fourth row second column ends here*/
			
			/*fourth row third column starts here*/
			int fourthrow_thirdcolumn_width=(int)(fourth_row_width*0.12);
			btn_exit_layout=(LinearLayout)fourth_row_layout.findViewById(R.id.btn_exit_layout);
			btn_exit_layout.setLayoutParams(new LinearLayout.LayoutParams(fourthrow_thirdcolumn_width, fourth_row_height));
			btn_exit_layout.setOrientation(LinearLayout.VERTICAL);
			//btn_exit_layout.setBackgroundColor(Color.GREEN);
			btn_exit_layout.setPadding(23, 0, 0, 0);
			
					/*To set imageview in fourthrow third vertical linear layout starts here*/
					btn_exit=(ImageView)btn_exit_layout.findViewById(R.id.btn_exit);
					//btn_exit.setBackgroundResource(R.drawable.exitglow);
					btn_exit.setOnClickListener(new OnClickListener() {
				   		
						@Override
						public void onClick(View v) {
								Toast.makeText(v.getContext(), "Exit clicked", Toast.LENGTH_LONG).show();
								Intent intent_return_to_menu_activity=new Intent();
								startActivity(intent_return_to_menu_activity);
						}
					});
					/*To set imageview in fourthrow third vertical linear layout ends here*/
			/*fourth row third column ends here*/
		
			
		    
		/* fourth row ends here  */

	}
	
	
	/*to initialize roulette_increment_bet_keypad starts here*/
	private void  initialize_increment_bet_keypad(LinearLayout roulette_increment_bet_layout,int columnonewidth,int secondrow_firstcolumn3_height)
	{
		roulette_increment_bet_layout.setLayoutParams(new LinearLayout.LayoutParams(columnonewidth,secondrow_firstcolumn3_height));
		
		/*finding horizontal rows for 1,5,10,50,100,500,1000,5000 textboxes layouts starts here*/
		int roulette_increment_bet_horizontal_row_height=(int)(secondrow_firstcolumn3_height*0.10);
		int roulette_increment_bet_horizontal_row_width=(int)(columnonewidth);
		
		final LinearLayout roulette_first_empty_horizontal_row=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_increment_bet_empty_layout_before_1_5_10_50);
		roulette_first_empty_horizontal_row.setLayoutParams(new LayoutParams(roulette_increment_bet_horizontal_row_width,(int)(roulette_increment_bet_horizontal_row_height*1.80)));
		//roulette_first_empty_horizontal_row.setBackgroundColor(Color.GREEN);
		
		final LinearLayout roulette_second_horizontal_row=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_increment_bet_layout_1_5_10_50);
		roulette_second_horizontal_row.setLayoutParams(new LayoutParams(roulette_increment_bet_horizontal_row_width,(int)(roulette_increment_bet_horizontal_row_height*3.60)));
		//roulette_second_horizontal_row.setBackgroundColor(Color.RED);
		
		final LinearLayout roulette_empty_third_horizontal_row=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_increment_bet_empty_layout_before_100_500_1000_5000);
		roulette_empty_third_horizontal_row.setLayoutParams(new LayoutParams(roulette_increment_bet_horizontal_row_width,(int)(roulette_increment_bet_horizontal_row_height*1.70)));
		//roulette_empty_third_horizontal_row.setBackgroundColor(Color.RED);
		
		final LinearLayout roulette_fourth_horizontal_row=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_increment_bet_layout_100_500_1000_5000);
		roulette_fourth_horizontal_row.setLayoutParams(new LayoutParams(roulette_increment_bet_horizontal_row_width,(int)(roulette_increment_bet_horizontal_row_height*3.80)));
		//roulette_fourth_horizontal_row.setBackgroundColor(Color.BLUE);
		/*finding horizontal rows for 1,5,10,50,100,500,1000,5000 textboxes layouts ends here*/
		
		/*finding textboxes vertical empty layout for 1,5,10,50,100,500,1000,5000 starts here*/
		final LinearLayout roulette_empty_before_1_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_before_1_layout);
		roulette_empty_before_1_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.05) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_before_1_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_1_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_1_layout);
		roulette_empty_after_1_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_1_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_5_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_5_layout);
		roulette_empty_after_5_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_5_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_10_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_10_layout);
		roulette_empty_after_10_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_10_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_50_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_50_layout);
		roulette_empty_after_50_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_50_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_before_100_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_before_100_layout);
		roulette_empty_before_100_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_before_100_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_100_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_100_layout);
		roulette_empty_after_100_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_100_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_500_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_500_layout);
		roulette_empty_after_500_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_500_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_1000_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_1000_layout);
		roulette_empty_after_1000_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_1000_layout.setBackgroundColor(Color.CYAN);
		
		final LinearLayout roulette_empty_after_5000_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_empty_after_5000_layout);
		roulette_empty_after_5000_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.04) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_empty_after_5000_layout.setBackgroundColor(Color.CYAN);
		
		
		/*finding textboxes vertical empty layout for 1,5,10,50,100,500,1000,5000 ends here*/
		
		/*finding textboxes vertical layout for 1,5,10,50,100,500,1000,5000 starts here*/
		final LinearLayout roulette_txt_1_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_1_layout);
		roulette_txt_1_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.18) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_1_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_5_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_5_layout);
		roulette_txt_5_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.21) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_5_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_10_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_10_layout);
		roulette_txt_10_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_10_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_50_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_50_layout);
		roulette_txt_50_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_50_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_100_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_100_layout);
		roulette_txt_100_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_100_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_500_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_500_layout);
		roulette_txt_500_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_500_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_1000_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_1000_layout);
		roulette_txt_1000_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_1000_layout.setBackgroundColor(Color.BLUE);
		
		final LinearLayout roulette_txt_5000_layout=(LinearLayout) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_5000_layout);
		roulette_txt_5000_layout.setLayoutParams(new LayoutParams((int)(roulette_increment_bet_horizontal_row_width*0.20) ,(int)(roulette_increment_bet_horizontal_row_height*4.05)));
		//roulette_txt_5000_layout.setBackgroundColor(Color.BLUE);
		/*finding textboxes vertical layout for 1,5,10,50,100,500,1000,5000 ends here*/
		
		
		
	}
	/*to initialize roulette_increment_bet_keypad ends here*/
	
	
	
      public void Start_Hilighting_Win_Number(int number) {
    	  
			   Toast.makeText(getApplicationContext(), "highlight number here....", Toast.LENGTH_SHORT).show();
    	  String keylayoutname;
    	  if (number == 38)
          {
    		  keylayoutname="layout_"+"0";
          }
    	  else if (number == 37)
          {
    		  keylayoutname="layout_"+"00";
          }
    	  else
    	  {
			  keylayoutname="layout_"+number+"";
    	  }
    	  
		    /* starts converting String to LinearLayout by finding on based of its name*/
			String resourceName = keylayoutname;
		    int resourceID = getResources().getIdentifier(resourceName, "id",getPackageName());
		    
		    if (resourceID != 0) {
		        TextView tv = (TextView) findViewById(resourceID);
		        if (tv != null) {
		            // Take action on TextView tv here...
		              tv.setBackgroundColor(Color.RED);
		        }
		        else
		        {
		        	
		        }
		    }
			/* ends converting String to LinearLayout by finding on based of its name*/
	
      }
	
	
	
	
	LinearLayout layout_00,layout_0,layout_3,layout_6,layout_9,layout_12,layout_15,layout_18,layout_21,layout_24,layout_27,layout_30,layout_33,layout_36,
	layout_2,layout_5,layout_8,layout_11,layout_14,layout_17,layout_20,layout_23,layout_26,layout_29,layout_32,layout_35,
	layout_1,layout_4,layout_7,layout_10,layout_13,layout_16,layout_19,layout_22,layout_25,layout_28,layout_31,layout_34;
	//to initialize keypad
	private void initialize_keypad(LinearLayout main_thirdrowlayout,int third_row_width,int third_row_height)
	{
		/* main linear layout properties starts here*/ 
		main_thirdrowlayout.setLayoutParams(new LinearLayout.LayoutParams(third_row_width,third_row_height));
		    
		/*first_horizontal_main_row starts here*/
    	int first_horizontal_main_row_height=(int)(third_row_height*0.052);
    	LinearLayout first_horizontal_main_row=(LinearLayout) main_thirdrowlayout.findViewById(R.id.first_horizontal_main_row);
    	first_horizontal_main_row.setLayoutParams(new LayoutParams(third_row_width, first_horizontal_main_row_height));
    	//first_horizontal_main_row.setBackgroundColor(Color.MAGENTA);
    	/*first_horizontal_main_row ends here*/   
    	
    	   
    	/*second_horizontal_main_row starts here*/
    	int second_horizontal_main_row_height=(int)(third_row_height*0.90);
    	LinearLayout second_horizontal_main_row=(LinearLayout) main_thirdrowlayout.findViewById(R.id.second_horizontal_main_row);
    	second_horizontal_main_row.setLayoutParams(new LayoutParams(third_row_width, second_horizontal_main_row_height));
    	//second_horizontal_main_row.setBackgroundColor(Color.CYAN);
    	
    	
    	/*second_horizontal_main_row_firstvertical starts here*/
		int second_horizontal_main_row_firstvertical_height=second_horizontal_main_row_height;
		int second_horizontal_main_row_firstvertical_width=(int)(third_row_width*0.012);
		LinearLayout second_horizontal_main_row_firstvertical=(LinearLayout) second_horizontal_main_row.findViewById(R.id.second_horizontal_main_row_firstvertical);
		second_horizontal_main_row_firstvertical.setLayoutParams(new LayoutParams(second_horizontal_main_row_firstvertical_width, second_horizontal_main_row_firstvertical_height));
		//second_horizontal_main_row_firstvertical.setBackgroundColor(Color.GREEN);
		/*second_horizontal_main_row_firstvertical ends here*/
		
		
		/*second_horizontal_main_row_secondvertical starts here*/
		int second_horizontal_main_row_secondvertical_height=second_horizontal_main_row_height;
		int second_horizontal_main_row_secondvertical_width=(int)(third_row_width*0.98);
		LinearLayout second_horizontal_main_row_secondvertical=(LinearLayout) second_horizontal_main_row.findViewById(R.id.second_horizontal_main_row_secondvertical);
		second_horizontal_main_row_secondvertical.setLayoutParams(new LayoutParams(second_horizontal_main_row_secondvertical_width, second_horizontal_main_row_secondvertical_height));
		//second_horizontal_main_row_secondvertical.setBackgroundColor(Color.BLUE);
		
		
		
			/*horizon1 starts here*/
			int horizon1_height=(int)(second_horizontal_main_row_height*0.63);
			int horizon1_width=second_horizontal_main_row_secondvertical_width;
			LinearLayout horizon1=(LinearLayout) second_horizontal_main_row_secondvertical.findViewById(R.id.horizon1);
			horizon1.setLayoutParams(new LayoutParams(horizon1_width, horizon1_height));
			//horizon1.setBackgroundColor(Color.RED);
			
			
					/*horizon1_vertical1 starts here*/
					int horizon1_vertical1_height=horizon1_height;
					int horizon1_vertical1_width=(int)(horizon1_width*0.070);
					LinearLayout horizon1_vertical1=(LinearLayout) horizon1.findViewById(R.id.horizon1_vertical1);
					horizon1_vertical1.setLayoutParams(new LayoutParams(horizon1_vertical1_width, horizon1_vertical1_height));
					//horizon1_vertical1.setBackgroundColor(Color.YELLOW);
					    
					
					/*layout_00 starts here*/
					int layout_00_height=(int)(horizon1_vertical1_height*0.50);
					int layout_00_width=horizon1_vertical1_width;
					layout_00=(LinearLayout) horizon1_vertical1.findViewById(R.id.layout_00);
					layout_00.setLayoutParams(new LayoutParams(layout_00_width, layout_00_height));
					//layout_00.setBackgroundResource(R.drawable.imgglow_00);
					//layout_00.setBackgroundColor(Color.MAGENTA);
					/*layout_00 ends here*/
					  
					
					/*layout_0 starts here*/
					int layout_0_height=(int)(horizon1_vertical1_height*0.51);
					int layout_0_width=horizon1_vertical1_width;
					layout_0=(LinearLayout) horizon1_vertical1.findViewById(R.id.layout_0);
					layout_0.setLayoutParams(new LayoutParams(layout_0_width, layout_0_height));
					//layout_0.setBackgroundResource(R.drawable.imgglow_0);
					//layout_0.setBackgroundColor(Color.BLUE);
					/*layout_0 ends here*/
					/*horizon1_vertical1 ends here*/
					   
					
					/*horizon1_vertical2 starts here*/
					int horizon1_vertical2_height=horizon1_height;
					int horizon1_vertical2_width=(int)(horizon1_width*0.94);
					LinearLayout horizon1_vertical2=(LinearLayout) horizon1.findViewById(R.id.horizon1_vertical2);
					horizon1_vertical2.setLayoutParams(new LayoutParams(horizon1_vertical2_width, horizon1_vertical2_height));
					//horizon1_vertical2.setBackgroundColor(Color.CYAN);
					  
					   
						/*horizon1_vertical2_horizon1 starts here*/
						int horizon1_vertical2_horizon1_height=(int)(horizon1_vertical2_height*0.32);
						int horizon1_vertical2_horizon1_width=horizon1_vertical2_width;
						LinearLayout horizon1_vertical2_horizon1=(LinearLayout) horizon1_vertical2.findViewById(R.id.horizon1_vertical2_horizon1);
						horizon1_vertical2_horizon1.setLayoutParams(new LayoutParams(horizon1_vertical2_horizon1_width, horizon1_vertical2_horizon1_height));
						//horizon1_vertical2_horizon1.setBackgroundColor(Color.RED);
						
						   
						/*layout_3 starts here*/
						int layout_3_height=horizon1_vertical2_horizon1_height;
						int layout_3_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_3=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_3);
						layout_3.setLayoutParams(new LayoutParams(layout_3_width, layout_3_height));
						//layout_3.setBackgroundColor(Color.GREEN);
						/*layout_3 ends here*/
						
						 
						/*layout_6 starts here*/
						int layout_6_height=horizon1_vertical2_horizon1_height;
						int layout_6_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_6=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_6);
						layout_6.setLayoutParams(new LayoutParams(layout_6_width, layout_6_height));
						//layout_6.setBackgroundColor(Color.GREEN);
						/*layout_6 ends here*/
						
						/*layout_9 starts here*/
						int layout_9_height=horizon1_vertical2_horizon1_height;
						int layout_9_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_9=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_9);
						layout_9.setLayoutParams(new LayoutParams(layout_9_width, layout_9_height));
						//layout_9.setBackgroundColor(Color.GREEN);
						/*layout_9 ends here*/
						
						/*layout_12 starts here*/
						int layout_12_height=horizon1_vertical2_horizon1_height;
						int layout_12_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_12=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_12);
						layout_12.setLayoutParams(new LayoutParams(layout_12_width, layout_12_height));
						//layout_12.setBackgroundColor(Color.MAGENTA);
						/*layout_12 ends here*/
						
						/*layout_15 starts here*/
						int layout_15_height=horizon1_vertical2_horizon1_height;
						int layout_15_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_15=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_15);
						layout_15.setLayoutParams(new LayoutParams(layout_15_width, layout_15_height));
						//layout_15.setBackgroundColor(Color.MAGENTA);
						/*layout_15 ends here*/
						                            
						/*layout_18 starts here*/
						int layout_18_height=horizon1_vertical2_horizon1_height;
						int layout_18_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_18=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_18);
						layout_18.setLayoutParams(new LayoutParams(layout_18_width, layout_18_height));
						//layout_18.setBackgroundColor(Color.GREEN);
						/*layout_18 ends here*/
						      
						/*layout_21 starts here*/
						int layout_21_height=horizon1_vertical2_horizon1_height;
						int layout_21_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_21=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_21);
						layout_21.setLayoutParams(new LayoutParams(layout_21_width, layout_21_height));
						//layout_21.setBackgroundColor(Color.MAGENTA);
						/*layout_21 ends here*/
						
						/*layout_24 starts here*/
						int layout_24_height=horizon1_vertical2_horizon1_height;
						int layout_24_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_24=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_24);
						layout_24.setLayoutParams(new LayoutParams(layout_24_width, layout_24_height));
						//layout_24.setBackgroundColor(Color.GREEN);
						/*layout_24 ends here*/
						  
						/*layout_27 starts here*/
						int layout_27_height=horizon1_vertical2_horizon1_height;
						int layout_27_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_27=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_27);
						layout_27.setLayoutParams(new LayoutParams(layout_27_width, layout_27_height));
						//layout_27.setBackgroundColor(Color.MAGENTA);
						/*layout_27 ends here*/
						
						/*layout_30 starts here*/
						int layout_30_height=horizon1_vertical2_horizon1_height;
						int layout_30_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_30=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_30);
						layout_30.setLayoutParams(new LayoutParams(layout_30_width, layout_30_height));
						//layout_30.setBackgroundColor(Color.GREEN);
						/*layout_30 ends here*/
						
						/*layout_33 starts here*/
						int layout_33_height=horizon1_vertical2_horizon1_height;
						int layout_33_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_33=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_33);
						layout_33.setLayoutParams(new LayoutParams(layout_33_width, layout_33_height));
						//layout_33.setBackgroundColor(Color.MAGENTA);
						/*layout_33 ends here*/
						
						/*layout_36 starts here*/
						int layout_36_height=horizon1_vertical2_horizon1_height;
						int layout_36_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_36=(LinearLayout) horizon1_vertical2_horizon1.findViewById(R.id.layout_36);
						layout_36.setLayoutParams(new LayoutParams(layout_36_width, layout_36_height));
						//layout_36.setBackgroundColor(Color.GREEN);
						/*layout_36 ends here*/
						
						
						
						
						/*horizon1_vertical2_horizon1 ends here*/
						
						/*horizon1_vertical2_horizon2 starts here*/
						int horizon1_vertical2_horizon2_height=(int)(horizon1_vertical2_height*0.35);
						int horizon1_vertical2_horizon2_width=horizon1_vertical2_width;
						LinearLayout horizon1_vertical2_horizon2=(LinearLayout) horizon1_vertical2.findViewById(R.id.horizon1_vertical2_horizon2);
						horizon1_vertical2_horizon2.setLayoutParams(new LayoutParams(horizon1_vertical2_horizon2_width, horizon1_vertical2_horizon2_height));
						//horizon1_vertical2_horizon2.setBackgroundColor(Color.GREEN);
						
						/*layout_2 starts here*/
						int layout_2_height=horizon1_vertical2_horizon2_height;
						int layout_2_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_2=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_2);
						layout_2.setLayoutParams(new LayoutParams(layout_2_width, layout_2_height));
						//layout_2.setBackgroundColor(Color.GREEN);
						/*layout_2 ends here*/
						
						/*layout_5 starts here*/
						int layout_5_height=horizon1_vertical2_horizon2_height;
						int layout_5_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_5=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_5);
						layout_5.setLayoutParams(new LayoutParams(layout_5_width, layout_5_height));
						//layout_5.setBackgroundColor(Color.GREEN);
						/*layout_5 ends here*/
						
						/*layout_8 starts here*/
						int layout_8_height=horizon1_vertical2_horizon2_height;
						int layout_8_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_8=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_8);
						layout_8.setLayoutParams(new LayoutParams(layout_8_width, layout_8_height));
						//layout_8.setBackgroundColor(Color.GREEN);
						/*layout_8 ends here*/
						
						/*layout_11 starts here*/
						int layout_11_height=horizon1_vertical2_horizon2_height;
						int layout_11_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_11=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_11);
						layout_11.setLayoutParams(new LayoutParams(layout_11_width, layout_11_height));
						//layout_11.setBackgroundColor(Color.MAGENTA);
						/*layout_11 ends here*/
						
						/*layout_14 starts here*/
						int layout_14_height=horizon1_vertical2_horizon2_height;
						int layout_14_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_14=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_14);
						layout_14.setLayoutParams(new LayoutParams(layout_14_width, layout_14_height));
						//layout_14.setBackgroundColor(Color.MAGENTA);
						/*layout_14 ends here*/
						
						/*layout_17 starts here*/
						int layout_17_height=horizon1_vertical2_horizon2_height;
						int layout_17_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_17=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_17);
						layout_17.setLayoutParams(new LayoutParams(layout_17_width, layout_17_height));
						//layout_17.setBackgroundColor(Color.GREEN);
						/*layout_17 ends here*/
						
						/*layout_20 starts here*/
						int layout_20_height=horizon1_vertical2_horizon2_height;
						int layout_20_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_20=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_20);
						layout_20.setLayoutParams(new LayoutParams(layout_20_width, layout_20_height));
						//layout_20.setBackgroundColor(Color.MAGENTA);
						/*layout_20 ends here*/
						
						/*layout_23 starts here*/
						int layout_23_height=horizon1_vertical2_horizon2_height;
						int layout_23_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_23=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_23);
						layout_23.setLayoutParams(new LayoutParams(layout_23_width, layout_23_height));
						//layout_23.setBackgroundColor(Color.GREEN);
						/*layout_23 ends here*/
						  
						/*layout_26 starts here*/
						int layout_26_height=horizon1_vertical2_horizon2_height;
						int layout_26_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_26=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_26);
						layout_26.setLayoutParams(new LayoutParams(layout_26_width, layout_26_height));
						//layout_26.setBackgroundColor(Color.MAGENTA);
						/*layout_26 ends here*/
						
						/*layout_29 starts here*/
						int layout_29_height=horizon1_vertical2_horizon2_height;
						int layout_29_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_29=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_29);
						layout_29.setLayoutParams(new LayoutParams(layout_29_width, layout_29_height));
						//layout_29.setBackgroundColor(Color.GREEN);
						/*layout_29 ends here*/
						
						/*layout_32 starts here*/
						int layout_32_height=horizon1_vertical2_horizon2_height;
						int layout_32_width=(int)(horizon1_vertical2_horizon1_width*0.08);
						layout_32=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_32);
						layout_32.setLayoutParams(new LayoutParams(layout_32_width, layout_32_height));
						//layout_32.setBackgroundColor(Color.MAGENTA);
						/*layout_32 ends here*/
						
						/*layout_35 starts here*/
						int layout_35_height=horizon1_vertical2_horizon2_height;
						int layout_35_width=(int)(horizon1_vertical2_horizon1_width*0.07);
						layout_35=(LinearLayout) horizon1_vertical2_horizon2.findViewById(R.id.layout_35);
						layout_35.setLayoutParams(new LayoutParams(layout_35_width, layout_35_height));
						//layout_35.setBackgroundColor(Color.GREEN);
						/*layout_35 ends here*/
						
						   
						
						/*horizon1_vertical2_horizon2 ends here*/
					
						
						/*horizon1_vertical2_horizon3 starts here*/
						int horizon1_vertical2_horizon3_height=(int)(horizon1_vertical2_height*0.35);
						int horizon1_vertical2_horizon3_width=horizon1_vertical2_width;
						LinearLayout horizon1_vertical2_horizon3=(LinearLayout) horizon1_vertical2.findViewById(R.id.horizon1_vertical2_horizon3);
						horizon1_vertical2_horizon3.setLayoutParams(new LayoutParams(horizon1_vertical2_horizon3_width, horizon1_vertical2_horizon3_height));
						//horizon1_vertical2_horizon3.setBackgroundColor(Color.RED);
						
						
						/*layout_1 starts here*/
						int layout_1_height=horizon1_vertical2_horizon3_height;
						int layout_1_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_1=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_1);
						layout_1.setLayoutParams(new LayoutParams(layout_1_width, layout_1_height));
						//layout_1.setBackgroundColor(Color.GREEN);
						/*layout_1 ends here*/
						
						/*layout_4 starts here*/
						int layout_4_height=horizon1_vertical2_horizon3_height;
						int layout_4_width=(int)(horizon1_vertical2_horizon3_width*0.07);
						layout_4=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_4);
						layout_4.setLayoutParams(new LayoutParams(layout_4_width, layout_4_height));
						//layout_4.setBackgroundColor(Color.GREEN);
						/*layout_4 ends here*/
						
						/*layout_7 starts here*/
						int layout_7_height=horizon1_vertical2_horizon3_height;
						int layout_7_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_7=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_7);
						layout_7.setLayoutParams(new LayoutParams(layout_7_width, layout_7_height));
						//layout_7.setBackgroundColor(Color.GREEN);
						/*layout_8 ends here*/
						
						/*layout_10 starts here*/
						int layout_10_height=horizon1_vertical2_horizon3_height;
						int layout_10_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_10=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_10);
						layout_10.setLayoutParams(new LayoutParams(layout_10_width, layout_10_height));
						//layout_10.setBackgroundColor(Color.MAGENTA);
						/*layout_10 ends here*/
						
						/*layout_13 starts here*/
						int layout_13_height=horizon1_vertical2_horizon3_height;
						int layout_13_width=(int)(horizon1_vertical2_horizon3_width*0.07);
						layout_13=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_13);
						layout_13.setLayoutParams(new LayoutParams(layout_13_width, layout_13_height));
						//layout_13.setBackgroundColor(Color.MAGENTA);
						/*layout_13 ends here*/
						
						/*layout_16 starts here*/
						int layout_16_height=horizon1_vertical2_horizon3_height;
						int layout_16_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_16=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_16);
						layout_16.setLayoutParams(new LayoutParams(layout_16_width, layout_16_height));
						//layout_16.setBackgroundColor(Color.GREEN);
						/*layout_16 ends here*/
						
						/*layout_19 starts here*/
						int layout_19_height=horizon1_vertical2_horizon3_height;
						int layout_19_width=(int)(horizon1_vertical2_horizon3_width*0.07);
						layout_19=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_19);
						layout_19.setLayoutParams(new LayoutParams(layout_19_width, layout_19_height));
						//layout_19.setBackgroundColor(Color.MAGENTA);
						/*layout_19 ends here*/
						
						/*layout_22 starts here*/
						int layout_22_height=horizon1_vertical2_horizon3_height;
						int layout_22_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_22=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_22);
						layout_22.setLayoutParams(new LayoutParams(layout_22_width, layout_22_height));
						//layout_22.setBackgroundColor(Color.GREEN);
						/*layout_22 ends here*/
						  
						/*layout_25 starts here*/
						int layout_25_height=horizon1_vertical2_horizon3_height;
						int layout_25_width=(int)(horizon1_vertical2_horizon3_width*0.07);
						layout_25=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_25);
						layout_25.setLayoutParams(new LayoutParams(layout_25_width, layout_25_height));
						//layout_25.setBackgroundColor(Color.MAGENTA);
						/*layout_25 ends here*/
						
						/*layout_28 starts here*/
						int layout_28_height=horizon1_vertical2_horizon3_height;
						int layout_28_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_28=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_28);
						layout_28.setLayoutParams(new LayoutParams(layout_28_width, layout_28_height));
						//layout_28.setBackgroundColor(Color.GREEN);
						/*layout_28 ends here*/
						
						/*layout_31 starts here*/
						int layout_31_height=horizon1_vertical2_horizon3_height;
						int layout_31_width=(int)(horizon1_vertical2_horizon3_width*0.08);
						layout_31=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_31);
						layout_31.setLayoutParams(new LayoutParams(layout_31_width, layout_31_height));
						//layout_31.setBackgroundColor(Color.MAGENTA);
						/*layout_31 ends here*/
						
						/*layout_34 starts here*/
						int layout_34_height=horizon1_vertical2_horizon3_height;
						int layout_34_width=(int)(horizon1_vertical2_horizon3_width*0.07);
						layout_34=(LinearLayout) horizon1_vertical2_horizon3.findViewById(R.id.layout_34);
						layout_34.setLayoutParams(new LayoutParams(layout_34_width, layout_34_height));
						//layout_34.setBackgroundColor(Color.GREEN);
						/*layout_34 ends here*/
						/*horizon1_vertical2_horizon3 ends here*/
					/*horizon1_vertical2 ends here*/
			
			/*horizon1 ends here*/
			
			/*horizon2 starts here*/
			int horizon2_height=(int)(second_horizontal_main_row_height*0.19);
			int horizon2_width=second_horizontal_main_row_secondvertical_width;
			LinearLayout horizon2=(LinearLayout) second_horizontal_main_row_secondvertical.findViewById(R.id.horizon2);
			horizon2.setLayoutParams(new LayoutParams(horizon2_width, horizon2_height));
			//horizon2.setBackgroundColor(Color.GREEN);
			/*horizon2 ends here*/
			
			/*horizon3 starts here*/
			int horizon3_height=(int)(second_horizontal_main_row_height*0.18);
			int horizon3_width=second_horizontal_main_row_secondvertical_width;
			LinearLayout horizon3=(LinearLayout) second_horizontal_main_row_secondvertical.findViewById(R.id.horizon3);
			horizon3.setLayoutParams(new LayoutParams(horizon3_width, horizon3_height));
			//horizon3.setBackgroundColor(Color.GRAY);
			/*horizon3 ends here*/
    	/*second_horizontal_main_main_row_secondvertical ends here*/
		
		/*second_horizontal_main_row_thirdvertical starts here*/
		int second_horizontal_main_row_thirdvertical_height=second_horizontal_main_row_height;
		int second_horizontal_main_row_thirdvertical_width=(int)(third_row_width*0.01);
		LinearLayout second_horizontal_main_row_thirdvertical=(LinearLayout) second_horizontal_main_row.findViewById(R.id.second_horizontal_main_row_thirdvertical);
		second_horizontal_main_row_thirdvertical.setLayoutParams(new LayoutParams(second_horizontal_main_row_thirdvertical_width,second_horizontal_main_row_thirdvertical_height));
		//second_horizontal_main_row_thirdvertical.setBackgroundColor(Color.RED);
		/*second_horizontal_main_row_thirdvertical ends here*/
    	/*second_horizontal_main_row ends here*/
    	
    	/*third_horizontal_main_row starts here*/
    	int third_horizontal_main_row_height=(int)(third_row_height*0.05);
    	LinearLayout third_horizontal_main_row=(LinearLayout) main_thirdrowlayout.findViewById(R.id.third_horizontal_main_row);
    	third_horizontal_main_row.setLayoutParams(new LayoutParams(third_row_width, third_horizontal_main_row_height));
    	//third_horizontal_main_row.setBackgroundColor(Color.MAGENTA);
    	/*third_horizontal_main_row ends here*/
		
		/* main linear layout properties ends here*/ 
		  
	}

	
	
	
	public class Get_FunRoulette_GameDetails_Class extends AsyncTask<Void, Void, Void>
	{
		  int status=0;
          Service_Client_Interaction client=new Service_Client_Interaction();
		  @Override
		  protected Void doInBackground(Void... arg0)
		  {
			  Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		      try
		      {
		    	  client.Get_FunRoulette_Game_Details();
		    	  status=1;
	          }
		      catch (Exception e)   {
		            e.printStackTrace();
		         }
		      return_status_async_GAMEDETAILS=status;
		      return null;
		   }

		   protected void onPostExecute(Void result) {		  }

		 }

	
	

	class A implements OnClickListener
	{
		TextView roulette_txt_1,roulette_txt_5,roulette_txt_10,roulette_txt_50,roulette_txt_100,roulette_txt_500,roulette_txt_1000,roulette_txt_5000;
		
		private void calculate_keypad_bet(LinearLayout roulette_increment_bet_layout )
		{
			roulette_txt_1=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_1);
			roulette_txt_1.setClickable(true);
			roulette_txt_1.setOnClickListener(this);
			
			roulette_txt_5=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_5);
			roulette_txt_5.setClickable(true);
			roulette_txt_5.setOnClickListener(this);
			
			roulette_txt_10=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_10);
			roulette_txt_10.setClickable(true);
			roulette_txt_10.setOnClickListener(this);
			
			roulette_txt_50=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_50);
			roulette_txt_50.setClickable(true);
			roulette_txt_50.setOnClickListener(this);
			
			roulette_txt_100=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_100);
			roulette_txt_100.setClickable(true);
			roulette_txt_100.setOnClickListener(this);
			
			roulette_txt_500=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_500);
			roulette_txt_500.setClickable(true);
			roulette_txt_500.setOnClickListener(this);
			
			roulette_txt_1000=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_1000);
			roulette_txt_1000.setClickable(true);
			roulette_txt_1000.setOnClickListener(this);
			
			roulette_txt_5000=(TextView) roulette_increment_bet_layout.findViewById(R.id.roulette_txt_5000);
			roulette_txt_5000.setClickable(true);
			roulette_txt_5000.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			//int current_bet_amount=1;
			switch(v.getId())
			{
			case R.id.roulette_txt_1:
				current_bet_amount=1;
				highlight_increment_bet_keypad(roulette_txt_1);
				break;
			case R.id.roulette_txt_5:
				current_bet_amount=5;	 
				highlight_increment_bet_keypad(roulette_txt_5);
				break;
			case R.id.roulette_txt_10:
				current_bet_amount=10;	 
				highlight_increment_bet_keypad(roulette_txt_10);
				break;
			case R.id.roulette_txt_50:
				current_bet_amount=50;	 
				highlight_increment_bet_keypad(roulette_txt_50);
				break;
			case R.id.roulette_txt_100:
				current_bet_amount=100;	 
				highlight_increment_bet_keypad(roulette_txt_100);
				break;
			case R.id.roulette_txt_500:
				current_bet_amount=500;	 
				highlight_increment_bet_keypad(roulette_txt_500);
				break;
			case R.id.roulette_txt_1000:
				current_bet_amount=1000;	 
				highlight_increment_bet_keypad(roulette_txt_1000);
				break;
			case R.id.roulette_txt_5000:
				current_bet_amount=5000;	 
				highlight_increment_bet_keypad(roulette_txt_5000);
				break;				
		 }
			
			Toast.makeText(getApplicationContext(),current_bet_amount+"Clicked", Toast.LENGTH_SHORT).show();
	}
	    
		private void highlight_increment_bet_keypad(TextView textview_to_highlight)
		{
			if(textview_to_highlight.getId()==R.id.roulette_txt_1)
			{
			textview_to_highlight.setBackgroundResource(R.drawable.onecoin);
			roulette_txt_5.setBackgroundResource(0);
			roulette_txt_10.setBackgroundResource(0);
			roulette_txt_50.setBackgroundResource(0);
			roulette_txt_100.setBackgroundResource(0);
			roulette_txt_500.setBackgroundResource(0);
			roulette_txt_1000.setBackgroundResource(0);
			roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_5)
			{
				roulette_txt_1.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.fivecoin);
				roulette_txt_10.setBackgroundResource(0);
				roulette_txt_50.setBackgroundResource(0);
				roulette_txt_100.setBackgroundResource(0);
				roulette_txt_500.setBackgroundResource(0);
				roulette_txt_1000.setBackgroundResource(0);
				roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_10)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.tencoin);
				roulette_txt_50.setBackgroundResource(0);
				roulette_txt_100.setBackgroundResource(0);
				roulette_txt_500.setBackgroundResource(0);
				roulette_txt_1000.setBackgroundResource(0);
				roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_50)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				roulette_txt_10.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.fiftycoin);
				roulette_txt_100.setBackgroundResource(0);
				roulette_txt_500.setBackgroundResource(0);
				roulette_txt_1000.setBackgroundResource(0);
				roulette_txt_5000.setBackgroundResource(0);
			}

			else if(textview_to_highlight.getId()==R.id.roulette_txt_100)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				roulette_txt_10.setBackgroundResource(0);
				roulette_txt_50.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.hundredcoin);
				roulette_txt_500.setBackgroundResource(0);
				roulette_txt_1000.setBackgroundResource(0);
				roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_500)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				roulette_txt_10.setBackgroundResource(0);
				roulette_txt_50.setBackgroundResource(0);
				roulette_txt_100.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.fivehundredcoin);
				roulette_txt_1000.setBackgroundResource(0);
				roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_1000)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				roulette_txt_10.setBackgroundResource(0);
				roulette_txt_50.setBackgroundResource(0);
				roulette_txt_100.setBackgroundResource(0);
				roulette_txt_500.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.thousandcoin);
				roulette_txt_5000.setBackgroundResource(0);
			}
			else if(textview_to_highlight.getId()==R.id.roulette_txt_5000)
			{
				roulette_txt_1.setBackgroundResource(0);
				roulette_txt_5.setBackgroundResource(0);
				roulette_txt_10.setBackgroundResource(0);
				roulette_txt_50.setBackgroundResource(0);
				roulette_txt_100.setBackgroundResource(0);
				roulette_txt_500.setBackgroundResource(0);
				roulette_txt_1000.setBackgroundResource(0);
				textview_to_highlight.setBackgroundResource(R.drawable.fivethousandcoin);
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

	private void animateandslideshow()
	{
	//	slidingimage=(ImageView) findViewById(Imageview3);
		tileimageview.setImageResource(Image_ids[currentimageindex%Image_ids.length]);
		currentimageindex++;
	}
	
	private void animateandslideshow_Bet_OK()
	{
		btn_bet_OK.setImageResource(Image_ids_bet_Ok[currentimageindex_bet_Ok%Image_ids_bet_Ok.length]);
/*		if(currentimageindex_bet_Ok%Image_ids_bet_Ok.length==0)
			FunRouletteMessages.button_Glow=true;
		else
			FunRouletteMessages.button_Glow=false;
*/		
		currentimageindex_bet_Ok ++;
	}
	
	private void animateandslideshow_Take_Bet()
	{
		btn_take.setImageResource(Image_ids_take_bet[currentimageindex_Take_Bet%Image_ids_take_bet.length]);
/*		if(currentimageindex_Take_Bet%Image_ids_take_bet.length==0)
			FunRouletteMessages.button_Glow=true;
		else
			FunRouletteMessages.button_Glow=false;
*/		currentimageindex_Take_Bet ++;
		
	}

	private void animateandslideshow_timelayout()
	{
		if(currentimageindex_timelayout%2==0)
		txt_time_layout.setBackgroundResource(R.drawable.clockglow);
		else
			txt_time_layout.setBackgroundResource(0);
		currentimageindex_timelayout ++;
		
	}
	
	
	public void Timer_Bet_OK_Start()
	{
		  boolean mWorkerThread_status=mWorkerThread_timer_Bet_OK_Blink.isAlive();
			if(mWorkerThread_status==false)
	      {
				mWorkerThread_timer_Bet_OK_Blink.start();
				mWorkerThread_timer_Bet_OK_Blink.onLooperPrepared();
	      }
	}
	
	public void Timer_Bet_OK_Stop()
	{
		boolean mWorkerThread_status=mWorkerThread_timer_Bet_OK_Blink.isAlive();
		if(mWorkerThread_status==true)
	    {
			try{
			    myhandler_Timer_Bet_OK.removeCallbacks(myupdateresults_timer_Bet_OK_Blink);
			    mWorkerThread_timer_Bet_OK_Blink.quit();
			}catch(Exception e){
				
			}
	    }
    }

	
	public void Timer_Take_Bet_Start()
	{
		  boolean mWorkerThread_status=mWorkerThread_timer_Take_Bet_Blink.isAlive();
			if(mWorkerThread_status==false)
	      {
				mWorkerThread_timer_Take_Bet_Blink.start();
				mWorkerThread_timer_Take_Bet_Blink.onLooperPrepared();
	      }
	}
	
	public void Timer_Take_Bet_Stop()
	{
		boolean mWorkerThread_status=mWorkerThread_timer_Take_Bet_Blink.isAlive();
		if(mWorkerThread_status==true)
	    {
			try{
			    myhandler_Timer_Take_Bet.removeCallbacks(myupdateresults_timer_Take_Bet_Blink);
			    mWorkerThread_timer_Take_Bet_Blink.quit();
			}catch(Exception e){
				
			}
	    }
    }

	
	public void Timer_timelayout_Start()
	{
		  boolean mWorkerThread_status=mWorkerThread_timer_timelayout_Blink.isAlive();
			if(mWorkerThread_status==false)
	      {
				mWorkerThread_timer_timelayout_Blink.start();
				mWorkerThread_timer_timelayout_Blink.onLooperPrepared();
	      }
	}
	
	public void Timer_timelayout_Stop()
	{
		boolean mWorkerThread_status=mWorkerThread_timer_timelayout_Blink.isAlive();
		if(mWorkerThread_status==true)
	    {
			try{
			    myhandler_Timer_TimeLayout.removeCallbacks(myupdateresults_timer_Timelayout_Blink);
			    mWorkerThread_timer_timelayout_Blink.quit(); 
			}catch(Exception e){
				
			}
	    }
    }
	
	

	
	
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if (mp1 != null) {
		      mp1.release();
		   }
		
		 if (timer_time != null) {
    		 timer_time.cancel();
    		 timer_time = null;
             CurrentTimer.timer_thread_count--;
         }
    	 
    	 if (mp != null) {
		      mp.release();
		   }
    	 
    	 if((CurrentTimer.application_interrupted==false) && ( CurrentTimer.application_interrupted_count==0))
    	 {
    		 CurrentTimer.application_interrupted_count++;
	    	 CurrentTimer.application_interrupted=true;
	    	 Calendar startDateTime = Calendar.getInstance();
	    	 CurrentTimer.date1_onpause= startDateTime.getTimeInMillis();
    	
    	 }
    	 
    	 
    	 
			boolean mWorkerThread_status2=mWorkerThread_slider_funroulette.isAlive();
			if(mWorkerThread_status2==true)
	        {
				try{
				    myhandler_slider_funroulette.removeCallbacks(myupdateresults_slider_funroulette);
	        	    mWorkerThread_slider_funroulette.quit();
				}catch(Exception e){
					
				}
	        }	

			
			
			//ABC obj=new ABC();
		    //obj.calculate_total_bet();
        	//    "bet_Values" contains final string to pass bet array
		   
	}

	
	
	
	
	
	public int[] get_Bet_Values_Array()
    {
   	 
   	 ABC objABC=new ABC();
   	 objABC.calculate_total_bet();
   	 
   	 return bet_Values;
    }
	
	
	@Override
	protected void onStart() {
		super.onStart();
	
		  try
	        {
			  if(return_status_async_GAMEDETAILS==0)
				  Get_FunRoulette_GameDetails();
	        }
	        catch(Exception e)
	        {
	        	
	        }

		  
		  boolean mWorkerThread_status=mWorkerThread_slider_funroulette.isAlive();
			if(mWorkerThread_status==false)
	        {
	              mWorkerThread_slider_funroulette.start();
	              mWorkerThread_slider_funroulette.onLooperPrepared();
	        }

					
				//new Get_FunRoulette_GameDetails_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	
	
	
	@Override
	protected void onStop() {
		super.onStop();
		
		boolean mWorkerThread_status2=mWorkerThread_slider_funroulette.isAlive();
		if(mWorkerThread_status2==true)
        {
			try{
			    myhandler_slider_funroulette.removeCallbacks(myupdateresults_slider_funroulette);
        	    mWorkerThread_slider_funroulette.quit();
			}catch(Exception e){
				
			}
        }	

	}
	
	
	

	private void Get_FunRoulette_GameDetails()
    {        
    	/* To generate transfer datagrid starts here */
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				new Get_FunRoulette_GameDetails_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);	
				//new Get_Transfer_Datagrid_Class().execute();	
			}
		});
	       
    }
       
	
	@Override
	protected void onResume() {
		super.onResume();
		 
		   mainlinearlayout=(LinearLayout) findViewById(R.id.mainlayout);//main layout 
	
	        try {
	        	while(true)
	        	{	
			        	if(return_status_async_GAMEDETAILS==0)
			        	{
			        	}
			        	else if(return_status_async_GAMEDETAILS==1)
			        	{
				        	break;
			        	}
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}

		   //objFunRouletteMessages.Stop_Bet_Ok_Take_Buttons_Group();
 		   
	    	//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
  		   int Time_Seconds=Service_Client_Interaction.Game_Details_FunRoulette.Time_Seconds;
 		   int[] Recent_Values=Service_Client_Interaction.Game_Details_FunRoulette.Recent_Values;
 		   int current_Game_Result_OR_Next_Game_Prepare=Service_Client_Interaction.Game_Details_FunRoulette.current_Game_Result_OR_Next_Game_Prepare;
 		   int game_Id=Service_Client_Interaction.Game_Details_FunRoulette.game_Id;
 		   CurrentTimer.counter=Time_Seconds;
	       initialize_roulette_screen(mainlinearlayout,screenWidthDp,screenHeightDp);
	      
 		  		

			/*code for recent list starts here*/
		       Start(Time_Seconds, Recent_Values, current_Game_Result_OR_Next_Game_Prepare);
		    /*code for recent list ends here*/
		   
		   
		   
		   
		   
		/*code to handle timer while phone call comes starts here*/
	        if((CurrentTimer.application_interrupted==true)&&(CurrentTimer.application_interrupted_count==1) )
	        {
		        CurrentTimer.application_interrupted=false;
		        Calendar startDateTime = Calendar.getInstance();
		        
		        CurrentTimer.date2_onresume= startDateTime.getTimeInMillis();
		        
			   	 long total_time_application_on_wait=CurrentTimer.date2_onresume-CurrentTimer.date1_onpause;
			   	 int hours = (int)total_time_application_on_wait / (60 * 60 * 1000);
		         int minutes = (int) (total_time_application_on_wait / (60 * 1000)); minutes = minutes - 60 * hours;
		         long seconds = total_time_application_on_wait / (1000);
		         long seconds_to_add_in_timer=seconds%60;
		   	 
		   	
			    CurrentTimer.counter-=seconds_to_add_in_timer;
			   	CurrentTimer.counter=CurrentTimer.counter%60;
		   	    CurrentTimer.application_interrupted_count--;
	        }

	        //onResume we start our timer so it can start when the app comes from the background
		    if(CurrentTimer.timer_thread_count==0)     {      startTimer();       }

		 /*code to handle timer while phone call comes ends here*/
		
	}
	
	
    public void startTimer() {
    	
	   	 if (timer_time!=null){
	   		timer_time.cancel();
	   		timer_time = null;
	   	     CurrentTimer.timer_thread_count--;
	   	    }
  	 
      //set a new Timer
	   	{
	   		timer_time = new Timer();
	       CurrentTimer.timer_thread_count++;
	   	}        
	   	//initialize the TimerTask's job
      initializeTimerTask();
     
      timer_time.schedule(timerTask, 1000, 1000); 
  }
   
    public void initializeTimerTask() {
       if(CurrentTimer.timer_thread_count==1)
       {
  		timerTask = new TimerTask() {
  			            @Override
          				public void run() {            					           					
          					  	final String secs=Integer.toString(CurrentTimer.counter);
          					  	final String mins="0";
          						//use a handler to run a toast that shows the current timestamp
          					  	runOnUiThread(new Runnable() {
          												@Override
          												public void run() {
          				
          													
          													
          													 if(CurrentTimer.counter==60)
          													 {
          											    	          if(secs.equalsIgnoreCase("60"))
          											    	          {
          											    	        	 final String strDate1="01:00";
          											    	        	 txt_time.setText(strDate1);
          											    	          }
          											    	          
          											    	          CurrentTimer.counter--;
          													   }
         												     else if(CurrentTimer.counter>=0 && CurrentTimer.counter<=59)
          												     {
          														   final String strDate1=mins+" : "+secs;
          														   txt_time.setText(strDate1);
          														   CurrentTimer.counter--;
          														   
          														   
          											/*start */
          	          														   
          														        if(CurrentTimer.counter==8)
          														        {
          															        currentimageindex_timelayout=0;
        												                    txt_time_layout.setBackgroundResource(0);
          														        }
          														   
		          														if (CurrentTimer.counter == 15 )
		          												        {
		          												            initialise_timelayout_slider();
		          												            Timer_timelayout_Start();
		          												        }
		          												        else if (CurrentTimer.counter == 10)
		          												        {
		          												        			          												                
		          												            if (FunRouletteMessages.enum_Bet_OK_Button_Group != (int)Button_Group.BET_TAKE.button_group_value)
		          												            {
		          												                Bet_Time_Over(true);
		          												                FunRouletteMessages.enum_Bet_OK_Button_Group = -1;
		          												                Timer_timelayout_Stop();
		          												               
		          												                // Timer_Bet_OK_Stop();
		          												                // Timer_Take_Bet_Stop();
		          												             

		          				
		          												                
		                                                                        /* start */
  		          												                LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		                													  	main_thirdrowlayout=(LinearLayout) inflate.inflate(R.layout.activity_funroulette_thirdrowlayout, null);
		                														try
		                														{
		                														initialize_keypad(main_thirdrowlayout,third_row_width,third_row_height);  // to initialize keypad
		          															    bet_keypad_layout.removeView(main_yellow_layout);
		          															    bet_keypad_layout.setClickable(false);				    
		                														bet_keypad_layout.addView(main_thirdrowlayout);
		                														}catch(Exception e)
		                														{
		                															e.printStackTrace();
		                														}
		                                                                       /* end */

		          												              
		          												              
		          												               // Stop_Bet_Ok_Take_Buttons_Group();
		          												                if (FunRouletteMessages.is_Bet_Taken == false && is_selected_bet_completed == true)
		          												                {
		          												                    Send_Bet_Values(false);
		          												                    is_selected_bet_completed = false;
		          												                }
		          												                else if (FunRouletteMessages.is_Bet_Taken == false && is_selected_bet_completed == false)
		          												                    Send_Bet_Values_On_Bet_Ok_Not_Selected();
		          												            }
		          												            else
		          												            {
		          												                FunRouletteMessages.ReSet_Number_2_Stop_Roulette(-1);
		          												               // Start_Bet_Not_Accpted_Timer();
		          												            }
		          												        }

		          												        else if (CurrentTimer.counter == 56)
		          											            {
		          											                result_Request_Thred.Start(Request_Type.GET_RESULT);  // start the request thred as well 
		          											            }
		          													
		          														
		          														
		          												        if (CurrentTimer.counter == 50 && FunRouletteMessages.is_Bet_Taken == false)
		          												        {
		          												            result_Request_Thred.Start(Request_Type.SET_UP_NEXT_GAME);
		          												            
		          												        }
		          												        
		          												        String current_activity_name=this.getClass().getSimpleName();
		          												        if ((current_activity_name.equalsIgnoreCase("FunRouletteActivity")) && (FunRouletteMessages.winning_amount_in_last_game > 0))
		          												        {
		          												           objFunRouletteMessages.Update_Balance_In_Fun_ROulette();
		          												        }                                   

		          												        if (FunRouletteMessages.is_bet_cancel == true)
		          												        {
		          												            objFunRouletteMessages.Clear_Total_Bet_Taken_Details();
		          												           // number_Pad.Clear_Last_Game_Betting();
		          												           //Timer_Bet_OK_Stop();
		          												           //Timer_Take_Bet_Stop();
		          												            FunRouletteMessages.is_bet_cancel = false;
		          												        }
		          														          												        
		          												        if (CurrentTimer.counter <= 59 && CurrentTimer.counter >= 48)
		          												            FunRouletteMessages.check_for_player_session = true;
		          												        
		          												     //   Update_Time_2_Start(Next_Game_ID_Time_FunTarget.Time_Seconds_Target);

          										    /*end */
          														
          														
          														
          												     }
          													 
          													 if(CurrentTimer.counter==0)
          													 {
          														         														 
          														 bet_keypad_layout.setClickable(true);
                                                                /* start */
          														 try{
          														LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          														//bet_keypad_layout.removeView(main_thirdrowlayout);
          														bet_keypad_layout.removeAllViews();
          														main_thirdrowlayout=(LinearLayout) inflate.inflate(R.layout.activity_funroulette_thirdrowlayout, null);
          														
          														initialize_keypad(main_thirdrowlayout,third_row_width,third_row_height);  // to initialize keypad
          														bet_keypad_layout.addView(main_thirdrowlayout);
          														
          														bet_keypad_layout.setOnClickListener(new OnClickListener() {
          															
          															@Override
          															public void onClick(View v) {
          														
          																LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          																main_yellow_layout=(LinearLayout) inflate.inflate(R.layout.activity_yellowbutton, null);
          																
          																initialize_zoomkeypad(main_yellow_layout, third_row_width, third_row_height);
          																					
          															    ABC obj=new ABC();
          																obj.calculate_keypad_bet(main_yellow_layout);   // to calculate bet on each key
          															    obj.reset_all_bet_text_boxes();
          															    
          															    
          															    bet_keypad_layout.removeView(main_thirdrowlayout);
          															    bet_keypad_layout.setClickable(false);				    
          															    
          															    bet_keypad_layout.addView(main_yellow_layout);    // instantiating the keypad

          														
          															}
          														});
                                                              /* end */
          														 }catch(Exception e)
          														 {
          															 e.printStackTrace();
          														 }
          														 
          														 
          														 objFunRouletteMessages.Update_Hint_Msg_On_Rolling();
          														 scaleandrotateWheel();
          													 }
          													 
          													 
          													 
          													 
          													 
          													 if(CurrentTimer.counter<0)
          													 {
          														   CurrentTimer.counter=59;
          														   //show the toast
         									                          int duration = Toast.LENGTH_SHORT;  
         									                          Toast toast = Toast.makeText(getApplicationContext(), "rescheduling timer", duration);
         									                          toast.show();
         									                          
		  													 }
          													
          													// Release any resources from previous MediaPlayer
          													 if (mp != null)
          													 {
          													      mp.release();
          													 }
          													   
          													 // Create a new MediaPlayer to play this sound
       													 try
       													 {
           													   // Create a new MediaPlayer to play this sound
           													 
       														 if (flag_playing_actualdevice==true)
           													   {
		           													   mp = MediaPlayer.create(getApplicationContext(), R.raw.clock);
		           													   mp.start();
		           													   
		           													   mp.setOnCompletionListener(new OnCompletionListener() {
																											@Override
																											public void onCompletion(MediaPlayer mp) {	mp.release();	}
		           													   								});
           													   }
       													 }
       													 catch(Exception e)
       													 {
       														 
       														   Toast.makeText(getApplicationContext(), "Media Player can't be tested in emulator as...."+e.getMessage()+"", Toast.LENGTH_SHORT).show();
	           												        //stop the timer, if it's not already null
	           												        if (timer_time != null) {
	           												        	timer_time.cancel();
	           												        	timer_time = null;
	           												            CurrentTimer.timer_thread_count--;
	           												        }
       													 }									                        
              											  }
              											});
          								}
      							};
  	    }
       
       
       else if(CurrentTimer.timer_thread_count>1)
       {
      	 Toast toast = Toast.makeText(getApplicationContext(), "more than one thread of timertask", Toast.LENGTH_SHORT);
           toast.show();
       }
       else if(CurrentTimer.timer_thread_count<1)
       {
      	 Toast toast = Toast.makeText(getApplicationContext(), "less than one thread of timertask", Toast.LENGTH_SHORT);
           toast.show();
       }
       }
   
    public void stoptimertask(View v) {
       //stop the timer, if it's not already null
       if (timer_time != null) {
       	timer_time.cancel();
       	timer_time = null;
           CurrentTimer.timer_thread_count--;
       }
       
       if (mp != null) {
		      mp.release();
		   }
   }
    
   
    
    
    public void scaleandrotateWheel()
    {
    	try{    
    		if(container==null)
    		{
			  container=(ViewGroup) overlay_two_imageviews.getParent().getParent().getParent().getParent().getParent().getParent();
			  container.getOverlay().add(overlay_two_imageviews);
    		}
		
		ScaleAnimation ani=new ScaleAnimation(1.0f,2.3f,1.0f,2.3f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.0f);
		ani.setDuration(1000);
		ani.setFillAfter(true);
		ani.setFillEnabled(true);
		ani.setAnimationListener(FunRouletteActivity.this);
		overlay_two_imageviews.startAnimation(ani);
		}
		catch(Exception e){
			e.printStackTrace();
		}
    }
    
    
    
    
		    // internal class to execute asynchronously starts here
			class AttemptUpdate extends AsyncTask<String, String, String>{
				
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
				}
		
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
				}
		
				@Override
				protected String doInBackground(String... params) {
					return "true";
				}
				
			}
		   //internal class to execute asynchronously starts here

			
			
			
		    void Start_Bet_Ok_Take_Button()  // for Bet_Ok or Previous buttons
		    {
		        // first detach all event handlers
		       //Stop_Bet_Ok_Take_Buttons_Group();
		        // attach specific event handlers based on requirement
		        if (FunRouletteMessages.enum_Bet_OK_Button_Group == (int)Button_Group.BET_OK.button_group_value)
		        {
		    		initialise_BetOk_slider();
		    		Timer_Bet_OK_Start();	

		        	
		        }
		        else if (FunRouletteMessages.enum_Bet_OK_Button_Group == (int) Button_Group.BET_PREVIOUS_OK.button_group_value)
		        {
		          //  objFunRouletteActivity.Timer_Previous_Bet_Start();
		        }
		        else // Bet_Take Group
		        {
		        	initialise_Take_Bet();
		            Timer_Take_Bet_Start();
		        }

		        FunRouletteMessages.button_Glow = true;
		    }
		    
		    
		    public void Start_Bet_Ok_Take_Buttons_Group(Button_Group button_Group)
		    {
		    	int abc=(int)button_Group.button_group_value;
		        if (FunRouletteMessages.enum_Bet_OK_Button_Group != abc)
		        {
		            FunRouletteMessages.enum_Bet_OK_Button_Group = (int)button_Group.button_group_value;
		           // Start_Bet_Ok_Take_Button();
		        }
		    }
		    
		    public void Stop_Bet_Ok_Take_Buttons_Group()
		    {
		        
		     Timer_Bet_OK_Stop();
		    // objFunRouletteActivity.Timer_Previous_Bet_Stop();
		     Timer_Take_Bet_Stop();
		       
		    }

			
			
			void Start_Bet_Not_Accpted_Timer()
		    {
		    
		    	//Timer_secondstimer_Stop();
		    	
		    	//Timer_Handle_Bet_Not_Accepted_By_Client_Start();
		    
		    }
			
			
			
			void Send_Bet_Values_On_Bet_Ok_Not_Selected()
		    {
		        //is_Bet_Taken = true;
		        Bet_Time_Over(true);
		        objFunRouletteMessages.bet_Values = new int[38];

		        for (int i = 0; i < 38; i++)
		        {
		        	objFunRouletteMessages.bet_Values[i] = 0;
		        }
		        //number_Pad.Prepare_Bets_Array(out bet_Values);
		        // Prepare the array contains bet values for all keys
		        result_Request_Thred.Start(Request_Type.SEND_BET_VALUES);
		    }
			
		    public void Send_Bet_Values(boolean is_Previous_Button)
		    {
		    	
		        is_Bet_Accpeted = false;
		        is_selected_bet_completed = false;
		        // Prepare the array contains bet values for all keys
		       int betarraytemp[] =get_Bet_Values_Array();
		                
		        // this is only when previous ok button is pressed
		        if (is_Previous_Button)
		        {
		            if (GlobalClass.getBalance() -FunRouletteMessages.total_Bet_Amount_On_Current_Game >= 0)
		            {
		                // needs to update the balance
		                GlobalClass.setBalance(GlobalClass.getBalance() - FunRouletteMessages.total_Bet_Amount_On_Current_Game);  // deduct total bet taken in previous game
		                FunRouletteMessages.player_balance_for_history = GlobalClass.getBalance();
		               
		               Update_Balance_Label();
		              //  Update_Balance_Label_in_Fun_Target();
		               Update_Total_Bet_Taken_4_Current_Game(true);
		            }
		            else
		            {
		               Stop_Bet_Ok_Take_Buttons_Group();
		               // number_Pad.Clear_Last_Game_Betting();
		                
		       //         "You don't have sufficent balance to take the previous bet"
		                return;
		            }
		        }
		        Bet_Time_Over(true);
		        FunRouletteMessages.is_Bet_Taken = true;
		        result_Request_Thred.Start(Request_Type.SEND_BET_VALUES);

		        //total_Bet_Amount_On_Current_Game = 0;  // reset total_Bet_Taken
		    }

			
			
			
			
	/*code for recent list starts here*/
			public void Start(int time_Seconds, int[] recent_List, int current_Game_Result_OR_Next_Game_Prepare)
	        {
	            Set_Up(time_Seconds, recent_List, current_Game_Result_OR_Next_Game_Prepare);
	        }  	
			
			
			public void Set_Up(int time_Seconds, int[] recent_List, int current_Game_Result_OR_Next_Game_Prepare)
	        {
	            Set_Recent_List(recent_List);
	           objFunRouletteMessages.Start_Game_Timer(time_Seconds, current_Game_Result_OR_Next_Game_Prepare);            
	        }
			
			
			static List<Integer> recent_Won_List = new ArrayList<Integer>();
		
			public void Set_Recent_List(int[] recent_List)
		    {
		            if (recent_List != null)
		            {
		                recent_Won_List.clear();

		                for(int i=0; i<recent_List.length;i++)
		                {
		                    recent_Won_List.add(recent_List[i]);
		                }

		                Update_Recent_List();
		            }
		            //double Acount_Balance = globalclass.getBalance();
		            //txt_score.setText(""+Acount_Balance);
		            Update_Balance_Label();
		    }
			
			public void Update_Recent_List()
	        {       
	            String list = " ";
        
	            int index=0;
	            for(int j=0;j<recent_Won_List.size();j++)
	            {
	            	int number=recent_Won_List.get(j);
	            	//actual mapping 37- 00, 38 -0
	                if (number == 37)
	                {
	                    list = "00" + " ";
	                    txt_last_5_data[index].setTextColor(Color.GREEN);
	                }
	                else if(number == 38 || number == 0) {
	                    list = "0" + " ";
	                    txt_last_5_data[index].setTextColor(Color.GREEN);
	                }
	                else if (objFunRouletteMessages.Is_Red_Black(number) == 1)
	                {
	                    list = number + "  ";
	                    txt_last_5_data[index].setTextColor(Color.RED);
	                }
	                else           
	                {
	                	/*Resources res=getResources();
	                    list = number + "  ";
	                    txt_last_5_data[index].setTextColor(res.getColor(R.color.Silver));*/
	                	 list = number + "  ";
	                    txt_last_5_data[index].setTextColor(Color.WHITE);
	                }
	                txt_last_5_data[index].setText(list);
		            ++index;
	            }
	            
	            
	            //String M=list;
	           // label_Recent_list_4.Content = list;
	        }

			
			public void Update_Recent_List(int current_win_number)
	        {
	            last_Result = current_win_number;
	            
	            recent_Won_List.add(current_win_number);
	           
	            int number=recent_Won_List.get(0);
	            if( recent_Won_List.size() == 6)
	            {
	                recent_Won_List.remove(recent_Won_List.get(0));     
	            }
	            Update_Recent_List();
	        }
			
			
    /*code for recent list ends here*/

			
			public void Update_Total_Bet_Taken_4_Current_Game(boolean isClear)
	        {
				if(isClear==true)
				txt_bottom_won_amount.setText("0");
				
					
	        }
			
			/* code for start game timer starts here*/
			public void Update_Balance_Label()
	        {
				double Acount_Balance = GlobalClass.getBalance();
	            txt_score.setText(""+Acount_Balance);
	        }
	
		    public void Update_Amount_Won()
		    {
		         FunRouletteMessages.amount_Won = FunRouletteMessages.winning_amount_in_last_game;
		         txt_winner.setText("" + FunRouletteMessages.amount_Won);
		    }
		    

			
		 // Result ( Win / Lost) messages
	        public void Update_Hint_Result_Message(boolean is_Won)
	        {
	            String msg = "You have lost. Game Over";
	            if (is_Won)
	                msg = "Congratulations!!! You won";

	            //label_Win_Message.Content =  msg;
	            Update_Hint_Msg(msg, Color.WHITE);  
	        }
	        
	        
	     // this is to display 3 variety of messages only
	        void Update_Hint_Msg(String str_Msg,int colorname)
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
	        
	        
	        
	        
			public void Bet_Time_Over( boolean is_bet_time_over){
	            Is_Bet_Taime_Over = is_bet_time_over;
	        }
			/* code for start game timer ends here*/
			
			
			public boolean Is_It_New_Game()
	        {
	            return is_New_Game;
	        }


			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				MyFragment frag=new MyFragment();
				FragmentManager manager=getFragmentManager();
				frag.show(manager, "MYDialog");
				
				
				
				
				
			}


			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}


			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			
}
