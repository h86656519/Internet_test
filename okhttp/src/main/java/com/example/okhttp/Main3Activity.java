package com.example.okhttp;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main3Activity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://demo.kidtech.com.tw/files/appexam/appexam1.htm";
    private String url2 = "https://api.github.com/users/h86656519/repos";
    final ExecutorService service = Executors.newSingleThreadExecutor();
    private static final String TAG = "Main3Activity";

    //目標:抓下二個資料源不同的url，然後印出來
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        service.execute(new Myrun(url2, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "url2 連線失敗 ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "response.isSuccessful() : " + response.isSuccessful());
             //   Log.i(TAG, "response.body(): " + response.body().string());
                GsonParser gsonParser = new GsonParser();
                ArrayList<GithubRepo> person = gsonParser.parse(response.body().string());
                Log.i(TAG, "person " + person.get(1).name);
            }
        }));

        service.execute(new Myrun(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG, "url 連線失敗 ");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.i(TAG, "response.isSuccessful() : " + response.isSuccessful());
                //   Log.i(TAG, "response.body(): " + response.body().string());
                Gson gson = new Gson();
                Repo temp = gson.fromJson(response.body().string(), new TypeToken<Repo>(){}.getType()); //要抓得資料就只有一個array
                Log.i(TAG, "temp " + temp.Name);
            }
        }));

    }

    public class Myrun implements Runnable {
        Callback callback;
        String url;

        public Myrun(String url, Callback callback) {
            this.callback = callback;
            this.url = url;
        }

        @Override
        public void run() {
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(headers.name(i) + headers.value(i));
                    Log.i(TAG, "headers: " + headers.name(i) + headers.value(i));
                    Log.i(TAG, "response.toString(): " + response.toString());


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            client.newCall(request).enqueue(callback);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        service.shutdown();
    }
}
