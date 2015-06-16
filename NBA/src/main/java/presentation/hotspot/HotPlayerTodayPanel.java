package presentation.hotspot;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import presentation.component.BgPanel;
import presentation.component.DatePanel;
import presentation.component.GLabel;
import presentation.contenui.StatsUtil;
import presentation.contenui.UIUtil;
import presentation.hotspot.HotPlayerProgressPanel.SeasonListener;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.po.PlayerDataPO;

public class HotPlayerTodayPanel extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";


	private GLabel title, borderUp, borderDown;

	SelectLabel score;  //得分
	SelectLabel backboard;  //篮板
	SelectLabel assis;  //助攻
	SelectLabel block;  //盖帽
	SelectLabel steal;  //抢断
	SelectLabel[] menuItem = new SelectLabel[5];

	DatePanel date;

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

	public HotPlayerTodayPanel() {
		super(bg);


		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(UIUtil.bgWhite);

		init();
	}


	private void init(){

		//--------------------标题--------------------
		borderUp = new GLabel("", new Point(0,0), new Point(940,4), this, true);
		borderUp.setOpaque(true);
		borderUp.setBackground(UIUtil.nbaBlue);

		borderDown = new GLabel("", new Point(0,56), new Point(940,4), this, true);
		borderDown.setOpaque(true);
		borderDown.setBackground(UIUtil.nbaBlue);

		title = new GLabel("   当天热点球员",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		//--------------------标签--------------------
		score = new SelectLabel("得分",new Point(0,70),new Point(187,35),this,true,0,16);
		score.setSelected(true);
		score.addMouseListener(new MenuListener());
		menuItem[0] = score;

		//		getRankingPanel("得分");

		backboard = new SelectLabel("篮板",new Point(188,70),new Point(187,35),this,true,0,16);
		backboard.addMouseListener(new MenuListener());
		menuItem[1] = backboard;

		assis = new SelectLabel("助攻",new Point(376,70),new Point(187,35),this,true,0,16);
		assis.addMouseListener(new MenuListener());
		menuItem[2] = assis;

		block = new SelectLabel("盖帽",new Point(564,70),new Point(187,35),this,true,0,16);
		block.addMouseListener(new MenuListener());
		menuItem[3] = block;

		steal = new SelectLabel("抢断",new Point(752,70),new Point(187,35),this,true,0,16);
		steal.addMouseListener(new MenuListener());
		menuItem[4] = steal;

		PlayerToday pt = new PlayerToday("得分");
		Thread t = new Thread(pt);
		t.start();

		this.repaint();
	}

	public synchronized void getRankingPanel(String type){
		//		String season = getSeason(date);  
		//		String day = date.substring(5);
		if(rankingPanel!=null){
			HotPlayerTodayPanel.this.remove(rankingPanel);
		}

		String en = getType(type);
		String[] info = this.logic_db.getHotPlayerDaily(en);

		JPanel p = factory.getPlayerDaily(info,en+"_hotdaily");
		rankingPanel = p;
		HotPlayerTodayPanel.this.add(rankingPanel);
		HotPlayerTodayPanel.this.repaint();
	}

	private String getType(String ch){
		switch(ch){
		case "得分":return "points";
		case "篮板":return "rebounds";
		case "助攻":return "assists";
		case "盖帽":return "blocks";
		case "抢断":return "steals";
		default:return "";
		}
	}

	public String getSeason(String date){
		String season = "13-14";

		int year = 2010;
		while(true){
			String d1 = String.valueOf(year)+"-10-01";
			String d2 = String.valueOf(year+1)+"-06-30";
			if(date.compareTo(d1)>=0 && date.compareTo(d2)<=0){
				season = String.valueOf(year).substring(2, 4)+"-"+String.valueOf(year+1).substring(2, 4);
				break;
			}else{
				year++;
			}
		}
		return season;
	}

	private String getToday(){
		Date dateNow = new Date();  
		SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");  
		return dateFormat.format(dateNow);
	}


	class MenuListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			SelectLabel sl = (SelectLabel)e.getSource();

			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			sl.setSelected(true);


			String type = sl.getText();
			PlayerToday pt = new PlayerToday(type);
			Thread t = new Thread(pt);
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


	class DateListener implements DocumentListener{

		public void insertUpdate(DocumentEvent e) {
			for(int i=0;i<menuItem.length;i++){
				menuItem[i].setSelected(false);
			}
			score.setSelected(true);

			if(rankingPanel!=null){
				HotPlayerTodayPanel.this.remove(rankingPanel);
			}
			getRankingPanel("得分");

			HotPlayerTodayPanel.this.repaint();
		}

		public void removeUpdate(DocumentEvent e) {}

		public void changedUpdate(DocumentEvent e) {}

	}

	class PlayerToday implements Runnable{

		String typech;

		public PlayerToday(String typech){
			this.typech = typech;
		}

		@Override
		public void run() {
			HotPlayerTodayPanel.this.getRankingPanel(typech);
		}
	}


}
