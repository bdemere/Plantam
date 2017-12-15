package com.cpsc310proj.babib.plantam.Event;

import java.io.Serializable;

/**
 * Created by bemnet on 12/3/17.
 */

public class EventInfo implements Serializable{
    public String mEventUID = ""; //this events unique ID
    public String mTitle = "";
    public String mDescription = "";
    public String mDate = "";
    public String mStartTime = "";
    public String mEndTime = "";
    public String mCategory = "";
    public String mAccessibility = "";

    public void setInfo(EventInfo info){
        mEventUID = info.mEventUID;
        mTitle = info.mTitle;
        mDescription = info.mDescription;
        mDate = info.mDate;
        mStartTime = info.mStartTime;
        mEndTime = info.mEndTime;
        mCategory = info.mCategory;
        mAccessibility = info.mAccessibility;
    }
    @Override
    public String toString() {
        return "EventInfo{" +
                "mEventUID='" + mEventUID + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mDate='" + mDate + '\'' +
                ", mStartTime='" + mStartTime + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mAccessibility='" + mAccessibility + '\'' +
                '}';
    }
    //    public EventInfo(String mEventUID, String mTitle,
//                     String mDescription, String mDate,
//                     String mStartTime, String mEndTime,
//                     String mCategory, String mAccessibility) {
//        this.mEventUID = mEventUID;
//        this.mTitle = mTitle;
//        this.mDescription = mDescription;
//        this.mDate = mDate;
//        this.mStartTime = mStartTime;
//        this.mEndTime = mEndTime;
//        this.mCategory = mCategory;
//        this.mAccessibility = mAccessibility;
//    }
//
//
//    public String getEventUID() {
//        return mEventUID;
//    }
//
//    public String getTitle() {
//        return mTitle;
//    }
//
//    public String getDescription() {
//        return mDescription;
//    }
//
//    public String getDate() {
//        return mDate;
//    }
//
//    public String getStartTime() {
//        return mStartTime;
//    }
//
//    public String getEndTime() {
//        return mEndTime;
//    }
//
//    public String getCategory() {
//        return mCategory;
//    }
//
//    public String getAccessibility() {
//        return mAccessibility;
//    }

}
