package com.example.letspicapp.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.letspicapp.model.Alarm;

public class ReminderDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHandler dbHandler;
	private String[] allColumns = { /* DatabaseHandler.KEY_ID,*/ DatabaseHandler.COLUMN_DATE, DatabaseHandler.COLUM_PATH, DatabaseHandler.COLUMN_IS_ALARM };
	
	 public ReminderDataSource(Context context) {
		 dbHandler = new DatabaseHandler(context);
	 }

	 public void open() throws SQLException {
		 database = dbHandler.getWritableDatabase();
	 }

	 public void close() {
		 dbHandler.close();
	 }
	 
	 public void createReminder(Alarm alarm){
		 ContentValues values = new ContentValues();
		 values.put(DatabaseHandler.COLUMN_DATE, alarm.getTime());
		 values.put(DatabaseHandler.COLUM_PATH, alarm.getImagePath());
		 values.put(DatabaseHandler.COLUMN_IS_ALARM, 1);
		 database.insert(DatabaseHandler.TABLE_REMINDER, null, values);
	 }
	 
	 public List<Alarm> getAllAlarms(){
		 createReminder(new Alarm(Calendar.getInstance(),"HelloWorld"));
		 List<Alarm> alarms = new ArrayList<Alarm>();
		 String whereClause = DatabaseHandler.COLUMN_IS_ALARM + " = 1";
		 Cursor cursor = database.query(DatabaseHandler.TABLE_REMINDER,
				 allColumns , null , null, null, null, null);

		 cursor.moveToFirst();
		 while (!cursor.isAfterLast()) {
			 Alarm alarm = new Alarm(cursor.getLong(0),cursor.getString(1));
			 alarms.add(alarm);
			 cursor.moveToNext();
		 }
		 // make sure to close the cursor
		 cursor.close();
		 return alarms;
	}

}
