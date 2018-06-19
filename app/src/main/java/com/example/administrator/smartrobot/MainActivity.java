package com.example.administrator.smartrobot;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.smartrobot.Adapter.MyBaseAdapter;
import com.example.administrator.smartrobot.HttpRequestUtils.HttpUtil;
import com.example.administrator.smartrobot.javabean.ListViewData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/10/15.
 */
public class MainActivity extends Activity {
    private double currentTime, oldTime = 0.0;
    private ImageButton IbBack;
    private ListView lvList;
    private EditText EtInput;
    private Button BtnSend;
    private ListViewData lvdReceive1;// 网络解析的数据封装到javaBean
    private List<ListViewData> mlist;// 声明集合 存放javabean（封装的数据）对象
    private MyBaseAdapter adapter;

    // Handler 对象处理 进行更新ui操作
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i(TAG, "handleMessage: " + "收到通知更新");
            int fre = msg.what;
            switch (fre) {
                // 都是通知更新的操作进行一次就行
                case 0:
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };


    public String welcom_word() {
        String welcomeData;
        String[] wel_array = this.getResources().getStringArray(R.array.data);
        int randomNUmber = (int) (Math.random() * (wel_array.length - 1));
        welcomeData = wel_array[randomNUmber];
        return welcomeData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();// 初始化控件
        initData();// 初始化数据
    }

    private void initData() {
        // 最上方  返回按钮处理
        IbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void initUI() {
        lvList = (ListView) findViewById(R.id.lv_list);// listView 控件
        EtInput = (EditText) findViewById(R.id.et_input); // 输入框控件
        BtnSend = (Button) findViewById(R.id.btn_send);//发送消息按钮
        IbBack = (ImageButton) findViewById(R.id.ib_back);
        mlist = new ArrayList<ListViewData>();//声明集合对象

        // 欢迎语
        ListViewData WelcomeWords = new ListViewData(welcom_word(), ListViewData.RECEIVE, getTime());
        Log.i(TAG, "initUI: 当前时间" + getTime());
        mlist.add(WelcomeWords);
        // 按钮的点击事件  点击按钮发送输入框的文字
        BtnSend.setOnClickListener(new View.OnClickListener() {

            private JSONObject jsonObject;
            private String mUrl; // 请求网址 url + key + 要说的话  （key 为自己申请的）
            private String getInputString;//获得输入的值


            @Override
            public void onClick(View view) {
                getInputString = EtInput.getText().toString();
                Log.i(TAG, "onClick: 获得输入内容 " + getInputString);
                ListViewData LVDSend = new ListViewData(getInputString, ListViewData.SEND, getTime());
                mlist.add(LVDSend);
                // 通知更新
                mHandler.sendEmptyMessage(0);// 0 为 发送更新
                EtInput.setText("");//发送消息后输入框置空


                // 把得到的数据发送给网络获得对话的数据
                mUrl = "http://www.tuling123.com/openapi/api?key=13c611e3c80e4acf963a788c005a2f90&info=" + getInputString;
                HttpUtil.SendOkHttpRequest(mUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //请求失败时执行
                        Log.i(TAG, "onFailure: " + "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // 请求成功时执行
                        String getNetResponseData = response.body().string();//得到网络返回json字符串
                        try {
                            jsonObject = new JSONObject(getNetResponseData);
                            Log.i(TAG, "onResponse 得到网络数据:" + jsonObject.getString("text"));
                            lvdReceive1 = new ListViewData(jsonObject.getString("text"), ListViewData.RECEIVE, getTime());
                            mlist.add(lvdReceive1);
                            mHandler.sendEmptyMessage(1);// 1 为接受刷新
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onResponse: " + "解析失败！！！");
                        }
                    }
                });

            }
        });
        adapter = new MyBaseAdapter(mlist, MainActivity.this);
        lvList.setAdapter(adapter);//listView设置数据
    }
    public String getTime() {
        currentTime = System.currentTimeMillis();// 当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = simpleDateFormat.format(new Date());
        Log.i(TAG, "getTime: 当前精确时间" + str);
        if (currentTime - oldTime > 0.5 * 60 * 1000) {
            // 间隔一分钟时显示时间
            oldTime = currentTime; // 事件赋值
            return str;
        } else {
            return "";
        }

    }

    //菜单，返回键响应 双击退出程序

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();//双击退出函数
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}