package data.db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import data.po.TeamData_Avg_PO;
import data.po.teamData.OtherTeamData;
import data.po.teamData.TeamCompleteInfo;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamHData;
import data.po.teamData.TeamLData;

public class TeamDb extends DataBaseLink {

	public static void main(String[] args) throws RemoteException {
		//TeamDb t = new TeamDb();
		// t.initializeTeamTable();
		// t.clearTeamTable();

		/*
		 * ArrayList<TeamData_Avg_PO> res = t.getted("SAS","unknown","unknown");
		 * for(int i=0;i<res.size();i++){
		 * System.out.println(res.get(i).getOff()+
		 * "  "+res.get(i).getAssistEff()); }
		 */

		/*
		 * TeamLData team3 = t.getLSeasonAvg("13-14", "yes");
		 * System.out.println(team3.getPPG()); TeamLData team2 =
		 * t.getLSeasonAvg("13-14", "unknown");
		 * System.out.println(team2.getPPG()); TeamLData team1 =
		 * t.getLSeasonAvg("13-14", "no"); System.out.println(team1.getPPG());
		 */

	}

	public void clearTeamTable() {
		System.out.println("clear team table start");
		operation("Truncate Table teambaseinfo");
		operation("Truncate Table teambasedata");
		operation("Truncate Table teamexdata");
		operation("Truncate Table otherteamdata");
		System.out.println("clear team table end");
	}

	public void initializeTeamTable() {
		System.out.println("initialize Team Table start");
		// 球队基本信息
		operation("create table teambaseinfo(" + "name varchar(255),"
				+ "shortname varchar(255)," + "season varchar(255),"
				+ "isseason varchar(255)," + "city varchar(255),"
				+ "eorw varchar(255)," + "area varchar(255),"
				+ "mainposition varchar(255)," + "players varchar(255),"
				+ "buildyear int(7)" + ")");

		// 球队低阶数据
		operation("create table teambasedata(" + "shortname varchar(255),"
				+ "season varchar(255)," + "isseason varchar(255),"
				+ "matchNumber double(7,0)," + "winmatch double(7,0),"
				+ "winrate double(7,3)," + "ppg  double(7,1),"

				+ "shooteff double(7,3)," + "shootnumberpg double(7,1),"
				+ "shooteffnumberpg double(7,1),"

				+ "tpeff double(7,3)," + "tpnumberpg double(7,1),"
				+ "tpeffnumberpg double(7,1),"

				+ "fteff double(7,3)," + "ftnumberpg double(7,1),"
				+ "fteffnumberpg double(7,1),"

				+ "backboardpg double(7,1)," + "offbackboardpg double(7,1),"
				+ "defbackboardpg double(7,1),"

				+ "assitnumberpg double(7,1)," + "stealnumberpg double(7,1),"
				+ "rejectionpg double(7,1)," + "topg double(7,1),"
				+ "foulpg double(7,1)"

				+ ")");

		// 球队高阶数据
		operation("create table teamexdata(" + "shortname varchar(255),"
				+ "season varchar(255)," + "isseason varchar(255),"
				+ "off double(7,1)," + "def double(7,1),"
				+ "offeff double(7,3)," + "defeff double(7,3),"
				+ "offbackboardeff double(7,3)," + "backboardeff double(7,3),"
				+ "defbackboardeff double(7,3)," + "stealeff double(7,3),"
				+ "assisteff double(7,3)"

				+ ")");

		// 对手球队数据
		operation("create table otherteamdata(" + "shortname varchar(255),"
				+ "season varchar(255)," + "isseason varchar(255),"
				+ "matchNumber double(7,0)," + "winmatch double(7,0),"
				+ "winrate double(7,3)," + "ppg  double(7,1),"

				+ "shooteff double(7,3)," + "shootnumberpg double(7,1),"
				+ "shooteffnumberpg double(7,1),"

				+ "tpeff double(7,3)," + "tpnumberpg double(7,1),"
				+ "tpeffnumberpg double(7,1),"

				+ "fteff double(7,3)," + "ftnumberpg double(7,1),"
				+ "fteffnumberpg double(7,1),"

				+ "backboardpg double(7,1)," + "offbackboardpg double(7,1),"
				+ "defbackboardpg double(7,1),"

				+ "assitnumberpg double(7,1)," + "stealnumberpg double(7,1),"
				+ "rejectionpg double(7,1)," + "topg double(7,1),"
				+ "foulpg double(7,1)"

				+ ")");
	}

