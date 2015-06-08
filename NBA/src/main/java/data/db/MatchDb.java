package data.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import data.po.matchData.MatchDataSeason;
import data.po.matchData.MatchPlayer;
import data.po.matchData.MatchTeam;

public class MatchDb extends DataBaseLink {

	ArrayList<String> seasonlist = new ArrayList<String>();

	public static void main(String[] args) throws SQLException {
		MatchDb m = new MatchDb();

		// m.clearMatchTable();

		ArrayList<MatchDataSeason> matches = m.getmatchinfo("SAS",
				"unknown", "13-14", "unknown", "yes", "unknown");

		System.out.println(matches.size());

		for (int i = 0; i < matches.size(); i++) {
			System.out.println(m.getmatchplayer(matches.get(i).getMatchID(),
					"unknown", "unknown", "no").size());
		}

	}

	public void clearMatchTable() {
		System.out.println("clear match table start");

		operation("Truncate Table matchinfo");
		operation("Truncate Table matchplayer");
		operation("Truncate Table matchteam");
		System.out.println("clear match table end");
	}

	public void initializeTeamTable() throws SQLException {
		System.out.println("initialize Team Table start");
		// 比赛基本信息

		operation("create table matchinfo (" + "isseason varchar(255),"
				+ "date varchar(255)," + "season varchar(255),"
				+ "team varchar(255)," + "otherteam varchar(255),"
				+ "matchid varchar(255)," + "point varchar(255)" + ")");

		// 比赛球员数据数据
		operation("create table matchplayer (" + "matchid varchar(255),"
				+ "isseason varchar(255)," + "team varchar(255),"
				+ "playername varchar(255)," + "time varchar(255),"
				+ "points   varchar(255)," + "isfirst varchar(255),"
				+ "shooteff varchar(255)," + "shootnumber varchar(255),"
				+ "shooteffnumber varchar(255)," + "tpeff varchar(255),"
				+ "tpnumber varchar(255)," + "tpeffnumber varchar(255),"
				+ "fteff varchar(255)," + "ftnumber varchar(255),"
				+ "fteffnumber varchar(255)," + "bank varchar(255),"
				+ "bankoff varchar(255)," + "bankdef varchar(255),"
				+ "realeff varchar(255)," + "assitnumber varchar(255),"
				+ "stealnumber varchar(255)," + "rejection varchar(255),"
				+ "tos varchar(255)," + "foul varchar(255)" + ")");

		// 比赛球队数据
		operation("create table matchteam (" + "matchid varchar(255),"
				+ "isseason varchar(255)," + "teamshortname varchar(255),"
				+ "playernumber varchar(255)," + "points   varchar(255),"
				+ "shooteff varchar(255)," + "shootnumber varchar(255),"
				+ "shooteffnumber varchar(255)," + "tpeff varchar(255),"
				+ "tpnumber varchar(255)," + "tpeffnumber varchar(255),"
				+ "fteff varchar(255)," + "ftnumber varchar(255),"
				+ "fteffnumber varchar(255)," + "bank varchar(255),"
				+ "bankoff varchar(255)," + "bankdef varchar(255),"
				+ "realeff varchar(255)," + "assitnumber varchar(255),"
				+ "stealnumber varchar(255)," + "rejection varchar(255),"
				+ "tos varchar(255)," + "foul varchar(255)" + ")");

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
					+ "'," + "'" + m.getIsseason() + "'," + "'" + m.getTeam()
					+ "'," + "'" + m.getPlayername() + "'," + "'" + m.getTime()
					+ "'," + "'" + m.getPoints() + "'," + "'" + m.getIsFirst()
					+ "'," + "'" + m.getShootEff() + "'," + "'" + m.getShoot()
					+ "'," + "'" + m.getShootEffNumber() + "'," + "'"
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

	// 插入比赛队伍信息
	public void addmatchteam(MatchTeam m) {
		try {
			operation("insert into matchteam values(" + "'" + m.getMatchID()
					+ "'," + "'" + m.getIsseason() + "'," + "'"
					+ m.getTeamShortName() + "'," + "'" + m.getPlayerNumber()
					+ "'," + "'" + m.getPoints() + "'," + "'" + m.getShootEff()
					+ "'," + "'" + m.getShoot() + "'," + "'"
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

	// 查询比赛信息
	public ArrayList<MatchDataSeason> getmatchinfo(String Team,
			String OtherTeam, String season, String Date, String isSeason,
			String MatchID) {
		ArrayList<MatchDataSeason> temp = new ArrayList<MatchDataSeason>();
		MatchDataSeason res = new MatchDataSeason();
		try {

			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchinfo where  (team = '"
							+ Team + "' or " + Team.equals("unknown") + " )"
							+ "and (otherteam = '" + OtherTeam + "' or "
							+ OtherTeam.equals("unknown") + " )"
							+ "and (date = '" + Date + "' or "
							+ Date.equals("unknown") + " )"
							+ "and (isseason = '" + isSeason + "' or "
							+ isSeason.equals("unknown") + " )"
							+ "and (matchid = '" + MatchID + "' or "
							+ MatchID.equals("unknown") + " )"
							+ "and( season = '" + season + "' or "
							+ season.equals("unknown") + " )");
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
	public ArrayList<MatchPlayer> getmatchplayer(String MatchID,
			String playername, String team, String isseason) {

		ArrayList<MatchPlayer> temp = new ArrayList<MatchPlayer>();
		MatchPlayer res = new MatchPlayer();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchplayer where (matchid = '"
							+ MatchID
							+ "' or "
							+ MatchID.equals("unknown")
							+ " )"
							+ "and (playername = '"
							+ playername
							+ "' or "
							+ playername.equals("unknown")
							+ " )"
							+ "and (isseason = '"
							+ isseason
							+ "' or "
							+ isseason.equals("unknown") + " )");
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

		ArrayList<MatchTeam> temp = new ArrayList<MatchTeam>();
		MatchTeam res = new MatchTeam();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchteam where (matchid = '"
							+ MatchID + "' or " + MatchID.equals("unknown")
							+ " )" + "and (teamshortname = '" + team + "' or "
							+ team.equals("unknown") + " )"
							+ "and (isseason = '" + isseason + "' or "
							+ isseason.equals("unknown") + " )");
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

	// 查询并装载双方球员数据
	public void getAllMatchPlayer(MatchDataSeason match, String MatchID,
			String team, String otherteam) {

		ArrayList<MatchPlayer> temp1 = new ArrayList<MatchPlayer>();
		ArrayList<MatchPlayer> temp2 = new ArrayList<MatchPlayer>();
		MatchPlayer res = new MatchPlayer();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchplayer where (matchid = '"
							+ MatchID
							+ "'and (team = '"
							+ team
							+ "' or team = '" + otherteam + "'))");
			while (rs.next()) {
				res = new MatchPlayer();
				setmatchplayer(rs, res);
				if (res.getTeam().equals(team))
					temp1.add(res);
				else if (res.getTeam().equals(otherteam)) {
					temp2.add(res);
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		match.setTeamPlayer(temp1);
		match.setOtherteamPlayer(temp2);

	}

	// 查询并装载双方球队数据
	public void getAllMatchTeam(MatchDataSeason match, String MatchID,
			String team, String otherteam) {

		MatchTeam temp = new MatchTeam();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "");
			if (!con.isClosed())
				System.out.println("success");
			Statement st = con.createStatement();
			ResultSet rs = st
					.executeQuery("select * from matchteam where (matchid = '"
							+ MatchID + "'and (teamshortname = '" + team
							+ "' or teamshortname = '" + otherteam + "'))");
			while (rs.next()) {
				temp = new MatchTeam();
				setmatchteam(rs, temp);
				if (temp.getTeamShortName().equals(team))
					match.setTeamdata(temp);
				else if ((temp.getTeamShortName().equals(otherteam))) {
					match.setTeamdata(temp);
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 装载比赛基本信息
	private MatchDataSeason setmatchinfo(ResultSet rs, MatchDataSeason res) {
		try {
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
			res.setIsseason(rs.getString("isseason"));
			res.setTeam(rs.getString("team"));
			res.setPlayername(rs.getString("playername"));
			res.setTime(rs.getString("time"));
			res.setPoints(rs.getString("points"));
			res.setIsFirst(rs.getString("isfirst"));

			res.setShootEff(rs.getString("shooteff"));
			res.setShoot(rs.getString("shootnumber"));
			res.setShootEffNumber(rs.getString("shooteffnumber"));
			res.setTPShootEff(rs.getString("tpeff"));
			res.setTPShoot(rs.getString("tpnumber"));
			res.setTPShootEffNumber(rs.getString("tpeffnumber"));
			res.setFTShootEff(rs.getString("fteff"));
			res.setFT(rs.getString("ftnumber"));
			res.setFTShootEffNumber(rs.getString("fteffnumber"));
			res.setBank(rs.getString("bank"));
			res.setBankOff(rs.getString("bankoff"));
			res.setBankDef(rs.getString("bankdef"));
			res.setRealEff(rs.getString("realeff"));
			res.setAss(rs.getString("assitnumber"));
			res.setSteal(rs.getString("stealnumber"));
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
			res.setIsseason(rs.getString("isseason"));
			res.setTeamShortName(rs.getString("teamshortname"));
			res.setPlayerNumber(rs.getString("playernumber"));
			res.setPoints(rs.getString("points"));
			res.setShootEff(rs.getString("shooteff"));
			res.setShoot(rs.getString("shootnumber"));
			res.setShootEffNumber(rs.getString("shooteffnumber"));
			res.setTPShootEff(rs.getString("tpeff"));
			res.setTPShoot(rs.getString("tpnumber"));
			res.setTPShootEffNumber(rs.getString("tpeffnumber"));
			res.setFTShootEff(rs.getString("fteff"));
			res.setFT(rs.getString("ftnumber"));
			res.setFTShootEffNumber(rs.getString("fteffnumber"));
			res.setBank(rs.getString("bank"));
			res.setBankOff(rs.getString("bankoff"));
			res.setBankDef(rs.getString("bankdef"));
			res.setRealEff(rs.getString("realeff"));

			res.setAss(rs.getString("assitnumber"));
			res.setSteal(rs.getString("stealnumber"));
			res.setRejection(rs.getString("rejection"));
			res.setTo(rs.getString("tos"));
			res.setFoul(rs.getString("foul"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
