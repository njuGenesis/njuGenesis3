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
		p.clearPlayerTable();
		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
		t.initializePlayerDetail(971, 972);
		t.initializePlayerSeason(971, 972);
		t.initializePlayerPlayOff(971, 972);
		try {
			ArrayList<PlayerDataSeason_Avg_Basic>temp = pd.gets_a_b(971);
			
			for(int i = 0;i<temp.size();i++){
				System.out.println(temp.get(i).getName()+";"+temp.get(i).getNameCn());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
