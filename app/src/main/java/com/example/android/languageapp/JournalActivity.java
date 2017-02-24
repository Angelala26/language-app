package com.example.android.languageapp;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.android.languageapp.R.id.journalQuestion;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalActivity extends AppCompatActivity {

    //set label for journal questions
    public TextView journalQuestionLabel;
    public int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);


        nextQuestionButton();

    }

    //TODO: this logic is messed up - the question starts blank, not with the first question
    // and the last question won't show up so I added a random one at the end
    private void nextQuestionButton() {
        // Set button to go to the next question (an arrary)
        // Initialize the journal question label
        journalQuestionLabel = (TextView) findViewById(journalQuestion);

        // Connect with the next question button
        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);

        // Get the questions array from the resources
        Resources res = getResources();
        final String[] presentQuestionArray = res.getStringArray(R.array.present_tense_question);

        // Set up the onClickListener with the questions and next button
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                journalQuestionLabel.setText(presentQuestionArray[counter]);
                if (counter < (presentQuestionArray.length - 1)) {
                    Log.d("inside if counter", "counter is: " + counter);
                    counter++;
                } else {
                    journalQuestionLabel.setText("Sorry, no more questions.");
                }
            }
        });
    }

}
