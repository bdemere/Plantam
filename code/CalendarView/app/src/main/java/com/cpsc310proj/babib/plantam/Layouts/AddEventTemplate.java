package com.cpsc310proj.babib.plantam.Layouts;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.EventForm;

/**
 * Created by bemnet on 12/14/17.
 *
 * This class is an abstract template class that encapsulates the
 * process of creating an event out a event fill out form.
 */

public abstract class AddEventTemplate {
    /**
     * Passed the android.View of the calling Activity and a form to validate,
     * throws an Exception if is not filled with the given specifications
     * @param view view of the calling activity
     * @param form the form to be validated
     */
    public void validate(View view, EventForm form){
        try{
            sanity_check(form);

            //ADD EVENT
            Event new_event = new Event();
            new_event.setTitle(form.mTitleEditText.getText().toString());
            new_event.setDate(form.mDatePicker.getText().toString());
            new_event.setStartTime(form.mStartTimePicker.getText().toString());
            new_event.setEndTime(form.mEndTimePicker.getText().toString());
            new_event.setDescription(form.mDescriptionEditText.getText().toString());
            new_event.setCategory(form.mCategorySpinner.getSelectedItem().toString());

            ifEventIsValid(new_event);
        } catch (Exception e) {
            Snackbar.make(view, e.getMessage().toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**
     * After form is checked and validated, do something to the event
     * @param event
     */
    protected abstract void ifEventIsValid(Event event);


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
