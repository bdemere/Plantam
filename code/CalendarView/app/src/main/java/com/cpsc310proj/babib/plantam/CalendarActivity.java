package com.cpsc310proj.babib.plantam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by MyNameIsYou on 10/21/17.
 */

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    private FirebaseAuth mAuth;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_sign_out:
                Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT)
                        .show();
                mAuth.getInstance().signOut();
//                Toast.makeText(this, mAuth.getCurrentUser() + " is signed in??", Toast.LENGTH_SHORT)
//                        .show();
                finish();
                Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

}
