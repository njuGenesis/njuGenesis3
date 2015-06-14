package bussinesslogic.player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import crawler.TestCrawlerByJsoup;
import data.db.PlayerDb;
import data.po.playerData.*;

public class PlayerLogic_db {
	PlayerDb pdb = new PlayerDb();
	TestCrawlerByJsoup tc = new TestCrawlerByJsoup();

	
	
	public void update(String name,boolean isseason,String season){
		int id = pdb.updateClear(name, isseason, season);
		if(isseason){
		tc.initializePlayerSeason(id,id+1);
		}
		else{
		tc.initializePlayerPlayOff(id, id+1);
		}
	}
	// ----------获得基本信息
	
	
	public ArrayList<PlayerDetailInfo> getAlldetail(String season)throws RemoteException{
		ArrayList<PlayerDetailInfo> res =pdb.getAlldetail(season);
		return res;
	}
	
	public PlayerDetailInfo getdetail(int id) throws RemoteException {
		PlayerDetailInfo res = new PlayerDetailInfo();
		res = pdb.getdetail(id);
		return res;
	}

	public String getLatestTeam(int id)throws RemoteException{
		int latest = -1;
		String res = "";
		ArrayList<PlayerDataSeason_Avg_Basic> temp = pdb.gets_a_b(id);
		for(int i = 0;i<temp.size();i++){
			int tempseason = Integer.valueOf(temp.get(i).getSeason().substring(0,2));
			if((tempseason>=0)&&(tempseason<=14)){
				if((latest>=0)&&(latest<=14)){
					if(tempseason>=latest){
						latest = tempseason;
						res = temp.get(i).getTeam();
					}
				}
				else{
					latest = tempseason;
					res = temp.get(i).getTeam();
				}
			}
			else{
				if((latest<=14)&&(latest>=0)){
					
				}
				else{
					if(tempseason>=latest){
						latest = tempseason;
						res = temp.get(i).getTeam();
					}
				}
			}
		}
		
		return res;
	}
	
	public ArrayList<String> getTeambyId(int id, String season)
			throws RemoteException {
		return pdb.getTeambyId(id, season);
	}

	public int getIDbyName(String name, String team) throws RemoteException {
		return pdb.getIDbyName(name, team);
	}

