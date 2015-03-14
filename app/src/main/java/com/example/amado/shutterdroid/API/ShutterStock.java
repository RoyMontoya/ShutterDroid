package com.example.amado.shutterdroid.API;

import android.util.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by Amado on 11/03/2015.
 */
public class ShutterStock {
    private static final RestAdapter ADAPTER =  new RestAdapter.Builder()
            .setEndpoint("https://api.shutterstock.com/v2")
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    String authInfo = "eaf9b998282250ec7b5b:09764cae03220bda05f5683f02699f04d5a1fbbf";
                    String authString = "Basic "+ Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Authorization", authString);
                }
            })
            .build();

    private static final ShutterStockService  SERVICE= ADAPTER.create(ShutterStockService.class);

    public static void search(String query, Callback<List<Image>> cb) {
        SERVICE.search(query, new ImageCallBack(cb));
    }

    public static void getRecent(String date, Callback<List<Image>> cb){
        SERVICE.getRecent(date, new ImageCallBack(cb));
    }

     private static class ImageCallBack implements Callback<Response> {
       Callback<List<Image>> cb;
         ImageCallBack(Callback<List<Image>> cb){
             this.cb = cb;
     }

         @Override
         public void success(Response response, retrofit.client.Response response2) {
             cb.success(response.data, response2);
         }

         @Override
         public void failure(RetrofitError error) {
            cb.failure(error);
         }
     }

}
