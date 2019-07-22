package com.example.internet_test;

public class HttpHelperFactory {

    private static HttpHelper httpHelper = new HttpHelper();

    public static HttpHelper getInstance() {
        return httpHelper;
    }

}
