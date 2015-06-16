package presentation.mainui;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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

	private GLabel[] hotButton = new GLabel[4];
	private String[] hotInfo = {"当天热点球员","赛季热点球员","进步最快球员","赛季热点球队"};

	private GLabel[] statsButton = new GLabel[2];
	private String[] statsInfo = {"球员数据","球队数据",};

	private boolean clicked_hot = false;
	private boolean clicked_stats = false;

	private GLabel exit,mini;
	private ImageIcon iconExit = new ImageIcon("img/frame/exitBt.png");
	private ImageIcon iconExit_chosen = new ImageIcon("img/frame/exitBtEnter.png");
	private ImageIcon iconMini = new ImageIcon("img/frame/miniBt.png");
	private ImageIcon iconMini_chosen = new ImageIcon("img/frame/miniBtEnter.png");
	
	private GLabel bgLabel;

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
		menuInit_hot();
		menuInit_stats();
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

		String buttonInfo[] = {"热点", "球员", "球队", "比赛", "数据", "比较"};
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
				//				GLabel label = (GLabel) e.getSource();
				//				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT), label.getText());
				if(clicked_hot){
					moveHot(false);
					clicked_hot = false;

				}else{
					moveHot(true);
					clicked_hot = true;

				}
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
				//				GLabel label = (GLabel) e.getSource();
				//				WebFrame.this.setPanel(turnController.turn(PanelKind.STATS), label.getText());
				if(clicked_stats){
					moveStats(false);
					clicked_stats = false;

				}else{
					moveStats(true);
					clicked_stats = true;

				}
			}
		});
		menuButton[5].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.COMPARE), label.getText());

			}
		});

//		close = new GLabel("X", new Point(0, 105+(80+2)*(menuButton.length)-2), new Point(250, this.getHeight()-30-(105+(80+2)*(menuButton.length))), menuPanel, true, 1, 50);
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

	private void moveHot(boolean down){

		int d;
		if(down){
			d = 200;
		}else{
			d = -200;
		}
		for(int i=1;i<6;i++){
			menuButton[i].setLocation(menuButton[i].getX(), menuButton[i].getY()+d);
		}

		for(int i=0;i<hotButton.length;i++){
			hotButton[i].setVisible(down);
		}

		if(clicked_stats){
			for(int i=0;i<statsButton.length;i++){
				statsButton[i].setLocation(0, menuButton[4].getY()+50+50*i);
			}
		}

		this.repaint();
	}

	private void moveStats(boolean down){

		int d;
		if(down){
			d = 100;
		}else{
			d = -100;
		}
		for(int i=5;i<6;i++){
			menuButton[i].setLocation(menuButton[i].getX(), menuButton[i].getY()+d);
		}

		for(int i=0;i<statsButton.length;i++){
			statsButton[i].setLocation(0, menuButton[4].getY()+50+50*i);
			statsButton[i].setVisible(down);
		}

		this.repaint();
	}


	private void menuInit_hot(){

		for(int i=0;i<4;i++){
			hotButton[i] = new GLabel("", new Point(0, menuButton[0].getY()+50+(50)*i), new Point(150, 30), menuPanel, true, 0, 14);
			hotButton[i].setForeground(UIUtil.foreGrey);
			hotButton[i].setBackground(bg.getBackground());
			hotButton[i].setHorizontalAlignment(JLabel.CENTER);
			hotButton[i].setOpaque(true);
			hotButton[i].setText(hotInfo[i]);
			hotButton[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			hotButton[i].setVisible(false);

			hotButton[i].addMouseListener(new MouseAdapter() {
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
		}
		hotButton[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT_PLAYERTODAY), label.getText());
			}
		});
		hotButton[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT_PLAYERSEASON), label.getText());
			}
		});
		hotButton[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT_PLAYERPROGRESS), label.getText());
			}
		});
		hotButton[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.HOT_TEAMSEASON), label.getText());
			}
		});

	}

	private void menuInit_stats(){

		for(int i=0;i<2;i++){
			statsButton[i] = new GLabel("", new Point(0, menuButton[4].getY()+50+(50)*i), new Point(150, 30), menuPanel, true, 0, 14);
			statsButton[i].setForeground(UIUtil.foreGrey);
			statsButton[i].setBackground(bg.getBackground());
			statsButton[i].setHorizontalAlignment(JLabel.CENTER);
			statsButton[i].setOpaque(true);
			statsButton[i].setText(statsInfo[i]);
			statsButton[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			statsButton[i].setVisible(false);

			statsButton[i].addMouseListener(new MouseAdapter() {
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
		}
		statsButton[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.STATS_PLAYER), label.getText());
			}
		});
		statsButton[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e){
				GLabel label = (GLabel) e.getSource();
				WebFrame.this.setPanel(turnController.turn(PanelKind.STATS_TEAM), label.getText());
			}
		});

	}

	private void bgInit(){
		bg = new GLabel("", new Point(0, 0), new Point(1150, 700), this, true);
		bg.setBackground(UIUtil.lightGrey);
		bg.setOpaque(true); 

		panelBg = new GLabel(new ImageIcon("img/content/homepanel.png"), new Point(180, 80), new Point(940, 600), bg, true);
		panelBg.setBackground(bg.getBackground());
//		panelBg.setBackground(UIUtil.bgWhite);
		panelBg.setOpaque(true);

		title = new GLabel("NBA", new Point(30, 0), new Point(100, 60), bg, true, 1, 30);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setForeground(UIUtil.bgWhite);
		title.setBackground(UIUtil.nbaBlue);
		title.setOpaque(true);
		
//		bgLabel = new GLabel(new ImageIcon(""),);
		
		exit = new GLabel(iconExit,new Point(1120,7),new Point(20,20),bg,true);
		exit.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				exit.setCursor(new Cursor(Cursor.HAND_CURSOR));
				exit.setIcon(iconExit_chosen);
			}
			public void mouseExited(MouseEvent e){
				exit.setIcon(iconExit);
			}
			public void mousePressed(MouseEvent e){
				WebFrame.this.dispose();
			}
		});

		mini = new GLabel(iconMini,new Point(1090,7),new Point(20,20),bg,true);
		mini.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				mini.setCursor(new Cursor(Cursor.HAND_CURSOR));
				mini.setIcon(iconMini_chosen);
			}
			public void mouseExited(MouseEvent e){
				mini.setIcon(iconMini);
			}
			public void mousePressed(MouseEvent e){
				WebFrame.this.setExtendedState(JFrame.ICONIFIED);
			}

		});
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
