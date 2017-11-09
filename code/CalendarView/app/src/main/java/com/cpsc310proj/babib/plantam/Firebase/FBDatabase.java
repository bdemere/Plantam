package com.cpsc310proj.babib.plantam.Firebase;

import android.util.Log;
import android.widget.Adapter;

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by bemnet on 11/8/17.
 *
 * This is a class that encapsulates the actions
 * pertaining to the online database Firebase.
 * It is designed specifically for this application
 * as it knows how the data is structured and provides
 * methods to fetch the needed data
 */

public class FBDatabase {
    private static FirebaseDatabase mFirebaseDatabase = null;

    private static DatabaseReference mUsersDatabaseReference  = null;
    private static DatabaseReference mEventsDatabaseReference = null;

    private static class EventCategoryCache{
        public static ArrayList<String> Sport = new ArrayList<>();
        public static ArrayList<String> Club = new ArrayList<>();
        public static ArrayList<String> Academic = new ArrayList<>();
        public static ArrayList<String> Social = new ArrayList<>();
//
//        public static boolean mDatasetchanged_sport = false;
//        public static boolean mDatasetchanged_club = false;
//        public static boolean mDatasetchanged_academic = false;
//        public static boolean mDatasetchanged_social = false;

//        public static boolean isDatasetchanged_sport(){return mDatasetchanged_sport;}
//        public static boolean isDatasetchanged_club(){return mDatasetchanged_club;}
//        public static boolean isDatasetchanged_academic(){return mDatasetchanged_academic;}
//        public static boolean isDatasetchanged_social(){return mDatasetchanged_social;}


    }

    private static class EventDatabaseReferences{
        public static DatabaseReference Sport = null;
        public static DatabaseReference Club = null;
        public static DatabaseReference Academic = null;
        public static DatabaseReference Social = null;
    }

    private static boolean isDatasetchanged = false;

    public static void makeLocal(){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        if(mEventsDatabaseReference == null)
            mEventsDatabaseReference = mFirebaseDatabase.getReference().child("events");


        if(EventDatabaseReferences.Sport == null)
            EventDatabaseReferences.Sport = mEventsDatabaseReference.child(Category.SPORT.toString());
        if(EventDatabaseReferences.Club == null)
            EventDatabaseReferences.Club = mEventsDatabaseReference.child(Category.CLUB.toString());
        if(EventDatabaseReferences.Academic == null)
            EventDatabaseReferences.Academic = mEventsDatabaseReference.child(Category.ACADEMIC.toString());
        if(EventDatabaseReferences.Social == null)
            EventDatabaseReferences.Social = mEventsDatabaseReference.child(Category.SOCIAL.toString());

        if(!isDatasetchanged) {
            updateCache(EventDatabaseReferences.Sport, EventCategoryCache.Sport);
            updateCache(EventDatabaseReferences.Club, EventCategoryCache.Club);
            updateCache(EventDatabaseReferences.Academic, EventCategoryCache.Academic);
            updateCache(EventDatabaseReferences.Social, EventCategoryCache.Social);

//            EventCategoryCache.mDatasetchanged_sport = true;
//            EventCategoryCache.mDatasetchanged_club = true;
//            EventCategoryCache.mDatasetchanged_academic = true;
//            EventCategoryCache.mDatasetchanged_social = true;

            isDatasetchanged = true;
        }




    }

    public static void update(){
        isDatasetchanged = false;
        EventCategoryCache.Sport = new ArrayList<>();
        EventCategoryCache.Club = new ArrayList<>();
        EventCategoryCache.Academic = new ArrayList<>();
        EventCategoryCache.Social = new ArrayList<>();
        makeLocal();

    }

    private static void updateCache(DatabaseReference dbRef, final ArrayList<String> toUpdate){
        dbRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            toUpdate.add(entry.getKey());

                        }

                        Log.d("ayyyy::::", "" + toUpdate.toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        Log.d("Error:::", databaseError.getDetails().toString());
                    }
                });
    }

    public static ArrayList<String> getPublicEventsWithCategory(Category category){
        ArrayList<String> toReturn = new ArrayList<>();
        switch (category){
            case SOCIAL:
                toReturn = EventCategoryCache.Social; break;
            case CLUB:
                toReturn = EventCategoryCache.Club; break;
            case ACADEMIC:
                toReturn = EventCategoryCache.Academic; break;
            case SPORT:
                toReturn = EventCategoryCache.Sport; break;
        }
        return toReturn;
    }








}
