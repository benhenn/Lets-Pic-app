package com.example.letspicapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.letspicapp.model.Alarm;
import com.example.letspicapp.reminder.ReminderHandler;
import com.example.letspicapp.technicalservices.Persistence;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Camera mCamera;
	private CameraPreview mPreview;
	private String path;
	private String name;
	private int mYear, mMonth, mDay, mHour, mMinute;
//	private Calendar alarm = Calendar.getInstance();
	private boolean fromGallery;
	private Alarm alarm;


	// /** A safe way to get an instance of the Camera object. */
	// public static Camera getCameraInstance(){
	// Camera c = null;
	// // int i = Camera.getNumberOfCameras();
	// try {
	// c = Camera.open(); // attempt to get a Camera instance
	// }
	// catch (Exception e){
	// Log.d("bla", "Error starting camera preview: " + e.getMessage());
	// }
	// return c; // returns null if camera is unavailable
	// }

	public Bitmap getBitmap(String path) {
		Bitmap imgthumBitmap = null;
		try {

			final int THUMBNAIL_SIZE = 300;

			FileInputStream fis = new FileInputStream(path);
			imgthumBitmap = BitmapFactory.decodeStream(fis);

			imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
					THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

			ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream();
			imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					bytearroutstream);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return imgthumBitmap;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent i = getIntent();
		
		//share menu
		if (Intent.ACTION_SEND.equals(i.getAction())) {   
			Bundle extras = i.getExtras();
			if (extras.containsKey(Intent.EXTRA_STREAM)) {
		    Uri uri = (Uri) extras.getParcelable(Intent.EXTRA_STREAM);
		    File pictureFile = new File(Persistence.getInstance().getRealPathFromURI(uri,this));
		    
		    if (pictureFile != null) {
		    	alarm.setName(pictureFile.getName());
		    	alarm.setPath(pictureFile.getAbsolutePath());
		    	fromGallery = true;
		    }
		  }
		}else{ 
			alarm.setName(i.getExtras().getString("name"));
			alarm.setPath(i.getExtras().getString("path"));
			fromGallery = i.getExtras().getBoolean("fromGallery");//TODO workaround
		}
		
		EditText text = (EditText) findViewById(R.id.editName);
		text.setText(Persistence.removeImageFileExtension(alarm.getName()));
		text.setSelection(text.getText().length());
		ImageView mImageView = (ImageView) findViewById(R.id.thumbnail);
		mImageView.setImageBitmap(getBitmap(alarm.getImagePath()));
	}
	
	private void setName(String name){
		this.name = name;
	}
	private void setPath(String path){
		this.path = path;
	}
	
	private void rename(){
		String newPath,newName;
		EditText editText = (EditText) findViewById(R.id.editName);
		newName = editText.getText().toString();
		Log.d("LetsPicAppDebug", "EditText: " + newName);
		newPath = Persistence.getInstance().renameImage(this.getApplicationContext(),alarm.getName(), newName);
		alarm.setName(newName);
		alarm.setPath(newPath);
	}
	
	private DatePickerDialog dateTimePicker(final boolean reminder){
		final Calendar c = Calendar.getInstance();
		final Calendar time = Calendar.getInstance();
		
		final TimePickerDialog tpd = new TimePickerDialog(this,
	        		new TimePickerDialog.OnTimeSetListener() {
	        	
	        	@Override
	        	public void onTimeSet(TimePicker view, int hourOfDay,
	        			int minute) {
	        		time.set(Calendar.HOUR_OF_DAY, hourOfDay);
	        		time.set(Calendar.MINUTE, minute);
	        		if(reminder)
	        			scheduleAlarm();
	        	}
	        }, c.get(Calendar.HOUR_OF_DAY),  c.get(Calendar.MINUTE), true);
	        
	        final DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){

				@Override
				public void onDateSet(DatePicker view,  int year, int monthOfYear, int dayOfMonth) {
					time.set(Calendar.YEAR, year);
					time.set(Calendar.MONTH, monthOfYear);
					time.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					tpd.show();
				}
	        	
	        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	      
	        alarm.setTime(time);
	        return dpd;
	}
	
	public void reminder(View v){
		if(!fromGallery) //TODO workaround
			this.rename();
		alarm = new Alarm();
        dateTimePicker(true).show();
	}
	
	public void deletePicture(View v){
		this.rename();
		boolean t = Persistence.getInstance().deleteImage(this.getApplicationContext(), alarm.getName());
		Log.d("LetsPicAppDebug", "Deletion: " + t);
	}
	
	public void scheduleAlarm() {		
		boolean alarmSet;
		alarmSet = ReminderHandler.getInstance().scheduleAlarm(alarm, this);
		
		if(alarmSet){
			Toast.makeText(this, "Alarm Scheduled", Toast.LENGTH_LONG)
			.show();
		}

	}
}
