package crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.po.playerData.*;

public class TestCrawlerByJsoup {
	ArrayList<String> tempInfo = new ArrayList<String>();
	
	ArrayList<PlayerDetailInfo> playerdetail = new ArrayList<PlayerDetailInfo>();
	ArrayList<PlayerDataSeason_Avg_Basic> p_s_a_b = new ArrayList<PlayerDataSeason_Avg_Basic>();
	ArrayList<PlayerDataSeason_Tot_Basic> p_s_t_b = new ArrayList<PlayerDataSeason_Tot_Basic>();
	ArrayList<PlayerDataSeason_Ad_Basic> p_s_ad_b = new ArrayList<PlayerDataSeason_Ad_Basic>();
	ArrayList<PlayerDataSeason_Ad_Shoot> p_s_ad_s = new ArrayList<PlayerDataSeason_Ad_Shoot>();
	
	ArrayList<PlayerDataPlayOff_Avg_Basic> p_p_a_b = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
	ArrayList<PlayerDataPlayOff_Tot_Basic> p_p_t_b = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
	ArrayList<PlayerDataPlayOff_Ad_Basic> p_p_ad_b = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
	ArrayList<PlayerDataPlayOff_Ad_Shoot> p_p_ad_s = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
	
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
		t.initializePlayerDetail();
	}
	//-------------装入detail对象
	public void initializeDetailObject(int i){
		//--------每一个球员的基本信息
		PlayerDetailInfo p = new PlayerDetailInfo();
		p.setId(i);
		p.setName(tempInfo.get(0));
		p.setPosition(tempInfo.get(1));
		p.setHeight(tempInfo.get(2));
		p.setWeight(tempInfo.get(3));
		p.setBirth(tempInfo.get(4));
		p.setBorncity(tempInfo.get(5));
		p.setNumber(tempInfo.get(6));
		
		playerdetail.add(p);
		
	}
	public void writeDB_Detail(){
		//数据库操作
	}
	//------------初始化最基本信息
	public void initializePlayerDetail(){
		Document doc = null;
		int temp = 0;		
		for(int i = 1;i<10;i++){
		   
		    String basicinfoUrl = "http://www.stat-nba.com/player/"+i+".html";
			//url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
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
						
					System.out.println(el.text().substring(5)+";");
					
					tempInfo.add(el.text().substring(5));
					temp++;
					}
					if(el.text().substring(0,5).equals("球衣号码:")){
						System.out.println(el.text()+";");
						//----每一个球员的信息暂存
						tempInfo.add(el.text().substring(5));
						initializeDetailObject(i);
						tempInfo.clear();//装入对象之后情况该数组
						temp = 0;
						
						break;
					}
					
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		writeDB_Detail();
		
	}
	
	//====================================================================================
	//-------------转入season_avg_basic对象
	public void initializeS_a_b(String name,int i){
		PlayerDataSeason_Avg_Basic p = new PlayerDataSeason_Avg_Basic();
		p.setId(i);
		p.setName(name);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setGp(tempInfo.get(2));
		p.setGs(tempInfo.get(3));
		p.setTime(tempInfo.get(4));
		p.setShootper(tempInfo.get(5));
		p.setShoot_in(tempInfo.get(6));
		p.setShoot_all(tempInfo.get(7));
		p.setThper(tempInfo.get(8));
		p.setTh_in(tempInfo.get(9));
		p.setTh_all(tempInfo.get(10));
		p.setFtper(tempInfo.get(11));
		p.setFt_in(tempInfo.get(12));
		p.setFt_all(tempInfo.get(13));
		p.setBackbound(tempInfo.get(14));
		p.setOffb(tempInfo.get(15));
		p.setDefb(tempInfo.get(16));
		p.setAssist(tempInfo.get(17));
		p.setSteal(tempInfo.get(18));
		p.setRejection(tempInfo.get(19));
		p.setMiss(tempInfo.get(20));
		p.setFoul(tempInfo.get(21));
		p.setPts(tempInfo.get(22));
		p.setWin(tempInfo.get(23));
		p.setLose(tempInfo.get(24));
		
		p_s_a_b.add(p);
	}
	//-------------装入season-tot-basic对象
	public void initializeS_t_b(String name,int i){
		PlayerDataSeason_Tot_Basic p = new PlayerDataSeason_Tot_Basic();
		p.setId(i);
		p.setName(name);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setGp(tempInfo.get(2));
		p.setGs(tempInfo.get(3));
		p.setTime(tempInfo.get(4));
		p.setShootper(tempInfo.get(5));
		p.setShoot_in(tempInfo.get(6));
		p.setShoot_all(tempInfo.get(7));
		p.setThper(tempInfo.get(8));
		p.setTh_in(tempInfo.get(9));
		p.setTh_all(tempInfo.get(10));
		p.setFtper(tempInfo.get(11));
		p.setFt_in(tempInfo.get(12));
		p.setFt_all(tempInfo.get(13));
		p.setBackbound(tempInfo.get(14));
		p.setOffb(tempInfo.get(15));
		p.setDefb(tempInfo.get(16));
		p.setAssist(tempInfo.get(17));
		p.setSteal(tempInfo.get(18));
		p.setRejection(tempInfo.get(19));
		p.setMiss(tempInfo.get(20));
		p.setFoul(tempInfo.get(21));
		p.setPts(tempInfo.get(22));
		p.setWin(tempInfo.get(23));
		p.setLose(tempInfo.get(24));
		
		p_s_t_b.add(p);
	}
	//-------------装入season-ad-basic对象
	public void initializeS_ad_b(String name,int i){
		PlayerDataSeason_Ad_Basic p = new PlayerDataSeason_Ad_Basic();
		
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setBackeff(tempInfo.get(2));
		p.setOffbeff(tempInfo.get(3));
		p.setDefbeff(tempInfo.get(4));
		p.setAssisteff(tempInfo.get(5));
		p.setStealeff(tempInfo.get(6));
		p.setRejeff(tempInfo.get(7));
		p.setMisseff(tempInfo.get(8));
		p.setUseeff(tempInfo.get(9));
		p.setOffeff(tempInfo.get(10));
		p.setDefeff(tempInfo.get(11));
		p.setWs(tempInfo.get(12));
		p.setOffws(tempInfo.get(13));
		p.setDefws(tempInfo.get(14));
		p.setPer(tempInfo.get(15));
		p.setStrshoot(tempInfo.get(16));
		p.setKda(tempInfo.get(17));
		p.setBerej(tempInfo.get(18));
		
		p_s_ad_b.add(p);
	}
	//-------------装入season-ad-shoot对象
	public void initializeS_ad_s(String name,int i){
		PlayerDataSeason_Ad_Shoot p = new PlayerDataSeason_Ad_Shoot();
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setShootdis(tempInfo.get(2));
		p.setBshootper(tempInfo.get(3));
		p.setBshoot_in(tempInfo.get(4));
		p.setBshoot_all(tempInfo.get(5));
		p.setB_per(tempInfo.get(6));
		p.setCloseshootper(tempInfo.get(7));
		p.setCloseshoot_in(tempInfo.get(8));
		p.setCloseshoot_all(tempInfo.get(9));
		p.setClose_per(tempInfo.get(10));
		p.setMidshootper(tempInfo.get(11));
		p.setMidshoot_in(tempInfo.get(12));
		p.setMidshoot_all(tempInfo.get(13));
		p.setMid_per(tempInfo.get(14));
		p.setFarshootper(tempInfo.get(15));
		p.setFarshoot_in(tempInfo.get(16));
		p.setFarshoot_all(tempInfo.get(17));
		p.setFar_per(tempInfo.get(18));
		p.setTrueshootper(tempInfo.get(19));
		p.setShooteff(tempInfo.get(20));
		
		p_s_ad_s.add(p);
	}
	//-------------数据库操作部分
	public void writeDB_S_a_b(){
		
	}
	public void writeDB_S_t_b(){
		
	}
	public void writeDB_S_ad_b(){
		
	}
	public void writeDB_S_ad_s(){
		
	}
	
	//-------------初始化常规赛信息
	public void initializePlayerSeason(){
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		String name = "";
		for(int i = 1;i<10;i++){
		    //int i = 379;
			
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
			try{
				//-------------------------初始化球员基本信息
				name = playerdetail.get(i-1).getName();//获取姓名
				//-------------------------初始化平均基础数据
				doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
			    Elements Avgbasic = doc.select("table#stat_box_avg").select("tbody").select("tr").select("td");
			    tablesize = doc.select("table#stat_box_avg").select("thead").select("tr").select("th").size()-1;
			    //System.out.println("size:"+tablesize);
			    temp = 0;
			    for(Element el:Avgbasic){
			    	if(!isFirst){
			    	isFirst = true;
			    	}
			    	else{
			    	temp++;
			
			    	System.out.print(el.text()+";");
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		if(tablesize==23){
			    			tempInfo.add("unknown");
			    			tempInfo.add("unknown");
			    		}
			    		initializeS_a_b(name,i);
			    		tempInfo.clear();
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
			        tempInfo.add(el.text());
			    	//temp++;
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		
			    		
			    		if(tablesize==23){
			    			tempInfo.add("unknown");
			    			tempInfo.add("unknown");
			    		}
			    		initializeS_t_b(name,i);
			    		tempInfo.clear();
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
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==19){
			    		System.out.println();
			    		
			    		initializeS_ad_b(name,i);
			    		tempInfo.clear();
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
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==21){
			    		System.out.println();
			    		
			    		initializeS_ad_s(name,i);
			    		tempInfo.clear();
			    		temp = 0;
			    	}
			    	}
			    }
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		writeDB_S_a_b();
		writeDB_S_t_b();
		writeDB_S_ad_b();
		writeDB_S_ad_s();
	}
	//=====================================================================================
	
	//-------------转入p_avg_basic对象
	public void initializeP_a_b(String name,int i){
		PlayerDataPlayOff_Avg_Basic p = new PlayerDataPlayOff_Avg_Basic();
		p.setId(i);
		p.setName(name);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setGp(tempInfo.get(2));
		//p.setGs(tempInfo.get(3));
		p.setTime(tempInfo.get(3));
		p.setShootper(tempInfo.get(4));
		p.setShoot_in(tempInfo.get(5));
		p.setShoot_all(tempInfo.get(6));
		p.setThper(tempInfo.get(7));
		p.setTh_in(tempInfo.get(8));
		p.setTh_all(tempInfo.get(9));
		p.setFtper(tempInfo.get(10));
		p.setFt_in(tempInfo.get(11));
		p.setFt_all(tempInfo.get(12));
		p.setBackbound(tempInfo.get(13));
		p.setOffb(tempInfo.get(14));
		p.setDefb(tempInfo.get(15));
		p.setAssist(tempInfo.get(16));
		p.setSteal(tempInfo.get(17));
		p.setRejection(tempInfo.get(18));
		p.setMiss(tempInfo.get(19));
		p.setFoul(tempInfo.get(20));
		p.setPts(tempInfo.get(21));
		p.setWin(tempInfo.get(22));
		p.setLose(tempInfo.get(23));
		
		p_p_a_b.add(p);
	}
	//-------------装入p-tot-basic对象
	//-------------装入p-tot-basic对象
	public void initializeP_t_b(String name,int i){
		PlayerDataPlayOff_Tot_Basic p = new PlayerDataPlayOff_Tot_Basic();
		p.setId(i);
		p.setName(name);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setGp(tempInfo.get(2));
		//p.setGs(tempInfo.get(3));
		p.setTime(tempInfo.get(3));
		p.setShootper(tempInfo.get(4));
		p.setShoot_in(tempInfo.get(5));
		p.setShoot_all(tempInfo.get(6));
		p.setThper(tempInfo.get(7));
		p.setTh_in(tempInfo.get(8));
		p.setTh_all(tempInfo.get(9));
		p.setFtper(tempInfo.get(10));
		p.setFt_in(tempInfo.get(11));
		p.setFt_all(tempInfo.get(12));
		p.setBackbound(tempInfo.get(13));
		p.setOffb(tempInfo.get(14));
		p.setDefb(tempInfo.get(15));
		p.setAssist(tempInfo.get(16));
		p.setSteal(tempInfo.get(17));
		p.setRejection(tempInfo.get(18));
		p.setMiss(tempInfo.get(19));
		p.setFoul(tempInfo.get(20));
		p.setPts(tempInfo.get(21));
		p.setWin(tempInfo.get(22));
		p.setLose(tempInfo.get(23));
		
		p_p_t_b.add(p);
	}
	//-------------装入p-ad-basic对象
	//-------------装入p-ad-basic对象
	public void initializeP_ad_b(String name,int i){
		PlayerDataPlayOff_Ad_Basic p = new PlayerDataPlayOff_Ad_Basic();
		
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setBackeff(tempInfo.get(2));
		p.setOffbeff(tempInfo.get(3));
		p.setDefbeff(tempInfo.get(4));
		p.setAssisteff(tempInfo.get(5));
		p.setStealeff(tempInfo.get(6));
		p.setRejeff(tempInfo.get(7));
		p.setMisseff(tempInfo.get(8));
		p.setUseeff(tempInfo.get(9));
		p.setOffeff(tempInfo.get(10));
		p.setDefeff(tempInfo.get(11));
		p.setWs(tempInfo.get(12));
		p.setOffws(tempInfo.get(13));
		p.setDefws(tempInfo.get(14));
		p.setPer(tempInfo.get(15));
		p.setStrshoot(tempInfo.get(16));
		p.setKda(tempInfo.get(17));
		p.setBerej(tempInfo.get(18));
		
		p_p_ad_b.add(p);
	}	
	//-------------装入p-ad-shoot对象
	public void initializeP_ad_s(String name,int i){
		PlayerDataPlayOff_Ad_Shoot p = new PlayerDataPlayOff_Ad_Shoot();
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0));
		p.setTeam(tempInfo.get(1));
		p.setShootdis(tempInfo.get(2));
		p.setBshootper(tempInfo.get(3));
		p.setBshoot_in(tempInfo.get(4));
		p.setBshoot_all(tempInfo.get(5));
		p.setB_per(tempInfo.get(6));
		p.setCloseshootper(tempInfo.get(7));
		p.setCloseshoot_in(tempInfo.get(8));
		p.setCloseshoot_all(tempInfo.get(9));
		p.setClose_per(tempInfo.get(10));
		p.setMidshootper(tempInfo.get(11));
		p.setMidshoot_in(tempInfo.get(12));
		p.setMidshoot_all(tempInfo.get(13));
		p.setMid_per(tempInfo.get(14));
		p.setFarshootper(tempInfo.get(15));
		p.setFarshoot_in(tempInfo.get(16));
		p.setFarshoot_all(tempInfo.get(17));
		p.setFar_per(tempInfo.get(18));
		p.setTrueshootper(tempInfo.get(19));
		p.setShooteff(tempInfo.get(20));
		
		p_p_ad_s.add(p);
	}
	//-------------数据库操作部分
	public void writeDB_P_a_b(){
		
	}
	public void writeDB_P_t_b(){
		
	}
	public void writeDB_P_ad_b(){
		
	}
	public void writeDB_P_ad_s(){
		
	}
	//-----------初始化季后赛信息
	public void initializePlayerPlayOff(){
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		String name = "";
		for(int i = 1;i<10;i++){
		    //int i = 379;
			
		   
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_playoff.html";
			
			try{
				//-------------------------初始化球员基本信息
				name = playerdetail.get(i-1).getName();
						
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
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		
			    		if(tablesize==22){
			    			tempInfo.add("unknown");
			    			tempInfo.add("unknown");
			    		}
			    		initializeP_a_b(name,i);
			    		tempInfo.clear();
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
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		System.out.println();
			    		
			    		if(tablesize==22){
			    			tempInfo.add("unknown");
			    			tempInfo.add("unknown");
			    		}
			    		initializeP_t_b(name,i);
			    		tempInfo.clear();
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
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==19){
			    		System.out.println();
			    		
			    		initializeP_ad_b(name,i);
			    		tempInfo.clear();
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
			    	
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==21){
			    		System.out.println();
			    		
			    		initializeP_ad_s(name,i);
			    		tempInfo.clear();
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
		writeDB_P_a_b();
		writeDB_P_t_b();
		writeDB_P_ad_b();
		writeDB_P_ad_s();
	}
}
