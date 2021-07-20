package com.example.windqq.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.windqq.R;
import com.example.windqq.bean.DaoMsg;
import com.example.windqq.util.VoiceDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private static final int TYPE_SEND = 0x01;
    private static final int TYPE_RECEIVE = 0x02;
    private final int mMaxWidth;
    private final int mMinWidth;
    private Context ctx;
    private final int duration;
    private final LayoutInflater mInflater;
    private List<DaoMsg> msgs;

    private MsgAdapter.OnItemCliclListener onItemCliclListener;

    private MyOnLongClickListener myOnLongClickListener;

    public void setOnLongClickListener(MyOnLongClickListener listener){
        this.myOnLongClickListener = listener;
    }

    public void setOnItemCliclListener(MsgAdapter.OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public MsgAdapter(Context ctx, List<DaoMsg> msgs, int duration) {
        this.ctx = ctx;
        this.duration = duration;
        if (msgs == null) this.msgs = new ArrayList<DaoMsg>();
        else this.msgs = msgs;
        mInflater = LayoutInflater.from(ctx);
        //获取屏幕的宽度
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        //最大宽度为屏幕宽度的百分之56
        mMaxWidth = (int) (outMetrics.widthPixels * 0.56f);
        //最小宽度为屏幕宽度的百分之16
        mMinWidth = (int) (outMetrics.widthPixels * 0.16f);
    }

    /**
     * 第一次加载
     * @param datas
     */
    public void setData(List<DaoMsg> datas) {
        msgs.clear();
        msgs.addAll(datas);
        notifyDataSetChanged();
    }

    public void loadMore(List<DaoMsg> datas) {
        msgs.addAll(0, datas);
        notifyItemChanged(0, datas.size());
        if (datas != null) {
            Log.e("tagdao", "是有数据的");
        }
    }

    /**
     * 添加一条
     * @param data
     */
    public void addData(DaoMsg data) {
        msgs.add(data);
        if (msgs.size() > 0) notifyItemInserted(msgs.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE_SEND:
                View view1 = mInflater.inflate(R.layout.chat_recycle_item_send_right, viewGroup, false);
                return new ViewHolder(view1);
            case TYPE_RECEIVE:
                View view2 = mInflater.inflate(R.layout.chat_recycle_item_receive_left, viewGroup, false);
                return new ViewHolder(view2);
            default:
                View view0 = mInflater.inflate(R.layout.chat_recycle_item_send_right, viewGroup, true);
                return new ViewHolder(view0);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        final int itemViewType = getItemViewType(position);
        final DaoMsg msg = msgs.get(position);
//        Log.e("ll", "onBindViewHolder(VoiceMsgAdapter.java:102---------)" + msg.getId());
        /*获取时间 并显示*/
        if (position == 0) {
            viewHolder.time.setText(VoiceDateUtils.getTimestampString(new Date(msg.getTime())));
            viewHolder.time.setVisibility(View.VISIBLE);
        } else {
            if (VoiceDateUtils.isCloseEnough(msg.getTime(), msgs.get(position - 1).getTime())) {
                viewHolder.time.setVisibility(View.GONE);
            } else {
                viewHolder.time.setText(VoiceDateUtils.getTimestampString(new Date(
                        msg.getTime())));
                viewHolder.time.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.Chat_item_content_text.setText(msg.getContent());

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String content= msgs.get(position).getContent();
                if (myOnLongClickListener!=null){
                    myOnLongClickListener.OnItemLongClickListener(content);
                }
                return false;
            }
        });

        /*如果是接收的消息 那么就获取发送人的姓名*/
        if (itemViewType == TYPE_RECEIVE) {
            viewHolder.tvName.setText(msg.getName());
            viewHolder.ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCliclListener != null) {
                        onItemCliclListener.onItemClick(position);
                    }
                }
            });


        }
    }

    @Override
    public int getItemViewType(int position) {
        DaoMsg msg = msgs.get(position);
        int deriction = msg.getDeriction();
        if (deriction == 0) {
            return TYPE_SEND;
        } else if (deriction == 1) {
            return TYPE_RECEIVE;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Chat_item_content_text;
        TextView time;
        ImageView ivIcon;
        TextView tvName;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.timestamp);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            Chat_item_content_text = (TextView) itemView.findViewById(R.id.chat_item_content_text);
            tvName = itemView.findViewById(R.id.tv_name);
            view=itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }


    public static interface MyOnLongClickListener{
        public void OnItemLongClickListener(String position);
    }
}