	// ----------获得单个球员的所有数据
	public ArrayList<PlayerDataSeason_Avg_Basic> gets_a_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Avg_Basic> res = new ArrayList<PlayerDataSeason_Avg_Basic>();
		res = pdb.gets_a_b(id);
		return res;
	}

	public ArrayList<PlayerDataSeason_Tot_Basic> gets_t_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Tot_Basic> res = new ArrayList<PlayerDataSeason_Tot_Basic>();
		res = pdb.gets_t_b(id);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Basic> gets_ad_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Basic> res = new ArrayList<PlayerDataSeason_Ad_Basic>();
		res = pdb.gets_ad_b(id);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Shoot> gets_ad_s(int id)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Shoot> res = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		res = pdb.gets_ad_s(id);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Avg_Basic> getp_a_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Avg_Basic> res = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		res = pdb.getp_a_b(id);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Tot_Basic> getp_t_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Tot_Basic> res = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		res = pdb.getp_t_b(id);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Ad_Basic> getp_ad_b(int id)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Basic> res = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		res = pdb.getp_ad_b(id);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getp_ad_s(int id)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Shoot> res = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		res = pdb.getp_ad_s(id);
		return res;
	}

	// ------------根据id和season获得所有数据
	public ArrayList<PlayerDataSeason_Avg_Basic> gets_a_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Avg_Basic> res = new ArrayList<PlayerDataSeason_Avg_Basic>();
		res = pdb.gets_a_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Tot_Basic> gets_t_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Tot_Basic> res = new ArrayList<PlayerDataSeason_Tot_Basic>();
		res = pdb.gets_t_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Basic> gets_ad_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Basic> res = new ArrayList<PlayerDataSeason_Ad_Basic>();
		res = pdb.gets_ad_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Shoot> gets_ad_s(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Shoot> res = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		res = pdb.gets_ad_s(id, season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Avg_Basic> getp_a_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Avg_Basic> res = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		res = pdb.getp_a_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Tot_Basic> getp_t_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Tot_Basic> res = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		res = pdb.getp_t_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Ad_Basic> getp_ad_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Basic> res = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		res = pdb.getp_ad_b(id, season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getp_ad_s(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Shoot> res = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		res = pdb.getp_ad_s(id, season);
		return res;
	}

	// -----------获取某一赛季的所有数据
	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getAllp_ad_s(String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Shoot> res = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		res = pdb.getAllp_ad_s(season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Ad_Basic> getAllp_ad_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Ad_Basic> res = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		res = pdb.getAllp_ad_b(season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Tot_Basic> getAllp_t_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Tot_Basic> res = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		res = pdb.getAllp_t_b(season);
		return res;
	}

	public ArrayList<PlayerDataPlayOff_Avg_Basic> getAllp_a_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataPlayOff_Avg_Basic> res = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		res = pdb.getAllp_a_b(season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Avg_Basic> getAlls_a_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Avg_Basic> res = new ArrayList<PlayerDataSeason_Avg_Basic>();
		res = pdb.getAlls_a_b(season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Tot_Basic> getAlls_t_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Tot_Basic> res = new ArrayList<PlayerDataSeason_Tot_Basic>();
		res = pdb.getAlls_t_b(season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Basic> getAlls_ad_b(String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Basic> res = new ArrayList<PlayerDataSeason_Ad_Basic>();
		res = pdb.getAlls_ad_b(season);
		return res;
	}

	public ArrayList<PlayerDataSeason_Ad_Shoot> getAlls_ad_s(String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Ad_Shoot> res = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		res = pdb.getAlls_ad_s(season);
		return res;
	}

	// ----------筛选---
	public ArrayList<Integer> selectByTag(String season, String tablename,
			String firstc, String namekey, String position, String union)
			throws RemoteException {
		ArrayList<Integer> res = new ArrayList<Integer>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		switch (tablename) {
		case "s_a_b":
			ArrayList<PlayerDataSeason_Avg_Basic> temp0 = getAlls_a_b(season);
			for (int i = 0; i < temp0.size(); i++) {
				temp.add(temp0.get(i).getId());
			}
			break;
		case "s_t_b":
			ArrayList<PlayerDataSeason_Tot_Basic> temp1 = getAlls_t_b(season);
			for (int i = 0; i < temp1.size(); i++) {
				temp.add(temp1.get(i).getId());
			}
			break;
		case "s_ad_b":
			ArrayList<PlayerDataSeason_Ad_Basic> temp2 = getAlls_ad_b(season);
			for (int i = 0; i < temp2.size(); i++) {
				temp.add(temp2.get(i).getId());
			}
			break;
		case "s_ad_s":
			ArrayList<PlayerDataSeason_Ad_Shoot> temp3 = getAlls_ad_s(season);
			for (int i = 0; i < temp3.size(); i++) {
				temp.add(temp3.get(i).getId());
			}
			break;
		case "p_a_b":
			ArrayList<PlayerDataPlayOff_Avg_Basic> temp4 = getAllp_a_b(season);
			for (int i = 0; i < temp4.size(); i++) {
				temp.add(temp4.get(i).getId());
			}
			break;
		case "p_t_b":
			ArrayList<PlayerDataPlayOff_Tot_Basic> temp5 = getAllp_t_b(season);
			for (int i = 0; i < temp5.size(); i++) {
				temp.add(temp5.get(i).getId());
			}
			break;
		case "p_ad_b":
			ArrayList<PlayerDataPlayOff_Ad_Basic> temp6 = getAllp_ad_b(season);
			for (int i = 0; i < temp6.size(); i++) {
				temp.add(temp6.get(i).getId());
			}
			break;
		case "p_ad_s":
			ArrayList<PlayerDataPlayOff_Ad_Shoot> temp7 = getAllp_ad_s(season);
			for (int i = 0; i < temp7.size(); i++) {
				temp.add(temp7.get(i).getId());
			}
			break;
		}

		PlayerDetailInfo tempinfo = new PlayerDetailInfo();
		int laest = 0;
		for (int i = 0; i < temp.size(); i++) {
			
			tempinfo = getdetail(temp.get(i));
			
			if ((firstc(tempinfo, firstc)) && (namekeys(tempinfo, namekey))
					&& (position(tempinfo, position))
					&& (union(tempinfo.getId(), union, season))) {
				if(res.size()==0){
					laest=temp.get(i);
					res.add(temp.get(i));
				}
				else{
					if(temp.get(i)==laest){
						
					}
					else{
						laest=temp.get(i);
						res.add(temp.get(i));
					}
				}
				
			}
			
		}

		return res;
	}

	// ------热点---
	public String[] getHotPlayerDaily(String key) {
		String[] res = tc.getHotPlayerDaily(key);
		return res;
	}

	public String[] getHotPlayerSeason(String key) {
		String[] res = tc.getHotPlayerSeason(key);
		return res;
	}

	public String[] getProgressPlayer(String key) {
		String[] res = tc.getProgressPlayer(key);
		return res;
	}

	//-----------联盟平均-----
	public ArrayList<Double> getAvgOfAll(String season,boolean isSeason)throws Exception{
		ArrayList<Double> res = new ArrayList<Double>();
		if(isSeason){
			res = pdb.getSeasonAvg(season);
			
		}
		else{
			res = pdb.getPlayOffAvg(season);
		}
		return res;
	}
	// ===============筛选方法
	private boolean firstc(PlayerDetailInfo p, String firstc) {
		if (firstc == "null") {
			return true;
		} else {
			try {
				if (p.getName().toLowerCase().startsWith(firstc.toLowerCase())) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}

	private boolean namekeys(PlayerDetailInfo p, String namekeys) {
		if (namekeys == "null") {
			return true;
		} else {
			try {
				if (p.getName().toLowerCase().contains(namekeys.toLowerCase())) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
	}

	private boolean position(PlayerDetailInfo p, String position) {
		if (position == "null") {
			return true;
		} else {
			try {
				if (p.getPosition().contains(position)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

	}

	private boolean union(int id, String union, String season)
			throws RemoteException {
		if (union == "null") {
			return true;
		} else {
			ArrayList<String> temp = getTeambyId(id, season);
			System.out.println(id+";"+temp.size());
			for (int i = 0; i < temp.size(); i++) {
				String teamname = temp.get(i).split(";")[1];
				try {

					if (union.equals("Southeast")) {

						if ((teamname.equals("亚特兰大老鹰"))
								|| (teamname.equals("夏洛特黄蜂"))
								|| (teamname.equals("迈阿密热火"))
								|| (teamname.equals("奥兰多魔术"))
								|| (teamname.equals("华盛顿奇才"))) {
							return true;
						}
						return false;

					} else if (union.equals("Central")) {

						if ((teamname.equals("克里夫兰骑士"))
								|| (teamname.equals("芝加哥公牛"))
								|| (teamname.equals("底特律活塞"))
								|| (teamname.equals("印第安纳步行者"))
								|| (teamname.equals("密尔沃基雄鹿"))) {
							return true;
						}
						return false;
					} else if (union.equals("Atlantic")) {

						if ((teamname.equals("布鲁克林篮网"))
								|| (teamname.equals("波士顿凯尔特人"))
								|| (teamname.equals("纽约尼克斯"))
								|| (teamname.equals("费城76人"))
								|| (teamname.equals("多伦多猛龙"))) {
							return true;
						}
						return false;
					} else if (union.equals("Southwest")) {

						if ((teamname.equals("达拉斯小牛"))
								|| (teamname.equals("休斯顿火箭"))
								|| (teamname.equals("孟菲斯灰熊"))
								|| (teamname.equals("新奥尔良鹈鹕"))
								|| (teamname.equals("圣安东尼奥马刺"))) {
							return true;
						}
						return false;
					} else if (union.equals("Northwest")) {

						if ((teamname.equals("金州勇士"))
								|| (teamname.equals("洛杉矶快船"))
								|| (teamname.equals("洛杉矶湖人"))
								|| (teamname.equals("菲尼克斯太阳"))
								|| (teamname.equals("萨克拉门托国王"))) {
							return true;
						}
						return false;
					} else if (union.equals("Pacific")) {

						if ((teamname.equals("丹佛掘金"))
								|| (teamname.equals("明尼苏达森林狼"))
								|| (teamname.equals("俄克拉荷马雷霆"))
								|| (teamname.equals("波特兰开拓者"))
								|| (teamname.equals("犹他爵士"))) {
							return true;
						}
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
			return false;
		}

	}
	// ===============筛选方法
}
