package com.example.letspicapp.receiver;

import com.example.letspicapp.reminder.ReminderHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceAtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
    	
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
           ReminderHandler.getInstance().setAllRemindersAfterReboot(context);
        }
    }
}
