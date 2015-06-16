package presentation.mainui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import presentation.component.GFrame;
import presentation.component.GLabel;
import presentation.component.WebLabel;
import presentation.contenui.PanelKind;
import presentation.contenui.TurnController;
import presentation.contenui.UIUtil;

public class WebFrame extends GFrame{

	private static final long serialVersionUID = 1L;
	private GLabel title, panelBg, close, bg, warn;
	public static WebFrame frame;
	private Vector<WebLabel> labelVector = new Vector<WebLabel>();
	private Vector<JPanel> panelVector = new Vector<JPanel>();
	private int a = 251, b = 251, c = 251;
	private JPanel currentPanel;
	
	private GLabel menuPanel, menuTitle, menuLabel;
	private GLabel[] menuButton = new GLabel[6];
	private GLabel[] menuButtonIcon = new GLabel[menuButton.length];
	
	private TurnController turnController;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new WebFrame();
			}
		});
	}

	public WebFrame(){
		this.setSize(1150, 700);
		this.setVisible(true);
		this.setMiddle();
		this.setLayout(null);
		
		turnController = new TurnController();
		
		bgInit();
		menuInit();
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
	
	private void menuInit(){
		menuPanel = new GLabel("", new Point(0, 0), new Point(150, 700), bg, true);
		menuLabel = new GLabel("", new Point(150, 0), new Point(1000, 70), bg, true);
		
		warn = new GLabel("页面已满:(", new Point(400, 2), new Point(200, 30), menuLabel, false, 0, 15);
		warn.setForeground(UIUtil.nbaRed);
		warn.setHorizontalAlignment(JLabel.CENTER);
//		
//		menuTitle = new GLabel("NBA", new Point(0, 0), new Point(250, 105), menuPanel, true, 1, 30);
//		menuTitle.setBackground(menuTitleColor);
//		menuTitle.setForeground(UIUtil.bgWhite);
//		menuTitle.setHorizontalAlignment(JLabel.CENTER);
//		menuTitle.setOpaque(true);
//		menuTitle.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e){
//				BgPanel panel = new BgPanel("");
//				panel.setBounds(0, 0, 1000, 670);
//				panel.setBackground(new Color(a%255, b%255, c%255));
//				frame.setPanel(panel, "Player");
//				a+=10;
//				b = 2*a;
//				c = 3*a;
//			}
//		});
		
		String buttonInfo[] = {"热点", "球员", "球队", "比赛", "统计", "分析"};
		//String buttonIconInfo[] = {"☀︎︎", "♂", "♗", "⚔︎︎", "⧲︎︎", "✍"};
		
		for(int i=0;i<menuButton.length;i++){
			menuButton[i] = new GLabel("", new Point(0, 105+(50)*i), new Point(150, 30), menuPanel, true, 0, 20);
			menuButton[i].setForeground(UIUtil.foreGrey);
			menuButton[i].setBackground(bg.getBackground());
			menuButton[i].setHorizontalAlignment(JLabel.CENTER);
			menuButton[i].setOpaque(true);
			menuButton[i].setText(buttonInfo[i]);
			menuButton[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			menuButton[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e){
					GLabel label = (GLabel)e.getSource();
					label.setForeground(UIUtil.bgWhite);
					label.setBackground(UIUtil.nbaBlue);
				}
				@Override
				public void mouseExited(MouseEvent e){
					GLabel label = (GLabel)e.getSource();
					label.setForeground(UIUtil.foreGrey);
					label.setBackground(bg.getBackground());
				}
			});
			
//			menuButtonIcon[i] = new GLabel("", new Point(0, 0), new Point(90, 80), menuButton[i], true, 1, 40);
//			menuButtonIcon[i].setBackground(menuButtonIconColor);
//			menuButtonIcon[i].setForeground(UIUtil.bgWhite);
//			menuButtonIcon[i].setHorizontalAlignment(JLabel.CENTER);
//			menuButtonIcon[i].setOpaque(true);
//			menuButtonIcon[i].setText(buttonIconInfo[i]);
		}
		//this.add(menuPanel, new Integer(Integer.MAX_VALUE));
		menuButton[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT), label.getText());
			}
		});
		menuButton[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.PLAYER), label.getText());
			}
		});
		menuButton[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.TEAM), label.getText());
			}
		});
		menuButton[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.MATCH), label.getText());
			}
		});
		menuButton[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.STATS), label.getText());
			}
		});
		menuButton[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.STATS), label.getText());
			}
		});
		
