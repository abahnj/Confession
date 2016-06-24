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

    public static final String PATH_PERSON = "PERSON";
    public static final String PATH_COMMANDMENTS = "COMMANDMENTS";

    // nested class defines contents of the contacts table
    public static final class PersonEntry implements BaseColumns{



        public static final String TABLE_NAME = "PERSON";

        //Uri for the person
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PERSON)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;



        // column names person table's columns
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SEX = "SEX";
        public static final String COLUMN_MARRIED = "MARRIED";
        public static final String COLUMN_LASTCONFESSION = "LASTCONFESSION";
        public static final String COLUMN_ACTOFCONTRITION = "ACTOFCONTRITION";
        public static final String COLUMN_BIRTHDATE = "BIRTHDATE";
        public static final String COLUMN_PASSWORD = "PASSWORD";

        // creates a Uri for a specific person
        public static Uri buildPersonUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CommandmentEntry implements BaseColumns{

        public static final String TABLE_NAME = "COMMANDMENTS";

        //Uri for the commandments table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_COMMANDMENTS)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMANDMENTS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMANDMENTS;

        // column names person table's columns
        public static final String COLUMN_NUMBER = "NUMBER";
        public static final String COLUMN_TEXT = "TEXT";
        public static final String COLUMN_CATEGORY = "CATEGORY";
        public static final String COLUMN_COMMANDMENT = "COMMANDMENT";
        public static final String COLUMN_CUSTOMID = "CUSTOM_ID";

    }



}
