package com.cpsc310proj.babib.plantam.Firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.CalendarActivity;
import com.cpsc310proj.babib.plantam.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class FirebaseUserAuthentication extends AppCompatActivity implements View.OnClickListener{
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mSignUpButton;
    private Button mSignInButton;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("users");


        mEmailEditText = (EditText)findViewById(R.id.user_auth_email);
        mPasswordEditText = (EditText)findViewById(R.id.user_auth_password);
        mSignUpButton = (Button)findViewById(R.id.user_auth_sign_up);
        mSignInButton = (Button)findViewById(R.id.user_auth_sign_in);
        mProgressDialog = new ProgressDialog(this);

        mSignUpButton.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void signUp(){
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        Log.d("resgister: ", email + password);
        if(email.isEmpty()){
            //if email is empty return
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(password.isEmpty()){
            //if password is empty return
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            mProgressDialog.cancel();
                            if(task.isSuccessful()) {
                                Toast.makeText(FirebaseUserAuthentication.this, "Worked",
                                        Toast.LENGTH_SHORT).show();
                                signIn(true);

                            }else{
                                Toast.makeText(FirebaseUserAuthentication.this, "Failed",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("Sign up: ", "createUserWithEmail:failure", task.getException());

                            }


                        }

                    });





    }

    public void signIn(final boolean write){
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("logging in", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("logging in", "signInWithEmail:failed", task.getException());
                            Toast.makeText(FirebaseUserAuthentication.this, "auth_failed",
                                    Toast.LENGTH_SHORT).show();
                        } else{
                            if(write) mUsersDatabaseReference.child(mAuth.getCurrentUser().getUid()).setValue(true);


                            finish();
                            Intent intent = new Intent(FirebaseUserAuthentication.this, CalendarActivity.class);
                            startActivity(intent);
                            Toast.makeText(FirebaseUserAuthentication.this, "auth_success",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if(v == mSignUpButton){
            signUp();
        } if(v == mSignInButton){
            signIn(false);
        }
    }
}
