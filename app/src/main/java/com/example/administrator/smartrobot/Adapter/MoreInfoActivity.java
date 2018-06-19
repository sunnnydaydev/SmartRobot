package com.example.administrator.smartrobot.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.smartrobot.R;

/**
 * Created by Administrator on 2017/10/18.
 */
public class MoreInfoActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        WebView wvOpen =(WebView) findViewById(R.id.wv_open);
        wvOpen.getSettings().setJavaScriptEnabled(true);//允许使用js
        wvOpen.setWebViewClient(new WebViewClient());//不跳转浏览器
        wvOpen.loadUrl("http://www.tuling123.com/");
    }
}
