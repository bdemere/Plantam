package com.cpsc310proj.babib.plantam.Layouts;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.EventForm;
<<<<<<< HEAD
import com.google.android.gms.maps.model.LatLng;
=======
>>>>>>> 06d520e0d502d93fde61224a5352df1ead724c84
import com.google.android.gms.maps.model.LatLngBounds;

/**
 * Created by bemnet on 12/14/17.
 *
 * This class is an abstract template class that encapsulates the
 * process of creating an event out a event fill out form.
 */

public abstract class AddEventTemplate {
    public static String LOCATION_DIVIDER = " ::: ";


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
            new_event.setLocation(form.mLocationPicker.getText().toString()
                    + LOCATION_DIVIDER + form.bounds.toString());



            ifEventIsValid(new_event);
        } catch (Exception e) {
            Snackbar.make(view, e.getMessage().toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /*  ExtractEventInfo
     *  @param String : str in format - "Hartford, CT 06106, USA%LatLngBounds{southwest=lat/lng: (41.743505105687404,-72.69189968705177), northeast=lat/lng: (41.74681889675219,-72.68803764134645)}"
     *  @return String[] - extracted geogrpahic information in String array 
     */

    public String[] extractEventInfo(String str){
        String[] locInfo = str.split(":::");
        String[] s = locInfo[1].split(" ");
        String val = (s[1].substring(1,19));
        String val2 = (s[1].substring(20,38));
        String val3 = (s[3].substring(1,18));
        String val4 = (s[3].substring(19,37));
        String[] retVal = {locInfo[0], val, val2, val3, val4};
        return retVal;

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
        if(form.mLocationPicker.getText().toString().equalsIgnoreCase("Location"))
            throw new IllegalArgumentException("Pick location");
    }
}
