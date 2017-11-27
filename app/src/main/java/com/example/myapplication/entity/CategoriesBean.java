package com.example.myapplication.entity;

import java.io.Serializable;

/**
 * Created by john1 on 2017/10/26.
 */

public class CategoriesBean implements Serializable {
    private String title; // 分类Tab名称
    private String href; // 分类点击地址
    public CategoriesBean() {
        super();
    }
    public CategoriesBean(String title, String href) {
        super();
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "CategoriesBean [title=" + title + ", href=" + href
                 + "]";
    }

}


