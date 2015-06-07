package data.db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import data.po.teamData.TeamData_Avg_PO;

public class TeamDb extends DataBaseLink {

	public static void main(String[] args) {
		TeamDb t = new TeamDb();
		t.initializeTeamTable();
		//t.clearTeamTable();
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
	public TeamData_Avg_PO getbaseinfo(String shortname) throws RemoteException {

		TeamData_Avg_PO res = new TeamData_Avg_PO();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed()) {
			}
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teambaseinfo where shotrname = '"
							+ shortname + "'");
			res.setName(rs.getString("name"));
			res.setShortName(rs.getString("shortname"));
			res.setSeason(rs.getString("season"));
			res.setCity(rs.getString("city"));
			res.setEorW(rs.getString("eorw"));
			res.setArea(rs.getString("area"));
			res.setMainposition(rs.getString("mainposition"));
			res.setPlayers(rs.getString("players"));
			res.setBuildyear(rs.getInt("buildyear"));

			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	// 查询球队低阶数据
	public ArrayList<TeamData_Avg_PO> gettbi(String shortname, String season,
			String isseason) {
		if (season.equals("unknown")) {
			season = "*";
		}
		if (shortname.equals("unknown")) {
			shortname = "*";
		}
		if (isseason.equals("unknown")) {
			isseason = "*";
		}
		ArrayList<TeamData_Avg_PO> temp = new ArrayList<TeamData_Avg_PO>();
		TeamData_Avg_PO res = new TeamData_Avg_PO();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teambasedata where shortname = '"
							+ shortname
							+ "'"
							+ "and season = '"
							+ season
							+ "'"
							+ "and isseason = '" + isseason + "'");
			while (rs.next()) {
				res = new TeamData_Avg_PO();
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
	public ArrayList<TeamData_Avg_PO> getted(String shortname, String season,
			String isseason) {
		if (season.equals("unknown")) {
			season = "*";
		}
		if (shortname.equals("unknown")) {
			shortname = "*";
		}
		ArrayList<TeamData_Avg_PO> temp = new ArrayList<TeamData_Avg_PO>();
		TeamData_Avg_PO res = new TeamData_Avg_PO();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from teamexdata where shortname = '"
							+ shortname
							+ "'"
							+ "and season = '"
							+ season
							+ "'"
							+ "and isseason = '" + isseason + "'");
			while (rs.next()) {
				res = new TeamData_Avg_PO();
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
	public ArrayList<TeamData_Avg_PO> getotd(String shortname, String season,
			String isseason) {
		if (season.equals("unknown")) {
			season = "*";
		}
		if (shortname.equals("unknown")) {
			shortname = "*";
		}
		ArrayList<TeamData_Avg_PO> temp = new ArrayList<TeamData_Avg_PO>();
		TeamData_Avg_PO res = new TeamData_Avg_PO();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from otherteamdata where shortname = '"
							+ shortname
							+ "'"
							+ "and season = '"
							+ season
							+ "'"
							+ "and isseason = '" + isseason + "'");
			while (rs.next()) {
				res = new TeamData_Avg_PO();
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

	// *查询球队所有数据

	// 装载球队低阶数据
	private TeamData_Avg_PO settbi(ResultSet rs, TeamData_Avg_PO res) {
		try {
			if (res.getSeason().equals("*")) {
				res.setSeason(rs.getString("season"));
			}
			if (res.getShortName().equals("*")) {
				res.setShortName(rs.getString("shortname"));
			}
			res.setIsSeason(rs.getString("isseason"));
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
	private TeamData_Avg_PO setted(ResultSet rs, TeamData_Avg_PO res) {
		try {
			if (res.getSeason().equals("*")) {
				res.setSeason(rs.getString("season"));
			}
			if (res.getShortName().equals("*")) {
				res.setShortName(rs.getString("shortname"));
			}
			res.setIsSeason(rs.getString("isseason"));
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
	private TeamData_Avg_PO setotd(ResultSet rs, TeamData_Avg_PO res) {
		try {
			if (res.getSeason().equals("*")) {
				res.setSeason(rs.getString("season"));
			}
			if (res.getShortName().equals("*")) {
				res.setShortName(rs.getString("shortname"));
			}
			res.setIsSeason(rs.getString("isseason"));
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
