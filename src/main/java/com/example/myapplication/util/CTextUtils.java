package com.example.myapplication.util;

/**
 * Created by john1 on 2017/10/27.
 */

public class CTextUtils {
    /**
     * 返回网址中的最后一段作为id
     */
    public static String getArticleId(String pStr){
        String tempStr=pStr.substring(pStr.lastIndexOf("/")+1);
        if(tempStr.contains(".")){
            String[] temp=tempStr.split("\\.");  //  5040196
            return temp[0];
        }
        return pStr;
    }
}
