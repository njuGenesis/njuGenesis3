package presentation.team;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import bussinesslogic.team.TeamLogic;
import data.po.teamData.TeamBaseInfo;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TurnController;
import presentation.mainui.StartUI;

public class TeamUI extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "img/team/teamAllBg.png";
	private GLabel[] team = new GLabel[30];
	private TurnController turnController;
	
	private TeamLogic teamLogic;
	private ArrayList<TeamBaseInfo> teamBaseInfo;

	public TeamUI(){
		super(bg);
		
//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {}
		
		teamLogic = new TeamLogic();
		try {
			teamBaseInfo = teamLogic.GetAllBaseInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		turnController = new TurnController();
		init();
	}

	private void init(){
		ArrayList<ArrayList<TeamBaseInfo>> teamDataPOArea = setTeamDataPOArea(teamBaseInfo);

		for(int i = 0; i<30; i++){
			if(i>=0&&i<=4){
				team[i] = new GLabel(getFileAddress(teamDataPOArea.get(0).get(i)), new Point(52, 71+i*41), new Point(147, 28), this, true, teamDataPOArea.get(0).get(i));//25, 25
			}else{
				if(i>=5&&i<=9){
					team[i] = new GLabel(getFileAddress(teamDataPOArea.get(1).get(i-5)), new Point(303, 71+(i-5)*41), new Point(147, 28), this, true, teamDataPOArea.get(1).get(i-5));
				}else{
					if(i>=10&&i<=14){
						team[i] = new GLabel(getFileAddress(teamDataPOArea.get(2).get(i-10)), new Point(52, 377+(i-10)*41), new Point(147, 28), this, true, teamDataPOArea.get(2).get(i-10));
					}else{
						if(i>=15&&i<=19){
							team[i] = new GLabel(getFileAddress(teamDataPOArea.get(3).get(i-15)), new Point(783, 71+(i-15)*41), new Point(147, 28), this, true, teamDataPOArea.get(3).get(i-15));
						}else{
							if(i>=20&&i<=24){
								team[i] = new GLabel(getFileAddress(teamDataPOArea.get(4).get(i-20)), new Point(532, 377+(i-20)*41), new Point(147, 28), this, true, teamDataPOArea.get(4).get(i-20));
							}else{
								if(i>=25&&i<=29){
									team[i] = new GLabel(getFileAddress(teamDataPOArea.get(5).get(i-25)), new Point(783, 377+(i-25)*41), new Point(147, 28), this, true, teamDataPOArea.get(5).get(i-25));
								}
							}
						}
					}
				}
			}
			team[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mousePressed(MouseEvent e) {
				GLabel button = (GLabel)e.getSource();
				StartUI.startUI.turn(turnController.turnToTeamDetials(button.teamBaseInfo));
			}
		};

		for(int i = 0; i<30; i++){
			team[i].addMouseListener(mouseAdapter);
		}

	}


	private ArrayList<ArrayList<TeamBaseInfo>> setTeamDataPOArea(ArrayList<TeamBaseInfo> teamBaseInfo){
		ArrayList<TeamBaseInfo> Southeast = new ArrayList<TeamBaseInfo>();
		ArrayList<TeamBaseInfo> Central = new ArrayList<TeamBaseInfo>();
		ArrayList<TeamBaseInfo> Atlantic = new ArrayList<TeamBaseInfo>();
		ArrayList<TeamBaseInfo> Southwest = new ArrayList<TeamBaseInfo>();
		ArrayList<TeamBaseInfo> Northwest = new ArrayList<TeamBaseInfo>();
		ArrayList<TeamBaseInfo> Pacific = new ArrayList<TeamBaseInfo>();
		for(int i = 0; i<30; i++){
			if(teamBaseInfo.get(i).getArea().equals("Southeast")){
				Southeast.add(teamBaseInfo.get(i));
			}else{
				if(teamBaseInfo.get(i).getArea().equals("Central")){
					Central.add(teamBaseInfo.get(i));
				}else{
					if(teamBaseInfo.get(i).getArea().equals("Atlantic")){
						Atlantic.add(teamBaseInfo.get(i));
					}else{
						if(teamBaseInfo.get(i).getArea().equals("Southwest")){
							Southwest.add(teamBaseInfo.get(i));
						}else{
							if(teamBaseInfo.get(i).getArea().equals("Northwest")){
								Northwest.add(teamBaseInfo.get(i));
							}else{
								if(teamBaseInfo.get(i).getArea().equals("Pacific")){
									Pacific.add(teamBaseInfo.get(i));
								}
							}
						}
					}
				}
			}
		}
		ArrayList<ArrayList<TeamBaseInfo>> setAera = new ArrayList<ArrayList<TeamBaseInfo>>();
		setAera.add(Southeast);
		setAera.add(Central);
		setAera.add(Atlantic);
		setAera.add(Southwest);
		setAera.add(Northwest);
		setAera.add(Pacific);
		return setAera;
	}

	/*
	 * data:
	 * 球队的所有简称
	 */
	private String getFileAddress(TeamBaseInfo teamBaseInfo){
		String fileAddress = "img/teamName/"+teamBaseInfo.getShortName()+".png";
		return fileAddress;
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
