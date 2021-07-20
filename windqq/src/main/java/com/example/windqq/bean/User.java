package com.example.windqq.bean;

public class User {
    private int touxiang;
    private String name;
    private String qianming;
    private Long id;
    public User() {
    }

    public User(int touxiang, String name, String qianming, Long id) {
        this.touxiang = touxiang;
        this.name = name;
        this.qianming = qianming;
        this.id = id;
    }

    public int getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(int touxiang) {
        this.touxiang = touxiang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQianming() {
        return qianming;
    }

    public void setQianming(String qianming) {
        this.qianming = qianming;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "touxiang=" + touxiang +
                ", name='" + name + '\'' +
                ", qianming='" + qianming + '\'' +
                ", id=" + id +
                '}';
    }
}
