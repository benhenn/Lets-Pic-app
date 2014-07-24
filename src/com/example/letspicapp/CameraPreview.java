package com.example.letspicapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letspicapp.db.ReminderDataSource;
import com.example.letspicapp.technicalservices.Persistence;
import com.example.letspicapp.views.ReminderOverview;

public class CameraPreview extends Activity implements SurfaceHolder.Callback {
	private final static String TAG = "LetsPicAppDebug";
	private static final int PICK_IMAGE = 1;
	private Camera mCamera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Button capture_image,open_gallery,reminderList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.camera_layout);
		capture_image = (Button) findViewById(R.id.capture_image);
		capture_image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				capture();
			}
		});
		
		open_gallery = (Button) findViewById(R.id.open_gallery);
		open_gallery.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				select();
			}

		});
		
		reminderList = (Button) findViewById(R.id.reminder);
		reminderList.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				openReminderList();
			}

		});
		
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(CameraPreview.this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
			Camera.Parameters p = mCamera.getParameters(); 
			p.set("orientation", "portrait");
			p.setRotation(90);
			
			mCamera.setParameters(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		releaseCamera();
	}

	@Override
	protected void onResume() {
		super.onResume();
		releaseCamera();
		surfaceHolder.removeCallback(CameraPreview.this);
		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(CameraPreview.this);
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
			Camera.Parameters p = mCamera.getParameters(); 
			p.set("orientation", "portrait");
			p.setRotation(90);
			mCamera.setParameters(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// test
	private static File getOutputMediaFile() {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = Persistence.getInstance().getMediaStorageDir();

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm")
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		Log.d(TAG, mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		
		return mediaFile;
	}
	
	private void select() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
	}
	
	private void openReminderList(){
		Intent i = new Intent(this, ReminderOverview.class);
		startActivity(i);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { 

	        if (resultCode == RESULT_OK) {

	                if (requestCode == PICK_IMAGE) {

	                        File pictureFile = new File(Persistence.getInstance().getRealPathFromURI(data.getData(),this));
	                        Intent i = new Intent(CameraPreview.this,MainActivity.class);
	        				i.putExtra("name", pictureFile.getName());
	        				i.putExtra("path", pictureFile.getAbsolutePath());
	        				i.putExtra("fromGallery", true);//TODO workaround
	        				startActivity(i);
	        				releaseCamera();
	                }
	        }
	}
	
	private void capture() {
		mCamera.takePicture(null, null, null, new Camera.PictureCallback() {

			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				mCamera.stopPreview();
				File pictureFile = getOutputMediaFile();

				Persistence.getInstance().saveData(CameraPreview.this.getApplicationContext(), data, pictureFile);

				Toast.makeText(getApplicationContext(), "Picture Taken",
						Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(CameraPreview.this,MainActivity.class);
				i.putExtra("name", pictureFile.getName());
				i.putExtra("path", pictureFile.getAbsolutePath());
				i.putExtra("fromGallery", false);
				startActivity(i);
				releaseCamera();
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
//		Log.e("Surface Changed", "format   ==   " + format + ",   width  ===  "
//				+ width + ", height   ===    " + height);
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e("Surface Created", "");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("Surface Destroyed", "");
	}

	@Override
	public void onPause() {
		super.onPause();
		// mCamera.stopPreview();
		releaseCamera();
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

}