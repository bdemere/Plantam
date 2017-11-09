package com.cpsc310proj.babib.plantam;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 *
 * Backend: Binh and Bemnet
 * Izzi designed layouts.
 *
 */

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

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

        mAuth = FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed [xml]
    public void signInExistingUser(View v)   {
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.cpsc310proj.babib.plantam.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptLogin() {
        /* Get what the users enter from the TextView */
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        /* Return if email or password is blank(empty string) */
        if (email.equals("") || password.equals(""))
            return; // return nothing
        Toast.makeText(this,"Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        // return a Task object so we can add a listener if the user is signed in
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.d("Plantam", "Problem signing in: " + task.getException());
                    showDialogError("There was a problem signing in");
                }
                else {
                    Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }

    private void showDialogError(String message){
        new AlertDialog.Builder(this)
                .setTitle("Please try again...")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show(); //create dialog and show it on screen
    }


}