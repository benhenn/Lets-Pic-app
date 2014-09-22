package com.example.letspicapp.receiver;

import com.example.letspicapp.R;
import com.example.letspicapp.R.drawable;
import com.example.letspicapp.views.ImageReminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;

public class AlarmReciever extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		//build the notification
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("LetsPicApp")
				.setContentText("Click me")
				.setDefaults(Notification.DEFAULT_ALL);
		
		//forward intent
		Intent resultIntent = new Intent(context, ImageReminder.class);
		resultIntent.putExtras(intent);

		// Because clicking the notification opens a new ("special") activity,
		// there's
		// no need to create an artificial back stack.
		PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
				0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);
		// Sets an ID for the notification
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
//		WakeLock screenOn = ((PowerManager) context
//				.getSystemService(Context.POWER_SERVICE)).newWakeLock(
//				PowerManager.SCREEN_BRIGHT_WAKE_LOCK
//						| PowerManager.ACQUIRE_CAUSES_WAKEUP, "example");
//		screenOn.acquire();
//		// TODO Auto-generated method stub
		// long id = intent.getLongExtra("id", 0);
		// // String msg = intent.getStringExtra("msg");
		// String msg = "TestMsg!";
		// Notification n = new Notification(R.drawable.ic_launcher, msg,
		// System.currentTimeMillis());
		// PendingIntent pi = PendingIntent.getActivity(context, 0, new
		// Intent(), 0);
		//
		// n.setLatestEventInfo(context, "Remind Me", msg, pi);
		//
		// // TODO check user preferences
		// n.defaults |= No tification.DEFAULT_VIBRATE;
		// n.sound = Uri.parse( DEFAULT_NOTIFICATION_URI.toString());
		// // n.defaults |= Notification.DEFAULT_SOUND;
		// n.flags |= Notification.FLAG_AUTO_CANCEL;
		//
		// NotificationManager nm = (NotificationManager)
		// context.getSystemService(Context.NOTIFICATION_SERVICE);
		// nm.notify((int)id, n);
	}

}
