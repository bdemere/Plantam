package com.cpsc310proj.babib.plantam;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

/**
 * Created by MyNameIsYou on 10/21/17.
 */

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        CalendarViewFragment upperFragment = new CalendarViewFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.upper_part_monthly_view, upperFragment, upperFragment.getTag())
                .commit();

        CalendarViewDetailFragmnet lowerFragmnet = new CalendarViewDetailFragmnet();
        FragmentManager manager1 = getSupportFragmentManager();
        manager1.beginTransaction()
                .replace(R.id.lower_part_monthly_view, lowerFragmnet, lowerFragmnet.getTag())
                .commit();
//        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
//
//        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                String date = month  + "/" + dayOfMonth + "/" + year;
//                Log.d(TAG, "onSelectedDateChange: date: " + date);
//            }
//        });
    }
}
