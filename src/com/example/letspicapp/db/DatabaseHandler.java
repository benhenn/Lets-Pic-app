package com.example.letspicapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "letspic";

	// Contacts table name
	static final String TABLE_REMINDER = "reminder";

	// Contacts Table Columns names
	static final String KEY_ID = "id";
	static final String COLUMN_DATE = "date";
	static final String COLUM_PATH = "path";
	static final String COLUMN_IS_ALARM = "is_alarm";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REMINDER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, "
				+ COLUM_PATH + " TEXT, " + COLUMN_IS_ALARM + " INTEGER "+ ");";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHandler.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);

		// Create tables again
		onCreate(db);
	}	
}
