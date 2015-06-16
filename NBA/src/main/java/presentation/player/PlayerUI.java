package presentation.player;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bussinesslogic.player.PlayerLogic_db;
import bussinesslogic.team.TeamLogic;
import data.po.playerData.PlayerDetailInfo;
import data.po.teamData.TeamBaseInfo;
import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;
import presentation.hotspot.SelectLabel;
import presentation.mainui.WebFrame;
import presentation.mainui.WebTable;

public class PlayerUI extends BgPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private static String file = "";
	private GLabel title, chooser, borderUp, borderDown, searchButton;
	private SelectLabel letter[];
	private WebTable table;
	private JComboBox<String> comboBoxTeam, comboBoxPosition;
	private JTextField search;
	private TurnController turnController = new TurnController();
	private PlayerLogic_db playerLogic_db;
	private TeamLogic teamLogic;
	private ArrayList<TeamBaseInfo> teamBaseInfo;
	private ArrayList<PlayerDetailInfo> playerDetailInfo;
	private boolean isFirst;

	public PlayerUI() {
		super(file);

		playerLogic_db = new PlayerLogic_db();
		ArrayList<Integer> idList = new ArrayList<>();
		try {
			idList = playerLogic_db.selectByTag("14-15", "detail", "A", "null", "null", "null","null");
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		playerDetailInfo = new ArrayList<>();
		for(int i=0;i<idList.size();i++){
			try {
				playerDetailInfo.add(playerLogic_db.getdetail(idList.get(i)));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setBackground(UIUtil.bgWhite);
//		this.setOpaque(false);

		init();
	}

	private void init(){
		isFirst = true;

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
					isFirst = false;
					for(int i=0;i<letter.length;i++){
						letter[i].setSelected(false);
					}
					SelectLabel Label = (SelectLabel)e.getSource();
					Label.setSelected(true);
					comboBoxTeam.setSelectedIndex(0);
					comboBoxPosition.setSelectedIndex(0);
					search.setText("根据姓名查找");
					ArrayList<Integer> idList = new ArrayList<>();
					try {
						idList = playerLogic_db.selectByTag("14-15", "detail", letterString, "null", "null", "null","null");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					playerDetailInfo = new ArrayList<>();
					for(int i=0;i<idList.size();i++){
						try {
							playerDetailInfo.add(playerLogic_db.getdetail(idList.get(i)));
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
					infoInit();
					table.updateUI();
				}
			});
		}
		letter[0].setSelected(true);
		
		teamLogic = new TeamLogic();
		try {
			teamBaseInfo = teamLogic.GetAllBaseInfo("14-15");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		int boxHeaderTeamLength = teamBaseInfo.size()+1;
		String[] boxHeaderTeam = new String[boxHeaderTeamLength];
		boxHeaderTeam[0] = "根据球队查找";
		for(int i=1;i<boxHeaderTeam.length;i++){
			boxHeaderTeam[i] = TableUtility.getChTeam(teamBaseInfo.get(i-1).getShortName())+" "+
					teamBaseInfo.get(i-1).getShortName();
		}
		comboBoxTeam = new JComboBox<String>(boxHeaderTeam);
		comboBoxTeam.setBounds(10, 44, 200, 30);
		chooser.add(comboBoxTeam);
		comboBoxTeam.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxTeam.getSelectedIndex()!=0){
					for(int i=0;i<letter.length;i++){
						letter[i].setSelected(false);
					}
					comboBoxPosition.setSelectedIndex(0);
					search.setText("根据姓名查找");
					if(comboBoxTeam.getSelectedIndex()!=0){
						String team = comboBoxTeam.getSelectedItem().toString().split(" ")[0];
						ArrayList<Integer> idList = new ArrayList<>();
						try {
							idList = playerLogic_db.selectByTag("14-15", "detail", "null", "null", "null", "null", team);
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						playerDetailInfo = new ArrayList<>();
						for(int i=0;i<idList.size();i++){
							try {
								playerDetailInfo.add(playerLogic_db.getdetail(idList.get(i)));
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
						infoInit();
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
				isFirst = false;
				if(comboBoxPosition.getSelectedIndex()!=0){
					for(int i=0;i<letter.length;i++){
						letter[i].setSelected(false);
					}
					comboBoxTeam.setSelectedIndex(0);
					search.setText("根据姓名查找");
					if(comboBoxPosition.getSelectedIndex()!=0){
						String position = comboBoxPosition.getSelectedItem().toString().split(" ")[0];
						ArrayList<Integer> idList = new ArrayList<>();
						try {
							idList = playerLogic_db.selectByTag("14-15", "detail", "null", "null", position, "null","null");System.out.println(idList.size());
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						playerDetailInfo = new ArrayList<>();
						for(int i=0;i<idList.size();i++){
							try {
								playerDetailInfo.add(playerLogic_db.getdetail(idList.get(i)));
							} catch (RemoteException e1) {
								e1.printStackTrace();
							}
						}
						infoInit();
						table.updateUI();
					}
				}
			}
		});
		
		search = new JTextField("根据姓名查找");
		search.setBounds(430, 44, 200, 30);
		chooser.add(search);
		search.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				isFirst = false;
				if(search.getText().equals("根据姓名查找")){
					search.setText("");
				}
			}
		});
		searchButton = new GLabel("搜名字", new Point(630, 44), new Point(100, 30), chooser, true, 0, 15);
		searchButton.setForeground(UIUtil.nbaBlue);
		searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				isFirst = false;
				for(int i=0;i<letter.length;i++){
					letter[i].setSelected(false);
				}
				comboBoxPosition.setSelectedIndex(0);
				comboBoxTeam.setSelectedIndex(0);
				String name = search.getText();
				ArrayList<Integer> idList = new ArrayList<>();
				try {
					idList = playerLogic_db.selectByTag("14-15", "detail", "null", name, "null", "null","null");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				playerDetailInfo = new ArrayList<>();
				for(int i=0;i<idList.size();i++){
					try {
						playerDetailInfo.add(playerLogic_db.getdetail(idList.get(i)));
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				infoInit();
				table.updateUI();
			}
		});
		
		infoInit();
	}

	private synchronized void infoInit(){
		if(!isFirst)this.remove(table);
		
		String h[] = {"姓名","球队","位置","号码","身高","体重","生日"};
		Object[][] d = new Object[playerDetailInfo.size()][h.length];
		for(int i=0;i<playerDetailInfo.size();i++){
			d[i][0] = playerDetailInfo.get(i).getName();
			d[i][1] = playerDetailInfo.get(i).getTeam();
			d[i][2] = playerDetailInfo.get(i).getPosition();
			d[i][3] = playerDetailInfo.get(i).getNumber();
			d[i][4] = playerDetailInfo.get(i).getHeight();
			d[i][5] = playerDetailInfo.get(i).getWeight();
			d[i][6] = playerDetailInfo.get(i).getBirth();
		}
		table = new WebTable(h, d, new Rectangle(0, 140, 940, 460), UIUtil.lightGrey);
		table.setColumDataCenter(2);
		table.setColumDataCenter(3);
		table.setColumDataCenter(4);
		table.setColumDataCenter(5);
		table.setColumDataCenter(6);
		table.setColumForeground(0, UIUtil.nbaBlue);
		table.setColumForeground(1, UIUtil.nbaBlue);
		table.setColumHand(0);
		table.setColumHand(1);
		table.setOrder(0, String.class);
		
		for(int i=0;i<playerDetailInfo.size();i++){
			table.getColum(0)[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e){
					JLabel label = (JLabel)e.getSource();
					String name = label.getText();
					int id = 0;
					try {
						id = playerLogic_db.getIDbyName(name, "");
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}System.out.println(id);
					WebFrame.frame.setPanel(turnController.turnToPlayerDetials(id), name);
				}
			});
			table.getColum(1)[i].addMouseListener(new MouseAdapter() {
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

	
	@Override
	public void run() {
		infoInit();
	}
}
