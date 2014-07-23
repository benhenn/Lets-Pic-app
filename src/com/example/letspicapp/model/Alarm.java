package com.example.letspicapp.model;

import java.util.Calendar;

public class Alarm {

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
//	
//	public Alarm(long time, String imagePath){
//		this.time = time;
//		this.imagePath = imagePath;
//	}
//	
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
	
}
