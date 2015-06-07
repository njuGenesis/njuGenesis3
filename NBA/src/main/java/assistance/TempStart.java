package assistance;

import java.rmi.RemoteException;
import java.util.ArrayList;

import crawler.TestCrawlerByJsoup;
import bussinesslogic.player.PlayerLogic;
import data.db.PlayerDb;
import data.po.PlayerDataPO;
import data.po.playerData.PlayerDataSeason_Avg_Basic;

public class TempStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PlayerLogic p = new PlayerLogic();
		PlayerDb pdb = new PlayerDb();
		//pdb.initializePlayerTable();
//		pdb.clearPlayerTable();
//		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
//		t.initializePlayerDetail(1,4273);
//		t.initializePlayerSeason(1,4273);
//		t.initializePlayerPlayOff(1,4273);
		try {
			ArrayList<PlayerDataSeason_Avg_Basic> temp = pdb.gets_a_b(1);
			System.out.println(temp.get(0).getId()+";"+temp.get(0).getBackbound()+";"+temp.get(0).getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
