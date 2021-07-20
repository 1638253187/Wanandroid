package com.example.windqq.bean;



public class Constants {
    //1.正式环境

    public static final String PUBLISH_BASE_URL = "http://www.qubaobei.com/ios/cf/dish_list.php/";
    public static final String PUBLISH_BASE_URL2 = "http://www.qubaobai.com/ios/cf/dish_list.php/";

    public static final String SERVER_IP = "111.198.38.150";//Test


    /*保存的图片路径*/
    public static final String SAVE_IMG_PATH="storage/emulated/0/Huawei/MagazineUnlock/";
    public static final String DEFAULT_DEVICE_ID = "1234567890";
    public static final String SERVER = "server";
    public static final String MQTT_IP = "MQTT_IP";
    public static final String MQTT_PORT = "MQTT_PORT";
    public static final String VIDEO_IP = "VIDEO_IP";
    public static final String VIDEO_PORT = "VIDEO_PORT";
    public static final String AUDIO_IP = "AUDIO_IP";
    public static final String AUDIO_PORT = "AUDIO_PORT";
    public static final String MQTT_IP_DEFAULT = SERVER_IP;
    public static final String MQTT_PORT_DEFAULT = "1883";
    public static final String MQTT_AUDIO_GROUP = "audio/group";

    /*单聊*/
    public static final int SINGLE_CHAT = 100;

    /*群聊*/
    public static final int GROUP_CHAT = 300;

    //订阅的主题
    public static final String MQTT_VIDEO_S = "video/server";


    // 2.测试环境
    public static final String DEBUG_BASE_URL = "http://www.qubaobei.com/ios/cf/dish_list.php/";

    //测试接口
    public static final String TEST_API = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";

    /*公司*/
    public static final String auto_ip_port = SERVER_IP + ":8083";
}
