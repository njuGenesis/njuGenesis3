package assistance;

import java.rmi.RemoteException;
import java.util.ArrayList;

import crawler.TestCrawlerByJsoup;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.db.PlayerDb;
import data.po.PlayerDataPO;
import data.po.playerData.PlayerDataSeason_Avg_Basic;

public class TempStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PlayerLogic p = new PlayerLogic();
		PlayerLogic_db pdb = new PlayerLogic_db();
		//pdb.initializePlayerTable();
//		pdb.clearPlayerTable();
//		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
//		t.initializePlayerDetail(1,4273);
//		t.initializePlayerSeason(1,4273);
//		t.initializePlayerPlayOff(1,4273);
		try {
			ArrayList<Integer> temp = pdb.selectByTag("14-15", "s_a_b", "null", "null", "前锋", "Northwest");
			for(int i = 0;i<temp.size();i++){
				System.out.println(temp.get(i));
			}
			//System.out.println(temp.get(0).getId()+";"+temp.get(0).getBackbound()+";"+temp.get(0).getName());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("'hi".replaceAll("'", "\\?"));
	}
	
}
