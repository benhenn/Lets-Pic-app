package com.example.letspicapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
    private Camera mCamera;
    private CameraPreview mPreview;
    private String path;
    private String name;
	
//	/** A safe way to get an instance of the Camera object. */
//	public static Camera getCameraInstance(){
//	    Camera c = null;
////	    int i = Camera.getNumberOfCameras();
//	    try {
//	        c = Camera.open(); // attempt to get a Camera instance
//	    }
//	    catch (Exception e){
//	    	 Log.d("bla", "Error starting camera preview: " + e.getMessage());
//	    }
//	    return c; // returns null if camera is unavailable
//	}
	
    public Bitmap getBitmap(String path){
        Bitmap imgthumBitmap=null;
         try    
         {

             final int THUMBNAIL_SIZE = 300;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = getIntent(); 
		name = i.getExtras().getString("name");
		path = i.getExtras().getString("path");
		EditText text = (EditText)findViewById(R.id.editText1);
		text.setText(name);
		text.setSelection(text.getText().length());
		ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageView.setImageBitmap(getBitmap(path));
		

//		NotificationCompat.Builder mBuilder =
//			    new NotificationCompat.Builder(this)
//			    .setSmallIcon(R.drawable.ic_launcher)
//			    .setContentTitle("My notification")
//			    .setContentText("Hello World!")
//			    .setDefaults(Notification.DEFAULT_ALL);
//		Intent resultIntent = new Intent(this, MainActivity.class);
//		
//		// Because clicking the notification opens a new ("special") activity, there's
//		// no need to create an artificial back stack.
//		PendingIntent resultPendingIntent =
//		    PendingIntent.getActivity(
//		    this,
//		    0,
//		    resultIntent,
//		    PendingIntent.FLAG_UPDATE_CURRENT
//		);
//		mBuilder.setContentIntent(resultPendingIntent);
//		// Sets an ID for the notification
//		int mNotificationId = 001;
//		// Gets an instance of the NotificationManager service
//		NotificationManager mNotifyMgr = 
//		        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//		// Builds the notification and issues it.
//		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		
//
//		    // create Intent to take a picture and return control to the calling application
//		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//		    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
//
//		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
//
//		    // start the image capture Intent
//		    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//				

//		   
//		    setContentView(R.layout.activity_preview);
//
//
//		        // Create an instance of Camera
//		        mCamera = getCameraInstance();
//
//		        // Create our Preview view and set it as the content of our activity.
//		        mPreview = new CameraPreview(this, mCamera);
////		        SurfaceView preview = (SurfaceView)  findViewById(R.id.surface_camera);
//		        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
//		        Log.d("bla", "position");
//		        preview.addView(mPreview);
//		        
//		        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotation);
////		        LayoutAnimationController animController = new LayoutAnimationController(rotateAnim, 0);
////		        Button layout = (Button)findViewById(R.id.button_capture);
////		        layout.startAnimation(rotateAnim);
//		        //		        layout.setAnimation(animController);
		    
		
//		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//		        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//		}
//		setContentView(R.layout.activity_main);

	}


//	/** Create a file Uri for saving an image or video */
//	private static Uri getOutputMediaFileUri(int type){
//	      return Uri.fromFile(getOutputMediaFile(type));
//	}
//
//	/** Create a File for saving an image or video */
//	private static File getOutputMediaFile(int type){
//	    // To be safe, you should check that the SDCard is mounted
//	    // using Environment.getExternalStorageState() before doing this.
//
//	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
//	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
//	    // This location works best if you want the created images to be shared
//	    // between applications and persist after your app has been uninstalled.
//
//	    // Create the storage directory if it does not exist
//	    if (! mediaStorageDir.exists()){
//	        if (! mediaStorageDir.mkdirs()){
//	            Log.d("MyCameraApp", "failed to create directory");
//	            return null;
//	        }
//	    }
//
//	    // Create a media file name
//	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//	    File mediaFile;
//	    if (type == MEDIA_TYPE_IMAGE){
//	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
//	        "IMG_"+ timeStamp + ".jpg");
//	    } else {
//	        return null;
//	    }
//
//	    return mediaFile;
//	}
//	   public void onPause()
//	    {
//	        super.onPause();
//	        mCamera.stopPreview();
//	        releaseCamera();
//	    }
//
//
//	    private void releaseCamera()
//	    {
//	        if(mCamera != null)
//	        {
//	            mCamera.release();
//	            mCamera = null;
//	        }
//	    }
//
//	    @Override
//	    public boolean onCreateOptionsMenu(Menu menu)
//	    {
//	        // Inflate the menu; this adds items to the action bar if it is present.
//	        getMenuInflater().inflate(R.menu.main, menu);
//	        return true;
//	    }
//	
//
//	
//
//	public void dispatchTakePictureIntent(View view) {
//	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//	    }
//	}
//	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
//		    super.onActivityResult(requestCode, resultCode, data);
//		 
//		//	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
////	        Bundle extras = data.getExtras();
////	        Bitmap imageBitmap = (Bitmap) extras.get("data");
////	        ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
////	        mImageView.setImageBitmap(imageBitmap);
////	    }
//	}

	 public void scheduleAlarm(View V)
	    {
	            // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time, 
	            // we fetch  the current time in milliseconds and added 1 day time
	            // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day        
	            Long time = new GregorianCalendar().getTimeInMillis()+5*1000;

	            // create an Intent and set the class which will execute when Alarm triggers, here we have
	            // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
	            // alarm triggers and 
	            //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
	            Intent intentAlarm = new Intent(this, AlarmReciever.class);
	            intentAlarm.putExtra("path", path);
	       
	            // create the object
	            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

	            //set the alarm for particular time
	            alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,0,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	            Toast.makeText(this, "Alarm Scheduled in 5 Seconds", Toast.LENGTH_LONG).show();
	         
	    }

}
