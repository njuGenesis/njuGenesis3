package presentation.hotspot;

import java.awt.Component;
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
import presentation.contenui.StatsUtil;
import presentation.contenui.UIUtil;
import presentation.hotspot.HotPlayerProgressPanel.SeasonListener;
import bussinesslogic.team.TeamLogic;
import data.po.TeamDataPO;
import data.po.teamData.TeamCompleteInfo;

public class HotTeamSeasonPanel extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";

	private GLabel title, borderUp, borderDown;

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
		Component[] c = this.getComponents();
		for(int i=0;i<c.length;i++){
			this.remove(c[i]);
		}

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
		//		this.add(seasonChooser);

		//--------------------标题--------------------

		borderUp = new GLabel("", new Point(0,0), new Point(940,4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		borderDown = new GLabel("", new Point(0,56), new Point(940,4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);

		title = new GLabel("   赛季热点球员",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		score = new SelectLabel("场均得分",new Point(0,83),new Point(117,35),this,true,0,16);
		score.setSelected(true);
		score.addMouseListener(new MenuListener());
		menuItem[0] = score;

		getRankingPanel("场均得分");

		backboard = new SelectLabel("场均篮板",new Point(118-this.getX(),83),new Point(117,35),this,true,0,16);
		backboard.addMouseListener(new MenuListener());
		menuItem[1] = backboard;

		assis = new SelectLabel("场均助攻",new Point(236-this.getX(),83),new Point(117,35),this,true,0,16);
		assis.addMouseListener(new MenuListener());
		menuItem[2] = assis;

		block = new SelectLabel("场均盖帽",new Point(354-this.getX(),83),new Point(117,35),this,true,0,16);
		block.addMouseListener(new MenuListener());
		menuItem[3] = block;

		steal = new SelectLabel("场均抢断",new Point(472-this.getX(),83),new Point(117,35),this,true,0,16);
		steal.addMouseListener(new MenuListener());
		menuItem[4] = steal;

		tps = new SelectLabel("三分命中率",new Point(590-this.getX(),83),new Point(117,35),this,true,0,16);
		tps.addMouseListener(new MenuListener());
		menuItem[5] = tps;

		shooting = new SelectLabel("投篮命中率",new Point(708-this.getX(),83),new Point(117,35),this,true,0,16);
		shooting.addMouseListener(new MenuListener());
		menuItem[6] = shooting;

		free = new SelectLabel("罚球命中率",new Point(826-this.getX(),83),new Point(117,35),this,true,0,16);
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
		case "backboardpg":data = String.valueOf(t.getLData().getBackBoardPG());break;
		case "assitnumberpg":data = String.valueOf(t.getLData().getAssitNumberPG());break;
		case "rejectionpg":data = String.valueOf(t.getLData().getRejectionPG());break;
		case "stealnumberpg":data = String.valueOf(t.getLData().getStealNumberPG());break;
		case "tpeff":data = String.valueOf(t.getLData().getTPEff())+"%";break;
		case "shooteff":data = String.valueOf(t.getLData().getShootEff())+"%";break;
		case "fteff":data = String.valueOf(t.getLData().getFTEff())+"%";break;
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
