package com.example.letspicapp.views;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.example.letspicapp.R;
import com.example.letspicapp.technicalservices.Persistence;

/**
 * 
 * Screen 9 - Reminder
 * Screen 10 as a pop-up
 * 
 * @author tendlich
 *
 */
public class ImageReminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_reminder);
		
		//get the intent and build a new 
		ImageView iV = (ImageView) findViewById(R.id.imageView42);
		iV.setImageBitmap(getBitmap(getIntent().getExtras().getString("path")));
	}

	public Bitmap getBitmap(String path) {
		Bitmap imgthumBitmap = null;
		try {

			final int THUMBNAIL_SIZE = 400;

			imgthumBitmap = Persistence.getInstance().getImage(path);

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_reminder, menu);
		return true;
	}
	
	public void snooze(View view){
		//TODO 
		/*
		 * reschedule alarm for 5 mins or sth like that
		 */
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

}
