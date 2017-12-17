package com.cpsc310proj.babib.plantam.Layouts.CalendarLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.EventDatabase;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.EventForm;
import com.cpsc310proj.babib.plantam.Layouts.AddEventTemplate;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;

import java.util.Scanner;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 */
public class EditEventDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match

    protected static final String ARG_EVENT = "EditEventDialog.ARG_EVENT";
    protected static final String ARG_DATABASE = "EditEventDialog.ARG_DATABASE";

    // TODO: Rename and change types of parameters

    protected EventForm mForm;
    protected Button mEdit;
    protected Button mDelete;


    protected String mEventID;
    protected EventDatabase mDatabase;
    protected Context mContext;

    protected Event toEdit;




    public EditEventDialog() {}// Required empty public constructor


    /**
     * A factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param toEdit Parameter
     * @return A new instance of fragment EditEventDialog.
     */
    public static EditEventDialog newInstance(Event toEdit, SQLiteEventDatabase database) {
        EditEventDialog fragment = new EditEventDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, toEdit);
        args.putSerializable(ARG_DATABASE, database);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * A factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param toEdit Parameter
     * @return A new instance of fragment EditEventDialog.
     */
    public static EditEventDialog newInstance(Event toEdit, FBDatabase database) {
        EditEventDialog fragment = new EditEventDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, toEdit);
        args.putSerializable(ARG_DATABASE, database);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            toEdit = (Event)getArguments().getSerializable(ARG_EVENT);
            mDatabase = (EventDatabase)getArguments().getSerializable(ARG_DATABASE);
        }
        mContext = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        // Inflate the layout for this fragment
        mForm = new EventForm(mContext);
        mDatabase = SQLiteEventDatabase.getEventDatabase(mContext);

        //Initialize all the fields when the activity is created
        mForm.mTitleEditText = (EditText)view.findViewById(R.id.add_event_title_input);
        mForm.mDescriptionEditText = (EditText)view.findViewById(R.id.add_event_description_input);
        mForm.mDatePicker = (Button)view.findViewById(R.id.add_event_date_picker);
        mForm.mStartTimePicker = (Button)view.findViewById(R.id.add_event_start_time_picker);
        mForm.mEndTimePicker = (Button)view.findViewById(R.id.add_event_end_time_picker);
        mForm.mCategorySpinner = (Spinner)view.findViewById(R.id.add_event_category_spinner);
        mForm.mLocationPicker = (Button)view.findViewById(R.id.add_event_location_picker);

        mEdit = (Button)view.findViewById(R.id.event_dialog_edit_event);
        mDelete = (Button)view.findViewById(R.id.event_dialog_delete_event);


        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteEvent(toEdit);
                FBDatabase fbDatabase = new FBDatabase();
                Log.d("Accessibility: ", toEdit.getAccessibility().toString());
                if(toEdit.getAccessibility().equals(Accessibility.USERPUBLIC.toString())) {
                    fbDatabase.deleteEvent(toEdit);
                    FBDatabase.updateEventsData();
                }

                Log.d("Trying: ", toEdit.getAccessibility().toString());
                dismiss();
            }
        });



        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEventTemplate addEventTemplate = new AddEventTemplate() {
                    @Override
                    protected void ifEventIsValid(Event event) {
                        //Delete old event from Databases first
                        mDatabase.deleteEvent(toEdit);
                        //Add new edited event to databases
                        mDatabase.addEvent(event);
                        FBDatabase fbDatabase = new FBDatabase();

                        Log.d("Accessibility: ", toEdit.getAccessibility().toString());
                        if(toEdit.getAccessibility().equals(Accessibility.USERPUBLIC.toString())) {
                            //Delete old event from Databases first
                            //then add the new event to the Databases
                            fbDatabase.deleteEvent(toEdit);
                            fbDatabase.addEvent(toEdit);
                            FBDatabase.updateEventsData();
                        }

                        dismiss();
                    }
                };
                //If form is valid, add event to database
                addEventTemplate.validate(view, mForm);
            }
        });

        //Fill the from from database
        Event event = toEdit;
        mForm.mTitleEditText.setText(event.getTitle());
        mForm.mDescriptionEditText.setText(event.getDescription());
        mForm.mDatePicker.setText(event.getDate().toString());
        mForm.mStartTimePicker.setText(event.getStartTime().toString());
        mForm.mEndTimePicker.setText(event.getEndTime().toString());
        mForm.mCategorySpinner.setSelection(Category.getIndex(toEdit.getCategory()));
        mForm.mLocationPicker.setText(event.getLocation());
        mForm.initializeForm();



        return view;
    }


    public void locationButtonClicked(View v){
        Intent intent = new Intent(getActivity(), ViewLocation.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        // safety check
        if (getDialog() == null)
            return;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        int dialogWidth = width;
        int dialogHeight = height;

        getDialog().getWindow().setLayout(MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }


}
