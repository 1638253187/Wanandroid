package com.example.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wanandroid.R;

public class WellCome extends AppCompatActivity implements View.OnClickListener {

    private ImageView mSpBg;
    private Button mSpJumpBtn;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_well_come);

        initView ();
    }

    private void initView() {
        mSpBg = (ImageView) findViewById (R.id.sp_bg);
        mSpJumpBtn = (Button) findViewById (R.id.sp_jump_btn);
        mSpJumpBtn.setOnClickListener (this);
        mSpJumpBtn.setVisibility (View.VISIBLE);
        countDownTimer = new CountDownTimer (3200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mSpJumpBtn.setText ("跳过(" + millisUntilFinished / 1000 + "s)");
            }

            @Override
            public void onFinish() {
                mSpJumpBtn.setText ("跳过(" + 0 + "s)");
                gotoLoginOrMainActivity ();
                finish ();
            }
        }.start ();
    }

    private void gotoLoginOrMainActivity() {
        startActivity (new Intent (this, MainActivity.class));
        finish ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            default:
                break;
            case R.id.sp_jump_btn:
                startActivity (new Intent (this, MainActivity.class));
                countDownTimer.cancel ();
                finish ();
                break;
            case R.id.sp_bg:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy ();
        countDownTimer.cancel ();
        finish ();
    }
}
