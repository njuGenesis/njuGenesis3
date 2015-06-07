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

import data.db.PlayerDb;
import data.po.playerData.*;

public class TestCrawlerByJsoup {
	PlayerDb pdb = new PlayerDb();
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
	   t.getProgressPlayer("assists");
	}
	public void test(){
		Document doc = null;
		String url = "http://www.stat-nba.com/team/stat_box_team.php?team=CHI&season=2014&col=pts&order=1&isseason=1";
		try{
			doc = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").get();
		    Elements e = doc.select("table.stat_box").select("tbody").select("tr");//.select("td");
		    
		    System.out.println(e);
//		    for(Element el:e){
//		    	temp++;
//		    	System.out.print(el.text()+";");
//		    	if(temp==22){
//		    		temp = 0;
//		    		System.out.println();
//		    	}
//		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//-------------装入detail对象
	private void initializeDetailObject(int i){
		//--------每一个球员的基本信息
		PlayerDetailInfo p = new PlayerDetailInfo();
		int x = 4;
		p.setId(i);
		p.setName(tempInfo.get(0).replaceAll("'", "?"));
		p.setPosition(tempInfo.get(1));
		p.setHeight(tempInfo.get(2));
		p.setWeight(tempInfo.get(3));
		try{
		p.setBirth(tempInfo.get(x));
		x++;
		}catch(Exception e){
			
		}
		try{
		p.setBorncity(tempInfo.get(x));
		x++;
		}catch(Exception e){
			
		}
		try{
		p.setNumber(tempInfo.get(x));
		x++;
		}catch(Exception e){
			System.out.println(i+": number not exist");
		}
		
		playerdetail.add(p);
		
	}
	private void writeDB_Detail() {
		//数据库操作
		try{
		for(int i=0;i<playerdetail.size();i++){
			pdb.adddetail(playerdetail.get(i));
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//------------初始化最基本信息
	public void initializePlayerDetail(int first,int size){
		System.out.println("initialize player detail start");
		int temp = 0;
		Document doc = null;
			
		for(int i = first;i<size;i++){
		   
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
					
						if(el.text().substring(0,5).equals("球衣号码:")){
						//System.out.println(el.text()+";");
						//----每一个球员的信息暂存
							tempInfo.add(el.text().substring(5).replaceAll(" 详情", ""));
							break;
						}
						
					
					if((el.text().substring(0,5).equals("全　　名:"))){					
					tempInfo.add(el.text().substring(5));		
					temp++;
					}					
					if((el.text().substring(0,5).equals("位　　置:"))){					
						tempInfo.add(el.text().substring(5));	
						temp++;
						}
					if((el.text().substring(0,5).equals("身　　高:"))){					
						tempInfo.add(el.text().substring(5));
						temp++;
						}
					if((el.text().substring(0,5).equals("体　　重:"))){					
						tempInfo.add(el.text().substring(5));	
						temp++;
						continue;
						}
					if(temp==4){
						temp++;
						if((el.text().substring(0,5).equals("出生日期:"))){					
							tempInfo.add(el.text().substring(5));
							
							}
						else{
							
							tempInfo.add("null");
						}
						continue;
					}
					if(temp==5){
						temp++;
						if((el.text().substring(0,5).equals("出生城市:"))){					
						tempInfo.add(el.text().substring(5));	
						
							}
						else{
						tempInfo.add("null");
						}
						continue;
					}
					
				}
				temp = 0;
				initializeDetailObject(i);
				tempInfo.clear();//装入对象之后情况该数组
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		writeDB_Detail();
		System.out.println("initialize player detail end");
	}
	
	//====================================================================================
	//-------------转入season_avg_basic对象
	private void initializeS_a_b(String name,int i){
		PlayerDataSeason_Avg_Basic p = new PlayerDataSeason_Avg_Basic();
		p.setId(i);
		p.setName(name.replaceAll("'", "?"));
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
	private void initializeS_t_b(String name,int i){
		PlayerDataSeason_Tot_Basic p = new PlayerDataSeason_Tot_Basic();
		p.setId(i);
		p.setName(name);
		p.setSeason(tempInfo.get(0).replaceAll("'", "?"));
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
	private void initializeS_ad_b(String name,int i){
		PlayerDataSeason_Ad_Basic p = new PlayerDataSeason_Ad_Basic();
		
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0).replaceAll("'", "?"));
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
	private void initializeS_ad_s(String name,int i){
		PlayerDataSeason_Ad_Shoot p = new PlayerDataSeason_Ad_Shoot();
		p.setName(name);
		p.setId(i);
		p.setSeason(tempInfo.get(0).replaceAll("'", "?"));
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
	private void writeDB_S_a_b(){
		try{
			for(int i=0;i<p_s_a_b.size();i++){
				pdb.adds_a_b(p_s_a_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_S_t_b(){
		try{
			for(int i=0;i<p_s_t_b.size();i++){
				pdb.adds_t_b(p_s_t_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_S_ad_b(){
		try{
			for(int i=0;i<p_s_ad_b.size();i++){
				pdb.adds_ad_b(p_s_ad_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_S_ad_s(){
		try{
			for(int i=0;i<p_s_ad_s.size();i++){
				pdb.adds_ad_s(p_s_ad_s.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	
	//-------------初始化常规赛信息
	public void initializePlayerSeason(int first,int size){
		System.out.println("initialize player season start");
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		String name = "";
		for(int i = first;i<size;i++){
		    //int i = 379;
			//System.out.println(i);
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_season.html";
			try{
				//-------------------------初始化球员基本信息
				name = playerdetail.get(i-first).getName();//获取姓名
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
			
			    	//System.out.print(el.text()+";");
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		//System.out.println();
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
			    	//System.out.print(el.text()+";");
			        tempInfo.add(el.text());
			    	//temp++;
			    	
			    	if(temp==tablesize){
			    		//System.out.println();
			    		
			    		
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
			    	//System.out.print(el.text()+";");
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==19){
			    		//System.out.println();
			    		
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
			    	//System.out.print(el.text()+";");
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==21){
			    		//System.out.println();
			    		
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
		System.out.println("initialize player season end");
	}
	//=====================================================================================
	
	//-------------转入p_avg_basic对象
	private void initializeP_a_b(String name,int i){
		PlayerDataPlayOff_Avg_Basic p = new PlayerDataPlayOff_Avg_Basic();
		p.setId(i);
		p.setName(name.replaceAll("'", "?"));
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
	private void initializeP_t_b(String name,int i){
		PlayerDataPlayOff_Tot_Basic p = new PlayerDataPlayOff_Tot_Basic();
		p.setId(i);
		p.setName(name.replaceAll("'", "?"));
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
	private void initializeP_ad_b(String name,int i){
		PlayerDataPlayOff_Ad_Basic p = new PlayerDataPlayOff_Ad_Basic();
		
		p.setName(name.replaceAll("'", "?"));
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
	private void initializeP_ad_s(String name,int i){
		PlayerDataPlayOff_Ad_Shoot p = new PlayerDataPlayOff_Ad_Shoot();
		p.setName(name.replaceAll("'", "?"));
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
	private void writeDB_P_a_b(){
		try{
			for(int i=0;i<p_p_a_b.size();i++){
				pdb.addp_a_b(p_p_a_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_P_t_b(){
		try{
			for(int i=0;i<p_p_t_b.size();i++){
				pdb.addp_t_b(p_p_t_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_P_ad_b(){
		try{
			for(int i=0;i<p_p_ad_b.size();i++){
				pdb.addp_ad_b(p_p_ad_b.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	private void writeDB_P_ad_s(){
		try{
			for(int i=0;i<p_p_ad_s.size();i++){
				pdb.addp_ad_s(p_p_ad_s.get(i));
			}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	}
	//-----------初始化季后赛信息
	public void initializePlayerPlayOff(int first,int size){
		System.out.println("initialize player playoff start");
		Document doc = null;
		String url = "";
		int temp = 0;
		int tablesize = 0;
		boolean isFirst = false;
		String name = "";
		for(int i = first;i<size;i++){
		    //int i = 379;
			
		   
			url = "http://www.stat-nba.com/player/stat_box/"+i+"_playoff.html";
			
			try{
				//-------------------------初始化球员基本信息
				name = playerdetail.get(i-first).getName();
						
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
			   // System.out.println("size:"+tablesize);
			    temp = 0;
			    for(Element el:Avgbasic){
			    	if(!isFirst){
			    	isFirst = true;
			    	}
			    	else{
			    	temp++;
			 
			    	//System.out.print(el.text()+";");//之后改成设置属性
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		//System.out.println();
			    		
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
			    	//System.out.print(el.text()+";");
			    	//temp++;
			    	tempInfo.add(el.text());
			    	
			    	if(temp==tablesize){
			    		//System.out.println();
			    		
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
			    	//System.out.print(el.text()+";");
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==19){
			    		//System.out.println();
			    		
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
			    	//System.out.print(el.text()+";");
			    	
			    	tempInfo.add(el.text());
			    	temp++;
			    	if(temp==21){
			    		//System.out.println();
			    		
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
		System.out.println("initialize player playoff end");
	}

	public void getHotPlayerDaily(String key){
		URL url;  
        StringBuffer sb = new StringBuffer();  
        String line = null; 
        String path = "";
        switch(key){
        case "points":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_points.json";break;
        case "rebounds":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_rebounds.json";break;
        case "assists":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_assists.json";break;
        case "blocks":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_blocks.json";break;
        case "steals":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_steals.json";break;
        	default:
        }
        try {  
            url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            BufferedReader buffer = new BufferedReader(  
                    new InputStreamReader(is));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
            }  
           
            String json = sb.substring(sb.indexOf("["), sb.indexOf("]") + 1);
//            json = json.replaceAll(":\\{", ":[\\{");
//            json = json.replaceAll("\\},\"teamProfile\"", "\\}],\"teamProfile\"");
//            json = json.replaceAll("\\},\"rank\"", "\\}],\"rank\"");
            System.out.println(json);
          //  System.out.println(sb.substring(sb.indexOf("playerProfile"),sb.indexOf("teamProfile")+1));
            JSONArray js  = new JSONArray(json);
           // String json = sb.substring(sb.indexOf("{"), sb.indexOf("}") + 1);
            for(int i = 0;i<js.length();i++){
            	
            	Object pprofile = js.getJSONObject(i).get("playerProfile");
            	JSONObject parr = new JSONObject(pprofile.toString());
            	System.out.println(parr.getString("displayName"));
            	System.out.println(parr.getString("jerseyNo"));
            	System.out.println(parr.getString("position"));
            	
            	Object tprofile = js.getJSONObject(i).get("teamProfile");
            	JSONObject tarr = new JSONObject(tprofile.toString());
            	System.out.println(tarr.getString("city"));
            	System.out.println(tarr.getString("name"));
            	
            	System.out.println(js.getJSONObject(i).getString("rank"));
            	System.out.println(js.getJSONObject(i).getString("value"));
            	System.out.println();
            }
            
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
	
	public void getHotPlayerSeason(String key){
		URL url;  
        StringBuffer sb = new StringBuffer();  
        String line = null; 
        String path = "";
        String keyavg = "";
        switch(key){
        case "points":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_points_perGame.json";keyavg = "pointsPg";break;
        case "rebounds":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_rebs_perGame.json";keyavg = "rebsPg";break;
        case "assists":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_assists_perGame.json";keyavg = "assistsPg";break;
        case "blocks":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_blocks_perGame.json";keyavg = "blocksPg";break;
        case "steals":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_steals_perGame.json";keyavg = "stealsPg";break;
        case "tppct":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_tppct_perGame.json";keyavg = "tppct";break;	
        case "fgpct":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_fgpct_perGame.json";keyavg = "fgpct";break;
        case "ftpct":path = "http://china.nba.com//wap/static/data/league/playerstats_5_true_ftpct_perGame.json";keyavg = "ftpct";break;
        default:
        }
        try {  
            url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            BufferedReader buffer = new BufferedReader(  
                    new InputStreamReader(is));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
            }  
           
            String json = sb.substring(sb.indexOf("["), sb.indexOf("]") + 1);
//            json = json.replaceAll(":\\{", ":[\\{");
//            json = json.replaceAll("\\},\"teamProfile\"", "\\}],\"teamProfile\"");
//            json = json.replaceAll("\\},\"rank\"", "\\}],\"rank\"");
            System.out.println(json);
          //  System.out.println(sb.substring(sb.indexOf("playerProfile"),sb.indexOf("teamProfile")+1));
            JSONArray js  = new JSONArray(json);
           // String json = sb.substring(sb.indexOf("{"), sb.indexOf("}") + 1);
            for(int i = 0;i<js.length();i++){
            	System.out.println(js.getJSONObject(i).getString("rank"));
            	
            	Object pprofile = js.getJSONObject(i).get("playerProfile");
            	JSONObject parr = new JSONObject(pprofile.toString());
            	System.out.println(parr.getString("displayName"));
            	System.out.println(parr.getString("jerseyNo"));
            	System.out.println(parr.getString("position"));
            	
            	Object tprofile = js.getJSONObject(i).get("teamProfile");
            	JSONObject tarr = new JSONObject(tprofile.toString());
            	System.out.println(tarr.getString("city"));
            	System.out.println(tarr.getString("name"));
            	//"statAverage":{"assistsPg":1.2,"blocksPg":2.3,"defRebsPg":10.4,"efficiency":22.8,"fgaPg":10.706,"fgmPg":6.176,"fgpct":57.7,"foulsPg":3.5,"ftaPg":9.706,"ftmPg":4.0,"ftpct":41.2,"games":17,"gamesStarted":17,"minsPg":33.8,"offRebsPg":3.6,"pointsPg":16.4,"rebsPg":14.0,"stealsPg":1.4,"tpaPg":0.0,"tpmPg":0.0,"tppct":0.0,"turnoversPg":2.2}
            	
            	Object stprofile = js.getJSONObject(i).get("statAverage");
            	JSONObject starr = new JSONObject(stprofile.toString());
            	System.out.println(starr.getDouble(keyavg));
            	
            	System.out.println();
            }
            
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}

	public void getProgressPlayer(String key){
		URL url;  
        StringBuffer sb = new StringBuffer();  
        String line = null; 
        String path = "http://china.nba.com/wap/static/data/league/playerwhoshot.json";
//        switch(key){
//        case "points":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_points.json";break;
//        case "rebounds":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_rebounds.json";break;
//        case "assists":path = "http://china.nba.com/wap/static/data/league/dailyplayerleader_assists.json";break;
//        
//        	default:
//        }
        JSONArray js = new JSONArray();;
        try {  
            url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            InputStream is = conn.getInputStream();  
            BufferedReader buffer = new BufferedReader(  
                    new InputStreamReader(is));  
            while ((line = buffer.readLine()) != null) {  
                sb.append(line);  
            }  
            
            System.out.println(sb.toString());
            
            JSONObject og = new JSONObject(sb.toString());
            JSONObject payload = og.getJSONObject("payload");
           // System.out.println(payload.toString());

            //JSONObject payload = temp.getJSONObject("");
            JSONArray pointHotPlayers=payload.getJSONArray("pointHotPlayers");
            JSONArray rebHotPlayers=payload.getJSONArray("reboundHotPlayers");
            JSONArray assistHotPlayers=payload.getJSONArray("assistHotPlayers");
          //  String pointHotPlayers = payload.getString("pointHotPlayers");
            
           // String rebHotPlayers = payload.getString("reboundHotPlayers");
           // String assistHotPlayers = payload.getString("assistHotPlayers");
            switch(key){
            case "points":js = pointHotPlayers;break;
            case "rebounds":js = rebHotPlayers;break;
            case "assists":js = assistHotPlayers;break;
            default:
            }
//            json = json.replaceAll(":\\{", ":[\\{");
//            json = json.replaceAll("\\},\"teamProfile\"", "\\}],\"teamProfile\"");
//            json = json.replaceAll("\\},\"rank\"", "\\}],\"rank\"");
            
          //  System.out.println(sb.substring(sb.indexOf("playerProfile"),sb.indexOf("teamProfile")+1));
            //JSONArray js  = new JSONArray(json);
           // String json = sb.substring(sb.indexOf("{"), sb.indexOf("}") + 1);
            for(int i = 0;i<js.length();i++){
            	
            	Object pprofile = js.getJSONObject(i).get("playerProfile");
            	JSONObject parr = new JSONObject(pprofile.toString());
            	System.out.println(parr.getString("displayName"));
            	System.out.println(parr.getString("jerseyNo"));
            	System.out.println(parr.getString("position"));
            	
            	Object tprofile = js.getJSONObject(i).get("teamProfile");
            	JSONObject tarr = new JSONObject(tprofile.toString());
            	System.out.println(tarr.getString("city"));
            	System.out.println(tarr.getString("name"));
            	
            	System.out.println(js.getJSONObject(i).getString("rank"));
            	System.out.println(js.getJSONObject(i).getString("last5"));
            	System.out.println(js.getJSONObject(i).getString("differential"));
            	System.out.println();
            }
            
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
}
