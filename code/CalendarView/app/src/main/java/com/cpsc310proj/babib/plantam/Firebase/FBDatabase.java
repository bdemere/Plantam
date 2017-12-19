package com.cpsc310proj.babib.plantam.Firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cpsc310proj.babib.plantam.DataObserver;
import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Event.EventInfo;
import com.cpsc310proj.babib.plantam.EventDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GROUP 4
 * @version 1.0
 * This is a class that encapsulates the actions
 * pertaining to the online database Firebase.
 * It is designed specifically for this application
 * as it knows how the data is structured and provides
 * methods to fetch the needed data
 */

public class FBDatabase implements EventDatabase, Serializable{
    private static String
            EVENT_ROOT = "events";    //event root node name in the jason structure

    private static String
            USERS_ROOT = "users";
    private static FirebaseDatabase
            mFirebaseDatabase = null; //Firebase database reference object

    private static DatabaseReference
            mEventsDatabaseReference = null; //Reference for the event node

    private static Map<Category, ArrayList<Event>>
            EventCategoryCache = null; //A temporary storage place for database events

    private static Map<Category, DatabaseReference>
            EventCategoryDatabaseReferences = null;
            //A map of category to their Firebase database root references


    private static FirebaseUser mUserReference = FirebaseAuth.getInstance().getCurrentUser();


    /**
     * This is a list of Observers that wait on data change on the local cache
     *
     **/
    private static ArrayList<DataObserver>
            dataObservers = null;


    /**
     * This method is used to add observer objects that
     * will be notified when there is a change made
     * to the downloaded data
     * @param observer
     */
    public static void addObserver(DataObserver observer){
        if(dataObservers == null) { //if not initialized
            dataObservers = new ArrayList<>();
            dataObservers.add(observer);
        } else {
            dataObservers.add(observer);
        }
    }

    /**
     * Remove an observer
     * @param observer
     * @return
     */
    public static boolean removeObserver(DataObserver observer){
        return dataObservers.remove(observer);
    }

    /**
     * Notify all observers that a data has changed
     */
    public static void eventDataUpdated(){
        if(dataObservers != null)
            for(DataObserver dataObserver : dataObservers)
                dataObserver.eventDataChanged();
    }

    public static void write(Event event){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();

        Log.d("FBDatabase: ", "" +
                mFirebaseDatabase.getReference().
                child(EVENT_ROOT + "_" + event.getCategory().toLowerCase()).
                child(mUserReference.getUid()).
                child(event.getEventUID()).
                setValue(event.getEventInfo()).isComplete());
    }

    public static boolean writeUser(String userID){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();

        return mFirebaseDatabase.getReference()
                .child(USERS_ROOT)
                .child(userID)
                .child(EVENT_ROOT)
                .setValue(true).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Log.d("UserID: ", task.getException().toString());
                    }
                }
        ).isSuccessful();
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


    public void addEvent(Event event){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();

        Log.d("FBDatabase: ", "" +
                mFirebaseDatabase.getReference().
                        child(EVENT_ROOT + "_" + event.getCategory().toLowerCase()).
                        child(mUserReference.getUid()).
                        child(event.getEventUID()).
                        setValue(event.getEventInfo()).isComplete());
        eventDataUpdated();
    }


    public void deleteEvent(Event event){
        if(mFirebaseDatabase == null)
            mFirebaseDatabase = FirebaseDatabase.getInstance();

        Log.d("deleteEvent(Event): ", mFirebaseDatabase.getReference().
                child(EVENT_ROOT + "_" + event.getCategory().toLowerCase()).
                child(mUserReference.getUid()).
                child(event.getEventUID()).removeValue().isSuccessful() + "");
        eventDataUpdated();
    }


    private static boolean isEventDataUpdated = false; //boolean value to
    public static boolean isEventDataUpdated(){
        return isEventDataUpdated;
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


        Log.d("Local: ", mFirebaseDatabase.getReference().child(EVENT_ROOT+"_"+Category.SPORT.toString()).toString());
        //If cache is uninitialized
        if(EventCategoryCache == null || EventCategoryDatabaseReferences == null) {
            EventCategoryCache = new HashMap<>();
            EventCategoryDatabaseReferences = new HashMap<>();
            for (Category category : Category.values()) {
                EventCategoryCache.put(category, new ArrayList<Event>());
                EventCategoryDatabaseReferences.put(
                        category, mFirebaseDatabase
                                .getReference()
                                .child(EVENT_ROOT + "_" + category.toString())
                );
            }
        }

        //if not updated, fill local cache with data from the database
        if(!isEventDataUpdated) {
            for(Category category : Category.values())
                updateEventsCache(
                        EventCategoryDatabaseReferences.get(category),
                        EventCategoryCache.get(category)
                );
            isEventDataUpdated = true;
        }
    }

    /**
     * A function to updateEventsData local data
     */
    public static void updateEventsData(){
        isEventDataUpdated = false;
        EventCategoryCache = new HashMap<>();
        for(Category category : Category.values())
            EventCategoryCache.put(category, new ArrayList<Event>());
        makeEventsDataLocal();

    }

    /**
     * A function to pull data from a given event root in the events database
     * @param dbRef
     * @param toUpdate
     */

    private static void updateEventsCache(DatabaseReference dbRef, final ArrayList<Event> toUpdate){
        dbRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in data snapshot
                        //Map<String, Event> map = (Map<String, Event>) dataSnapshot.getValue();

                        for (final DataSnapshot userID: dataSnapshot.getChildren()) {

                            /*Initialize the class that fetches a user bio*/
                            FetchUser fetchUser = new FetchUser(mFirebaseDatabase) {
                                @Override
                                public void onFetch(User user) {
                                    for (DataSnapshot eventID : userID.getChildren()) {
                                        Log.d("EventID: " + eventID.getKey(), "" + user);

                                        Event toAdd = new Event(eventID.getValue(EventInfo.class));
                                        toAdd.setUser(user);
                                        toUpdate.add(toAdd);
                                    }
                                    /**
                                     * Notify all observers
                                     **/
                                    eventDataUpdated();
                                }
                            };
                            /*Do something to the fetched user*/
                            fetchUser.fetchUser(userID.getKey());
                        }
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

    public static ArrayList<Event> getPublicEventsWithCategory(Category category){
        ArrayList<Event> toReturn = EventCategoryCache.get(category);
        return toReturn != null ? //in case cache is empty, return an empty list
                toReturn /*if not empty*/: new ArrayList<Event>() /*else empty list*/;
    }

}
