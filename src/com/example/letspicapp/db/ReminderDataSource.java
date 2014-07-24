package com.example.letspicapp.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.letspicapp.model.Alarm;
/**
 * DAO
 * @author tendlich
 *
 */
public class ReminderDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHandler dbHandler;
	private String[] allColumns = { /* DatabaseHandler.KEY_ID,*/ DatabaseHandler.COLUMN_DATE, DatabaseHandler.COLUM_PATH,  DatabaseHandler.COLUMN_IS_ALARM };
	
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
		 long id = database.insert(DatabaseHandler.TABLE_REMINDER, null, values);
		 alarm.setId(id);
	 }
	 
	 public List<Alarm> getAllReminders(){
		 List<Alarm> alarms = new ArrayList<Alarm>();
		 Cursor cursor = database.query(DatabaseHandler.TABLE_REMINDER,
				 allColumns , null , null, null, null, null);

		 cursor.moveToFirst();
		 while (!cursor.isAfterLast()) {
			 Alarm alarm = new Alarm(cursor.getLong(0),cursor.getString(1));
			 alarms.add(alarm);
			 cursor.moveToNext();
		 }
		 cursor.close();
		 return alarms;
	 }
	 
	 public List<Alarm> getAllAlarms(){
//		 createReminder(new Alarm(Calendar.getInstance(),"HelloWorld"));
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
		 cursor.close();
		 return alarms;
	}

}
