package com.cpsc310proj.babib.plantam.Event;

/**
 * Created by bemnet on 12/3/17.
 */

public class EventInfo {
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mStartTime;
    private String mEndTime;
    private String mCategory;


    public EventInfo(String mTitle, String mDescription, String mDate,
                     String mStartTime, String mEndTime, String mCategory) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDate = mDate;
        this.mStartTime = mStartTime;
        this.mEndTime = mEndTime;
        this.mCategory = mCategory;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }
}
