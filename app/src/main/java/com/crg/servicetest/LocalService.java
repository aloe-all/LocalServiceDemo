package com.crg.servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by aloe on 16-11-27.
 */
public class LocalService  extends Service {
    public static final String TAG = LocalService.class.getSimpleName();
    private NotificationManager mNotificationManager;
    private  int NOTIFICATION = R.string.local_service_start;

    public class LocalBinder extends Binder {
        LocalService getService(){
//            return LocalService.this;
            return new LocalService();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "LocalService ---> onCreate() intent : " );
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "LocalService ---> onStartCommand(): " + intent.toString() + "startId: " + startId + "flags: " +flags);
        return START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "LocalService ---> onUnbind() intent : " + intent.toString());
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LocalService ---> onDestroy() intent : ");
        mNotificationManager.cancel(NOTIFICATION);
        Toast.makeText(this, "停止本地服务", Toast.LENGTH_SHORT).show();
    }
    private IBinder mBinder = new LocalBinder();
    private void showNotification() {
        CharSequence text = getText(R.string.local_service_start);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Main2Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.notification)
                .setTicker(text)
                .setContentTitle("notification ContentTitle")
                .setContentInfo("notification ContentInfo")
                .setContentText("notification contenttext")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
        mNotificationManager.notify(NOTIFICATION, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "LocalService ---> onBind() intent : ");
        return mBinder;
    }

    // 加法
    public int add(int a, int b){
        return a + b;
    }
}
