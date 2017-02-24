package com.example.android.languageapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OpeningActivity extends AppCompatActivity {

    // Create a variable for the spinner
    private Spinner grammarChoiceSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        //find the spinner to read user input
        grammarChoiceSpinner = (Spinner) findViewById(R.id.spinner);

        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter grammarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options,
                android.R.layout.simple_spinner_dropdown_item);

        grammarChoiceSpinner.setAdapter(grammarSpinnerAdapter);

    }
}
