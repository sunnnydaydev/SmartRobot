package com.example.administrator.smartrobot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017/10/15.
 */
public class Welcome extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    // 按钮的属性点击事件直接开启  避免代码过多此处不用findView。。。
    public void OpenMainActivity(View view){
        startActivity(new Intent(Welcome.this,MainActivity.class));
        finish();
    }
}
