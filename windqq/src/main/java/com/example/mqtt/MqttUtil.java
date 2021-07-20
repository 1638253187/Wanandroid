package com.example.mqtt;

import android.util.Log;

/**
 * 类Util的实现描述
 */

public class MqttUtil {

    public static String getHeartBeatOnline(String deviceID) {
        return "{\"type\":\"heartbeat\",\"online\":true," + "\"deviceCode\":\"" + deviceID + "\"}";
    }

    public static String getAudio(String Type, String FromCode, String ToCode,String callUser, int Action) {
        return "{\"type\":\"" + Type + "\",\"FromCode\":\"" + FromCode + "\",\"ToCode\":\"" + ToCode + "\",\"callUser\":\"" + callUser+"\",\"action\":\"" + Action + "\"}";
    }

    public static String getAudio_Group(String Type, String Group_Audio, String FromCode, String ToCode, int Action) {
        return "{\"type\":\"" + Type + "\",\"Group_Audio\":\"" + Group_Audio + "\",\"FromCode\":\"" + FromCode + "\",\"ToCode\":\"" + ToCode + "\",\"action\":\"" + Action + "\"}";
    }

    /*
     *  {'type':'connect','commander':'13266668888','users':['863065035752780']}
     * */

    /*群聊:
     * :{'type':'connect','commander':'863065035752780','users':['863065035752780',
     *  '13266668888', '3baa62e538133f25', '65dea8ec1e49518d']}
     * */

    /*
     * audio/server:{'type':'connect','commander':'COM_COMMAND_0001','users':['13266668888']}
     */

    public static String ConnectAudio(String Type, String commander, String... users) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\'type\':\'" + Type + "\',\'commander\':\'" + commander + "\',\'users\':");
        for (int i = 0; i < users.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("[\'");
            sb.append(users[i]);
            sb.append("\']");
        }
        sb.append("}");
        Log.e("dayinjieguo", sb.toString());
        return sb.toString();
    }

    /*群语音*/
    public static String ConnectGroupAudio(String Type, String commander, String... users) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\'type\':\'" + Type + "\',\'commander\':\'" + commander + "\',\'users\':");
        for (int i = 0; i < users.length; i++) {
//            if (i!=0){
//                sb.append(",");
//            }
//            sb.append("[\'");
            sb.append(users[i]);
//            sb.append("\']");
        }
        sb.append("}");
        Log.e("dayinjieguo", sb.toString());
        return sb.toString();
    }

    public static String getStopAudio(String deviceID) {
        return "{\"type\":\"stop\"," + "\"user\":\"" + deviceID + "\"}";
    }

    public static String getGpsInfo(double lat, double lon, String deviceID) {
        return "{\"type\":\"gps\",\"miei\":\"" + deviceID + "\",\"lat\":\"" + lat + "\",\"lon\":\"" + lon + "\"}";
    }

    public static String ChangeFenBian(String type, String video_id, String width, String height) {
        return "{\"type\":\"" + type + "\",\"video_id\":\"" + video_id + "\",\"width\":\"" + width + "\",\"height\":\"" + height + "\"}";
    }


    public static String getVideo(String type, String deviceID, String callUser, int Action) {
        return "{\"type\":\"" + type + "\",\"video_id\":\"" + deviceID + "\",\"callUser\":\"" + callUser + "\",\"action\":\"" + Action + "\"}";
    }

    public static String getSendGroupVideo(String type, String groupID, String groupUserId, String callUser, int Action, int UserSize) {
        return "{\"type\":\"" + type + "\",\"groupId\":\"" + groupID + "\",\"groupUserId\":\"" + groupUserId + "\",\"callUser\":\"" + callUser + "\",\"action\":\"" + Action + "\",\"userSize\":\"" + UserSize + "\"}";
    }

    public static String getSendMessage(String type, String Content, String deviceID, String username) {
        return "{\"type\":\"" + type + "\",\"content\":\"" + Content + "\",\"userId\":\"" + deviceID + "\",\"username\":\"" + username + "\"}";
    }

    public static String getSendGroupMessage(String type, String GroupUser, String Content, String deviceID, String username) {
        return "{\"type\":\"" + type + "\",\"content\":\"" + Content + "\",\"groupuser\":\"" + GroupUser + "\",\"userId\":\"" + deviceID + "\",\"username\":\"" + username + "\"}";
    }

    public static String getSendGroupMessages(String type, String groupId, String groupUserId, String groupUser, String groupName, int UserSize) {
        return "{\"type\":\"" + type + "\",\"groupId\":\"" + groupId + "\",\"groupUserId\":\"" + groupUser + "\",\"groupUser\":\"" + groupUserId + "\",\"groupName\":\"" + groupName + "\",\"userSize\":\"" + UserSize + "\"}";
    }

}