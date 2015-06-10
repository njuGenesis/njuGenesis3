package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sun.management.VMOption.Origin;

import data.db.TeamDb;
import data.po.TeamData_Avg_PO;
import data.po.playerData.PlayerDataPlayOff_Ad_Basic;
import data.po.playerData.PlayerDataPlayOff_Ad_Shoot;
import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Shoot;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import data.po.playerData.PlayerDetailInfo;
import data.po.teamData.TeamAdData;

public class TeatTeamdataCrawer {
	TeamDb teamDb= new TeamDb();
	TeamAdData help = new TeamAdData();
	ArrayList<String> tempInfo = new ArrayList<String>();
	String[] teamName = { "CHI", "CLE", "DET", "IND", "MIL", "BKN", "BOS",
			"NYK", "PHI", "TOR", "ATL", "CHA", "MIA", "ORL", "WAS", "DEN",
			"MIN", "OKC", "POR", "UTA", "GSW", "LAC", "LAL", "PHO", "SAC",
			"DAL", "HOU", "MEM", "NOH", "SAS" };// "CHH夏洛特黄蜂队 88-01" “SEA 西雅图超音速
												// 85-07” "NJN 新泽西篮网  85-11"
												// 85年最早不能更多...

	ArrayList<TeamData_Avg_PO> TeamDataSeason_Avgs = new ArrayList<TeamData_Avg_PO>();
	ArrayList<TeamData_Avg_PO> TeamDataPlayOff_Avgs = new ArrayList<TeamData_Avg_PO>();
	ArrayList<TeamData_Avg_PO> teamInfo = new ArrayList<TeamData_Avg_PO>();

	public static void main(String[] args) {
		TeatTeamdataCrawer t = new TeatTeamdataCrawer();
		t.readTeamfile();
	     t.initializeSeason_Avg();
		t.initializePlayOff_Avg();
		/*
		 * Document doc = null; try { // 所有数据都在stat_box里面 doc = Jsoup .connect(
		 * "http://www.stat-nba.com/team/stat_box_team.php?team=SAS&season=2014&col=pts&order=1&isseason=0"
		 * ) .header("User-Agent",
		 * "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0"
		 * ) .get();
		 * 
		 * Element e = doc.select("table").select("tbody").select("tr") .last();
		 * Elements e2 =e.select("td"); for (Element el : e2) { //if
		 * (!el.text().equals("")) System.out.println(el.text() + ""); } } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
	}

	public void initializeSeason_Avg() {
		Document doc = null;
		int season = 2014;
		String isseason = "1";
		String players = "";
		for (int i = 29; i >= 0; i--) {
			for (int templeseason = season; templeseason > 2004; templeseason--) {
				players = "";
				TeamData_Avg_PO one = new TeamData_Avg_PO();
				one.setShortName(teamName[i]);
				getTeamInfo(one);
				one.setSeason(Integer.toString(templeseason).substring(2) + "-"
						+ Integer.toString(templeseason + 1).substring(2));
				try {
					doc = Jsoup
							.connect(
									"http://www.stat-nba.com/team/stat_box_team.php?team="
											+ teamName[i] + "&season="
											+ Integer.toString(templeseason)
											+ "&col=pts&order=1&isseason="
											+ isseason)
							.timeout(10000)
							.header("User-Agent",
									"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
							.get();
					Element origin = doc.select("table").select("tbody")
							.select("tr").last();
					Elements e = doc.select("table").select("tbody")
							.select("tr").select("td");
					
					if (e.size() == 0) {
						continue;
					}
					System.out.println(teamName[i]
							+ "______________________________________" + i);
					int k = 0;// 记录“全队数据"出现的位置
					for (k = 0; k < e.size(); k++) {
						if (e.get(k).text().equals("全队数据")) {
							k++;
							break;
						}
						if ((k % 22) == 1) {
							players = players + e.get(k).text() + ";";
						}
					}
					// 本队数据初始化
					one.setIsSeason("yes");
					one.setPlayers(players);
					one.setMatchNumber(Double.valueOf(e.get(k).text()));
					k++;
					one.setWinMatch(Double.valueOf(e.get(k).text()));
					k += 2;

					if (e.get(k).text().contains("%"))
						one.setShootEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setShootEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setShootNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setTPEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setTPEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setTPNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setFTEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setFTEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setFTNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOffBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setDefBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setAssitNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setStealNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setRejectionPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setToPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setFoulPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setPPG(Double.valueOf(e.get(k).text()));
					k += 2;

					// 对手数据初始化-----------------------------------------------------------------------------------------------------
					e = origin.select("td");
					k = 1;
					one.setOtherMatchNumber(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherWinMatch(Double.valueOf(e.get(k).text()));
					k += 2;

					if (e.get(k).text().contains("%"))
						one.setOtherShootEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setOtherShootEffNumberPG(Double
							.valueOf(e.get(k).text()));
					k++;
					one.setOtherShootNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setOtherTPEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setOtherTPEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherTPNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setOtherFTEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setOtherFTEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherFTNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setOtherBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherOffBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherDefBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setOtherAssitNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherStealNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherRejectionPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherToPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherFoulPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherPPG(Double.valueOf(e.get(k).text()));

					TeamDataSeason_Avgs.add(one);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < TeamDataSeason_Avgs.size(); i++) {
			help.TeamRate(TeamDataSeason_Avgs.get(i));
		}
		/*System.out.println(TeamDataSeason_Avgs.size() + " ---------"
				+ TeamDataSeason_Avgs.get(10).getPlayers()
				+ TeamDataSeason_Avgs.get(10).getWinrate() + "--------"
				+ TeamDataSeason_Avgs.get(10).getOffBackBoardEff() + "------"
				+ TeamDataSeason_Avgs.get(10).getDef());*/
		writeIn(TeamDataSeason_Avgs);
	}

