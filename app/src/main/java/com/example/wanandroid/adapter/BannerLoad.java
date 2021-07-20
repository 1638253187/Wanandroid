package com.example.wanandroid.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.Rotate;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.wanandroid.bean.BannerBean;
import com.youth.banner.loader.ImageLoader;

public class BannerLoad extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri dataBean = (Uri) path;
        RequestOptions requestOptions = new RequestOptions ();
        requestOptions.transform (new RoundedCorners (30));
        Glide.with (context).load (dataBean).apply (requestOptions).into (imageView);
    }
}
