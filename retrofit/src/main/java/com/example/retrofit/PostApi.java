package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PostApi {
    @GET("repos") //放URL 地址中最後的位置
    Call<List<GithubRepo>> getMygithub();

    @GET("appexam1.htm")
    Call<Repo> getJsonArray();

//    @POST("repos")
//    Call<List<GithubRepo>> postMygithub();
}
