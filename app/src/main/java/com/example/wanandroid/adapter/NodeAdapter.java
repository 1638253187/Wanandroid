package com.example.wanandroid.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.ui.FlowLayout;
import com.example.wanandroid.ui.activity.Web;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DaoHBean.DataBean> list;
    private OnItemCliclListener onItemCliclListener;
    private Random random;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public NodeAdapter(Context context, ArrayList<DaoHBean.DataBean> list) {
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
        DaoHBean.DataBean dataBean = list.get(i);
        final List<DaoHBean.DataBean.ArticlesBean> articles = dataBean.getArticles();
        if (articles != null && articles.size() > 0) {
            voewHolder.ff.clearAll ();
            voewHolder.ff.removeAllViews ();
            for (int j = 0; j < articles.size(); j++) {
                //加载textview布局‘
                TextView lable = (TextView) LayoutInflater.from(context).inflate(R.layout.item_label, null);
                lable.setText(articles.get(j).getTitle());
                random=new Random ();
                int color=random.nextInt(0xffffff+1)+0xff000000;
                lable.setTextColor (color);
                //标签点击事件
                final int finalJ = j;
                lable.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = articles.get(finalJ).getLink();
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
