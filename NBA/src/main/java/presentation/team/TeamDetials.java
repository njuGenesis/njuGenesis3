package presentation.team;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import bussinesslogic.player.PlayerLogic;
import bussinesslogic.team.TeamLogic;
import data.po.TeamDataPO;
import data.po.teamData.TeamBaseInfo;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TableUtility;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;

public class TeamDetials extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private GLabel title, borderUp;
	private TeamBaseInfo teamBaseInfo;
	
	public TeamDetials(TeamBaseInfo teamBaseInfo){
		super("");
		
		this.teamBaseInfo = teamBaseInfo;
		
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setBounds(0, 0, 940, 2000);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		title = new GLabel("  "+"name"//po.getName()
				, new Point(0, 4), new Point(940, 46), this, true, 0, 25);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);
		
		borderUp = new GLabel("", new Point(0, 0), new Point(940, 4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);
		
		TeamPlayer teamPlayer = new TeamPlayer(teamBaseInfo.getPlayers(), TableUtility.getChTeam(teamBaseInfo.getShortName()));
		TeamInfo teamInfo = new TeamInfo(teamBaseInfo);
		TeamData teamData = new TeamData(teamBaseInfo.getShortName());
//		TeamMatch teamMatch = new TeamMatch(shortName);
//		TeamCmp teamCmp = new TeamCmp(shortName);
		
		this.add(teamPlayer);
		this.add(teamInfo);
		this.add(teamData);
//		this.add(teamMatch);
//		this.add(teamCmp);
	}
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
			
		}
	}
}
