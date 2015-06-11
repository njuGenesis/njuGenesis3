package bussinesslogic.match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import bslogicService.MatchInfoService;
import data.db.MatchDb;
import data.po.matchData.MatchDataSeason;
import data.po.matchData.MatchPlayer;
import data.po.matchData.MatchTeam;

public class MatchLogic implements MatchInfoService {
	MatchDb m = new MatchDb();

	// 获得一个赛季所有的比赛的基本信息 season格式为13-14    isseason为yes时返回常规赛，为no时返回季后赛，为unknown时返回全部比赛
	public ArrayList<MatchDataSeason> GetSeasonMatch(String season,String isseason) {
		ArrayList<MatchDataSeason> matches = new ArrayList<MatchDataSeason>();
		matches = m.getmatchinfo("unknown", "unknown", season, "unknown",
				isseason, "unknown");
		return matches;
	}

	// 获得某一天的所有比赛 ，date的格式为“2014-11-01”
	public ArrayList<MatchDataSeason> GetDateMatch(String date) {
		ArrayList<MatchDataSeason> matches = new ArrayList<MatchDataSeason>();
		matches = m.getmatchinfo("unknown", "unknown", "unknown", date,
				"unknown", "unknown");
		return matches;
	}

	// 获得一个球队一个赛季的*所有*比赛 ，season的格式为“13-14”,球队为简称, isseason格式同上
	public ArrayList<MatchDataSeason> GetTeamSeasonMatch(String shotrName,
			String season,String isseason) {
		ArrayList<MatchDataSeason> matches1 = new ArrayList<MatchDataSeason>();
		matches1 = m.getmatchinfo(shotrName, "unknown",season, "unknown",
				isseason,"unknown");
		ArrayList<MatchDataSeason> matches2 = new ArrayList<MatchDataSeason>();
		matches2 = m.getmatchinfo("unknown", shotrName, season, "unknown",
				isseason,"unknown");
		matches1.addAll(matches2);
		matches1.sort(new Comparator<MatchDataSeason>() {
			public int compare(MatchDataSeason o1, MatchDataSeason o2) {
				if (o1.getDate().compareTo(o2.getDate()) <= 0)
					return 0;
				else
					return 1;
			}
		});
		return matches1;
	}

	// 获得一个球队一个赛季的所有*主场*比赛 ，season的格式为“13-14”,球队为简称, isseason格式同上
	public ArrayList<MatchDataSeason> GetTeamSeasonHomeMatch(String shotrName,
			String season,String isseason) {
		ArrayList<MatchDataSeason> matches1 = new ArrayList<MatchDataSeason>();
		matches1 = m.getmatchinfo(shotrName, "unknown",season, "unknown",
				isseason, "unknown");
		matches1.sort(new Comparator<MatchDataSeason>() {
			public int compare(MatchDataSeason o1, MatchDataSeason o2) {
				if (o1.getDate().compareTo(o2.getDate()) <= 0)
					return 0;
				else
					return 1;
			}
		});
		return matches1;
	}

	// 获得一个球队一个赛季的所有*客场*比赛 ，season的格式为“13-14”,球队为简称, isseason格式同上
	public ArrayList<MatchDataSeason> GetTeamSeasonAwayMatch(String shotrName,
			String season,String isseason) {
		ArrayList<MatchDataSeason> matches1 = new ArrayList<MatchDataSeason>();
		matches1 = m.getmatchinfo("unknown", shotrName, season, "unknown",
				isseason, "unknown");
		matches1.sort(new Comparator<MatchDataSeason>() {
			public int compare(MatchDataSeason o1, MatchDataSeason o2) {
				if (o1.getDate().compareTo(o2.getDate()) <= 0)
					return 0;
				else
					return 1;
			}
		});
		return matches1;
	}
	
	// 根据球员和赛季返回球员的比赛信息，isseason格式同上，season为unknown时不限制赛季
	public ArrayList<MatchPlayer> GetPlayerMatch(String playername,
			String isseason,String season) {
		ArrayList<MatchPlayer> playerMatches = m.getmatchplayer("unknown",
				playername, "unknown",isseason,season);
		
		return playerMatches;
	}
	
