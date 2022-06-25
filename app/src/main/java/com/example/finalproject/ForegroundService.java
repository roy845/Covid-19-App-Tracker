package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private CountriesViewModel countriesViewModel;
    @SuppressLint("SimpleDateFormat")
    Notification notification;
    String[] dataArrayString;
    long daysBetween;
    Context context;
    Timer timer;
    TimerTask timerTask;
    int DAYS_IN_SECONDS = 86400000;

    Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 1000ms the TimerTask will run every 1day
        timer.schedule(timerTask, 1000, DAYS_IN_SECONDS * 1000);
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                handler.post(new Runnable() {
                    public void run() {
                        getNotifications();
                    }
                });
            }
        };
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        countriesViewModel = CountriesViewModel.getInstance(getApplication(),context);
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void getNotifications()
    {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Quarantine dates are empty")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_alert)
                .build();
        startForeground(1, notification);
        stopForeground(Service.STOP_FOREGROUND_REMOVE);


        //do heavy work on a background thread
        try {
            String myFormat = "dd/MM/yy";
            dataArrayString  = countriesViewModel.getDataFromFile();
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date endTimeQuarantine;
            if(dataArrayString!=null) {
                final String CURRENT_DATE = new SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().getTime());
                endTimeQuarantine = sdf.parse(dataArrayString[1]);
                Date today = sdf.parse(CURRENT_DATE);
                daysBetween = getDifferenceDays(today,endTimeQuarantine);
                System.out.println ("Days: " + daysBetween);
            }

            if(QuarantineFragment.isDeleted){
                notification = getEmptyNotification();
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                synchronized(notification){
                    mNotificationManager.notify(1,notification);
                }
                startForeground(1,notification);
                stopForeground(Service.STOP_FOREGROUND_REMOVE);
                stopSelf();
            }

            else if(daysBetween==0) {

                notification = getFreeQuarantineNotification();
                if (notification == null) {
                    notification = getEmptyNotification();
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    synchronized (notification) {
                        mNotificationManager.notify(1, notification);
                    }
                    startForeground(1, notification);
                    stopForeground(Service.STOP_FOREGROUND_REMOVE);
                } else {
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    synchronized (notification) {
                        mNotificationManager.notify(1, notification);

                    }
                    startForeground(1, notification);
                }
            }

            else if(daysBetween > 0)
            {

                notification = getRemainingTimeRemainedNotification();
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                synchronized(notification){
                    mNotificationManager.notify(1, notification);
                }
                startForeground(1,notification);
            }

            else if(daysBetween<0)
            {
                notification = getTimeSinceLeftNotification();
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                synchronized(notification){
                    mNotificationManager.notify(1,notification);
                }
                startForeground(1,notification);
                stopForeground(Service.STOP_FOREGROUND_REMOVE);
                stopSelf();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return START_STICKY;
    }


    private Notification getFreeQuarantineNotification(){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);
        Notification notification;
        if(dataArrayString!=null) {

            notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Quarantine time ends")
                    .setContentText("the " + dataArrayString[1] + " has come. you are free now")
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_alert).getNotification();

            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        }
        else
        {
            return null;
        }
        return notification;
    }

    private Notification getRemainingTimeRemainedNotification(){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);

        Notification notification;
        notification=new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Quarantine time")
                .setContentText("you have " + daysBetween + " more days to be free")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_alert).getNotification();

        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        return notification;
    }

    private Notification getTimeSinceLeftNotification(){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);

        Notification notification;
        notification=new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Quarantine time")
                .setContentText("It's been " + -daysBetween + " days since you left quarantine")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_alert).getNotification();

        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        return notification;
    }

    private Notification getEmptyNotification(){
        // The PendingIntent to launch our activity if the user selects
        // this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);

        Notification notification;
        notification=new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Quarantine time")
                .setContentText("Quarantine dates are empty")
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_alert).getNotification();

        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
        return notification;
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy() {
        stopTimerTask();
        super.onDestroy();
    }
}
