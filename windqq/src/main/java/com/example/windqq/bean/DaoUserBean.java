package com.example.windqq.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DaoUserBean {
    @Id
    private Long id;

    private String user;
    private String userId;
    @Generated(hash = 1861181785)
    public DaoUserBean(Long id, String user, String userId) {
        this.id = id;
        this.user = user;
        this.userId = userId;
    }
    @Generated(hash = 884277471)
    public DaoUserBean() {
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
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
