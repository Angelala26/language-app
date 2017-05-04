package com.example.android.languageapp.apiData;

/**
 * Created by Angela on 5/4/17.
 */

public class Error {

    String id;
    int offset;
    int length;
    String bad;
    String[] better;

    public String getId() {
        return id;
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public String getBad() {
        return bad;
    }

    public String[] getBetter() {
        return better;
    }

}

