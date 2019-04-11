package com.example.internet_test;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Animation {
    private ExecutorService animateService = Executors.newSingleThreadExecutor(); //

    public void runAnimate() {
        animateService.submit(new Runnable() {
            @Override
            public void run() {
                Log.i("suvini", "事情二:跑動畫");
            }
        });
    }

    public void Destroy() {
        animateService.shutdown();
    }
}
