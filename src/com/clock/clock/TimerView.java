package com.clock.clock;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TimerView extends LinearLayout {

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerView(Context context) {
		super(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		this.btnStart = (Button) findViewById(R.id.btnStart);
		this.btnPause = (Button) findViewById(R.id.btnPause);
		this.btnResume = (Button) findViewById(R.id.btnResume);
		this.btnReset = (Button) findViewById(R.id.btnReset);
		
		
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
			}
		});
		
		this.etHour = (EditText) findViewById(R.id.etHour);
		this.etMin = (EditText) findViewById(R.id.etMin);
		this.etSec = (EditText) findViewById(R.id.etSec);
		
		etHour.setText("00");
		etHour.addTextChangedListener(new TextWatcher(){

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s))
				{
					int value = Integer.parseInt(s.toString()) ;
					
					if (value > 59)
					{
						etHour.setText("59");
					}else if (value < 0)
					{
						etHour.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}});	
		etMin.setText("00");
		etMin.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if(!TextUtils.isEmpty(s))
				{
					int value = Integer.parseInt(s.toString()) ;
					
					if (value > 59)
					{
						etMin.setText("59");
					}else if (value < 0)
					{
						etMin.setText("0");
					}
				}
				checkToEnableBtnStart();
			}});
		
		etSec.setText("00");
		etSec.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s))
				{
					int value = Integer.parseInt(s.toString()) ;
					
					if (value > 59)
					{
						etSec.setText("59");
					}else if (value < 0)
					{
						etSec.setText("0");
					}
				}
				checkToEnableBtnStart();
			}});
		
		btnStart.setVisibility(View.VISIBLE);
		btnStart.setEnabled(false);
		btnPause.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
		btnReset.setVisibility(View.GONE);
	}
	
	private void checkToEnableBtnStart()
	{
		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText()))&& Integer.parseInt(etHour.getText().toString())>0 || 
				(!TextUtils.isEmpty(etMin.getText()))&&Integer.parseInt(etMin.getText().toString())>0 ||
				(!TextUtils.isEmpty(etSec.getText()))&&Integer.parseInt(etSec.getText().toString())>0);
	}
	
	private void startTimer()
	{
		if(null == timer)
		{
			allTimerCount = Integer.parseInt(etHour.getText().toString())* 60*60 + Integer.parseInt(etMin.getText().toString())*60 + Integer.parseInt(etSec.getText().toString());
			timerTask = new TimerTask(){

				@Override
				public void run() {
					allTimerCount--;
					
					if(allTimerCount <= 0)
					{
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);
						stopTimer();
					}
				}
			};
			
			timer.schedule(timerTask, 1000, 1000);
			
			btnStart.setVisibility(View.GONE);
			btnPause.setVisibility(View.VISIBLE);
			btnReset.setVisibility(View.VISIBLE);
			
		}
	}
	
	private void stopTimer()
	{
		if(null != timerTask)
		{
			timerTask.cancel();
			timerTask = null;
		}
	}
	
	private Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case MSG_WHAT_TIME_IS_UP:
				new AlertDialog.Builder(getContext()).setTitle("Time is up").setMessage("Time is up").setNegativeButton("Cancel", null).show();
				break;
			default:
				break;
			}
		}
	};
	
	
	private static final int MSG_WHAT_TIME_IS_UP = 1;
	private int allTimerCount = 0;
	private Timer timer = new Timer();
	private TimerTask timerTask = null;
	private Button btnStart, btnPause, btnResume, btnReset;
	private EditText etHour, etMin, etSec;
	

}
	