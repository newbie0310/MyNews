package com.example.myapplication.manager;

import com.example.myapplication.entity.ArticleBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/10/31.
 */

public class ArticleDataManager {
    public ArticleBean getArticBean(Document document){
        ArticleBean articleBeans = new ArticleBean();
        Element articleTitle = document.select("div.article-title").first();
        String headTit = articleTitle.select("h1.headTit").first().text();
        String dateTime = articleTitle.select("div.msg").select("td.time").first().text();
        String author = articleTitle.select("div.msg").select("td.aut").first().text();
        String contentt = document.select("div.info").first().select("p").text();
        articleBeans.setTitle(headTit);
        articleBeans.setDatetime(dateTime);
        articleBeans.setAuthor(author);
        articleBeans.setContext(contentt);
        return articleBeans;
    }
}
