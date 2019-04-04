package com.example.internet_test;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    private static HttpHelper httpHelper = new HttpHelper();
    public static HttpHelper getInstance() {
        return httpHelper;
    }

    //------------------------------------------------------

    private int timeout = 5000;
    private String path;
    private String method;
    private String token = null;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Response request() throws Exception {
        Response response = new Response();
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(timeout);
        conn.setRequestMethod(method);
        if (method != null) {
            conn.setRequestProperty("Authorization", "token" + " " + token);
        }

        Log.i("suvini", "getResponseCode : " + conn.getResponseCode());
        response.setHttpCode(conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = StreamTool.read(in);
            // String html = new String(data, "UTF-8");
            response.setJson(new String(data, "UTF-8"));
            //  Log.i("suviniii", "html : " + html);
            return response;
        }
        response.setErrorMessage(conn.getResponseMessage());

        return response;
    }

    class Response {
        private String json;
        private int httpCode; //404, 200
        private String errorMessage; //Wrong url

        public String getJson() {
            return json;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    }
}
