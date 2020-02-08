package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;

/**
 * @author 王一宁
 * @date 2020/2/8 10:10
 */
public class JsoupFirstTest {

    @Test
    public void testUrl() throws Exception{
        //1.解析Url地址
        Document doc = Jsoup.parse(new URL("http://www.itcast.cn"), 1000);
        //2.使用标签选择器，获取title
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testString() throws Exception{
        //1.实用工具类获取文件的字符串
        String content = FileUtils.readFileToString(new File("f:\\......"), "utf8");
        //2.解析
        Document doc = Jsoup.parse(content);
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void testFile() throws Exception{
        Document doc = Jsoup.parse(new File(""), "utf8");
        String title = doc.getElementsByTag("title").first().text();
        System.out.println(title);
    }

    @Test
    public void  testDOM() throws Exception{
        //1.解析文件，获取Document对象
        Document doc = Jsoup.parse(new File(""), "utf8");
        Element element = doc.getElementById("id1");
        Element element1 = doc.getElementsByTag("span").first();
        Element element2 = doc.getElementsByClass("class_a").first();
        System.out.println("根据ID获取元素内容是："+element.text());
        System.out.println("根据span获取元素内容是："+element1.text());
        System.out.println("根据class获取元素内容是："+element2.text());
    }

    @Test
    public void testData() throws Exception{
        //1.解析文件，获取Document对象
        Document doc = Jsoup.parse(new File(""), "utf8");
        Element element = doc.getElementById("id1");
        String str = "";
        str = element.id();
        str = element.className();
        str = element.data();
        str = element.attr("id");
        str = element.attr("class");
        str = element.text();
        System.out.println();
    }

    @Test
    public void testSelector() throws Exception{
        //1.解析文件，获取Document对象
        Document doc = Jsoup.parse(new File(""), "utf8");
        Elements elements = doc.select("span");
        for (Element element : elements){
            System.out.println(element.text());
        }

        Element element1 = doc.select("#city_id").first();
        System.out.println(element1.text());

        Element el = doc.select(".class_a").first();

    }
}
