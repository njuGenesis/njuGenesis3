package data.db;

import java.rmi.RemoteException;

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

	public void clearPlayerTable(){
		operation("Truncate Table p_s_a_b");
		operation("Truncate Table p_s_t_b");
		operation("Truncate Table p_s_ad_b");
		operation("Truncate Table p_s_ad_s");
		operation("Truncate Table p_p_a_b");
		operation("Truncate Table p_p_t_b");
		operation("Truncate Table p_p_ad_b");
		operation("Truncate Table p_p_ad_s");
		operation("Truncate Table p_detail");
	}
	public void initializePlayerTable(){
		
		//------------detail建表
		operation("create table p_detail("
				+ "name varchar(255),"
				+ "position varchar(255),"
				+ "height varchar(255),"
				+ "weight varchar(255),"
				+ "birth varchar(255),"
				+ "borncity varchar(255),"
				+ "number varchar(255),"
				+ "id int primary key"
				+ ")");
//-------------p_s_a_b建表
		operation("create table p_s_a_b("
				+ "name varchar(255),"
				+ "id int primary key,"
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
	//--------------------p_s_t_b建表
		operation("create table p_s_t_b("
				+ "name varchar(255),"
				+ "id int primary key,"
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

		//------------------p_s_ad_b建表
		operation("create table p_s_ad_b("
				+ "name varchar(255),"
				+ "id int primary key,"
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
		//------------------p_s_ad_s建表
		operation("create table p_s_ad_s("
				+ "name varchar(255),"
				+ "id int primary key,"
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
		
		//-------------p_p_a_b建表
				operation("create table p_p_a_b("
						+ "name varchar(255),"
						+ "id int primary key,"
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
			//--------------------p_p_t_b建表
				operation("create table p_p_t_b("
						+ "name varchar(255),"
						+ "id int primary key,"
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

				//------------------p_p_ad_b建表
				operation("create table p_p_ad_b("
						+ "name varchar(255),"
						+ "id int primary key,"
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
				//------------------p_p_ad_s建表
				operation("create table p_p_ad_s("
						+ "name varchar(255),"
						+ "id int primary key,"
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
					+ "'"+p.getId()+"'"
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
		return null;
	}

	@Override
	public PlayerDataSeason_Avg_Basic gets_a_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataSeason_Tot_Basic gets_t_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataSeason_Ad_Basic gets_ad_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataSeason_Ad_Shoot gets_ad_s(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataPlayOff_Avg_Basic getp_a_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataPlayOff_Tot_Basic getp_t_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataPlayOff_Ad_Basic getp_ad_b(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerDataPlayOff_Ad_Shoot getp_ad_s(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
