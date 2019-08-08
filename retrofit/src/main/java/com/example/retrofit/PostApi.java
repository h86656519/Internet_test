package com.example.retrofit;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface PostApi {
    @GET("repos") //放URL 地址中最後的位置
    Call<List<GithubRepo>> getDynamicGithub();

    @GET("{user_id}/repos")
    Call<List<GithubRepo>> getDynamicGithub(@Path(value = "user_id", encoded = true) String userId);

    @GET("appexam1.htm")
    Call<Repo> getJsonArray();

}
