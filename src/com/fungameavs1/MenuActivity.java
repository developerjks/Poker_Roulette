package com.fungameavs1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import com.fungameavs1.Service_Client_Interaction.Login_User_Details;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Telephony.Sms.Conversations;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

class MyWorkerThread1 extends HandlerThread {

    private Handler mWorkerHandler1;

    public MyWorkerThread1(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        mWorkerHandler1 = new Handler(getLooper());
    }

    public void postTask(Runnable task){
        mWorkerHandler1.post(task);
    }
}




public class MenuActivity extends Activity {
	Timer timer1=null;
    Handler myhandler_internet_connection_status1=new Handler();
    private MyWorkerThread1 mWorkerThread1;
    Runnable myupdateresults_internet_connection_status1;

	

    
    
	
    
    
	
	/* listview receivables starts here */
		    ListView list_recievables,list_transferables;
		    CheckBox chkAll_receivables,chkall_transferables;
			List list_receiver_list=null;
		    List list_transfer_list=null;
		    static int return_status_async_list=0;
		    static int return_status_async_recievables_recieve=0;
		    static int return_status_async_recievables_reject=0;
		    static int return_status_async_transferables_reject=0;
		    static int return_status_async_response_point_transfer=0;
		    List lst=null,lst1=null;
		    static List lst_to_update_receivables=null;
		    static List lst_to_update_transferables=null;
		    
	 /* listview receivables ends here */
	 
	String status;
	LinearLayout mainlinearlayout;
	
	int screenWidthDp,screenHeightDp;
	String retrieve_Id;
	int retrieve_balance;

	ImageView btn_logout_menu,btn_refresh_menu,btn_receivables_receive_menu,btn_receivables_reject_menu,btn_transferables_reject_menu,btn_OK_menu;
	TextView txt_user_id;
	TextView txt_user_balance;
	String transaction_status = "active";
	
	//GlobalClass globalclass;
	Service_Client_Interaction service_client = null;
	static boolean window_expired_status=false;
	
	public void Update_Balance_label()
    {
       
       txt_user_balance.setText(GlobalClass.getBalance());
    }
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /*to make display full screen starts (it requires uses-permission : ACCESS_NETWORK_STATE */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		/*to make display full screen ends*/
        setContentView(R.layout.activity_menu);
        
        /*to be deleted*/
       /* GlobalClass.setUserId("ADMIN");
	    GlobalClass.setBalance(8761639);
	    GlobalClass.setWindow_id("ffffffff-c7db-48a6-ffff-ffffae561673");*/
	    /*to be deleted*/
        
        return_status_async_list=0;
        return_status_async_recievables_recieve=0;
        return_status_async_recievables_reject=0;
        return_status_async_transferables_reject=0;
        return_status_async_response_point_transfer=0;
        
        service_client = new Service_Client_Interaction();
     	//globalclass=(GlobalClass) getApplicationContext();
        
        /* to get screen width and height of real device starts  */
 		Point size=new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidthDp=size.x;
        screenHeightDp=size.y;
        /* to get screen width and height of real device ends  */
        
        /* finding all the controls starts   */
        txt_user_id=(TextView) findViewById(R.id.txt_logged_Id);
        txt_user_balance=(TextView) findViewById(R.id.txt_points);
        
		txt_user_id.setText(GlobalClass.getUserId());
		txt_user_id.setTextSize(9);
		txt_user_id.setTextColor(Color.WHITE);
		txt_user_balance.setText(""+GlobalClass.getBalance());
		txt_user_balance.setTextSize(9);
		txt_user_balance.setTextColor(Color.WHITE);
    
