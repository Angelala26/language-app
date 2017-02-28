package com.example.android.languageapp;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.languageapp.R.id.journalQuestion;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalActivity extends AppCompatActivity {

    //set label for journal questions
    public TextView journalQuestionLabel;
    public int counter = 0;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        //Send saved preferences here
        preferences = getSharedPreferences("Selection", MODE_PRIVATE);
        int selection = preferences.getInt("grammarOption", -1);

        // Initialize the journal question label
        journalQuestionLabel = (TextView) findViewById(journalQuestion);

        //Load the first question depending on what the user chose
        if (selection == 1) {
            journalQuestionLabel.setText(getResources().getString(R.string.question_one_present));
        } else if (selection == 2) {
            journalQuestionLabel.setText(getResources().getString(R.string.question_one_past));
        } else if (selection == 3) {
            journalQuestionLabel.setText(getResources().getString(R.string.question_one_future));
        } else if (selection == 4) {
            journalQuestionLabel.setText(getResources().getString(R.string.question_one_conditional));
        } else {
            Toast.makeText(getApplicationContext(), "Please make a grammar selection", Toast.LENGTH_LONG).show();
        }

        nextQuestionButton();
    }

    // Make a method to go to the next question depending on which grammar selection the user chose
    private void nextQuestionButton() {
        // Set button to go to the next question (the arrary)
        // Initialize the journal question label
        journalQuestionLabel = (TextView) findViewById(journalQuestion);

        // Connect with the next question button
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);

        //Send saved preferences here
        preferences = getSharedPreferences("Selection", MODE_PRIVATE);
        final int selection = preferences.getInt("grammarOption", -1);

        // Get the questions array from the resources
        Resources res = getResources();
        final String[] presentQuestionArray = res.getStringArray(R.array.present_tense_questions);
        final String[] pastQuestionArray = res.getStringArray(R.array.past_tense_questions);
        final String[] futureQuestionArray = res.getStringArray(R.array.future_tense_questions);
        final String[] conditionalQuestionArray = res.getStringArray(R.array.conditional_tense_questions);

        // Set up the onClickListener with the questions and next button
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (selection == 1) {
                    journalQuestionLabel.setText(presentQuestionArray[counter + 1]);
                    if (counter < (presentQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        counter++;
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                    }
                } else if (selection == 2) {
                    journalQuestionLabel.setText(pastQuestionArray[counter + 1]);
                    if (counter < (pastQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        counter++;
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                    }
                } else if (selection == 3) {
                    journalQuestionLabel.setText(futureQuestionArray[counter + 1]);
                    if (counter < (futureQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        counter++;
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                    }
                } else if (selection == 4) {
                    journalQuestionLabel.setText(conditionalQuestionArray[counter + 1]);
                    if (counter < (conditionalQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        counter++;
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                    }
                }
            }
        });
    }
}
