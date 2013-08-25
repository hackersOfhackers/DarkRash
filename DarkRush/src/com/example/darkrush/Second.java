package com.example.darkrush;


import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class Second extends Activity implements OnGestureListener,TextToSpeech.OnInitListener  {

GestureDetector gestureScanner;
long prevEventTime;
PendingIntent pi;
BroadcastReceiver br;
MediaPlayer mp;
AlarmManager am;
long counter=0;
//long apple[]={2040,2100,2160,2280,2340,2460,2580,2700,2760,4500,4620,4680,4800,4860,4980};
int hurdle[]={17000,61000};
int hurdle_no=0;
int swipe_no=0;
private TextToSpeech tts;
private Button btnSpeak;
private EditText txtText;

boolean flag[]={false,false,false};
//@SuppressWarnings("deprecation")
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  //  setContentView(R.layout.wheelview);
    setContentView(R.layout.activity_dark_rush);

    //Calendar C=Calendar.getInstance();
    //TextView t=(TextView) findViewById(R.id.label);
    //t.setText(C.get(Calendar.SECOND));   
    /*Time time = new Time();
    time.setToNow();
    int hour,min,sec;
    hour=time.hour;
    min=time.minute;
    sec=time.second;
    */
    //View view = this.getWindow().getDecorView();
    //view.setBackgroundColor(0x00000000);
    gestureScanner = new GestureDetector(this);
   // prevEventTime=hour*3600*1000+min*60000+sec*1000;
    prevEventTime=System.currentTimeMillis();
    setup();
    am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, prevEventTime + 
    		hurdle[0], pi );
    audioPlayer("/sdcard","full_game.wav");
   long time=System.currentTimeMillis();
    
   
  /*  time.setToNow();
    hour=time.hour;
    min=time.minute;
    sec=time.second;*/
    
   // long time1=hour*3600*1000+min*60000+sec*1000;
    if(Math.abs(time-prevEventTime)>=90000)
    	finish_activity();
    
}
void finish_activity()
{
	
   //  mp.stop();
	TextView t=(TextView) findViewById(R.id.label);
    t.setText("finishing activity");
     speakOut();
}
private void setup() {
    br = new BroadcastReceiver() {
           @Override
           public void onReceive(Context c, Intent i) {
                //  Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
                  hurdle_no++;
                  if(hurdle_no!=swipe_no)
                  {
                	  counter--;
                	  swipe_no=hurdle_no;
                  }
                  am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, prevEventTime+ 
                  		hurdle[hurdle_no], pi );  
                	  
                  }
           };
    registerReceiver(br, new IntentFilter("com.authorwjf.wakeywakey") );
    pi = PendingIntent.getBroadcast( this, 0, new Intent("com.authorwjf.wakeywakey"),
0 );
    am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
}

@Override
protected void onDestroy() {
       am.cancel(pi);
       unregisterReceiver(br);
       super.onDestroy();
}
@Override
public boolean onTouchEvent(MotionEvent event) {
    // TODO Auto-generated method stub
    return gestureScanner.onTouchEvent(event);
}

@Override
public boolean onDown(MotionEvent e) {
    // TODO Auto-generated method stub
	//TextView t=(TextView) findViewById(R.id.label);
//	t.setText("DOWN");
	//Log.i("Test", "on down");
    return true;
}

@Override
public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
        float velocityY) {
    // TODO Auto-generated method stub
	//TextView t=(TextView) findViewById(R.id.label);
//	t.setText("FILLING");
   // Log.i("Test", "On Fling");
    return true;
}

@Override
public void onLongPress(MotionEvent e) {
    // TODO Auto-generated method stub
	long serverTimeStamp=-1;
	TextView t=(TextView) findViewById(R.id.label);
	//t.setText("ON LONG PRESS");
	//Log.i("Test", "on long press");
	serverTimeStamp=System.currentTimeMillis();
    t.setText(prevEventTime+"    "+serverTimeStamp+"  "+counter);
    if(Math.abs(serverTimeStamp-prevEventTime)>36000 && Math.abs(serverTimeStamp-prevEventTime)<36720)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
   

}

