package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventTemplate;
import com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout.PublicEventsActivity;
import com.cpsc310proj.babib.plantam.R;

/**
 * An Activity class that will inquire all the necessary information
 * about an event and will add to the database
 */
public class AddEventActivity extends AppCompatActivity{

    public static String EVENT_RESULT = AddEventActivity.class.toString() + ".EVENT_RESULT";

    public static Class[] CALLING_ACTIVITIES = {
            PublicEventsActivity.class,


    };

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
        mForm.mCategorySpinner = (Spinner)findViewById(R.id.add_event_category_spinner);

        mForm.initializeForm();


        //Initialize the 'finalize and add' button
        mFloatingAddButton = (FloatingActionButton) findViewById(R.id.fab);
        //Set an onclick listener
        mFloatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEventTemplate addEventTemplate = new AddEventTemplate() {
                    @Override
                    protected void ifEventIsValid(Event event) {
                        Intent toReturn = new Intent();
                        toReturn.putExtra(EVENT_RESULT, event);
                        setResult(AppCompatActivity.RESULT_OK, toReturn);

                        finish();
                    }
                };
                addEventTemplate.validate(view, mForm);


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
