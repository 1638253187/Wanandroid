package com.example.windqq.event;

/**
 * Author:maxuesong
 * Created by Administrator on 2018-11-25.
 */
public class MqttToSendEvent {
    private String topic;
    private String msg;
    private String user;
    private String time;

    public MqttToSendEvent(String topic, String msg) {
        this.topic = topic;
        this.msg = msg;
    }

    public String getTopic() {
        return topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
