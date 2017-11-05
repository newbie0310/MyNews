package com.example.myapplication.entity;


/**
 * Created by john1 on 2017/10/31.
 */

public class ArticleBean {
    private String title; // 文章标题
    private String datetime; // 文章发表时间
    private String context; // 文章内容

    private String author; // 文章作者信息

    public ArticleBean() {
       super();
    }

    public ArticleBean(String title, String datetime, String context,  String author) {
        this.title = title;
        this.datetime = datetime;
        this.context = context;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String toString() {
        return "ArticleBean [title=" + title + ", datetime=" + datetime
                + ", context=" + context +", author=" + author + "]";
    }
}
