package crawler;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.db.MatchDb;
import data.po.matchData.MatchDataSeason;
import data.po.matchData.MatchPlayer;
import data.po.matchData.MatchTeam;

public class TestMatchdataCrawer {
	MatchDb matchDb = new MatchDb();
	public static void main(String[] args) {
		//20637
		TestMatchdataCrawer m = new TestMatchdataCrawer();
		for (int i = 20637; i <=37455; i++) {
			m.getSeasonMatch(i);
		}

	}
	
	public void update(){
		TestMatchdataCrawer m = new TestMatchdataCrawer();
		ArrayList<MatchDataSeason> newmatches = new ArrayList<MatchDataSeason>(); 
		MatchDataSeason newmatch=new MatchDataSeason();
		for (int i = 20637; i <=37455; i++) {
			newmatch=m.getSeasonMatch(i);
			newmatches.add(newmatch);
		}
		for(int i=0;i<newmatches.size();i++){
			for(int k=0;k<newmatches.get(i).getTeamPlayer().size();k++){
				//updateplayer(name,season,isseason);
			}
			//updateteam(teamshortname,season,isseason)
			//updateotherteam(otherteamshortname,season,season)
		}
		
		
	}
	

	public MatchDataSeason getSeasonMatch(int number) {
		
		MatchDataSeason match = new MatchDataSeason();
		
		Document doc = null;
		try {
			doc = Jsoup
					.connect("http://www.stat-nba.com/game/" + number + ".html")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0").timeout(10000)
					.get();

			if (doc.select("table").size() == 0) {
				return match;
			}

			// timeInfo,nameInfo 
			Element nameInfo1 = doc.select("div[class=teamDiv]")
					.select("a[href]").get(0);
			Element nameInfo2 = doc.select("div[class=teamDiv]")
					.select("a[href]").get(5);
			String  isSeason = doc.select("div[class=title]").first().text().split(" ")[1];
			
			String timeInfo = doc.select("meta[name=description]").get(0)
					.attr("content");
			
			if(isSeason.equals("常规赛")){
				System.out.println(number+"------------------------");
				match.setisSeason("yes");
			}
			else{
				match.setisSeason("no");
			}
			
			match.setMatchID(String.valueOf(number));
			match.setSeason(timeInfo.split(",")[0].split("赛")[0]);
			System.out.println(match.getSeason());
			match.setDate( timeInfo.split(",")[1].replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", ""));
	
			match.setOtherTeam(nameInfo1.attr("href").split("/")[2].split("\\.")[0]); 	
			match.setTeam(nameInfo2.attr("href").split("/")[2].split("\\.")[0]);

			// pointInfo
			String[] pointInfos = new String[10];
			pointInfos[0] = "0-0";
			Elements pointInfo = doc.select("table[class!=stat_box]")
					.select("tbody").select("tr");
			for (int k = 0; k < pointInfo.size() / 2; k++) {
				pointInfos[0] = String.valueOf(Integer.valueOf(pointInfo.get(k)
						.text().split(" ")[1])
						+ Integer.valueOf(pointInfos[0].split("-")[0]))
						+ "-"
						+ String.valueOf(Integer.valueOf(pointInfo
								.get(pointInfo.size() / 2 + k).text()
								.split(" ")[0])
								+ Integer.valueOf(pointInfos[0].split("-")[1]));
				pointInfos[k + 1] = pointInfo.get(k).text().split(" ")[1]
						+ "-"
						+ pointInfo.get(pointInfo.size() / 2 + k).text()
								.split(" ")[0];
			}
			match.setPoint(pointInfos);

			

			// 球员球队详细信息
			Elements e = doc.select("table[class=stat_box]").select("tbody")
					.select("tr");
			Elements last;
			ArrayList<MatchPlayer> players = new ArrayList<MatchPlayer>();
			MatchPlayer player = new MatchPlayer();
			MatchTeam team = new MatchTeam();
			MatchTeam otherteam = new MatchTeam();
			if(isSeason.equals("常规赛")){
				team.setIsseason("yes");
				otherteam.setIsseason("yes");
			}
			else{
				team.setIsseason("no");
				otherteam.setIsseason("no");
			}
			int other = 0; // 记录主队的第一人数据位置
			for (int k = 0; k < e.size(); k++) {
				player = new MatchPlayer();
				if(isSeason.equals("常规赛")){
					player.setIsseason("yes");
				}
				else{
					player.setIsseason("no");
				}

				team.setMatchID(String.valueOf(number));
				otherteam.setMatchID(String.valueOf(number));
				player.setMatchID(String.valueOf(number));
				
				team.setSeason(match.getSeason());
				otherteam.setSeason(match.getSeason());
				player.setSeason(match.getSeason());
				
				team.setDate(match.getDate());
				otherteam.setDate(match.getDate());
				player.setDate(match.getDate());
				
				team.setResult(match.getPoint()[0]);
				otherteam.setResult(match.getPoint()[0]);
				player.setResult(match.getPoint()[0]);
				
				team.setTwoteam(match.getOtherTeam()+"-"+match.getTeam());
				otherteam.setTwoteam(match.getOtherTeam()+"-"+match.getTeam());
				player.setTwoteam(match.getOtherTeam()+"-"+match.getTeam());
				
				
				last = e.get(k).select("td");

				if (last.text().equals("")) {
					other = k + 2;
					continue;
				}
				if(other == 0){
					player.setTeam(match.getOtherTeam());
					player.setPlayername(last.get(1).text());
					if(player.getPlayername().contains("'")){
						player.setPlayername(player.getPlayername().replaceAll("'", "’"));
					}
					player.setIsFirst(last.get(2).text());
					player.setTime(last.get(3).text());
					player.setShootEff(last.get(4).text());
					player.setShootEffNumber(last.get(5).text());
					player.setShoot(last.get(6).text());
					player.setTPShootEff(last.get(7).text());
					player.setTPShootEffNumber(last.get(8).text());
					player.setTPShoot(last.get(9).text());
					player.setFTShootEff(last.get(10).text());
					player.setFTShootEffNumber(last.get(11).text());
					player.setFT(last.get(12).text());
					player.setRealEff(last.get(13).text());
					player.setBank(last.get(14).text());
					player.setBankOff(last.get(15).text());
					player.setBankDef(last.get(16).text());
					player.setAss(last.get(17).text());
					player.setSteal(last.get(18).text());
					player.setRejection(last.get(19).text());
					player.setTo(last.get(20).text());
					player.setFoul(last.get(21).text());
					player.setPoints(last.get(22).text());
					players.add(player);
				}
				if (other != 0) {
					if(k==other-1){
						team.setTeamShortName(match.getOtherTeam());
						team.setPlayerNumber(last.get(1).text());
						team.setShootEff(last.get(2).text());
						team.setShootEffNumber(last.get(3).text());
						team.setShoot(last.get(4).text());
						team.setTPShootEff(last.get(5).text());
						team.setTPShootEffNumber(last.get(6).text());
						team.setTPShoot(last.get(7).text());
						team.setFTShootEff(last.get(8).text());
						team.setFTShootEffNumber(last.get(9).text());
						team.setFT(last.get(10).text());
						team.setRealEff(last.get(11).text());
						team.setBank(last.get(12).text());
						team.setBankOff(last.get(13).text());
						team.setBankDef(last.get(14).text());
						team.setAss(last.get(15).text());
						team.setSteal(last.get(16).text());
						team.setRejection(last.get(17).text());
						team.setTo(last.get(18).text());
						team.setFoul(last.get(19).text());
						team.setPoints(last.get(20).text());
					}
					else if(k==(e.size()-1)){
						otherteam.setTeamShortName(match.getTeam());
						otherteam.setPlayerNumber(last.get(1).text());
						otherteam.setShootEff(last.get(2).text());
						otherteam.setShootEffNumber(last.get(3).text());
						otherteam.setShoot(last.get(4).text());
						otherteam.setTPShootEff(last.get(5).text());
						otherteam.setTPShootEffNumber(last.get(6).text());
						otherteam.setTPShoot(last.get(7).text());
						otherteam.setFTShootEff(last.get(8).text());
						otherteam.setFTShootEffNumber(last.get(9).text());
						otherteam.setFT(last.get(10).text());
						otherteam.setRealEff(last.get(11).text());
						otherteam.setBank(last.get(12).text());
						otherteam.setBankOff(last.get(13).text());
						otherteam.setBankDef(last.get(14).text());
						otherteam.setAss(last.get(15).text());
						otherteam.setSteal(last.get(16).text());
						otherteam.setRejection(last.get(17).text());
						otherteam.setTo(last.get(18).text());
						otherteam.setFoul(last.get(19).text());
						otherteam.setPoints(last.get(20).text());
					}
					else{
						player.setTeam(match.getTeam());
						player.setPlayername(last.get(1).text());
						if(player.getPlayername().contains("'")){
							player.setPlayername(player.getPlayername().replaceAll("'", "’"));
						}
						player.setIsFirst(last.get(2).text());
						player.setTime(last.get(3).text());
						player.setShootEff(last.get(4).text());
						player.setShootEffNumber(last.get(5).text());
						player.setShoot(last.get(6).text());
						player.setTPShootEff(last.get(7).text());
						player.setTPShootEffNumber(last.get(8).text());
						player.setTPShoot(last.get(9).text());
						player.setFTShootEff(last.get(10).text());
						player.setFTShootEffNumber(last.get(11).text());
						player.setFT(last.get(12).text());
						player.setRealEff(last.get(13).text());
						player.setBank(last.get(14).text());
						player.setBankOff(last.get(15).text());
						player.setBankDef(last.get(16).text());
						player.setAss(last.get(17).text());
						player.setSteal(last.get(18).text());
						player.setRejection(last.get(19).text());
						player.setTo(last.get(20).text());
						player.setFoul(last.get(21).text());
						player.setPoints(last.get(22).text());
						players.add(player);
					}
				}
			}
			
			/*System.out.println(players.size());
			System.out.println(team.getPoints());
			System.out.println(otherteam.getPoints());*/
			writematchInfo(match);
			writematchPlayer(players);
			writematchTeam(team, otherteam);
			
			
			match.setTeamPlayer(players);
			match.setTeamdata(team);
			match.setOtherteamdata(otherteam);
			
			return match;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	private void writematchInfo(MatchDataSeason m){
		matchDb.addmatchinfo(m);
	}
	private void writematchPlayer(ArrayList<MatchPlayer> m){
		for(int i=0;i<m.size();i++){
		matchDb.addmatchplayer(m.get(i));
		}
	}
	private void writematchTeam(MatchTeam team,MatchTeam otherTeam){
		matchDb.addmatchteam(team);
		matchDb.addmatchteam(otherTeam);
	}
	

}