	// 根据球队和赛季返回球队的比赛信息，isseason格式同上，season为unknown时不限制赛季
	public ArrayList<MatchTeam> GetTeamMatch(String teamshortname,
			String isseason,String season) {
		ArrayList<MatchTeam> teamMatches = m.getmatchteam("unknown", teamshortname, isseason,season);
		return teamMatches;
	}
	
	// 根据球员和赛季返回比赛，isseason格式同上，season为unknown时不限制赛季
	public ArrayList<MatchDataSeason> GetPlayerSeasonMatch(String playername,
			String season,String isseason) {
		ArrayList<MatchPlayer> playerMatchID = m.getmatchplayer("unknown",
				playername, "unknown",isseason,season);
		ArrayList<MatchDataSeason> res = new ArrayList<MatchDataSeason>();
		for (int i = 0; i < playerMatchID.size(); i++) {
			res.add(m.getmatchinfo("unknown", "unknown",  "unknown", "unknown",
					 "unknown", playerMatchID.get(i).getMatchID()).get(0));
		}
		return res;
	}

	// 根据两个球队返回比赛，isseason格式同上
	public ArrayList<MatchDataSeason> GetTeamToTeamMatch(String team,String season,
			String otherteam,String isseason) {
		ArrayList<MatchDataSeason> res1 = new ArrayList<MatchDataSeason>();
		res1 = m.getmatchinfo(team, otherteam, season, "unknown", isseason,
				"unknown");
		ArrayList<MatchDataSeason> res2 = new ArrayList<MatchDataSeason>();
		res2 = m.getmatchinfo(otherteam, team, season, "unknown", isseason,
				"unknown");
		res1.addAll(res2);
		return res1;
	}

	// 根据MatchID返回该场比赛的所有信息
	public MatchDataSeason GetCompleteMatch(String MatchID) {
		MatchDataSeason res = new MatchDataSeason();
		res = m.getmatchinfo("unknown", "unknown", "unknown", "unknown",
				"unknown", MatchID).get(0);
		
		m.getAllMatchPlayer(res, MatchID, res.getTeam(), res.getOtherTeam());
		m.getAllMatchTeam(res, MatchID, res.getTeam(), res.getOtherTeam());
		/*res.setTeamdata(m.getmatchteam(MatchID, res.getTeam(), "unknown")
				.get(0));
		res.setOtherteamdata(m.getmatchteam(MatchID, res.getOtherTeam(),
				"unknown").get(0));
		res.setTeamPlayer(m.getmatchplayer(MatchID, "unknown", res.getTeam(), "unknown"));
		res.setOtherteamPlayer(m.getmatchplayer(MatchID, "unknown", res.getOtherTeam(),
				"unknown"));*/
		return res;
	}

	/*	// 根据球员返回比赛，isseason格式同上
	public ArrayList<MatchDataSeason> GetPlayerMatch(String playername,String isseason) {
		ArrayList<MatchPlayer> playerMatchID = m.getmatchplayer("unknown",
				playername, "unknown",isseason);
		ArrayList<MatchDataSeason> res = new ArrayList<MatchDataSeason>();
		for (int i = 0; i < playerMatchID.size(); i++) {
			res.add(m.getmatchinfo("unknown", "unknown", "unknown", "unknown",
					isseason, playerMatchID.get(i).getMatchID()).get(0));
		}
		return res;
	}*/

	public static void main(String[] args) {
		
		MatchLogic m = new MatchLogic();
		System.out.println(MatchLogic.getTime());
		ArrayList<MatchTeam> a = m.GetTeamMatch("SAS",  "yes", "13-14");
		System.out.println(a.size());
		System.out.println(MatchLogic.getTime());
		
		/*MatchDataSeason a =m.GetCompleteMatch("25401");
		System.out.println(a.getDate());
		System.out.println(a.getTeamdata().getAss());
		System.out.println(a.getOtherteamPlayer().get(0).getPoints());*/
	}

	public static long getTime() {
		Date d = new Date();
		return d.getTime();
	}

}
