package com.androiddesdecero.retrofitudemy.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by albertopalomarrobledo on 1/12/18.
 */

public class WebService {

    private static final String BASE_URL = "http://10.0.2.2:8040/";
    private static WebService instance;
    private Retrofit retrofit;

    private WebService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized WebService getInstance(){
        if(instance == null){
            instance = new WebService();
        }
        return instance;
    }

    public WebService createService(){
        return retrofit.create(WebService.class);
    }
}
