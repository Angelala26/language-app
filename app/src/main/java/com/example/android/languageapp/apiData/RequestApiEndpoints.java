package com.example.android.languageapp.apiData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Angela on 5/4/17.
 */

public interface RequestApiEndpoints {

    @GET("check.php")
    Call<GrammarResponse> performCheck(@Query("text") String text,
                                       @Query("key") int eg03DHPdWCEj0YSA);

}
