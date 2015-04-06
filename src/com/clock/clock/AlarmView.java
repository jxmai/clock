package com.clock.clock;

import java.util.Calendar;
import java.util.Date;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

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
		Calendar c = Calendar.getInstance();
		
		new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				
				Calendar currentTime = Calendar.getInstance();
				if(calendar.getTimeInMillis()<=currentTime.getTimeInMillis())
				{
					calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
				}
				adapter.add(new AlarmData(calendar.getTimeInMillis()));
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
	}
	
	private static class AlarmData
	{
		
		private long time = 0;
		private String timeLabel = "";
		private Calendar date;
		
		
		@SuppressWarnings("deprecation")
		public AlarmData(long time)
		{
			this.time = time;
			
			date = Calendar.getInstance();
			date.setTimeInMillis(time);
			this.timeLabel = String.format("%d-%d-%d:%d", date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE));
					
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
