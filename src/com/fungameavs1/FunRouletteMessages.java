package com.fungameavs1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import android.graphics.Color;
import android.os.Handler;
import android.provider.CalendarContract.Colors;
import android.util.Log;
import android.widget.Toast;

import com.fungameavs1.FunRouletteActivity.ABC;
import com.fungameavs1.FunRouletteActivity.ABC.StringInt_keys_Bet_Taken;
import com.fungameavs1.Result_Request_Worker_Thread.Bet_Send_Result;
import com.fungameavs1.Result_Request_Worker_Thread.Request_Type;

public class FunRouletteMessages {
	
	
	
	

	
	
	public enum Button_Group
	{
	    //You can initialize enums using enumname(value)
		BET_OK(0),
		BET_PREVIOUS_OK(1),
		BET_TAKE(2),
		NO(3);
	    
	    public int button_group_value;
	    //Constructor which will initialize the enum
	    Button_Group(int val)
	    {
	    	button_group_value = val;
	    }
	}

	 
	static boolean button_Glow = true;
	public  static   Button_Group button_Group_Bet_OK_Take = Button_Group.NO;
	public static int enum_Bet_OK_Button_Group = -1;
	Button_Group button_group=null;
	public static int winning_amount_in_last_game = 0;
    int[] bet_Values;
    int[] red_Ids = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    int[] black_Ids = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };
    boolean is_First_Time_Window_Loaded = true;
	public static boolean check_for_player_session = false;
	public static boolean is_Bet_Taken = false;
    public  int time_2_Start = 60;
    public static int total_Bet_Amount_On_Current_Game = 0;
    public static boolean is_bet_cancel = false;
    public static Result_Request_Worker_Thread result_Request_Thred = null;
    public static int amount_Won = 0;
    public static int player_balance_for_history = 0; //player balance for update history       
    boolean is_winning_amount_take = false;
    public static FunRouletteMessages fun_roulette_messsages=null;
	static FunRouletteActivity objFunRouletteActivity=null;
	 
	
	
	
	
	
	public boolean Is_Bet_OK_Blinking()
    {
         if(enum_Bet_OK_Button_Group == (int) Button_Group.BET_OK.button_group_value)
             return true;
         return false;
    }
    public boolean Is_Bet_Previous_Blinking()
    {
        if (enum_Bet_OK_Button_Group == (int)Button_Group.BET_PREVIOUS_OK.button_group_value)
            return true;
        return false;
    }
	
	
	
	
	
    static int stop_At_roulett = -1;
    public static void ReSet_Number_2_Stop_Roulette(int stop_Number)
    {
        stop_At_roulett =  stop_Number;
    }
    public static int Stop_At_Roulette(int stop_Number)
    {
        return stop_At_roulett=stop_Number;
    }

    
    public void Prepare_Next_Game()
    {
        result_Request_Thred.Start(Request_Type.SET_UP_NEXT_GAME);
        objFunRouletteActivity.Update_Recent_List();
    }
    
   
    
    
    

    

    
    public void Clear_Total_Bet_Taken_Details()
    {
        total_Bet_Amount_On_Current_Game = 0;
       objFunRouletteActivity.Update_Total_Bet_Taken_4_Current_Game(true);
    }
    
    public void Update_Balance_In_Fun_ROulette()
    {           
        if (winning_amount_in_last_game != 0)
        {
            //Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_TAKE);
            GlobalClass.setBalance(GlobalClass.getBalance() + winning_amount_in_last_game);
            is_winning_amount_take = true;
            is_Bet_Taken = false;
            objFunRouletteActivity.Update_Amount_Won();                
            winning_amount_in_last_game = 0;
        }
    }
    
    
  
 // Bet_Ok or after 10 - 0 seconds messages 
    public void Update_Hint_Message_Time_Over_OR_Bet_Accepted(boolean is_Data_Sent)
    {
    	is_Data_Sent = true;
    	
        if (FunRouletteMessages.is_Bet_Taken == false)
            objFunRouletteActivity.Update_Hint_Msg("Bet Time is Over",Color.WHITE);
	
        else
        {
        	 if(is_Data_Sent)
        		 objFunRouletteActivity.Update_Hint_Msg("Your Bet has been accepted", Color.WHITE);
             else if (Result_Request_Worker_Thread.Get_Bet_Send_Result_Status() == Bet_Send_Result.REJECTED.GetBet_Send_Result())
             {
            	 objFunRouletteActivity.Update_Hint_Msg("Your bet has been accepted", Color.WHITE);
             }
             else
             {
            	 objFunRouletteActivity.Update_Hint_Msg("Server not conneted", Color.WHITE);
             }
        	
        }
    }
    
    // these messages have to be displayed when rolling starts based on is_Bet_Taken flag
    public void Update_Hint_Msg_On_Rolling()
    {
    	boolean is_Clear = false;
        String str_Hint_Msg;
        String str_Game_Type;
        
        // clear all message
        if (is_Clear)
        {
            str_Hint_Msg = "";
            str_Game_Type = "";
        }
        else if (!is_Bet_Taken)
        {
            str_Hint_Msg = "This is a Demo game as You did not click Bet OK button";
            str_Game_Type = "Demo Game";
        }
        else
        {
            str_Hint_Msg = "Now Game Starts";
            str_Game_Type = "Real Game";
        }

        // update Hind message
        objFunRouletteActivity.Update_Hint_Msg(str_Hint_Msg,Color.WHITE );
        // update Game type message
        //label_Game_Type_Real_Demo.Content = str_Game_Type;
        
    }
    
    
    public boolean check_won_or_not(int stop_Ball_At_Result)
    {
    	int bets[]=objFunRouletteActivity.get_Bet_Values_Array();
    	
    	if(bets[stop_Ball_At_Result]>0)
    		      return true;   	
    	else
    		      return false;
    }
    
    public void Set_Amount_Won(int amount)
    {
        player_balance_for_history = GlobalClass.getBalance();

        player_balance_for_history += amount;
        amount_Won = amount;
        //string path;
        if (amount_Won != 0 /*&& Active_Window.Current_Active_Page == Active_Page.Fun_Roulette*/)
        {
           objFunRouletteActivity.play_music(R.raw.win);
           objFunRouletteActivity.Update_Amount_Won();
        }
        else
        {
            winning_amount_in_last_game = amount_Won;               
            objFunRouletteActivity.Update_Amount_Won();
        }                                
    }
    
    

    
    
    
    
	 
    public void Update_Recent_List(int stop_Ball_At_Result) {
        boolean is_Win = false;
        boolean is_Demo_Game = true;        // don't conside as a new game if it's demo game
        if (stop_Ball_At_Result == 0)
        {
            stop_Ball_At_Result = 38;
        }
       amount_Won = 0;
       objFunRouletteActivity.stop_Ball_At = stop_Ball_At_Result;
       objFunRouletteActivity.Update_Recent_List(objFunRouletteActivity.stop_Ball_At);        // update recent list
       objFunRouletteActivity.Start_Hilighting_Win_Number(objFunRouletteActivity.stop_Ball_At);
      
        
        
        
     
     
     int ss=1;
     int ssj=ss;
     
     if (FunRouletteActivity.arl.size() > 0)
     {
    	 boolean check_won_or_not_status=check_won_or_not(objFunRouletteActivity.stop_Ball_At);
    	 if (check_won_or_not_status == true && FunRouletteMessages.is_Bet_Taken)
         {
             is_Win = true;
             amount_Won = bet_Values[objFunRouletteActivity.stop_Ball_At];
             Set_Amount_Won(amount_Won);
             objFunRouletteActivity.Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_TAKE);
         }
    	 else
    	 {
    		 objFunRouletteActivity.Bet_Time_Over(false);  // if lost , then directly allow to choose individual buttons / previous bet button

             if ((FunRouletteMessages.is_Bet_Taken) ==false )
             {
                 is_Demo_Game = false;
                 if (is_Demo_Game == false && objFunRouletteActivity.is_selected_bet_completed == true)
                 {
                   objFunRouletteActivity.Update_Total_Bet_Taken_4_Current_Game(true);
                     objFunRouletteActivity.Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_OK);
                     
                 }
                 else if (objFunRouletteActivity.is_selected_bet_completed == false)
                 {
                     is_Demo_Game = true;
                     if (is_Win){
                         objFunRouletteActivity.Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_TAKE);
                     }
                 }
             }
             else if ((FunRouletteMessages.is_Bet_Taken) ==true ) //incase of lost
             {
               objFunRouletteActivity.Start_Bet_Ok_Take_Buttons_Group(Button_Group.BET_PREVIOUS_OK);
             }
    	 }    	 
    	 if (is_Bet_Taken)
         {
            objFunRouletteActivity.Update_Hint_Result_Message(is_Win);  // update result for user whether he has won or not
             objFunRouletteActivity.Update_Total_Bet_Taken_4_Current_Game(true); // clear label with Total bet amount taken
         }
     }
     else
     {
    	   objFunRouletteActivity.Bet_Time_Over(false);
     }
     
     


        if (is_Demo_Game == true && amount_Won == 0)
        {
        	//Clear_Last_Game_Betting_Labels_Only(); // clear All labels
        }
        if (is_Bet_Taken)
            result_Request_Thred.Start(Request_Type.UPDATE_USER_ACCOUNT);
        if(amount_Won==0)
        {
           // Clear_Last_Game();
        }
        objFunRouletteActivity.is_New_Game = is_Demo_Game;
    }

    
    
    
    
    
		public FunRouletteMessages() {
			fun_roulette_messsages = this;
		    objFunRouletteActivity=new FunRouletteActivity();
		    result_Request_Thred=new Result_Request_Worker_Thread(fun_roulette_messsages);
		    
		
		    
		  /* start to be deleted  */
		    bet_Values = new int[38];

            for (int i = 0; i < 38; i++)
            {
                bet_Values[i] = 0;
            }  
		  /*  end to be deleted  */
		}
		

		public int Is_Red_Black(int key)
        {     
			int m=Arrays.binarySearch(red_Ids,key);
			if(m>0)
            //if( Array.IndexOf(red_Ids , key) != -1 ) 
                return 1;
            return 0;
        }	
		
	
		
		   int current_Game_Result_OR_Next_Game_Prepare = 0;
	       public void Start_Game_Timer(int time_Server, int current_Game_Result_OR_Next_Game_Prepare)
	        {
	            if (is_First_Time_Window_Loaded == false)
	            {                          
	               objFunRouletteActivity.Update_Balance_Label();
	                return;
	            }
	            is_First_Time_Window_Loaded = false;

	            // if server respose time is n secons, decrese those many seconds
	            time_2_Start = time_Server;

	            this.current_Game_Result_OR_Next_Game_Prepare = current_Game_Result_OR_Next_Game_Prepare;

	            /* TO BE DELETED STARTS HERE */
	            //current_Game_Result_OR_Next_Game_Prepare=2;
	            /* TO BE DELETED ENDS HERE */
	            
	            //Request_Type objRequest_Type;
	            if (current_Game_Result_OR_Next_Game_Prepare == 2) // it;s true if (server_time - lapsed_time) ~ 49 
	            {
	                objFunRouletteActivity.Bet_Time_Over(false);
	                result_Request_Thred.Start(Request_Type.SET_UP_NEXT_GAME);
	                
	                
	              // result_Request_Thred.Logged_In_At_Game_End(true);
	              //  result_Request_Thred.Start(Request_Type.GET_RESULT);
	                
	            }
	            else if (current_Game_Result_OR_Next_Game_Prepare == 1)
	            {
	            	objFunRouletteActivity.Bet_Time_Over(false);
	                result_Request_Thred.Logged_In_At_Game_End(true);
	                result_Request_Thred.Start(Request_Type.GET_RESULT);
	            }
	            else if (time_2_Start <= 10)
	            {
	            	objFunRouletteActivity.Bet_Time_Over(true);
	            }
	            else if (time_2_Start < 58 && time_2_Start >= 50) // need to think more on these condition
	            {
	            	objFunRouletteActivity.Bet_Time_Over(true);
	                result_Request_Thred.Logged_In_At_Game_End(true);
	                result_Request_Thred.Start(Request_Type.GET_RESULT);
	                result_Request_Thred.Get_Server_Time(time_2_Start);
	            }

	            
	         
	            
	        }
		
	       
	        public int[] Get_Bet_Values()
	        {
	        	int bets[]=objFunRouletteActivity.get_Bet_Values_Array();
	            bets = bet_Values;
	            return bets;
	        }

	        
