package assistance;

import crawler.TestCrawlerByJsoup;
import bussinesslogic.player.PlayerLogic;
import data.db.PlayerDb;
import data.po.PlayerDataPO;

public class TempStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		PlayerLogic p = new PlayerLogic();
		PlayerDb pdb = new PlayerDb();
		//pdb.initializePlayerTable();
		pdb.clearPlayerTable();
		TestCrawlerByJsoup t = new TestCrawlerByJsoup();
		t.initializePlayerDetail(80,90);
		t.initializePlayerSeason(80,90);
		t.initializePlayerPlayOff(80,90);
	}
	
}
