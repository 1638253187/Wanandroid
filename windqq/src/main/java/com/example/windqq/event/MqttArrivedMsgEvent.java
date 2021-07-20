package com.example.windqq.event;

/**
 * Author:maxuesong
 * Created by Administrator on 2018-11-25.
 */
public class MqttArrivedMsgEvent {

    private String topic;
    private String msg;
    private String user;
    private String time;

    public MqttArrivedMsgEvent(String topic, String msg) {
        this.topic = topic;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getTopic() {
        return topic;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
