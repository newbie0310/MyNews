package com.example.myapplication.manager;

import com.example.myapplication.entity.AdHeadBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/10/26.
 */

public class HeadDataManager {

    public List<AdHeadBean> getHeadBeans(Document document) {
        List<AdHeadBean> adHeadBeans = new ArrayList<>();
        try {
            Element headElement = document.select("div.idx-selRead").first();
            Elements box = headElement.select("div.box");
            for (Element element : box) {
                String mask = headElement.select("div.title").text();
                String title = element.select("div.txt").first().text();
                String href = element.select("div.txt").select("a[href]").attr("href");
                String imgurl = element.select("div.pic").select("img[src]").attr("src");
                adHeadBeans.add(new AdHeadBean(title,imgurl,href,mask));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adHeadBeans;
    }
}
