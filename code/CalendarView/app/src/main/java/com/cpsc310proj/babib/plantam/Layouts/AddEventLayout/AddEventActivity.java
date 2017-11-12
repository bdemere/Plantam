package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.CalendarActivity;
import com.cpsc310proj.babib.plantam.R;

import java.sql.Array;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private Button mDatePicker;
    private Button mStartTimePicker;
    private Button mEndTimePicker;
    private SwitchCompat mPublicPersonalSwitch;
    private Spinner mCategorySpinner;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mStartTimePickerDialog;
    private TimePickerDialog mEndTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitleEditText = (EditText)findViewById(R.id.add_event_title_input);
        mDescriptionEditText = (EditText)findViewById(R.id.add_event_description_input);
        mDatePicker = (Button)findViewById(R.id.add_event_date_picker);
        mStartTimePicker = (Button)findViewById(R.id.add_event_start_time_picker);
        mEndTimePicker = (Button)findViewById(R.id.add_event_end_time_picker);
        mPublicPersonalSwitch = (SwitchCompat)findViewById(R.id.add_event_public_or_personal_switch);
        mCategorySpinner = (Spinner)findViewById(R.id.add_event_category_spinner);

        initialize_categories(mCategorySpinner); //setting category options


        initialize_date_dialog();
        initialize_start_time_dialog();
        initialize_end_time_dialog();


        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatePickerDialog.show();
            }
        });

        mStartTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimePickerDialog.show();
            }
        });

        mEndTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndTimePickerDialog.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sanity_check().equalsIgnoreCase("OK")){

                } else {
                    Snackbar.make(view, sanity_check(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDatePicker.setText(year+"/"+month+"/"+dayOfMonth);
        //Toast.makeText(AddEventActivity.this, , Toast.LENGTH_SHORT);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private void initialize_categories(Spinner category_spinner){
        ArrayList<String> categories = new ArrayList<>();

        for(Category category : Category.values()){
            categories.add(category.toString().toUpperCase());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);

        category_spinner.setAdapter(adapter);
    }


    private void initialize_date_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();


        mDatePickerDialog = new DatePickerDialog(AddEventActivity.this, this,
                date.getYear(), date.getMonth(),
                date.getDay());
    }

    private void initialize_start_time_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();

        mStartTimePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mStartTimePicker.setText( selectedHour + ":" + selectedMinute);
            }
        }, date.getHours(), date.getMinutes(), false);
    }

    private void initialize_end_time_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();

        mEndTimePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mEndTimePicker.setText( selectedHour + ":" + selectedMinute);
            }
        }, date.getHours(), date.getMinutes(), false);
    }

    private String sanity_check(){

        if(mTitleEditText.getText().toString().isEmpty())
            return "Enter title";
        if(mDescriptionEditText.getText().toString().isEmpty())
            return "Enter description";
        if(mDatePicker.getText().toString().equalsIgnoreCase("Date"))
            return "Pick date";
        if(mStartTimePicker.getText().toString().equalsIgnoreCase("Start time"))
            return "Pick start time";
        if(mEndTimePicker.getText().toString().equalsIgnoreCase("End time"))
            return "Pick end time";


        return "ok";
    }

}
