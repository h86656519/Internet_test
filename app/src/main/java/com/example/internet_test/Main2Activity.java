package com.example.internet_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
            public void onSuccess(HttpHelper.MyResponse myResponse) {
                textView.setText(myResponse.getJson());
            }
        });
        httpHelper.requestSequence(urls);

    }
}
