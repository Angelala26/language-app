package com.example.android.languageapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Angela on 3/16/17.
 */

public class JournalContract {
    private JournalContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.languageapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_JOURNAL = "journal";

    //create inner class for pets table
    public static abstract class JournalEntry implements BaseColumns {

        public static final String TABLE_NAME = "journal";

        //add constants for column headers
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_ANSWER = "answer";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_JOURNAL);

        /**
         * The MIME type of the {@link #CONTENT_URI} for the answers.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOURNAL;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single answer.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_JOURNAL;

    }
}
