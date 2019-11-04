package com.example.internet_test;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Why still need Handler, why not just use HttpListener directly?
//What's the difference between use UrlConnection in Activity and use HttpHelper in Activity?
public class HttpHelper {
    Runnable thread;

    private MyResponse myResponse;

    public HttpListener getListener() {
        return listener;
    }

    public void setListener(HttpListener listener) {
        this.listener = listener;
    }

    private HttpListener listener;
    private int timeout = 5000;
    private String method;
    private String token = null;

    public void setMethod(String method) {
        this.method = method;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MyResponse getMyResponse() {
        return myResponse;
    }

    public void setRequestParellal(boolean isParallel) { //要不要同步request
        this.isParallel = isParallel;
    }

    private boolean isParallel = false;

    // ExecutorService 負責管理 Thread
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private ExecutorService multiexecutorService = Executors.newFixedThreadPool(4); //同時

    public void requestSequence(List<String> urls) {
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            request(url);
        }
    }

    public void request(final String path) {
         thread = new Runnable() {
            @Override
            public void run() {
                Log.i("suvini", "事情一:跑網路" );
                try {
                    final MyResponse myResponse = new MyResponse();
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(timeout);
                    conn.setRequestMethod(method);
                    if (method != null) {
                        conn.setRequestProperty("Authorization", "token" + " " + token);
                    }

                    Message message = new Message();
                    message.what = 0x03;

                    Log.i("suvini", "getResponseCode : " + conn.getResponseCode());
                    myResponse.setHttpCode(conn.getResponseCode());
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        byte[] data = StreamTool.read(in);
                        // String html = new String(data, "UTF-8");
                        myResponse.setJson(new String(data, "UTF-8"));
                        //  Log.i("suviniii", "html : " + html);

                        message.obj = myResponse;
                        handler.sendMessage(message);
                    }else if(conn.getResponseCode() == 401){
                        Log.i("suvini", "401 訪問權限不夠，確認一下token");
                    }
                    myResponse.setErrorMessage(conn.getResponseMessage());
                    message.obj = myResponse;
                    handler.sendMessage(message);
                } catch (Exception e) {

                }
            }
        };

         if(isParallel){
             multiexecutorService.execute(thread);
         }else{
             executorService.execute(thread);
         }
    }

    class MyResponse {
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


    public interface HttpListener {
        public void onSuccess(MyResponse myResponse);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x003:
                    myResponse = (MyResponse) msg.obj;
                    if (listener != null) {
                        listener.onSuccess(myResponse);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void Destroy() {
        executorService.shutdown();

    }
}
