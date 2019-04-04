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
     private String HTML_URL = "https://api.github.com/repos/h86656519/Showgithub/contents/6.txt";
   // String HTML_URL = "https://api.github.com/user/repos";
    //    String HTML_URL1 = "https://api.github.com/users/88454/repos"; 假設有要做2件事的話 second things
    private String token = "96dabf928bf295bcbb71f4d942fe0156bc79b3fe";
    private String body = "{ \"message\": \"commit from h86656519\", \"content\": \"aDg2NjU2NTE5\"}";
    private String body_delete = "{ \"message\": \"delet from h86656519\",\n" + "  \"sha\": \"b2801dcb664056251439aca9fa02edf54eb847ac\"\n" + "}";

    TextView id, nodeId, name, fullName;
    private GsonParser gsonParser = new GsonParser();
    private ArrayList<String> name_list = new ArrayList<>();
    private MyAdapter myAdapter;
    RecyclerView recyclerView;

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

        myAdapter = new MyAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        new Thread() {
            public void run() {
                try {
                    Message message = new Message();
                    message.what = 0x002;
                    // String personData = GetData.deletHtml(HTML_URL, body_delete, token); //刪除一筆資料
                   // HttpHelper httpHelper = new HttpHelper();
                    HttpHelper httpHelper =  HttpHelper.getInstance();
                    httpHelper.setPath(HTML_URL);
                    httpHelper.setMethod("GET");
                    httpHelper.setTimeout(5000);
                    httpHelper.setToken(token);
                    HttpHelper.Response request = httpHelper.request();

//               不成功就不用送handler了，防呆 persons = null
                    if (request.getHttpCode() == 200) {
                        ArrayList<Repo> persons = gsonParser.parse(request.getJson());
                        message.obj = persons;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x002:
                    ArrayList<Repo> persons = (ArrayList<Repo>) msg.obj;
                    for (int i = 0; i < persons.size(); i++) {
                        name_list.add(persons.get(i).getName());
                    }

                    Toast.makeText(MainActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    //        new Thread() {
//            public void run() {
//                try {
//                    Message message = new Message();
//                    message.what = 0x002;
////                    message2.what = 0x003;  //second things
//                    //包裹
////                    寫法1-1 寫法不好 因為用 message.obj 接資料，但message.obj 之後卻沒有做其他事情，會讓人誤會
////                    message.obj = GetData.getHtml(HTML_URL); //抓下來的資料
////                    persons =  jsonParser.parse(message.obj.toString()); //傳過去做解析
//
////                    寫法1-2 比較不會讓人誤會
////                    String personData = GetData.getHtml(HTML_URL); //抓下資料
//                    // String personData = GetData.getHtmlwithtoken(HTML_URL, token); //用token權限，抓下資料
////                    String personData = GetData.putHtml(HTML_URL, body, token); // 新增一筆資料
//                    String personData = GetData.deletHtml(HTML_URL, body_delete, token); //刪除一筆資料
//
////                    List<Repo> persons =  jsonParser.parse(personData.toString());  //方法1 傳過去Json做解析
//                    ArrayList<Repo> persons = gsonParser.parse(personData.toString()); //方法2 傳過去用Gson做解析
//
//                    message.obj = persons;
////                    message2.obj = GetData.getHtml(HTML_URL1); //second things
//                    handler.sendMessage(message);
////                    handler.sendMessage(message2); //second things
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }


//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            switch (msg.what) {
//                case 0x002:
//                    ArrayList<Repo> persons = (ArrayList<Repo>) msg.obj;
//                    // id.setText((String)msg.obj);
//                    String nameString = "";
//                    for (int i = 0; i < persons.size(); i++) {
//                        nameString += persons.get(i).getName() + ", ";
//                        name_list.add(persons.get(i).getName());
//                    }
//                    myAdapter.setNames(name_list);
//                    recyclerView.setAdapter(myAdapter); //必須放在 myAdapter.setNames 之後做
//                    Log.i("suvini", "conn.getResponseCode() : " + persons.get(i).);
//                    id.setText(persons.get(0).getId()); //設定0，因為只想抓第一筆即可
//                    nodeId.setText(persons.get(0).getNodeId());
////                    name.setText(nameString);
//                    fullName.setText(persons.get(0).getFullName());
//                    Toast.makeText(MainActivity.this, "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
//                    break;
////                case 0x003:
////                    txtshow2.setText((String)msg.obj);
//                default:
//                    break;
//            }
//        }
//    };

// 寫法2 可以run
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        new Thread() {
//            @Override
//            public void run() {
//                //第一步：实例化URL对象
//                String address = "http://139.199.171.179/androidtest/androidtest1.php"; //设置接口
//                try {
//                    URL url = new URL(HTML_URL);   //实例化URL对象
//                    //实例化 HttpURLConnection对象
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    //设置链接属性
//                    conn.setRequestMethod("GET");//设置请求方法
//                    conn.setReadTimeout(5000);//设置超时时间
//                    if (conn.getResponseCode() == 200) { //获取状态码 200表示连接成功
//                        //获取输入流
//                        InputStream in = conn.getInputStream();
//                        //读取输入流
//                        byte[] b = new byte[1024 * 512]; //定义一个byte数组读取输入流
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream(); //定义缓存流来保存输入流的数据
//                        int len = 0;
//                        while ((len = in.read(b)) > -1) {  //每次读的len>-1 说明是是有数据的
//                            baos.write(b, 0, len);  //三个参数  输入流byte数组   读取起始位置  读取终止位置
//                        }
//                        String msg = baos.toString();
//                        Log.e("TAG", msg);
//                    }
//                } catch (java.io.IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
}
