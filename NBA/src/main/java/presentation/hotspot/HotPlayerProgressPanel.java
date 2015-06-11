package presentation.hotspot;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.StatsUtil;
import presentation.contenui.UIUtil;
import bussinesslogic.player.PlayerLogic;
import bussinesslogic.player.PlayerLogic_db;
import data.po.PlayerDataPO;

public class HotPlayerProgressPanel extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String bg = "";
	
	private GLabel title, borderUp, borderDown;

	SelectLabel score;  //场均得分
	SelectLabel backboard;  //场均篮板
	SelectLabel assis;  //场均助攻
	SelectLabel[] menuItem = new SelectLabel[3];

	PlayerLogic logic = new PlayerLogic();
	PlayerLogic_db logic_db = new PlayerLogic_db();
	
	JComboBox<String> seasonChooser;

	RankingFactory factory = new RankingFactory();
	JPanel rankingPanel;
	
	@Override
	public void refreshUI() {
		this.remove(title);
		this.remove(score);
		this.remove(backboard);
		this.remove(assis);
		this.remove(seasonChooser);
		this.remove(rankingPanel);

		init();
	}

	public HotPlayerProgressPanel() {
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
		
		title = new GLabel("   进步最快球员",new Point(0,4),new Point(940,52),this,true,0,24);
		title.setOpaque(true);
		title.setBackground(UIUtil.bgWhite);
		title.setForeground(UIUtil.nbaBlue);

		
		
		//--------------------标签--------------------
		score = new SelectLabel("场均得分",new Point(0,70),new Point(312,35),this,true,0,16);
//		score.setOpaque(true);
//		score.setBackground(UIUtil.bgGrey);
//		score.setForeground(UIUtil.bgWhite);
//		score.setHorizontalAlignment(JLabel.CENTER);
		score.setSelected(true);
		score.addMouseListener(new MenuListener());
		menuItem[0] = score;

		getRankingPanel("场均得分");

		backboard = new SelectLabel("场均篮板",new Point(313,70),new Point(313,35),this,true,0,16);
//		backboard.setOpaque(true);
//		backboard.setBackground(UIUtil.bgGrey);
//		backboard.setForeground(UIUtil.bgWhite);
//		backboard.setHorizontalAlignment(JLabel.CENTER);
		backboard.addMouseListener(new MenuListener());
		menuItem[1] = backboard;

		assis = new SelectLabel("场均助攻",new Point(627,70),new Point(314,35),this,true,0,16);
//		assis.setOpaque(true);
//		assis.setBackground(UIUtil.bgGrey);
//		assis.setForeground(UIUtil.bgWhite);
		assis.setHorizontalAlignment(JLabel.CENTER);
		assis.addMouseListener(new MenuListener());
		menuItem[2] = assis;

		this.repaint();
	}

	private String getType(String ch){
		switch(ch){
		case "场均得分":return "points";
		case "场均篮板":return "rebounds";
		case "场均助攻":return "assists";
		default:return "";
		}
	}
	
	public void getRankingPanel(String type){
		String en = getType(type);
		String[] info = this.logic_db.getProgressPlayer(en);

//		PlayerDataPO[] players = logic.progressPlayer(getSeasonStr(), type);
//		JPanel p = factory.getPlayerProgress(players,type);
		JPanel p = factory.getPlayerProgress(info);
		rankingPanel = p;
		this.add(rankingPanel);
		this.repaint();
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

			if(rankingPanel!=null){
				HotPlayerProgressPanel.this.remove(rankingPanel);
			}

			String type = sl.getText();
			HotPlayerProgressPanel.this.getRankingPanel(type);
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
				HotPlayerProgressPanel.this.remove(rankingPanel);
			}
			
			getRankingPanel("场均得分");
		}
		
	}
	
}
