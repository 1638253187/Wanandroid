package com.example.windqq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windqq.activity.MainActivity;
import com.example.windqq.activity.Tv_User;
import com.example.windqq.activity.Tv_zhu;
import com.example.windqq.app.SysUtils;
import com.example.windqq.bean.Constants;
import com.example.windqq.util.HideIMEUtil;
import com.gyf.barlibrary.ImmersionBar;

import org.w3c.dom.Text;

public class Hollo extends AppCompatActivity {

    private LinearLayout mHead;
    private ImageView mImage;
    /**
     * 输入账号
     */
    private EditText mNumber;
    private Spinner mSpinner;
    private RelativeLayout mBody;
    private ImageButton mVisible;
    /**
     * 输入密码
     */
    private EditText mPassword;
    private RelativeLayout mBody2;
    private ImageView mLoad;
    /**
     * 忘记密码
     */
    private TextView mTvPsw;
    /**
     * 用户注册
     */
    private TextView mTvZhuce;
    private PopupWindow popupWindow3;
    private String name;
    private String psw;
    private boolean isHideFirst = true;    // 输入框密码是否是隐藏的，默认为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        ImmersionBar.with (this)
                .transparentNavigationBar()//透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .statusBarDarkFont(false)
                .init ();
        setContentView (R.layout.activity_hollo);
        initView ();
        HideIMEUtil.wrap(this);
        SharedPreferences sp = getSharedPreferences ("mima", 0);
        boolean flag = sp.getBoolean ("flag", false);
        if (flag == true) {
            String names = sp.getString ("names", "");
            String psw = sp.getString ("psw", "");
            SysUtils.phone_sys=names;
            if (names.equals (SysUtils.phone_sys) && psw.equals ("123456")) {
                Toast.makeText (this, "用户识别完成", Toast.LENGTH_SHORT).show ();
                startActivity (new Intent (Hollo.this, MainActivity.class));
                finish ();
            } else {
                Toast.makeText (this, "不匹配", Toast.LENGTH_SHORT).show ();
            }
            mNumber.setText (names);
            mPassword.setText (psw);
        }

    }

    private void initView() {
        mHead = (LinearLayout) findViewById (R.id.head);
        mImage = (ImageView) findViewById (R.id.image);
        mNumber = (EditText) findViewById (R.id.number);
        mSpinner = (Spinner) findViewById (R.id.spinner);
        mBody = (RelativeLayout) findViewById (R.id.body);
        mVisible = (ImageButton) findViewById (R.id.visible);
        mPassword = (EditText) findViewById (R.id.password);
        mBody2 = (RelativeLayout) findViewById (R.id.body2);
        mLoad = (ImageView) findViewById (R.id.load);
        mTvPsw = (TextView) findViewById (R.id.tv_psw);
        mTvZhuce = (TextView) findViewById (R.id.tv_zhuce);
        initLisenter ();
    }

    private void initLisenter() {
        mVisible.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                if (isHideFirst == true) {
                    mVisible.setBackground (getResources ().getDrawable (R.drawable.openeis));
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance ();
                    mPassword.setTransformationMethod (method1);
                    isHideFirst = false;
                } else {
                    mVisible.setBackground (getResources ().getDrawable (R.drawable.closeeis));
                    TransformationMethod method = PasswordTransformationMethod.getInstance ();
                    mPassword.setTransformationMethod (method);
                    isHideFirst = true;
                }

            }


//                if (mVisible.isClickable ()) {
//                    mPassword.setInputType (Text.TEXT_NODE);
//                    isHideFirst=false;
//                } else {
//                    isHideFirst=true;
//                    if (isHideFirst=false) {
//                        mPassword.setInputType (Text.ATTRIBUTE_NODE);
//                    }
//                    }
        });


        mLoad.setOnClickListener (new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                name = mNumber.getText ().toString ().trim ();
                psw = mPassword.getText ().toString ().trim ();
                if (name.isEmpty () || psw.isEmpty ()) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    mNumber.startAnimation(shake);
                    mPassword.startAnimation(shake);
                    Toast.makeText (Hollo.this, "请输入账号密码", Toast.LENGTH_SHORT).show ();

                } else {
                    if (!name.isEmpty() && psw.equals ("123456")) {
                        SysUtils.phone_sys=name;
                        startActivity (new Intent (Hollo.this, MainActivity.class));
                        boolean clickable = mLoad.isClickable ();
                        SharedPreferences sp = getSharedPreferences ("mima", 0);
                        SharedPreferences.Editor edit = sp.edit ();
                        if (clickable == true) {
                            edit.putString ("names", name);
                            edit.putString ("psw", psw);
                            edit.putBoolean ("flag", true);
                            edit.commit ();
                        }
                        finish ();
                    } else {
                        Window window = getWindow ();
                        WindowManager.LayoutParams lp = window.getAttributes ();
                        lp.alpha = 0.5f;
                        window.setAttributes (lp);
                        View inflate3 = LayoutInflater.from (Hollo.this).inflate (R.layout.pop, null);
                        Button btn_dl = inflate3.findViewById (R.id.btn_dl);
                        btn_dl.setOnClickListener (new View.OnClickListener () {
                            @Override
                            public void onClick(View v) {
                                popupWindow3.dismiss ();
                            }
                        });
                        popupWindow3 = new PopupWindow (inflate3, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupWindow3.setBackgroundDrawable (new BitmapDrawable ());
                        popupWindow3.setOutsideTouchable (true);

                        /*
                            2.先创建动画的style 样式,去使用进出场动画

                         */
                        popupWindow3.setAnimationStyle (R.style.popAnimation);

                        popupWindow3.showAtLocation (mLoad, Gravity.CENTER, 0, 0);
                        popupWindow3.setOnDismissListener (new PopupWindow.OnDismissListener () {
                            @Override
                            public void onDismiss() {
                                Window window = getWindow ();
                                WindowManager.LayoutParams lp = window.getAttributes ();
                                lp.alpha = 1.0f;
                                window.setAttributes (lp);
                            }
                        });
                    }
                }
            }
        });


        mTvPsw.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Hollo.this, Tv_User.class));
            }
        });
        mTvZhuce.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Hollo.this, Tv_zhu.class));
            }
        });
    }

    private long exitTime = 0;
    public void onBackPressed() {
        doubleBackQuit ();
    }

    /**
     * 连续按两次返回键，退出应用
     */
    private void doubleBackQuit() {
        if (System.currentTimeMillis () - exitTime > 2000) {
            Toast.makeText (getApplicationContext (), "你真的真的要退出嘛", Toast.LENGTH_SHORT).show ();
            exitTime = System.currentTimeMillis ();
        } else {
            finish ();
        }
    }
}
