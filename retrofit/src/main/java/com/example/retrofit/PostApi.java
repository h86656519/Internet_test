package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApi {
    @GET("repos") //
    Call<List<GithubRepo>> git();

    @GET("appexam1.htm")
    Call<Repo> jsonArray();
}
