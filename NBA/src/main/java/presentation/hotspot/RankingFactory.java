package presentation.hotspot;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import presentation.component.GLabel;
import presentation.component.TeamImageAssist;
import presentation.contenui.TableUtility;
import presentation.contenui.TurnController;
import presentation.mainui.StartUI;
import bussinesslogic.player.PlayerLogic_db;
import bussinesslogic.team.TeamLogic;
import data.po.teamData.TeamBaseInfo;
import data.po.teamData.TeamCompleteInfo;

public class RankingFactory {
	
	TeamImageAssist imgAssist = new TeamImageAssist();
	
	private PlayerLogic_db logic = new PlayerLogic_db();
	private TeamLogic logict = new TeamLogic();
	
	private int getID(String name){
		int id = 1;
		try {
			id = logic.getIDbyName(name, "");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	

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
		player.addMouseListener(new PlayerListener(getID(namestr)));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(145,375),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(getInfo(teamstr)));
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
		player.addMouseListener(new PlayerListener(getID(namestr)));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(205,316),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(getInfo(teamstr)));
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
		player.addMouseListener(new PlayerListener(getID(namestr)));
		player.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		GLabel team = new GLabel(imgAssist.loadImageIcon("迭代一数据/teams/"+TableUtility.checkNOH(TableUtility.getShortChTeam(teamstr))+".svg", 95, 75),
				new Point(460,23),new Point(95,75),p,true);
		team.addMouseListener(new TeamListener(getInfo(teamstr)));
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
		team.addMouseListener(new TeamListener(getInfo(teamstr)));
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
		team.addMouseListener(new TeamListener(getInfo(teamstr)));
		team.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		GLabel name = new GLabel(teamstr,new Point(170,30),new Point(180,30),p,true,0,20);
		GLabel detail = new GLabel(union,new Point(170,60),new Point(180,30),p,true,0,12);
		GLabel infoLabel = new GLabel(data,new Point(370,30),new Point(130,60),p,true,0,22);
		
		return p;
	}
	
	
	private TeamBaseInfo getInfo(String shortName){
		 try {
			ArrayList<TeamCompleteInfo> list = logict.GetPartCompleteInfo(shortName, "14-15", "yes");
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
	
	
	private String getSeason(String date){
		String[] temp = date.split("-");
		int year = Integer.parseInt(temp[0]);
		int month = Integer.parseInt(temp[1]);
		
		if(month>=10){
			String res = String.valueOf(year).substring(2, 4)+"-"+String.valueOf(year+1).substring(2, 4);
			return res;
		}else{
			String res = String.valueOf(year-1).substring(2, 4)+"-"+String.valueOf(year).substring(2, 4);
			return res;
		}
		
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
		
		TeamBaseInfo info;
		
		public TeamListener(TeamBaseInfo info){
			this.info = info;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TurnController tc = new TurnController();
			StartUI.startUI.turn(tc.turnToTeamDetials(info));
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
		int id;
		
		public PlayerListener(int id){
			this.id = id;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			TurnController tc = new TurnController();
			StartUI.startUI.turn(tc.turnToPlayerDetials(id));
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
