package presentation.player;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.sun.org.apache.xml.internal.security.Init;

import bussinesslogic.player.PlayerLogic;
import bussinesslogic.team.TeamLogic;
import data.po.PlayerDataPO;
import data.po.TeamDataPO;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.component.StyleScrollPane;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;

public class PlayerUI extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private static String file = "";
	private GLabel title, chooser, borderUp, borderDown;
	private SelectLabel letter[];
	private PlayerLogic playerLogic = new PlayerLogic();
	private TeamLogic teamLogic = new TeamLogic();
	private WebTable table;
	private StyleScrollPane scrollPane;
	private JComboBox<String> comboBoxTeam, comboBoxPosition;
	private JTextField search;
	private JCheckBox checkBox1, checkBox2;
	private Vector<String> header = new Vector<String>();
	private Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	private TableModel tableModel;
	private PlayerDataPO[] playList;
	private TurnController turnController = new TurnController();

	public PlayerUI() {
		super(file);

//		try {
//		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//		        if ("Nimbus".equals(info.getName())) {
//		            UIManager.setLookAndFeel(info.getClassName());
//		            break;
//		        }
//		    }
//		} catch (Exception e) {}
		
		this.setLayout(null);
		//this.setBackground(UIUtil.bgWhite);
		this.setOpaque(false);
		
		init();
	}

	private void init(){
		playList = playerLogic.getAllInfo(playerLogic.getLatestSeason());
		
		borderUp = new GLabel("", new Point(0,0), new Point(940,4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);
		
		borderDown = new GLabel("", new Point(0,56), new Point(940,4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);

		title = new GLabel("  球员列表",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		chooser = new GLabel("",new Point(0,60),new Point(940,80),this,true,0,16);
		chooser.setOpaque(true);
		chooser.setBackground(UIUtil.bgWhite);
		chooser.setForeground(UIUtil.nbaBlue);

		letter = new SelectLabel[26];
		for(int i=0;i<letter.length;i++){
			final String letterString = String.valueOf((char)(65+i));
			letter[i] = new SelectLabel(letterString, new Point(10+i*31, 7), new Point(30, 30), chooser, true, 0, 20);
			letter[i].setOpaque(true);
			letter[i].setHorizontalAlignment(SwingConstants.CENTER);
			letter[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			letter[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					SelectLabel Label = (SelectLabel)e.getSource();
					Label.setSelected(true);
					comboBoxTeam.setSelectedIndex(0);
					comboBoxPosition.setSelectedIndex(0);
					search.setText("根据姓名查找");
					playList = playerLogic.getPlayerByFirstName(playerLogic.getAllInfo(playerLogic.getLatestSeason()), letterString);
					refreshTable();
					table.updateUI();
				}
				public void mouseExited(MouseEvent e) {
//					for(int i=0;i<letter.length;i++){
//						letter[i].setForeground(UIUtil.nbaBlue);
//					}
				}
				public void mouseEntered(MouseEvent e) {
//					GLabel Label = (GLabel)e.getSource();
//					for(int i=0;i<letter.length;i++){
//						letter[i].setForeground(UIUtil.nbaBlue);
//					}
//					Label.setForeground(UIUtil.nbaRed);
				}
			});
		}
		
		ArrayList<TeamDataPO> teamDataPOs = teamLogic.GetInfoBySeason("13-14");
		int boxHeaderTeamLength = teamDataPOs.size()+1;
		String[] boxHeaderTeam = new String[boxHeaderTeamLength];
		boxHeaderTeam[0] = "根据球队查找";
		for(int i=1;i<boxHeaderTeam.length;i++){
			boxHeaderTeam[i] = TableUtility.getChTeam(teamDataPOs.get(i-1).getShortName())+" "+
					teamDataPOs.get(i-1).getShortName();
		}
		comboBoxTeam = new JComboBox<String>(boxHeaderTeam);
		comboBoxTeam.setBounds(10, 44, 200, 30);
		chooser.add(comboBoxTeam);
		comboBoxTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxTeam.getSelectedIndex()!=0){
					for(int i=0;i<letter.length;i++){
						letter[i].setSelected(false);
					}
					comboBoxPosition.setSelectedIndex(0);
					search.setText("根据姓名查找");
					if(comboBoxTeam.getSelectedIndex()!=0){
						String team = comboBoxTeam.getSelectedItem().toString().split(" ")[1];
						playList = playerLogic.getPlayerByTeam(team, "null", "null", playerLogic.getLatestSeason());
						refreshTable();
						table.updateUI();
					}
				}
			}
		});
		
		String[] boxHeaderPosition = {"根据位置查找", "前锋 F", "中锋 C", "后卫 G"};
		comboBoxPosition = new JComboBox<String>(boxHeaderPosition);
		comboBoxPosition.setBounds(220, 44, 200, 30);
		chooser.add(comboBoxPosition);
		comboBoxPosition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBoxPosition.getSelectedIndex()!=0){
					for(int i=0;i<letter.length;i++){
						letter[i].setSelected(false);
					}
					comboBoxTeam.setSelectedIndex(0);
					search.setText("根据姓名查找");
					if(comboBoxPosition.getSelectedIndex()!=0){
						String position = comboBoxPosition.getSelectedItem().toString().split(" ")[1];
						playList = playerLogic.getPlayerByTeam("null", "null", position, playerLogic.getLatestSeason());
						refreshTable();
						table.updateUI();
					}
				}
			}
		});
		
		search = new JTextField("根据姓名查找");
		search.setBounds(430, 44, 200, 30);
		chooser.add(search);
		search.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				for(int i=0;i<letter.length;i++){
					letter[i].setSelected(false);
				}
				comboBoxPosition.setSelectedIndex(0);
				comboBoxTeam.setSelectedIndex(0);
				String name = search.getText();System.out.println(name);
				playList = playerLogic.getPlayerByTeam("null", name, "null", playerLogic.getLatestSeason());
				refreshTable();
				table.repaint();
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		search.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(search.getText().equals("根据姓名查找")){
					search.setText("");
				}
			}
		});
		
		infoInit();
	}
	
	private void refreshTable(){
		data.removeAllElements();
		for(int i=0;i<playList.length;i++){
			PlayerDataPO p = playList[i];
			Vector<Object> vector = new Vector<Object>();
			vector.addElement(p.getName());
			vector.addElement(TableUtility.getChTeam(p.getTeamName())+" "+p.getTeamName());
			vector.addElement(p.getPosition());
			vector.addElement(p.getNumber());
			vector.addElement(p.getHeight());
			vector.addElement(p.getWeight());
			vector.addElement(p.getBirth());
			vector.addElement(p.getExp());
			data.addElement(vector);
		}
	}
	
	private void infoInit(){
//		header.removeAllElements();
//		data.removeAllElements();
//		header.addElement("姓名");header.addElement("球队");header.addElement("位置");header.addElement("号码");header.addElement("身高");
//		header.addElement("体重");header.addElement("生日");header.addElement("球龄");
//		for(int i=0;i<playList.length;i++){
//			PlayerDataPO p = playList[i];
//			Vector<Object> vector = new Vector<Object>();
//			vector.addElement(p.getName());
//			vector.addElement(TableUtility.getChTeam(p.getTeamName())+" "+p.getTeamName());
//			vector.addElement(p.getPosition());
//			vector.addElement(p.getNumber());
//			vector.addElement(p.getHeight());
//			vector.addElement(p.getWeight());
//			vector.addElement(p.getBirth());
//			vector.addElement(p.getExp());
//			data.addElement(vector);
//		}
		
		String h[] = {"姓名","球队","位置","号码","身高","体重","生日","球龄"};
		Object[][] d = new Object[playList.length][h.length];
		for(int i=0;i<playList.length;i++){
			PlayerDataPO p = playList[i];
			d[i][0] = p.getName()==null?"a":p.getName();
			d[i][1] = TableUtility.getChTeam(p.getTeamName())+" "+p.getTeamName()==null?"a":TableUtility.getChTeam(p.getTeamName())+" "+p.getTeamName();
			d[i][2] = p.getPosition()==null?"a":p.getPosition();
			d[i][3] = p.getNumber()==null?"a":p.getNumber();
			d[i][4] = p.getHeight()==null?"a":p.getHeight();
			d[i][5] = p.getWeight();
			d[i][6] = p.getBirth()==null?"a":p.getBirth();
			d[i][7] = p.getExp();
		}
		table = new WebTable(h, d, new Rectangle(0, 140, 940, 460), UIUtil.lightGrey);
		table.setColumDataCenter(2);
		table.setColumDataCenter(3);
		table.setColumDataCenter(4);
		table.setColumDataCenter(5);
		table.setColumDataCenter(7);
		table.setColumForeground(0, UIUtil.nbaBlue);
		table.setColumForeground(1, UIUtil.nbaBlue);
		table.setColumHand(0);
		table.setColumHand(1);
		
		for(int i=0;i<playList.length;i++){
			table.getColum(0)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String playerName = label.getText();
					WebFrame.frame.setPanel(turnController.turnToPlayerDetials(playerName), playerName);
				}
			});
			table.getColum(1)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String teamName = label.getText().split(" ")[1];
					WebFrame.frame.setPanel(turnController.turnToPlayerDetials(teamName), teamName);
				}
			});
		}
		this.add(table);
		this.repaint();
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
