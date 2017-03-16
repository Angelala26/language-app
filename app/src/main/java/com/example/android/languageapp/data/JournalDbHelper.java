package com.example.android.languageapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Angela on 3/16/17.
 */

public class JournalDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "journalanswers.db";
    public static final int DATABASE_VERSION = 1;

    final String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + JournalContract.JournalEntry.TABLE_NAME + " (" +
            JournalContract.JournalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            JournalContract.JournalEntry.COLUMN_USER_ANSWER + " TEXT NOT NULL, ";

    public JournalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
