package presentation.component;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;

public class WebLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	
	private JPanel panel = null;
	private WebLabel label = null;
	private GLabel text = null;
	public boolean isSelected;

	public WebLabel(String message, Point location, Point size, Container container, 
			boolean visible, int bord, int wordSize, 
			JPanel panel){
		this.setText(message);
		this.setBounds(location.x, location.y, size.x, size.y);
		this.setVisible(visible);
		this.setFont(new java.awt.Font("微软雅黑", bord, wordSize));
		if(panel != null){
			this.panel = panel;
		}
		if(container != null){
			container.add(this);
		}
//		text = new GLabel(message, new Point(35, 0), new Point(size.x-35, size.y), this, true, bord, wordSize);
//		text.setHorizontalAlignment(JLabel.CENTER);
		
		setClose();
		setListener();
	}
	
	public WebLabel(String message, Point location, Point size, 
			boolean visible, int bord, int wordSize, 
			BgPanel panel){
		this.setText(message);
		this.setBounds(location.x, location.y, size.x, size.y);
		this.setVisible(visible);
		this.setFont(new java.awt.Font("微软雅黑", bord, wordSize));
		if(panel != null){
			this.panel = panel;
		}
	}
	
	private void setClose(){
		label = new WebLabel("✕︎", new Point(10, 0), new Point(25, 25), true, 0, 15, null);
		label.setForeground(UIUtil.bgWhite);
		label.setLabel(this);
		
		this.add(label);
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				label.setVisible(true);
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				label.setText("✖︎");
			}
			public void mouseExited(MouseEvent e){
				label.setText("✕︎");
				if(WebLabel.this.isSelected == false){
					label.setVisible(false);
				}
			}
			public void mousePressed(MouseEvent e){
				WebFrame.frame.getLabel().removeElement(label.getLabel());
				WebFrame.frame.getPanel().removeElement(label.getLabel().getPanel());
				WebFrame.frame.getmenuLabel().remove(label.getLabel());
				WebFrame.frame.getpanelBg().remove(label.getLabel().getPanel());
				WebFrame.frame.repaint();
				if(WebLabel.this.isSelected){
					WebFrame.frame.setLabelLocation(true);
				}else{
					WebFrame.frame.setLabelLocation(false);
				}
			}
		});
	}
	
	private void setListener(){
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				WebLabel label = (WebLabel) e.getSource();
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				label.label.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e){
				WebLabel label = (WebLabel) e.getSource();
				if(label.isSelected == false){
					label.label.setVisible(false);
				}
			}
			@Override
			public void mousePressed(MouseEvent e){
				WebLabel label = (WebLabel) e.getSource();
				label.setSelected();
			}
		});
	}
	
	public void setLabel(WebLabel label){
		this.label = label;
	}
	
	public JPanel getPanel(){
		return this.panel;
	}
	
	public WebLabel getLabel(){
		return this.label;
	}
	
	public void setSelected(){
		for(int i = 0;i<WebFrame.frame.getLabel().size();i++){
			WebFrame.frame.getLabel().get(i).setBackground(WebFrame.frame.getBg().getBackground());
			WebFrame.frame.getLabel().get(i).setForeground(UIUtil.foreGrey);
			WebFrame.frame.getLabel().get(i).isSelected = false;
			WebFrame.frame.getLabel().get(i).label.setVisible(false);
			WebFrame.frame.getLabel().get(i).label.setForeground(UIUtil.foreGrey);
		}
		for(int i = 0;i<WebFrame.frame.getPanel().size();i++){
			WebFrame.frame.getPanel().get(i).setVisible(false);
		}
		this.setBackground(UIUtil.nbaBlue);
		this.setForeground(UIUtil.bgWhite);
		this.isSelected = true;
		this.label.setVisible(true);
		this.label.setForeground(UIUtil.bgWhite);
		this.getPanel().setVisible(true);
	}

}
