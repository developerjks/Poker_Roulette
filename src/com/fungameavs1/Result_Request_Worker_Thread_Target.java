package com.fungameavs1;

import java.util.Arrays;

import com.fungameavs1.FunRouletteMessages.Return_Last_Game_Details_2_Save_Values;
import com.fungameavs1.FunTargetMessages.Return_Last_Game_Details_2_Save_Values_Target;
import com.fungameavs1.Result_Request_Worker_Thread.Bet_Send_Result;
import com.fungameavs1.Result_Request_Worker_Thread.Request_Type;
import com.fungameavs1.Result_Request_Worker_Thread.backgroundWorker1;
import com.fungameavs1.Service_Client_Interaction.Game_Details_FunRoulette;
import com.fungameavs1.Service_Client_Interaction.Login_User_Details;
import com.fungameavs1.Service_Client_Interaction_Target.Game_Details_Funtarget;
import com.fungameavs1.Service_Client_Interaction_Target.Next_Game_ID_Time_FunTarget;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;


public class Result_Request_Worker_Thread_Target extends Application
{
		
		@Override
		public void onCreate() {
			super.onCreate();
			//globalclass=(GlobalClass) getApplicationContext();
	        GlobalClass.setTotal_Bet_Amount_Target(0);
	        
		}
	    
	    public static int  balance_after_win=0;
	    FunTargetMessages objFunTargetMessages = null;
	    int[] bet_Values_Target = null;
	    public static int request_Result_Target;
	    public static int  balance_after_win_target=0;
	    Request_Type request_Type_To_Service_Target=null;
	    boolean Is_User_Signed_In_At_Game_End_Target = false;
	    public static Service_Client_Interaction_Target service_client_target;
	    public static Service_Client_Interaction service_client_roulette;
	    backgroundWorker2 objbackgroundWorker2=null;
	     
	    public static void Set_Service_Client_Target(Service_Client_Interaction_Target service_client_Interaction_Target,Service_Client_Interaction service_client_Interaction)
	    {
	        service_client_target = service_client_Interaction_Target;
	        service_client_roulette = service_client_Interaction;
	    }
	        
	    public Result_Request_Worker_Thread_Target(FunTargetMessages fun_Target_Msges)
	    {
	        Reset_Reuest_Result_Target();
	        this.objFunTargetMessages = fun_Target_Msges;
	    }

	    void Reset_Reuest_Result_Target()
	    {
	        request_Result_Target = -10;
	    }
	    
	    
	    public static int Get_Bet_Send_Result_Status_Target()
        {
            return request_Result_Target;
        }
	    
	    
	    public void Start_Target( Request_Type request_Type_Target)
	    {
	        if (async_backgroundWorker2_IsBusy_status_target == false)
	        {
	            request_Type_To_Service_Target = request_Type_Target;
	            objbackgroundWorker2=new backgroundWorker2();
	            objbackgroundWorker2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	           // new backgroundWorker2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);     
	        }
	    }
	    
	    
	    public void Stop_Target()
	    {
	        if (async_backgroundWorker2_IsBusy_status_target == true)
	        {
	            objbackgroundWorker2.cancel(true);
	            async_backgroundWorker2_IsBusy_status_target=false;
	        }
	    }
	    
	    public void Add_Balance_On_Bet_Cancel_In_FT()
	    {
	    	double balance_target = GlobalClass.getBalance();
	        int total_invest_amount_target = FunTargetMessages.total_Bet_Amount_On_Current_Game_Target;
	        service_client_target.Add_Balance_During_Bet_Cancel_In_FunTarget(balance_target,total_invest_amount_target);
	    }

	    public void Logged_In_At_Game_End_Target(boolean Is_User_Loged_in_At_End_Target)
	    {
	        Is_User_Signed_In_At_Game_End_Target = Is_User_Loged_in_At_End_Target;
	    }

	    
	    int seconds_target;
	    public void  Get_Server_Time_Target(int sec)
	    {
	         seconds_target=sec;
	    }
	    
	    
	    public static boolean async_backgroundWorker2_IsBusy_status_target=false;
	    
	    public class backgroundWorker2 extends AsyncTask<Void, Void, Void>
	    {     	
	    	@Override
			protected void onPreExecute() {
				super.onPreExecute();
				async_backgroundWorker2_IsBusy_status_target=true;
			}
	    	
		
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				
				
