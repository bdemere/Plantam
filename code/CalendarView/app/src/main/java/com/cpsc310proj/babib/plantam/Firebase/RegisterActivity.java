package com.cpsc310proj.babib.plantam.Firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author GROUP 4
 * @version 1.0
 */

public class RegisterActivity extends AppCompatActivity {

    // Constants
    public static final String CAL_PREFS = "CalPrefs";
    public static final String DISPLAY_NAME_KEY = "username";


    // UI references.
    private AutoCompleteTextView mFullName;
    private AutoCompleteTextView mClassYear;
    private AutoCompleteTextView mEmail;
    private AutoCompleteTextView mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;


    // Firebase instance variables
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = (AutoCompleteTextView) findViewById(R.id.register_name);
        mClassYear = (AutoCompleteTextView) findViewById(R.id.register_classyear);
        mUsername = (AutoCompleteTextView) findViewById(R.id.register_username);
        mEmail = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPassword = (EditText) findViewById(R.id.register_password);
        mConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);

        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference("bio");

        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

    }

    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {
        // Reset errors displayed in the form.
        mEmail.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            signUp();
        }
    }

    private boolean isEmailValid(String email) {
        //add more checking logic here.
        return email.contains("@trincoll.edu");
    }

    private boolean isPasswordValid(String password) {
        //Add our own logic to check for a valid password
        String confirmPassword = mConfirmPassword.getText().toString();
        return confirmPassword.equals(password) && password.length() > 4;
    }


    private void signUp() {
        String name = mFullName.getText().toString();
        String classyear = mClassYear.getText().toString();
        String username = mUsername.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        final User user = new User(name, classyear, username, email); // Create the user

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("plantam", "createrUser onComplete " + task.isSuccessful());
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Account is created",
                            Toast.LENGTH_SHORT).show();
                    FBDatabase.writeUser(mAuth.getCurrentUser().getUid());
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    addUserBioToDatabase(user);
                    saveDisplayName(); // Save username to local storage after registration is successful
                    finish(); // finish RegisterActivity
                    startActivity(intent); // Start login activity with the intent we created
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Failed",
                            Toast.LENGTH_SHORT).show();
                    Log.d("Sign up: ", "createUserWithEmail:failure", task.getException());
                }}}
            );
    }

    // Save the display name to Shared Preferences (local storage)
    private void saveDisplayName() {
        String displayName = mUsername.getText().toString();
        SharedPreferences prefsLocalStorage = getSharedPreferences(CAL_PREFS, 0); // Created a local storage by specifying desired preferences file(CHAT_PREFS)
        prefsLocalStorage.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }


    /**
     * Take a User and add that person information to the database under the node 'bio'
     * @param user User's information
     */
    private void addUserBioToDatabase(User user) {
        String id = mAuth.getCurrentUser().getUid(); // Get current user's id
        user.setId(id);
        mUsersDatabase.child(id).setValue(user);
        Log.d("UserAdded", "User is added to the firebase");

    }






}
