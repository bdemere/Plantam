package com.cpsc310proj.babib.plantam;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.CustomTime;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;
import java.util.Calendar;
import static android.content.Context.NOTIFICATION_SERVICE;

/*
*   Class:      NotificationAlarmReceiver.java
*   Purpose:    Handles notifications and alarms for events in a user calender 
*
*   @author     Brian Cieplicki
*   @version    1.0
*   @since      2017-12-10
*
*/
public class NotificationAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventUID = intent.getStringExtra("eventUID");
        Log.d("UID", eventUID);
        if(eventUID!=null) {
            SQLiteEventDatabase db = SQLiteEventDatabase.getEventDatabase(context);
            Event e = db.getEvent(eventUID);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(e.getTitle())
                            .setContentText("EVENT STARTING")
                            .setAutoCancel(true);

            Intent resultIntent = new Intent(context, NotificationAlarmReceiver.class);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            int mNotificationId = (int)System.currentTimeMillis();
          //  int mNotificationId = Integer.valueOf(eventUID);
            Log.d("Alarm", "Alarm is called");
            NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void scheduleAlarms(Context context, Event e) {

            CustomDate cd = e.getDate();
            CustomTime ct = e.getStartTime();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, cd.getYear());
            cal.set(Calendar.MONTH, cd.getMonth()-1);
            cal.set(Calendar.DAY_OF_MONTH, cd.getDay());
            cal.set(Calendar.HOUR_OF_DAY, ct.getHour());
            cal.set(Calendar.MINUTE, ct.getMin());
            cal.set(Calendar.AM_PM, Calendar.AM);
            //long time = cal.getTimeInMillis();
            Log.d("Alarm", "Month: " + cd.getMonth() + " Day: " + cd.getDay() + " Year: " + cd.getYear() + " Hour: " + ct.getHour()
            + " Min: " + ct.getMin()
            );


            Intent intentAlarm = new Intent(context, NotificationAlarmReceiver.class);
            intentAlarm.putExtra("eventUID", e.getEventUID());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,  intentAlarm,0);

            Log.d("ALARMID", e.getEventUID());
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

}
