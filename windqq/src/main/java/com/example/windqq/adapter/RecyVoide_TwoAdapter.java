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
import com.bumptech.glide.request.RequestOptions;
import com.example.windqq.R;
import com.example.windqq.bean.VideoBean;
import com.example.windqq.bean.VideoBean_Tow;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class RecyVoide_TwoAdapter extends RecyclerView.Adapter<RecyVoide_TwoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoBean_Tow.ResultBean> list;
    private MonImageViewListener mOnimageviewListener;
    private OnItemPlayListener mOnMideoListener;
    private OnItemCliclListener onItemCliclListener;
    public static ViewHolder viewHolder1;
    private int play = 0;

    public void setPlay(int play) {
        this.play = play;
        notifyDataSetChanged ();
    }


    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public void setmOnMideoListener(OnItemPlayListener mOnMideoListener) {
        this.mOnMideoListener = mOnMideoListener;
    }

    public RecyVoide_TwoAdapter(Context context, ArrayList<VideoBean_Tow.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setmOnimageviewListener(MonImageViewListener mOnimageviewListener) {
        this.mOnimageviewListener = mOnimageviewListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from (context).inflate (R.layout.item_video, null);
        return new ViewHolder (inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        VideoBean_Tow.ResultBean videobean = list.get (i);
        viewHolder1 = viewHolder;
        viewHolder.jz_video.setUp (videobean.getVideo (), viewHolder.jz_video.SCREEN_WINDOW_NORMAL, videobean.getText ());
//        viewHolder.jz_video.setVideoImageDisplayType (JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        Glide.with (context).load (videobean.getThumbnail ()).into (viewHolder.jz_video.thumbImageView);
        viewHolder.tv_topicName.setText (videobean.getName ());
        RequestOptions requestOptions = new RequestOptions ().circleCrop ();
        Glide.with (context).load (videobean.getHeader ()).apply (requestOptions).into (viewHolder.iv_top);
        viewHolder.iv_zan.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (mOnimageviewListener != null) {
                    mOnimageviewListener.onItemClick (v, i);
                }
            }
        });
        viewHolder.view.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (onItemCliclListener != null) {
                    onItemCliclListener.onItemClick (i);
                }
            }
        });


    }

    public static void StopPlay() {
        //暂停释放资源
        JZVideoPlayerStandard.releaseAllVideos ();
        viewHolder1.jz_video.release();
    }

    public void SetPlay(String video, String title) {
        viewHolder1.jz_video.setUp (video, viewHolder1.jz_video.SCREEN_WINDOW_NORMAL, "");
        viewHolder1.jz_video.startVideo ();

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private JZVideoPlayerStandard jz_video;
        private ImageView iv_top, iv_share, iv_zan;
        private TextView tv_topicName ;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            jz_video = itemView.findViewById (R.id.jzvideo);
            iv_top = itemView.findViewById (R.id.iv_top);
            tv_topicName = itemView.findViewById (R.id.tv_topicName);
            iv_share = itemView.findViewById (R.id.iv_share);
            iv_zan = itemView.findViewById (R.id.iv_zan);
            view = itemView;
        }
    }

    public interface MonImageViewListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemPlayListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }

}
