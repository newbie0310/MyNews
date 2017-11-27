package com.example.myapplication.util;

import com.example.myapplication.entity.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaXie on 2017/11/16.
 */

public class CatagoryDataUtils {
    public static List<CategoriesBean> getCategoryBeans() {
        List<CategoriesBean> beans = new ArrayList<>();
        beans.add(new CategoriesBean("全部","https://www.leiphone.com/"));
        beans.add(new CategoriesBean("业界","https://www.leiphone.com/category/sponsor"));
        beans.add(new CategoriesBean("人工智能","https://www.leiphone.com/category/ai"));
        beans.add(new CategoriesBean("智能驾驶","https://www.leiphone.com/category/transportation"));
        beans.add(new CategoriesBean("AI+","https://www.leiphone.com/category/aijuejinzhi"));
        beans.add(new CategoriesBean("Fintech","https://www.leiphone.com/category/fintech"));
        beans.add(new CategoriesBean("未来医疗","https://www.leiphone.com/category/aihealth"));
        beans.add(new CategoriesBean("网络安全","https://www.leiphone.com/category/letshome"));
        beans.add(new CategoriesBean("AR/VR","https://www.leiphone.com/category/arvr"));
        beans.add(new CategoriesBean("机器人","https://www.leiphone.com/category/robot"));
        beans.add(new CategoriesBean("开发者","https://www.leiphone.com/category/yanxishe"));
        beans.add(new CategoriesBean("智能硬件","https://www.leiphone.com/category/weiwu"));
        beans.add(new CategoriesBean("物联网","https://www.leiphone.com/category/iot"));
        return beans;

    }
}