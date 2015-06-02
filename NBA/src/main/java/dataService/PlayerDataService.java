package dataService;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
	public ArrayList<PlayerDataSeason_Avg_Basic> gets_a_b(int id)throws RemoteException;
	public ArrayList<PlayerDataSeason_Tot_Basic> gets_t_b(int id)throws RemoteException;
	public ArrayList<PlayerDataSeason_Ad_Basic> gets_ad_b(int id)throws RemoteException;
	public ArrayList<PlayerDataSeason_Ad_Shoot> gets_ad_s(int id)throws RemoteException;
	public ArrayList<PlayerDataPlayOff_Avg_Basic> getp_a_b(int id)throws RemoteException;
	public ArrayList<PlayerDataPlayOff_Tot_Basic> getp_t_b(int id)throws RemoteException;
	public ArrayList<PlayerDataPlayOff_Ad_Basic> getp_ad_b(int id)throws RemoteException;
	public ArrayList<PlayerDataPlayOff_Ad_Shoot> getp_ad_s(int id)throws RemoteException;
	
	ArrayList<PlayerDataPlayOff_Ad_Shoot> getAllp_ad_s(String season)
			throws RemoteException;
	ArrayList<PlayerDataPlayOff_Ad_Basic> getAllp_ad_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataPlayOff_Tot_Basic> getAllp_t_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataPlayOff_Avg_Basic> getAllp_a_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataSeason_Avg_Basic> getAlls_a_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataSeason_Tot_Basic> getAlls_t_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataSeason_Ad_Basic> getAlls_ad_b(String season)
			throws RemoteException;
	ArrayList<PlayerDataSeason_Ad_Shoot> getAlls_ad_s(String season)
			throws RemoteException;
}