				if ((request_Type_To_Service_Target.name()).equals(Request_Type.GET_RESULT.name()))
	            {
	                try
	  		        {
	            		if(Is_User_Signed_In_At_Game_End_Target)
	            		{
	            			Is_User_Signed_In_At_Game_End_Target=false;
	            			return;
	            		}
	            		
	            		FunTargetMessages.ReSet_Number_2_Stop_Target();
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	            }
				
				else if ((request_Type_To_Service_Target.name()).equals(Request_Type.GAME_RESULT_DEMO_GAME.name()))
	            {
	                try
	  		        {
	            		FunTargetMessages.ReSet_Number_2_Stop_Target();
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	            }
				
				else if ((request_Type_To_Service_Target.name()).equals(Request_Type.SEND_BET_VALUES.name()))
	            {
	                try
	  		        {
	            		    boolean is_Bet_Values_Sent;
			            	if(request_Result_Target==(int) Bet_Send_Result.SUCESS.GetBet_Send_Result())
			            	{
			            		is_Bet_Values_Sent=true;
			            	}else{
			            		is_Bet_Values_Sent=false;
			            	}
			            		
			            	
			               	objFunTargetMessages.Update_Hint_Message_Time_Over_OR_Bet_Accepted_Target(is_Bet_Values_Sent);
		        			//Toast.makeText(getApplicationContext(), "Update_Hint_Message_Time_Over_OR_Bet_Accepted(is_Bet_Values_Sent) will be fired here", Toast.LENGTH_SHORT).show();	            		
	                    
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	            }
				
				
				else if ((request_Type_To_Service_Target.name()).equals(Request_Type.UPDATE_USER_ACCOUNT.name()))
	            {
	                try
	  		        {
	                   Stop_Target();
	                   Reset_Reuest_Result_Target();
	                   Start_Target(Request_Type.SET_UP_NEXT_GAME);
	                   return;
        			} catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	            }
				
				
				Stop_Target();
				Reset_Reuest_Result_Target();
				async_backgroundWorker2_IsBusy_status_target=false;
			}
			
			
			@Override
			protected Void doInBackground(Void... params) {
	            // keep calling Serivce method till it returns the minimum number
	            
				String m=request_Type_To_Service_Target.name();
				Request_Type n= Request_Type.GET_RESULT;
	            if ((request_Type_To_Service_Target.name()).equals(Request_Type.GET_RESULT.name()))
	            {
	              try
	  		      {
	            		Get_Result_Target();		    	  
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }               
	            }
	            else if ((request_Type_To_Service_Target.name()).equals(Request_Type.SEND_BET_VALUES.name()))
	            {
	            	  try
	      		      {
	            		  Send_Bet_Values_Target();		    	  
	      	          }
	      		      catch (Exception e)   {
	      		            e.printStackTrace();
	      		         }
	            }

	            else if ((request_Type_To_Service_Target.name()).equals(Request_Type.SET_UP_NEXT_GAME.name()))
	            {
	            	try
	    		      {
	            		
	            		Retrive_Game_ID_Target();  
	    	          }
	    		      catch (Exception e)   {
	    		            e.printStackTrace();
	    		         }
	            }
	            else if ((request_Type_To_Service_Target.name()).equals(Request_Type.UPDATE_USER_ACCOUNT.name())) 
	            {
	            	try
	  		      {
	            		Update_User_Account();  
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }
	            }
	            else if ((request_Type_To_Service_Target.name()).equals(Request_Type.GAME_RESULT_DEMO_GAME.name())) 
	            {
	            	try
	  		      {
	            		Get_Result_Demo_Game_Target();  
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }
	            }

				return null;
			  }    		
			
	   }


		public static class Last_Game_Details_2_Save_Values_Target
		{
			static String str_Bets_Number_Wise_Target = "test";    
	        static int total_Amount_target = 0;
	        static int win_Amount_Target = 0;
	        static int account_balance_target=0;
	        static int player_balance_for_history_Target = 0;
		}

	    
		
		void Get_Result_Demo_Game_Target()
	    {
	        Get_Result_Target();
	        int current_game_id_target = Game_Details_Funtarget.game_Id_Target;
	        int next_game_id_target = Next_Game_ID_Time_FunTarget.game_Id_Target ;
	        while( (next_game_id_target > current_game_id_target)==false)
	        {
	            Retrive_Game_ID_Target();
	            next_game_id_target = Next_Game_ID_Time_FunTarget.game_Id_Target;
	        }
	    }
		
		
	    private void Update_User_Account()
	    {
	    	             
	             Return_Last_Game_Details_2_Save_Values_Target obj= objFunTargetMessages.Last_Game_Details_2_Save_Target();
	       
	             String user_id=Login_User_Details.USER_ID;
	    	     String game_Id=String.valueOf(Game_Details_FunRoulette.game_Id);
	    	          
	    	     if(FunTargetMessages.is_current_game_new==true)
	    	     {
	        int Save_Player_Game_Playing_Info_result= service_client_target.Save_Player_Game_Playing_Info_Target_json(String.valueOf(obj.total_Amount),obj.str_Bets_Number_Wise,String.valueOf(obj.win_Amount),String.valueOf(obj.player_balance_for_history),game_Id,user_id);

	       if (obj.win_Amount != 0)
	           obj.account_balance = obj.win_Amount - obj.total_Amount;
	        else
	           obj.account_balance = obj.win_Amount - obj.total_Amount;
	       
	      int RES= service_client_roulette.Update_User_Account_Balance_json(String.valueOf(obj.account_balance),user_id);
	      FunTargetMessages.is_current_game_new = false;
	       balance_after_win = service_client_roulette.GetPlayerBalance_json(user_id);
	    	     }
	    }


		
		
		

	 
	    
	    
	    
	    
	    void Send_Bet_Values_Target()
	    {
	    	Service_Client_Interaction_Target service_client=new Service_Client_Interaction_Target();
	        // first get bet details
	       bet_Values_Target= objFunTargetMessages.Get_Bet_Values_Target();

	      bet_Values_Target[0]=36;bet_Values_Target[1]=36;bet_Values_Target[2]=72;bet_Values_Target[3]=52;bet_Values_Target[4]=48;    //to check with test bet_Values hard coded values
	       
	        int total_bet=FunTargetMessages.total_Bet_Amount_On_Current_Game_Target;
	        //FunTargetMessages.total_Bet_Amount_On_Current_Game_Target = 0;
	        
	        // Send bet values to the server & notify accordingly
	        int times = 3;

	        request_Result_Target = Bet_Send_Result.SERVER_NOT_CONNTECTED.Bet_Send_Result_value;

	        while ((request_Result_Target != (int)Bet_Send_Result.SUCESS.Bet_Send_Result_value) && (times != 0) /*&& (fun_Roulette_Messages.Time_2_Start >= 4)*/)
	        {
	            try
	            {
	               	String strSeparator = "-";
	            	String strNumbers = Arrays.toString(bet_Values_Target);
	            	strNumbers = strNumbers.replaceAll(", ", strSeparator).replace("[", "").replace("]", "");
	            	
	              	if (service_client.Send_Bet_Values_Target_json(strNumbers,total_bet))
	                    request_Result_Target = (int)Bet_Send_Result.SUCESS.Bet_Send_Result_value;  // success
	                else
	                    request_Result_Target = (int)Bet_Send_Result.REJECTED.Bet_Send_Result_value;  // failure
	            	}
	            catch (Exception exception)
	            {
	                request_Result_Target = (int)Bet_Send_Result.SERVER_NOT_CONNTECTED.Bet_Send_Result_value;  // failure                
	            }
	            times--;
	        }

	        
	        
	        
	    }
	    
	
	    
	    
	    
	    

	    
	    
	    void Retrive_Game_ID_Target()
	    {
	        int time_Seconds_target = 0;
	        Service_Client_Interaction_Target client=new Service_Client_Interaction_Target();
	        client.Get_Next_Game_ID_And_Time_Target(); // store with service_Client object only
	        
	        time_Seconds_target=Next_Game_ID_Time_FunTarget.Time_Seconds_Target;
	        
	        objFunTargetMessages.time_2_Start_Target = time_Seconds_target; // update the client with the latest details
	    }
	    
	    
	    void Get_Result_Target()
	    {
	        int next_Win_Number_Target = Get_Number_Target();
	        // if minimum value not calcualted yet, then send another request after some time
	        while (next_Win_Number_Target < 0)
	        {
	           // Thread.Sleep(1000);
	            next_Win_Number_Target = Get_Number_Target();
	        }
	        request_Result_Target = next_Win_Number_Target;
	       
	       // Calling_result_function_log(next_Win_Number.ToString(),seconds.ToString());
	    }

	    public int Get_Number_Target()
	    {
	    	Service_Client_Interaction_Target client=new Service_Client_Interaction_Target();
	    	int game_id_target=Game_Details_Funtarget.game_Id_Target;
	    	int joker_img_index=Game_Details_Funtarget.joker_img_index;
	        int res=client.Get_FunTarget_Game_Result(game_id_target, joker_img_index);
	        return res;
	    }
	    
	    
	}
