package presentation.stats;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import presentation.component.BgPanel;
import presentation.component.GComboBox;
import presentation.component.GLabel;
import presentation.component.StyleScrollPane;
import presentation.contenui.StatsUtil;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;
import assistance.NewFont;
import bussinesslogic.player.PlayerLogic_db;
import bussinesslogic.team.TeamLogic;
import data.po.playerData.PlayerDataPlayOff_Ad_Basic;
import data.po.playerData.PlayerDataPlayOff_Ad_Shoot;
import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Shoot;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamCompleteInfo;

public class PlayerStatsPanelNew extends BgPanel{


	/*
	 * 数据说明：
	 * season的是常规赛，playoff是季后赛
	 * avg是平均，tot是总计，ad是进阶数据
	 * 
	 * */



	private static final long serialVersionUID = 1L;
	private static String bg = "";

	private PlayerLogic_db logic_db = new PlayerLogic_db();
	private TeamLogic logict = new TeamLogic();

	private GLabel title, borderUp, borderDown;

	public JComboBox<String> position;
	public JComboBox<String> league;
	public JComboBox<String> season;
	public JComboBox<String> type;  //常规赛or季后赛

	public String[] positionItem = {"全部位置","后卫","前锋","中锋"}; 
	public String[] leagueItem = {"全部联盟","东-大西洋分区","东-中央分区","东-东南分区","西-西北分区","西-太平洋分区","西-西南分区"};
	public String[] seasonItem = StatsUtil.seasons;
	public String[] typeItem = {"常规赛","季后赛"};

	public JButton submit;

	String[] header_basic = {"姓名","球队","出场","首发","时间",
			"投篮","命中","出手",
			"三分","命中","出手",
			"罚球","命中","出手",
	};
	
	String[] header_basic_off = {"姓名","球队","出场","时间",
			"投篮","命中","出手",
			"三分","命中","出手",
			"罚球","命中","出手",
	};

	String[] header_basic2 = {"姓名","球队",
			"篮板","前场","后场",
			"助攻","抢断","盖帽","失误","犯规",
			"得分","胜","负",};

	String[] header_ad_basic = {"姓名","球队",
			"篮板","进攻板","防守板",
			"助攻","抢断","盖帽","失误","使用",
			"<html>进攻<br>效率<html>","<html>防守<br>效率<html>",
			"WS","<html>进攻<br>WS<html>","<html>防守<br>WS<html>",  //胜利贡献率
			"PER","扣篮","2/3+1","被帽",  //PER为效率
	};
	String[] header_ad_shoot = {"姓名","球队","<html>出手<br>距离<html>",
			"<html> 篮下<br>命中率<html>","命中","出手","占比",
			"<html>近距<br>两分<html>","命中","出手","占比",
			"<html>中距<br>两分<html>","命中","出手","占比",
			"<html>远距<br>两分<html>","命中","出手","占比",
			"<html> 真实<br>命中率<html>","<html>投篮<br>效率<html>",
	};


//	JCheckBox all;  //总数
//	JCheckBox avg;  //场均
//
//	JCheckBox all2;  //总数2
//	JCheckBox avg2;  //场均2
//
//	JCheckBox ad_eff;  //进阶数据
//	JCheckBox ad_shooteff;  //投篮进阶数据
	
	JRadioButton all;  //总数
	JRadioButton avg;  //场均

	JRadioButton all2;  //总数2
	JRadioButton avg2;  //场均2

	JRadioButton ad_eff;  //进阶数据
	JRadioButton ad_shooteff;  //投篮进阶数据
	
	ButtonGroup group;
	Boolean[] selection = new Boolean[6];
	private Font radioBtFont  = new Font("微软雅黑",0,12);

//	JCheckBox[] checkBoxes = new JCheckBox[6];

	public StyleScrollPane jspAll;
	public StyleScrollPane jspAvg;
	public StyleScrollPane jspEff;


	public WebTable allTable1;
	public WebTable allTable2;
	public WebTable avgTable1;
	public WebTable avgTable2;
	public WebTable adBasicTable;
	public WebTable adShootTable;

	WebTable[] tables = new WebTable[6];
	
	int[] allTable1_coloum = {0,1};
	int[] allTable1_width = {140,120};
	int[] allTable2_coloum = {0,1};
	int[] allTable2_width = {140,120};
	int[] avgTable1_coloum = {0,1};
	int[] avgTable1_width = {140,120};
	
	int[] avgTable2_coloum = {0,1};
	int[] avgTable2_width = {140,120};
	int[] adBasicTable_coloum = {0,1};
	int[] adBasicTable_width = {130,55};
	int[] adShootTable_coloum = {0,1};
	int[] adShootTable_width = {130,55};
	

	StatsFactory factory = new StatsFactory();
	
	

