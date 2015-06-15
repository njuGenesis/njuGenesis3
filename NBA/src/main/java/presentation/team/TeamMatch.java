package presentation.team;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
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
	private JComboBox<String> comboBox;
	private GLabel border, label;
	
	public TeamMatch(String teamShortName) {
		super(file);
		
		this.teamShortName = teamShortName;
		matchLogic = new MatchLogic();
		matchTeam = matchLogic.GetTeamMatch(teamShortName, "unknown", "unknown");
		Vector<String> season = new Vector<String>();
		for(int i=0;i<matchTeam.size();i++){
			for(int j=0;j<season.size();j++){
				if(matchTeam.get(i).getSeason().equals(season.get(i))){
					continue;
				}
				season.add(matchTeam.get(i).getSeason());
			}
		}
		for(int i=0;i<season.size()-1;i++){
			for(int j=0;j<season.size()-1;j++){
				String a = season.get(j);
				String b = season.get(j+1);
				if(a.compareTo(b)<0){
					season.set(j, b);
					season.set(j+1, a);
				}
			}
		}
		
		matchTeam = matchLogic.GetTeamMatch(teamShortName, "unknown", season.get(0));
		
		comboBox = new JComboBox<String>(season);
		comboBox.setBounds(600, 0, 100, 30);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				matchTeam = matchLogic.GetTeamMatch(TeamMatch.this.teamShortName, "unknown", comboBox.getSelectedItem().toString());
				basicSetting();
			}
		});
		
		this.setBounds(26, 120, 948, 530);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		rectangle = new Rectangle(14, 50, 920, 460);

		label = new GLabel("比赛信息", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
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
		String header[] = {"赛季", "日期", "客－主", "结果", "比分", "比赛链接"};
		
		Object[][] data = new Object[matchTeam.size()][header.length];
		for(int i=0;i<matchTeam.size();i++){
			data[i][0] = matchTeam.get(i).getSeason();
			data[i][0] = matchTeam.get(i).getDate();
			data[i][0] = matchTeam.get(i).getTwoteam();
			data[i][0] = "?";
			data[i][0] = matchTeam.get(i).getResult();
			data[i][0] = (String)("比赛链接"+matchTeam.get(i).getMatchID());
		}
		
		table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table.setVisible(true);
		this.add(table);
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
