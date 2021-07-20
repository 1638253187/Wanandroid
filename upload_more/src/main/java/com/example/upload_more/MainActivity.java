package com.example.upload_more;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.upload_more.adapter.GlideLoader;
import com.example.upload_more.adapter.UpBean;
import com.google.gson.Gson;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class
MainActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 来个金色传说
     */
    private Button mBtnUp;
    private ImageConfig imageConfig;
    private ArrayList<String> list_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        list_path = new ArrayList<> ();
        list_path.clear ();
        imageConfig = new ImageConfig.Builder (MainActivity.this, new GlideLoader ())
                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                .steepToolBarColor (getResources ().getColor (R.color.blue))
                // 标题的背景颜色 （默认黑色）
                .titleBgColor (getResources ().getColor (R.color.blue))
                //提交按钮字体的颜色(默认白色)
                .titleSubmitTextColor (getResources ().getColor (R.color.white))
                //标题颜色(默认白色)
                .titleTextColor (getResources ().getColor (R.color.white))
                //开启多选(默认为多选)(单选 为 singleSelect)
                .mutiSelect ()
                //多选时的最大数量(默认 9张)
                .mutiSelectMaxSize (9)
                //已选择的图片路径
                .pathList (list_path)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath ("/ImageSelector/Pictures")
                // 开启拍照功能 （默认关闭）
                .showCamera ()
                .build ();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {


            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra (ImageSelectorActivity.EXTRA_RESULT);
            for (final String path : pathList) {
                File file = new File (path);

                OkHttpClient okHttpClient = new OkHttpClient.Builder ()
                        .build ();

                MediaType mediaType = MediaType.parse ("image/png");

                RequestBody requestBody = RequestBody.create (mediaType, file);

                MultipartBody multipartBody = new MultipartBody.Builder ()
                        .setType (MultipartBody.FORM)
                        .addFormDataPart ("key", "123")
                        .addFormDataPart ("file", file.getName (), requestBody).build ();

                Request request = new Request.Builder ()
                        .post (multipartBody).url ("http://yun918.cn/study/public/file_upload.php")
                        .build ();
                Call call = okHttpClient.newCall (request);
                call.enqueue (new Callback () {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e ("tag", e.getMessage ());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body ().string ();
                        final UpBean upBean = new Gson ().fromJson (json, UpBean.class);
                        runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                if (upBean != null) {
                                    if (upBean.getCode () == 200) {
                                        Toast.makeText (MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show ();
                                    } else {
                                        Toast.makeText (MainActivity.this, upBean.getCode (), Toast.LENGTH_SHORT).show ();
                                    }
                                } else {
                                    Toast.makeText (MainActivity.this, upBean.getRes (), Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    private void initView() {
        mBtnUp = (Button) findViewById (R.id.btn_up);
        mBtnUp.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            default:
                break;
            case R.id.btn_up:
                list_path.clear ();
                ImageSelector.open (imageConfig); // 开启图片选择器
                break;
        }
    }
//    public void RecyAdapter (RecyclerView.Adapter<re>){

    }

