package presentation.contenui;

import java.util.ArrayList;

import presentation.component.BgPanel;
import presentation.hotspot.HotPlayerProgressPanel;
import presentation.hotspot.HotPlayerSeasonPanel;
import presentation.hotspot.HotPlayerTodayPanel;
import presentation.hotspot.HotTeamSeasonPanel;
import presentation.mainui.StartUI;
import presentation.match.MatchDetailPanel;
import presentation.player.PlayerDetials;
import presentation.player.PlayerUI;
import presentation.stats.PlayerStatsPanelNew;
import presentation.stats.TeamStatsPanelNew;
import presentation.team.TeamDetials;
import presentation.team.TeamUI;
import bussinesslogic.match.MatchLogic;
import data.po.MatchDataPO;

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
		case ANALYSIS:newPanel = new StatsUI("img/Framebg/Stats.png");break;
		
		case HOT_PLAYERTODAY:newPanel = new HotPlayerTodayPanel();break;
		case HOT_PLAYERSEASON:newPanel = new HotPlayerSeasonPanel();break;
		case HOT_PLAYERPROGRESS:newPanel = new HotPlayerProgressPanel();break;
		case HOT_TEAMSEASON:newPanel = new HotTeamSeasonPanel();break;
		
		case STATS_PLAYER:newPanel = new PlayerStatsPanelNew();break;
		case STATS_TEAM:newPanel = new TeamStatsPanelNew();break;
		}
		return newPanel;
	}

	//	public 
	
	public BgPanel turnToTeamDetials(String shortName){
		BgPanel newPanel = new TeamDetials(shortName);
		newPanel.setBounds(30, 30, 940, 600);
		newPanel.setVisible(false);
		
		StartUI.startUI.setMenu(1);
		
		return newPanel;
	}
	
	public BgPanel turnToPlayerDetials(String name){
		BgPanel newPanel = new PlayerDetials(name);
		return newPanel;
	}
	
	//date格式为13-14_01-01
	public BgPanel turnToMatchDetials(String date,String shortName){
		MatchLogic l = new MatchLogic();
		ArrayList<MatchDataPO> pos = l.GetInfo(date, date, shortName);
		
		BgPanel newPanel = new MatchDetailPanel(pos.get(0));
		newPanel.setBounds(30, 30, 940, 600);
		newPanel.setVisible(false);
		
		StartUI.startUI.setMenu(3);
		
		return newPanel;
	}
}