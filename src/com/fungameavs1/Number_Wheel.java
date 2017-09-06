package com.fungameavs1;

import java.util.Timer;

import android.media.MediaPlayer;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class Number_Wheel {

    private double angle;
    private double angle_ball;  // default is 0.5 .. initialized in constructor

    private double ball_Next_Angle;
    private double whee_Next_Angle;
    
    private double ball_ellipse_radius_X;  // ellipse radius on x- axis
    private double ball_ellipse_radius_Y;  // ellipse radius on y-axis
    private int[] wheel_numbers;
    private double screen_Height = 0;
    private double screen_Width = 0;
    MediaPlayer mediaPlayer = null;
      
    int diamong_img_Id = 0;

    double left_Ball = 0;
    double top_Ball = 0;

    boolean is_Rotation_Stop = false;
    int stop_Ball_At;

    private RotateAnimation wheel_RotateTransform = null;
    private TranslateAnimation ball_TranslateTransform = null;        
    

    Timer timer_Rotate = null;

	
	
	
    
    
    public Number_Wheel(RelativeLayout grid,int screenWidthDp,int screenHeightDp)
    {
        //this.grid_FR_Lay_Out = grid;
        //screen_Height = screen_height;
        //screen_Width = screen_height;

        //Fill_Wheel_Numbers();

        //Place_Number_Wheel(col_Num, row_Num);
        //Place_Diamond();

        angle = 0.0d;
        angle_ball = 0.0d;  // setting to defauls values

       // Update_Diamond_Back_Ground();
       // Prepare_For_Rotation();

        /*timer_Rotate = new System.Windows.Threading.DispatcherTimer(System.Windows.Threading.DispatcherPriority.Render);
        timer_Rotate.Tick += new EventHandler(Start_Rotating);
        timer_Rotate.Interval = new TimeSpan(0, 0, 0, 0, 1);*/
    }
    
    
	
}
