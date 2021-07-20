package com.example.byebur.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.byebur.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ByeFragment extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private ImageView mCloud;
    private ImageView mLauncher;
    private ImageView mRocket;
    private Handler mHandler = new Handler ();
    private Button btn;

    public ByeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_bye, container, false);
        recyclerView = (RecyclerView) inflate.findViewById (R.id.recyclerView);
        initRecyclerView ();
        initView (inflate);
        return inflate;
    }

    private void initRecyclerView() {
        MyAdapter myAdapter = new MyAdapter ();
        recyclerView.setLayoutManager (new LinearLayoutManager (getActivity (), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator (new DefaultItemAnimator ());
        recyclerView.addItemDecoration (new DividerItemDecoration (getActivity (), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter (myAdapter);
        myAdapter.setOnItemCliclListener (new OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                Toast.makeText (getActivity (), "我太帅了", Toast.LENGTH_SHORT).show ();
                launcherTheRocket ();
            }
        });
    }

    private void launcherTheRocket() {
        mHandler.postDelayed (new Runnable () {

            @Override
            public void run() {
                //初始化
                Animation rocketAnimation = AnimationUtils.loadAnimation (
                        getActivity (), R.anim.rocket);
                //设置动画监听
                rocketAnimation
                        .setAnimationListener (new VisibilityAnimationListener (
                                mRocket));
                //开启
                mRocket.startAnimation (rocketAnimation);

                Animation cloudAnimation = AnimationUtils.loadAnimation (
                        getActivity (), R.anim.cloud);
                cloudAnimation
                        .setAnimationListener (new VisibilityAnimationListener (
                                mCloud));
                mCloud.startAnimation (cloudAnimation);

                Animation launcherAnimation = AnimationUtils.loadAnimation (
                        getActivity (), R.anim.launcher);
                launcherAnimation
                        .setAnimationListener (new VisibilityAnimationListener (
                                mLauncher));
                mLauncher.startAnimation (launcherAnimation);

            }
        }, 150);

    }

    private void initView(View inflate) {
        mCloud = (ImageView) inflate.findViewById (R.id.cloud);
        mLauncher = (ImageView) inflate.findViewById (R.id.launcher);
        mRocket = (ImageView) inflate.findViewById (R.id.rocket);
    }

    public class VisibilityAnimationListener implements Animation.AnimationListener {

        private View mVisibilityView;

        public VisibilityAnimationListener(View view) {
            mVisibilityView = view;
        }

        public void setVisibilityView(View view) {
            mVisibilityView = view;
        }

        //动画开始
        @Override
        public void onAnimationStart(Animation animation) {
            Log.i ("START", "...");
            if (mVisibilityView != null) {
                mVisibilityView.setVisibility (View.VISIBLE);
                // mVisibilityView.setVisibility(View.GONE);
            }

        }

        //动画结束
        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i ("END", "...");
            if (mVisibilityView != null) {
                mVisibilityView.setVisibility (View.GONE);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }


    public class MyAdapter extends RecyclerView.Adapter {
        private OnItemCliclListener onItemCliclListener;

        public void setOnItemCliclListener(OnItemCliclListener onItemCliclListener) {
            this.onItemCliclListener = onItemCliclListener;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = (TextView) View.inflate (parent.getContext (), android.R.layout.simple_list_item_1, null);
            tv.setPadding (300, 20, 20, 20);
            tv.setGravity (Gravity.CENTER);
            return new MyViewHolder (tv);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.tv.setText ("item");
            holder.view.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    if (onItemCliclListener != null) {
                        onItemCliclListener.onItemClick (position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;
        public View view;

        public MyViewHolder(View itemView) {
            super (itemView);
            tv = (TextView) itemView;
            view = itemView;
        }
    }

    public interface OnItemCliclListener {
        void onItemClick(int position);
    }
}
