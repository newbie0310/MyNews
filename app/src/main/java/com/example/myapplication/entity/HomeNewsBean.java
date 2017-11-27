package com.example.myapplication.entity;

/**
 * Created by john1 on 2017/10/27.
 */

public class HomeNewsBean {
    private String tid; //文章id
    private String mask; // 文章类型
    private String imgurl; // 文章列表缩略图片
    private String href; // 文章地址
    private String title; // 文章标题
    private String brief; // 文章简述
    private String authorName; // 作者名字
    private String datetime; // 文章发表时间
    private String authoImg; //文章作者头像

    public HomeNewsBean(String tid,String mask, String imgurl, String href,
                        String title, String brief, String authorName,
                        String datetime,String authoImg) {
        this.tid = tid;
        this.mask = mask;
        this.imgurl = imgurl;
        this.href = href;
        this.title = title;
        this.brief = brief;
        this.authorName = authorName;
        this.datetime = datetime;
        this.authoImg = authoImg;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }



    public String getAuthoImg() {
        return authoImg;
    }

    public void setAuthoImg(String authoImg) {
        this.authoImg = authoImg;
    }

    @Override
    public String toString() {
        return "HomeNewsBean" +
                "[tid=" + tid +
                ",mask='" + mask +
                ", imgurl='" + imgurl +
                ", href='" + href +
                ", title='" + title +
                ", brief='" + brief +
                ", authorName='" + authorName  +
                ", datetime='" + datetime  +
                ", authoImg" +
                ']';
    }
}


