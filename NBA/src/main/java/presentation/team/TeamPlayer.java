package presentation.team;

import java.awt.Rectangle;
import java.rmi.RemoteException;
import java.util.ArrayList;
import presentation.component.BgPanel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import bussinesslogic.player.PlayerLogic_db;
import data.po.playerData.PlayerDetailInfo;

public class TeamPlayer extends BgPanel{

	private static final long serialVersionUID = 1L;
	private static String file = "";
	private WebTable tableBasic;
	private Rectangle rectangle;
	private PlayerLogic_db playerLogic_db;
	private ArrayList<PlayerDetailInfo> playerDetailInfo;
	
	public TeamPlayer(String players, String team) {
		super(file);
		
		playerLogic_db = new PlayerLogic_db();

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
		this.setBounds(0, 600, 940, 400);
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		rectangle = new Rectangle(50, 40, 840, 360);

		basicSetting();
		tableBasic.setVisible(true);

		repaint();
	}
	
	private void basicSetting(){
		String[] header = {"姓名", "位置", "号码", "身高", "体重", "生日", "出生地"};
		
		Object[][] data = new Object[playerDetailInfo.size()][header.length];
		for(int i=0;i<playerDetailInfo.size();i++){
			data[i][0] = playerDetailInfo.get(i).getName();
			data[i][0] = playerDetailInfo.get(i).getPosition();
			data[i][0] = playerDetailInfo.get(i).getNumber();
			data[i][0] = playerDetailInfo.get(i).getHeight();
			data[i][0] = playerDetailInfo.get(i).getWeight();
			data[i][0] = playerDetailInfo.get(i).getBirth();
			data[i][0] = playerDetailInfo.get(i).getBorncity();
		}
		
		tableBasic = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		tableBasic.setVisible(false);
		this.add(tableBasic);
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
