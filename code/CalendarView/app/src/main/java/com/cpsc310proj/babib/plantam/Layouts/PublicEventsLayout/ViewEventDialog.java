package com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Firebase.User;
import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.EditEventDialog;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 */
public class ViewEventDialog extends EditEventDialog {

    public ViewEventDialog() {}// Required empty public constructor


    /**
     * A factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param toEdit Parameter
     * @return A new instance of fragment EditEventDialog.
     */
    public static ViewEventDialog newInstance(Event toEdit, SQLiteEventDatabase database) {
        ViewEventDialog fragment = new ViewEventDialog();
        Bundle args = new Bundle();

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
    public static ViewEventDialog newInstance(Event toEdit, FBDatabase database) {
        ViewEventDialog fragment = new ViewEventDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, toEdit);
        args.putSerializable(ARG_DATABASE, database);
        fragment.setArguments(args);
        return fragment;
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

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        /**
         * The ViewEventDialog class should only view hence all the input fields
         * should be disabled and just show the details of the event
         **/
        String CANCEL = "Cancel";
        String ADD = "Add";
        Button cancelDialog = mEdit;
        Button addToDatabase = mDelete;


        /*
         * Since the purpose of this Dialog is only to view, the form
         * fields from the super class are made uneditable
         */

        mForm.setMode(false);
        mForm.mStartTimePicker.setText("Start time: " + mForm.mStartTimePicker.getText());
        mForm.mEndTimePicker.setText("End time: " + mForm.mEndTimePicker.getText());

        addToDatabase.setText(ADD);
        cancelDialog.setText(CANCEL);



        //Set on click listener to add public event to database
        addToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEdit.setAccessibility(Accessibility.PUBLIC.toString());
                mDatabase.addEvent(toEdit);
                dismiss();
            }

        });

        //Set on click listener to cancel the event detail dialog
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        /*
         * Get the user info to display who posted the event
         */
        User user = toEdit.getUser();

        String MESSAGE = user != null ?
                "Posted by: " + user.getName() + ", " + user.getClassYear() +
                        "@ " + user.getEmail()
                : "";

        /*
         * A box to show the detail of the user who posted the event
         */
        final Snackbar postInfo = Snackbar.make(getView(), MESSAGE , Snackbar.LENGTH_INDEFINITE);
        postInfo.setAction("ADD/CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInfo.dismiss();
            }
        });

        postInfo.show();

        /*
         * This is to open an alert dialog to display the description of the event in a bigger
         * box
         */

        mForm.mDescriptionEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Description");
                alertDialog.setMessage(mForm.mDescriptionEditText.getText());
                alertDialog.show();
            }
        });


        getDialog().getWindow().setLayout(MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }
}
