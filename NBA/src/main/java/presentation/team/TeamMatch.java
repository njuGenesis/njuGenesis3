package presentation.team;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import bussinesslogic.match.MatchLogic;
import data.po.matchData.MatchTeam;

public class TeamMatch extends BgPanel{

	private static final long serialVersionUID = 1L;
	private static String file = "";
	private MatchLogic matchLogic;
	private WebTable table;
	private Rectangle rectangle;
	private ArrayList<MatchTeam> matchTeam;
	private String teamShortName;
	private JComboBox<String> comboBoxSeason, comboBoxNormal;
	private boolean isFirst;
	private GLabel border, label;
	
	public TeamMatch(String teamShortName) {
		super(file);
		
		this.teamShortName = teamShortName;
		matchLogic = new MatchLogic();
		matchTeam = matchLogic.GetTeamMatch(teamShortName, "unknown", "unknown");
		String[] season = {"14-15", "13-14", "12-13", "11-12", "10-11", 
				"09-10", "08-09", "07-08", "06-07", "05-06"};
		String[] normal = {"常规赛", "季后赛"};
		
		matchTeam = matchLogic.GetTeamMatch(teamShortName, "yes", season[0]);
		
		isFirst = true;
		
		comboBoxSeason = new JComboBox<String>(season);
		comboBoxSeason.setBounds(680, 35, 100, 30);
		comboBoxSeason.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxNormal.getSelectedIndex() == 0){
					matchTeam = matchLogic.GetTeamMatch(TeamMatch.this.teamShortName, "yes", comboBoxSeason.getSelectedItem().toString());
				}else{
					matchTeam = matchLogic.GetTeamMatch(TeamMatch.this.teamShortName, "no", comboBoxSeason.getSelectedItem().toString());
				}
				basicSetting();
			}
		});
		this.add(comboBoxSeason);
		
		comboBoxNormal = new JComboBox<String>(normal);
		comboBoxNormal.setBounds(800, 35, 100, 30);
		comboBoxNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxNormal.getSelectedIndex() == 0){
					matchTeam = matchLogic.GetTeamMatch(TeamMatch.this.teamShortName, "yes", comboBoxSeason.getSelectedItem().toString());
				}else{
					matchTeam = matchLogic.GetTeamMatch(TeamMatch.this.teamShortName, "no", comboBoxSeason.getSelectedItem().toString());
				}
				basicSetting();
			}
		});
		this.add(comboBoxNormal);
		
		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		rectangle = new Rectangle(0, 80, 940, 420);

		label = new GLabel("比赛数据", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);
		
		basicSetting();
		
		repaint();
	}
	
	private void basicSetting(){
		if(!isFirst)this.remove(table);
		
		String header[] = {"赛季", "日期", "客－主", "结果", "比分", "比赛链接"};
		
		Object[][] data = new Object[matchTeam.size()][header.length];
		for(int i=0;i<matchTeam.size();i++){
			data[i][0] = matchTeam.get(i).getSeason();
			data[i][1] = matchTeam.get(i).getDate();
			data[i][2] = matchTeam.get(i).getTwoteam();
			String team = matchTeam.get(i).getTeamShortName();
			String[] teams = matchTeam.get(i).getTwoteam().split("-");
			int k = 0;
			if(team.equals(teams[1])){
				k = 1;
			}
			String[] points = matchTeam.get(i).getResult().split("-");
			if(Integer.valueOf(points[0])>Integer.valueOf(points[1])){
				if(k == 0){
					data[i][3] = "胜";
				}else{
					data[i][3] = "负";
				}
			}else{
				if(k == 1){
					data[i][3] = "胜";
				}else{
					data[i][3] = "负";
				}
			}
			data[i][4] = matchTeam.get(i).getResult();
			data[i][5] = (String)(matchTeam.get(i).getMatchID());
		}
		
		table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table.setVisible(true);
		for (int i = 0; i < header.length; i++) {
			table.setColumDataCenter(i);
		}
		table.setColumForeground(5, UIUtil.nbaBlue);
		table.setColumHand(5);
		this.add(table);
		
		this.updateUI();
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
