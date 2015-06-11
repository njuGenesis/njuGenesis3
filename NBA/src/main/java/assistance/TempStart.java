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
//		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
//		String[] a = t.getHotPlayerDaily("rebounds");
//		for(int i=0;i<5;i++){
//			System.out.println(a[i]);
//		}
		PlayerLogic_db pdb = new PlayerLogic_db();
		try {
			ArrayList<Double> res=pdb.getAvgOfAll("13-14", false);
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
