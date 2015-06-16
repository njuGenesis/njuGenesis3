package bussinesslogic.team;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;

import bslogicService.TeamInfoService;
import bussinesslogic.match.MatchLogic;
import data.db.TeamDb;
import data.po.matchData.MatchTeam;
import data.po.teamData.OtherTeamData;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamCompleteInfo;
import data.po.teamData.TeamDescriptionStat;
import data.po.teamData.TeamHData;
import data.po.teamData.TeamLData;

public class TeamLogic implements TeamInfoService {

	// 返回所有球队的基本信息（场馆，建队年份等）
	public ArrayList<TeamBaseInfo> GetAllBaseInfo(String season)
			throws RemoteException {
		TeamDb t = new TeamDb();
		ArrayList<TeamBaseInfo> temp = t.getbaseinfo("unknown", season);
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

	// 赛季低阶数据的平均值 (shortname为unknown时为全联盟)
	public TeamLData getLAvg(String shortname, String season, String isseason) {
		TeamDb t = new TeamDb();
		return t.getLSeasonAvg(shortname, season, isseason);
	}

	// 赛季对手数据的平均值
	public OtherTeamData getOtherAvg(String shortname, String season,
			String isseason) {
		TeamDb t = new TeamDb();
		return t.getOSeasonAvg(shortname, season, isseason);
	}

	// 赛季高阶数据的平均值
	public TeamHData getHAvg(String shortname, String season, String isseason) {
		TeamDb t = new TeamDb();
		return t.getHSeasonAvg(shortname, season, isseason);
	}

	// 描述统计值
	public ArrayList<TeamDescriptionStat> getDescription(String shortname,
			String season, String isseason) throws RemoteException {

		ArrayList<TeamDescriptionStat> res = new ArrayList<TeamDescriptionStat>();
		TeamLogic t = new TeamLogic();
		ArrayList<TeamLData> teams = t
				.GetPartLInfo(shortname, season, isseason);
		TeamLData avg = t.getLAvg(shortname, season, isseason);
		TeamDescriptionStat one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "PPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "TPEff");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "ShootEff");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "FTEff");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "BackBoardPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "AssitNumberPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "StealNumberPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "RejectionPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "ToPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "FoulPG");
		res.add(one);
		one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "Winrate");
		res.add(one);
		/*one = new TeamDescriptionStat();
		one.setvalue(teams, avg, "WinMatch");
		res.add(one);*/

