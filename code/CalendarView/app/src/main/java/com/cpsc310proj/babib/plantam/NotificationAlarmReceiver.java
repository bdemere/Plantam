package com.cpsc310proj.babib.plantam;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.CustomTime;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.CalendarActivity;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        if(eventUID!=null) {
            SQLiteEventDatabase db = SQLiteEventDatabase.getEventDatabase(context);
            Event e = db.getEvent(eventUID);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(e.getTitle())
                            .setContentText("Event coming up in 1 hour!");
            Intent resultIntent = new Intent(context, CalendarActivity.class);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            int mNotificationId = Integer.parseInt(eventUID);
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }
    }
    public static void scheduleAlarms(Context context) {
        
        SQLiteEventDatabase db = SQLiteEventDatabase.getEventDatabase(context);
        List<Event> events = db.getEventsAtDate(new CustomDate(CurrentDate.getYear(),CurrentDate.getMonth(),CurrentDate.getDay()));
        for(Event e : events) {
            CustomDate cd = e.getDate();
            CustomTime ct = e.getStartTime();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, cd.getYear());
            cal.set(Calendar.MONTH, cd.getMonth()-1);
            cal.set(Calendar.DAY_OF_MONTH, cd.getDay());
            cal.set(Calendar.HOUR_OF_DAY, ct.getHour()-1);
            cal.set(Calendar.MINUTE, ct.getMin());
            long time = cal.getTimeInMillis();
    
            Intent intentAlarm = new Intent(context, NotificationAlarmReceiver.class);
            intentAlarm.putExtra("eventUID", e.getEventUID());
    
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }

    class AlarmSetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            scheduleAlarms(context);
        }
    }

    class BootReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            scheduleAlarms(context);
        }
    }
}
