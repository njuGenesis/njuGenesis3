package presentation.team;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import bussinesslogic.team.TeamLogic;
import data.po.teamData.TeamCompleteInfo;

public class TeamData extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private WebTable table_avg_normal, table_avg_off, table_total_off, table_total_normal;
	private GLabel label, border;
	private JCheckBox checkBox1, checkBox2;
	private TeamLogic teamLogic;
	private Rectangle rectangle;
	private ArrayList<TeamCompleteInfo> teamCompleteInfoNormal, teamCompleteInfoOff;
	private JComboBox<String> comboBox;
	private boolean isNormal;
	
	public TeamData(String shortName){
		super("");
		
		teamLogic = new TeamLogic();
		try {
			teamCompleteInfoNormal = teamLogic.GetPartCompleteInfo(shortName, "unknown", "yes");
			teamCompleteInfoOff = teamLogic.GetPartCompleteInfo(shortName, "unknown", "no");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
		this.setVisible(true);

		init();
	}
	private void init(){
		rectangle = new Rectangle(0, 80, 940, 420);
		
		label = new GLabel("比赛数据", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		total_normal_setting();
		avg_normal_setting();
		total_off_setting();
		avg_off_setting();
		
		table_total_normal.setVisible(true);
		
		isNormal = true;
		
		String comHeader[] = {"常规赛", "季后赛"};
		comboBox = new JComboBox<String>(comHeader);
		comboBox.setBounds(590, 35, 90, 30);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0){
					isNormal = true;
					setFalse();
					if(checkBox1.isSelected()){
						table_total_normal.setVisible(true);
					}else{
						if(checkBox2.isSelected()){
							table_avg_normal.setVisible(true);
						}
					}
				}else{
					isNormal = false;
					setFalse();
					if(checkBox1.isSelected()){
						table_total_off.setVisible(true);
					}else{
						if(checkBox2.isSelected()){
							table_avg_off.setVisible(true);
						}
					}
				}
			}
		});
		this.add(comboBox);
		
		checkBox1 = new JCheckBox("总览");
		checkBox1.setBounds(720, 35, 90, 30);
		checkBox1.setSelected(true);
		this.add(checkBox1);
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox1.isSelected()){
						checkBox2.setSelected(false);
						setFalse();
						table_total_normal.setVisible(true);
					}else{
						checkBox1.setSelected(true);
					}
				}else{
					if(checkBox1.isSelected()){
						checkBox2.setSelected(false);
						setFalse();
						table_total_off.setVisible(true);
					}else{
						checkBox1.setSelected(true);
					}
				}
			}
		});
		
		checkBox2 = new JCheckBox("场均");
		checkBox2.setBounds(checkBox1.getLocation().x+checkBox1.getSize().width, checkBox1.getLocation().y, 
				checkBox1.getSize().width, checkBox1.getSize().height);
		this.add(checkBox2);
		checkBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox2.isSelected()){
						checkBox1.setSelected(false);
						setFalse();
						table_avg_normal.setVisible(true);
					}else{
						checkBox2.setSelected(true);
					}
				}else{
					if(checkBox2.isSelected()){
						checkBox1.setSelected(false);
						setFalse();
						table_avg_off.setVisible(true);
					}else{
						checkBox2.setSelected(true);
					}
				}
			}
		});
		
	}
	
	private void total_normal_setting(){
		String[] header = {"赛季", "场数", "胜", "胜率", "投篮％", "三分％", "罚球％", "进攻效率", "防守效率", "篮板效率", "抢断效率", "助攻率"};
		
		Object[][] data = new Object[teamCompleteInfoNormal.size()][header.length];
		for(int i=0;i<teamCompleteInfoNormal.size();i++){
			data[i][0] = teamCompleteInfoNormal.get(i).getLData().getSeason();
			data[i][1] = teamCompleteInfoNormal.get(i).getLData().getMatchNumber();
			data[i][2] = teamCompleteInfoNormal.get(i).getLData().getWinMatch();
			data[i][3] = teamCompleteInfoNormal.get(i).getLData().getWinrate();
			data[i][4] = teamCompleteInfoNormal.get(i).getLData().getShootEff();
			data[i][5] = teamCompleteInfoNormal.get(i).getLData().getTPEff();
			data[i][6] = teamCompleteInfoNormal.get(i).getLData().getFTEff();
			data[i][7] = teamCompleteInfoNormal.get(i).getHData().getOffEff();
			data[i][8] = teamCompleteInfoNormal.get(i).getHData().getDefEff();
			data[i][9] = teamCompleteInfoNormal.get(i).getHData().getBackBoardEff();
			data[i][10] = teamCompleteInfoNormal.get(i).getHData().getStealEff();
			data[i][11] = teamCompleteInfoNormal.get(i).getHData().getAssistEff();
		}
		
		table_total_normal = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table_total_normal.setVisible(false);
		for(int i=0;i<header.length;i++){
			table_total_normal.setColumDataCenter(i);
		}
		for(int i=1;i<header.length;i++){
			table_total_normal.setOrder(i, Double.class);
		}
		this.add(table_total_normal);
	}
	
	private void avg_normal_setting(){
	     String[] header = {"赛季", "得分", "投篮", "投篮命中", "三分", "三分命中", "罚球", 
	    		 "罚球命中", "篮板", "进攻篮板", "防守篮板", "助攻", "抢断", "盖帽", "失误", "犯规"};
			
			Object[][] data = new Object[teamCompleteInfoNormal.size()][header.length];
			for(int i=0;i<teamCompleteInfoNormal.size();i++){
				data[i][0] = teamCompleteInfoNormal.get(i).getLData().getSeason();
				data[i][1] = teamCompleteInfoNormal.get(i).getLData().getPPG();
				data[i][2] = teamCompleteInfoNormal.get(i).getLData().getShootNumberPG();
				data[i][3] = teamCompleteInfoNormal.get(i).getLData().getShootEffNumberPG();
				data[i][4] = teamCompleteInfoNormal.get(i).getLData().getTPNumberPG();
				data[i][5] = teamCompleteInfoNormal.get(i).getLData().getTPEffNumberPG();
				data[i][6] = teamCompleteInfoNormal.get(i).getLData().getFTNumberPG();
				data[i][7] = teamCompleteInfoNormal.get(i).getLData().getFTEffNumberPG();
				data[i][8] = teamCompleteInfoNormal.get(i).getLData().getBackBoardPG();
				data[i][9] = teamCompleteInfoNormal.get(i).getLData().getOffBackBoardPG();
				data[i][10] = teamCompleteInfoNormal.get(i).getLData().getDefBackBoardPG();
				data[i][11] = teamCompleteInfoNormal.get(i).getLData().getAssitNumberPG();
				data[i][12] = teamCompleteInfoNormal.get(i).getLData().getStealNumberPG();
				data[i][13] = teamCompleteInfoNormal.get(i).getLData().getRejectionPG();
				data[i][14] = teamCompleteInfoNormal.get(i).getLData().getToPG();
				data[i][15] = teamCompleteInfoNormal.get(i).getLData().getFoulPG();
			}
		
		table_avg_normal = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table_avg_normal.setVisible(false);
		for(int i=0;i<header.length;i++){
			table_avg_normal.setColumDataCenter(i);
		}
		for(int i=1;i<header.length;i++){
			table_avg_normal.setOrder(i, Double.class);
		}
		this.add(table_avg_normal);
	}

	private void avg_off_setting(){
		String[] header = {"赛季", "得分", "投篮", "投篮命中", "三分", "三分命中", "罚球", 
				"罚球命中", "篮板", "进攻篮板", "防守篮板", "助攻", "抢断", "盖帽", "失误", "犯规"};

		Object[][] data = new Object[teamCompleteInfoOff.size()][header.length];
		for(int i=0;i<teamCompleteInfoOff.size();i++){
			data[i][0] = teamCompleteInfoOff.get(i).getLData().getSeason();
			data[i][1] = teamCompleteInfoOff.get(i).getLData().getPPG();
			data[i][2] = teamCompleteInfoOff.get(i).getLData().getShootNumberPG();
			data[i][3] = teamCompleteInfoOff.get(i).getLData().getShootEffNumberPG();
			data[i][4] = teamCompleteInfoOff.get(i).getLData().getTPNumberPG();
			data[i][5] = teamCompleteInfoOff.get(i).getLData().getTPEffNumberPG();
			data[i][6] = teamCompleteInfoOff.get(i).getLData().getFTNumberPG();
			data[i][7] = teamCompleteInfoOff.get(i).getLData().getFTEffNumberPG();
			data[i][8] = teamCompleteInfoOff.get(i).getLData().getBackBoardPG();
			data[i][9] = teamCompleteInfoOff.get(i).getLData().getOffBackBoardPG();
			data[i][10] = teamCompleteInfoOff.get(i).getLData().getDefBackBoardPG();
			data[i][11] = teamCompleteInfoOff.get(i).getLData().getAssitNumberPG();
			data[i][12] = teamCompleteInfoOff.get(i).getLData().getStealNumberPG();
			data[i][13] = teamCompleteInfoOff.get(i).getLData().getRejectionPG();
			data[i][14] = teamCompleteInfoOff.get(i).getLData().getToPG();
			data[i][15] = teamCompleteInfoOff.get(i).getLData().getFoulPG();
		}

		table_avg_off = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table_avg_off.setVisible(false);
		for(int i=0;i<header.length;i++){
			table_avg_off.setColumDataCenter(i);
		}
		for(int i=1;i<header.length;i++){
			table_avg_off.setOrder(i, Double.class);
		}
		this.add(table_avg_off);
	}

	private void total_off_setting(){
		String[] header = {"赛季", "场数", "胜", "胜率", "投篮％", "三分％", "罚球％", "进攻效率", "防守效率", "篮板效率", "抢断效率", "助攻率"};

		Object[][] data = new Object[teamCompleteInfoOff.size()][header.length];
		for(int i=0;i<teamCompleteInfoOff.size();i++){
			data[i][0] = teamCompleteInfoOff.get(i).getLData().getSeason();
			data[i][1] = teamCompleteInfoOff.get(i).getLData().getMatchNumber();
			data[i][2] = teamCompleteInfoOff.get(i).getLData().getWinMatch();
			data[i][3] = teamCompleteInfoOff.get(i).getLData().getWinrate();
			data[i][4] = teamCompleteInfoOff.get(i).getLData().getShootEff();
			data[i][5] = teamCompleteInfoOff.get(i).getLData().getTPEff();
			data[i][6] = teamCompleteInfoOff.get(i).getLData().getFTEff();
			data[i][7] = teamCompleteInfoOff.get(i).getHData().getOffEff();
			data[i][8] = teamCompleteInfoOff.get(i).getHData().getDefEff();
			data[i][9] = teamCompleteInfoOff.get(i).getHData().getBackBoardEff();
			data[i][10] = teamCompleteInfoOff.get(i).getHData().getStealEff();
			data[i][11] = teamCompleteInfoOff.get(i).getHData().getAssistEff();
		}

		table_total_off = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		table_total_off.setVisible(false);
		for(int i=0;i<header.length;i++){
			table_total_off.setColumDataCenter(i);
		}
		for(int i=1;i<header.length;i++){
			table_total_off.setOrder(i, Double.class);
		}
		this.add(table_total_off);
	}
	
	private void setFalse(){
		table_avg_normal.setVisible(false);
		table_avg_off.setVisible(false);
		table_total_off.setVisible(false);
		table_total_normal.setVisible(false);
	}

	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
