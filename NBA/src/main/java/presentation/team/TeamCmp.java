package presentation.team;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.omg.CORBA.PRIVATE_MEMBER;

import bussinesslogic.match.MatchLogic;
import bussinesslogic.team.TeamLogic;
import data.po.TeamDataPO;
import data.po.teamData.TeamLData;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.HoriDynamicBarLeft;
import presentation.component.HoriDynamicBarRight;
import presentation.component.TeamImageAssist;
import presentation.contenui.UIUtil;

public class TeamCmp extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private TeamLogic teamLogic;
	private double[] dataLeft, dataRight;
	private TeamImageAssist assist = new TeamImageAssist();
	private TeamLData avgOfAllNormal;
	private ArrayList<Double> avgOffAllOff;
	private ArrayList<TeamLData> teamLDataNormal, teamLDataOff;
	private boolean isNormal;
	private JComboBox<String> seasonBox;
	private boolean isFirst;
	private String[] item = {"场均得分", "场均助攻", "场均篮板", "罚球％", "三分％"};
	private String shortName;
	private GLabel label, border;
	private HoriDynamicBarLeft[] horiDynamicBarLeft;
	private HoriDynamicBarRight[] horiDynamicBarRight;

	public TeamCmp(String teamShortName){
		super("");

		this.shortName = teamShortName;
		teamLogic = new TeamLogic();

		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);

		init();
	}
	private void init(){
		isFirst = true;

		String[] season = {"14-15", "13-14", "12-13", "11-12", "10-11", 
				"09-10", "08-09", "07-08", "06-07", "05-06"};
		seasonBox = new JComboBox<String>(season);
		seasonBox.setBounds(430, 35, 100, 30);
		seasonBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				try {
					avgOfAllNormal = teamLogic.getLAvg("unknown", seasonBox.getSelectedItem().toString(), "yes");
					teamLDataNormal = teamLogic.GetPartLInfo(shortName, seasonBox.getSelectedItem().toString(), "yes");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				dataInit();
			}
		});
		this.add(seasonBox);



		try {
			teamLDataNormal = teamLogic.GetPartLInfo(shortName, season[0], "yes");
			avgOfAllNormal = teamLogic.getLAvg("unknown", season[0], "yes");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		label = new GLabel("联盟对比", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);

		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		GLabel teamPic = new GLabel(assist.loadImageIcon("img/teams/"+shortName+
				".svg", 170, 170), new Point(80, 20), new Point(170, 170), this, true);
		GLabel vs = new GLabel("VS", new Point(454, 80), new Point(60, 60), this, true, 1, 40);
		GLabel defaultPic = new GLabel("img/teamDetials/default.png", new Point(742, 15), new Point(61, 146), this, true);
		GLabel defaultText = new GLabel("联盟平均", new Point(730, 171), new Point(200,30), this, true, 0, 20);

		horiDynamicBarLeft = new HoriDynamicBarLeft[item.length];
		horiDynamicBarRight = new HoriDynamicBarRight[item.length];

		for(int i=0;i<item.length;i++){
			GLabel label = new GLabel(item[i], new Point(430, 232+51*i), new Point(80, 40), this, true, 0, 15);
			label.setHorizontalAlignment(JLabel.CENTER);
		}

		dataInit();
	}

	private void dataInit(){
		if(!isFirst){
			for(int i=0;i<item.length;i++){
				this.remove(horiDynamicBarLeft[i]);
				this.remove(horiDynamicBarRight[i]);
			}
		}

		dataLeft = new double[item.length];
		dataRight = new double[item.length];
		
		dataRight[0] = ShortDouble(avgOfAllNormal.getPPG());
		dataRight[1] = ShortDouble(avgOfAllNormal.getAssitNumberPG());
		dataRight[2] = ShortDouble(avgOfAllNormal.getBackBoardPG());
		dataRight[3] = ShortDouble(avgOfAllNormal.getFTEff());
		dataRight[4] = ShortDouble(avgOfAllNormal.getTPEff());


		dataLeft[0] = ShortDouble(teamLDataNormal.get(0).getPPG());
		dataLeft[1] = ShortDouble(teamLDataNormal.get(0).getAssitNumberPG());
		dataLeft[2] = ShortDouble(teamLDataNormal.get(0).getBackBoardPG());
		dataLeft[3] = ShortDouble(teamLDataNormal.get(0).getTPEff());
		dataLeft[4] = ShortDouble(teamLDataNormal.get(0).getFTEff());

		for(int i=0;i<item.length;i++){
			horiDynamicBarLeft[i] = new HoriDynamicBarLeft(dataLeft[i], new Dimension(300, 40));
			horiDynamicBarLeft[i].setLocation(45, 232+51*i);
			this.add(horiDynamicBarLeft[i]);

			horiDynamicBarRight[i] = new HoriDynamicBarRight(dataRight[i], new Dimension(300, 40));
			horiDynamicBarRight[i].setLocation(545, 232+51*i);
			this.add(horiDynamicBarRight[i]);

			if(dataLeft[i] < dataRight[i]){
				horiDynamicBarLeft[i].setColor(UIUtil.bgGrey);
			}else{
				if(dataLeft[i] > dataRight[i]){
					horiDynamicBarRight[i].setColor(UIUtil.bgGrey);
				}
			}
		}

		showOut();

		this.updateUI();
	}

	public void showOut(){
		for(int i=0;i<item.length;i++){
			horiDynamicBarLeft[i].showOut();
			horiDynamicBarRight[i].showOut();
		}
		this.updateUI();
	}
	
	private double ShortDouble(double d){
		DecimalFormat df = new DecimalFormat(".00");
		return Double.parseDouble(df.format(d));
	}
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
