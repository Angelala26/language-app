package com.example.android.languageapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);


        // Set button to go back to opening page
        Button goBackToJournalExercises = (Button) findViewById(R.id.backToJournalExercises);
        goBackToJournalExercises.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), JournalExercisesActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
