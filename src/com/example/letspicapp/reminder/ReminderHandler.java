package com.example.letspicapp.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.letspicapp.AlarmReciever;
import com.example.letspicapp.db.ReminderDataSource;
import com.example.letspicapp.model.Alarm;

public class ReminderHandler {

	
	
	public boolean scheduleAlarm(Alarm alarm, Context context){

		//save it to the db
		ReminderDataSource dataSource = new ReminderDataSource(context);
		dataSource.open();
		dataSource.createReminder(alarm);
		dataSource.close();

		//create the intent for the alarm later and put the variables in the intent
		Intent intentAlarm = new Intent(context, AlarmReciever.class);
//		intentAlarm.putExtra(Alarm.ID, alarm.getId());
//		intentAlarm.putExtra(, alarm.getImagePath());
//		intentAlarm.putExtra("time", alarm.getTime());
		intentAlarm.putExtras(alarm.toBundle());
		
		// create the object
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		// set the alarm for particular time
		alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), PendingIntent
				.getBroadcast(context, 0, intentAlarm,
						PendingIntent.FLAG_UPDATE_CURRENT));

		return true;
	}
	
	//singleton
	private static ReminderHandler instance = null;
	
	private ReminderHandler(){
		
	}
	
	public static ReminderHandler getInstance() {
		if (instance == null) {
			instance = new ReminderHandler();
		}
		return instance;
	}
	// singleton end
	
}
