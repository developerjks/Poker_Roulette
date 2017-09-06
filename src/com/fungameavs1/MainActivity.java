package com.fungameavs1;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

class MyWorkerThread extends HandlerThread {

    private Handler mWorkerHandler;

    public MyWorkerThread(String name) {
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

public class MainActivity extends Activity {

	int screenWidthDp,screenHeightDp;
	TextView txt_response_message;
	ImageView btn_Enter;
    ImageView btn_Close;
 
    EditText txt_Account;
    EditText txt_Password;
    static  boolean handler_internet_connection_status=false;
    Timer timer=null;
    Handler myhandler_internet_connection_status=new Handler();
    private MyWorkerThread mWorkerThread;
    Runnable myupdateresults_internet_connection_status;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
	   /*to make display full screen starts (it requires uses-permission : ACCESS_NETWORK_STATE */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*to make display full screen ends*/
		
		setContentView(R.layout.activity_main);
		
	
		/* to get screen width and height of real device starts  */
	 		Point size=new Point();
	        getWindowManager().getDefaultDisplay().getSize(size);
	        screenWidthDp=size.x;
	        screenHeightDp=size.y;
        /* to get screen width and height of real device ends  */
	
	        
		 	/* finding the window or device ID starts   */
			    GlobalClass.setWindow_id(GetWindowId());
			/* finding the window or device ID ends   */
	
			    
			    
		        mWorkerThread = new MyWorkerThread("myWorkerThread");
		        myupdateresults_internet_connection_status = new Runnable() {
		            @Override
		            public void run()
		            {
		                        myhandler_internet_connection_status.post(new Runnable() {
												                        	@Override
												                        	public void run()
												                        	{
													                        	handler_internet_connection_status= get_Internet_Connection_Status();
														                        if(handler_internet_connection_status==true)
														                        {
														                        	On_Internet_Connection_Status_Update();
														                        }
												                        	}
			                        									});

		            
		            }
		        };
		        
		        
		        
		        
		        int delay=4000;//delay for 1 sec
		        int period=4000; //repeat every 4 sec
		        timer=new Timer();
		        timer.scheduleAtFixedRate(new TimerTask() {
					        				    @Override
					        				    public void run()
					        				    {
					        				    	 mWorkerThread.postTask(myupdateresults_internet_connection_status);
					        				    }
		                                  },delay,period);
		        
	
	}

	
	
	@Override
	protected void onResume() {
		super.onResume();
			
		LinearLayout mainlinearlayout=(LinearLayout) findViewById(R.id.Mainlayout);//main layout 
	    mainlinearlayout.setOrientation(LinearLayout.VERTICAL);
		//mainlinearlayout.setBackgroundColor(Color.YELLOW);
        
		
		initialize_login_screen(mainlinearlayout,screenWidthDp,screenHeightDp );
		
        On_Internet_Connection_Status_Update();
	}
	
public void On_Internet_Connection_Status_Update()
{
	/* finding all the controls starts   */
	txt_response_message=(TextView) findViewById(R.id.txt_internet_status);	
	
	btn_Enter= (ImageView) findViewById(R.id.btn_Enter);
	btn_Enter.setOnClickListener(new EventHandler_Enter_Close());
	
	btn_Close= (ImageView) findViewById(R.id.btn_Close);
	btn_Close.setOnClickListener(new EventHandler_Enter_Close());
	 
	txt_Account=(EditText) findViewById(R.id.txt_account);
	 
    txt_Password=(EditText) findViewById(R.id.txt_password);
     
    /* finding all the controls ends   */

     
    if(handler_internet_connection_status==true)
		{
			 txt_Account.setFocusable(true);
			 txt_Account.setEnabled(true);
			 txt_Password.setEnabled(true);
			 btn_Enter.setEnabled(true);
			 
			 txt_response_message.setText("Please Login");
			 Toast.makeText(this, "MainActivity Network is Available", Toast.LENGTH_LONG).show();
		}
		else
		{
			 txt_Account.clearFocus();
			 txt_Account.setEnabled(false);
			 txt_Password.setEnabled(false);
			 btn_Enter.setEnabled(false);
			 
			txt_response_message.setText("Check your Wifi/Internet Connection");
			 Toast.makeText(this, "Check your Wifi/Internet Connection", Toast.LENGTH_LONG).show();
		}
}
	
	@SuppressWarnings("deprecation")
	public void initialize_login_screen(LinearLayout Mainlayout,int screenWidthDp,int screenHeightDp )	
	  {
		  
		  //Mainlayout.setBackgroundColor(Color.RED);
		  /* to set background image bg in main grid starts  */
	      Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.loginscreen1);
	      Mainlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
	      /* to set background image bg in main grid ends  */
	      
	    
			/* first horizontal starts here*/
				int first_horizontal_layout_width=(int)(screenWidthDp);
		        int first_horizontal_layout_height=(int)(screenHeightDp*0.050);
		        final LinearLayout first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.firsthorizontal);
		        first_horizontal_linear_layout.setLayoutParams(new LayoutParams(first_horizontal_layout_width,first_horizontal_layout_height));
		        //first_horizontal_linear_layout.setBackgroundColor(Color.GREEN);
		        
