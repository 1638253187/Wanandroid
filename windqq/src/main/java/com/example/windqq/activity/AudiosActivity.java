package com.example.windqq.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.example.windqq.R;
import com.example.windqq.adapter.MsgAdapter;
import com.example.windqq.app.SysUtils;
import com.example.windqq.bean.Constants;
import com.example.windqq.bean.DaoCallBean;
import com.example.windqq.bean.DaoMsg;
import com.example.windqq.bean.DaoUserBean;
import com.example.windqq.event.MqttArrivedMsgEvent;
import com.example.windqq.event.MqttToSendEvent;
import com.example.windqq.service.MqttUtil;
import com.example.windqq.util.ActivityHook;
import com.example.windqq.util.PictureFileUtil;
import com.example.windqq.util.VoiceDbUtil;
import com.example.windqq.view.ChatUiHelper;
import com.example.windqq.view.CircleImagesView;
import com.example.windqq.view.RecordsButton;
import com.example.windqq.view.StateButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*稳定版 有文字 半语音消息(语音消息无法发送)*/
public class AudiosActivity extends BaseActivity {

    //message_text
    public String MESSAGE_TYPE = "";
    public static final int REQUEST_CODE_IMAGE = 1111;
    public static final int REQUEST_CODE_VEDIO = 2222;
    public static final int REQUEST_CODE_FILE = 3333;

