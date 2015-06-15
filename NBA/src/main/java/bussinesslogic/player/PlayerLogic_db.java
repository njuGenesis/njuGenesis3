package bussinesslogic.player;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

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
		case "detail":
			ArrayList<PlayerDetailInfo> temp8 = getAlldetail(season);
			for(int i = 0;i<temp8.size();i++){
				if ((firstc(temp8.get(i), firstc)) && (namekeys(temp8.get(i), namekey))
						&& (position(temp8.get(i), position))
						&& (union(temp8.get(i).getId(), union, season))) {
					
							res.add(temp8.get(i).getId());
						}
					
					
				
			}
			return res;
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
				if ((p.getName().toLowerCase().contains(namekeys.toLowerCase()))||(p.getNameCn().contains(namekeys))) {
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
			//System.out.println(id+";"+temp.size());
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
	
	public ArrayList<PlayerDescriptionStat> getPlayerStat(int id,boolean isAvg,boolean isSeason){
		ArrayList<PlayerDescriptionStat> res = new ArrayList<PlayerDescriptionStat>();
		ArrayList<Double> thper = new ArrayList<Double>();
		ArrayList<Double> shootper = new ArrayList<Double>();
		ArrayList<Double> ftper = new ArrayList<Double>();
		ArrayList<Double> assist = new ArrayList<Double>();
		ArrayList<Double> steal = new ArrayList<Double>();
		ArrayList<Double> rebound = new ArrayList<Double>();
		ArrayList<Double> block = new ArrayList<Double>();
		ArrayList<Double> miss = new ArrayList<Double>();
		ArrayList<Double> foul = new ArrayList<Double>();
		ArrayList<Double> time = new ArrayList<Double>();
		ArrayList<Double> pts = new ArrayList<Double>();
		try{
		if(isSeason){
			if(isAvg){
				ArrayList<PlayerDataSeason_Avg_Basic> temp = gets_a_b(id);
				for(int i = 0;i<temp.size();i++){
					try{
					thper.add(Double.valueOf(temp.get(i).getThper().replaceAll("%", "")));
					}catch(Exception e){
						thper.add(0.0);
					}
					try{
					shootper.add(Double.valueOf(temp.get(i).getShootper().replaceAll("%", "")));
					}catch(Exception e){
						shootper.add(0.0);
					}
					try{
					ftper.add(Double.valueOf(temp.get(i).getFtper().replaceAll("%", "")));
					}catch(Exception e){
						ftper.add(0.0);
					}
					try{
					assist.add(Double.valueOf(temp.get(i).getAssist()));
					}catch(Exception e){
						assist.add(0.0);
					}
					try{
					steal.add(Double.valueOf(temp.get(i).getSteal()));
					}catch(Exception e){
						steal.add(0.0);
					}
					try{
					rebound.add(Double.valueOf(temp.get(i).getBackbound()));
					}catch(Exception e){
						rebound.add(0.0);
					}
					try{
					block.add(Double.valueOf(temp.get(i).getRejection()));
					}catch(Exception e){
						block.add(0.0);
					}
					try{
					miss.add(Double.valueOf(temp.get(i).getMiss()));
					}catch(Exception e){
						miss.add(0.0);
					}
					try{
					foul.add(Double.valueOf(temp.get(i).getFoul()));
					}catch(Exception e){
						foul.add(0.0);
					}
					try{
					time.add(Double.valueOf(temp.get(i).getTime()));
					}catch(Exception e){
						time.add(0.0);
					}
					try{
					pts.add(Double.valueOf(temp.get(i).getPts()));
					}catch(Exception e){
						pts.add(0.0);
					}
				}
			}
			else{
				ArrayList<PlayerDataSeason_Tot_Basic> temp = gets_t_b(id);
				for(int i = 0;i<temp.size();i++){
					try{
					thper.add(Double.valueOf(temp.get(i).getThper().replaceAll("%", "")));
					}catch(Exception e){
						thper.add(0.0);
					}
					try{
					shootper.add(Double.valueOf(temp.get(i).getShootper().replaceAll("%", "")));
					}catch(Exception e){
						shootper.add(0.0);
					}
					try{
					ftper.add(Double.valueOf(temp.get(i).getFtper().replaceAll("%", "")));
					}catch(Exception e){
						ftper.add(0.0);
					}
					try{
					assist.add(Double.valueOf(temp.get(i).getAssist()));
					}catch(Exception e){
						assist.add(0.0);
					}
					try{
					steal.add(Double.valueOf(temp.get(i).getSteal()));
					}catch(Exception e){
						steal.add(0.0);
					}
					try{
					rebound.add(Double.valueOf(temp.get(i).getBackbound()));
					}catch(Exception e){
						rebound.add(0.0);
					}
					try{
					block.add(Double.valueOf(temp.get(i).getRejection()));
					}catch(Exception e){
						block.add(0.0);
					}
					try{
					miss.add(Double.valueOf(temp.get(i).getMiss()));
					}catch(Exception e){
						miss.add(0.0);
					}
					try{
					foul.add(Double.valueOf(temp.get(i).getFoul()));
					}catch(Exception e){
						foul.add(0.0);
					}
					try{
					time.add(Double.valueOf(temp.get(i).getTime()));
					}catch(Exception e){
						time.add(0.0);
					}
					try{
					pts.add(Double.valueOf(temp.get(i).getPts()));
					}catch(Exception e){
						pts.add(0.0);
					}
				}
			}
		}
		else{
			if(isAvg){
				ArrayList<PlayerDataPlayOff_Avg_Basic> temp = getp_a_b(id);
				for(int i = 0;i<temp.size();i++){
					try{
					thper.add(Double.valueOf(temp.get(i).getThper().replaceAll("%", "")));
					}catch(Exception e){
						thper.add(0.0);
					}
					try{
					shootper.add(Double.valueOf(temp.get(i).getShootper().replaceAll("%", "")));
					}catch(Exception e){
						shootper.add(0.0);
					}
					try{
					ftper.add(Double.valueOf(temp.get(i).getFtper().replaceAll("%", "")));
					}catch(Exception e){
						ftper.add(0.0);
					}
					try{
					assist.add(Double.valueOf(temp.get(i).getAssist()));
					}catch(Exception e){
						assist.add(0.0);
					}
					try{
					steal.add(Double.valueOf(temp.get(i).getSteal()));
					}catch(Exception e){
						steal.add(0.0);
					}
					try{
					rebound.add(Double.valueOf(temp.get(i).getBackbound()));
					}catch(Exception e){
						rebound.add(0.0);
					}
					try{
					block.add(Double.valueOf(temp.get(i).getRejection()));
					}catch(Exception e){
						block.add(0.0);
					}
					try{
					miss.add(Double.valueOf(temp.get(i).getMiss()));
					}catch(Exception e){
						miss.add(0.0);
					}
					try{
					foul.add(Double.valueOf(temp.get(i).getFoul()));
					}catch(Exception e){
						foul.add(0.0);
					}
					try{
					time.add(Double.valueOf(temp.get(i).getTime()));
					}catch(Exception e){
						time.add(0.0);
					}
					try{
					pts.add(Double.valueOf(temp.get(i).getPts()));
					}catch(Exception e){
						pts.add(0.0);
					}
				}
			}
			else{
				ArrayList<PlayerDataPlayOff_Tot_Basic> temp = getp_t_b(id);
				for(int i = 0;i<temp.size();i++){
					try{
					thper.add(Double.valueOf(temp.get(i).getThper().replaceAll("%", "")));
					}catch(Exception e){
						thper.add(0.0);
					}
					try{
					shootper.add(Double.valueOf(temp.get(i).getShootper().replaceAll("%", "")));
					}catch(Exception e){
						shootper.add(0.0);
					}
					try{
					ftper.add(Double.valueOf(temp.get(i).getFtper().replaceAll("%", "")));
					}catch(Exception e){
						ftper.add(0.0);
					}
					try{
					assist.add(Double.valueOf(temp.get(i).getAssist()));
					}catch(Exception e){
						assist.add(0.0);
					}
					try{
					steal.add(Double.valueOf(temp.get(i).getSteal()));
					}catch(Exception e){
						steal.add(0.0);
					}
					try{
					rebound.add(Double.valueOf(temp.get(i).getBackbound()));
					}catch(Exception e){
						rebound.add(0.0);
					}
					try{
					block.add(Double.valueOf(temp.get(i).getRejection()));
					}catch(Exception e){
						block.add(0.0);
					}
					try{
					miss.add(Double.valueOf(temp.get(i).getMiss()));
					}catch(Exception e){
						miss.add(0.0);
					}
					try{
					foul.add(Double.valueOf(temp.get(i).getFoul()));
					}catch(Exception e){
						foul.add(0.0);
					}
					try{
					time.add(Double.valueOf(temp.get(i).getTime()));
					}catch(Exception e){
						time.add(0.0);
					}
					try{
					pts.add(Double.valueOf(temp.get(i).getPts()));
					}catch(Exception e){
						pts.add(0.0);
					}
				}
			}
			
		}
		PlayerDescriptionStat thper_stat = calculateStat(id,"thper",thper);
		PlayerDescriptionStat shootper_stat = calculateStat(id,"shootper",shootper);
		PlayerDescriptionStat ftper_stat = calculateStat(id,"ftper",ftper);
		PlayerDescriptionStat assist_stat = calculateStat(id,"assist",assist);
		PlayerDescriptionStat steal_stat = calculateStat(id,"steal",steal);
		PlayerDescriptionStat rebound_stat = calculateStat(id,"rebound",rebound);
		PlayerDescriptionStat block_stat = calculateStat(id,"block",block);
		PlayerDescriptionStat miss_stat = calculateStat(id,"miss",miss);
		PlayerDescriptionStat foul_stat = calculateStat(id,"foul",foul);
		PlayerDescriptionStat time_stat = calculateStat(id,"time",time);
		PlayerDescriptionStat pts_stat = calculateStat(id,"pts",pts);
		
		res.add(thper_stat);
		res.add(shootper_stat);
		res.add(ftper_stat);
		res.add(assist_stat);
		res.add(steal_stat);
		res.add(rebound_stat);
		res.add(block_stat);
		res.add(miss_stat);
		res.add(foul_stat);
		res.add(time_stat);
		res.add(pts_stat);
		return res;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	private PlayerDescriptionStat calculateStat(int id,String key,ArrayList<Double> orgin){
		PlayerDescriptionStat res = new PlayerDescriptionStat();
		res.setId(id);
		res.setType(key);
		Collections.sort(orgin);
		double avg = calculateAvg(orgin);
		double median = calculateMedian(orgin);
		double range = calculateRange(orgin);
		double var = calculateVar(orgin,avg);
		double varq = calculateVarq(var);
		double c_v = calculatec_v(avg,varq);
		double sk = calculateSK(orgin,avg);
		double kur = calculateKur(orgin,avg);
		
		BigDecimal bg = new BigDecimal(avg);
		avg =bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg1 = new BigDecimal(median);
		median =bg1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg2 = new BigDecimal(range);
		range =bg2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg3 = new BigDecimal(var);
		var =bg3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg4 = new BigDecimal(varq);
		varq =bg4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg5 = new BigDecimal(c_v);
		c_v =bg5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg6 = new BigDecimal(sk);
		sk =bg6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bg7 = new BigDecimal(kur);
		kur =bg7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		res.setAvg(avg);
		res.setMedian(median);
		res.setRange(range);
		res.setVar(var);
		res.setVarq(varq);
		res.setC_v(c_v);
		res.setSkewness(sk);
		res.setKurtosis(kur);
		return res;
	}
	private Double calculateAvg(ArrayList<Double> orgin){
		double res = 0;
		double sum = 0;
		if(orgin.size()!=0){
		for(int i = 0;i<orgin.size();i++){
			sum = sum + orgin.get(i);
		}
		res = sum/orgin.size();
		}
		return res;
	}
	private Double calculateMedian(ArrayList<Double> orgin){
		double res = 0;
		Collections.sort(orgin);
		if(orgin.size()%2==0){
			int i1 = (orgin.size()-1)/2;
			int i2 = (orgin.size()+1)/2;
			res = (orgin.get(i1)+orgin.get(i2))/2;
			
		}
		else{
			int i = orgin.size()/2;
			res = orgin.get(i);
		}
		return res;
	}
	private Double calculateRange(ArrayList<Double> orgin){
		double res = 0;
		Collections.sort(orgin);
		if(orgin.size()!=0){
		res = orgin.get(orgin.size()-1)-orgin.get(0);
		}
		return res;
	}
	private Double calculateVar(ArrayList<Double> orgin,double avg){
		double res = 0;
		double sum = 0;
		if(orgin.size()!=0){
		for(int i = 0;i<orgin.size();i++){
			sum = sum + Math.pow((orgin.get(i)-avg),2);
		}
		res = sum/orgin.size();
		}
		return res;
	}
	private Double calculateVarq(double var){
		return Math.sqrt(var);
	}
	private Double calculatec_v(double avg,double varq){
		double res = 0;
		if(avg!=0){
			res  = 100*varq/avg;
		}
		return res;
	}
	private Double calculateSK(ArrayList<Double> orgin,double avg){
		double res = 0;
		double three_k = calculateK_Center(orgin,avg,3);
		double two_k = calculateK_Center(orgin,avg,2);
		if(two_k!=0){
		 res = three_k/Math.pow(two_k, 1.5);
		}
		return res;
	}
	private Double calculateKur(ArrayList<Double> orgin,double avg){
		double res = 0;
		double four_k = calculateK_Center(orgin,avg,4);
		double two_k = calculateK_Center(orgin,avg,2);
		if(res!=0){
		 res =  four_k/Math.pow(two_k, 2);
		}
		 return res;
	}
	private Double calculateK_Center(ArrayList<Double> orgin,double avg,int k){
		double res = 0;
		double sum = 0;
		if(orgin.size()!=0){
		for(int i = 0;i<orgin.size();i++){
			sum = sum + Math.pow(orgin.get(i)-avg, k);
		}
		res = sum/orgin.size();
		}
		return res;
	}
}
