package com.example.letspicapp.model;

import java.util.Calendar;

import android.os.Bundle;

public class Alarm {

	public final static String ID = "id";
	public final static String REMINDER_TIME = "time";
	public final static String IMAGE_PATH = "path";
	
	long id;
	long time;
	Calendar alarm = Calendar.getInstance();
	String imagePath;
	
	public Alarm(){
	}
	
	
	public Alarm(Calendar date, String imagePath){
		this.alarm = date;
		this.imagePath = imagePath;
	}
	
	public Alarm(long time, String imagePath){
		this.time = time;
		this.imagePath = imagePath;
	}
	
	public Alarm(Bundle extras) {
		this.id = extras.getLong(ID);
		this.time = extras.getLong(REMINDER_TIME);
		this.imagePath = extras.getString(IMAGE_PATH);
	}


	public void setAlarmTime(int hour,int minute){
		alarm.set(Calendar.HOUR_OF_DAY, hour);
		alarm.set(Calendar.MINUTE, minute);
	}
	
	public void setAlarmDate(int year,int month, int day){
		alarm.set(Calendar.YEAR, year);
		alarm.set(Calendar.MONTH, month);
		alarm.set(Calendar.DAY_OF_MONTH, day);
	}
	
	public long getId() {
		return id;
	}
	
	public long getTime() {
		return alarm.getTimeInMillis();
//		return time;
	}
	
	public String getImagePath() {
		return imagePath;
	}	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return imagePath + " Time: " + alarm.get(Calendar.DAY_OF_MONTH);
	}
	
	public Bundle toBundle(){
		Bundle bundle = new Bundle();
		bundle.putLong(ID, id);
		bundle.putLong(REMINDER_TIME, time);
		bundle.putString(IMAGE_PATH, imagePath);
		return bundle;
	}


	public void setId(long id) {
		if(this.id == 0)
			this.id = id;
	}


	public void setPath(String path) {
		this.imagePath = path;
	}

	
	public void setTime(long time) {
		this.alarm.setTimeInMillis(time);		
	}
	
}
