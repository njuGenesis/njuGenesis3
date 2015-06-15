package presentation.team;

import java.awt.Dimension;
import java.awt.Point;
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
	private ArrayList<Double> avgOfAllNormal, avgOffAllOff;
	private ArrayList<TeamLData> teamLDataNormal, teamLDataOff;
	private boolean isNormal;
	private JComboBox<String> comboBox;
	
	public TeamCmp(String teamShortName){
		super("");
		
		teamLogic = new TeamLogic();
		avgOfAllNormal = teamLogic.getAvg("yes");
		avgOffAllOff = teamLogic.getAvg("no");
		try {
			teamLDataNormal = teamLogic.GetPartLInfo(teamShortName, "14-15", "yes");
			teamLDataOff = teamLogic.GetPartLInfo(teamShortName, "14-15", "no");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		comboBox = new JComboBox<String>();
		
		this.setBounds(26, 120, 948, 530);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	private void init(){
		GLabel teamPic = new GLabel(assist.loadImageIcon("img/teams/"+//po.getShortName()+
				".svg", 170, 170), new Point(80, 20), new Point(170, 170), this, true);
		GLabel vs = new GLabel("VS", new Point(454, 80), new Point(60, 60), this, true, 1, 40);
		GLabel defaultPic = new GLabel("img/teamDetials/default.png", new Point(742, 15), new Point(61, 146), this, true);
		GLabel defaultText = new GLabel("联盟平均", new Point(730, 171), new Point(200,30), this, true, 0, 20);
		
		String[] item = {"场均得分", "场均助攻", "场均篮板", "三分％", "罚球％"};
		dataLeft = new double[item.length];
		dataRight = new double[item.length];
		
		if(isNormal){
			dataRight[0] = ShortDouble(avgOfAllNormal.get(0));
			dataRight[1] = ShortDouble(avgOfAllNormal.get(1));
			dataRight[2] = ShortDouble(avgOfAllNormal.get(2));
			dataRight[3] = ShortDouble(avgOfAllNormal.get(3)*100);
			dataRight[4] = ShortDouble(avgOfAllNormal.get(4)*100);
			
			dataLeft[0] = ShortDouble(teamLDataNormal.get(0).getPPG());
			dataLeft[1] = ShortDouble(teamLDataNormal.get(0).getAssitNumberPG());
			dataLeft[2] = ShortDouble(teamLDataNormal.get(0).getBackBoardPG());
			dataLeft[3] = ShortDouble(teamLDataNormal.get(0).getTPEff()*100);
			dataLeft[4] = ShortDouble(teamLDataNormal.get(0).getFTEff()*100);
		}else{
			dataRight[0] = ShortDouble(avgOffAllOff.get(0));
			dataRight[1] = ShortDouble(avgOffAllOff.get(1));
			dataRight[2] = ShortDouble(avgOffAllOff.get(2));
			dataRight[3] = ShortDouble(avgOffAllOff.get(3)*100);
			dataRight[4] = ShortDouble(avgOffAllOff.get(4)*100);
			
			dataLeft[0] = ShortDouble(teamLDataOff.get(0).getPPG());
			dataLeft[1] = ShortDouble(teamLDataOff.get(0).getAssitNumberPG());
			dataLeft[2] = ShortDouble(teamLDataOff.get(0).getBackBoardPG());
			dataLeft[3] = ShortDouble(teamLDataOff.get(0).getTPEff()*100);
			dataLeft[4] = ShortDouble(teamLDataOff.get(0).getFTEff()*100);
		}
		
		for(int i=0;i<item.length;i++){
			GLabel label = new GLabel(item[i], new Point(430, 232+51*i), new Point(80, 40), this, true, 0, 15);
			label.setHorizontalAlignment(JLabel.CENTER);
			
			HoriDynamicBarLeft horiDynamicBarLeft = new HoriDynamicBarLeft(dataLeft[i], new Dimension(300, 40));
			horiDynamicBarLeft.setLocation(45, 232+51*i);
			this.add(horiDynamicBarLeft);

			HoriDynamicBarRight horiDynamicBarRight = new HoriDynamicBarRight(dataRight[i], new Dimension(300, 40));
			horiDynamicBarRight.setLocation(545, 232+51*i);
			this.add(horiDynamicBarRight);

			if(dataLeft[i] < dataRight[i]){
				horiDynamicBarLeft.setColor(UIUtil.bgGrey);
			}else{
				if(dataLeft[i] > dataRight[i]){
					horiDynamicBarRight.setColor(UIUtil.bgGrey);
				}
			}
			
			horiDynamicBarLeft.showOut();
			horiDynamicBarRight.showOut();
			
			this.repaint();
		}
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
