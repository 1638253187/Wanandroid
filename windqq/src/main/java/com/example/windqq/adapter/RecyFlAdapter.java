package com.example.windqq.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.windqq.R;
import com.example.windqq.bean.FlBean;

import java.util.ArrayList;

public class RecyFlAdapter extends RecyclerView.Adapter<RecyFlAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FlBean.ResultsBean> list;
    private OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public RecyFlAdapter(Context context, ArrayList<FlBean.ResultsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from (context).inflate (R.layout.fl_item, null);
        return new ViewHolder (inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        FlBean.ResultsBean resultsBean = list.get (i);
        viewHolder.tv_type.setText (resultsBean.getType ());
        Glide.with (context).load (resultsBean.getUrl ()).apply (new RequestOptions ().transform (new RoundedCorners (35))).into (viewHolder.iv_url);

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
        private ImageView iv_url;
        private TextView tv_type;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            iv_url = itemView.findViewById (R.id.iv_url);
            tv_type = itemView.findViewById (R.id.tv_type);
            view = itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
