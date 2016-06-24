package com.abahnj.confession.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.abahnj.confession.R;
import com.abahnj.confession.data.ConfessionContract.PersonEntry;

/**
 * Created by abahnj on 5/31/2016.
 */
public class ConfessionProvider extends ContentProvider {

    //used to access the database
    private ConfessionDbHelper mDbHelper;

    private static final int PERSON = 100;
    private static final int ONE_PERSON = 101;
    private static final int COMMANDMENTS = 200 ;

    //used to figure out the URI to match
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private Cursor getPersonWithId(Uri uri, String[] projection, String sortOrder) {
        String personId = PersonEntry._ID + "=" + uri.getLastPathSegment();
        return  mDbHelper.getReadableDatabase().query(
                ConfessionContract.PersonEntry.TABLE_NAME,
                projection,
                personId,
                null,
                null,
                null,
                sortOrder
        );
    }

    private static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ConfessionContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        matcher.addURI(authority, ConfessionContract.PATH_PERSON, PERSON);
        matcher.addURI(authority, ConfessionContract.PATH_PERSON + "/#", ONE_PERSON);
        matcher.addURI(authority, ConfessionContract.PATH_COMMANDMENTS, COMMANDMENTS);

        // 3) Return the new matcher!
        return matcher;
    }
    @Override
    public boolean onCreate() {
        //create the new Confession Database
        mDbHelper = new ConfessionDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        // create SQLiteQueryBuilder for querying person table

        switch (sUriMatcher.match(uri)){
            case ONE_PERSON:
                retCursor = getPersonWithId(uri, projection, sortOrder);
                break;
            case PERSON:
                retCursor = mDbHelper.getReadableDatabase().query(
                        ConfessionContract.PersonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case COMMANDMENTS:
                retCursor = mDbHelper.getReadableDatabase().query(
                        ConfessionContract.CommandmentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException(
                        getContext().getString(R.string.invalid_query_uri) + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case PERSON:
                return ConfessionContract.PersonEntry.CONTENT_TYPE;
            case ONE_PERSON:
                return ConfessionContract.PersonEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PERSON: {
                long _id = db.insert(PersonEntry.TABLE_NAME, null, values);
                if ( _id > 0 ) {
                    returnUri = PersonEntry.buildPersonUri(_id);
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                else
                    throw new SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        db.close();
        return returnUri;    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Student: This is a lot like the delete function.  We return the number of rows impacted
        // by the update.
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case ONE_PERSON: {
                String id = uri.getLastPathSegment();
                rowsDeleted = db.delete(PersonEntry.TABLE_NAME, PersonEntry._ID + "=" + id, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
            db.close();
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Student: This is a lot like the delete function.  We return the number of rows impacted
        // by the update.
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case ONE_PERSON: {

                String id = uri.getLastPathSegment();
                rowsUpdated = db.update(PersonEntry.TABLE_NAME, values, PersonEntry._ID + "=" + id, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
            db.close();
        }

        return rowsUpdated;
    }
}
