package presentation.stats;

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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GComboBox;
import presentation.component.GLabel;
import presentation.contenui.StatsUtil;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;
import assistance.NewFont;
import bussinesslogic.team.TeamLogic;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamCompleteInfo;
import data.po.teamData.TeamHData;
import data.po.teamData.TeamLData;

public class TeamStatsPanelNew extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";

	private TeamLogic logic = new TeamLogic();

	public JScrollPane jsp;

	private GLabel title, borderUp, borderDown;

	public JComboBox<String> season;
	public JComboBox<String> type;  //常规赛or季后赛

	public String[] seasonItem = StatsUtil.seasons;
	public String[] typeItem = {"常规赛","季后赛"};

	public JButton submit;

	String[] header1 = {"名称","场数","投篮命中","投篮","三分命中","三分","罚球命中","罚球","进攻篮板","防守篮板","篮板","助攻","抢断","盖帽","失误","犯规","得分",};
	String[] header2 = {"名称","投篮%","三分%","罚球%","胜率","进攻效率","防守效率","篮板效率","抢断效率","助攻率"};

	String[] header_avg1 = {"名称","投篮","命中","出手",
			"三分","命中","出手",
			"罚球","命中","出手",
			"篮板","前场","后场",
			"助攻","抢断","盖帽","失误","犯规"};
	String[] header_avg2 = {"名称","场数","胜","胜率",
			"助攻","抢断","盖帽","失误","犯规","得分",};
	String[] header_ad = {"名称","进攻回合","防守回合",
			"进攻效率","防守效率",
			"篮板效率","进攻篮板效率","防守篮板效率",
			"抢断效率","助攻率",};

	//	JCheckBox avg;  //场均
	//	
	//	JCheckBox avg2;
	//	JCheckBox ad;

	JRadioButton avg;  //场均
	JRadioButton avg2;
	JRadioButton ad;

	ButtonGroup group;
	Boolean[] selection = new Boolean[3];
	private Font radioBtFont  = new Font("微软雅黑",0,12);

	//	JCheckBox[] checkBoxes = new JCheckBox[3];

	public WebTable avgTable1;
	public WebTable avgTable2;
	public WebTable adTable;

	WebTable[] tables = new WebTable[3];

	int[] avgTable1_column = {0};
	int[] avgTable1_width = {140};
	int[] avgTable2_column = {0};
	int[] avgTable2_width = {140};
	int[] adTable_column = {0};
	int[] adTable_width = {140};

	//	public StyleScrollPane jspAll;
	//	public StyleScrollPane jspAvg;
	//	public StyleScrollPane jspEff;

	StatsFactory factory = new StatsFactory();

	@Override
	public void refreshUI() {

		init();
	}


	public TeamStatsPanelNew() {
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

		title = new GLabel("   球队",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		//--------------------筛选条件--------------------
		season = new GComboBox(seasonItem);
		season.setBounds(20, 70, 150, 30);
		season.setFont(NewFont.ComboBoxFont);
		this.add(season);

		type = new GComboBox(typeItem);
		type.setBounds(200, 70, 150, 30);
		type.setFont(NewFont.ComboBoxFont);
		this.add(type);

		submit = UIUtil.getSelectButton();
		submit.setBounds(780, 70, 150, 30);
		submit.addMouseListener(new SubmitListener());
		this.add(submit);

		//--------------------筛选条件end--------------------

		//		avg = new JCheckBox("场均一");
		//		avg.setBounds(0, 110, 70, 30);
		//		avg.setSelected(true);
		//		avg.setOpaque(false);
		//		avg.addMouseListener(new CheckListener(0));
		//		this.add(avg);
		//		checkBoxes[0] = avg;
		//
		//		avg2 = new JCheckBox("场均二");
		//		avg2.setBounds(100, 110, 70, 30);
		//		avg2.setOpaque(false);
		//		avg2.addMouseListener(new CheckListener(1));
		//		this.add(avg2);
		//		checkBoxes[1] = avg2;
		//
		//		ad = new JCheckBox("进阶");
		//		ad.setBounds(200, 110, 70, 30);
		//		ad.setOpaque(false);
		//		ad.addMouseListener(new CheckListener(2));
		//		this.add(ad);
		//		checkBoxes[2] = ad;

		group = new ButtonGroup();

		avg = new JRadioButton("场均一");
		avg.setBounds(20, 110, 70, 30);
		avg.setSelected(true);
		avg.setOpaque(false);
		avg.setFont(radioBtFont);
		avg.addMouseListener(new CheckListener(0));
		this.add(avg);
		group.add(avg);
		selection[0] = avg.isSelected();

		avg2 = new JRadioButton("场均二");
		avg2.setBounds(120, 110, 70, 30);
		avg2.setOpaque(false);
		avg2.setFont(radioBtFont);
		avg2.addMouseListener(new CheckListener(1));
		this.add(avg2);
		group.add(avg2);
		selection[1] = avg2.isSelected();

		ad = new JRadioButton("进阶");
		ad.setBounds(220, 110, 70, 30);
		ad.setOpaque(false);
		ad.setFont(radioBtFont);
		ad.addMouseListener(new CheckListener(2));
		this.add(ad);
		group.add(ad);
		selection[2] = ad.isSelected();


		//		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		//		avgTable1.setColumnWidth(avgTable1_column, avgTable1_width);
		//		addTeamListener_fullName(avgTable1);
		//		this.add(avgTable1);
		//		tables[0] = avgTable1;
		//
		//		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		//		avgTable2.setVisible(false);
		//		avgTable2.setColumnWidth(avgTable2_column, avgTable2_width);
		//		addTeamListener_fullName(avgTable2);
		//		this.add(avgTable2);
		//		tables[1] = avgTable2;
		//
		//		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		//		adTable.setVisible(false);
		//		adTable.setColumnWidth(adTable_column, adTable_width);
		//		addTeamListener_fullName(adTable);
		//		this.add(adTable);
		//		tables[2] = adTable;
		//
		//		setCenter();

		//		TeamStats pt = new TeamStats(0);
		//		Thread t = new Thread(pt);
		//		t.start();

		startDefault();

		this.repaint();
	}

	private void setDefaultTable_avgTable1(){
		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable1.setColumnWidth(avgTable1_column, avgTable1_width);
		addTeamListener_fullName(avgTable1);
		factory.setCenter(avgTable1, 0);
		TeamStatsPanelNew.this.add(avgTable1);
		tables[0] = avgTable1;

		TeamStatsPanelNew.this.repaint();
	}

	private void setDefaultTable_avgTable2(){
		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		avgTable2.setColumnWidth(avgTable2_column, avgTable2_width);
		addTeamListener_fullName(avgTable2);
		factory.setCenter(avgTable2, 0);
		TeamStatsPanelNew.this.add(avgTable2);
		tables[1] = avgTable2;

		TeamStatsPanelNew.this.repaint();
	}

	private void setDefaultTable_adTable(){
		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adTable.setVisible(false);
		adTable.setColumnWidth(adTable_column, adTable_width);
		addTeamListener_fullName(adTable);
		factory.setCenter(adTable, 0);
		TeamStatsPanelNew.this.add(adTable);
		tables[2] = adTable;

		TeamStatsPanelNew.this.repaint();
	}

	private void startDefault(){
		DefaultData d1 = new DefaultData(0);
		Thread t1 = new Thread(d1);
		t1.start();

		DefaultData d2 = new DefaultData(1);
		Thread t2 = new Thread(d2);
		t2.start();

		DefaultData d3 = new DefaultData(2);
		Thread t3 = new Thread(d3);
		t3.start();

	}

	private synchronized void setDefaultTable(){
		System.out.println("start default");

		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable1.setColumnWidth(avgTable1_column, avgTable1_width);
		addTeamListener_fullName(avgTable1);
		TeamStatsPanelNew.this.add(avgTable1);
		tables[0] = avgTable1;

		System.out.println("efault");

		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		avgTable2.setColumnWidth(avgTable2_column, avgTable2_width);
		addTeamListener_fullName(avgTable2);
		TeamStatsPanelNew.this.add(avgTable2);
		tables[1] = avgTable2;

		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adTable.setVisible(false);
		adTable.setColumnWidth(adTable_column, adTable_width);
		addTeamListener_fullName(adTable);
		TeamStatsPanelNew.this.add(adTable);
		tables[2] = adTable;

		setCenter();

		System.out.println("end default");

		this.repaint();
	}


	private void addTeamListener_fullName(WebTable table){
		table.setColumForeground(0,UIUtil.nbaBlue);
		table.setColumHand(0);
		for(int i=0;i<table.row;i++){
			table.getColum(0)[i].addMouseListener(new MouseAdapter() {
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
			ArrayList<TeamCompleteInfo> list = logic.GetPartCompleteInfo(en, "14-15", "yes");
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
			factory.setCenter(tables[i], 0);
		}
	}

	private Object[][] getAvgData1_select(){
		String s = season.getSelectedItem().toString();
		Object[][] data;
		ArrayList<TeamLData> temp;

		if(isRegular()){
			try {
				temp = logic.GetPartLInfo("unknown", s, "yes");
				data = this.getAvgData1(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}else{
			try {
				temp = logic.GetPartLInfo("unknown", s, "no");
				data = this.getAvgData1(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}

	private Object[][] getAvgData2_select(){
		String s = season.getSelectedItem().toString();
		Object[][] data;
		ArrayList<TeamLData> temp;

		if(isRegular()){
			try {
				temp = logic.GetPartLInfo("unknown", s, "yes");
				data = this.getAvgData2(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}else{
			try {
				temp = logic.GetPartLInfo("unknown", s, "no");
				data = this.getAvgData2(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}
		return data;
	}

	private Object[][] getAdData_select(){
		String s = season.getSelectedItem().toString();
		Object[][] data;
		ArrayList<TeamHData> temp;

		if(isRegular()){
			try {
				temp = logic.GetPartHInfo("unknown", s, "yes");
				data = this.getAdData(temp);
			} catch (RemoteException e) {
				data = new Object[0][0];
				e.printStackTrace();
			}
		}else{
			try {
				temp = logic.GetPartHInfo("unknown", s, "no");
				data = this.getAdData(temp);
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

	private Object[][] getAvgData1(ArrayList<TeamLData> temp){
		Object[][] data = new Object[temp.size()][header_avg1.length];

		//		"助攻","抢断","盖帽","失误","犯规",};

		for(int i=0;i<temp.size();i++){
			data[i][0] = TableUtility.getChTeam(TableUtility.checkNOH(temp.get(i).getShortName()));

			data[i][1] = temp.get(i).getShootEff();
			data[i][2] = temp.get(i).getShootEffNumberPG();
			data[i][3] = temp.get(i).getShootNumberPG();

			data[i][4] = temp.get(i).getTPEff();
			data[i][5] = temp.get(i).getTPEffNumberPG();
			data[i][6] = temp.get(i).getTPNumberPG();

			data[i][7] = temp.get(i).getFTEff();
			data[i][8] = temp.get(i).getFTEffNumberPG();
			data[i][9] = temp.get(i).getFTNumberPG();

			data[i][10] = temp.get(i).getBackBoardPG();
			data[i][11] = temp.get(i).getOffBackBoardPG();
			data[i][12] = temp.get(i).getDefBackBoardPG();

			data[i][13] = temp.get(i).getAssitNumberPG();
			data[i][14] = temp.get(i).getStealNumberPG();
			data[i][15] = temp.get(i).getRejectionPG();

			data[i][16] = temp.get(i).getToPG();
			data[i][17] = temp.get(i).getFoulPG();
			//			data[i][18] = temp.get(i).getPPG();
		}
		return data;
	}

	private Object[][] getAvgData2(ArrayList<TeamLData> temp){
		Object[][] data = new Object[temp.size()][header_avg2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = TableUtility.getChTeam(TableUtility.checkNOH(temp.get(i).getShortName()));

			data[i][1] = temp.get(i).getMatchNumber();
			data[i][2] = temp.get(i).getWinMatch();
			data[i][3] = temp.get(i).getWinrate();

			data[i][4] = temp.get(i).getAssitNumberPG();
			data[i][5] = temp.get(i).getStealNumberPG();
			data[i][6] = temp.get(i).getRejectionPG();

			data[i][7] = temp.get(i).getToPG();
			data[i][8] = temp.get(i).getFoulPG();

			data[i][9] = temp.get(i).getPPG();

		}
		return data;
	}

	private Object[][] getAdData(ArrayList<TeamHData> temp){
		Object[][] data = new Object[temp.size()][header_ad.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = TableUtility.getChTeam(TableUtility.checkNOH(temp.get(i).getShortName()));

			data[i][1] = temp.get(i).getOff();
			data[i][2] = temp.get(i).getDef();

			data[i][3] = temp.get(i).getOffEff();
			data[i][4] = temp.get(i).getDefEff();

			data[i][5] = temp.get(i).getBackBoardEff();
			data[i][6] = temp.get(i).getOffBackBoardEff();
			data[i][7] = temp.get(i).getDefBackBoardEff();


			data[i][8] = temp.get(i).getStealEff();
			data[i][9] = temp.get(i).getAssistEff();
		}
		return data;
	}


	private String getSeasonStr(String s){
		return s.substring(0, 5);
	}

	private int getSelectNumber(){
		for(int i=0;i<selection.length;i++){
			if(selection[i]){
				return i;
			}
		}
		return 0;
	}

	private void setSelectTable_avgTable1(){

		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable1.setColumnWidth(avgTable1_column, avgTable1_width);
		addTeamListener_fullName(avgTable1);
		factory.setCenter(avgTable1, 0);
		TeamStatsPanelNew.this.add(avgTable1);
		tables[0] = avgTable1;

		TeamStatsPanelNew.this.repaint();
	}

	private void setSelectTable_avgTable2(){

		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		avgTable2.setColumnWidth(avgTable2_column, avgTable2_width);
		addTeamListener_fullName(avgTable2);
		factory.setCenter(avgTable2, 0);
		TeamStatsPanelNew.this.add(avgTable2);
		tables[1] = avgTable2;

		TeamStatsPanelNew.this.repaint();
	}

	private void setSelectTable_adTable(){

		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adTable.setVisible(false);
		adTable.setColumnWidth(adTable_column, adTable_width);
		addTeamListener_fullName(adTable);
		factory.setCenter(adTable, 0);
		TeamStatsPanelNew.this.add(adTable);
		tables[2] = adTable;

		TeamStatsPanelNew.this.repaint();
	}

	

	private synchronized void setSelectTable(){
		int select = getSelectNumber();

		for(int i=0;i<tables.length;i++){
			TeamStatsPanelNew.this.remove(tables[i]);
		}

		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable1.setColumnWidth(avgTable1_column, avgTable1_width);
		addTeamListener_fullName(avgTable1);
		factory.setCenter(avgTable1, 0);
		TeamStatsPanelNew.this.add(avgTable1);
		tables[0] = avgTable1;

		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		avgTable2.setColumnWidth(avgTable2_column, avgTable2_width);
		addTeamListener_fullName(avgTable2);
		factory.setCenter(avgTable2, 0);
		TeamStatsPanelNew.this.add(avgTable2);
		tables[1] = avgTable2;

		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(0, 140, 940, 460), UIUtil.bgWhite);
		adTable.setVisible(false);
		adTable.setColumnWidth(adTable_column, adTable_width);
		addTeamListener_fullName(adTable);
		factory.setCenter(adTable, 0);
		TeamStatsPanelNew.this.add(adTable);
		tables[2] = adTable;

		setCenter();

		for(int i=0;i<tables.length;i++){
			if(i==select){
				tables[i].setVisible(true);
			}else{
				tables[i].setVisible(false);
			}
		}

		for(int i=0;i<tables.length;i++){
			if(i==select){
				selection[i] = true;
			}else{
				selection[i] = false;
			}
		}

		this.repaint();
	}


	class SubmitListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			SelectData pt = new SelectData();
			Thread t = new Thread(pt);
			t.start();

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

		int type;  //0,1,2

		public DefaultData(int type){
			this.type = type;
		}

		@Override
		public void run() {
			if(type == 0){
				TeamStatsPanelNew.this.setDefaultTable_avgTable1();
			}else if(type == 1){
				TeamStatsPanelNew.this.setDefaultTable_avgTable2();
			}else{
				TeamStatsPanelNew.this.setDefaultTable_adTable();
			}

		}
	}

	class SelectData implements Runnable{

		public SelectData(){
		}

		@Override
		public void run() {
			TeamStatsPanelNew.this.setSelectTable();
		}

	}

	class TeamStats implements Runnable{

		int type;  //表示类型：0为默认表格；1为筛选表格

		public TeamStats(int type){
			this.type = type;
		}


		@Override
		public void run() {
			if(type==0){
				TeamStatsPanelNew.this.setDefaultTable();
			}else{
				System.out.println("1111111");
				TeamStatsPanelNew.this.setSelectTable();
			}
		}

	}

}
