package com.cpsc310proj.babib.plantam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Backend: Binh and Bemnet
 * Izzi designed layouts.
 */

public class RegisterActivity extends AppCompatActivity {

    // Constants
    public static final String CAL_PREFS = "CalPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;

    // Firebase instance variables
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirm_password);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.register_username);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            createFirebaseUser();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add our own logic to check for a valid password
        String confirmPassword = mConfirmPasswordView.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }


    private void createFirebaseUser() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        Log.d("Flashchat", "password: " + password);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Plantam", "createrUser onComplete " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("Plantam", "Task Error : " + task.isSuccessful());
                    showErrorDialog("Registration attempt failed");
                }
                else {
                    saveDisplayName(); // Save username to local storage after registration is successful
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); // intent to navigate back to login activity
                    finish(); // finish RegisterActivity
                    startActivity(intent); // Start login activity with the intent we created
                }
            }


        });
    }

    // TODO: Save the display name to Shared Preferences (local storage)
    private void saveDisplayName() {
        String displayName = mUsernameView.getText().toString();
        SharedPreferences prefsLocalStorage = getSharedPreferences(CAL_PREFS, 0); // Created a local storage by specifying desired preferences file(CHAT_PREFS)
        prefsLocalStorage.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }


    // TODO: Create an alert dialog to show in case registration failed by [Android Builder Pattern]
    private void showErrorDialog(String message){
        // object is configured before its created
        new AlertDialog.Builder(this)
                .setTitle("Oops" )
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }




}
