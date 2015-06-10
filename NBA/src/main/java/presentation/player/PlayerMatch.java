package presentation.player;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableCellRenderer;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.StyleScrollPane;
import presentation.component.StyleTable;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.mainui.StartUI;
import presentation.mainui.WebTable;
import bussinesslogic.match.MatchLogic;
import data.po.MatchDataPO;
import data.po.Match_PlayerPO;
import data.po.PlayerDataPO;

public class PlayerMatch extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private PlayerDataPO po;
	private WebTable basicTable, pgTable;
	private JCheckBox checkBox1, checkBox2;
	private MatchLogic matchLogic = new MatchLogic();
	private ArrayList<Match_PlayerPO> match_PlayerPOs = new ArrayList<Match_PlayerPO>();
	private ArrayList<ArrayList<MatchDataPO>> matchDataPOs = new ArrayList<ArrayList<MatchDataPO>>();
	private Rectangle rectangle;
	private GLabel label, border;

	public PlayerMatch(PlayerDataPO po) {
		super("");
		
//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {}
		
		this.po = po;
		this.setBounds(0, 1050, 940, 400);
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		rectangle = new Rectangle(50, 40, 840, 360);
		
		label = new GLabel("比赛信息", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		//match_PlayerPOs = matchLogic.GetPlayerInfo(po.getName());

		basicSetting();
		pgSetting();

		checkBox1 = new JCheckBox("总览");
		checkBox1.setBounds(740, 5, 70, 30);
		checkBox1.setSelected(true);
		this.add(checkBox1);
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox1.isSelected()){
					checkBox2.setSelected(false);
					pgTable.setVisible(false);
					basicTable.setVisible(true);
				}else{
					checkBox1.setSelected(true);
				}
			}
		});
		
		checkBox2 = new JCheckBox("详细");
		checkBox2.setBounds(810, 5, 70, 30);
		this.add(checkBox2);
		checkBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkBox2.isSelected()){
					checkBox1.setSelected(false);
					basicTable.setVisible(false);
					pgTable.setVisible(true);
				}else{
					checkBox2.setSelected(true);
				}
			}
		});
	}
	
	private double ShortDouble(double d){
		DecimalFormat df = new DecimalFormat(".00");
		return Double.parseDouble(df.format(d));
	}
	
	private void basicSetting(){
		
		String[] header = {"日期", "对手", "位置", "在场时间", "得分", "篮板", "三分命中率", "罚球命中率", "助攻数", "比赛链接"};
		
		Object[][] data = new Object[match_PlayerPOs.size()][header.length];
		for(int i=match_PlayerPOs.size()-1;i>=0;i--){
			data[i][0] = match_PlayerPOs.get(i).getDate();
			data[i][1] = match_PlayerPOs.get(i).getOtherTeam();
			if(match_PlayerPOs.get(i).getState().equals("")){
				data[i][2] = "非首发";
			}else{
				data[i][2] = match_PlayerPOs.get(i).getState();
			}
			data[i][3] = match_PlayerPOs.get(i).getTime();
			data[i][4] = (int)match_PlayerPOs.get(i).getPoints();
			data[i][5] = (int)match_PlayerPOs.get(i).getBank();
			data[i][6] = ShortDouble(match_PlayerPOs.get(i).getTPShootEff());
			data[i][7] = ShortDouble(match_PlayerPOs.get(i).getFTShootEff());
			data[i][8] = (int)match_PlayerPOs.get(i).getAss();
			data[i][9] = "比赛链接";
		}
		
		basicTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		basicTable.setVisible(true);
		this.add(basicTable);
	}
	
	private void pgSetting(){
		String[] header = {"日期", "对手", "进攻篮板", "防守篮板", "罚球", "失误", "投球", "投篮命中", 
				"投篮命中率", "三分投篮", "三分命中", "罚篮命中", "抢断", "盖帽", "犯规", "比赛链接"};
		
		Object[][] data = new Object[match_PlayerPOs.size()][header.length];
		for(int i=match_PlayerPOs.size()-1;i>=0;i--){
			data[i][0] = match_PlayerPOs.get(i).getDate();
			data[i][0] = match_PlayerPOs.get(i).getOtherTeam();
			data[i][0] = (int)match_PlayerPOs.get(i).getBankOff();
			data[i][0] = (int)match_PlayerPOs.get(i).getBankDef();
			data[i][0] = (int)match_PlayerPOs.get(i).getFT();
			data[i][0] = (int)match_PlayerPOs.get(i).getTo();
			data[i][0] = (int)match_PlayerPOs.get(i).getShoot();
			data[i][0] = (int)match_PlayerPOs.get(i).getShootEffNumber();
			data[i][0] = ShortDouble(match_PlayerPOs.get(i).getShootEff());
			data[i][0] = (int)match_PlayerPOs.get(i).getTPShoot();
			data[i][0] = (int)match_PlayerPOs.get(i).getTPShootEffNumber();
			data[i][0] = (int)match_PlayerPOs.get(i).getFTShootEffNumber();
			data[i][0] = (int)match_PlayerPOs.get(i).getSteal();
			data[i][0] = (int)match_PlayerPOs.get(i).getRejection();
			data[i][0] = (int)match_PlayerPOs.get(i).getFoul();
			data[i][0] = "比赛链接";
		}
		
		pgTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		pgTable.setVisible(false);
		this.add(pgTable);
	}
	
	private void tableSetting(final StyleTable table){
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int column = table.getColumnModel().getColumnIndexAtX(e.getX());
				int row    = e.getY()/table.getRowHeight();

				TurnController turnController = new TurnController();
				if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0 && (column == table.getColumnCount()-1)) {
					String date = table.getValueAt(row, 0).toString();
					String team = table.getValueAt(row, 1).toString();
					StartUI.startUI.turn(turnController.turnToMatchDetials(date, team));
				}else{
					if(row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0 && (column == 1)){
						String team = table.getValueAt(row, 1).toString();
						StartUI.startUI.turn(turnController.turnToTeamDetials(team));
					}
				}
			}
		};
		table.addMouseListener(mouseAdapter);
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
