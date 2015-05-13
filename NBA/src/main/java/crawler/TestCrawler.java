package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCrawler {
	public static void main(String[] args){
		Document doc = null;
		try{
			doc = Jsoup.connect("http://news.baidu.com/ns?word=jsoup&tn=news&from=news&cl=2&rn=20&ct=1").get();
			Elements e = doc.select("h3.c-title a");
			for(Element el:e){
				System.out.println(el.text());
				System.out.println(el.attr("href").toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
