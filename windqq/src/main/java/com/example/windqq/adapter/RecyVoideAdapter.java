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
import com.example.windqq.R;
import com.example.windqq.bean.VideoBean;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class RecyVoideAdapter extends RecyclerView.Adapter<RecyVoideAdapter.ViewHolder> {
    private Context context;
    private ArrayList<VideoBean.ResultBean>list;
    private MonImageViewListener mOnimageviewListener;

    public RecyVoideAdapter(Context context, ArrayList<VideoBean.ResultBean> list) {
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
        VideoBean.ResultBean videobean = list.get (i);
        viewHolder.jz_video.setUp (videobean.getData ().getContent ().getData ().getPlayUrl (),viewHolder.jz_video.SCREEN_WINDOW_NORMAL,videobean.getData ().getHeader ().getTitle ());
        viewHolder.jz_video.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
      Glide.with (context).load (videobean.getData ().getContent ().getData ().getCover ().getDetail ()).into (viewHolder.jz_video.thumbImageView);
      viewHolder.tv_topicName.setText (videobean.getData ().getHeader ().getTitle ());
        viewHolder.jz_video.widthRatio = 4;
        viewHolder.jz_video.heightRatio = 3;
      Glide.with (context).load (videobean.getData ().getHeader ().getIcon ()).into (viewHolder.iv_top);
      viewHolder.iv_zan.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              if (mOnimageviewListener!=null){
                  mOnimageviewListener.onItemClick (v,i);
              }
          }
      });
      viewHolder.iv_zan.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick(View v) {
              if (mOnimageviewListener!=null){
                  mOnimageviewListener.onItemClick (v,i);
              }
          }
      });
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private JZVideoPlayerStandard jz_video;
        private ImageView iv_top,iv_share,iv_zan;
        private TextView tv_topicName;
        public ViewHolder(@NonNull View itemView) {
            super (itemView);
             jz_video = itemView.findViewById (R.id.jzvideo);
              iv_top = itemView.findViewById (R.id.iv_top);
            tv_topicName = itemView.findViewById (R.id.tv_topicName);
            iv_share = itemView.findViewById (R.id.iv_share);
              iv_zan = itemView.findViewById (R.id.iv_zan);
        }
    }

    public interface MonImageViewListener {
        void onItemClick(View v, int position);
    }
}
