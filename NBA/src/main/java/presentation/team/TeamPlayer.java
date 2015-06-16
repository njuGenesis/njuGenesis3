package presentation.team;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;
import bussinesslogic.player.PlayerLogic_db;
import data.po.playerData.PlayerDetailInfo;

public class TeamPlayer extends BgPanel{

	private static final long serialVersionUID = 1L;
	private static String file = "";
	private GLabel label, border;
	private WebTable tableBasic;
	private Rectangle rectangle;
	private PlayerLogic_db playerLogic_db;
	private ArrayList<PlayerDetailInfo> playerDetailInfo;
	private TurnController turnController;
	
	public TeamPlayer(String players, String team) {
		super(file);
		
		turnController = new TurnController();
		playerLogic_db = new PlayerLogic_db();
		playerDetailInfo = new ArrayList<PlayerDetailInfo>();
		String[] names = players.split(";");
		int id[] = new int[names.length];
		try {
			for(int i=0;i<names.length;i++){
				id[i] = playerLogic_db.getIDbyName(names[i], team);
				playerDetailInfo.add(playerLogic_db.getdetail(id[i]));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		rectangle = new Rectangle(0, 80, 940, 420);
		
		label = new GLabel("球员数据", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
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
		String[] header = {"姓名", "位置", "号码", "身高", "体重", "生日", "出生地"};
		
		Object[][] data = new Object[playerDetailInfo.size()][header.length];
		for(int i=0;i<playerDetailInfo.size();i++){
			data[i][0] = playerDetailInfo.get(i).getName();
			data[i][1] = playerDetailInfo.get(i).getPosition();
			data[i][2] = playerDetailInfo.get(i).getNumber();
			data[i][3] = playerDetailInfo.get(i).getHeight();
			data[i][4] = playerDetailInfo.get(i).getWeight();
			data[i][5] = playerDetailInfo.get(i).getBirth();
			data[i][6] = playerDetailInfo.get(i).getBorncity();
		}
		
		tableBasic = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		tableBasic.setVisible(true);
		for(int i=1;i<header.length;i++){
			tableBasic.setColumDataCenter(i);
		}
		tableBasic.setColumForeground(0, UIUtil.nbaBlue);
		tableBasic.setColumHand(0);
		this.add(tableBasic);
		
		for(int i=0;i<playerDetailInfo.size();i++){
			tableBasic.getColum(0)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String name = label.getText();
					int id = 0;
					try {
						id = playerLogic_db.getIDbyName(name, "");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}System.out.println(id);
					WebFrame.frame.setPanel(turnController.turnToPlayerDetials(id), name);
				}
			});
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
