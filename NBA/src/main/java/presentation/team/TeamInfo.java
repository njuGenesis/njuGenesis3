package presentation.team;

import java.awt.Font;
import java.awt.Point;
import javax.swing.JTextArea;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.TeamImageAssist;
import presentation.contenui.UIUtil;
import data.po.teamData.TeamBaseInfo;

public class TeamInfo extends BgPanel{

	private static final long serialVersionUID = 1L;
	private static String file = "img/teamDetials/info.png";
	
	private TeamImageAssist assist;
	private GLabel teamPic, name, shortName, city, time, position;
	private JTextArea place;
	private TeamBaseInfo teamBaseInfo;
	
	public TeamInfo(TeamBaseInfo teamBaseInfo){
		super(file);
		
		this.teamBaseInfo = teamBaseInfo;
		
		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setVisible(true);
		
		assist = new TeamImageAssist();
		
		init();
	}
	private void init(){
		teamPic = new GLabel(assist.loadImageIcon("img/teams/"+teamBaseInfo.getShortName()+".svg", 360, 360), new Point(281, 60), new Point(360, 360), this, true);
		name = new GLabel(teamBaseInfo.getName(), new Point(103, 40), new Point(180, 25), this, true, 0, 20);
		shortName = new GLabel(teamBaseInfo.getShortName(), new Point(103, 75), new Point(180, 25), this, true, 0, 20);
		city = new GLabel(teamBaseInfo.getCity(), new Point(733, 144), new Point(180, 25), this, true, 0, 20);
		time = new GLabel(String.valueOf(teamBaseInfo.getBuildyear()), new Point(762, 179), new Point(180, 25), this, true, 0, 20);
		position = new GLabel(teamBaseInfo.getEorW()+"-"+teamBaseInfo.getArea(), new Point(246, 467), new Point(180, 25), this, true, 0, 20);
		place = new JTextArea();
		place.setEditable(false);
		place.setLineWrap(true);
		place.setWrapStyleWord(true);
		place.setBounds(733, 211, 200, 50);
		place.setText(teamBaseInfo.getMainposition());
		place.setFont(new Font("微软雅黑", 0, 20));
		place.setOpaque(false);
		place.setBackground(UIUtil.bgWhite);
		place.setBorder(null);
		place.setLineWrap(true);
		place.setWrapStyleWord(true);
		this.add(place);
	}
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
