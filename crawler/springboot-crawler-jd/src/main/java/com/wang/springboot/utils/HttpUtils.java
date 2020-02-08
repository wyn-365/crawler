package com.wang.springboot.utils;

import com.sun.istack.Pool;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

/**
 * @author 王一宁
 * @date 2020/2/8 12:43
 */
@Component //实例才能使用工具类
public class HttpUtils {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载数据
     * @param url
     * @return
     * @throws Exception
     */
    public String doGetHtml(String url) throws Exception {
        //1.获取对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //2.地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(getConfig());
        //3.请求数据
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4.解析
        if (response.getStatusLine().getStatusCode()==200){
            if (response.getEntity() != null){
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                return content;
            }
        }
        if (response != null){
            response.close();
        }
        return ""; //没有数据时候返回空
    }

    /**
     * 下载图片
     * @param url
     * @return
     */
    public String doGetImage(String url) throws Exception {
        //1.获取对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //2.地址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(getConfig());
        //3.请求数据
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4.解析
        if (response.getStatusLine().getStatusCode()==200){
            if (response.getEntity() != null){
                //获取图片的后缀
                String extName = url.substring(url.lastIndexOf("."));
                //重命名图片
                String picName = UUID.randomUUID().toString()+extName;
                //下载图片
                OutputStream outputStream = new FileOutputStream(new File("D:\\APP\\IDEA\\workplace\\crawler\\images\\"+picName));
                response.getEntity().writeTo(outputStream);
                //返回图片名称
                return picName;
            }
        }
        if (response != null){
            response.close();
        }
        return ""; //没有数据时候返回空
    }




    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000) //创建连接的最长时间
                .setConnectionRequestTimeout(500) //获取连接的最长时间
                .setSocketTimeout(10000)  //数据传输的最长时间
                .build();
        return config;
    }

}
