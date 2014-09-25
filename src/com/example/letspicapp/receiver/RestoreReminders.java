package com.example.letspicapp.receiver;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.letspicapp.MainActivity;
import com.example.letspicapp.model.Alarm;
import com.example.letspicapp.reminder.ReminderHandler;

public class RestoreReminders extends Service{

	 @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
		 ReminderHandler.getInstance().setAllRemindersAfterReboot(getBaseContext());

		 //TEST
		 Calendar c = Calendar.getInstance();
		 c.setTimeInMillis(c.getTimeInMillis() + (1 * 60 * 100));
		 Alarm alarm1 = new Alarm(c, "mnt/sdcard/Pictures/LetsPicApp/IMG_1.jpg", "IMG_1.jpg");
		 ReminderHandler.getInstance().scheduleAlarm(alarm1, getApplicationContext());
		 c.setTimeInMillis(c.getTimeInMillis() + (1 * 60 * 100));
		 Alarm alarm2 = new Alarm(c, "mnt/sdcard/Pictures/LetsPicApp/IMG_2.jpg", "IMG_2.jpg");
		 ReminderHandler.getInstance().scheduleAlarm(alarm2, getApplicationContext());
		 
		 
		 return Service.START_NOT_STICKY;
	  }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
