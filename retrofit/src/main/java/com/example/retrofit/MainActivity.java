package com.example.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    private String account;
    private TextView id_tv, nodeId_tv, name_tv, fullNmae_tv;
    private EditText api_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        api_ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) { //按下ENTER 的動作
                    account = api_ed.getText().toString();
                    Log.i("event", "captured");
                    //     requestgithub();
                    DynamicRequestGithubByhPost();
                    return false;
                }
                return false;
            }
        });
        //requestjsonArray();
    }


    public void requestgithub() {
        PostApi postApi = AppClientManager.getClient().create(PostApi.class);
        postApi.getDynamicGithub(account).enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                Log.i(TAG, "onResponse: " + response.body().get(0));
                id_tv.setText("id : " + response.body().get(0).getId());
                nodeId_tv.setText("node id : " + response.body().get(0).getNodeId());
                name_tv.setText("name : " + response.body().get(0).getName());
                fullNmae_tv.setText("full name : " + response.body().get(0).getFull_name());
                //   InsideObject owner = response.body().get(0).getOwner();
//                Log.i(TAG, "owner.getNode_id : " + owner.getNode_id());

            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Log.i(TAG, "onFailure");
            }
        });
    }

    //用GET請求一個object，將內容抓下來
    public void requestjsonArray() {
        PostApi postApi = AppClientManager.getClient().create(PostApi.class);
        postApi.getJsonArray().enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                Log.i(TAG, "onResponse: " + response.body().Name);
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
            }
        });
    }

    public void initView() {
        api_ed = findViewById(R.id.githubaccount);
        id_tv = findViewById(R.id.id);
        nodeId_tv = findViewById(R.id.node_id);
        name_tv = findViewById(R.id.name);
        fullNmae_tv = findViewById(R.id.full_name);
    }

    //    目標:在一個githubg 上的專案裡增加一條Comment
    public void DynamicRequestGithubByhPost() {
        PostApi postApi = AppClientManager.getClient().create(PostApi.class);
        postApi.getDynamicGithubByhPost(account,
                new Comment("Comment內容寫在這邊")).enqueue(new Callback<GithubRepo>() {
            @Override
            public void onResponse(Call<GithubRepo> call, Response<GithubRepo> response) {
                Log.i(TAG, "response " + response.code());
                Log.i(TAG, "response " + response.message());
                Log.i(TAG, "response " + response.body());
            }

            @Override
            public void onFailure(Call<GithubRepo> call, Throwable t) {
                Log.e(TAG, "onFailure", t);
            }
        });
    }
}
