package com.example.wanandroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.ChangBean;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.ui.FlowLayout;
import com.example.wanandroid.ui.activity.Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChangBean.DataBean> list;
    private OnItemCliclListener onItemCliclListener;
    private Random random;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public PopAdapter(Context context, ArrayList<ChangBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_nodenavi, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder voewHolder, int i) {
        if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++) {
                //加载textview布局‘
                TextView lable = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label1, null);
                lable.setText(list.get (j).getName ());
                random=new Random ();
                int currColor = (int) -(Math.random() * (16777216 - 1) + 1);//获取随机颜色
                int color=random.nextInt(0xffffff+1)+0xff000200;
                lable.setTextColor (Color.WHITE);
                lable.setBackgroundColor (currColor);
                //标签点击事件
                final int finalJ = j;
                lable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = list.get (finalJ).getLink ();
                        Intent intent = new Intent(context, Web.class);
                        intent.putExtra("link",link);
                        context.startActivity(intent);
                    }
                });

                //将Textview标签添加到Flowlayout
            voewHolder.ff.addView(lable);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FlowLayout ff;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              ff = itemView.findViewById (R.id.ff);
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
