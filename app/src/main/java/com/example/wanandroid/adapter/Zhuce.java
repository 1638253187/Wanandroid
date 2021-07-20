package com.example.wanandroid.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wanandroid.R;
import com.example.wanandroid.ui.activity.Hello;
import com.example.wanandroid.util.ToastUtil;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Zhuce extends AppCompatActivity implements View.OnClickListener {

    /**
     * 邮箱/手机号
     */
    private EditText mEtPhone;
    /**
     * 输入密码
     */
    private EditText mEtPsw;
    /**
     * 确认密码
     */
    private EditText mEtPswagine;
    private ImageView mIvBack;
    /**
     * 注册
     */
    private Button mBtnZhuce;
    /**
     * 验证码
     */
    private Button mBtnYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_zhuce);
        initView ();
    }

    private void initView() {
        mEtPhone = (EditText) findViewById (R.id.et_phone);
        mEtPsw = (EditText) findViewById (R.id.et_psw);
        mEtPswagine = (EditText) findViewById (R.id.et_pswagine);
        mIvBack = (ImageView) findViewById (R.id.iv_back);
        mBtnZhuce = (Button) findViewById (R.id.btn_zhuce);
        mBtnZhuce.setOnClickListener (this);
        mBtnYzm = (Button) findViewById (R.id.btn_yzm);
        mBtnYzm.setOnClickListener (this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            default:
                break;
            case R.id.btn_zhuce:
                String phone = mEtPhone.getText ().toString ();
                String psw = mEtPsw.getText ().toString ();
                String pswagine = mEtPswagine.getText ().toString ();
                if (phone.isEmpty () || psw.isEmpty () || pswagine.isEmpty ()) {
                    ToastUtil.showShort ("请您输入完整");
                } else {
                    if (psw.matches ("[a-z1-9]{6,16}")) {
                        if (pswagine.matches ("[a-z1-9]{6,16}")) {
                            if (psw.equals (pswagine)) {
                                ToastUtil.showShort ("注册成功");
                                finish ();
                            } else {
                                ToastUtil.showShort ("两次密码输入不一致");
                            }
                        }
                    } else {
                        ToastUtil.showShort ("密码最低为六位");
                    }

                }
                break;
            case R.id.btn_yzm:
                Random random = new Random ();
                Set<Integer> set = new HashSet<Integer> ();
                while (set.size () < 4) {
                    int randomInt = random.nextInt (10);
                    set.add (randomInt);
                }
                StringBuffer sb = new StringBuffer ();
                for (Integer i : set) {
                    sb.append ("" + i);
                }
                new CountDownTimer (61000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mBtnYzm.setText (millisUntilFinished / 1000 + "");
                        mBtnYzm.setEnabled (false);
                        mBtnYzm.setBackgroundColor (Color.GRAY);
                    }

                    @Override
                    public void onFinish() {
                        mBtnYzm.setEnabled (true);
                        mBtnYzm.setText ("验证码");
                        mBtnYzm.setBackgroundColor (Color.rgb (225, 222, 222));
                        Random random = new Random ();
                        Set<Integer> set = new HashSet<Integer> ();
                        while (set.size () < 4) {
                            int randomInt = random.nextInt (10);
                            set.add (randomInt);
                        }
                        StringBuffer sb = new StringBuffer ();
                        for (Integer i : set) {
                            sb.append ("" + i);
                        }
                        mEtPhone.setText (sb.toString ());
                    }
                }.start ();
                ToastUtil.showShort (sb.toString ());
                break;
        }
    }
}
