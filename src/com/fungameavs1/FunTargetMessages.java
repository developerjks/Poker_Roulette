package com.fungameavs1;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;

import com.fungameavs1.FunRouletteActivity.ABC.StringInt_keys_Bet_Taken;
import com.fungameavs1.FunRouletteMessages.Return_Last_Game_Details_2_Save_Values;
import com.fungameavs1.FunTargetActivity.A.StringInt_keys_Bet_Taken_Target;
import com.fungameavs1.Result_Request_Worker_Thread.Bet_Send_Result;
import com.fungameavs1.Result_Request_Worker_Thread.Request_Type;



	public class FunTargetMessages {
		
	    int[] bet_Values_Target;
	    boolean is_First_Time_Window_Loaded_Target = true;
		public static boolean check_for_player_session_target = false;
		public static boolean is_Bet_Taken_Target = false;
	    public  int time_2_Start_Target = 60;
	    public static int total_Bet_Amount_On_Current_Game_Target = 0;
	    public static boolean is_bet_cancel_Target = false;
	    //public static Result_Request_Worker_Thread_Target result_Request_Thred_Target = null;
	    public static int amount_Won_Target = 0;
	    public int player_balance_for_history_Target = 0; //player balance for update history       
	    public static boolean is_current_game_new = false;
	    public static FunTargetMessages fun_target_messsages=null;
		FunTargetActivity objFunTargetActivity=null;
		 
		
	    static int stop_At_target = -1;
	    public static void ReSet_Number_2_Stop_Target()
	    {
	        stop_At_target = -1;              
	    }
	    public static int Stop_At_Target()
	    {
	        return stop_At_target;
	    }

		
	 // Bet_Ok or after 10 - 0 seconds messages 
	    public void Update_Hint_Message_Time_Over_OR_Bet_Accepted_Target(boolean are_Bet_Values_Sent)
	    {
	    	are_Bet_Values_Sent = false;
	    	
	        if (FunTargetMessages.is_Bet_Taken_Target == false)
	            objFunTargetActivity.Update_Hint_Msg_Target("Bet Time is Over",Color.RED);
		
	        else
	        {
	        	 if( are_Bet_Values_Sent || FunTargetMessages.is_Bet_Taken_Target == true   )
	        		 objFunTargetActivity.Update_Hint_Msg_Target("Your Bet has been accepted", Color.RED);
	             else if (Result_Request_Worker_Thread_Target.Get_Bet_Send_Result_Status_Target() == Bet_Send_Result.REJECTED.GetBet_Send_Result())
	             {
	            	 objFunTargetActivity.Update_Hint_Msg_Target("Your bet has been accepted", Color.RED);
	             }
	             else
	             {
	            	 objFunTargetActivity.Update_Hint_Msg_Target("Server not conneted", Color.RED);
	             }
	        	
	        }
	    }
	     
	    
	    
		 
			public FunTargetMessages() {
				fun_target_messsages = this;
			    objFunTargetActivity=new FunTargetActivity();
			   // result_Request_Thred=new Result_Request_Worker_Thread(fun_roulette_messsages);
			    
			
			    
			  /* start to be deleted  */
			    bet_Values_Target = new int[10];

	            for (int i = 0; i < 10; i++)
	            {
	                bet_Values_Target[i] = 0;
	            }  
			  /*  end to be deleted  */
			}
			

		/*	public int Is_Red_Black(int key)
	        {     
				int m=Arrays.binarySearch(red_Ids,key);
				if(m>0)
	            //if( Array.IndexOf(red_Ids , key) != -1 ) 
	                return 1;
	            return 0;
	        }	
		*/	
		
			
			 int current_Game_Result_OR_Next_Game_Prepare_Target = 0;
		       public void Start_Game_Timer_Target(int time_Server_Target, int current_Game_Result_OR_Next_Game_Prepare_Target)
		        {
		            if (is_First_Time_Window_Loaded_Target == false)
		            {                          
		               objFunTargetActivity.Update_Balance_Label_Target();
		                return;
		            }
		            is_First_Time_Window_Loaded_Target = false;
		            //Reset_All_Items();
		            //number_Wheel.Reset_all_Data_in_number_wheel();
		            // if server respose time is n secons, decrese those many seconds
		            time_2_Start_Target = time_Server_Target;

		            this.current_Game_Result_OR_Next_Game_Prepare_Target = current_Game_Result_OR_Next_Game_Prepare_Target;

		            /* TO BE DELETED STARTS HERE */
		            //current_Game_Result_OR_Next_Game_Prepare=2;
		            /* TO BE DELETED ENDS HERE */
		            
		           // timer_Seconds.Start();  
		        }
			
		       
		       
		       public int[] Get_Bet_Values_Target()
		        {
		        	int bets[]=objFunTargetActivity.get_Bet_Values_Array_Target();
		            bets = bet_Values_Target;
		            return bets;
		        }
		       

		       
		       
		        public class Return_Last_Game_Details_2_Save_Values_Target{
		        	
		        	String str_Bets_Number_Wise="" ;
		             int total_Amount = 0;
		             int win_Amount = 0;
		             int account_balance=0;
		             int player_balance_for_history = 0;
		        } 
		        
		        
	         public Return_Last_Game_Details_2_Save_Values_Target Last_Game_Details_2_Save_Target()
		       {
	        	   int account_balance;
	        	   String  str_Bet_Details_Numebr_pad_Key_wise = "";
		           String str_Bet_Detail_4_Key = "";
		           int total_Bet_Amount_Put_on_Game = 0;
		           int  player_balance_4_history = player_balance_for_history_Target;
	              int win_Amount;
	      //  HardCorearl();
		           
		            
		        /* to create a str_Bet_Details_Numebr_pad_Key_wise staring starts here */
		            ArrayList<StringInt_keys_Bet_Taken_Target> ar_temp= FunTargetActivity.arl_target; 
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
		            win_Amount = amount_Won_Target;
		            account_balance=GlobalClass.getBalance();
		           
		           
		           
		           Return_Last_Game_Details_2_Save_Values_Target obj=new Return_Last_Game_Details_2_Save_Values_Target();
		           obj.str_Bets_Number_Wise = str_Bet_Details_Numebr_pad_Key_wise;
		           obj.total_Amount = total_Bet_Amount_Put_on_Game;
		           obj.win_Amount = win_Amount;
		           obj.account_balance=account_balance;
		           obj.player_balance_for_history = player_balance_4_history;

		           return obj;
		        }

		       
		        
		        


		 
	}
