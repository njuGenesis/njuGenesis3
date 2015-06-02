package presentation.mainui;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;

import presentation.component.BgPanel;
import presentation.component.GFrame;
import presentation.component.GLabel;
import presentation.component.WebLabel;
import presentation.contenui.UIUtil;

public class WebFrame extends GFrame{

	private static final long serialVersionUID = 1L;
	private GLabel title, bg;
	public static WebFrame frame;
	private Vector<WebLabel> labelVector = new Vector<WebLabel>();
	private Vector<BgPanel> panelVector = new Vector<BgPanel>();
	private int a = 251, b = 251, c = 251;
	
	private GLabel menuPanel, menuTitle, menuLabel;
	private GLabel[] menuButton = new GLabel[6];
	private GLabel[] menuButtonIcon = new GLabel[menuButton.length];
	
	private Color menuPanelColor = new Color(192, 27, 44);
	private Color menuTitleColor = new Color(238, 34, 57);
	private Color menuLabelColor = new Color(222, 35, 56);
	private Color menuButtonColor = new Color(215, 36, 55);
	private Color menuButtonIconColor = new Color(199, 32, 49);
	private Color labelColor = new Color(242, 224, 224);
	
	public static void main(String[] args) {
		new WebFrame();
	}

	public WebFrame(){
		this.setSize(1250, 700);
		this.setVisible(true);
		this.setMiddle();
		this.setLayout(null);
		menuInit();
		bgInit();
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
		menuPanel = new GLabel("", new Point(0, 30), new Point(250, 670), this, true);
		menuPanel.setBackground(menuPanelColor);
		menuPanel.setOpaque(true);
		
		menuLabel = new GLabel("", new Point(0, 0), new Point(1250, 30), this, true, 1, 20);
		menuLabel.setBackground(menuLabelColor);
		menuLabel.setOpaque(true);
		
		menuTitle = new GLabel("NBA", new Point(0, 0), new Point(250, 105), menuPanel, true, 1, 30);
		menuTitle.setBackground(menuTitleColor);
		menuTitle.setForeground(UIUtil.bgWhite);
		menuTitle.setHorizontalAlignment(JLabel.CENTER);
		menuTitle.setOpaque(true);
		menuTitle.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				BgPanel panel = new BgPanel("");
				panel.setBounds(0, 0, 1000, 670);
				panel.setBackground(new Color(a%255, b%255, c%255));
				frame.setPanel(panel, "Player");
				a+=10;
				b = 2*a;
				c = 3*a;
			}
		});
		
		for(int i=0;i<menuButton.length;i++){
			menuButton[i] = new GLabel("", new Point(0, 105+(80+2)*i), new Point(250, 80), menuPanel, true, 1, 15);
			menuButton[i].setBackground(menuButtonColor);
			menuButton[i].setForeground(UIUtil.bgWhite);
			menuButton[i].setHorizontalAlignment(JLabel.CENTER);
			menuButton[i].setOpaque(true);
			
			menuButtonIcon[i] = new GLabel("", new Point(0, 0), new Point(90, 80), menuButton[i], true, 1, 40);
			menuButtonIcon[i].setBackground(menuButtonIconColor);
			menuButtonIcon[i].setForeground(UIUtil.bgWhite);
			menuButtonIcon[i].setHorizontalAlignment(JLabel.CENTER);
			menuButtonIcon[i].setOpaque(true);
		}
		menuButton[0].setText("热点");menuButtonIcon[0].setText("☀︎︎");
		menuButton[1].setText("球员");menuButtonIcon[1].setText("♂");
		menuButton[2].setText("球队");menuButtonIcon[2].setText("♗");
		menuButton[3].setText("比赛");menuButtonIcon[3].setText("⚔︎︎");
		menuButton[4].setText("统计");menuButtonIcon[4].setText("⧲︎︎");
		menuButton[5].setText("分析");menuButtonIcon[5].setText("✍");
		//this.add(menuPanel, new Integer(Integer.MAX_VALUE));
		
		
	}
	
	private void bgInit(){
		bg = new GLabel("", new Point(250, 30), new Point(1000, 670), this, true);
		bg.setBackground(UIUtil.bgWhite);
		bg.setOpaque(true);
	}
	
	public void setPanel(BgPanel newPanel, String message){
		
		for(int i=0;i<panelVector.size();i++){
			panelVector.get(i).setVisible(false);
		}
		panelVector.addElement(newPanel);
		bg.add(panelVector.get(panelVector.size()-1));
		
		WebLabel gLabel = new WebLabel(" "+message, new Point(250+labelVector.size()*130, 0), new Point(130, 30), menuLabel, true, 0, 15, newPanel);
		gLabel.setHorizontalAlignment(JLabel.CENTER);
		gLabel.setBackground(UIUtil.bgWhite);
		gLabel.setForeground(UIUtil.nbaRed);
		gLabel.isSelected = true;
		//gLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		gLabel.setOpaque(true);
		
		labelVector.addElement(gLabel);
		for(int i = 0;i<labelVector.size()-1;i++){
			labelVector.get(i).setBackground(menuLabelColor);
			labelVector.get(i).setForeground(UIUtil.bgWhite);
			labelVector.get(i).getLabel().setForeground(UIUtil.bgWhite);
			labelVector.get(i).isSelected = false;
			labelVector.get(i).getLabel().setVisible(false);
		}

		this.repaint();
	}
	
	public Vector<WebLabel> getLabel(){
		return this.labelVector;
	}
	
	public Vector<BgPanel> getPanel(){
		return this.panelVector;
	}

	public void setLabelLocation(boolean key){
		if(key){
			if(labelVector.size()!=0){
				for(int i = 0;i<labelVector.size();i++){
					labelVector.get(i).setLocation(250+i*130, 0);
				}
				labelVector.get(labelVector.size()-1).setSelected();
			}
		}else{
			for(int i = 0;i<labelVector.size();i++){
				labelVector.get(i).setLocation(250+i*130, 0);
			}
		}
		this.repaint();
	}
	
	public GLabel getmenuLabel(){
		return menuLabel;
	}
	
	public GLabel getbg(){
		return bg;
	}
}