		        /*first horizontal first vertical starts here*/
		        int first_horizontal_first_vertical_layout_width=(int)(screenWidthDp*0.14);
		        int first_horizontal_first_vertical_layout_height=(int)(first_horizontal_layout_height);
		        final LinearLayout first_horizontal_first_vertical_linear_layout=(LinearLayout)Mainlayout.findViewById(R.id.firsthorizontalfirstvertical);
		        first_horizontal_first_vertical_linear_layout.setLayoutParams(new LayoutParams(first_horizontal_first_vertical_layout_width,first_horizontal_first_vertical_layout_height));
		        //first_horizontal_first_vertical_linear_layout.setBackgroundColor(Color.YELLOW);
		        /*first horizontal first vertical ends here*/
		        
		        /*first horizontal second vertical starts here*/
		        int first_horizontal_second_vertical_layout_width=(int)(screenWidthDp*0.42);
		        int first_horizontal_second_vertical_layout_height=(int)(first_horizontal_layout_height);
		        final LinearLayout first_horizontal_second_vertical_linear_layout=(LinearLayout)Mainlayout.findViewById(R.id.firsthorizontalsecondvertical);
		        first_horizontal_second_vertical_linear_layout.setLayoutParams(new LayoutParams(first_horizontal_second_vertical_layout_width,first_horizontal_second_vertical_layout_height));
		        //first_horizontal_second_vertical_linear_layout.setBackgroundColor(Color.BLUE); 
		        /*first horizontal second vertical ends here*/
		        
		        /*first horizontal third vertical starts here*/
		        int first_horizontal_third_vertical_layout_width=(int)(screenWidthDp*0.45);
		        int first_horizontal_third_vertical_layout_height=(int)(first_horizontal_layout_height);
		        final LinearLayout first_horizontal_third_vertical_linear_layout=(LinearLayout)Mainlayout.findViewById(R.id.firsthorizontalthirdvertical);
		        first_horizontal_third_vertical_linear_layout.setLayoutParams(new LayoutParams(first_horizontal_third_vertical_layout_width,first_horizontal_third_vertical_layout_height));
		        //first_horizontal_third_vertical_linear_layout.setBackgroundColor(Color.MAGENTA);
		        /*first horizontal third vertical ends here*/
		    /* first horizontal ends here*/  
		  
		        
		        
		        
		        
