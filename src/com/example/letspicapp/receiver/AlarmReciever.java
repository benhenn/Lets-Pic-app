package com.example.letspicapp.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.letspicapp.R;
import com.example.letspicapp.views.ImageReminder;

public class AlarmReciever extends BroadcastReceiver {
	
	static int notId = 1;
	
	private static synchronized int getNotId(){
		Log.d("AlarmReceiver","notification id " + notId);
		return notId++;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent resultIntent = new Intent(context, ImageReminder.class);
		resultIntent.putExtras(intent);
		resultIntent.setAction(Long.toString(System.currentTimeMillis()));
	    PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)(System.currentTimeMillis()), resultIntent, 0);
	    
	    NotificationCompat.Builder notification = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("LetsPicApp")
				.setContentText("Click me")
				.setAutoCancel(true)
				.setContentIntent(pendingIntent)
				.setVibrate(new long[]{0,1000,300,1000});	    

	    NotificationManager mNotifyMgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	    
	    mNotifyMgr.notify(getNotId(), notification.build());
		
		
		
		
//		//build the notification
//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//				context).setSmallIcon(R.drawable.ic_launcher)
//				.setContentTitle("LetsPicApp")
//				.setContentText("Click me")
//				.setDefaults(Notification.DEFAULT_ALL)
//				.setAutoCancel(true);
//		
//		//forward intent
//		Intent resultIntent = new Intent(context, ImageReminder.class);
//		resultIntent.putExtras(intent);
//		int intentId = getNotId();
//		resultIntent.putExtra("intentId", intentId);
//		resultIntent.setAction(Long.toString(System.currentTimeMillis()));
//		resultIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		Log.d("AlarmReceiver", "Alarm ID " + intentId + new Alarm(intent.getExtras()).toString());
//		
//		// Because clicking the notification opens a new ("special") activity,
//		// there's
//		// no need to create an artificial back stack.
//		PendingIntent resultPendingIntent = PendingIntent.getActivity(context, intentId, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		
//		mBuilder.setContentIntent(resultPendingIntent);
//		// Sets an ID for the notification
////		int mNotificationId = 001;
//		// Gets an instance of the NotificationManager service
//		NotificationManager mNotifyMgr = (NotificationManager) context
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		// Builds the notification and issues it.
//		mNotifyMgr.notify(intentId, mBuilder.build());
//		Log.d("AlarmReceiver", "Intent ID" +resultIntent.getExtras().getInt("intentId"));
////		WakeLock screenOn = ((PowerManager) context
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
