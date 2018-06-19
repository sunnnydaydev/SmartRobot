package com.example.administrator.smartrobot.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.smartrobot.R;

/**
 * Created by Administrator on 2017/10/17.
 */
public class HeadLeft extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_left);
        Button btnMoreInfo =(Button) findViewById(R.id.btn_more_info);
        btnMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HeadLeft.this,MoreInfoActivity.class));
            }
        });
    }
}
