// File: Binh Vo
// Authors: Bemnet, Binh, Izzi
package com.cpsc310proj.babib.plantam.Firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Layouts.CalendarLayout.CalendarActivity;
import com.cpsc310proj.babib.plantam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;



public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mSignUp;
    private Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mSignIn = (Button) findViewById(R.id.login_sign_in_button);
        mSignUp = (Button) findViewById(R.id.login_register_button);
        mAuth = FirebaseAuth.getInstance();

        // Setting listeners to sign in and sign up
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {signInExistingUser(v);}
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {signUp(v);}
        });


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(mAuthListener != null){
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
    }

    // Executed when Sign in button pressed [xml]
    public void signInExistingUser(View v)   {
        attemptLogin();
    }

    // Executed when Register button pressed
    public void signUp(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    // Check if user information are entered correctly
    private void attemptLogin() {
        /* Get what the users enter from the TextView */
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        /* Return if email or password is blank(empty string) */
        if (email.equals("") || password.equals(""))
            return; // return nothing
        Toast.makeText(this,"Login in progress...", Toast.LENGTH_SHORT).show();


        // Check if existing user is on the firebase and
        // return a Task object to see if the user was able to sign in
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                    finish();
                    startActivity(intent);
                }
                else{
                    Log.d("Plantam", "Problem signing in: " + task.getException());
                    //showDialogSignInError("There was problem sign in your account");
                    showTest("Error signing in");
                }}});

    }

    // Display error message when user tried to sign in
    private void showDialogSignInError(String message){
        new AlertDialog.Builder(this)
                .setTitle(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showTest(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setItems(new CharSequence[]{"button 1", "button 2"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:
                                Toast.makeText(LoginActivity.this, "clicked 1", 0).show();
                                break;
                            case 1:
                                Toast.makeText(LoginActivity.this, "clicked 2", 0).show();
                                break;
                            case 2:
                                Toast.makeText(LoginActivity.this, "clicked 3", 0).show();
                                break;
                            case 3:
                                Toast.makeText(LoginActivity.this, "clicked 4", 0).show();
                                break;
                        }
                    }
                });
        builder.create().show();
    }





}