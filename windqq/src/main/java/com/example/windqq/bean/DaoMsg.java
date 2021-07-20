package com.example.windqq.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "messages")
public class DaoMsg {

    @Id
    private Long id;
    private long time;
    private String fromuser;
    private String touser;
    private String name;
    private int deriction;
    private String content;
    @Generated(hash = 824720726)
    public DaoMsg(Long id, long time, String fromuser, String touser, String name,
            int deriction, String content) {
        this.id = id;
        this.time = time;
        this.fromuser = fromuser;
        this.touser = touser;
        this.name = name;
        this.deriction = deriction;
        this.content = content;
    }
    @Generated(hash = 424462115)
    public DaoMsg() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getFromuser() {
        return this.fromuser;
    }
    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }
    public String getTouser() {
        return this.touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDeriction() {
        return this.deriction;
    }
    public void setDeriction(int deriction) {
        this.deriction = deriction;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
