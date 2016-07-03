package com.abahnj.confession;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.abahnj.confession.data.ConfessionContract;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CreateUserActivity extends AppCompatActivity {

    // EditTexts for contact information
    private EditText nameTextInputLayout;
    private EditText passwordTextInputLayout;
    private int sex;
    private int vocation;
    private static Long dob;
    private static Long lastConfession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sexAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sexSpinner.setAdapter(sexAdapter);
        sexSpinner.setOnItemSelectedListener(VS);


        Spinner vocationSpinner = (Spinner) findViewById(R.id.vocation_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> vocationAdapter = ArrayAdapter.createFromResource(this,
                R.array.vocation_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        vocationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        vocationSpinner.setAdapter(vocationAdapter);
        vocationSpinner.setOnItemSelectedListener(VS);

        nameTextInputLayout =
                (EditText) findViewById(R.id.name_editTextView);
        //nameTextInputLayout.addTextChangedListener(nameChangedListener);
        passwordTextInputLayout =
                (EditText) findViewById(R.id.password_editTextView);


        // set FloatingActionButton's event listener
        Button savePersonButton = (Button) findViewById(
                R.id.save_user_button);
        savePersonButton.setOnClickListener(BC);

    }

    View.OnClickListener BC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // hide the virtual keyboard
            ((InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    v.getWindowToken(), 0);
            savePerson(); // save contact to the database

        }
    };


    AdapterView.OnItemSelectedListener VS = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sex_spinner:
                    sex = position;
                    break;
                case R.id.vocation_spinner:
                    vocation = position;
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);


    DatePickerDialog.OnDateSetListener dobL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = new GregorianCalendar(year, month, day);
            dob = c.getTimeInMillis();
        }
    };

    DatePickerDialog.OnDateSetListener lcL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = new GregorianCalendar(year, month, day);
            lastConfession = c.getTimeInMillis();
        }
    };

    // saves contact information to the database
    private void savePerson() {
        // create ContentValues object containing contact's key-value pairs
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_NAME, nameTextInputLayout.getText().toString());
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_PASSWORD, passwordTextInputLayout.getText().toString());
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_BIRTHDATE, dob);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_LASTCONFESSION, lastConfession);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_MARRIED, vocation);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_SEX, sex);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_ACTOFCONTRITION, 2);

        // use Activity's ContentResolver to invoke
        // insert on the AddressBookContentProvider
        Uri newPersonUri = getContentResolver().insert(ConfessionContract.PersonEntry.CONTENT_URI, contentValues);

        if (newPersonUri != null) {
            setResult(RESULT_OK);
        }
        finish();
    }


    public void showDateDob(View view) {
        new DatePickerDialog(this, dobL, year, month, day)
                .show();
    }

    public void showDateLC(View view) {
        new DatePickerDialog(this, lcL, year, month, day)
                .show();

    }
}
