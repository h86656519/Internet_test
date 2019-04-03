package com.example.internet_test;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    private int timeout;
    private String path;
    private String method;
    private String token = null;

    public String getMethod() {
        return method;
    }

    public String getToken() {
        return token;
    }

    public String getPath() {
        return path;
    }

    public int getTimeout() {
        return timeout;
    }

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
        URL url = new URL(getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(getTimeout());
        conn.setRequestMethod(getMethod());
        if (method != null) {
            conn.setRequestProperty("Authorization", "token" + " " + getToken());
        }

        Log.i("suvini", "conn.getResponseCode() : " + conn.getResponseCode());
        response.setHttpCode(conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            InputStream in = conn.getInputStream();
            byte[] data = StreamTool.read(in);
           // String html = new String(data, "UTF-8");
            response.setJson(new String(data, "UTF-8"));
          //  Log.i("suviniii", "html : " + html);
            return response;
        }
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
