package com.fungameavs1;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fungameavs1.Service_Client_Interaction.Add_Bet_Status_Get_Current_Server_GameID_FR;
import com.fungameavs1.Service_Client_Interaction.Game_Details_FunRoulette;
import com.fungameavs1.Service_Client_Interaction.Next_Game_ID_Time_FunRoulette;


public class Service_Client_Interaction_Target {
	public static String player_user_id_logged_in_last_game_target;
	public static int Total_Bet_Amount_target=0; 
	
	//String IPAddressURL="http://192.168.1.100/AndroidService_Target/Service.svc";
	String IPAddressURL="http://playgameindia.net/test_AndroidService_Target/Service.svc";
		
	
	
	
	 /* Add_Balance_During_Bet_Cancel_In_FunRoulett starts here  */
	 public void Add_Balance_During_Bet_Cancel_In_FunTarget(double balance,int total_invest_amount)
    { 
                
    }
	/* Add_Balance_During_Bet_Cancel_In_FunRoulett ends here  */

	 
	
	 
		public void Get_FunTarget_Game_Details()
		{
			//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Get_Game_Details");
			String str=IPAddressURL+ "/json/Get_Game_Details_Target";
			JSONObject jsonObject= func2(str);
			JSONArray jsonArray_game_details_target=null;
			JSONArray jsonArray_game_details_recent_list_target=null;
			Game_Details_Funtarget.Recent_Values_10_Target=null;
			Game_Details_Funtarget.game_Id_Target=-1;
			  //int Time_Seconds=-1,current_Game_Result_OR_Next_Game_Prepare=-1;
			  //int Recent_Values[];
	       try
	       {
	    	   Game_Details_Funtarget.Recent_Values_10_Target=new int[10];
	    	   jsonArray_game_details_target= jsonObject.getJSONArray("Get_Game_Details_TargetResult");
	    	   
	    	   for(int i=0;i<jsonArray_game_details_target.length();i++){
		    		  JSONObject Game_Details_JSON_Target = jsonArray_game_details_target.getJSONObject(i);
		    		  
		    		  Game_Details_Funtarget.Time_Seconds_Target= Game_Details_JSON_Target.getInt("Time_Seconds_Target");
		    		  Game_Details_Funtarget.current_Game_Result_OR_Next_Game_Prepare_Target = Game_Details_JSON_Target.getInt("current_Game_Result_OR_Next_Game_Prepare_Target");
		    		  Game_Details_Funtarget.game_Id_Target= Game_Details_JSON_Target.getInt("game_Id_Target");
		    		  jsonArray_game_details_recent_list_target= Game_Details_JSON_Target.getJSONArray("Recent_Values_10_Target");
		    		  
		    		  for(int j=0;j<jsonArray_game_details_recent_list_target.length();j++)	  {
		    			 Game_Details_Funtarget.Recent_Values_10_Target[j]=jsonArray_game_details_recent_list_target.getInt(j);    	
		              }
	    	    }
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      
		}
		
	
		
		
		int fun_target_result = 0;        
		public int Get_FunTarget_Game_Result(int current_game_id,int joker_img_index)
		{
			JSONObject jsonObject= func2(IPAddressURL+"/json/Get_Minimum_Number_Target/"+current_game_id+"/"+joker_img_index+"");
	       try
	       {
	    	   fun_target_result=jsonObject.getInt("Get_Minimum_Number_TargetResult");
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      return fun_target_result;
		}
		
		
		
		boolean Is_Bet_In_Time_Status_Target;
		public boolean Send_Bet_Values_Target_json(String strNumbers_target,int total_bet_target)
		{
			String game_id_target= Integer.toString(Game_Details_Funtarget.game_Id_Target);
			String str=IPAddressURL+"/json/Send_Bet_Values_Target/"+game_id_target+"/"+strNumbers_target+"/"+ Integer.toString(total_bet_target)+"";
			
			JSONObject jsonObject878= func2(str);
            JSONArray jsonArray_send_bet_target_values=null;
            
            //Add_Bet_Status_Get_Current_Server_GameID_FR.Is_Bet_In_Time_Status=false;
			Add_Bet_Status_Get_Current_Server_GameID_FT.Game_Id_Target=-1;
			
			try
		       {
				jsonArray_send_bet_target_values= jsonObject878.getJSONArray("Send_Bet_Values_TargetResult");
		    	   
		    	   for(int i=0;i<jsonArray_send_bet_target_values.length();i++){
			    		  JSONObject Add_Bet_Status_Get_Current_Server_GameID_FT_JSON = jsonArray_send_bet_target_values.getJSONObject(i);
			    
			              Add_Bet_Status_Get_Current_Server_GameID_FT.Is_Bet_In_Time_Status_Target=Add_Bet_Status_Get_Current_Server_GameID_FT_JSON.getBoolean("Is_Bet_In_Time_Status_Target");
			              Add_Bet_Status_Get_Current_Server_GameID_FT.Game_Id_Target=Add_Bet_Status_Get_Current_Server_GameID_FT_JSON.getInt("Game_Id_Target");

		    	    }
			   } catch (JSONException e) {
				    	e.printStackTrace();
			          }
		      return Add_Bet_Status_Get_Current_Server_GameID_FT.Is_Bet_In_Time_Status_Target;
		}
		
		
		public int Save_Player_Game_Playing_Info_Target_json(String total_Amount,String str_Bets_Number_Wise,String win_Amount,String player_balance_for_history,String game_Id,String user_id)
		{
			String str=IPAddressURL+"/json/Save_Player_Game_Playing_Info_in_Fun_Target/"+total_Amount+"/"+str_Bets_Number_Wise+"/"+win_Amount +"/"+player_balance_for_history+"/"+game_Id+"/"+user_id+"";
			int Save_Player_Game_Playing_Info_result_Target=0;
			JSONObject jsonObject879= func2(str);
			
			try
		       {
				Save_Player_Game_Playing_Info_result_Target=jsonObject879.getInt("Save_Player_Game_Playing_Info_in_Fun_TargetResult");
			   } catch (JSONException e) {
				    	e.printStackTrace();
			          }
		      return Save_Player_Game_Playing_Info_result_Target;
		}
	
		
		public void Get_Next_Game_ID_And_Time_Target()
		{
			//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Get_Game_Details");
			JSONObject jsonObject= func2(IPAddressURL+ "/json/Get_Next_Game_ID_And_Time_Target");
			JSONArray jsonArray_next_game_id_time_target=null;
			
			Next_Game_ID_Time_FunTarget.game_Id_Target=-1;
			Next_Game_ID_Time_FunTarget.Time_Seconds_Target=-1;
		   try
	       {
			   jsonArray_next_game_id_time_target= jsonObject.getJSONArray("Get_Next_Game_ID_And_Time_TargetResult");
	    	   
	    	   for(int i=0;i<jsonArray_next_game_id_time_target.length();i++){
		    		  JSONObject Next_Game_ID_Time_FunTarget_JSON = jsonArray_next_game_id_time_target.getJSONObject(i);
		    
		    		  Next_Game_ID_Time_FunTarget.Time_Seconds_Target=Next_Game_ID_Time_FunTarget_JSON.getInt("Time_Seconds_Target");
		    		  Next_Game_ID_Time_FunTarget.game_Id_Target=Next_Game_ID_Time_FunTarget_JSON.getInt("game_Id_Target");
	    	    }
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      
		}
		
		
		
		
	    static JSONObject objJSON2;
		public static JSONObject func2(String url)
		{
			//url =http://localhost:49724/AndroidService/Service.svc/dowork/1
			android.util.Log.i("WCFIP", url);
			
			try
			{
				System.setProperty("http.keepAlive", "false");
				HttpGet request=new HttpGet(url);
				request.setHeader("Accept","application/json");
				request.setHeader("Content-type","application/json");
				DefaultHttpClient httpClient=new DefaultHttpClient();
				HttpResponse response=httpClient.execute(request);
				HttpEntity responseEntity=response.getEntity();
				char[] buffer=new char[(int) responseEntity.getContentLength()];
				InputStream stream=responseEntity.getContent();
				InputStreamReader reader=new InputStreamReader(stream);
				reader.read(buffer);
				stream.close();
				objJSON2 =new JSONObject(new String(buffer));
				
			}catch(Exception e){
			   android.util.Log.i("loi Roi :",e.toString());	
			}
			
			return objJSON2;
		}

		
	 
		
		
		
		public static class Game_Details_Funtarget
		{
			  public static int Time_Seconds_Target;
		      public static int[] Recent_Values_10_Target;
		      public static int current_Game_Result_OR_Next_Game_Prepare_Target;
		      public static int game_Id_Target;
		      public static int joker_img_index;
		}
	 
		
		public static class Add_Bet_Status_Get_Current_Server_GameID_FT
		{
		   public static boolean Is_Bet_In_Time_Status_Target;
		   public static int Game_Id_Target;
		}
		
		
		public static class Next_Game_ID_Time_FunTarget
		{
			  public static int Time_Seconds_Target;
		      public static int game_Id_Target;
		}
	}





