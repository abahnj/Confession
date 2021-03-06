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
    static final String CONTENT_AUTHORITY =
            "com.abahnj.confession";
    static final String PATH_PERSON = "PERSON";
    static final String PATH_COMMANDMENTS = "COMMANDMENTS";
    static final String PATH_SIN = "SIN";
    static final String PATH_SIN_ACTIVE = "SIN_ACTIVE";
    static final String PATH_PERSON_2_SIN = "PERSON_2_SIN";
    static final String PATH_PRAYERS = "PRAYERS";
    static final String PATH_INSPIRATION = "INSPIRATION";
    static final String PATH_GUIDE = "guide_main";

    // base URI used to interact with the ContentProvider
    private static final Uri BASE_CONTENT_URI =
            Uri.parse("content://" + CONTENT_AUTHORITY);

    // nested class defines contents of the contacts table
    public static final class PersonEntry implements BaseColumns{


        //Uri for the person
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PERSON)
                .build();
        // column names person table's columns
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_SEX = "SEX";
        public static final String COLUMN_MARRIED = "MARRIED";
        public static final String COLUMN_LASTCONFESSION = "LASTCONFESSION";
        public static final String COLUMN_ACTOFCONTRITION = "ACTOFCONTRITION";
        public static final String COLUMN_BIRTHDATE = "BIRTHDATE";
        public static final String COLUMN_PASSWORD = "PASSWORD";
        static final String TABLE_NAME = "PERSON";
        static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;
        static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        // creates a Uri for a specific person
        public static Uri buildPersonUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class CommandmentEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_COMMANDMENTS)
                .build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMANDMENTS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COMMANDMENTS;
        // column names commandment table's columns
        public static final String COLUMN_NUMBER = "NUMBER";
        public static final String COLUMN_TEXT = "TEXT";
        public static final String COLUMN_CATEGORY = "CATEGORY";
        public static final String COLUMN_COMMANDMENT = "COMMANDMENT";
        public static final String COLUMN_CUSTOMID = "CUSTOM_ID";
        static final String TABLE_NAME = "COMMANDMENTS";

        // creates a Uri for a specific person
        public static Uri buildCommandmentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SinEntry implements BaseColumns{
        public static final String TABLE_NAME = "SIN";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SIN)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIN;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIN;


        public static final String COLUMN_COMMANDMENT_ID = "COMMANDMENT_ID";
        public static final String COLUMN_ADULT = "ADULT";
        public static final String COLUMN_SINGLE = "SINGLE";
        public static final String COLUMN_MARRIED = "MARRIED";
        public static final String COLUMN_RELIGIOUS = "RELIGIOUS";
        public static final String COLUMN_PRIEST = "PRIEST";
        public static final String COLUMN_TEEN = "TEEN";
        public static final String COLUMN_FEMALE = "FEMALE";
        public static final String COLUMN_MALE = "MALE";
        public static final String COLUMN_CHILD = "CHILD";
        public static final String COLUMN_CUSTOM_ID = "CUSTOM_ID";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

        // creates a Uri for a specific person
        public static Uri buildSinUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class SinActiveEntry implements BaseColumns{
        public static final String TABLE_NAME = "SIN_ACTIVE";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_SIN_ACTIVE)
                .build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIN;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SIN;


        public static final String COLUMN_COMMANDMENT_ID = "COMMANDMENT_ID";
        public static final String COLUMN_ADULT = "ADULT";
        public static final String COLUMN_SINGLE = "SINGLE";
        public static final String COLUMN_MARRIED = "MARRIED";
        public static final String COLUMN_RELIGIOUS = "RELIGIOUS";
        public static final String COLUMN_PRIEST = "PRIEST";
        public static final String COLUMN_TEEN = "TEEN";
        public static final String COLUMN_FEMALE = "FEMALE";
        public static final String COLUMN_MALE = "MALE";
        public static final String COLUMN_CHILD = "CHILD";
        public static final String COLUMN_CUSTOM_ID = "CUSTOM_ID";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

        // creates a Uri for a specific person
        public static Uri buildSinUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class PersonToSinEntry implements BaseColumns{
        public static final String TABLE_NAME = "PERSON_2_SIN";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PERSON_2_SIN)
                .build();

        public static final String COLUMN_PERSON_ID = "PERSON_ID";
        public static final String COLUMN_SINS_ID = "SINS_ID";
        public static final String COLUMN_COUNT = "COUNT";
        public static final String COLUMN_EDITED = "EDITED";
        public static final String COLUMN_DELETED = "DELETED";

        // creates a Uri for a specific person
        public static Uri buildSinUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class PrayersEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRAYERS)
                .build();
        public static final String COLUMN_PRAYERNAME = "PRAYERNAME";
        public static final String COLUMN_PRAYERTEXT = "PRAYERTEXT";
        public static final String COLUMN_GROUPNAME = "GROUPNAME";
        public static final String COLUMN_CUSTOM = "CUSTOM";
        static final String TABLE_NAME = "PRAYERS";

        public static Uri buildPrayersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);

        }
    }

    public static final class InspirationEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_INSPIRATION)
                .build();
        public static final String COLUMN_QUOTE = "QUOTE";
        public static final String COLUMN_AUTHOR = "AUTHOR";
        static final String TABLE_NAME = "INSPIRATION";

    }

    public static final class GuideEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_GUIDE)
                .build();
        public static final String COLUMN_HEADER_ID ="h_id";
        public static final String COLUMN_GUIDE_ID="g_id";
        public static final String COLUMN_GUIDE_TITLE="g_title";
        public static final String COLUMN_GUIDE_TEXT="text";
        public static final String COLUMN_IMG_NAME="img_name";
        static final String TABLE_NAME = "guide_main";

    }


}
