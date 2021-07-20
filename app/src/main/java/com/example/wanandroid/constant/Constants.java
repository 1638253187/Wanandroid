package com.example.wanandroid.constant;


public class Constants {
    //1.正式环境

    public static final String PUBLISH_BASE_URL = "http://www.qubaobei.com/ios/cf/dish_list.php/";
    public static final String PUBLISH_BASE_URL2 = "http://www.qubaobai.com/ios/cf/dish_list.php/";

    public static final String SERVER_IP = "111.198.38.150";//Test


    public static final String MQTT_IP = "MQTT_IP";
    public static final String MQTT_PORT = "MQTT_PORT";
    public static final String VIDEO_IP = "VIDEO_IP";
    public static final String VIDEO_PORT = "VIDEO_PORT";
    public static final String AUDIO_IP = "AUDIO_IP";
    public static final String AUDIO_PORT = "AUDIO_PORT";
    public static final String MQTT_IP_DEFAULT = SERVER_IP;





    // 2.测试环境
    public static final String DEBUG_BASE_URL = "http://www.qubaobei.com/ios/cf/dish_list.php/";

    //测试接口
    public static final String TEST_API = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";

    /*公司*/
    public static final String auto_ip_port = SERVER_IP + ":8083";
}
