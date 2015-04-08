package com.clock.clock;

import android.content.Context;
import android.util.AttributeSet;
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
		
		this.etHour = (EditText) findViewById(R.id.etHour);
		this.etMin = (EditText) findViewById(R.id.etMin);
		this.etSec = (EditText) findViewById(R.id.etSec);
	}
	
	private Button btnStart, btnPause, btnResume, btnReset;
	private EditText etHour, etMin, etSec;
	

}
	