/* to be deleted starts here*/   	        
	/*        
	        public class StringInt_keys_Bet_Taken1{
	        	         private String keybet_name;
	        	         private int keybet_value;

	        	public StringInt_keys_Bet_Taken1(String o1, int o2){		keybet_name = o1;	keybet_value = o2;       	}

	        	public String getString(){ 	return keybet_name;   	}
	        	public int getInt(){   	return keybet_value;     	}
	        	public void setString(String s){   	this.keybet_name = s;  	}
	        	public void setInt(int i){    	this.keybet_value = i;     	}
	        	} 
		      

	    	public static ArrayList<StringInt_keys_Bet_Taken1> arl_temp=new ArrayList<StringInt_keys_Bet_Taken1>();
	        
	        String[] keybet_names1={"txt_00","txt_00_0","txt_0","txt_00_3","txt_3","txt_3_6","txt_6","txt_6_9","txt_9","txt_9_12","txt_12","txt_12_15","txt_15","txt_15_18","txt_18","txt_18_21","txt_21","txt_21_24","txt_24","txt_24_27","txt_27","txt_27_30","txt_30","txt_30_33","txt_33","txt_33_36","txt_36","txt_first2to1","txt_00_3_2","txt_3_2","txt_3_6_2_5","txt_6_5","txt_6_9_5_8","txt_9_8","txt_9_12_8_11","txt_12_11","txt_12_15_11_14","txt_15_14","txt_15_18_14_17","txt_18_17","txt_18_21_17_20","txt_21_20","txt_21_24_20_23","txt_24_23","txt_24_27_23_26","txt_27_26","txt_27_30_26_29","txt_30_29","txt_30_33_29_32","txt_33_32","txt_33_36_32_35","txt_36_35","txt_00_0_2","txt_2","txt_2_5","txt_5","txt_5_8","txt_8","txt_8_11","txt_11","txt_11_14","txt_14","txt_14_17","txt_17","txt_17_20","txt_20","txt_20_23","txt_23","txt_23_26","txt_26","txt_26_29","txt_29","txt_29_32","txt_32","txt_32_35","txt_35","txt_second2to1","txt_0_2_1","txt_2_1","txt_2_5_1_4","txt_5_4","txt_5_8_4_7","txt_8_7","txt_8_11_7_10","txt_11_10","txt_11_14_10_13","txt_14_13","txt_14_17_13_16","txt_17_16","txt_17_20_16_19","txt_20_19","txt_20_23_19_22","txt_23_22","txt_23_26_22_25","txt_26_25","txt_26_29_25_28","txt_29_28","txt_29_32_28_31","txt_32_31","txt_32_35_31_34","txt_35_34","txt_0_1","txt_1","txt_1_4","txt_4","txt_4_7","txt_7","txt_7_10","txt_10","txt_10_13","txt_13","txt_13_16","txt_16","txt_16_19","txt_19","txt_19_22","txt_22","txt_22_25","txt_25","txt_25_28","txt_28","txt_28_31","txt_31","txt_31_34","txt_34","txt_third2to1","txt_00_0_3_2_1","txt_3_2_1","txt_3_2_1_6_5_4","txt_6_5_4","txt_6_5_4_9_8_7","txt_9_8_7","txt_9_8_7_12_11_10","txt_12_11_10","txt_12_11_10_15_14_13","txt_15_14_13","txt_15_14_13_18_17_16","txt_18_17_16","txt_18_17_16_21_20_19","txt_21_20_19","txt_21_20_19_24_23_22","txt_24_23_22","txt_24_23_22_27_26_25","txt_27_26_25","txt_27_26_25_30_29_28","txt_30_29_28","txt_30_29_28_33_32_31","txt_33_32_31","txt_33_32_31_36_35_34","txt_36_35_34","txt_1st12","txt_2nd12","txt_3rd12","txt_1to18","txt_Even","txt_Red","txt_Black","txt_Odd","txt_19to36"};
	        public void HardCorearl()
	        {    
	            for(int i=0;i<keybet_names1.length;i++)
	            {
	            	if(i==0)
	            		arl_temp.add(new StringInt_keys_Bet_Taken1(keybet_names1[i],2));
	            	else if(i==1)
	            		arl_temp.add(new StringInt_keys_Bet_Taken1(keybet_names1[i],1));
	            	else if(i==2)
	            		arl_temp.add(new StringInt_keys_Bet_Taken1(keybet_names1[i],2));
	            	else	
	               arl_temp.add(new StringInt_keys_Bet_Taken1(keybet_names1[i],0));
	            }
	        }
*/
/* to be deleted ends here   */	        
	        
	        public class Return_Last_Game_Details_2_Save_Values{
	        	
	        	String str_Bets_Number_Wise="" ;
	             int total_Amount = 0;
	             int win_Amount = 0;
	             int account_balance=0;
	             int player_balance_for_history = 0;
	        } 
	        
	        
         public Return_Last_Game_Details_2_Save_Values Last_Game_Details_2_Save()
	       {
        	   int account_balance;
        	   String  str_Bet_Details_Numebr_pad_Key_wise = "";
	           String str_Bet_Detail_4_Key = "";
	           int total_Bet_Amount_Put_on_Game = 0;
	           int  player_balance_4_history = player_balance_for_history;
              int win_Amount;
      //  HardCorearl();
	           
	            
	        /* to create a str_Bet_Details_Numebr_pad_Key_wise staring starts here */
	            ArrayList<StringInt_keys_Bet_Taken> ar_temp= FunRouletteActivity.arl; 
	            int ar_temp_length=ar_temp.size();
	            int total_mm_value=0;
	            String extractedResult_final = "";
	        	for(int i=0;i<ar_temp_length;i++)
	        	{	    		
	        		String mm=ar_temp.get(i).getString();
	                int mm_value=ar_temp.get(i).getInt();
	              if(mm_value>0)
	              {
	    	    	String[] parts = mm.split("_");
	    	    	String extractedResult = "";
	    	    	for(int j=1;j<parts.length;j++){	    	    		
	    	    		   if(parts[j].equals("00"))
	    	    			   extractedResult += "_"+"37" ;
	    	    		   else if(parts[j].equals("0"))
	    	    			   extractedResult +="_"+ "38";
	    	    	        else
	    	    			extractedResult += "_"+parts[j]; // prasad_hv is captured here.
	    	    	    }
	    	    	
	    	    	extractedResult+=","+mm_value;
	    	    	total_mm_value+=mm_value;
	    	    	
	    	    	extractedResult_final+="("+extractedResult+")";
	              }
	        	}
	        /* to create a str_Bet_Details_Numebr_pad_Key_wise staring ends here */
	            
	            str_Bet_Details_Numebr_pad_Key_wise=extractedResult_final;
	            total_Bet_Amount_Put_on_Game=total_mm_value;
	            win_Amount = amount_Won;
	            account_balance=GlobalClass.getBalance();
	           
	           
	           
	           Return_Last_Game_Details_2_Save_Values obj=new Return_Last_Game_Details_2_Save_Values();
	           obj.str_Bets_Number_Wise = str_Bet_Details_Numebr_pad_Key_wise;
	           obj.total_Amount = total_Bet_Amount_Put_on_Game;
	           obj.win_Amount = win_Amount;
	           obj.account_balance=account_balance;
	           obj.player_balance_for_history = player_balance_4_history;

	           return obj;
	        }


	 
}
