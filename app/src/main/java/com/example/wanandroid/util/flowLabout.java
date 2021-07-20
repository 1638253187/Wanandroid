package com.example.wanandroid.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/*Time:2019/3/25
 *Author:zhaozhiwei
 *Description:
 */public class flowLabout extends ViewGroup {


    public flowLabout(Context context) {
        super(context);
    }

    public flowLabout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public flowLabout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t2, int r2, int b3) {
        int witth = getWidth();
        int low=0;//自定义常量
        int diswith=50;//自定义间距
        for (int j=0;j<getChildCount();j++){
            View child = getChildAt(j);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if (diswith+width>witth){
                low++;
                diswith=10;
            }
            child.layout(diswith,low*height,diswith+width,height*(low+1));
            diswith+=width;
        }

    }
}