		//window_expired_status=false;
	     
		
		/* To get login user details starts here */
		new Get_Login_User_Details_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		//new Get_Login_User_Details_Class().execute();		
        /* To get login user details ends here */
        
 
        	  try {
				if((window_expired_status==false))
				    {
					//starts here
					 mWorkerThread1 = new MyWorkerThread1("myWorkerThread1");
				        myupdateresults_internet_connection_status1 = new Runnable() {
				            @Override
				            public void run()
				            {
				                        myhandler_internet_connection_status1.post(new Runnable() {
														                        	@Override
														                        	public void run()
														                        	{
														                        		 if(window_expired_status==false)
														                        		 {
														                        			 checking_expiration();
														                        			 Log.d("Handler1", "window not expired");
														                        		 }
																			 	         else if(window_expired_status==true)
																			 	         {
																			 	        	 Expire_Session_And_Cancel_Bet();
																			 	        	Log.d("Handler1", "window expired");
																			 	        	 timer1.cancel();   
																			 	         }					
														                        	}
					                        									});
				            }
				        };
				        
				        
				        
				        
				        int delay=4000;//delay for 1 sec
				        int period=1000; //repeat every 4 sec
				        timer1=new Timer();
				        timer1.scheduleAtFixedRate(new TimerTask() {
							        				    @Override
							        				    public void run()
							        				    {
							        				    	 mWorkerThread1.postTask(myupdateresults_internet_connection_status1);
							        				    }
				                                  },delay,period);

					//ends here
				    }
			} 
        	  catch (Exception e) {
				
				e.printStackTrace();
			}   	
        	
        	
        	
    }
  
   
    
    @Override
	protected void onDestroy() {
		
		super.onDestroy();		
	}

	@Override
	protected void onStart() {
		super.onStart();
		 boolean mWorkerThread_status1=mWorkerThread1.isAlive();
			if(mWorkerThread_status1==false)
	        {				
	              mWorkerThread1.start();
	              mWorkerThread1.onLooperPrepared();
	        }
	
		
		  try
	        {
			  if(return_status_async_list==0)
	        	Show_Transfer_DataGrid_Records();
	        }
	        catch(Exception e)
	        {
	        	
	        }
	}

	@Override
    protected void onResume() {
    	super.onResume();
    	  	
    	mainlinearlayout=(LinearLayout) findViewById(R.id.Menulayout);//main layout 
        mainlinearlayout.setOrientation(LinearLayout.VERTICAL);
    	//mainlinearlayout.setBackgroundColor(Color.YELLOW);
        
        try {
        	while(true)
        	{
        		
        	if(return_status_async_list==0)
        	{
        	}
        	else if((return_status_async_list==1)||((list_receiver_list!=null)||(list_transfer_list!=null)))
        	{
        		//Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        		 initialize_menu_screen(mainlinearlayout,screenWidthDp,screenHeightDp );
        		 
        		 break;
        	}
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
 
	
	 @Override
		protected void onPause() {
			
			super.onPause();
			
			boolean mWorkerThread_status1=mWorkerThread1.isAlive();
			if(mWorkerThread_status1==true)
	        {
				try{
				    myhandler_internet_connection_status1.removeCallbacks(myupdateresults_internet_connection_status1);
	        	    mWorkerThread1.quit();
				}catch(Exception e){
					
				}
	        }	
			
			if(return_status_async_list==0)
			{
				//Show_Transfer_DataGrid_Records();
			}
			//return_status_async_list=0;
		}
	
	
	@Override
	protected void onStop() {
		
		super.onStop();
		
		   boolean mWorkerThread_status1=mWorkerThread1.isAlive();
					if(mWorkerThread_status1==true)
			        {
						try{
						    myhandler_internet_connection_status1.removeCallbacks(myupdateresults_internet_connection_status1);
			        	    mWorkerThread1.quit();
						}catch(Exception e){
							
						}
			        }
			
	}
	
	@Override
	protected void onRestart() {		
		super.onRestart();
	}

	private void Show_Transfer_DataGrid_Records()
    {        
    	
    	/* To generate transfer datagrid starts here */
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				new Get_Transfer_Datagrid_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);	
				//new Get_Transfer_Datagrid_Class().execute();	
			}
		});
	       		
	       /* To generate transfer datagrid ends here */
	       //data_grid_transfer.ItemsSource = service_client.Show_Transfer_Datagrid(transaction_status);
    }
       
    private void checking_expiration()
	{
    	expirelogin();
        /*if (FunRouletteMessages.check_for_player_session ==false && FunTargetMessages.check_session_in_Fun_Target == false)            
            expirelogin();
        else
        {
            FunRouletteMessages.check_for_player_session = false;
            FunTargetMessages.check_session_in_Fun_Target = false;
        } 
        */
        boolean internet_connection=true;
        if(service_client != null)                
            internet_connection = get_Internet_Connection_Status();   
         
            if (internet_connection == false)
            {
               //MessageBox.Show("Sorry,could not connect to server", "PCM");
               Expire_Session_And_Cancel_Bet(); 
            }

	}
   
    boolean internet_connection_status=false;
	public boolean get_Internet_Connection_Status()
	{
		
		try {
			ConnectivityManager cManager=(ConnectivityManager) getSystemService(MenuActivity.CONNECTIVITY_SERVICE);
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
		return internet_connection_status;
	}
    
    public void expirelogin()
    {
    	/* To get login user details starts here */
    	    new Get_Window_Expiration_Status_Class().execute();		
       /* To get login user details ends here */
    }
    
    private void Expire_Session_And_Cancel_Bet()
    {
    	
    	boolean mWorkerThread_status1=mWorkerThread1.isAlive();
		if(mWorkerThread_status1==true)
        {
			try{
			    myhandler_internet_connection_status1.removeCallbacks(myupdateresults_internet_connection_status1);
        	    mWorkerThread1.quit();
			}catch(Exception e){
				
			}
        }	
        /* to redirect to another MainActivity in case of window expire to avoid redundancy with same id starts here */
			Intent call_main_activity_intent=new Intent(MenuActivity.this,MainActivity.class);
			startActivity(call_main_activity_intent);
			//finish();
		/* to redirect to another MainActivity in case of window expire to avoid redundancy with same id ends here */
        
    }
     
    @SuppressWarnings("deprecation")
    public void initialize_menu_screen(final LinearLayout Mainlayout,final int screenWidthDp,final int screenHeightDp )	
      {   
    	 ListView list;
    	 String[] Gname;
    	 int images[]={R.drawable.funroulettelistimage,R.drawable.funtargetlistimage,R.drawable.bingolistimage};
    	 
    	 /* to set background image bg in main grid starts  */
         Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.menuscreen);
         Mainlayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
         /* to set background image bg in main grid ends  */
             
         /* firstrow starts here*/
         int firstrow_layout_width=(int)(screenWidthDp);
         int firstrow_layout_height=(int)(screenHeightDp*0.060);
         LinearLayout firstrow_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.firstrow);
         firstrow_linear_layout.setLayoutParams(new LayoutParams(firstrow_layout_width,firstrow_layout_height));
         //firstrow_linear_layout.setBackgroundColor(Color.GREEN);
          
          		/*firstrow_firstcolumn starts here*/
          		int firstrow_firstcolumn_layout_width=(int)(firstrow_layout_width*0.19);
          		int firstrow_firstcolumn_layout_height=(int)(firstrow_layout_height);
          		LinearLayout firstrow_firstcolumn_linear_layout=(LinearLayout) firstrow_linear_layout.findViewById(R.id.firstrow_firstcolumn);
          		firstrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(firstrow_firstcolumn_layout_width,firstrow_firstcolumn_layout_height));
          		//firstrow_firstcolumn_linear_layout.setBackgroundColor(Color.BLUE);
                /*firstrow_firstcolumn ends here*/
          		
          		/*firstrow_secondcolumn starts here*/
          		int firstrow_secondcolumn_layout_width=(int)(firstrow_layout_width*0.11);
          		int firstrow_secondcolumn_layout_height=(int)(firstrow_layout_height);
          		LinearLayout firstrow_secondcolumn_linear_layout=(LinearLayout) firstrow_linear_layout.findViewById(R.id.firstrow_secondcolumn);
          		firstrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(firstrow_secondcolumn_layout_width,firstrow_secondcolumn_layout_height));
          		//firstrow_secondcolumn_linear_layout.setBackgroundColor(Color.YELLOW);
          		
          			/*firstrow_secondcolumn_firstrow starts here*/
          			int firstrow_secondcolumn_firstrow_layout_width=(int)(firstrow_secondcolumn_layout_width);
          			int firstrow_secondcolumn_firstrow_layout_height=(int)(firstrow_secondcolumn_layout_height*0.50);
          			LinearLayout firstrow_secondcolumn_firstrow_linear_layout=(LinearLayout) firstrow_secondcolumn_linear_layout.findViewById(R.id.firstrow_secondcolumn_firstrow);
          			firstrow_secondcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(firstrow_secondcolumn_firstrow_layout_width,firstrow_secondcolumn_firstrow_layout_height));
          		    //firstrow_secondcolumn_firstrow_linear_layout.setBackgroundColor(Color.MAGENTA);
          		    /*firstrow_secondcolumn_firstrow ends here*/
          			
          			/*firstrow_secondcolumn_secondrow starts here*/
          			int firstrow_secondcolumn_secondrow_layout_width=(int)(firstrow_secondcolumn_layout_width);
          			int firstrow_secondcolumn_secondrow_layout_height=(int)(firstrow_secondcolumn_layout_height*0.50);
          			LinearLayout firstrow_secondcolumn_secondrow_linear_layout=(LinearLayout) firstrow_secondcolumn_linear_layout.findViewById(R.id.firstrow_secondcolumn_secondrow);
          			firstrow_secondcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(firstrow_secondcolumn_secondrow_layout_width,firstrow_secondcolumn_secondrow_layout_height));
          			//firstrow_secondcolumn_secondrow_linear_layout.setBackgroundColor(Color.CYAN);
          		    /*firstrow_secondcolumn_secondrow ends here*/
          			
                /*firstrow_secondcolumn ends here*/
          		
          		/*firstrow_thirdcolumn starts here*/
          		int firstrow_thirdcolumn_layout_width=(int)(firstrow_layout_width*0.15);
          		int firstrow_thirdcolumn_layout_height=(int)(firstrow_layout_height);
          		LinearLayout firstrow_thirdcolumn_linear_layout=(LinearLayout) firstrow_linear_layout.findViewById(R.id.firstrow_thirdcolumn);
          		firstrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(firstrow_thirdcolumn_layout_width,firstrow_thirdcolumn_layout_height));
          		//firstrow_thirdcolumn_linear_layout.setBackgroundColor(Color.RED);
                /*firstrow_thirdcolumn ends here*/
          		
          		/*firstrow_fourthcolumn starts here*/
          		int firstrow_fourthcolumn_layout_width=(int)(firstrow_layout_width*0.07);
          		int firstrow_fourthcolumn_layout_height=(int)(firstrow_layout_height);
          		LinearLayout firstrow_fourthcolumn_linear_layout=(LinearLayout) firstrow_linear_layout.findViewById(R.id.firstrow_fourthcolumn);
          		firstrow_fourthcolumn_linear_layout.setLayoutParams(new LayoutParams(firstrow_fourthcolumn_layout_width,firstrow_fourthcolumn_layout_height));
          		//firstrow_fourthcolumn_linear_layout.setBackgroundColor(Color.CYAN);
          		
          			/*firstrow_fourthcolumn_firstrow starts here*/
          			int firstrow_fourthcolumn_firstrow_layout_width=(int)(firstrow_fourthcolumn_layout_width);
          			int firstrow_fourthcolumn_firstrow_layout_height=(int)(firstrow_fourthcolumn_layout_height*0.50);
          			LinearLayout firstrow_fourthcolumn_firstrow_linear_layout=(LinearLayout) firstrow_fourthcolumn_linear_layout.findViewById(R.id.firstrow_fourthcolumn_firstrow);
          			firstrow_fourthcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(firstrow_fourthcolumn_firstrow_layout_width,firstrow_fourthcolumn_firstrow_layout_height));
          			//firstrow_fourthcolumn_firstrow_linear_layout.setBackgroundColor(Color.GREEN);
          			/*firstrow_fourthcolumn_firstrow ends here*/
          			
          			/*firstrow_fourthcolumn_secondrow starts here*/
          			int firstrow_fourthcolumn_secondrow_layout_width=(int)(firstrow_fourthcolumn_layout_width);
          			int firstrow_fourthcolumn_secondrow_layout_height=(int)(firstrow_fourthcolumn_layout_height*0.50);
          			LinearLayout firstrow_fourthcolumn_secondrow_linear_layout=(LinearLayout) firstrow_fourthcolumn_linear_layout.findViewById(R.id.firstrow_fourthcolumn_secondrow);
          			firstrow_fourthcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(firstrow_fourthcolumn_secondrow_layout_width,firstrow_fourthcolumn_secondrow_layout_height));
          			//firstrow_fourthcolumn_secondrow_linear_layout.setBackgroundColor(Color.RED);
          			/*firstrow_fourthcolumn_secondrow ends here*/
                /*firstrow_fourthcolumn ends here*/
             		 
          		/*firstrow_fifthcolumn starts here*/
          		int firstrow_fifthcolumn_layout_width=(int)(firstrow_layout_width*0.50);
          		int firstrow_fifthcolumn_layout_height=(int)(firstrow_layout_height);
          		LinearLayout firstrow_fifthcolumn_linear_layout=(LinearLayout) firstrow_linear_layout.findViewById(R.id.firstrow_fifthcolumn);
          		firstrow_fifthcolumn_linear_layout.setLayoutParams(new LayoutParams(firstrow_fifthcolumn_layout_width,firstrow_fifthcolumn_layout_height));
          		//firstrow_fifthcolumn_linear_layout.setBackgroundColor(Color.WHITE);
                /*firstrow_fifthcolumn ends here*/
          /* firstrow ends here*/  

          /*secondrow starts here*/
    	  int secondrow_layout_width=(int)(screenWidthDp);
    	  int secondrow_layout_height=(int)(screenHeightDp*0.52);
    	  LinearLayout secondrow_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondrow);
    	  secondrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_layout_width,secondrow_layout_height));
    	  //secondrow_linear_layout.setBackgroundColor(Color.MAGENTA);
    	      
    	  		/*secondrow_firstcolumn starts here*/
    	  		int secondrow_firstcolumn_layout_width=(int)(secondrow_layout_width*0.41);
    	  		int secondrow_firstolumn_layout_height=(int)(secondrow_layout_height);
    	  		LinearLayout secondrow_firstcolumn_linear_layout=(LinearLayout) secondrow_linear_layout.findViewById(R.id.secondrow_firstcolumn);
    	  		secondrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_firstcolumn_layout_width,secondrow_firstolumn_layout_height));
    	  		//secondrow_firstcolumn_linear_layout.setBackgroundColor(Color.RED);
    	  		/*secondrow_firstcolumn ends here*/
    	  		
    	  		/*secondrow_secondcolumn starts here*/
    	  		int secondrow_secondcolumn_layout_width=(int)(secondrow_layout_width*0.24);
    	  		int secondrow_secondolumn_layout_height=(int)(secondrow_layout_height);
    	  		LinearLayout secondrow_secondcolumn_linear_layout=(LinearLayout) secondrow_linear_layout.findViewById(R.id.secondrow_secondcolumn);
    	  		secondrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_secondcolumn_layout_width,secondrow_secondolumn_layout_height));
    	  		//secondrow_secondcolumn_linear_layout.setBackgroundColor(Color.WHITE);
    	  		
    	  				/*secondrow_secondcolumn_firstrow starts here*/
    	  				int secondrow_secondcolumn_firstrow_layout_width=(int)(secondrow_secondcolumn_layout_width);
    	  				int secondrow_secondcolumn_firstrow_layout_height=(int)(secondrow_secondolumn_layout_height*0.12);
    	  				LinearLayout secondrow_secondcolumn_firstrow_linear_layout=(LinearLayout) secondrow_secondcolumn_linear_layout.findViewById(R.id.secondrow_secondcolumn_firstrow);
    	  				secondrow_secondcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_secondcolumn_firstrow_layout_width,secondrow_secondcolumn_firstrow_layout_height));
    	  				//secondrow_secondcolumn_firstrow_linear_layout.setBackgroundColor(Color.YELLOW);
    	  				/*secondrow_secondcolumn_firstrow ends here*/
    	  				
    	  				/*secondrow_secondcolumn_secondrow starts here*/
    	  				int secondrow_secondcolumn_secondrow_layout_width=(int)(secondrow_secondcolumn_layout_width);
    	  				int secondrow_secondcolumn_secondrow_layout_height=(int)(secondrow_secondolumn_layout_height*0.60);
    	  				LinearLayout secondrow_secondcolumn_secondrow_linear_layout=(LinearLayout) secondrow_secondcolumn_linear_layout.findViewById(R.id.secondrow_secondcolumn_secondrow);
    	  				secondrow_secondcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_secondcolumn_secondrow_layout_width,secondrow_secondcolumn_secondrow_layout_height));
    	  				//secondrow_secondcolumn_secondrow_linear_layout.setBackgroundColor(Color.GREEN);
    	  				
    	  				ImageView point_transfer=(ImageView) secondrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.pointtransfer);
    	  				point_transfer.setBackgroundResource(R.drawable.pointtransfermain);
    	  				point_transfer.setOnClickListener(new OnClickListener() {
							
							@SuppressLint("InflateParams") @Override
							public void onClick(View v) 
							{
								LayoutInflater layoutinflater=LayoutInflater.from(MenuActivity.this);
								View dialogview=layoutinflater.inflate(R.layout.activity_pointtransferdialog, null);
    			  			    initialize_dialog_screen(dialogview,screenWidthDp,screenHeightDp);
							}
						});  
    	  				/*secondrow_secondcolumn_secondrow ends here*/
    	  				
    	  				/*secondrow_secondcolumn_thirdrow starts here*/
    	  				int secondrow_secondcolumn_thirdrow_layout_width=(int)(secondrow_secondcolumn_layout_width);
    	  				int secondrow_secondcolumn_thirdrow_layout_height=(int)(secondrow_secondolumn_layout_height*0.28);
    	  				LinearLayout secondrow_secondcolumn_thirdrow_linear_layout=(LinearLayout) secondrow_secondcolumn_linear_layout.findViewById(R.id.secondrow_secondcolumn_thirdrow);
    	  				secondrow_secondcolumn_thirdrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_secondcolumn_thirdrow_layout_width,secondrow_secondcolumn_thirdrow_layout_height));
    	  				//secondrow_secondcolumn_thirdrow_linear_layout.setBackgroundColor(Color.MAGENTA);
    	  				/*secondrow_secondcolumn_thirdrow ends here*/
    	  		/*secondrow_secondcolumn ends here*/
    	  		
    	  		/*secondrow_thirdcolumn starts here*/
    	  		int secondrow_thirdcolumn_layout_width=(int)(secondrow_layout_width*0.14);
    	  		int secondrow_thirdcolumn_layout_height=(int)(secondrow_layout_height);
    	  		LinearLayout secondrow_thirdcolumn_linear_layout=(LinearLayout) secondrow_linear_layout.findViewById(R.id.secondrow_thirdcolumn);
    	  		secondrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_thirdcolumn_layout_width,secondrow_thirdcolumn_layout_height));
    	  		//secondrow_thirdcolumn_linear_layout.setBackgroundColor(Color.BLUE);
    	  		/*secondrow_thirdcolumn ends here*/
    	  		
    	  		/*secondrow_fourthcolumn starts here*/
    	  		int secondrow_fourthcolumn_layout_width=(int)(secondrow_layout_width*0.20);
    	  		int secondrow_fourthcolumn_layout_height=(int)(secondrow_layout_height);
    	  		LinearLayout secondrow_fourthcolumn_linear_layout=(LinearLayout) secondrow_linear_layout.findViewById(R.id.secondrow_fourthcolumn);
    	  		secondrow_fourthcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_layout_width,secondrow_fourthcolumn_layout_height));
    	  		//secondrow_fourthcolumn_linear_layout.setBackgroundColor(Color.CYAN);
    	  		
    	  			/*secondrow_fourthcolumn_firstrow starts here*/
    	  			int secondrow_fourthcolumn_firstrow_layout_width=(int)(secondrow_fourthcolumn_layout_width);
    	  			int secondrow_fourthcolumn_firstrow_layout_height=(int)(secondrow_fourthcolumn_layout_height*0.03);
    	  			LinearLayout secondrow_fourthcolumn_firstrow_linear_layout=(LinearLayout) secondrow_fourthcolumn_linear_layout.findViewById(R.id.secondrow_fourthcolumn_firstrow);
    	  			secondrow_fourthcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_firstrow_layout_width,secondrow_fourthcolumn_firstrow_layout_height));
    	  			//secondrow_fourthcolumn_firstrow_linear_layout.setBackgroundColor(Color.GREEN);
    	  			/*secondrow_fourthcolumn_firstrow ends here*/
    	  			
    	  			/*secondrow_fourthcolumn_secondrow starts here*/
    	  			int secondrow_fourthcolumn_secondrow_layout_width=(int)(secondrow_fourthcolumn_layout_width);
    	  			int secondrow_fourthcolumn_secondrow_layout_height=(int)(secondrow_fourthcolumn_layout_height*0.77);
    	  			LinearLayout secondrow_fourthcolumn_secondrow_linear_layout=(LinearLayout) secondrow_fourthcolumn_linear_layout.findViewById(R.id.secondrow_fourthcolumn_secondrow);
    	  			secondrow_fourthcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_secondrow_layout_width,secondrow_fourthcolumn_secondrow_layout_height));
    	  			//secondrow_fourthcolumn_secondrow_linear_layout.setBackgroundColor(Color.MAGENTA);
    	  			
    	  				/*secondrow_fourthcolumn_secondrow_firstcolumn starts here*/
    	  				int secondrow_fourthcolumn_secondrow_firstcolumn_layout_width=(int)(secondrow_fourthcolumn_secondrow_layout_width*0.02);
    	  				int secondrow_fourthcolumn_secondrow_firstcolumn_layout_height=(int)(secondrow_fourthcolumn_secondrow_layout_height);
    	  				LinearLayout secondrow_fourthcolumn_secondrow_firstcolumn_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondrow_fourthcolumn_secondrow_firstcolumn);
    	  				secondrow_fourthcolumn_secondrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_secondrow_firstcolumn_layout_width,secondrow_fourthcolumn_secondrow_firstcolumn_layout_height));
    	  				//secondrow_fourthcolumn_secondrow_firstcolumn_linear_layout.setBackgroundColor(Color.WHITE);
    	  				/*secondrow_fourthcolumn_secondrow_firstcolumn ends here*/
    	  				
    	  				/*secondrow_fourthcolumn_secondrow_secondcolumn starts here*/
    	  				int secondrow_fourthcolumn_secondrow_secondcolumn_layout_width=(int)(secondrow_fourthcolumn_secondrow_layout_width*0.84);
    	  				int secondrow_fourthcolumn_secondrow_secondcolumn_layout_height=(int)(secondrow_fourthcolumn_secondrow_layout_height);
    	  				LinearLayout secondrow_fourthcolumn_secondrow_secondcolumn_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondrow_fourthcolumn_secondrow_secondcolumn);
    	  				secondrow_fourthcolumn_secondrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_secondrow_secondcolumn_layout_width,secondrow_fourthcolumn_secondrow_secondcolumn_layout_height));
    	  				//secondrow_fourthcolumn_secondrow_secondcolumn_linear_layout.setBackgroundColor(Color.YELLOW);
    	  				
    	  				Resources res=getResources();
    	  				Gname=res.getStringArray(R.array.gamename);
    	  				list=(ListView) findViewById(R.id.listview1);
    	  				list.setDivider(null);
    	  				list.setDividerHeight(0);
    	  				
    	  				
    	  		        /* Defining checkbox click event listener */
					        OnItemClickListener itemlistclickListener = new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> adapter,View arg1, int position,long arg3) {
						    	 String item=adapter.getItemAtPosition(position).toString();
						  		
						  		if(item.equalsIgnoreCase("FunRoulette"))
						  		{
						  	         /* to redirect to another FunRouletteActivity containing user balance with it starts  */
						   			Intent call_roulette_activity_intent=new Intent(getBaseContext(),FunRouletteActivity.class);
						   			startActivity(call_roulette_activity_intent);
						  			/* to redirect to another FunRouletteActivity containing user balance with it ends  */
						  		}
						  		else if(item.equalsIgnoreCase("FunTarget"))
						  		{
						  	        /* to redirect to another FunTargetActivity containing user balance with it starts  */
						  			Intent call_target_activity_intent=new Intent(getBaseContext(),FunTargetActivity.class);
						  			startActivity(call_target_activity_intent); 			       
						  			/* to redirect to another FunRouletteActivity containing user balance with it ends  */
						  		}
						  		else if(item=="Bingo")
						  		{
						  			//////
						  		}


								
							}
						}; 
   
    	  				
    	  				
    	  			    list.setOnItemClickListener(itemlistclickListener);
    	  		        listadapter adapter=new listadapter(this, Gname, images);
    	  				list.setAdapter(adapter);
    	  				/*secondrow_fourthcolumn_secondrow_secondcolumn ends here*/
    	  				
    	  				/*secondrow_fourthcolumn_secondrow_thirdcolumn starts here*/
    	  				int secondrow_fourthcolumn_secondrow_thirdcolumn_layout_width=(int)(secondrow_fourthcolumn_secondrow_layout_width*0.14);
    	  				int secondrow_fourthcolumn_secondrow_thirdcolumn_layout_height=(int)(secondrow_fourthcolumn_secondrow_layout_height);
    	  				LinearLayout secondrow_fourthcolumn_secondrow_thirdcolumn_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.secondrow_fourthcolumn_secondrow_thirdcolumn);
    	  				secondrow_fourthcolumn_secondrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_secondrow_thirdcolumn_layout_width,secondrow_fourthcolumn_secondrow_thirdcolumn_layout_height));
    	  				//secondrow_fourthcolumn_secondrow_thirdcolumn_linear_layout.setBackgroundColor(Color.BLUE);
    	  				/*secondrow_fourthcolumn_secondrow_thirdcolumn ends here*/
    	  			/*secondrow_fourthcolumn_secondrow ends here*/
    	  			
    	  			/*secondrow_fourthcolumn_thirdrow starts here*/
    	  			int secondrow_fourthcolumn_thirdrow_layout_width=(int)(secondrow_fourthcolumn_layout_width);
    	  			int secondrow_fourthcolumn_thirdrow_layout_height=(int)(secondrow_fourthcolumn_layout_height*0.20);
    	  			LinearLayout secondrow_fourthcolumn_thirdrow_linear_layout=(LinearLayout) secondrow_fourthcolumn_linear_layout.findViewById(R.id.secondrow_fourthcolumn_thirdrow);
    	  			secondrow_fourthcolumn_thirdrow_linear_layout.setLayoutParams(new LayoutParams(secondrow_fourthcolumn_thirdrow_layout_width,secondrow_fourthcolumn_thirdrow_layout_height));
    	  			//secondrow_fourthcolumn_thirdrow_linear_layout.setBackgroundColor(Color.BLUE);
    	  			/*secondrow_fourthcolumn_thirdrow ends here*/
    	  		/*secondrow_fourthcolumn ends here*/
    	  		
    	  		/*secondrow_fifthcolumn starts here*/
    	  		int secondrow_fifthcolumn_layout_width=(int)(secondrow_layout_width*0.01);
    	  		int secondrow_fifthcolumn_layout_height=(int)(secondrow_layout_height);
    	  		LinearLayout secondrow_fifthcolumn_linear_layout=(LinearLayout) secondrow_linear_layout.findViewById(R.id.secondrow_fifthcolumn);
    	  		secondrow_fifthcolumn_linear_layout.setLayoutParams(new LayoutParams(secondrow_fifthcolumn_layout_width,secondrow_fifthcolumn_layout_height));
    	  		//secondrow_fifthcolumn_linear_layout.setBackgroundColor(Color.YELLOW);
    	  		/*secondrow_fifthcolumn ends here*/
    	  /*secondrow ends here */
    	       
    	  /*thirdrow starts here*/
    	  int thirdrow_layout_width=(int)(screenWidthDp);
    	  //int thirdrow_layout_height=(int)(screenHeightDp*0.46);
    	  int thirdrow_layout_height=screenHeightDp-(firstrow_layout_height+secondrow_layout_height);
    	  LinearLayout thirdrow_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.thirdrow);
    	  thirdrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_layout_width,thirdrow_layout_height));
    	  //thirdrow_linear_layout.setBackgroundColor(Color.GREEN);
    	       
    	  		/*thirdrow_firstcolumn starts here*/
    	  		int thirdrow_firstcolumn_layout_width=(int)(thirdrow_layout_width*0.06);
    	  		int thirdrow_firstcolumn_layout_height=(int)(thirdrow_layout_height);
    	  		LinearLayout thirdrow_firstcolumn_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.thirdrow_firstcolumn);
    	  		thirdrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_firstcolumn_layout_width,thirdrow_firstcolumn_layout_height));
    	  		//thirdrow_firstcolumn_linear_layout.setBackgroundColor(Color.RED);
    	  		/*thirdrow_firstcolumn ends here*/
    	  		
    	  		/*thirdrow_secondcolumn starts here*/
    	  		int thirdrow_secondcolumn_layout_width=(int)(thirdrow_layout_width*0.88);
    	  		int thirdrow_secondcolumn_layout_height=(int)(thirdrow_layout_height);
    	  		LinearLayout thirdrow_secondcolumn_linear_layout=(LinearLayout) Mainlayout.findViewById(R.id.thirdrow_secondcolumn);
    	  		thirdrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_layout_width,thirdrow_secondcolumn_layout_height));
    	  		//thirdrow_secondcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
    	  		
    	  			/*thirdrow_secondcolumn_firstrow starts here*/
    	  			int thirdrow_secondcolumn_firstrow_layout_width=(int)(thirdrow_secondcolumn_layout_width);
    	  			int thirdrow_secondcolumn_firstrow_layout_height=(int)(thirdrow_secondcolumn_layout_height*0.15);
    	  			LinearLayout thirdrow_secondcolumn_firstrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow);
    	  			thirdrow_secondcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_layout_width,thirdrow_secondcolumn_firstrow_layout_height));
    	  			//thirdrow_secondcolumn_firstrow_linear_layout.setBackgroundColor(Color.WHITE);
    	  			
    	  				/*thirdrow_secondcolumn_firstrow_firstcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_firstcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.20);
	  					int thirdrow_secondcolumn_firstrow_firstcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_firstcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_firstcolumn);
	  					thirdrow_secondcolumn_firstrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_firstcolumn_layout_width,thirdrow_secondcolumn_firstrow_firstcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_firstcolumn_linear_layout.setBackgroundColor(Color.CYAN);
	  					/*thirdrow_secondcolumn_firstrow_firstcolumn ends here*/
	  				    
	  					/*thirdrow_secondcolumn_firstrow_secondcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_secondcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.11);
	  					int thirdrow_secondcolumn_firstrow_secondcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_secondcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_secondcolumn);
	  					thirdrow_secondcolumn_firstrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_secondcolumn_layout_width,thirdrow_secondcolumn_firstrow_secondcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_secondcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					
	  					//thirdrow_secondcolumn_firstrow_secondcolumn_linear_layout.setBackgroundResource(R.drawable.receivables_receive);
	  					
	  					btn_receivables_receive_menu=(ImageView)findViewById(R.id.btn_receivables_receive_menu);
	  					btn_receivables_receive_menu.setBackgroundResource(R.drawable.receive);
	  					btn_receivables_receive_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
	  					
	  					
	  					/*thirdrow_secondcolumn_firstrow_secondcolumn ends here*/
	  				
	  					/*thirdrow_secondcolumn_firstrow_thirdcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_thirdcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.06);
	  					int thirdrow_secondcolumn_firstrow_thirdcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_thirdcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_thirdcolumn);
	  					thirdrow_secondcolumn_firstrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_thirdcolumn_layout_width,thirdrow_secondcolumn_firstrow_thirdcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_thirdcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					/*thirdrow_secondcolumn_firstrow_thirdcolumn ends here*/
	  					
	  					/*thirdrow_secondcolumn_firstrow_fourthcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_fourthcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.09);
	  					int thirdrow_secondcolumn_firstrow_fourthcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_fourthcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_fourthcolumn);
	  					thirdrow_secondcolumn_firstrow_fourthcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_fourthcolumn_layout_width,thirdrow_secondcolumn_firstrow_fourthcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_fourthcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					//thirdrow_secondcolumn_firstrow_fourthcolumn_linear_layout.setBackgroundResource(R.drawable.receivables_reject);
	  					
	  					btn_receivables_reject_menu=(ImageView)findViewById(R.id.btn_receivables_reject_menu);
	  					btn_receivables_reject_menu.setBackgroundResource(R.drawable.receivables_reject);
	  					btn_receivables_reject_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
	  					
	  					/*thirdrow_secondcolumn_firstrow_fourthcolumn ends here*/
	  					
	  					/*thirdrow_secondcolumn_firstrow_fifthcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_fifthcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.35);
	  					int thirdrow_secondcolumn_firstrow_fifthcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_fifthcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_fifthcolumn);
	  					thirdrow_secondcolumn_firstrow_fifthcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_fifthcolumn_layout_width,thirdrow_secondcolumn_firstrow_fifthcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_fifthcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					/*thirdrow_secondcolumn_firstrow_fifthcolumn ends here*/
	  					
	  					/*thirdrow_secondcolumn_firstrow_sixthcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_sixthcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.10);
	  					int thirdrow_secondcolumn_firstrow_sixthcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_sixthcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_sixthcolumn);
	  					thirdrow_secondcolumn_firstrow_sixthcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_sixthcolumn_layout_width,thirdrow_secondcolumn_firstrow_sixthcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_sixthcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					//thirdrow_secondcolumn_firstrow_sixthcolumn_linear_layout.setBackgroundResource(R.drawable.receivables_reject);
	  					  
	  					btn_transferables_reject_menu=(ImageView)findViewById(R.id.btn_transferables_reject_menu);
	  					btn_transferables_reject_menu.setBackgroundResource(R.drawable.receivables_reject);
	  					btn_transferables_reject_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
   					    /*thirdrow_secondcolumn_firstrow_sixthcolumn ends here*/
	  					  
	  					/*thirdrow_secondcolumn_firstrow_seventhcolumn starts here*/
	  					int thirdrow_secondcolumn_firstrow_seventhcolumn_layout_width=(int)(thirdrow_secondcolumn_firstrow_layout_width*0.09);
	  					int thirdrow_secondcolumn_firstrow_seventhcolumn_layout_height=(int)(thirdrow_secondcolumn_firstrow_layout_height);
	  					LinearLayout thirdrow_secondcolumn_firstrow_seventhcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_firstrow_seventhcolumn);
	  					thirdrow_secondcolumn_firstrow_seventhcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_firstrow_seventhcolumn_layout_width,thirdrow_secondcolumn_firstrow_seventhcolumn_layout_height));
	  					//thirdrow_secondcolumn_firstrow_seventhcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
	  					/*thirdrow_secondcolumn_firstrow_seventhcolumn ends here*/
    	  			
    	  			/*thirdrow_secondcolumn_firstrow ends here*/
    	  			
    	  			/*thirdrow_secondcolumn_secondrow starts here*/
    	  			int thirdrow_secondcolumn_secondrow_layout_width=(int)(thirdrow_secondcolumn_layout_width);
    	  			int thirdrow_secondcolumn_secondrow_layout_height=(int)(thirdrow_secondcolumn_layout_height*0.72);
    	  			LinearLayout thirdrow_secondcolumn_secondrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow);
    	  			thirdrow_secondcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_layout_width,thirdrow_secondcolumn_secondrow_layout_height));
    	  			//thirdrow_secondcolumn_secondrow_linear_layout.setBackgroundColor(Color.YELLOW);
    	  			      
    	  				/*thirdrow_secondcolumn_secondrow_firstcolumn starts here*/
    	  				/*int thirdrow_secondcolumn_secondrow_firstcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_layout_width*0.01);
    	  				int thirdrow_secondcolumn_secondrow_firstcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_layout_height);
    	  				LinearLayout thirdrow_secondcolumn_secondrow_firstcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_firstcolumn);
    	  				thirdrow_secondcolumn_secondrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_firstcolumn_layout_width,thirdrow_secondcolumn_secondrow_firstcolumn_layout_height));
    	  				*///thirdrow_secondcolumn_secondrow_firstcolumn_linear_layout.setBackgroundColor(Color.GREEN);
    	  				/*thirdrow_secondcolumn_secondrow_firstcolumn ends here*/   
    	  				  
    	  				/*thirdrow_secondcolumn_secondrow_secondcolumn starts here*/
    	  				int thirdrow_secondcolumn_secondrow_secondcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_layout_width);
    	  				int thirdrow_secondcolumn_secondrow_secondcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_layout_height);
    	  				LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn);
    	  				thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_layout_height));
    	  				//thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout.setBackgroundColor(Color.BLUE);
    	  				    
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_firstrow starts here*/
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_height*0.72);
    	  					LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_firstrow);
    	  					thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_height));
    	  					//thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_linear_layout.setBackgroundColor(Color.WHITE);
    	  					
    	  						/*gridvertical1 starts here*/
    	  						int gridvertical1_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_width*0.50);
    	  						int gridvertical1_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_height);
    	  						LinearLayout gridvertical1_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.gridvertical1);
    	  						gridvertical1_linear_layout.setLayoutParams(new LayoutParams(gridvertical1_layout_width,gridvertical1_layout_height));
    	  						//gridvertical1_linear_layout.setBackgroundColor(Color.GREEN);
    	  						
    
    	  						/* finding and firing checkbox and listview starts here */				
    	  						lst=new ArrayList();
    	  						lst_to_update_receivables=new ArrayList();
    	  						
    	  						if(list_receiver_list.size()>0)
    	  								lst=list_receiver_list;


    	  						     /* Defining checkbox click event listener */
    	  						        OnClickListener clickListener = new OnClickListener() {
    	  						 
    	  						            @Override
    	  						            public void onClick(View v) {
    	  						                CheckBox chk = (CheckBox) v;
    	  						                int itemCount = list_recievables.getCount();
    	  						                if(chk.isChecked())
    	  						                {
    	  							   	    	 	for(int i=0; i<itemCount;i++)
    	  								            {
    	  							   	    	 	list_recievables.getChildAt(i).setBackgroundColor(Color.GRAY);
    	  								            }
    	  							   	    	lst_to_update_receivables=list_receiver_list;
    	  						                }
    	  						                else
    	  						                {
    	  						                	for(int i=0; i<itemCount;i++)
    	  								            {
    	  						                		list_recievables.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
    	  						                		
    	  								            }
    	  						                	lst_to_update_receivables=null;
    	  						                	//chk.setChecked(true);
    	  						                }
    	  						               
    	  						            }
    	  						        };
    	  						 
      	  						        /* Defining checkbox click event listener */
    	  						        OnItemClickListener itemlistclickListener1 = new OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> adapter,View arg1, int position,long arg3) {

										    	 int count=adapter.getCount();
										    	 chkAll_receivables.setChecked(false);
										    	 for(int i=0; i<count;i++)
										            {
										    		 adapter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
										                //stList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
										            }

										            adapter.getChildAt(position).setBackgroundColor(Color.GRAY);
										            
										           // lst_to_update= (List)adapter;
										            
									                TextView c = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView1);
									                
									                String playerChanged = c.getText().toString();
									                
									                
									                TextView c1 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView2);
									                String playerChanged1 = c1.getText().toString();
									                
									                TextView c2 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView3);
									                String playerChanged2 = c2.getText().toString();
									                
									                TextView c3 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView4);
									                String playerChanged3 = c3.getText().toString();
									                
									                List temp_list_to_update=new ArrayList();
									                temp_list_to_update.add(playerChanged);
									                temp_list_to_update.add(playerChanged1);
									                temp_list_to_update.add(playerChanged2);
									                temp_list_to_update.add(playerChanged3);
									                
									               List temp_list_to_update1=new ArrayList();
									               temp_list_to_update1.add(temp_list_to_update);
									               
									               lst_to_update_receivables=temp_list_to_update1;
									               int ttt=lst_to_update_receivables.size();
											}
										}; 
    	  						        
    	  						        
    	  		/* finding and firing checkbox and listview ends here */				
										
    	  						list_recievables=(ListView) findViewById(R.id.listreceivables);
    	  						list_recievables.setDivider(null);
    	  						list_recievables.setDividerHeight(0);

   	  						    list_recievables.setOnItemClickListener(itemlistclickListener1);
    	  						  Integer[] newArray = new Integer[lst.size()];
    	  						  
    	  						  for(int m1=0;m1<lst.size();m1++)
    	  						  {
    	  							  newArray[m1]=m1;
    	  						  }
    	  						    listadapter1 adapter1=new listadapter1(this, newArray,lst,R.layout.single_row_receivables,gridvertical1_layout_width,gridvertical1_layout_height);
    	  						    list_recievables.setAdapter(adapter1);

    	  						
        	  						/*gridvertical1 ends here*/
    	  						
    	  						/*gridvertical2 starts here*/
    	  						int gridvertical2_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_width*0.50);
    	  						int gridvertical2_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_layout_height);
    	  						LinearLayout gridvertical2_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_firstrow_linear_layout.findViewById(R.id.gridvertical2);
    	  						gridvertical2_linear_layout.setLayoutParams(new LayoutParams(gridvertical2_layout_width,gridvertical2_layout_height));
    	  						//gridvertical2_linear_layout.setBackgroundColor(Color.RED);
    	  						

    	  						
    	  						
    	  						/* finding and firing checkbox and listview starts here */				
    	  						lst1=new ArrayList();
    	  						//lst.add(list1);
    	  						//lst.add(list2);
    	
    	  						lst_to_update_transferables=new ArrayList();
    	  						if(list_transfer_list.size()>0){
    	  							lst1=list_transfer_list;
    	  							
    	  						}
    	  						
 	  						        /* Defining checkbox click event listener */
    	  						        OnClickListener clickListener1 = new OnClickListener() {
    	  						 
    	  						            @Override
    	  						            public void onClick(View v) {
    	  						                CheckBox chk = (CheckBox) v;
    	  						                int itemCount = list_transferables.getCount();
    	  						                if(chk.isChecked())
    	  						                {
    	  							   	    	 	for(int i=0; i<itemCount;i++)
    	  								            {
    	  							   	    	 	list_transferables.getChildAt(i).setBackgroundColor(Color.GRAY);
    	  								            }
    	  							   	    	lst_to_update_transferables=list_transfer_list;
    	  						                }
    	  						                else
    	  						                {
    	  						                	for(int i=0; i<itemCount;i++)
    	  								            {
    	  						                		list_transferables.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
    	  								            }
    	  						                	lst_to_update_transferables=null;
    	  						                	//chk.setChecked(true);
    	  						                }
    	  						              
    	  						            }
    	  						        };
    	  						 
     	  						        /* Defining checkbox click event listener */
    	  						        OnItemClickListener itemlistclickListener2 = new OnItemClickListener() {

											@Override
											public void onItemClick(AdapterView<?> adapter,View arg1, int position,long arg3) {

												 int count=adapter.getCount();
										    	 chkall_transferables.setChecked(false);
										    	 for(int i=0; i<count;i++)
										            {
										    		 adapter.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
										                //stList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
										            }

										            adapter.getChildAt(position).setBackgroundColor(Color.GRAY);
										            
										            //View curr = adapter.getChildAt((int) arg3);
									                TextView c = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView1);
									                
									                String playerChanged = c.getText().toString();
									                
									                TextView c1 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView2);
									                String playerChanged1 = c1.getText().toString();
									                
									                TextView c2 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView3);
									                String playerChanged2 = c2.getText().toString();

									                TextView c3 = (TextView)adapter.getChildAt((int) arg3).findViewById(R.id.textView4);
									                String playerChanged3 = c3.getText().toString();
									                
									                
									                List temp_list_to_update_transferables=new ArrayList();
									                temp_list_to_update_transferables.add(playerChanged);
									                temp_list_to_update_transferables.add(playerChanged1);
									                temp_list_to_update_transferables.add(playerChanged2);
									                temp_list_to_update_transferables.add(playerChanged3);
									                
									               List temp_list_to_update11=new ArrayList();
									               temp_list_to_update11.add(temp_list_to_update_transferables);
									               
									               lst_to_update_transferables=temp_list_to_update11;
									               int ttt=lst_to_update_transferables.size();
											}
										}; 
        	  						
    	  						    
    	  		/* finding and firing checkbox and listview ends here */				
    	  						
    	  						list_transferables=(ListView) findViewById(R.id.listtransferables);
    	  						list_transferables.setDivider(null);
    	  						list_transferables.setDividerHeight(0);
    	  						list_transferables.setOnItemClickListener(itemlistclickListener2);
    	  						 Integer[] newArray1 = new Integer[lst1.size()];
   	  						  
   	  						  for(int m1=0;m1<lst1.size();m1++)
   	  						  {
   	  							  newArray1[m1]=m1;
   	  						  }

    	  						listadapter2 adapter2=new listadapter2(this, newArray1,lst1,R.layout.single_row_transferables,gridvertical2_layout_width,gridvertical2_layout_height);
	  						    list_transferables.setAdapter(adapter2);
    	  						/*gridvertical2 ends here*/
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_firstrow ends here*/
    	  					
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow starts here*/
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_height*0.15);
    	  					LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_secondrow);
    	  					thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_height));
    	  					//thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout.setBackgroundColor(Color.YELLOW);
    	  					
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn starts here*/
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_width*0.20);
	  						int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_height);
	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn);
	  						thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_layout_height));
	  						//thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn_linear_layout.setBackgroundColor(Color.CYAN);    
	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_firstcolumn ends here*/
    	  					
    	  					
    	  					
    	  					
    	  					
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_width*0.35);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_linear_layout.setBackgroundColor(Color.RED);
    	  					    /* Getting reference to checkbox available in the menu.xml layout */
  	  							 chkAll_receivables =  ( CheckBox ) findViewById(R.id.chkall_receivables);
  	  							 chkAll_receivables.setLayoutParams(new LayoutParams((int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_width*0.40),thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_height));
  	  							//chkAll_receivables.setBackgroundColor(Color.MAGENTA);
  	  							 int chkAll_receivables_textsize=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn_layout_height*0.60);
  						        chkAll_receivables.setTextSize(10);
  	  							 chkAll_receivables.setText("selectall");
  	  							 chkAll_receivables.setTextColor(Color.WHITE);
  	  						    /** Setting a click listener for the checkbox **/
	  						        chkAll_receivables.setOnClickListener(clickListener);
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_secondcolumn ends here*/
    	  						
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_width*0.35);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_linear_layout.setBackgroundColor(Color.MAGENTA);
    	  						
    	  						 /** Getting reference to checkbox available in the menu.xml layout */
 	  							 chkall_transferables =  ( CheckBox ) findViewById(R.id.chkall_transferables);
 	  							 chkall_transferables.setLayoutParams(new LayoutParams((int)(thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_width*0.40), thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn_layout_height));
    	  						 chkall_transferables.setTextSize(10);
    	  						 chkall_transferables.setText("selectall");
    	  						 chkall_transferables.setTextColor(Color.WHITE);
    	  						//chkall_transferables.setBackgroundColor(Color.WHITE);
 	  							 /** Setting a click listener for the checkbox **/
 	  							 chkall_transferables.setOnClickListener(clickListener1);
 	  							 /*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow_thirdcolumn ends here*/
    	  						
    	  			
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_secondrow ends here*/
    	  					
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow starts here*/
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_layout_height*0.13);
    	  					LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow);
    	  					thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height));
    	  					//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.setBackgroundColor(Color.RED );
    	  					   
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width*0.12);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn_linear_layout.setBackgroundColor(Color.GREEN);
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_firstcolumn ends here*/
    	  						   
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width*0.35);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn_linear_layout.setBackgroundColor(Color.CYAN);
    	  						TextView Responsetextview=(TextView) findViewById(R.id.txt_response_menu);
    	  						Responsetextview.setText("Response successfully");
    	  						Responsetextview.setTextSize(8);
    	  						Responsetextview.setTextColor(Color.WHITE);
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_secondcolumn ends here*/
    	  						                    
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width*0.05);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_linear_layout.setBackgroundColor(Color.YELLOW);
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn_linear_layout.setBackgroundResource(R.drawable.okimage);
    	  						
    	  						btn_OK_menu=(ImageView)findViewById(R.id.btn_OK_menu);
    	  						btn_OK_menu.setBackgroundResource(R.drawable.okimage);
    	  						//btn_OK_menu.setPadding(0, 50, 0, 70);
    	  						btn_OK_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_thirdcolumn ends here*/
    	  						
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn starts here*/
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_layout_width=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_width*0.51);
    	  						int thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_layout_height=(int)(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_layout_height);
    	  						LinearLayout thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn);
    	  						thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_layout_width,thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_layout_height));
    	  						//thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn_linear_layout.setBackgroundColor(Color.CYAN);
    	  						/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow_fourthcolumn ends here*/
    	  					
    	  					/*thirdrow_secondcolumn_secondrow_secondcolumn_thirdrow ends here*/
    	  				
    	  				        
    	  				/*thirdrow_secondcolumn_secondrow_secondcolumn ends here*/
    	  			/*thirdrow_secondcolumn_secondrow ends here*/
    	  			
    	  			/*thirdrow_secondcolumn_thirdrow starts here*/
    	  			int thirdrow_secondcolumn_thirdrow_layout_width=(int)(thirdrow_secondcolumn_layout_width);
    	  			int thirdrow_secondcolumn_thirdrow_layout_height=(int)(thirdrow_secondcolumn_layout_height*0.13);
    	  			LinearLayout thirdrow_secondcolumn_thirdrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow);
    	  			thirdrow_secondcolumn_thirdrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_layout_width,thirdrow_secondcolumn_thirdrow_layout_height));
    	  			//thirdrow_secondcolumn_thirdrow_linear_layout.setBackgroundColor(Color.RED);
    	  			
    	  				/*thirdrow_secondcolumn_thirdrow_firstcolumn starts here*/
    	  				int thirdrow_secondcolumn_thirdrow_firstcolumn_layout_width=(int)(thirdrow_secondcolumn_thirdrow_layout_width*0.01);
    	  				int thirdrow_secondcolumn_thirdrow_firstcolumn_layout_height=(int)(thirdrow_secondcolumn_thirdrow_layout_height);
    	  				LinearLayout thirdrow_secondcolumn_thirdrow_firstcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_firstcolumn);
    	  				thirdrow_secondcolumn_thirdrow_firstcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_firstcolumn_layout_width,thirdrow_secondcolumn_thirdrow_firstcolumn_layout_height));
    	  				//thirdrow_secondcolumn_thirdrow_firstcolumn_linear_layout.setBackgroundColor(Color.GRAY);
    	  				/*thirdrow_secondcolumn_thirdrow_firstcolumn ends here*/
    	  				
    	  				/*thirdrow_secondcolumn_thirdrow_secondcolumn starts here*/
    	  				int thirdrow_secondcolumn_thirdrow_secondcolumn_layout_width=(int)(thirdrow_secondcolumn_thirdrow_layout_width*0.99);
    	  				int thirdrow_secondcolumn_thirdrow_secondcolumn_layout_height=(int)(thirdrow_secondcolumn_thirdrow_layout_height);
    	  				LinearLayout thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_secondcolumn);
    	  				thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_width,thirdrow_secondcolumn_thirdrow_secondcolumn_layout_height));
    	  				//thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout.setBackgroundColor(Color.GREEN);
    	  				
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow starts here*/
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_height*0.2);
    	  					LinearLayout thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow);
    	  					thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_layout_width,thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_layout_height));
    	  					//thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow_linear_layout.setBackgroundColor(Color.RED);
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_firstrow ends here*/
    	  					
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow starts here*/    
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_height*0.6); 
    	  					LinearLayout thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow);
    	  					thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width,thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height));
    	  					//thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.setBackgroundColor(Color.YELLOW);
    	  					    
    	  						
    	  						/*vertical2 starts here*/
    	  						int vertical2_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width*0.09);
    	  						int vertical2_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout vertical2_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.vertical2);
    	  						vertical2_linear_layout.setLayoutParams(new LayoutParams(vertical2_layout_width,vertical2_layout_height));
    	  						//vertical2_linear_layout.setBackgroundColor(Color.WHITE);
    	  						btn_refresh_menu=(ImageView)vertical2_linear_layout.findViewById(R.id.btn_refresh_menu);
    	  						btn_refresh_menu.setBackgroundResource(R.drawable.refresh);
    	  						btn_refresh_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
    	  						/*vertical2 ends here*/
    	  						
    	  						/*vertical3 starts here*/
    	  						int vertical3_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width*0.82);
    	  						int vertical3_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout vertical3_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.vertical3);
    	  						vertical3_linear_layout.setLayoutParams(new LayoutParams(vertical3_layout_width,vertical3_layout_height));
    	  						//vertical3_linear_layout.setBackgroundColor(Color.YELLOW);
    	  						/*vertical3 ends here*/
    	  						
    	  						/*vertical4 starts here*/
    	  						int vertical4_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width*0.07);
    	  						int vertical4_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout vertical4_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.vertical4);
    	  						vertical4_linear_layout.setLayoutParams(new LayoutParams(vertical4_layout_width,vertical4_layout_height));
    	  						//vertical4_linear_layout.setBackgroundColor(Color.GREEN);
