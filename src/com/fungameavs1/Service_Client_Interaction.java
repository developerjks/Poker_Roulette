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



public class Service_Client_Interaction {
	
	public static String player_user_id_logged_in_last_game;
	//public static int Total_Bet_Amount=0; 
	
	//String IPAddressURL="http://192.168.1.100/AndroidService/Service.svc";
	String IPAddressURL="http://playgameindia.net/test_AndroidService/Service.svc";
	
	
	
	
	
	
	
	 
	 
	/*  Update_Point_Transaction_Status JSON function   (to update point transaction status using transaction status and id)  starts here*/
	 int update_point_transaction_status_value_json;
	 @SuppressWarnings("deprecation")
	 public int Update_Point_Transaction_Status_json(String status, String id) {
		 //String hostURL=	"http://192.168.1.3/AndroidService/Service.svc/json/Update_Point_Transaction_Status/"+status+"/"+id+"";
		 String hostURL=IPAddressURL +"/json/Update_Point_Transaction_Status/"+status+"/"+id+"";
		 JSONObject jsonObject8= func1(hostURL);
			int m=2;
	       try
	       {  
	    	   update_point_transaction_status_value_json=jsonObject8.getInt("Update_Point_Transaction_StatusResult");
	       } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	  
	  return update_point_transaction_status_value_json;
	 }
	 /*  Update_Point_Transaction_Status- JSON function   (to update point transaction status using transaction status and id)  ends here*/
	 
	 
	 /*  Save_Trasaction_Info JSON function   (to save transaction info status of userid) starts here*/
	 int save_trasaction_info_status_value_json;
	 @SuppressWarnings("deprecation")
	 public int Save_Trasaction_Info_json(String receiver_id, String r_type, String retrieved_r_amount, String s_type, String retireved_s_amount,String transaction_status,String user_id)
	 {
		 //String hostURL="http://192.168.1.6/AndroidService/Service.svc/json/Save_Trasaction_Info/"+receiver_id+"/"+r_type+"/"+retrieved_r_amount+"/"+s_type+"/"+retireved_s_amount+"/"+transaction_status+"/"+user_id+"";
		 String hostURL=IPAddressURL +"/json/Save_Trasaction_Info/"+receiver_id+"/"+r_type+"/"+retrieved_r_amount+"/"+s_type+"/"+retireved_s_amount+"/"+transaction_status+"/"+user_id+"";
	 	 JSONObject jsonObject91= func1(hostURL);
			
	       try
	       {
	    	   save_trasaction_info_status_value_json=jsonObject91.getInt("Save_Trasaction_InfoResult");
	       } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	  
	  return save_trasaction_info_status_value_json;
	 }
     /*  Save_Trasaction_Info JSON function   (to save transaction info status of userid) ends here*/

	 /*  Update_User_Account_Balance JSON function   (to update user account balance of userid) starts here*/
	 int update_user_acccount_balance_status_value_json;
	 @SuppressWarnings("deprecation")
	 public int Update_User_Account_Balance_json(String bal,String user_id) {
           //String hostURL= "http://192.168.1.3/AndroidService/Service.svc/json/Update_User_Account_Balance/"+bal+"/"+user_id+"";
		 String hostURL= IPAddressURL +"/json/Update_User_Account_Balance/"+bal+"/"+user_id+"";
		 	JSONObject jsonObject9= func1(hostURL);
			
	       try
	       {
	    	   update_user_acccount_balance_status_value_json=jsonObject9.getInt("Update_User_Account_BalanceResult");
	       } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	  
	  return update_user_acccount_balance_status_value_json;
	 }
	 /*  Update_User_Account_Balance JSON function   (to update user account balance of userid) ends here*/
	
	 /* CheckUserAvailability JSON function starts  */
	 int signIn_status;
	 @SuppressWarnings("deprecation")
	 public int CheckUserAvailabilty_json(String username,String pass)
	 {
				//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/SignIn/"+username+"/"+pass+"");
		 JSONObject jsonObject= func(IPAddressURL+ "/json/SignIn/"+username+"/"+pass+"");
				
		       try {
		    	   signIn_status=jsonObject.getInt("SignInResult");
		       } catch (JSONException e) {
				    	e.printStackTrace();
			          }
	  return signIn_status;
	 }
     /* CheckUserAvailability JSON function ends  */ 	 
	 
