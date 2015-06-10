package presentation.player;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.JTextArea;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.TeamImageAssist;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import data.po.playerData.PlayerDetailInfo;

public class PlayerInfo extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private static String file = "img/playerDetials/info.png";
	private TeamImageAssist assist;
	private GLabel playerPic, teamPic, number, position, height, weight, birthday, age, exp;
	private JTextArea borncity;
	
	private PlayerDetailInfo playerDetailInfo;
	private PlayerLogic_db playerLogic_db;

	public PlayerInfo(String name, String team) {
		super(file);
		
		playerLogic_db = new PlayerLogic_db();
		try {
			playerDetailInfo = playerLogic_db.getdetail(playerLogic_db.getIDbyName(name, team));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		this.setBounds(0, 50, 940, 550);
		this.setBackground(UIUtil.bgWhite);
		this.setLayout(null);
		this.setVisible(true);
		init();
	}

	private void init(){
		assist = new TeamImageAssist();

		playerPic = new GLabel("img/action/"//+po.getName()
				+".png", new Point(276, 2), new Point(330, 525), this, true);
		teamPic = new GLabel(""//assist.loadImageIcon("img/teams/"+po.getTeamName()+".svg", 150, 150)
				, new Point(47, 42), new Point(150, 150), this, true);
		teamPic.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				teamPic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e){
//				String teamName = playerDetailInfo.getTeamName();
//				TurnController turnController = new TurnController();
//				StartUI.startUI.turn(turnController.turnToTeamDetials(teamName));
			}
		});
		number = new GLabel(playerDetailInfo.getNumber(), new Point(163, 408), new Point(200, 25), this, true, 0, 18);
		position = new GLabel(TableUtility.getChPosition(playerDetailInfo.getPosition())+" "+playerDetailInfo.getPosition(), 
				new Point(160, 438), new Point(200, 25), this, true, 0, 18);
		height = new GLabel(playerDetailInfo.getHeight(), new Point(718, 147), new Point(200, 25), this, true, 0, 18);
		weight = new GLabel(String.valueOf(playerDetailInfo.getWeight()), new Point(718, 180), new Point(200, 25), this, true, 0, 18);
		birthday = new GLabel(playerDetailInfo.getBirth(), new Point(718, 213), new Point(200, 25), this, true, 0, 18);
//		age = new GLabel(String.valueOf(playerDetailInfo.getAge()), new Point(718, 244), new Point(200, 25), this, true, 0, 18);
//		exp = new GLabel(String.valueOf(playerDetailInfo.getExp()), new Point(718, 275), new Point(200, 25), this, true, 0, 18);
		borncity = new JTextArea();
		borncity.setEditable(false);
		borncity.setLineWrap(true);
		borncity.setWrapStyleWord(true);
		borncity.setBounds(747, 308, 200, 50);
		borncity.setText(playerDetailInfo.getBorncity());
		borncity.setFont(new Font("微软雅黑", 0, 18));
		borncity.setBorder(null);
		borncity.setBackground(UIUtil.bgWhite);
		borncity.setOpaque(false);
		this.add(borncity);
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
