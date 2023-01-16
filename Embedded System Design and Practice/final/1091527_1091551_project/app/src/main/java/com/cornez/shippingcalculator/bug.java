package com.cornez.shippingcalculator;

import android.util.Log;

import java.io.IOException;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.json.JSONObject;

public class bug {
    // 定義要爬取的網頁地址
    private String url;

    // 定義構造函數，用於初始化爬蟲對象
    public bug(String url) {
        this.url = url;
    }

    // 定義一個方法，用於獲取網頁中的文本內容
    public String[] getText() {
        String[] prices = new String[4];
        try {
            // 使用jsoup連接到指定的網頁
            Document doc = Jsoup.connect(this.url).get();
            // 獲取網頁中的文本內容
            String text = doc.body().html();
            JSONObject json = new JSONObject(text);
            prices[0] = json.getString("sPrice1");
            prices[1] = json.getString("sPrice2");
            prices[2] = json.getString("sPrice3");
            prices[3] = json.getString("sPrice5");
            return prices;
        } catch (IOException | JSONException e) {
            System.out.println("Error when crawling " + this.url);
            return null;
        }
    }
}

