package com.example.wanandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanandroid.R;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<BannerBean.DataBean> bannerList;
    private ArrayList<HomeBean.DataBean.DatasBean> homeList;
    private OnItemCliclListener onItemCliclListener;

    public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
        this.onItemCliclListener = onItemCliclListener;
    }

    public HomeAdapter(Context context, ArrayList<BannerBean.DataBean> bannerList, ArrayList<HomeBean.DataBean.DatasBean> homeList) {
        this.context = context;
        this.bannerList = bannerList;
        this.homeList = homeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int type = getItemViewType (i);
        switch (type) {
            case 0:
                View inflate = LayoutInflater.from (context).inflate (R.layout.item_banner, null);
                return new ViewHolder (inflate);
            case 1:
                View inflate1 = LayoutInflater.from (context).inflate (R.layout.item_home, null);
                return new ViewHolder1 (inflate1);
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        int type = getItemViewType (i);
        if (type == 0) {
            ViewHolder viewHolder1 = (ViewHolder) viewHolder;
            ArrayList<Uri> banner = new ArrayList<> ();
            banner.add (Uri.parse ("https://www.wanandroid.com/blogimgs/7010eea2-16cc-4370-a3ca-9d372170f263.png"));
            banner.add (Uri.parse ("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png"));
            banner.add (Uri.parse ("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png"));
            banner.add (Uri.parse ("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png"));
            viewHolder1.banner.setImages (banner);
            viewHolder1.banner.setDelayTime (3000);
            viewHolder1.banner.setBannerStyle (BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            int numIndicatorTitle = BannerConfig.NUM_INDICATOR_TITLE;
            viewHolder1.banner.setBannerStyle (numIndicatorTitle);
            String[] strings = {"Android程序员简历这么写，面试邀约20+", "一起来做个App吧", "我们新增了一个常用导航Tab~", "flutter 中文社区"};
            viewHolder1.banner.setBannerTitles (Arrays.asList (strings));
            viewHolder1.banner.setPageTransformer (true, new RotationPageTransformer ());
            viewHolder1.banner.setImageLoader (new ImageLoader () {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.transform (new RoundedCorners (20));
                    Glide.with (context).load ((Uri) path).apply (requestOptions).into (imageView);
                }
            }).start ();
        } else if (type == 1) {
            ViewHolder1 viewHolder1 = (ViewHolder1) viewHolder;
            HomeBean.DataBean.DatasBean datasBean = homeList.get (i);
            boolean fresh = datasBean.isFresh ();
            if (fresh == true) {
                viewHolder1.iv_new.setVisibility (View.VISIBLE);
            } else {
                viewHolder1.iv_new.setVisibility (View.GONE);
            }
            viewHolder1.tv_author.setText (datasBean.getAuthor ());
            viewHolder1.tv_chapterName.setText ("/ " + datasBean.getChapterName ());
            viewHolder1.tv_niceDate.setText (datasBean.getNiceDate ());
            viewHolder1.tv_superChapterName.setText (datasBean.getSuperChapterName ());
            viewHolder1.tv_title.setText (datasBean.getTitle ());
            viewHolder1.view.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    if (onItemCliclListener != null) {
                        onItemCliclListener.onItemClick (i);
                    }
                }
            });
        }
    }


    public class RotationPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;

        @Override
        public void transformPage(View page, float position) {
            float scaleFactor = Math.max (MIN_SCALE, 1 - Math.abs (position));
            float rotate = 10 * Math.abs (position);
            //position小于等于1的时候，代表page已经位于中心item的最左边，
            //此时设置为最小的缩放率以及最大的旋转度数
            if (position <= -1) {
                page.setScaleX (MIN_SCALE);
                page.setScaleY (MIN_SCALE);
                page.setRotationY (rotate);
            } else if (position < 0) {//position从0变化到-1，page逐渐向左滑动
                page.setScaleX (scaleFactor);
                page.setScaleY (scaleFactor);
                page.setRotationY (rotate);
            } else if (position >= 0 && position < 1) {//position从0变化到1，page逐渐向右滑动
                page.setScaleX (scaleFactor);
                page.setScaleY (scaleFactor);
                page.setRotationY (-rotate);
            } else if (position >= 1) {//position大于等于1的时候，代表page已经位于中心item的最右边
                page.setScaleX (scaleFactor);
                page.setScaleY (scaleFactor);
                page.setRotationY (-rotate);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if (bannerList.size () > 0) {
            return homeList.size () - 1;
        } else {
            return homeList.size ();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
            banner = itemView.findViewById (R.id.banner);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        private TextView tv_author, tv_chapterName, tv_superChapterName, tv_niceDate, tv_title;
        private View view;
        private ImageView iv_new;

        public ViewHolder1(@NonNull View itemView) {
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
