package com.clock.clock;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class StopWatch extends LinearLayout {

	
	public StopWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		tvHour = (TextView) findViewById(R.id.timeHour);
		tvHour.setText("0");
		tvMin = (TextView) findViewById(R.id.timeMin);
		tvMin.setText("0");
		tvSec = (TextView) findViewById(R.id.timeSec);
		tvSec.setText("0");
		tvMSec = (TextView) findViewById(R.id.timeMSec);
		tvMSec.setText("0");
		
		btnLap = (Button) findViewById(R.id.btnStopWatchLap);
		btnLap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				adapter.insert(String.format("%d:%d:%d.%d", tenMSec/100/60/60, tenMSec/100/60%60, tenMSec/100%60, tenMSec%100), 0);
			}
		});
		
		btnPause = (Button) findViewById(R.id.btnStopWatchPause);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				stopTimer();
				
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
				
				btnLap.setVisibility(View.GONE);
				btnReset.setVisibility(View.VISIBLE);
				
			}
		});
		
		btnReset = (Button) findViewById(R.id.btnReset);
		btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopTimer();
				tenMSec = 0;
				
				adapter.clear();
				
				btnLap.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnReset.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
			}
		});
		
		btnResume = (Button) findViewById(R.id.btnStopWatchResume);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				
				btnReset.setVisibility(View.GONE);
				btnLap.setVisibility(View.VISIBLE);
			}
		});
		
		btnStart = (Button) findViewById(R.id.btnStopWatchStart);
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startTimer();
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnLap.setVisibility(View.VISIBLE);
			}
		});
		
		btnLap.setVisibility(View.GONE);
		btnPause.setVisibility(View.GONE);
		btnReset.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
		
		lvTimeList = (ListView) findViewById(R.id.lvWatchTime);
		adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
		lvTimeList.setAdapter(adapter);
		
		showTimeTask = new TimerTask(){

			@Override
			public void run() {
				handler.sendEmptyMessage(MSG_WHAT_SHOW_TIME);
			}
		};
		timer.schedule(showTimeTask, 200, 200);
	}
	
	private void startTimer()
	{
		if (null == timerTask)
		{
			timerTask = new TimerTask(){

				@Override
				public void run() {
					tenMSec++;
					
				}
			};
			timer.schedule(timerTask, 10, 10);
		}
	}
	
	private void stopTimer()
	{
		if(timerTask != null)
		{
			timerTask.cancel();
			timerTask = null;
		}
	}
	
	private int tenMSec = 0;
	private Timer timer = new Timer();
	private TimerTask timerTask = null;
	private TimerTask showTimeTask = null;
	
	
	
	private TextView tvHour, tvMin, tvSec, tvMSec;
	private Button btnStart, btnResume, btnReset, btnLap, btnPause;
	private ListView lvTimeList;
	private ArrayAdapter<String> adapter;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_WHAT_SHOW_TIME:
				tvHour.setText(tenMSec/100/60/60 + "");
				tvMin.setText(tenMSec/100/60%60 + "");
				tvSec.setText(tenMSec/100%60 + "");
				tvMSec.setText(tenMSec%100+"");
				break;
				
			default:
				break;
			
			}
			
		};
	};
	
	private static final int MSG_WHAT_SHOW_TIME = 1;

	public void onDestroy() {
		timer.cancel();
		
	}
}