//    	  						
    	  						btn_logout_menu=(ImageView) vertical4_linear_layout.findViewById(R.id.btn_logout_menu);
    	  						btn_logout_menu.setLayoutParams(new LayoutParams(vertical4_layout_width,vertical4_layout_height));
    	  						btn_logout_menu.setBackgroundResource(R.drawable.logout);
    	  						btn_logout_menu.setOnClickListener(new EventHandler_Logout_Refresh_Reject_Recieve());
    	  						   
    	  						/*vertical4 ends here*/
    	  						
    	  						/*vertical5 starts here*/
    	  						int vertical5_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_width*0.02);
    	  						int vertical5_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_layout_height);
    	  						LinearLayout vertical5_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow_linear_layout.findViewById(R.id.vertical5);
    	  						vertical5_linear_layout.setLayoutParams(new LayoutParams(vertical5_layout_width,vertical5_layout_height));
    	  						//vertical5_linear_layout.setBackgroundColor(Color.RED);
    	  						/*vertical5 ends here*/
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_secondrow ends here*/
    	  					
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow starts here*/
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_layout_width=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_width);
    	  					int thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_layout_height=(int)(thirdrow_secondcolumn_thirdrow_secondcolumn_layout_height*0.2);
    	  					LinearLayout thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_secondcolumn_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow);
    	  					thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_layout_width,thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_layout_height));
    	  					//thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow_linear_layout.setBackgroundColor(Color.WHITE);
    	  					/*thirdrow_secondcolumn_thirdrow_secondcolumn_thirdrow ends here*/
    	  				/*thirdrow_secondcolumn_thirdrow_secondcolumn ends here*/
    	  				
    	  				/*thirdrow_secondcolumn_thirdrow_thirdcolumn starts here*/
    	  				int thirdrow_secondcolumn_thirdrow_thirdcolumn_layout_width=(int)(thirdrow_secondcolumn_thirdrow_layout_width*0.04);
    	  				int thirdrow_secondcolumn_thirdrow_thirdcolumn_layout_height=(int)(thirdrow_secondcolumn_thirdrow_layout_height);
    	  				LinearLayout thirdrow_secondcolumn_thirdrow_thirdcolumn_linear_layout=(LinearLayout) thirdrow_secondcolumn_thirdrow_linear_layout.findViewById(R.id.thirdrow_secondcolumn_thirdrow_thirdcolumn);
    	  				thirdrow_secondcolumn_thirdrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_secondcolumn_thirdrow_thirdcolumn_layout_width,thirdrow_secondcolumn_thirdrow_thirdcolumn_layout_height));
    	  				//thirdrow_secondcolumn_thirdrow_thirdcolumn_linear_layout.setBackgroundColor(Color.WHITE);
    	  				/*thirdrow_secondcolumn_thirdrow_thirdcolumn ends here*/
    	  			/*thirdrow_secondcolumn_thirdrow ends here*/
    	  		/*thirdrow_secondcolumn ends here*/
    	  		
    	  		/*thirdrow_thirdcolumn starts here*/
    	  		int thirdrow_thirdcolumn_layout_width=(int)(thirdrow_layout_width*0.06);
    	  		int thirdrow_thirdcolumn_layout_height=(int)(thirdrow_layout_height);
    	  		LinearLayout thirdrow_thirdcolumn_linear_layout=(LinearLayout) thirdrow_linear_layout.findViewById(R.id.thirdrow_thirdcolumn);
    	  		thirdrow_thirdcolumn_linear_layout.setLayoutParams(new LayoutParams(thirdrow_thirdcolumn_layout_width,thirdrow_thirdcolumn_layout_height));
    	  		//thirdrow_thirdcolumn_linear_layout.setBackgroundColor(Color.CYAN);
    	  		/*thirdrow_thirdcolumn ends here*/
    	  		     
    	  		
    	  		
    	  /*thirdrow ends here */
      }
    public void initialize_dialog_screen(View dialogview,int screenWidthDp,int screenHeightDp)
     {
    	return_status_async_response_point_transfer=0;
    	/*To display alert dialog box on onclick button starts here*/ 
    	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    	builder.setView(dialogview);
    	final AlertDialog alert=builder.create();
    	alert.setCancelable(false);
	    alert.setCanceledOnTouchOutside(false);
    	alert.show();
    	/*To display alert dialog box on onclick button starts here*/ 
    	/*set width and height of dialogbox  starts here*/
    	int screenWidthDpDialog=(int)(screenWidthDp*0.48);
    	int screenHeightDpDialog=(int)(screenHeightDp*0.47);
    	alert.getWindow().setLayout(screenWidthDpDialog,screenHeightDpDialog);
    	//alert.getWindow().findViewById(R.id.pointtransferdialogmain).setBackgroundColor(Color.GREEN);
    	
    	/*declare dialog inner layouts width and height starts here */
    	
        int secondrow_firstdialogvertical_width=(int)(screenWidthDpDialog*0.34);
        int secondrow_seconddialogvertical_width=(int)(screenWidthDpDialog*0.25);
        int secondrow_thirddialogvertical_width=(int)(screenWidthDpDialog*0.30);
        int fourthrow_firstdialogvertical_width=(int)(screenWidthDpDialog*0.34);
        int fourthrow_seconddialogvertical_width=(int)(screenWidthDpDialog*0.25);
        int fourthrow_thirddialogvertical_width=(int)(screenWidthDpDialog*0.30);
        int sixthrow_firstdialogvertical_width=(int)(screenWidthDpDialog*0.34);
        int sixthrow_seconddialogvertical_width=(int)(screenWidthDpDialog*0.25);
        int sixthrow_thirddialogvertical_width=(int)(screenWidthDpDialog*0.30);
        int eighthrow_firstdialogvertical_width=(int)(screenWidthDpDialog*0.17);
        int eighthrow_seconddialogvertical_width=(int)(screenWidthDpDialog*0.20);
        int eighthrow_thirddialogvertical_width=(int)(screenWidthDpDialog*0.13);
        int eighthrow_fourthdialogvertical_width=(int)(screenWidthDpDialog*0.20);
        int eighthrow_fifthdialogvertical_width=(int)(screenWidthDpDialog*0.15);
        int tenthrow_firstdialogvertical_width=(int)(screenWidthDpDialog*0.24);
        int tenthrow_seconddialogvertical_width=(int)(screenWidthDpDialog*0.70);
        
        final EditText txt_accountno_reciever=(EditText) dialogview.findViewById(R.id.txt_accountno_reciever);
        final EditText txt_pin_sender=(EditText) dialogview.findViewById(R.id.txt_pin_sender);
        final EditText txt_amount_to_transfer=(EditText) dialogview.findViewById(R.id.txt_amount_to_transfer);
        final TextView txt_response_point_transfer=(TextView) dialogview.findViewById(R.id.txt_response_point_transfer);
        /*declare dialog inner layouts width and height ends here */
        
        /*firstdialogrow starts here*/
    	alert.getWindow().findViewById(R.id.firstdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDpDialog*0.12)));
        //alert.getWindow().findViewById(R.id.firstdialogrow).setBackgroundColor(Color.BLUE);
    	/*firstdialogrow starts here*/
    	
    	/*seconddialogrow starts here*/
        alert.getWindow().findViewById(R.id.seconddialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDpDialog*0.10)));
        //alert.getWindow().findViewById(R.id.seconddialogrow).setBackgroundColor(Color.YELLOW);
        	/*secondrow_firstdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.secondrow_firstdialogvertical).setLayoutParams(new LayoutParams(secondrow_firstdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.secondrow_firstdialogvertical).setBackgroundColor(Color.RED);
        	/*secondrow_firstdialogvertical ends here*/
        	/*secondrow_seconddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.secondrow_seconddialogvertical).setLayoutParams(new LayoutParams(secondrow_seconddialogvertical_width,(int)(screenHeightDp*0.05)));
        	alert.getWindow().findViewById(R.id.secondrow_seconddialogvertical).setBackgroundResource(R.drawable.pointtransferback);	
        	/*secondrow_seconddialogvertical ends here*/
        	
        	/*secondrow_thirddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.secondrow_thirddialogvertical).setLayoutParams(new LayoutParams(secondrow_thirddialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.secondrow_thirddialogvertical).setBackgroundColor(Color.CYAN);
        	/*secondrow_thirddialogvertical starts here*/
        /*seconddialogrow ends here*/
        	
        /*thirddialogrow starts here*/
        alert.getWindow().findViewById(R.id.thirddialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDpDialog*0.05)));
        //alert.getWindow().findViewById(R.id.thirddialogrow).setBackgroundColor(Color.GREEN);
        /*thirddialogrow ends here*/
        
        /*fourthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.fourthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.05)));
        //alert.getWindow().findViewById(R.id.fourthdialogrow).setBackgroundColor(Color.RED);
          
        	/*fourthrow_firstdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.fourthrow_firstdialogvertical).setLayoutParams(new LayoutParams(fourthrow_firstdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.fourthrow_firstdialogvertical).setBackgroundColor(Color.RED);
        	/*fourthrow_firstdialogvertical ends here*/
        	/*fourthrow_seconddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.fourthrow_seconddialogvertical).setLayoutParams(new LayoutParams(fourthrow_seconddialogvertical_width,(int)(screenHeightDp*0.05)));
    		alert.getWindow().findViewById(R.id.fourthrow_seconddialogvertical).setBackgroundResource(R.drawable.pointtransferback);
    		/*fourthrow_seconddialogvertical ends here*/
    		/*fourthrow_thirddialogvertical starts here*/
    		alert.getWindow().findViewById(R.id.fourthrow_thirddialogvertical).setLayoutParams(new LayoutParams(fourthrow_thirddialogvertical_width,(int)(screenHeightDp*0.05)));
    		//alert.getWindow().findViewById(R.id.fourthrow_thirddialogvertical).setBackgroundColor(Color.CYAN);
    		/*fourthrow_thirddialogvertical ends here*/
    	/*fourthdialogrow ends here*/
    		
        /*fifthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.fifthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.02)));
        //alert.getWindow().findViewById(R.id.fifthdialogrow).setBackgroundColor(Color.GREEN);
        /*fifthdialogrow ends here*/
        
        /*sixthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.sixthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.05)));
        //alert.getWindow().findViewById(R.id.sixthdialogrow).setBackgroundColor(Color.WHITE);
        	/*sixthrow_firstdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.sixthrow_firstdialogvertical).setLayoutParams(new LayoutParams(sixthrow_firstdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.sixthrow_firstdialogvertical).setBackgroundColor(Color.RED);
        	/*sixthrow_firstdialogvertical ends here*/
        	/*sixthrow_seconddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.sixthrow_seconddialogvertical).setLayoutParams(new LayoutParams(sixthrow_seconddialogvertical_width,(int)(screenHeightDp*0.05)));
        	alert.getWindow().findViewById(R.id.sixthrow_seconddialogvertical).setBackgroundResource(R.drawable.pointtransferback);
        	/*sixthrow_seconddialogvertical ends here*/
        	/*sixthrow_thirddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.sixthrow_thirddialogvertical).setLayoutParams(new LayoutParams(sixthrow_thirddialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.sixthrow_thirddialogvertical).setBackgroundColor(Color.CYAN);
        	/*sixthrow_thirddialogvertical ends here*/
        /*sixthdialogrow ends here*/
        	
        /*seventhdialogrow starts here*/
        alert.getWindow().findViewById(R.id.seventhdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.04)));
        //alert.getWindow().findViewById(R.id.seventhdialogrow).setBackgroundColor(Color.CYAN);
        /*seventhdialogrow ends here*/
        
        /*eighthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.eighthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.05)));
        //alert.getWindow().findViewById(R.id.eighthdialogrow).setBackgroundColor(Color.MAGENTA);
        
        	/*eighthrow_firstdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.eighthrow_firstdialogvertical).setLayoutParams(new LayoutParams(eighthrow_firstdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.eighthrow_firstdialogvertical).setBackgroundColor(Color.RED);
        	/*eighthrow_firstdialogvertical ends here*/
        	/*eighthrow_seconddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.eighthrow_seconddialogvertical).setLayoutParams(new LayoutParams(eighthrow_seconddialogvertical_width,(int)(screenHeightDp*0.05)));
        	alert.getWindow().findViewById(R.id.eighthrow_seconddialogvertical).setBackgroundResource(R.drawable.transferdialog);
        	alert.getWindow().findViewById(R.id.eighthrow_seconddialogvertical).setOnClickListener(new OnClickListener() 
        	{   
				@Override
				public void onClick(View v) {
					alert.getWindow().findViewById(R.id.eighthrow_seconddialogvertical).setBackgroundResource(R.drawable.transferglowimage);
			        final String account_no_reciever=txt_accountno_reciever.getText().toString();
			        final String pin_sender=txt_pin_sender.getText().toString();
			        final String amount_to_tranfer=txt_amount_to_transfer.getText().toString();

					Toast.makeText(getApplicationContext(),"" +account_no_reciever +pin_sender +amount_to_tranfer,Toast.LENGTH_LONG).show();
					if(account_no_reciever=="" || pin_sender=="" ||amount_to_tranfer=="")
		            {
						txt_response_point_transfer.setText("Field can not be blank");
		            }
					else
					{
					Point_Transfer_Class objPoint_Transfer_Class= new Point_Transfer_Class(account_no_reciever,pin_sender,amount_to_tranfer);
					objPoint_Transfer_Class.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

		        	while(true)
		        	{
		        		int ret_status_async_point_transfer=return_status_async_response_point_transfer;
		        	   switch (ret_status_async_point_transfer) {
							
							case 1:
								txt_response_point_transfer.setText("Sorry, Receiver Account does not exists");
								break;
		
							case 2: 
					                     alert.dismiss();			
								break;
								
							case 3:
								         alert.dismiss();
								break;
								
							case 4:
								txt_response_point_transfer.setText("Sorry, Receiver account does not found");
								break;

							case 5:
								txt_response_point_transfer.setText("Point Transfer Successful");
								alert.dismiss();
				                break;

							case 6:
								txt_response_point_transfer.setText("Sorry,Point not transfer");
				            	break;

							case 7:
								txt_response_point_transfer.setText("Sorry,Invalid Account Id or Pin");
				                break;
				                
							case 8:
								txt_response_point_transfer.setText("Sorry ,Insuficient Balance");
				                break;
								
							default:
								break;
					}	
		        	
		        	}

					
					}
					}
			});
        	
        	  
        	
        	/*eighthrow_seconddialogvertical ends here*/
        	/*eighthrow_thirddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.eighthrow_thirddialogvertical).setLayoutParams(new LayoutParams(eighthrow_thirddialogvertical_width,(int)(screenHeightDp*0.05)));
    		//alert.getWindow().findViewById(R.id.eighthrow_thirddialogvertical).setBackgroundColor(Color.CYAN);
        	/*eighthrow_thirddialogvertical ends here*/
        	/*eighthrow_fourthdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.eighthrow_fourthdialogvertical).setLayoutParams(new LayoutParams(eighthrow_fourthdialogvertical_width,(int)(screenHeightDp*0.05)));
        	alert.getWindow().findViewById(R.id.eighthrow_fourthdialogvertical).setBackgroundResource(R.drawable.closedialog);
        	alert.getWindow().findViewById(R.id.eighthrow_fourthdialogvertical).setOnClickListener(new OnClickListener() 
        	{	    
				@Override
				public void onClick(View v) {
					alert.getWindow().findViewById(R.id.eighthrow_fourthdialogvertical).setBackgroundResource(R.drawable.pointcloseglow);
					alert.dismiss();
					
				}
			});    
        	/*eighthrow_fourthdialogvertical ends here*/
        	/*eighthrow_fifthdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.eighthrow_fifthdialogvertical).setLayoutParams(new LayoutParams(eighthrow_fifthdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.eighthrow_fifthdialogvertical).setBackgroundColor(Color.RED);
        	/*eighthrow_fifthdialogvertical starts here*/
        /*eighthdialogrow ends here*/
        	
        /*ninthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.ninthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.03)));
        //alert.getWindow().findViewById(R.id.ninthdialogrow).setBackgroundColor(Color.YELLOW);
        /*ninthdialogrow ends here*/
        
        /*tenthdialogrow starts here*/
        alert.getWindow().findViewById(R.id.tenthdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.04)));
        //alert.getWindow().findViewById(R.id.tenthdialogrow).setBackgroundColor(Color.YELLOW);
           
        	/*tenthrow_firstdialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.tenthrow_firstdialogvertical).setLayoutParams(new LayoutParams(tenthrow_firstdialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.tenthrow_firstdialogvertical).setBackgroundColor(Color.RED);
        	/*tenthrow_firstdialogvertical ends here*/
        	/*tenthrow_seconddialogvertical starts here*/
        	alert.getWindow().findViewById(R.id.tenthrow_seconddialogvertical).setLayoutParams(new LayoutParams(tenthrow_seconddialogvertical_width,(int)(screenHeightDp*0.05)));
        	//alert.getWindow().findViewById(R.id.tenthrow_seconddialogvertical).setBackgroundColor(Color.BLUE);
        	
        	
        	
        	
            txt_response_point_transfer.setText("Please enter correct LoginId");
            txt_response_point_transfer.setTextSize(10);
            txt_response_point_transfer.setTextColor(Color.WHITE);
        	
            
        	/*tenthrow_seconddialogvertical starts here*/
        /*tenthdialogrow ends here*/
        	     
        /*eleventhdialogrow starts here*/
        alert.getWindow().findViewById(R.id.eleventhdialogrow).setLayoutParams(new LayoutParams(screenWidthDpDialog,(int)(screenHeightDp*0.03)));
        //alert.getWindow().findViewById(R.id.eleventhdialogrow).setBackgroundColor(Color.RED);
        /*eleventhdialogrow ends here*/
        /*set width and height of dialogbox  ends here*/
     }
	
    
    public class Point_Transfer_Class extends AsyncTask<Void, Void, Void>
    {
    	  String sent_account_no_reciever,sent_pin_sender, sent_amount_to_tranfer;
          Service_Client_Interaction service_client=new Service_Client_Interaction();
          String user = GlobalClass.getUserName();
          double balance = GlobalClass.getBalance();
          int pin1;
          double transfer_point = 0;
          double receiver_point;
          String receiver_id;
          String receiver_role;
          String trans_time;
          String trans_status = "active";
          String sender_account = GlobalClass.getUserId();
          int pin = GlobalClass.getPin();
          String sender_type = GlobalClass.getSendererRole();   // show sender role
          String r_id;     //insert id by sender
          
          Point_Transfer_Class(String account_no_reciever,String pin_sender,String amount_to_tranfer)
          {
        	  sent_account_no_reciever=account_no_reciever;
        	  sent_pin_sender=pin_sender;
        	  sent_amount_to_tranfer=amount_to_tranfer;
          }
          
    	  @Override
    	  protected Void doInBackground(Void... arg0)
    	  {
    	      try
    	      {
    	    	    r_id=sent_account_no_reciever;
    	    	    String reciever_info = service_client.Get_Receiver_info_json(r_id);
    	    	    String[] reciever_info_array={"",""}; 
    	    	    
    	    	    if(reciever_info!=null)
    	    	    {
    	    	      reciever_info_array = reciever_info.split("-"); 
    	    	    }
    	    	    else
    	    	    {
    	    	    	return_status_async_response_point_transfer=1;
    	    	    	//lbl_response.Content = "Sorry,Receiver Account not exist";
    	    	    }
    	    	    
    	    	      receiver_id  =reciever_info_array[0];
    	    	      receiver_role=reciever_info_array[1];
    	    	      
    	    	        pin1 = Integer.parseInt(sent_pin_sender);
    	                transfer_point = Double.parseDouble(sent_amount_to_tranfer);
    	                receiver_point = transfer_point;
    	                int tmp = 0;
    	                
    	                if (r_id == sender_account)
    	                {
    	                	return_status_async_response_point_transfer=2;
    	                    //MessageBox.Show("Sorry you can not transfer to your own account", "PCM");
    	                    tmp = 1;
    	                    r_id = "";
    	                    //this.close();
    	                }
    	                
    	              //if receiver id exist so point can be transfer
    	                if (receiver_id != null)
    	                {
    	                  // point transfer not allow  if sender is Player and Receiver is player
    	                    if (sender_type == "player" && receiver_role == "player")
    	                    {
    	                        if(tmp==0)
    	                        {
    	                        	return_status_async_response_point_transfer=3;
    	                        //MessageBox.Show("Sorry,Point can not transfer for same type", "PCM");
    	                        }
    	                      //this.close();
    	                    }
    	                    // point transfer successsful in sender is agent and Receiver is player
    	                    else if (sender_type == "agent" && receiver_role == "agent")
    	                    {
    	                        if(tmp==0)
    	                        {
    	                        	return_status_async_response_point_transfer=3;
    	                            //MessageBox.Show("Sorry,Point can not transfer for same type", "PCM");
    	                        }
    	                        //this.close();
    	                    }
    	                    else
    	                    {
    	                        Point_Transfer_Process();
    	                    }
    	                }
    	                else
    	                {
    	                	return_status_async_response_point_transfer=4;
    	                   // lbl_response.Content = "Sorry Receiver Account not found";
    	                }
     
    	                
    	    	   
    	    	    
    	    	    int m=2;
    	    	    int n=m;
              }
    	      catch (Exception e)   {
    	            e.printStackTrace();
    	         }

    	      return null;
    	   }

    	  
    	  
          private void Point_Transfer_Process()
          {
              // check available balance is sufficient or not
              if (balance >= transfer_point)
              {
                  // check pin and receiver id is correct or not
                  if ((pin1 == pin) && (r_id.equalsIgnoreCase(receiver_id)))
                  {
                      int save_point_transfer = service_client.Save_Trasaction_Info_json(receiver_id, receiver_role, Double.toString(receiver_point), sender_type, Double.toString(transfer_point),trans_status,GlobalClass.getUserId());

                     
                      // check point transfer successfully or not
                      if (save_point_transfer > 0)
                      {
                          balance -= transfer_point;
                          GlobalClass.setBalance((int)balance);
                          int update_balance = service_client.Update_User_Account_Balance_json("-"+transfer_point,GlobalClass.getUserId());
                          return_status_async_response_point_transfer=5;
                          //lbl_response.Content = "Point Transfer Successful";
                          /*txt_receiver_account.Text = string.Empty;
                          txt_pin.Text = string.Empty;
                          txt_amount.Text = string.Empty;                        
                          Switcher.Switch(new GameList1());*/
                      }
                      else
                      {
                    	  return_status_async_response_point_transfer=6;
                         // lbl_response.Content = "Sorry,Point not transfer";
                      }
                  }
                  else
                  {
                	  return_status_async_response_point_transfer=7;
                     // lbl_response.Content = "Sorry,Invalid Account Id or Pin";
                  }
              }
              else
              {
            	  return_status_async_response_point_transfer=8;
                 // lbl_response.Content = "Sorry ,Insuficient Balance";
              }
          }

    	  
    	  
    	  
    	   protected void onPostExecute(Void result) {		  }

    	 }

    
    
 	 @Override
 	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		super.onActivityResult(requestCode, resultCode, data);
 		if(resultCode==RESULT_OK)
 		{
 			
 		
 		}
 	}
 	
     public class Get_Transfer_Datagrid_Class extends AsyncTask<Void, Void, Void>
     { 
           int length1;
           int status=0;
           Service_Client_Interaction client=new Service_Client_Interaction();
          
           @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
        	   Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
     	      try
     	      {
     	    	 list_receiver_list= client.Show_Receiver_Datagrid_Records(transaction_status,GlobalClass.getUserId());
      	    	length1= list_receiver_list.size();
      	    	int m1=length1;
     	    	  
     	    	  
     	    	 list_transfer_list= client.Show_Transfer_DataGrid_Records(transaction_status,GlobalClass.getUserId());
     	    	length1= list_transfer_list.size();
       	    	int m=length1;
       	    	
       	    	 status=1;
       	    	
       	    
              }
     	      catch (Exception e)   {
     	            e.printStackTrace();
     	         }
     	     return_status_async_list=status;
     	      return null;
     	   }

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
		}

     	

     	  
     	 }
 
     public class Get_Login_User_Details_Class extends AsyncTask<Void, Void, Void>
     {
           Service_Client_Interaction client=new Service_Client_Interaction();
     	  @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     	      try
     	      {
     	    	  client.Show_user_detail(GlobalClass.getUserId());
     	    	  
     	    	   GlobalClass.setPin(Login_User_Details.USER_PIN);
     	    	   GlobalClass.setBalance(Login_User_Details.USER_BALANCE);
     	    	   GlobalClass.setSenderRole(Login_User_Details.SENDER_ROLE);
     	    	   GlobalClass.setUserName(Login_User_Details.USERNAME);
               }
     	      catch (Exception e)   {
     	            e.printStackTrace();
     	         }

     	      return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
         
     public class Get_Window_Expiration_Status_Class extends AsyncTask<Void, Void, Void>
     {
           Service_Client_Interaction client=new Service_Client_Interaction();
     	  @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     	      try
     	      {
     	    		Service_Client_Interaction service_client=new Service_Client_Interaction();
     	       	    //int del = service_client.Delete_User_Id_During_Logout_json(globalVariable.getUserId(),globalVariable.getWindow_id());
     	       	
     	      	   String WINDOWID=GlobalClass.getWindow_id();
     	           int count = service_client.checkwindowid_expire_status_json(WINDOWID);
     	           if (count == 0)
     	           {
     	        	   window_expired_status=true;
     	        	   
     	               Expire_Session_And_Cancel_Bet();
     	               //MessageBox.Show("Session Expire", "PCM");
     	           }
               }
     	      catch (Exception e)   {
     	            e.printStackTrace();
     	         }

     	      return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
     
     public class Delete_User_Id_During_Logout_Class extends AsyncTask<Void, Void, Void>
     {
          @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     	      try
     	      {
     	    	   service_client=new Service_Client_Interaction();
       	      	   String WINDOWID=GlobalClass.getWindow_id();
     	    	   int del = service_client.Delete_User_Id_During_Logout_json(GlobalClass.getUserId(),WINDOWID);
               }
     	      catch (Exception e)   {
     	            e.printStackTrace();
     	         }

     	      return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
     
     public class Update_User_Transaction_Balance_Recievables_Recieve_Class extends AsyncTask<Void, Void, Void>
     {
           Service_Client_Interaction service_client=new Service_Client_Interaction();
     	  @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     		 int update_balance = 0;
             int update_transaction = 0;
             int cnt = 0;
             double balance = 0;
             
             try{
            	 if(lst_to_update_receivables!=null)
            	 {
            		 for(int position=0;position<lst_to_update_receivables.size();position++)
            		 { 
            			  cnt++;
            			  String m=lst_to_update_receivables.get(position).toString();
            			  String charToDel = "[]";
            			  String pat = "[" + Pattern.quote(charToDel) + "]";
            			  m = m.replaceAll(pat,"");
            			  m=m.replace(" ", "");
            			
            			  String[] arr = m.split(",");
            			  
            			  String id=arr[0];
            			  String SAMOUNT=arr[3];
            			  status = "Receive";
             			  transaction_status = "";	
             			  update_transaction = service_client.Update_Point_Transaction_Status_json(status, id);
             			 
             			 //condition to check cancel transaction succesful then user main balance updated 
                         if (update_transaction > 0)
                         {
                             double samount= Double.parseDouble(SAMOUNT);
                             balance = balance + samount;
                         }
            		 }
            		 	 
                     if(balance!=0)
                         update_balance = service_client.Update_User_Account_Balance_json(Double.toString(balance),GlobalClass.getUserId());
                         if (update_balance > 0)
                         {
                        	 GlobalClass.setBalance(GlobalClass.getBalance()+(int)balance);
                             int b=GlobalClass.getBalance();
                             int mm=b;
                             int mm1=mm;
                             //lbl_response.Content = "Congratulation,Point receive success";
                             //MessageBox.Show("Point Receive and Account Updated", "PCM");
                         }
                         else
                         {
                            // MessageBox.Show("Invalid Transaction", "PCM");
                         }
            	 }
            	 else
        		 {
        			 int tt=0;
        			 int mm=tt;
        			 // aleast select one row to recieve
        		 }
            	 
             }
             catch(Exception e)
             {
            	 e.printStackTrace();
             }
             
             /*foreach (Point_Transaction c in data_grid_receiver.ItemsSource)
             {
                 if (c.IsSelected)
                 {
                     cnt++;
                     int id = c.ID;
                     status = "Receive";
                     transaction_status = "";
                     update_transaction = service_client.Update_Point_Transaction_Status(status, id);
                     Switcher.Switch(game_list);
                     //condition to check cancel transaction succesful then user main balance updated 
                     if (update_transaction > 0)
                     {
                         //user_balance += balance;                        
                         balance = balance + c.SAMOUNT;
                     }
                 }                
             }*/
             /*if(balance!=0)
             update_balance = service_client.Update_User_Account_Balance_json(balance);
             if (update_balance > 0)
             {
                 user_balance += balance;
                 User_Detail_Pass_Value.BALANCE = user_balance;
                 //lbl_response.Content = "Congratulation,Point receive success";
                 MessageBox.Show("Point Receive and Account Updated", "PCM");
                 //Switcher.Switch(game_list);
                 Show_Receiver_DataGrid_Records();
                 lbl_point.Content = user_balance;
             }
             else
             {
                 MessageBox.Show("Invalid Transaction", "PCM");
                 Show_Receiver_DataGrid_Records();
             }
     		  
     	      try
     	      {
     	    		service_client=new Service_Client_Interaction();
     	            int update_user_acccount_balance_status_value = service_client.Update_User_Account_Balance_json(globalclass.getBalance_to_update(),globalclass.getUserId());
     	    		//int update_transaction = service_client.Update_Point_Transaction_Status_json(status,8);
     	      }
     	      catch (Exception e)   {
     	            e.printStackTrace();
     	         }
*/
             return_status_async_recievables_recieve=1;
     	      return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
     
     public class Update_User_Transaction_Balance_Recievables_Reject_Class extends AsyncTask<Void, Void, Void>
     {
          Service_Client_Interaction service_client=new Service_Client_Interaction();
     	  @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     		 int rjt = 0;
             
             try{
            	 if(lst_to_update_receivables!=null)
            	 {
            		 for(int position=0;position<lst_to_update_receivables.size();position++)
            		 { 
               			  String m=lst_to_update_receivables.get(position).toString();
            			  String charToDel = "[]";
            			  String pat = "[" + Pattern.quote(charToDel) + "]";
            			  m = m.replaceAll(pat,"");
            			  m=m.replace(" ", "");
            			  String[] arr = m.split(",");
            			  
            			  String id=arr[0];
            			  status = "Reject";
             			  transaction_status = "";	
             			 if (id != null)
                         {
                             rjt = 1;
                             int update_transaction = service_client.Update_Point_Transaction_Status_json(status, id);
                             
                             //lbl_response.Content = "Point Rejected";
                             /*if (update_transaction > 0)
                                MessageBox.Show("Point Rejected", "PCM");
                             else
                                 MessageBox.Show("Point Not Rejected", "PCM");*/
                         }
             			 
            		 }
            	   }
             	}
	             catch(Exception e)
	             {
	            	 e.printStackTrace();
	             }
             return_status_async_recievables_reject=1;
            return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
               
     public class Update_User_Transaction_Balance_Transferables_Reject_Class extends AsyncTask<Void, Void, Void>
     {
           Service_Client_Interaction service_client=new Service_Client_Interaction();
     	  @Override
     	  protected Void doInBackground(Void... arg0)
     	  {
     		 int update_balance = 0;
             int update_transaction = 0;
             int cnt = 0;
             double balance = 0;
             
             try{
            	 if(lst_to_update_transferables!=null)
            	 {
            		 for(int position=0;position<lst_to_update_transferables.size();position++)
            		 { 
            			  cnt++;
            			  String m=lst_to_update_transferables.get(position).toString();
            			  String charToDel = "[]";
            			  String pat = "[" + Pattern.quote(charToDel) + "]";
            			  m = m.replaceAll(pat,"");
            			  m=m.replace(" ", "");
            			
            			  String[] arr = m.split(",");
            			  
            			  String id=arr[0];
            			  String RAMOUNT=arr[3];
            			  status = "Cancel";
             			  transaction_status = "";	
             			  update_transaction = service_client.Update_Point_Transaction_Status_json(status, id);
             			 
             			 //condition to check cancel transaction succesful then user main balance updated 
                         if (update_transaction > 0)
                         {
                             double ramount= Double.parseDouble(RAMOUNT);
                             balance = balance + ramount;
                         }
            		 }
            		 	 
                     if(balance!=0)
                         update_balance = service_client.Update_User_Account_Balance_json(Double.toString(balance),GlobalClass.getUserId());
                         if (update_balance > 0)
                         {
                        	 GlobalClass.setBalance(GlobalClass.getBalance()+(int)balance);
                             int b=GlobalClass.getBalance();
                             int mm=b;
                             int mm1=mm;
                             //lbl_response.Content = "Congratulation,Point receive success";
                             //MessageBox.Show("Point Receive and Account Updated", "PCM");
                         }
                         else
                         {
                            // MessageBox.Show("Invalid Transaction", "PCM");
                         }
            	 }
            	 else
        		 {
        			 int tt=0;
        			 int mm=tt;
        			 // aleast select one row to recieve
        		 }
            	 
             }
             catch(Exception e)
             {
            	 e.printStackTrace();
             }
             
  
             return_status_async_transferables_reject=1;
     	      return null;
     	   }

     	   protected void onPostExecute(Void result) {		  }

     	 }
     
     class EventHandler_Logout_Refresh_Reject_Recieve implements OnClickListener
 	 {
    		Service_Client_Interaction service_client=new Service_Client_Interaction();
    		
 		@Override
 		public void onClick(View v)
 		{
 			if(R.id.btn_logout_menu==v.getId())
            {
 				btn_logout_menu.setBackgroundResource(R.drawable.logoutglow);
 				//Toast.makeText(getApplicationContext(), "btn logout clicked", Toast.LENGTH_SHORT).show();
	        	   //window_expired_status=true;
	        	   
	        	  /* new Delete_User_Id_During_Logout_Class().execute();
 	  			   Expire_Session_And_Cancel_Bet();*/
 				
/* 				dispatcherTimer.Stop();
 	            
 	            if (Fun_Roulette_Messages.is_Bet_Taken == false && Fun_Target_Messages.is_Bet_Taken == false)
 	            {
 	                //var funroulett = fun_roulette as IDisposable;
 	                if (fun_roulette != null)
 	                {
 	                    fun_roulette = null;
 	                    fun_roulette_ref = null;
 	                    GC.Collect();                    
 	                    GC.WaitForPendingFinalizers();
 	                    //GC.SuppressFinalize(fun_roulette);
 	                   
 	                    //((IDisposable)funroulett).Dispose();
 	                }
 	                
 	                ///var fun_target = funtarget as IDisposable;
 	                if (funtarget != null)
 	                {
 	                    funtarget = null;
 	                    funtarget_ref = null;
 	                    GC.Collect();
 	                    GC.WaitForPendingFinalizers();
 	                    //GC.SuppressFinalize(funtarget);
 	                    
 	                    //((IDisposable)fun_target).Dispose();
 	                }
 	                
 	                
 	            }*/
            }
 			else if(R.id.btn_refresh_menu==v.getId())
 			{
 				btn_refresh_menu.setBackgroundResource(R.drawable.refreshglow);
 				//Toast.makeText(getApplicationContext(), "new refresh clicked", Toast.LENGTH_SHORT).show();
 				Intent call_main_activity_intent=new Intent(MenuActivity.this,MenuActivity.class);
 				startActivity(call_main_activity_intent);
 			}
 			else if(R.id.btn_receivables_receive_menu==v.getId())
 			{
 				Toast.makeText(getApplicationContext(), "Receivable new receive clicked", Toast.LENGTH_LONG).show();
 				new Update_User_Transaction_Balance_Recievables_Recieve_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
 		        try {
 		        	while(true)
 		        	{
 		        		
 		        	if(return_status_async_recievables_recieve==0)
 		        	{
 		        	}
 		        	else if(return_status_async_recievables_recieve==1)
 		        	{
 		 				Intent call_main_activity_intent=new Intent(MenuActivity.this,MenuActivity.class);
 		 				startActivity(call_main_activity_intent);	
 		        		break;
 		        	}
 		        	}
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 			else if(R.id.btn_receivables_reject_menu==v.getId())
 			{
 				Toast.makeText(getApplicationContext(), "Receivable new reject clicked", Toast.LENGTH_LONG).show();
 				new Update_User_Transaction_Balance_Recievables_Reject_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
 		        try {
 		        	while(true)
 		        	{
 		        		
 		        	if(return_status_async_recievables_reject==0)
 		        	{
 		        	}
 		        	else if(return_status_async_recievables_reject==1)
 		        	{
 		 				Intent call_main_activity_intent=new Intent(MenuActivity.this,MenuActivity.class);
 		 				startActivity(call_main_activity_intent);
 		 				
 		        		break;
 		        	}
 		        	}
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 			}
 			else if(R.id.btn_transferables_reject_menu==v.getId())
 			{
 				Toast.makeText(getApplicationContext(), "Transferables reject clicked", Toast.LENGTH_LONG).show();
 				new Update_User_Transaction_Balance_Transferables_Reject_Class().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
 		        try {
 		        	while(true)
 		        	{
 		        		
 		        	if(return_status_async_transferables_reject==0)
 		        	{
 		        	}
 		        	else if(return_status_async_transferables_reject==1)
 		        	{
 		 				Intent call_main_activity_intent=new Intent(MenuActivity.this,MenuActivity.class);
 		 				startActivity(call_main_activity_intent);	
 		        		break;
 		        	}
 		        	}
 				} catch (Exception e) {
 					e.printStackTrace();
 				}
 	
 			}
 			else if(R.id.btn_OK_menu==v.getId())
 			{
 				Toast.makeText(getApplicationContext(), "new OK clicked", Toast.LENGTH_LONG).show();
 			}
    	}
 	 
 	 }

 	
    }




/*class to access the list values starts here*/
class listadapter1 extends ArrayAdapter<Integer>
{
	Context context;
	Integer[] titlearray;
	List list123;
	int layout_name;
	int gridvertical1_layout_width,gridvertical1_layout_height;
	
	listadapter1(Context c, Integer[] titles,List listabc, int layout_name,int gridvertical1_layout_width,int gridvertical1_layout_height)
	{
		//super();
		super(c, layout_name,R.id.textView1,titles);
		this.context=c;
		this.titlearray=titles;
		this.list123=listabc;
		this.layout_name=layout_name;
		this.gridvertical1_layout_width=gridvertical1_layout_width;
		this.gridvertical1_layout_height=gridvertical1_layout_height;
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(layout_name, parent,false);
		TextView Mytextview=(TextView) row.findViewById(R.id.textView1);
		TextView Mytextview1=(TextView) row.findViewById(R.id.textView2);
		TextView Mytextview2=(TextView) row.findViewById(R.id.textView3);
		TextView Mytextview3=(TextView) row.findViewById(R.id.textView4);
		
		  String m=list123.get(position).toString();
		  String charToDel = "[]";
		  String pat = "[" + Pattern.quote(charToDel) + "]";
		  m = m.replaceAll(pat,"");
		  m=m.replace(" ", "");
		
		  String[] arr = m.split(",");
		  
			String a=arr[0];
			String b=arr[1];
			String c=arr[2];
			String d=arr[3];
            		  Mytextview.setText(a);
            		  Mytextview1.setText(b);
            		  Mytextview2.setText(c);
            		  Mytextview3.setText(d);
		  //Mytextview1.setText(list123.get(1).toString());
		
		return row;
	}
}
/*class to access the list values ends here*/




/*class to access the list values starts here*/
class listadapter2 extends ArrayAdapter<Integer>
{
	Context context;
	Integer[] titlearray;
	List list123;
	int layout_name;
	int gridvertical2_layout_width,gridvertical2_layout_height;
	
	listadapter2(Context c, Integer[] titles,List listabc, int layout_name,int gridvertical2_layout_width,int gridvertical2_layout_height)
	{
		//super();
		super(c, layout_name,R.id.textView1,titles);
		this.context=c;
		this.titlearray=titles;
		this.list123=listabc;
		this.layout_name=layout_name;
		this.gridvertical2_layout_width=gridvertical2_layout_width;
		this.gridvertical2_layout_height=gridvertical2_layout_height;
	}
	
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(layout_name, parent,false);
		TextView Mytextview=(TextView) row.findViewById(R.id.textView1);
		TextView Mytextview1=(TextView) row.findViewById(R.id.textView2);
		TextView Mytextview2=(TextView) row.findViewById(R.id.textView3);
		TextView Mytextview3=(TextView) row.findViewById(R.id.textView4);
		
		  String m=list123.get(position).toString();
		  String charToDel = "[]";
		  String pat = "[" + Pattern.quote(charToDel) + "]";
		  m = m.replaceAll(pat,"");
		  m=m.replace(" ", "");
		
		  String[] arr = m.split(",");
		  
			String a=arr[0];
			String b=arr[1];
			String c=arr[2];
			String d=arr[3];
            		  Mytextview.setText(a);
            		  Mytextview1.setText(b);
            		  Mytextview2.setText(c);
            		  Mytextview3.setText(d);
		  //Mytextview1.setText(list123.get(1).toString());
		
		return row;
	}
}
/*class to access the list values ends here*/


/*class to access the list values starts here*/
class listadapter extends ArrayAdapter<String>
{
	Context context;
	int images[];
	String[] titlearray;
	listadapter(Context c,String[] titles,int imgs[])
	{
		super(c, R.layout.single_row,R.id.textView1,titles);
		this.context=c;
		this.images=imgs;
		this.titlearray=titles;
	}
	@SuppressLint("ViewHolder") @Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inflater.inflate(R.layout.single_row, parent,false);
		ImageView Myimage=(ImageView) row.findViewById(R.id.imageView1);
		TextView Mytextview=(TextView) row.findViewById(R.id.textView1);
		Myimage.setImageResource(images[position]);
		Mytextview.setText(titlearray[position]);
		return row;
	}
}
/*class to access the list values ends here*/