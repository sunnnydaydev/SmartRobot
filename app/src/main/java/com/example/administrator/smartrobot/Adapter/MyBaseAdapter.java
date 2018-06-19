package com.example.administrator.smartrobot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.smartrobot.R;
import com.example.administrator.smartrobot.javabean.ListViewData;

import java.util.List;

/**
 * Created by Administrator on 2017/10/15.
 */

public class MyBaseAdapter extends BaseAdapter {
    private View mView=null;
    private List<ListViewData> mlist;//集合对象 从构造函数中得到数据
    private Context context;//上下文环境
    /*构造函数 参数1 集合对象
   *          参数2 上下文环境
   * */
   public MyBaseAdapter(List<ListViewData> mlist, Context context){
       this.context = context;
       this.mlist   = mlist;
   }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            if (mlist.get(i).getFlag() == ListViewData.SEND) {
                mView= View.inflate(context, R.layout.right_view, null);
            }
            if (mlist.get(i).getFlag() == ListViewData.RECEIVE) {
                mView = View.inflate(context, R.layout.left, null);
            }
        TextView txvWords = (TextView) mView.findViewById(R.id.txv_words);
        TextView txvTime  = (TextView) mView.findViewById(R.id.txv_time);
        txvWords.setText(mlist.get(i).getContext());
        txvTime.setText(mlist.get(i).getmTime());
        PersonInfo(i);// 查看头像的详细信息
        return mView;
    }

    /*
    查看人物信息：
    错误处理  此处设为 getApplicationContext() 真机跳转失败
     adapter = new MyBaseAdapter(mlist,MainActivity.this);
    */
    public void PersonInfo(int i){
        ImageView ivHeadRight=null;
        ImageView ivHeadLeft =null;
        if( mlist.get(i).getFlag()==ListViewData.RECEIVE){
           ivHeadLeft =(ImageView) mView.findViewById(R.id.iv_head_left);
      }
        // 左面头像点击事件
        if (ivHeadLeft!=null){
            ivHeadLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,HeadLeft.class));
                }
            });
        }
        /*进行容错处理  先看对象是否存在
          由于点击进入后 只有一个集合对象而 不进行容错处理会出现右面头像的空指针异常*/
        if (mlist.get(i).getFlag()==ListViewData.SEND){
            ivHeadRight  =(ImageView) mView.findViewById(R.id.iv_head_right);
        }
        // 右面头像点击事件
        if(ivHeadRight!=null){
            ivHeadRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "亲，自己就不用介绍了！！！", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
