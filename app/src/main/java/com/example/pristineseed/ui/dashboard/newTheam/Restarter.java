package com.example.pristineseed.ui.dashboard.newTheam;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Restarter extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Log.e("Broadcast Listened", "Service tried to stop");

        if (!isServiceRunning()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, ServiceBg.class));
            } else {
                context.startService(new Intent(context, ServiceBg.class));
            }
        } else {
            Log.e("else Listened", "Service tried to stop");

        }
    }

    public boolean isServiceRunning() {
        Class<?> serviceClass = ServiceBg.class;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}