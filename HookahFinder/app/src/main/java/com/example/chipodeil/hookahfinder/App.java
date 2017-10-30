package com.example.chipodeil.hookahfinder;

import android.app.Application;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chipodeil on 30.10.2017.
 */

public class App extends Application {

    private static VedroServerApi vedroServerApi;
    private Retrofit retrofit;
    String url = "http://138.197.182.48:4000/";

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        retrofit = new Retrofit.Builder().baseUrl(url).client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();
        vedroServerApi = retrofit.create(VedroServerApi.class);
    }

    public static VedroServerApi getApi(){
        return vedroServerApi;
    }
}
