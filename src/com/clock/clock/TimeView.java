package com.clock.clock;

import java.util.Calendar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout 
{
	private TextView tvTime;
	
	public TimeView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
	}

	
	public TimeView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public TimeView(Context context) 
	{
		super(context);
	}
	
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		
		this.tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setText("HELLO Time");
		
		timeHandler.sendEmptyMessage(0);
	}
	
	protected void onVisibilityChanged(View changedView, int visibility)
	{
		super.onVisibilityChanged(changedView, visibility);
		
		if(visibility==View.VISIBLE)
		{
			timeHandler.sendEmptyMessage(0);
		}
		else
		{
			timeHandler.removeMessages(0);
		}
	}
	
	private void refreshTime()
	{
		Calendar c = Calendar.getInstance();
		
		tvTime.setText(String.format("%d:%d:%d", c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND)));
	}
	
	private Handler timeHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			refreshTime();
			
			if(getVisibility()==View.VISIBLE)
			{
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
		};
	};
}
