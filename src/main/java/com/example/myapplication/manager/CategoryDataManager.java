package com.example.myapplication.manager;

import com.example.myapplication.entity.AdHeadBean;
import com.example.myapplication.entity.CategoriesBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/10/26.
 */

public class CategoryDataManager {
    public List<CategoriesBean> getCategoryBean(Document document){
        List<CategoriesBean> categoriesBeen = new ArrayList<>();
        try {
//            doc = Jsoup.connect(html).timeout(5000).get();
            Element headElement = document.select("div.wrapper").first();
            Elements elements = headElement.select("a[href]");
            for (Element element : elements) {
                String href = element.attr("href");
                String title = element.text();
                categoriesBeen.add(new CategoriesBean(title,href));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoriesBeen;
    }

}
