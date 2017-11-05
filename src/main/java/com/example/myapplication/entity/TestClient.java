package com.example.myapplication.entity;

import android.util.Log;

import com.example.myapplication.manager.ArticleDataManager;
import com.example.myapplication.manager.CategoryDataManager;
import com.example.myapplication.manager.HeadDataManager;
import com.example.myapplication.manager.HomeNewsDataManager;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by john1 on 2017/10/25.
 */

public class TestClient {

    private static String mask;

    public static void main(String[] args){
        String html = "https://www.leiphone.com/";
        try {
            Document doc = Jsoup.connect(html).timeout(10000).get();
            List<AdHeadBean> adHeadBeanList = new HeadDataManager().getHeadBeans(doc);
            Gson gson=new Gson();
            System.out.println(gson.toJson(adHeadBeanList));

            List<CategoriesBean> categoriesBeen = new CategoryDataManager().getCategoryBean(doc);
            System.out.println(gson.toJson(categoriesBeen));

            List<HomeNewsBean> homeNewsBeanList = new HomeNewsDataManager().getHomeNews(doc);
            System.out.println(gson.toJson(homeNewsBeanList));

//            new ArticleDataManager().getArticBean(Jsoup.connect("https://www.leiphone.com/news/201710/IO3uxgYZyHt0AcPI.html").timeout(10000).get());
            System.out.println(gson.toJson(new ArticleDataManager().getArticBean(Jsoup.connect("https://www.leiphone.com/news/201710/IO3uxgYZyHt0AcPI.html").timeout(5000).get())));
//            Element headElement = doc.select("div.idx-selRead").first();
//            Elements box = headElement.select("div.box");
//            for (Element element : box){
//                String mask = headElement.select("div.title").text();
//                String title = element.select("div.txt").first().text();
//                String href = element.select("div.txt").select("a[href]").attr("href");
//                String imgurl = element.select("div.pic").select("img[src]").attr("src");
//                System.out.println(mask);
//                System.out.println(imgurl);
//                System.out.println(href);
//                System.out.println(title);
            } catch (IOException e) {
            e.printStackTrace();
        }
//            Elements elements = headElement.select("a[target]");
//            Elements elements2 = headElement.select("img[src]");
//            mask = headElement.select("div.title").first().text();
//            System.out.println(mask);
//            for (Element element : elements) {
//                href = element.attr("href");
//                String title = element.text();
//                System.out.println(href);
////                System.out.println(title);
//            }
//            for (Element element2 : elements2) {
//                imgurl = element2.attr("src");
//                System.out.println(imgurl);
//            }
//            System.out.println(headElement);
        }
    }
