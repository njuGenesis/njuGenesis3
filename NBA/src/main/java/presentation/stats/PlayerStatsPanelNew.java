package presentation.stats;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GComboBox;
import presentation.component.GLabel;
import presentation.component.GTable;
import presentation.component.StyleScrollPane;
import presentation.contenui.StatsUtil;
import presentation.contenui.TableUtility;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import assistance.NewFont;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.po.PlayerDataPO;
import data.po.playerData.PlayerDataPlayOff_Ad_Basic;
import data.po.playerData.PlayerDataPlayOff_Ad_Shoot;
import data.po.playerData.PlayerDataPlayOff_Avg_Basic;
import data.po.playerData.PlayerDataPlayOff_Tot_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Basic;
import data.po.playerData.PlayerDataSeason_Ad_Shoot;
import data.po.playerData.PlayerDataSeason_Avg_Basic;
import data.po.playerData.PlayerDataSeason_Tot_Basic;

public class PlayerStatsPanelNew extends BgPanel{


	/*
	 * 数据说明：
	 * season的是常规赛，playoff是季后赛
	 * avg是平均，tot是总计，ad是进阶数据
	 * 
	 * */



	private static final long serialVersionUID = 1L;
	private static String bg = "";

	private PlayerLogic logic = new PlayerLogic();
	private PlayerLogic_db logic_db = new PlayerLogic_db();

	public JLabel title;

	public JComboBox<String> position;
	public JComboBox<String> league;
	public JComboBox<String> season;
	public JComboBox<String> type;  //常规赛or季后赛

	public String[] positionItem = {"全部位置","后卫","前锋","中锋"}; 
	public String[] leagueItem = {"全部联盟","东-大西洋分区","东-中央分区","东-东南分区","西-西北分区","西-太平洋分区","西-西南分区"};
	//	public String[] statsItem = {"得分","篮板","助攻","得分/篮板/助攻","盖帽","抢断","犯规","失误","分钟","效率","投篮","三分","罚球","两双"};
	public String[] seasonItem = StatsUtil.seasons;
	public String[] typeItem = {"常规赛","季后赛"};

	public JButton submit;

	String[] header_basic = {"姓名","球队","出场","首发","时间",
			"投篮","命中","出手",
			"三分","命中","出手",
			"罚球","命中","出手",
	};

	String[] header_basic2 = {"姓名","球队",
			"篮板","前场","后场",
			"助攻","抢断","盖帽","失误","犯规",
			"得分","胜","负",};

	String[] header_basic_off = {"姓名","球队","出场","时间",   //季后赛，无首发属性
			"投篮","命中","出手",
			"三分","命中","出手",
			"罚球","命中","出手",
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
	String[] header_ad_shoot = {"姓名","球队","出手距离",
			"<html>篮下<br>命中率<html>","命中","出手","占比",
			"<html>近距<br>两分<html>","命中","出手","占比",
			"<html>中距<br>两分<html>","命中","出手","占比",
			"<html>远距<br>两分<html>","命中","出手","占比",
			"<html>真实<br>命中率<html>","<html>投篮<br>效率<html>",
	};


	JCheckBox all;  //总数
	JCheckBox avg;  //场均
	JCheckBox eff;  //效率

	JCheckBox all2;  //总数2
	JCheckBox avg2;  //场均2

	JCheckBox ad_eff;  //进阶数据
	JCheckBox ad_shooteff;  //投篮进阶数据

	JCheckBox[] checkBoxes = new JCheckBox[6];

	public GTable table;
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

	StatsFactory factory = new StatsFactory();

	@Override
	public void refreshUI() {
		this.remove(title);
		this.remove(jspAll);
		this.remove(jspAvg);
		this.remove(jspEff);
		this.remove(position);
		this.remove(league);
		this.remove(season);
		this.remove(submit);
		this.remove(all);
		this.remove(avg);
		this.remove(eff);

		init();
	}

	public PlayerStatsPanelNew() {
		super(bg);

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}

		this.setBounds(50, 0, 950, 650);
		this.setLayout(null);
		this.setOpaque(false);

		init();

	}

