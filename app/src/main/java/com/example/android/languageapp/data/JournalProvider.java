package com.example.android.languageapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Angela on 3/16/17.
 */

public class JournalProvider extends ContentProvider {
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = JournalProvider.class.getSimpleName();
    /**
     * URI matcher code for the content URI for the answers table
     */
    private static final int ANSWERS = 100;
    /**
     * URI matcher code for the content URI for a single pet in the pets table
     */
    private static final int ANSWERS_ID = 101;
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        sUriMatcher.addURI(JournalContract.CONTENT_AUTHORITY, JournalContract.PATH_JOURNAL, ANSWERS);
        sUriMatcher.addURI(JournalContract.CONTENT_AUTHORITY, JournalContract.PATH_JOURNAL + "/#", ANSWERS_ID);
    }

    //Database helper object
    private JournalDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new JournalDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ANSWERS:
                // For the ANSWERS code, query the answers table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the answers table.
                cursor = database.query(JournalContract.JournalEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case ANSWERS_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = JournalContract.JournalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(JournalContract.JournalEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ANSWERS:
                return JournalContract.JournalEntry.CONTENT_LIST_TYPE;
            case ANSWERS_ID:
                return JournalContract.JournalEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ANSWERS:
                return insertAnswer(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertAnswer(Uri uri, ContentValues values) {
        // Check that the name is not null
        String answer = values.getAsString(JournalContract.JournalEntry.COLUMN_USER_ANSWER);
        if (answer == null) {
            throw new IllegalArgumentException("Requires an answer.");
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(JournalContract.JournalEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ANSWERS:
                return updateAnswer(uri, contentValues, selection, selectionArgs);
            case ANSWERS_ID:
                // For the ANSWERS_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = JournalContract.JournalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateAnswer(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update answers in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more answers).
     * Return the number of rows that were successfully updated.
     */
    private int updateAnswer(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Update the selected pets in the pets database table with the given ContentValues
        // Check that the name is not null
        if (values.containsKey(JournalContract.JournalEntry.COLUMN_USER_ANSWER)) {
            String answer = values.getAsString(JournalContract.JournalEntry.COLUMN_USER_ANSWER);
            if (answer == null) {
                throw new IllegalArgumentException("Requires an answer.");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(JournalContract.JournalEntry.TABLE_NAME, values,
                selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows that were affected
        return rowsUpdated;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ANSWERS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(JournalContract.JournalEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;
            case ANSWERS_ID:
                // Delete a single row given by the ID in the URI
                selection = JournalContract.JournalEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(JournalContract.JournalEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }
}
