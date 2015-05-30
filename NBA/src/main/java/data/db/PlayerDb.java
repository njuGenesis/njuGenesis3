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
		operation("clear table");
	}
	public void initializePlayerTable(){
		operation("create table");
	}
	
	@Override
	public void adddetail(PlayerDetailInfo p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adds_a_b(PlayerDataSeason_Avg_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adds_t_b(PlayerDataSeason_Tot_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adds_ad_b(PlayerDataSeason_Ad_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adds_ad_s(PlayerDataSeason_Ad_Shoot p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addp_a_b(PlayerDataPlayOff_Avg_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addp_t_b(PlayerDataPlayOff_Tot_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addp_ad_b(PlayerDataPlayOff_Ad_Basic p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addp_ad_s(PlayerDataPlayOff_Ad_Shoot p) throws RemoteException {
		// TODO Auto-generated method stub
		
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
