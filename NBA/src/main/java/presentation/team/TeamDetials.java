package presentation.team;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
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
	private GLabel title, borderUp, borderDown;
	private TeamBaseInfo teamBaseInfo;
	private SelectLabel[] selectLabels;
	private String[] functions = {"基本信息", "球员数据", "赛季数据", "比赛数据", "联盟对比", "统计分析"};
	private JPanel[] panels;
	
	public TeamDetials(TeamBaseInfo teamBaseInfo){
		super("");
		
		this.teamBaseInfo = teamBaseInfo;
		
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setBounds(0, 0, 940, 600);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		title = new GLabel("  "+teamBaseInfo.getName()
				, new Point(0, 4), new Point(940, 42), this, true, 0, 25);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);
		
		borderUp = new GLabel("", new Point(0, 0), new Point(940, 4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);
		
		borderDown = new GLabel("", new Point(0, 46), new Point(940, 4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);
		
		int width = 940/functions.length;
		
		selectLabels = new SelectLabel[functions.length];
		for(int i=0;i<functions.length;i++){
			if(i<functions.length-1){
				selectLabels[i] = new SelectLabel(functions[i], new Point(i*width, 50), new Point(width, 50), this, true, 0, 15);
			}else{
				selectLabels[i] = new SelectLabel(functions[i], new Point(i*width, 50), new Point(940-i*width, 50), this, true, 0, 15);
			}
			selectLabels[i].setNumber(i);
			selectLabels[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					SelectLabel selectLabel = (SelectLabel)e.getSource();
					setFalse();
					selectLabel.setSelected(true);
					panels[selectLabel.getNumber()].setVisible(true);
				}
			});
		}
		
		panels = new JPanel[functions.length];
		
		TeamInfo teamInfo = new TeamInfo(teamBaseInfo);
		TeamPlayer teamPlayer = new TeamPlayer(teamBaseInfo.getPlayers(), TableUtility.getChTeam(teamBaseInfo.getShortName()));
		TeamData teamData = new TeamData(teamBaseInfo.getShortName());
		TeamMatch teamMatch = new TeamMatch(teamBaseInfo.getShortName());
		TeamCmp teamCmp = new TeamCmp(teamBaseInfo.getShortName());
		TeamBarChart teamBarChart = new TeamBarChart(teamBaseInfo.getPlayers());
		
		this.add(teamInfo);
		this.add(teamPlayer);
		this.add(teamData);
		this.add(teamMatch);
		this.add(teamCmp);
		this.add(teamBarChart);
		
		panels[0] = teamInfo;
		panels[1] = teamPlayer;
		panels[2] = teamData;
		panels[3] = teamMatch;
		panels[4] = teamCmp;
		panels[5] = teamBarChart;
		
		setFalse();
		panels[0].setVisible(true);
		selectLabels[0].setSelected(true);
	}
	
	private void setFalse(){
		for(int i=0;i<functions.length;i++){
			selectLabels[i].setSelected(false);
			panels[i].setVisible(false);
		}
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
			
		}
	}
}
