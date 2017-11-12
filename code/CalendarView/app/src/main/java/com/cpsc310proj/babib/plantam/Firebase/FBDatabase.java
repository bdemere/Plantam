package com.cpsc310proj.babib.plantam.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static String EVENT_ROOT = "events";
    private static FirebaseDatabase mFirebaseDatabase = null;

    private static DatabaseReference mUsersDatabaseReference  = null;
    private static DatabaseReference mEventsDatabaseReference = null;

    private static Map<Category, ArrayList<String>> EventCategoryCache = null;
    private static Map<Category, DatabaseReference> EventCategoryDatabaseReferences = null;


    private static ArrayList<FireBaseDataObserver> dataObservers = null;

    public static void addObserver(FireBaseDataObserver observer){
        if(dataObservers == null) {
            dataObservers = new ArrayList<>();
            dataObservers.add(observer);
        } else {
            dataObservers.add(observer);
        }
    }

    public static boolean removeObserver(FireBaseDataObserver observer){
        return dataObservers.remove(observer);
    }



    public static void writeEvent(Event e){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        if(mEventsDatabaseReference == null)
            mEventsDatabaseReference = mFirebaseDatabase.getReference().child(EVENT_ROOT);

        mEventsDatabaseReference.child("sport").child(e.getTitle()).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Writing: ", "Great");
                }else{
                    Log.d("Writing: ", task.getException().toString());
                }
            }
        });
    }


    private static boolean isEventDataUpdated = false;

    public static void eventDataUpdated(){
        if(dataObservers != null)
            for(FireBaseDataObserver fireBaseDataObserver: dataObservers)
                fireBaseDataObserver.eventDataChanged();
    }

    /**
     * A function to fetch data under the events root from
     * the Firebase Database
     */
    public static void makeEventsDataLocal(){
        //if not initialized, initialize database reference
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();
        if(mEventsDatabaseReference == null)
            mEventsDatabaseReference = mFirebaseDatabase.getReference().child(EVENT_ROOT);

        //If cache is uninitialized
        if(EventCategoryCache == null || EventCategoryDatabaseReferences == null) {
            EventCategoryCache = new HashMap<>();
            EventCategoryDatabaseReferences = new HashMap<>();
            for (Category category : Category.values()) {
                EventCategoryCache.put(category, new ArrayList<String>());
                EventCategoryDatabaseReferences.put(category,
                        mEventsDatabaseReference.child(category.toString()));
            }
        }

        //if not updated, fill local cache with data from the database
        if(!isEventDataUpdated) {
            for(Category category : Category.values())
                updateEventsCache(EventCategoryDatabaseReferences.get(category),
                        EventCategoryCache.get(category));
            isEventDataUpdated = true;
        }






    }

    /**
     * A function to update local data
     */
    public static void update(){
        isEventDataUpdated = false;
        for(Category category : Category.values())
            EventCategoryCache.put(category, new ArrayList<String>());
        makeEventsDataLocal();

    }

    /**
     * A function to pull data from a given root in the events database
     * @param dbRef
     * @param toUpdate
     */
    private static void updateEventsCache(DatabaseReference dbRef, final ArrayList<String> toUpdate){
        dbRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            toUpdate.add(entry.getKey());

                        }
                        eventDataUpdated();

                        //Log.d("ayyyy::::", "" + toUpdate.toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        Log.d("Error:::", databaseError.getDetails().toString());
                    }
                });
    }

    /**
     * Given an events category, return a list of the events
     * @param category
     * @return
     */

    public static ArrayList<String> getPublicEventsWithCategory(Category category){
        ArrayList<String> toReturn = EventCategoryCache.get(category);

        return toReturn != null ? //in case cache is empty, return an empty list
                toReturn : new ArrayList<String>();
    }








}
