package com.example.retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClientManager {
    private static AppClientManager manager = new AppClientManager();
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    //設定 init
    private AppClientManager() {
        okHttpClient = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.postURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

    }

    public static Retrofit getClient() {
        return manager.retrofit;
    }
}
