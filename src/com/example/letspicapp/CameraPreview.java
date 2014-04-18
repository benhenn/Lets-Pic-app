package com.example.letspicapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraPreview extends Activity implements SurfaceHolder.Callback {

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

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"LetsPicApp");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("LetsPicApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm")
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	private void capture() {
		mCamera.takePicture(null, null, null, new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				mCamera.stopPreview();
				File pictureFile = getOutputMediaFile();
				try {
					FileOutputStream fos = new FileOutputStream(pictureFile);
					fos.write(data);
					fos.close();
				} catch (FileNotFoundException e) {
					Log.d("LetsPicApp", "File not found: " + e.getMessage());
				} catch (IOException e) {
					Log.d("LetsPicApp", "Error accessing file: " + e.getMessage());
				}
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
						Uri.fromFile(pictureFile)));
				Toast.makeText(getApplicationContext(), "Picture Taken",
						Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(CameraPreview.this,MainActivity.class);
				startActivity(i.putExtra("name", pictureFile.getName()).putExtra("path", pictureFile.getAbsolutePath()));
				
//				mCamera.startPreview();
				// String PATH = "/" +
				// "mnt/external_sd/test/";
				// try {
				// FileOutputStream fos=new FileOutputStream(PATH);
				//
				// fos.write(data);
				// fos.close();
				// }
				// catch (java.io.IOException e) {
				//
				// }
				// Intent intent = new Intent();
				// intent.putExtra("image_arr", PATH);
				// setResult(RESULT_OK, intent);
				// camera.stopPreview();
				 releaseCamera();
				// finish();
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

// @SuppressLint("NewApi")
// public class CameraPreview extends SurfaceView implements
// SurfaceHolder.Callback
// {
// private final String TAG = "CameraPreview";
// private SurfaceHolder mHolder;
// private Camera mCamera;
// private List<Size> mSupportedPreviewSizes;
// private Size mPreviewSize;
//
// public CameraPreview(Context context, Camera camera)
// {
// super(context);
// mCamera = camera;
//
// // Install a SurfaceHolder.Callback so we get notified when the
// // underlying surface is created and destroyed.
// mHolder = getHolder();
// mHolder.addCallback(this);
// //deprecated setting, but required on Android versions prior to 3.0
// mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
// mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
// }
//
// public void surfaceCreated(SurfaceHolder holder)
// {
// //the Surface has been created, now tell the camera where to draw the preview
// try
// {
//
//
// mCamera.setDisplayOrientation(0);
// mCamera.setPreviewDisplay(holder);
// Camera.Parameters parameters = mCamera.getParameters();
// List<Size> sizes = parameters.getSupportedPictureSizes();
// parameters.setPictureSize(sizes.get(0).width, sizes.get(0).height); // mac
// dinh solution 0
// parameters.set("orientation","portrait");
// //parameters.setPreviewSize(viewWidth, viewHeight);
// List<Size> size = parameters.getSupportedPreviewSizes();
// parameters.setPreviewSize(size.get(0).width, size.get(0).height);
// mCamera.setParameters(parameters);
// mCamera.startPreview();
// }
// catch(IOException e)
// {
// Log.d(TAG, "Error setting camera preview: " + e.getMessage());
// }
// }
//
// public void surfaceDestroyed(SurfaceHolder holder)
// {
// //empty. take care of releasing the Camera preview in your activity
// }
//
// public void surfaceChanged(SurfaceHolder holder, int format, int width, int
// height)
// {
// //if your preview can change or rotate, take care of those events here.
// //make sure to stop the preview before resizing of reformatting it.
// if(mHolder.getSurface() == null)
// {
// //preview surface does not exist
// return;
// }
//
// //stop preview before making changes
// try
// {
// mCamera.stopPreview();
// }
// catch(Exception e)
// {
// //ignore: tried to stop a non-existent preview
// }
//
// //set preview size and make any resize, rotate or
// //reformatting changes here
// try
// {
// //start preview with new settings
// mCamera.setPreviewDisplay(mHolder);
// mCamera.getParameters().setPreviewSize(this.getWidth(), this.getHeight());
// mCamera.startPreview();
// }
// catch(Exception e)
// {
// Log.d(TAG, "Error starting camera preview: " + e.getMessage());
// }
// }
//
// @Override
// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
// final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
// final int height = resolveSize(getSuggestedMinimumHeight(),
// heightMeasureSpec);
// setMeasuredDimension(width, height);
//
// if (mSupportedPreviewSizes != null) {
// mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
// }
// }
//
// private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int
// h) {
// final double ASPECT_TOLERANCE = 0.1;
// double targetRatio=(double)h / w;
//
// if (sizes == null) return null;
//
// Camera.Size optimalSize = null;
// double minDiff = Double.MAX_VALUE;
//
// int targetHeight = h;
//
// for (Camera.Size size : sizes) {
// double ratio = (double) size.width / size.height;
// if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
// if (Math.abs(size.height - targetHeight) < minDiff) {
// optimalSize = size;
// minDiff = Math.abs(size.height - targetHeight);
// }
// }
//
// if (optimalSize == null) {
// minDiff = Double.MAX_VALUE;
// for (Camera.Size size : sizes) {
// if (Math.abs(size.height - targetHeight) < minDiff) {
// optimalSize = size;
// minDiff = Math.abs(size.height - targetHeight);
// }
// }
// }
// return optimalSize;
// }
// }