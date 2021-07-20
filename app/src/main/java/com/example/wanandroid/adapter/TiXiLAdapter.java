package com.example.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.HomeBean;
import com.example.wanandroid.bean.TiXiListBean;

import java.util.ArrayList;

public class TiXiLAdapter extends RecyclerView.Adapter<TiXiLAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TiXiListBean.DataBean.DatasBean> list;
    private HomeAdapter.OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(HomeAdapter.OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public TiXiLAdapter(Context context, ArrayList<TiXiListBean.DataBean.DatasBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from (context).inflate (R.layout.item_home, null);
        return new ViewHolder (inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        TiXiListBean.DataBean.DatasBean datasBean = list.get (i);
        viewHolder.tv_author.setText (datasBean.getAuthor ());
        viewHolder.tv_chapterName.setText ("/ " + datasBean.getChapterName ());
        viewHolder.tv_niceDate.setText (datasBean.getNiceDate ());
        viewHolder.tv_superChapterName.setText (datasBean.getSuperChapterName ());
        viewHolder.tv_title.setText (datasBean.getTitle ());
        viewHolder.view.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (onItemCliclListener != null) {
                    onItemCliclListener.onItemClick (i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_author, tv_chapterName, tv_superChapterName, tv_niceDate, tv_title;
        private View view;
        private ImageView iv_new;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            tv_title = itemView.findViewById (R.id.tv_title);
            tv_niceDate = itemView.findViewById (R.id.tv_niceDate);
            tv_chapterName = itemView.findViewById (R.id.tv_chapterName);
            tv_superChapterName = itemView.findViewById (R.id.tv_superChapterName);
            tv_author = itemView.findViewById (R.id.tv_author);
            iv_new = itemView.findViewById (R.id.iv_new);
            view = itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