	private void init(){
		title = new GLabel("   球员",new Point(80-this.getX(),30),new Point(890,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.nbaBlue);
		title.setForeground(UIUtil.bgWhite);

		//--------------------筛选条件--------------------
		position = new GComboBox(positionItem);
		position.setBounds(80-this.getX(), 100, 150, 30);
		position.setFont(NewFont.ComboBoxFont);
		this.add(position);

		league = new GComboBox(leagueItem);
		league.setBounds(260-this.getX(), 100, 150, 30);
		league.setBackground(new Color(250,250,250));
		league.setFont(NewFont.ComboBoxFont);
		this.add(league);

		season = new GComboBox(seasonItem);
		season.setBounds(440-this.getX(), 100, 150, 30);
		season.setFont(NewFont.ComboBoxFont);
		this.add(season);

		type = new GComboBox(typeItem);
		type.setBounds(620-this.getX(), 100, 150, 30);
		type.setFont(NewFont.ComboBoxFont);
		this.add(type);

		submit = UIUtil.getSelectButton();
		submit.setBounds(820-this.getX(), 100, 150, 30);
		//		submit.addMouseListener(new SubmitListener());
		this.add(submit);

		//--------------------筛选条件end--------------------


		//--------------------表格分类--------------------
		all = new JCheckBox("总数");
		all.setBounds(80, 150, 70, 30);
		all.setSelected(true);
		all.setOpaque(false);
		all.addMouseListener(new CheckListener(0));
		this.add(all);
		checkBoxes[0] = all;

		all2 = new JCheckBox("总数二");
		all2.setBounds(180, 150, 70, 30);
		all2.setOpaque(false);
		all2.addMouseListener(new CheckListener(1));
		this.add(all2);
		checkBoxes[1] = all2;

		avg = new JCheckBox("场均");
		avg.setBounds(280, 150, 70, 30);
		avg.setOpaque(false);
		avg.addMouseListener(new CheckListener(2));
		this.add(avg);
		checkBoxes[2] = avg;

		avg2 = new JCheckBox("场均二");
		avg2.setBounds(380, 150, 70, 30);
		avg2.setOpaque(false);
		avg2.addMouseListener(new CheckListener(3));
		this.add(avg2);
		checkBoxes[3] = avg2;

		ad_eff = new JCheckBox("进阶数据");
		ad_eff.setBounds(480, 150, 70, 30);
		ad_eff.setOpaque(false);
		ad_eff.addMouseListener(new CheckListener(4));
		this.add(ad_eff);
		checkBoxes[4] = ad_eff;

		ad_shooteff = new JCheckBox("投篮进阶");
		ad_shooteff.setBounds(580, 150, 70, 30);
		ad_shooteff.setOpaque(false);
		ad_shooteff.addMouseListener(new CheckListener(5));
		this.add(ad_shooteff);
		checkBoxes[5] = ad_shooteff;

		//--------------------表格分类end--------------------




		//--------------------默认表格内容--------------------
		allTable1 = new WebTable(getBasicHeader(), getAllData1_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		this.add(allTable1);
		tables[0] = allTable1;

		allTable2 = new WebTable(this.header_basic2, getAllData2_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		allTable2.setVisible(false);
		this.add(allTable2);
		tables[1] = allTable2;

		avgTable1 = new WebTable(getBasicHeader(), getAvgData1_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		avgTable1.setVisible(false);
		this.add(avgTable1);
		tables[2] = avgTable1;

		avgTable2 = new WebTable(this.header_basic2, getAvgData2_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		this.add(avgTable2);
		tables[3] = avgTable2;

		adBasicTable = new WebTable(header_ad_basic, getAdEff_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		adBasicTable.setVisible(false);
		this.add(adBasicTable);
		tables[4] = adBasicTable;

		adShootTable = new WebTable(header_ad_shoot, getAdEffShoot_default(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		adShootTable.setVisible(false);
		this.add(adShootTable);
		tables[5] = adShootTable;

		//--------------------默认表格内容end--------------------

		this.repaint();
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_t_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_t_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_t_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_t_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_a_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_a_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_a_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_a_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_ad_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_ad_b","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"s_ad_s","null", "null", pos, un);
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
				ArrayList<Integer> ints = logic_db.selectByTag(s,"p_ad_s","null", "null", pos, un);
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



	private Vector<Vector<Object>> getEffData(){
		String pos = position.getSelectedItem().toString();
		String leag = league.getSelectedItem().toString();
		String s = getSeasonStr(season.getSelectedItem().toString());

		PlayerDataPO[] po = logic.getSelect(TableUtility.getChPosition(pos), changeUnStr(leag),s);

		Vector<Vector<Object>> data = new Vector<Vector<Object>>();

		for(int i=0;i<po.length;i++){
			Vector<Object> v = new Vector<Object>();
			//			"序号","姓名","球队","投篮命中率","三分命中率","罚球命中率",
			//			"GmSc效率","真实命中率","投篮效率","篮板率","进攻篮板率","防守篮板率",
			//			"助攻率","抢断率","盖帽率","失误率","使用率"
			v.addElement(po[i].getName());
			v.addElement(TableUtility.getShortChTeam(po[i].getTeamName()));
			v.addElement(po[i].getFieldGoalPercentage());
			v.addElement(po[i].getThreePGPercentage());
			v.addElement(po[i].getFTPercentage());
			v.addElement(po[i].getGmsc());
			v.addElement(po[i].getTruePercentage());
			v.addElement(po[i].getShootEff());
			v.addElement(po[i].getBackboardEff());
			v.addElement(po[i].getOffBEff());
			v.addElement(po[i].getDefBEff());
			v.addElement(po[i].getAssitEff());
			v.addElement(po[i].getStealEff());
			v.addElement(po[i].getRejectionEff());
			v.addElement(po[i].getToEff());
			v.addElement(po[i].getUseEff());

			data.add(v);
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
		if(chinese=="前锋"){
			return "F";
		}else if(chinese=="中锋"){
			return "C";
		}else if(chinese=="后卫"){
			return "G";
		}else{
			return "null";
		}
	}
	private String changeUnStr(String chinese){
		if(chinese=="东-大西洋分区"){
			return "Atlantic";
		}else if(chinese=="东-中央分区"){
			return "Central";
		}else if(chinese=="东-东南分区"){
			return "Southeast";
		}else if(chinese=="西-西北分区"){
			return "Northwest";
		}else if(chinese=="西-太平洋分区"){
			return "Pacific";
		}else if(chinese=="西-西南分区"){
			return "Southwest";
		}else{
			return "null";
		}
	}

	private int getSelectNumber(){
		for(int i=0;i<checkBoxes.length;i++){
			if(checkBoxes[i].isSelected()){
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
		Object[][] data = new Object[temp.size()][header_basic_off.length];

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
			data[i][1] = temp.get(i).getTeam();

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
			data[i][1] = temp.get(i).getTeam();

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
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getShootdis();

			data[i][3] = temp.get(i).getBshootper();
			data[i][4] = temp.get(i).getBshoot_in();
			data[i][5] = temp.get(i).getBshoot_all();
			data[i][6] = temp.get(i).getB_per();

			data[i][7] = temp.get(i).getCloseshootper();
			data[i][8] = temp.get(i).getCloseshoot_in();
			data[i][8] = temp.get(i).getCloseshoot_all();
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
			data[i][1] = temp.get(i).getTeam();
			data[i][2] = temp.get(i).getShootdis();

			data[i][3] = temp.get(i).getBshootper();
			data[i][4] = temp.get(i).getBshoot_in();
			data[i][5] = temp.get(i).getBshoot_all();
			data[i][6] = temp.get(i).getB_per();

			data[i][7] = temp.get(i).getCloseshootper();
			data[i][8] = temp.get(i).getCloseshoot_in();
			data[i][8] = temp.get(i).getCloseshoot_all();
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
				if(i==0){
					checkBoxes[i].setSelected(true);
				}else{
					checkBoxes[i].setSelected(false);
				}
			}

			//--------------------表格内容--------------------
			allTable1 = new WebTable(getBasicHeader(), getAllData1_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(allTable1);
			tables[0] = allTable1;

			allTable2 = new WebTable(header_basic2, getAllData2_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(allTable2);
			tables[1] = allTable2;

			avgTable1 = new WebTable(getBasicHeader(), getAvgData1_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(avgTable1);
			tables[2] = avgTable1;

			avgTable2 = new WebTable(header_basic2, getAvgData2_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(avgTable2);
			tables[3] = avgTable2;

			adBasicTable = new WebTable(header_ad_basic, getAdEff_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(adBasicTable);
			tables[4] = adBasicTable;

			adShootTable = new WebTable(header_ad_shoot, getAdEffShoot_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			PlayerStatsPanelNew.this.add(adShootTable);
			tables[5] = adShootTable;
			
			for(int i=0;i<tables.length;i++){
				if(i==select){
					tables[i].setVisible(true);
				}else{
					tables[i].setVisible(false);
				}
			}

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
			if(checkBoxes[num].isSelected()){
				for(int i=0;i<checkBoxes.length;i++){
					if(i!=num){
						checkBoxes[i].setSelected(false);
						tables[i].setVisible(false);
					}else{
						tables[i].setVisible(true);
					}
				}
			}else{
				checkBoxes[num].setSelected(true);
			}
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
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}



	public static void main(String[] args){
		JFrame f = new JFrame();
		f.setSize(1000, 650);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new PlayerStatsPanelNew());
		f.setVisible(true);
	}


}