//		close = new GLabel("⦿", new Point(0, 105+(80+2)*(menuButton.length)-2), new Point(250, this.getHeight()-30-(105+(80+2)*(menuButton.length))), menuPanel, true, 1, 50);
//		close.setForeground(UIUtil.bgWhite);
//		close.setHorizontalAlignment(JLabel.CENTER);
//		close.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent e){
//				close.setCursor(new Cursor(Cursor.HAND_CURSOR));
//			}
//			public void mousePressed(MouseEvent e){
//				WebFrame.this.dispose();
//			}
//		});
	}
	
	private void bgInit(){
		bg = new GLabel("", new Point(0, 0), new Point(1150, 700), this, true);
		bg.setBackground(UIUtil.lightGrey);
		bg.setOpaque(true); 
		
		panelBg = new GLabel("", new Point(180, 80), new Point(958, 600), bg, true);
		panelBg.setBackground(bg.getBackground());
		panelBg.setOpaque(true);
		
		title = new GLabel("NBA", new Point(100, 0), new Point(140, 60), bg, true, 0, 30);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(UIUtil.bgWhite);
		title.setBackground(UIUtil.nbaBlue);
		title.setOpaque(true);
	}
	
	public void setPanel(JPanel newPanel, String message){
		if(labelVector.size()<10){
			for(int i=0;i<panelVector.size();i++){
				panelVector.get(i).setVisible(false);
			}
			panelVector.addElement(newPanel);
			currentPanel = newPanel;
			currentPanel.setVisible(true);
			panelBg.add(currentPanel);


			WebLabel gLabel = new WebLabel(" "+message, new Point(100+labelVector.size()*130, 35), new Point(130, 25), menuLabel, true, 0, 15, newPanel);
			labelVector.addElement(gLabel);
			gLabel.setSelected();
			
			setLabelLocation(false);
			showWarn();

//			for(int i = 0;i<labelVector.size()-1;i++){
//				labelVector.get(i).setBackground(bg.getBackground());
//				labelVector.get(i).setForeground(UIUtil.foreGrey);
//				labelVector.get(i).getLabel().setForeground(UIUtil.foreGrey);
//				labelVector.get(i).isSelected = false;
//				labelVector.get(i).getLabel().setVisible(false);
//			}
		}
		
		this.repaint();
	}

	public Vector<WebLabel> getLabel(){
		return this.labelVector;
	}

	public Vector<JPanel> getPanel(){
		return this.panelVector;
	}

	public void setLabelLocation(boolean key){
		setLabelSize();
		
		if(key){
			if(labelVector.size()!=0){
				int size = labelVector.get(0).getSize().width;
				for(int i = 0;i<labelVector.size();i++){
					labelVector.get(i).setLocation(100+i*size, 35);
				}
				labelVector.get(labelVector.size()-1).setSelected();
			}
		}else{
			int size = labelVector.get(0).getSize().width;
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setLocation(100+i*size, 35);
			}
		}
		
		showWarn();
		this.repaint();
	}
	
	public void setLabelSize(){
		switch (labelVector.size()) {
		case 0:break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setSize(130, 25);
			}
			break;
		}
		case 7:{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setSize(125, 25);
			}
			break;
		}
		case 8:{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setSize(110, 25);
			}
			break;
		}
		case 9:{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setSize(99, 25);
			}
			break;
		}
		case 10:{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setSize(89, 25);
			}
			break;
		}
		default:
			break;
		}
	}
	
	public void showWarn(){
		if(labelVector.size() == 10){
			warn.setVisible(true);
		}else{
			warn.setVisible(false);
		}
	}
	
	public GLabel getmenuLabel(){
		return menuLabel;
	}

	public GLabel getBg(){
		return bg;
	}
	public GLabel getpanelBg(){
		return panelBg;
	}
}
