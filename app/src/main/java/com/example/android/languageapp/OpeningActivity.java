package com.example.android.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        Button goToJournal = (Button) findViewById(R.id.journal);
        goToJournal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JournalActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

    }

    // TODO: fix this setOnItemSelectedListener
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter grammarSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_grammar_options,
                android.R.layout.simple_spinner_dropdown_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        grammarSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Apply the adapter to the spinner
        grammarChoiceSpinner.setAdapter(grammarSpinnerAdapter);

        // Create the intent to send the position to journal activity
        grammarChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), JournalActivity.class);
                intent.putExtra("selected", position);
                Log.d("in spinner selected", "position of spinner:" + position );
                startActivity(intent);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGrammar = 0;
            }
        });
    }


}
