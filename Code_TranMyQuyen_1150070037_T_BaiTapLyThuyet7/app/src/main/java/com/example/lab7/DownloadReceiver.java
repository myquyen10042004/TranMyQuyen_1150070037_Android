package com.example.lab7;

import android.content.*;
import android.os.Build;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Toast.makeText(context, "Action: " + action, Toast.LENGTH_SHORT).show();
        Intent service = new Intent(context, DownloadService.class);
        service.putExtra("action", action);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service);
        } else {
            context.startService(service);
        }
    }
}