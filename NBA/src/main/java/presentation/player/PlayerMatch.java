package presentation.player;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import presentation.mainui.WebTable;
import bussinesslogic.match.MatchLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.po.matchData.MatchPlayer;

public class PlayerMatch extends BgPanel{
	
	private static final long serialVersionUID = 1L;
	private WebTable basicTable;
	private MatchLogic matchLogic;
	private PlayerLogic_db playerLogic_db;
	private Rectangle rectangle;
	private GLabel label, border;
	private ArrayList<MatchPlayer> matchPlayer;
	private JComboBox<String> comboBoxSeason, comboBoxNormal;
	private String playerName;
	private boolean isFirst;

	public PlayerMatch(int id) {
		super("");

		playerLogic_db = new PlayerLogic_db();
		try {
			this.playerName = playerLogic_db.getdetail(id).getNameCn().equals("null")?playerLogic_db.getdetail(id).getName():playerLogic_db.getdetail(id).getNameCn();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}System.out.println(playerName);
		matchLogic = new MatchLogic();
		matchPlayer = matchLogic.GetPlayerMatch(playerName, "unknown", "unknown");System.out.println(matchPlayer.size());
		String[] season = {"14-15", "13-14", "12-13", "11-12", "10-11", 
				"09-10", "08-09", "07-08", "06-07", "05-06"};
		String[] normal = {"常规赛", "季后赛"};
		
		matchPlayer = matchLogic.GetPlayerMatch(playerName, "yes", season[0]);
		
		comboBoxSeason = new JComboBox<String>(season);
		comboBoxSeason.setBounds(680, 35, 100, 30);
		comboBoxSeason.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxNormal.getSelectedIndex() == 0){
					matchPlayer = matchLogic.GetPlayerMatch(PlayerMatch.this.playerName, "yes", comboBoxSeason.getSelectedItem().toString());
				}else{
					matchPlayer = matchLogic.GetPlayerMatch(PlayerMatch.this.playerName, "no", comboBoxSeason.getSelectedItem().toString());
				}
				basicSetting();
			}
		});
		this.add(comboBoxSeason);
		
		comboBoxNormal = new JComboBox<String>(normal);
		comboBoxNormal.setBounds(800, 35, 100, 30);
		comboBoxNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isFirst = false;
				if(comboBoxNormal.getSelectedIndex() == 0){
					matchPlayer = matchLogic.GetPlayerMatch(PlayerMatch.this.playerName, "yes", comboBoxSeason.getSelectedItem().toString());
				}else{
					matchPlayer = matchLogic.GetPlayerMatch(PlayerMatch.this.playerName, "no", comboBoxSeason.getSelectedItem().toString());
				}
				basicSetting();
			}
		});
		this.add(comboBoxNormal);

		this.setBounds(0, 100, 940, 500);
		this.setLayout(null);
		this.setVisible(true);
		this.setBackground(UIUtil.bgWhite);

		init();

	}

	private void init(){
		isFirst = true;
		
		rectangle = new Rectangle(0, 80, 940, 320);

		label = new GLabel("比赛数据", new Point(100, 0), new Point(100, 30), this, true, 0, 20);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBackground(UIUtil.bgWhite);
		label.setOpaque(true);
		
		border = new GLabel("", new Point(0, 13), new Point(300, 4), this, true);
		border.setBackground(UIUtil.nbaBlue);
		border.setOpaque(true);

		basicSetting();

	}
	
	private void basicSetting(){
		if(!isFirst)this.remove(basicTable);
		
		String[] header = {"日期", "客－主", "首发", "<html>在场<br>时间<html>", "得分", "<html>命中<br>％<html>", "出手", 
				"<html>出手<br>命中<html>", "<html>三分<br>％<html>", "<html>三分<br>出手<html>", "<html>三分<br>命中<html>", 
				"<html>罚球<br>％<html>", "罚球", "<html>罚球<br>命中<html>", "篮板", 
				"助攻", "抢断", "盖帽", "失误", "犯规", "<html>比赛<br>链接<html>"};
		
		Object[][] data = new Object[matchPlayer.size()][header.length];
		for(int i=matchPlayer.size()-1;i>=0;i--){
			data[i][0] = matchPlayer.get(i).getDate();
			data[i][1] = matchPlayer.get(i).getTwoteam();
			if(matchPlayer.get(i).getIsFirst().equals("1")){
				data[i][2] = "是";
			}else{
				data[i][2] = "否";
			}
			data[i][3] = matchPlayer.get(i).getTime();
			data[i][4] = matchPlayer.get(i).getPoints();
			data[i][5] = matchPlayer.get(i).getShootEff().replaceAll("%", "");
			data[i][6] = matchPlayer.get(i).getShoot();
			data[i][7] = matchPlayer.get(i).getShootEffNumber();
			data[i][8] = matchPlayer.get(i).getTPShootEff().replaceAll("%", "");
			data[i][9] = matchPlayer.get(i).getTPShoot();
			data[i][10] = matchPlayer.get(i).getTPShootEffNumber();
			data[i][11] = matchPlayer.get(i).getFTShootEff().replaceAll("%", "");
			data[i][12] = matchPlayer.get(i).getFT();
			data[i][13] = matchPlayer.get(i).getFTShootEffNumber();
			data[i][14] = matchPlayer.get(i).getBank();
			data[i][15] = matchPlayer.get(i).getAss();
			data[i][16] = matchPlayer.get(i).getSteal();
			data[i][17] = matchPlayer.get(i).getRejection();
			data[i][18] = matchPlayer.get(i).getTo();
			data[i][19] = matchPlayer.get(i).getFoul();
			data[i][20] = matchPlayer.get(i).getMatchID();
		}
		
		basicTable = new WebTable(header, data, rectangle, UIUtil.bgWhite);
		basicTable.setVisible(true);
		for(int i=2;i<header.length;i++){
			basicTable.setColumDataCenter(i);
		}
		basicTable.setColumForeground(20, UIUtil.nbaBlue);
		basicTable.setColumHand(20);
		this.add(basicTable);
		
		this.updateUI();
	}
	
	@Override
	public void refreshUI(){
		if(this!=null){
			this.removeAll();
			this.init();
		}
	}
}
