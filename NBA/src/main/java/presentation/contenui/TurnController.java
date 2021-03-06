package presentation.contenui;

import presentation.compare.PlayerAnalysis;
import presentation.compare.PlayerCompare;
import presentation.component.BgPanel;
import presentation.hotspot.HotPlayerProgressPanel;
import presentation.hotspot.HotPlayerSeasonPanel;
import presentation.hotspot.HotPlayerTodayPanel;
import presentation.hotspot.HotTeamSeasonPanel;
import presentation.hotspot.HotUI;
import presentation.match.MatchDetailPanel;
import presentation.match.MatchUI;
import presentation.player.PlayerDetials;
import presentation.player.PlayerUI;
import presentation.stats.PlayerStatsPanelNew;
import presentation.stats.StatsUI;
import presentation.stats.TeamStatsPanelNew;
import presentation.team.TeamDetials;
import presentation.team.TeamUI;
import data.po.matchData.MatchDataSeason;
import data.po.teamData.TeamBaseInfo;

public class TurnController {

	public TurnController(){
		
	}
	
	public BgPanel turn(PanelKind goingTo){
		BgPanel newPanel = null;
		switch(goingTo){
		case HOT:newPanel = new HotUI("img/Framebg/Hot.png");break;
		case TEAM:newPanel = new TeamUI();break;
		case PLAYER:newPanel = new PlayerUI();break;
		case MATCH:newPanel = new MatchUI("img/Framebg/Match.png");break;
		case STATS:newPanel = new StatsUI("img/Framebg/Stats.png");break;
		case ANALYSIS:newPanel = new PlayerAnalysis();break;
		
		case HOT_PLAYERTODAY:newPanel = new HotPlayerTodayPanel();break;
		case HOT_PLAYERSEASON:newPanel = new HotPlayerSeasonPanel();break;
		case HOT_PLAYERPROGRESS:newPanel = new HotPlayerProgressPanel();break;
		case HOT_TEAMSEASON:newPanel = new HotTeamSeasonPanel();break;
		
		case STATS_PLAYER:newPanel = new PlayerStatsPanelNew();break;
		case STATS_TEAM:newPanel = new TeamStatsPanelNew();break;
		
		case COMPARE:newPanel = new PlayerCompare();break;
		}
		return newPanel;
	}

	//	public 
	
	public BgPanel turnToTeamDetials(TeamBaseInfo teamBaseInfo){
		BgPanel newPanel = new TeamDetials(teamBaseInfo);
		return newPanel;
	}
	
	public BgPanel turnToPlayerDetials(int id){
		BgPanel newPanel = new PlayerDetials(id);
		return newPanel;
	}
	
	public BgPanel turnToMatchDetials(MatchDataSeason po){
		BgPanel newPanel = new MatchDetailPanel(po);
		return newPanel;
	}
	
	//date格式为13-14_01-01
//	public BgPanel turnToMatchDetials(String date,String shortName){
//		MatchLogic l = new MatchLogic();
//		ArrayList<MatchDataPO> pos = l.GetInfo(date, date, shortName);
//		
//		BgPanel newPanel = new MatchDetailPanel(pos.get(0));
//		newPanel.setBounds(30, 30, 940, 600);
//		newPanel.setVisible(false);
//		
//		StartUI.startUI.setMenu(3);
//		
//		return newPanel;
//	}
}