package presentation.hotspot;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.UIUtil;
import bussinesslogic.team.TeamLogic;
import data.po.TeamDataPO;
import data.po.teamData.TeamCompleteInfo;

public class HotTeamSeasonPanel extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";

	GLabel title;

	SelectLabel score;  //场均得分
	SelectLabel backboard;  //场均篮板
	SelectLabel assis;  //场均助攻
	SelectLabel block;  //场均盖帽
	SelectLabel steal;  //场均抢断
	SelectLabel tps;  //三分命中率
	SelectLabel shooting;  //投篮命中率
	SelectLabel free;  //罚球命中率
	
	SelectLabel[] menuItem = new SelectLabel[8];
	
	JComboBox<String> seasonChooser;

	TeamLogic logic = new TeamLogic();


	RankingFactory factory = new RankingFactory();
	JPanel rankingPanel;
	
	@Override
	public void refreshUI() {
		this.remove(title);
		this.remove(score);
		this.remove(backboard);
		this.remove(assis);
		this.remove(block);
		this.remove(steal);
		this.remove(tps);
		this.remove(shooting);
		this.remove(free);
		this.remove(seasonChooser);
		this.remove(rankingPanel);

		init();
	}

	public HotTeamSeasonPanel() {
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
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);

		init();
	}
	
	private void init(){
		String[] seasons = {"13-14赛季","12-13赛季",};
		seasonChooser = new JComboBox<String>(seasons);
		seasonChooser.setBounds(800-this.getX(), 42, 120, 30);
		seasonChooser.addActionListener(new SeasonListener());
		this.add(seasonChooser);

		title = new GLabel("   赛季热点球队",new Point(80-this.getX(),30),new Point(890,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.nbaBlue);
		title.setForeground(UIUtil.bgWhite);

		score = new SelectLabel("场均得分",new Point(80-this.getX(),83),new Point(110,35),this,true,0,16);
		score.setOpaque(true);
		score.setBackground(UIUtil.bgGrey);
		score.setForeground(UIUtil.bgWhite);
		score.setHorizontalAlignment(JLabel.CENTER);
		score.setSelected(true);
		score.addMouseListener(new MenuListener());
		menuItem[0] = score;

		getRankingPanel("场均得分");

		backboard = new SelectLabel("场均篮板",new Point(191-this.getX(),83),new Point(110,35),this,true,0,16);
		backboard.setOpaque(true);
		backboard.setBackground(UIUtil.bgGrey);
		backboard.setForeground(UIUtil.bgWhite);
		backboard.setHorizontalAlignment(JLabel.CENTER);
		backboard.addMouseListener(new MenuListener());
		menuItem[1] = backboard;

		assis = new SelectLabel("场均助攻",new Point(302-this.getX(),83),new Point(110,35),this,true,0,16);
		assis.setOpaque(true);
		assis.setBackground(UIUtil.bgGrey);
		assis.setForeground(UIUtil.bgWhite);
		assis.setHorizontalAlignment(JLabel.CENTER);
		assis.addMouseListener(new MenuListener());
		menuItem[2] = assis;

		block = new SelectLabel("场均盖帽",new Point(413-this.getX(),83),new Point(110,35),this,true,0,16);
		block.setOpaque(true);
		block.setBackground(UIUtil.bgGrey);
		block.setForeground(UIUtil.bgWhite);
		block.setHorizontalAlignment(JLabel.CENTER);
		block.addMouseListener(new MenuListener());
		menuItem[3] = block;

		steal = new SelectLabel("场均抢断",new Point(524-this.getX(),83),new Point(110,35),this,true,0,16);
		steal.setOpaque(true);
		steal.setBackground(UIUtil.bgGrey);
		steal.setForeground(UIUtil.bgWhite);
		steal.setHorizontalAlignment(JLabel.CENTER);
		steal.addMouseListener(new MenuListener());
		menuItem[4] = steal;
		
		tps = new SelectLabel("三分命中率",new Point(635-this.getX(),83),new Point(110,35),this,true,0,16);
		tps.setOpaque(true);
		tps.setBackground(UIUtil.bgGrey);
		tps.setForeground(UIUtil.bgWhite);
		tps.setHorizontalAlignment(JLabel.CENTER);
		tps.addMouseListener(new MenuListener());
		menuItem[5] = tps;
		
		shooting = new SelectLabel("投篮命中率",new Point(746-this.getX(),83),new Point(110,35),this,true,0,16);
		shooting.setOpaque(true);
		shooting.setBackground(UIUtil.bgGrey);
		shooting.setForeground(UIUtil.bgWhite);
		shooting.setHorizontalAlignment(JLabel.CENTER);
		shooting.addMouseListener(new MenuListener());
		menuItem[6] = shooting;
		
		free = new SelectLabel("罚球命中率",new Point(857-this.getX(),83),new Point(113,35),this,true,0,16);
		free.setOpaque(true);
		free.setBackground(UIUtil.bgGrey);
		free.setForeground(UIUtil.bgWhite);
		free.setHorizontalAlignment(JLabel.CENTER);
		free.addMouseListener(new MenuListener());
		menuItem[7] = free;

		this.repaint();
	}
	
	private String getType(String ch){
		switch(ch){
		case "场均得分":return "ppg";
		case "场均篮板":return "backboardpg";
		case "场均助攻":return "assitnumberpg";
		case "场均盖帽":return "rejectionpg";
		case "场均抢断":return "stealnumberpg";
		case "三分命中率":return "tpeff";
		case "投篮命中率":return "shooteff";
		case "罚球命中率":return "fteff";
		default:return "";
		}
	}
	
	private String getDataStr(TeamCompleteInfo t,String property){
		String team = t.getBaseinfo().getName();
		String shortteam = t.getBaseinfo().getShortName();
		String union = t.getBaseinfo().getEorW();
		String data = "";
		switch(property){
		case "ppg":data = String.valueOf(t.getLData().getPPG());break;
		case "backboardpg":String.valueOf(t.getLData().getBackBoardPG());break;
		case "assitnumberpg":String.valueOf(t.getLData().getAssitNumberPG());break;
		case "rejectionpg":String.valueOf(t.getLData().getRejectionPG());break;
		case "stealnumberpg":String.valueOf(t.getLData().getStealNumberPG());break;
		case "tpeff":String.valueOf(t.getLData().getTPEff());break;
		case "shooteff":String.valueOf(t.getLData().getShootEff());break;
		case "fteff":String.valueOf(t.getLData().getFTEff());break;
		}
		
		return team+";"+shortteam+";"+union+";"+data;
	}
	
	
	// property为排序的属性
	// ppg场均得分、assitnumberpg场均助攻、stealnumberpg场均抢断、rejectionpg场均盖帽等（teambasedata表的属性都可以）
	// 返回的array不只5项，从大到小排序。
//	public ArrayList<TeamCompleteInfo> hotTeamSeason(String season,
//			String property, String isseason) throws RemoteException {
//	
	
	public void getRankingPanel(String type){
		String property = getType(type);

		ArrayList<TeamCompleteInfo> teams;
		try {
			teams = logic.hotTeamSeason("14-15", property, "unknown");
			String[] info = new String[5];
			for(int i=0;i<5;i++){
				info[i] = getDataStr(teams.get(i),property);
			}
			JPanel p = factory.getTeamSeason(info,property);
			rankingPanel = p;
			this.add(rankingPanel);
			this.repaint();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getSeasonStr(){
		String s = (String)seasonChooser.getSelectedItem();
		return s.substring(0, 5);
	}
	
	private String transType(String ch){
		String res = "";
		if(ch.equals("场均得分")){
			res = "PPG"; 
		}else if(ch.equals("场均篮板")){
			res = "BackBoardPG"; 
		}else if(ch.equals("场均助攻")){
			res = "AssitNumberPG"; 
		}else if(ch.equals("场均盖帽")){
			res = "RejectionPG"; 
		}else if(ch.equals("场均抢断")){
			res = "StealNumberPG"; 
		}else if(ch.equals("三分命中率")){
			res = "TPEff"; 
		}else if(ch.equals("投篮命中率")){
			res = "ShootEff"; 
		}else if(ch.equals("罚球命中率")){
			res = "FTEff"; 
		}
		return res;
	}

	class MenuListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			SelectLabel sl = (SelectLabel)e.getSource();

			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			sl.setSelected(true);

			if(rankingPanel!=null){
				HotTeamSeasonPanel.this.remove(rankingPanel);
			}

			String type = sl.getText();
			HotTeamSeasonPanel.this.getRankingPanel(type);
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	
	class SeasonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//更新标签
			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			score.setSelected(true);
			
			if(rankingPanel!=null){
				HotTeamSeasonPanel.this.remove(rankingPanel);
			}
			
			getRankingPanel("场均得分");
		}
		
	}
}
