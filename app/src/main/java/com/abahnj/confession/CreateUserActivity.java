package com.abahnj.confession;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abahnj.confession.data.ConfessionContract;
import com.github.orangegangsters.lollipin.lib.managers.AppLock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CreateUserActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ENABLE = 11;
    private static String name;
    private static Long dob;
    private static Long lastConfession;
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    // EditTexts for contact information
    private TextInputLayout nameTextInputLayout;
    private TextView dob_tv;
    public static final String PREFS_NAME = "MyPrefsFile";

    DatePickerDialog.OnDateSetListener dobL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = new GregorianCalendar(year, month, day);
            dob = c.getTimeInMillis();
            SimpleDateFormat dateInstance = new SimpleDateFormat("d'th' MMM yyyy", Locale.getDefault());
            dob_tv.setText(dateInstance.format(dob));
        }
    };
    private TextView lc_tv;
    DatePickerDialog.OnDateSetListener lcL = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = new GregorianCalendar(year, month, day);
            lastConfession = c.getTimeInMillis();
            SimpleDateFormat dateInstance = new SimpleDateFormat("d'th' MMM yyyy", Locale.getDefault());
            lc_tv.setText(dateInstance.format(lastConfession));
        }
    };
    private int sex;
    private int vocation;
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

        dob_tv = (TextView) findViewById(R.id.dob_button);
        lc_tv = (TextView) findViewById(R.id.last_confession_button);

        nameTextInputLayout =
                (TextInputLayout) findViewById(R.id.name_editTextView);
        //nameTextInputLayout.addTextChangedListener(nameChangedListener);

        // set FloatingActionButton's event listener
        Button savePersonButton = (Button) findViewById(
                R.id.save_user_button);
        savePersonButton.setOnClickListener(BC);

    }

    // saves contact information to the database
    private void savePerson() {
        name = nameTextInputLayout.getEditText().getText().toString();
        // create ContentValues object containing contact's key-value pairs
        ContentValues contentValues = new ContentValues();
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_NAME, name);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_BIRTHDATE, dob);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_LASTCONFESSION, lastConfession);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_MARRIED, vocation);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_SEX, sex);
        contentValues.put(ConfessionContract.PersonEntry.COLUMN_ACTOFCONTRITION, 2);

        // use Activity's ContentResolver to invoke
        // insert on the AddressBookContentProvider
        Uri newPersonUri = getContentResolver().insert(ConfessionContract.PersonEntry.CONTENT_URI, contentValues);

        if (newPersonUri != null && validate()) {
            setResult(RESULT_OK);
            SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("username", name );
            editor.putInt("sex", sex) ;
            editor.putInt("vocation", vocation);
            editor.putInt("actOfContrition", 2);
            editor.putLong("birthDate", dob);
            editor.putLong("lastConfession", lastConfession);
            editor.putInt("id", Integer.valueOf(newPersonUri.getLastPathSegment()));
            editor.apply();

            Intent intent = new Intent(this, ConfessionPinActivity.class);
            intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
            startActivityForResult(intent, REQUEST_CODE_ENABLE);

        }

    }


    public void showDateDob(View view) {
        new DatePickerDialog(this, dobL, year, month, day)
                .show();
    }

    public void showDateLC(View view) {
        new DatePickerDialog(this, lcL, year, month, day)
                .show();

    }

    public boolean validate() {
        boolean valid = true;

        String name = nameTextInputLayout.getEditText().getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameTextInputLayout.setError("at least 3 characters");
            valid = false;
        } else {
            nameTextInputLayout.setError(null);
        }


        return valid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                Toast.makeText(this, "PinCode enabled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
