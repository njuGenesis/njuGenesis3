package dataService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import data.po.PlayerDataPO;
import data.po.playerData.*;

public interface PlayerDataService extends Remote{
	public void adddetail(PlayerDetailInfo p)throws RemoteException;
	public void adds_a_b(PlayerDataSeason_Avg_Basic p)throws RemoteException;
	public void adds_t_b(PlayerDataSeason_Tot_Basic p)throws RemoteException;
	public void adds_ad_b(PlayerDataSeason_Ad_Basic p)throws RemoteException;
	public void adds_ad_s(PlayerDataSeason_Ad_Shoot p)throws RemoteException;
	public void addp_a_b(PlayerDataPlayOff_Avg_Basic p)throws RemoteException;
	public void addp_t_b(PlayerDataPlayOff_Tot_Basic p)throws RemoteException;
	public void addp_ad_b(PlayerDataPlayOff_Ad_Basic p)throws RemoteException;
	public void addp_ad_s(PlayerDataPlayOff_Ad_Shoot p)throws RemoteException;
	
	public PlayerDetailInfo getdetail(int id)throws RemoteException;
	public PlayerDataSeason_Avg_Basic gets_a_b(int id)throws RemoteException;
	public PlayerDataSeason_Tot_Basic gets_t_b(int id)throws RemoteException;
	public PlayerDataSeason_Ad_Basic gets_ad_b(int id)throws RemoteException;
	public PlayerDataSeason_Ad_Shoot gets_ad_s(int id)throws RemoteException;
	public PlayerDataPlayOff_Avg_Basic getp_a_b(int id)throws RemoteException;
	public PlayerDataPlayOff_Tot_Basic getp_t_b(int id)throws RemoteException;
	public PlayerDataPlayOff_Ad_Basic getp_ad_b(int id)throws RemoteException;
	public PlayerDataPlayOff_Ad_Shoot getp_ad_s(int id)throws RemoteException;
}
