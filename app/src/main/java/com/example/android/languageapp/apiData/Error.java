package com.example.android.languageapp.apiData;

import java.util.List;

/**
 * Created by Angela on 5/4/17.
 */

public class Error {

    private String id;
    private int offset;
    private int length;
    private String bad;
    private List<String> better;

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

    public List<String> getBetter() {
        return better;
    }
}