	public void update(TeamData_Avg_PO newteam) {
		operation("delete from teambaseinfo where shortname = " + "'"
				+ newteam.getShortName() + "' and season = '"
				+ newteam.getSeason() + "'");
		addtbi(newteam);
		operation("delete from teambasedata where shortname = " + "'"
				+ newteam.getShortName() + "' and season = '"
				+ newteam.getSeason() + "'");
		addtbd(newteam);
		operation("delete from teamexdata where shortname = " + "'"
				+ newteam.getShortName() + "' and season = '"
				+ newteam.getSeason() + "'");
		addted(newteam);
		operation("delete from otherteamdata where shortname = " + "'"
				+ newteam.getShortName() + "' and season = '"
				+ newteam.getSeason() + "'");
		addotd(newteam);

		/*
		 * operation("update teambaseinfo set name = '" + newteam.getName() +
		 * "', shortname = '" + newteam.getShortName() + "', season = '" +
		 * newteam.getSeason() + "', isseason = '" + newteam.getIsSeason() +
		 * "', city = '" + newteam.getCity() + "', eorw = '" + newteam.getEorW()
		 * + "', area = '" + newteam.getArea() + "', mainposition = '" +
		 * newteam.getMainposition() + "', players = '" + newteam.getPlayers() +
		 * "', buildyear = '" + newteam.getBuildyear() +
		 * "'  where shortname = '" + newteam.getShortName() +
		 * "' and season = '" + newteam.getSeason() + "'");
		 */

	}

