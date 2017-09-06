package com.fungameavs1;

import android.app.Application;

public class GlobalClass extends Application{
     
	public static String username;
	public static int balance;
	public static String balance_to_update;
	public static int pin;
	public static String connction_msg;
	public static String userid;
	public static String sender_role;
	public static String transaction_status;
    private static String window_id;
    private static int Total_Bet_Amount;
    private static int Total_Bet_Amount_Target;
    

    public static int getTotal_Bet_Amount_Target() {        return Total_Bet_Amount_Target;    }  
    public static void setTotal_Bet_Amount_Target(int tbamt) {      Total_Bet_Amount_Target = tbamt;  }

    public static int getTotal_Bet_Amount() {        return Total_Bet_Amount;    }  
    public static void setTotal_Bet_Amount(int tbamt) {      Total_Bet_Amount = tbamt;  }
    
    public static String getUserId() {        return userid;    }  
    public static void setUserId(String uid) {      userid = uid;  }
    
    public static int getBalance() {     return balance;   }
    public static void setBalance(int ubal) { balance = ubal ;    }

    public static String getUserName() { return username; }
	public static void setUserName(String user) { username = user; }
	
    public static int getPin() {return pin;	}
	public static void setPin(int userpin) {pin = userpin;}

    public static String getConnction_msg() {return connction_msg;	}
	public static void setConnction_msg(String connction_msg_) { connction_msg = connction_msg_;}
    
    public static String getSendererRole() { return sender_role;}
	public static void setSenderRole(String userrole) {sender_role = userrole;}

    public static String getTransaction_status() {return transaction_status;}
	public static void setTransaction_status(String user_transaction_status) {transaction_status = user_transaction_status;}

	public static String getWindow_id() {		return window_id;	}
	public static void setWindow_id(String windowid) {		window_id = windowid;	}
 	
	public static String getBalance_to_update() {	return balance_to_update;	}
	public static void setBalance_to_update(String balance) {	balance_to_update = balance;	}
	
	
	
	
	
	 @Override
		public void onTerminate() {
		 super.onTerminate();
	    	Service_Client_Interaction service_client=new Service_Client_Interaction();
	    	int del = service_client.Delete_User_Id_During_Logout_json(getUserId(), getWindow_id());
			
		}

	
}