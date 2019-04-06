package com.example.internet_test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    TextView textView;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.textview_main2);

//        new Thread() {
//            public void run() {
//                try {
//                    HttpHelper httpHelper = HttpHelper.getInstance();
//                    HttpHelper.Response response = httpHelper.request();
//                    Message message = new Message();
//                    message.what = 0x003;
//                    code = response.getHttpCode();
//                    message.obj = code;
//                    handler.sendMessage(message);
//                } catch (
//                        Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
        HttpHelper httpHelper = HttpHelper.getInstance();
        //like OnClickListener
        httpHelper.setListener(new HttpHelper.HttpListener() {
            @Override
            public void onSuccess(HttpHelper.Response response) {
                textView.setText(response.getJson());
            }
        });
        httpHelper.request();

    }

//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case 0x003:
//                    int code1 = (Integer) msg.obj;
//                    textView.setText(code1);
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
}
