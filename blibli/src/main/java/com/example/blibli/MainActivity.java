package com.example.blibli;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dou361.ijkplayer.widget.IjkVideoView;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;

public class MainActivity extends AppCompatActivity {

    private IjkVideoView mIjkIvRotation;
    private PlayerView playerView;

    /**
     * 请输入想要观看的视频网址
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
    }

    private void initView() {
        mIjkIvRotation = (IjkVideoView) findViewById (R.id.ijk_iv_rotation);
        View rootView = getLayoutInflater ().from (this).inflate (R.layout.simple_player_view_player, null);
        setContentView (rootView);
        playerView = new PlayerView (this)
                .setTitle ("凤凰网")
                .setScaleType (PlayStateParams.fitparent)
                .hideMenu (true)
                .forbidTouch (false)
                .setPlaySource ("http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4") //里面写你播放视频的地址
                .startPlay ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        playerView.onPause ();
    }

    @Override
    protected void onResume() {
        super.onResume ();
        playerView.onResume ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        playerView.onDestroy ();
    }
}
