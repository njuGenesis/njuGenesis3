package data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import data.po.matchData.MatchDataSeason;
import data.po.matchData.MatchPlayer;
import data.po.matchData.MatchTeam;

public class MatchDb extends DataBaseLink {

	public static void main(String[] args) {
		MatchDb m = new MatchDb();
		//m.initializeTeamTable();
		m.clearMatchTable();
	}

	public void clearMatchTable() {
		System.out.println("clear team table start");
		operation("Truncate Table matchinfo");
		operation("Truncate Table matchplayer");
		operation("Truncate Table matchteam");
		System.out.println("clear team table end");
	}

	public void initializeTeamTable() {
		System.out.println("initialize Team Table start");
		// 比赛基本信息
		operation("create table matchinfo(" + "isseason varchar(255),"
				+ "date varchar(255)," + "season varchar(255),"
				+ "team varchar(255)," + "otherteam varchar(255),"
				+ "mathcid varchar(255)," + "point varchar(255)" + ")");

		// 比赛球员数据数据
		operation("create table matchplayer(" + "matchid varchar(255),"
				+ "team varchar(255)," + "playername varchar(255),"
				+ "time varchar(255)," + "points   varchar(255),"
				+ "isfirst varchar(255)," + "shooteff varchar(255),"
				+ "shootnumber varchar(255)," + "shooteffnumber varchar(255),"
				+ "tpeff varchar(255)," + "tpnumber varchar(255),"
				+ "tpeffnumber varchar(255)," + "fteff varchar(255),"
				+ "ftnumber varchar(255)," + "fteffnumber varchar(255),"
				+ "bank varchar(255)," + "bankoff varchar(255),"
				+ "bankdef varchar(255)," + "realeff varchar(255),"
				+ "assitnumber varchar(255)," + "stealnumber varchar(255),"
				+ "rejection varchar(255)," + "tos varchar(255),"
				+ "foul varchar(255)" + ")");

		// 比赛球队数据
		operation("create table matchteam(" + "matchid varchar(255),"
				+ "teamshortname varchar(255)," + "playernumber varchar(255),"
				+ "points   varchar(255)," + "shooteff varchar(255),"
				+ "shootnumber varchar(255)," + "shooteffnumber varchar(255),"
				+ "tpeff varchar(255)," + "tpnumber varchar(255),"
				+ "tpeffnumber varchar(255)," + "fteff varchar(255),"
				+ "ftnumber varchar(255)," + "fteffnumber varchar(255),"
				+ "bank varchar(255)," + "bankoff varchar(255),"
				+ "bankdef varchar(255)," + "realeff varchar(255),"
				+ "assitnumber varchar(255)," + "stealnumber varchar(255),"
				+ "rejection varchar(255)," + "tos varchar(255),"
				+ "foul varchar(255)" + ")");

	}

