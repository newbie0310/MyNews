package com.example.myapplication.util;

import com.example.myapplication.R;
import com.example.myapplication.entity.LeftItemMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/11/1.
 */

public class MenuDataUtils {
    public static List<LeftItemMenu> getItemMenus(){
        List<LeftItemMenu> menus = new ArrayList<>();
        menus.add(new LeftItemMenu(R.drawable.icon_zhanghaoxinxi,"账号信息"));
        menus.add(new LeftItemMenu(R.drawable.icon_wodeguanzhu,"我的关注"));
        menus.add(new LeftItemMenu(R.drawable.icon_shoucang,"我的收藏"));
        menus.add(new LeftItemMenu(R.drawable.icon_yijianfankui,"意见反馈"));
        menus.add(new LeftItemMenu(R.drawable.icon_shezhi,"设置"));
        return menus;
    }
}
