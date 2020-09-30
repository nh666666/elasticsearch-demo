package com.cloud.example.clouddemo.util;

import com.alibaba.fastjson.JSON;
import com.cloud.example.clouddemo.elasticsearchtest.model.Good;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: niehan
 * @Description:
 * @Date:Create：in 2020/9/29 11:11
 */
public class JsoupUtils {
    public List<Good> getTargetGoods(String keywords) throws IOException{
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        Document document = Jsoup.parse(new URL(url), 5000);
        Element list = document.getElementById("J_goodsList");
        Elements li = list.getElementsByTag("li");
        System.out.println(li);
        List<Good> goods = new ArrayList<>();
        for(Element element:li) {
            String img = element.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String name = element.getElementsByClass("p-name").eq(0).text();
            String price = element.getElementsByClass("p-price").eq(0).text();
            Good good = new Good();
            good.setImg(img);
            good.setName(name);
            good.setPrice(price);
            goods.add(good);
        }
        return goods;
//        System.out.println(list.html());
    }

    public static void main(String[] args) {
        try {
            JsoupUtils jsoupUtils = new JsoupUtils();
            System.out.println(JSON.toJSONString(jsoupUtils.getTargetGoods("java")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
