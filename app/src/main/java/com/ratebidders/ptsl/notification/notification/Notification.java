package com.ratebidders.ptsl.notification.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.ratebidders.ptsl.notification.MainActivity;
import com.ratebidders.ptsl.notification.R;

public class Notification implements INotification {

    private Context mContext;

    private NotificationManager mManager;

    private NotificationCompat.Builder mBuilder;

    private static final String NOTIFICATION_CHANNEL_ID = "10001";
    private static final int REQUEST_CODE = 0;
    private static final String RATE_BIDDERS_CHANNEL_NAME = "RATEBIDDERS_CHANNEL";


    public Notification(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void createNotification(String title, String message) {
        Intent resultIntent = new Intent(mContext, MainActivity.class);
//        resultIntent.putExtra(IS_NOTIFICATION_CALL_KEY,true);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext, REQUEST_CODE, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_RINGTONE_URI)
                .setContentIntent(resultPendingIntent);
        mManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, RATE_BIDDERS_CHANNEL_NAME, importance);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mManager!=null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mManager.createNotificationChannel(channel);


        }
        assert mManager != null;
        mManager.notify(0 /* Request Code */, mBuilder.build());
    }
}
