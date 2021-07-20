package com.example.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.bean.TiXiBean;

import java.util.ArrayList;
import java.util.List;

public class TiXiAdapter extends RecyclerView.Adapter<TiXiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TiXiBean.DataBean> list;
    private ArrayList<TiXiBean.DataBean.ChildrenBean> list_tow;
    private OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public TiXiAdapter(Context context, ArrayList<TiXiBean.DataBean> list, ArrayList<TiXiBean.DataBean.ChildrenBean> list_tow) {
        this.context = context;
        this.list = list;
        this.list_tow = list_tow;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from (context).inflate (R.layout.item_tixi, null);
        return new ViewHolder (inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        TiXiBean.DataBean dataBean = list.get (i);
        viewHolder.tv_name.setText (dataBean.getName ());
        List<TiXiBean.DataBean.ChildrenBean> children = dataBean.getChildren ();
        String s = children.toString ();
        String replace = s.replaceAll("[,]+", "   ");//替换字符 ','
        String substring = replace.substring (1, replace.length ()-1);
        viewHolder.tv_names.setText (substring+"\r\n");

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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_names;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            tv_name = itemView.findViewById (R.id.tv_name);
            tv_names = itemView.findViewById (R.id.tv_names);
            view = itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
