package com.example.letspicapp.views;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

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

	    List<Alarm> alarms = datasource.getAllAlarms();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
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
