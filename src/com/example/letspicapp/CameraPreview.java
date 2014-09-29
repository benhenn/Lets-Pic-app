package com.example.letspicapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letspicapp.technicalservices.Persistence;
import com.example.letspicapp.views.ReminderOverview;

public class CameraPreview extends Activity implements SurfaceHolder.Callback {
	private final static String TAG = "LetsPicAppDebug";
	private static final int PICK_IMAGE = 1;
	private Camera mCamera = null;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Button capture_image, open_gallery, reminderList;

	@SuppressWarnings("deprecation")
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
		open_gallery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				select();
			}

		});

		reminderList = (Button) findViewById(R.id.reminder);
		reminderList.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openReminderList();
			}

		});

		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(CameraPreview.this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		openCamera();
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
		openCamera();
	}

	// test
	private static File getOutputMediaFile() {
		// TODO check that the sdcard is mounted
//		 Environment.getExternalStorageState();
		
		File mediaStorageDir = Persistence.getInstance().getMediaStorageDir();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		Log.d(TAG, mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

		return mediaFile;
	}

	private void select() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
	}

	private void openReminderList() {
		Intent i = new Intent(this, ReminderOverview.class);
		startActivity(i);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if (requestCode == PICK_IMAGE) {

				File pictureFile = new File(Persistence.getInstance().getRealPathFromURI(data.getData(), this));
				Intent i = new Intent(CameraPreview.this, MainActivity.class);
				i.putExtra("name", pictureFile.getName());
				i.putExtra("path", pictureFile.getAbsolutePath());
				i.putExtra("fromGallery", true);// TODO workaround
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
				releaseCamera();

				Toast.makeText(getApplicationContext(), "Picture Taken", Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(CameraPreview.this, MainActivity.class);
				i.putExtra("name", pictureFile.getName());
				i.putExtra("path", pictureFile.getAbsolutePath());
				i.putExtra("fromGallery", false);
				startActivity(i);
			}
		});
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d("Surface Changed", "format   ==   " + format + ",   width  ===  " + width + ", height   ===    " + height);
		try {
			Parameters parameters = mCamera.getParameters();
			List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
			List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes(); // picture size must have same ratio

			Camera.Size previewSize = getOptimalPreviewSize(previewSizes, height, width);

			parameters.setPreviewSize(previewSize.width, previewSize.height);
			parameters.setPictureSize(pictureSizes.get(1).width, pictureSizes.get(1).height);
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			parameters.setRotation(90);
			mCamera.setParameters(parameters);

			mCamera.startPreview();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) h / w;

		if (sizes == null)
			return null;

		Camera.Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;

		int targetHeight = h;

		for (Camera.Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}

		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Camera.Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.setDisplayOrientation(90);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.e("Surface Destroyed", "");
	}

	@Override
	public void onPause() {
		
		super.onPause();
//		mCamera.stopPreview();
		releaseCamera();
	}

	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
			mUnexpectedTerminationHelper.fini();
		}
	}
	
	private void openCamera() {
		if (mCamera == null) {
			mUnexpectedTerminationHelper.init();
			mCamera = Camera.open();
		}
	}
	
	
	/* class to avoid that in case of an error the camera is still released */
	
	private UnexpectedTerminationHelper mUnexpectedTerminationHelper = new UnexpectedTerminationHelper();
	
	private class UnexpectedTerminationHelper {
	    private Thread mThread;
	    private Thread.UncaughtExceptionHandler mOldUncaughtExceptionHandler = null;
	    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
	        @Override
	        public void uncaughtException(Thread thread, Throwable ex) { // gets called on the same (main) thread
	            releaseCamera(); // TODO: write appropriate code here
	            if(mOldUncaughtExceptionHandler != null) {
//	            	Log.e("UnexpectedTerminationHelper", ex.printStackTrace());
	                // it displays the "force close" dialog
	                mOldUncaughtExceptionHandler.uncaughtException(thread, ex);
	            }
	        }
	    };
	    void init() {
	        mThread = Thread.currentThread();
	        mOldUncaughtExceptionHandler = mThread.getUncaughtExceptionHandler();
	        mThread.setUncaughtExceptionHandler(mUncaughtExceptionHandler);
	    }
	    void fini() {
	        mThread.setUncaughtExceptionHandler(mOldUncaughtExceptionHandler);
	        mOldUncaughtExceptionHandler = null;
	        mThread = null;
	    }
	}

}