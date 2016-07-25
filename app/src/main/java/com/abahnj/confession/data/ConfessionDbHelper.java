package com.abahnj.confession.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by abahnj on 5/30/2016.
 */
public class ConfessionDbHelper extends SQLiteAssetHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static String DATABASE_NAME = "confession.db";


    public ConfessionDbHelper(Context context, String DBNAME) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PERSON_TABLE = "CREATE TABLE "
                + PersonEntry.TABLE_NAME
                + " ("
                + PersonEntry._ID + " INTEGER PRIMARY KEY, "
                + PersonEntry.COLUMN_NAME + " VARCHAR, "
                + PersonEntry.COLUMN_SEX + " VARCHAR, "
                + PersonEntry.COLUMN_PASSWORD + " VARCHAR, "
                + PersonEntry.COLUMN_BIRTHDATE + " INTEGER, "
                + PersonEntry.COLUMN_ACTOFCONTRITION + " INTEGER, "
                + PersonEntry.COLUMN_LASTCONFESSION + " INTEGER, "
                + PersonEntry.COLUMN_MARRIED + " INTEGER"
                + " );";

        final String SQL_CREATE_COMMANDMENT_TABLE = "CREATE TABLE "
                + CommandmentEntry.TABLE_NAME
                + " ("
                + CommandmentEntry._ID + " INTEGER PRIMARY KEY, "
                + CommandmentEntry.COLUMN_NUMBER + " INTEGER, "
                + CommandmentEntry.COLUMN_TEXT + " VARCHAR, "
                + CommandmentEntry.COLUMN_CATEGORY + " VARCHAR, "
                + CommandmentEntry.COLUMN_COMMANDMENT + " VARCHAR, "
                + CommandmentEntry.COLUMN_CUSTOMID + " INTEGER"
                + " );";

        db.execSQL(SQL_CREATE_PERSON_TABLE);
        db.execSQL(SQL_CREATE_COMMANDMENT_TABLE);
    }
*/
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
