package com.example.android.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class OpeningActivity extends AppCompatActivity {

    // Create a variable for the spinner
    private Spinner grammarChoiceSpinner;

    // Create a variable to store the spinner's choice
    private int mGrammar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        //find the spinner to read user input
        grammarChoiceSpinner = (Spinner) findViewById(R.id.spinner);

        setupSpinner();

        //Set up the intent and OnClickLIstener for the button to go to journal or exercises
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

        //Apply the adapter to the spinner
        grammarChoiceSpinner.setAdapter(grammarSpinnerAdapter);

        // Set the integer mSelected to the constant values
        grammarChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.grammar_present))) {
                        mGrammar = 1;
                    } else if (selection.equals(getString(R.string.grammar_past))) {
                        mGrammar = 2;
                    } else if (selection.equals(getString(R.string.grammar_future))) {
                        mGrammar = 3;
                    } else if (selection.equals(getString(R.string.grammar_conditional))) {
                        mGrammar = 4;
                    }
                    else {
                        mGrammar = 0;
                    }
                }
                Intent intent = new Intent(getApplicationContext(), JournalActivity.class);
                intent.putExtra("data", mGrammar);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGrammar = 0;
            }
        });
    }


}
