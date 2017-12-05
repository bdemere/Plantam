package com.cpsc310proj.babib.plantam.Layouts.CalendarLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.EventForm;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.EventDatabase;

/**
 */
public class EventDetailDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_EVENT_ID = "parameter::event_id";

    // TODO: Rename and change types of parameters

    private EventForm mForm;
    private Button mEdit;
    private Button mDelete;


    private String mEventID;
    private EventDatabase mDatabase;
    private Context mContext;



    public EventDetailDialog() {}// Required empty public constructor


    /**
     * A factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventId Parameter
     * @return A new instance of fragment EventDetailDialog.
     */
    public static EventDetailDialog newInstance(String eventId) {
        EventDetailDialog fragment = new EventDetailDialog();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_ID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventID = getArguments().getString(ARG_EVENT_ID);
        }
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        // Inflate the layout for this fragment
        mForm = new EventForm(mContext);
        mDatabase = EventDatabase.getEventDatabase(mContext);

        //Initialize all the fields when the activity is created
        mForm.mTitleEditText = (EditText)view.findViewById(R.id.add_event_title_input);
        mForm.mDescriptionEditText = (EditText)view.findViewById(R.id.add_event_description_input);
        mForm.mDatePicker = (Button)view.findViewById(R.id.add_event_date_picker);
        mForm.mStartTimePicker = (Button)view.findViewById(R.id.add_event_start_time_picker);
        mForm.mEndTimePicker = (Button)view.findViewById(R.id.add_event_end_time_picker);
        mForm.mPublicPersonalSwitch =
                (SwitchCompat)view.findViewById(R.id.add_event_public_or_personal_switch);
        mForm.mCategorySpinner = (Spinner)view.findViewById(R.id.add_event_category_spinner);

        mEdit = (Button)view.findViewById(R.id.event_dialog_edit_event);
        mDelete = (Button)view.findViewById(R.id.event_dialog_delete_event);


        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.deleteEvent(mEventID);
                dismiss();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: edit the event
            }
        });

        //Fill the from from database
        Event event = mDatabase.getEvent(mEventID);
        mForm.mTitleEditText.setText(event.getTitle());
        mForm.mDescriptionEditText.setText(event.getDescription());
        mForm.mDatePicker.setText(event.getDate().toString());
        mForm.mStartTimePicker.setText(event.getStartTime().toString());
        mForm.mEndTimePicker.setText(event.getEndTime().toString());

        //mForm.mPublicPersonalSwitch.setText(event.getAccessibility());
        mForm.initializeForm();



        return view;
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

    }
}
