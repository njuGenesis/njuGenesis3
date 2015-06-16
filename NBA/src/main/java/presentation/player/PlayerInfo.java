package presentation.player;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JTextArea;

import bussinesslogic.player.PlayerLogic_db;
import bussinesslogic.team.TeamLogic;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.TeamImageAssist;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;
import data.po.playerData.PlayerDetailInfo;

public class PlayerInfo extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private static String file = "img/playerDetials/info.png";
	private TeamImageAssist assist;
	private GLabel playerPic, teamPic, number, position, height, weight, birthday, chName, exp;
	private JTextArea borncity;
	
	private PlayerDetailInfo playerDetailInfo;
	private PlayerLogic_db playerLogic_db;
	private TeamLogic teamLogic = new TeamLogic();
	private int id;

	public PlayerInfo(int id) {
		super(file);
		
		this.id = id;
		playerLogic_db = new PlayerLogic_db();
		try {
			playerDetailInfo = playerLogic_db.getdetail(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		this.setBounds(0, 100, 940, 500);
		this.setBackground(UIUtil.bgWhite);
		this.setLayout(null);
		this.setVisible(true);
		init();
	}

	private void init(){
		assist = new TeamImageAssist();

		playerPic = new GLabel("img/players/"+id+".jpg", new Point(350, 155), new Point(180, 180), this, true);
		teamPic = new GLabel(assist.loadImageIcon("img/teams/"+TableUtility.getChTeam(playerDetailInfo.getTeam())+".svg", 150, 150)
				, new Point(47, 42), new Point(150, 150), this, true);
		teamPic.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				teamPic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			public void mousePressed(MouseEvent e){
				String teamName = TableUtility.getChTeam(playerDetailInfo.getTeam());
				TurnController turnController = new TurnController();
				try {
					WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamLogic.GetTeamBaseInfo("14-15", teamName).get(0)), teamName);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		number = new GLabel(playerDetailInfo.getNumber(), new Point(163, 408), new Point(200, 25), this, true, 0, 15);
		position = new GLabel(playerDetailInfo.getPosition(), new Point(160, 438), new Point(200, 25), this, true, 0, 15);
		height = new GLabel(playerDetailInfo.getHeight(), new Point(718, 147), new Point(200, 25), this, true, 0, 15);
		weight = new GLabel(String.valueOf(playerDetailInfo.getWeight()), new Point(718, 180), new Point(200, 25), this, true, 0, 15);
		birthday = new GLabel(playerDetailInfo.getBirth(), new Point(718, 212), new Point(200, 25), this, true, 0, 15);
		chName = new GLabel(String.valueOf(playerDetailInfo.getNameCn()), new Point(735, 245), new Point(200, 25), this, true, 0, 15);
		borncity = new JTextArea();
		borncity.setEditable(false);
		borncity.setLineWrap(true);
		borncity.setWrapStyleWord(true);
		borncity.setBounds(735, 279, 200, 50);
		borncity.setText(playerDetailInfo.getBorncity());
		borncity.setFont(new Font("微软雅黑", 0, 15));
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
