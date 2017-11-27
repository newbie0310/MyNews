package com.example.myapplication.manager;

import android.util.Log;

import com.example.myapplication.entity.CategoriesBean;
import com.example.myapplication.entity.HomeNewsBean;
import com.example.myapplication.util.CTextUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/10/27.
 */

public class HomeNewsDataManager {
    public List<HomeNewsBean> getHomeNews(Document document) {
        List<HomeNewsBean> homeNewsBeen = new ArrayList<>();
        try {
            Element headElement = document.select("div.lph-pageList").first();
            Elements elements = headElement.select("div.box");
            for (Element element : elements) {
                String mask = element.select("div.img").first().text();
                String imgurl = element.select("img.lazy").first().attr("data-original");
                String title = element.select("img.lazy").first().attr("title");
                String brief = element.select("div.word").first().select("div.des").text();
                String href = element.select("div.word").first().select("a[href]").attr("href");
                String tid = CTextUtils.getArticleId(href);
                String authorName = element.select("div.word").first().select("div.msg").select("a.aut").text();
                String authoImg = element.select("div.word").first().select("img").attr("src");
                String datetime = element.select("div.word").first().select("div.msg").select("div.time").text();
                homeNewsBeen.add(new HomeNewsBean(tid,mask,imgurl,href,title,brief,authorName,datetime,authoImg));
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return homeNewsBeen;
    }
}
