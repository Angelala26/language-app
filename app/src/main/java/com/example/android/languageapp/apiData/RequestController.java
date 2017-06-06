package com.example.android.languageapp.apiData;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Angela on 5/4/17.
 */

public class RequestController {


    private final static String BASE_URL_CLUB = "https://api.textgears.com/";
    private static RequestApiEndpoints apiServiceAsync;
    private static RequestController instance;
    private static final int TIMEOUT_MILLIS = 10000;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private Context context;

    private RequestController(Context context) {

        this.context = context;

        Retrofit retrofitAsync = new Retrofit.Builder()
                .baseUrl(BASE_URL_CLUB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestApiEndpoints requestApiEndpoints = retrofitAsync.create(RequestApiEndpoints.class);

    }

    public static RequestController getInstance(Context context) {
        if (instance == null) {
            instance = new RequestController(context);
        }
        return instance;
    }

}
