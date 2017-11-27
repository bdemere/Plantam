package com.cpsc310proj.babib.plantam.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper { //change variables to public to try adding event to database
    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "EVENT_DATABASE";
    // Contacts table name
    public static final String TABLE_NAME = DATABASE_NAME + "_events";


    // Shops Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_START = "start_time";
    public static final String KEY_END = "end_time";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_CATEGORY = "category";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                        "CREATE TABLE " +
                        TABLE_NAME + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_ID + " TEXT, " +
                        KEY_TITLE + " TEXT, " +
                        KEY_DATE + " TEXT, " +
                        KEY_START +  " TEXT, " +
                        KEY_END + " TEXT, " +
                        KEY_DESCRIPTION + " TEXT, " +
                        KEY_CATEGORY + " TEXT " +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Sort table based on starting time
        final String sortSQL = "select * from " + TABLE_NAME + " ORDER by " + KEY_START + " asc";
        db.execSQL(sortSQL);

        // Creating tables again
        onCreate(db);
    }
}
