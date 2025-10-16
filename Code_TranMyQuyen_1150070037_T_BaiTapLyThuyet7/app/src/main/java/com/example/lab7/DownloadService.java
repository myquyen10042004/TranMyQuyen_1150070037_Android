package com.example.lab7;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import java.io.*;
import java.net.*;

public class DownloadService extends Service {
    public static final String CHANNEL_ID = "download_channel";
    private boolean isPaused = false, isCancelled = false;
    private String fileUrl;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DownloadService", "Service started");
        if (intent == null) return START_NOT_STICKY;
        if (intent.hasExtra("action")) handleAction(intent.getStringExtra("action"));
        else {
            fileUrl = intent.getStringExtra("url");
            createChannel();
            updateNotification(0); // Show initial notification immediately
            new Thread(this::downloadFile).start();
        }
        return START_STICKY;
    }

    private void handleAction(String action) {
        switch (action) {
            case "PAUSE": isPaused = true; break;
            case "RESUME": isPaused = false; break;
            case "CANCEL": isCancelled = true; stopSelf(); break;
        }
    }

    private void downloadFile() {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            int fileLength = conn.getContentLength();
            InputStream input = new BufferedInputStream(conn.getInputStream());
            String name = "file_" + System.currentTimeMillis() + ".bin";
            File outFile = new File(getExternalFilesDir(null), name);
            FileOutputStream output = new FileOutputStream(outFile);

            byte[] data = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled) break;
                while (isPaused) Thread.sleep(300);
                total += count;
                output.write(data, 0, count);
                int progress = (int) (total * 100 / fileLength);
                updateNotification(progress);
            }

            output.flush();
            output.close();
            input.close();

            stopSelf();
        } catch (Exception e) {
            Log.e("DownloadService", e.getMessage());
        }
    }

    private void updateNotification(int progress) {
        Log.d("DownloadService", "updateNotification called, progress: " + progress);

        // Tạo RemoteViews từ custom layout
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.custom_notification);

        // Cập nhật nội dung
        notificationLayout.setTextViewText(R.id.tv_title, "Download manager");
        notificationLayout.setTextViewText(R.id.tv_link, "Link: " + (fileUrl != null ? fileUrl : "..."));
        notificationLayout.setTextViewText(R.id.tv_progress, "Complete: " + progress + "%");
        notificationLayout.setProgressBar(R.id.progress_bar, 100, progress, false);

        // Tạo PendingIntent cho các nút
        Intent pause = new Intent(this, DownloadReceiver.class).setAction("PAUSE");
        Intent resume = new Intent(this, DownloadReceiver.class).setAction("RESUME");
        Intent cancel = new Intent(this, DownloadReceiver.class).setAction("CANCEL");

        PendingIntent pPause = PendingIntent.getBroadcast(this, 0, pause, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pResume = PendingIntent.getBroadcast(this, 1, resume, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pCancel = PendingIntent.getBroadcast(this, 2, cancel, PendingIntent.FLAG_IMMUTABLE);

        // Gắn PendingIntent vào các nút
        notificationLayout.setOnClickPendingIntent(R.id.btn_pause, pPause);
        notificationLayout.setOnClickPendingIntent(R.id.btn_resume, pResume);
        notificationLayout.setOnClickPendingIntent(R.id.btn_cancel, pCancel);

        // Tạo notification với custom layout
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayout)
                .addAction(android.R.drawable.ic_media_pause, "Pause", pPause)
                .addAction(android.R.drawable.ic_media_play, "Resume", pResume)
                .addAction(android.R.drawable.ic_delete, "Cancel", pCancel)
                ;
        startForeground(1, builder.build());
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL_ID, "Download", NotificationManager.IMPORTANCE_DEFAULT); // Changed from IMPORTANCE_LOW to IMPORTANCE_DEFAULT
            getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}