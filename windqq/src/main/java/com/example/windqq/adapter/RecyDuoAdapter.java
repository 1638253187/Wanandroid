package com.example.windqq.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.windqq.R;
import com.example.windqq.bean.FoodBean;
import com.example.windqq.callback.TouchCallBack;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;

public class RecyDuoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements TouchCallBack {
    private Context context;
    private ArrayList<FoodBean.DataBean> list;
    private OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public RecyDuoAdapter(Context context, ArrayList<FoodBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int type = getItemViewType (i);
        switch (type) {
            case 0:
                View inflate = LayoutInflater.from (context).inflate (R.layout.item_f2, null);
                return new ViewHolder2 (inflate);

            case 1:
                View inflate1 = LayoutInflater.from (context).inflate (R.layout.item_fl, null);
                return new ViewHolder1 (inflate1);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int type = getItemViewType (i);
        if (type == 0) {
            ArrayList<Uri> strings = new ArrayList<> ();
            ViewHolder2 holder1 = (ViewHolder2) viewHolder;
            strings.add (Uri.parse ("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2120610136,808118452&fm=26&gp=0.jpg"));
            strings.add (Uri.parse ("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1017486331,1394773415&fm=26&gp=0.jpg"));
            strings.add (Uri.parse ("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=179843221,2843667560&fm=26&gp=0.jpg"));
            strings.add (Uri.parse ("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2402565495,782656767&fm=26&gp=0.jpg"));
            strings.add (Uri.parse ("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1686655742,600127603&fm=26&gp=0.jpg"));
            holder1.banner.setImageLoader (new ImageLoader () {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with (context).load ((Uri) path).into (imageView);
                }
            });
            holder1.banner.setDelayTime (5000);
            holder1.banner.setImages (strings);
            holder1.banner.start ();

        } else if (type == 1) {
            ViewHolder1 viewHolder1 = (ViewHolder1) viewHolder;
            FoodBean.DataBean resultsBean = list.get (i);
            viewHolder1.tv_desc.setText (resultsBean.getId ());
            viewHolder1.tv_type.setText (resultsBean.getTitle ());
            viewHolder1.tv_who.setText (resultsBean.getFood_str ());
            RequestOptions requestOptions = new RequestOptions ();
            requestOptions.circleCrop ();
            Glide.with (context).load (resultsBean.getPic ()).apply (requestOptions).into (viewHolder1.iv_url);
            viewHolder1.view.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    if (onItemCliclListener!=null){
                        onItemCliclListener.onItemClick (i);
                    }
                }
            });
        } else {

        }
    }
    @Override
    public int getItemCount() {
        return list.size ();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //通过集合工具类交换位置
        Collections.swap (list, fromPosition, toPosition);
        //局部刷新
        notifyItemMoved (fromPosition, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        list.remove (position);
        notifyItemRangeChanged (position, list.size () - position);
        notifyItemRemoved (position);
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return 0;
            default:
                return 1;
        }
    }
    class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tv_type, tv_desc, tv_who;
        private ImageView iv_url;
        private View view;

        public ViewHolder1(@NonNull View itemView) {
            super (itemView);
            tv_desc = itemView.findViewById (R.id.tv_desc);
            tv_type = itemView.findViewById (R.id.tv_type);
            tv_who = itemView.findViewById (R.id.tv_who);
            iv_url = itemView.findViewById (R.id.iv_url);
            view = itemView;
        }
    }
    class ViewHolder2 extends RecyclerView.ViewHolder {
        private Banner banner;
        public ViewHolder2(@NonNull View itemView) {
            super (itemView);
            banner = itemView.findViewById (R.id.banner);
        }
    }
    class ViewHolder3 extends RecyclerView.ViewHolder {
        private EditText et_pwd;
        public ViewHolder3(@NonNull View itemView) {
            super (itemView);
            et_pwd = itemView.findViewById (R.id.et_pwd);
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
