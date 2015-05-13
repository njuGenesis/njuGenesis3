package bussinesslogic.team;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import test.data.TeamHighInfo;
import test.data.TeamHotInfo;
import test.data.TeamNormalInfo;
import assistance.GetFileData;
import bslogicService.TeamInfoService;
import bussinesslogic.match.MatchLogic;
import bussinesslogic.player.PlayerLogic;
import data.po.MatchDataPO;
import data.po.PlayerDataPO;
import data.po.TeamDataPO;
import data.team.TeamData;

public class TeamLogic implements TeamInfoService {
	private ArrayList<TeamDataPO> Teams;

	// 储存球队的球员名
	private void saveTeamPlayer(PlayerDataPO[] players) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getTeamName().equals("NOH")) {
				players[i].setTeamName("NOP");
			}
			for (int k = 0; k < Teams.size(); k++) {
				if (players[i].getTeamName()
						.equals(Teams.get(k).getShortName())
						&& (Teams.get(k).getSeason().equals(players[i]
								.getSeason()))) {
					if (Teams.get(k).getPlayers() == null) {
						Teams.get(k).setPlayers("");
					}
					if (!Teams.get(k).getPlayers()
							.contains(players[i].getName())) {
						Teams.get(k).setPlayers(
								Teams.get(k).getPlayers()
										+ players[i].getName() + ";");
					}
					break;
				}
			}
		}
	}

	private void upteamPlayer(MatchDataPO match, String season) {
		for (int i = 0; i < Teams.size(); i++) { // 遍历所有球队
			if (Teams.get(i).getPlayers() == null) {
				Teams.get(i).setPlayers("");
				continue;
			}
			if (Teams.get(i).getShortName().equals(match.getFirstteam())
					&& (Teams.get(i).getSeason().equals(season))) {
				for (int k = 0; k < match.getPlayers1().size(); k++) { // 遍历参加比赛的球
					if (Teams
							.get(i)
							.getPlayers()
							.contains(
									match.getPlayers1().get(k).getPlayername())) {
						Teams.get(i).setPlayers(
								Teams.get(i)
										.getPlayers()
										.replaceAll(
												match.getPlayers1().get(k)
														.getPlayername()
														+ ";", ""));
					}
				}
			} else if (Teams.get(i).getShortName()
					.equals(match.getSecondteam())
					&& (Teams.get(i).getSeason().equals(season))) {
				for (int k = 0; k < match.getPlayers2().size(); k++) {
					if (Teams
							.get(i)
							.getPlayers()
							.contains(
									match.getPlayers2().get(k).getPlayername())) {
						Teams.get(i).setPlayers(
								Teams.get(i)
										.getPlayers()
										.replaceAll(
												match.getPlayers2().get(k)
														.getPlayername()
														+ ";", ""));
					}
				}
			}
		}

		for (int i = 0; i < Teams.size(); i++) { // 遍历所有球队
			if (Teams.get(i).getShortName().equals(match.getFirstteam())
					&& (Teams.get(i).getSeason().equals(season))) {
				for (int k = 0; k < match.getPlayers1().size(); k++) { // 遍历参加比赛的球员
					if (!Teams
							.get(i)
							.getPlayers()
							.contains(
									match.getPlayers1().get(k).getPlayername())) {
						Teams.get(i).setPlayers(
								Teams.get(i).getPlayers()
										+ match.getPlayers1().get(k)
												.getPlayername() + ";");

					}
				}
			} else if (Teams.get(i).getShortName()
					.equals(match.getSecondteam())
					&& (Teams.get(i).getSeason().equals(season))) {
				for (int k = 0; k < match.getPlayers2().size(); k++) {
					if (!Teams
							.get(i)
							.getPlayers()
							.contains(
									match.getPlayers2().get(k).getPlayername())) {
						Teams.get(i).setPlayers(
								Teams.get(i).getPlayers()
										+ match.getPlayers2().get(k)
												.getPlayername() + ";");
					}
				}
			}
		}

	}

	// 球队名称，所在地等从teams文件里直接读取的信息
	public void initTeamData(String fileDir) {
		GetFileData teamFileReader = new GetFileData();
		Teams = teamFileReader.readTeamfile(fileDir); // 球队基本信息初始化
		// MatchLogic matchLogic = new MatchLogic();
		ArrayList<MatchDataPO> Matches = MatchLogic.matches; // 一个含有全部比赛基本信息的集合
		for (int i = 0; i < Matches.size(); i++) {
			if (Matches.get(i).isValid())
				calcuPoints(Matches.get(i).getPoints(), Matches.get(i)
						.getFirstteam(), Matches.get(i).getSecondteam(),
						Matches.get(i).getSeason());
		}
		for (int i = 0; i < Matches.size(); i++) {
			if (Matches.get(i).isValid())
				calcuRate(Matches.get(i), Matches.get(i).getFirstteam(),
						Matches.get(i).getSecondteam(), Matches.get(i)
								.getSeason());
		}
		/*PlayerLogic getPlayers = new PlayerLogic();
		saveTeamPlayer(getPlayers.getAllInfo("13-14"));
		saveTeamPlayer(getPlayers.getAllInfo("12-13"));
		saveTeamPlayer(getPlayers.getAllInfo(getPlayers.getLatestSeason()));*/

		/*
		 * saveTeamPlayer(getPlayers.getAllInfo("13-14"));
		 * saveTeamPlayer(getPlayers.getAllInfo("14-15"));
		 */
		TeamData add = new TeamData();
		add.WriteIn(Teams);
	}

	// 根据新添加的比赛更新球队信息
	public void updateTeamInfo(ArrayList<MatchDataPO> newMatches) {
		Teams = GetAllInfo(); // 获得之前的球队数据
		/*if (Teams.size() == 0) {
			GetFileData teamFileReader = new GetFileData();
			Teams = teamFileReader.readTeamfile(); // 球队基本信息初始化
		}*/
		for (int i = 0; i < newMatches.size(); i++) {
			if (newMatches.get(i).isValid())
				calcuPoints(newMatches.get(i).getPoints(), newMatches.get(i)
						.getFirstteam(), newMatches.get(i).getSecondteam(),
						newMatches.get(i).getSeason());
		}
		for (int i = 0; i < newMatches.size(); i++) {
			if (newMatches.get(i).isValid())
				calcuRate(newMatches.get(i), newMatches.get(i).getFirstteam(),
						newMatches.get(i).getSecondteam(), newMatches.get(i)
								.getSeason());
		}
		upteamPlayer(newMatches.get(0), (newMatches.get(0).getSeason()));
		TeamData add = new TeamData();
		add.WriteIn(Teams);
	}

	// 计算球队的比赛场数，总得分和均分
	private void calcuPoints(String points, String team1, String team2,
			String season) {
		String[] point = points.split("-");
		for (int i = 0; i < Teams.size(); i++) {
			if (Teams.get(i).getShortName().equals(team1)
					&&( Teams.get(i).getSeason().equals(season)||Teams.get(i).getSeason().equals("unknow"))) {
				if(Teams.get(i).getSeason().equals("unknow")){
					Teams.get(i).setSeason(season);
				}
				Teams.get(i).setMatchNumber(Teams.get(i).getMatchNumber() + 1); // 比赛场数+1
				if (point[0].compareTo(point[1]) >= 0) {
					Teams.get(i).setWinMatch(Teams.get(i).getWinMatch() + 1);
				}
				Teams.get(i).setWR(
						Teams.get(i).getWinMatch()
								/ Teams.get(i).getMatchNumber());// 胜率

				Teams.get(i).setPTS(
						Teams.get(i).getPTS() + Integer.parseInt(point[0])); // 球队1总得分

				Teams.get(i).setPPG(
						Teams.get(i).getPTS() / Teams.get(i).getMatchNumber());// 均分

				Teams.get(i).setLPS(
						Teams.get(i).getLPS() + Integer.parseInt(point[1])); // 球队1总失分

				Teams.get(i).setLPG(
						Teams.get(i).getLPS() / Teams.get(i).getMatchNumber());// 均失分

			} else if (Teams.get(i).getShortName().equals(team2)
					&& (Teams.get(i).getSeason().equals(season)||Teams.get(i).getSeason().equals("unknow"))) {
				if(Teams.get(i).getSeason().equals("unknow")){
					Teams.get(i).setSeason(season);
				}
				Teams.get(i).setMatchNumber(Teams.get(i).getMatchNumber() + 1); // 比赛场数+1
				if (point[0].compareTo(point[1]) <= 0) {
					Teams.get(i).setWinMatch(Teams.get(i).getWinMatch() + 1);
				}
				Teams.get(i).setWR(
						Teams.get(i).getWinMatch()
								/ Teams.get(i).getMatchNumber());
				Teams.get(i).setPTS(
						Teams.get(i).getPTS() + Integer.parseInt(point[1]));// 球队2总得分
				Teams.get(i).setPPG(
						Teams.get(i).getPTS() / Teams.get(i).getMatchNumber());// 均分

				Teams.get(i).setLPS(
						Teams.get(i).getLPS() + Integer.parseInt(point[0])); // 球队2总失分

				Teams.get(i).setLPG(
						Teams.get(i).getLPS() / Teams.get(i).getMatchNumber());// 均失分
			}
		}
	}

	// 计算和回合数相关的rate 以及根据比赛单场信息汇总球队信息
	private void calcuRate(MatchDataPO match, String team1, String team2,
			String season) {
		for (int i = 0; i < Teams.size(); i++) {
			if ((Teams.get(i).getShortName().equals(team1) && Teams.get(i)
					.getSeason().equals(season))
					|| (Teams.get(i).getShortName().equals(team2) && Teams
							.get(i).getSeason().equals(season))) {
				if (Teams.get(i).getShortName().equals(team1)) {
					Teams.get(i).setOff(
							Teams.get(i).getOff() + match.getTeamround1()); // 进攻，防守回合总数

					Teams.get(i).setDef(
							Teams.get(i).getDef() + match.getTeamround2());
					Teams.get(i).setDefPG(
							Teams.get(i).getDef()
									/ Teams.get(i).getMatchNumber());
					Teams.get(i).setOffPG(
							Teams.get(i).getOff()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setOffBackBoard(
							Teams.get(i).getOffBackBoard()
									+ match.getTeam1Off()); // 总前场篮板和对手前场篮板
					Teams.get(i).setOtherOffBoard(
							Teams.get(i).getOtherOffBoard()
									+ match.getTeam2Off());

					Teams.get(i).setDefBackBoard(
							Teams.get(i).getDefBackBoard()
									+ match.getTeam1Def()); // 总后场篮板和对手后场篮板
					Teams.get(i).setOtherDefBoard(
							Teams.get(i).getOtherDefBoard()
									+ match.getTeam2Def());

					Teams.get(i).setShootNumber(
							(int) (Teams.get(i).getShootNumber() + match
									.getShoot1())); // 投篮总数，场均数
					Teams.get(i).setShootNumberPG(
							Teams.get(i).getShootNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setShootEffNumber(
							Teams.get(i).getShootEffNumber()
									+ match.getShootEffNumber1()); // 投篮命中总数，场均数
					Teams.get(i).setShootEffNumberPG(
							Teams.get(i).getShootEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getShootNumber() > 0)) {
						Teams.get(i).setShootEff(
								Teams.get(i).getShootEffNumber()
										/ Teams.get(i).getShootNumber()); // 投篮进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i).setTPNumber(
							(int) (Teams.get(i).getTPNumber() + match
									.getTPShoot1())); // 总三分投篮书，场均数
					Teams.get(i).setTPNumberPG(
							Teams.get(i).getTPNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setTPEffNumber(
							Teams.get(i).getTPEffNumber()
									+ match.getTPShootEffNumber1());// 总三分命中书，场均数
					Teams.get(i).setTPEffNumberPG(
							Teams.get(i).getTPEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getTPNumber() > 0)) {
						Teams.get(i).setTPEff(
								Teams.get(i).getTPEffNumber()
										/ Teams.get(i).getTPNumber()); // 三分进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i)
							.setFTNumber(
									(int) (Teams.get(i).getFTNumber() + match
											.getFT1())); // 总罚球投篮书，场均
					Teams.get(i).setFTNumberPG(
							Teams.get(i).getFTNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setFTEffNumber(
							Teams.get(i).getFTEffNumber()
									+ match.getFTShootEffNumber1());// 总罚球命中书，场均数
					Teams.get(i).setFTEffNumberPG(
							Teams.get(i).getFTEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getFTNumber() > 0)) {
						Teams.get(i).setFTEff(
								Teams.get(i).getFTEffNumber()
										/ Teams.get(i).getFTNumber()); // 罚球进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i).setOffBackBoardPG(
							Teams.get(i).getOffBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setDefBackBoardPG(
							Teams.get(i).getDefBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setBackBoard(
							(int) (Teams.get(i).getBackBoard() + match
									.getTeam1bank())); // 总篮板数
					Teams.get(i).setBackBoardPG(
							Teams.get(i).getBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setAssitNumber(
							(int) (Teams.get(i).getAssitNumber() + match
									.getAss1())); // 总助攻数
					Teams.get(i).setAssitNumberPG(
							Teams.get(i).getAssitNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setStealNumber(
							(int) (Teams.get(i).getStealNumber() + match
									.getSteal1())); // 总抢断数
					Teams.get(i).setStealNumberPG(
							Teams.get(i).getStealNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setRejection(
							(int) (Teams.get(i).getRejection() + match
									.getRejection1())); // 总盖帽数
					Teams.get(i).setRejectionPG(
							Teams.get(i).getRejection()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setTo(
							(int) (Teams.get(i).getTo() + match.getTo1())); // 总失误数
					Teams.get(i).setToPG(
							Teams.get(i).getTo()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setFoul(
							(int) (Teams.get(i).getFoul() + match.getFoul1())); // 总犯规数
					Teams.get(i).setFoulPG(
							Teams.get(i).getFoul()
									/ Teams.get(i).getMatchNumber());

				} else if (Teams.get(i).getShortName().equals(team2)) {
					Teams.get(i).setOff(
							Teams.get(i).getOff() + match.getTeamround2()); // 进攻，防守回合总数
					Teams.get(i).setDef(
							Teams.get(i).getDef() + match.getTeamround1());
					Teams.get(i).setDefPG(
							Teams.get(i).getDef()
									/ Teams.get(i).getMatchNumber());
					Teams.get(i).setOffPG(
							Teams.get(i).getOff()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setOffBackBoard(
							Teams.get(i).getOffBackBoard()
									+ match.getTeam2Off()); // 总前场篮板和对手前场篮板
					Teams.get(i).setOtherOffBoard(
							Teams.get(i).getOtherOffBoard()
									+ match.getTeam1Off());

					Teams.get(i).setDefBackBoard(
							Teams.get(i).getDefBackBoard()
									+ match.getTeam2Def()); // 总后场篮板和对手后场篮板
					Teams.get(i).setOtherDefBoard(
							Teams.get(i).getOtherDefBoard()
									+ match.getTeam1Def());

					Teams.get(i).setShootNumber(
							(int) (Teams.get(i).getShootNumber() + match
									.getShoot2())); // 投篮总数，场均数
					Teams.get(i).setShootNumberPG(
							Teams.get(i).getShootNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setShootEffNumber(
							Teams.get(i).getShootEffNumber()
									+ match.getShootEffNumber2()); // 投篮命中总数，场均数
					Teams.get(i).setShootEffNumberPG(
							Teams.get(i).getShootEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getShootNumber() > 0)) {
						Teams.get(i).setShootEff(
								Teams.get(i).getShootEffNumber()
										/ Teams.get(i).getShootNumber()); // 投篮进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i).setTPNumber(
							(int) (Teams.get(i).getTPNumber() + match
									.getTPShoot2())); // 总三分投篮书，场均数
					Teams.get(i).setTPNumberPG(
							Teams.get(i).getTPNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setTPEffNumber(
							Teams.get(i).getTPEffNumber()
									+ match.getTPShootEffNumber2());// 总三分命中书，场均数
					Teams.get(i).setTPEffNumberPG(
							Teams.get(i).getTPEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getTPNumber() > 0)) {
						Teams.get(i).setTPEff(
								Teams.get(i).getTPEffNumber()
										/ Teams.get(i).getTPNumber()); // 三分进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i)
							.setFTNumber(
									(int) (Teams.get(i).getFTNumber() + match
											.getFT2())); // 总罚球投篮书，场均
					Teams.get(i).setFTNumberPG(
							Teams.get(i).getFTNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setFTEffNumber(
							Teams.get(i).getFTEffNumber()
									+ match.getFTShootEffNumber2());// 总罚球命中书，场均数
					Teams.get(i).setFTEffNumberPG(
							Teams.get(i).getFTEffNumber()
									/ Teams.get(i).getMatchNumber());

					if ((Teams.get(i).getFTNumber() > 0)) {
						Teams.get(i).setFTEff(
								Teams.get(i).getFTEffNumber()
										/ Teams.get(i).getFTNumber()); // 罚球进球率
					}
					// -------------------------------------------------------------------------------------------------------

					Teams.get(i).setOffBackBoardPG(
							Teams.get(i).getOffBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setDefBackBoardPG(
							Teams.get(i).getDefBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setBackBoard(
							(int) (Teams.get(i).getBackBoard() + match
									.getTeam2bank())); // 总篮板数
					Teams.get(i).setBackBoardPG(
							Teams.get(i).getBackBoard()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setAssitNumber(
							(int) (Teams.get(i).getAssitNumber() + match
									.getAss2())); // 总助攻数
					Teams.get(i).setAssitNumberPG(
							Teams.get(i).getAssitNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setStealNumber(
							(int) (Teams.get(i).getStealNumber() + match
									.getSteal2())); // 总抢断数
					Teams.get(i).setStealNumberPG(
							Teams.get(i).getStealNumber()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setRejection(
							(int) (Teams.get(i).getRejection() + match
									.getRejection2())); // 总盖帽数
					Teams.get(i).setRejectionPG(
							Teams.get(i).getRejection()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setTo(
							(int) (Teams.get(i).getTo() + match.getTo2())); // 总失误数
					Teams.get(i).setToPG(
							Teams.get(i).getTo()
									/ Teams.get(i).getMatchNumber());

					Teams.get(i).setFoul(
							(int) (Teams.get(i).getFoul() + match.getFoul2())); // 总犯规数
					Teams.get(i).setFoulPG(
							Teams.get(i).getFoul()
									/ Teams.get(i).getMatchNumber());

				}

				Teams.get(i).setStealEff(
						Teams.get(i).getStealNumber() * 100
								/ Teams.get(i).getDef()); // 抢断效率
				Teams.get(i).setAssistEff(
						Teams.get(i).getAssitNumber() * 100
								/ Teams.get(i).getOff()); // 助攻效率

				Teams.get(i).setOffPG(
						Teams.get(i).getOff() / Teams.get(i).getMatchNumber());// 进攻场均回合数

				Teams.get(i).setOffEff(
						Teams.get(i).getPTS() * 100 / Teams.get(i).getOff()); // 进攻、防守效率
				Teams.get(i).setDefEff(
						Teams.get(i).getLPS() * 100 / Teams.get(i).getDef());

				Teams.get(i).setOffBackBoardEff(
						Teams.get(i).getOffBackBoard()
								/ (Teams.get(i).getOffBackBoard() + Teams
										.get(i).getOtherDefBoard()));
				Teams.get(i).setDefBackBoardEff(
						Teams.get(i).getDefBackBoard()
								/ (Teams.get(i).getDefBackBoard() + Teams
										.get(i).getOtherOffBoard()));
				Teams.get(i).setBackBoardEff(
						Teams.get(i).getOffBackBoardEff()
								+ Teams.get(i).getDefBackBoardEff());
			}

		}
	}

	public ArrayList<TeamDataPO> GetAllInfo() {
		TeamData t = new TeamData();
		return t.GetAllInfo();
	}

	public TeamDataPO GetInfo(String name, String season) {
		TeamData t = new TeamData();
		return t.GetInfo(name, season);
	}

	public ArrayList<TeamDataPO> GetInfo(String name) {
		TeamData t = new TeamData();
		return t.GetInfo(name);
	}

	public ArrayList<TeamDataPO> GetInfoBySeason(String season) {
		TeamData t = new TeamData();
		return t.GetInfoBySeason(season);
	}

	public TeamDataPO GetBySN(String Shortname, String season) {
		TeamData t = new TeamData();
		return t.GetInfo(Shortname, season);
	}

	public ArrayList<TeamDataPO> GetBySN(String name) {
		TeamData t = new TeamData();
		return t.GetInfo(name);
	}

	public ArrayList<TeamDataPO> hotTeamSeason(String season, String property) {
		ArrayList<TeamDataPO> res = new ArrayList<TeamDataPO>();
		ArrayList<TeamDataPO> origin = GetInfoBySeason(season);
		TeamSortLogic.sortByDouble(origin, property);
		for (int i = 0; i < 5; i++) {
			res.add(origin.get(i));
		}
		return res;
	}

	public ArrayList<Double> getAvg() {
		ArrayList<TeamDataPO> allTeams = GetAllInfo();
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
		allTeams = GetInfoBySeason(season);
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

	//
	/*
	 * public double[] getAvg(String ){ ArrayList<TeamDataPO> teams. }
	 */
	// 自动化测试
	public void aotoTest(PrintStream out, boolean isAvg, boolean isHigh,
			String AllOrHot, int number, String sortCondition) {
		int size = number;
		ArrayList<TeamDataPO> res = new ArrayList<TeamDataPO>();
		ArrayList<TeamDataPO> temp = new ArrayList<TeamDataPO>();
		// all、hot的判断
		if (AllOrHot.startsWith("hot")) {
			temp = GetAllInfo();
			if (AllOrHot.contains("score")) {
				res = TeamSortLogic.sortByDouble(temp, "PPG");
			} else if (AllOrHot.contains("rebound")) {
				res = TeamSortLogic.sortByDouble(temp, "BackBoardPG");
			} else if (AllOrHot.contains("blockShot")) {
				res = TeamSortLogic.sortByDouble(temp, "RejectionPG");
			} else if (AllOrHot.contains("assist")) {
				res = TeamSortLogic.sortByDouble(temp, "AssitNumberPG");
			} else if (AllOrHot.contains("steal")) {
				res = TeamSortLogic.sortByDouble(temp, "StealNumberPG");
			} else if (AllOrHot.contains("foul")) {
				res = TeamSortLogic.sortByDouble(temp, "FoulPG");
			} else if (AllOrHot.contains("fault")) {
				res = TeamSortLogic.sortByDouble(temp, "ToPG");
			} else if (AllOrHot.contains("shot")) {
				res = TeamSortLogic.sortByDouble(temp, "ShootEff");
			} else if (AllOrHot.contains("three")) {
				res = TeamSortLogic.sortByDouble(temp, "TPEff");
			} else if (AllOrHot.contains("penalty")) {
				res = TeamSortLogic.sortByDouble(temp, "FTEff");
			} else if (AllOrHot.contains("defendRebound")) {
				res = TeamSortLogic.sortByDouble(temp, "DefBackBoardPG");
			} else if (AllOrHot.contains("offendRebound")) {
				res = TeamSortLogic.sortByDouble(temp, "OffBackBoardPG");
			}

			// System.out.println("write hot team");
			// out输出流写入热点球队数据
			
			if(size>res.size()){
				size=res.size();
			}
			for (int i = 0; i < size; i++) {
				TeamHotInfo reshot = new TeamHotInfo();
				reshot.setTeamName(res.get(i).getShortName());
				if(res.get(i).getEorW().equals("W")){
					res.get(i).setEorW("West");
				}
				if(res.get(i).getEorW().equals("E")){
					res.get(i).setEorW("Eest");
				}
				if (AllOrHot.contains("score")) {
					reshot.setField("score");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getPPG());
				} else if (AllOrHot.contains("rebound")) {
					reshot.setField("rebound");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getBackBoardPG());
				} else if (AllOrHot.contains("blockShot")) {
					reshot.setField("blockShot");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getRejectionPG());
				} else if (AllOrHot.contains("assist")) {
					reshot.setField("assist");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getAssitNumberPG());
				} else if (AllOrHot.contains("steal")) {
					reshot.setField("steal");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getStealNumberPG());
				} else if (AllOrHot.contains("foul")) {
					reshot.setField("foul");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getFoulPG());
				} else if (AllOrHot.contains("fault")) {
					reshot.setField("fault");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getToPG());
				} else if (AllOrHot.contains("shot")) {
					reshot.setField("shot");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getShootEff()*100);
				} else if (AllOrHot.contains("three")) {
					reshot.setField("three");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getTPEff()*100);
				} else if (AllOrHot.contains("penalty")) {
					reshot.setField("penalty");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getFTEff()*100);
				} else if (AllOrHot.contains("defendRebound")) {
					reshot.setField("defendRebound");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getDefBackBoardPG());
				} else if (AllOrHot.contains("offendRebound")) {
					reshot.setField("offendRebound");
					reshot.setLeague(res.get(i).getEorW());
					reshot.setValue(res.get(i).getOffBackBoardPG());
				}
				out.print(i+1);
				out.print("\n");
				out.print(reshot);
				out.print("\n");
			}
		}
		// all
		else {
			temp = GetAllInfo();
			String[] sorttemp = sortCondition.split(",");
			String[] st1 = sorttemp[0].split("\\.");
			String orderwords = "";
			boolean isAsc = true;
			if (st1[1].equals("desc")) {
				isAsc = false;
			} else {
				isAsc = true;
			}
			switch (st1[0]) {
			case "point":
				orderwords = "PPG";
				break;
			case "rebound":
				orderwords = "BackBoardPG";
				break;
			case "assist":
				orderwords = "AssitNumberPG";
				break;
			case "blockShot":
				orderwords = "RejectionPG";
				break;
			case "steal":
				orderwords = "StealNumberPG";
				break;
			case "foul":
				orderwords = "FoulPG";
				break;
			case "fault":
				orderwords = "ToPG";
				break;
			case "shot":
				orderwords = "ShootEff";
				break;
			case "three":
				orderwords = "TPEff";
				break;
			case "penalty":
				orderwords = "FTEff";
				break;
			case "defendRebound":
				orderwords = "DefBackBoardPG";
				break;
			case "offendRebound":
				orderwords = "OffBackBoardPG";
				break;
			case "winRate":
				orderwords = "WR";
				break;
			case "offendRound":
				orderwords = "OffPG";
				break;
			case "offendEfficient":
				orderwords = "OffEff";
				break;
			case "defendEfficient":
				orderwords = "DefEff";
				break;
			case "defendReboundEfficient":
				orderwords = "DefBackBoardEff";
				break;
			case "offendReboundEfficient":
				orderwords = "OffBackBoardEff";
				break;
			case "stealEfficient":
				orderwords = "StealEff";
				break;
			case "assistEfficient":
				orderwords = "AssistEff";
				break;
			}
			// 输出对象到输出流
			if (isHigh) {
				if (orderwords.equals("")) {
					orderwords = "WR";
				}
				res = TeamSortLogic.sortByDouble(temp, orderwords); // 排序
				if (isAsc) {
					res = TeamSortLogic.sortByDoubleAsc(res,orderwords);
				}
				if(size>res.size()){
					size=res.size();
				}
				if (isAvg) {
					for (int i = 0; i < size; i++) {
						TeamHighInfo reshigh = new TeamHighInfo();
						reshigh.setTeamName(res.get(i).getShortName());
						reshigh.setWinRate(res.get(i).getWR());
						reshigh.setOffendRound(res.get(i).getOffPG());
						reshigh.setOffendEfficient(res.get(i).getOffEff());
						reshigh.setDefendEfficient(res.get(i).getDefEff());
						reshigh.setOffendReboundEfficient(res.get(i)
								.getOffBackBoardEff());
						reshigh.setDefendReboundEfficient(res.get(i)
								.getDefBackBoardEff());
						reshigh.setAssistEfficient(res.get(i).getAssistEff());
						reshigh.setStealEfficient(res.get(i).getStealEff());
						out.print(i+1);
						out.print("\n");
						out.print(reshigh);
						out.print("\n");
					}
				} else {
					for (int i = 0; i < size; i++) {
						TeamHighInfo reshigh = new TeamHighInfo();
						reshigh.setTeamName(res.get(i).getShortName());
						reshigh.setWinRate(res.get(i).getWR());
						reshigh.setOffendRound(res.get(i).getOff());
						reshigh.setOffendEfficient(res.get(i).getOffEff());
						reshigh.setDefendEfficient(res.get(i).getDefEff());
						reshigh.setOffendReboundEfficient(res.get(i)
								.getOffBackBoardEff());
						reshigh.setDefendReboundEfficient(res.get(i)
								.getDefBackBoardEff());
						reshigh.setAssistEfficient(res.get(i).getAssistEff());
						reshigh.setStealEfficient(res.get(i).getStealEff());
						out.print(i+1);
						out.print("\n");
						out.print(reshigh);
						out.print("\n");
					}
				}
			} 
			//如果是normal数据
			else {
				TeamNormalInfo resnormal = new TeamNormalInfo();
				
				if (isAvg) {//如果是normal平均数据
					if (orderwords.equals("")) {
						orderwords = "PPG";
					}
					res = TeamSortLogic.sortByDouble(temp, orderwords); // 排序
					if (isAsc) {
						res = TeamSortLogic.sortByDoubleAsc(res,orderwords);
					}
					if(size>res.size()){
						size=res.size();
					}
					for (int i = 0; i < size; i++) {
						resnormal.setPoint(res.get(i).getPPG());
						resnormal.setRebound(res.get(i).getBackBoardPG());
						resnormal.setAssist(res.get(i).getAssitNumberPG());
						resnormal.setBlockShot(res.get(i).getRejectionPG());
						resnormal.setSteal(res.get(i).getStealNumberPG());
						resnormal.setFoul(res.get(i).getFoulPG());
						resnormal.setFault(res.get(i).getToPG());
						resnormal.setShot(res.get(i).getShootEff()*100);
						resnormal.setThree(res.get(i).getTPEff()*100);
						resnormal.setPenalty(res.get(i).getFTEff()*100);
						resnormal.setDefendRebound(res.get(i)
								.getDefBackBoardPG());
						resnormal.setOffendRebound(res.get(i)
								.getOffBackBoardPG());
						resnormal.setTeamName(res.get(i).getShortName());
						resnormal.setNumOfGame((int) res.get(i)
								.getMatchNumber());
						
						out.print(i+1);
						out.print("\n");
						out.print(resnormal);
						out.print("\n");
					}
				}
				else {//如果是normal总数据
					if (orderwords.equals("")) {
						orderwords = "PTS";
					}
					res = TeamSortLogic.sortByDouble(temp, orderwords); // 排序
					if (isAsc) {
						res = TeamSortLogic.sortByDoubleAsc(res,orderwords);
					}
					if(size>res.size()){
						System.out.print("-----"+res.size());
						size=res.size();
					}
					for (int i = 0; i < size; i++) {
						resnormal.setPoint(res.get(i).getPTS());
						resnormal.setRebound(res.get(i).getBackBoard());
						resnormal.setAssist(res.get(i).getAssitNumber());
						resnormal.setBlockShot(res.get(i).getRejection());
						resnormal.setSteal(res.get(i).getStealNumber());
						resnormal.setFoul(res.get(i).getFoul());
						resnormal.setFault(res.get(i).getTo());
						resnormal.setShot(res.get(i).getShootEff()*100);
						resnormal.setThree(res.get(i).getTPEff()*100);
						resnormal.setPenalty(res.get(i).getFTEff()*100);
						resnormal
								.setDefendRebound(res.get(i).getDefBackBoard());
						resnormal
								.setOffendRebound(res.get(i).getOffBackBoard());
						resnormal.setTeamName(res.get(i).getShortName());
						resnormal.setNumOfGame((int) res.get(i)
								.getMatchNumber());
					
						out.print(i+1);
						out.print("\n");
						out.print(resnormal);
						out.print("\n");
					}
				}
			}

			
		}

	}

	public static void main(String[] args) {
		TeamLogic t = new TeamLogic();
		ArrayList<TeamDataPO> a = t.GetAllInfo();
		System.out.println(a.size());
		for(int i=0;i<a.size();i++){
			System.out.println(i);
			System.out.println(a.get(i).getSeason()+" "+a.get(i).getPlayers()+" "+a.get(i).getShortName());
		}
		
		

	}

}
