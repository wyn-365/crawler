package com.wang.crawler;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * @author 王一宁
 * @date 2020/2/8 9:48
 */
public class HttpClientPoolTest {
    public static void main(String[] args) throws Exception {
        //1.创建连接池管理器
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //设置连接数
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        //2.管理器发起请求
        doGet(cm);
    }

    private static void doGet(PoolingHttpClientConnectionManager cm) throws Exception{
        //1.从连接池中获取HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //2.发请求
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode()==200){
            String content = EntityUtils.toString(response.getEntity(), "utf8");
            System.out.println(content.length());
        }

        //3.关闭
        response.close();
        //httpClient.close();  连接池管理 不必关闭
    }
}
