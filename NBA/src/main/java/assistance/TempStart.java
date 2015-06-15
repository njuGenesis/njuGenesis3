package assistance;

import java.rmi.RemoteException;
import java.util.ArrayList;

import crawler.TestCrawlerByJsoup;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.db.PlayerDb;
import data.po.PlayerDataPO;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDescriptionStat;
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
//		try {
//			for(int i = 0;i<p.gets_a_b(3).size();i++){
//			System.out.println(p.gets_a_b(3).get(i).getSeason()+";"+p.gets_a_b(3).get(i).getTeam());
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
//		String[] res = t.getHotPlayerDaily("points");
//		for(int i = 0;i<5;i++){
//			System.out.println(res[i]);
		
//		}
		try {
			ArrayList<Integer> t = pd.selectByTag("13-14", "detail", "a", "null", "null", "null");
			System.out.println(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
