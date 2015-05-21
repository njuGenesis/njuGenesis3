package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCrawlerByJsoup {
	public static void main(String[] args){
		Document doc = null;
		try{
			//所有数据都在stat_box里面，例如379_allstar/379_playoff/379_normal这种
			doc = Jsoup.connect("http://www.stat-nba.com/player/stat_box/379_allstar.html").header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
			Elements e = doc.select("table#stat_box_avg").select("tbody").select("tr").select("td");
			System.out.println(e.size());
			for(Element el:e){
				System.out.println(el.text());
				//System.out.println("g");
				//System.out.println(el.attr("href").toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