	// 插入球队基本信息
	public void addtbi(TeamData_Avg_PO t) {
		try {
			operation("insert into teambaseinfo values(" + "'" + t.getName()
					+ "'," + "'" + t.getShortName() + "'," + "'"
					+ t.getSeason() + "'," + "'" + t.getIsSeason() + "'," + "'"
					+ t.getCity() + "'," + "'" + t.getEorW() + "'," + "'"
					+ t.getArea() + "'," + "'" + t.getMainposition() + "',"
					+ "'" + t.getPlayers() + "'," + "'" + t.getBuildyear()
					+ "'" + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入球队基础数据
	public void addtbd(TeamData_Avg_PO t) {
		try {
			operation("insert into teambasedata values(" + "'"
					+ t.getShortName() + "'," + "'" + t.getSeason() + "',"
					+ "'" + t.getIsSeason() + "'," + "'" + t.getMatchNumber()
					+ "'," + "'" + t.getWinMatch() + "'," + "'"
					+ t.getWinrate() + "'," + "'" + t.getPPG() + "'," + "'"
					+ t.getShootEff() + "'," + "'" + t.getShootNumberPG()
					+ "'," + "'" + t.getShootEffNumberPG() + "'," + "'"
					+ t.getTPEff() + "'," + "'" + t.getTPNumberPG() + "',"
					+ "'" + t.getTPEffNumberPG() + "'," + "'" + t.getFTEff()
					+ "'," + "'" + t.getFTNumberPG() + "'," + "'"
					+ t.getFTEffNumberPG() + "'," + "'" + t.getBackBoardPG()
					+ "'," + "'" + t.getOffBackBoardPG() + "'," + "'"
					+ t.getDefBackBoardPG() + "'," + "'" + t.getAssitNumberPG()
					+ "'," + "'" + t.getStealNumberPG() + "'," + "'"
					+ t.getRejectionPG() + "'," + "'" + t.getToPG() + "',"
					+ "'" + t.getFoulPG() + "'" + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入球队高阶数据
	public void addted(TeamData_Avg_PO t) {
		try {
			operation("insert into teamexdata values(" + "'" + t.getShortName()
					+ "'," + "'" + t.getSeason() + "'," + "'" + t.getIsSeason()
					+ "'," + "'" + t.getOff() + "'," + "'" + t.getDef() + "',"
					+ "'" + t.getOffEff() + "'," + "'" + t.getDefEff() + "',"
					+ "'" + t.getOffBackBoardEff() + "'," + "'"
					+ t.getBackBoardEff() + "'," + "'" + t.getDefBackBoardEff()
					+ "'," + "'" + t.getStealEff() + "'," + "'"
					+ t.getAssistEff() + "'" + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 插入对手球队数据
	public void addotd(TeamData_Avg_PO t) {
		try {
			operation("insert into otherteamdata values(" + "'"
					+ t.getShortName() + "'," + "'" + t.getSeason() + "',"
					+ "'" + t.getIsSeason() + "'," + "'"
					+ t.getOtherMatchNumber() + "'," + "'"
					+ t.getOtherWinMatch() + "'," + "'" + t.getOtherWinrate()
					+ "'," + "'" + t.getOtherPPG() + "'," + "'"
					+ t.getOtherShootEff() + "'," + "'"
					+ t.getOtherShootNumberPG() + "'," + "'"
					+ t.getOtherShootEffNumberPG() + "'," + "'"
					+ t.getOtherTPEff() + "'," + "'" + t.getOtherTPNumberPG()
					+ "'," + "'" + t.getOtherTPEffNumberPG() + "'," + "'"
					+ t.getOtherFTEff() + "'," + "'" + t.getOtherFTNumberPG()
					+ "'," + "'" + t.getOtherFTEffNumberPG() + "'," + "'"
					+ t.getOtherBackBoardPG() + "'," + "'"
					+ t.getOtherOffBackBoardPG() + "'," + "'"
					+ t.getOtherDefBackBoardPG() + "'," + "'"
					+ t.getOtherAssitNumberPG() + "'," + "'"
					+ t.getOtherStealNumberPG() + "'," + "'"
					+ t.getOtherRejectionPG() + "'," + "'" + t.getOtherToPG()
					+ "'," + "'" + t.getOtherFoulPG() + "'" + ")");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 查询球队基本信息
	public ArrayList<TeamBaseInfo> getbaseinfo(String shortname)
			throws RemoteException {
		ArrayList<TeamBaseInfo> temp = new ArrayList<TeamBaseInfo>();
		TeamBaseInfo res = new TeamBaseInfo();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teambaseinfo where (shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )");
			while (rs.next()) {
				res = new TeamBaseInfo();
				res.setName(rs.getString("name"));
				res.setIsSeason(rs.getString("isseason"));
				res.setShortName(rs.getString("shortname"));
				res.setSeason(rs.getString("season"));
				res.setCity(rs.getString("city"));
				res.setEorW(rs.getString("eorw"));
				res.setArea(rs.getString("area"));
				res.setMainposition(rs.getString("mainposition"));
				res.setPlayers(rs.getString("players"));
				res.setBuildyear(rs.getInt("buildyear"));
				temp.add(res);
			}
			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询球队低阶数据
	public ArrayList<TeamLData> getLData(String shortname, String season,
			String isseason) {

		ArrayList<TeamLData> temp = new ArrayList<TeamLData>();
		TeamLData res = new TeamLData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teambasedata where (shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");
			while (rs.next()) {
				res = new TeamLData();
				res.setShortName(shortname);
				res.setSeason(season);
				settbi(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询球队高阶数据
	public ArrayList<TeamHData> getHData(String shortname, String season,
			String isseason) {

		ArrayList<TeamHData> temp = new ArrayList<TeamHData>();
		TeamHData res = new TeamHData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teamexdata where (shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");
			while (rs.next()) {
				res = new TeamHData();
				res.setShortName(shortname);
				res.setSeason(season);
				setted(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询对手球队数据
	public ArrayList<OtherTeamData> getOtherTeamData(String shortname,
			String season, String isseason) {

		ArrayList<OtherTeamData> temp = new ArrayList<OtherTeamData>();
		OtherTeamData res = new OtherTeamData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from otherteamdata where (shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");

			while (rs.next()) {
				res = new OtherTeamData();
				res.setShortName(shortname);
				res.setSeason(season);
				setotd(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// *查询并装载球队所有数据
	public TeamCompleteInfo getAllTeamInfo(String shortname, String season,
			String isseason) throws RemoteException {
		TeamCompleteInfo temp = new TeamCompleteInfo();
		temp.setBaseinfo(getbaseinfo(shortname).get(0));
		temp.setLData(getLData(shortname, season, isseason).get(0));
		temp.setHData(getHData(shortname, season, isseason).get(0));
		temp.setOtherTeam(getOtherTeamData(shortname, season, isseason).get(0));

		return temp;
	}

	// 根据低阶数据中任意属性排序

	public ArrayList<TeamLData> getSortLData(String season, String property,
			String isseason) {

		ArrayList<TeamLData> temp = new ArrayList<TeamLData>();
		TeamLData res = new TeamLData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teambasedata where  (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown")
							+ " ) order by "
							+ property + " desc");
			while (rs.next()) {
				res = new TeamLData();
				res.setSeason(season);
				settbi(rs, res);
				temp.add(res);

			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询低阶数据的赛季平均数
	public TeamLData getLSeasonAvg(String shortname, String season,
			String isseason) {
		// TODO Auto-generated method stub
		TeamLData res = new TeamLData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select avg(winmatch) as winmatch,avg(matchnumber) as matchnumber,avg(winrate) as winrate,avg(ppg) as ppg,avg(shooteff) as shooteff,avg(shootnumberpg) as shootnumberpg,avg(shooteffnumberpg) as shooteffnumberpg,avg(tpeff) as tpeff,avg(tpnumberpg) as tpnumberpg,avg(tpeffnumberpg) as tpeffnumberpg,avg(fteff) as fteff,avg(ftnumberpg) as ftnumberpg,avg(fteffnumberpg) as fteffnumberpg,avg(backboardpg) as backboardpg,avg(offbackboardpg) as offbackboardpg,avg(defbackboardpg) as defbackboardpg,avg(assitnumberpg) as assitnumberpg,avg(stealnumberpg) as stealnumberpg,avg(rejectionpg) as rejectionpg,avg(topg) as topg,avg(foulpg) as foulpg "
							+ "from teambasedata  where (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");

			while (rs.next()) {
				res.setShortName("avg");
				res.setSeason(season);
				res.setIsseason(isseason);
				res.setMatchNumber(rs.getDouble("matchnumber"));
				res.setWinMatch(rs.getDouble("winmatch"));
				res.setWinrate(rs.getDouble("winrate"));
				res.setPPG(rs.getDouble("ppg"));
				res.setShootEff(rs.getDouble("shooteff"));
				res.setShootNumberPG(rs.getDouble("shootnumberpg"));
				res.setShootEffNumberPG(rs.getDouble("shooteffnumberpg"));
				res.setTPEff(rs.getDouble("tpeff"));
				res.setTPNumberPG(rs.getDouble("tpnumberpg"));
				res.setTPEffNumberPG(rs.getDouble("tpeffnumberpg"));
				res.setFTEff(rs.getDouble("fteff"));
				res.setFTNumberPG(rs.getDouble("ftnumberpg"));
				res.setFTEffNumberPG(rs.getDouble("fteffnumberpg"));
				res.setBackBoardPG(rs.getDouble("backboardpg"));
				res.setOffBackBoardPG(rs.getDouble("offbackboardpg"));
				res.setDefBackBoardPG(rs.getDouble("defbackboardpg"));
				res.setAssitNumberPG(rs.getDouble("assitnumberpg"));
				res.setStealNumberPG(rs.getDouble("stealnumberpg"));
				res.setRejectionPG(rs.getDouble("rejectionpg"));
				res.setToPG(rs.getDouble("topg"));
				res.setFoulPG(rs.getDouble("foulpg"));
			}

			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	// 查询高阶数据的联盟赛季平均数
	public TeamHData getHSeasonAvg(String shortname,String season, String isseason) {
		// TODO Auto-generated method stub
		TeamHData res = new TeamHData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select avg(off) as off,avg(def) as def,avg(offeff) as offeff,avg(defeff) as defeff,avg(offbackboardeff) as offbackboardeff,avg(backboardeff) as backboardeff,avg(defbackboardeff) as defbackboardeff,avg(stealeff) as stealeff,avg(assisteff) as assisteff "
							+ "from teamexdata  where (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");

			while (rs.next()) {
				res.setShortName("avg");
				res.setSeason(season);
				res.setIsseason(isseason);
				res.setOff(rs.getDouble("off"));
				res.setDef(rs.getDouble("def"));
				res.setOffEff(rs.getDouble("offeff"));
				res.setDefEff(rs.getDouble("defeff"));
				res.setOffBackBoardEff(rs.getDouble("offbackboardeff"));
				res.setBackBoardEff(rs.getDouble("backboardeff"));
				res.setDefBackBoardEff(rs.getDouble("defbackboardeff"));
				res.setStealEff(rs.getDouble("stealeff"));
				res.setAssistEff(rs.getDouble("assisteff"));
			}

			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	// 查询对手数据的联盟赛季平均数
	public OtherTeamData getOSeasonAvg(String shortname,String season, String isseason) {
		// TODO Auto-generated method stub
		OtherTeamData res = new OtherTeamData();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");

			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select avg(winmatch) as winmatch,avg(matchnumber) as matchnumber,avg(winrate) as winrate,avg(ppg) as ppg,avg(shooteff) as shooteff,avg(shootnumberpg) as shootnumberpg,avg(shooteffnumberpg) as shooteffnumberpg,avg(tpeff) as tpeff,avg(tpnumberpg) as tpnumberpg,avg(tpeffnumberpg) as tpeffnumberpg,avg(fteff) as fteff,avg(ftnumberpg) as ftnumberpg,avg(fteffnumberpg) as fteffnumberpg,avg(backboardpg) as backboardpg,avg(offbackboardpg) as offbackboardpg,avg(defbackboardpg) as defbackboardpg,avg(assitnumberpg) as assitnumberpg,avg(stealnumberpg) as stealnumberpg,avg(rejectionpg) as rejectionpg,avg(topg) as topg,avg(foulpg) as foulpg "
							+ "from otherteamdata  where (season = '"
							+ season
							+ "' or "
							+ season.equals("unknown")
							+ " )"
							+ "and( shortname = '"
							+ shortname
							+ "' or "
							+ shortname.equals("unknown")
							+ " )"
							+ "and( isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");

			while (rs.next()) {
				res.setShortName("avg");
				res.setSeason(season);
				res.setIsseason(isseason);
				res.setMatchNumber(rs.getDouble("matchnumber"));
				res.setWinMatch(rs.getDouble("winmatch"));
				res.setWinrate(rs.getDouble("winrate"));
				res.setPPG(rs.getDouble("ppg"));
				res.setShootEff(rs.getDouble("shooteff"));
				res.setShootNumberPG(rs.getDouble("shootnumberpg"));
				res.setShootEffNumberPG(rs.getDouble("shooteffnumberpg"));
				res.setTPEff(rs.getDouble("tpeff"));
				res.setTPNumberPG(rs.getDouble("tpnumberpg"));
				res.setTPEffNumberPG(rs.getDouble("tpeffnumberpg"));
				res.setFTEff(rs.getDouble("fteff"));
				res.setFTNumberPG(rs.getDouble("ftnumberpg"));
				res.setFTEffNumberPG(rs.getDouble("fteffnumberpg"));
				res.setBackBoardPG(rs.getDouble("backboardpg"));
				res.setOffBackBoardPG(rs.getDouble("offbackboardpg"));
				res.setDefBackBoardPG(rs.getDouble("defbackboardpg"));
				res.setAssitNumberPG(rs.getDouble("assitnumberpg"));
				res.setStealNumberPG(rs.getDouble("stealnumberpg"));
				res.setRejectionPG(rs.getDouble("rejectionpg"));
				res.setToPG(rs.getDouble("topg"));
				res.setFoulPG(rs.getDouble("foulpg"));
			}

			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	// 装载球队低阶数据
	private TeamLData settbi(ResultSet rs, TeamLData res) {
		try {
			res.setSeason(rs.getString("season"));
			res.setShortName(rs.getString("shortname"));
			res.setIsseason(rs.getString("isseason"));
			res.setMatchNumber(rs.getDouble("matchnumber"));
			res.setWinMatch(rs.getDouble("winmatch"));
			res.setWinrate(rs.getDouble("winrate"));
			res.setPPG(rs.getDouble("ppg"));
			res.setShootEff(rs.getDouble("shooteff"));
			res.setShootNumberPG(rs.getDouble("shootnumberpg"));
			res.setShootEffNumberPG(rs.getDouble("shooteffnumberpg"));
			res.setTPEff(rs.getDouble("tpeff"));
			res.setTPNumberPG(rs.getDouble("tpnumberpg"));
			res.setTPEffNumberPG(rs.getDouble("tpeffnumberpg"));
			res.setFTEff(rs.getDouble("fteff"));
			res.setFTNumberPG(rs.getDouble("ftnumberpg"));
			res.setFTEffNumberPG(rs.getDouble("fteffnumberpg"));
			res.setBackBoardPG(rs.getDouble("backboardpg"));
			res.setOffBackBoardPG(rs.getDouble("offbackboardpg"));
			res.setDefBackBoardPG(rs.getDouble("defbackboardpg"));
			res.setAssitNumberPG(rs.getDouble("assitnumberpg"));
			res.setStealNumberPG(rs.getDouble("stealnumberpg"));
			res.setRejectionPG(rs.getDouble("rejectionpg"));
			res.setToPG(rs.getDouble("topg"));
			res.setFoulPG(rs.getDouble("foulpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 装载球队高阶数据
	private TeamHData setted(ResultSet rs, TeamHData res) {
		try {
			res.setSeason(rs.getString("season"));
			res.setShortName(rs.getString("shortname"));
			res.setIsseason(rs.getString("isseason"));
			res.setOff(rs.getDouble("off"));
			res.setDef(rs.getDouble("def"));
			res.setOffEff(rs.getDouble("offeff"));
			res.setDefEff(rs.getDouble("defeff"));
			res.setOffBackBoardEff(rs.getInt("offbackboardeff"));
			res.setBackBoardEff(rs.getDouble("backboardeff"));
			res.setDefBackBoardEff(rs.getDouble("defbackboardeff"));
			res.setStealEff(rs.getDouble("stealeff"));
			res.setAssistEff(rs.getDouble("assisteff"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 装载对手球队数据
	private OtherTeamData setotd(ResultSet rs, OtherTeamData res) {
		try {
			res.setSeason(rs.getString("season"));
			res.setShortName(rs.getString("shortname"));
			res.setIsseason(rs.getString("isseason"));
			res.setMatchNumber(rs.getDouble("matchnumber"));
			res.setWinMatch(rs.getDouble("winmatch"));
			res.setWinrate(rs.getDouble("winrate"));
			res.setPPG(rs.getDouble("ppg"));
			res.setShootEff(rs.getDouble("shooteff"));
			res.setShootNumberPG(rs.getDouble("shootnumberpg"));
			res.setShootEffNumberPG(rs.getDouble("shooteffnumberpg"));
			res.setTPEff(rs.getDouble("tpeff"));
			res.setTPNumberPG(rs.getDouble("tpnumberpg"));
			res.setTPEffNumberPG(rs.getDouble("tpeffnumberpg"));
			res.setFTEff(rs.getDouble("fteff"));
			res.setFTNumberPG(rs.getDouble("ftnumberpg"));
			res.setFTEffNumberPG(rs.getDouble("fteffnumberpg"));
			res.setBackBoardPG(rs.getDouble("backboardpg"));
			res.setOffBackBoardPG(rs.getDouble("offbackboardpg"));
			res.setDefBackBoardPG(rs.getDouble("defbackboardpg"));
			res.setAssitNumberPG(rs.getDouble("assitnumberpg"));
			res.setStealNumberPG(rs.getDouble("stealnumberpg"));
			res.setRejectionPG(rs.getDouble("rejectionpg"));
			res.setToPG(rs.getDouble("topg"));
			res.setFoulPG(rs.getDouble("foulpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
