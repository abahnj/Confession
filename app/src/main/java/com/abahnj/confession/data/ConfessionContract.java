package com.abahnj.confession.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by abahnj on 5/30/2016.
 */
public class ConfessionContract {

    // ContentProvider's name: typically the package name
    public static final String CONTENT_AUTHORITY =
            "com.abahnj.confession";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PERSON = "person";

    // nested class defines contents of the contacts table
    public static final class PersonEntry implements BaseColumns{



        public static final String TABLE_NAME = "person";

        //Uri for the person
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PERSON)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;



        // column names person table's columns
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_MARRIED = "married";
        public static final String COLUMN_LASTCONFESSION = "lastconfession";
        public static final String COLUMN_ACTOFCONTRITION = "actofcontrition";
        public static final String COLUMN_BIRTHDATE = "birthdate";
        public static final String COLUMN_PASSWORD = "password";

        // creates a Uri for a specific person
        public static Uri buildPersonUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CommandmentsEntry implements BaseColumns{
        
    }
}
