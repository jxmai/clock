package com.clock.clock;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends Activity 
{
	private TabHost tabHost;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tabHost = (TabHost) findViewById(android.R.id.tabhost); // link the view to the mainActivity
        tabHost.setup(); // initialize the tabHost
        
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("clock").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("alarm").setContent(R.id.tabAlarm));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("timer").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("stopwatch").setContent(R.id.tabStopWatch));
        
    } 
    
    
}
