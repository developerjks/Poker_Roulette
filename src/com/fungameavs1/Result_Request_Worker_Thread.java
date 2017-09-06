package com.fungameavs1;

import java.util.Arrays;

import com.fungameavs1.FunRouletteActivity.CurrentTimer;
import com.fungameavs1.FunRouletteMessages.Return_Last_Game_Details_2_Save_Values;
import com.fungameavs1.Service_Client_Interaction.Add_Bet_Status_Get_Current_Server_GameID_FR;
import com.fungameavs1.Service_Client_Interaction.Game_Details_FunRoulette;
import com.fungameavs1.Service_Client_Interaction.Login_User_Details;
import com.fungameavs1.Service_Client_Interaction.Next_Game_ID_Time_FunRoulette;
import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;


public class Result_Request_Worker_Thread extends Application
{		
		@Override
		public void onCreate() {
			super.onCreate();
			//globalclass=(GlobalClass) getApplicationContext();
	        GlobalClass.setTotal_Bet_Amount(0);
	        
		}


		
		public enum Request_Type
		{
		    //You can initialize enums using enumname(value)
		     GET_RESULT(1),
		     SEND_BET_VALUES(2),
		     SET_UP_NEXT_GAME(3),
		     UPDATE_USER_ACCOUNT(4),
		     GAME_RESULT_DEMO_GAME(5);
		    
		    private int request_type_value;
		    //Constructor which will initialize the enum
		    Request_Type(int req)
		    {
		      request_type_value = req;
		    }
		}
		
		public enum Bet_Send_Result
		{
		    //You can initialize enums using enumname(value)
				SUCESS(100),
				REJECTED(101),
				SERVER_NOT_CONNTECTED(102);
		    
		    public int Bet_Send_Result_value;
		    //Constructor which will initialize the enum
		    Bet_Send_Result(int send_bet_result)
		    {
		    	Bet_Send_Result_value = send_bet_result;
		    }
		    
		    
		    public int GetBet_Send_Result()
		    {
		        return Bet_Send_Result_value;
		    }
		}
		
		public static int request_Result;
		Request_Type request_Type_To_Service=null;
	    FunRouletteMessages objFunRouletteMessages = null;
	    boolean Is_User_Signed_In_At_Game_End = false;
	    String str = null;
	    int[] bet_Values = null;
	    public static int  balance_after_win=0;
	    public static Service_Client_Interaction service_client;
	     backgroundWorker1 objbackgroundWorker1=null;
	    public static void Set_Service_Client(Service_Client_Interaction service_client_Interaction)
	    {
	        service_client = service_client_Interaction;
	    }
	    
	    
	    
	    public Result_Request_Worker_Thread(FunRouletteMessages fun_Roulette_Msges)
	    {
	        Reset_Reuest_Result();
	        this.objFunRouletteMessages = fun_Roulette_Msges;
	    }

	    void Reset_Reuest_Result()
	    {
	        request_Result = -10;
	    }
	    
