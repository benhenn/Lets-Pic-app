package com.example.letspicapp.views;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.letspicapp.CameraPreview;
import com.example.letspicapp.MainActivity;
import com.example.letspicapp.R;
import com.example.letspicapp.db.ReminderDataSource;
import com.example.letspicapp.model.Alarm;

public class ReminderOverview extends ListActivity {
	
	 private ReminderDataSource datasource;
	 private List<Alarm> alarms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_overview);
	    datasource = new ReminderDataSource(this);
	    datasource.open();
	    display();
	    ListView lv = getListView();
	    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				
				Alarm alarm = alarms.get(position);
				
				Intent i = new Intent(ReminderOverview.this,MainActivity.class);
				i.putExtras(alarm.toBundle());
//				i.putExtra("name", alarm.getName());
//				i.putExtra("path", alarm.getImagePath());
				i.putExtra("fromGallery", false);
				startActivity(i);
				
			}
	    
		});
	    
	}

	public void test(View view) {
		display();
	}
	
	private void display(){
		RadioGroup rg = (RadioGroup) findViewById(R.id.displayReminders);
		
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
