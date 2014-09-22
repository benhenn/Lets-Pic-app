package com.example.letspicapp.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.letspicapp.CameraPreview;
import com.example.letspicapp.R;
import com.example.letspicapp.model.Alarm;
import com.example.letspicapp.reminder.ReminderHandler;


/**
 * 
 * Screen 9 - Reminder
 * Screen 10 as a pop-up
 * 
 * @author tendlich
 *
 */
public class ImageReminder extends Activity {

	private Alarm alarm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		alarm = new Alarm(getIntent().getExtras());
		
		setContentView(R.layout.activity_image_reminder);		 
		
		ImageView iV = (ImageView) findViewById(R.id.imageView42);
		iV.setImageBitmap(getBitmap(alarm.getImagePath()));
		
		Button remindMeOn = (Button) findViewById(R.id.remindMeOn);
//		remindMeOn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				
//			}
//		});
	}
	
	public void onClick(View v){
		
	}

	public Bitmap getBitmap(String path) {
		Bitmap imgthumBitmap = null;
		try {
		       //Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(new FileInputStream(path),null,o);

	        //The new size we want to scale to
	        final int REQUIRED_SIZE=400;

	        //Find the correct scale value. It should be the power of 2.
	        int scale=1;
	        while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
	            scale*=2;

	        //Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize=scale;
	        return BitmapFactory.decodeStream(new FileInputStream(path), null, o2);
	    } catch (FileNotFoundException e) {}
	    return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_reminder, menu);
		return true;
	}
	
	public void snooze(View view){
		boolean alarmSet; 
		alarmSet = ReminderHandler.getInstance().snooze(alarm, this);
		if(alarmSet){
			Toast.makeText(this, "Snoozed for 9 minutes", Toast.LENGTH_LONG)
			.show();
		}
	}
	
	//next screen / pop-up
	public void ok(View view){
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setTitle("Title test");
		
		 LayoutInflater inflater = this.getLayoutInflater();

		    // Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		    builder.setView(inflater.inflate(R.layout.dialog_reminder, null));

		// 3. Get the AlertDialog from create()
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU){
			startActivity(new Intent(this, CameraPreview.class));
		}
		return super.onKeyDown(keyCode, event);
	}

}
