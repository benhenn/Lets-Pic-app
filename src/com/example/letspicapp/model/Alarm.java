package com.example.letspicapp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;

public class Alarm {

	public final static String ID = "id";
	public final static String REMINDER_TIME = "time";
	public final static String IMAGE_PATH = "path";
	public final static String NAME = "name";
	
	private long id;
	private long time;
//	Calendar alarm = Calendar.getInstance();
	private String imagePath;
	private String name;
	
	public Alarm(){
		
	}
	
	
	public Alarm(Calendar date, String imagePath, String name){
		this.time = date.getTimeInMillis();
		this.imagePath = imagePath;
	}
	
	public Alarm(long time, String imagePath, String name){
		this.time = time;
		this.imagePath = imagePath;
		this.name = name;
	}
	
	public Alarm(Bundle extras) {
		this.id = extras.getLong(ID);
		this.time = extras.getLong(REMINDER_TIME);
		this.imagePath = extras.getString(IMAGE_PATH);
		this.name = extras.getString(NAME);
	}

//	public void setAlarmTime(int hour,int minute){
//		Calendar
//		time.set(Calendar.HOUR_OF_DAY, hour);
//		time.set(Calendar.MINUTE, minute);
//	}
//	
//	public void setAlarmDate(int year,int month, int day){
//		time.set(Calendar.YEAR, year);
//		time.set(Calendar.MONTH, month);
//		time.set(Calendar.DAY_OF_MONTH, day);
//	}
	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm dd-MM-yy");
		// TODO Auto-generated method stub
		return name + " - Time: " + format1.format(cal.getTime());
	}
	
	/*******Getter and setter*/
	public long getId() {
		return id;
	}
	
	
	public long getTime() {
		return time;
	}
	
	
	public void setId(long id) {
		if(this.id == 0)
			this.id = id;
	}

	
	public void setPath(String path) {
		this.imagePath = path;
	}

	
	public void setTime(long time) {
		this.time = time;		
	}
	
	
	public void setTime(Calendar calendar){
		this.time = calendar.getTimeInMillis();		
	}

	
	public String getImagePath() {
		return imagePath;
	}	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/************************/ 
	
	public Bundle toBundle(){
		Bundle bundle = new Bundle();
		bundle.putLong(ID, id);
		bundle.putLong(REMINDER_TIME, time);
		bundle.putString(IMAGE_PATH, imagePath);
		bundle.putString(NAME, name);
		return bundle;
	}



	
}
