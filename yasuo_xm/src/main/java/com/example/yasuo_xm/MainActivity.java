package com.example.yasuo_xm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvYasuo;
    /**
     * 设置进去
     */
    private Button mBtnShezhi; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
    }

    private void initView() {
        mIvYasuo = (ImageView) findViewById (R.id.iv_yasuo);
        mBtnShezhi = (Button) findViewById (R.id.btn_shezhi);
        mBtnShezhi.setOnClickListener (this);
        WindowManager systemService = (WindowManager) getSystemService (WINDOW_SERVICE);
        Display defaultDisplay = systemService.getDefaultDisplay ();
        int width = defaultDisplay.getWidth ();
        int height = defaultDisplay.getHeight ();

    }

    private void check() {
        //判断是否6.0以上的手机   不是就不用
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission (this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions (this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                //  writeToSdCard2();
                writeToSdCard3 ();
            }
        }


    }

    //裁剪压缩 压缩是图片尺寸 基于内存压缩的
    private void writeToSdCard3() {
        Bitmap bitmap = BitmapFactory.decodeResource (getResources (), R.mipmap.a);
        Matrix matrix = new Matrix ();
        matrix.setScale (0.1F, 0.1F);
        Bitmap newBitmap = Bitmap.createBitmap (bitmap, 0, 0, bitmap.getWidth (), bitmap.getHeight (), matrix, true);
        int mp = newBitmap.getByteCount ();
        Log.e ("tag", "mp" + mp + "宽度:" + newBitmap.getWidth () + "高度:" + newBitmap.getHeight ());

        mIvYasuo.setImageBitmap (bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            default:
                break;
            case R.id.btn_shezhi:
                check ();
                break;
        }
    }
    //图片二次采样 (采用两次尝试解析图片，第一次不是完全解析，第一次)


}
