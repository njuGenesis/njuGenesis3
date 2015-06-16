package presentation.hotspot;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.StatsUtil;
import presentation.contenui.UIUtil;
import presentation.hotspot.HotPlayerProgressPanel.PlayerProgress;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;

public class HotPlayerSeasonPanel extends BgPanel{
	
	
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

	PlayerLogic logic = new PlayerLogic();
	PlayerLogic_db logic_db = new PlayerLogic_db();


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
	
	public HotPlayerSeasonPanel() {
		super(bg);
		
		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);
		
		init();
		
//		String[] str = logic_db.getHotPlayerSeason("points");
//		for(int i = 0;i<5;i++){
//			System.out.println(str[i]);
//		}
	}
	
	private void init(){
		//--------------------标题--------------------
		String[] seasons = StatsUtil.seasons;
		seasonChooser = new JComboBox<String>(seasons);
		seasonChooser.setBounds(800-this.getX(), 42, 120, 30);
		seasonChooser.addActionListener(new SeasonListener());
//		this.add(seasonChooser);

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

		//--------------------标签--------------------
		score = new SelectLabel("场均得分",new Point(0,70),new Point(117,35),this,true,0,16);
		score.setSelected(true);
		score.addMouseListener(new MenuListener());
		menuItem[0] = score;

//		getRankingPanel("场均得分");

		backboard = new SelectLabel("场均篮板",new Point(118,70),new Point(117,35),this,true,0,16);
		backboard.addMouseListener(new MenuListener());
		menuItem[1] = backboard;

		assis = new SelectLabel("场均助攻",new Point(236-this.getX(),70),new Point(117,35),this,true,0,16);
		assis.addMouseListener(new MenuListener());
		menuItem[2] = assis;

		block = new SelectLabel("场均盖帽",new Point(354-this.getX(),70),new Point(117,35),this,true,0,16);
		block.addMouseListener(new MenuListener());
		menuItem[3] = block;

		steal = new SelectLabel("场均抢断",new Point(472-this.getX(),70),new Point(117,35),this,true,0,16);
		steal.addMouseListener(new MenuListener());
		menuItem[4] = steal;
		
		tps = new SelectLabel("三分命中率",new Point(590-this.getX(),70),new Point(117,35),this,true,0,16);
		tps.addMouseListener(new MenuListener());
		menuItem[5] = tps;
		
		shooting = new SelectLabel("投篮命中率",new Point(708-this.getX(),70),new Point(117,35),this,true,0,16);
		shooting.addMouseListener(new MenuListener());
		menuItem[6] = shooting;
		
		free = new SelectLabel("罚球命中率",new Point(826-this.getX(),70),new Point(117,35),this,true,0,16);
		free.addMouseListener(new MenuListener());
		menuItem[7] = free;

		PlayerSeason p = new PlayerSeason("场均得分");
		Thread t = new Thread(p);
		t.start();
		
		this.repaint();
	}
	
	private String getType(String ch){
		switch(ch){
		case "场均得分":return "points";
		case "场均篮板":return "rebounds";
		case "场均助攻":return "assists";
		case "场均盖帽":return "blocks";
		case "场均抢断":return "steals";
		case "三分命中率":return "tppct";
		case "投篮命中率":return "fgpct";
		case "罚球命中率":return "ftpct";
		default:return "";
		}
	}
	
	private boolean isPercent(String en){
		switch(en){
		case "tppct":
		case "fgpct":
		case "ftpct":return true;
		default:return false;
		}
	}
	
	public synchronized void getRankingPanel(String type){
		if(rankingPanel!=null){
			HotPlayerSeasonPanel.this.remove(rankingPanel);
		}
		
		String en = getType(type);
		String[] str = logic_db.getHotPlayerSeason(en);
//		PlayerDataPO[] players = logic.hotPlayerSeason(getSeasonStr(), type);
		
		JPanel p = factory.getPlayerSeason(str,isPercent(en),en+"_hotseason");
		rankingPanel = p;
		HotPlayerSeasonPanel.this.add(rankingPanel);
		HotPlayerSeasonPanel.this.repaint();
	}
	
	private String getSeasonStr(){
		String s = (String)seasonChooser.getSelectedItem();
		return s.substring(0, 5);
	}
	
	
	class MenuListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			SelectLabel sl = (SelectLabel)e.getSource();

			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			sl.setSelected(true);


			String type = sl.getText();
			PlayerSeason p = new PlayerSeason(type);
			Thread t = new Thread(p);
			t.start();
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
				HotPlayerSeasonPanel.this.remove(rankingPanel);
			}
			
			getRankingPanel("场均得分");
		}
		
	}
	
	class PlayerSeason implements Runnable{

		String typech;

		public PlayerSeason(String typech){
			this.typech = typech;
		}

		@Override
		public void run() {
			HotPlayerSeasonPanel.this.getRankingPanel(typech);
		}
	}

}
