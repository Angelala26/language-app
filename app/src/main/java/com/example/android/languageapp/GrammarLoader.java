package com.example.android.languageapp;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Angela on 5/2/17.
 */

public class GrammarLoader extends AsyncTask<Void, Void, String> {

    private TextView journalQuestionLabel;
    private ProgressBar progressBar;
    private Exception exception;
    private static final String API_URL = "https://api.textgears.com/check.php";
    private static final String API_KEY = "eg03DHPdWCEj0YSA";

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            journalQuestionLabel.setText("");
        }

        protected String doInBackground(Void... urls) {
            //TODO: Understand how to put the users answers here then send it to the API

            try {
                URL url = new URL(API_URL + "email=" + "userInput" + "&apiKey=" + API_KEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if(response == null) {
                response = "THERE WAS AN ERROR";
            }
            progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            journalQuestionLabel.setText(response);
        }
}
