package com.example.administrator.smartrobot.javabean;

/**
 * Created by Administrator on 2017/10/15.
 */

public class ListViewData {
    public static int SEND = 1;
    public static int RECEIVE = 2;
    private String mTime;
    private int flag ;
    private String context;// 发送或接受的字符串
    // 构造函数
    public ListViewData(String context,int flag,String mTime){
        this.context = context;
        this.flag=flag;
        this.mTime=mTime;
    }

   /* get
     set
    方法
    */
   public String getmTime() {
       return mTime;
   }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
