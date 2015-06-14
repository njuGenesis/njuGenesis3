package bussinesslogic.team;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import presentation.contenui.TeamOfAll;
import bslogicService.TeamInfoService;
import bussinesslogic.match.MatchLogic;
import bussinesslogic.player.PlayerLogic;
import data.db.TeamDb;
import data.po.teamData.OtherTeamData;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamCompleteInfo;
import data.po.teamData.TeamHData;
import data.po.teamData.TeamLData;

public class TeamLogic implements TeamInfoService {

	// 返回所有球队的基本信息（场馆，建队年份等）
	public ArrayList<TeamBaseInfo> GetAllBaseInfo() throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamBaseInfo> temp = t.getbaseinfo("unknown");
		return temp;
	}

	// 根据season，shortname，isseason（是否是常规赛）返回部分球队的完整信息，（若要取得所有赛季的，则将season设为"unknown",另外两个属性类似）
	public ArrayList<TeamCompleteInfo> GetPartCompleteInfo(String shortName,
			String season, String isseason) throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamHData> temp = t.getHData(shortName, season, isseason);
		ArrayList<TeamCompleteInfo> res = new ArrayList<TeamCompleteInfo>();
		for (int i = 0; i < temp.size(); i++) {
			res.add(t.getAllTeamInfo(temp.get(i).getShortName(), temp.get(i)
					.getSeason(), temp.get(i).getIsseason()));
		}
		return res;
	}

	// 返回部分球队的低阶信息 （参数同上）
	public ArrayList<TeamLData> GetPartLInfo(String shortName, String season,
			String isseason) throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamLData> temp = t.getLData(shortName, season, isseason);
		return temp;
	}

	// 返回部分球队的高阶信息 （参数同上）
	public ArrayList<TeamHData> GetPartHInfo(String shortName, String season,
			String isseason) throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamHData> temp = t.getHData(shortName, season, isseason);
		return temp;
	}

	// 返回部分球队的对手信息 （参数同上）
	public ArrayList<OtherTeamData> GetPartOInfo(String shortName,
			String season, String isseason) throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<OtherTeamData> temp = t.getOtherTeamData(shortName, season,
				isseason);
		return temp;
	}

	// property为排序的属性
	// ppg场均得分、assitnumberpg场均助攻、stealnumberpg场均抢断、rejectionpg场均盖帽等（teambasedata表的属性都可以）
	// 返回的array不只5项，从大到小排序。
	public ArrayList<TeamCompleteInfo> hotTeamSeason(String season,
			String property, String isseason) throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamCompleteInfo> res = new ArrayList<TeamCompleteInfo>();
		ArrayList<TeamLData> origin = t
				.getSortLData(season, property, isseason);
		for (int i = 0; i < origin.size(); i++) {
			res.add(t.getAllTeamInfo(origin.get(i).getShortName(), origin
					.get(i).getSeason(), origin.get(i).getIsseason()));
		}
		return res;

	}

	// 0是场均得分，1是场均助攻，2是场均篮板，3是场均三分命中率，4是场均罚球进球率
	public ArrayList<Double> getAvg(String isseason) {
		TeamDb t = new TeamDb();
		ArrayList<TeamLData> allTeams = t.getSortLData("unknown", "season",
				isseason);
		String season = allTeams.get(0).getSeason();
		double pointsAvg = 0.0;
		double assAvg = 0.0;
		double bankAvg = 0.0;
		double threeAvg = 0.0;
		double ftAvg = 0.0;

		for (int i = 0; i < allTeams.size(); i++) {
			if (season.compareTo(allTeams.get(i).getSeason()) < 0) {
				season = allTeams.get(i).getSeason();
			}
		}

		allTeams = t.getSortLData(season, "season", isseason);

		for (int i = 0; i < allTeams.size(); i++) {
			pointsAvg = (pointsAvg * i + allTeams.get(i).getPPG()) / (i + 1);
			assAvg = (assAvg * i + allTeams.get(i).getAssitNumberPG())
					/ (i + 1);
			bankAvg = (bankAvg * i + allTeams.get(i).getBackBoardPG())
					/ (i + 1);
			threeAvg = (threeAvg * i + allTeams.get(i).getTPEff()) / (i + 1);
			ftAvg = (ftAvg * i + allTeams.get(i).getFTEff()) / (i + 1);
		}
		BigDecimal bg1 = new BigDecimal(pointsAvg);
		pointsAvg = bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bg2 = new BigDecimal(assAvg);
		assAvg = bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bg3 = new BigDecimal(bankAvg);
		bankAvg = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bg4 = new BigDecimal(threeAvg);
		threeAvg = bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		BigDecimal bg5 = new BigDecimal(ftAvg);
		ftAvg = bg5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		ArrayList<Double> res = new ArrayList<Double>();
		res.add(pointsAvg);
		res.add(assAvg);
		res.add(bankAvg);
		res.add(threeAvg);
		res.add(ftAvg);
		return res;
	}

	// 赛季低阶数据的平均值
	public TeamLData getLAvg(String season, String isseason) {
		TeamDb t = new TeamDb();
		return t.getLSeasonAvg(season, isseason);
	}

	// 赛季对手数据的平均值
	public OtherTeamData getOtherAvg(String season, String isseason) {
		TeamDb t = new TeamDb();
		return t.getOSeasonAvg(season, isseason);
	}

	// 赛季高阶数据的平均值
	public TeamHData getHAvg(String season, String isseason) {
		TeamDb t = new TeamDb();
		return t.getHSeasonAvg(season, isseason);
	}

	public static void main(String[] args) throws RemoteException {
		TeamLogic t = new TeamLogic(); // GetAllCompleteInfo
		// PlayerLogic p = new PlayerLogic();
		 ArrayList<TeamLData> teamLdata =t.GetPartLInfo("unknown", "unknown",
		 "unknown");
		ArrayList<TeamHData> teamHdata = t.GetPartHInfo("unknown", "unknown",
				"unknown");
		// ArrayList<OtherTeamData> teamLdata = t.GetPartOInfo("unknown",
		// "unknown", "unknown");

		// double winrate = t.getLAvg("unknown", "unknown").getWinrate();
		// double ppg = t.getLAvg("unknown", "unknown").getPPG();

		//  ASSIS  队ppg的影响 r=0.5845    defback(0.40)影响远大于offback(0.08)  shootnumber  0.558   tpnumber 0.5218    
		//shooteff 0.35      tpeff 0.46
		double avg_x = t.getLAvg("unknown", "unknown").getPPG();
		double avg_y = t.getLAvg("unknown", "unknown").getShootEff();
        System.out.println(avg_x+"   "+avg_y);
		
		double s_x = 0.0;
		double s_y = 0.0;

		for (int i = 0; i < teamLdata.size(); i++) {
			s_x = s_x + Math.pow(teamLdata.get(i).getPPG()- avg_x, 2);
			s_y = s_y + Math.pow(teamLdata.get(i).getShootEff() - avg_y, 2);
		}
		
		s_x = s_x / (teamLdata.size() - 1);
		s_y = s_y / (teamLdata.size() - 1);
		s_x = Math.sqrt(s_x);
		s_y = Math.sqrt(s_y);

		double r = 0.0;
		for (int i = 0; i < teamLdata.size(); i++) {
			r = r + (teamLdata.get(i).getPPG() - avg_x)
					* (teamLdata.get(i).getShootEff() - avg_y) / (s_x * s_y);
		}
		r = r / (teamLdata.size() - 1);
		
		
		System.out.println(r);

	}
}