		for (int i = 0; i < res.size(); i++) {
			res.get(i).setShortname(shortname);
			res.get(i).setSeason(season);
			res.get(i).setIsseason(isseason);
		}
		return res;
	}

	// t 0.025(22)=2.7039
	public void PersonR(String property1, String property2, String team,
			String season, String isseason) throws RemoteException {
		TeamLogic t = new TeamLogic();
		ArrayList<TeamLData> teamLdata = t
				.GetPartLInfo(team, "13-14", isseason);

		double avg_x = t.getLAvg(team, "13-14", isseason)
				.getproperty(property1);
		double avg_y = t.getLAvg(team, "13-14", isseason)
				.getproperty(property2);
		System.out.println(avg_x + "   " + avg_y);

		double s_x = 0.0;
		double s_y = 0.0;

		for (int i = 0; i < teamLdata.size(); i++) {
			s_x = s_x
					+ Math.pow(teamLdata.get(i).getproperty(property1) - avg_x,
							2);
			s_y = s_y
					+ Math.pow(teamLdata.get(i).getproperty(property2) - avg_y,
							2);
		}

		s_x = s_x / (teamLdata.size() - 1);
		s_y = s_y / (teamLdata.size() - 1);
		s_x = Math.sqrt(s_x);
		s_y = Math.sqrt(s_y);

		double r = 0.0;
		for (int i = 0; i < teamLdata.size(); i++) {
			r = r + (teamLdata.get(i).getproperty(property1) - avg_x)
					* (teamLdata.get(i).getproperty(property2) - avg_y)
					/ (s_x * s_y);
		}
		r = r / (teamLdata.size() - 1);
		System.out.println(r);
		double t_value = r
				* Math.pow((teamLdata.size() - 2) / (1 - Math.pow(r, 2)), 0.5);
		System.out.println(t_value);
	}

	
	//妈个鸡检验了半天都没什么数据符合正态分布还方差分析个卵！
	// 两只球队的单因素方差分析 (在某项属性上是否存在存在显著性差异)
	public void Var_a(String team1, String team2, String isseason,
			String season, String property) throws RemoteException {
		MatchLogic m = new MatchLogic();
		ArrayList<MatchTeam> t1 = m.GetTeamMatch(team1, isseason, season);
		ArrayList<MatchTeam> t2 = m.GetTeamMatch(team2, isseason, season);
		System.out.println(t2.size());
		double t1_avg = 0.0;
		double t2_avg = 0.0;
		double Qt = 0.0;

		for (int i = 0; i < t1.size(); i++) {
			t1_avg = t1_avg + Double.valueOf(t1.get(i).getproperty(property));
			t2_avg = t2_avg + Double.valueOf(t2.get(i).getproperty(property));
			Qt = Qt
					+ Math.pow(Double.valueOf(t1.get(i).getproperty(property)),
							2);
			Qt = Qt
					+ Math.pow(Double.valueOf(t2.get(i).getproperty(property)),
							2);
		}
		double C = Math.pow(t1_avg + t2_avg, 2) / (t1.size() + t2.size());
		double Qa = (Math.pow(t1_avg, 2) + Math.pow(t2_avg, 2)) / t1.size();
		t1_avg = t1_avg / t1.size();
		t2_avg = t2_avg / t2.size();

		double St = Qt - C;
		double Sa = Qa - C;
		double Va = Sa / 1;
		double Ve = (St - Sa) / (t1.size() + t2.size() - 2);
		double Fa = Va / Ve;
		if (Fa > 2.7) {
			if (t1_avg > t2_avg) {
				System.out.println(team1);
			} else {
				System.out.println(team2);
			}
		}
		/*
		 * System.out.println(C); System.out.println(Va);
		 * System.out.println(Ve);
		 */
		System.out.println(Fa);
	}

	// 主客场的单因素方差分析
	public void Var_a(String team, String isseason, String season,
			String property) throws RemoteException {
		MatchLogic m = new MatchLogic();
		ArrayList<MatchTeam> t1 = m.GetTeamMatch(team, isseason, season);
		ArrayList<MatchTeam> home = new ArrayList<MatchTeam>();
		ArrayList<MatchTeam> away = new ArrayList<MatchTeam>();

		System.out.println(t1.size());
		for (int i = 0; i < t1.size(); i++) {
			if (t1.get(i).getTwoteam().startsWith(t1.get(i).getTeamShortName())) {
				away.add(t1.get(i));
			} else {
				home.add(t1.get(i));
			}
		}
		System.out.println(home.size());
		System.out.println(away.size());

		// ArrayList<MatchDataSeason> t3=m.GetTeamToTeamMatch(team1, season,
		// team2, isseason);
		double t1_avg = 0.0;
		double t2_avg = 0.0;
		double Qt = 0.0;

		for (int i = 0; i < home.size(); i++) {
			t1_avg = t1_avg + Double.valueOf(home.get(i).getproperty(property));
			Qt = Qt
					+ Math.pow(
							Double.valueOf(home.get(i).getproperty(property)),
							2);
		}

		for (int i = 0; i < away.size(); i++) {
			t2_avg = t2_avg + Double.valueOf(away.get(i).getproperty(property));
			Qt = Qt
					+ Math.pow(
							Double.valueOf(away.get(i).getproperty(property)),
							2);
		}

		double C = Math.pow(t1_avg + t2_avg, 2) / (home.size() + away.size());
		double Qa = (Math.pow(t1_avg, 2) + Math.pow(t2_avg, 2)) / home.size();
		t1_avg = t1_avg / home.size();
		t2_avg = t2_avg / away.size();

		System.out.println(t1_avg + "   " + t2_avg);
		double St = Qt - C;
		double Sa = Qa - C;
		double Va = Sa / 1;
		double Ve = (St - Sa) / (home.size() + away.size() - 2);
		double Fa = Va / Ve;

		System.out.println(Fa);
	}

	// 将arraylist里面的某项属性存入double数组
	public double[] ChangeIntoDouble(ArrayList<TeamLData> teams, String property) {
		double[]  res = new double[teams.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = teams.get(i).getproperty(property);
		}
		return res;
	}

	
	
	
	//偏度还行峰度差的不是一点... 
	// 峰度偏度检验
	public void B3B4(String team, String isseason, String season,
			String property) throws RemoteException {
		ArrayList<TeamDescriptionStat> discription = getDescription(team,
				season, isseason);
		TeamDescriptionStat dis = new TeamDescriptionStat();
		for (int i = 0; i < discription.size(); i++) {
			if (discription.get(i).getType().equals(property)) {
				dis = discription.get(i);
				break;
			}
		}
		ArrayList<TeamLData> teamdata = GetPartLInfo(team, season, isseason);
		double up1 = 6 * (teamdata.size() - 2);
		double down1 = ((teamdata.size() + 1) * (teamdata.size() + 3));
		double a1 = Math.pow(up1 / down1, 0.5);

		double up2 = 24 * (teamdata.size() - 2) * (teamdata.size() - 3);
		double down2 = (teamdata.size() + 1) * (teamdata.size() + 1)
				* (teamdata.size() + 5) * (teamdata.size() + 3);
		double a2 = Math.pow(up2 / down2, 0.5);

		double u2 = 3 - 6.0 / (teamdata.size() + 1);

		System.out.println(dis.getSkewness() + "  " + a1);

		double U1 = dis.getSkewness() / a1;
		double U2 = (dis.getKurtosis() - u2) / a2;
		System.out.println(U1 + "  " + U2);
	}

	// 一元线性回归方程 array 0是x系数，1是常数项，2是r2
	public ArrayList<Double> LineCoefficient(String shortname, String season,
			String property_x, String property_y) throws RemoteException {
		double[] x = ChangeIntoDouble(GetPartLInfo(shortname, season, "yes"),
				property_x);
		double[] y = ChangeIntoDouble(GetPartLInfo(shortname, season, "yes"),
				property_y);

		ArrayList<Double> a = LineCoefficient.estimate(x, y);
		System.out.println(a.get(0) + " x + " + a.get(1) + "  r2= " + a.get(2));
		return a;
	}

	public void test() throws RemoteException {
		TeamLogic t = new TeamLogic();
		ArrayList<TeamLData> team = t.GetPartLInfo("unknown", "unknown", "yes");
		// ArrayList<TeamHData> team2=t.GetPartHInfo("unknown", "unknown",
		// "yes");
		for (int i = 0; i < team.size(); i++) {
			System.out.println(team.get(i).getShootEff());
		}
	}

	public static void main(String[] args) throws RemoteException {
		TeamLogic t = new TeamLogic(); // GetAllCompleteInfo
		// t.Var_a("HOU", "SAS", "yes", "13-14", "Bank");
		System.out
				.println("--------------------------------------------------");
		t.PersonR("PPG", "Winrate", "unknown", "unknown", "yes");
		//t.B3B4("unknown", "yes", "unknown", "Winrate");
		// t.LineCoefficient("unknown", "13-14", "TPEff", "ShootEff");
		// t.test();
		// ArrayList<TeamDescriptionStat>teams =t.getDescription("SAS",
		// "unknown", "yes");
		// System.out.println(teams.get(2).getType()+"  "+teams.get(2).getC_v());
		// MatchLogic m = new MatchLogic();

	}
}
