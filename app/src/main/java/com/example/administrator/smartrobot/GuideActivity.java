package com.example.administrator.smartrobot;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

/* 引导页面 首先开启app的欢迎引导页面*/
public class GuideActivity extends Activity {

    private RelativeLayout RlGuide;
    private AnimationSet as;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        initUI();//初始化UI控件
        initData();//初始化数据
    }

    private void initData() {
       // 初始化引导动画
        initAnimation();
    }

    private void initAnimation() {
        // 旋转动画  360度旋转  相对自身中心点旋转
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);// 保持状态

        // 缩放动画  0 到完整尺寸（1）  基于中心点缩放
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(2000); //执行时间
        sa.setFillAfter(true);//保持执行完毕状态

        // 渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(3000);
        aa.setFillAfter(true);

        // 动画集合  参数是否公用同一个加速度
        as = new AnimationSet(false);
        as.addAnimation(ra);
        as.addAnimation(ra);
        as.addAnimation(aa);
        RlGuide.startAnimation(as);

        //设立事件监听  动画完成进入新手引导页
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
              if (as !=null){
                  // 跳转到欢迎页 结束本页
                  startActivity(new Intent(getApplicationContext(),Welcome.class));
                  finish();
              }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }





    private void initUI() {
        RlGuide = (RelativeLayout) findViewById(R.id.rl_guide);
    }

}