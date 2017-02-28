package com.example.android.languageapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class OpeningActivity extends AppCompatActivity {

    // Create a variable for the spinner
    private Spinner grammarChoiceSpinner;

    // Create a variable to store the spinner's choice
    private int mGrammar;

    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        //find the spinner to read user input
        grammarChoiceSpinner = (Spinner) findViewById(R.id.spinner);

        setupSpinner();

        //Set up the intent and OnClickListener for the button to go to journal or exercises
        final Button chooseGrammarButton = (Button) findViewById(R.id.goButton);
        final Button journalButton = (Button) findViewById(R.id.journal);
        final Button exercisesButton = (Button) findViewById(R.id.exercises);

        // make the Go button disappear and the exercises or grammar button appear
        chooseGrammarButton.setVisibility(View.VISIBLE);
        journalButton.setVisibility(View.GONE);
        exercisesButton.setVisibility(View.GONE);
        chooseGrammarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                chooseGrammarButton.setVisibility(View.INVISIBLE);
                journalButton.setVisibility(View.VISIBLE);
                exercisesButton.setVisibility(View.VISIBLE);
            }
        });

        //Set button to go to the journal page
        journalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JournalActivity.class);
                if (grammarChoiceSpinner.getSelectedItemPosition() != 0) {
                    startActivityForResult(myIntent, 0);
                }
                else {
                    Toast.makeText(getBaseContext(), "Please make a grammar selection",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        final ArrayAdapter grammarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_grammar_options,
                android.R.layout.simple_spinner_dropdown_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        grammarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Apply the adapter to the spinner
        grammarChoiceSpinner.setAdapter(grammarSpinnerAdapter);

        //Create shared preferences to store the spinner selection
        SharedPreferences preferences = getApplicationContext().getSharedPreferences
                ("Selection", MODE_PRIVATE);

        editor = preferences.edit();

        // Create the intent to save the position
        grammarChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //receive the string of the option and store it
                int grammarOptionPosition = grammarChoiceSpinner.getSelectedItemPosition();
                //put the string in the editor
                editor.putInt("grammarOption", grammarOptionPosition);
                editor.commit();
                //make a toast so the user knows if it's not "select"
                if (grammarOptionPosition != 0) {
                    Toast.makeText(getApplicationContext(), "Choice saved.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            // Define onNothingSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGrammar = 0;
            }
        });
    }


}
