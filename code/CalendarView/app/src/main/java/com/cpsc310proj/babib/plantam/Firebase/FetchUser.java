package com.cpsc310proj.babib.plantam.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author GROUP 4
 * @version 1.0
 * Fetch a user data from firebase based on the given key(node)
 */

public abstract class FetchUser {
    private static String BIO_ROOT = "bio";
    private static User fetchedUser = null;
    private FirebaseDatabase firebaseDatabase;

    public FetchUser(FirebaseDatabase reference){
        this.firebaseDatabase = reference;
    }

    private ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            fetchedUser = dataSnapshot.getValue(User.class);
            onFetch(fetchedUser);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void fetchUser(final String userID){

        DatabaseReference bioReference =
                firebaseDatabase.getReference().
                        child(BIO_ROOT).child(userID);

        bioReference.addListenerForSingleValueEvent(listener);

    }

    public abstract void onFetch(User user);
}

