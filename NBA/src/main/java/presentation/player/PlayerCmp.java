package presentation.player;

import java.awt.Dimension;
import java.awt.Point;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import bussinesslogic.player.PlayerLogic;
import data.po.PlayerDataPO;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.HoriDynamicBarLeft;
import presentation.component.HoriDynamicBarRight;
import presentation.component.TeamImageAssist;
import presentation.contenui.UIUtil;

public class PlayerCmp extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private PlayerDataPO po;
	private PlayerLogic playerLogic = new PlayerLogic();
	private double[] dataLeft, dataRight;
	private TeamImageAssist assist = new TeamImageAssist();
	private GLabel label, border;

	public PlayerCmp(PlayerDataPO po){
		super("");
		
//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {}
		
		this.setBounds(0, 1500, 940, 530);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);
		this.po = po;
		
		init();
	}
	
	private void init(){
		label = new GLabel("联盟对比", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);
		
		GLabel teamPic = new GLabel("img/portrait/"+//po.getName()+
				".png", new Point(80, 20), new Point(172, 139), this, true);
		GLabel vs = new GLabel("VS", new Point(454, 80), new Point(60, 60), this, true, 1, 40);
		GLabel defaultPic = new GLabel("img/teamDetials/default.png", new Point(742, 15), new Point(61, 146), this, true);
		GLabel defaultText = new GLabel("联盟平均", new Point(730, 171), new Point(200,30), this, true, 0, 20);
		
		String[] item = {"场均得分", "场均助攻", "场均篮板", "三分％", "罚球％"};
		dataLeft = new double[item.length];
		dataRight = new double[item.length];
		
		Double[] rightList = playerLogic.getSeasonAverage(playerLogic.getLatestSeason());
		dataRight[0] = ShortDouble(rightList[0]);
		dataRight[1] = ShortDouble(rightList[1]);
		dataRight[2] = ShortDouble(rightList[2]);
		dataRight[3] = ShortDouble(rightList[3]*100);
		dataRight[4] = ShortDouble(rightList[4]*100);
		
//		dataLeft[0] = ShortDouble(po.getPPG());
//		dataLeft[1] = ShortDouble(po.getAPG());
//		dataLeft[2] = ShortDouble(po.getBPG());
//		dataLeft[3] = ShortDouble(po.getThreePGPercentage()*100);
//		dataLeft[4] = ShortDouble(po.getFTPercentage()*100);
		
		dataLeft[0] = ShortDouble(rightList[0]);
		dataLeft[1] = ShortDouble(rightList[1]);
		dataLeft[2] = ShortDouble(rightList[2]);
		dataLeft[3] = ShortDouble(rightList[3]*100);
		dataLeft[4] = ShortDouble(rightList[4]*100);
		
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