    @BindView(R.id.llContent)
    LinearLayout mLlContent;
    @BindView(R.id.rv_chat_list)
    RecyclerView mRvChat;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.bottom_layout)
    RelativeLayout mRlBottomLayout;//表情,添加底部布局
    @BindView(R.id.ivAdd)
    ImageView mIvAdd;
    @BindView(R.id.ivEmo)
    ImageView mIvEmo;

    @BindView(R.id.ivAudio)
    ImageView mIvAudio;//录音图片
    @BindView(R.id.btnAudio)
    RecordsButton mBtnAudio;//录音按钮
    @BindView(R.id.rlEmotion)
    LinearLayout mLlEmotion;//表情布局
    @BindView(R.id.llAdd)
    LinearLayout mLlAdd;//添加布局
    @BindView(R.id.swipe_chat)
    SwipeRefreshLayout mSwipeRefresh;//下拉刷新
    @BindView(R.id.common_toolbar_back)
    RelativeLayout mBack_toolbar;//下拉刷新
    @BindView(R.id.common_toolbar_title)
    TextView commonToolbarTitle;
    @BindView(R.id.iv_user_about)
    CircleImagesView ivUserAbout;
    @BindView(R.id.rl_video)
    RelativeLayout rlVideo;
    @BindView(R.id.tv_video)
    TextView tvVideo;
    private int page = 1;
    private static final int DURATION = 15;
    private MsgAdapter adapters;
    private ChatUiHelper mUiHelper;
    private String user;
    private String code;
    private String user_name;
    private DaoMsg daoMsg;
    private String fromUser_name;
    private List<DaoMsg> wxTwentyMsg;
    private String[] users;
    private DaoMsg group_daoMsg;
    private String fromUser_ID;
    private Intent intents_group;
    private Intent intents;
    private int types;
    private Intent intent;
    private int onlines;
    private String gpName;
    private List<File> imageList = new ArrayList<>();
    private int size;

    /**
     * 语音开始
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityHook.hookOrientation(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);
        ivUserAbout.setVisibility(View.VISIBLE);
        EventBus.getDefault().register(this);
        intents = new Intent();
        intents.setAction("action.refreshuser");
        intents_group = new Intent();
        intents_group.setAction("action.refreshuser_group");
        /*刷新聊天*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshmessage");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        Bundle bundle = this.getIntent().getExtras();
        user_name = bundle.getString("users");
        code = bundle.getString("codes");
        types = bundle.getInt("types", 100);
        onlines = bundle.getInt("onlins", 1);
        code.replace(",", "");
        users = new String[0];
        users = code.split(",");
        //Log.e("tag", "这是code:" + code);
        if (types == Constants.GROUP_CHAT) {
            MESSAGE_TYPE = "group_text";
            tvVideo.setText("群视频");
            //MqttService.setSubscribe();
        } else {
            MESSAGE_TYPE = "message_text";
            tvVideo.setText("视频");
        }
        //List<DaoUserBean> user = VoiceDbUtil.getInstance().getUser(code);
        //fromUser_name = user.get(1).getUser();
        initContent();
        initData();
        toBottom();
        initNATiVI();
    }

    public void setMessageType(String TYPE) {
        MESSAGE_TYPE = TYPE;
    }

    private void initData() {
        if (user_name == null) {
            Intent intent = getIntent();
            user = intent.getStringExtra("users");
            code = intent.getStringExtra("codes");
            if (user == null) {
                commonToolbarTitle.setText(code);
            }
        } else {
            commonToolbarTitle.setText(user_name);
        }
        List<DaoUserBean> userbean = VoiceDbUtil.getInstance().getUser(SysUtils.getDeviceId());
        if (userbean.size() > 1) {
            fromUser_name = userbean.get(1).getUser();
        } else {
            fromUser_name = SysUtils.getDeviceId();
        }
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ++page;
                loadData(page);
                //下拉
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshmessage")) {
//                initData();
//                addData();
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MqttArrivedMsgEvent messageEvent) {
        analyzeMqttMsg(messageEvent.getMsg());
    }

    private void analyzeMqttMsg(String message) {
        try {
            JSONObject msgJson = JSONObject.parseObject(message);
            String type = msgJson.getString("type");
            /*单聊*/
            if (type.equals("message_text")) {
                String content = msgJson.getString("content");
                String userid = msgJson.getString("userId");
//                Log.e("Tag", "Audios这是用户Audios:" + userid);
//                Log.e("Tag", "Audios这是内容:" + content);

                if (code.equals(userid)) {
                    daoMsg = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 1, content);
                    addData();
                }
            }

            /*群聊*/
            if (type.equals("group_text")) {
                String content = msgJson.getString("content");
                String group_userid = msgJson.getString("userId");
                String group_name = msgJson.getString("username");
                List<DaoUserBean> userbean = VoiceDbUtil.getInstance().getUserID(group_name);
                fromUser_ID = userbean.get(1).getUserId();
                if (code.equals(group_userid)) {
                    if (fromUser_ID.equals(SysUtils.getDeviceId())) {
//                        Log.e("tag", "自己发的消息 拦截");
                    } else {
                        group_daoMsg = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, group_name, 1, content);
                        addData();
//                        Log.e("tag", "是一样的code");
                    }
                } else {
                    Log.e("tag", "不一样");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addData() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*群聊保存聊天记录并展示*/
                if (MESSAGE_TYPE.equals("group_text")) {
                    if (group_daoMsg != null) {
                        adapters.addData(group_daoMsg);
                        adapters.notifyDataSetChanged();
                        toBottom();
                    }
                }
                /*单聊保存聊天记录*/
                if (MESSAGE_TYPE.equals("message_text")) {
                    if (daoMsg != null) {
                        adapters.addData(daoMsg);
                        adapters.notifyDataSetChanged();
                        toBottom();
                    }
                }
            }
        });
    }

    /*获取内容*/
    private void initContent() {
        ButterKnife.bind(this);
        adapters = new MsgAdapter(this, null, DURATION);
        LinearLayoutManager mLinearLayout = new LinearLayoutManager(this);
        mRvChat.setLayoutManager(mLinearLayout);
        mRvChat.setAdapter(adapters);
        adapters.setOnItemCliclListener(new MsgAdapter.OnItemCliclListener() {
            @Override
            public void onItemClick(int position) {
//                Intent intent = new Intent(AudiosActivity.this, UserAboutActivity.class);
//                intent.putExtra("users", user_name);
//                intent.putExtra("codes", code);
//                intent.putExtra("groupName", commonToolbarTitle.getText().toString());
//                if (types == Constant.GROUP_CHAT) {
//                    intent.putExtra("types", Constant.GROUP_CHAT);
//                } else {
//                    intent.putExtra("types", Constant.SINGLE_CHAT);
//                }
//                startActivity(intent);
            }
        });
        initChatUi();
        toBottom();
        mBack_toolbar.setOnClickListener(backClick);
        ivUserAbout.setOnClickListener(useraboutActivityClick);
        page = 1;
        loadData(page);
        toBottom();
    }

    /*发送文本消息*/
    private void initSend() {
        if (MESSAGE_TYPE.equals("message_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, mEtContent.getText().toString());
            String sendMessage = MqttUtil.getSendMessage(MESSAGE_TYPE, mEtContent.getText().toString(), SysUtils.getDeviceId(), fromUser_name);
            boolean ok = sendMessage.equals("ok");
            Log.e("tag", "数据是什么:" + sendMessage + "," + "是不是会相等:" + ok);
            //发送消息
            Log.e("tag", "不确定的code:" + "message/" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + code, sendMessage));

            //Log.e("tag", "sendMessage " + sendMessage);
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);
            //聊天记录beanDao
            DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我: " + mEtContent.getText().toString(), Constants.SINGLE_CHAT, System.currentTimeMillis());
            VoiceDbUtil.getInstance().insertUser(daoCallBean);
            //Log.e("tag", "audios数据是：" + daoCallBean.getContent());
            Log.e("tag", "audio数据为:" + daoCallBean.getContent());
            toBottom();
            sendBroadcast(intents);
        }

        if (MESSAGE_TYPE.equals("group_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, mEtContent.getText().toString());
            String sendMessage = MqttUtil.getSendGroupMessage(MESSAGE_TYPE, user_name, mEtContent.getText().toString(), code, fromUser_name);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + MESSAGE_TYPE + "/" + code, sendMessage));
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);
            //本地聊天记录beanDao
            DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我: " + mEtContent.getText().toString(), Constants.GROUP_CHAT, System.currentTimeMillis());
            VoiceDbUtil.getInstance().insertUser(daoCallBean);
            Log.e("tag", "Group数据为:" + daoCallBean.getContent());
            toBottom();
            sendBroadcast(intents_group);
        }
    }

    //滑动到底部
    private void toBottom() {
        if (adapters.getItemCount() > 0) {
            mRvChat.scrollToPosition(adapters.getItemCount() - 1);
        }
    }

    private void loadData(int page) {
        wxTwentyMsg = VoiceDbUtil.getInstance().getWXTwentyMsg(page, SysUtils.getDeviceId(), code);
        if (wxTwentyMsg != null) {
            //Log.e("tagdao", "数据是:" + wxTwentyMsg.size());
            if (wxTwentyMsg.size() > 0) {
                adapters.loadMore(wxTwentyMsg);
            } else {
                if (page > 2) {
                    Log.e("tag", "page：" + page);
                    Toast.makeText(this, "没有更多数据,您可以再尝试一下", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private View.OnClickListener useraboutActivityClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (types == Constants.GROUP_CHAT) {
//                Intent intent = new Intent(AudiosActivity.this, UserAboutActivity.class);
//                intent.putExtra("users", user_name);
//                intent.putExtra("codes", code);
//                intent.putExtra("userSize", users.length);
//                intent.putExtra("groupName", commonToolbarTitle.getText().toString());
//                intent.putExtra("types", Constant.GROUP_CHAT);
//                intent.putExtra("onlins", 1);
//                startActivity(intent);
            } else {
//                Intent intent = new Intent(AudiosActivity.this, UserAboutActivity.class);
//                intent.putExtra("users", user_name);
//                intent.putExtra("codes", code);
//                intent.putExtra("types", Constants.SINGLE_CHAT);
//                startActivity(intent);
            }
        }
    };

    private View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUiHelper.hideBottomLayout(false);
            mUiHelper.bindEmojiData();
            mUiHelper.hideSoftInput();
            mEtContent.clearFocus();
            finish();
        }
    };

    /*界面UI*/
    private void initChatUi() {
        //mBtnAudio
        mUiHelper = ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(mLlContent)

                .bindEditText(mEtContent)
                .bindBottomLayout(mRlBottomLayout)
                .bindEmojiLayout(mLlEmotion)
                .bindAddLayout(mLlAdd)
                .bindToAddButton(mIvAdd)
                .bindToEmojiButton(mIvEmo)
                .bindAudioBtn(mBtnAudio)
                .bindAudioIv(mIvAudio)
                .bindEmojiData();

        //底部布局弹出,聊天列表上滑
        mRvChat.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    mRvChat.post(new Runnable() {
                        @Override
                        public void run() {
                            if (adapters.getItemCount() > 0) {
                                mRvChat.smoothScrollToPosition(adapters.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });

        ((RecordsButton) mBtnAudio).setOnFinishedRecordListener(new RecordsButton.OnFinishedRecordListener() {
            @Override
            public void onFinishedRecord(String audioPath, int time) {
                Log.d("tag", "录音结束回调");
                File file = new File(audioPath);
                if (file.exists()) {
                    if (MESSAGE_TYPE.equals("message_text")) {
                        DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "语音片段···" + time + "S" + "\r\n" + " 地址:" + audioPath);
                        String sendMessage = MqttUtil.getSendMessage(MESSAGE_TYPE, "语音片段···" + time + "S" + "\r\n" + " 地址:" + audioPath, SysUtils.getDeviceId(), fromUser_name);
                        //发送消息
                        //Log.e("tag", "不确定的code:" + "message/" + code);
                        EventBus.getDefault().post(new MqttToSendEvent("message/" + code, sendMessage));
                        //Log.e("tag", "sendMessage " + sendMessage);
                        //存入数据库
                        VoiceDbUtil.getInstance().insert(dao);
                        adapters.addData(dao);
                        //聊天记录beanDao
                        DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我: " + "语音片段···" + time + "S" + "\r\n" + " 地址:" + audioPath, Constants.SINGLE_CHAT, System.currentTimeMillis());
                        VoiceDbUtil.getInstance().insertUser(daoCallBean);
                        //Log.e("tag", "audios数据是：" + daoCallBean.getContent());
                        toBottom();
                        sendBroadcast(intents);
                    }

                    if (MESSAGE_TYPE.equals("group_text")) {
                        DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "语音片段···" + time + "S" + "\r\n" + " 地址:" + audioPath);
                        String sendMessage = MqttUtil.getSendGroupMessage(MESSAGE_TYPE, user_name, "语音片段···" + time + "S" + "\r\n" + " 地址:" + audioPath, code, fromUser_name);
                        EventBus.getDefault().post(new MqttToSendEvent("message/" + MESSAGE_TYPE + "/" + code, sendMessage));
                        //Log.e("tag_id", "不确定的code:" + "message/" + MESSAGE_TYPE);
                        //存入数据库
                        VoiceDbUtil.getInstance().insert(dao);
                        adapters.addData(dao);
                        //本地聊天记录beanDao
                        //DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getInstance().getDeviceId(), "我: " + "语音片段···" + time + "S", Constant.GROUP_CHAT, System.currentTimeMillis());
                        //VoiceDbUtil.getInstance().insertUser(daoCallBean);
                        //Log.e("tag", "group数据是：" + daoCallBean.getContent());
                        toBottom();
                        sendBroadcast(intents_group);
                    }
                }
            }
        });

        //点击空白区域关闭键盘
        mRvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mUiHelper.hideBottomLayout(false);
                mUiHelper.hideSoftInput();
                mEtContent.clearFocus();
                mIvEmo.setImageResource(R.mipmap.ic_emoji);
                return false;
            }
        });
    }

    @OnClick({ R.id.rlPhoto, R.id.rlVideo, R.id.rlFile, R.id.rl_video, R.id.rl_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.btn_send:
//                initSend();
//                mEtContent.setText("");
//                break;
            /*发送照片*/
            case R.id.rlPhoto:
                PictureFileUtil.openGalleryPic(AudiosActivity.this, REQUEST_CODE_IMAGE);
                break;
            /*发送本地视频*/
            case R.id.rlVideo:
                PictureFileUtil.openGalleryAudio(AudiosActivity.this, REQUEST_CODE_VEDIO);
                break;

            /*发送音频*/
            /*action*/
            /* 0 ---发起语音请求
               1 ---语音通话连接成功
               2 ---语音通话结束
               6 ---双方建立连接
               3 ---对方正在忙线
            */

            case R.id.rl_voice:
                if (types == Constants.GROUP_CHAT) {
                    /*群语音*/
                    String last_name = commonToolbarTitle.getText().toString();
//                    String sendAudio = MqttUtil.getAudio_Group(Constants.MQTT_AUDIO_GROUP, last_name, SysUtils.getDeviceId(), code, 0);
//                    EventBus.getDefault().post(new MqttToSendEvent("group/audio/message/" + code, sendAudio));
//                    Intent intent = new Intent(AudiosActivity.this, Audio_ChatActivity.class);
//                    intent.putExtra("FromCode", SysUtils.getDeviceId());
//                    intent.putExtra("type", "send");
//                    intent.putExtra("AllCode", code);
//                    startActivity(intent);
                } else if (types == Constants.SINGLE_CHAT) {
                    if (code.equals(SysUtils.getDeviceId())) {
                        Toast.makeText(this, "不能给自己拨打电话", Toast.LENGTH_SHORT).show();
                    } else {
//                        if (onlines == 1) {
//                            String sendAudio = MqttUtil.getAudio(Constant.MQTT_AUDIO_SINGLE, SysUtils.getInstance().getDeviceId(), code, 0);
//                            startService(new Intent(AudiosActivity.this, SocketService.class));
//                            /*双人语音*/
//                            EventBus.getDefault().post(new MqttToSendEvent("audio/client", sendAudio));
//                            Intent intent = new Intent(AudiosActivity.this, Audio_MainActivity.class);
//                            intent.putExtra("FromCode", code);
//                            intent.putExtra("type", "send");
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(AudiosActivity.this, "对方已离线", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }
                break;
//            /*视频*/
//            case R.id.rl_video:
//                if (ActivityUtil.isServiceWork(this, "com.demon.suspensionbox.FloatingService")) {//防止重复启动
//                    Toast.makeText(this, "已启动！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (!Settings.canDrawOverlays(this)) {
//                        Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
//                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
//                    } else {
//                        if (types == Constant.GROUP_CHAT) {
//                            /*群视频*/
//                            Toast.makeText(this, "开始群视频", Toast.LENGTH_SHORT).show();
//                            String sendMessage = MqttUtil.getSendGroupVideo("group_video", commonToolbarTitle.getText().toString(), code, SysUtils.getInstance().getDeviceId(), 0, users.length);
//                            EventBus.getDefault().post(new MqttToSendEvent("group/video/message/" + code, sendMessage));
////                          Log.e("tag_video", "参数是:" + "message/" + sendMessage);
//                            this.intent = new Intent(AudiosActivity.this, Group_ChatActivity.class);
//                            this.intent.putExtra("action", 0);
//                            this.intent.putExtra("video_user", SysUtils.getInstance().getDeviceId());
//                            this.intent.putExtra("groupUserId", code);
//                            this.intent.putExtra("groupName", commonToolbarTitle.getText().toString());
//                            this.intent.putExtra("userSize", users.length);
//                            this.intent.putExtra("toId", code);
//                            startActivity(this.intent);
//                        } else {
//                            /*双人视频*/
//                            /*设置点击后两秒内不可点击*/
//                            intent = new Intent(AudiosActivity.this, MainActivity_Tk.class);
//                            intent.putExtra("action", 0);
//                            intent.putExtra("toId", code);
//                            if (onlines == 1) {
//                                initVideo();
//                                startActivity(intent);
//                                AudiosActivity.this.finish();
//                            } else {
//                                Toast.makeText(AudiosActivity.this, "对方已离线", Toast.LENGTH_SHORT).show();
////                                Log.e("tag", "视频的离线");
//                            }
//                        }
//                    }
//                }
//                break;
            /*发送文件*/
            case R.id.rlFile:
//                PictureFileUtil.openFile(AudiosActivity.this, REQUEST_CODE_FILE);
                break;
        }
    }

    /*图片保存下来*/
    public void saveToSystemGallery(Bitmap bmp) {
        // 首先保存图片
        File fileDir = new File(Environment.getExternalStorageDirectory(), Constants.SAVE_IMG_PATH);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(fileDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
        //图片保存成功，图片路径：
        //Log.e("tag", "图片保存成功 路径:" + file.getAbsolutePath());
    }

    /*返回结果*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_FILE:
//                    String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
////                    Log.d("tag", "获取到的文件路径:" + filePath);
//                    sendFileMessage(filePath);
                    break;
                case REQUEST_CODE_IMAGE:
                    //图片选择结果回调
                    List<LocalMedia> selectListPic = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListPic) {
//                        Log.e("tag", "获取图片路径成功:" + media.getPath());
                        imageList.add(new File(media.getPath()));
                        size = imageList.size();
                        sendImageMessage(size, media);
                    }
//                    Log.e("tag", "总共发送了:" + size + "张照片");
                    break;
                case REQUEST_CODE_VEDIO:
                    //视频选择结果回调
                    List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectListVideo) {
//                        Log.d("tag", "获取视频路径成功:" + media.getPath());
                        sendVedioMessage(media);
                    }
                    break;
            }
        }
    }

    /*发送视频消息*/
    private void sendVedioMessage(LocalMedia media) {
        if (MESSAGE_TYPE.equals("message_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "视频···" + "   " + media.getPath());
            String sendMessage = MqttUtil.getSendMessage(MESSAGE_TYPE, "视频···" + "   " + media.getPath(), SysUtils.getDeviceId(), fromUser_name);
            //发送消息
//            Log.e("tag", "不确定的code:" + "message/" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + code, sendMessage));
//            Log.e("tag", "sendMessage:" + sendMessage);
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);

            //聊天记录beanDao
            DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我: " + "视频···" + "   " + media.getPath(), Constants.SINGLE_CHAT, System.currentTimeMillis());
            VoiceDbUtil.getInstance().insertUser(daoCallBean);
//            Log.e("tag", "audios数据是：" + daoCallBean.getContent());
            toBottom();
            sendBroadcast(intents);
        }

        if (MESSAGE_TYPE.equals("group_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "视频···" + "   " + media.getPath());
            String sendGroupMessage = MqttUtil.getSendGroupMessage(MESSAGE_TYPE, user_name, "视频···" + "   " + media.getPath(), code, fromUser_name);
//            Log.e("tag_id", "群聊唯一ID为:" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + MESSAGE_TYPE + "/" + code, sendGroupMessage));
//            Log.e("tag_id", "不确定的code:" + "message/" + MESSAGE_TYPE);
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);
            toBottom();
            sendBroadcast(intents_group);
        }

        Toast.makeText(this, "发送一段视频,Path为:" + "\r\n" + media.getPath(), Toast.LENGTH_SHORT).show();
    }

    /*发送文件消息*/
    private void sendFileMessage(String filePath) {
        if (MESSAGE_TYPE.equals("message_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "文件···" + "   " + filePath);
            String sendMessage = MqttUtil.getSendMessage(MESSAGE_TYPE, "文件···" + "   " + filePath, SysUtils.getDeviceId(), fromUser_name);
            //发送消息
//            Log.e("tag", "不确定的code:" + "message/" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + code, sendMessage));
//            Log.e("tag", "sendMessage " + sendMessage);
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);
            //聊天记录beanDao
            DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我: " + "文件···" + "   " + filePath, Constants.SINGLE_CHAT, System.currentTimeMillis());
            VoiceDbUtil.getInstance().insertUser(daoCallBean);
//            Log.e("tag", "audios数据是：" + daoCallBean.getContent());
            toBottom();
            sendBroadcast(intents);
        }

        if (MESSAGE_TYPE.equals("group_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "文件···" + "   " + filePath);
            String sendMessage = MqttUtil.getSendGroupMessage(MESSAGE_TYPE, user_name, "文件···" + "   " + filePath, code, fromUser_name);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + MESSAGE_TYPE + "/" + code, sendMessage));
//            Log.e("tag_id", "不确定的code:" + "message/" + MESSAGE_TYPE);
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);
            toBottom();
            sendBroadcast(intents_group);
        }
    }

    //发送图片消息
    private void sendImageMessage(final int Size, final LocalMedia media) {
        if (MESSAGE_TYPE.equals("message_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "图片···" + "   " + media.getPath());
            String sendMessage = MqttUtil.getSendMessage(MESSAGE_TYPE, "图片···" + "   " + media.getPath(), SysUtils.getDeviceId(), fromUser_name);
            //发送消息
//            Log.e("tag", "不确定的code:" + "message/" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + code, sendMessage));
//            Log.e("tag", "sendMessage " + sendMessage);
            //存入数据库
            VoiceDbUtil.getInstance().insert(dao);
            adapters.addData(dao);

            //聊天记录beanDao
            DaoCallBean daoCallBean = new DaoCallBean(null, commonToolbarTitle.getText().toString(), code, SysUtils.getDeviceId(), "我:" + "图片···" + "   " + media.getPath(), Constants.SINGLE_CHAT, System.currentTimeMillis());
            VoiceDbUtil.getInstance().insertUser(daoCallBean);
//            Log.e("tag", "audios数据是：" + daoCallBean.getContent());
            toBottom();
            sendBroadcast(intents);
        }

        if (MESSAGE_TYPE.equals("group_text")) {
            DaoMsg dao = new DaoMsg(null, System.currentTimeMillis(), SysUtils.getDeviceId(), code, user_name, 0, "图片···" + "   " + media.getPath());
            String sendMessage = MqttUtil.getSendGroupMessage(MESSAGE_TYPE, user_name, "图片···" + "   " + media.getPath(), code, fromUser_name);
//            Log.e("tag_id", "群聊唯一ID为:" + code);
            EventBus.getDefault().post(new MqttToSendEvent("message/" + MESSAGE_TYPE + "/" + code, sendMessage));
//            Log.e("tag_id", "不确定的code:" + "message/" + MESSAGE_TYPE);
            //存入数据库
            boolean insert = VoiceDbUtil.getInstance().insert(dao);
            if (insert) {
                Log.e("tag", "存入数据库成功");
            }
            adapters.addData(dao);
            toBottom();
            sendBroadcast(intents_group);
        }
    }

    private void initVideo() {
        List<DaoUserBean> fromuser = VoiceDbUtil.getInstance().getUser(SysUtils.getDeviceId());
        if (fromuser.size() > 1) {
            gpName = fromuser.get(1).getUser();
        } else {
            gpName = "好友";
        }

        String video = MqttUtil.getVideo("video_message", SysUtils.getDeviceId(), gpName, 0);
        EventBus.getDefault().post(new MqttToSendEvent("video/message" + code, video));
//        Log.e("tag", "video/message" + code);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        unregisterReceiver(mRefreshBroadcastReceiver);
        finish();
    }

    private void initNATiVI() {
        //设置底部导航栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }
    }
}
