package presentation.stats;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import data.po.teamData.TeamHData;
import data.po.teamData.TeamLData;
import assistance.NewFont;
import bussinesslogic.team.TeamLogic;
import presentation.component.BgPanel;
import presentation.component.GComboBox;
import presentation.component.GLabel;
import presentation.component.GTable;
import presentation.contenui.StatsUtil;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;

public class TeamStatsPanelNew extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";
	
	private TeamLogic logic = new TeamLogic();

	public GTable table;
	public JScrollPane jsp;

	public JLabel title;

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
			"篮板","前场篮板","后场",
			"助攻","抢断","盖帽","失误","犯规","得分",};
	String[] header_avg2 = {"名称","场数","胜","胜率",
			"助攻","抢断","盖帽","失误","犯规","得分",};
	String[] header_ad = {"名称","进攻回合","防守回合",
			"进攻效率","防守效率",
			"篮板效率","进攻篮板效率","防守篮板效率",
			"抢断效率","助攻率",};
	
//	JCheckBox all;  //总数
	JCheckBox avg;  //场均
//	JCheckBox eff;  //效率
	
	JCheckBox avg2;
	JCheckBox ad;
	
	JCheckBox[] checkBoxes = new JCheckBox[3];
	
	public WebTable avgTable1;
	public WebTable avgTable2;
	public WebTable adTable;
	
	WebTable[] tables = new WebTable[3];
	
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
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}
		
		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(false);
		
		init();
		
		
	}
	
	
	private void init(){
		title = new GLabel("   球队",new Point(80-this.getX(),30),new Point(890,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.nbaBlue);
		title.setForeground(UIUtil.bgWhite);
		
		//--------------------筛选条件--------------------
		season = new GComboBox(seasonItem);
		season.setBounds(80-this.getX(), 100, 150, 30);
		season.setFont(NewFont.ComboBoxFont);
		this.add(season);
		
		type = new GComboBox(typeItem);
		type.setBounds(260-this.getX(), 100, 150, 30);
		type.setFont(NewFont.ComboBoxFont);
		this.add(type);
		
		submit = UIUtil.getSelectButton();
		submit.setBounds(820-this.getX(), 100, 150, 30);
		submit.addMouseListener(new SubmitListener());
		this.add(submit);
		
		//--------------------筛选条件end--------------------
		
		avg = new JCheckBox("场均一");
		avg.setBounds(80, 150, 70, 30);
		avg.setSelected(true);
		avg.setOpaque(false);
		avg.addMouseListener(new CheckListener(0));
		this.add(avg);
		checkBoxes[0] = avg;

		avg2 = new JCheckBox("场均二");
		avg2.setBounds(180, 150, 70, 30);
		avg2.setOpaque(false);
		avg2.addMouseListener(new CheckListener(1));
		this.add(avg2);
		checkBoxes[1] = avg2;

		ad = new JCheckBox("进阶");
		ad.setBounds(280, 150, 70, 30);
		ad.setOpaque(false);
		ad.addMouseListener(new CheckListener(2));
		this.add(ad);
		checkBoxes[2] = ad;

		
		avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		this.add(avgTable1);
		tables[0] = avgTable1;

		avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		avgTable2.setVisible(false);
		this.add(avgTable2);
		tables[1] = avgTable2;
		
		adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
		adTable.setVisible(false);
		this.add(adTable);
		tables[2] = adTable;
		
		
		
		this.repaint();
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

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getShortName();
			
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
		}
		return data;
	}
	
	private Object[][] getAvgData2(ArrayList<TeamLData> temp){
		Object[][] data = new Object[temp.size()][header_avg2.length];

		for(int i=0;i<temp.size();i++){
			data[i][0] = temp.get(i).getShortName();
			
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
			data[i][0] = temp.get(i).getShortName();
			
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
		for(int i=0;i<checkBoxes.length;i++){
			if(checkBoxes[i].isSelected()){
				return i;
			}
		}
		return 0;
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
				TeamStatsPanelNew.this.remove(tables[i]);
				if(i==0){
					checkBoxes[i].setSelected(true);
				}else{
					checkBoxes[i].setSelected(false);
				}
			}

			avgTable1 = new WebTable(header_avg1, getAvgData1_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			TeamStatsPanelNew.this.add(avgTable1);
			tables[0] = avgTable1;

			avgTable2 = new WebTable(header_avg2, getAvgData2_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			avgTable2.setVisible(false);
			TeamStatsPanelNew.this.add(avgTable2);
			tables[1] = avgTable2;
			
			adTable = new WebTable(header_ad, getAdData_select(), new Rectangle(40, 200, 920, 440), UIUtil.bgWhite);
			adTable.setVisible(false);
			TeamStatsPanelNew.this.add(adTable);
			tables[2] = adTable;
			
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

}
