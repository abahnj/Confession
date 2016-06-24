package com.abahnj.confession;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.abahnj.confession.data.ConfessionContract.PersonEntry;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, PasswordFragment.PasswordDialogListener{

    private final String LOG_TAG = LoginActivity.class.getSimpleName();
    static final int PERSON_SAVED_REQUEST = 2;
    private RecyclerView recyclerView;
    private static final int PERSON_LOADER = 0;// identifies Loader
    private PersonAdapter personAdapter; // adapter for recyclerView


    private static final String[] PERSON_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            PersonEntry._ID,
            PersonEntry.COLUMN_NAME,
            PersonEntry.COLUMN_PASSWORD

    };

    // These indices are tied to PERSON_COLUMNS.  If PERSON_COLUMNS changes, these
    // must change.
    static final int COL_PERSON_ID = 0;
    static final int COL_PERSON_NAME = 1;
    static final int COL_PERSON_PASSWORD = 2;

    private static final String[] PASSWORD_COLUMNS = {
            // In this case the id needs to be fully qualified with a table name, since
            // the content provider joins the location & weather tables in the background
            // (both have an _id column)
            // On the one hand, that's annoying.  On the other, you can search the weather table
            // using the location set by the user, which is only in the Location table.
            // So the convenience is worth it.
            PersonEntry.COLUMN_PASSWORD

    };

    static final int COL_PASSWORD = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getLoaderManager().initLoader(PERSON_LOADER, null, this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewL);

        // recyclerView should display items in a vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // create recyclerView's adapter and item click listener
        personAdapter = new PersonAdapter(
                new PersonAdapter.PersonClickListener() {
                    @Override
                    public void onClick(Uri contactUri, int position) {
                        confirmPasswordDialog(contactUri);
                    }
                }
        );
        recyclerView.setAdapter(personAdapter); // set the adapter

    }



    public void createUser (View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivityForResult(intent,PERSON_SAVED_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERSON_SAVED_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.person_added, Toast.LENGTH_LONG).show();
                personAdapter.notifyDataSetChanged();
            }
        }
    }


    // called by LoaderManager to create a Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // create an appropriate CursorLoader based on the id argument;
        // only one Loader in this fragment, so the switch is unnecessary
        String sortOrder = PersonEntry.COLUMN_NAME + " COLLATE NOCASE ASC";

        return new CursorLoader(this,
                PersonEntry.CONTENT_URI, // Uri of contacts table
                PERSON_COLUMNS, // null projection returns all columns
                null, // null selection returns all rows
                null, // no selection arguments
                sortOrder); // sort order
    }


    // called by LoaderManager when loading completes
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        personAdapter.swapCursor(data);
    }

    // called by LoaderManager when the Loader is being reset
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        personAdapter.swapCursor(null);
    }

    public void confirmPasswordDialog(Uri personUri) {
        Bundle args = new Bundle();
        args.putParcelable(PasswordFragment.PERSON_URI, personUri);

        DialogFragment newFragment = new PasswordFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "password");
    }



    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String password, Uri mUri) {
//        Toast.makeText(this, mUri.toString(), Toast.LENGTH_LONG).show();

        if (password.contentEquals(confirmPassword(mUri))){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setData(mUri);
            startActivity(intent);
        }
        else {Toast.makeText(this, R.string.incorrect_password, Toast.LENGTH_LONG).show();}


    }
    private String confirmPassword(Uri mUri) {
        String password = null;
        Cursor passwordCursor = getContentResolver().query(mUri,
                PASSWORD_COLUMNS,
                null,
                null,
                null);

        if (passwordCursor != null && passwordCursor.moveToFirst()) {
            int passwordIndex = passwordCursor.getColumnIndex(PersonEntry.COLUMN_PASSWORD);
            password = passwordCursor.getString(passwordIndex);
            passwordCursor.close();
        }
        return password;
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }

}
