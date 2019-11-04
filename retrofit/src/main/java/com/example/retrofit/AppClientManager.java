package com.example.retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

        /*攔截器的功用，好處 1.在request 途中要 固定的變數 ex: 每次token 都是一樣，就可以寫在攔截器裡面
                              2.每次接收回來的東西不用印出來，直接在locat 就可以看到

         */
        okHttpClient = builder
                .connectTimeout(30, TimeUnit.SECONDS)   // 設置連線Timeout
                .addInterceptor(new LoggingInterceptor()) //新增攔截器
                .build(); // 租裝用的，組裝的元件就是上面的 connectTimeout，addInterceptor ..... 
        retrofit = new Retrofit.Builder()
                .baseUrl(Config.postURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient) // 將okHttpClient加入連線基底
                .build();
        //new AlertDialog.Builder()
        //                .setTitle
        //                .setMessage

    }

    public static Retrofit getClient() {
        return manager.retrofit;
    }
}
