package presentation.player;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import data.po.PlayerDataPO;

public class PlayerData extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private PlayerDataPO[] pos;
	private WebTable basicTable,totalTable,  pgTable, effTable;
	private JCheckBox checkBox1, checkBox2, checkBox3, checkBox4;
	private Rectangle rectangle;
	private GLabel label, border;

	public PlayerData(PlayerDataPO[] pos) {
		super("");
		
		this.pos = pos;
		this.setBounds(0, 600, 940, 400);
		this.setLayout(null);
		this.setVisible(true);
		this.setOpaque(false);
		this.setBackground(UIUtil.bgWhite);
		
		init();
	}

	private void init(){
		rectangle = new Rectangle(50, 40, 840, 360);
		
		label = new GLabel("赛季信息", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		basicSetting();
		totalSetting();
		pgSetting();
		effSetting();

		checkBox1 = new JCheckBox("总览");
		checkBox1.setBounds(600, 0, 70, 30);
		checkBox1.setSelected(true);
		this.add(checkBox1);
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox1.isSelected()){
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);
					pgTable.setVisible(false);
					effTable.setVisible(false);
					totalTable.setVisible(false);
					basicTable.setVisible(true);
				}else{
					checkBox1.setSelected(true);
				}
			}
		});
		
		checkBox2 = new JCheckBox("总计");
		checkBox2.setBounds(670, 0, 70, 30);
		this.add(checkBox2);
		checkBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox2.isSelected()){
					checkBox1.setSelected(false);
					checkBox3.setSelected(false);
					checkBox4.setSelected(false);
					pgTable.setVisible(false);
					effTable.setVisible(false);
					basicTable.setVisible(false);
					totalTable.setVisible(true);
				}else{
					checkBox2.setSelected(true);
				}
			}
		});
		
		checkBox3 = new JCheckBox("场均");
		checkBox3.setBounds(740, 0, 70, 30);
		this.add(checkBox3);
		checkBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox3.isSelected()){
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox4.setSelected(false);
					basicTable.setVisible(false);
					effTable.setVisible(false);
					totalTable.setVisible(false);
					pgTable.setVisible(true);
				}else{
					checkBox3.setSelected(true);
				}
			}
		});
		
		checkBox4 = new JCheckBox("效率");
		checkBox4.setBounds(810, 0, 70, 30);
		this.add(checkBox4);
		checkBox4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox4.isSelected()){
					checkBox1.setSelected(false);
					checkBox2.setSelected(false);
					checkBox3.setSelected(false);
					basicTable.setVisible(false);
					pgTable.setVisible(false);
					totalTable.setVisible(false);
					effTable.setVisible(true);
				}else{
					checkBox4.setSelected(true);
				}
			}
		});
	}
	
	private void totalSetting(){
		String[] header = {"赛季", "进攻篮板", "防守篮板", "总篮板", "助攻", "投篮", 
				"命中", "三分", "三分命中", "罚球", "罚球命中", "进攻", "防守", "抢断", "盖帽", "失误", "犯规"};
		
		Object[][] data = new Object[pos.length][header.length];
		for(int i=0;i<pos.length;i++){
			data[i][0] = pos[pos.length-1-i].getSeason();
			data[i][1] = pos[pos.length-1-i].getOffb();
			data[i][2] = pos[pos.length-1-i].getDefb();
			data[i][3] = pos[pos.length-1-i].getBackboard();
			data[i][4] = pos[pos.length-1-i].getAssist();
			data[i][5] = pos[pos.length-1-i].getTotalFieldGoal();
			data[i][6] = pos[pos.length-1-i].getFieldGoal();
			data[i][7] = pos[pos.length-1-i].getTotalThreeGoal();
			data[i][8] = pos[pos.length-1-i].getThreeGoal();
			data[i][9] = pos[pos.length-1-i].getTotalFT();
			data[i][10] = pos[pos.length-1-i].getFT();
			data[i][11] = pos[pos.length-1-i].getOff();
			data[i][12] = pos[pos.length-1-i].getDef();
			data[i][13] = pos[pos.length-1-i].getSteal();
			data[i][14] = pos[pos.length-1-i].getRejection();
			data[i][15] = pos[pos.length-1-i].getTo();
			data[i][16] = pos[pos.length-1-i].getFoul();
		}
		totalTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		totalTable.setVisible(false);
		this.add(totalTable);
	}
	
	private void basicSetting(){
		String[] header = {"赛季", "上场数", "先发场数", "上场总时间", "总得分", "两双数"};
		
		Object[][] data = new Object[100][header.length];
		for(int i=0;i<100;i++){
//			data[i][0] = pos[pos.length-1-i].getSeason();
//			data[i][1] = pos[pos.length-1-i].getGP();
//			data[i][2] = pos[pos.length-1-i].getGS();
//			data[i][3] = pos[pos.length-1-i].getMinutesOnField();
//			data[i][4] = pos[pos.length-1-i].getPTS();
//			data[i][5] = pos[pos.length-1-i].getDouble();
			data[i][0] = pos[pos.length-1].getSeason();
			data[i][1] = pos[pos.length-1].getGP();
			data[i][2] = pos[pos.length-1].getGS();
			data[i][3] = pos[pos.length-1].getMinutesOnField();
			data[i][4] = pos[pos.length-1].getPTS();
			data[i][5] = pos[pos.length-1].getDouble();
		}
		
		basicTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		basicTable.setVisible(true);
		this.add(basicTable);
	}
	
	private void pgSetting(){
		String[] header = {"赛季", "上场时间", "得分", "篮板", "助攻", "进攻", 
				"防守", "抢断", "盖帽", "失误", "犯规"};
		
		Object[][] data = new Object[pos.length][header.length];
		for(int i=0;i<pos.length;i++){
			data[i][0] = pos[pos.length-1-i].getSeason();
			data[i][1] = pos[pos.length-1-i].getMPG();
			data[i][2] = pos[pos.length-1-i].getPPG();
			data[i][3] = pos[pos.length-1-i].getBPG();
			data[i][4] = pos[pos.length-1-i].getAPG();
			data[i][5] = pos[pos.length-1-i].getOffPG();
			data[i][6] = pos[pos.length-1-i].getDefPG();
			data[i][7] = pos[pos.length-1-i].getStealPG();
			data[i][8] = pos[pos.length-1-i].getRPG();
			data[i][9] = pos[pos.length-1-i].getToPG();
			data[i][10] = pos[pos.length-1-i].getFoulPG();
		}
		
		pgTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		pgTable.setVisible(false);
		this.add(pgTable);
	}
	
	private void effSetting(){
		String[] header = {"赛季", "效率", "GMSC", "使用率", "真实命中率", "投篮命中率", 
				"三分命中率", "罚篮命中率", "投篮", "篮板", "进攻篮板", "防守篮板", "助攻", "抢断", "盖帽", "失误率"};
		
		Object[][] data = new Object[pos.length][header.length];
		for(int i=0;i<pos.length;i++){
			data[i][0] = pos[pos.length-1-i].getSeason();
			data[i][1] = pos[pos.length-1-i].getEff();
			data[i][2] = pos[pos.length-1-i].getGmsc();
			data[i][3] = pos[pos.length-1-i].getUseEff();
			data[i][4] = pos[pos.length-1-i].getTruePercentage();
			data[i][5] = pos[pos.length-1-i].getFieldGoalPercentage();
			data[i][6] = pos[pos.length-1-i].getThreePGPercentage();
			data[i][7] = pos[pos.length-1-i].getFTPercentage();
			data[i][8] = pos[pos.length-1-i].getShootEff();
			data[i][9] = pos[pos.length-1-i].getBackboardEff();
			data[i][10] = pos[pos.length-1-i].getOffBEff();
			data[i][11] = pos[pos.length-1-i].getDefBEff();
			data[i][12] = pos[pos.length-1-i].getAssitEff();
			data[i][13] = pos[pos.length-1-i].getStealEff();
			data[i][14] = pos[pos.length-1-i].getRejectionEff();
			data[i][15] = pos[pos.length-1-i].getToEff();
		}
		
		effTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		effTable.setVisible(false);
		this.add(effTable);
	}
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
	
}
