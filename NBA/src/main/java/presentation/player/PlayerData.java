package presentation.player;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import bussinesslogic.player.PlayerLogic_db;
import bussinesslogic.team.TeamLogic;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;
import data.po.playerData.PlayerDataPlayOff_Ad_Basic;
import data.po.playerData.PlayerDataPlayOff_Ad_Shoot;
import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Shoot;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import data.po.teamData.TeamBaseInfo;

public class PlayerData extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private WebTable avg_basic_normal_table,total_basic_normal_table,  advance_basic_normal_table, advance_shoot_normal_table, 
					 avg_basic_off_table,total_basic_off_table,  advance_basic_off_table, advance_shoot_off_table;
	private JCheckBox checkBox1, checkBox2, checkBox3, checkBox4;
	private JComboBox<String> comboBox;
	private Rectangle rectangle;
	private GLabel label, border;
	private TeamLogic teamLogic;
	private TurnController turnController;
	
	private boolean isNormal;
	
	private ArrayList<PlayerDataSeason_Avg_Basic> playerDataSeason_Avg_Basic;
	private ArrayList<PlayerDataSeason_Tot_Basic> playerDataSeason_Tot_Basics;
	private ArrayList<PlayerDataSeason_Ad_Basic> playerDataSeason_Ad_Basics;
	private ArrayList<PlayerDataSeason_Ad_Shoot> playerDataSeason_Ad_Shoots;
	private ArrayList<PlayerDataPlayOff_Avg_Basic> playerDataPlayOff_Avg_Basics;
	private ArrayList<PlayerDataPlayOff_Tot_Basic> playerDataPlayOff_Tot_Basics;
	private ArrayList<PlayerDataPlayOff_Ad_Basic> playerDataPlayOff_Ad_Basics;
	private ArrayList<PlayerDataPlayOff_Ad_Shoot> playerDataPlayOff_Ad_Shoots;
	
	private PlayerLogic_db playerLogic_db;

	public PlayerData(int id) {
		super("");System.out.println(id);
		
		teamLogic = new TeamLogic();
		turnController = new TurnController();
		playerLogic_db = new PlayerLogic_db();
		try {
			playerDataSeason_Avg_Basic = playerLogic_db.gets_a_b(id);
			playerDataSeason_Tot_Basics = playerLogic_db.gets_t_b(id);
			playerDataSeason_Ad_Basics = playerLogic_db.gets_ad_b(id);
			playerDataSeason_Ad_Shoots = playerLogic_db.gets_ad_s(id);
			playerDataPlayOff_Avg_Basics = playerLogic_db.getp_a_b(id);
			playerDataPlayOff_Tot_Basics = playerLogic_db.getp_t_b(id);
			playerDataPlayOff_Ad_Basics = playerLogic_db.getp_ad_b(id);
			playerDataPlayOff_Ad_Shoots = playerLogic_db.getp_ad_s(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setVisible(true);
		this.setOpaque(false);
		this.setBackground(UIUtil.bgWhite);
		
		init();
	}

	private void init(){
		rectangle = new Rectangle(0, 80, 940, 420);
		
		label = new GLabel("赛季数据", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		avg_basic_normal_setting();
		total_basic_normal_setting();
		advance_basic_normal_setting();
		advance_shoot_normal_setting();
		avg_basic_off_setting();
		total_basic_off_setting();
		advance_basic_off_setting();
		advance_shoot_off_setting();
		
		isNormal = true;
		
		String comHeader[] = {"常规赛", "季后赛"};
		comboBox = new JComboBox<String>(comHeader);
		comboBox.setBounds(450, 35, 90, 30);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 0){
					isNormal = true;
					setFalse();
					if(checkBox1.isSelected()){
						avg_basic_normal_table.setVisible(true);
					}else{
						if(checkBox2.isSelected()){
							total_basic_normal_table.setVisible(true);
						}else{
							if(checkBox3.isSelected()){
								advance_basic_normal_table.setVisible(true);
							}else{
								if(checkBox4.isSelected()){
									advance_shoot_normal_table.setVisible(true);
								}
							}
						}
					}
				}else{
					isNormal = false;
					setFalse();
					if(checkBox1.isSelected()){
						avg_basic_off_table.setVisible(true);
					}else{
						if(checkBox2.isSelected()){
							total_basic_off_table.setVisible(true);
						}else{
							if(checkBox3.isSelected()){
								advance_basic_off_table.setVisible(true);
							}else{
								if(checkBox4.isSelected()){
									advance_shoot_off_table.setVisible(true);
								}
							}
						}
					}
				}
			}
		});
		this.add(comboBox);

		checkBox1 = new JCheckBox("基础场均");
		checkBox1.setBounds(540, 35, 90, 30);
		checkBox1.setSelected(true);
		this.add(checkBox1);
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox1.isSelected()){
						checkBox2.setSelected(false);
						checkBox3.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						avg_basic_normal_table.setVisible(true);
					}else{
						checkBox1.setSelected(true);
					}
				}else{
					if(checkBox1.isSelected()){
						checkBox2.setSelected(false);
						checkBox3.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						avg_basic_off_table.setVisible(true);
					}else{
						checkBox1.setSelected(true);
					}
				}
			}
		});
		
		checkBox2 = new JCheckBox("基础总计");
		checkBox2.setBounds(checkBox1.getLocation().x+checkBox1.getSize().width, checkBox1.getLocation().y, 
				checkBox1.getSize().width, checkBox1.getSize().height);
		this.add(checkBox2);
		checkBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox2.isSelected()){
						checkBox1.setSelected(false);
						checkBox3.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						total_basic_normal_table.setVisible(true);
					}else{
						checkBox2.setSelected(true);
					}
				}else{
					if(checkBox2.isSelected()){
						checkBox1.setSelected(false);
						checkBox3.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						total_basic_off_table.setVisible(true);
					}else{
						checkBox2.setSelected(true);
					}
				}
			}
		});
		
		checkBox3 = new JCheckBox("基础进阶");
		checkBox3.setBounds(checkBox2.getLocation().x+checkBox2.getSize().width, checkBox2.getLocation().y, 
				checkBox2.getSize().width, checkBox2.getSize().height);
		this.add(checkBox3);
		checkBox3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox3.isSelected()){
						checkBox1.setSelected(false);
						checkBox2.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						advance_basic_normal_table.setVisible(true);
					}else{
						checkBox3.setSelected(true);
					}
				}else{
					if(checkBox3.isSelected()){
						checkBox1.setSelected(false);
						checkBox2.setSelected(false);
						checkBox4.setSelected(false);
						setFalse();
						advance_basic_off_table.setVisible(true);
					}else{
						checkBox3.setSelected(true);
					}
				}
			}
		});
		
		checkBox4 = new JCheckBox("高级进阶");
		checkBox4.setBounds(checkBox3.getLocation().x+checkBox3.getSize().width, checkBox3.getLocation().y, 
				checkBox3.getSize().width, checkBox3.getSize().height);
		this.add(checkBox4);
		checkBox4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isNormal){
					if(checkBox4.isSelected()){
						checkBox1.setSelected(false);
						checkBox2.setSelected(false);
						checkBox3.setSelected(false);
						setFalse();
						advance_shoot_normal_table.setVisible(true);
					}else{
						checkBox4.setSelected(true);
					}
				}else{
					if(checkBox4.isSelected()){
						checkBox1.setSelected(false);
						checkBox2.setSelected(false);
						checkBox3.setSelected(false);
						setFalse();
						advance_shoot_off_table.setVisible(true);
					}else{
						checkBox4.setSelected(true);
					}
				}
			}
		});
	}
	
	private void total_basic_normal_setting(){
		String[] header = {"赛季", "球队", "出场", "首发", "分钟", "投篮", 
				"<html>投篮<br>命中<html>", "三分", "<html>三分<br>命中<html>",
				"罚球", "<html>罚球<br>命中<html>","篮板", "<html>前场<br>篮板<html>", 
				"<html>后场<br>篮板<html>" , "助攻", "抢断",  "盖帽", "失误", "犯规", "得分", "胜", "负"};

		Object[][] data = new Object[playerDataSeason_Tot_Basics.size()][header.length];
		for(int i=0;i<playerDataSeason_Tot_Basics.size();i++){
			data[i][0] = playerDataSeason_Tot_Basics.get(i).getSeason();
			data[i][1] = playerDataSeason_Tot_Basics.get(i).getTeam();
			data[i][2] = playerDataSeason_Tot_Basics.get(i).getGp();
			data[i][3] = playerDataSeason_Tot_Basics.get(i).getGs();
			data[i][4] = playerDataSeason_Tot_Basics.get(i).getTime();
			data[i][5] = playerDataSeason_Tot_Basics.get(i).getShoot_all();
			data[i][6] = playerDataSeason_Tot_Basics.get(i).getShoot_in();
			data[i][7] = playerDataSeason_Tot_Basics.get(i).getTh_all();
			data[i][8] = playerDataSeason_Tot_Basics.get(i).getTh_in();
			data[i][9] = playerDataSeason_Tot_Basics.get(i).getFt_all();
			data[i][10] = playerDataSeason_Tot_Basics.get(i).getFt_in();
			data[i][11] = playerDataSeason_Tot_Basics.get(i).getBackbound();
			data[i][12] = playerDataSeason_Tot_Basics.get(i).getOffb();
			data[i][13] = playerDataSeason_Tot_Basics.get(i).getDefb();
			data[i][14] = playerDataSeason_Tot_Basics.get(i).getAssist();
			data[i][15] = playerDataSeason_Tot_Basics.get(i).getSteal();
			data[i][16] = playerDataSeason_Tot_Basics.get(i).getRejection();
			data[i][17] = playerDataSeason_Tot_Basics.get(i).getMiss();
			data[i][18] = playerDataSeason_Tot_Basics.get(i).getRejection();
			data[i][19] = playerDataSeason_Tot_Basics.get(i).getPts();
			data[i][20] = playerDataSeason_Tot_Basics.get(i).getWin();
			data[i][21] = playerDataSeason_Tot_Basics.get(i).getLose();
		}
		
		total_basic_normal_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		total_basic_normal_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			total_basic_normal_table.setColumDataCenter(i);
			total_basic_normal_table.setOrder(i, Double.class);
		}
		total_basic_normal_table.setColumForeground(1, UIUtil.nbaBlue);
		total_basic_normal_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		total_basic_normal_table.setColumnWidth(columNumber, width);
		this.add(total_basic_normal_table);
		
		for(int i=0;i<playerDataSeason_Tot_Basics.size();i++){
			total_basic_normal_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}

	private void avg_basic_normal_setting(){
		String[] header = {"赛季", "球队", "分钟", "投篮％", "三分％", "罚球％", "篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"};

		Object[][] data = new Object[playerDataSeason_Avg_Basic.size()][header.length];
		for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
			data[i][0] = playerDataSeason_Avg_Basic.get(i).getSeason();
			data[i][1] = playerDataSeason_Avg_Basic.get(i).getTeam();
			data[i][2] = playerDataSeason_Avg_Basic.get(i).getTime();
			data[i][3] = playerDataSeason_Avg_Basic.get(i).getShootper().replaceAll("%", "");
			data[i][4] = playerDataSeason_Avg_Basic.get(i).getThper().replaceAll("%", "");
			data[i][5] = playerDataSeason_Avg_Basic.get(i).getFtper().replaceAll("%", "");
			data[i][6] = playerDataSeason_Avg_Basic.get(i).getBackbound();
			data[i][7] = playerDataSeason_Avg_Basic.get(i).getAssist();
			data[i][8] = playerDataSeason_Avg_Basic.get(i).getSteal();
			data[i][9] = playerDataSeason_Avg_Basic.get(i).getRejection();
			data[i][10] = playerDataSeason_Avg_Basic.get(i).getMiss();
			data[i][11] = playerDataSeason_Avg_Basic.get(i).getFoul();
			data[i][12] = playerDataSeason_Avg_Basic.get(i).getPts();
		}
		
		avg_basic_normal_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		avg_basic_normal_table.setVisible(true);
		for(int i=2;i<header.length;i++){
			avg_basic_normal_table.setColumDataCenter(i);
			avg_basic_normal_table.setOrder(i, Double.class);
		}
		avg_basic_normal_table.setColumForeground(1, UIUtil.nbaBlue);
		avg_basic_normal_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		avg_basic_normal_table.setColumnWidth(columNumber, width);
		this.add(avg_basic_normal_table);
		
		for(int i=0;i<playerDataSeason_Avg_Basic.size();i++){
			avg_basic_normal_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}
	
	private void advance_basic_normal_setting(){
		String[] header = {"赛季", "球队", "篮板率", "<html>进攻<br>篮板率<html>", "<html>防守<br>篮板率<html>", "助攻率", 
				"抢断率", "盖帽率", "失误率", "使用率", "<html>进攻<br>效率<html>", "<html>防守<br>效率<html>", "<html>胜利<br>贡献<html>", 
				"<html>进攻胜<br>利贡献<html>", "<html>防守胜<br>利贡献<html>", "效率", "扣篮", "2/3+1", "被冒"};

		Object[][] data = new Object[playerDataSeason_Ad_Basics.size()][header.length];
		for(int i=0;i<playerDataSeason_Ad_Basics.size();i++){
			data[i][0] = playerDataSeason_Ad_Basics.get(i).getSeason();
			data[i][1] = playerDataSeason_Ad_Basics.get(i).getTeam();
			data[i][2] = playerDataSeason_Ad_Basics.get(i).getBackeff().replaceAll("%", "");
			data[i][3] = playerDataSeason_Ad_Basics.get(i).getOffbeff().replaceAll("%", "");
			data[i][4] = playerDataSeason_Ad_Basics.get(i).getDefbeff().replaceAll("%", "");
			data[i][5] = playerDataSeason_Ad_Basics.get(i).getAssisteff().replaceAll("%", "");
			data[i][6] = playerDataSeason_Ad_Basics.get(i).getStealeff().replaceAll("%", "");
			data[i][7] = playerDataSeason_Ad_Basics.get(i).getRejeff().replaceAll("%", "");
			data[i][8] = playerDataSeason_Ad_Basics.get(i).getMisseff().replaceAll("%", "");
			data[i][9] = playerDataSeason_Ad_Basics.get(i).getUseeff().replaceAll("%", "");
			data[i][10] = playerDataSeason_Ad_Basics.get(i).getOffeff();
			data[i][11] = playerDataSeason_Ad_Basics.get(i).getDefeff();
			data[i][12] = playerDataSeason_Ad_Basics.get(i).getWs();
			data[i][13] = playerDataSeason_Ad_Basics.get(i).getOffws();
			data[i][14] = playerDataSeason_Ad_Basics.get(i).getDefws();
			data[i][15] = playerDataSeason_Ad_Basics.get(i).getPer();
			data[i][16] = playerDataSeason_Ad_Basics.get(i).getStrshoot();
			data[i][17] = playerDataSeason_Ad_Basics.get(i).getKda();
			data[i][18] = playerDataSeason_Ad_Basics.get(i).getBerej();
		}
		
		advance_basic_normal_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		advance_basic_normal_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			advance_basic_normal_table.setColumDataCenter(i);
			advance_basic_normal_table.setOrder(i, Double.class);
		}
		advance_basic_normal_table.setColumForeground(1, UIUtil.nbaBlue);
		advance_basic_normal_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		advance_basic_normal_table.setColumnWidth(columNumber, width);
		this.add(advance_basic_normal_table);
		
		for(int i=0;i<playerDataSeason_Ad_Basics.size();i++){
			advance_basic_normal_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}
	
	private void advance_shoot_normal_setting(){
		String[] header = {"赛季", "球队", "<html>出手<br>距离<html>", 
				"<html>篮下<br>％<html>", "<html>篮下<br>出手<html>", "<html>篮下<br>命中<html>", "<html>篮下<br>占比<html>", 
				"<html>近距<br>离％<html>", "<html>近距离<br>出手<html>", "<html>近距离<br>命中<html>", "<html>近距离<br>占比<html>", 
				"<html>中距<br>离％<html>", "<html>中距离<br>出手<html>", "<html>中距离<br>命中<html>", "<html>中距离<br>占比<html>", 
				"<html>远距<br>离％<html>", "<html>远距离<br>出手<html>", "<html>远距离<br>命中<html>", "<html>远距离<br>占比<html>", 
				"<html>真实<br>命中率<html>", "<html>投篮<br>效率<html>"};

		Object[][] data = new Object[playerDataSeason_Ad_Shoots.size()][header.length];
		for(int i=0;i<playerDataSeason_Ad_Shoots.size();i++){
			data[i][0] = playerDataSeason_Ad_Shoots.get(i).getSeason();
			data[i][1] = playerDataSeason_Ad_Shoots.get(i).getTeam();
			data[i][2] = playerDataSeason_Ad_Shoots.get(i).getShootdis();
			data[i][3] = playerDataSeason_Ad_Shoots.get(i).getBshootper().replaceAll("%", "");
			data[i][4] = playerDataSeason_Ad_Shoots.get(i).getBshoot_all();
			data[i][5] = playerDataSeason_Ad_Shoots.get(i).getBshoot_in();
			data[i][6] = playerDataSeason_Ad_Shoots.get(i).getB_per().replaceAll("%", "");
			data[i][7] = playerDataSeason_Ad_Shoots.get(i).getCloseshootper().replaceAll("%", "");
			data[i][8] = playerDataSeason_Ad_Shoots.get(i).getCloseshoot_all();
			data[i][9] = playerDataSeason_Ad_Shoots.get(i).getCloseshoot_in();
			data[i][10] = playerDataSeason_Ad_Shoots.get(i).getClose_per().replaceAll("%", "");
			data[i][11] = playerDataSeason_Ad_Shoots.get(i).getMidshootper().replaceAll("%", "");
			data[i][12] = playerDataSeason_Ad_Shoots.get(i).getMidshoot_all();
			data[i][13] = playerDataSeason_Ad_Shoots.get(i).getMidshoot_in();
			data[i][14] = playerDataSeason_Ad_Shoots.get(i).getMid_per().replaceAll("%", "");
			data[i][15] = playerDataSeason_Ad_Shoots.get(i).getFarshootper().replaceAll("%", "");
			data[i][16] = playerDataSeason_Ad_Shoots.get(i).getFarshoot_all();
			data[i][17] = playerDataSeason_Ad_Shoots.get(i).getFarshoot_in();
			data[i][18] = playerDataSeason_Ad_Shoots.get(i).getFar_per().replaceAll("%", "");
			data[i][19] = playerDataSeason_Ad_Shoots.get(i).getTrueshootper().replaceAll("%", "");
			data[i][20] = playerDataSeason_Ad_Shoots.get(i).getShooteff().replaceAll("%", "");
		}

		advance_shoot_normal_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		advance_shoot_normal_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			advance_shoot_normal_table.setColumDataCenter(i);
			advance_shoot_normal_table.setOrder(i, Double.class);
		}
		advance_shoot_normal_table.setColumForeground(1, UIUtil.nbaBlue);
		advance_shoot_normal_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		advance_shoot_normal_table.setColumnWidth(columNumber, width);
		this.add(advance_shoot_normal_table);
		
		for(int i=0;i<playerDataSeason_Ad_Shoots.size();i++){
			advance_shoot_normal_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}
	
	private void total_basic_off_setting(){
		String[] header = {"赛季", "球队", "出场", "分钟", "投篮", 
				"<html>投篮<br>命中<html>", "三分", "<html>三分<br>命中<html>",
				"罚球", "<html>罚球<br>命中<html>","篮板", "<html>前场<br>篮板<html>", 
				"<html>后场<br>篮板<html>" , "助攻", "抢断",  "盖帽", "失误", "犯规", "得分", "胜", "负"};

		Object[][] data = new Object[playerDataPlayOff_Tot_Basics.size()][header.length];
		for(int i=0;i<playerDataPlayOff_Tot_Basics.size();i++){
			data[i][0] = playerDataPlayOff_Tot_Basics.get(i).getSeason();
			data[i][1] = playerDataPlayOff_Tot_Basics.get(i).getTeam();
			data[i][2] = playerDataPlayOff_Tot_Basics.get(i).getGp();
			data[i][3] = playerDataPlayOff_Tot_Basics.get(i).getTime();
			data[i][4] = playerDataPlayOff_Tot_Basics.get(i).getShoot_all();
			data[i][5] = playerDataPlayOff_Tot_Basics.get(i).getShoot_in();
			data[i][6] = playerDataPlayOff_Tot_Basics.get(i).getTh_all();
			data[i][7] = playerDataPlayOff_Tot_Basics.get(i).getTh_in();
			data[i][8] = playerDataPlayOff_Tot_Basics.get(i).getFt_all();
			data[i][9] = playerDataPlayOff_Tot_Basics.get(i).getFt_in();
			data[i][10] = playerDataPlayOff_Tot_Basics.get(i).getBackbound();
			data[i][11] = playerDataPlayOff_Tot_Basics.get(i).getOffb();
			data[i][12] = playerDataPlayOff_Tot_Basics.get(i).getDefb();
			data[i][13] = playerDataPlayOff_Tot_Basics.get(i).getAssist();
			data[i][14] = playerDataPlayOff_Tot_Basics.get(i).getSteal();
			data[i][15] = playerDataPlayOff_Tot_Basics.get(i).getRejection();
			data[i][16] = playerDataPlayOff_Tot_Basics.get(i).getMiss();
			data[i][17] = playerDataPlayOff_Tot_Basics.get(i).getRejection();
			data[i][18] = playerDataPlayOff_Tot_Basics.get(i).getPts();
			data[i][19] = playerDataPlayOff_Tot_Basics.get(i).getWin();
			data[i][20] = playerDataPlayOff_Tot_Basics.get(i).getLose();
		}
		
		total_basic_off_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		total_basic_off_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			total_basic_off_table.setColumDataCenter(i);
			total_basic_off_table.setOrder(i, Double.class);
		}
		total_basic_off_table.setColumForeground(1, UIUtil.nbaBlue);
		total_basic_off_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		total_basic_off_table.setColumnWidth(columNumber, width);
		this.add(total_basic_off_table);
		
		for(int i=0;i<playerDataPlayOff_Tot_Basics.size();i++){
			total_basic_off_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}

	private void avg_basic_off_setting(){
		String[] header = {"赛季", "球队", "分钟", "投篮％", "三分％", "罚球％", "篮板", "助攻", "抢断", "盖帽", "失误", "犯规", "得分"};

		Object[][] data = new Object[playerDataPlayOff_Avg_Basics.size()][header.length];
		for(int i=0;i<playerDataPlayOff_Avg_Basics.size();i++){
			data[i][0] = playerDataPlayOff_Avg_Basics.get(i).getSeason();
			data[i][1] = playerDataPlayOff_Avg_Basics.get(i).getTeam();
			data[i][2] = playerDataPlayOff_Avg_Basics.get(i).getTime();
			data[i][3] = playerDataPlayOff_Avg_Basics.get(i).getShootper().replaceAll("%", "");
			data[i][4] = playerDataPlayOff_Avg_Basics.get(i).getThper().replaceAll("%", "");
			data[i][5] = playerDataPlayOff_Avg_Basics.get(i).getFtper().replaceAll("%", "");
			data[i][6] = playerDataPlayOff_Avg_Basics.get(i).getBackbound();
			data[i][7] = playerDataPlayOff_Avg_Basics.get(i).getAssist();
			data[i][8] = playerDataPlayOff_Avg_Basics.get(i).getSteal();
			data[i][9] = playerDataPlayOff_Avg_Basics.get(i).getRejection();
			data[i][10] = playerDataPlayOff_Avg_Basics.get(i).getMiss();
			data[i][11] = playerDataPlayOff_Avg_Basics.get(i).getFoul();
			data[i][12] = playerDataPlayOff_Avg_Basics.get(i).getPts();
		}
		
		avg_basic_off_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		avg_basic_off_table.setVisible(true);
		for(int i=2;i<header.length;i++){
			avg_basic_off_table.setColumDataCenter(i);
			avg_basic_off_table.setOrder(i, Double.class);
		}
		avg_basic_off_table.setColumForeground(1, UIUtil.nbaBlue);
		avg_basic_off_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		avg_basic_off_table.setColumnWidth(columNumber, width);
		this.add(avg_basic_off_table);
		
		for(int i=0;i<playerDataPlayOff_Avg_Basics.size();i++){
			avg_basic_off_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}
	
	private void advance_basic_off_setting(){
		String[] header = {"赛季", "球队", "篮板率", "<html>进攻<br>篮板率<html>", "<html>防守<br>篮板率<html>", "助攻率", 
				"抢断率", "盖帽率", "失误率", "使用率", "<html>进攻<br>效率<html>", "<html>防守<br>效率<html>", "<html>胜利<br>贡献<html>", 
				"<html>进攻胜<br>利贡献<html>", "<html>防守胜<br>利贡献<html>", "效率", "扣篮", "2/3+1", "被冒"};

		Object[][] data = new Object[playerDataPlayOff_Ad_Basics.size()][header.length];
		for(int i=0;i<playerDataPlayOff_Ad_Basics.size();i++){
			data[i][0] = playerDataPlayOff_Ad_Basics.get(i).getSeason();
			data[i][1] = playerDataPlayOff_Ad_Basics.get(i).getTeam();
			data[i][2] = playerDataPlayOff_Ad_Basics.get(i).getBackeff().replaceAll("%", "");
			data[i][3] = playerDataPlayOff_Ad_Basics.get(i).getOffbeff().replaceAll("%", "");
			data[i][4] = playerDataPlayOff_Ad_Basics.get(i).getDefbeff().replaceAll("%", "");
			data[i][5] = playerDataPlayOff_Ad_Basics.get(i).getAssisteff().replaceAll("%", "");
			data[i][6] = playerDataPlayOff_Ad_Basics.get(i).getStealeff().replaceAll("%", "");
			data[i][7] = playerDataPlayOff_Ad_Basics.get(i).getRejeff().replaceAll("%", "");
			data[i][8] = playerDataPlayOff_Ad_Basics.get(i).getMisseff().replaceAll("%", "");
			data[i][9] = playerDataPlayOff_Ad_Basics.get(i).getUseeff().replaceAll("%", "");
			data[i][10] = playerDataPlayOff_Ad_Basics.get(i).getOffeff();
			data[i][11] = playerDataPlayOff_Ad_Basics.get(i).getDefeff();
			data[i][12] = playerDataPlayOff_Ad_Basics.get(i).getWs();
			data[i][13] = playerDataPlayOff_Ad_Basics.get(i).getOffws();
			data[i][14] = playerDataPlayOff_Ad_Basics.get(i).getDefws();
			data[i][15] = playerDataPlayOff_Ad_Basics.get(i).getPer();
			data[i][16] = playerDataPlayOff_Ad_Basics.get(i).getStrshoot();
			data[i][17] = playerDataPlayOff_Ad_Basics.get(i).getKda();
			data[i][18] = playerDataPlayOff_Ad_Basics.get(i).getBerej();
		}
		
		advance_basic_off_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		advance_basic_off_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			advance_basic_off_table.setColumDataCenter(i);
			advance_basic_off_table.setOrder(i, Double.class);
		}
		advance_basic_off_table.setColumForeground(1, UIUtil.nbaBlue);
		advance_basic_off_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		advance_basic_off_table.setColumnWidth(columNumber, width);
		this.add(advance_basic_off_table);
		
		for(int i=0;i<playerDataPlayOff_Ad_Basics.size();i++){
			advance_basic_off_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}
	
	private void advance_shoot_off_setting(){
		String[] header = {"赛季", "球队", "<html>出手<br>距离<html>", 
				"<html>篮下<br>％<html>", "<html>篮下<br>出手<html>", "<html>篮下<br>命中<html>", "<html>篮下<br>占比<html>", 
				"<html>近距<br>离％<html>", "<html>近距离<br>出手<html>", "<html>近距离<br>命中<html>", "<html>近距离<br>占比<html>", 
				"<html>中距<br>离％<html>", "<html>中距离<br>出手<html>", "<html>中距离<br>命中<html>", "<html>中距离<br>占比<html>", 
				"<html>远距<br>离％<html>", "<html>远距离<br>出手<html>", "<html>远距离<br>命中<html>", "<html>远距离<br>占比<html>", 
				"<html>真实<br>命中率<html>", "<html>投篮<br>效率<html>"};

		Object[][] data = new Object[playerDataPlayOff_Ad_Shoots.size()][header.length];
		for(int i=0;i<playerDataPlayOff_Ad_Shoots.size();i++){
			data[i][0] = playerDataPlayOff_Ad_Shoots.get(i).getSeason();
			data[i][1] = playerDataPlayOff_Ad_Shoots.get(i).getTeam();
			data[i][2] = playerDataPlayOff_Ad_Shoots.get(i).getShootdis();
			data[i][3] = playerDataPlayOff_Ad_Shoots.get(i).getBshootper().replaceAll("%", "");
			data[i][4] = playerDataPlayOff_Ad_Shoots.get(i).getBshoot_all();
			data[i][5] = playerDataPlayOff_Ad_Shoots.get(i).getBshoot_in();
			data[i][6] = playerDataPlayOff_Ad_Shoots.get(i).getB_per().replaceAll("%", "");
			data[i][7] = playerDataPlayOff_Ad_Shoots.get(i).getCloseshootper().replaceAll("%", "");
			data[i][8] = playerDataPlayOff_Ad_Shoots.get(i).getCloseshoot_all();
			data[i][9] = playerDataPlayOff_Ad_Shoots.get(i).getCloseshoot_in();
			data[i][10] = playerDataPlayOff_Ad_Shoots.get(i).getClose_per().replaceAll("%", "");
			data[i][11] = playerDataPlayOff_Ad_Shoots.get(i).getMidshootper().replaceAll("%", "");
			data[i][12] = playerDataPlayOff_Ad_Shoots.get(i).getMidshoot_all();
			data[i][13] = playerDataPlayOff_Ad_Shoots.get(i).getMidshoot_in();
			data[i][14] = playerDataPlayOff_Ad_Shoots.get(i).getMid_per().replaceAll("%", "");
			data[i][15] = playerDataPlayOff_Ad_Shoots.get(i).getFarshootper().replaceAll("%", "");
			data[i][16] = playerDataPlayOff_Ad_Shoots.get(i).getFarshoot_all();
			data[i][17] = playerDataPlayOff_Ad_Shoots.get(i).getFarshoot_in();
			data[i][18] = playerDataPlayOff_Ad_Shoots.get(i).getFar_per().replaceAll("%", "");
			data[i][19] = playerDataPlayOff_Ad_Shoots.get(i).getTrueshootper().replaceAll("%", "");
			data[i][20] = playerDataPlayOff_Ad_Shoots.get(i).getShooteff().replaceAll("%", "");
		}

		advance_shoot_off_table = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		advance_shoot_off_table.setVisible(false);
		for(int i=2;i<header.length;i++){
			advance_shoot_off_table.setColumDataCenter(i);
			advance_shoot_off_table.setOrder(i, Double.class);
		}
		advance_shoot_off_table.setColumForeground(1, UIUtil.nbaBlue);
		advance_shoot_off_table.setColumHand(1);
		int columNumber[] = {0,1};
		int width[] = {60,100};
		advance_shoot_off_table.setColumnWidth(columNumber, width);
		this.add(advance_shoot_off_table);
		
		for(int i=0;i<playerDataPlayOff_Ad_Shoots.size();i++){
			advance_shoot_off_table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String team = label.getText();
					String shortName = TableUtility.getChTeam(team);
					if(!shortName.equals("null")){
						TeamBaseInfo teamBaseInfo = new TeamBaseInfo();
						try {
							teamBaseInfo = teamLogic.GetTeamBaseInfo("14-15", shortName).get(0);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						WebFrame.frame.setPanel(turnController.turnToTeamDetials(teamBaseInfo), shortName);
					}
				}
			});
		}
	}

	private void setFalse(){
		avg_basic_normal_table.setVisible(false);
		total_basic_normal_table.setVisible(false);
		advance_basic_normal_table.setVisible(false);
		advance_shoot_normal_table.setVisible(false);
		avg_basic_off_table.setVisible(false);
		total_basic_off_table.setVisible(false);
		advance_basic_off_table.setVisible(false);
		advance_shoot_off_table.setVisible(false);
	}

	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
	
}
