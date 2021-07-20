package com.example.wanandroid.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LikeDaoBean {
    @Id(autoincrement = true)
    private Long id;
    private String author;
    private String superChapterName;
    private String chapterName;
    private String title;
    private String niceDate;
    @Generated(hash = 641507040)
    public LikeDaoBean(Long id, String author, String superChapterName,
            String chapterName, String title, String niceDate) {
        this.id = id;
        this.author = author;
        this.superChapterName = superChapterName;
        this.chapterName = chapterName;
        this.title = title;
        this.niceDate = niceDate;
    }
    @Generated(hash = 1580867527)
    public LikeDaoBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getSuperChapterName() {
        return this.superChapterName;
    }
    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getNiceDate() {
        return this.niceDate;
    }
    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

     
}
