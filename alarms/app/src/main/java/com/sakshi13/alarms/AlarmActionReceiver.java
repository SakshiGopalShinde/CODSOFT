package com.sakshi13.alarms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmActionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int notificationId = intent.getIntExtra("notificationId", 0);

        if ("SNOOZE".equals(action)) {
            Toast.makeText(context, "Snoozed", Toast.LENGTH_SHORT).show();
            // Implement snooze functionality
        } else if ("DISMISS".equals(action)) {
            Toast.makeText(context, "Dismissed", Toast.LENGTH_SHORT).show();
            // Implement dismiss functionality
        }
    }
}
