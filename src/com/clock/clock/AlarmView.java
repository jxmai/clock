package com.clock.clock;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout 
{	
	private Button btnAddAlarm;
	private ListView lvAlarmList;
	private static final String KEY_ALARM_LIST = "alarmList";
	private ArrayAdapter<AlarmData> adapter;
	private AlarmManager alarmManager;
	
	public AlarmView(Context context, AttributeSet attrs, int defStyleAttr) 
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public AlarmView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		init();
	}

	public AlarmView(Context context) 
	{
		super(context);
		init();
	}
	
	private void init()
	{
		alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
	}
	
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		
		this.btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		this.lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);
		
		adapter = new ArrayAdapter<AlarmView.AlarmData>(getContext(),android.R.layout.simple_list_item_1);
		this.lvAlarmList.setAdapter(adapter);
		readSavedAlarmList();
		
		
		btnAddAlarm.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				addAlarm();
			}
		});
		
		lvAlarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				new AlertDialog.Builder(getContext()).setTitle("options").setItems(new CharSequence[]{"delete"}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch(which){
						case 0:
							deleteAlarm(position);
							break;
							
						default:
							break;
						}
					}
				}).setNegativeButton("cancel", null).show();
				
				return false;
			}
			
		});
	}
	
	private void deleteAlarm(int position)
	{
		adapter.remove(adapter.getItem(position));
		saveAlarmList();
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
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 5*60*1000, PendingIntent.getBroadcast(getContext(), 0, new Intent(getContext(),AlarmReceiver.class), 0));
				saveAlarmList();
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
	}
	
	private void saveAlarmList()
	{
		Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i< adapter.getCount();i++)
		{
			sb.append(adapter.getItem(i).getTime()).append(",");
		}
		
		String content = sb.toString().substring(0,sb.length()-1);
		
		editor.putString(this.KEY_ALARM_LIST, content);
		
		System.out.println(content);
		editor.commit();
	}
	
	
	private void readSavedAlarmList()
	{
		SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content= sp.getString(this.KEY_ALARM_LIST, null);
		
		if(content!=null)
		{
			String[] timeStrings = content.split(",");
			for(String string: timeStrings)
			{
				adapter.add(new AlarmData(Long.parseLong(string)));
			}
		}
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
