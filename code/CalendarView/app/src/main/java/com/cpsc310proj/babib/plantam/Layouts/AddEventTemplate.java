package com.cpsc310proj.babib.plantam.Layouts;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.EventForm;

/**
 * @author Group 4
 * @version 1.0
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

    /**
     *  ExtractEventInfo
     *  @param str : str in format -
     *             "Hartford, CT 06106, USA%LatLngBounds{southwest=lat/lng:
     *             (41.743505105687404,-72.69189968705177), northeast=lat/lng:
     *             (41.74681889675219,-72.68803764134645)}"
     *  @return String[] - extracted geogrpahic information in String array 
     */

    public static String[] extractLocationInfo(String str){

        String[] locInfo = str.split(LOCATION_DIVIDER);

        String[] s = locInfo[1].split(" ");
        Log.d("String: ", s.toString());
        String[] val2 = (s[1].split(","));
        String[] val4 = (s[3].split(","));
        String[] retVal =
                {
                        locInfo[0],
                        removeExtra(val2[0]),
                        removeExtra(val2[1]),
                        removeExtra(val4[0]),
                        removeExtra(val4[1])
                };
        Log.d("Oldway: ", retVal.toString());
        return retVal;

    }

    /**
     * A method to be used in the function extractInfo to remove parenthesis and comma
     * from a string
     * @param str
     * @return a parsable string (to double)
     */
    private static String removeExtra(String str){
        return str
                .replaceAll(",", "") //remove ','
                .replaceAll("\\(", "") //remove '('
                .replaceAll("\\)", "") // remove ')'
                .replaceAll("\\}", "") //remove '}'
                .replaceAll("\\{", ""); //remove '{'
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
