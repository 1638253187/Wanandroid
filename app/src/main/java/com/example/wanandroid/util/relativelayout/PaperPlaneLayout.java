package com.example.wanandroid.util.relativelayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wanandroid.R;

import java.util.Random;

public class PaperPlaneLayout extends RelativeLayout implements View.OnClickListener {
    private OnClickListener mOnClickListener;

    //自定义布局的宽、高
    private int mHeight;
    private int mWidth;
    private LayoutParams lp;
    private Drawable[] drawables;
    private Random random = new Random ();

    //获取4架纸飞机的宽高
    private int dHeight;
    private int dWidth;

    private int mX;
    private int mY;

    public PaperPlaneLayout(Context context) {
        super (context);
        init ();
    }

    public PaperPlaneLayout(Context context,
                            AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    public PaperPlaneLayout(Context context,
                            AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init ();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaperPlaneLayout(Context context,
                            AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super (context, attrs, defStyleAttr, defStyleRes);
        init ();
    }

    private void init() {
        // 初始化显示的图片
        drawables = new Drawable[4];
        Drawable pink =
                getResources ().getDrawable (R.drawable.ic_clear_all);
        Drawable yellow =
                getResources ().getDrawable (R.drawable.ic_clear_all);
        Drawable green =
                getResources ().getDrawable (R.drawable.ic_clear_all);
        Drawable blue =
                getResources ().getDrawable (R.drawable.ic_clear_all_gone);

        drawables[0] = blue;
        drawables[1] = yellow;
        drawables[2] = green;
        drawables[3] = pink;
        // 获取图的宽高 用于后面的计算
        // 注意 我这里4张图片的大小都是一样的,所以我只取了一个
//        dHeight = UIUtility.dipTopx(getContext(), 80);
//        dWidth = UIUtility.dipTopx(getContext(), 80);
        lp = new LayoutParams (dWidth, dHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth ();
        mHeight = getMeasuredHeight ();
    }

    //真正动画开始的入口，从外部进行调用，x、y分别表示飞机进入之后所
    //停留的位置坐标
    public void addHeart(int x, int y, int position) {
        mX = x;
        mY = y;
        ImageView imageView = new ImageView (getContext ());
        // 随机选一个
        imageView.setImageDrawable (drawables[position]);
        imageView.setLayoutParams (lp);
        addView (imageView);
        //获取进入前后动画
        Animator set = getAnimator (imageView);
        set.start ();
        imageView.setOnClickListener (this);
    }

    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimator (target);
        AnimatorSet set2 = getLineAnimation (target);
        AnimatorSet finalSet = new AnimatorSet ();
        finalSet.playSequentially (set, set2);
        finalSet.setInterpolator (new LinearInterpolator ());
        finalSet.setTarget (target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimator(final View target) {
        ObjectAnimator alpha = ObjectAnimator
                .ofFloat (target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator translationX = ObjectAnimator
                .ofFloat (target, View.TRANSLATION_X,
                        -2 * mWidth, -mWidth);
        AnimatorSet enter = new AnimatorSet ();
        enter.setDuration (500);
        enter.setInterpolator (new LinearInterpolator ());
        enter.playTogether (translationX, alpha);
        enter.setTarget (target);
        return enter;
    }

    private AnimatorSet getLineAnimation(final View iconView) {
        ObjectAnimator transX = ObjectAnimator
                .ofFloat (iconView, "translationX", -dWidth, mX);
        ObjectAnimator transY = ObjectAnimator
                .ofFloat (iconView, "translationY",
                        (mHeight - dHeight) / 2, mY);
        transY.
                setInterpolator (PathInterpolatorCompat
                        .create (0.7f, 1f));

        AnimatorSet flyUpAnim = new AnimatorSet ();
        flyUpAnim.setDuration (900);
        flyUpAnim.playTogether (transX, transY);
        flyUpAnim.setTarget (iconView);
        return flyUpAnim;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick ((ImageView) v);
        }
    }

    //定义ImageView单击事件
    public interface OnClickListener {
        void onClick(ImageView v);
    }
}