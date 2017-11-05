package com.example.myapplication.entity;

/**
 * Created by john1 on 2017/11/1.
 */

public class LeftItemMenu {
    private int leftIcon;
    private String title;

    private LeftItemMenu(){

    }

    public LeftItemMenu(int leftIcon, String title) {
        this.leftIcon = leftIcon;
        this.title = title;
    }

    public int getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        this.leftIcon = leftIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
