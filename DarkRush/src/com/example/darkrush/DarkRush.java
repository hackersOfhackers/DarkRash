package com.example.darkrush;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;

public class DarkRush extends Activity /*implements OnGestureListener*/{

	MediaPlayer mp1;
	MediaPlayer mp2;
	MediaPlayer mp3;
	GestureDetector gestureScanner;
	int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		count=0;
		setContentView(R.layout.activity_dark_rush);
		//audioPlayer("/sdcard","welcome.mp3");
	/*	try {
			Thread.sleep(200000);
			audioPlayer("/sdcard","introduction.wav");
			Thread.sleep(300000);
			audioPlayer("/sdcard","instruction.mp3");
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Intent intent = new Intent(this, Second.class);
		startActivity(intent);

	}
	
	
/*	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // TODO Auto-generated method stub
	    return gestureScanner.onTouchEvent(event);
	}

	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
	    // TODO Auto-generated method stub
		//if(count==0)
		{
		audioPlayer("/sdcard","welcome.mp3");
		TextView t=(TextView) findViewById(R.id.label);
		t.setText("Playing audio");
		count++;
		//SystemClock.sleep(2);
		//mp.stop();
		}
		else if(count==1)
		{
		audioPlayer("/sdcard","instruction.mp3");
		//SystemClock.sleep(8);
		//mp.stop();
		count++;
		}
		else if(count==2)
		{
		audioPlayer("/sdcard","introduction.wav");
		//SystemClock.sleep(29);
		//mp.stop();
		count++;
		}
		else if(count==3)	
		{
	    mp1.stop();
	    mp2.stop();
	    mp3.stop();
		Intent intent = new Intent(this, Third.class);
		//startActivity(intent);
		count++;
		}
	   
		//Log.i("Test", "Single tap up");
	    return false;
	}
	
	*/
	
	
	
	public void audioPlayer(String path, String fileName){
	    //set up MediaPlayer
		if(count==0)
	     mp1 = new MediaPlayer();
		else if(count==1)
		     mp2 = new MediaPlayer();
		else if(count==2)
		     mp3 = new MediaPlayer();

	    try {
	    	if(count==0)
	        mp1.setDataSource(path+"/"+fileName);
	    /*	else if(count==1)
	    		mp2.setDataSource(path+"/"+fileName);
	    	else if(count==2)
	    		mp3.setDataSource(path+"/"+fileName);*/
	    } catch (IllegalArgumentException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IllegalStateException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    try {
	    	if(count==0)
	        mp1.prepare();
	    /*	else if(count==1)
	    		mp2.prepare();
	    	else if(count==2)
	    		mp3.prepare();*/
	    		
	    } catch (IllegalStateException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    if(count==0)
	    mp1.start();
	 /*   else if(count==1)
	    mp2.start();
	    else
	    	mp3.start();*/
	    
	}



/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dark_rush, menu);
		return true;
	}


	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}*/

}