	public void initializePlayOff_Avg() {
		Document doc = null;
		int season = 2014;
		String isseason = "0";
		String players = "";
		TeamData_Avg_PO one = new TeamData_Avg_PO();
		for (int i = 29; i >= 0; i--) {
			for (int templeseason = season; templeseason > 2004; templeseason--) {
				players = "";
				one = new TeamData_Avg_PO();
				one.setShortName(teamName[i]);
				getTeamInfo(one);
				one.setSeason(Integer.toString(templeseason).substring(2) + "-"
						+ Integer.toString(templeseason + 1).substring(2));
				try {
					doc = Jsoup
							.connect(
									"http://www.stat-nba.com/team/stat_box_team.php?team="
											+ teamName[i] + "&season="
											+ Integer.toString(templeseason)
											+ "&col=pts&order=1&isseason="
											+ isseason)
							.timeout(10000)
							.header("User-Agent",
									"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0")
							.get();
					Element origin = doc.select("table").select("tbody")
							.select("tr").last();
					Elements e = doc.select("table").select("tbody")
							.select("tr").select("td");
					if (e.size() == 0) {
						continue;
					}
					System.out.println(teamName[i]
							+ "______________________________________" + i);
					int k = 0;// 记录“全队数据"出现的位置
					for (k = 0; k < e.size(); k++) {
						if (e.get(k).text().equals("全队数据")) {
							k++;
							break;
						}
						if ((k % 21) == 1) {
							players = players + e.get(k).text() + ";";
						}
					}
					// 本队数据初始化
					one.setPlayers(players);
					one.setIsSeason("no");
					one.setMatchNumber(Double.valueOf(e.get(k).text()));
					k++;
					one.setWinMatch(Double.valueOf(e.get(k).text()));
					k += 2;

					one.setShootEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setShootNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					// 这个奇葩的网站居然在季后赛没有命中率这列了....
					BigDecimal bg1 = new BigDecimal(
							(one.getShootEffNumberPG() / one.getShootNumberPG()));
					double ShootEff = bg1.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					one.setShootEff(ShootEff);

					if (e.get(k).text().contains("%"))
						one.setTPEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setTPEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setTPNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setFTEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setFTEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setFTNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOffBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setDefBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setAssitNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setStealNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setRejectionPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setToPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setFoulPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setPPG(Double.valueOf(e.get(k).text()));
					k += 2;

					// 对手数据初始化-----------------------------------------------------------------------------------------------------
					e = origin.select("td");
					k = 1;
					one.setOtherMatchNumber(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherWinMatch(Double.valueOf(e.get(k).text()));
					k += 2;

					one.setOtherShootEffNumberPG(Double
							.valueOf(e.get(k).text()));
					k++;
					one.setOtherShootNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					// 这个奇葩的网站居然在季后赛没有命中率这列了....
					BigDecimal bg = new BigDecimal(
							(one.getOtherShootEffNumberPG() / one.getOtherShootNumberPG()));
					double OtherShootEff = bg.setScale(2,
							BigDecimal.ROUND_HALF_UP).doubleValue();
					one.setOtherShootEff(OtherShootEff);

					if (e.get(k).text().contains("%"))
						one.setOtherTPEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setOtherTPEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherTPNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					if (e.get(k).text().contains("%"))
						one.setOtherFTEff(Double.valueOf(e.get(k).text()
								.substring(0, e.get(k).text().indexOf("%"))));
					k++;
					one.setOtherFTEffNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherFTNumberPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setOtherBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherOffBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherDefBackBoardPG(Double.valueOf(e.get(k).text()));
					k++;

					one.setOtherAssitNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherStealNumberPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherRejectionPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherToPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherFoulPG(Double.valueOf(e.get(k).text()));
					k++;
					one.setOtherPPG(Double.valueOf(e.get(k).text()));

					TeamDataPlayOff_Avgs.add(one);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		for (int i = 0; i < TeamDataPlayOff_Avgs.size(); i++) {
			help.TeamRate(TeamDataPlayOff_Avgs.get(i));
		}
		/*System.out.println(TeamDataPlayOff_Avgs.get(5).getSeason()
				+ "---------" + TeamDataPlayOff_Avgs.get(5).getPlayers()
				+ TeamDataPlayOff_Avgs.get(5).getWinrate() + "--------"
				+ TeamDataPlayOff_Avgs.get(5).getOffBackBoardEff() + "------"
				+ TeamDataPlayOff_Avgs.get(5).getDef());*/

		writeIn(TeamDataPlayOff_Avgs);
	}

	private void writeIn(ArrayList<TeamData_Avg_PO> t){
		for(int i=0;i<t.size();i++){
			teamDb.addotd(t.get(i));
			teamDb.addtbd(t.get(i));
			teamDb.addtbi(t.get(i));
			teamDb.addted(t.get(i));
		}
	}
	
	private void getTeamInfo(TeamData_Avg_PO team) {
		for (int i = 0; i < 30; i++) {
			if (team.getShortName().equals(teamInfo.get(i).getShortName())) {
				team.setArea(teamInfo.get(i).getArea());
				team.setCity(teamInfo.get(i).getCity());
				team.setName(teamInfo.get(i).getName());
				team.setEorW(teamInfo.get(i).getEorW());
				team.setMainposition(teamInfo.get(i).getMainposition());
				team.setBuildyear(teamInfo.get(i).getBuildyear());
				team.setArea(teamInfo.get(i).getArea());
				team.setArea(teamInfo.get(i).getArea());
				team.setArea(teamInfo.get(i).getArea());
			}
		}
	}

	private void readTeamfile() {
		String Teamfilename = "迭代一数据//teams//teams";
		TeamData_Avg_PO team = new TeamData_Avg_PO();

		try {
			File f = new File(Teamfilename);

			InputStreamReader fr = new InputStreamReader(
					new FileInputStream(f), "UTF-8");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String data = br.readLine();// 一次读入一行，直到读入null为文件结束

			while (data != null) {
				team = new TeamData_Avg_PO();

				while (data.contains("═")) {
					data = br.readLine();
				}
				String[] temp = data.split("║");
				String[] teamData = temp[1].split("│");
				team.setName(teamData[0].trim());
				team.setShortName(teamData[1].trim());
				if(team.getShortName().equals("NOP")){
					team.setShortName("NOH");
				}
				if(team.getShortName().equals("PHX")){
					team.setShortName("PHO");
				}
				team.setCity(teamData[2].trim());
				team.setEorW(teamData[3].trim());
				team.setArea(teamData[4].trim());
				team.setMainposition(teamData[5].trim());
				team.setBuildyear(Integer.parseInt(teamData[6].trim()));

				teamInfo.add(team);
				data = br.readLine();
				if (data.contains("═")) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}