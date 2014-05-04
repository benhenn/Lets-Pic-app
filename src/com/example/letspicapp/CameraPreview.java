package com.example.letspicapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.letspicapp.technicalservices.Persistence;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraPreview extends Activity implements SurfaceHolder.Callback {
	private final static String TAG = "LetsPicAppDebug";
	private Camera mCamera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Button capture_image;

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
				startActivity(i);
				releaseCamera();
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.e("Surface Changed", "format   ==   " + format + ",   width  ===  "
				+ width + ", height   ===    " + height);
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
		} catch (IOException e) {
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