	@Override
	public void refreshUI() {

		Component[] com = this.getComponents();
		for(int i=0;i<com.length;i++){
			this.remove(com[i]);
		}

		init();
	}

	public PlayerStatsPanelNew() {
		super(bg);

		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		borderUp = new GLabel("", new Point(0,0), new Point(940,4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		borderDown = new GLabel("", new Point(0,56), new Point(940,4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);

		title = new GLabel("   球员",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		//--------------------筛选条件--------------------
		position = new GComboBox(positionItem);
		position.setBounds(20, 70, 150, 30);
		position.setFont(NewFont.ComboBoxFont);
		this.add(position);

		league = new GComboBox(leagueItem);
		league.setBounds(200, 70, 150, 30);
		league.setBackground(new Color(250,250,250));
		league.setFont(NewFont.ComboBoxFont);
		this.add(league);

		season = new GComboBox(seasonItem);
		season.setBounds(380, 70, 150, 30);
		season.setFont(NewFont.ComboBoxFont);
		this.add(season);

		type = new GComboBox(typeItem);
		type.setBounds(560, 70, 150, 30);
		type.setFont(NewFont.ComboBoxFont);
		this.add(type);

		submit = UIUtil.getSelectButton();
		submit.setBounds(780, 70, 150, 30);
		submit.addMouseListener(new SubmitListener());
		this.add(submit);

		//--------------------筛选条件end--------------------


		//--------------------表格分类--------------------
//		all = new JCheckBox("总数");
//		all.setBounds(0, 110, 70, 30);
//		all.setSelected(true);
//		all.setOpaque(false);
//		all.addMouseListener(new CheckListener(0));
//		this.add(all);
//		checkBoxes[0] = all;
//
//		all2 = new JCheckBox("总数二");
//		all2.setBounds(100, 110, 70, 30);
//		all2.setOpaque(false);
//		all2.addMouseListener(new CheckListener(1));
//		this.add(all2);
//		checkBoxes[1] = all2;
//
//		avg = new JCheckBox("场均");
//		avg.setBounds(200, 110, 70, 30);
//		avg.setOpaque(false);
//		avg.addMouseListener(new CheckListener(2));
//		this.add(avg);
//		checkBoxes[2] = avg;
//
//		avg2 = new JCheckBox("场均二");
//		avg2.setBounds(300, 110, 70, 30);
//		avg2.setOpaque(false);
//		avg2.addMouseListener(new CheckListener(3));
//		this.add(avg2);
//		checkBoxes[3] = avg2;
//
//		ad_eff = new JCheckBox("进阶数据");
//		ad_eff.setBounds(400, 110, 70, 30);
//		ad_eff.setOpaque(false);
//		ad_eff.addMouseListener(new CheckListener(4));
//		this.add(ad_eff);
//		checkBoxes[4] = ad_eff;
//
//		ad_shooteff = new JCheckBox("投篮进阶");
//		ad_shooteff.setBounds(500, 110, 70, 30);
//		ad_shooteff.setOpaque(false);
//		ad_shooteff.addMouseListener(new CheckListener(5));
//		this.add(ad_shooteff);
//		checkBoxes[5] = ad_shooteff;
		
		group = new ButtonGroup();
		
		all = new JRadioButton("总数");
		all.setBounds(20, 110, 90, 30);
		all.setSelected(true);
		all.setOpaque(false);
		all.setFont(radioBtFont);
		all.addMouseListener(new CheckListener(0));
		all.setSelected(true);
		this.add(all);
		group.add(all);
		selection[0] = all.isSelected();

		all2 = new JRadioButton("总数二");
		all2.setBounds(120, 110, 90, 30);
		all2.setOpaque(false);
		all2.setFont(radioBtFont);
		all2.addMouseListener(new CheckListener(1));
		this.add(all2);
		group.add(all2);
		selection[1] = all2.isSelected();

		avg = new JRadioButton("场均");
		avg.setBounds(220, 110, 90, 30);
		avg.setOpaque(false);
		avg.setFont(radioBtFont);
		avg.addMouseListener(new CheckListener(2));
		this.add(avg);
		group.add(avg);
		selection[2] = avg.isSelected();

		avg2 = new JRadioButton("场均二");
		avg2.setBounds(320, 110, 90, 30);
		avg2.setOpaque(false);
		avg2.setFont(radioBtFont);
		avg2.addMouseListener(new CheckListener(3));
		this.add(avg2);
		group.add(avg2);
		selection[3] = avg2.isSelected();

		ad_eff = new JRadioButton("进阶数据");
		ad_eff.setBounds(420, 110, 90, 30);
		ad_eff.setOpaque(false);
		ad_eff.setFont(radioBtFont);
		ad_eff.addMouseListener(new CheckListener(4));
		this.add(ad_eff);
		group.add(ad_eff);
		selection[4] = ad_eff.isSelected();

		ad_shooteff = new JRadioButton("投篮进阶");
		ad_shooteff.setBounds(520, 110, 90, 30);
		ad_shooteff.setOpaque(false);
		ad_shooteff.setFont(radioBtFont);
		ad_shooteff.addMouseListener(new CheckListener(5));
		this.add(ad_shooteff);
		group.add(ad_shooteff);
		selection[5] = ad_shooteff.isSelected();
		

		//--------------------表格分类end--------------------




		//--------------------默认表格内容--------------------
		allTable1 = new WebTable(getBasicHeader(), getAllData1_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		allTable1.setColumnWidth(allTable1_coloum, allTable1_width);
		addNameListener(allTable1);
		addTeamListener_fullName(allTable1);
		this.add(allTable1);
		tables[0] = allTable1;

		allTable2 = new WebTable(this.header_basic2, getAllData2_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		allTable2.setVisible(false);
		allTable2.setColumnWidth(allTable2_coloum, allTable2_width);
		addNameListener(allTable2);
		addTeamListener_fullName(allTable2);
		this.add(allTable2);
		tables[1] = allTable2;

		avgTable1 = new WebTable(getBasicHeader(), getAvgData1_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable1.setVisible(false);
		avgTable1.setColumnWidth(avgTable1_coloum, avgTable1_width);
		addNameListener(avgTable1);
		addTeamListener_fullName(avgTable1);
		this.add(avgTable1);
		tables[2] = avgTable1;

		avgTable2 = new WebTable(this.header_basic2, getAvgData2_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		avgTable2.setColumnWidth(avgTable2_coloum, avgTable2_width);
		addNameListener(avgTable2);
		addTeamListener_fullName(avgTable2);
		this.add(avgTable2);
		tables[3] = avgTable2;

		adBasicTable = new WebTable(header_ad_basic, getAdEff_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adBasicTable.setVisible(false);
		adBasicTable.setColumnWidth(adBasicTable_coloum, adBasicTable_width);
		addNameListener(adBasicTable);
		addTeamListener_shortName(adBasicTable);
		this.add(adBasicTable);
		tables[4] = adBasicTable;

		adShootTable = new WebTable(header_ad_shoot, getAdEffShoot_default(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adShootTable.setVisible(false);
		adShootTable.setColumnWidth(adShootTable_coloum, adShootTable_width);
		addNameListener(adShootTable);
		addTeamListener_shortName(adShootTable);
		this.add(adShootTable);
		tables[5] = adShootTable;
		
		setCenter();
		setDefaultOrder();

		//--------------------默认表格内容end--------------------

		this.repaint();
	}
	
	private void addNameListener(WebTable table){
		table.setColumForeground(0,UIUtil.nbaBlue);
		table.setColumHand(0);
		for(int i=0;i<table.row;i++){
			table.getColum(0)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String name = label.getText();
					int id = 1;
					try {
						id = logic_db.getIDbyName(name, "");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}System.out.println(id);
					TurnController tc = new TurnController();
					WebFrame.frame.setPanel(tc.turnToPlayerDetials(id), name);
				}
			});
		}
	}
	private void addTeamListener_shortName(WebTable table){
		table.setColumForeground(1,UIUtil.nbaBlue);
		table.setColumHand(1);
		for(int i=0;i<table.row;i++){
			table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String shortName = label.getText();
					TeamBaseInfo info = getInfo_shortname(shortName);
					TurnController tc = new TurnController();
					WebFrame.frame.setPanel(tc.turnToTeamDetials(info), info.getName());	
				}
			});
		}
	}
	private void addTeamListener_fullName(WebTable table){
		table.setColumForeground(1,UIUtil.nbaBlue);
		table.setColumHand(1);
		for(int i=0;i<table.row;i++){
			table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String fullName = label.getText();
					TeamBaseInfo info = getInfo_fullname(fullName);
					TurnController tc = new TurnController();
					WebFrame.frame.setPanel(tc.turnToTeamDetials(info), info.getName());	
				}
			});
		}
	}
	
	//根据球队中文全称得到TeamBaseInfo
	private TeamBaseInfo getInfo_fullname(String fullName){
		String en = TableUtility.checkNOH(TableUtility.getChTeam(fullName));
		 try {
			ArrayList<TeamCompleteInfo> list = logict.GetPartCompleteInfo(en, "14-15", "yes");
			if(list.size()!=0){
				return list.get(0).getBaseinfo();
			}else{
				return new TeamBaseInfo();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return new TeamBaseInfo();
		}
	}
	//根据球队中文简称得到TeamBaseInfo
	private TeamBaseInfo getInfo_shortname(String shortName){
		String en = TableUtility.checkNOH(TableUtility.getShortChTeam(shortName));
		 try {
			ArrayList<TeamCompleteInfo> list = logict.GetPartCompleteInfo(en, "14-15", "yes");
			if(list.size()!=0){
				return list.get(0).getBaseinfo();
			}else{
				return new TeamBaseInfo();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return new TeamBaseInfo();
		}
	}

	private void setCenter(){
		for(int i=0;i<tables.length;i++){
			factory.setCenter(tables[i], 1);
		}
	}
	
	private void setDefaultOrder(){
		for(int i=0;i<tables.length;i++){
			tables[i].setOrder(0, String.class);
		}
	}
	

	private String[] getBasicHeader(){
		if(isRegular()){
			return this.header_basic;
		}else{
			return this.header_basic_off;
		}
	}



	private Object[][] getAllData1_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());

		Object[][] data;

		if(isRegular()){  //常规赛
			ArrayList<PlayerDataSeason_Tot_Basic> temp = new ArrayList<PlayerDataSeason_Tot_Basic>();
			try {
				System.out.println(s+"  "+pos+"  "+un);

				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_t_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_t_b(ints.get(i),s));
				}

				data = getAllData1_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}else{  //季后赛
			ArrayList<PlayerDataPlayOff_Tot_Basic> temp = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_t_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_t_b(ints.get(i),s));
				}
				data = getAllData1_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}



