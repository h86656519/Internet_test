package com.example.okhttp;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://demo.kidtech.com.tw/files/appexam/appexam1.htm";
    private String url2 = "http://demo.kidtech.com.tw/files/appexam/appexam2.htm";
    private static final String TAG = "MainActivity";
    final ExecutorService service = Executors.newFixedThreadPool(2);

//目標:用okhttp 同時request 二個url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Myrun myrun = new Myrun(url);
//        Myrun myrun2 = new Myrun(url2);
//        myrun.setURL(url
//        myrun2.setURL(url2);
        service.execute(new Myrun(url));
        service.execute(new Myrun(url2));

    }

    public class Myrun implements Runnable {
        String url;

        public Myrun(String url) {
            this.url = url;
        }

        void setURL(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.i(TAG, "isSuccessful() " + response.isSuccessful());
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            service.shutdown();
        }
    }

}
