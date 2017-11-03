package com.cpsc310proj.babib.plantam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper { //change variables to public to try adding event to database
    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "eventsList.db";
    // Contacts table name
    public static final String TABLE_EVENTS = "events";
    // Shops Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DATE = "date";
    public static final String KEY_START = "start_time";
    public static final String KEY_END = "end_time";
    public static final String KEY_DESCRP = "description";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "(EVENT NO. "
        + KEY_ID + ", TITLE "+ KEY_TITLE + ", ON" + KEY_DATE + ", FROM " + KEY_START + ", TO " + KEY_END
        + KEY_DESCRP + ", WITH DESCRIPTION: " + KEY_DESCRP + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
// Creating tables again
        onCreate(db);
    }
}
