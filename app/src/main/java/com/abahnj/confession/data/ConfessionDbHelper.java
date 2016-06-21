package com.abahnj.confession.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.abahnj.confession.data.ConfessionContract.PersonEntry;
/**
 * Created by abahnj on 5/30/2016.
 */
public class ConfessionDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "confession.db";

    public ConfessionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
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

        db.execSQL(SQL_CREATE_PERSON_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
