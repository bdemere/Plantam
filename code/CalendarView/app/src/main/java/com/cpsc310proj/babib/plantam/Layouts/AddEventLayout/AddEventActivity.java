package com.cpsc310proj.babib.plantam.Layouts.AddEventLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventTemplate;
import com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout.PublicEventsActivity;
import com.cpsc310proj.babib.plantam.NotificationAlarmReceiver;
import com.cpsc310proj.babib.plantam.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import static com.google.android.gms.location.places.ui.PlacePicker.getLatLngBounds;

/**
 * @author GROUP 4
 * @version 1.0
 *
 * An Activity class that will provide a user interface to inquire
 * the details of an event. An instance of this class will implement
 * the layout that has fields for every information necessary. After
 * the information is extracted, it is checked for validity and then
 * an Event object containing all the information entered is sent back
 * to the Caller. Then the caller of this class will then add the
 * returned Event information in to one of the databases. Amen!
 *
 *
 */
public class AddEventActivity extends AppCompatActivity{

    //A string to recognize an object returned after the Caller of this class
    public static String EVENT_RESULT = AddEventActivity.class.toString() + ".EVENT_RESULT";

    //A number to recognize the object returned after
    private int PLACE_PICKER_REQUEST = 1;

    //A form object to store the information
    private EventForm mForm;

    private FloatingActionButton mFloatingAddButton; //Floating Button: finalize and add buttons


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, AddEventActivity.this);

                //Get the latitude and longitude of the location
                mForm.bounds = PlacePicker.getLatLngBounds(data);

                String location = String.format("%s", place.getName());


                mForm.mLocationPicker.setText(location);
            }
        }
    }
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
        mForm.mLocationPicker = (Button)findViewById(R.id.add_event_location_picker);

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
                        NotificationAlarmReceiver.scheduleAlarms(getBaseContext(), event);
                        finish();
                    }
                };
                addEventTemplate.validate(view, mForm);


            }
        });


        //Pick location
        mForm.mLocationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                if(!mForm.mLocationPicker.getText().toString().equals("Location"))
                    builder.setLatLngBounds(
                            mForm.bounds
                    );

                try{
                    startActivityForResult(builder.build(AddEventActivity.this), PLACE_PICKER_REQUEST);
                } catch (Exception e){
                    Log.d( "PLACE: ",
                            e.getMessage().toString());
                }
            }
        });
    }

}
