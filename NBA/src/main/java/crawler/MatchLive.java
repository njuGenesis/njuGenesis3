package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MatchLive {
	public static void main(String[] args){
		MatchLive m = new MatchLive();
		m.liveMatch2();
	}
	public void liveMatch(){
		
		Document doc = null;
		int size = 0;
		int i = 0;
		   
		    String basicinfoUrl = "http://g.hupu.com/nba/playbyplay_150122.html";
			//url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
		    while(true){
			try{
				
				//-------------------------初始化球员基本信息
				doc = Jsoup.connect(basicinfoUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
				Elements BasicInfo = doc.select("table[matchstatus]").select("tbody").select("tr");
				if(BasicInfo.size()==size){
					continue;
				}
				else{
					i = BasicInfo.size()-size;
					System.out.println("res:"+size);
					System.out.println(i);
				for(Element el:BasicInfo){
					
					if(i<=0){
						break;
					}
					size++;
					i--;
					System.out.println(el.text());
					//res.add(el.text());
				}
				}
				Thread.sleep(2000);

			}
			catch(Exception e){
				e.printStackTrace();
			
			}
		    }
		
	}
	public void liveMatch2(){
		
		URL url;  
        StringBuffer sb = new StringBuffer();  
        String line = null; 
        String path = "http://api.sports.sina.com.cn/pbp/?format=json&source=show&callback=mGetterGetReSeventShoot2015061105__1&mid=2015061105&pid=&eid=1&dpc=1";
//        switch(key){
//        case "points":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_points.json";break;
//        case "rebounds":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_rebounds.json";break;
//        case "assists":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_assists.json";break;
//        
//        	default:
//        }
       // while(true){
        
        try {  
            url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            BufferedReader buffer = new BufferedReader(  
                    new InputStreamReader(is));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
            }  
            String js = sb.toString();
            js = js.substring(js.indexOf("2015061105__1(")+14,js.length()-2);
            System.out.println(js);
            
            JSONObject og = new JSONObject(js);
            JSONObject res = og.getJSONObject("result");
            JSONObject data = res.getJSONObject("data");
            int last = data.getInt("last_eid");
            
            JSONObject msg = data.getJSONObject("pbp_msgs");
            JSONObject pid = msg.getJSONObject("2");
            System.out.println(pid.getString("description"));
           // System.out.println(payload.toString());

            //JSONObject payload = temp.getJSONObject("");
            
          //  String pointHotPlayers = payload.getString("pointHotPlayers");
            
           // String rebHotPlayers = payload.getString("reboundHotPlayers");
           // String assistHotPlayers = payload.getString("assistHotPlayers");
        
            
            
        } catch(Exception e){
        	e.printStackTrace();
        }
	//}
        
	}
    }
        
       
	


