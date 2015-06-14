package presentation.hotspot;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import presentation.component.GLabel;
import presentation.component.TeamImageAssist;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.mainui.StartUI;
import data.po.PlayerDataPO;
import data.po.TeamDataPO;

public class RankingFactory {
	
	TeamImageAssist imgAssist = new TeamImageAssist();
	

	

	
	private String[] getDetailInfo_progress(String str){
//		1;马修 德拉维多瓦;8;后卫;克利夫兰;骑士;11.2;49.33333333333332
//		2;安德鲁 伊格达拉;9;后卫-前锋;金州;勇士;11.2;28.73563218390805
//		3;文斯 卡特;15;后卫-前锋;孟菲斯;灰熊;8.0;26.984126984126988
//		4;提莫菲 莫兹戈夫;20;中锋;克利夫兰;骑士;12.4;26.530612244897956
//		5;杰夫 蒂格;0;后卫;亚特兰大;老鹰;21.2;26.19047619047618
		String[] temp = str.split(";");
		
		String name = temp[1];
		String detail = temp[2] + " / " + temp[3] + " / " + temp[4] + temp[5];
		String data = temp[6] + "/" + temp[7].substring(0, 4) + "%";
		String team = temp[5];
		
		String[] info =  {name,detail,data,team};
		return info;
	}
	
	public JPanel getPlayerProgress(String[] data,String type){
		JPanel panel = getInitPanel();
		
		for(int i=0;i<5;i++){
			String[] info = getDetailInfo_progress(data[i]);
			if(i==0){
				panel.add(this.getPlayerPanel_1_progress(info,type));
			}else{
				panel.add(this.getPlayerPanel_2to5(i+1, info,type));
			}
		}
		return panel;
	}

	

	

	private String[] getDetailInfo_player(String str){
//		1;安东尼 戴维斯;23;前锋-中锋;新奥尔良;鹈鹕;31.5
		String[] temp = str.split(";");
		
		String name = temp[1];
		String detail = temp[2] + " / " + temp[3] + " / " + temp[4] + temp[5];
		String data = temp[6];
		String team = temp[5];
		
		String[] info =  {name,detail,data,team};
		return info;
	}
	
	private String[] getDetailInfo_player_percent(String str){
//		1;安东尼 戴维斯;23;前锋-中锋;新奥尔良;鹈鹕;31.5
		String[] temp = str.split(";");
		
		String name = temp[1];
		String detail = temp[2] + " / " + temp[3] + " / " + temp[4] + temp[5];
		String data = temp[6] + "%";
		String team = temp[5];
		
		String[] info =  {name,detail,data,team};
		return info;
	}
	
	public JPanel getPlayerSeason(String[] data,boolean percent,String type){
		JPanel panel = getInitPanel();
		
		for(int i=0;i<5;i++){
			String[] info;
			if(percent){
				info = getDetailInfo_player_percent(data[i]);
			}else{
				info = getDetailInfo_player(data[i]);
			}
			if(i==0){
				panel.add(this.getPlayerPanel_1(info,type));
			}else{
				panel.add(this.getPlayerPanel_2to5(i+1, info,type));
			}
		}
		
		return panel;
	}
	
	public JPanel getPlayerDaily(String[] data,String type){
		JPanel panel = getInitPanel();
		
		for(int i=0;i<5;i++){
			String[] info = getDetailInfo_player(data[i]);
			if(i==0){
				panel.add(this.getPlayerPanel_1(info,type));
			}else{
				panel.add(this.getPlayerPanel_2to5(i+1, info,type));
			}
		}
		
		return panel;
	}
	
//	team+";"+shortteam+";"+union+";"+data;
	private String[] getDetailInfo_team(String str){
		String[] temp = str.split(";");
		
		String team = TableUtility.getChTeam(temp[0]);
		String shortteam = temp[1];
		String union = TableUtility.getChUnion(temp[2]);
		String data = temp[3];
		
		String[] info =  {team,shortteam,union,data};
		return info;
	}
	
