package com.example.letspicapp;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class ImageReminder extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_reminder);
		ImageView iV = (ImageView)findViewById(R.id.imageView42);
		iV.setImageBitmap(getBitmap(getIntent().getExtras().getString("path")));
	}
	
	 public Bitmap getBitmap(String path){
	        Bitmap imgthumBitmap=null;
	         try    
	         {

	             final int THUMBNAIL_SIZE = 400;

	             FileInputStream fis = new FileInputStream(path);
	              imgthumBitmap = BitmapFactory.decodeStream(fis);

	             imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
	                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

	            ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream(); 
	            imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytearroutstream);


	         }
	         catch(Exception ex) {
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

}