	 /*  SaveUserIdDuringLogin JSON function   (to save the information about userid and window id)  starts*/
	 int save_value_JSON;
	 @SuppressWarnings("deprecation")
	 public int SaveUserIdDuringLogin_json(String userid,String windowid) {
		 	//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Save_User_Id_During_Login/"+userid+"/"+windowid+"");
		 JSONObject jsonObject= func(IPAddressURL+ "/json/Save_User_Id_During_Login/"+userid+"/"+windowid+"");
			
	       try
	       {
	    	   save_value_JSON=jsonObject.getInt("Save_User_Id_During_LoginResult");
	       } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	  
	  return save_value_JSON;
	 }
	 /*  SaveUserIdDuringLogin JSON function   (to save the information about userid and window id)  ends*/
	 
	 /*  SaveUserIdDuringLogin JSON function   (to save the information about userid and window id)  starts*/
	 int del_value_JSON;
	 @SuppressWarnings("deprecation")
	 public int Delete_User_Id_During_Logout_json(String userid,String windowid) {
		 	//JSONObject jsonObject2= func("http://192.168.1.3/AndroidService/Service.svc/json/Delete_User_Id_During_Logout/"+userid+"/"+windowid+"");
		 JSONObject jsonObject2= func(IPAddressURL+"/json/Delete_User_Id_During_Logout/"+userid+"/"+windowid+"");
			
	       try {
	    	   del_value_JSON=jsonObject2.getInt("Delete_User_Id_During_LogoutResult");
	          } catch (JSONException e) {
			    	  e.printStackTrace();
		            }
	    return del_value_JSON;
	  }
	 /*  SaveUserIdDuringLogin JSON function   (to save the information about userid and window id)  ends*/

	 /*  checkwindowid_expire_status JSON function   (to check the expiration of window id)  starts here*/
	 int checkwindowid_JSON;
	 @SuppressWarnings("deprecation")
	 public int checkwindowid_expire_status_json(String windowid) {
		 	//JSONObject jsonObject1= func("http://192.168.1.3/AndroidService/Service.svc/json/checkwindowid_expire_status/"+windowid+"");
		 JSONObject jsonObject1= func(IPAddressURL+"/json/checkwindowid_expire_status/"+windowid+"");
			
	       try{
	    	   checkwindowid_JSON=jsonObject1.getInt("checkwindowid_expire_statusResult");
	    	   
	          } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	  return checkwindowid_JSON;
	 }
	 /*  checkwindowid_expire_status JSON function   (to check the expiration of window id)  ends here*/
	 
	 /* GetPlayerBalance JSON function starts   */
	 int balance_json;
	 @SuppressWarnings("deprecation")
	 public int GetPlayerBalance_json(String userid) {
		 	//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Get_Player_Balance/"+userid+"");
		 JSONObject jsonObject= func( IPAddressURL+ "/json/Get_Player_Balance/"+userid+"");
			
		       try {
		    	   balance_json=jsonObject.getInt("Get_Player_BalanceResult");
		       } catch (JSONException e) {
				    	e.printStackTrace();
			          }
	  return balance_json;
	 }
	 /*GetPlayerBalance JSON function ends   */
	
	 public List Show_Transfer_DataGrid_Records(String transaction_status,String userid)
     {
		   List myList=null;

    	    //JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Show_Transfer_Datagrid/"+transaction_status+"/"+userid+"");
		   JSONObject jsonObject= func(IPAddressURL+ "/json/Show_Transfer_Datagrid/"+transaction_status+"/"+userid+"");
			JSONArray jsonArray_Point_Transaction=null;
			Point_Transaction.ID=-1;
			Point_Transaction.RECEIVER_ID="";
			Point_Transaction.RECEIVER_ROLE="";
			Point_Transaction.RAMOUNT=-1;
			
	       try
	       {
	    	   myList=new ArrayList();
	    	 	
	    	   jsonArray_Point_Transaction= jsonObject.getJSONArray("Show_Transfer_DatagridResult");
	    	   
	    	   for(int i=0;i<jsonArray_Point_Transaction.length();i++){
		    		    JSONObject Transfer_Login_Point_Transaction_JSON = jsonArray_Point_Transaction.getJSONObject(i);
		    		  
		    		    Point_Transaction.ID=Transfer_Login_Point_Transaction_JSON.getInt("ID");
		    		    Point_Transaction.RECEIVER_ID=Transfer_Login_Point_Transaction_JSON.getString("RECEIVER_ID");
		    		    Point_Transaction.RECEIVER_ROLE=Transfer_Login_Point_Transaction_JSON.getString("RECEIVER_ROLE");
		    		    Point_Transaction.RAMOUNT=Transfer_Login_Point_Transaction_JSON.getDouble("RAMOUNT");
		    		    
		    		    
		    		    List list1=new ArrayList();
		    		    list1.add(Point_Transaction.ID);
		    		    list1.add(Point_Transaction.RECEIVER_ID);
		    		    list1.add(Point_Transaction.RECEIVER_ROLE);
		    		    list1.add(Point_Transaction.RAMOUNT);
		    		    	       
   	            myList.add(list1);


		    	     }
		    } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	   return myList;
     }

