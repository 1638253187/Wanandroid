package com.example.windqq.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;


//聊天记录数据库
@Entity(nameInDb = "calls")
public class DaoCallBean {

    @Id
    private Long id;

    @NotNull//不能为空
    @Unique
    private String user;
    @Unique
    private String touserid;
    private String fromuserid;
    private String content;
    private int type;
    private long time;
    @Generated(hash = 825474256)
    public DaoCallBean(Long id, @NotNull String user, String touserid,
            String fromuserid, String content, int type, long time) {
        this.id = id;
        this.user = user;
        this.touserid = touserid;
        this.fromuserid = fromuserid;
        this.content = content;
        this.type = type;
        this.time = time;
    }
    @Generated(hash = 311619981)
    public DaoCallBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTouserid() {
        return this.touserid;
    }
    public void setTouserid(String touserid) {
        this.touserid = touserid;
    }
    public String getFromuserid() {
        return this.fromuserid;
    }
    public void setFromuserid(String fromuserid) {
        this.fromuserid = fromuserid;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    
}
