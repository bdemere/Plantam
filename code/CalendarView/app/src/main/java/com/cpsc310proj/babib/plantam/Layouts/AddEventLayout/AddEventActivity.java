package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.cpsc310proj.babib.plantam.CurrentDate;
import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.CustomTime;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.EventDatabase;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * An Activity class that will inquire all the necessary information
 * about an event and will add to the database
 */
public class AddEventActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private EditText mTitleEditText; //Text Field: for the title of an event
    private EditText mDescriptionEditText; //Text Field: for the description of an event
    private Button mDatePicker; //Button: opens a date picker
    private Button mStartTimePicker; //Button: opens a time picker (start time)
    private Button mEndTimePicker; //Button: opens a time picker (end time)
    private SwitchCompat mPublicPersonalSwitch; //Switch: determine the accessibility identifier
    private Spinner mCategorySpinner; //Spinner: choose the category of the event

    private DatePickerDialog mDatePickerDialog; //Dialog: will be opened to choose date
    private TimePickerDialog mStartTimePickerDialog; //Dialog: will be opened to choose start time
    private TimePickerDialog mEndTimePickerDialog; //Dialog: will be opened to choose end time

    private FloatingActionButton mFloatingAddButton; //Floating Button: finalize and add buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event); //attach layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //attach toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Initialize all the fields when the activity is created
        mTitleEditText = (EditText)findViewById(R.id.add_event_title_input);
        mDescriptionEditText = (EditText)findViewById(R.id.add_event_description_input);
        mDatePicker = (Button)findViewById(R.id.add_event_date_picker);
        mStartTimePicker = (Button)findViewById(R.id.add_event_start_time_picker);
        mEndTimePicker = (Button)findViewById(R.id.add_event_end_time_picker);
        mPublicPersonalSwitch = (SwitchCompat)findViewById(R.id.add_event_public_or_personal_switch);
        mCategorySpinner = (Spinner)findViewById(R.id.add_event_category_spinner);

        initialize_categories(mCategorySpinner); //setting category options


        //these functions are called to initialize the dialogs and set 'time/date-picked'
        //listeners to update the fields in the form when times and dates are picked
        //from the dialogs
        initialize_date_dialog();
        initialize_start_time_dialog();
        initialize_end_time_dialog();


        //Set on click listeners to show the dialogs
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


        //Initialize the 'finalize and add' button
        mFloatingAddButton = (FloatingActionButton) findViewById(R.id.fab);
        //Set an onclick listener
        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sanity_check();

                    //ADD EVENT
                    Event new_event = new Event();
                    new_event.setTitle(mTitleEditText.getText().toString());
                    new_event.setDate(mDatePicker.getText().toString());
                    new_event.setStartTime(mStartTimePicker.getText().toString());
                    new_event.setEndTime(mEndTimePicker.getText().toString());
                    new_event.setDescription(mDescriptionEditText.getText().toString());
                    new_event.setCategory(mCategorySpinner.getSelectedItem().toString());
                    new_event.setEventUID();


                    //TODO: ADD to database
                    EventDatabase eventDatabase = new EventDatabase(AddEventActivity.this);
                    Log.d("Adding: ", new_event.toString());

                    eventDatabase.addEvent(new_event);
                    finish();
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }


    /**
     * This method is found in DatePickerDialog.OnDateSetListener
     * that should be implemented in order to make changes to the activity
     * whenever a date is picked
     * @param view
     * @param year
     * @param month
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDatePicker.setText(
                (new CustomDate()).setDay(dayOfMonth).setMonth(month).setYear(year).toString()
        );
    }

    /**
     * This method is found in TimePickerDialog.OnTimeSetListener
     * that should be implemented in order to make changes to the activity
     * whenever the time picker is modified
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    /**
     * Initialize a spinner with event categories
     * @param category_spinner
     */
    private void initialize_categories(Spinner category_spinner){
        ArrayList<String> categories = new ArrayList<>();

        for(Category category : Category.values()){
            categories.add(category.toString().toUpperCase());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categories);

        category_spinner.setAdapter(adapter);
    }


    /**
     * Initialize the date
     */
    private void initialize_date_dialog(){
        int day = CurrentDate.getDay();
        int month = CurrentDate.getMonth();
        int year = CurrentDate.getYear();


        mDatePickerDialog = new DatePickerDialog(AddEventActivity.this, this,
                year, month,day);
    }

    /**
     * Initialize start time dialog
     * Whenever the dialog is opened and a time is set,
     * the button field indicating the start time is updated with the time
     */
    private void initialize_start_time_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();

        mStartTimePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mStartTimePicker.setText(
                        (new CustomTime()).setHour(selectedHour).setMin(selectedMinute).toString()
                );
            }
        }, date.getHours(), date.getMinutes(), false);
    }

    /**
     * Initialize end time dialog
     * Whenever the dialog is opened and a time is set,
     * the button field indicating the event end time is updated with the time
     */
    private void initialize_end_time_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();

        mEndTimePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mEndTimePicker.setText(
                        (new CustomTime()).setHour(selectedHour).setMin(selectedMinute).toString()
                );
            }
        }, date.getHours(), date.getMinutes(), false);
    }


    /**
     * Checks the validity of the information entered
     * @throws IllegalArgumentException
     */
    private void sanity_check() throws IllegalArgumentException{

        /** Check if each field and dialog is filled **/

        if(mTitleEditText.getText().toString().isEmpty())
            throw new IllegalArgumentException("Enter title");
        if(mDescriptionEditText.getText().toString().isEmpty())
            throw new IllegalArgumentException("Enter description");
        if(mDatePicker.getText().toString().equalsIgnoreCase("Date"))
            throw new IllegalArgumentException("Pick date");
        if(mStartTimePicker.getText().toString().equalsIgnoreCase("Start time"))
            throw new IllegalArgumentException("Pick start time");
        if(mEndTimePicker.getText().toString().equalsIgnoreCase("End time"))
            throw new IllegalArgumentException("Pick end time");
    }

}