@Override
public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
        float distanceY) {
	
	
	 speakOut();
	 speakOut();
	 speakOut();
	TextView t=(TextView) findViewById(R.id.label);
	//t.setText("SCROLL");
	long serverTimeStamp=-1;
	//TextView t=(TextView) findViewById(R.id.label);
	//t.setText("ON LONG PRESS");
	//Log.i("Test", "on long press");
	serverTimeStamp=System.currentTimeMillis();
	//Time time = new Time();
    /*time.setToNow();
    int hour,min,sec;
    hour=time.hour;
    min=time.minute;
    sec=time.second;
    long serverTimeStamp=hour*3600*1000+min*60000+sec*1000;*/
	
	t.setText(prevEventTime+"    "+serverTimeStamp+"  "+counter);
    if(Math.abs(serverTimeStamp-prevEventTime)>hurdle[hurdle_no] && Math.abs(serverTimeStamp-prevEventTime)<hurdle[hurdle_no]+80 && !flag[hurdle_no])
    {
    //server timestamp is within 5 minutes of current system time
    	//counter++;
    	swipe_no++;
    	flag[hurdle_no]=true;
    	t.setText("Action was scroll  "+counter+"\n");
    } 
    else
    {
    	
    }
  // Log.i("Test", "Scroll");
    return false;
}

@Override
public void onShowPress(MotionEvent e) {
    // TODO Auto-generated method stub
	TextView t=(TextView) findViewById(R.id.label);
	t.setText("SHOW PRESS");
	//Log.i("Test", "on show press");

}

@Override
public boolean onSingleTapUp(MotionEvent e) {
	
	/*Time time = new Time();
    time.setToNow();
    int hour,min,sec;
    hour=time.hour;
    min=time.minute;
    sec=time.second;
    long serverTimeStamp=hour*3600*1000+min*60000+sec*1000;*/
	
	 speakOut();
	 speakOut();
	 speakOut();
    // TODO Auto-generated method stub
	TextView t=(TextView) findViewById(R.id.label);
	t.setText("SINGLE TAP UP");
	long serverTimeStamp=-1;
	//TextView t=(TextView) findViewById(R.id.label);
	//t.setText("ON LONG PRESS");
	//Log.i("Test", "on long press");
	serverTimeStamp=System.currentTimeMillis();
	t.setText(prevEventTime+"    "+serverTimeStamp+"  "+counter +"   "+Math.abs(serverTimeStamp-prevEventTime));
    if(Math.abs(serverTimeStamp-prevEventTime)>33900 && Math.abs(serverTimeStamp-prevEventTime)<34010)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<35010 && Math.abs(serverTimeStamp-prevEventTime)>34915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<36010 && Math.abs(serverTimeStamp-prevEventTime)>35915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<38010 && Math.abs(serverTimeStamp-prevEventTime)>37915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<39010 && Math.abs(serverTimeStamp-prevEventTime)>38915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<41010 && Math.abs(serverTimeStamp-prevEventTime)>40915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<43010 && Math.abs(serverTimeStamp-prevEventTime)>42915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<44010 && Math.abs(serverTimeStamp-prevEventTime)>43915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<45010 && Math.abs(serverTimeStamp-prevEventTime)>44915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<46010 && Math.abs(serverTimeStamp-prevEventTime)>45915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<71010 && Math.abs(serverTimeStamp-prevEventTime)>70915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<75010 && Math.abs(serverTimeStamp-prevEventTime)>74915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<77010 && Math.abs(serverTimeStamp-prevEventTime)>76915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<80010 && Math.abs(serverTimeStamp-prevEventTime)>79915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
    else if(Math.abs(serverTimeStamp-prevEventTime)<81010 && Math.abs(serverTimeStamp-prevEventTime)>80915)
    {
    //server timestamp is within 5 minutes of current system time
    	counter++;
    	t.setText("Action was long press  "+counter+"\n");
    } 
	//Log.i("Test", "Single tap up");
    return false;
}
public void audioPlayer(String path, String fileName){
    //set up MediaPlayer
     mp = new MediaPlayer();

    try {
        mp.setDataSource(path+"/"+fileName);
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
        mp.prepare();
    } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    mp.start();
}


//text to speech 
@Override
public void onInit(int status) {

    if (status == TextToSpeech.SUCCESS) {

        int result = tts.setLanguage(Locale.US);

        if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "This Language is not supported");
        } else {
            btnSpeak.setEnabled(true);
            speakOut();
        }

    } else {
        Log.e("TTS", "Initilization Failed!");
    }

}

private void speakOut() {

    String text = new String("Your score is "+counter+".");

    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
}
}



