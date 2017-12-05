package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.EventDatabase;

/**
 * An Activity class that will inquire all the necessary information
 * about an event and will add to the database
 */
public class AddEventActivity extends AppCompatActivity{


    private EventForm mForm;

    private FloatingActionButton mFloatingAddButton; //Floating Button: finalize and add buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event); //attach layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //attach toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mForm = new EventForm(this);

        //Initialize all the fields when the activity is created
        mForm.mTitleEditText = (EditText)findViewById(R.id.add_event_title_input);
        mForm.mDescriptionEditText = (EditText)findViewById(R.id.add_event_description_input);
        mForm.mDatePicker = (Button)findViewById(R.id.add_event_date_picker);
        mForm.mStartTimePicker = (Button)findViewById(R.id.add_event_start_time_picker);
        mForm.mEndTimePicker = (Button)findViewById(R.id.add_event_end_time_picker);
        mForm.mPublicPersonalSwitch = (SwitchCompat)findViewById(R.id.add_event_public_or_personal_switch);
        mForm.mCategorySpinner = (Spinner)findViewById(R.id.add_event_category_spinner);

        mForm.initializeForm();


        //Initialize the 'finalize and add' button
        mFloatingAddButton = (FloatingActionButton) findViewById(R.id.fab);
        //Set an onclick listener
        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    sanity_check(mForm);

                    //ADD EVENT
                    Event new_event = new Event();
                    new_event.setTitle(mForm.mTitleEditText.getText().toString());
                    new_event.setDate(mForm.mDatePicker.getText().toString());
                    new_event.setStartTime(mForm.mStartTimePicker.getText().toString());
                    new_event.setEndTime(mForm.mEndTimePicker.getText().toString());
                    new_event.setDescription(mForm.mDescriptionEditText.getText().toString());
                    new_event.setCategory(mForm.mCategorySpinner.getSelectedItem().toString());
                    new_event.setAccessibility(
                            mForm.mPublicPersonalSwitch.isChecked() ?
                                    Accessibility.PUBLIC.toString() :
                                    Accessibility.PRIVATE.toString()
                    );



                    //TODO: ADD to database
                    EventDatabase eventDatabase = new EventDatabase(AddEventActivity.this);
                    Log.d("Adding: ", new_event.toString());

                    eventDatabase.addEvent(new_event);
                    finish();
                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    /**
     * Checks the validity of the information entered
     * @param form
     * @throws IllegalArgumentException
     */
    private void sanity_check(EventForm form) throws IllegalArgumentException{

        /** Check if each field and dialog is filled **/

        if(form.mTitleEditText.getText().toString().isEmpty())
            throw new IllegalArgumentException("Enter title");
        if(form.mDescriptionEditText.getText().toString().isEmpty())
            throw new IllegalArgumentException("Enter description");
        if(form.mDatePicker.getText().toString().equalsIgnoreCase("Date"))
            throw new IllegalArgumentException("Pick date");
        if(form.mStartTimePicker.getText().toString().equalsIgnoreCase("Start time"))
            throw new IllegalArgumentException("Pick start time");
        if(form.mEndTimePicker.getText().toString().equalsIgnoreCase("End time"))
            throw new IllegalArgumentException("Pick end time");
    }

}
