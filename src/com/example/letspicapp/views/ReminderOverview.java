package com.example.letspicapp.views;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.example.letspicapp.R;
import com.example.letspicapp.db.ReminderDataSource;
import com.example.letspicapp.model.Alarm;

public class ReminderOverview extends ListActivity {
	
	 private ReminderDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_overview);

	    datasource = new ReminderDataSource(this);
	    datasource.open();
	    display();
	    
	}

	public void test(View view) {
		display();
	}
	
	private void display(){
		RadioGroup rg = (RadioGroup) findViewById(R.id.displayReminders);
		List<Alarm> alarms;
		switch (rg.getCheckedRadioButtonId()) {
			default:
			case R.id.displayAllReminders:
				alarms = datasource.getAllReminders();
				break;
	
			case R.id.displayAlarmReminders:
				alarms = datasource.getAllAlarms();
				break;
	
			case R.id.displayDeletionReminders:
				alarms = new ArrayList<Alarm>();
				break;
		}
		ArrayAdapter<Alarm> adapter = new ArrayAdapter<Alarm>(this,
		        android.R.layout.simple_list_item_1, alarms);
		setListAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reminder_overview, menu);
		return true;
	}

}
