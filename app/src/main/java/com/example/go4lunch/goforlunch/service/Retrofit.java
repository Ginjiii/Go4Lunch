package com.example.go4lunch.goforlunch.service;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    /**
     * Create an instance of Retrofit with the base url of API Google
     */
    private static retrofit2.Retrofit retrofit;

    public static retrofit2.Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/maps/api/place/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
