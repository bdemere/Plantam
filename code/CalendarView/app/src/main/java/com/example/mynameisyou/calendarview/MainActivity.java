package com.example.mynameisyou.calendarview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView theDate;
    private Button btnGoCalendar;
    private Button btnGoWeek;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theDate = (TextView) findViewById(R.id.date);

        btnGoCalendar = (Button) findViewById(R.id.btnGoCalendar);
        btnGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        btnGoWeek = (Button) findViewById(R.id.btnGoWeek);
        btnGoWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.weekly_layout);
                Intent intent = new Intent(MainActivity.this, WeekActivity.class);
                startActivity(intent);
            }
        });
    }
}
