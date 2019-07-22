package com.example.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://demo.kidtech.com.tw/files/appexam/appexam1.htm";
    private String url2 = "http://demo.kidtech.com.tw/files/appexam/appexam2.htm";
    private static final String TAG = "Main2Activity";
    final ExecutorService service = Executors.newSingleThreadExecutor();

    //目標: 先去呼叫url，等確定成功後再呼叫url2，若失敗就不呼叫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Myrun myrun = new Myrun(url, new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                setSuccess(response.isSuccessful());
                onResult();
            }
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }
            void onResult() {
                Log.i(TAG, "onResult: " + isSuccess);
                if (isSuccess) {
                    //Post second api
                    service.execute(new Main2Activity.Myrun(url2, new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) { }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException { }
                    }));
                }
            }
        });
        service.execute(myrun);
    }

    private boolean isSuccess;

    void setSuccess(boolean success) {
        this.isSuccess = success;
    }

    public class Myrun implements Runnable {
        String url;
        boolean isSuccessful;
        Callback callback;

        public Myrun(String url, Callback callback) {
            this.url = url;
            this.callback = callback;
        }

        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            client.newCall(request).enqueue(callback);
            service.shutdown();
        }
    }
}
