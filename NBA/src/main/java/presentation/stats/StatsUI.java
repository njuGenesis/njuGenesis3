package presentation.stats;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import presentation.component.BgPanel;
import presentation.component.GLabel;
import presentation.contenui.PanelKind;
import presentation.contenui.StatsUtil;
import presentation.contenui.TurnController;
import presentation.mainui.WebFrame;

public class StatsUI extends BgPanel{

	private static final long serialVersionUID = 1L;


	private static String bgStr = "img/hotspot/whitebg.jpg";


	private JLabel titleLabel;

	private BgPanel statsPanel;


	Point2D[] polygonPlayer = {new Point(313,322),new Point(384,392),new Point(313,463),new Point(243,392)};
	Point2D[] polygonTeam = {new Point(666,183),new Point(737,253),new Point(666,324),new Point(596,253)};

	private TurnController controller = new TurnController();
	
	enum RunType{
		team,
		player,
		back,
	}

	@Override
	public void refreshUI() {
		if(statsPanel!=null){
			statsPanel.refreshUI();
		}else{
			this.remove(titleLabel);
			
			init();
		}
	}

	public StatsUI(String s) {
		super(bgStr);

		
		this.setBounds(0, 0, 940, 600);
		this.setLayout(null);
		this.setOpaque(false);

		init();
	}

	private void init(){


		titleLabel = new JLabel();
		titleLabel.setBounds(0,0,1000,670);
		titleLabel.setIcon(StatsUtil.title);
		titleLabel.addMouseMotionListener(new StatsListener());
		titleLabel.addMouseListener(new StatsListener());
		this.add(titleLabel);

		this.repaint();
	}


	/**
	 * @param point 要判断的点
	 * @param polygon 多边形顶点集合
	 * @return 点是否在多边形范围内
	 */
	public boolean checkWithJdkPolygon(Point2D point, Point2D[] polygon) {
		java.awt.Polygon p = new Polygon();
		// java.awt.geom.GeneralPath
		final int TIMES = 1000;
		for (Point2D d : polygon) {
			int x = (int) d.getX() * TIMES;
			int y = (int) d.getY() * TIMES;
			p.addPoint(x, y);
		}
		int x = (int) point.getX() * TIMES;
		int y = (int) point.getY() * TIMES;
		return p.contains(x, y);
	}

	


	class StatsListener implements MouseListener,MouseMotionListener{

		public void mouseClicked(MouseEvent e) {
			Point p = e.getPoint();
			if(StatsUI.this.checkWithJdkPolygon(p, polygonPlayer)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.STATS_PLAYER), "球员数据");	
			}else if(StatsUI.this.checkWithJdkPolygon(p, polygonTeam)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.STATS_TEAM), "球队数据");	
			}
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
			titleLabel.setIcon(StatsUtil.title);
			titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseMoved(MouseEvent e) {
			Point p = e.getPoint();
			if(StatsUI.this.checkWithJdkPolygon(p, polygonPlayer)){
				titleLabel.setIcon(StatsUtil.title_player);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else if(StatsUI.this.checkWithJdkPolygon(p, polygonTeam)){
				titleLabel.setIcon(StatsUtil.title_team);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else{
				titleLabel.setIcon(StatsUtil.title);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}


}
