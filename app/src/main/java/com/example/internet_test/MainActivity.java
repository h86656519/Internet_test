package com.example.internet_test;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // private String HTML_URL = "https://api.github.com/repos/h86656519/Showgithub/contents/6.txt";
    String HTML_URL = "https://api.github.com/user/repos";
    //    String HTML_URL1 = "https://api.github.com/users/88454/repos"; 假設有要做2件事的話 second things
    private String token = "01ec49968820b8542ab04e12de0fc3950fb358cb ";
    private String body = "{ \"message\": \"commit from h86656519\", \"content\": \"aDg2NjU2NTE5\"}";
    private String body_delete = "{ \"message\": \"delet from h86656519\",\n" + "  \"sha\": \"b2801dcb664056251439aca9fa02edf54eb847ac\"\n" + "}";

    TextView id, nodeId, name, fullName;
    private GsonParser gsonParser = new GsonParser();
    private ArrayList<String> name_list = new ArrayList<>();
    private MyAdapter myAdapter;
    RecyclerView recyclerView;
    HttpHelper httpHelper;
    Animation animation = new Animation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (TextView) findViewById(R.id.id);
        nodeId = (TextView) findViewById(R.id.node_id);
        name = (TextView) findViewById(R.id.name);
        fullName = (TextView) findViewById(R.id.full_name);

//        網路判斷
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);//先取得此service
        NetworkInfo networInfo = conManager.getActiveNetworkInfo();  //在取得相關資訊
        if (networInfo == null || !networInfo.isAvailable()) { //判斷是否有網路
            Log.i("suvini", "沒網路");
        } else {
            Log.i("suvini", "有網路");
        }

        httpHelper = HttpHelperFactory.getInstance();
        httpHelper.setMethod("GET");
        httpHelper.setToken(token);
        httpHelper.request(HTML_URL);
        //  animation.runAnimate(); //若要確保先走 request 完後再走runAnimate，就將runAnimate 放在request 的run裡面，是比較差的作法

        myAdapter = new MyAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        httpHelper.Destroy();
        animation.Destroy();
    }
}
