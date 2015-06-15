package presentation.player;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import bussinesslogic.player.PlayerLogic_db;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.HoriDynamicBarLeft;
import presentation.component.HoriDynamicBarRight;
import presentation.contenui.UIUtil;

public class PlayerCmp extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private PlayerLogic_db playerLogic_db;
	private double[] dataLeft, dataRight;
	private GLabel label, border;
	private ArrayList<Double> avgOfAll;
	private ArrayList<PlayerDataSeason_Avg_Basic> playerDataSeason_Avg_Basics;
	private HoriDynamicBarLeft[] horiDynamicBarLeft;
	private HoriDynamicBarRight[] horiDynamicBarRight;
	private JComboBox<String> seasonBox;
	private int id;
	private boolean isFirst;
	private String[] item = {"场均得分", "场均助攻", "场均篮板", "罚球％", "三分％"};
	

	public PlayerCmp(int id){
		super("");
		
		playerLogic_db = new PlayerLogic_db();
		this.id = id;
		
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
					avgOfAll = playerLogic_db.getAvgOfAll(seasonBox.getSelectedItem().toString(), true);
					playerDataSeason_Avg_Basics = playerLogic_db.gets_a_b(id, seasonBox.getSelectedItem().toString());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				dataInit();
			}
		});
		this.add(seasonBox);
		
		try {
			avgOfAll = playerLogic_db.getAvgOfAll(season[0], true);
			playerDataSeason_Avg_Basics = playerLogic_db.gets_a_b(id, season[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		if(avgOfAll.size()!=0){
			dataRight[0] = ShortDouble(avgOfAll.get(0));
			dataRight[1] = ShortDouble(avgOfAll.get(1));
			dataRight[2] = ShortDouble(avgOfAll.get(2));
			dataRight[3] = ShortDouble(avgOfAll.get(3));
			dataRight[4] = ShortDouble(avgOfAll.get(4));
		}else{
			dataRight[0] = 0;
			dataRight[1] = 0;
			dataRight[2] = 0;
			dataRight[3] = 0;
			dataRight[4] = 0;
		}
		
		if(playerDataSeason_Avg_Basics.size()!=0){
			dataLeft[0] = ShortDouble(Double.valueOf(playerDataSeason_Avg_Basics.get(0).getPts().equals("")?"0":playerDataSeason_Avg_Basics.get(0).getPts()));
			dataLeft[1] = ShortDouble(Double.valueOf(playerDataSeason_Avg_Basics.get(0).getAssist().equals("")?"0":playerDataSeason_Avg_Basics.get(0).getAssist()));
			dataLeft[2] = ShortDouble(Double.valueOf(playerDataSeason_Avg_Basics.get(0).getBackbound().equals("")?"0":playerDataSeason_Avg_Basics.get(0).getBackbound()));
			dataLeft[3] = ShortDouble(Double.valueOf(playerDataSeason_Avg_Basics.get(0).getFtper().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basics.get(0).getFtper().replaceAll("%", "")));
			dataLeft[4] = ShortDouble(Double.valueOf(playerDataSeason_Avg_Basics.get(0).getThper().replaceAll("%", "").equals("")?"0":playerDataSeason_Avg_Basics.get(0).getThper().replaceAll("%", "")));
		}else{
			dataLeft[0] = 0;
			dataLeft[1] = 0;
			dataLeft[2] = 0;
			dataLeft[3] = 0;
			dataLeft[4] = 0;
		}
		
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
