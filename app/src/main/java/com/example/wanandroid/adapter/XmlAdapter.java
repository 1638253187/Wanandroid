package com.example.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.XmListBean;

import java.util.ArrayList;

public class XmlAdapter extends RecyclerView.Adapter<XmlAdapter.ViewHolder> {
    private ArrayList<XmListBean.DataBean.DatasBean> list;
    private Context context;
    private OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public XmlAdapter(ArrayList<XmListBean.DataBean.DatasBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public XmlAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from (context).inflate (R.layout.item_xml, null);
        return new ViewHolder (inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull XmlAdapter.ViewHolder viewHolder, final int i) {
        XmListBean.DataBean.DatasBean datasBean = list.get (i);
        viewHolder.tv_author.setText (datasBean.getAuthor ());
        viewHolder.tv_desc.setText (datasBean.getDesc ());
        viewHolder.tv_niceDate.setText (datasBean.getNiceDate ());
        viewHolder.tv_title.setText (datasBean.getTitle ());
        Glide.with (context).load (datasBean.getEnvelopePic ()).into (viewHolder.iv_envelopePic);
        viewHolder.view.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (onItemCliclListener!=null){
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
        private ImageView iv_envelopePic;
        private TextView tv_author, tv_desc, tv_title, tv_niceDate;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            tv_author = itemView.findViewById (R.id.tv_author);
            iv_envelopePic = itemView.findViewById (R.id.iv_envelopePic);
            tv_desc = itemView.findViewById (R.id.tv_desc);
            tv_title = itemView.findViewById (R.id.tv_title);
            tv_niceDate = itemView.findViewById (R.id.tv_niceDate);
            view = itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
