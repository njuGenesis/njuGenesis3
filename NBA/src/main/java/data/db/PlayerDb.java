package data.db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import data.po.playerData.PlayerDataPlayOff_Ad_Basic;
import data.po.playerData.PlayerDataPlayOff_Ad_Shoot;
import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Shoot;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import data.po.playerData.PlayerDetailInfo;
import dataService.PlayerDataService;

public class PlayerDb  extends DataBaseLink implements PlayerDataService{
	//====================================database table
	public void clearPlayerTable(){
		System.out.println("clear player table start");
		operation("Truncate Table p_s_a_b");
		operation("Truncate Table p_s_t_b");
		operation("Truncate Table p_s_ad_b");
		operation("Truncate Table p_s_ad_s");
		operation("Truncate Table p_p_a_b");
		operation("Truncate Table p_p_t_b");
		operation("Truncate Table p_p_ad_b");
		operation("Truncate Table p_p_ad_s");
		operation("Truncate Table p_detail");
		System.out.println("clear player table end");
	}
	public void clearPlayerTableForUpdate(int id,boolean isseason){
		if(isseason){
		operation("delete from  p_s_a_b where id = '"+id+"'");
		operation("delete from  p_s_t_b where id = '"+id+"'");
		operation("delete from  p_s_ad_b where id = '"+id+"'");
		operation("delete from  p_s_ad_s where id = '"+id+"'");
		}
		else{
		operation("delete from  p_s_a_b where id = '"+id+"'");
		operation("delete from  p_s_t_b where id = '"+id+"'");
		operation("delete from  p_s_ad_b where id = '"+id+"'");
		operation("delete from  p_s_ad_s where id = '"+id+"'");
		}
	}
	public int updateClear(String name,boolean isseason,String season){
		int id = 0;
		try {
			id = getIDForUpdate(name,season);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clearPlayerTableForUpdate(id,isseason);
		return id;
	}

	public void initializePlayerTable(){
		System.out.println("initialize Player Table start");
		//------------detail建表
		operation("create table p_detail("
				+ "name varchar(255),"
				+ "position varchar(255),"
				+ "height varchar(255),"
				+ "weight varchar(255),"
				+ "birth varchar(255),"
				+ "borncity varchar(255),"
				+ "number varchar(255),"
				+ "id int primary key,"
				+ "namecn varchar(255),"
				+ "team varchar(255)"
				+ ")");
//-------------p_s_a_b建表
		operation("create table p_s_a_b("
				+ "name varchar(255),"
				+ "namecn varchar(255),"
				+ "id int,"
				+"season varchar(255),"
				+ "team varchar(255),"
				+ "gp varchar(255),"
				+ "gs varchar(255),"
				+ "time varchar(255),"
				+ "shootper varchar(255),"
				+ "shoot_in varchar(255),"
				+ "shoot_all varchar(255),"
				+ "thper varchar(255),"
				+ "th_in varchar(255),"
				+ "th_all varchar(255),"
				+ "ftper varchar(255),"
				+ "ft_in varchar(255),"
				+ "ft_all varchar(255),"
				+ "backbound varchar(255),"
				+ "offb varchar(255),"
				+ "defb varchar(255),"
				+ "assist varchar(255),"
				+ "steal varchar(255),"
				+ "rejection varchar(255),"
				+ "miss varchar(255),"
				+ "foul varchar(255),"
				+ "pts varchar(255),"
				+ "win varchar(255),"
				+ "lose varchar(255)"				
				+ ")");
		operation("alter table p_s_a_b add primary key(id, season,team)");
	//--------------------p_s_t_b建表
		operation("create table p_s_t_b("
				+ "name varchar(255),"
				+ "namecn varchar(255),"
				+ "id int,"
				+"season varchar(255),"
				+ "team varchar(255),"
				+ "gp varchar(255),"
				+ "gs varchar(255),"
				+ "time varchar(255),"
				+ "shootper varchar(255),"
				+ "shoot_in varchar(255),"
				+ "shoot_all varchar(255),"
				+ "thper varchar(255),"
				+ "th_in varchar(255),"
				+ "th_all varchar(255),"
				+ "ftper varchar(255),"
				+ "ft_in varchar(255),"
				+ "ft_all varchar(255),"
				+ "backbound varchar(255),"
				+ "offb varchar(255),"
				+ "defb varchar(255),"
				+ "assist varchar(255),"
				+ "steal varchar(255),"
				+ "rejection varchar(255),"
				+ "miss varchar(255),"
				+ "foul varchar(255),"
				+ "pts varchar(255),"
				+ "win varchar(255),"
				+ "lose varchar(255)"				
				+ ")");
		operation("alter table p_s_t_b add primary key(id, season,team)");
		//------------------p_s_ad_b建表
		operation("create table p_s_ad_b("
				+ "name varchar(255),"
				+ "namecn varchar(255),"
				+ "id int,"
				+ "season varchar(255),"
				+ "team varchar(255),"
				+ "backeff varchar(255),"
				+ "offbeff varchar(255),"
				+ "defbeff varchar(255),"
				+ "assisteff varchar(255),"
				+ "stealeff varchar(255),"
				+ "rejeff varchar(255),"
				+ "misseff varchar(255),"
				+ "useeff varchar(255),"
				+ "offeff varchar(255),"
				+ "defeff varchar(255),"
				+ "ws varchar(255),"
				+ "offws varchar(255),"
				+ "defws varchar(255),"
				+ "per varchar(255),"
				+ "strshoot varchar(255),"
				+ "kda varchar(255),"
				+ "berej varchar(255)"
				+ ")");
		operation("alter table p_s_ad_b add primary key(id, season,team)");
		//------------------p_s_ad_s建表
		operation("create table p_s_ad_s("
				+ "name varchar(255),"
				+ "namecn varchar(255),"
				+ "id int,"
				+ "season varchar(255),"
				+ "team varchar(255),"
				+ "shootdis varchar(255),"
				+ "bshootper varchar(255),"
				+ "bshoot_in varchar(255),"
				+ "bshoot_all varchar(255),"
				+ "b_per varchar(255),"
				+ "closeshootper varchar(255),"
				+ "closeshoot_in varchar(255),"
				+ "closeshoot_all varchar(255),"
				+ "close_per varchar(255),"
				+ "midshootper varchar(255),"
				+ "midshoot_in varchar(255),"
				+ "midshoot_all varchar(255),"
				+ "mid_per varchar(255),"
				+ "farshootper varchar(255),"
				+ "farshoot_in varchar(255),"
				+ "farshoot_all varchar(255),"
				+ "far_per varchar(255),"
				+ "trueshootper varchar(255),"
				+ "shooteff varchar(255)"
				+")");
		operation("alter table p_s_ad_s add primary key(id, season,team)");
		//-------------p_p_a_b建表
				operation("create table p_p_a_b("
						+ "name varchar(255),"
						+ "namecn varchar(255),"
						+ "id int,"
						+"season varchar(255),"
						+ "team varchar(255),"
						+ "gp varchar(255),"
						+ "time varchar(255),"
						+ "shootper varchar(255),"
						+ "shoot_in varchar(255),"
						+ "shoot_all varchar(255),"
						+ "thper varchar(255),"
						+ "th_in varchar(255),"
						+ "th_all varchar(255),"
						+ "ftper varchar(255),"
						+ "ft_in varchar(255),"
						+ "ft_all varchar(255),"
						+ "backbound varchar(255),"
						+ "offb varchar(255),"
						+ "defb varchar(255),"
						+ "assist varchar(255),"
						+ "steal varchar(255),"
						+ "rejection varchar(255),"
						+ "miss varchar(255),"
						+ "foul varchar(255),"
						+ "pts varchar(255),"
						+ "win varchar(255),"
						+ "lose varchar(255)"				
						+ ")");
				operation("alter table p_p_a_b add primary key(id, season,team)");
			//--------------------p_p_t_b建表
				operation("create table p_p_t_b("
						+ "name varchar(255),"
						+ "namecn varchar(255),"
						+ "id int,"
						+"season varchar(255),"
						+ "team varchar(255),"
						+ "gp varchar(255),"
						+ "time varchar(255),"
						+ "shootper varchar(255),"
						+ "shoot_in varchar(255),"
						+ "shoot_all varchar(255),"
						+ "thper varchar(255),"
						+ "th_in varchar(255),"
						+ "th_all varchar(255),"
						+ "ftper varchar(255),"
						+ "ft_in varchar(255),"
						+ "ft_all varchar(255),"
						+ "backbound varchar(255),"
						+ "offb varchar(255),"
						+ "defb varchar(255),"
						+ "assist varchar(255),"
						+ "steal varchar(255),"
						+ "rejection varchar(255),"
						+ "miss varchar(255),"
						+ "foul varchar(255),"
						+ "pts varchar(255),"
						+ "win varchar(255),"
						+ "lose varchar(255)"				
						+ ")");
				operation("alter table p_p_t_b add primary key(id, season,team)");
				//------------------p_p_ad_b建表
				operation("create table p_p_ad_b("
						+ "name varchar(255),"
						+ "namecn varchar(255),"
						+ "id int,"
						+ "season varchar(255),"
						+ "team varchar(255),"
						+ "backeff varchar(255),"
						+ "offbeff varchar(255),"
						+ "defbeff varchar(255),"
						+ "assisteff varchar(255),"
						+ "stealeff varchar(255),"
						+ "rejeff varchar(255),"
						+ "misseff varchar(255),"
						+ "useeff varchar(255),"
						+ "offeff varchar(255),"
						+ "defeff varchar(255),"
						+ "ws varchar(255),"
						+ "offws varchar(255),"
						+ "defws varchar(255),"
						+ "per varchar(255),"
						+ "strshoot varchar(255),"
						+ "kda varchar(255),"
						+ "berej varchar(255)"
						+ ")");
				operation("alter table p_p_ad_b add primary key(id, season,team)");
				//------------------p_p_ad_s建表
				operation("create table p_p_ad_s("
						+ "name varchar(255),"
						+ "namecn varchar(255),"
						+ "id int,"
						+ "season varchar(255),"
						+ "team varchar(255),"
						+ "shootdis varchar(255),"
						+ "bshootper varchar(255),"
						+ "bshoot_in varchar(255),"
						+ "bshoot_all varchar(255),"
						+ "b_per varchar(255),"
						+ "closeshootper varchar(255),"
						+ "closeshoot_in varchar(255),"
						+ "closeshoot_all varchar(255),"
						+ "close_per varchar(255),"
						+ "midshootper varchar(255),"
						+ "midshoot_in varchar(255),"
						+ "midshoot_all varchar(255),"
						+ "mid_per varchar(255),"
						+ "farshootper varchar(255),"
						+ "farshoot_in varchar(255),"
						+ "farshoot_all varchar(255),"
						+ "far_per varchar(255),"
						+ "trueshootper varchar(255),"
						+ "shooteff varchar(255)"
						+")");
				operation("alter table p_p_ad_s add primary key(id, season,team)");
				System.out.println("initialize Player Table end");
	}
	//=======================================get and set
	public PlayerDataSeason_Avg_Basic sets_a_b(ResultSet rs){
		PlayerDataSeason_Avg_Basic res = new PlayerDataSeason_Avg_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setGp(rs.getString("gp"));
		res.setGs(rs.getString("gs"));
		res.setTime(rs.getString("time"));
		res.setShootper(rs.getString("shootper"));
		res.setShoot_in(rs.getString("shoot_in"));
		res.setShoot_all(rs.getString("shoot_all"));
		res.setThper(rs.getString("thper"));
		res.setTh_in(rs.getString("th_in"));
		res.setTh_all(rs.getString("th_all"));
		res.setFtper(rs.getString("ftper"));
		res.setFt_in(rs.getString("ft_in"));
		res.setFt_all(rs.getString("ft_all"));
		res.setBackbound(rs.getString("backbound"));
		res.setOffb(rs.getString("offb"));
		res.setDefb(rs.getString("defb"));
		res.setAssist(rs.getString("assist"));
		res.setSteal(rs.getString("steal"));
		res.setRejection(rs.getString("rejection"));
		res.setMiss(rs.getString("miss"));
		res.setFoul(rs.getString("foul"));
		res.setPts(rs.getString("pts"));
		res.setWin(rs.getString("win"));
		res.setLose(rs.getString("lose"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public PlayerDataSeason_Tot_Basic sets_t_b(ResultSet rs){
		PlayerDataSeason_Tot_Basic res = new PlayerDataSeason_Tot_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setGp(rs.getString("gp"));
		res.setGs(rs.getString("gs"));
		res.setTime(rs.getString("time"));
		res.setShootper(rs.getString("shootper"));
		res.setShoot_in(rs.getString("shoot_in"));
		res.setShoot_all(rs.getString("shoot_all"));
		res.setThper(rs.getString("thper"));
		res.setTh_in(rs.getString("th_in"));
		res.setTh_all(rs.getString("th_all"));
		res.setFtper(rs.getString("ftper"));
		res.setFt_in(rs.getString("ft_in"));
		res.setFt_all(rs.getString("ft_all"));
		res.setBackbound(rs.getString("backbound"));
		res.setOffb(rs.getString("offb"));
		res.setDefb(rs.getString("defb"));
		res.setAssist(rs.getString("assist"));
		res.setSteal(rs.getString("steal"));
		res.setRejection(rs.getString("rejection"));
		res.setMiss(rs.getString("miss"));
		res.setFoul(rs.getString("foul"));
		res.setPts(rs.getString("pts"));
		res.setWin(rs.getString("win"));
		res.setLose(rs.getString("lose"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public PlayerDataPlayOff_Avg_Basic setp_a_b(ResultSet rs){
		PlayerDataPlayOff_Avg_Basic res = new PlayerDataPlayOff_Avg_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setGp(rs.getString("gp"));		
		res.setTime(rs.getString("time"));
		res.setShootper(rs.getString("shootper"));
		res.setShoot_in(rs.getString("shoot_in"));
		res.setShoot_all(rs.getString("shoot_all"));
		res.setThper(rs.getString("thper"));
		res.setTh_in(rs.getString("th_in"));
		res.setTh_all(rs.getString("th_all"));
		res.setFtper(rs.getString("ftper"));
		res.setFt_in(rs.getString("ft_in"));
		res.setFt_all(rs.getString("ft_all"));
		res.setBackbound(rs.getString("backbound"));
		res.setOffb(rs.getString("offb"));
		res.setDefb(rs.getString("defb"));
		res.setAssist(rs.getString("assist"));
		res.setSteal(rs.getString("steal"));
		res.setRejection(rs.getString("rejection"));
		res.setMiss(rs.getString("miss"));
		res.setFoul(rs.getString("foul"));
		res.setPts(rs.getString("pts"));
		res.setWin(rs.getString("win"));
		res.setLose(rs.getString("lose"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public PlayerDataPlayOff_Tot_Basic setp_t_b(ResultSet rs){
		PlayerDataPlayOff_Tot_Basic res = new PlayerDataPlayOff_Tot_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setGp(rs.getString("gp"));		
		res.setTime(rs.getString("time"));
		res.setShootper(rs.getString("shootper"));
		res.setShoot_in(rs.getString("shoot_in"));
		res.setShoot_all(rs.getString("shoot_all"));
		res.setThper(rs.getString("thper"));
		res.setTh_in(rs.getString("th_in"));
		res.setTh_all(rs.getString("th_all"));
		res.setFtper(rs.getString("ftper"));
		res.setFt_in(rs.getString("ft_in"));
		res.setFt_all(rs.getString("ft_all"));
		res.setBackbound(rs.getString("backbound"));
		res.setOffb(rs.getString("offb"));
		res.setDefb(rs.getString("defb"));
		res.setAssist(rs.getString("assist"));
		res.setSteal(rs.getString("steal"));
		res.setRejection(rs.getString("rejection"));
		res.setMiss(rs.getString("miss"));
		res.setFoul(rs.getString("foul"));
		res.setPts(rs.getString("pts"));
		res.setWin(rs.getString("win"));
		res.setLose(rs.getString("lose"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	public PlayerDataSeason_Ad_Basic sets_ad_b(ResultSet rs){
		PlayerDataSeason_Ad_Basic res = new PlayerDataSeason_Ad_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setBackeff(rs.getString("backeff"));
		res.setOffbeff(rs.getString("offbeff"));
		res.setDefbeff(rs.getString("defbeff"));
		res.setAssisteff(rs.getString("assisteff"));
		res.setStealeff(rs.getString("stealeff"));
		res.setRejeff(rs.getString("rejeff"));
		res.setMisseff(rs.getString("misseff"));
		res.setUseeff(rs.getString("useeff"));
		res.setOffeff(rs.getString("offeff"));
		res.setDefeff(rs.getString("defeff"));
		res.setWs(rs.getString("ws"));
		res.setOffws(rs.getString("offws"));
		res.setDefws(rs.getString("defws"));
		res.setPer(rs.getString("per"));
		res.setStrshoot(rs.getString("strshoot"));
		res.setKda(rs.getString("kda"));
		res.setBerej(rs.getString("berej"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public PlayerDataPlayOff_Ad_Basic setp_ad_b(ResultSet rs){
		PlayerDataPlayOff_Ad_Basic res = new PlayerDataPlayOff_Ad_Basic();
		try{
		res.setName(rs.getString("name").replaceAll("\\?", "'"));
		res.setNameCn(rs.getString("namecn"));
		res.setId(rs.getInt("id"));
		res.setSeason(rs.getString("season"));
		res.setTeam(rs.getString("team"));
		res.setBackeff(rs.getString("backeff"));
		res.setOffbeff(rs.getString("offbeff"));
		res.setDefbeff(rs.getString("defbeff"));
		res.setAssisteff(rs.getString("assisteff"));
		res.setStealeff(rs.getString("stealeff"));
		res.setRejeff(rs.getString("rejeff"));
		res.setMisseff(rs.getString("misseff"));
		res.setUseeff(rs.getString("useeff"));
		res.setOffeff(rs.getString("offeff"));
		res.setDefeff(rs.getString("defeff"));
		res.setWs(rs.getString("ws"));
		res.setOffws(rs.getString("offws"));
		res.setDefws(rs.getString("defws"));
		res.setPer(rs.getString("per"));
		res.setStrshoot(rs.getString("strshoot"));
		res.setKda(rs.getString("kda"));
		res.setBerej(rs.getString("berej"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	public PlayerDataSeason_Ad_Shoot sets_ad_s(ResultSet rs){
		PlayerDataSeason_Ad_Shoot res = new PlayerDataSeason_Ad_Shoot();
		try{
			res.setName(rs.getString("name").replaceAll("\\?", "'"));
			res.setNameCn(rs.getString("namecn"));
			res.setId(rs.getInt("id"));
			res.setSeason(rs.getString("season"));
			res.setTeam(rs.getString("team"));
			res.setShootdis(rs.getString("shootdis"));
			res.setBshootper(rs.getString("bshootper"));
			res.setBshoot_in(rs.getString("bshoot_in"));
			res.setBshoot_all(rs.getString("bshoot_all"));
			res.setB_per(rs.getString("b_per"));
			res.setCloseshootper(rs.getString("closeshootper"));
			res.setCloseshoot_in(rs.getString("closeshoot_in"));
			res.setCloseshoot_all(rs.getString("closeshoot_all"));
			res.setClose_per(rs.getString("close_per"));
			res.setMidshootper(rs.getString("midshootper"));
			res.setMidshoot_in(rs.getString("midshoot_in"));
			res.setMidshoot_all(rs.getString("midshoot_all"));
			res.setMid_per(rs.getString("mid_per"));
			res.setFarshootper(rs.getString("farshootper"));
			res.setFarshoot_in(rs.getString("farshoot_in"));
			res.setFarshoot_all(rs.getString("farshoot_all"));
			res.setFar_per(rs.getString("far_per"));
			res.setTrueshootper(rs.getString("trueshootper"));
			res.setShooteff(rs.getString("shooteff"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	public PlayerDataPlayOff_Ad_Shoot setp_ad_s(ResultSet rs){
		PlayerDataPlayOff_Ad_Shoot res = new PlayerDataPlayOff_Ad_Shoot();
		try{
			res.setName(rs.getString("name").replaceAll("\\?", "'"));
			res.setNameCn(rs.getString("namecn"));
			res.setId(rs.getInt("id"));
			res.setSeason(rs.getString("season"));
			res.setTeam(rs.getString("team"));
			res.setShootdis(rs.getString("shootdis"));
			res.setBshootper(rs.getString("bshootper"));
			res.setBshoot_in(rs.getString("bshoot_in"));
			res.setBshoot_all(rs.getString("bshoot_all"));
			res.setB_per(rs.getString("b_per"));
			res.setCloseshootper(rs.getString("closeshootper"));
			res.setCloseshoot_in(rs.getString("closeshoot_in"));
			res.setCloseshoot_all(rs.getString("closeshoot_all"));
			res.setClose_per(rs.getString("close_per"));
			res.setMidshootper(rs.getString("midshootper"));
			res.setMidshoot_in(rs.getString("midshoot_in"));
			res.setMidshoot_all(rs.getString("midshoot_all"));
			res.setMid_per(rs.getString("mid_per"));
			res.setFarshootper(rs.getString("farshootper"));
			res.setFarshoot_in(rs.getString("farshoot_in"));
			res.setFarshoot_all(rs.getString("farshoot_all"));
			res.setFar_per(rs.getString("far_per"));
			res.setTrueshootper(rs.getString("trueshootper"));
			res.setShooteff(rs.getString("shooteff"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	@Override
	public void adddetail(PlayerDetailInfo p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_detail values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getPosition()+"',"
					+ "'"+p.getHeight()+"',"
					+ "'"+p.getWeight()+"',"
					+ "'"+p.getBirth()+"',"
					+ "'"+p.getBorncity()+"',"
					+ "'"+p.getNumber()+"',"
					+ "'"+p.getId()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getTeam()+"'"
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void adds_a_b(PlayerDataSeason_Avg_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_s_a_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+"'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getGp()+"',"
					+ "'"+p.getGs()+"',"
					+ "'"+p.getTime()+"',"
					+ "'"+p.getShootper()+"',"
					+ "'"+p.getShoot_in()+"',"
					+ "'"+p.getShoot_all()+"',"
					+ "'"+p.getThper()+"',"
					+ "'"+p.getTh_in()+"',"
					+ "'"+p.getTh_all()+"',"
					+ "'"+p.getFtper()+"',"
					+ "'"+p.getFt_in()+"',"
					+ "'"+p.getFt_all()+"',"
					+ "'"+p.getBackbound()+"',"
					+ "'"+p.getOffb()+"',"
					+ "'"+p.getDefb()+"',"
					+ "'"+p.getAssist()+"',"
					+ "'"+p.getSteal()+"',"
					+ "'"+p.getRejection()+"',"
					+ "'"+p.getMiss()+"',"
					+ "'"+p.getFoul()+"',"
					+ "'"+p.getPts()+"',"
					+ "'"+p.getWin()+"',"
					+ "'"+p.getLose()+"'"				
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void adds_t_b(PlayerDataSeason_Tot_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_s_t_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+"'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getGp()+"',"
					+ "'"+p.getGs()+"',"
					+ "'"+p.getTime()+"',"
					+ "'"+p.getShootper()+"',"
					+ "'"+p.getShoot_in()+"',"
					+ "'"+p.getShoot_all()+"',"
					+ "'"+p.getThper()+"',"
					+ "'"+p.getTh_in()+"',"
					+ "'"+p.getTh_all()+"',"
					+ "'"+p.getFtper()+"',"
					+ "'"+p.getFt_in()+"',"
					+ "'"+p.getFt_all()+"',"
					+ "'"+p.getBackbound()+"',"
					+ "'"+p.getOffb()+"',"
					+ "'"+p.getDefb()+"',"
					+ "'"+p.getAssist()+"',"
					+ "'"+p.getSteal()+"',"
					+ "'"+p.getRejection()+"',"
					+ "'"+p.getMiss()+"',"
					+ "'"+p.getFoul()+"',"
					+ "'"+p.getPts()+"',"
					+ "'"+p.getWin()+"',"
					+ "'"+p.getLose()+"'"				
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void adds_ad_b(PlayerDataSeason_Ad_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_s_ad_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+ "'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getBackeff()+"',"
					+ "'"+p.getOffbeff()+"',"
					+ "'"+p.getDefbeff()+"',"
					+ "'"+p.getAssisteff()+"',"
					+ "'"+p.getStealeff()+"',"
					+ "'"+p.getRejeff()+"',"
					+ "'"+p.getMisseff()+"',"
					+ "'"+p.getUseeff()+"',"
					+ "'"+p.getOffeff()+"',"
					+ "'"+p.getDefeff()+"',"
					+ "'"+p.getWs()+"',"
					+ "'"+p.getOffws()+"',"
					+ "'"+p.getDefws()+"',"
					+ "'"+p.getPer()+"',"
					+ "'"+p.getStrshoot()+"',"
					+ "'"+p.getKda()+"',"
					+ "'"+p.getBerej()+"'"
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void adds_ad_s(PlayerDataSeason_Ad_Shoot p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_s_ad_s values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+ "'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getShootdis()+"',"
					+ "'"+p.getBshootper()+"',"
					+ "'"+p.getBshoot_in()+"',"
					+ "'"+p.getBshoot_all()+"',"
					+ "'"+p.getB_per()+"',"
					+ "'"+p.getCloseshootper()+"',"
					+ "'"+p.getCloseshoot_in()+"',"
					+ "'"+p.getCloseshoot_all()+"',"
					+ "'"+p.getClose_per()+"',"
					+ "'"+p.getMidshootper()+"',"
					+ "'"+p.getMidshoot_in()+"',"
					+ "'"+p.getMidshoot_all()+"',"
					+ "'"+p.getMid_per()+"',"
					+ "'"+p.getFarshootper()+"',"
					+ "'"+p.getFarshoot_in()+"',"
					+ "'"+p.getFarshoot_all()+"',"
					+ "'"+p.getFar_per()+"',"
					+ "'"+p.getTrueshootper()+"',"
					+ "'"+p.getShooteff()+"'"
					+")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addp_a_b(PlayerDataPlayOff_Avg_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_p_a_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+"'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getGp()+"',"					
					+ "'"+p.getTime()+"',"
					+ "'"+p.getShootper()+"',"
					+ "'"+p.getShoot_in()+"',"
					+ "'"+p.getShoot_all()+"',"
					+ "'"+p.getThper()+"',"
					+ "'"+p.getTh_in()+"',"
					+ "'"+p.getTh_all()+"',"
					+ "'"+p.getFtper()+"',"
					+ "'"+p.getFt_in()+"',"
					+ "'"+p.getFt_all()+"',"
					+ "'"+p.getBackbound()+"',"
					+ "'"+p.getOffb()+"',"
					+ "'"+p.getDefb()+"',"
					+ "'"+p.getAssist()+"',"
					+ "'"+p.getSteal()+"',"
					+ "'"+p.getRejection()+"',"
					+ "'"+p.getMiss()+"',"
					+ "'"+p.getFoul()+"',"
					+ "'"+p.getPts()+"',"
					+ "'"+p.getWin()+"',"
					+ "'"+p.getLose()+"'"				
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addp_t_b(PlayerDataPlayOff_Tot_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_p_t_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+"'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getGp()+"',"					
					+ "'"+p.getTime()+"',"
					+ "'"+p.getShootper()+"',"
					+ "'"+p.getShoot_in()+"',"
					+ "'"+p.getShoot_all()+"',"
					+ "'"+p.getThper()+"',"
					+ "'"+p.getTh_in()+"',"
					+ "'"+p.getTh_all()+"',"
					+ "'"+p.getFtper()+"',"
					+ "'"+p.getFt_in()+"',"
					+ "'"+p.getFt_all()+"',"
					+ "'"+p.getBackbound()+"',"
					+ "'"+p.getOffb()+"',"
					+ "'"+p.getDefb()+"',"
					+ "'"+p.getAssist()+"',"
					+ "'"+p.getSteal()+"',"
					+ "'"+p.getRejection()+"',"
					+ "'"+p.getMiss()+"',"
					+ "'"+p.getFoul()+"',"
					+ "'"+p.getPts()+"',"
					+ "'"+p.getWin()+"',"
					+ "'"+p.getLose()+"'"				
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addp_ad_b(PlayerDataPlayOff_Ad_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_p_ad_b values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+ "'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getBackeff()+"',"
					+ "'"+p.getOffbeff()+"',"
					+ "'"+p.getDefbeff()+"',"
					+ "'"+p.getAssisteff()+"',"
					+ "'"+p.getStealeff()+"',"
					+ "'"+p.getRejeff()+"',"
					+ "'"+p.getMisseff()+"',"
					+ "'"+p.getUseeff()+"',"
					+ "'"+p.getOffeff()+"',"
					+ "'"+p.getDefeff()+"',"
					+ "'"+p.getWs()+"',"
					+ "'"+p.getOffws()+"',"
					+ "'"+p.getDefws()+"',"
					+ "'"+p.getPer()+"',"
					+ "'"+p.getStrshoot()+"',"
					+ "'"+p.getKda()+"',"
					+ "'"+p.getBerej()+"'"
					+ ")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addp_ad_s(PlayerDataPlayOff_Ad_Shoot p) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			operation("insert into p_p_ad_s values("
					+ "'"+p.getName()+"',"
					+ "'"+p.getNameCn()+"',"
					+ "'"+p.getId()+"',"
					+ "'"+p.getSeason()+"',"
					+ "'"+p.getTeam()+"',"
					+ "'"+p.getShootdis()+"',"
					+ "'"+p.getBshootper()+"',"
					+ "'"+p.getBshoot_in()+"',"
					+ "'"+p.getBshoot_all()+"',"
					+ "'"+p.getB_per()+"',"
					+ "'"+p.getCloseshootper()+"',"
					+ "'"+p.getCloseshoot_in()+"',"
					+ "'"+p.getCloseshoot_all()+"',"
					+ "'"+p.getClose_per()+"',"
					+ "'"+p.getMidshootper()+"',"
					+ "'"+p.getMidshoot_in()+"',"
					+ "'"+p.getMidshoot_all()+"',"
					+ "'"+p.getMid_per()+"',"
					+ "'"+p.getFarshootper()+"',"
					+ "'"+p.getFarshoot_in()+"',"
					+ "'"+p.getFarshoot_all()+"',"
					+ "'"+p.getFar_per()+"',"
					+ "'"+p.getTrueshootper()+"',"
					+ "'"+p.getShooteff()+"'"
					+")");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public PlayerDetailInfo getdetail(int id) throws RemoteException {
		// TODO Auto-generated method stub
		PlayerDetailInfo res = new PlayerDetailInfo();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_detail where id = '"+id+"'");
			while(rs.next()){
			res.setName(rs.getString("name").replaceAll("\\?", "'"));
			res.setNameCn(rs.getString("namecn"));
			res.setId(rs.getInt("id"));
			res.setPosition(rs.getString("position"));
			res.setHeight(rs.getString("height"));
			res.setWeight(rs.getString("weight"));
			res.setBirth(rs.getString("birth"));
			res.setBorncity(rs.getString("borncity"));
			res.setNumber(rs.getString("number"));
			res.setTeam(rs.getString("team"));
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}

	@Override
	public ArrayList<PlayerDataSeason_Avg_Basic> gets_a_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Avg_Basic> temp = new ArrayList<PlayerDataSeason_Avg_Basic>();
		PlayerDataSeason_Avg_Basic res = new PlayerDataSeason_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed())

				System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
				res = sets_a_b(rs);
				temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	
		
	}

	@Override
	public ArrayList<PlayerDataSeason_Tot_Basic> gets_t_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Tot_Basic> temp = new ArrayList<PlayerDataSeason_Tot_Basic>();
		PlayerDataSeason_Tot_Basic res = new PlayerDataSeason_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_t_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
					res = sets_t_b(rs);
					temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataSeason_Ad_Basic> gets_ad_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Basic> temp = new ArrayList<PlayerDataSeason_Ad_Basic>();
		PlayerDataSeason_Ad_Basic res = new PlayerDataSeason_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
		
	}

	@Override
	public ArrayList<PlayerDataSeason_Ad_Shoot> gets_ad_s(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Shoot> temp = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		PlayerDataSeason_Ad_Shoot res = new PlayerDataSeason_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_s where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Avg_Basic> getp_a_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Avg_Basic> temp = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		PlayerDataPlayOff_Avg_Basic res = new PlayerDataPlayOff_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_a_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_a_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Tot_Basic> getp_t_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Tot_Basic> temp = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		PlayerDataPlayOff_Tot_Basic res = new PlayerDataPlayOff_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_t_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_t_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Basic> getp_ad_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Basic> temp = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		PlayerDataPlayOff_Ad_Basic res = new PlayerDataPlayOff_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_b where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getp_ad_s(int id) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Shoot> temp = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		PlayerDataPlayOff_Ad_Shoot res = new PlayerDataPlayOff_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_s where id = '"+id+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
   //--------------
	public ArrayList<PlayerDetailInfo> getAlldetail(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDetailInfo> pd = new ArrayList<PlayerDetailInfo>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int latest = 0;
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				

			Statement st = con.createStatement();
			ResultSet rs;
			if(!season.equals("null")){
			rs = st.executeQuery("select * from p_s_a_b where season = '"+season+"'");
			while(rs.next()){
				if(temp.size()==0){
					latest = rs.getInt("id");
					temp.add(rs.getInt("id"));
				}
				else{
					if(latest==rs.getInt("id")){
						
					}
					else{
						latest = rs.getInt("id");
						temp.add(rs.getInt("id"));
					}				
				}
			}			
			con.close();
			for(int i = 0;i<temp.size();i++){
				//System.out.println(temp.get(i));
				pd.add(getdetail(temp.get(i)));
			}
			return pd;
			}
			else{
				rs = st.executeQuery("select * from p_detail");
				while(rs.next()){
					PlayerDetailInfo r = new PlayerDetailInfo();
					r.setName(rs.getString("name").replaceAll("\\?", "'"));
					r.setNameCn(rs.getString("namecn"));
					r.setId(rs.getInt("id"));
					r.setPosition(rs.getString("position"));
					r.setHeight(rs.getString("height"));
					r.setWeight(rs.getString("weight"));
					r.setBirth(rs.getString("birth"));
					r.setBorncity(rs.getString("borncity"));
					r.setNumber(rs.getString("number"));
					r.setTeam(rs.getString("team"));
					pd.add(r);
				}
				return pd;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public ArrayList<PlayerDataSeason_Avg_Basic> getAlls_a_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Avg_Basic> temp = new ArrayList<PlayerDataSeason_Avg_Basic>();
		PlayerDataSeason_Avg_Basic res = new PlayerDataSeason_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
				res = sets_a_b(rs);
				temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	
		
	}

	@Override
	public ArrayList<PlayerDataSeason_Tot_Basic> getAlls_t_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Tot_Basic> temp = new ArrayList<PlayerDataSeason_Tot_Basic>();
		PlayerDataSeason_Tot_Basic res = new PlayerDataSeason_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_t_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_t_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataSeason_Ad_Basic> getAlls_ad_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Basic> temp = new ArrayList<PlayerDataSeason_Ad_Basic>();
		PlayerDataSeason_Ad_Basic res = new PlayerDataSeason_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
		
	}

	@Override
	public ArrayList<PlayerDataSeason_Ad_Shoot> getAlls_ad_s(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Shoot> temp = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		PlayerDataSeason_Ad_Shoot res = new PlayerDataSeason_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_s where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Avg_Basic> getAllp_a_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Avg_Basic> temp = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		PlayerDataPlayOff_Avg_Basic res = new PlayerDataPlayOff_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_a_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_a_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Tot_Basic> getAllp_t_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Tot_Basic> temp = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		PlayerDataPlayOff_Tot_Basic res = new PlayerDataPlayOff_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_t_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_t_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Basic> getAllp_ad_b(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Basic> temp = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		PlayerDataPlayOff_Ad_Basic res = new PlayerDataPlayOff_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_b where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}

	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getAllp_ad_s(String season) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Shoot> temp = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		PlayerDataPlayOff_Ad_Shoot res = new PlayerDataPlayOff_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_s where season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	
	//==================================== ui request
	public int getIDbyName(String name,String team) throws RemoteException{
		// TODO Auto-generated method stub
		String keyname = name;
		int res = 0;
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where (name = '"+keyname+"' or namecn = '"+keyname+"')");
			while(rs.next()){
			res = rs.getInt("id");
			
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return res;
	}
	public int getIDForUpdate(String name,String season)throws RemoteException{
		int res = 0;
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where (name = '"+name+"' or namecn = '"+name+"')and season = '"+season+"'");
			while(rs.next()){
			res = rs.getInt("id");
			
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return res;
	}
	public int getIDHot(String name,String team) throws RemoteException{
		// TODO Auto-generated method stub
		int res = 0;
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where name = '"+name+"' and team like '%"+team+"%'");
			while(rs.next()){
				
					res = rs.getInt("id");
					System.out.println(res);
					break;
				
			
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return res;
	}
	@Override
	public ArrayList<PlayerDataSeason_Avg_Basic> gets_a_b(int id, String season)
			throws RemoteException {
		ArrayList<PlayerDataSeason_Avg_Basic> temp = new ArrayList<PlayerDataSeason_Avg_Basic>();
		PlayerDataSeason_Avg_Basic res = new PlayerDataSeason_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed())

				System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_a_b where id = '"+id+"'and season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
				res = sets_a_b(rs);
				temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataSeason_Tot_Basic> gets_t_b(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Tot_Basic> temp = new ArrayList<PlayerDataSeason_Tot_Basic>();
		PlayerDataSeason_Tot_Basic res = new PlayerDataSeason_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_t_b where id = '"+id+"'and season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_t_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataSeason_Ad_Basic> gets_ad_b(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Basic> temp = new ArrayList<PlayerDataSeason_Ad_Basic>();
		PlayerDataSeason_Ad_Basic res = new PlayerDataSeason_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_b where id = '"+id+"'and season ='"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataSeason_Ad_Shoot> gets_ad_s(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataSeason_Ad_Shoot> temp = new ArrayList<PlayerDataSeason_Ad_Shoot>();
		PlayerDataSeason_Ad_Shoot res = new PlayerDataSeason_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_s_ad_s where id = '"+id+"'and season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = sets_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataPlayOff_Avg_Basic> getp_a_b(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Avg_Basic> temp = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
		PlayerDataPlayOff_Avg_Basic res = new PlayerDataPlayOff_Avg_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_a_b where id = '"+id+"'and season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_a_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getp_ad_s(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Shoot> temp = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
		PlayerDataPlayOff_Ad_Shoot res = new PlayerDataPlayOff_Ad_Shoot();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_s where id = '"+id+"'and season = '"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_s(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataPlayOff_Tot_Basic> getp_t_b(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Tot_Basic> temp = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
		PlayerDataPlayOff_Tot_Basic res = new PlayerDataPlayOff_Tot_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_t_b where id = '"+id+"'and season='"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_t_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	@Override
	public ArrayList<PlayerDataPlayOff_Ad_Basic> getp_ad_b(int id, String season)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<PlayerDataPlayOff_Ad_Basic> temp = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
		PlayerDataPlayOff_Ad_Basic res = new PlayerDataPlayOff_Ad_Basic();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from p_p_ad_b where id = '"+id+"'and season='"+season+"'");
			while(rs.next()){
				if((rs.getString("team").equals("总计"))||(rs.getString("team").endsWith("支"))){
					
				}
				else{
			res = setp_ad_b(rs);
			temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
	}
	public ArrayList<String> getTeambyId(int id, String season)
		throws RemoteException{
		// TODO Auto-generated method stub
		String res = "";
		ArrayList<String> temp = new ArrayList<String>();
		try{
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs;
			if(season.equals("null")){
				rs = st.executeQuery("select * from p_s_a_b where id = '"+id+"'");
			}
			else{
			rs = st.executeQuery("select * from p_s_a_b where id = '"+id+"'and season='"+season+"'");
			}
			while(rs.next()){
				res = rs.getString("season")+";"+rs.getString("team");
				if((!res.endsWith("支"))&&(!res.endsWith("计"))){
					temp.add(res);
				}
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp;
		
		
	}
	public ArrayList<Double> getSeasonAvg(String season) {
		// TODO Auto-generated method stub
		ArrayList<Double> res = new ArrayList<Double>();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select avg(pts) as ptsavg,avg(assist) as assistavg,avg(backbound) as backboundavg,avg(ftper) as ftperavg,avg(thper) as thperavg from p_s_a_b where season = '"+season+"'");
			while(rs.next()){
				res.add(rs.getDouble("ptsavg"));
				res.add(rs.getDouble("assistavg"));
				res.add(rs.getDouble("backboundavg"));
				res.add(rs.getDouble("ftperavg"));
				res.add(rs.getDouble("thperavg"));
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}
	public ArrayList<Double> getPlayOffAvg(String season) {
		// TODO Auto-generated method stub
		ArrayList<Double> res = new ArrayList<Double>();
		try {
			Connection con = DriverManager.getConnection(DataBaseLink.url,
					"root", "nba");
			if (!con.isClosed()){}

				//System.out.println("success");

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select avg(pts) as ptsavg,avg(assist) as assistavg,avg(backbound) as backboundavg,avg(ftper) as ftperavg,avg(thper) as thperavg from p_p_a_b where season = '"+season+"'");
			while(rs.next()){
				res.add(rs.getDouble("ptsavg"));
				res.add(rs.getDouble("assistavg"));
				res.add(rs.getDouble("backboundavg"));
				res.add(rs.getDouble("ftperavg"));
				res.add(rs.getDouble("thperavg"));
			}
			con.close();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return res;
	}
	//=====================================statics request
	public void insertTeam(int id,String team)throws RemoteException{
		
		operation("update p_detail set team = '"+team+"'where id = '"+id+"'");
	}
	
}
