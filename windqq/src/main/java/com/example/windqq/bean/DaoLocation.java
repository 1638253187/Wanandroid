package com.example.windqq.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity(nameInDb = "location")
public class DaoLocation {
    @Id(autoincrement = true)
    private Long id;
    private String user;

    private String userId;
    private double lat;
    private double lng;
    @Generated(hash = 473184966)
    public DaoLocation(Long id, String user, String userId, double lat,
            double lng) {
        this.id = id;
        this.user = user;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }
    @Generated(hash = 1820572371)
    public DaoLocation() {
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
    public double getLat() {
        return this.lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLng() {
        return this.lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }

}