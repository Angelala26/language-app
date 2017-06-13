package com.example.android.languageapp.apiData;

import android.content.Context;

import com.example.android.languageapp.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Angela on 5/4/17.
 */

public class RequestController {

    private final static String BASE_URL_CLUB = "https://api.textgears.com/";
    private static RequestApiEndpoints apiService;
    private static RequestController instance;
    private static final int TIMEOUT_MILLIS = 10000;
    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    // Keep a reference to our API key here.  We'll want to append it to every API request we make.
    // By saving a reference to the key instead of the Context we derive it from, we prevent a
    // potential memory leak.
    private final String API_KEY;

    private RequestController(Context context) {

        // Grab the API key from the strings.xml file
        API_KEY = context.getString(R.string.api_key);

        // Create an interceptor that appends the API key to every request we make
        Interceptor apiKeyInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        };

        // Apply the timeouts and our API key interceptor to our HTTP client
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .readTimeout(TIMEOUT_MILLIS, TIMEOUT_UNIT)
                .addInterceptor(apiKeyInterceptor)
                .build();

        // Build our Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_CLUB)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the API endpoint bridge
        apiService = retrofit.create(RequestApiEndpoints.class);
    }

    public static RequestController getInstance(Context context) {
        if (instance == null) {
            instance = new RequestController(context);
        }
        return instance;
    }

    public void fetchGrammarCheck(String text, Callback<GrammarResponse> callback) {
        // Using enqueue() makes this request asynchronous.
        apiService.performCheck(text).enqueue(callback);
    }
}
