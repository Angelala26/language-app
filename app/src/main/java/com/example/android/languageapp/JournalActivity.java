package com.example.android.languageapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalActivity extends AppCompatActivity{

    public TextView journalQuestion;
    public int counter;

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

        // Set button to go to the next question (an arrary)
        // Initialize the journal question label
        journalQuestion = (TextView) findViewById(R.id.journalQuestion);

        // Connect with the button
        Button nextQuestionButton = (Button) findViewById(R.id.nextButton);

        // Get the questions array from the resources
        Resources res = getResources();
        final String[] presentQuestionArray = res.getStringArray(R.array.present_tense_question);

        // Set up the onClickListener with the questions and next button
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                journalQuestion.setText(presentQuestionArray[counter]);
                if(counter < (presentQuestionArray.length)) {
                    counter++;
                }
                else {
                    journalQuestion.setText("Sorry, no more questions.");
                }
            }
        });
    }
}