	 public List Show_Receiver_Datagrid_Records(String transaction_status,String userid)
     {
		 List myList=null;
    	    //JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Show_Receiver_Datagrid/"+transaction_status+"/"+userid+"");
		 JSONObject jsonObject= func(IPAddressURL+ "/json/Show_Receiver_Datagrid/"+transaction_status+"/"+userid+"");
			JSONArray jsonArray_Point_Transaction=null;
			Point_Transaction.ID=-1;
			Point_Transaction.SENDER_ID="";
	        Point_Transaction.SENDER_ROLE="";
	        Point_Transaction.SAMOUNT=-1;
	        
	       try
	       {
	    	   jsonArray_Point_Transaction= jsonObject.getJSONArray("Show_Receiver_DatagridResult");
	  		 
	    	   myList=new ArrayList();
	    	   
	    	   for(int i=0;i<jsonArray_Point_Transaction.length();i++){
		    		    JSONObject Reciever_Point_Transaction_JSON = jsonArray_Point_Transaction.getJSONObject(i);
		    		    //Point_Transaction obj=new Point_Transaction();
		    		    Point_Transaction.ID= Reciever_Point_Transaction_JSON.getInt("ID");
		    	        Point_Transaction.SENDER_ID=Reciever_Point_Transaction_JSON.getString("SENDER_ID");
		    	        Point_Transaction.SENDER_ROLE=Reciever_Point_Transaction_JSON.getString("SENDER_ROLE");
		    	        Point_Transaction.SAMOUNT=Reciever_Point_Transaction_JSON.getDouble("SAMOUNT");

					 List list1=new ArrayList();
					 list1.add(Point_Transaction.ID);
					 list1.add(Point_Transaction.SENDER_ID);
					 list1.add(Point_Transaction.SENDER_ROLE);
					 list1.add(Point_Transaction.SAMOUNT);
		    	       
		    	myList.add(list1);
		    	       
	    	   }
		    } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	       
	       return myList;
	   
     }
	 
	 
     public void Show_user_detail(String userid)
     {
    	    //JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Show_Login_User_Account_Detail/"+userid+"");
    	    JSONObject jsonObject= func(IPAddressURL+ "/json/Show_Login_User_Account_Detail/"+userid+"");
			JSONArray jsonArray_Login_User_Details=null;
			Login_User_Details.USER_BALANCE=-1;
			Login_User_Details.USER_ID="";
			Login_User_Details.USER_PIN=-1;
			Login_User_Details.SENDER_ROLE="";
			Login_User_Details.USERNAME="";

	       try
	       {
	    	   jsonArray_Login_User_Details= jsonObject.getJSONArray("Show_Login_User_Account_DetailResult");
	    	   
	    	   for(int i=0;i<jsonArray_Login_User_Details.length();i++){
		    		    JSONObject Login_User_Details_JSON = jsonArray_Login_User_Details.getJSONObject(i);
		    		  
			    		Login_User_Details.USER_BALANCE=Login_User_Details_JSON.getInt("USER_BALANCE");
			  			Login_User_Details.USER_ID=Login_User_Details_JSON.getString("USER_ID");
			  			Login_User_Details.USER_PIN=Login_User_Details_JSON.getInt("USER_PIN");
			  			Login_User_Details.SENDER_ROLE=Login_User_Details_JSON.getString("USER_ROLE");
			  			Login_User_Details.USERNAME=Login_User_Details_JSON.getString("USERNAME");
	    	         }
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
     }
     
     
	 /* GetRecieverInfo JSON function starts here  */
	 String receiver_info_json;
	 @SuppressWarnings("deprecation")
	 public String Get_Receiver_info_json(String r_id) {
		 	//JSONObject jsonObject123= func1("http://192.168.1.3/AndroidService/Service.svc/json/Get_Receiver_info/"+r_id+"");
		    JSONObject jsonObject123= func1(IPAddressURL+"/json/Get_Receiver_info/"+r_id+"");
			
		       try {
		    	   receiver_info_json=jsonObject123.getString("Get_Receiver_infoResult");
		       } catch (JSONException e) {
				    	e.printStackTrace();
			          }
	  return receiver_info_json;
	 }
	 /* GetRecieverInfo JSON function ends here   */
     
        static JSONObject objJSON1;
		public static JSONObject func1(String url)
		{
			//url =http://localhost:49724/AndroidService/Service.svc/dowork/1
			android.util.Log.i("WCFIP", url);
			
			try
			{
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
				objJSON1 =new JSONObject(new String(buffer));
				
			}catch(Exception e){
			   android.util.Log.i("loi Roi :",e.toString());	
			}
			
			return objJSON1;
		}
     
	    static JSONObject objJSON;
		public static JSONObject func(String url)
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
				objJSON =new JSONObject(new String(buffer));
				
			}catch(Exception e){
			   android.util.Log.i("loi Roi :",e.toString());	
			}
			
