package com.example.upload_more.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageLoader;

public class GlideLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with (context)
                .load (path)
                .placeholder (com.yancy.imageselector.R.mipmap.imageselector_photo)
                .centerCrop ()
                .into (imageView);
    }
}
