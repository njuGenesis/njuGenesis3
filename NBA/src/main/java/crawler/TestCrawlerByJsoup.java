package crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCrawlerByJsoup {
	public static void main(String[] args){
//		Document doc = null;
//		try{
//			//所有数据都在stat_box里面，例如379_allstar/379_playoff/379_normal这种
//			doc = Jsoup.connect("http://www.stat-nba.com/player/stat_box/379_allstar.html").header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
//			Elements e = doc.select("table#stat_box_avg").select("tbody").select("tr").select("td");
//			//System.out.println(e.size());
//			for(Element el:e){
//				System.out.println(el.text());
//				//System.out.println("g");
//				//System.out.println(el.attr("href").toString());
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
	    TestCrawlerByJsoup  t = new TestCrawlerByJsoup();
		t.initializePlayerPlayOff();
	}
	public void initializePlayerSeason(){
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		for(int i = 1;i<10;i++){
		    //int i = 379;
			System.out.println();
			System.out.println(i);
		    String basicinfoUrl = "http://www.stat-nba.com/player/"+i+".html";
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
			try{
				//-------------------------初始化球员基本信息
				doc = Jsoup.connect(basicinfoUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
				Elements BasicInfo = doc.select("div.playerinfo").select("div.detail").select("div.row");
				for(Element el:BasicInfo){
					//System.out.print(el.tagName());
//					if((el.tagName().equals("a")||(el.tagName().equals("div.column")))){
//						continue;
//					}
					if(temp<6){
					System.out.println(el.text()+";");
					temp++;
					}
					if(el.text().substring(0,5).equals("球衣号码:")){
						System.out.println(el.text()+";");
						temp = 0;
						break;
					}
					
					
				}
				//-------------------------初始化平均基础数据
				doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
			    Elements Avgbasic = doc.select("table#stat_box_avg").select("tbody").select("tr").select("td");
			    tablesize = doc.select("table#stat_box_avg").select("thead").select("tr").select("th").size()-1;
			    System.out.println("size:"+tablesize);
			    temp = 0;
			    for(Element el:Avgbasic){
			    	if(!isFirst){
			    	isFirst = true;
			    	}
			    	else{
			    	temp++;
			
			    	System.out.print(el.text()+";");
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	//忽略胜负场
			    	
			    	}
			    }
			    //------------------------初始化总计基础数据
			    Elements Totbasic = doc.select("table#stat_box_tot").select("tbody").select("tr").select("td");
			    
			    temp =0;
			    for(Element el:Totbasic){
			    	if(isFirst){
			    		isFirst = false;
			    	}
			    	else {
			    	temp++;
			    	System.out.print(el.text()+";");
			    	//temp++;
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	//忽略胜负场
			    	
			    	}
			    }
			    //------------------------初始化基础的进阶数据
			    Elements Advancedbasic = doc.select("table#stat_box_advanced_basic").select("tbody").select("tr").select("td");
			   temp = 0;
			    for(Element el:Advancedbasic){
			    	if(!isFirst){
				    	isFirst = true;
				    	}
			    	else{
			    	System.out.print(el.text()+";");
			    	temp++;
			    	if(temp==19){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	}
			    }
			    //------------------------初始化shoot的进阶数据
			    Elements Advancedshoot = doc.select("table#stat_box_advanced_shooting").select("tbody").select("tr").select("td");
			   temp = 0;
			    for(Element el:Advancedshoot){
			    	if(isFirst){
				    	isFirst = false;
				    	}
			    	else{
			    	System.out.print(el.text()+";");
			    	temp++;
			    	if(temp==21){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	}
			    }
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void initializePlayerPlayOff(){
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		for(int i = 1;i<10;i++){
		    //int i = 379;
			System.out.println();
			System.out.println(i);
		    String basicinfoUrl = "http://www.stat-nba.com/player/"+i+".html";
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_playoff.html";
			try{
				//-------------------------初始化球员基本信息
				doc = Jsoup.connect(basicinfoUrl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
				Elements BasicInfo = doc.select("div.playerinfo").select("div.detail").select("div.row");
				for(Element el:BasicInfo){
					//System.out.print(el.tagName());
//					if((el.tagName().equals("a")||(el.tagName().equals("div.column")))){
//						continue;
//					}
					if(temp<6){
					System.out.println(el.text()+";");
					temp++;
					}
					if(el.text().substring(0,5).equals("球衣号码:")){
						System.out.println(el.text()+";");
						temp = 0;
						break;
					}
					
					
				}
				//-------------------------初始化平均基础数据
				doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
				tablesize = doc.select("table#stat_box_avg").select("thead").select("tr").select("th").size()-1;
				if(tablesize<0){
					//--没有参加过季后赛
				}
				else{
					//--参加过季后赛
				Elements Avgbasic = doc.select("table#stat_box_avg").select("tbody").select("tr").select("td");
			    //tablesize = doc.select("table#stat_box_avg").select("thead").select("tr").select("th").size()-1;
			    System.out.println("size:"+tablesize);
			    temp = 0;
			    for(Element el:Avgbasic){
			    	if(!isFirst){
			    	isFirst = true;
			    	}
			    	else{
			    	temp++;
			
			    	System.out.print(el.text()+";");//之后改成设置属性
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	//忽略胜负场
			    	
			    	}
			    }
			    //------------------------初始化总计基础数据
			    Elements Totbasic = doc.select("table#stat_box_tot").select("tbody").select("tr").select("td");
			    
			    temp =0;
			    for(Element el:Totbasic){
			    	if(isFirst){
			    		isFirst = false;
			    	}
			    	else {
			    	temp++;
			    	System.out.print(el.text()+";");
			    	//temp++;
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	//忽略胜负场
			    	
			    	}
			    }
			    //------------------------初始化基础的进阶数据
			    Elements Advancedbasic = doc.select("table#stat_box_advanced_basic").select("tbody").select("tr").select("td");
			   temp = 0;
			    for(Element el:Advancedbasic){
			    	if(!isFirst){
				    	isFirst = true;
				    	}
			    	else{
			    	System.out.print(el.text()+";");
			    	temp++;
			    	if(temp==19){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	}
			    }
			    //------------------------初始化shoot的进阶数据
			    Elements Advancedshoot = doc.select("table#stat_box_advanced_shooting").select("tbody").select("tr").select("td");
			   temp = 0;
			    for(Element el:Advancedshoot){
			    	if(isFirst){
				    	isFirst = false;
				    	}
			    	else{
			    	System.out.print(el.text()+";");
			    	temp++;
			    	if(temp==21){
			    		System.out.println();
			    		temp = 0;
			    	}
			    	}
			    }
			    //参加过季后赛
			   }
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
