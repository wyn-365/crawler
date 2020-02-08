package com.wang.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author 王一宁
 * @date 2020/2/8 9:10
 */
public class Crawler01 {
    public static void main(String[] args) throws Exception{
        //1.打开浏览器，创建对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.网址
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        //3.发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4.解析响应，获取数据
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity, "utf8");
            System.out.println(content);
        }
    }
}
