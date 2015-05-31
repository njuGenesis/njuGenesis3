package presentation.component;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import presentation.contenui.UIUtil;
import presentation.mainui.WebFrame;

public class WebLabel extends JLabel{

	private static final long serialVersionUID = 1L;
	
	private BgPanel panel = null;
	private WebLabel label = null;

	public WebLabel(String message, Point location, Point size, Container container, 
			boolean visible, int bord, int wordSize, 
			BgPanel panel){
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
		label = new WebLabel("X", new Point(10, 0), new Point(25, 25), true, 0, 15, null);
		label.setForeground(UIUtil.bgWhite);
		label.setLabel(this);
		
		this.add(label);
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				label.setForeground(UIUtil.bgGrey);
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e){
				label.setForeground(UIUtil.bgWhite);
			}
			public void mousePressed(MouseEvent e){
				WebFrame.frame.getLabel().removeElement(label.getLabel());
				WebFrame.frame.getPanel().removeElement(label.getLabel().getPanel());
				WebFrame.frame.remove(label.getLabel());
				WebFrame.frame.remove(label.getLabel().getPanel());
				WebFrame.frame.repaint();//System.out.println(WebFrame.frame.getPanel());
				WebFrame.frame.setLabelLocation();
			}
		});
	}
	
	private void setListener(){
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e){
				WebLabel label = (WebLabel) e.getSource();
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e){
				for(int i = 0;i<WebFrame.frame.getLabel().size();i++){
					WebFrame.frame.getLabel().get(i).setBackground(UIUtil.nbaBlue);
				}
				for(int i = 0;i<WebFrame.frame.getPanel().size();i++){
					WebFrame.frame.getPanel().get(i).setVisible(false);
				}
				WebLabel label = (WebLabel) e.getSource();
				label.setBackground(UIUtil.nbaRed);
				label.getPanel().setVisible(true);
				//title.setText(label.getText());
			}
		});
	}
	
	public void setLabel(WebLabel label){
		this.label = label;
	}
	
	public BgPanel getPanel(){
		return this.panel;
	}
	
	public WebLabel getLabel(){
		return this.label;
	}
}
