package com.example.internet_test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    int code;
    List<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textview_main2);
        urls.add("http://demo.kidtech.com.tw/files/appexam/appexam1.htm");
        urls.add("http://demo.kidtech.com.tw/files/appexam/appexam2.htm");

        HttpHelper httpHelper = HttpHelperFactory.getInstance();
        httpHelper.setListener(new HttpHelper.HttpListener() {
            @Override
            public void onSuccess(HttpHelper.Response response) {
                textView.setText(response.getJson());
            }
        });
        httpHelper.requestSequence(urls);

    }
}
