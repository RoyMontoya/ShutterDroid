package com.example.amado.shutterdroid.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Amado on 11/03/2015.
 */
interface ShutterStockService {
    @GET("/images/search")
    public void search(@Query("query") String query, Callback<Response> cb);

    @GET("/images/search")
    public void getRecent(@Query("added_date_start") String date, Callback<Response> cb);



}
