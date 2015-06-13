package assistance;

import java.rmi.RemoteException;
import java.util.ArrayList;

import crawler.TestCrawlerByJsoup;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.db.PlayerDb;
import data.po.PlayerDataPO;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDetailInfo;

public class TempStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerLogic_db pd = new PlayerLogic_db();
		PlayerDb p = new PlayerDb();
//		//p.initializePlayerTable();
//		p.clearPlayerTable();
//		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
//		t.initializePlayerDetail(1, 4364);
//		t.initializePlayerSeason(1, 4364);
//		t.initializePlayerPlayOff(1, 4364);
		try {
			System.out.println(p.getdetail(1).getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
