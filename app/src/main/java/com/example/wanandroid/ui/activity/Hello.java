package com.example.wanandroid.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.Zhuce;
import com.example.wanandroid.util.ToastUtil;

public class Hello extends AppCompatActivity {

    /**
     * 请输入账号
     */
    private EditText mEditTextName;
    private TextInputLayout mTextInputLayoutName;
    private ImageButton mVisible;
    /**
     * 请输入密码
     */
    private EditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutpassword;
    private ImageView mLoad;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
    /**
     * 忘记密码
     */
    private TextView mTvPsw;
    /**
     * 用户注册
     */
    private TextView mTvZhuce;
    private PopupWindow popupWindow3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_hello);
        initView ();
    }

    private void initView() {
        mEditTextName = (EditText) findViewById (R.id.editTextName);
        mTextInputLayoutName = (TextInputLayout) findViewById (R.id.textInputLayoutName);
        mVisible = (ImageButton) findViewById (R.id.visible);
        mEditTextPassword = (EditText) findViewById (R.id.editTextPassword);
        mTextInputLayoutpassword = (TextInputLayout) findViewById (R.id.textInputLayoutpassword);
        mLoad = (ImageView) findViewById (R.id.load);
        mTvPsw = (TextView) findViewById (R.id.tv_psw);
        mTvZhuce = (TextView) findViewById (R.id.tv_zhuce);
        initLisnter ();


    }

    private void initLisnter() {
        mVisible.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (isHideFirst == true) {
                    mVisible.setBackground (getResources ().getDrawable (R.drawable.openeis));
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance ();
                    mEditTextPassword.setTransformationMethod (method1);
                    isHideFirst = false;
                } else {
                    mVisible.setBackground (getResources ().getDrawable (R.drawable.closeeis));
                    TransformationMethod method = PasswordTransformationMethod.getInstance ();
                    mEditTextPassword.setTransformationMethod (method);
                    isHideFirst = true;
                }


            }
        });
        mTvPsw.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

            }
        });
        mTvZhuce.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (Hello.this, Zhuce.class));
            }
        });
        mLoad.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText ().toString ();
                String password = mEditTextPassword.getText ().toString ();
                if (name.isEmpty ()) {
                    ToastUtil.showShort ("账号不能为空");
                } else if (password.isEmpty ()) {
                    ToastUtil.showShort ("密码不能为空");
                }
                if (name.matches ("[a-z1-9]{8,14}") || password.matches ("[1-9a-zA-Z]{8,16}")) {
                    startActivity (new Intent (Hello.this, Like.class));
                    SharedPreferences sp = getSharedPreferences ("mima", 0);
                    SharedPreferences.Editor edit = sp.edit ();
                    boolean clickable = mLoad.isClickable ();
                    if (clickable == true) {
                        edit.putString ("names", name);
                        edit.putString ("psw", password);
                        edit.putBoolean ("flag", true);
                        edit.commit ();
                    }
                    Toast.makeText (Hello.this, "用户:"+"\r\n" + "   "+ mEditTextName.getText ().toString () + "\r\n" + "      欢迎", Toast.LENGTH_SHORT).show ();
                    finish ();
                } else {
                    Window window = getWindow ();
                    WindowManager.LayoutParams lp = window.getAttributes ();
                    lp.alpha = 0.5f;
                    window.setAttributes (lp);
                    View inflate3 = LayoutInflater.from (Hello.this).inflate (R.layout.pop, null);
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
        });
    }
}
