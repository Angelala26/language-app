package com.example.android.languageapp.apiData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Angela on 5/4/17.
 */

public interface RequestApiEndpoints {

    @GET("errors/{errors}")
    Call<List<Error>> groupList(@Path("errors") int errors, @Query("sort") String sort);

    @GET("errors/{id}")
    Call<Error> getId(@Path("id") String id);

    @GET("errors/{offset}")
    Call<Error> getOffset(@Path("offset") int offset);

    @GET("errors/{length}")
    Call<Error> getLength(@Path("length") int length);

    @GET("errors/{bad}")
    Call<Error> getBad(@Path("bad") String bad);

    @GET("errors/{better}")
    Call<List<Error>> betterList(@Path("better") int better, @Query("sort") String sort);
}
