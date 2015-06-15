package presentation.player;

import java.awt.Point;
import java.rmi.RemoteException;
import bussinesslogic.player.PlayerLogic_db;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;

public class PlayerDetials extends BgPanel{
	private static final long serialVersionUID = 1L;
	private GLabel title, borderUp;
	private int id;
	
	public PlayerDetials(int id){
		super("");
		
		this.id = id;
		
		this.setBounds(0, 0, 940, 3000);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		title = new GLabel("  "+"name"//po.getName()
				, new Point(0, 4), new Point(940, 46), this, true, 0, 25);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);
		
		borderUp = new GLabel("", new Point(0, 0), new Point(940, 4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		PlayerInfo info = new PlayerInfo(id);
		PlayerData data = new PlayerData(id);
		PlayerMatch match = new PlayerMatch(id);
//		PlayerCmp cmp = new PlayerCmp(id);
		PlayerCrosshairOverlay cross = new PlayerCrosshairOverlay(id);
		
		this.add(info);
		this.add(data);
		this.add(match);
//		this.add(cmp);
		this.add(cross);
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
			
		}
	}
}