	public JPanel getTeamSeason(String[] data,String type){
		JPanel panel = getInitPanel();
		
		for(int i=0;i<5;i++){
			String[] info = getDetailInfo_team(data[i]);
			if(i==0){
				panel.add(this.getTeamPanel_1(info));
			}else{
				panel.add(this.getTeamPanel_2to5(i+1, info));
			}
		}
		
		return panel;
	}
	
	private JPanel getPlayerPanel_1_progress(String[] info,String type){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		String namestr = info[0];
		String detailstr = info[1];
		String data = info[2];
		String teamstr = info[3];
		
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
		GLabel player = new GLabel(getBigPlayer(1,type),new Point(80,130),new Point(230,185),p,true);
//		player.addMouseListener(new PlayerListener(name));
//		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(145,375),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(teamstr));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(namestr,new Point(130,50),new Point(220,30),p,true,0,18);
		GLabel detail = new GLabel(detailstr,new Point(130,80),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(130,325),new Point(180,60),p,true,0,26);
		
		return p;
		
	}
	
	private JPanel getPlayerPanel_1(String[] info,String type){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		String namestr = info[0];
		String detailstr = info[1];
		String data = info[2];
		String teamstr = info[3];
		
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
		GLabel player = new GLabel(getBigPlayer(1,type),new Point(0,102),new Point(207,329),p,true);
//		player.addMouseListener(new PlayerListener(name));
//		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(205,316),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(teamstr));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(namestr,new Point(180,50),new Point(220,30),p,true,0,18);
		GLabel detail = new GLabel(detailstr,new Point(180,90),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(225,220),new Point(180,60),p,true,0,26);
		
		return p;
		
	}
	
	private JPanel getPlayerPanel_2to5(int number,String[] info,String type){
		JPanel p = new JPanel();
		p.setBounds(370, 150+(number-2)*115-100, 570, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		String namestr = info[0];
		String detailstr = info[1];
		String data = info[2];
		String teamstr = info[3];
		
		ImageIcon image = new ImageIcon();
		switch(number){
		case 2:image = HotspotUtil.ranking_2;break;
		case 3:image = HotspotUtil.ranking_3;break;
		case 4:image = HotspotUtil.ranking_4;break;
		case 5:image = HotspotUtil.ranking_5;
		}
		
		GLabel num = new GLabel(image,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(number,type),new Point(75,23),new Point(66,66),p,true);
//		player.addMouseListener(new PlayerListener(namestr));
//		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(460,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(teamstr));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(namestr,new Point(170,30),new Point(220,30),p,true,0,18);
		GLabel detail = new GLabel(detailstr,new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(350,30),new Point(130,60),p,true,0,20);
		
		return p;
	}
	
	private JPanel getTeamPanel_1(String[] info){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		
		String teamstr = info[0];
		String shortteam = TableUtility.checkNOH(info[1]);
		String union = info[2];
		String data = info[3];
		
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+shortteam+".svg", 200, 300),new Point(80,110),new Point(200,300),p,true);
		team.addMouseListener(new TeamListener(shortteam));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(teamstr,new Point(160,40),new Point(200,30),p,true,0,20);
		GLabel detail = new GLabel(union,new Point(160,80),new Point(200,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(150,390),new Point(130,60),p,true,0,24);
		
		return p;
		
	}
	
