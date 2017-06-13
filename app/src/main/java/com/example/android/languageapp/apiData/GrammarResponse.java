package com.example.android.languageapp.apiData;

import java.util.List;

/**
 * Created by Angela on 5/4/17.
 */

public class GrammarResponse {

    private boolean result;
    private List<Error> errors;
    private int score;

    public boolean isResult() {
        return result;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public int getScore() {
        return score;
    }
}
