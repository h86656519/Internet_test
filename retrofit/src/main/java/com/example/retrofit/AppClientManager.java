package com.example.retrofit;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppClientManager {
    private static AppClientManager manager = new AppClientManager();
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    //設定 init
    private AppClientManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        寫法2，採用post 請求的話也可以用寫法2將token 加進去在header 裡面，或是用寫法1(在PostApi)
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Interceptor.Chain chain) throws IOException {
//                Request original = chain.request();
//                Request request = original.newBuilder()
//                        .header("Authorization", "token ae10c2bdf77f47ed95034fdf275e715bd573e938")
//                        .header("Content-Type", "application/json")
//                        .method(original.method(), original.body())
//                        .build();
//                return chain.proceed(request);
//            }
//        });

//        一般GET請求的話只需要37~42行即可，需要POST請求的話需要再加上23~34行
        okHttpClient = builder.build();
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
