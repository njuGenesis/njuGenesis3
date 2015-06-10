package crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MatchLive {
	public static void main(String[] args){
		MatchLive m = new MatchLive();
		m.liveMatch();
	}
	public void liveMatch(){
		
		Document doc = null;
		ArrayList<String> res = new ArrayList<String>();
		
		   
		    String basicinfoUrl = "http://g.hupu.com/nba/daily/playbyplay_150119.html";
			//url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
		    while(true){
			try{
				//-------------------------初始化球员基本信息
				doc = Jsoup.connect(basicinfoUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
				Elements BasicInfo = doc.select("table[matchstatus]").select("tbody").select("tr");
				if(BasicInfo.size()==res.size()){
					continue;
				}
				else{
				for(Element el:BasicInfo){
					System.out.println(el.text());
					res.add(el.text());
				}
				}
				Thread.sleep(2000);

			}
			catch(Exception e){
				e.printStackTrace();
			
			}
		    }
		
	}
   
    }
        
       
	


