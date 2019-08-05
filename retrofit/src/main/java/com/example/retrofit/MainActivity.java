package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  requestgithub();
        requestjsonArray();
    }

    public void requestgithub() {
        PostApi postApi = AppClientManager.getClient().create(PostApi.class);
        postApi.git().enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                Log.i(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) { }

        });
    }

    public void requestjsonArray() {
        PostApi postApi = AppClientManager.getClient().create(PostApi.class);
        postApi.jsonArray().enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Log.i(TAG, "onResponse: " + response.body().Name);
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
            }
        });
    }
}
