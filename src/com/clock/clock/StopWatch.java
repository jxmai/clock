package com.clock.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StopWatch extends LinearLayout {

	
	public StopWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
	}
	
	
	
	private TextView tvHour, tvMin, tvSec, tvMSec;
	private Button btnStart, btnResume, btnReset, btnLap, btnPause;
	
	

}