	private JPanel getTeamPanel_2to5(int number,String[] info){
		JPanel p = new JPanel();
		p.setBounds(370, 150+(number-2)*115-100, 570, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		String teamstr = info[0];
		String shortteam = TableUtility.checkNOH(info[1]);
		String union = info[2];
		String data = info[3];
		
		ImageIcon image = new ImageIcon();
		switch(number){
		case 2:image = HotspotUtil.ranking_2;break;
		case 3:image = HotspotUtil.ranking_3;break;
		case 4:image = HotspotUtil.ranking_4;break;
		case 5:image = HotspotUtil.ranking_5;
		}
		
		GLabel num = new GLabel(HotspotUtil.ranking_2,new Point(18,38),new Point(36,40),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+shortteam+".svg", 95, 75),new Point(68,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(shortteam));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(teamstr,new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(union,new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(370,30),new Point(130,60),p,true,0,22);
		
		return p;
	}
	

	
	private String getPercent(double d){
		DecimalFormat df = new DecimalFormat("#.0");  
		return String.valueOf(df.format(d*100)) + "%";
	}
	
	private ImageIcon getBigPlayer(int num, String type){
		File f = new File("Hotimg/"+num+"_"+type+".png");
		if(f.exists()){
			return new ImageIcon("Hotimg/"+num+"_"+type+".png");
		}else{
			return HotspotUtil.noBigPlayer;
		}
	}
	
	private ImageIcon getPlayer(int num,String type){
		File f = new File("Hotimg/"+num+"_"+type+".png");
		if(f.exists()){
			return new ImageIcon("Hotimg/"+num+"_"+type+".png");
		}else{
			return HotspotUtil.noPlayer;
		}
	}
	
	private JPanel getInitPanel(){
		JPanel p = new JPanel();
		p.setBounds(0, 80, 940, 600);
		p.setLayout(null);
		p.setOpaque(false);
		return p;
	}
	
	
	
	//////////////////////////////////////////////////////////////////
	//
	//                       上一迭代
	//
	//////////////////////////////////////////////////////////////////
	
	public JPanel getTeamSeason(ArrayList<TeamDataPO> teams,String type){
		JPanel panel = getInitPanel();
		String[] info = new String[5];
		
		for(int i=0;i<5;i++){
			if(type.equals("场均得分")){
				info[i] = String.valueOf(teams.get(i).getPPG());
			}else if(type.equals("场均篮板")){
				info[i] = String.valueOf(teams.get(i).getBackBoardPG());
			}else if(type.equals("场均助攻")){
				info[i] = String.valueOf(teams.get(i).getAssitNumberPG());
			}else if(type.equals("场均盖帽")){
				info[i] = String.valueOf(teams.get(i).getRejectionPG());
			}else if(type.equals("场均抢断")){
				info[i] = String.valueOf(teams.get(i).getStealNumberPG());
			}else if(type.equals("三分命中率")){
				info[i] = getPercent(teams.get(i).getTPEff());
			}else if(type.equals("投篮命中率")){
				info[i] = getPercent(teams.get(i).getShootEff());
			}else if(type.equals("罚球命中率")){
				info[i] = getPercent(teams.get(i).getFTEff());
			}
		}
		
		panel.add(getTeamPanel1(teams.get(0),info[0]));
		panel.add(getTeamPanel2(teams.get(1),info[1]));
		panel.add(getTeamPanel3(teams.get(2),info[2]));
		panel.add(getTeamPanel4(teams.get(3),info[3]));
		panel.add(getTeamPanel5(teams.get(4),info[4]));
		
		return panel;
	}
	
	public JPanel getPlayerProgress(PlayerDataPO[] players,String type){
		JPanel panel = getInitPanel();
		String[] info = new String[5];
		
		for(int i=0;i<5;i++){
			if(type.equals("场均得分")){
				info[i] = String.valueOf(players[i].getRecentAvgP()) + "/" + String.valueOf(players[i].getPProgressPecentage());
			}else if(type.equals("场均篮板")){
				info[i] = String.valueOf(players[i].getRecentAvgB()) + "/" + String.valueOf(players[i].getBProgressPecentage());
			}else if(type.equals("场均助攻")){
				info[i] = String.valueOf(players[i].getRecentAvgA()) + "/" + String.valueOf(players[i].getAProgressPecentage());
			}
		}
		
		panel.add(getPlayerPanel1(players[0],info[0]));
		panel.add(getPlayerPanel2(players[1],info[1]));
		panel.add(getPlayerPanel3(players[2],info[2]));
		panel.add(getPlayerPanel4(players[3],info[3]));
		panel.add(getPlayerPanel5(players[4],info[4]));
		
		return panel;
	}
	
	public JPanel getPlayerSeason(PlayerDataPO[] players,String type){
		JPanel panel = getInitPanel();
		String[] info = new String[5];
		
		for(int i=0;i<5;i++){
			if(type.equals("场均得分")){
				info[i] = String.valueOf(players[i].getPPG());
			}else if(type.equals("场均篮板")){
				info[i] = String.valueOf(players[i].getBPG());
			}else if(type.equals("场均助攻")){
				info[i] = String.valueOf(players[i].getAPG());
			}else if(type.equals("场均盖帽")){
				info[i] = String.valueOf(players[i].getRPG());
			}else if(type.equals("场均抢断")){
				info[i] = String.valueOf(players[i].getStealPG());
			}else if(type.equals("三分命中率")){
				info[i] = getPercent(players[i].getThreePGPercentage());
			}else if(type.equals("投篮命中率")){
				info[i] = getPercent(players[i].getFieldGoalPercentage());
			}else if(type.equals("罚球命中率")){
				info[i] = getPercent(players[i].getFTPercentage());
			}
		}
		
		panel.add(getPlayerPanel1(players[0],info[0]));
		panel.add(getPlayerPanel2(players[1],info[1]));
		panel.add(getPlayerPanel3(players[2],info[2]));
		panel.add(getPlayerPanel4(players[3],info[3]));
		panel.add(getPlayerPanel5(players[4],info[4]));
		
		return panel;
	}
	
	
	
	public JPanel getPlayerToday(PlayerDataPO[] players,String type){
		JPanel panel = getInitPanel();
		String[] info = new String[5];
		
		if(!(players[0].getName()==null)){
			for(int i=0;i<5;i++){
				if(type.equals("得分")){
					info[i] = String.valueOf(players[i].getPTS());
				}else if(type.equals("篮板")){
					info[i] = String.valueOf(players[i].getBackboard());
				}else if(type.equals("助攻")){
					info[i] = String.valueOf(players[i].getAssist());
				}else if(type.equals("盖帽")){
					info[i] = String.valueOf(players[i].getRejection());
				}else if(type.equals("抢断")){
					info[i] = String.valueOf(players[i].getSteal());
				}
			}
			
			panel.add(getPlayerPanel1(players[0],info[0]));
			panel.add(getPlayerPanel2(players[1],info[1]));
			panel.add(getPlayerPanel3(players[2],info[2]));
			panel.add(getPlayerPanel4(players[3],info[3]));
			panel.add(getPlayerPanel5(players[4],info[4]));
		}else{
			GLabel l = new GLabel(new ImageIcon("img/match/nogame.png"),new Point(350,150),new Point(200,200),panel,true);
		}
		
		return panel;
	}
	
	private JPanel getTeamPanel1(TeamDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
//		GLabel player = new GLabel(new ImageIcon("迭代一数据/players/action/"+po.getName()+".png"),new Point(0,102),new Point(207,329),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+po.getShortName()+".svg", 200, 300),new Point(0,102),new Point(200,300),p,true);
		team.addMouseListener(new TeamListener(po.getShortName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(TableUtility.getChTeam(po.getName()),new Point(160,40),new Point(200,30),p,true,0,20);
		GLabel detail = new GLabel(TableUtility.getChUnion(po.getEorW()),new Point(160,80),new Point(200,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(230,220),new Point(130,60),p,true,0,24);
		
		return p;
	}
	
	
	
	private JPanel getTeamPanel2(TeamDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 150-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_2,new Point(18,38),new Point(36,40),p,true);
//		GLabel player = new GLabel(new ImageIcon("迭代一数据/players/portrait/"+po.getName()+".png"),new Point(68,23),new Point(81,62),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+po.getShortName()+".svg", 95, 75),new Point(68,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getShortName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(TableUtility.getChTeam(po.getName()),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(TableUtility.getChUnion(po.getEorW()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getTeamPanel3(TeamDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 260-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_3,new Point(18,38),new Point(36,40),p,true);
//		GLabel player = new GLabel(new ImageIcon("迭代一数据/players/portrait/"+po.getName()+".png"),new Point(68,23),new Point(81,62),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+po.getShortName()+".svg", 95, 75),new Point(68,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getShortName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(TableUtility.getChTeam(po.getName()),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(TableUtility.getChUnion(po.getEorW()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getTeamPanel4(TeamDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 370-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_4,new Point(18,38),new Point(36,40),p,true);
//		GLabel player = new GLabel(new ImageIcon("迭代一数据/players/portrait/"+po.getName()+".png"),new Point(68,23),new Point(81,62),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+po.getShortName()+".svg", 95, 75),new Point(68,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getShortName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(TableUtility.getChTeam(po.getName()),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(TableUtility.getChUnion(po.getEorW()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getTeamPanel5(TeamDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 480-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_5,new Point(18,38),new Point(36,40),p,true);
//		GLabel player = new GLabel(new ImageIcon("迭代一数据/players/portrait/"+po.getName()+".png"),new Point(68,23),new Point(81,62),p,true);
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+po.getShortName()+".svg", 95, 75),new Point(68,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getShortName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(TableUtility.getChTeam(po.getName()),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(TableUtility.getChUnion(po.getEorW()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getPlayerPanel1(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
		GLabel player = new GLabel(getBigPlayer(po.getName()),new Point(0,102),new Point(207,329),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(215,315),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(160,40),new Point(200,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(160,80),new Point(200,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(230,220),new Point(130,60),p,true,0,24);
		
		return p;
	}
	
	private JPanel getPlayerPanel2(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 150-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_2,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(435,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getPlayerPanel3(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 260-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_3,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(435,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getPlayerPanel4(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 370-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_4,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(435,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	private JPanel getPlayerPanel5(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(350, 480-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_5,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(435,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,13);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	
	private JPanel getProgressPanel1(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(0, 150-120, 370, 470);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_1,new Point(20,45),new Point(36,40),p,true);
		GLabel player = new GLabel(getBigPlayer(po.getName()),new Point(0,102),new Point(207,329),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(225,316),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(180,50),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(180,90),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(245,220),new Point(80,60),p,true,0,26);
		
		return p;
	}
	
	private JPanel getProgressPanel2(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(370, 150-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_2,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(422,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(80,60),p,true,0,24);
		
		return p;
	}
	
	private JPanel getProgressPanel3(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(370, 260-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_3,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(422,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(80,60),p,true,0,24);
		
		return p;
	}
	
	private JPanel getProgressPanel4(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(370, 370-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_4,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(422,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(80,60),p,true,0,24);
		
		return p;
	}
	
	private JPanel getProgressPanel5(PlayerDataPO po,String info){
		JPanel p = new JPanel();
		p.setBounds(370, 480-120, 520, 110);
		p.setLayout(null);
		p.setOpaque(false);
		
		GLabel num = new GLabel(HotspotUtil.ranking_5,new Point(18,38),new Point(36,40),p,true);
		GLabel player = new GLabel(getPlayer(po.getName()),new Point(68,23),new Point(81,62),p,true);
		player.addMouseListener(new PlayerListener(po.getName()));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(po.getTeamName())+".svg", 95, 75),new Point(422,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(po.getTeamName()));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(po.getName(),new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(po.getNumber()+" / "+TableUtility.getChPosition(po.getPosition())+" / "+TableUtility.getChTeam(po.getTeamName()),new Point(170,60),new Point(180,30),p,true,0,13);
		GLabel infoLabel = new GLabel(info,new Point(354,40),new Point(80,60),p,true,0,24);
		
		return p;
	}
	
	private ImageIcon getBigPlayer(String name){
		File f = new File("迭代一数据/players/action/"+name+".png");
		if(f.exists()){
			return new ImageIcon("迭代一数据/players/action/"+name+".png");
		}else{
			return HotspotUtil.noBigPlayer;
		}
	}
	
	private ImageIcon getPlayer(String name){
		File f = new File("迭代一数据/players/portrait/"+name+".png");
		if(f.exists()){
			return new ImageIcon("迭代一数据/players/portrait/"+name+".png");
		}else{
			return HotspotUtil.noPlayer;
		}
	}

	class TeamListener implements MouseListener{
		
		String shortName;
		
		public TeamListener(String shortName){
			this.shortName = shortName;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TurnController tc = new TurnController();
			StartUI.startUI.turn(tc.turnToTeamDetials(shortName));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
	
	class PlayerListener implements MouseListener{
		
		String name;
		
		public PlayerListener(String name){
			this.name = name;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TurnController tc = new TurnController();
			StartUI.startUI.turn(tc.turnToPlayerDetials(name));
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
	
	
	
}
