package presentation.mainui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GFrame;
import presentation.component.GLabel;
import presentation.component.WebLabel;
import presentation.contenui.UIUtil;

public class WebFrame extends GFrame{

	private static final long serialVersionUID = 1L;
	private GLabel title, bg, menuPanel, menuButton;
	private GLabel hot, player, team, match, stats;
	public static WebFrame frame;
	private Vector<WebLabel> labelVector = new Vector<WebLabel>();
	private Vector<BgPanel> panelVector = new Vector<BgPanel>();
	private int a = 0, b = 255, c = 125;
	
	public static void main(String[] args) {
		new WebFrame();
	}

	public WebFrame(){
		this.setSize(1000, 700);
		this.setVisible(true);
		this.setMiddle();
		this.setLayout(null);
		init();
		MouseAdapter mouseListener = new MouseAdapter(){

			int xOld = 0;
			int yOld = 0;

			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				WebFrame.this.setLocation(xx, yy);
			}
		};

		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
		
		frame = this;
	}
	
	private void init(){
		title = new GLabel("NBA", new Point(0, 0), new Point(this.getWidth(), 35), this, true, 0 , 15);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBackground(UIUtil.nbaBlue);
		title.setForeground(UIUtil.bgWhite);
		title.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		title.setOpaque(true);
		
		title.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				BgPanel panel = new BgPanel("");
				panel.setBounds(0, 60, 1000, 640);
				panel.setBackground(new Color(a%255, b%255, c%255));
				panel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
				frame.setPanel(panel, "Player");
				a+=10;
				b = 2*a;
				c = 3*a;
			}
		});
		
		menuButton = new GLabel("目录", new Point(15, 8), new Point(35, 20), title, true, 0, 15);
		menuButton.setBackground(UIUtil.nbaBlue);
		menuButton.setForeground(UIUtil.bgWhite);
		menuButton.setBorder(BorderFactory.createLineBorder(UIUtil.bgWhite, 1));
		menuButton.setOpaque(true);
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e){
				if(labelVector.size() == 0){
					menuPanel.setLocation(0, 35);
					if(menuPanel.isVisible()){
						menuPanel.setVisible(false);
					}else{
						menuPanel.setVisible(true);
					}
				}else{
					menuPanel.setLocation(0, 60);
					if(menuPanel.isVisible()){
						menuPanel.setVisible(false);
					}else{
						menuPanel.setVisible(true);
					}
				}
			}
		});
		
		
		menuPanel = new GLabel("", new Point(0, 35), new Point(200, 665), this, false);
		menuPanel.setBackground(UIUtil.tableGrey);
		menuPanel.setOpaque(true);
		this.add(menuPanel, new Integer(Integer.MAX_VALUE));
		
		bg = new GLabel("", new Point(0, 35), new Point(1000, 700),  this, true);
		bg.setBackground(UIUtil.bgWhite);
		bg.setOpaque(true);
		
		menuInit();
	}
	
	private void menuInit(){
		Point size = new Point(200, 35);
		Point location = new Point(0, 10);
		
		hot = new GLabel("热点", location, size, menuPanel, true, 0, 20);
		hot.setForeground(Color.BLACK);
		hot.setHorizontalAlignment(JLabel.CENTER);
		hot.setBackground(menuPanel.getBackground());
		hot.setOpaque(true);
		hot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				hot.setCursor(new Cursor(Cursor.HAND_CURSOR));
				hot.setForeground(UIUtil.bgWhite);
				hot.setBackground(UIUtil.bgGrey);
			}
			@Override
			public void mouseExited(MouseEvent e){
				hot.setBackground(menuPanel.getBackground());
				hot.setForeground(Color.BLACK);
			}
		});
		
		player = new GLabel("球员", new Point(location.x, location.y+size.y), size, menuPanel, true, 0, 20);
		player.setForeground(Color.BLACK);
		player.setHorizontalAlignment(JLabel.CENTER);
		player.setBackground(menuPanel.getBackground());
		player.setOpaque(true);
		player.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				player.setCursor(new Cursor(Cursor.HAND_CURSOR));
				player.setForeground(UIUtil.bgWhite);
				player.setBackground(UIUtil.bgGrey);
			}
			@Override
			public void mouseExited(MouseEvent e){
				player.setBackground(menuPanel.getBackground());
				player.setForeground(Color.BLACK);
			}
		});
		
		team = new GLabel("球队", new Point(location.x, location.y+2*size.y), size, menuPanel, true, 0, 20);
		team.setForeground(Color.BLACK);
		team.setHorizontalAlignment(JLabel.CENTER);
		team.setBackground(menuPanel.getBackground());
		team.setOpaque(true);
		team.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				team.setCursor(new Cursor(Cursor.HAND_CURSOR));
				team.setForeground(UIUtil.bgWhite);
				team.setBackground(UIUtil.bgGrey);
			}
			@Override
			public void mouseExited(MouseEvent e){
				team.setBackground(menuPanel.getBackground());
				team.setForeground(Color.BLACK);
			}
		});
		
		match = new GLabel("比赛", new Point(location.x, location.y+3*size.y), size, menuPanel, true, 0, 20);
		match.setForeground(Color.BLACK);
		match.setHorizontalAlignment(JLabel.CENTER);
		match.setBackground(menuPanel.getBackground());
		match.setOpaque(true);
		match.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				match.setCursor(new Cursor(Cursor.HAND_CURSOR));
				match.setForeground(UIUtil.bgWhite);
				match.setBackground(UIUtil.bgGrey);
			}
			@Override
			public void mouseExited(MouseEvent e){
				match.setBackground(menuPanel.getBackground());
				match.setForeground(Color.BLACK);
			}
		});

		stats = new GLabel("统计", new Point(location.x, location.y+4*size.y), size, menuPanel, true, 0, 20);
		stats.setForeground(Color.BLACK);
		stats.setHorizontalAlignment(JLabel.CENTER);
		stats.setBackground(menuPanel.getBackground());
		stats.setOpaque(true);
		stats.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				stats.setCursor(new Cursor(Cursor.HAND_CURSOR));
				stats.setForeground(UIUtil.bgWhite);
				stats.setBackground(UIUtil.bgGrey);
			}
			@Override
			public void mouseExited(MouseEvent e){
				stats.setBackground(menuPanel.getBackground());
				stats.setForeground(Color.BLACK);
			}
		});

	}
	
	public void setPanel(BgPanel newPanel, String message){
		menuPanel.setVisible(false);
		bg.setVisible(false);
		
		for(int i=0;i<panelVector.size();i++){
			panelVector.get(i).setVisible(false);
		}
		panelVector.addElement(newPanel);//System.out.println(panelVector);
		this.add(panelVector.get(panelVector.size()-1));
		
		WebLabel gLabel = new WebLabel(" "+message, new Point(0, 0), new Point(0, 0), this, true, 0, 15, newPanel);
		gLabel.setHorizontalAlignment(JLabel.CENTER);
		gLabel.setBackground(UIUtil.nbaBlue);
		gLabel.setForeground(UIUtil.bgWhite);
		gLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		gLabel.setOpaque(true);
		
		labelVector.addElement(gLabel);
		int w = (this.getWidth())/labelVector.size();
		for(int i = 0;i<labelVector.size();i++){
			if(i<labelVector.size()-1){
				labelVector.get(i).setSize(w, 25);
				labelVector.get(i).setBackground(UIUtil.nbaBlue);
			}else{
				labelVector.get(i).setSize(this.getWidth()-i*w, 25);
				labelVector.get(i).setBackground(UIUtil.nbaRed);
			}
			labelVector.get(i).setLocation(w*i, 35);
		}

		this.repaint();
	}
	
	public Vector<WebLabel> getLabel(){
		return this.labelVector;
	}
	
	public Vector<BgPanel> getPanel(){
		return this.panelVector;
	}

	public void setLabelLocation(){
		if(labelVector.size()!=0){
			int w = (this.getWidth())/labelVector.size();
			for(int i = 0;i<labelVector.size();i++){
				if(i<labelVector.size()-1){
					labelVector.get(i).setSize(w, 25);
					labelVector.get(i).setBackground(UIUtil.nbaBlue);
				}else{
					labelVector.get(i).setSize(this.getWidth()-i*w, 25);
					labelVector.get(i).setBackground(UIUtil.nbaRed);
				}
				labelVector.get(i).setLocation(w*i, 35);
			}

			panelVector.get(panelVector.size()-1).setVisible(true);
		}else{
			if(menuPanel.isVisible()){
				menuPanel.setLocation(0, 35);
			}else{
				menuPanel.setLocation(0, 60);
			}
			bg.setVisible(true);
		}
		this.repaint();
	}
}
