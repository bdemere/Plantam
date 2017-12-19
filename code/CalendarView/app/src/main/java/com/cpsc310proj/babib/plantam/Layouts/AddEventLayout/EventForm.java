package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GROUP 4
 * An Activity class that will inquire all the necessary information
 * about an event and will add to the database
 */
public class EventForm
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    private Context mContext;

    public EditText mTitleEditText; //Text Field: for the title of an event
    public EditText mDescriptionEditText; //Text Field: for the description of an event
    public Button mDatePicker; //Button: opens a date picker
    public Button mStartTimePicker; //Button: opens a time picker (start time)
    public Button mEndTimePicker; //Button: opens a time picker (end time)
    public Spinner mCategorySpinner; //Spinner: choose the category of the event
    public Button mLocationPicker; //Button: set the location of the event

    public LatLngBounds bounds;

    public DatePickerDialog mDatePickerDialog; //Dialog: will be opened to choose date
    public TimePickerDialog mStartTimePickerDialog; //Dialog: will be opened to choose start time
    public TimePickerDialog mEndTimePickerDialog; //Dialog: will be opened to choose end time


    public boolean isEditable = true;

    public EventForm(Context context){
        mContext = context;
    }

    /**
     *
     * @return whether the form is editable or not
     */
    public boolean isEditable(){return isEditable;}

    /**
     * Sets the field identifying whether the forms is editable or not
     * @param isEditable
     */
    public void setMode(boolean isEditable){
        //If the state of the form needs to change to the required state

        if(!this.isEditable && isEditable || !isEditable && this.isEditable) {
            if (mTitleEditText != null){
                //mTitleEditText.setEnabled(isEditable);
                mTitleEditText.setCursorVisible(isEditable);
                mTitleEditText.setBackgroundColor(Color.TRANSPARENT);
                mTitleEditText.setKeyListener(null);
            }
            if (mDescriptionEditText != null){
                mDescriptionEditText.setCursorVisible(isEditable);
                mDescriptionEditText.setBackgroundColor(Color.TRANSPARENT);
                mDescriptionEditText.setKeyListener(null);
            }
            if (mDatePicker != null)mDatePicker.setClickable(isEditable);
            if (mStartTimePicker != null) mStartTimePicker.setClickable(isEditable);
            if (mEndTimePicker != null) mEndTimePicker.setClickable(isEditable);
            if (mCategorySpinner != null)
                mCategorySpinner.setVisibility(isEditable ? View.VISIBLE : View.INVISIBLE);


            this.isEditable = isEditable;
        }


    }


    public void initializeForm(){
        initialize_categories(mCategorySpinner); //setting category options


        //these functions are called to initialize the dialogs and set 'time/date-picked'
        //listeners to updateEventsData the fields in the form when times and dates are picked
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



    }


    /**
     * This method is found in DatePickerDialog.OnDateSetListener
     * that should be implemented in order to make changes to the object
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
     * that should be implemented in order to make changes to the object
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
                mContext, android.R.layout.simple_spinner_item, categories);

        category_spinner.setAdapter(adapter);

    }


    /**
     * Initialize the date
     */
    private void initialize_date_dialog(){
        int day = CurrentDate.getDay();
        int month = CurrentDate.getMonth();
        int year = CurrentDate.getYear();


        mDatePickerDialog = new DatePickerDialog(mContext, this,
                year, month,day);
    }

    /**
     * Initialize start time dialog
     * Whenever the dialog is opened and a time is set,
     * the button field indicating the start time is updated with the time
     */
    private void initialize_start_time_dialog(){
        java.util.Date date = Calendar.getInstance().getTime();

        mStartTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
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

        mEndTimePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mEndTimePicker.setText(
                        (new CustomTime()).setHour(selectedHour).setMin(selectedMinute).toString()
                );
            }
        }, date.getHours(), date.getMinutes(), false);
    }




}
