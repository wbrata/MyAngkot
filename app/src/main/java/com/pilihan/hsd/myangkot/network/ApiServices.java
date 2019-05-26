package com.pilihan.hsd.myangkot.network;

import com.pilihan.hsd.myangkot.response.ResponseRoute;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("json")
    Call<ResponseRoute> request_route(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String api_key
    );
}
