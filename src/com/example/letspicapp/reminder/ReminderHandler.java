package com.example.letspicapp.reminder;

import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.letspicapp.db.ReminderDataSource;
import com.example.letspicapp.model.Alarm;
import com.example.letspicapp.receiver.AlarmReciever;

public class ReminderHandler {

	
	public boolean snooze(Alarm alarm, Context context) {
		long time = alarm.getTime();
		time += 9 * 60 * 1000;
		alarm.setTime(time);
		
		//update it in the db no update
//		ReminderDataSource dataSource = new ReminderDataSource(context);
//		dataSource.open();
//		dataSource.updateReminder(alarm);
//		dataSource.close();
//		
		return setAlarm(alarm, context);
	}
	
	public boolean scheduleAlarm(Alarm alarm,Context context){

		//save it to the db
		ReminderDataSource dataSource = new ReminderDataSource(context);
		dataSource.open();
		dataSource.createReminder(alarm);
		dataSource.close();
		
		return setAlarm(alarm,context);
	}
	
	public void setAllRemindersAfterReboot(Context context) {
		
		ReminderDataSource dataSource = new ReminderDataSource(context);
		dataSource.open();
		List<Alarm> alarms = dataSource.getAllReminders();
		long time = Calendar.getInstance().getTimeInMillis();
		for (Alarm alarm : alarms) {
			if(time < alarm.getTime())
				setAlarm(alarm, context);
		}
		dataSource.close();

	}
	
	private boolean setAlarm(Alarm alarm, Context context){


		//create the intent for the alarm later and put the variables in the intent
		Intent intentAlarm = new Intent(context, AlarmReciever.class);
//		intentAlarm.putExtra(Alarm.ID, alarm.getId());
//		intentAlarm.putExtra(, alarm.getImagePath());
//		intentAlarm.putExtra("time", alarm.getTime());
		intentAlarm.putExtras(alarm.toBundle());
		
		// create the object
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Log.d("Test", (int)alarm.getId() + " " + alarm.getId());
		// set the alarm for particular time TODO (int) id
		alarmManager.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), PendingIntent
				.getBroadcast(context, (int)alarm.getId(), intentAlarm,
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
