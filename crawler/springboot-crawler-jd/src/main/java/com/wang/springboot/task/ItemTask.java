package com.wang.springboot.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.springboot.entities.Item;
import com.wang.springboot.service.ItemService;
import com.wang.springboot.utils.HttpUtils;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author 王一宁
 * @date 2020/2/8 13:06
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;
    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //间隔100s执行一次任务
    @Scheduled(fixedDelay = 100*1000)
    public void  itemTask() throws Exception{
        //1.声明地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&suggest=1.def.0.V18--12s0%2C20s0%2C38s0%2C97s0&wq=shouji&s=122&click=0&page=1";

        //2.遍历页面
        for (int i = 1;i < 10;i = i+2){
            String html = httpUtils.doGetHtml(url + i);
            //解析页面，获取商品数据并存储
            parse(html);
        }

        System.out.println("手机数据抓取完成！");

    }

    /**
     * //解析页面，获取商品数据并存储
     * @param html
     */
    private void parse(String html) throws Exception{
        //1.解析html
        Document doc = Jsoup.parse(html);
        //2.获取spu
        Elements spuEles = doc.select("div#J_goodsList > ul > li");
        for (Element spuEle : spuEles){
            //获取spu
            long spu = Long.parseLong(spuEle.attr("data-apu"));
            //获取sku
            Elements skuEles = spuEle.select("li.ps-item");
            for (Element skuEle : skuEles){
                long sku = Long.parseLong(skuEle.select("[data-sku]").attr("data-sku"));

                //根据sku查询商品数据 存在的话不保存
                Item item = new Item();
                //1.设置商品的sku
                item.setSku(sku);
                List<Item> list = itemService.findAll(item);
                if (list.size()>0){
                    continue;
                }
                //2.设置商品的spu
                item.setSpu(spu);

                //3.设置商品的详情url
                String itemUrl = "https://item.jd.com/"+ sku +".html";
                item.setUrl(itemUrl);

                //4.设置商品的图片
                String picUrl = "https:"+skuEle.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/","/n1/");
                String picName = httpUtils.doGetImage(picUrl);
                item.setPic(picName);
                //5.设置商品的价格
                String priceJson = httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                item.setPrice(price);
                //6.设置商品的标题
                String itemInfo = httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).select("div.sku-name").text();
                item.setTitle(title);
                //7.设置商品的时间
                item.setCreated(new Date());
                item.setUpdated(item.getCreated());

                //8.保存数据库
                itemService.save(item);
            }
        }
    }
}