			return objJSON;
		}
	 
		
		
		
	     
			
			
			
		
		
		
		
		
		int fun_roulett_result = 0;        
		public int Get_FunRoulette_Game_Result(int current_game_id)
		{
			JSONObject jsonObject= func(IPAddressURL+"/json/Get_Minimum_Number/"+current_game_id+"");
	       try
	       {
				fun_roulett_result=jsonObject.getInt("Get_Minimum_NumberResult");
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      return fun_roulett_result;
		}
		
		
		
		public void Get_Next_Game_ID_And_Time()
		{
			//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Get_Game_Details");
			JSONObject jsonObject= func(IPAddressURL+ "/json/Get_Next_Game_ID_And_Time");
			JSONArray jsonArray_next_game_id_time=null;
			
			Next_Game_ID_Time_FunRoulette.game_Id=-1;
			Next_Game_ID_Time_FunRoulette.Time_Seconds=-1;
		   try
	       {
			   jsonArray_next_game_id_time= jsonObject.getJSONArray("Get_Next_Game_ID_And_TimeResult");
	    	   
	    	   for(int i=0;i<jsonArray_next_game_id_time.length();i++){
		    		  JSONObject Next_Game_ID_Time_FunRoulette_JSON = jsonArray_next_game_id_time.getJSONObject(i);
		    
		    		  Next_Game_ID_Time_FunRoulette.Time_Seconds=Next_Game_ID_Time_FunRoulette_JSON.getInt("Time_Seconds");
		    		  Next_Game_ID_Time_FunRoulette.game_Id=Next_Game_ID_Time_FunRoulette_JSON.getInt("Game_Id");
	    	    }
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      
		}
		
		
		boolean Is_Bet_In_Time_Status;
		public boolean Send_Bet_Values_json(String strNumbers,int total_bet)
		{
			//String game_id= Integer.toString(Game_Details_FunRoulette.game_Id);
			String str=IPAddressURL+"/json/Send_Bet_Values/"+strNumbers+"/"+ Integer.toString(total_bet)+"";
			
			JSONObject jsonObject878= func(str);
            JSONArray jsonArray_send_bet_values=null;
            
            //Add_Bet_Status_Get_Current_Server_GameID_FR.Is_Bet_In_Time_Status=false;
			Add_Bet_Status_Get_Current_Server_GameID_FR.Game_Id=-1;
			
			try
		       {
				jsonArray_send_bet_values= jsonObject878.getJSONArray("Send_Bet_ValuesResult");
		    	   
		    	   for(int i=0;i<jsonArray_send_bet_values.length();i++){
			    		  JSONObject Add_Bet_Status_Get_Current_Server_GameID_FR_JSON = jsonArray_send_bet_values.getJSONObject(i);
			    
			              Add_Bet_Status_Get_Current_Server_GameID_FR.Is_Bet_In_Time_Status=Add_Bet_Status_Get_Current_Server_GameID_FR_JSON.getBoolean("Is_Bet_In_Time_Status");
			              Add_Bet_Status_Get_Current_Server_GameID_FR.Game_Id=Add_Bet_Status_Get_Current_Server_GameID_FR_JSON.getInt("Game_Id");

		    	    }
			   } catch (JSONException e) {
				    	e.printStackTrace();
			          }
		      return Add_Bet_Status_Get_Current_Server_GameID_FR.Is_Bet_In_Time_Status;
		}
		
		

		 /* Add_Balance_During_Bet_Cancel_In_FunRoulett starts here  */
		 public void Add_Balance_During_Bet_Cancel_In_FunRoulett(double balance,int total_invest_amount)
	     { 
	                 
	     }
		/* Add_Balance_During_Bet_Cancel_In_FunRoulett ends here  */
		
		

		public int Save_Player_Game_Playing_Info_json(String total_Amount,String str_Bets_Number_Wise,String win_Amount,String player_balance_for_history,String game_Id,String user_id)
		{
			String str=IPAddressURL+"/json/Save_Player_Game_Playing_Info/"+total_Amount+"/"+str_Bets_Number_Wise+"/"+win_Amount +"/"+player_balance_for_history+"/"+game_Id+"/"+user_id+"";
			int Save_Player_Game_Playing_Info_result=0;
			JSONObject jsonObject879= func(str);
			
			try
		       {
				Save_Player_Game_Playing_Info_result=jsonObject879.getInt("Save_Player_Game_Playing_InfoResult");
			   } catch (JSONException e) {
				    	e.printStackTrace();
			          }
		      return Save_Player_Game_Playing_Info_result;
		}
		
	
		public void Get_FunRoulette_Game_Details()
		{
			//JSONObject jsonObject= func("http://192.168.1.3/AndroidService/Service.svc/json/Get_Game_Details");
			JSONObject jsonObject= func(IPAddressURL+ "/json/Get_Game_Details");
			JSONArray jsonArray_game_details=null;
			JSONArray jsonArray_game_details_recent_list=null;
			Game_Details_FunRoulette.Recent_Values=null;
			Game_Details_FunRoulette.game_Id=-1;
			  //int Time_Seconds=-1,current_Game_Result_OR_Next_Game_Prepare=-1;
			  //int Recent_Values[];
	       try
	       {
	    	   Game_Details_FunRoulette.Recent_Values=new int[5];
	    	   jsonArray_game_details= jsonObject.getJSONArray("Get_Game_DetailsResult");
	    	   
	    	   for(int i=0;i<jsonArray_game_details.length();i++){
		    		  JSONObject Game_Details_JSON = jsonArray_game_details.getJSONObject(i);
		    		  
		    		  Game_Details_FunRoulette.Time_Seconds= Game_Details_JSON.getInt("Time_Seconds");
		    		  Game_Details_FunRoulette.current_Game_Result_OR_Next_Game_Prepare = Game_Details_JSON.getInt("current_Game_Result_OR_Next_Game_Prepare");
		    		  Game_Details_FunRoulette.game_Id= Game_Details_JSON.getInt("game_Id");
		    		  jsonArray_game_details_recent_list= Game_Details_JSON.getJSONArray("Recent_Values");
		    		  
		    		  for(int j=0;j<jsonArray_game_details_recent_list.length();j++)	  {
		    			 Game_Details_FunRoulette.Recent_Values[j]=jsonArray_game_details_recent_list.getInt(j);    	
		              }
	    	    }
		   } catch (JSONException e) {
			    	e.printStackTrace();
		          }
	      
		}
		
		
		
		
		public static class Login_User_Details
	    {
	        public static String USER_ID;
	        public static int USER_PIN;
	        public static int USER_BALANCE;
	        public static String SENDER_ROLE;
	        public static String USERNAME;
	    }
		
		public static class Game_Details_FunRoulette
		{
			  public static int Time_Seconds;
		      public static int[] Recent_Values;
		      public static int current_Game_Result_OR_Next_Game_Prepare;
		      public static int game_Id;
		}
		 
		
		
		public static class Next_Game_ID_Time_FunRoulette
		{
			  public static int Time_Seconds;
		      public static int game_Id;
		}
		
		
		public static class Add_Bet_Status_Get_Current_Server_GameID_FR
		{
		   public static boolean Is_Bet_In_Time_Status;
		   public static int Game_Id;
		}
		
		public static class Point_Transaction
		{
		        public static int ID;
		        public static String RECEIVER_ID;
		        public static String RECEIVER_ROLE;
		        public static double RAMOUNT;
		        public static String SENDER_ID;
		        public static String SENDER_ROLE;
		        public static double SAMOUNT;
		        public static String TRANSACTION_STATUS;
		        public static int COUNT;
		        public static boolean IsSelected;
		}
	 
}
