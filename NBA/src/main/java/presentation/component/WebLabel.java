package presentation.component;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
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
	
	private Font unchosen = new Font("微软雅黑",0,15);
	private Font chosen = new Font("微软雅黑",1,15);

	public WebLabel(String message, Point location, Point size, Container container, 
			boolean visible, int bord, int wordSize, 
			JPanel panel){
		this.setBounds(location.x, location.y, size.x, size.y);
		this.setVisible(visible);
		if(panel != null){
			this.panel = panel;
		}
		if(container != null){
			container.add(this);
		}
		text = new GLabel(message, new Point(30, 0), new Point(100, 25), this, true, bord, wordSize);
		//text.setHorizontalAlignment(JLabel.CENTER);
		text.setBackground(UIUtil.nbaBlue);
		text.setForeground(UIUtil.bgWhite);
		text.setOpaque(true);
		text.setFont(new java.awt.Font("微软雅黑", bord, wordSize));
		
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
		label = new WebLabel("X", new Point(10, 0), new Point(20, 25), true, 0, 15, null);
		label.setForeground(UIUtil.bgWhite);
		label.setLabel(this);
		
		this.add(label);
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				label.setVisible(true);
				label.setCursor(new Cursor(Cursor.HAND_CURSOR));
				label.setFont(chosen);
			}
			public void mouseExited(MouseEvent e){
				label.setFont(unchosen);
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
			WebFrame.frame.getLabel().get(i).text.setBackground(WebFrame.frame.getBg().getBackground());
			WebFrame.frame.getLabel().get(i).text.setForeground(UIUtil.foreGrey);
			WebFrame.frame.getLabel().get(i).isSelected = false;
			WebFrame.frame.getLabel().get(i).label.setVisible(false);
			WebFrame.frame.getLabel().get(i).label.setForeground(UIUtil.foreGrey);
		}
		for(int i = 0;i<WebFrame.frame.getPanel().size();i++){
			WebFrame.frame.getPanel().get(i).setVisible(false);
		}
		this.text.setBackground(UIUtil.nbaBlue);
		this.text.setForeground(UIUtil.bgWhite);
		this.isSelected = true;
		this.label.setVisible(true);
		this.label.setForeground(UIUtil.nbaBlue);
		this.getPanel().setVisible(true);
	}

}