		    /*second horizontal starts here*/
			    int second_horizontal_layout_width=(int)(screenWidthDp);
			    int second_horizontal_layout_height=(int)(screenHeightDp*0.95);
			    final LinearLayout second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontal);
			    second_horizontal_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_layout_width,second_horizontal_layout_height));
			    //second_horizontal_linear_layout.setBackgroundColor(Color.MAGENTA);
			    
			    /*second horizontal first vertical starts here*/
			    int second_horizontal_first_vertical_layout_width=(int)(screenWidthDp*0.71);
			    int second_horizontal_first_vertical_layout_height=(int)(second_horizontal_layout_height);
			    final LinearLayout second_horizontal_first_vertical_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalfirstvertical);
			    second_horizontal_first_vertical_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_first_vertical_layout_width,second_horizontal_first_vertical_layout_height));
			    //second_horizontal_first_vertical_linear_layout.setBackgroundColor(Color.BLUE);
			    
			    /*second horizontal first vertical first horizontal starts here*/
			    int second_horizontal_first_vertical_first_horizontal_layout_width=(int)(second_horizontal_first_vertical_layout_width);
			    int second_horizontal_first_vertical_first_horizontal_layout_height=(int)(second_horizontal_first_vertical_layout_height*0.75);
			    final LinearLayout second_horizontal_first_vertical_first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalfirstverticalfirsthorizontal);
			    second_horizontal_first_vertical_first_horizontal_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_first_vertical_first_horizontal_layout_width,second_horizontal_first_vertical_first_horizontal_layout_height));
			    //second_horizontal_first_vertical_first_horizontal_linear_layout.setBackgroundColor(Color.RED);
			    /*second horizontal first vertical first horizontal ends here*/
			    
			    /*second horizontal first vertical second horizontal starts here*/
			    int second_horizontal_first_vertical_second_horizontal_layout_width=(int)(second_horizontal_first_vertical_layout_width);
			    int second_horizontal_first_vertical_second_horizontal_layout_height=(int)(second_horizontal_first_vertical_layout_height*0.25);
			    final LinearLayout second_horizontal_first_vertical_second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalfirstverticalsecondhorizontal);
			    second_horizontal_first_vertical_second_horizontal_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_first_vertical_second_horizontal_layout_width,second_horizontal_first_vertical_second_horizontal_layout_height));
			    //second_horizontal_first_vertical_second_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
			    
			    /*vertical1 linear layout starts here*/
			    int vertical1_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.06);
			    int vertical1_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical1_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical1);
			    vertical1_linear_layout.setLayoutParams(new LayoutParams(vertical1_layout_width,vertical1_layout_height));
			    //vertical1_linear_layout.setBackgroundColor(Color.CYAN);
			    /*vertical1 linear layout ends here*/
			    
			    /*vertical2 linear layout starts here*/
			    int vertical2_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.27);
			    int vertical2_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical2_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2);
			    vertical2_linear_layout.setLayoutParams(new LayoutParams(vertical2_layout_width,vertical2_layout_height));
			    //vertical2_linear_layout.setBackgroundColor(Color.MAGENTA);
			    
			    /*vertical2 first horizontal starts here*/
			    int vertical2_first_horizontal_layout_width=(int)(vertical2_layout_width);
			    int vertical2_first_horizontal_layout_height=(int)(vertical2_layout_height*0.12);
			    final LinearLayout vertical2_first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2firsthorizontal);
			    vertical2_first_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical2_first_horizontal_layout_width,vertical2_first_horizontal_layout_height));
			    //vertical2_first_horizontal_linear_layout.setBackgroundColor(Color.CYAN);
			    /*vertical2 first horizontal ends here*/
			    
			    /*vertical2 second horizontal starts here*/
			    int vertical2_second_horizontal_layout_width=(int)(vertical2_layout_width);
			    int vertical2_second_horizontal_layout_height=(int)(vertical2_layout_height*0.18);
			    final LinearLayout vertical2_second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2secondhorizontal);
			    vertical2_second_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical2_second_horizontal_layout_width,vertical2_second_horizontal_layout_height));
			    //vertical2_second_horizontal_linear_layout.setBackgroundColor(Color.LTGRAY);
			    vertical2_second_horizontal_linear_layout.setPadding(0, 0, 15, 0);
			    /*vertical2 second horizontal ends here*/
			    
			    /*vertical2 third horizontal starts here*/
			    int vertical2_third_horizontal_layout_width=(int)(vertical2_layout_width);
			    int vertical2_third_horizontal_layout_height=(int)(vertical2_layout_height*0.26);
			    final LinearLayout vertical2_third_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2thirdhorizontal);
			    vertical2_third_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical2_third_horizontal_layout_width,vertical2_third_horizontal_layout_height));
			   //vertical2_third_horizontal_linear_layout.setBackgroundColor(Color.RED);
			    /*vertical2 third horizontal ends here*/
			    
			    /*vertical2 fourth horizontal starts here*/
			    int vertical2_fourth_horizontal_layout_width=(int)(vertical2_layout_width);
			    int vertical2_fourth_horizontal_layout_height=(int)(vertical2_layout_height*0.18);
			    final LinearLayout vertical2_fourth_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2fourthhorizontal);
			    vertical2_fourth_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical2_fourth_horizontal_layout_width,vertical2_fourth_horizontal_layout_height));
			    //vertical2_fourth_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
			    /*vertical2 fourth horizontal ends here*/
			    
			    /*vertical2 fifth horizontal starts here*/
			    int vertical2_fifth_horizontal_layout_width=(int)(vertical2_layout_width);
			    int vertical2_fifth_horizontal_layout_height=(int)(vertical2_layout_height*0.28);
			    final LinearLayout vertical2_fifth_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical2fifthhorizontal);
			    vertical2_fifth_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical2_fifth_horizontal_layout_width,vertical2_fifth_horizontal_layout_height));
			    //vertical2_fifth_horizontal_linear_layout.setBackgroundColor(Color.RED);
			    /*vertical2 fifth horizontal ends here*/
			    
			    /*vertical2 linear layout ends here*/
			    
			    /*vertical3 linear layout starts here*/
			    int vertical3_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.05);
			    int vertical3_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical3_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical3);
			    vertical3_linear_layout.setLayoutParams(new LayoutParams(vertical3_layout_width,vertical3_layout_height));
			    //vertical3_linear_layout.setBackgroundColor(Color.CYAN);
			    /*vertical3 linear layout ends here*/
			    
			    
			    
			    
			    /*vertical4 linear layout starts here*/
			    int vertical4_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.18);
			    int vertical4_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical4_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4);
			    vertical4_linear_layout.setLayoutParams(new LayoutParams(vertical4_layout_width,vertical4_layout_height));
			    //vertical4_linear_layout.setBackgroundColor(Color.RED);
			    
			    /*vertical4 first horizontal starts here*/
			    int vertical4_first_horizontal_layout_width=(int)(vertical4_layout_width);
			    int vertical4_first_horizontal_layout_height=(int)(vertical4_layout_height*0.12);
			    final LinearLayout vertical4_first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4firsthorizontal);
			    vertical4_first_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical4_first_horizontal_layout_width,vertical4_first_horizontal_layout_height));
			    //vertical4_first_horizontal_linear_layout.setBackgroundColor(Color.CYAN);
			    /*vertical4 first horizontal ends here*/
			    
			    /*vertical4 second horizontal starts here*/
			    int vertical4_second_horizontal_layout_width=(int)(vertical4_layout_width);
			    int vertical4_second_horizontal_layout_height=(int)(vertical4_layout_height*0.18);
			    final LinearLayout vertical4_second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4secondhorizontal);
			    vertical4_second_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical4_second_horizontal_layout_width,vertical4_second_horizontal_layout_height));
			    //vertical4_second_horizontal_linear_layout.setBackgroundColor(Color.LTGRAY);
			    /*vertical4 second horizontal ends here*/
			    
			    /*vertical4 third horizontal starts here*/
			    int vertical4_third_horizontal_layout_width=(int)(vertical4_layout_width);
			    int vertical4_third_horizontal_layout_height=(int)(vertical4_layout_height*0.26);
			    final LinearLayout vertical4_third_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4thirdhorizontal);
			    vertical4_third_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical4_third_horizontal_layout_width,vertical4_third_horizontal_layout_height));
			    //vertical4_third_horizontal_linear_layout.setBackgroundColor(Color.RED);
			    /*vertical4 third horizontal ends here*/
			    
			    /*vertical4 fourth horizontal starts here*/
			    int vertical4_fourth_horizontal_layout_width=(int)(vertical4_layout_width);
			    int vertical4_fourth_horizontal_layout_height=(int)(vertical4_layout_height*0.18);
			    final LinearLayout vertical4_fourth_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4fourthhorizontal);
			    vertical4_fourth_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical4_fourth_horizontal_layout_width,vertical4_fourth_horizontal_layout_height));
			    //vertical4_fourth_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
			    /*vertical4 fourth horizontal ends here*/
			    
			    /*vertical4 fifth horizontal starts here*/
			    int vertical4_fifth_horizontal_layout_width=(int)(vertical4_layout_width);
			    int vertical4_fifth_horizontal_layout_height=(int)(vertical4_layout_height*0.28);
			    final LinearLayout vertical4_fifth_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical4fifthhorizontal);
			    vertical4_fifth_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical4_fifth_horizontal_layout_width,vertical4_fifth_horizontal_layout_height));
			    //vertical4_fifth_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
			    /*vertical4 fifth horizontal ends here*/
			    
			    /*vertical4 linear layout ends here*/
			    
			    /*vertical5 linear layout starts here*/
			    int vertical5_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.10);
			    int vertical5_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical5_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical5);
			    vertical5_linear_layout.setLayoutParams(new LayoutParams(vertical5_layout_width,vertical5_layout_height));
			    //vertical5_linear_layout.setBackgroundColor(Color.CYAN);
			    /*vertical5 linear layout ends here*/
			    
			    /*vertical6 linear layout starts here*/
			    int vertical6_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.17);
			    int vertical6_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical6_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical6);
			    vertical6_linear_layout.setLayoutParams(new LayoutParams(vertical6_layout_width,vertical6_layout_height));
			    //vertical6_linear_layout.setBackgroundColor(Color.GRAY);
			    
			    /*vertical6 first horizontal starts here*/
			    int vertical6_first_horizontal_layout_width=(int)(vertical6_layout_width);
			    int vertical6_first_horizontal_layout_height=(int)(vertical6_layout_height*0.70);
			    final LinearLayout vertical6_first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical6firsthorizontal);
			    vertical6_first_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical6_first_horizontal_layout_width,vertical6_first_horizontal_layout_height));
			    //vertical6_first_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
			    /*vertical6 first horizontal ends here*/
			    
			    
			    
			    /*vertical6 second horizontal starts here*/
			    int vertical6_second_horizontal_layout_width=(int)(vertical6_layout_width);
			    int vertical6_second_horizontal_layout_height=(int)(vertical6_layout_height*0.20);
			    final LinearLayout vertical6_second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical6secondhorizontal);
			    vertical6_second_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical6_second_horizontal_layout_width,vertical6_second_horizontal_layout_height));
			    //vertical6_second_horizontal_linear_layout.setBackgroundColor(Color.RED);
			   // vertical6_second_horizontal_linear_layout.setPadding(10, 35, 40, 15);
			    /* to set background image bg in main grid starts  */
			       //Bitmap enter_bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.enter);
			       //btn_Enter=(ImageView)vertical6_second_horizontal_linear_layout.findViewById(R.id.btn_Enter);
			       //btn_Enter.setLayoutParams(new LayoutParams(vertical6_second_horizontal_layout_width,vertical6_second_horizontal_layout_height));
			       //btn_Enter.setBackgroundDrawable(new BitmapDrawable(enter_bitmap));
			    ImageView btn_enter=(ImageView)vertical6_second_horizontal_linear_layout.findViewById(R.id.btn_Enter);
			    btn_enter.setBackgroundResource(R.drawable.enter);
			    
			     /* to set background image bg in main grid ends  */
			    
			    /*vertical6 second horizontal ends here*/
			       
			       
			       /*vertical6 third horizontal starts here*/
				    int vertical6_third_horizontal_layout_width=(int)(vertical6_layout_width);
				    int vertical6_third_horizontal_layout_height=(int)(vertical6_layout_height*0.05);
				    final LinearLayout vertical6_third_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical6thirdhorizontal);
				    vertical6_third_horizontal_linear_layout.setLayoutParams(new LayoutParams(vertical6_third_horizontal_layout_width,vertical6_third_horizontal_layout_height));
				   // vertical6_third_horizontal_linear_layout.setBackgroundColor(Color.YELLOW);
				    /*vertical6 third horizontal ends here*/
			    
			    /*vertical6 linear layout ends here*/
			    
			    /*vertical7 linear layout starts here*/
			    int vertical7_layout_width=(int)(second_horizontal_first_vertical_layout_width*0.25);
			    int vertical7_layout_height=(int)(second_horizontal_first_vertical_second_horizontal_layout_height);
			    final LinearLayout vertical7_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.vertical7);
			    vertical7_linear_layout.setLayoutParams(new LayoutParams(vertical7_layout_width,vertical7_layout_height));
			    //vertical7_linear_layout.setBackgroundColor(Color.WHITE);
			    /*vertical7 linear layout ends here*/
			    
			    
			    /*second horizontal first vertical second horizontal ends here*/  
			    
			    /*second horizontal first vertical ends here*/
			    
			    /*second horizontal second vertical starts here*/
			    int second_horizontal_second_vertical_layout_width=(int)(screenWidthDp*0.30);
			    int second_horizontal_second_vertical_layout_height=(int)(second_horizontal_layout_height);
			    final LinearLayout second_horizontal_second_vertical_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalsecondvertical);
			    second_horizontal_second_vertical_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_second_vertical_layout_width,second_horizontal_second_vertical_layout_height));
			    //second_horizontal_second_vertical_linear_layout.setBackgroundColor(Color.GREEN);
			    
			    /*second horizontal second vertical first horizontal starts here*/
			    int second_horizontal_second_vertical_first_horizontal_layout_width=(int)(second_horizontal_second_vertical_layout_width);
			    int second_horizontal_second_vertical_first_horizontal_layout_height=(int)(second_horizontal_second_vertical_layout_height*0.63);
			    final LinearLayout second_horizontal_second_vertical_first_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalsecondverticalfirsthorizontal);
			    second_horizontal_second_vertical_first_horizontal_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_second_vertical_first_horizontal_layout_width,second_horizontal_second_vertical_first_horizontal_layout_height));
			    //second_horizontal_second_vertical_first_horizontal_linear_layout.setBackgroundColor(Color.RED);
			    /*second horizontal second vertical first horizontal ends here*/
			    
			    /*second horizontal second vertical second horizontal starts here*/
			    int second_horizontal_second_vertical_second_horizontal_layout_width=(int)(second_horizontal_second_vertical_layout_width);
			    int second_horizontal_second_vertical_second_horizontal_layout_height=(int)(second_horizontal_second_vertical_layout_height*0.38);
			    final LinearLayout second_horizontal_second_vertical_second_horizontal_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondhorizontalsecondverticalsecondhorizontal);
			    second_horizontal_second_vertical_second_horizontal_linear_layout.setLayoutParams(new LayoutParams(second_horizontal_second_vertical_second_horizontal_layout_width,second_horizontal_second_vertical_second_horizontal_layout_height));
			    //second_horizontal_second_vertical_second_horizontal_linear_layout.setBackgroundColor(Color.WHITE);
			    
			    /*second vertical1 starts here*/
			    int second_vertical1_layout_width=(int)(second_horizontal_second_vertical_second_horizontal_layout_width*0.55);
			    int second_vertical1_layout_height=(int)(second_horizontal_second_vertical_second_horizontal_layout_height);
			    final LinearLayout second_vertical1_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondvertical1);
			    second_vertical1_linear_layout.setLayoutParams(new LayoutParams(second_vertical1_layout_width,second_vertical1_layout_height));
			    //second_vertical1_linear_layout.setBackgroundColor(Color.GRAY);
			    second_vertical1_linear_layout.setPadding(9, 0, 5, 18);
			    
			    
			    final ImageView btn_close=(ImageView)second_vertical1_linear_layout.findViewById(R.id.btn_Close);
			    btn_close.setImageResource(R.drawable.close);
			    /*btn_close.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						btn_close.setImageResource(R.drawable.ic_launcher);
						Toast.makeText(getApplicationContext(), "close button clicked", Toast.LENGTH_SHORT).show();
					}
				});*/
			    /*second vertical1 ends here*/
			    
			    /*second vertical2 starts here*/
			    int second_vertical2_layout_width=(int)(second_horizontal_second_vertical_second_horizontal_layout_width*0.50);
			    int second_vertical2_layout_height=(int)(second_horizontal_second_vertical_second_horizontal_layout_height);
			    final LinearLayout second_vertical2_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondvertical2);
			    second_vertical2_linear_layout.setLayoutParams(new LayoutParams(second_vertical2_layout_width,second_vertical2_layout_height));
			    //second_vertical2_linear_layout.setBackgroundColor(Color.MAGENTA);
			    /*second vertical2ends here*/
			    
			    /*second horizontal second vertical second horizontal ends here*/
			    
			    
			    /*second horizontal second vertical ends here*/
			    
			    /*second horizontal second vertical starts here*/
			    /*second horizontal second vertical ends here*/
			/*second horizontal ends here */

	  }
		
	public boolean get_Internet_Connection_Status()
	{
		boolean internet_connection_status=false;
		
		try {
			ConnectivityManager cManager=(ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
			NetworkInfo nInfo=cManager.getActiveNetworkInfo();
			
			if(nInfo!=null && nInfo.isConnected())
			{
				internet_connection_status=true;
			}
			else
			{
				internet_connection_status=false;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		Log.d("Handler","alive");
		return internet_connection_status;
	}


    private String username="";
    private String pass="";

	public class CheckUserAvailabilityClass extends AsyncTask<Void, Void, Void>
	{
        private int user_availability_status;
		Service_Client_Interaction client=new Service_Client_Interaction();
		  @Override
		  protected Void doInBackground(Void... arg0)
		  {
			  if (Service_Client_Interaction.player_user_id_logged_in_last_game != null)
	          {
	              if (username != Service_Client_Interaction.player_user_id_logged_in_last_game)
	              {
	            	  txt_response_message.setText("Please terminate your last session");
	                  return null;
	              }
	          }
			  
	          if (username == "" || pass == "")
	          {
	             txt_response_message.setText("Please enter username or password");
              }

		      else
		     {
		        try
		        {
		    	  user_availability_status= client.CheckUserAvailabilty_json(username,pass);
		  	  
		    	  //start
		    	  if (user_availability_status == 1 || user_availability_status == 2 || user_availability_status == 3 || user_availability_status == 4 || user_availability_status == 5)
                  {
                      //User_Detail_Pass_Value.USER_ID = useraccount;
                      int save = client.SaveUserIdDuringLogin_json(username, GlobalClass.getWindow_id());
                      
                      if (save > 0)
                      {
                    	  //txt_response_message.setText("lOGGED IN sUCCESSFULLY");
                    	     int balance = client.GetPlayerBalance_json(username);  // to get the balance                         
                    	
                    	     
                    	     
                    	     //to store retrieved balance from service in global application balance variable
                    	     GlobalClass.setUserId(username);
                    	     GlobalClass.setBalance(balance);
                    	     
                    /* to redirect to another MenuActivity containing user balance with it starts  */
                 			Intent call_menu_activity_intent=new Intent(MainActivity.this,MenuActivity.class);
                 			startActivity(call_menu_activity_intent);
                    /* to redirect to another MenuActivity containing user balance with it ends  */
                    	     
                    	     }
                      else{
                          txt_response_message.setText("Already Login,Try with another Id");
                      }
                  }

                  else if (user_availability_status == 6)
                  {
                      txt_response_message.setText("Sorry,Your account is not active.Please contact Administrator");
                  }
                  else if (user_availability_status == 0)
                  {
                      txt_response_message.setText("Check User Id or Password");
                      txt_Account.setText("");
                      txt_Password.setText("");
                  }
                  else if (user_availability_status == 8)
                  {
                      txt_response_message.setText("Some Network Problem,Try again");
                  }
		    	  //end
		    	  
		       }
		       catch (Exception e){
		    	   txt_response_message.setText("Some Network Problem,Try again");
		    	 }
		     }
		      return null;
		   }

		   protected void onPostExecute(Void result) {
		                         // txt_response_message.setText(""+user_availability_status+"");

		  }

		 }
	
    
    public String GetWindowId()
    {
    	final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

	    final String tmDevice, tmSerial, androidId;
	    tmDevice = "" + tm.getDeviceId();
	    tmSerial = "" + tm.getSimSerialNumber();
	    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

	    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
	    String deviceId = deviceUuid.toString();

	    return deviceId;
    }
    
    
	
	class EventHandler_Enter_Close implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			
           if(R.id.btn_Enter==v.getId())
           {
        	  btn_Enter.setImageResource(R.drawable.enterglow);
        	  //btn_Enter.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        	  String txt_Account_value= txt_Account.getText().toString();
        	  String txt_Password_value=txt_Password.getText().toString();
        	  
		        	  if((!"".equals(txt_Account_value))&&(!"".equals(txt_Password_value)))
		        	  {
		        	     username=txt_Account_value;
		        	     pass=txt_Password_value;
		          	  
		        		  new CheckUserAvailabilityClass().execute();
		        	  }
		        	  else
		        	  {
		        		  txt_response_message.setText("Please enter 10digit LoginID");
		        	  }
           }
           else if(R.id.btn_Close==v.getId())
           {
        	   btn_Close.setImageResource(R.drawable.ic_launcher);
        	   //txt_response_message.setText("Close button is clicked") ;
        	  String current_logged_userid= GlobalClass.getUserId();
       		if(current_logged_userid!=null)
       		{
        	  Service_Client_Interaction service_client=new Service_Client_Interaction();
        	  int del = service_client.Delete_User_Id_During_Logout_json(current_logged_userid,GlobalClass.getWindow_id());
       		}
        	
        	Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        	finish();
        	android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
           }
           
           
           
        	   
		}
	}

	
	@Override
	protected void onStart() {
		super.onStart();
        boolean mWorkerThread_status=mWorkerThread.isAlive();
			if(mWorkerThread_status==false)
	        {
				
	              mWorkerThread.start();
	              mWorkerThread.onLooperPrepared();
	        }
	        
	       
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
        boolean mWorkerThread_status=mWorkerThread.isAlive();
			if(mWorkerThread_status==true)
	        {
				try{
				    myhandler_internet_connection_status.removeCallbacks(myupdateresults_internet_connection_status);
	        	    mWorkerThread.quit();
	        	   
				}catch(Exception e){
					
				}
	        }
		
	}


		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
	        boolean mWorkerThread_status=mWorkerThread.isAlive();
				if(mWorkerThread_status==true)
		        {
					try{
						   myhandler_internet_connection_status.removeCallbacks(myupdateresults_internet_connection_status);
			        	   mWorkerThread.quit();
						}catch(Exception e){
							
						}
		        }
		}

		 @Override
			protected void onRestart() {
				// TODO Auto-generated method stub
				super.onRestart();
			}

			@Override
			protected void onDestroy() {
				// TODO Auto-generated method stub
				super.onDestroy();
				Service_Client_Interaction service_client=new Service_Client_Interaction();
				String user_id=GlobalClass.getUserId();
				if(user_id!=null)
				{
					int del = service_client.Delete_User_Id_During_Logout_json(user_id,GlobalClass.getWindow_id());
				}
			}

}