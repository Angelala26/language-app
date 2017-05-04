package com.example.android.languageapp.apiData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angela on 5/4/17.
 */

public class GrammarResponse {
    List<Error> errors;

    public GrammarResponse() {
        errors = new ArrayList<Error>();
    }

    public static GrammarResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        GrammarResponse grammarMovieResponse = gson.fromJson(response, GrammarResponse.class);
        return grammarMovieResponse;
    }
}
