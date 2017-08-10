package com.example.android.languageapp;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.languageapp.apiData.Error;
import com.example.android.languageapp.apiData.GrammarResponse;
import com.example.android.languageapp.apiData.RequestController;
import com.example.android.languageapp.data.JournalContract;
import com.example.android.languageapp.data.JournalDbHelper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.languageapp.R.id.journalQuestion;

/**
 * Created by Angela on 2/24/17.
 */

public class JournalActivity extends AppCompatActivity {

    //set label for journal questions
    private TextView journalQuestionLabel;
    private int counter = 0;
    protected SharedPreferences preferences;
    private TextView userInput;
    private Uri currentAnswerUri;
    public RequestController requestController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestController for the RetrofitApi
        requestController = RequestController.getInstance(this);

        //TODO: connect the requestController with the stored answers in the database

        //get an instance of the database through the helper
        JournalDbHelper helper = new JournalDbHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();


        requestController.fetchGrammarCheck("This am a gooder test", new Callback<GrammarResponse>() {
            @Override
            public void onResponse(Call<GrammarResponse> call, Response<GrammarResponse> response) {
                GrammarResponse grammarResponse = response.body();
                for (Error error : grammarResponse.getErrors()) {
                    Log.v("Grammar Error", "Bad: " + error.getBad() + ", Better: " + error.getBetter());
                }
                Log.v("Grammer Error", "Total score: " + grammarResponse.getScore());
            }

            @Override
            public void onFailure(Call<GrammarResponse> call, Throwable t) {
                Log.v("Grammer Error Failure", t.getLocalizedMessage());
            }
        });



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

        // Initialize the user answers
        userInput = (TextView) findViewById(R.id.journalAnswer);

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

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        final ContentValues values = new ContentValues();

        //TODO: the array is off - had to add a random question at the end that doesn't show up
        // Set up the onClickListener with the questions and next button
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (selection == 1) {
                    journalQuestionLabel.setText(presentQuestionArray[counter + 1]);
                    if (counter < (presentQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        saveAnswer();
                        counter++;
                        userInput.setText("");
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                        saveAnswer();
                        userInput.setVisibility(View.INVISIBLE);
                    }
                } else if (selection == 2) {
                    journalQuestionLabel.setText(pastQuestionArray[counter + 1]);
                    if (counter < (pastQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        saveAnswer();
                        counter++;
                        userInput.setText("");
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                        saveAnswer();
                        userInput.setVisibility(View.INVISIBLE);
                    }
                } else if (selection == 3) {
                    journalQuestionLabel.setText(futureQuestionArray[counter + 1]);
                    if (counter < (futureQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        saveAnswer();
                        counter++;
                        userInput.setText("");
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                        saveAnswer();
                        userInput.setVisibility(View.INVISIBLE);
                    }
                } else if (selection == 4) {
                    journalQuestionLabel.setText(conditionalQuestionArray[counter + 1]);
                    if (counter < (conditionalQuestionArray.length - 2)) {
                        Log.d("inside if counter", "counter is: " + counter);
                        saveAnswer();
                        counter++;
                        userInput.setText("");
                    } else {
                        journalQuestionLabel.setText("Sorry, no more questions.");
                        saveAnswer();
                        userInput.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void saveAnswer() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String userInputToString = userInput.getText().toString().trim();

        // Check if this is supposed to be a new entry
        // and check if all the fields in the editor are blank
        if (currentAnswerUri == null && TextUtils.isEmpty(userInputToString)) {
            // Since no fields were modified, we can return early without creating a new entry.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(JournalContract.JournalEntry.COLUMN_USER_ANSWER, userInputToString);

        // Determine if this is a new or existing entry by checking if mCurrentAnswerUri is null or not
        if (currentAnswerUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(JournalContract.JournalEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "Input failed.",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Input succeeded.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING entry, so update the entry with content URI: mCurrentAnswerUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentAnswerUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(currentAnswerUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "Update failed.",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Successful update.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



}
