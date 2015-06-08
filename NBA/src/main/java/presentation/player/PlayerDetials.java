package presentation.player;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import bussinesslogic.player.PlayerLogic;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;
import data.po.PlayerDataPO;

public class PlayerDetials extends BgPanel{
	private static final long serialVersionUID = 1L;
	private GLabel title, borderUp, borderDown, borderMid;
	private SelectLabel tdMenu[];
	private PlayerDataPO po;
	private BgPanel sonPanel;
	private PlayerLogic playerLogic = new PlayerLogic();
	private String name;
	
	public PlayerDetials(final String name){
		super("");
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}
		
		//this.po = playerLogic.getInfo(name, playerLogic.getLatestSeason());
		this.name = name;
		
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		
		init();
	}
	
	private void init(){
		title = new GLabel("  "+"name"//po.getName()
				, new Point(0, 4), new Point(940, 52), this, true, 0, 25);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);
		
		borderUp = new GLabel("", new Point(0, 0), new Point(940, 4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		tdMenu = new SelectLabel[4];
		tdMenu[0] = new SelectLabel("资料", new Point(84, 56), new Point(60, 30), this, true, 0, 20);
		tdMenu[1] = new SelectLabel("数据", new Point(84+235+2, 56), new Point(60, 30), this, true, 0, 20);
		tdMenu[2] = new SelectLabel("比赛", new Point(84+(235+2)*2, 56), new Point(60, 30), this, true, 0, 20);
		tdMenu[3] = new SelectLabel("对比", new Point(84+(235+2)*3, 56), new Point(60, 30), this, true, 0, 20);
		
		borderDown = new GLabel("", new Point(0, 69), new Point(940, 4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);
		
		tdMenu[0].setSelected(true);
		PlayerInfo info = new PlayerInfo(PlayerDetials.this.po);
		sonPanel = info;
		this.add(sonPanel);
		
		tdMenu[0].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for(int i=0;i<tdMenu.length;i++){
					tdMenu[i].setSelected(false);
				}
				tdMenu[0].setSelected(true);
				PlayerInfo info = new PlayerInfo(PlayerDetials.this.po);
				PlayerDetials.this.remove(sonPanel);
				sonPanel = info;
				PlayerDetials.this.add(sonPanel);
				repaint();
			}
		});
		tdMenu[1].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for(int i=0;i<tdMenu.length;i++){
					tdMenu[i].setSelected(false);
				}
				tdMenu[1].setSelected(true);
				PlayerDataPO[] pos = playerLogic.getAllSeasonInfo(name);
				PlayerData playerData = new PlayerData(pos);
				PlayerDetials.this.remove(sonPanel);
				sonPanel = playerData;
				PlayerDetials.this.add(sonPanel);
				repaint();
			}
		});
		
		tdMenu[2].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for(int i=0;i<tdMenu.length;i++){
					tdMenu[i].setSelected(false);
				}
				tdMenu[2].setSelected(true);
				PlayerDataPO[] pos = playerLogic.getAllSeasonInfo(name);
				PlayerMatch playerMatch = new PlayerMatch(po);
				PlayerDetials.this.remove(sonPanel);
				sonPanel = playerMatch;
				PlayerDetials.this.add(sonPanel);
				repaint();
			}
		});
		
		tdMenu[3].addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for(int i=0;i<tdMenu.length;i++){
					tdMenu[i].setSelected(false);
				}
				tdMenu[3].setSelected(true);
				PlayerCmp playerCmp = new PlayerCmp(PlayerDetials.this.po);
				PlayerDetials.this.remove(sonPanel);
				sonPanel = playerCmp;
				PlayerDetials.this.add(sonPanel);
				repaint();
			}
		});
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			
//			sonPanel.refreshUI();
//			sonPanel.setVisible(true);
			this.removeAll();
			//this.add(sonPanel);
			this.init();
			
		}
	}
}
