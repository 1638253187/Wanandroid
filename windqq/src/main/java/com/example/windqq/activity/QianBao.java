package com.example.windqq.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windqq.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QianBao extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qian_bao);
        ButterKnife.bind(this);
        Toast.makeText(this, "这个还暂时没有开发出来呢", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv_back)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
