package com.example.android.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_exercises);

        //Set button to go to the journal page
        Button goToJournal = (Button) findViewById(R.id.journal);
        goToJournal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JournalActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Set button to go back to opening page
        Button goBackToOpening = (Button) findViewById(R.id.backToOpening);
        goBackToOpening.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), OpeningActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
