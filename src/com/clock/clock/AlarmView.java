package com.clock.clock;

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AlarmView extends LinearLayout 
{	
	private Button btnAddAlarm;
	private ListView lvAlarmlist;
	private ArrayAdapter<AlarmData> adapter;
	
	public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
	}

	public AlarmView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
	}

	public AlarmView(Context context) 
	{
		super(context);
	}
	
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		
		this.btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		this.lvAlarmlist = (ListView) findViewById(R.id.lvAlarmList);
		
		adapter = new ArrayAdapter<AlarmView.AlarmData>(getContext(),android.R.layout.simple_list_item_1);
		this.lvAlarmlist.setAdapter(adapter);
		
		adapter.add(new AlarmData(System.currentTimeMillis()));
		
		
		btnAddAlarm.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				addAlarm();
			}
		});
	}
	
	private void addAlarm()
	{
		//TODO
	}
	
	private static class AlarmData
	{
		
		private long time = 0;
		private String timeLabel = "";
		private Date date;
		
		
		@SuppressWarnings("deprecation")
		public AlarmData(long time)
		{
			this.time = time;
			
			date = new Date(time);
			this.timeLabel = date.getHours() + ":" + date.getMinutes();
		}
		
		public long getTime()
		{
			return this.time;
		}
		
		public String toString()
		{
			return getTimeLabel();
		}
		
		public String getTimeLabel()
		{
			return timeLabel;
		}
	}

}
