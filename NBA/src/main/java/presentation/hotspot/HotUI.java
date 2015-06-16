package presentation.hotspot;

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
import presentation.contenui.TurnController;
import presentation.mainui.WebFrame;

public class HotUI extends BgPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String bgStr = "img/hotspot/whitebg.jpg";

	private JLabel titleLabel;
	private GLabel rightBt;
	

	private Point2D[] polygonPlayerToday = {new Point(433,87),new Point(504,157),new Point(433,228),new Point(363,157)};
	private Point2D[] polygonTeamSeason = {new Point(326,333),new Point(397,403),new Point(326,474),new Point(256,403)};
	private Point2D[] polygonPlayerSeason = {new Point(679,194),new Point(750,264),new Point(679,335),new Point(609,264)};
	private Point2D[] polygonPlayerFast = {new Point(572,440),new Point(643,510),new Point(572,581),new Point(502,510)};

	private BgPanel hotPanel;
	
	private TurnController controller = new TurnController();
	
	enum RunType{
		teamseason,
		playertoday,
		playerfast,
		playerseason,
		back,
	}
	
	@Override
	public void refreshUI() {
		if(hotPanel!=null){
			hotPanel.refreshUI();
		}else{
//			this.remove(text);
			this.remove(titleLabel);
			this.remove(rightBt);
			
			init();
			
		}
	}

	public HotUI(String s) {
		super(bgStr);
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {}

		this.setSize(1000, 670);
		this.setLocation(15, 50);
		this.setLayout(null);
		this.setOpaque(true);
		
		init();
		


	}
	
	//----------初始化页面----------
	private void init(){

		titleLabel = new JLabel();
		titleLabel.setBounds(0, 0, 1000, 670);
		titleLabel.setIcon(HotspotUtil.titleIcon);
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





	//---------------选项监听---------------
	//判断鼠标移动、点击位置是否在对应选项的多边形范围内
	class StatsListener implements MouseListener,MouseMotionListener{

		public void mouseClicked(MouseEvent e) {
			Point p = e.getPoint();
			if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerToday)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.HOT_PLAYERTODAY), "当日热点球员");	
				
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonTeamSeason)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.HOT_TEAMSEASON), "赛季热点球队");	
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerSeason)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.HOT_PLAYERSEASON), "赛季热点球员");	
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerFast)){
				WebFrame.frame.setPanel(controller.turn(PanelKind.HOT_PLAYERPROGRESS), "进步最快球员");	
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
			titleLabel.setIcon(HotspotUtil.titleIcon);
			titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseMoved(MouseEvent e) {
			Point p = e.getPoint();
			if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerToday)){
				titleLabel.setIcon(HotspotUtil.title_playertoday);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonTeamSeason)){
				titleLabel.setIcon(HotspotUtil.title_teamseason);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerSeason)){
				titleLabel.setIcon(HotspotUtil.title_playerseason);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else if(HotUI.this.checkWithJdkPolygon(p, polygonPlayerFast)){
				titleLabel.setIcon(HotspotUtil.title_playerfast);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}else{
				titleLabel.setIcon(HotspotUtil.titleIcon);
				titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}

	}



}