	    public static int Get_Bet_Send_Result_Status()
        {
            return request_Result;
        }
	    	    
	    
	    public void Start( Request_Type request_Type)
	    {
	        if (async_backgroundWorker1_IsBusy_status == false)
	        {
	            request_Type_To_Service = request_Type;
	            objbackgroundWorker1=new backgroundWorker1();
	            objbackgroundWorker1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	          //  new backgroundWorker1().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        }
	    }
	    
	    public void Stop()
	    {
	        if (async_backgroundWorker1_IsBusy_status == true)
	        { 
	            objbackgroundWorker1.cancel(true);
	            async_backgroundWorker1_IsBusy_status=false;
	        }
	        
	    }
	    
	    
	    public void Add_Balance_On_Bet_Cancel_In_FR()
	    {
	    	double balance = GlobalClass.getBalance();
	        int total_invest_amount = FunRouletteMessages.total_Bet_Amount_On_Current_Game;
	        service_client.Add_Balance_During_Bet_Cancel_In_FunRoulett(balance,total_invest_amount);
	    }

	    public void Logged_In_At_Game_End(boolean Is_User_Loged_in_At_End)
	    {
	        Is_User_Signed_In_At_Game_End = Is_User_Loged_in_At_End;
	    }

	    
	    int seconds;
	    public void  Get_Server_Time(int sec)
	    {
	         seconds=sec;
	    }
	    
	    
	    public static boolean async_backgroundWorker1_IsBusy_status=false;
	    
	    public class backgroundWorker1 extends AsyncTask<Void, Void, Void>
	    {     	
	    	@Override
			protected void onPreExecute() {
				super.onPreExecute();
				async_backgroundWorker1_IsBusy_status=true;
			}
	    	
			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
						
				if ((request_Type_To_Service.name()).equals(Request_Type.GET_RESULT.name()))
	            {
	                try
	  		        {
	            		if(Is_User_Signed_In_At_Game_End)
	            		{
	            			Is_User_Signed_In_At_Game_End=false;
	            			            			
	            			objFunRouletteMessages.Update_Recent_List(request_Result);   // Update_Recent_List(int stop_Ball_At_Result) on number_pad
	            			//Toast.makeText(getApplicationContext(), "Update_Recent_List(inr stop_Ball_At_Result) will be fired here", Toast.LENGTH_SHORT).show();
	            			
	            			
	            		}
	            		
	            		FunRouletteMessages.ReSet_Number_2_Stop_Roulette(request_Result);
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	            }
				
				else if ((request_Type_To_Service.name()).equals(Request_Type.GAME_RESULT_DEMO_GAME.name()))
	            {
	                try
	  		        {
	            		FunRouletteMessages.ReSet_Number_2_Stop_Roulette(request_Result);
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	            }
				
				else if ((request_Type_To_Service.name()).equals(Request_Type.SEND_BET_VALUES.name()))
	            {
	                try
	  		        {
	            		    boolean is_Bet_Values_Sent;
			            	if(request_Result==(int) Bet_Send_Result.SUCESS.GetBet_Send_Result())
			            	{
			            		is_Bet_Values_Sent=true;
			            	}else{
			            		is_Bet_Values_Sent=false;
			            	}
			            
			            	objFunRouletteMessages.Update_Hint_Message_Time_Over_OR_Bet_Accepted(is_Bet_Values_Sent);	
		        			//Toast.makeText(getApplicationContext(), "Update_Hint_Message_Time_Over_OR_Bet_Accepted(is_Bet_Values_Sent) will be fired here", Toast.LENGTH_SHORT).show();	            		
	                    
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	                
	            }
				
				else if ((request_Type_To_Service.name()).equals(Request_Type.UPDATE_USER_ACCOUNT.name()))
	            {
	                try
	  		        {
	                   Stop();
	                   Reset_Reuest_Result();
	                   Start(Request_Type.SET_UP_NEXT_GAME);
	                   return;
	  	            } catch (Exception e)   {
	  		                    e.printStackTrace();
	  		               } 
	             	              
	            }
				
				
				
				Stop();
				Reset_Reuest_Result();
				async_backgroundWorker1_IsBusy_status=false;
			}
			
			@Override
			protected Void doInBackground(Void... params) {
	            // keep calling Serivce method till it returns the minimum number
	            
				String m=request_Type_To_Service.name();
				Request_Type n= Request_Type.GET_RESULT;
	            if ((request_Type_To_Service.name()).equals(Request_Type.GET_RESULT.name()))
	            {
	              try
	  		      {
	            		Get_Result();		            		
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }               
	            }
	            else if ((request_Type_To_Service.name()).equals(Request_Type.SEND_BET_VALUES.name()))
	            {
	            	  try
	      		      {  
	            		  Send_Bet_Values();	
	          	        /*
	          	        int current_game_id=Game_Details_FunRoulette.game_Id;
	        	        int next_game_id_on_server=Add_Bet_Status_Get_Current_Server_GameID_FR.Game_Id;
	        	        boolean status_bet_allowed_or_not=Add_Bet_Status_Get_Current_Server_GameID_FR.Is_Bet_In_Time_Status;
	        	        Get_Result();       
	        	        int stop_at_roulette=request_Result;
	        	      */
	      		      }
	      		      catch (Exception e)   {
	      		            e.printStackTrace();
	      		         }
	            }

	            else if ((request_Type_To_Service.name()).equals(Request_Type.SET_UP_NEXT_GAME.name()))
	            {
	            	try
	    		      {		
	            		Retrive_Game_ID();
	            		
	    	          }
	    		      catch (Exception e)   {
	    		            e.printStackTrace();
	    		         }
	            }
	            else if ((request_Type_To_Service.name()).equals(Request_Type.UPDATE_USER_ACCOUNT.name())) 
	            {
	            	try
	  		      {
	            		Update_User_Account();  
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }
	            }
	            else if ((request_Type_To_Service.name()).equals(Request_Type.GAME_RESULT_DEMO_GAME.name())) 
	            {
	            	try
	  		      {
	            		Get_Result_Demo_Game();  
	  	          }
	  		      catch (Exception e)   {
	  		            e.printStackTrace();
	  		         }
	            }

				return null;
			  }    		
	    }


		
		
		void Get_Result_Demo_Game()
	    {
	        Get_Result();
	        int current_game_id = Game_Details_FunRoulette.game_Id;
	        int next_game_id = Next_Game_ID_Time_FunRoulette.game_Id ;
	        while( (next_game_id > current_game_id)==false)
	        {
	            Retrive_Game_ID();
	            next_game_id = Next_Game_ID_Time_FunRoulette.game_Id;
	        }
	    }
		
	    private void Update_User_Account()
	    {
	    	     Service_Client_Interaction service_client=new Service_Client_Interaction();
	    	    	        
	             Return_Last_Game_Details_2_Save_Values obj= objFunRouletteMessages.Last_Game_Details_2_Save();
	       
	             String user_id=Login_User_Details.USER_ID;
	    	     String game_Id=String.valueOf(Game_Details_FunRoulette.game_Id);
	    	          
	        int Save_Player_Game_Playing_Info_result= service_client.Save_Player_Game_Playing_Info_json(String.valueOf(obj.total_Amount),obj.str_Bets_Number_Wise,String.valueOf(obj.win_Amount),String.valueOf(obj.player_balance_for_history),game_Id,user_id);

	       if (obj.win_Amount != 0)
	           obj.account_balance = obj.win_Amount - obj.total_Amount;
	        else
	           obj.account_balance = obj.win_Amount - obj.total_Amount;
	       
	      int RES= service_client.Update_User_Account_Balance_json(String.valueOf(obj.account_balance),user_id);
	             
	       balance_after_win = service_client.GetPlayerBalance_json(user_id);
	       
	    }
	    
	    
	    void Retrive_Game_ID()
	    {
	        int time_Seconds = 0,new_game_id;
	        Service_Client_Interaction client=new Service_Client_Interaction();
	        client.Get_Next_Game_ID_And_Time(); // store with service_Client object only
	        
	        time_Seconds=Next_Game_ID_Time_FunRoulette.Time_Seconds;
	        new_game_id=Next_Game_ID_Time_FunRoulette.game_Id;
	        
	        CurrentTimer.counter=time_Seconds;
	        Game_Details_FunRoulette.game_Id=new_game_id;
	        Game_Details_FunRoulette.Time_Seconds=time_Seconds;
	        //objFunRouletteMessages.time_2_Start = time_Seconds; // update the client with the latest details
	    }
	    
	    
	    void Send_Bet_Values()
	    {
	    	Service_Client_Interaction service_client=new Service_Client_Interaction();
	        // first get bet details
	       bet_Values= objFunRouletteMessages.Get_Bet_Values();

	     // bet_Values[0]=36;bet_Values[1]=36;bet_Values[2]=72;bet_Values[3]=52;bet_Values[4]=48;    //to check with test bet_Values hard coded values
	       
	        int total_bet=FunRouletteMessages.total_Bet_Amount_On_Current_Game;
	        FunRouletteMessages.total_Bet_Amount_On_Current_Game = 0;
	        
	        // Send bet values to the server & notify accordingly
	        int times = 3;

	        request_Result = Bet_Send_Result.SERVER_NOT_CONNTECTED.Bet_Send_Result_value;

	        while ((request_Result != (int)Bet_Send_Result.SUCESS.Bet_Send_Result_value) && (times != 0) /*&& (fun_Roulette_Messages.Time_2_Start >= 4)*/)
	        {
	            try
	            {
	               	String strSeparator = "-";
	            	String strNumbers = Arrays.toString(bet_Values);
	            	strNumbers = strNumbers.replaceAll(", ", strSeparator).replace("[", "").replace("]", "");
	            	
	              	if (service_client.Send_Bet_Values_json(strNumbers,total_bet))
	                    request_Result = (int)Bet_Send_Result.SUCESS.Bet_Send_Result_value;  // success
	                else
	                    request_Result = (int)Bet_Send_Result.REJECTED.Bet_Send_Result_value;  // failure
	            	}
	            catch (Exception exception)
	            {
	                request_Result = (int)Bet_Send_Result.SERVER_NOT_CONNTECTED.Bet_Send_Result_value;  // failure                
	            }
	            times--;
	        }

	        
	        
	        
	    }
	    
	    
	    
	    void Get_Result()
	    {
	        int next_Win_Number = Get_Number();
	        // if minimum value not calcualted yet, then send another request after some time
	        while (next_Win_Number < 0)
	        {
	           // Thread.Sleep(1000);
	            next_Win_Number = Get_Number();
	        }
	        request_Result = next_Win_Number;
	       
	       // Calling_result_function_log(next_Win_Number.ToString(),seconds.ToString());
	    }

	    public int Get_Number()
	    {
	    	Service_Client_Interaction client=new Service_Client_Interaction();
	    	int game_id=Game_Details_FunRoulette.game_Id;
	    	
	        int res=client.Get_FunRoulette_Game_Result(game_id);
	        return res;
	    }
	    
	    
	}
