package com.example.android.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

        Button goToJournalOrExercises = (Button) findViewById(R.id.goButton);
        goToJournalOrExercises.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JournalExercisesActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter grammarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options,
                android.R.layout.simple_spinner_dropdown_item);


        // Specify dropdown layout style - simple list view with 1 item per line
        grammarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        grammarChoiceSpinner.setAdapter(grammarSpinnerAdapter);

    }


}
