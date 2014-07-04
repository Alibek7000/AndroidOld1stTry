package com.example.helsdas;

import java.util.Date;

import com.example.helloworld.R;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnCompletionListener {

	Button buttonStart;
	TextView tvResult;
	CheckBox checkBox1;
	CheckBox checkBox2;
	CheckBox checkBox3;
	boolean activated = false;
	Date startTime;
	Date endTime;
	Handler h;
	MediaPlayer mediaPlayer;

	private void releaseMP() {
		    if (mediaPlayer != null) {
		      try {
		        mediaPlayer.release();
		        mediaPlayer = null;
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
	}

	Handler.Callback hc = new Handler.Callback() {
		    public boolean handleMessage(Message msg) {
		    	if (msg.what == 1) {
		    	tvResult.setText("Сработал пробный будильник 1 мин.");
		    	mediaPlayer.start();
		    	}
		    	if (msg.what == 2) {
		    	tvResult.setText("Сработал будильник 6 ч. Запишите ваш сон. Ложитесь спать дальше.");
		    	mediaPlayer.start();
		    	}
		    	if (msg.what == 3) {
		    	tvResult.setText("Сработал будильник 7,5 ч. Запишите ваш сон. Ложитесь спать дальше. Если хотите!");
		    	mediaPlayer.start();
		    	}
		      return false;
		    }
	};
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        buttonStart = (Button) findViewById(R.id.button1);
        tvResult = (TextView) findViewById(R.id.textView2);
        checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
        
        buttonStart.setOnClickListener(this);
        h = new Handler(hc);
        
    }    
      
        void sendMessages(int i, long x) {
            h.sendEmptyMessageDelayed(i, x);            	
			}
          
    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	@Override
	public void onClick(View arg0) {
		
		releaseMP();
		mediaPlayer = MediaPlayer.create(this, R.raw.alarm_clock_bell_ringing);
    	mediaPlayer.setOnCompletionListener(this);
	    	
		if (!activated) {
			activated = true; // включаем 
			buttonStart.setTextKeepState("Стоп");
			tvResult.setText("Мы спим");
			startTime = new Date();
			checkBox1.setEnabled(false);
			checkBox2.setEnabled(false);
			checkBox3.setEnabled(false);
							
			if (checkBox1.isChecked()) {
				h.sendEmptyMessageDelayed(1, 60*1000);						
			}	
			if (checkBox2.isChecked()) {
				h.sendEmptyMessageDelayed(2, 6*60*60*1000);
			}	
			if (checkBox3.isChecked()) {
				h.sendEmptyMessageDelayed(3, 75*6*60*1000);
			}
		}
		
		else if (activated) {
			activated = false; //отжимаем кнопку
			buttonStart.setTextKeepState("Старт");
			tvResult.setText("Мы проснулись!");
			endTime = new Date();
			tvResult.setText(Count.doIt(startTime, endTime));
			checkBox1.setEnabled(true);
			checkBox2.setEnabled(true);
			checkBox3.setEnabled(true);
		    h.removeMessages(1);
		    h.removeMessages(2);			
		    h.removeMessages(3);
								
		}

}				

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub		
	}
}