	// 插入比赛基本信息
	public void addmatchinfo(MatchDataSeason m) {
		try {
			operation("insert into matchinfo values(" + "'" + m.getisSeason()
					+ "'," + "'" + m.getDate() + "'," + "'" + m.getSeason()
					+ "'," + "'" + m.getTeam() + "'," + "'" + m.getOtherTeam()
					+ "'," + "'" + m.getMatchID() + "'," + "'" + m.getPoints()
					+ "'" + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 插入比赛球员信息
	public void addmatchplayer(MatchPlayer m) {
		try {
			operation("insert into matchplayer values(" + "'" + m.getMatchID()
					+ "'," + "'" + m.getTeam() + "'," + "'" + m.getPlayername()
					+ "'," + "'" + m.getTime() + "'," + "'" + m.getPoints()
					+ "'," + "'" + m.getIsFirst() + "'," + "'"
					+ m.getShootEff() + "'," + "'" + m.getShoot() + "'," + "'"
					+ m.getShootEffNumber() + "'," + "'" + m.getTPShootEff()
					+ "'," + "'" + m.getTPShoot() + "'," + "'"
					+ m.getTPShootEffNumber() + "'," + "'" + m.getFTShootEff()
					+ "'," + "'" + m.getFT() + "'," + "'"
					+ m.getFTShootEffNumber() + "'," + "'" + m.getBank() + "',"
					+ "'" + m.getBankOff() + "'," + "'" + m.getBankDef() + "',"
					+ "'" + m.getRealEff() + "'," + "'" + m.getAss() + "',"
					+ "'" + m.getSteal() + "'," + "'" + m.getRejection() + "',"
					+ "'" + m.getTo() + "'," + "'" + m.getFoul() + "'" + ")");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 插入比赛队伍信息
	public void addmatchteam(MatchTeam m) {
		try {
			operation("insert into matchteam values(" + "'" + m.getMatchID()
					+ "'," + "'" + m.getTeamShortName() + "'," + "'"
					+ m.getPlayerNumber() + "'," + "'" + m.getPoints() + "',"
					+ "'" + m.getShootEff() + "'," + "'" + m.getShoot() + "',"
					+ "'" + m.getShootEffNumber() + "'," + "'"
					+ m.getTPShootEff() + "'," + "'" + m.getTPShoot() + "',"
					+ "'" + m.getTPShootEffNumber() + "'," + "'"
					+ m.getFTShootEff() + "'," + "'" + m.getFT() + "'," + "'"
					+ m.getFTShootEffNumber() + "'," + "'" + m.getBank() + "',"
					+ "'" + m.getBankOff() + "'," + "'" + m.getBankDef() + "',"
					+ "'" + m.getRealEff() + "'," + "'" + m.getAss() + "',"
					+ "'" + m.getSteal() + "'," + "'" + m.getRejection() + "',"
					+ "'" + m.getTo() + "'," + "'" + m.getFoul() + "'" + ")");

		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 查询比赛信息
	public ArrayList<MatchDataSeason> getmatchinfo(String Team,
			String OtherTeam, String season, String Date, String isSeason,
			String MatchID) {
		if (season.equals("unknown")) {
			season = "*";
		}
		if (Team.equals("unknown")) {
			Team = "*";
		}
		if (OtherTeam.equals("unknown")) {
			OtherTeam = "*";
		}
		if (Date.equals("unknown")) {
			Date = "*";
		}
		if (MatchID.equals("unknown")) {
			MatchID = "*";
		}
		if (isSeason.equals("unknown")) {
			isSeason = "*";
		}

		ArrayList<MatchDataSeason> temp = new ArrayList<MatchDataSeason>();
		MatchDataSeason res = new MatchDataSeason();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchinfo where team = '"
							+ Team + "'" + "and season = '" + season + "'"
							+ "and otherteam = '" + OtherTeam + "'"
							+ "and date = '" + Date + "'" + "and isseason = '"
							+ isSeason + "'" + "and matchid = '" + MatchID
							+ "'");
			while (rs.next()) {
				res = new MatchDataSeason();
				setmatchinfo(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询比赛球员信息
	public ArrayList<MatchPlayer> getmatchplayeri(String MatchID,
			String playername, String isseason) {
		if (MatchID.equals("unknown")) {
			MatchID = "*";
		}
		if (playername.equals("unknown")) {
			playername = "*";
		}
		if (isseason.equals("unknown")) {
			isseason = "*";
		}

		ArrayList<MatchPlayer> temp = new ArrayList<MatchPlayer>();
		MatchPlayer res = new MatchPlayer();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchplayer where playername = '"
							+ playername
							+ "and matchid = '"
							+ MatchID
							+ "'"
							+ "and isseason = '" + isseason + "'");
			while (rs.next()) {
				res = new MatchPlayer();
				setmatchplayer(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 查询比赛队伍信息
	public ArrayList<MatchTeam> getmatchteam(String MatchID, String team,
			String isseason) {
		if (MatchID.equals("unknown")) {
			MatchID = "*";
		}
		if (team.equals("unknown")) {
			team = "*";
		}
		if (isseason.equals("unknown")) {
			isseason = "*";
		}

		ArrayList<MatchTeam> temp = new ArrayList<MatchTeam>();
		MatchTeam res = new MatchTeam();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchteam where team = '"
							+ team + "and matchid = '" + MatchID + "'"
							+ "and isseason = '" + isseason + "'");
			while (rs.next()) {
				res = new MatchTeam();
				setmatchteam(rs, res);
				temp.add(res);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	// 装载比赛基本信息
	private MatchDataSeason setmatchinfo(ResultSet rs, MatchDataSeason res) {
		try {
			/*
			 * if (res.getSeason().equals("*")) {
			 * res.setSeason(rs.getString("season")); } if
			 * (res.getTeam().equals("*")) { res.setTeam(rs.getString("team"));
			 * } if (res.getOtherTeam().equals("*")) {
			 * res.setOtherTeam(rs.getString("otherteam")); } if
			 * (res.getisSeason().equals("*")) {
			 * res.setisSeason(rs.getString("isseason")); } if
			 * (res.getDate().equals("*")) { res.setDate(rs.getString("date"));
			 * } if (res.getMatchID().equals("*")) {
			 * res.setMatchID(rs.getString("matchid")); }
			 */
			res.setisSeason(rs.getString("isseason"));
			res.setDate(rs.getString("date"));
			res.setisSeason(rs.getString("isseason"));
			res.setSeason(rs.getString("season"));
			res.setTeam(rs.getString("team"));
			res.setOtherTeam(rs.getString("otherteam"));
			res.setMatchID(rs.getString("matchid"));
			res.setPoint(res.getpointArray(rs.getString("point")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 装载比赛球员数据
	private MatchPlayer setmatchplayer(ResultSet rs, MatchPlayer res) {
		try {
			res.setMatchID(rs.getString("matchid"));
			res.setTeam(rs.getString("team"));
			res.setPlayername(rs.getString("playername"));
			res.setTime(rs.getString("time"));
			res.setPoints(rs.getString("points"));
			res.setIsFirst(rs.getString("isfirst"));

			res.setShootEff(rs.getString("shooteff"));
			res.setShoot(rs.getString("shoot"));
			res.setShootEffNumber(rs.getString("shooteffnumber"));
			res.setTPShootEff(rs.getString("tpshooteff"));
			res.setTPShoot(rs.getString("tpshoot"));
			res.setTPShootEffNumber(rs.getString("tpeffnumber"));
			res.setFTShootEff(rs.getString("ftshooteff"));
			res.setFT(rs.getString("ft"));
			res.setFTShootEffNumber(rs.getString("ftshooteffnumber"));
			res.setBank(rs.getString("bank"));
			res.setBankOff(rs.getString("bankoff"));
			res.setBankDef(rs.getString("bankdef"));
			res.setRealEff(rs.getString("realeff"));
			res.setAss(rs.getString("ass"));
			res.setSteal(rs.getString("steal"));
			res.setRejection(rs.getString("rejection"));
			res.setTo(rs.getString("tos"));
			res.setFoul(rs.getString("foul"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 装载比赛球队数据
	private MatchTeam setmatchteam(ResultSet rs, MatchTeam res) {
		try {
			res.setMatchID(rs.getString("matchid"));
			res.setTeamShortName(rs.getString("teamshootname"));
			res.setPlayerNumber(rs.getString("playernumber"));
			res.setPoints(rs.getString("points"));

			res.setShootEff(rs.getString("shooteff"));
			res.setShoot(rs.getString("shoot"));
			res.setShootEffNumber(rs.getString("shooteffnumber"));
			res.setTPShootEff(rs.getString("tpshooteff"));
			res.setTPShoot(rs.getString("tpshoot"));
			res.setTPShootEffNumber(rs.getString("tpeffnumber"));
			res.setFTShootEff(rs.getString("ftshooteff"));
			res.setFT(rs.getString("ft"));
			res.setFTShootEffNumber(rs.getString("ftshooteffnumber"));
			res.setBank(rs.getString("bank"));
			res.setBankOff(rs.getString("bankoff"));
			res.setBankDef(rs.getString("bankdef"));
			res.setRealEff(rs.getString("realeff"));

			res.setAss(rs.getString("ass"));
			res.setSteal(rs.getString("steal"));
			res.setRejection(rs.getString("rejection"));
			res.setTo(rs.getString("tos"));
			res.setFoul(rs.getString("foul"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