	private Object[][] getAllData2_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Tot_Basic> temp = new ArrayList<PlayerDataSeason_Tot_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_t_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_t_b(ints.get(i),s));
				}
				data = getAllData2_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{
			ArrayList<PlayerDataPlayOff_Tot_Basic> temp = new ArrayList<PlayerDataPlayOff_Tot_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_t_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_t_b(ints.get(i),s));
				}
				data = getAllData2_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}
		return data;
	}


	private Object[][] getAvgData1_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());
		Object[][] data;

		if(isRegular()){  //常规赛
			ArrayList<PlayerDataSeason_Avg_Basic> temp = new ArrayList<PlayerDataSeason_Avg_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_a_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_a_b(ints.get(i),s));
				}
				data = getAvgData1_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{  //季后赛
			ArrayList<PlayerDataPlayOff_Avg_Basic> temp = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_a_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_a_b(ints.get(i),s));
				}
				data = getAvgData1_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}
		return data;
	}




	private Object[][] getAvgData2_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Avg_Basic> temp = new ArrayList<PlayerDataSeason_Avg_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_a_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_a_b(ints.get(i),s));
				}
				data = getAvgData2_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{
			ArrayList<PlayerDataPlayOff_Avg_Basic> temp = new ArrayList<PlayerDataPlayOff_Avg_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_a_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_a_b(ints.get(i),s));
				}
				data = getAvgData2_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}
		return data;
	}



	//获取默认条件下，进阶数据表格内容
	private Object[][] getAdEff_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Ad_Basic> temp = new ArrayList<PlayerDataSeason_Ad_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_ad_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_ad_b(ints.get(i),s));
				}
				data = getAdEff_Regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}


		}else{
			ArrayList<PlayerDataPlayOff_Ad_Basic> temp = new ArrayList<PlayerDataPlayOff_Ad_Basic>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_ad_b","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_ad_b(ints.get(i),s));
				}
				data = getAdEff_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}




	private Object[][] getAdEffShoot_select(){
		String s = season.getSelectedItem().toString();
		String pos = changePosStr(position.getSelectedItem().toString());
		String un = changeUnStr(league.getSelectedItem().toString());
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Ad_Shoot> temp = new ArrayList<PlayerDataSeason_Ad_Shoot>();
			try {

				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_ad_s","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.gets_ad_s(ints.get(i),s));
				}
				data = getAdEffShoot_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}


		}else{
			ArrayList<PlayerDataPlayOff_Ad_Shoot> temp = new ArrayList<PlayerDataPlayOff_Ad_Shoot>();
			try {
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_ad_s","null", "null", pos, un,"null");
				for(int i=0;i<ints.size();i++){
					temp.addAll(logic_db.getp_ad_s(ints.get(i),s));
				}
				data = getAdEffShoot_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}


	//	private Vector<Vector<Object>> getAvgData(){
	//		String pos = position.getSelectedItem().toString();
	//		String leag = league.getSelectedItem().toString();
	//		String s = getSeasonStr(season.getSelectedItem().toString());
	//
	//		PlayerDataPO[] po = logic.getSelect(TableUtility.getChPosition(pos), changeUnStr(leag),s);
	//
	//		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	//
	//		for(int i=0;i<po.length;i++){
	//			Vector<Object> v = new Vector<Object>();
	//			//			"序号","姓名","球队","参赛","先发","在场时间","得分","篮板","助攻","两双","进攻","防守","抢断","盖帽","失误","犯规","效率"
	//			v.addElement(po[i].getName());
	//			v.addElement(TableUtility.getShortChTeam(po[i].getTeamName()));
	//			v.addElement(po[i].getGP());
	//			v.addElement(po[i].getGS());
	//			v.addElement(getMinute(po[i].getMPG()));
	//			v.addElement(po[i].getPPG());
	//			v.addElement(po[i].getBPG());
	//			v.addElement(po[i].getAPG());
	//			v.addElement(po[i].getDouble());
	//			v.addElement(po[i].getOffPG());
	//			v.addElement(po[i].getDefPG());
	//			v.addElement(po[i].getStealPG());
	//			v.addElement(po[i].getRPG());
	//			v.addElement(po[i].getToPG());
	//			v.addElement(po[i].getFoulPG());
	//			v.addElement(po[i].getEff());
	//
	//			data.add(v);
	//		}
	//		return data;
	//	}




	//获取默认条件下，总数表格1内容
	private Object[][] getAllData1_default(){
		String s = season.getSelectedItem().toString();

		Object[][] data;

		if(isRegular()){  //常规赛
			ArrayList<PlayerDataSeason_Tot_Basic> temp;
			try {
				temp = logic_db.getAlls_t_b(s);
				data = getAllData1_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}else{  //季后赛
			ArrayList<PlayerDataPlayOff_Tot_Basic> temp;
			try {
				temp = logic_db.getAllp_t_b(s);
				data = getAllData1_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}



	//获取默认条件下，总数表格2内容
	private Object[][] getAllData2_default(){
		String s = season.getSelectedItem().toString();
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Tot_Basic> temp;
			try {
				temp = logic_db.getAlls_t_b(s);
				data = getAllData2_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{
			ArrayList<PlayerDataPlayOff_Tot_Basic> temp;
			try {
				temp = logic_db.getAllp_t_b(s);
				data = getAllData2_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}


		}
		return data;
	}


	//获取默认条件下，场均1表格内容
	private Object[][] getAvgData1_default(){
		String s = season.getSelectedItem().toString();
		Object[][] data;

		if(isRegular()){  //常规赛
			ArrayList<PlayerDataSeason_Avg_Basic> temp;
			try {
				temp = logic_db.getAlls_a_b(s);
				data = getAvgData1_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{  //季后赛
			ArrayList<PlayerDataPlayOff_Avg_Basic> temp;
			try {
				temp = logic_db.getAllp_a_b(s);
				data = getAvgData1_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}
		return data;
	}




	//获取默认条件下，场均表格2内容
	private Object[][] getAvgData2_default(){
		String s = season.getSelectedItem().toString();
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Avg_Basic> temp;
			try {
				temp = logic_db.getAlls_a_b(s);
				data = getAvgData2_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}else{
			ArrayList<PlayerDataPlayOff_Avg_Basic> temp;
			try {
				temp = logic_db.getAllp_a_b(s);
				data = getAvgData2_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}

		}
		return data;
	}



	//获取默认条件下，进阶数据表格内容
	private Object[][] getAdEff_default(){
		String s = season.getSelectedItem().toString();
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Ad_Basic> temp;
			try {
				temp = logic_db.getAlls_ad_b(s);
				data = getAdEff_Regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}


		}else{
			ArrayList<PlayerDataPlayOff_Ad_Basic> temp;
			try {
				temp = logic_db.getAllp_ad_b(s);
				data = getAdEff_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}




	private Object[][] getAdEffShoot_default(){
		String s = season.getSelectedItem().toString();
		Object[][] data;

		if(isRegular()){
			ArrayList<PlayerDataSeason_Ad_Shoot> temp;
			try {
				temp = logic_db.getAlls_ad_s(s);
				data = getAdEffShoot_regular(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}


		}else{
			ArrayList<PlayerDataPlayOff_Ad_Shoot> temp;
			try {
				temp = logic_db.getAllp_ad_s(s);
				data = getAdEffShoot_playoff(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}


	//判断是否为常规赛
	private boolean isRegular(){
		return type.getSelectedItem().toString().equals("常规赛");
	}

	private String getSeasonStr(String s){
		return s.substring(0, 5);
	}

	private String changePosStr(String chinese){
		//		if(chinese=="前锋"){
		//			return "F";
		//		}else if(chinese=="中锋"){
		//			return "C";
		//		}else if(chinese=="后卫"){
		//			return "G";
		//		}else{
		//			return "null";
		//		}
		if(chinese.equals("全部位置")){
			return "null";
		}else{
			return chinese;
		}
	}
	private String changeUnStr(String chinese){
		if(chinese.equals("东-大西洋分区")){
			return "Atlantic";
		}else if(chinese.equals("东-中央分区")){
			return "Central";
		}else if(chinese.equals("东-东南分区")){
			return "Southeast";
		}else if(chinese.equals("西-西北分区")){
			return "Northwest";
		}else if(chinese.equals("西-太平洋分区")){
			return "Pacific";
		}else if(chinese.equals("西-西南分区")){
			return "Southwest";
		}else{
			return "null";
		}
	}

	private int getSelectNumber(){
		for(int i=0;i<selection.length;i++){
			if(selection[i]){
				return i;
			}
		}
		return 0;
	}


	private Object[][] getAllData1_regular(ArrayList<PlayerDataSeason_Tot_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getGs();
			data[i][3] = temp.get(i).getGp();
			data[i][4] = temp.get(i).getTime();

			data[i][5] = temp.get(i).getShootper();
			data[i][6] = temp.get(i).getShoot_in();
			data[i][7] = temp.get(i).getShoot_all();

			data[i][8] = temp.get(i).getThper();
			data[i][9] = temp.get(i).getTh_in();
			data[i][10] = temp.get(i).getTh_all();

			data[i][11] = temp.get(i).getFtper();
			data[i][12] = temp.get(i).getFt_in();
			data[i][13] = temp.get(i).getFt_all();
		}

		return data;
	}

	private Object[][] getAllData1_playoff(ArrayList<PlayerDataPlayOff_Tot_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic_off.length]; 

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getGp();
			data[i][3] = temp.get(i).getTime();

			data[i][4] = temp.get(i).getShootper();
			data[i][5] = temp.get(i).getShoot_in();
			data[i][6] = temp.get(i).getShoot_all();

			data[i][7] = temp.get(i).getThper();
			data[i][8] = temp.get(i).getTh_in();
			data[i][9] = temp.get(i).getTh_all();

			data[i][10] = temp.get(i).getFtper();
			data[i][11] = temp.get(i).getFt_in();
			data[i][12] = temp.get(i).getFt_all();
		}
		return data;
	}

	private Object[][] getAllData2_regular(ArrayList<PlayerDataSeason_Tot_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();

			data[i][2] = temp.get(i).getBackbound();
			data[i][3] = temp.get(i).getOffb();
			data[i][4] = temp.get(i).getDefb();

			data[i][5] = temp.get(i).getAssist();
			data[i][6] = temp.get(i).getSteal();
			data[i][7] = temp.get(i).getRejection();
			data[i][8] = temp.get(i).getMiss();
			data[i][9] = temp.get(i).getFoul();

			data[i][10] = temp.get(i).getPts();
			data[i][11] = temp.get(i).getWin();
			data[i][12] = temp.get(i).getLose();
		}
		return data;
	}

	private Object[][] getAllData2_playoff(ArrayList<PlayerDataPlayOff_Tot_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();

			data[i][2] = temp.get(i).getBackbound();
			data[i][3] = temp.get(i).getOffb();
			data[i][4] = temp.get(i).getDefb();

			data[i][5] = temp.get(i).getAssist();
			data[i][6] = temp.get(i).getSteal();
			data[i][7] = temp.get(i).getRejection();
			data[i][8] = temp.get(i).getMiss();
			data[i][9] = temp.get(i).getFoul();

			data[i][10] = temp.get(i).getPts();
			data[i][11] = temp.get(i).getWin();
			data[i][12] = temp.get(i).getLose();
		}
		return data;
	}

	private Object[][] getAvgData1_regular(ArrayList<PlayerDataSeason_Avg_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getGs();
			data[i][3] = temp.get(i).getGp();
			data[i][4] = temp.get(i).getTime();

			data[i][5] = temp.get(i).getShootper();
			data[i][6] = temp.get(i).getShoot_in();
			data[i][7] = temp.get(i).getShoot_all();

			data[i][8] = temp.get(i).getThper();
			data[i][9] = temp.get(i).getTh_in();
			data[i][10] = temp.get(i).getTh_all();

			data[i][11] = temp.get(i).getFtper();
			data[i][12] = temp.get(i).getFt_in();
			data[i][13] = temp.get(i).getFt_all();
		}
		return data;
	}

	private Object[][] getAvgData1_playoff(ArrayList<PlayerDataPlayOff_Avg_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic_off.length];  //无首发属性

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getGp();
			data[i][3] = temp.get(i).getTime();

			data[i][4] = temp.get(i).getShootper();
			data[i][5] = temp.get(i).getShoot_in();
			data[i][6] = temp.get(i).getShoot_all();

			data[i][7] = temp.get(i).getThper();
			data[i][8] = temp.get(i).getTh_in();
			data[i][9] = temp.get(i).getTh_all();

			data[i][10] = temp.get(i).getFtper();
			data[i][11] = temp.get(i).getFt_in();
			data[i][12] = temp.get(i).getFt_all();
		}
		return data;
	}

	private Object[][] getAvgData2_regular(ArrayList<PlayerDataSeason_Avg_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();

			data[i][2] = temp.get(i).getBackbound();
			data[i][3] = temp.get(i).getOffb();
			data[i][4] = temp.get(i).getDefb();

			data[i][5] = temp.get(i).getAssist();
			data[i][6] = temp.get(i).getSteal();
			data[i][7] = temp.get(i).getRejection();
			data[i][8] = temp.get(i).getMiss();
			data[i][9] = temp.get(i).getFoul();

			data[i][10] = temp.get(i).getPts();
			data[i][11] = temp.get(i).getWin();
			data[i][12] = temp.get(i).getLose();
		}
		return data;
	}

	private Object[][] getAvgData2_playoff(ArrayList<PlayerDataPlayOff_Avg_Basic> temp){
		Object[][] data = new Object[temp.size()][header_basic2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = temp.get(i).getTeam();

			data[i][2] = temp.get(i).getBackbound();
			data[i][3] = temp.get(i).getOffb();
			data[i][4] = temp.get(i).getDefb();

			data[i][5] = temp.get(i).getAssist();
			data[i][6] = temp.get(i).getSteal();
			data[i][7] = temp.get(i).getRejection();
			data[i][8] = temp.get(i).getMiss();
			data[i][9] = temp.get(i).getFoul();

			data[i][10] = temp.get(i).getPts();
			data[i][11] = temp.get(i).getWin();
			data[i][12] = temp.get(i).getLose();
		}
		return data;
	}

	private Object[][] getAdEff_Regular(ArrayList<PlayerDataSeason_Ad_Basic> temp){
		Object[][] data = new Object[temp.size()][header_ad_basic.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = TableUtility.getShortChTeam(TableUtility.getChTeam(temp.get(i).getTeam()));

			data[i][2] = temp.get(i).getBackeff();
			data[i][3] = temp.get(i).getOffbeff();
			data[i][4] = temp.get(i).getDefbeff();

			data[i][5] = temp.get(i).getAssisteff();
			data[i][6] = temp.get(i).getStealeff();
			data[i][7] = temp.get(i).getRejeff();
			data[i][8] = temp.get(i).getMisseff();
			data[i][9] = temp.get(i).getUseeff();

			data[i][10] = temp.get(i).getOffeff();
			data[i][11] = temp.get(i).getDefeff();


			data[i][12] = temp.get(i).getWs();
			data[i][13] = temp.get(i).getOffws();
			data[i][14] = temp.get(i).getDefws();


			data[i][15] = temp.get(i).getPer();
			data[i][16] = temp.get(i).getStrshoot();

			data[i][17] = temp.get(i).getKda();
			data[i][18] = temp.get(i).getBerej();
		}
		return data;
	}

	private Object[][] getAdEff_playoff(ArrayList<PlayerDataPlayOff_Ad_Basic> temp){
		Object[][] data = new Object[temp.size()][header_ad_basic.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = TableUtility.getShortChTeam(TableUtility.getChTeam(temp.get(i).getTeam()));

			data[i][2] = temp.get(i).getBackeff();
			data[i][3] = temp.get(i).getOffbeff();
			data[i][4] = temp.get(i).getDefbeff();

			data[i][5] = temp.get(i).getAssisteff();
			data[i][6] = temp.get(i).getStealeff();
			data[i][7] = temp.get(i).getRejeff();
			data[i][8] = temp.get(i).getMisseff();
			data[i][9] = temp.get(i).getUseeff();

			data[i][10] = temp.get(i).getOffeff();
			data[i][11] = temp.get(i).getDefeff();


			data[i][12] = temp.get(i).getWs();
			data[i][13] = temp.get(i).getOffws();
			data[i][14] = temp.get(i).getDefws();


			data[i][15] = temp.get(i).getPer();
			data[i][16] = temp.get(i).getStrshoot();

			data[i][17] = temp.get(i).getKda();
			data[i][18] = temp.get(i).getBerej();
		}
		return data;
	}

	private Object[][] getAdEffShoot_regular(ArrayList<PlayerDataSeason_Ad_Shoot> temp){
		Object[][] data = new Object[temp.size()][header_ad_shoot.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = TableUtility.getShortChTeam(TableUtility.getChTeam(temp.get(i).getTeam()));
			data[i][2] = temp.get(i).getShootdis();

			data[i][3] = temp.get(i).getBshootper();
			data[i][4] = temp.get(i).getBshoot_in();
			data[i][5] = temp.get(i).getBshoot_all();
			data[i][6] = temp.get(i).getB_per();

			data[i][7] = temp.get(i).getCloseshootper();
			data[i][8] = temp.get(i).getCloseshoot_in();
			data[i][9] = temp.get(i).getCloseshoot_all();
			data[i][10] = temp.get(i).getClose_per();

			data[i][11] = temp.get(i).getMidshootper();
			data[i][12] = temp.get(i).getMidshoot_in();
			data[i][13] = temp.get(i).getMidshoot_all();
			data[i][14] = temp.get(i).getMid_per();

			data[i][15] = temp.get(i).getFarshootper();
			data[i][16] = temp.get(i).getFarshoot_in();
			data[i][17] = temp.get(i).getFarshoot_all();
			data[i][18] = temp.get(i).getFar_per();


			data[i][19] = temp.get(i).getTrueshootper();
			data[i][20] = temp.get(i).getShooteff();
		}
		return data;
	}

	private Object[][] getAdEffShoot_playoff(ArrayList<PlayerDataPlayOff_Ad_Shoot> temp){
		Object[][] data = new Object[temp.size()][header_ad_shoot.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getName();
			data[i][1] = TableUtility.getShortChTeam(TableUtility.getChTeam(temp.get(i).getTeam()));
			data[i][2] = temp.get(i).getShootdis();

			data[i][3] = temp.get(i).getBshootper();
			data[i][4] = temp.get(i).getBshoot_in();
			data[i][5] = temp.get(i).getBshoot_all();
			data[i][6] = temp.get(i).getB_per();

			data[i][7] = temp.get(i).getCloseshootper();
			data[i][8] = temp.get(i).getCloseshoot_in();
			data[i][9] = temp.get(i).getCloseshoot_all();
			data[i][10] = temp.get(i).getClose_per();

			data[i][11] = temp.get(i).getMidshootper();
			data[i][12] = temp.get(i).getMidshoot_in();
			data[i][13] = temp.get(i).getMidshoot_all();
			data[i][14] = temp.get(i).getMid_per();

			data[i][15] = temp.get(i).getFarshootper();
			data[i][16] = temp.get(i).getFarshoot_in();
			data[i][17] = temp.get(i).getFarshoot_all();
			data[i][18] = temp.get(i).getFar_per();


			data[i][19] = temp.get(i).getTrueshootper();
			data[i][20] = temp.get(i).getShooteff();
		}
		return data;
	}



	class SubmitListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {

			int select = getSelectNumber();
			
			for(int i=0;i<tables.length;i++){
				PlayerStatsPanelNew.this.remove(tables[i]);
			}

			//--------------------表格内容--------------------
			allTable1 = new WebTable(getBasicHeader(), getAllData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			allTable1.setColumnWidth(allTable1_coloum, allTable1_width);
			PlayerStatsPanelNew.this.add(allTable1);
			tables[0] = allTable1;

			allTable2 = new WebTable(header_basic2, getAllData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			allTable2.setColumnWidth(allTable2_coloum, allTable2_width);
			PlayerStatsPanelNew.this.add(allTable2);
			tables[1] = allTable2;

			avgTable1 = new WebTable(getBasicHeader(), getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			avgTable1.setColumnWidth(avgTable1_coloum, avgTable1_width);
			PlayerStatsPanelNew.this.add(avgTable1);
			tables[2] = avgTable1;

			avgTable2 = new WebTable(header_basic2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			avgTable2.setColumnWidth(avgTable2_coloum, avgTable2_width);
			PlayerStatsPanelNew.this.add(avgTable2);
			tables[3] = avgTable2;

			adBasicTable = new WebTable(header_ad_basic, getAdEff_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			adBasicTable.setColumnWidth(adBasicTable_coloum, adBasicTable_width);
			PlayerStatsPanelNew.this.add(adBasicTable);
			tables[4] = adBasicTable;

			adShootTable = new WebTable(header_ad_shoot, getAdEffShoot_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
			adShootTable.setColumnWidth(adShootTable_coloum, adShootTable_width);
			PlayerStatsPanelNew.this.add(adShootTable);
			tables[5] = adShootTable;
			
			setCenter();

			for(int i=0;i<tables.length;i++){
				if(i==select){
					tables[i].setVisible(true);
				}else{
					tables[i].setVisible(false);
				}
			}

			for(int i=0;i<selection.length;i++){
				if(i==select){
					selection[i] = true;
				}else{
					selection[i] = false;
				}
			}
			
			PlayerStatsPanelNew.this.repaint();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}



	class CheckListener implements MouseListener{

		private int num;

		public CheckListener(int i){
			num = i;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
//			if(checkBoxes[num].isSelected()){
//				for(int i=0;i<checkBoxes.length;i++){
//					if(i!=num){
//						checkBoxes[i].setSelected(false);
//						tables[i].setVisible(false);
//					}else{
//						tables[i].setVisible(true);
//					}
//				}
//			}else{
//				checkBoxes[num].setSelected(true);
//			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			for(int i=0;i<tables.length;i++){
				if(i==num){
					tables[i].setVisible(true);
					selection[i] = true;
				}else{
					tables[i].setVisible(false);
					selection[i] = false;
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
	
	class DefaultData implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}



}
